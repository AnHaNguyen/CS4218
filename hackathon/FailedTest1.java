package hackathon;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class FailedTest1 {

	ShellImpl shellImpl;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}
	
	/**
	 * Cat: No exception when file does not exist
	 * Ref: Bug Report Number 1
	 */
	@Test(expected = Exception.class)
	public void testCatWithNonExistingFile() throws AbstractApplicationException, ShellException {
		String input = "cat test-data/sample.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	    shellImpl.parseAndEvaluate(input, output);
	}
	
	/**
	 * Cal: No exception when there are more than 3 arguments
	 * Ref: Bug Report Number 2
	 */
	@Test(expected = Exception.class)
	public void testCalWithMoreThan3Arguments() throws AbstractApplicationException, ShellException {
		ShellImpl shellImpl = new ShellImpl();
		String input = "cal -m 1 20";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, output);
	}
	
	/**
	 * Cal: No exception when month is not valid (<= 0 or > 12)
	 * Ref: Bug Report Number 3
	 */
	@Test(expected = Exception.class)
	public void testCalWithInvalidMonth() throws AbstractApplicationException, ShellException {
		ShellImpl shellImpl = new ShellImpl();
		String input = "cal 14 2017";
		ByteArrayOutputStream outputeck 	 * Command Substitution: Cat doest not work with echo
	 * Ref: Bug Report Number 4
	 */
	@Test
	public void testCommandSubstitutionCatAndEcho() throws AbstractApplicationException, ShellException {
		ShellImpl shellImpl = new ShellImpl();
		String expectedOutput = "Hello World" + System.lineSeparator()
			+ "Ali Hello World Ali" + System.lineSeparator()
			+ "CS4218 Software Testing" + System.lineSeparator();
		
		String input = "cat `echo \"Source Code/Tests/pipeFiles/pipeTest.txt\"`";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		shellImpl.parseAndEvaluate(input, output);
		assertEquals(expectedOutput, output.toString());
	}
}
