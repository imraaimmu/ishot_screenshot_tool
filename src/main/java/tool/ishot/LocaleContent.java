package tool.ishot;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleContent {

	private static String FILE_NAME = "File name :";
	private static String FIND_ME = "Find Me";
	private static String EXIT = "Exit";
	private static String SCREENSHOT_MODE = "Screenshot Mode";
	private static String SCREEN_RECORDER_MODE = "Screen Recorder Mode";

	private static String TIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS = "Time taken to enter credentials (in secs)";
	private static String URL = "URL";
	private static String ENTER_URL = "Enter URL";
	private static String DELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS = "Delay Time (in secs): \n[Maximum Delay time - 30 secs]";
	private static String NO = "No";
	private static String YES = "Yes";
	private static String RECORDING = "Recording..";
	private static String RECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT = "Recording is in progress.\nDo you want to exit?";
	private static String SPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT = "Split video for every(MB): \n[0 indicates No Split]";
	private static String MAIL_TO = "Mail To";
	private static String COPY = "Copy";
	private static String DOCUMENT_MODE = "Document Mode";
	private static String RESET2 = "Reset";
	private static String SHORTCUT = "Create Shortcut";
	private static String USER_DEFINED = "User Defined";
	private static String DEFAULT = "Default";
	private static String SAVE_AS = "Save As";
	private static String VIDEO_QUALITY = "Video Quality";
	private static String ALL_MONITORS = "All Monitors";
	private static String SHOW_PREVIEW = "Show Preview";
	private static String SHOW_CLICKS = "Show Clicks";
	private static String SPLIT_VIDEO = "Split Video";
	private static String RECORD_AUDIO = "Record Audio";
	private static String TAKE_SCREENSHOT = "Take Screenshot";
	private static String REPORT_ISSUE = "Report Issue";
	private static String RECTANGULAR_SNIP = "Rectangular Snip";
	private static String GO_TO_TRAY = "Go to Tray";
	private static String ID_CLASS_SCREENSHOT = "ID/Class Screenshot";
	private static String FULL_SCREENSHOT = "Full Screenshot";
	private static String DELAY_CUSTOM = "Delay | Custom";
	private static String DELAY_5_SEC = "Delay | 5 sec";
	private static String DELAY_2_SEC = "Delay | 2 sec";
	private static String RECTANGULAR_VIDEO = "Rectangular Video";
	private static String DELAY_OPTIONS = "Delay Options";
	private static String OPTIONS = "Options";
	private static String CHANGE_SAVE_LOCATION = "Change Save Location";
	private static String OPEN_SAVED_FOLDER = "Open Saved Folder";
	private static String STOP2 = "Stop";
	private static String RESUME2 = "Resume";
	private static String PAUSE2 = "Pause";
	private static String HD = "HD";
	private static String NORMAL = "Normal";
	private static String VIDEO_ENHANCER = "VIDEO ENHANCER";


	private static String DELAY_TIME_SHOULD_NOT_BE_EMPTY = "Delay time should not be empty.";
	private static String SPLIT_VALUE_SHOULD_NOT_BE_EMPTY = "Split value should not be empty.";
	private static String DECIMAL_IS_NOT_ALLOWED = "Decimal is not allowed.";
	private static String VALUE_SHOULD_BE_LESS_THAN = "Value should be less than ";
	private static String SELECTOR_ID_SHOULD_NOT_BE_EMPTY = "Selector ID should not be empty.";
	private static String SELECTOR_CLASS_SHOULD_NOT_BE_EMPTY = "Selector Class should not be empty.";
	private static String ID_NOT_FOUND = "ID not found.";
	private static String CLASS_NOT_FOUND = "Class not found.";
	private static String TRY_TO_INPUT_WITH_30_SECONDS = "Try to input with 30 seconds.";
	private static String SORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED = "Sorry, Error in showing preview, Your screenshot might be saved.";
	private static String INPUT_ONLY_INTEGERS_IN_TIME_FIELD = "Input only integers in time field.";
	private static String TIME_SHOULD_NOT_BE_EMPTY = "Time should not be empty.";
	private static String URL_SHOULD_NOT_BE_EMPTY = "URL should not be empty.";
	private static String INPUT_INTEGER_ONLY = "Input Integer only.";
	private static String ERROR = "Error";
	private static String DELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S = "Delay time should be between 1 and 30 second(s).";
	private static String INPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0 = "Input number greater than or equal to 0.";
	private static String SUCCESS = "Success";
	private static String APP_HAS_BEEN_RESTARTED = "App has been restarted.";
	private static String INFO = "Info";
	private static String I_M_HERE = "I'm here.";
	private static String I_SHOT_IS_ALREADY_RUNNING = "iShot is already running.";
	private static String CLOSE_THE_FILE_AND_TRY_AGAIN = "Close the file and try again.";
	private static String BROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN = "Browser is terminated or not invoked. Please try again.";
	private static String INVALID_URL = "Invalid URL.";
	private static String CHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN = "Check the save location and try again.";
	private static String UNFORTUNATE_ERROR_OCCURED = "Unfortunate error occured.";
	private static String SAVED_SUCCESS = "Save location is changed.";
	private static String SET_PROPER_LOCATION = "Set proper location again.";
	private static String I_SHOT_V1 = "iShot 1.0";
	private static String FILE_CHOOSER_TITLE = "Choose location";
	private static String SCREENSHOTS = "Screenshots";
	private  static String ON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD = "On saving, Screenshot will be saved and copied to clipboard.";
	private static String CANCEL = "Cancel";
	private static String SAVE = "Save";
	private static String PREVIEW = "Preview";


	private static String ENTER_SELECTOR = "Enter selector ";
	private static String PLEASE_SELECT = "Please select";
	private static String CLASS = "Class";
	private static String ID = "ID";
	private static String VIDEO_SPLITTER = "VIDEO SPLITTER";
	private static String SHORTCUT_ALREADY_EXISTS_IN = "Shortcut already exists in";
	private static String SHORTCUT_CREATED_IN = "Shortcut created in";
	
	
	public static String getSHORTCUT_CREATED_IN() {
		return SHORTCUT_CREATED_IN;
	}
	public static void setSHORTCUT_CREATED_IN(String string) {
		SHORTCUT_CREATED_IN = string;
	}
	public static String getSHORTCUT_ALREADY_EXISTS_IN() {
		return SHORTCUT_ALREADY_EXISTS_IN;
	}
	public static void setSHORTCUT_ALREADY_EXISTS_IN(String string) {
		SHORTCUT_ALREADY_EXISTS_IN = string;
	}
	public static String getVIDEO_SPLITTER() {
		return VIDEO_SPLITTER;
	}
	public static void setVIDEO_SPLITTER(String vIDEO_SPLITTER) {
		VIDEO_SPLITTER = vIDEO_SPLITTER;
	}
	public static String getFILE_NAME() {
		return FILE_NAME;
	}
	public static void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public static String getFIND_ME() {
		return FIND_ME;
	}
	public static void setFIND_ME(String fIND_ME) {
		FIND_ME = fIND_ME;
	}
	public static String getEXIT() {
		return EXIT;
	}
	public static void setEXIT(String eXIT) {
		EXIT = eXIT;
	}
	public static String getSCREENSHOT_MODE() {
		return SCREENSHOT_MODE;
	}
	public static void setSCREENSHOT_MODE(String sCREENSHOT_MODE) {
		SCREENSHOT_MODE = sCREENSHOT_MODE;
	}
	public static String getSCREEN_RECORDER_MODE() {
		return SCREEN_RECORDER_MODE;
	}
	public static void setSCREEN_RECORDER_MODE(String sCREEN_RECORDER_MODE) {
		SCREEN_RECORDER_MODE = sCREEN_RECORDER_MODE;
	}
	public static String getTIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS() {
		return TIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS;
	}
	public static void setTIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS(String tIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS) {
		TIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS = tIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS;
	}
	public static String getURL() {
		return URL;
	}
	public static void setURL(String uRL) {
		URL = uRL;
	}
	public static String getENTER_URL() {
		return ENTER_URL;
	}
	public static void setENTER_URL(String eNTER_URL) {
		ENTER_URL = eNTER_URL;
	}
	public static String getDELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS() {
		return DELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS;
	}
	public static void setDELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS(String dELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS) {
		DELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS = dELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS;
	}
	public static String getNO() {
		return NO;
	}
	public static void setNO(String nO) {
		NO = nO;
	}
	public static String getYES() {
		return YES;
	}
	public static void setYES(String yES) {
		YES = yES;
	}
	public static String getRECORDING() {
		return RECORDING;
	}
	public static void setRECORDING(String rECORDING) {
		RECORDING = rECORDING;
	}
	public static String getRECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT() {
		return RECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT;
	}
	public static void setRECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT(String rECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT) {
		RECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT = rECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT;
	}
	public static String getSPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT() {
		return SPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT;
	}
	public static void setSPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT(String sPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT) {
		SPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT = sPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT;
	}
	public static String getMAIL_TO() {
		return MAIL_TO;
	}
	public static void setMAIL_TO(String mAIL_TO) {
		MAIL_TO = mAIL_TO;
	}
	public static String getCOPY() {
		return COPY;
	}
	public static void setCOPY(String cOPY) {
		COPY = cOPY;
	}
	public static String getDOCUMENT_MODE() {
		return DOCUMENT_MODE;
	}
	public static void setDOCUMENT_MODE(String dOCUMENT_MODE) {
		DOCUMENT_MODE = dOCUMENT_MODE;
	}
	public static String getRESET2() {
		return RESET2;
	}
	public static void setRESET2(String rESET2) {
		RESET2 = rESET2;
	}
	public static String getShortcut() {
		return SHORTCUT;
	}
	public static void setShortcut(String rESET2) {
		SHORTCUT = rESET2;
	}
	public static String getUSER_DEFINED() {
		return USER_DEFINED;
	}
	public static void setUSER_DEFINED(String uSER_DEFINED) {
		USER_DEFINED = uSER_DEFINED;
	}
	public static String getDEFAULT() {
		return DEFAULT;
	}
	public static void setDEFAULT(String dEFAULT) {
		DEFAULT = dEFAULT;
	}
	public static String getSAVE_AS() {
		return SAVE_AS;
	}
	public static void setSAVE_AS(String sAVE_AS) {
		SAVE_AS = sAVE_AS;
	}
	public static String getVIDEO_QUALITY() {
		return VIDEO_QUALITY;
	}
	public static void setVIDEO_QUALITY(String vIDEO_QUALITY) {
		VIDEO_QUALITY = vIDEO_QUALITY;
	}
	public static String getALL_MONITORS() {
		return ALL_MONITORS;
	}
	public static void setALL_MONITORS(String aLL_MONITORS) {
		ALL_MONITORS = aLL_MONITORS;
	}
	public static String getSHOW_PREVIEW() {
		return SHOW_PREVIEW;
	}
	public static void setSHOW_PREVIEW(String sHOW_PREVIEW) {
		SHOW_PREVIEW = sHOW_PREVIEW;
	}
	public static String getSHOW_CLICKS() {
		return SHOW_CLICKS;
	}
	public static void setSHOW_CLICKS(String sHOW_CLICKS) {
		SHOW_CLICKS = sHOW_CLICKS;
	}
	public static String getSPLIT_VIDEO() {
		return SPLIT_VIDEO;
	}
	public static void setSPLIT_VIDEO(String sPLIT_VIDEO) {
		SPLIT_VIDEO = sPLIT_VIDEO;
	}
	public static String getRECORD_AUDIO() {
		return RECORD_AUDIO;
	}
	public static void setRECORD_AUDIO(String rECORD_AUDIO) {
		RECORD_AUDIO = rECORD_AUDIO;
	}
	public static String getTAKE_SCREENSHOT() {
		return TAKE_SCREENSHOT;
	}
	public static void setTAKE_SCREENSHOT(String tAKE_SCREENSHOT) {
		TAKE_SCREENSHOT = tAKE_SCREENSHOT;
	}
	public static String getREPORT_ISSUE() {
		return REPORT_ISSUE;
	}
	public static void setREPORT_ISSUE(String rEPORT_ISSUE) {
		REPORT_ISSUE = rEPORT_ISSUE;
	}
	public static String getRECTANGULAR_SNIP() {
		return RECTANGULAR_SNIP;
	}
	public static void setRECTANGULAR_SNIP(String rECTANGULAR_SNIP) {
		RECTANGULAR_SNIP = rECTANGULAR_SNIP;
	}
	public static String getGO_TO_TRAY() {
		return GO_TO_TRAY;
	}
	public static void setGO_TO_TRAY(String gO_TO_TRAY) {
		GO_TO_TRAY = gO_TO_TRAY;
	}
	public static String getID_CLASS_SCREENSHOT() {
		return ID_CLASS_SCREENSHOT;
	}
	public static void setID_CLASS_SCREENSHOT(String iD_CLASS_SCREENSHOT) {
		ID_CLASS_SCREENSHOT = iD_CLASS_SCREENSHOT;
	}
	public static String getFULL_SCREENSHOT() {
		return FULL_SCREENSHOT;
	}
	public static void setFULL_SCREENSHOT(String fULL_SCREENSHOT) {
		FULL_SCREENSHOT = fULL_SCREENSHOT;
	}
	public static String getDELAY_CUSTOM() {
		return DELAY_CUSTOM;
	}
	public static void setDELAY_CUSTOM(String dELAY_CUSTOM) {
		DELAY_CUSTOM = dELAY_CUSTOM;
	}
	public static String getDELAY_5_SEC() {
		return DELAY_5_SEC;
	}
	public static void setDELAY_5_SEC(String dELAY_5_SEC) {
		DELAY_5_SEC = dELAY_5_SEC;
	}
	public static String getDELAY_2_SEC() {
		return DELAY_2_SEC;
	}
	public static void setDELAY_2_SEC(String dELAY_2_SEC) {
		DELAY_2_SEC = dELAY_2_SEC;
	}
	public static String getRECTANGULAR_VIDEO() {
		return RECTANGULAR_VIDEO;
	}
	public static void setRECTANGULAR_VIDEO(String rECTANGULAR_VIDEO) {
		RECTANGULAR_VIDEO = rECTANGULAR_VIDEO;
	}
	public static String getDELAY_OPTIONS() {
		return DELAY_OPTIONS;
	}
	public static void setDELAY_OPTIONS(String dELAY_OPTIONS) {
		DELAY_OPTIONS = dELAY_OPTIONS;
	}
	public static String getOPTIONS() {
		return OPTIONS;
	}
	public static void setOPTIONS(String oPTIONS) {
		OPTIONS = oPTIONS;
	}
	public static String getCHANGE_SAVE_LOCATION() {
		return CHANGE_SAVE_LOCATION;
	}
	public static void setCHANGE_SAVE_LOCATION(String cHANGE_SAVE_LOCATION) {
		CHANGE_SAVE_LOCATION = cHANGE_SAVE_LOCATION;
	}
	public static String getOPEN_SAVED_FOLDER() {
		return OPEN_SAVED_FOLDER;
	}
	public static void setOPEN_SAVED_FOLDER(String oPEN_SAVED_FOLDER) {
		OPEN_SAVED_FOLDER = oPEN_SAVED_FOLDER;
	}
	public static String getSTOP2() {
		return STOP2;
	}
	public static void setSTOP2(String sTOP2) {
		STOP2 = sTOP2;
	}
	public static String getRESUME2() {
		return RESUME2;
	}
	public static void setRESUME2(String rESUME2) {
		RESUME2 = rESUME2;
	}
	public static String getPAUSE2() {
		return PAUSE2;
	}
	public static void setPAUSE2(String pAUSE2) {
		PAUSE2 = pAUSE2;
	}
	public static String getHD() {
		return HD;
	}
	public static void setHD(String hD) {
		HD = hD;
	}
	public static String getNORMAL() {
		return NORMAL;
	}
	public static void setNORMAL(String nORMAL) {
		NORMAL = nORMAL;
	}
	public static String getDELAY_TIME_SHOULD_NOT_BE_EMPTY() {
		return DELAY_TIME_SHOULD_NOT_BE_EMPTY;
	}
	public static void setDELAY_TIME_SHOULD_NOT_BE_EMPTY(String dELAY_TIME_SHOULD_NOT_BE_EMPTY) {
		DELAY_TIME_SHOULD_NOT_BE_EMPTY = dELAY_TIME_SHOULD_NOT_BE_EMPTY;
	}
	public static String getSPLIT_VALUE_SHOULD_NOT_BE_EMPTY() {
		return SPLIT_VALUE_SHOULD_NOT_BE_EMPTY;
	}
	public static void setSPLIT_VALUE_SHOULD_NOT_BE_EMPTY(String sPLIT_VALUE_SHOULD_NOT_BE_EMPTY) {
		SPLIT_VALUE_SHOULD_NOT_BE_EMPTY = sPLIT_VALUE_SHOULD_NOT_BE_EMPTY;
	}
	public static String getDECIMAL_IS_NOT_ALLOWED() {
		return DECIMAL_IS_NOT_ALLOWED;
	}
	public static void setDECIMAL_IS_NOT_ALLOWED(String dECIMAL_IS_NOT_ALLOWED) {
		DECIMAL_IS_NOT_ALLOWED = dECIMAL_IS_NOT_ALLOWED;
	}
	public static String getVALUE_SHOULD_BE_LESS_THAN() {
		return VALUE_SHOULD_BE_LESS_THAN;
	}
	public static void setVALUE_SHOULD_BE_LESS_THAN(String vALUE_SHOULD_BE_LESS_THAN) {
		VALUE_SHOULD_BE_LESS_THAN = vALUE_SHOULD_BE_LESS_THAN;
	}
	public static String getSELECTOR_ID_SHOULD_NOT_BE_EMPTY() {
		return SELECTOR_ID_SHOULD_NOT_BE_EMPTY;
	}
	public static void setSELECTOR_ID_SHOULD_NOT_BE_EMPTY(String sELECTOR_ID_SHOULD_NOT_BE_EMPTY) {
		SELECTOR_ID_SHOULD_NOT_BE_EMPTY = sELECTOR_ID_SHOULD_NOT_BE_EMPTY;
	}
	public static String getSELECTOR_CLASS_SHOULD_NOT_BE_EMPTY() {
		return SELECTOR_CLASS_SHOULD_NOT_BE_EMPTY;
	}
	public static void setSELECTOR_CLASS_SHOULD_NOT_BE_EMPTY(String sELECTOR_CLASS_SHOULD_NOT_BE_EMPTY) {
		SELECTOR_CLASS_SHOULD_NOT_BE_EMPTY = sELECTOR_CLASS_SHOULD_NOT_BE_EMPTY;
	}
	public static String getID_NOT_FOUND() {
		return ID_NOT_FOUND;
	}
	public static void setID_NOT_FOUND(String iD_NOT_FOUND) {
		ID_NOT_FOUND = iD_NOT_FOUND;
	}
	public static String getCLASS_NOT_FOUND() {
		return CLASS_NOT_FOUND;
	}
	public static void setCLASS_NOT_FOUND(String cLASS_NOT_FOUND) {
		CLASS_NOT_FOUND = cLASS_NOT_FOUND;
	}
	public static String getTRY_TO_INPUT_WITH_30_SECONDS() {
		return TRY_TO_INPUT_WITH_30_SECONDS;
	}
	public static void setTRY_TO_INPUT_WITH_30_SECONDS(String tRY_TO_INPUT_WITH_30_SECONDS) {
		TRY_TO_INPUT_WITH_30_SECONDS = tRY_TO_INPUT_WITH_30_SECONDS;
	}
	public static String getSORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED() {
		return SORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED;
	}
	public static void setSORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED(
			String sORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED) {
		SORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED = sORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED;
	}
	public static String getINPUT_ONLY_INTEGERS_IN_TIME_FIELD() {
		return INPUT_ONLY_INTEGERS_IN_TIME_FIELD;
	}
	public static void setINPUT_ONLY_INTEGERS_IN_TIME_FIELD(String iNPUT_ONLY_INTEGERS_IN_TIME_FIELD) {
		INPUT_ONLY_INTEGERS_IN_TIME_FIELD = iNPUT_ONLY_INTEGERS_IN_TIME_FIELD;
	}
	public static String getTIME_SHOULD_NOT_BE_EMPTY() {
		return TIME_SHOULD_NOT_BE_EMPTY;
	}
	public static void setTIME_SHOULD_NOT_BE_EMPTY(String tIME_SHOULD_NOT_BE_EMPTY) {
		TIME_SHOULD_NOT_BE_EMPTY = tIME_SHOULD_NOT_BE_EMPTY;
	}
	public static String getURL_SHOULD_NOT_BE_EMPTY() {
		return URL_SHOULD_NOT_BE_EMPTY;
	}
	public static void setURL_SHOULD_NOT_BE_EMPTY(String uRL_SHOULD_NOT_BE_EMPTY) {
		URL_SHOULD_NOT_BE_EMPTY = uRL_SHOULD_NOT_BE_EMPTY;
	}
	public static String getINPUT_INTEGER_ONLY() {
		return INPUT_INTEGER_ONLY;
	}
	public static void setINPUT_INTEGER_ONLY(String iNPUT_INTEGER_ONLY) {
		INPUT_INTEGER_ONLY = iNPUT_INTEGER_ONLY;
	}
	public static String getERROR() {
		return ERROR;
	}
	public static void setERROR(String eRROR) {
		ERROR = eRROR;
	}
	public static String getDELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S() {
		return DELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S;
	}
	public static void setDELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S(String dELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S) {
		DELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S = dELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S;
	}
	public static String getINPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0() {
		return INPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0;
	}
	public static void setINPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0(String iNPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0) {
		INPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0 = iNPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0;
	}
	public static String getSUCCESS() {
		return SUCCESS;
	}
	public static void setSUCCESS(String sUCCESS) {
		SUCCESS = sUCCESS;
	}
	public static String getAPP_HAS_BEEN_RESTARTED() {
		return APP_HAS_BEEN_RESTARTED;
	}
	public static void setAPP_HAS_BEEN_RESTARTED(String aPP_HAS_BEEN_RESTARTED) {
		APP_HAS_BEEN_RESTARTED = aPP_HAS_BEEN_RESTARTED;
	}
	public static String getINFO() {
		return INFO;
	}
	public static void setINFO(String iNFO) {
		INFO = iNFO;
	}
	public static String getI_M_HERE() {
		return I_M_HERE;
	}
	public static void setI_M_HERE(String i_M_HERE) {
		I_M_HERE = i_M_HERE;
	}
	public static String getI_SHOT_IS_ALREADY_RUNNING() {
		return I_SHOT_IS_ALREADY_RUNNING;
	}
	public static void setI_SHOT_IS_ALREADY_RUNNING(String i_SHOT_IS_ALREADY_RUNNING) {
		I_SHOT_IS_ALREADY_RUNNING = i_SHOT_IS_ALREADY_RUNNING;
	}
	public static String getCLOSE_THE_FILE_AND_TRY_AGAIN() {
		return CLOSE_THE_FILE_AND_TRY_AGAIN;
	}
	public static void setCLOSE_THE_FILE_AND_TRY_AGAIN(String cLOSE_THE_FILE_AND_TRY_AGAIN) {
		CLOSE_THE_FILE_AND_TRY_AGAIN = cLOSE_THE_FILE_AND_TRY_AGAIN;
	}
	public static String getBROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN() {
		return BROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN;
	}
	public static void setBROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN(
			String bROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN) {
		BROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN = bROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN;
	}
	public static String getINVALID_URL() {
		return INVALID_URL;
	}
	public static void setINVALID_URL(String iNVALID_URL) {
		INVALID_URL = iNVALID_URL;
	}
	public static String getCHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN() {
		return CHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN;
	}
	public static void setCHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN(String cHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN) {
		CHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN = cHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN;
	}
	public static String getUNFORTUNATE_ERROR_OCCURED() {
		return UNFORTUNATE_ERROR_OCCURED;
	}
	public static void setUNFORTUNATE_ERROR_OCCURED(String uNFORTUNATE_ERROR_OCCURED) {
		UNFORTUNATE_ERROR_OCCURED = uNFORTUNATE_ERROR_OCCURED;
	}
	public static String getSAVED_SUCCESS() {
		return SAVED_SUCCESS;
	}
	public static void setSAVED_SUCCESS(String sAVED_SUCCESS) {
		SAVED_SUCCESS = sAVED_SUCCESS;
	}
	public static String getSET_PROPER_LOCATION() {
		return SET_PROPER_LOCATION;
	}
	public static void setSET_PROPER_LOCATION(String sET_PROPER_LOCATION) {
		SET_PROPER_LOCATION = sET_PROPER_LOCATION;
	}
	public static String getI_SHOT_V1() {
		return I_SHOT_V1;
	}
	public static void setI_SHOT_V1(String i_SHOT_V1) {
		I_SHOT_V1 = i_SHOT_V1;
	}
	public static String getFILE_CHOOSER_TITLE() {
		return FILE_CHOOSER_TITLE;
	}
	public static void setFILE_CHOOSER_TITLE(String fILE_CHOOSER_TITLE) {
		FILE_CHOOSER_TITLE = fILE_CHOOSER_TITLE;
	}
	public static String getSCREENSHOTS() {
		return SCREENSHOTS;
	}
	public static void setSCREENSHOTS(String sCREENSHOTS) {
		SCREENSHOTS = sCREENSHOTS;
	}
	public static String getON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD() {
		return ON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD;
	}
	public static void setON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD(
			String oN_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD) {
		ON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD = oN_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD;
	}
	public static String getCANCEL() {
		return CANCEL;
	}
	public static void setCANCEL(String cANCEL) {
		CANCEL = cANCEL;
	}
	public static String getSAVE() {
		return SAVE;
	}
	public static void setSAVE(String sAVE) {
		SAVE = sAVE;
	}
	public static String getPREVIEW() {
		return PREVIEW;
	}
	public static void setPREVIEW(String pREVIEW) {
		PREVIEW = pREVIEW;
	}
	public static String getENTER_SELECTOR() {
		return ENTER_SELECTOR;
	}
	public static void setENTER_SELECTOR(String eNTER_SELECTOR) {
		ENTER_SELECTOR = eNTER_SELECTOR;
	}
	public static String getPLEASE_SELECT() {
		return PLEASE_SELECT;
	}
	public static void setPLEASE_SELECT(String pLEASE_SELECT) {
		PLEASE_SELECT = pLEASE_SELECT;
	}
	public static String getCLASS() {
		return CLASS;
	}
	public static void setCLASS(String cLASS) {
		CLASS = cLASS;
	}
	public static String getID() {
		return ID;
	}
	public static void setID(String iD) {
		ID = iD;
	}
	
	public static void setLocale() {
		ResourceBundle bundle = getResourceBundle();
		setFILE_NAME(bundle.getString("FILE_NAME"));
		setFIND_ME(bundle.getString("FIND_ME"));
		setEXIT(bundle.getString("EXIT"));
		setSCREENSHOT_MODE(bundle.getString("SCREENSHOT_MODE"));
		setSCREEN_RECORDER_MODE(bundle.getString("SCREEN_RECORDER_MODE"));
		setTIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS(bundle.getString("TIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS"));
		setURL(bundle.getString("URL"));
		setENTER_URL(bundle.getString("ENTER_URL"));
		setDELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS(bundle.getString("DELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS"));
		setNO(bundle.getString("NO"));
		setYES(bundle.getString("YES"));
		setRECORDING(bundle.getString("RECORDING"));
		setRECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT(bundle.getString("RECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT"));
		setSPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT(bundle.getString("SPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT"));
		setMAIL_TO(bundle.getString("MAIL_TO"));
		setCOPY(bundle.getString("COPY"));
		setDOCUMENT_MODE(bundle.getString("DOCUMENT_MODE"));
		setRESET2(bundle.getString("RESET2"));
		setShortcut(bundle.getString("SHORTCUT"));
		setUSER_DEFINED(bundle.getString("USER_DEFINED"));
		setDEFAULT(bundle.getString("DEFAULT"));
		setSAVE_AS(bundle.getString("SAVE_AS"));
		setVIDEO_QUALITY(bundle.getString("VIDEO_QUALITY"));
		setALL_MONITORS(bundle.getString("ALL_MONITORS"));
		setSHOW_PREVIEW(bundle.getString("SHOW_PREVIEW"));
		setSHOW_CLICKS(bundle.getString("SHOW_CLICKS"));
		setSPLIT_VIDEO(bundle.getString("SPLIT_VIDEO"));
		setTAKE_SCREENSHOT(bundle.getString("TAKE_SCREENSHOT"));
		setREPORT_ISSUE(bundle.getString("REPORT_ISSUE"));
		setRECTANGULAR_SNIP(bundle.getString("RECTANGULAR_SNIP"));
		setGO_TO_TRAY(bundle.getString("GO_TO_TRAY"));
		setID_CLASS_SCREENSHOT(bundle.getString("ID_CLASS_SCREENSHOT"));
		setFULL_SCREENSHOT(bundle.getString("FULL_SCREENSHOT"));
		setDELAY_CUSTOM(bundle.getString("DELAY_CUSTOM"));
		setDELAY_5_SEC(bundle.getString("DELAY_5_SEC"));
		setDELAY_2_SEC(bundle.getString("DELAY_2_SEC"));
		setRECTANGULAR_VIDEO(bundle.getString("RECTANGULAR_VIDEO"));
		setDELAY_OPTIONS(bundle.getString("DELAY_OPTIONS"));
		setOPTIONS(bundle.getString("OPTIONS"));
		setCHANGE_SAVE_LOCATION(bundle.getString("CHANGE_SAVE_LOCATION"));
		setOPEN_SAVED_FOLDER(bundle.getString("OPEN_SAVED_FOLDER"));
		setSTOP2(bundle.getString("STOP2"));
		setRESUME2(bundle.getString("RESUME2"));
		setPAUSE2(bundle.getString("PAUSE2"));
		setHD(bundle.getString("HD"));
		setNORMAL(bundle.getString("NORMAL"));
		setDELAY_TIME_SHOULD_NOT_BE_EMPTY(bundle.getString("DELAY_TIME_SHOULD_NOT_BE_EMPTY"));
		setSPLIT_VALUE_SHOULD_NOT_BE_EMPTY(bundle.getString("SPLIT_VALUE_SHOULD_NOT_BE_EMPTY"));
		setDECIMAL_IS_NOT_ALLOWED(bundle.getString("DECIMAL_IS_NOT_ALLOWED"));
		setVALUE_SHOULD_BE_LESS_THAN(bundle.getString("VALUE_SHOULD_BE_LESS_THAN"));
		setSELECTOR_ID_SHOULD_NOT_BE_EMPTY(bundle.getString("SELECTOR_ID_SHOULD_NOT_BE_EMPTY"));
		setSELECTOR_CLASS_SHOULD_NOT_BE_EMPTY(bundle.getString("SELECTOR_CLASS_SHOULD_NOT_BE_EMPTY"));
		setID_NOT_FOUND(bundle.getString("ID_NOT_FOUND"));
		setCLASS_NOT_FOUND(bundle.getString("CLASS_NOT_FOUND"));
		setTRY_TO_INPUT_WITH_30_SECONDS(bundle.getString("TRY_TO_INPUT_WITH_30_SECONDS"));
		setSORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED(bundle.getString("SORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED"));
		setINPUT_ONLY_INTEGERS_IN_TIME_FIELD(bundle.getString("INPUT_ONLY_INTEGERS_IN_TIME_FIELD"));
		setTIME_SHOULD_NOT_BE_EMPTY(bundle.getString("TIME_SHOULD_NOT_BE_EMPTY"));
		setURL_SHOULD_NOT_BE_EMPTY(bundle.getString("URL_SHOULD_NOT_BE_EMPTY"));
		setINPUT_INTEGER_ONLY(bundle.getString("INPUT_INTEGER_ONLY"));
		setERROR(bundle.getString("ERROR"));
		setDELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S(bundle.getString("DELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S"));
		setINPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0(bundle.getString("INPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0"));
		setSUCCESS(bundle.getString("SUCCESS"));
		setAPP_HAS_BEEN_RESTARTED(bundle.getString("APP_HAS_BEEN_RESTARTED"));
		setINFO(bundle.getString("INFO"));
		setI_M_HERE(bundle.getString("I_M_HERE"));
		setI_SHOT_IS_ALREADY_RUNNING(bundle.getString("I_SHOT_IS_ALREADY_RUNNING"));
		setCLOSE_THE_FILE_AND_TRY_AGAIN(bundle.getString("CLOSE_THE_FILE_AND_TRY_AGAIN"));
		setBROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN(bundle.getString("BROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN"));
		setINVALID_URL(bundle.getString("INVALID_URL"));
		setCHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN(bundle.getString("CHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN"));
		setUNFORTUNATE_ERROR_OCCURED(bundle.getString("UNFORTUNATE_ERROR_OCCURED"));
		setSAVED_SUCCESS(bundle.getString("SAVED_SUCCESS"));
		setSET_PROPER_LOCATION(bundle.getString("SET_PROPER_LOCATION"));
		setI_SHOT_V1(bundle.getString("I_SHOT_V1"));
		setFILE_CHOOSER_TITLE(bundle.getString("FILE_CHOOSER_TITLE"));
		setSCREENSHOTS(bundle.getString("SCREENSHOTS"));
		setON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD(bundle.getString("ON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD"));
		setCANCEL(bundle.getString("CANCEL"));
		setSAVE(bundle.getString("SAVE"));
		setPREVIEW(bundle.getString("PREVIEW"));
		setENTER_SELECTOR(bundle.getString("ENTER_SELECTOR"));
		setPLEASE_SELECT(bundle.getString("PLEASE_SELECT"));
		setCLASS(bundle.getString("CLASS"));
		setID(bundle.getString("ID"));
		setVIDEO_SPLITTER(bundle.getString("VIDEO_SPLITTER"));
		setVIDEO_ENHANCER(bundle.getString("VIDEO_ENHANCER"));
		setSHORTCUT_ALREADY_EXISTS_IN(bundle.getString("SHORTCUT_ALREADY_EXISTS_IN"));
		setSHORTCUT_CREATED_IN(bundle.getString("SHORTCUT_CREATED_IN"));
	}
	public static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		try{
			bundle = ResourceBundle.getBundle("tool.ishot.locale.ishot",new Locale(Locale.getDefault().getLanguage()));
		}catch(Exception e){
			Common.log(e);
			bundle = ResourceBundle.getBundle("tool.ishot.locale.ishot",new Locale("en"));
		}
		return bundle;
	}
	public static void iShotAlreadyRunning(){
		ResourceBundle bundle = getResourceBundle();
		LocaleContent.setI_SHOT_IS_ALREADY_RUNNING(bundle.getString("I_SHOT_IS_ALREADY_RUNNING"));
	}
	
	public static String getVIDEO_ENHANCER() {
		return VIDEO_ENHANCER;
	}
	public static void setVIDEO_ENHANCER(String vIDEO_ENHANCER) {
		VIDEO_ENHANCER = vIDEO_ENHANCER;
	}
}
