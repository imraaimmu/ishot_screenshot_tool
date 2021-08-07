package tool.ishot;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ImageMaker
{

	static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	public static File file = null;

	public static BufferedImage takeScreenshot(int delay, Rectangle captureRect, BufferedImage oldScreenShot) throws Exception
	{
		Calendar now = Calendar.getInstance();
		BufferedImage screenShot = null;
		Robot robot = new Robot();
		Rectangle monitor = getActiveMonitor();
		robot.delay(delay);
		AssistiveScreenshot.hideButton();
		if(captureRect != null){
			screenShot = robot.createScreenCapture(new Rectangle(captureRect));
		}else{
			if(PopUpDemo.allScreen.isSelected()){
				Rectangle rect = new Rectangle(ScreenCaptureRectangle.getVirtualBounds());
				screenShot = robot.createScreenCapture(rect);
				SplashFrame.showFlash(rect);
			}else{
				screenShot = robot.createScreenCapture(new Rectangle(monitor));
				SplashFrame.showFlash(new Rectangle(monitor));
			}
		}
		AssistiveScreenshot.showButton();
		if(oldScreenShot != null){
			screenShot = oldScreenShot;
		}
		boolean isShowPreview = PopUpDemo.showPreviewMenu.isSelected();

		int result = 1;
		if(isShowPreview || AssistiveScreenshot.isRectangularSnip){
			result = ScreenCaptureRectangle.showImage(screenShot);
			if(result == 0){
				saveToDisk(now, AssistiveScreenshot.currentDirectory, screenShot);
			}
		}else{
			saveToDisk(now, AssistiveScreenshot.currentDirectory, screenShot);
		}

		return screenShot;
	}
	private static void saveToDisk(Calendar now, String currentDirectory, BufferedImage screenShot)
			throws FileNotFoundException, IOException {
		try{

			String fileName = FileNameUtil.askName(null);
			if(fileName != null && !fileName.isEmpty()){
				ImageIO.write(screenShot, "JPG", new File(fileName+".jpg"));
				copyToClipboard(screenShot);
			}else{

			}
		}catch(LocationNotFoundException e){
			Common.log(e);
			Common.setProperLocationMessage();
			AssistiveScreenshot.showLocationChanger(true);
			AssistiveScreenshot.showButton();
		}
		catch(Exception e){
			Common.log(e);
			Common.setProperLocationMessage();
			AssistiveScreenshot.showLocationChanger(true);
			AssistiveScreenshot.showButton();
		}
		AssistiveScreenshot.issavepreview = 1;
	}
	public static void copyToClipboard(BufferedImage image) {
		TransferableImage trans = new TransferableImage( image );
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(trans,null);
	}

	public static BufferedImage execute(int delay, Rectangle captureRect,BufferedImage oldScreenShot) throws Exception
	{
		return takeScreenshot(delay,captureRect,oldScreenShot);
	}


	public static Rectangle getActiveMonitor() {
		Rectangle rectangle = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
		rectangle.setSize((int)rectangle.getWidth(), (int)rectangle.getHeight());
		return rectangle;
	}
	public static Rectangle getActiveMonitorForVideo() {

		Rectangle rectangle = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
		rectangle.setSize((int)rectangle.getWidth(), (int)rectangle.getHeight());
		return rectangle;
	}


	public static File startVideo(Rectangle rectangle){
		try {
			if(AssistiveScreenshot.isRectangularVideoSnip){
				file = VideoCaptureMode.startVideo(rectangle);
			}else{
				if(PopUpDemo.allScreen.isSelected()){
					file = VideoCaptureMode.startVideo(ScreenCaptureRectangle.getVirtualBounds());
				}else{
					if(AssistiveScreenshot.splitSize != 0){
						file = VideoCaptureMode.startVideo(AssistiveScreenshot.splitMonitor);
					}else{
						file = VideoCaptureMode.startVideo(getActiveMonitorForVideo());
					}
				}
			}
		} catch(LocationNotFoundException e){
			Common.log(e);
		}catch (Exception e) {
			Common.log(e);
			e.printStackTrace();
		}
		return file;
	}
	public static void stopVideo(){
		try {
			VideoCaptureMode.stopVideo();
			AssistiveScreenshot.cancelRectangularThing();
			file = null;
		} catch (Exception e) {
			Common.log(e);
			e.printStackTrace();
		}
	}
	public static void pauseVideo(){
		try {

			VideoCaptureMode.pauseVideo();
			AssistiveScreenshot.isRecording = false;
			AssistiveScreenshot.isVideoPaused = true;
		} catch (Exception e) {
			Common.log(e);
			e.printStackTrace();
		}
	}
}

class TransferableImage implements Transferable {

    Image i;

    public TransferableImage( Image i ) {
        this.i = i;
    }

    public Object getTransferData( DataFlavor flavor )
    throws UnsupportedFlavorException, IOException {
        if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
            return i;
        }
        else {
            throw new UnsupportedFlavorException( flavor );
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = new DataFlavor[ 1 ];
        flavors[ 0 ] = DataFlavor.imageFlavor;
        return flavors;
    }

    public boolean isDataFlavorSupported( DataFlavor flavor ) {
        DataFlavor[] flavors = getTransferDataFlavors();
        for ( int i = 0; i < flavors.length; i++ ) {
            if ( flavor.equals( flavors[ i ] ) ) {
                return true;
            }
        }

        return false;
    }
}