package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class SedApplicationTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testReplaceFirstSubStringInFile() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!";
		String replacement = "s/Apple/Microsoft";
		String filePath = "testSed.txt";
		String[] args = new String[] { replacement, filePath};
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);;
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testReplaceAllSubstringsInFile() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Microsoft is love!";
		String replacement = "s/Apple/Microsoft/g";
		String filePath = "testSed.txt";
		String[] args = new String[] { replacement, filePath};
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);;
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdin() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!";
		String replacement = "s/Apple/Microsoft";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream("Apple beetroot carrot! Apple is love!".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);;
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testReplaceAllSubStringInStdin() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Microsoft is love!";
		String replacement = "s/Apple/Microsoft/g";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream("Apple beetroot carrot! Apple is love!".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);;
		assertEquals(expectedOutput, stdout.toString());
	}
}
