package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.Parser;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken.TokenType;

public class SeqCommand implements Command {
	private List<String> commandList;
	private String inputCommand;
	
	public static void main(String[] args) throws AbstractApplicationException, ShellException {
		Scanner sc = new Scanner(System.in);
		while(true) {
			String cmd = sc.nextLine();
			SeqCommand sqc = new SeqCommand(cmd);
			sqc.evaluate(System.in, System.out);
		}
	}
	
	public SeqCommand(String command) {
		this.inputCommand = command;
		this.commandList = splitCommand();
	}
	
	private List<String> splitCommand() {
		String curCmd = "";
		List<String> cmds = new ArrayList<String>();
		List<AbstractToken> tokens = Parser.tokenize(inputCommand);
		for (AbstractToken token : tokens) {
			if (token.getType() == TokenType.SEMICOLON) {	//semicolon will split commands
				if (!curCmd.trim().equals("")) {
					cmds.add(curCmd.trim());
					curCmd = "";
				}
			} else {
				curCmd += " " + token.toString();
			}
		}
		if (!curCmd.trim().equals("")) {
			cmds.add(curCmd.trim());
		}
		return cmds;
	}
	
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
		for (int i = 0; i < commandList.size(); i++) {
			Command command = Utility.getCommandFromString(commandList.get(i));
			command.evaluate(stdin ,stdout);
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

}
