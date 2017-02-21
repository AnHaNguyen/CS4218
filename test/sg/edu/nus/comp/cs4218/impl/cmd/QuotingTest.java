package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Test;

public class QuotingTest {

	@Test
	public void testCallCommandWithDoubleQuoting() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!"+System.lineSeparator();
		String cmdLine = "echo \"Apple beetroot carrot!\"";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}
	
	@Test
	public void testCallCommandWithSingleQuoting() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!"+System.lineSeparator();
		String cmdLine = "echo 'Apple beetroot carrot!'";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}
	
	@Test
	public void testCallCommandWithBackQuoting() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!"+System.lineSeparator();
		String cmdLine = "echo Apple beetroot `echo carrot`!";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}
	
	@Test
	public void testCallCommandWithAllQuoting1() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!"+System.lineSeparator();
		String cmdLine = "echo Apple `echo \" \"` beetroot `echo carrot`!";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}
	
	@Test
	public void testCallCommandWithAllQuoting2() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "Travel time Singapore -> Paris is 13h and 15`"+System.lineSeparator();
		String cmdLine = "echo 'Travel time Singapore -> Paris is 13h and 15`'";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}
	
	@Test
	public void testCallCommandWithAllQuoting3() throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String expectedOutput = "echo Apple beetroot carrot!"+System.lineSeparator();
		String cmdLine = "echo \"echo Apple beetroot carrot!\"";
		CallCommand cc = new CallCommand(cmdLine);
		
		cc.evaluate(null, outputStream);
		String output = outputStream.toString();
		assertEquals(expectedOutput, output );
	}

}
