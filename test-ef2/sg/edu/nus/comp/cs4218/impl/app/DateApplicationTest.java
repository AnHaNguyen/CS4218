package sg.edu.nus.comp.cs4218.impl.app;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class DateApplicationTest {
	
	@Test
	public void testRun() {
		//[week day] [month] [day] [hh:mm:ss] [time zone][year].
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
				
		DateApplicationMock dateApp = new DateApplicationMock();
		String[] args = new String[0];
		OutputStream stdout = new ByteArrayOutputStream();
		InputStream stdin = new ByteArrayInputStream("".getBytes());
		try {
			dateApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		String expectedOutput = dateFormat.format(cal);
		
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintCurrentDate() {
		//[week day] [month] [day] [hh:mm:ss] [time zone][year].
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
				
		DateApplicationMock dateApp = new DateApplicationMock();
		String output = dateApp.printCurrentDate("date");
		Calendar cal = Calendar.getInstance();
		String expectedOutput = dateFormat.format(cal);
		
		assertEquals(expectedOutput, output);
	}

}
