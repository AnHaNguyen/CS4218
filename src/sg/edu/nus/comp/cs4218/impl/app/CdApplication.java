package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CdException;

public class CdApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (args.length != 1) {
			throw new CdException("Application requires 1 argument");
		}
		String curDir = Environment.currentDirectory;
		String newDir = curDir + File.separator + args[0];

		File pathToDir = new File(newDir);
		
		if (pathToDir.exists()) {
			try {
				String finalPath = pathToDir.getCanonicalPath();
				System.setProperty("user.dir", finalPath);
				Environment.currentDirectory = finalPath;	
			} catch (IOException e) {
				e.printStackTrace();
				throw new CdException("Cannot get canonical Path");
			}
		} else {
			throw new CdException("This is not a directory");
		}
	}
}
