package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class CdApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (args.length != 1) {
			throw new IllegalArgumentException("Application requires 1 argument");
		}
		Path currentDir = Paths.get(Environment.currentDirectory);
		Path newDir = currentDir.resolve(args[0]);
		if (Files.isDirectory(newDir)) {
			System.setProperty("user.dir", newDir.toString());
			Environment.currentDirectory = newDir.toString();	
		} else {
			throw new IllegalArgumentException("This is not a directory");
		}
	}
}
