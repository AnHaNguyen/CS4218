package sg.edu.nus.comp.cs4218;

import java.util.Calendar;

import sg.edu.nus.comp.cs4218.Constants;

/**
 * Created by thientran on 2/7/17.
 */
public class Utility {
	
	public static int[][] initArray(int rowSize, int colSize, int defaultValue) {
		int[][] result = new int[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				result[i][j] = defaultValue;
			}
		}
		
		return result;
	}
	
	public static int[][] getCalendarArrayForMonth(int month, int year, boolean mondayFirst) {
		int[][] calendarArr = initArray(Constants.Common.CALENDAR_ROW_SIZE, Constants.Common.CALENDAR_COL_SIZE, -1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (mondayFirst) {
			dayIndex = (dayIndex + 7 - 1) % 7;
		}
		
		for (int i = 1; i <= maxDay; i++) {
			int rowIndex = dayIndex / Constants.Common.CALENDAR_COL_SIZE;
			int colIndex = dayIndex % Constants.Common.CALENDAR_COL_SIZE;
			calendarArr[rowIndex][colIndex] = i;
			dayIndex++;
		}
		
		return calendarArr;
	}
	
	public static void printCalTitle(int month, int year) {
		// print month and year
		int calendarWidth = 20;
		String calendarTitle = Constants.Common.MONTH_TO_TEXT[month] + ", " + year;
		int space = (calendarWidth - calendarTitle.length()) / 2;
		for (int i = 0; i < space; i++) {
			System.out.print(" ");
		}
		
		System.out.print(calendarTitle);
		for (int i = 0; i < space; i++) {
			System.out.print(" ");
		}		
	}
	
	public static void printCalHeaders(boolean mondayFirst) {
		// print headers
		if (mondayFirst) {
			System.out.print("Mo Tu We Th Fr Sa Su");
		} else {
			System.out.print("Su Mo Tu We Th Fr Sa");
		}		
	}
	
	public static void printMonthSpace() {
		System.out.print(Constants.Common.SPACE_BETWEEN_MONTH);
	}

}
