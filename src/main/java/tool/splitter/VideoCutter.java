package tool.splitter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tool.splitter.locale.SplitterLocaleContent;

public class VideoCutter {
	public static String toolVersion = SplitterLocaleContent.VIDEO_SPLITTER;
	public static JFrame frame;

	public VideoCutter(boolean isImageSelected) {
		SplitterLocaleContent.setLocaleContent();
		frame = new JFrame();
		frame.setBounds(600, 300, 417, 365);
		JPanel jPanel = new VideoSplitterJPanel().getVideoSplitterJPanel(isImageSelected);
		frame.setContentPane(jPanel);
		frame.setAlwaysOnTop(true);
		ImageIcon frameIcon = CommonMethods.createImageIcon("videoSpliter.png");
		if (frameIcon != null) {
			frame.setIconImage(frameIcon.getImage());
		}
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle(toolVersion);
		frame.setDefaultCloseOperation(0);
		frame.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent windowEvent) {
				VideoCutter.closeVideoSplitterFrame();
			}
		});
	}

	public static void show() {
		if(frame != null){
			frame.setVisible(true);
		}
	}
	public static void closeVideoSplitterFrame() {
		System.out.println("VideoSplitter Tool Closed...");
		frame.dispose();
	}

}
