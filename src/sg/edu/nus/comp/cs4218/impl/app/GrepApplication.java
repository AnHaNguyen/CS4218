package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;

public class GrepApplication implements Grep{
	private final String NEW_LINE = System.lineSeparator();
	private InputStream is;
	private String file;
	private String[] files;
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null || stdout == null) {
			throw new GrepException("Null Pointer Exception");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				throw new GrepException("Null Pointer Exception");
			}
			if (args[i] == "") {
				throw new GrepException("Empty argument");
			}
		}
		
		switch (args.length){
		case 0:
			throw new GrepException("Invalid arguments");
		case 1:
			if (stdin == null) {
				throw new GrepException("Null Pointer Exception");
			}
			is = stdin;
			try {
				if (isValidRegex(args[0])) {
					stdout.write(grepFromStdin(args[0]).getBytes());
				} else {
					grepInvalidPatternInStdin(args[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new GrepException("Cannot write to stdout");
			}
			break;
		case 2:
			file = args[1];
			try {
				if (isValidRegex(args[0])) {
					stdout.write(grepFromOneFile(args[0]).getBytes());
				} else {
					grepInvalidPatternInFile(args[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new GrepException("Cannot write to stdout");
			}
			break;
		default:
			files = new String[args.length -1];
			files = Arrays.copyOfRange(args, 1, args.length);
			try {
				if (isValidRegex(args[0])) {
					stdout.write(grepFromMultipleFiles(args[0]).getBytes());
				} else {
					grepInvalidPatternInFile(args[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new GrepException("Cannot write to stdout");
			}
			break;
		}
	}
	
	private boolean isValidRegex(String pattern) {
		try {
			Pattern.compile(pattern);
		} catch (PatternSyntaxException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public String grepFromStdin(String args) {
		int lineNo = 1;
		String outString = "";
		String curLine = "";
		int intCount = -1;
		boolean isRead = false;
		do {
			try {
				intCount = is.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (intCount == (byte)'\n') {
				lineNo++;
				curLine = "";
				isRead = false;
			} else {
				curLine += (char) intCount;
			}
			if (curLine.contains(args) && !isRead) {
				outString += lineNo + NEW_LINE;
				isRead = true;
			}
		} while(intCount != -1);
		return outString;
	}

	@Override
	public String grepFromOneFile(String args) throws GrepException {
		int lineNo = 1;
		String outString = "";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if (line.contains(args)) {
		    		outString += lineNo + NEW_LINE;
		    	}
		    	lineNo++;
		    }
		} catch (IOException e) {
		//	e.printStackTrace();
			throw new GrepException("Unable to read file");
		}
		return outString;
	}

	@Override
	public String grepFromMultipleFiles(String args) throws GrepException {
		String outString = "";
		for (int i = 0; i < files.length; i++) {
			outString += files[i] + NEW_LINE;
			int lineNo = 1;
			try (BufferedReader br = new BufferedReader(new FileReader(files[i]))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	if (line.contains(args)) {
			    		outString += lineNo + NEW_LINE;
			    	}
			    	lineNo++;
			    }
			} catch (IOException e) {
			//	e.printStackTrace();
				throw new GrepException("Unable to read file");
			}
		}
		return outString;
	}

	@Override
	public String grepInvalidPatternInStdin(String args) throws GrepException {
		throw new GrepException("Invalid Pattern in stdin");
	}

	@Override
	public String grepInvalidPatternInFile(String args) throws GrepException {
		throw new GrepException("Invalid Pattern in file");
	}

}
