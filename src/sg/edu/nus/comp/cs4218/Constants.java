package sg.edu.nus.comp.cs4218;

/**
 * Created by thientran on 2/7/17.
 */
public class Constants {
    public static class Common {
        public static final int CALENDAR_ROW_SIZE = 6;
        public static final int CALENDAR_COL_SIZE = 7;
        public static final int YEAR_ROW_SIZE = 4;
        public static final int YEAR_COL_SIZE = 3;
        public static final String SPACE_BETWEEN_MONTH = "        ";
        public static final String[] MONTH_TO_TEXT = {
        	"Jannuary", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
    }
    
    public class CalMessage {
    	public static final String INVALID_ARGS = "Agrs is null";
    	public static final String INVALID_NUMBER_ARGUMENTS = "Invalid number of arguments";
    	public static final String INVALID_YEAR = "Invalid year";
    	public static final String INVALID_MONTH = "Invalid month";
    }
}
