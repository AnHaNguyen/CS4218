package sg.edu.nus.comp.cs4218;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;
import sg.edu.nus.comp.cs4218.impl.cmd.SeqCommand;

/**
 * Created by thientran on 2/7/17.
 */
public class Utility {
	
	/**
	 * function to create Command from the respective String
	 * @param command
	 * @return command as type Command
	 */
	public static Command getCommandFromString(String command) {
		command = command.trim();
		if (command.indexOf(";") != -1) {
			return new SeqCommand(command);
		}
		
		return new CallCommand(command);
	}
}
