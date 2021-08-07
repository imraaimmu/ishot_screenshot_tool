package tool.ishot;

import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * 
 * @author Imran Khan M.
 */
public class Delay5Task extends TimerTask {

	static int delay = 5;
	public void run() {
		setImageToAssisitveButton();
	}

	private static void setImageToAssisitveButton() {
		if(delay == 0){
			AssistiveScreenshot.delay5Timer.cancel();
			try {
				AssistiveScreenshot.delayButton.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON))));
			} catch (IOException e) {
				Common.log(e);
				e.printStackTrace();
			};
			delay = 5;
		}else{
			try {
				AssistiveScreenshot.delayButton.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream("countdown/countdown_"+(delay--)+".png"))));
			} catch (IOException e) {
				Common.log(e);
				e.printStackTrace();
			};
			AssistiveScreenshot.frame.setVisible(false);
			AssistiveScreenshot.frame.setVisible(true);
		}
	}
}
