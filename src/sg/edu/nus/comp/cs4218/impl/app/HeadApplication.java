package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;

public class HeadApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		int totalReadLine;
		InputStream is;
		switch (args.length) {
		case 0: 
			totalReadLine = 10;
			is = stdin;
			break;
		case 1:
			if (args[0].equals("-n")){
				throw new IllegalArgumentException("Missing argument");
			}
			totalReadLine = 10;
			try{
				is = new BufferedInputStream(new FileInputStream(args[0]));
			} catch (FileNotFoundException e) {
				throw new CatException("File Not Found");
			}
			break;
		case 2:
			is = stdin;
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("An integer must follow -n");
				}
			} else {
				throw new IllegalArgumentException("Invalid arguments");
			}
			break;
		case 3:
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("An integer must follow -n");
				}
			} else {
				throw new IllegalArgumentException("Invalid arguments");
			}
	
			try{
				is = new BufferedInputStream(new FileInputStream(args[2]));
			} catch (FileNotFoundException e) {
				throw new CatException("File Not Found");
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid number of arguments");
		}
		try {
			printHeadToStdout(is, totalReadLine, stdout);
			is.close();
		} catch (IOException e) {
			throw new CatException("Error reading input stream");
		}
	}
	
	private void printHeadToStdout(InputStream is, int totalLine, OutputStream os) throws IOException{
		while (totalLine > 0) {
			int nextByte = is.read();
			if (nextByte == -1) {
				break;
			}

			if (nextByte == (byte)'\n') {
				totalLine--;
			}
			os.write(nextByte);;
		}
	}
}
