package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;


import java.io.ByteArrayOutputStream;
import java.io.File;


import org.junit.Test;


import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;


public class GrepWithWcCommandTest {
	@Test
	public void testGrepWithWcAndGlobbing()
			throws AbstractApplicationException, ShellException {
		String path = System.getProperty("user.dir");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("wc test-data/SubstituteCommandTestFiles/* | grep \"GrepWithSub\"", outStream);
		String expected = "138 25 6 " + path + File.separator + "test-data" 
				+ File.separator + "SubstituteCommandTestFiles"
						+ File.separator + "GrepWithSubCommand.txt" + System.lineSeparator() 
						+ "78 14 2 "+ path + File.separator + "test-data" 
								+ File.separator + "SubstituteCommandTestFiles"
								+ File.separator +"GrepWithSubCommand2.txt"
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	@Test(expected = WcException.class)
	public void testGrepWithWcFailing() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("wc | grep \"GrepWithSub\"", outStream);
	}
}
