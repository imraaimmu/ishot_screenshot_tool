package tool.ishot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class ClickFrame  implements NativeMouseInputListener  , WindowListener {

	public static JFrame frame;
	public static boolean frameAlive = false;
	public static JLabel clickLabel = null;
	boolean isFirstTime = true;
	public static void main(){

		EventQueue.invokeLater(new Runnable(){
			public void run() {
				frameAlive = true;
				initializeClickFrame();
			}
		});

	}
	public static void initializeClickFrame() {
		if(frame == null){
			clickLabel = new JLabel();
			clickLabel.setBounds(2, 0, 50, 30);
			clickLabel.setForeground(Color.BLACK);
			clickLabel.setSize(50, 30);
			clickLabel.setFont(new Font("Roboto", Font.BOLD, 12));
			try {
				clickLabel.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.CLICK_ICON))));
			} catch (IOException e) {
				Common.log(e);
				e.printStackTrace();
			}
			frame = new JFrame("CD");
			frame.setUndecorated(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(null);
			frame.add(clickLabel);
			frame.setSize(new Dimension(55,32));
			frame.setLocationByPlatform( true );
			frame.setBackground(new Color(0, 0, 0, 0));
			frame.setAlwaysOnTop(true);
			frame.setVisible( true );
			frame.setVisible( false );
		}
		new ClickFrame().registerHook();
	}
	protected void registerHook() {
		try {
			if(!GlobalScreen.isNativeHookRegistered()){
				GlobalScreen.registerNativeHook();
				// Add the appropriate listeners.
				GlobalScreen.addNativeMouseListener(this);
				GlobalScreen.addNativeMouseMotionListener(this);

				Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
				logger.setLevel(Level.OFF);

				// Change the level for all handlers attached to the default logger.
				Handler[] handlers = Logger.getLogger("").getHandlers();
				for (int i = 0; i < handlers.length; i++) {
					handlers[i].setLevel(Level.OFF);
				}
			}
		}
		catch (NativeHookException ex) {
			Common.log(ex);
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

		}


	}
	public static void close(){
		frameAlive = false;
	}
	public static boolean isAlive(){
		return frameAlive;
	}
	public void nativeMouseClicked(NativeMouseEvent arg0) {
	}
	public void nativeMousePressed(NativeMouseEvent arg0) {

		if(ClickFrame.isAlive() && !AssistiveScreenshot.menu.isShowing()){
			Point point = MouseInfo.getPointerInfo().getLocation();
			if(!(point.x >=AssistiveScreenshot.x && point.x <= AssistiveScreenshot.x+50 && point.y >=AssistiveScreenshot.y && point.y <= AssistiveScreenshot.y+50)){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
				if(point.y < 40){
					ClickFrame.frame.setLocation(point.x,point.y + 40);
				}else{
					ClickFrame.frame.setLocation(point.x,point.y-40);
				}
				ClickFrame.frame.setVisible(true);
			}
		}
	}

	public void nativeMouseReleased(NativeMouseEvent arg0) {
		try {
			Thread.sleep(120);
			if(ClickFrame.frame != null){
				ClickFrame.frame.setVisible(false);
			}
		} catch (InterruptedException e) {
			Common.log(e);
			e.printStackTrace();
		}
	}
	public void nativeMouseDragged(NativeMouseEvent arg0) {

	}
	public void nativeMouseMoved(NativeMouseEvent arg0) {

	}
	public void windowOpened(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1) {
			Common.log(e1);
			e1.printStackTrace();
		}
	}
	public void windowClosed(WindowEvent e) {

	}
	public void windowIconified(WindowEvent e) {

	}
	public void windowDeiconified(WindowEvent e) {

	}
	public void windowActivated(WindowEvent e) {

	}
	public void windowDeactivated(WindowEvent e) {

	}
}
