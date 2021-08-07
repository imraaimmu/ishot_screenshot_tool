package tool.ishot;

import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Common {

	

	public static final String ASSISTIVE_ICON = "ishot-images/AssistiveScreenshot.png";

	public static final String STOP_ICON = "ishot-images/onRecord.png";
	
	public static final String CLICK_ICON = "ishot-images/click.png";

	public static final String SMILEY_ICON = "ironarc.png";
	
	public static final String VIDEO_START_ICON = "ishot-images/screenRecord.png";
	


	public static void showUnfortunateMessage() {
		showError(LocaleContent.getUNFORTUNATE_ERROR_OCCURED());
	}

	public static void showLocationMessage() {
		showError(LocaleContent.getCHECK_THE_SAVE_LOCATION_AND_TRY_AGAIN());
	}
	public static void showInvalidUrl() {
		showError(LocaleContent.getINVALID_URL());
	}

	public static void checkChromeDriver() {
		showError(LocaleContent.getBROWSER_IS_TERMINATED_OR_NOT_INVOKED_PLEASE_TRY_AGAIN());
	}

	public static void locationSavedMessage(boolean isAbnormal) {
		showMessage(LocaleContent.getSAVED_SUCCESS());
	}

	public static void setProperLocationMessage() {
		showError(LocaleContent.getSET_PROPER_LOCATION());
	}

	public static void fileIsBeingUsedMessage() {
		showError(LocaleContent.getCLOSE_THE_FILE_AND_TRY_AGAIN());
	}

	public static void toolAlreadyRunningMessage() {
		JOptionPane pane  = new JOptionPane(LocaleContent.getI_SHOT_IS_ALREADY_RUNNING());
		JDialog d = pane.createDialog(LocaleContent.getINFO());
		d.setAlwaysOnTop(true);
		int x1=AssistiveScreenshot.x-100;
		int y1=AssistiveScreenshot.y-35;
		if(AssistiveScreenshot.x<20){
			x1 = 0;
		}
		if(AssistiveScreenshot.y<20){
			y1 = 0;
		}
		if(x1<0){
			x1=0;
		}
		if(y1<0){
			y1=0;
		}
		d.setLocation(x1,y1);
		d.setVisible(true);
	}

	public static void imHereMessage() {
		JOptionPane pane  = new JOptionPane(LocaleContent.getI_M_HERE());
		JDialog d = pane.createDialog(LocaleContent.getINFO());
		d.setAlwaysOnTop(true);
		int x1=AssistiveScreenshot.x-100;
		int y1=AssistiveScreenshot.y-35;
		if(AssistiveScreenshot.x<20){
			x1 = 0;
		}
		if(AssistiveScreenshot.y<20){
			y1 = 0;
		}
		if(x1<0){
			x1=0;
		}
		if(y1<0){
			y1=0;
		}
		d.setLocation(x1,y1);
		d.setVisible(true);

	}

	public static void restartMessage() {

		JOptionPane pane  = new JOptionPane(LocaleContent.getAPP_HAS_BEEN_RESTARTED());
		JDialog d = pane.createDialog(LocaleContent.getSUCCESS());
		d.setAlwaysOnTop(true);
		int x1=AssistiveScreenshot.x-100;
		int y1=AssistiveScreenshot.y-35;
		if(AssistiveScreenshot.x<20){
			x1 = 0;
		}
		if(AssistiveScreenshot.y<20){
			y1 = 0;
		}
		if(x1<0){
			x1=0;
		}
		if(y1<0){
			y1=0;
		}
		d.setLocation(x1,y1);
		d.setVisible(true);

	}

	public static void numberLengthValidationMessage() {
		showError(LocaleContent.getINPUT_NUMBER_GREATER_THAN_OR_EQUAL_TO_0());
	}

	public static void delayTimeValidationMessage() {
		showError(LocaleContent.getDELAY_TIME_SHOULD_BE_BETWEEN_1_AND_30_SECOND_S());
	}

	public static void showError(String message) {
		JOptionPane pane  = new JOptionPane(message);
		JDialog d = pane.createDialog(LocaleContent.getERROR());
		d.setAlwaysOnTop(true);
		int x1=AssistiveScreenshot.x-100;
		int y1=AssistiveScreenshot.y-35;
		if(AssistiveScreenshot.x<20){
			x1 = 0;
		}
		if(AssistiveScreenshot.y<20){
			y1 = 0;
		}
		if(x1<0){
			x1=0;
		}
		if(y1<0){
			y1=0;
		}
		d.setLocation(x1,y1);
		d.setVisible(true);
	}

	public static void showMessage(String message) {
		JOptionPane pane  = new JOptionPane(message);
		JDialog d = pane.createDialog(LocaleContent.getSUCCESS());
		d.setAlwaysOnTop(true);
		int x1=AssistiveScreenshot.x-100;
		int y1=AssistiveScreenshot.y-35;
		if(AssistiveScreenshot.x<20){
			x1 = 0;
		}
		if(AssistiveScreenshot.y<20){
			y1 = 0;
		}
		if(x1<0){
			x1=0;
		}
		if(y1<0){
			y1=0;
		}
		d.setLocation(x1,y1);
		d.setVisible(true);
	}


	public static void numberTypeValidationMessage() {
		showError(LocaleContent.getINPUT_INTEGER_ONLY());
	}

	public static void urlValidationMessage() {
		showError(LocaleContent.getURL_SHOULD_NOT_BE_EMPTY());
	}

	public static void timeValidationMessage() {
		showError(LocaleContent.getTIME_SHOULD_NOT_BE_EMPTY());
	}

	public static void timeBlankValidationMessage() {
		showError(LocaleContent.getINPUT_ONLY_INTEGERS_IN_TIME_FIELD());
	}

	public static void previewIssueMessage() {
		showError(LocaleContent.getSORRY_ERROR_IN_SHOWING_PREVIEW_YOUR_SCREENSHOT_MIGHT_BE_SAVED());
	}

	public static void thirtySecondsMessage() {
		showMessage(LocaleContent.getTRY_TO_INPUT_WITH_30_SECONDS());
	}

	public static void classNotFoundMessage() {
		showError(LocaleContent.getCLASS_NOT_FOUND());
	}

	public static void idNotFoundMessage() {
		showError(LocaleContent.getID_NOT_FOUND());
	}

	public static void selectorClassBlankMessage() {
		showError(LocaleContent.getSELECTOR_CLASS_SHOULD_NOT_BE_EMPTY());
	}
	public static void selectorIdBlankMessage() {
		showError(LocaleContent.getSELECTOR_ID_SHOULD_NOT_BE_EMPTY());
	}

	public static void maximumValueReachedException() {
		showError(LocaleContent.getVALUE_SHOULD_BE_LESS_THAN() + Integer.MAX_VALUE+1 +".");
	}

	public static void decimalNotAllowedException() {
		showError(LocaleContent.getDECIMAL_IS_NOT_ALLOWED());
	}

	public static void valueEmptyMessage() {
		showError(LocaleContent.getSPLIT_VALUE_SHOULD_NOT_BE_EMPTY());
	}

	public static void delayValueEmptyMessage() {
		showError(LocaleContent.getDELAY_TIME_SHOULD_NOT_BE_EMPTY());
	}

	public static void log(Exception e) {
			AssistiveScreenshot.logger.log(Level.SEVERE, e.getMessage(), e);
	}
	public static boolean isInValidName(String name) {
		return name.isEmpty() || name.contains("\\") || name.contains("/") || name.contains(":") || name.contains("*") || name.contains("?")|| name.contains("\"")|| name.contains("<")|| name.contains(">")|| name.contains("|");
	}
}
