package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CommandSubstitutionTest {

	@Test
	public void testPipeTwoCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" 
				+ System.lineSeparator() + "Apple is life!";
		String args = "cat test-data/testPipe.txt | grep “Apple”";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.pipeTwoCommands(args);

		assertEquals(expectedOutput, output);
	}

	@Test
	public void testPipeMultipleCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" ;
		String args = "cat test-data/testPipe.txt | grep “Apple” | grep carrot";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.pipeMultipleCommands(args);

		assertEquals(expectedOutput, output);
	}
	
//	@Test
//	public void testPipeWithException() throws AbstractApplicationException, ShellException {
//		String expectedOutput = "Exception???" ;
//		String args = "cat test-data/testPipe1.txt | grep “Apple” | grep carrot";
//		ShellImplementation shell = new ShellImplementation();
//		String output = shell.pipeWithException(args);
//
//		assertEquals(expectedOutput, output);
//	}
	
	@Test
	public void testPerformCommandSubstitution() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" 
				+ System.lineSeparator() + "Apple is life!";
		String args = "grep “Apple” `cat test-data/testPipe.txt`";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.performCommandSubstitution(args);

		assertEquals(expectedOutput, output);
	}
	
//	@Test
//	public void testPerformCommandSubstitutionWithException() throws AbstractApplicationException, ShellException {
//		String expectedOutput = "Exception???" ;
//		String args = "cat test-data/testPipe1.txt | grep “Apple” | grep carrot";
//		ShellImplementation shell = new ShellImplementation();
//		String output = shell.performCommandSubstitutionWithException(args);
//
//		assertEquals(expectedOutput, output);
//	}
}
