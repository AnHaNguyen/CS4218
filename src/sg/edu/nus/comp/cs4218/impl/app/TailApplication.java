package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplication implements Application {
	private int totalReadLine;
	private InputStream is;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (args == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				throw new TailException("Null Pointer Exception");
			}
		}
		parseArgument(args, stdin);
		try {
			printTailToStdout(is, totalReadLine, stdout);
		} catch (IOException e) {
			throw new TailException("Error reading input stream");
		}
	}
	
	private void parseArgument(String[] args, InputStream stdin) throws TailException {
		totalReadLine = 10;
		int i = 0;
		while (i < args.length - 1) {	
			if (args[i].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException nfe) {
					throw new TailException("An integer must follow -n");
				}
				i += 2;
			} else {
				break;
			}
		}
		if (totalReadLine <= 0) {
			throw new TailException("Invalid number of lines to be read");	
		}
		if (i == args.length - 1) {
			try{
				is = new BufferedInputStream(new FileInputStream(args[args.length - 1]));
			} catch (FileNotFoundException e) {
				throw new TailException("File Not Found");
			}
		} else if (i == args.length) {
			if (stdin == null) {
				throw new TailException("Null Stdin");
			}
			is = stdin;
		} else {
			throw new TailException("Incorrect syntax of head argument");
		}
	}
	
	private void printTailToStdout(InputStream is, int totalLine, OutputStream os) throws IOException{
		Vector<String> lineList = new Vector<String>();
		String str;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		if (is != null) {                            
        	while ((str = reader.readLine()) != null) {    
        		lineList.add(str);
            }                
        }
		int startLine = totalLine >= lineList.size() ? 0 : lineList.size() - totalLine;
		for (int i = startLine; i < lineList.size() ; i++) {
			os.write(lineList.get(i).getBytes());
			os.write(System.lineSeparator().getBytes());
		}
	}

}
