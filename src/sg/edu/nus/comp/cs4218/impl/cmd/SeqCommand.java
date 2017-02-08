package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class SeqCommand implements Command {
	private List<String> commandList;
	private String inputCommand;
	
	public SeqCommand(String command) {
		this.inputCommand = command;
		this.commandList = new ArrayList<String>();
		String[] splits = splitCommand();
		for (int i = 0; i < splits.length; i++) {
			commandList.add(splits[i].trim());
		}
	}
	
	private String[] splitCommand() {
		return inputCommand.split(";");
	}
	
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
		for (int i = 0; i < commandList.size(); i++) {
			//Command command = ConvertcommandList.get(i)
			command.evaluate(stdin ,stdout);
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

}
