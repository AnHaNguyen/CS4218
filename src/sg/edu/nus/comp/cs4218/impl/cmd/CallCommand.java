package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplemtation;

/**
 * A Call Command is a sub-command consisting of at least one non-keyword and
 * quoted (if any).
 * 
 * <p>
 * <b>Command format:</b> <code>(&lt;non-Keyword&gt; | &lt;quoted&gt;)*</code>
 * </p>
 */

public class CallCommand implements Command {
	public static final String EXP_INVALID_APP = "Invalid app.";
	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String EXP_REDIR_PIPE = "File output redirection and pipe "
			+ "operator cannot be used side by side.";
	public static final String EXP_SAME_REDIR = "Input redirection file same as "
			+ "output redirection file.";
	public static final String EXP_STDOUT = "Error writing to stdout.";
	public static final String EXP_NOT_SUPPORTED = " not supported yet";

	String app;
	String cmdline, inputStreamS, outputStreamS;
	String[] argsArray;
	Boolean error;
	String errorMsg;
	
	public static void main(String[] args) throws AbstractApplicationException, ShellException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String cmd = sc.nextLine();
			CallCommand cc = new CallCommand(cmd);
			cc.evaluate(System.in, System.out);
		//	System.out.println(cc.toString());
		}
	}
	
	public CallCommand(String cmdline) {
		this.cmdline = cmdline.trim();
		app = inputStreamS = outputStreamS = "";
		error = false;
		errorMsg = "";
		argsArray = new String[0];
	}

	public CallCommand() {
		this("");
	}

	/**
	 * Evaluates sub-command using data provided through stdin stream. Writes
	 * result to stdout stream.
	 * 
	 * @param stdin
	 *            InputStream to get data from.
	 * @param stdout
	 *            OutputStream to write resultant data to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while evaluating the sub-command.
	 * @throws ShellException
	 *             If an exception happens while evaluating the sub-command.
	 */
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		this.parse();
		//process parse (argument split) before glob
		
		if (error) {
			throw new ShellException(errorMsg);
		}

		InputStream inputStream;
		OutputStream outputStream;

		argsArray = ShellImplemtation.processBQ(argsArray);
		argsArray = expandGlob();		//handle globbing
		
		if (("").equals(inputStreamS)) {// empty
			inputStream = stdin;
		} else { // not empty
			inputStream = ShellImplemtation.openInputRedir(inputStreamS);
		}
		if (("").equals(outputStreamS)) { // empty
			outputStream = stdout;
		} else {
			outputStream = ShellImplemtation.openOutputRedir(outputStreamS);
		}

		ShellImplemtation.runApp(app, argsArray, inputStream, outputStream);
		ShellImplemtation.closeInputStream(inputStream);
		ShellImplemtation.closeOutputStream(outputStream);
	}

	/**
	 * Parses and splits the sub-command to the call command into its different
	 * components, namely the application name, the arguments (if any), the
	 * input redirection file path (if any) and output redirection file path (if
	 * any).
	 * 
	 * @throws ShellException
	 *             If an exception happens while parsing the sub-command, or if
	 *             the input redirection file path is same as that of the output
	 *             redirection file path.
	 */
	public void parse() throws ShellException {
		Vector<String> cmdVector = new Vector<String>();
		Boolean result = true;
		int endIdx = 0;
		String str = " " + cmdline + " ";
		try {
			endIdx = extractArgs(str, cmdVector);
			cmdVector.add(""); // reserved for input redir
			cmdVector.add(""); // reserved for output redir
			endIdx = extractInputRedir(str, cmdVector, endIdx);
			endIdx = extractOutputRedir(str, cmdVector, endIdx);
			// System.out.println(cmdVector.toString());
		} catch (ShellException e) {
			result = false;
		}
		if (str.substring(endIdx).trim().isEmpty()) {
			result = true;
		} else {
			result = false;
		}
		if (!result) {
			this.app = cmdVector.get(0);
			error = true;
			if (("").equals(errorMsg)) {
				errorMsg = ShellImplemtation.EXP_SYNTAX;
			}
			throw new ShellException(errorMsg);
		}

		String[] cmdTokensArray = cmdVector
				.toArray(new String[cmdVector.size()]);
		this.app = cmdTokensArray[0];
		int nTokens = cmdTokensArray.length;

		// process inputRedir and/or outputRedir
		if (nTokens >= 3) { // last 2 for inputRedir & >outputRedir
			this.inputStreamS = cmdTokensArray[nTokens - 2].trim();
			this.outputStreamS = cmdTokensArray[nTokens - 1].trim();
			if (!("").equals(inputStreamS)
					&& inputStreamS.equals(outputStreamS)) {
				error = true;
				errorMsg = ShellImplemtation.EXP_SAME_REDIR;
				throw new ShellException(errorMsg);
			}
			this.argsArray = Arrays.copyOfRange(cmdTokensArray, 1,
					cmdTokensArray.length - 2);
		} else {
			this.argsArray = new String[0];
		}
	}

	/**
	 * Parses the sub-command's arguments to the call command and splits it into
	 * its different components, namely the application name and the arguments
	 * (if any), based on rules: Unquoted: any char except for whitespace
	 * characters, quotes, newlines, semicolons �;�, �|�, �<� and �>�. Double
	 * quoted: any char except \n, ", ` Single quoted: any char except \n, '
	 * Back quotes in Double Quote for command substitution: DQ rules for
	 * outside BQ + `anything but \n` in BQ.
	 * 
	 * @param str
	 *            String of command to split.
	 * @param cmdVector
	 *            Vector of String to store the split arguments into.
	 * 
	 * @return endIdx Index of string where the parsing of arguments stopped
	 *         (due to no more matches).
	 * 
	 * @throws ShellException
	 *             If an error in the syntax of the command is detected while
	 *             parsing.
	 */
	int extractArgs(String str, Vector<String> cmdVector) throws ShellException {
		String patternDash = "[\\s]+(-[A-Za-z]*)[\\s]";
		String patternUQ = "[\\s]+([^\\s\"'`\\n;|<>]*)[\\s]";
		String patternDQ = "[\\s]+\"([^\\n\"`]*)\"[\\s]";
		String patternSQ = "[\\s]+\'([^\\n']*)\'[\\s]";
		String patternBQ = "[\\s]+(`[^\\n`]*`)[\\s]";
		String patternBQinDQ = "[\\s]+\"([^\\n\"`]*`[^\\n]*`[^\\n\"`]*)\"[\\s]";
		String[] patterns = { patternDash, patternUQ, patternDQ, patternSQ,
				patternBQ, patternBQinDQ };
		String substring;
		int newStartIdx = 0, smallestStartIdx, smallestPattIdx, newEndIdx = 0;
		do {
			substring = str.substring(newEndIdx);
			smallestStartIdx = -1;
			smallestPattIdx = -1;
			if (substring.trim().startsWith("<")
					|| substring.trim().startsWith(">")) {
				break;
			}
			for (int i = 0; i < patterns.length; i++) {
				Pattern pattern = Pattern.compile(patterns[i]);
				Matcher matcher = pattern.matcher(substring);
				if (matcher.find()
						&& (matcher.start() < smallestStartIdx || smallestStartIdx == -1)) {
					smallestPattIdx = i;
					smallestStartIdx = matcher.start();
				}
			}
			if (smallestPattIdx != -1) { // if a pattern is found
				Pattern pattern = Pattern.compile(patterns[smallestPattIdx]);
				Matcher matcher = pattern.matcher(str.substring(newEndIdx));
				if (matcher.find()) {
					String matchedStr = matcher.group(1);
					newStartIdx = newEndIdx + matcher.start();
					if (newStartIdx != newEndIdx) {
						error = true;
						errorMsg = ShellImplemtation.EXP_SYNTAX;
						throw new ShellException(errorMsg);
					} // check if there's any invalid token not detected
					cmdVector.add(matchedStr);
					newEndIdx = newEndIdx + matcher.end() - 1;
				}
			}
		} while (smallestPattIdx != -1);
		return newEndIdx;
	}

	/**
	 * Extraction of input redirection from cmdLine with two slots at end of
	 * cmdVector reserved for <inputredir and >outredir. For valid inputs,
	 * assumption that input redir and output redir are always at the end of the
	 * command and input stream first the output stream if both are in the args
	 * 
	 * @param str
	 *            String of command to split.
	 * @param cmdVector
	 *            Vector of String to store the found result into.
	 * @param endIdx
	 *            Index of str to start parsing from.
	 * 
	 * @return endIdx Index of string where the parsing of arguments stopped
	 *         (due to no more matches).
	 * 
	 * @throws ShellException
	 *             When more than one input redirection string is found, or when
	 *             invalid syntax is encountered..
	 */
	int extractInputRedir(String str, Vector<String> cmdVector, int endIdx)
			throws ShellException {
		String substring = str.substring(endIdx);
		String strTrm = substring.trim();
		if (strTrm.startsWith(">") || strTrm.isEmpty()) {
			return endIdx;
		}
		if (!strTrm.startsWith("<")) {
			throw new ShellException(EXP_SYNTAX);
		}

		int newEndIdx = endIdx;
		Pattern inputRedirP = Pattern
				.compile("[\\s]+<[\\s]+(([^\\n\"`'<>]*))[\\s]");
		Matcher inputRedirM;
		String inputRedirS = "";
		int cmdVectorIndex = cmdVector.size() - 2;

		while (!substring.trim().isEmpty()) {
			inputRedirM = inputRedirP.matcher(substring);
			inputRedirS = "";
			if (inputRedirM.find()) {
				if (!cmdVector.get(cmdVectorIndex).isEmpty()) {
					throw new ShellException(EXP_SYNTAX);
				}
				inputRedirS = inputRedirM.group(1);
				cmdVector.set(cmdVectorIndex, inputRedirS);
				newEndIdx = newEndIdx + inputRedirM.end() - 1;
			} else {
				break;
			}
			substring = str.substring(newEndIdx);
		}
		return newEndIdx;
	}

	/**
	 * Extraction of output redirection from cmdLine with two slots at end of
	 * cmdVector reserved for <inputredir and >outredir. For valid inputs,
	 * assumption that input redir and output redir are always at the end of the
	 * command and input stream first the output stream if both are in the args.
	 * 
	 * @param str
	 *            String of command to split.
	 * @param cmdVector
	 *            Vector of String to store the found result into.
	 * @param endIdx
	 *            Index of str to start parsing from.
	 * 
	 * @return endIdx Index of string where the parsing of arguments stopped
	 *         (due to no more matches).
	 * 
	 * @throws ShellException
	 *             When more than one input redirection string is found, or when
	 *             invalid syntax is encountered..
	 */
	int extractOutputRedir(String str, Vector<String> cmdVector, int endIdx)
			throws ShellException {
		String substring = str.substring(endIdx);
		String strTrm = substring.trim();
		if (strTrm.isEmpty()) {
			return endIdx;
		}
		if (!strTrm.startsWith(">")) {
			throw new ShellException(EXP_SYNTAX);
		}

		int newEndIdx = endIdx;
		Pattern inputRedirP = Pattern
				.compile("[\\s]+>[\\s]+(([^\\n\"`'<>]*))[\\s]");
		Matcher inputRedirM;
		String inputRedirS = "";
		int cmdVectorIdx = cmdVector.size() - 1;
		while (!substring.trim().isEmpty()) {

			inputRedirM = inputRedirP.matcher(substring);
			inputRedirS = "";
			if (inputRedirM.find()) {
				if (!cmdVector.get(cmdVectorIdx).isEmpty()) {
					throw new ShellException(EXP_SYNTAX);
				}
				inputRedirS = inputRedirM.group(1);
				cmdVector.set(cmdVectorIdx, inputRedirS);
				newEndIdx = newEndIdx + inputRedirM.end() - 1;
			} else {
				break;
			}
			substring = str.substring(newEndIdx);
		}
		return newEndIdx;
	}
	
	private String[] expandGlob() {		//assume * is not in any quotation
		List<String> expandedArgs = new ArrayList<String>();
		for (int i = 0; i < argsArray.length; i++) {
			String arg = argsArray[i].trim();
			if (arg.indexOf("*") != -1) {
				try {
					expandedArgs.addAll(expandPath(arg));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				expandedArgs.add(arg);
			}
		}
		return expandedArgs.toArray(new String[expandedArgs.size()]);
	}
	
	/**
	 * function to expand a path to all of its existed files and directories
	 * @param curPath
	 * @return
	 * @throws IOException 
	 */
	//rule: Collect all the paths to existing files and directories such that these paths can be
	// obtained by replacing all the unquoted asterisk symbols in ARG by some (possibly
	//	empty) sequences of non-slash characters
	//If no such path -> return List with only curPath
	//else replace curPath with new list of expanded paths
	
	//we can assume first asterisk is after last slash (ie only expand current directory)
	private List<String> expandPath(String curPath) throws IOException {
		List<String> expanded = new ArrayList<String>();
		expanded.add(curPath);
		//TODO : check whether / or File.separator
		int lastFileSepIndex = curPath.lastIndexOf("/");
//		System.out.println(lastFileSepIndex);
		int firstAsteriskIndex = curPath.indexOf("*");
		
		if (firstAsteriskIndex < lastFileSepIndex && lastFileSepIndex != -1) {
			return expanded;		//non applicable to glob
		}
		String directory = "";
		String glob = "";
		if (lastFileSepIndex == -1) {
			glob = curPath;
			directory = Environment.currentDirectory;
		} else {
			directory = curPath.substring(0, lastFileSepIndex);

			File f = new File(directory);
			if (!f.isAbsolute()) {
				directory = Environment.currentDirectory + File.separator +  directory;
			//	System.out.println(directory);
				File newPath = new File(directory);
				if (newPath.exists()) {
					directory = newPath.getCanonicalPath();
				} else {
					return expanded; //directory does not exist
				}
			}
			glob = curPath.substring(lastFileSepIndex+1);
		}
		File dir = new File(directory);
		String [] files = dir.list();
		String regex = ("\\Q" + glob + "\\E").replace("*", "\\E.*\\Q");
		for (int i = 0; i < files.length; i++) {
			if (files[i].matches(regex)) {
				expanded.add(directory + File.separator + files[i]);
			}
		}
		if (expanded.size() > 1) {
			expanded.remove(0);	//remove original curPath
		}

		return expanded;
	}
	
	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		String s = "";
		s += "App: " + app + System.lineSeparator();
		s += "CmdLine: " + cmdline + System.lineSeparator();
		s += "Input: " + inputStreamS+ System.lineSeparator();
		s += "Output: " + outputStreamS + System.lineSeparator();
		s += "Args: ";
		for (int i = 0; i < argsArray.length; i++) {
			s+= argsArray[i] + " ";
		}
		s += System.lineSeparator();
		return s;
	}

}
