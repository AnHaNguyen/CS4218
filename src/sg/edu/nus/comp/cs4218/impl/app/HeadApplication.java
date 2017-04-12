package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplication implements Application{
	private int totalReadLine;
	private InputStream is;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null || stdout == null) {
			throw new HeadException("Null Pointer Exception");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				throw new HeadException("Null Pointer Exception");
			}
		}

		parseArgument(args, stdin);
		try {
			printHeadToStdout(is, totalReadLine, stdout);
		} catch (IOException e) {
			throw new HeadException("Error reading input stream");
		}
	}
	
	private void parseArgument(String[] args, InputStream stdin) throws HeadException {
		totalReadLine = 10;
		int i = 0;
		while (i < args.length - 1) {	
			if (args[i].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException nfe) {
					throw new HeadException("An integer must follow -n");
				}
				i += 2;
			} else {
				break;
			}
		}
		if (totalReadLine <= 0) {
			throw new HeadException("Invalid number of lines to be read");	
		}
		if (i == args.length - 1) {
			try{
				is = new BufferedInputStream(new FileInputStream(args[args.length - 1]));
			} catch (FileNotFoundException e) {
				throw new HeadException("File Not Found");
			}
		} else if (i == args.length) {
			if (stdin == null) {
				throw new HeadException("Null Stdin");
			}
			is = stdin;
		} else {
			throw new HeadException("Incorrect syntax of head argument");
		}
	}
	
	private void printHeadToStdout(InputStream inputStream, int totalLine, OutputStream outputStream) throws IOException{
		int total = totalLine;
		while (total > 0) {
			int nextByte = inputStream.read();
			if (nextByte == -1) {
				break;
			}

			if (nextByte == (byte)'\n') {
				total--;
			}
			outputStream.write(nextByte);;
		}
	}
}
