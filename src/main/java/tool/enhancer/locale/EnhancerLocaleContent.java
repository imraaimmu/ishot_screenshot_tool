package tool.enhancer.locale;

import java.util.Locale;
import java.util.ResourceBundle;

import tool.ishot.Common;

public class EnhancerLocaleContent {

	public static String DO_YOU_WANT_TO_GO_TO_THE_STARTING_OF_THE_SEEKER = "Do you want to go to the starting of the seeker?";
	public static String CHOOSE_COLOR = "Choose Color";
	public static String FONT_SIZE = "Font Size";
	public static String FONT_NAME = "Font Name";
	public static String SELECT_FONT = "Select Font";
	public static String ENTER_TEXT = "Enter Text";
	public static String CUSTOMIZE_TEXT = "Customize Text";
	public static String SHAPE_THICKNESS = "Shape Thickness";
	public static String SHAPE_COLOR = "Shape Color";
	public static String CANCEL = "Cancel";
	public static String SET = "Set";
	public static String CUSTOMIZE_SHAPE = "Customize Shape";
	public static String OK = "Ok";
	public static String CONFIRMATION_TO_CHANGE_PATH = "Confirmation to change path";
	public static String CONFIRM = "Confirm";
	public static String DO_YOU_WANT_TO_CHANGE_DESTINATION_PATH = "Do you want to change destination path?";
	public static String OPEN_RESOURCE_DIRECTORY = "Open Resource Directory";
	public static String VIDEO_FILES = "Video Files";
	public static String SELECT_SOURCE_FILE = "Select source file";
	public static String PLEASE_SELECT_MP4_OR_WMV_EXTENSIONS = "Please Select .mp4 or .wmv extensions.";
	public static String PLEASE_SELECT_DESTINATION_PATH = "Please Select Destination Path.";
	public static String WRONG_SOURCE_PATH = "Wrong Source Path.";
	public static String WRONG_DESTINATION_PATH = "Wrong Destination Path.";
	public static String PLEASE_PROVIDE_PROPER_DESTINATION_PATH = "Please Provide Proper Destination Path.";
	public static String INVALID_FILENAME = "Invalid Filename.";
	public static String FILENAME_SHOULD_NOT_BE_EMPTY = "Filename should not be empty.";
	public static String PLEASE_SELECT_FILE_PATH = "Please Select File Path.";
	public static String PLEASE_PROVIDE_PROPER_FILE_PATH = "Please Provide Proper File Path.";
	public static String LAUNCH_EDITOR = "Launch Editor";
	public static String CHOOSE_DIRECTORY = "Choose Directory";
	public static String DESTINATION_PATH = "Destination Path";
	public static String OUTPUT_FILE_NAME = "Output File Name";
	public static String CHOOSE_FILE = "Choose File";
	public static String CLOSE = "Close";
	public static String SETTINGS = "Settings";
	public static String EXPORTED_TO = "Exported to";
	public static String EXPORT_FAILED = "Export Failed";
	public static String RESTART = "Restart";
	public static String STOP = "Stop";
	public static String PAUSE = "Pause";
	public static String PLAY = "Play";
	public static String THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT = "This file isn't playable. That might be because the file type is unsupported,the file extension is incorrect,the file is corrupt.\nAlso note that the video resolution should be 1920x1080. Check the file and try again.";
	public static String ERROR = "Error";
	public static String DO_YOU_WANT_TO_EXIT = "Do you want to exit?";
	public static String CONFIRMATION = "Confirmation";
	public static String VIDEO_EDITOR = "Video Editor";
	public static String BACK = "Back";
	public static String ADD_SHAPE = "Add Shape";
	public static String ADD_TEXT = "Add Text";
	public static String EXPORT = "Export";
	public static String FFMPEG_NOT_FOUND_1 = "ffmpeg.exe not found\nSee log file for more details";
	public static String FFMPEG_NOT_FOUND_2 = "Video splitter needs 'ffmpeg.exe' in";
	public static String FFMPEG_NOT_FOUND_3 = "folder to run.";
	public static String FFMPEG_NOT_FOUND_4 = "if ffmpeg is not found, try to re-install iShot again.";
	public static String SOURCE_FILE = "Source File";
	public static String ARE_YOU_SURE_YOU_WANT_TO_GO_BACK = "Discard Modifications and continue current action ?";
	public static String THIS_ACTION_WILL_DISCARD_CURRENT_CHANGES = "This action will discard current changes.";
	public static String CONVERTING_WMV_TO_MP4_VIDEO_EDITOR_SUPPORTS_ONLY_MP4_FILES = "Converting wmv to mp4, Video Editor supports only mp4 files.";

	public static void setLocaleContent(){
		ResourceBundle bundle = getResourceBundle();
		DO_YOU_WANT_TO_GO_TO_THE_STARTING_OF_THE_SEEKER = bundle.getString("DO_YOU_WANT_TO_GO_TO_THE_STARTING_OF_THE_SEEKER");
		CHOOSE_COLOR = bundle.getString("CHOOSE_COLOR");
		FONT_SIZE = bundle.getString("FONT_SIZE");
		FONT_NAME = bundle.getString("FONT_NAME");
		SELECT_FONT = bundle.getString("SELECT_FONT");
		ENTER_TEXT = bundle.getString("ENTER_TEXT");
		CUSTOMIZE_TEXT = bundle.getString("CUSTOMIZE_TEXT");
		SHAPE_THICKNESS = bundle.getString("SHAPE_THICKNESS");
		SHAPE_COLOR = bundle.getString("SHAPE_COLOR");
		CANCEL = bundle.getString("CANCEL");
		SET = bundle.getString("SET");
		CUSTOMIZE_SHAPE = bundle.getString("CUSTOMIZE_SHAPE");
		OK = bundle.getString("OK");
		CONFIRMATION_TO_CHANGE_PATH = bundle.getString("CONFIRMATION_TO_CHANGE_PATH");
		CONFIRM = bundle.getString("CONFIRM");
		DO_YOU_WANT_TO_CHANGE_DESTINATION_PATH = bundle.getString("DO_YOU_WANT_TO_CHANGE_DESTINATION_PATH");
		OPEN_RESOURCE_DIRECTORY = bundle.getString("OPEN_RESOURCE_DIRECTORY");
		VIDEO_FILES = bundle.getString("VIDEO_FILES");
		SELECT_SOURCE_FILE = bundle.getString("SELECT_SOURCE_FILE");
		PLEASE_SELECT_MP4_OR_WMV_EXTENSIONS = bundle.getString("PLEASE_SELECT_MP4_OR_WMV_EXTENSIONS");
		PLEASE_SELECT_DESTINATION_PATH = bundle.getString("PLEASE_SELECT_DESTINATION_PATH");
		WRONG_SOURCE_PATH = bundle.getString("WRONG_SOURCE_PATH");
		WRONG_DESTINATION_PATH = bundle.getString("WRONG_DESTINATION_PATH");
		PLEASE_PROVIDE_PROPER_DESTINATION_PATH = bundle.getString("PLEASE_PROVIDE_PROPER_DESTINATION_PATH");
		INVALID_FILENAME = bundle.getString("INVALID_FILENAME");
		FILENAME_SHOULD_NOT_BE_EMPTY = bundle.getString("FILENAME_SHOULD_NOT_BE_EMPTY");
		PLEASE_SELECT_FILE_PATH = bundle.getString("PLEASE_SELECT_FILE_PATH");
		PLEASE_PROVIDE_PROPER_FILE_PATH = bundle.getString("PLEASE_PROVIDE_PROPER_FILE_PATH");
		LAUNCH_EDITOR = bundle.getString("LAUNCH_EDITOR");
		CHOOSE_DIRECTORY = bundle.getString("CHOOSE_DIRECTORY");
		DESTINATION_PATH = bundle.getString("DESTINATION_PATH");
		OUTPUT_FILE_NAME = bundle.getString("OUTPUT_FILE_NAME");
		CHOOSE_FILE = bundle.getString("CHOOSE_FILE");
		CLOSE = bundle.getString("CLOSE");
		SETTINGS = bundle.getString("SETTINGS");
		EXPORTED_TO = bundle.getString("EXPORTED_TO");
		EXPORT_FAILED = bundle.getString("EXPORT_FAILED");
		RESTART = bundle.getString("RESTART");
		STOP = bundle.getString("STOP");
		PAUSE = bundle.getString("PAUSE");
		PLAY = bundle.getString("PLAY");
		THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT = bundle.getString("THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT");
		ERROR = bundle.getString("ERROR");
		DO_YOU_WANT_TO_EXIT = bundle.getString("DO_YOU_WANT_TO_EXIT");
		CONFIRMATION = bundle.getString("CONFIRMATION");
		VIDEO_EDITOR = bundle.getString("VIDEO_EDITOR");
		BACK = bundle.getString("BACK");
		ADD_SHAPE = bundle.getString("ADD_SHAPE");
		ADD_TEXT = bundle.getString("ADD_TEXT");
		EXPORT = bundle.getString("EXPORT");
		FFMPEG_NOT_FOUND_1 = bundle.getString("FFMPEG_NOT_FOUND_1");
		FFMPEG_NOT_FOUND_2 = bundle.getString("FFMPEG_NOT_FOUND_2");
		FFMPEG_NOT_FOUND_3 = bundle.getString("FFMPEG_NOT_FOUND_3");
		FFMPEG_NOT_FOUND_4 = bundle.getString("FFMPEG_NOT_FOUND_4");
		SOURCE_FILE = bundle.getString("SOURCE_FILE");
		ARE_YOU_SURE_YOU_WANT_TO_GO_BACK = bundle.getString("ARE_YOU_SURE_YOU_WANT_TO_GO_BACK");
		THIS_ACTION_WILL_DISCARD_CURRENT_CHANGES = bundle.getString("THIS_ACTION_WILL_DISCARD_CURRENT_CHANGES");
		CONVERTING_WMV_TO_MP4_VIDEO_EDITOR_SUPPORTS_ONLY_MP4_FILES = bundle.getString("CONVERTING_WMV_TO_MP4_VIDEO_EDITOR_SUPPORTS_ONLY_MP4_FILES");
	}

	public static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		try{
			bundle = ResourceBundle.getBundle("tool.enhancer.locale.enhancer",new Locale(Locale.getDefault().getLanguage()));
		}catch(Exception e){
			Common.log(e);
			bundle = ResourceBundle.getBundle("tool.enhancer.locale.enhancer",new Locale("en"));
		}
		return bundle;
	}
}
