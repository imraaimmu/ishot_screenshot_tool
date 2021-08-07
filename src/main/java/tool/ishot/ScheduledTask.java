package tool.ishot;

import java.util.TimerTask;
/**
 * 
 * @author Imran Khan M.
 */
public class ScheduledTask extends TimerTask {

	static boolean redLight = false;
	public void run() {

		if(AssistiveScreenshot.isRecording){
			if(redLight){
				AssistiveScreenshot.stopButton.setVisible(true);
				AssistiveScreenshot.assistiveButton.setVisible(false);
				redLight = false;
			}else{
				AssistiveScreenshot.stopButton.setVisible(false);
				AssistiveScreenshot.assistiveButton.setVisible(true);
				redLight = true;
			}
		}else{
			AssistiveScreenshot.stopButton.setVisible(false);
			AssistiveScreenshot.assistiveButton.setVisible(true);
		}
	}
}
