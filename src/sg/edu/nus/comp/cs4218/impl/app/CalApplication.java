package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.Constants;

public class CalApplication implements Cal {
	private static final DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private int[][] calendar;

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		// init calendar
		calendar = new int[Constants.Common.CALENDAR_ROW_SIZE][Constants.Common.CALENDAR_COL_SIZE];

		// current date
		Calendar cal = Calendar.getInstance();
		System.out.println(simpleDateFormat.format(cal.getTime()));
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
