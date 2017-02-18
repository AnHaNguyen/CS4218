package sg.edu.nus.comp.cs4218.impl.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateApplicationTest {

	@Test
	public void testPrintCurrentDate() {
		//[week day] [month] [day] [hh:mm:ss] [time zone][year].
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
				
		DateApplication dateApp = new DateApplication();
		String output = dateApp.printCurrentDate("date");
		Calendar cal = Calendar.getInstance();
		String expectedOutput = dateFormat.format(cal);
		
		assertEquals(expectedOutput, output);
	}

}
