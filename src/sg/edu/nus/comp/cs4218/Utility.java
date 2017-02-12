package sg.edu.nus.comp.cs4218;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;
import sg.edu.nus.comp.cs4218.impl.cmd.SeqCommand;
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

	/**
	 * function to create Command from the respective String
	 * @param command
	 * @return command as type Command
	 */
	public static Command getCommandFromString(String command) {
		command = command.trim();
		if (command.indexOf(";") != -1) {
			return new SeqCommand(command);
		}
		
		return new CallCommand(command);
	}
	
	//Implement merge sort
	public static <T extends Comparable<T>> void sort (T[] values) {
		mergeSort(values, 0, values.length/2);
	}
	public static <T extends Comparable<T>> void mergeSort (T[] values, int left, int right) {
		int middle = (left+right)/2;
		
		//Recursively dividing
		mergeSort(values, left, middle);
		mergeSort(values, middle+1, right);
		
		//Merge partitions
		merge(values, left, middle, right);
	}
	public static <T extends Comparable<T>> void merge (T[] values, int left, int middle, int right) {
		int stepCount = right-left+1;
		
		int leftStep = left;
		T leftVal = null;
		
		int rightStep = middle+1;
		T rightVal = null;
		
		//Initialize tempArr with T objects of values' size
		ArrayList<T> tempList = new ArrayList<T>(Collections.nCopies(stepCount, values[0]));
		
		//Merge the 2 lists' items iteratively
		for (int i = 0; i<stepCount; i++){
			if (leftStep <= middle &&rightStep <= right) {
				leftVal = values[leftStep];
				rightVal = values[rightStep];
			}
			//If 1 of the 2 lists is already exhausted, add the remaining sorted items to list
			else {
				if (leftStep == middle) {
					for (int j = rightStep; j<stepCount; j++) {
						rightVal = values[j];
						tempList.set(j, rightVal);
					}
				}
				else {
					for (int j = leftStep; j<=middle; j++) {
						leftVal = values[j];
						tempList.set(j, leftVal);
					}
				}
				break;
			}
			
			//Add the smaller value to tempList
			if(compare(leftVal, rightVal)<=0) {
				tempList.set(i, leftVal);
				leftStep++;
			}
			else {
				tempList.set(i, rightVal);
				rightStep++;
			}
		}
		for (int i = 0; i<stepCount; i++) {
			values[left + i] = tempList.get(i);
		}
	}
	//Compare 2 T objects as define in object comparator
	public static <T extends Comparable<T>> int compare(T first, T second) {
		return first.compareTo(second);
	}
}
