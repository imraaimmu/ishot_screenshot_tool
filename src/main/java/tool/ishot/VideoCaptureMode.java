package tool.ishot;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

import com.teamdev.jxcapture.Codec;
import com.teamdev.jxcapture.CompressionQuality;
import com.teamdev.jxcapture.EncodingParameters;
import com.teamdev.jxcapture.InterpolationMode;
import com.teamdev.jxcapture.VideoCapture;
import com.teamdev.jxcapture.audio.AudioCodec;
import com.teamdev.jxcapture.audio.AudioEncodingParameters;
import com.teamdev.jxcapture.audio.AudioSource;
import com.teamdev.jxcapture.video.FullScreen;
import com.teamdev.jxcapture.video.VideoFormat;
import com.teamdev.jxcapture.video.win.WMVCapture;

import javafx.animation.Timeline;

public class VideoCaptureMode {
	static VideoCapture videoCapture;
	static Timeline timeLine;
	static String videoFile;
	static boolean firstSplit = false;
	static Logger logger = Logger.getLogger(VideoCaptureMode.class.getName());
	
	public static File startVideo(Rectangle defaultRectangle) throws Exception {
		File file = null;
		EncodingParameters encodingParameters = null;
		if(!AssistiveScreenshot.isVideoPaused){
		    if(AssistiveScreenshot.slash.equals("\\")) {
		        videoCapture = new WMVCapture();
		    }else {
		        videoCapture = VideoCapture.create(VideoFormat.AVI);
		    }
		       
			videoCapture.setVideoSource(new FullScreen());
			videoCapture.setCaptureTransparentWindows(false);
			videoCapture.setCaptureArea(defaultRectangle);
			List<Codec> videoCodecs = videoCapture.getVideoCodecs();
			Codec videoCodec = videoCodecs.get(0);
			String folderPath = null;
			if(PopUpDemo.userDefinedName.isSelected() && AssistiveScreenshot.splitSize > 0){
				if(firstSplit){
					AssistiveScreenshot.splitName = folderPath = FileNameUtil.askName(null);
				}else{
					String[] name = AssistiveScreenshot.splitName.substring(AssistiveScreenshot.splitName.lastIndexOf("\\")+1,AssistiveScreenshot.splitName.length()).split("_");
					String finalName = "";
					for(int i=0;i<name.length-1;i++){
						finalName += name[i]+"_";
					}
					folderPath = AssistiveScreenshot.splitName.substring(0, AssistiveScreenshot.splitName.lastIndexOf("\\")+1)+finalName+ImageMaker.formatter.format(Calendar.getInstance().getTime());
				}
			}else{
				folderPath = FileNameUtil.askName(null);
			}
			if(folderPath != null && folderPath.isEmpty()){
			}
			else if(folderPath != null && !folderPath.isEmpty() && new File(folderPath.substring(0, folderPath.lastIndexOf(AssistiveScreenshot.slash)+1)).exists()){
			    if(AssistiveScreenshot.slash.equals("\\")) {
			        videoFile = folderPath + ".wmv";
			    }else {
			        videoFile = folderPath + ".avi";
			    }
				file = new File(videoFile);
				if (file.exists()) {
					FileUtils.forceDelete(file);
				}
				encodingParameters = new EncodingParameters(file);
				setAudio(encodingParameters);
				encodingParameters.setCodec(videoCodec);
				if(AssistiveScreenshot.videoQuallity == 2){
					encodingParameters.setBitrate(1000000);
					encodingParameters.setFramerate(24);
					encodingParameters.setCodec(videoCodec);
					encodingParameters.setCompressionQuality(CompressionQuality.BEST);
					encodingParameters.setInterpolationMode(InterpolationMode.HighQuality);
					if(PopUpDemo.allScreen.isSelected()){
						encodingParameters.setSize(new Dimension((int)(defaultRectangle.getWidth() + defaultRectangle.getWidth() % 2.0), (int)(defaultRectangle.getHeight() + defaultRectangle.getHeight() % 2.0)));	
					}else{
						if ((int)defaultRectangle.getWidth() < 1500) {
							encodingParameters.setSize(new Dimension((int)defaultRectangle.getWidth(), (int)defaultRectangle.getHeight()));
						} else {
							encodingParameters.setSize(new Dimension((int)(defaultRectangle.getWidth() + defaultRectangle.getWidth() % 2.0), (int)(defaultRectangle.getHeight() + defaultRectangle.getHeight() % 2.0)));
						}
					}
				}else if(AssistiveScreenshot.videoQuallity == 3){
					encodingParameters.setBitrate(8000000);
					encodingParameters.setCompressionQuality(CompressionQuality.LEAST);
					encodingParameters.setFramerate(30);
					encodingParameters.setSize(new Dimension((int)(defaultRectangle.getWidth() + defaultRectangle.getWidth() % 2.0), (int)(defaultRectangle.getHeight() + defaultRectangle.getHeight() % 2.0)));
					encodingParameters.setInterpolationMode(InterpolationMode.HighQuality);
				}
			}else{
				file= null;
			}
		}else if(AssistiveScreenshot.isVideoPaused && ImageMaker.file != null){
			file = ImageMaker.file;        	
			encodingParameters = new EncodingParameters(file);
		}
		if(file != null){
			if(ScreenCaptureRectangle.selectionPane != null){
				SwingUtilities.getWindowAncestor(ScreenCaptureRectangle.selectionPane).dispose();
			}
			if(videoCapture.isStarted()){
				if(AssistiveScreenshot.isRectangularVideoSnip){
					new RectangleOutline(RectangleOutline.x, RectangleOutline.y, RectangleOutline.width, RectangleOutline.height);
				}
				videoCapture.start();
			}else{
				if(PopUpDemo.showClicks.isSelected()){
					ClickFrame.main();
				}
				videoCapture.start(encodingParameters);
			}
			AssistiveScreenshot.isVideoPaused = false;
		}
		return file;
	}

	private static void setAudio(EncodingParameters encodingParameters) {
		if (PopUpDemo.recordAudio.isSelected()) {
            logger.info("Available audio recording sources:");
            List<AudioSource> audioSources = AudioSource.getAvailable();
            for (AudioSource audioSource : audioSources) {
            	logger.info("audioSource = " + audioSource);
            }
            if (audioSources.isEmpty()) {
                logger.log(Level.SEVERE,"No audio sources available");
            } else {
                AudioSource audioSource = audioSources.get(0);
                logger.info("Selected audio source = " + audioSource);
                videoCapture.setAudioSource(audioSource);

                List<AudioCodec> audioCodecs = videoCapture.getAudioCodecs();
                if (audioSources.isEmpty()) {
                	logger.log(Level.SEVERE,"No audio codecs available");
                } else {
                	logger.info("Available audio codecs:");
                    for (AudioCodec audioCodec : audioCodecs) {
                    	logger.info("audioCodec = " + audioCodec);
                    }

                    // Enable and configure audio encoding
                    AudioEncodingParameters audioEncoding = new AudioEncodingParameters();

                    AudioCodec audioCodec = audioCodecs.get(0);
                    logger.info("Selected audio codec = " + audioCodec);
                    audioEncoding.setCodec(audioCodec);

                    encodingParameters.setAudioEncoding(audioEncoding);
                }
            }
        }
	}

	public static String stopVideo() throws IOException {
		if(videoCapture != null && videoCapture.isStarted()){
			PopUpDemo.allScreen.setEnabled(true);
			PopUpDemo.saveAsMenu.setEnabled(true);
			RectangleOutline.close();
			InvisibleFrame.close();
			AssistiveScreenshot.isVideoPaused = false;
			AssistiveScreenshot.rectangle = null;
			AssistiveScreenshot.isRectangularVideoSnip = false;
			PopUpDemo.snipVideo.setEnabled(true);
			PopUpDemo.recorderOptionsMenu.setEnabled(true);
			PopUpDemo.location.setEnabled(true);
			videoCapture.stop();
			ClickFrame.close();
			firstSplit = false;
			if(ClickFrame.frame != null){
				ClickFrame.frame.setVisible(false);
			}
//			try {
//				GlobalScreen.unregisterNativeHook();
//			} catch (NativeHookException e1) {
//				Common.log(e1);
//				e1.printStackTrace();
//			}
		}
		return videoFile;
	}
	public static String pauseVideo() throws IOException {
		RectangleOutline.close();
		AssistiveScreenshot.isVideoPaused = true;
		videoCapture.pause();
		return videoFile;
	}
}

