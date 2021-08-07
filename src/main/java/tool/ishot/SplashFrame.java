package tool.ishot;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;

public class SplashFrame {
	static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	public static JFrame frame;

	public SplashFrame() {
		EventQueue.invokeLater(new Runnable(){

			public void run() {
				SplashFrame.frame = new JFrame();
				SplashFrame.frame.setUndecorated(true);
				SplashFrame.frame.setBackground(new Color(255,255,255, 40));
				SplashFrame.frame.setAlwaysOnTop(true);
				SplashFrame.frame.setDefaultCloseOperation(3);
				SplashFrame.frame.setLocationRelativeTo(null);
			}
		});
	}
	
	public static void close() {
		if(service!=null){
			service.shutdown();
		}
		if(frame!=null){
			frame.dispose();
		}
	}

	public static void showFlash(Rectangle rectangle) {
		SplashFrame.frame.setBounds(rectangle);
		SplashFrame.frame.setVisible(true);
		try {
			Thread.sleep(100);
		SplashFrame.frame.setVisible(false);
		} catch (InterruptedException e) {
			Common.log(e);
			e.printStackTrace();
		}
		
	}

}
