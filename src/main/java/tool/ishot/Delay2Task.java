package tool.ishot;

import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * Create a class extends with TimerTask
 * 
 * @author Imran Khan M.
 */
public class Delay2Task extends TimerTask {

	static int delay = 2;
	public void run() {
		setImageToAssisitveButton();
	}

	private static void setImageToAssisitveButton() {
		try {
			if(delay == 0){
				AssistiveScreenshot.delay2Timer.cancel();
				AssistiveScreenshot.delayButton.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON))));
				delay = 2;
			}else{
				AssistiveScreenshot.delayButton.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream("countdown/countdown_"+(delay--)+".png"))));;
				AssistiveScreenshot.frame.setVisible(false);
				AssistiveScreenshot.frame.setVisible(true);
			}
		} catch (IOException e2) {
			Common.log(e2);
			e2.printStackTrace();
		}
	}
}
