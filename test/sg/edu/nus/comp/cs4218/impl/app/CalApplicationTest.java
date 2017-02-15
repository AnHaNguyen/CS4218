package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalApplicationTest {

	@Test
	public void testPrintCal() {
		assert(true);
	}
	
	@Test
	public void testPrintCalMondayFirst() {
		assert(true);
	}
	
	@Test
	public void testPrintCalForMonthYear() {
		String expectedOuput = "   February, 2017   \n" +
							   "Su Mo Tu We Th Fr Sa \n" +
							   "         1  2  3  4  \n" +
							   "5  6  7  8  9  10 11 \n" +
							   "12 13 14 15 16 17 18 \n" +
							   "19 20 21 22 23 24 25 \n" +
							   "26 27 28             \n" +
							   "                     \n";
		
		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForMonthYear("02 2017");
		assertEquals(output, expectedOuput);
	}
	
	@Test
	public void testPrintCalForMonthYearMondayFirst() {
		String expectedOuput = "   February, 2017   \n" +
				   			   "Mo Tu We Th Fr Sa Su \n" +
				   			   "      1  2  3  4  5  \n" +
				   			   "6  7  8  9  10 11 12 \n" +
				   			   "13 14 15 16 17 18 19 \n" +
				   			   "20 21 22 23 24 25 26 \n" +
				   			   "27 28                \n" +
				   			   "                     \n";

		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForMonthYearMondayFirst("-m 02 2017");
		assertEquals(output, expectedOuput);
	}
	
	@Test
	public void testPrintCalForYear() {
		assert(true);
	}
	
	@Test
	public void testPrintCalForYearMondayFirst() {
		assert(true);
	}
}

