package tool.ishot;

import java.math.BigInteger;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
/**
 * 
 * @author Imran Khan M.
 */
public class SplitVideo extends TimerTask {
	public void run() {
		if(AssistiveScreenshot.isRecording && AssistiveScreenshot.splitSize != 0 && ImageMaker.file != null){
			BigInteger size = FileUtils.sizeOfAsBigInteger(ImageMaker.file);
			BigInteger kb = size.divide(BigInteger.valueOf(1024));
			int split = AssistiveScreenshot.splitSize*(1020);
			if((kb.intValue() <= split) && (kb.intValue() >= (split)-30 || kb.intValue() >= (split)-50 || kb.intValue() >= (split)-100 || kb.intValue() >= (split)-200 || kb.intValue() >= (split)-250)){
				ImageMaker.stopVideo();
				ImageMaker.startVideo(AssistiveScreenshot.rectangle);
				AssistiveScreenshot.isRecording = true;
				VideoCaptureMode.firstSplit = false;
			}else if(kb.intValue() >= split){
				ImageMaker.stopVideo();
				ImageMaker.startVideo(AssistiveScreenshot.rectangle);
				AssistiveScreenshot.isRecording = true;
				VideoCaptureMode.firstSplit = false;
			}
		}
	}
}
