package hackathon;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestFailed2 {
	
	SortApplication sortApp;
	SortRead sortRead;
	OutputStream stdout;
	InputStream stdin;
	String toSort;
	static final String UNSORTED_FILE = "sort.txt";
	static final String SPECIAL_FILE = "specialChar.txt";
	static final String NUMBER_FILE = "number.txt";
	static final String CAPITAL_FILE = "capital.txt";
	static final String SIMPLE_FILE = "simple.txt";
	static final String SORT_SIMPLE_FILE = "sortSimple.txt";
	static final String	SORT_CAPITAL_FILE = "sortCapital.txt";
	static final String SORT_NUMBER_FILE = "sortNumbers.txt";
	static final String SORT_NUMBER_N_FILE = "sortNumbers-n.txt";
	static final String SORT_SPECIAL_FILE = "sortSpecialChars.txt";
	static final String SORT_SIMPLE_CAPITAL = "sortSimpleCapital.txt";	
	static final String SORT_SIMPLE_NUMBER = "sortSimpleNumbers.txt";
	static final String SORT_SIMPLE_NUMBER_N = "sortSimpleNumbers-n.txt";
	static final String SORT_SIMPLE_SPECIAL = "sortSimpleSpecialChars.txt";
	static final String SORT_CAPITAL_NUMBER = "sortCapitalNumbers.txt";
	static final String SORT_CAPITAL_NUMBER_N ="sortCapitalNumbers-n.txt";
	static final String SORT_CAPITAL_SPECIAL = "sortCapitalSpecialChars.txt";
	static final String SORT_NUMBER_SPECIAL = "sortNumbersSpecialChars.txt";
	static final String SORT_NUMBER_SPECIAL_N = "sortNumbersSpecialChars-n.txt";
	static final String SORT_SIMPLE_CAPITAL_NUMBER = "sortSimpleCapitalNumbers.txt";
	static final String SORT_SIMPLE_CAPITAL_NUMBER_N = "sortSimpleCapitalNumbers-n.txt";
	static final String SORT_SIMPLE_CAPITAL_SPECIAL = "sortSimpleCapitalSpecialChars.txt";
	static final String SORT_SIMPLE_NUMBER_SPECIAL = "sortSimpleNumbersSpecialChars.txt";
	static final String SORT_SIMPLE_NUMBER_SPECIAL_N = "sortSimpleNumbersSpecialChars-n.txt";
	static final String SORT_CAPITAL_NUMBER_SPECIAL = "sortCapitalNumbersSpecialChars.txt";
	static final String SORT_CAPITAL_NUMBER_SPECIAL_N = "sortCapitalNumbersSpecialChars-n.txt";
	static final String SORT_ALL_FILE = "sortAll.txt";
	static final String SORT_ALL_N_FILE = "sortAll-n.txt";
	
	static final String FILE_PATH = "Source Code"+File.separator+"tests" + File.separator + "sortFiles" + File.separator;
	
	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/*
	 * Set the "toSort" string data for which contains all the lines in "sort.txt"
	 * Each line is separated by a file separator
	 */
	@Before
	public void setup() throws SortException {
		sortApp = new SortApplication();
		sortRead = new SortRead();
		String sortFilename = FILE_PATH + UNSORTED_FILE;
		String data = sortRead.readFromFile(sortFilename);
		toSort = "" + System.lineSeparator() + data;
		stdout = new ByteArrayOutputStream();
	}
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
	}
	
	
	/* Sort: invalid option does not throw exception
	 * Ref: Bug report number 17
	 * The bug here is due to the fact that sortApp returns 
	 *normally sorted lines follow by a printout of "invalid file/option"
	 * It should throw an exception: "invalid option exception"
	 *since this violates the syntax for sort command sort[-n][FILE]
	 */
	@Test(expected = Exception.class)
	public void testInvalidOptionValidFile() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {"-invalidoption", strArgs};
		sortApp.run(args, null, stdout);
		sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);

	}
	
	
	/* Sort: invalid number of options does not throw exception
	 * Ref: Bug report number 18
	 * The bug here is due to the fact that sortApp returns 
	 *normally sorted lines follow by a printout of "invalid file/option"
	 * It should throw an exception: "invalid number of options"
	 *since this violates the syntax for sort command sort[-n][FILE]
	 */
	@Test(expected = Exception.class)
	public void testInvalidNumberOfOptionsValidFile() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {"-option1", strArgs, "-option2"};
		sortApp.run(args, null, stdout);
		sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);

	}
	
	
	/* Sort: sort numbers (-n option) in stdin returns nothing
	 * Ref: Bug report number 19
	 * The bug here is due to the fact that sortApp returns 
	 *nothing when args with -n option and stdin are provided
	 * It should return the sorted lines based on numerical order
	 *from the stdin
	 */
	@Test
	public void testSortNumbersStdin() throws AbstractApplicationException {
		
		String expectedOutput = "1"+System.lineSeparator()
		+"2"+System.lineSeparator()
		+"12";
		String toSort = "2"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"12";
		String[] args = new String[] { "-n" };

		SortApplication sortApp = new SortApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(toSort.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput + System.lineSeparator(), stdout.toString());
	
	}
	
	
	/* Pipe and Command Substitution: no exception thrown 
	 *when a component is invalid
	 * Ref: Bug report number 20
	 * The bug here is due to the fact that shell
	 *does not throw exception when a component is supposed to
	 * Some of the commands where this happens:
	 * "echo `head invalid.txt` | grep \"usage\""
	 * "echo `tail invalid.txt` | grep \"usage\""
	 * "wc | grep \"GrepWithSub\""
	 * Those commands should cause shell to throw exceptions
	 */
	@Test(expected = Exception.class)
	public void testGrepWithHeadInvalidFile()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		shellImpl.parseAndEvaluate(
				"echo `head invalid.txt` | grep \"usage\"",
				outStream);
		System.lineSeparator();
	}
	
	@After
	public void tearDown() {
		sortApp = null;
	}
	
}
