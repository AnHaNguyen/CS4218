package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class GrepApplication implements Grep{
	private final String NEW_LINE = System.getProperty("line.separator");
	private InputStream is;
	private String file;
	private String[] files;
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		switch (args.length){
		case 0:
			throw new IllegalArgumentException("Invalid arguments");
		case 1:
			is = stdin;
			try {
				stdout.write(grepFromStdin(args[0]).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			file = args[1];
			try {
				stdout.write(grepFromOneFile(args[0]).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			files = new String[args.length -1];
			files = Arrays.copyOfRange(args, 1, args.length);
			try {
				stdout.write(grepFromMultipleFiles(args[0]).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
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
	public String grepFromOneFile(String args) {
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
			e.printStackTrace();
		}
		return outString;
	}

	@Override
	public String grepFromMultipleFiles(String args) {
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
				e.printStackTrace();
			}
		}
		return outString;
	}

	@Override
	public String grepInvalidPatternInStdin(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grepInvalidPatternInFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

}
