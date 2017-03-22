package hackathon;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class FailedTest3 {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * Cal, Cd, Date, Grep, Head, Pwd, Sed, Sort, Tail, Wc apps
	 * do not throw exception for null args 
	 * Ref: Bug Report No 5
	 */
	@Test
	public void testNullArgsApp() throws Exception {	
		expectedEx.expect(CalException.class);
		CalApplication calApp = new CalApplication();
		calApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
		
		expectedEx.expect(CdException.class);
		CdApplication cdApp = new CdApplication();
		cdApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
		
		expectedEx.expect(DateException.class);
		DateApplication dateApp = new DateApplication();
		dateApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());

		expectedEx.expect(GrepException.class);
		GrepApplication grepApp = new GrepApplication();
		grepApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
		
		expectedEx.expect(HeadException.class);
		HeadApplication headApp = new HeadApplication();
		headApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
	
		expectedEx.expect(PwdException.class);
		PwdApplication pwdApp = new PwdApplication();
		pwdApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
		
		expectedEx.expect(SedException.class);
		SedApplication sedApp = new SedApplication();
		sedApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
	
		expectedEx.expect(SortException.class);
		SortApplication sortApp = new SortApplication();
		sortApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
		
		expectedEx.expect(TailException.class);
		TailApplication tailApp = new TailApplication();
		tailApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
	
		expectedEx.expect(WcException.class);
		WcApplication wcApp = new WcApplication();
		wcApp.run(null, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
	}
	
	/**
	 * Head, Tail: do not give exception for negative number of lines 
	 * Ref: Bug Report No 6
	 */	
	@Test
	public void testHeadTailWithNegativeLineNumberOption() throws Exception {
		expectedEx.expect(HeadException.class);
		Shell shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		shell.parseAndEvaluate("head -n -1 test-data/sample.txt", output);
		
		expectedEx.expect(TailException.class);
		shell = new ShellImpl();
		output = new ByteArrayOutputStream();
		shell.parseAndEvaluate("tail -n -1 test-data/sample.txt", output);
	}
	
	
	/**
	 * Cd does not give exception on multiple input directories
	 * Ref: Bug Report No 7
	 */
	@Test
	public void testCdWithMultipleInputArgs() throws Exception {
		expectedEx.expect(CdException.class);
		Shell shell = new ShellImpl();
		shell.parseAndEvaluate("cd ../ fakeDir", new ByteArrayOutputStream());
	}
	
	
	/** 
	 * Date: run correctly and also throw exception on more than 0 input args
	 * Ref: Bug Report No 8
	 */
	@Test
	public void testDateWithMoreThan0Args() throws Exception {
		//output the date correctly and throw exception
		expectedEx.expect(DateException.class);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Shell shell = new ShellImpl();
		shell.parseAndEvaluate("date 123", os);
	}
	
	/**
	 * Grep: 0 args does not return exception
	 * Ref: Bug Report No 9
	 */
	@Test
	public void testGrep0Args() throws Exception {
		expectedEx.expect(GrepException.class);
		Shell shell = new ShellImpl();
		shell.parseAndEvaluate("grep", new ByteArrayOutputStream());
	}


	/**
 	* Grep: Does not throw exception on invalid pattern
 	* Ref: Bug Report No 10
 	*/	
	@Test
	public void testGrepInvalidPattern() throws Exception {
		expectedEx.expect(GrepException.class);
		Shell shell = new ShellImpl();
		shell.parseAndEvaluate("grep * test-data/sample.txt", new ByteArrayOutputStream());
	}

	/**
	 * Grep: Does not throw exception for nonexistent file
	 * Ref: Bug Report No 11
	 */
	@Test
	public void testGrepNonExistentFile() throws Exception {
		expectedEx.expect(GrepException.class);
		Shell shell = new ShellImpl();
		shell.parseAndEvaluate("grep * test-data/sampl.txt", new ByteArrayOutputStream());
	}
	
	/**
	* Sed: return null for correct case using | as separator 
	* Stated in project description: "For example, 
	* “s/a/b/” and “s|a|b|” are the same replacement rules"
	* Ref: Bug Report No 12
	*/
	@Test
	public void testSedWithDifferentSeparator() throws Exception {
		Shell shell = new ShellImpl();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		shell.parseAndEvaluate("sed s|The|xyz| test-data/sample.txt", os);
		assertEquals("xyz power is over 9000 !\n"+
				"xyz quick brown fox jumps over the lazy dog\n", os.toString());	
	}

	/**
	 * Wc: throw exception when using option + stdin
	 * Ref: Bug Report No 13
	 */
	@Test
	public void testWcUsingOptionAndStdin() throws Exception {
		Shell shell = new ShellImpl();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		shell.parseAndEvaluate("wc -w < test-data/sample.txt", os);
		assertEquals("   15\n", os.toString());	
	}
	
	/**
	 * Wc: return nothing when using more than 1 options + stdin
	 * Ref: Bug Report No 14
	 */
	@Test
	public void testWcUsingMoreThan1OptionsAndStdin() throws Exception {
		Shell shell = new ShellImpl();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		shell.parseAndEvaluate("wc -w -l < test-data/sample.txt", os);
		assertEquals("   15   1\n", os.toString());	
	}
	

	/**
	 * Glob: for nonexistent path, does not keep ARG so grep asks for stdin instead of 
	 * throwing nonexistent file exception  
	 * Stated in project description: "If there are no such paths, 
	 * leave ARG without changes"
	 * Ref: Bug Report No 15
	 */
	@Test
	public void testGlobUsingNonexistentPath() throws Exception {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("File does not exist");	//this part is up to implementation
		Shell shell = new ShellImpl();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		shell.parseAndEvaluate("grep 9000 test-data/1*", os);	
	}
	
	/**
	 * Glob: Asterisk (*) in quotation is interpreted as globbing
	 * Stated in project description: "The symbol * (asterisk)
	 * in an unquoted part of an argument is interpreted as globbing"
	 * Ref: Bug Report No 16
	 */
	@Test
	public void testGlobAsteriskInQuotation() throws Exception {
		expectedEx.expect(GrepException.class);
		Shell shell = new ShellImpl();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		shell.parseAndEvaluate("grep 9000 \"test-data/*\"", os);	
	}

}
