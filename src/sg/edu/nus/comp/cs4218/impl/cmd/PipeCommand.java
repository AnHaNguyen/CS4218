package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellThread;

public class PipeCommand implements Command {
	private List<CallCommand> command;
	private List<ShellThread> threads;
	private String commandLine;
	
	public PipeCommand(String commandLine) throws ShellException, AbstractApplicationException {
		
	}
	
	@Override
	public void evaluate(InputStream stdin, OutputStream sdtout) {
		
	}
	
	@Override
	public void terminate() {
		
	}
}
