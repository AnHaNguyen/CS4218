package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.Constants;
import sg.edu.nus.comp.cs4218.Utility;

public class CalApplication implements Cal {
	static final String MONDAY_FIRST_FLAG = "-m";
	static final String ONE_SPACE = " ";
	static final String TWO_SPACE = "  ";
	static final String THREE_SPACE = "   ";
	static final int MONTHS_PER_YEAR = 12;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CalException {		
		boolean isMondayFirst = false;
		List<String> paramsList = new ArrayList<String>(Arrays.asList(args));
		
		if (args == null) {
			throw new CalException(Constants.CalMessage.INVALID_ARGS);
		}
	
		if (args.length > 3) {
			throw new CalException(Constants.CalMessage.INVALID_NUMBER_ARGUMENTS);
		}
		
		if (args.length > 0 && args[0].equals(MONDAY_FIRST_FLAG)) {
			isMondayFirst = true;
			paramsList.remove(0);
		}
		
		if (paramsList.size() == 0) {
			// print current month and year
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			printCalendarForMonthYear(month, year, isMondayFirst);
		} else if (paramsList.size() == 1) {
			// print all month of year
			try {
				int year = Integer.parseInt(paramsList.get(0));
				if (year < 0) {
					throw new CalException(Constants.CalMessage.INVALID_YEAR);
				}
				
				printCalendarForYear(year, isMondayFirst);
			} catch(Exception e) {
				throw new CalException(e.getMessage());
			}
		} else if (paramsList.size() == 2) {
			// print month and year from user
			try {
				int month = Integer.parseInt(paramsList.get(0));
				int year = Integer.parseInt(paramsList.get(1));
				
				if (year < 0) {
					throw new CalException(Constants.CalMessage.INVALID_YEAR);
				}
				
				if (month <= 0 || month > 12) {
					throw new CalException(Constants.CalMessage.INVALID_MONTH);
				}
				
				printCalendarForMonthYear(month - 1, year, isMondayFirst); // month is from 0 to 11
			} catch(Exception e) {
				throw new CalException(e.getMessage());
			}
		}
	}

	public void printCalendarForMonthYear(int month, int year, boolean mondayFirst) {
		int[][] monthArr = Utility.getCalendarArrayForMonth(month, year, mondayFirst);
		Utility.printCalTitle(month, year);
		System.out.println();
		
		Utility.printCalHeaders(mondayFirst);
		System.out.println();
		
		for (int i = 0; i < Constants.Common.CALENDAR_ROW_SIZE; i++) {
			for (int j = 0; j < Constants.Common.CALENDAR_COL_SIZE; j++) {
				if (monthArr[i][j] == -1) {
					System.out.print(THREE_SPACE);
				} else if (monthArr[i][j] < 10) {
					System.out.print(monthArr[i][j] + TWO_SPACE);
				} else {
					System.out.print(monthArr[i][j] + ONE_SPACE);
				}
			}
			System.out.println();
		}
	}
	
	public void printCalendarForYear(int year, boolean isMondayFirst) {
		List<int[][]> yearArray = new ArrayList<int[][]>();
		for (int month = 0; month < MONTHS_PER_YEAR; month++) {
			int[][] monthArr = Utility.getCalendarArrayForMonth(month, year, isMondayFirst);
			yearArray.add(monthArr);
		}

		for (int month = 0; month < MONTHS_PER_YEAR; month += Constants.Common.YEAR_COL_SIZE) {
			printMonthCalendarInRow(year, isMondayFirst, month, month + Constants.Common.YEAR_COL_SIZE - 1, yearArray);
			System.out.println();
		}
		
	}
	
	private void printMonthCalendarInRow(int year, boolean mondayFirst, int startMonth, int endMonth, List<int[][]> yearArray) {
		for (int month = startMonth; month <= endMonth; month++) {
			Utility.printCalTitle(month, year);
			Utility.printMonthSpace();
			System.out.print("  ");
		}
		System.out.println();
		
		for (int month = startMonth; month <= endMonth; month++) {
			Utility.printCalHeaders(mondayFirst);
			Utility.printMonthSpace();
			System.out.print(" ");
		}
		System.out.println();
		
		for (int i = 0; i < Constants.Common.CALENDAR_ROW_SIZE; i++) {
			for (int month = startMonth; month <= endMonth; month++) {
				int[][] monthArr = yearArray.get(month);
				for (int j = 0; j < Constants.Common.CALENDAR_COL_SIZE; j++) {
					if (monthArr[i][j] == -1) {
						System.out.print(THREE_SPACE);
					} else if (monthArr[i][j] < 10) {
						System.out.print(monthArr[i][j] + TWO_SPACE);
					} else {
						System.out.print(monthArr[i][j] + ONE_SPACE);
					}
				}
				Utility.printMonthSpace();
			}
			System.out.println();
		}		
	}
	
	@Override
	public String printCal(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalWithMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForMonthYear(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForYear(String args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String printCalForMonthYearMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForYearMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

}
