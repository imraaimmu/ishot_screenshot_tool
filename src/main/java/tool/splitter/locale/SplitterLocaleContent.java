package tool.splitter.locale;

import java.util.Locale;
import java.util.ResourceBundle;

import tool.ishot.Common;

public class SplitterLocaleContent {

	public static String IS_GREATER_THAN_THE_ACTUAL_START_MIN = "is greater than the actual Start Min";
	public static String IS_LESSER_THAN_THE_CURRENT_START_MIN = "is lesser than the current Start Min";
	public static String IS_GREATER_THAN_THE_CURRENT_END_MIN = "is greater than the current End Min";
	public static String IS_GREATER_THAN_THE_ACTUAL_END_MIN = "is greater than the actual End Min";
	public static String IS_GREATER_THAN_THE_ACTUAL_END_SEC = "is greater than the actual end sec";
	public static String IS_GREATER_THAN_THE_59 = "is greater than the 59 sec.";
	public static String FILE_SIZE = "File Size";
	public static String FILE_DURATION = "File Duration";
	public static String FILE_NAME = "File Name";
	public static String OUTPUT_FILE_SIZE = "Output File size";
	public static String OUTPUT_FILE_DURATION = "Output File Duration";
	public static String OUTPUT_FILE_NAME = "Output File Name";
	public static String OUTPUT_FILE_DETAILS = "OUTPUT FILE DETAILS";
	public static String CLOSE = "Close";
	public static String RESET = "Reset";
	public static String START_TIME_IS_GREATER_THAN_END_TIME = "Invalid split time.";
	public static String ENTER_VALUE_FOR_ENDING_SECOND = "Enter value for Ending second";
	public static String ENTER_VALUE_FOR_ENDING_MINUTE = "Enter value for Ending minute";
	public static String ENTER_VALUE_FOR_STARTING_SECOND = "Enter value for Starting second";
	public static String ENTER_VALUE_FOR_STARTING_MINUTE = "Enter value for Starting minute";
	public static String END_TIME_SHOULD_NOT_BE_EMPTY = "End time should not be empty";
	public static String START_TIME_SHOULD_NOT_BE_EMPTY = "Start time should not be empty";
	public static String START_TIME_AND_END_TIME_SHOULD_NOT_BE_EMPTY = "Start time and End time should not be empty";
	public static String SUCCESS = "Success";
	public static String SPLIT_COMPLETED = "Split Completed.";
	public static String SPLIT_FAILED = "Split Failed.";
	public static String OUTPUT_FILENAME_SHOULD_NOT_BE_EMPTY = "Output Filename should not be empty.";
	public static String SPLIT = "Split";
	public static String OUTPUT_FILE = "Output File";
	public static String END_TIME = "End Time";
	public static String SEC = "sec";
	public static String MIN = "min";
	public static String START_TIME = "Start Time";
	public static String ERROR = "Error";
	public static String DROP_1_FILE_ONLY = "Drop 1 file only";
	public static String CHOOSE_A_VIDEO_FILE_SHOULD_END_WITH_WMV = "Choose a Video File (Supported formats are .wmv and .mp4).";
	public static String SOURCE_PATH = "Source Path";
	public static String SELECT_VIDEO_TO_SPLIT = "Select video to split";
	public static String SELECT_VIDEO_FILE = "Select Video File";
	public static String CHOOSE_ANOTHER_VIDEO_FILE = "Choose another video file";
	public static String FFMPEG_NOT_FOUND_1 = "ffmpeg.exe not found\nSee log file for more details";
	public static String FFMPEG_NOT_FOUND_2 = "Video splitter needs 'ffmpeg.exe' in";
	public static String FFMPEG_NOT_FOUND_3 = "folder to run.";
	public static String FFMPEG_NOT_FOUND_4 = "if ffmpeg is not found, try to re-install iShot again.";
	public static String BACK = "Back";
	public static String VIDEO_SPLITTER = "Video Splitter 1.0";
	public static String IS_GREATER_THAN_THE_ACTUAL_START_TIME = "is greater than the actual start time";
	public static String IS_GREATER_THAN_THE_ACTUAL_END_TIME = "is greater than the actual end time";
	public static String INVALID_FILE_NAME = "Invalid Filename.";
	public static String INVALID_FILE = "Invalid File";
	public static String FILE_NAME_SHOULD_BE_LESS_THAN_100_CHAR = "File name should not be more than 100 characters. ";

	public static void setLocaleContent(){
		ResourceBundle bundle = getResourceBundle();
		IS_GREATER_THAN_THE_ACTUAL_START_MIN = bundle.getString("IS_GREATER_THAN_THE_ACTUAL_START_MIN");
		IS_LESSER_THAN_THE_CURRENT_START_MIN = bundle.getString("IS_LESSER_THAN_THE_CURRENT_START_MIN");
		IS_GREATER_THAN_THE_CURRENT_END_MIN = bundle.getString("IS_GREATER_THAN_THE_CURRENT_END_MIN");
		IS_GREATER_THAN_THE_ACTUAL_END_MIN = bundle.getString("IS_GREATER_THAN_THE_ACTUAL_END_MIN");
		IS_GREATER_THAN_THE_ACTUAL_END_SEC = bundle.getString("IS_GREATER_THAN_THE_ACTUAL_END_SEC");
		IS_GREATER_THAN_THE_59 = bundle.getString("IS_GREATER_THAN_THE_59");
		FILE_SIZE = bundle.getString("FILE_SIZE");
		FILE_DURATION = bundle.getString("FILE_DURATION");
		FILE_NAME = bundle.getString("FILE_NAME");
		OUTPUT_FILE_SIZE = bundle.getString("OUTPUT_FILE_SIZE");
		OUTPUT_FILE_DURATION = bundle.getString("OUTPUT_FILE_DURATION");
		OUTPUT_FILE_NAME = bundle.getString("OUTPUT_FILE_NAME");
		OUTPUT_FILE_DETAILS = bundle.getString("OUTPUT_FILE_DETAILS");
		CLOSE = bundle.getString("CLOSE");
		RESET = bundle.getString("RESET");
		START_TIME_IS_GREATER_THAN_END_TIME = bundle.getString("START_TIME_IS_GREATER_THAN_END_TIME");
		ENTER_VALUE_FOR_ENDING_SECOND = bundle.getString("ENTER_VALUE_FOR_ENDING_SECOND");
		ENTER_VALUE_FOR_ENDING_MINUTE = bundle.getString("ENTER_VALUE_FOR_ENDING_MINUTE");
		ENTER_VALUE_FOR_STARTING_SECOND = bundle.getString("ENTER_VALUE_FOR_STARTING_SECOND");
		ENTER_VALUE_FOR_STARTING_MINUTE = bundle.getString("ENTER_VALUE_FOR_STARTING_MINUTE");
		END_TIME_SHOULD_NOT_BE_EMPTY = bundle.getString("END_TIME_SHOULD_NOT_BE_EMPTY");
		START_TIME_SHOULD_NOT_BE_EMPTY = bundle.getString("START_TIME_SHOULD_NOT_BE_EMPTY");
		START_TIME_AND_END_TIME_SHOULD_NOT_BE_EMPTY = bundle.getString("START_TIME_AND_END_TIME_SHOULD_NOT_BE_EMPTY");
		SUCCESS = bundle.getString("SUCCESS");
		SPLIT_COMPLETED = bundle.getString("SPLIT_COMPLETED");
		SPLIT_FAILED = bundle.getString("SPLIT_FAILED");
		OUTPUT_FILENAME_SHOULD_NOT_BE_EMPTY = bundle.getString("OUTPUT_FILENAME_SHOULD_NOT_BE_EMPTY");
		SPLIT = bundle.getString("SPLIT");
		OUTPUT_FILE = bundle.getString("OUTPUT_FILE");
		END_TIME = bundle.getString("END_TIME");
		SEC = bundle.getString("SEC");
		MIN = bundle.getString("MIN");
		START_TIME = bundle.getString("START_TIME");
		ERROR = bundle.getString("ERROR");
		DROP_1_FILE_ONLY = bundle.getString("DROP_1_FILE_ONLY");
		CHOOSE_A_VIDEO_FILE_SHOULD_END_WITH_WMV = bundle.getString("CHOOSE_A_VIDEO_FILE_SHOULD_END_WITH_WMV");
		SOURCE_PATH = bundle.getString("SOURCE_PATH");
		SELECT_VIDEO_TO_SPLIT = bundle.getString("SELECT_VIDEO_TO_SPLIT");
		SELECT_VIDEO_FILE = bundle.getString("SELECT_VIDEO_FILE");
		CHOOSE_ANOTHER_VIDEO_FILE = bundle.getString("CHOOSE_ANOTHER_VIDEO_FILE");
		FFMPEG_NOT_FOUND_1 = bundle.getString("FFMPEG_NOT_FOUND_1");
		FFMPEG_NOT_FOUND_2 = bundle.getString("FFMPEG_NOT_FOUND_2");
		FFMPEG_NOT_FOUND_3 = bundle.getString("FFMPEG_NOT_FOUND_3");
		FFMPEG_NOT_FOUND_4 = bundle.getString("FFMPEG_NOT_FOUND_4");
		BACK = bundle.getString("BACK");
		VIDEO_SPLITTER= bundle.getString("VIDEO_SPLITTER");
		IS_GREATER_THAN_THE_ACTUAL_START_TIME = bundle.getString("IS_GREATER_THAN_THE_ACTUAL_START_TIME");
		IS_GREATER_THAN_THE_ACTUAL_END_TIME = bundle.getString("IS_GREATER_THAN_THE_ACTUAL_END_TIME");
		INVALID_FILE_NAME = bundle.getString("INVALID_FILE_NAME");
		FILE_NAME_SHOULD_BE_LESS_THAN_100_CHAR = bundle.getString("FILE_NAME_SHOULD_BE_LESS_THAN_100_CHAR");
		INVALID_FILE = bundle.getString("INVALID_FILE");
	}

	public static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		try{
			bundle = ResourceBundle.getBundle("tool.splitter.locale.splitter",new Locale(Locale.getDefault().getLanguage()));
		}catch(Exception e){
			Common.log(e);
			bundle = ResourceBundle.getBundle("tool.splitter.locale.splitter",new Locale("en"));
		}
		return bundle;
	}
}
