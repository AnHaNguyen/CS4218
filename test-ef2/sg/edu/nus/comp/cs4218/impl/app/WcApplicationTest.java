package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class WcApplicationTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testPrintCharacterCountInFile() throws AbstractApplicationException {
		String expectedOutput = "37";
		String option = "-m";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String[] args = new String[] { option, filePath };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintWordCountInFile() throws AbstractApplicationException {
		String expectedOutput = "6";
		String option = "-w";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String[] args = new String[] { filePath };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintNewlineCountInFile() throws AbstractApplicationException {
		String expectedOutput = "1";
		String option = "-l";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String[] args = new String[] { filePath };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintAllCountsInFile() throws AbstractApplicationException {
		String expectedOutput = "44";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String[] args = new String[] { filePath };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintCharacterCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "37";
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = "-m";
		String[] args = new String[] { option };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintWordCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "6";
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = "-w";
		String[] args = new String[] { option };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintNewlineCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "1";
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = "-l";
		String[] args = new String[] { option };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintAllCountsInStdin() throws AbstractApplicationException {
		String expectedOutput = "44";
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = null;
		String[] args = new String[] { option };

		WcApplication WcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		WcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
}
