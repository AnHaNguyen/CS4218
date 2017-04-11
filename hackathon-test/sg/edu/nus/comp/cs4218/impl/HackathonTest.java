package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class HackathonTest {
	static ShellImplementation shellImpl;
	static WcApplication wcApp;
	static ByteArrayOutputStream output;
	private static final String NEW_LINE = System.lineSeparator();
	private File createAndWriteFile(String fileName, String content) {
		try{
			File input = new File(Environment.getCurrentDirectory() 
					+ File.separator + fileName);
		
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input)));
			writer.write(content);
			writer.close();
			
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		return null;
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Environment.setCurrentDirectory(System.getProperty("user.dir")); 
	}
	
	@Before
	public void setUp() {
		shellImpl = new ShellImplementation();
		wcApp = new WcApplication();
	}
	
	@Test
	public void testBugPipeTwoCommandsWithoutSpace() throws AbstractApplicationException, ShellException{
		String input = "echo test| echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test1" + NEW_LINE;
		shellImpl.parseAndEvaluate(input, output);
		String actual = output.toString();
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testCompositeOrder() throws AbstractApplicationException {
		String path = "test-data/sample.txt";
		String args[] = {"-lwm", path};
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		wcApp.run(args, null, stdout);
		long byteCount = (new File(path)).length();
		String expectedResults = "2 14 " + byteCount +" " + path + System.lineSeparator();
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testCatPipeWc() throws ShellException, AbstractApplicationException {
		String path = "test-data/sample.txt";
		String cmdline = "cat " + path + " | wc";
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(cmdline, stdout);
		long byteCount = (new File(path)).length();
		String expectedResults = byteCount + " 14 2"+ System.lineSeparator();
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testHeadWithPathAndMultipleLineNumber() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 4; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 4; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		String expected = "n0" + System.lineSeparator() + "n1" + System.lineSeparator();
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[]{"-n","4","-n","2","input.txt"}, null, stdout);
			assertEquals(expected, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	@Test
	public void testTailWithPathAndMultipleLineNumber() {
		TailApplication tailApp = new TailApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 6; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		String expected = "n8" + System.lineSeparator() + "n9" + System.lineSeparator();
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","-n","2","input.txt"}, null, stdout);
			assertEquals(expected, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
}
