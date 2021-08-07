package tool.ishot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputAdapter;

import javafx.application.Platform;
import tool.enhancer.JavaFxToolLayoutWithShapes;
import tool.splitter.CommonMethods;
import tool.splitter.VideoCutter;
import tool.splitter.VideoSplitterJPanel;
import tool.splitter.locale.SplitterLocaleContent;



public class AssistiveScreenshot extends MouseInputAdapter 
{
	private Component parent;
	private Component destination;
	private Point pressed;
	static JButton assistiveButton;
	static JButton delayButton;
	static JButton stopButton;
	static boolean mousePresse = false;
	static boolean mouseDragging= false;
	static JFrame frame; 
	static JFileChooser jfc = null;
	public static String currentDirectory = null;
	public static String slash = null;
	public static String os = null;
	static volatile boolean isRecording = false;
	static Timer timer = new Timer();
	static Timer splitTimer = new Timer();
	static Timer delay2Timer = new Timer();
	static Timer delay5Timer = new Timer();
	static Timer delayCustomTimer = new Timer();
	public static boolean isAdvanceFullScreenShot = false;
	protected static boolean isFullScreenShot = false;
	protected static int splitSize = 0;
	protected static int customDelayTime = 0;
	public static boolean isVideoPaused = false;
	public static int videoQuallity = 2;
	static PopUpDemo menu = null; 
	static Properties prop = new Properties();
	public static boolean isRectangularSnip = false;
	protected static boolean isRectangularVideoSnip = false;
	public static Rectangle rectangle = null;
	public static Rectangle splitMonitor = null;
	public static String splitName = null;
	protected static int issavepreview = 0;
	public static Integer fstimedelay = 0;
	static Date date = new Date();
	static File lockFile = null;
	static RandomAccessFile randomAccessFile = null;
	static FileLock fileLock = null;
	static String tempFolder = System.getProperty("java.io.tmpdir");
	public static int x = 0;
	public static int y = 0;
	static TrayIcon trayIcon;
	static SystemTray tray;
	static boolean cancelClickedInAskName;
	protected static boolean dragDelay;
	static ActionListener mainActionListener;
	static Logger logger = Logger.getLogger(AssistiveScreenshot.class.getName());
	static FileHandler fh; 
	AssistiveScreenshot() {}
	{
	}

	AssistiveScreenshot(Component parent)
	{
		this.parent = parent;
	}

	@Override
	public void mousePressed(MouseEvent me)
	{
		destination = (parent == null) ? me.getComponent() : parent;
		pressed = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), destination);
		mousePresse = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public static void showLocationChanger(boolean isAbnormal) {
		String folderPath = "";
		try {
			folderPath = FileNameUtil.getFolderPath("", PopUpDemo.image.isSelected());
		} catch (LocationNotFoundException e) {
			Common.log(e);
		}catch(IOException e){
			Common.log(e);
		}

		jfc = new JFileChooser(new File(folderPath));
		jfc.setDialogTitle(LocaleContent.getFILE_CHOOSER_TITLE());
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if(!file.exists()){
				file.mkdirs();
			}
			String filePathtoStored = file.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\") + slash+slash;
			AssistiveScreenshot.prop.put("currentlocation", filePathtoStored);
			Common.locationSavedMessage(isAbnormal);
		}
	}

	public static void storeTofile(String key,String value,String comment) {
		try {
			prop.put(key, value);
			prop.store(new FileWriter(AssistiveScreenshot.currentDirectory+"properties"+AssistiveScreenshot.slash+"settings.properties"),comment);
		} catch (IOException e1) {
			Common.log(e1);
			e1.printStackTrace();
		}
	}

	public static void deleteFromfile(String key,String value,String comment) {
		try {
			prop.remove(key, value);
			prop.store(new FileWriter(AssistiveScreenshot.currentDirectory+"properties"+AssistiveScreenshot.slash+"settings.properties"),comment);
		} catch (IOException e1) {
			Common.log(e1);
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseDragged(MouseEvent me)
	{
		if(!AssistiveScreenshot.dragDelay){
			Point location = destination.getLocation();
			Point drag = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), destination);
			x = (int)(location.x - pressed.getX() + drag.getX());
			y = (int)(location.y - pressed.getY() + drag.getY());
			destination.setLocation(x, y);
			mousePresse = true;
			mouseDragging = true;
		}
		AssistiveScreenshot.dragDelay = false;
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
		mouseDragging = false;
	}

	private static void userInterface() throws IOException
	{
		setNimbusUI();
		initializeSettingsFile();
		initializeLogger();

		assistiveButton = new JButton("Screenshot");
		assistiveButton.setPreferredSize(new Dimension(54,50));
		assistiveButton.setSize(assistiveButton.getPreferredSize());
		assistiveButton.setBackground(Color.black);
		assistiveButton.setMargin(new Insets(1, 13, 0, 0));
		assistiveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		assistiveButton.setFocusPainted(false);
		assistiveButton.setBorderPainted(false);

		delayButton = new JButton("delay");
		delayButton.setPreferredSize(new Dimension(54,50));
		delayButton.setSize(delayButton.getPreferredSize());
		delayButton.setBackground(Color.black);
		delayButton.setMargin(new Insets(1, 13, 0, 0));
		delayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		delayButton.setFocusPainted(false);
		delayButton.setBorderPainted(false);
		delayButton.setVisible(false);

		stopButton = new JButton("Stop");
		stopButton.setVisible(false);
		stopButton.setPreferredSize(new Dimension(54,50));
		stopButton.setSize(stopButton.getPreferredSize());
		stopButton.setBackground(Color.black);
		stopButton.setMargin(new Insets(1, 13, 0, 0));
		stopButton.setFocusPainted(false);
		stopButton.setBorderPainted(false);
		setImageToStopButton();
		stopButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		stopButton.setIcon(new ImageIcon(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.STOP_ICON))));

		frame = new JFrame(LocaleContent.getI_SHOT_V1());
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setShape(new RoundRectangle2D.Double(0, 0, 54,50,7.5,7.5));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.add(assistiveButton);
		frame.add(stopButton);
		frame.add(delayButton);
		frame.setSize(new Dimension(54,50));
		frame.setAlwaysOnTop(true);
		frame.setIconImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON)));
		frame.setResizable(false);
		Handler h = new Handler();
		frame.addWindowStateListener(h);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
	            e.getWindow().dispose();
	            System.exit(0);
		    }
		});
		setImageToTimeStartButton(false);
		if(AssistiveScreenshot.prop.get("lastlocation") != null){
			String[] location = AssistiveScreenshot.prop.get("lastlocation").toString().split(",");
			frame.setLocation(Integer.parseInt(location[0]),Integer.parseInt(location[1]));
			AssistiveScreenshot.x = Integer.parseInt(location[0]);
			AssistiveScreenshot.y = Integer.parseInt(location[1]);
		}else{
			frame.setLocation(50,50);
			AssistiveScreenshot.x = 50;
			AssistiveScreenshot.y = 50;
		}
		frame.setVisible( true );
		menu = new PopUpDemo();
		assistiveButton.setComponentPopupMenu(menu);

		AssistiveScreenshot bcm1 = new AssistiveScreenshot(frame);
		assistiveButton.addMouseListener(bcm1);
		assistiveButton.addMouseMotionListener(bcm1);
		stopButton.addMouseListener(bcm1);
		stopButton.addMouseMotionListener(bcm1);
		stopButton.setComponentPopupMenu(menu);


		mainActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!mousePresse && !mouseDragging){
						clickAssistiveButton(menu);
					}
				} catch(LocationNotFoundException e2){
					Common.log(e2);
					Common.setProperLocationMessage();
					AssistiveScreenshot.showLocationChanger(true);
					AssistiveScreenshot.showButton();
				}catch(java.io.IOException ie){
					Common.log(ie);
					Common.setProperLocationMessage();
					AssistiveScreenshot.showLocationChanger(true);
					AssistiveScreenshot.showButton();
				}
				catch (Exception e1) {
					Common.log(e1);
					Common.showUnfortunateMessage();
					showButton();
				}
			}
		};
		assistiveButton.addActionListener(mainActionListener);
		stopButton.addActionListener(mainActionListener);
		new SplashFrame();
	}

	public static void initializeSettingsFile() throws IOException, FileNotFoundException {
		File appFile = new File(currentDirectory+"properties"+slash+"settings.properties");
		if(!new File(currentDirectory+"properties").exists()){
			new File(currentDirectory+"properties").mkdirs();
		}
		if(!appFile.exists()){
			appFile.createNewFile();
		}
		FileReader fr = new FileReader(appFile);
		prop.load(fr);
	}

	public static void initializeLogger() throws IOException {
		if(!new File(currentDirectory+"properties"+slash+"log").exists()){
			new File(currentDirectory+"properties"+slash+"log").mkdirs();
		}
		fh = new FileHandler(currentDirectory+"properties"+slash+"log"+slash+"iShot-error-log.log");  
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);
	}

	private static void addShortcutToButton(final PopUpDemo menu) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(e.toString().contains("KEY_PRESSED")){
					if((isRecording || PopUpDemo.resume.isVisible()) && e.getKeyCode() == KeyEvent.VK_SPACE && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
						try {
							ImageMaker.execute(0, null, null);
						} catch (Exception e1) {
							Common.log(e1);
							e1.printStackTrace();
						}
					}
					else if(PopUpDemo.pause.isVisible() && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_P){
						PopUpDemo.pauseVideo();
					}
					else if(PopUpDemo.resume.isVisible() && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_R){
						PopUpDemo.resumeVideo();
					}
					else if((e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_O){
						PopUpDemo.openSavedLocation();
					}
					else if((e.getModifiers() & KeyEvent.CTRL_MASK) == 0 && e.getKeyCode() == KeyEvent.VK_SPACE){
						try {
							clickAssistiveButton(menu);
						} catch (FileNotFoundException e1) {
							Common.log(e1);
							e1.printStackTrace();
						} catch (AWTException e1) {
							Common.log(e1);
							e1.printStackTrace();
						} catch (IOException e1) {
							Common.log(e1);
							e1.printStackTrace();
						} catch (Exception e1) {
							Common.log(e1);
							e1.printStackTrace();
						}
					}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
						if(ScreenCaptureRectangle.selectionPane != null){
							SwingUtilities.getWindowAncestor(ScreenCaptureRectangle.selectionPane).dispose();
							AssistiveScreenshot.showButton();
							ScreenCaptureRectangle.frame.dispose();
							cancelRectangularThing();
						}
					}
				}
				return false;
			}

		});
	}

	public static void cancelRectangularThing() {
		AssistiveScreenshot.isRectangularVideoSnip = false;
		AssistiveScreenshot.isRectangularSnip = false;
		if(PopUpDemo.image.isSelected()){
			PopUpDemo.rectangleSnip.setEnabled(true);
		}
		if(PopUpDemo.video.isSelected()){
			PopUpDemo.snipVideo.setEnabled(true);
		}
	}

	private static void setImageToStopButton() {
		Image img = null;
		try {
			img = ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.STOP_ICON));
			stopButton.setIcon(new ImageIcon(img));
			stopButton.setToolTipText(LocaleContent.getSCREEN_RECORDER_MODE());
		} catch (IOException e2) {
			Common.log(e2);
			e2.printStackTrace();
		}
	}

	public static Image setImageToVideoStartButton(boolean isInitial) {
		Image img = null;
		try {
			img = ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.VIDEO_START_ICON));
			frame.setIconImage(img);
			assistiveButton.setIcon(new ImageIcon(img));
			assistiveButton.setToolTipText(LocaleContent.getSCREEN_RECORDER_MODE());
		} catch (IOException e2) {
			Common.log(e2);
			e2.printStackTrace();
		}
		return null;
	}

	public static void setImageToAssisitveButton(boolean isInitial) {
		try {
			BufferedImage img = ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON));
			frame.setIconImage(img);
			assistiveButton.setIcon(new ImageIcon(img));
			assistiveButton.setToolTipText(LocaleContent.getSCREENSHOT_MODE());
		} catch (IOException e2) {
			Common.log(e2);
			e2.printStackTrace();
		}
	}

	public static void setImageToTimeStartButton(boolean isInitial) {
		try {
			BufferedImage img = ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON));
			frame.setIconImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON)));
			delayButton.setIcon(new ImageIcon(img));
		} catch (IOException e2) {
			Common.log(e2);
			e2.printStackTrace();
		}
	}

	private static void setNimbusUI() {
		UIManager.LookAndFeelInfo[] arrlookAndFeelInfo = UIManager.getInstalledLookAndFeels();
		int n = arrlookAndFeelInfo.length;
		int n2 = 0;
		while (n2 < n) {
			UIManager.LookAndFeelInfo info = arrlookAndFeelInfo[n2];
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e1) {
					Common.log(e1);
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					Common.log(e1);
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					Common.log(e1);
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
				break;
			}
			++n2;
		}
	}

	public static  void clickAssistiveButton(final PopUpDemo menu)
			throws AWTException, FileNotFoundException, IOException, Exception {
		if(PopUpDemo.image.isSelected()){
			AssistiveScreenshot.isRectangularSnip = false;
			if(PopUpDemo.documentMode.isSelected()){
				createTestcaseDoc(0,null);
			}else{
				ImageMaker.execute(0,null,null);
			}
		}else if(!isRecording && !AssistiveScreenshot.isVideoPaused){
			AssistiveScreenshot.isRectangularVideoSnip = false;
			startVideoRecording(null);
		}else if(isRecording || AssistiveScreenshot.isVideoPaused){
			AssistiveScreenshot.isRectangularVideoSnip = false;
			stopVideo();
		}
	}

	public static void stopVideo() {
		PopUpDemo.pause.setVisible(false);
		PopUpDemo.resume.setVisible(false);
		PopUpDemo.stop.setVisible(false);
		PopUpDemo.reset.setEnabled(true);
		PopUpDemo.reportIssue.setEnabled(true);
		isRecording = false;
		ImageMaker.stopVideo();
		timer.cancel();
		PopUpDemo.image.setEnabled(true);
		PopUpDemo.videoQualityMenu.setEnabled(true);
		PopUpDemo.splitVideo.setEnabled(true);
		stopButton.setVisible(false);
		assistiveButton.setVisible(true);
		PopUpDemo.takeScreenshot.setVisible(false);
		AssistiveScreenshot.isVideoPaused = false;
		AssistiveScreenshot.cancelRectangularThing();
		frame.setVisible(false);
		frame.setVisible(true);
		frame.setOpacity(1.0f);
		timer = new Timer();
		splitTimer = new Timer();
	}

	public static boolean startVideoRecording(Rectangle rect) {
		ScheduledTask st = new ScheduledTask();
		SplitVideo size= new SplitVideo();
		timer.scheduleAtFixedRate(st,AssistiveScreenshot.date, 500);
		if(AssistiveScreenshot.splitSize != 0){
			VideoCaptureMode.firstSplit = true;
			splitTimer.scheduleAtFixedRate(size, AssistiveScreenshot.date, 1);
			AssistiveScreenshot.splitMonitor = ImageMaker.getActiveMonitorForVideo();
		}
		if(ImageMaker.startVideo(rect) == null){
			RectangleOutline.close();
			InvisibleFrame.close();
			AssistiveScreenshot.isRectangularVideoSnip = false;
			if(ScreenCaptureRectangle.selectionPane != null){
				SwingUtilities.getWindowAncestor(ScreenCaptureRectangle.selectionPane).dispose();
			}
			if(!AssistiveScreenshot.cancelClickedInAskName){
				Common.setProperLocationMessage();
				AssistiveScreenshot.showLocationChanger(true);
			}
			AssistiveScreenshot.cancelClickedInAskName = false;
			timer.cancel();
			timer = new Timer();
			splitTimer.cancel();
			splitTimer = new Timer();
			AssistiveScreenshot.splitMonitor = null;
			showButton();
			return false;
		}else{
			PopUpDemo.pause.setVisible(true);
			PopUpDemo.resume.setVisible(false);
			PopUpDemo.stop.setVisible(true);
			frame.setOpacity(0.2f);
			isRecording = true;
			PopUpDemo.image.setEnabled(false);
			PopUpDemo.videoQualityMenu.setEnabled(false);
			PopUpDemo.splitVideo.setEnabled(false);
			PopUpDemo.takeScreenshot.setVisible(true);
			PopUpDemo.snipVideo.setEnabled(false);
			PopUpDemo.allScreen.setEnabled(false);
			PopUpDemo.saveAsMenu.setEnabled(false);
			PopUpDemo.recorderOptionsMenu.setEnabled(false);
			PopUpDemo.location.setEnabled(false);
			PopUpDemo.reset.setEnabled(false);
			PopUpDemo.reportIssue.setEnabled(false);
			return true;
		}
	}


	public static void createTestcaseDoc(int delay, BufferedImage bufferedImage) throws AWTException, FileNotFoundException, IOException {
		Robot robot = new Robot();
		Rectangle monitor = ImageMaker.getActiveMonitor();
		robot.delay(delay);
		BufferedImage image = null;
		if(bufferedImage == null){
			AssistiveScreenshot.hideButton();
			if(PopUpDemo.allScreen.isSelected()){
				Rectangle rect = new Rectangle(ScreenCaptureRectangle.getVirtualBounds());
				image = robot.createScreenCapture(rect);
				SplashFrame.showFlash(rect);
			}else{
				image = robot.createScreenCapture(new Rectangle(monitor));
				SplashFrame.showFlash(new Rectangle(monitor));
			}
			AssistiveScreenshot.showButton();
		}else{
			image = bufferedImage;
		}
		int result = 1;
		if(PopUpDemo.showPreviewMenu.isSelected() || isRectangularSnip){
			result = ScreenCaptureRectangle.showImage(image);
			if(result == 0){
				startOdsProcess(image);
			}
		}else{
			startOdsProcess(image);
		}
	}

	public static void startOdsProcess(BufferedImage image) throws FileNotFoundException, IOException {
		try{
			File file;
			String fileName = FileNameUtil.askName(null);
			if (fileName != null && !fileName.isEmpty() && !(file = new File(fileName + ".png")).exists()) {
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ImageIO.write((RenderedImage)image, "png", fileOutputStream);
				fileOutputStream.close();
				if (new File(fileName + ".ods").exists()) {
					if(!DocumentMode.addImage(fileName)){
						Common.fileIsBeingUsedMessage();
					}
				}else {
					DocumentMode.newExcel(fileName, 1);
				}
				ImageMaker.copyToClipboard(image);
			}
		}catch(LocationNotFoundException e){
			Common.log(e);
			Common.setProperLocationMessage();
			AssistiveScreenshot.showLocationChanger(true);
		}finally{
			showButton();
		}
	}

	public static void showButton() {
		if(isRecording || PopUpDemo.resume.isVisible()){
			frame.setOpacity(0.2f);
		}else{
			frame.setOpacity(1.0f);
		}
	}
	public static void hideButton() {
		frame.setOpacity(0.0f);
		menu.setVisible(false); 
	}
	
	    
	    public static String detectOs(String OS) {
	        if ((OS.indexOf("win") >= 0)) {
	            return "windows";
	        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
	            return "linux";
	        } else {
	            return "no-support";
	        }
	    }

	public static void main(String[] args) throws Exception
	{
	    String OS = System.getProperty("os.name").toLowerCase();
	    os = detectOs(OS);
	    if(os.equals("windows")) {
	        slash = "\\";
	        currentDirectory = System.getProperty("user.dir").replaceAll("\\\\", "\\\\\\\\") + slash + slash;
	    }else if(os.equals("linux")) {
	        slash = "/";
	        currentDirectory = System.getProperty("user.dir")+ slash;
	    }
		if (lock()){
	    
			LocaleContent.setLocale();
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						userInterface();
					} catch (Exception e) {
						Common.log(e);
						e.printStackTrace();
					}
				}
			};
			EventQueue.invokeLater(runnable);
		}else{
		    LocaleContent.iShotAlreadyRunning();
		    frame = new JFrame(LocaleContent.getI_SHOT_V1());
		    setNimbusUI();
		    Rectangle monitor = ImageMaker.getActiveMonitor();
		    AssistiveScreenshot.x = monitor.width/2 + 50;
		    AssistiveScreenshot.y = monitor.height/2;
		    Common.toolAlreadyRunningMessage();
		    System.exit(0);
		}
	}

	public static String openSavedLocation() throws IOException {
		String folderPath = "";
		if(AssistiveScreenshot.prop.get("currentlocation") != null && !String.valueOf(AssistiveScreenshot.prop.get("currentlocation")).isEmpty()){
			folderPath = String.valueOf(AssistiveScreenshot.prop.get("currentlocation"));
		}
		if(folderPath.equals("")){
			if(PopUpDemo.image.isSelected()){
				folderPath = currentDirectory + "screenshots"+slash+slash;
			}else{
				folderPath = currentDirectory + "videos"+slash+slash;
			}
			File file = new File(folderPath);
			if(!file.exists()){
				file.mkdir();
			}
		}
		return folderPath;
	}
	static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
	public static void removeTrayIcon() {
		if(tray != null && trayIcon != null){
			tray.remove(trayIcon);
		}
	}
	public static void createTrayIcon() {
		BufferedImage image = null;
		if(trayIcon != null){
			if(tray != null){
				try {
					tray.remove(trayIcon);
					try {
						if(PopUpDemo.image.isSelected()){
							image = AssistiveScreenshot.resizeImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON)), 2, 20,20);
						}else{
							image = AssistiveScreenshot.resizeImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.VIDEO_START_ICON)), 2, 20,20);
						}
					} catch (IOException e1) {
						Common.log(e1);
						e1.printStackTrace();
					}
					trayIcon.setImage(image);
					tray.add(trayIcon);
				} catch (AWTException e) {
					Common.log(e);
					e.printStackTrace();
				}
			}
		}else{
			if (!SystemTray.isSupported()) {
				return;
			}
			tray = SystemTray.getSystemTray();
			try {
				if(PopUpDemo.image.isSelected()){
					image = AssistiveScreenshot.resizeImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON)), 2, 20,20);
				}else{
					image = AssistiveScreenshot.resizeImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.VIDEO_START_ICON)), 2, 20,20);
				}
			} catch (IOException e1) {
				Common.log(e1);
				e1.printStackTrace();
			}
			ActionListener exitListener=new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(AssistiveScreenshot.isRecording){
						ImageMaker.stopVideo();
						AssistiveScreenshot.timer.cancel();					
					}
					AssistiveScreenshot.frame.dispose();
					System.exit(0);
				}
			};
			ActionListener findMeListener=new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Common.imHereMessage();
					AssistiveScreenshot.showButton();
					frame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
				}
			};
			PopupMenu popup=new PopupMenu();
			MenuItem defaultItem=new MenuItem(LocaleContent.getEXIT());
			MenuItem findMe=new MenuItem(LocaleContent.getFIND_ME());
			defaultItem.addActionListener(exitListener);
			findMe.addActionListener(findMeListener);
			popup.add(defaultItem);
			popup.add(findMe);
			trayIcon = new TrayIcon(image, LocaleContent.getI_SHOT_V1(), popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)){
						AssistiveScreenshot.showButton();
						frame.setExtendedState(JFrame.NORMAL);
						TrayIcon[] trayIcons = tray.getTrayIcons();
						for(TrayIcon icon : trayIcons){
							if(icon.getToolTip().equals(LocaleContent.getI_SHOT_V1())){
								tray.remove(icon);
							}
						}
					}
				}

			});
			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AssistiveScreenshot.showButton();
					frame.setExtendedState(JFrame.NORMAL);
					TrayIcon[] trayIcons = tray.getTrayIcons();
					for(TrayIcon icon : trayIcons){
						if(icon.getToolTip().equals(LocaleContent.getI_SHOT_V1())){
							tray.remove(icon);
						}
					}
				}
			});

			try {
				TrayIcon[] trayIcons = tray.getTrayIcons();
				for(TrayIcon icon : trayIcons){
					if(icon.getToolTip().equals(LocaleContent.getI_SHOT_V1())){
						tray.remove(icon);
					}
				}
				tray.add(trayIcon);

				frame.setExtendedState(JFrame.ICONIFIED);
			} catch (AWTException e1) {
				Common.log(e1);
				e1.printStackTrace();
			}
		}
	}

	private static boolean lock() {
		try {
			lockFile = new File(tempFolder+slash+"iShot.lock");
			randomAccessFile = new RandomAccessFile(lockFile, "rw");
			fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						try {
							releaseLock();
						} catch (Exception e) {
							Common.log(e);
						}
					}

				});
				return true;
			}
		} catch (Exception e) {
			Common.log(e);
		}
		return false;
	}    
	public static void releaseLock() throws IOException {
		if(fileLock != null)
			fileLock.release();
		if(randomAccessFile != null)
			randomAccessFile.close();
		if(lockFile != null)
			lockFile.delete();
	}
	public static String askName(){
		String name = JOptionPane.showInputDialog(AssistiveScreenshot.frame, LocaleContent.getFILE_NAME());
		return name;
	}
	public static void ffmpegNotFoundException() {
		JOptionPane.showMessageDialog(VideoCutter.frame != null ? VideoCutter.frame : AssistiveScreenshot.frame, SplitterLocaleContent.FFMPEG_NOT_FOUND_1, LocaleContent.getERROR(), 2);
		AssistiveScreenshot.logger.info(SplitterLocaleContent.FFMPEG_NOT_FOUND_2+" "+"'"+AssistiveScreenshot.currentDirectory+"properties\\\\' "+SplitterLocaleContent.FFMPEG_NOT_FOUND_3+"\n"
				+ SplitterLocaleContent.FFMPEG_NOT_FOUND_4);
		try {
			Desktop.getDesktop().open(new File(currentDirectory+"properties"+AssistiveScreenshot.slash+"log"+AssistiveScreenshot.slash));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
@SuppressWarnings("serial")
class PopUpDemo extends JPopupMenu {
	
	
	public static JMenuItem pause;
	public static JMenuItem resume;
	public static JMenuItem stop;
	static JMenuItem savedLocation;
	public static JMenuItem location;
    public static JMenuItem image;
    public static JMenuItem video;
    public static JMenuItem snipVideo;
    final JMenu delayOptionsMenu;
    JMenuItem delay2sec;
    JMenuItem delay5sec;
    JMenuItem delayCustomSec;
    public static JCheckBoxMenuItem documentMode;
    JMenuItem fullScreenshot;
    JMenuItem advanceFullScreenshot;
    JMenuItem dnd;
    public static JMenuItem reportIssue;
    JMenuItem exit;
    public static JMenuItem rectangleSnip;
    public static JMenuItem takeScreenshot;
    public static JMenuItem splitVideo;
    public static JMenu videoQualityMenu;
    public static JCheckBoxMenuItem videoQualityNormal;
    public static JCheckBoxMenuItem videoQualityHd;
    public static JCheckBoxMenuItem showClicks;
    public static JCheckBoxMenuItem showPreviewMenu;
    public static JCheckBoxMenuItem allScreen;
    public static JMenuItem reset;
    public static JMenu recorderOptionsMenu;
    public static JMenu saveAsMenu;
    public static JCheckBoxMenuItem defaultName;
    public static JCheckBoxMenuItem userDefinedName;
    final JMenu screenshotOptionsMenu;
    public static JMenuItem videoSplitter;
    public static JMenuItem videoEnhancer;
    
    
    public PopUpDemo() {
    	if(AssistiveScreenshot.prop.get("splitSize") != null){
    		AssistiveScreenshot.splitSize = Integer.valueOf(AssistiveScreenshot.prop.get("splitSize").toString());
    	}
    	videoQualityNormal =new JCheckBoxMenuItem(LocaleContent.getNORMAL(),false);
    	videoQualityHd =new JCheckBoxMenuItem(LocaleContent.getHD(),false);
    	if(AssistiveScreenshot.prop.get("quality") != null){
    		if(AssistiveScreenshot.prop.get("quality").toString().equals(LocaleContent.getNORMAL())){
    			videoQualityNormal =new JCheckBoxMenuItem(LocaleContent.getNORMAL(),true);
    			AssistiveScreenshot.videoQuallity = 2;
    		}
    		if(AssistiveScreenshot.prop.get("quality").toString().equals(LocaleContent.getHD())){
    			videoQualityHd =new JCheckBoxMenuItem(LocaleContent.getHD(),true);
    			AssistiveScreenshot.videoQuallity = 3;
    		}
    	}else{
    		AssistiveScreenshot.videoQuallity = 2;
        	videoQualityNormal =new JCheckBoxMenuItem(LocaleContent.getNORMAL(),true);
        	videoQualityHd =new JCheckBoxMenuItem(LocaleContent.getHD(),false);
    	}
    	
    	pause = new JMenuItem(LocaleContent.getPAUSE2());
    	pause.setVisible(false);
    	resume= new JMenuItem(LocaleContent.getRESUME2());
    	resume.setVisible(false);
    	stop= new JMenuItem(LocaleContent.getSTOP2());
    	stop.setVisible(false);
    	
    	savedLocation = new JMenuItem(LocaleContent.getOPEN_SAVED_FOLDER());
    	location = new JMenuItem(LocaleContent.getCHANGE_SAVE_LOCATION());
    	screenshotOptionsMenu = new JMenu(LocaleContent.getOPTIONS());
    	delayOptionsMenu = new JMenu(LocaleContent.getDELAY_OPTIONS());
    	
    	snipVideo= new JMenuItem(LocaleContent.getRECTANGULAR_VIDEO());
    	snipVideo.setEnabled(false);
    	
    	exit= new JMenuItem(LocaleContent.getEXIT());
    	videoSplitter= new JMenuItem(LocaleContent.getVIDEO_SPLITTER());
    	videoEnhancer= new JMenuItem(LocaleContent.getVIDEO_ENHANCER());
    	delay2sec= new JMenuItem(LocaleContent.getDELAY_2_SEC());
    	delay5sec= new JMenuItem(LocaleContent.getDELAY_5_SEC());
    	delayCustomSec= new JMenuItem(LocaleContent.getDELAY_CUSTOM());
    	fullScreenshot= new JMenuItem(LocaleContent.getFULL_SCREENSHOT());
    	advanceFullScreenshot= new JMenuItem(LocaleContent.getID_CLASS_SCREENSHOT());
    	dnd= new JMenuItem(LocaleContent.getGO_TO_TRAY());
    	rectangleSnip = new JMenuItem(LocaleContent.getRECTANGULAR_SNIP());
    	reportIssue= new JMenuItem(LocaleContent.getREPORT_ISSUE());
    	takeScreenshot =  new JMenuItem(LocaleContent.getTAKE_SCREENSHOT());
    	takeScreenshot.setVisible(false);
    	splitVideo =new JMenuItem(LocaleContent.getSPLIT_VIDEO());
    	splitVideo.setEnabled(false);
    	
    	if(AssistiveScreenshot.prop.get("showClicks") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("showClicks").toString())){
    		showClicks = new JCheckBoxMenuItem(LocaleContent.getSHOW_CLICKS(),true);
    		ClickFrame.initializeClickFrame();
    	}else{
    		showClicks = new JCheckBoxMenuItem(LocaleContent.getSHOW_CLICKS(),false);
    		ClickFrame.close();
    	}
    	
    	showPreviewMenu = new JCheckBoxMenuItem(LocaleContent.getSHOW_PREVIEW(), false);
    	if(AssistiveScreenshot.prop.get("allScreen") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("allScreen").toString())){
    		allScreen = new JCheckBoxMenuItem(LocaleContent.getALL_MONITORS(), true);
    	}else{
    		allScreen = new JCheckBoxMenuItem(LocaleContent.getALL_MONITORS(), false);
    	}
    	recorderOptionsMenu = new JMenu(LocaleContent.getOPTIONS());
    	recorderOptionsMenu.add(splitVideo);
    	recorderOptionsMenu.add(showClicks);
    	videoQualityMenu = new JMenu(LocaleContent.getVIDEO_QUALITY());
    	recorderOptionsMenu.add(videoQualityMenu);
    	
    	saveAsMenu = new JMenu(LocaleContent.getSAVE_AS());
    	defaultName = new JCheckBoxMenuItem(LocaleContent.getDEFAULT(), true);
    	userDefinedName= new JCheckBoxMenuItem(LocaleContent.getUSER_DEFINED(), false);
    	if(AssistiveScreenshot.prop.get("defaultname") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("defaultname").toString())){
    		defaultName.setSelected(true);
    		userDefinedName.setSelected(false);
    	}
    	if(AssistiveScreenshot.prop.get("userdefinedname") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("userdefinedname").toString())){
    		userDefinedName.setSelected(true);
    		defaultName.setSelected(false);
    	}
    	saveAsMenu.add(defaultName);
    	saveAsMenu.add(userDefinedName);
    	
    	videoQualityMenu.add(videoQualityNormal);
    	videoQualityMenu.add(videoQualityHd);
    	videoQualityMenu.setEnabled(false);
    	recorderOptionsMenu.setEnabled(false);
    	
    	reset= new JMenuItem(LocaleContent.getRESET2());
    	
    	if(AssistiveScreenshot.prop.get("showPreview") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("showPreview").toString())){
			showPreviewMenu.setSelected(true);
    	}
    	if(AssistiveScreenshot.prop.get("showPreview") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("showPreview").toString())){
			showPreviewMenu.setSelected(true);
    	}
    	if(AssistiveScreenshot.prop.get("fstimedelay") != null){
    		AssistiveScreenshot.fstimedelay  = Integer.valueOf(AssistiveScreenshot.prop.get("fstimedelay").toString());
    	}
    	if(AssistiveScreenshot.prop.get("documentmode") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("documentmode").toString())){
    		documentMode= new JCheckBoxMenuItem(LocaleContent.getDOCUMENT_MODE(),true);
    		delayOptionsMenu.setEnabled(true);
			fullScreenshot.setEnabled(true);
			advanceFullScreenshot.setEnabled(true);
			rectangleSnip.setEnabled(true);
    	}else{
    		documentMode= new JCheckBoxMenuItem(LocaleContent.getDOCUMENT_MODE(),false);
    	}
    	if(AssistiveScreenshot.prop.get("image") == null && AssistiveScreenshot.prop.get("video") == null ){ 
    		selectImageMode();
    	}
    	else if(AssistiveScreenshot.prop.get("image") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("image").toString())){
    			selectImageMode();
    	}else if(AssistiveScreenshot.prop.get("video") != null && Boolean.valueOf(AssistiveScreenshot.prop.get("video").toString())){
    			image = new JCheckBoxMenuItem(LocaleContent.getSCREENSHOT_MODE(), true);
    			video= new JCheckBoxMenuItem(LocaleContent.getSCREEN_RECORDER_MODE(), true);
        		image.setSelected(false);
        		video.setSelected(true);
        		splitVideo.setEnabled(true);
        		snipVideo.setEnabled(true);
        		videoQualityMenu.setEnabled(true);
        		recorderOptionsMenu.setEnabled(true);
        		screenshotOptionsMenu.setEnabled(false);
        		delayOptionsMenu.setEnabled(false);
        		image.setSelected(false);
        		delayOptionsMenu.setEnabled(false);
    			advanceFullScreenshot.setEnabled(false);
    			fullScreenshot.setEnabled(false);
    			rectangleSnip.setEnabled(false);
    			showPreviewMenu.setEnabled(false);
    			AssistiveScreenshot.setImageToVideoStartButton(false);
    	}
    	
    	screenshotOptionsMenu.add(fullScreenshot);
    	screenshotOptionsMenu.add(advanceFullScreenshot);
    	screenshotOptionsMenu.add(documentMode);
    	screenshotOptionsMenu.add(showPreviewMenu);
    	
    	delayOptionsMenu.add(delay2sec);
    	delayOptionsMenu.add(delay5sec);
    	delayOptionsMenu.add(delayCustomSec);
    	
    	add(pause);
    	add(resume);
    	add(stop);
    	add(takeScreenshot);
    	add(savedLocation);
        add(location);
        add(new JSeparator());
        add(video);
        if(AssistiveScreenshot.slash.equals("\\")) {
            add(snipVideo);
        }
        add(recorderOptionsMenu);
        add(new JSeparator());
        add(image);
        if(AssistiveScreenshot.slash.equals("\\")) {
            add(rectangleSnip);
        }
        add(delayOptionsMenu);
        add(screenshotOptionsMenu);
        add(new JSeparator());
        add(allScreen);
        add(saveAsMenu);
//        add(videoSplitter);
//        add(videoEnhancer);
        add(new JSeparator());
        add(dnd);
        add(reset);
        add(reportIssue);
        add(new JSeparator());
        add(exit);
        
        
        image.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
            		AssistiveScreenshot.assistiveButton.setEnabled(false);
            		AssistiveScreenshot.stopButton.setVisible(false);
            		AssistiveScreenshot.setImageToAssisitveButton(false);
            		splitVideo.setEnabled(false);
            		videoQualityMenu.setEnabled(false);
            		recorderOptionsMenu.setEnabled(false);
            		snipVideo.setEnabled(false);
            		screenshotOptionsMenu.setEnabled(true);
            		delayOptionsMenu.setEnabled(true);
            		AssistiveScreenshot.storeTofile("image","true","Image mode");
            		AssistiveScreenshot.storeTofile("video","false","Video mode");
    				documentMode.setEnabled(true);
    				advanceFullScreenshot.setEnabled(true);
    				fullScreenshot.setEnabled(true);
    				rectangleSnip.setEnabled(true);
    				showPreviewMenu.setEnabled(true);
    				AssistiveScreenshot.assistiveButton.setEnabled(true);
    				video.setSelected(false);
            	}
            	else{
            		video.setSelected(true);
            	}
            }
         });
        
        video.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
            		AssistiveScreenshot.assistiveButton.setEnabled(false);
            		splitVideo.setEnabled(true);
            		snipVideo.setEnabled(true);
            		videoQualityMenu.setEnabled(true);
            		recorderOptionsMenu.setEnabled(true);
            		screenshotOptionsMenu.setEnabled(false);
            		delayOptionsMenu.setEnabled(false);
            		AssistiveScreenshot.storeTofile("video","true","Video mode");
            		AssistiveScreenshot.storeTofile("image","false","Image mode");
            		AssistiveScreenshot.setImageToVideoStartButton(false);
					documentMode.setEnabled(false);
					advanceFullScreenshot.setEnabled(false);
					fullScreenshot.setEnabled(false);
					rectangleSnip.setEnabled(false);
					showPreviewMenu.setEnabled(false);
					AssistiveScreenshot.assistiveButton.setEnabled(true);
					image.setSelected(false);
            	}
            	else{
            		image.setSelected(true);
            	}
            }
         });
        
        
        showPreviewMenu.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
            		AssistiveScreenshot.storeTofile("showPreview", "true", "smiley");
            	}else{
            		AssistiveScreenshot.storeTofile("showPreview", "false", "smiley");
            	}
            }

        });
        defaultName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            		AssistiveScreenshot.storeTofile("defaultname", "true", "name");
            		AssistiveScreenshot.storeTofile("userdefinedname", "false", "name");
            		userDefinedName.setSelected(false);
            		defaultName.setSelected(true);
            }
        });
        
        userDefinedName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            		AssistiveScreenshot.storeTofile("userdefinedname", "true", "name");
            		AssistiveScreenshot.storeTofile("defaultname", "false", "name");
            		defaultName.setSelected(false);
            		userDefinedName.setSelected(true);
            }
        });
        
        showClicks.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
            		AssistiveScreenshot.storeTofile("showClicks", "true", "click");
            		ClickFrame.initializeClickFrame();
            	}else{
            		AssistiveScreenshot.storeTofile("showClicks", "false", "click");
            		ClickFrame.close();
            	}
            }
        });
        allScreen.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
            		AssistiveScreenshot.storeTofile("allScreen", "true", "allScreen");
            	}else{
            		AssistiveScreenshot.storeTofile("allScreen", "false", "allScreen");
            	}
            }

        });
        
        reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter writer = new PrintWriter(new File(AssistiveScreenshot.currentDirectory+"properties"+AssistiveScreenshot.slash+"settings.properties"));
					writer.print("");
					writer.close();
					writer.flush();
					AssistiveScreenshot.prop.clear();
					if(AssistiveScreenshot.x != 0 && AssistiveScreenshot.y != 0)
						AssistiveScreenshot.storeTofile("lastlocation",AssistiveScreenshot.x+","+AssistiveScreenshot.y,"Last position");
					
					AssistiveScreenshot.releaseLock();
					Common.restartMessage();
					Runtime.getRuntime().exec("\""+AssistiveScreenshot.currentDirectory+"iShot.exe"+"\"");
			        System.exit(0);
			        
				} catch (IOException e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
			}
        });
        reportIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI("https://mail.google.com"));
					} catch (IOException e1) {
						Common.log(e1);
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						Common.log(e1);
						e1.printStackTrace();
					}
				}
				Object[] option = new Object[]{LocaleContent.getCOPY()};
	            int appNameFilter = JOptionPane.showOptionDialog(AssistiveScreenshot.frame, "imrankhan.m@mookambikainfo.com", LocaleContent.getMAIL_TO(), 2, 3, new ImageIcon("ishot-images/screenshot.png"), option, option[0]);
	            if (appNameFilter == 0) {
	            	StringSelection stringSelection = new StringSelection("imrankhan.m@mookambikainfo.com");
	            	Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
	            	c.setContents(stringSelection,null);
	            }
			}
			
        });
        
        pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseVideo();
			}

        });
        
        resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resumeVideo();
			}
        });
        stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssistiveScreenshot.stopVideo();
			}
        });
        takeScreenshot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImageMaker.execute(0,null,null);
				}catch(LocationNotFoundException e2){
					Common.log(e2);
				} catch (Exception e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
			}
        });
        
        splitVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSplitValue(false,null);
			}

			public void getSplitValue(boolean errorOccured,String oldInputValue) {
				String input = null;
				if(errorOccured){
					input = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getSPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT(),oldInputValue);
				}else{
					input = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getSPLIT_VIDEO_FOR_EVERY_MB_0_INDICATES_NO_SPLIT(),AssistiveScreenshot.splitSize);
				}
				if(input == null){
					
				}
				else if(input.isEmpty()){
					Common.valueEmptyMessage();
					AssistiveScreenshot.splitMonitor = null;
					AssistiveScreenshot.splitSize = 0;
					AssistiveScreenshot.storeTofile("splitSize",AssistiveScreenshot.splitSize+"","Split mode");
					getSplitValue(true,"0");
				}
				else if(isInteger(input)){
					if(input != null && !input.isEmpty()){
						 int tempInput = Integer.parseInt(input);
						 if(tempInput>=0){
							 AssistiveScreenshot.splitSize = tempInput;
							 AssistiveScreenshot.storeTofile("splitSize",AssistiveScreenshot.splitSize+"","Split mode");
						 }else{
							 Common.numberLengthValidationMessage();
							 AssistiveScreenshot.splitMonitor = null;
							 getSplitValue(true,input);
						 }
					}
				}else{
					try{
						if(!input.matches("-?\\d+(\\.\\d+)?")){
							Common.numberTypeValidationMessage();
						}
						else if(Long.valueOf(input) > Integer.MAX_VALUE){
							Common.maximumValueReachedException();	
						}else{
							Common.numberTypeValidationMessage();
						}
					}catch(Exception e){
						Common.log(e);
						if(input != null && input.contains(".")){
							Common.decimalNotAllowedException();
						}else if(input != null){
							Common.maximumValueReachedException();
						}
					}finally{
						AssistiveScreenshot.splitMonitor = null;
						getSplitValue(true,input);
					}
				}
			}
        });
        savedLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSavedLocation();
			}

		});
        location.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssistiveScreenshot.showLocationChanger(false);
			}
		});
        
        videoSplitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(VideoCutter.frame != null){
					VideoCutter.show();
					VideoCutter.frame.setExtendedState(Frame.NORMAL);
				}else{
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if(!new File(CommonMethods.FFMPEG_EXE_PATH).exists()){
							AssistiveScreenshot.ffmpegNotFoundException();
							return;
				    	}
						new VideoCutter(PopUpDemo.image.isSelected());
						VideoSplitterJPanel.grabInitialFocus();
					}

					
				});
				}
			}
        });
        
        videoEnhancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(AssistiveScreenshot.x != 0 && AssistiveScreenshot.y != 0)
//					AssistiveScreenshot.storeTofile("lastlocation",AssistiveScreenshot.x+","+AssistiveScreenshot.y,"Last position");
//				StringBuilder cmd = new StringBuilder();
//		        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
//		        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
//		            cmd.append(jvmArg + " ");
//		        }
//		        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
//		        cmd.append(JavaFxToolLayoutWithShapes.class.getName()).append(" ");
//		        try {
//					Runtime.getRuntime().exec(cmd.toString());
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
				if(!new File(CommonMethods.FFMPEG_EXE_PATH).exists()){
					AssistiveScreenshot.ffmpegNotFoundException();
					return;
		    	}
				if(JavaFxToolLayoutWithShapes.window == null){
				new Thread() {
		            @Override
		            public void run() {
		                javafx.application.Application.launch(JavaFxToolLayoutWithShapes.class);
		                if(JavaFxToolLayoutWithShapes.logger != null){
		        			JavaFxToolLayoutWithShapes.logger.addHandler(AssistiveScreenshot.fh);	
		        		}
		            }
		        }.start();
				}else{
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							JavaFxToolLayoutWithShapes.window.setIconified(false);
							JavaFxToolLayoutWithShapes.goBack();
							JavaFxToolLayoutWithShapes.sourcePathField.setText("");
							JavaFxToolLayoutWithShapes.fileLocationField.setText("");
							try {
								JavaFxToolLayoutWithShapes.setDestinationPath();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							JavaFxToolLayoutWithShapes.window.show();
							JavaFxToolLayoutWithShapes.window.toFront();
							
						}
					});
				}
			}
        });
       
        exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer result = 1;
				if(AssistiveScreenshot.isRecording || AssistiveScreenshot.isVideoPaused){
			        Object[] options = {LocaleContent.getYES(),LocaleContent.getNO()};
					result = JOptionPane.showOptionDialog(AssistiveScreenshot.frame, LocaleContent.getRECORDING_IS_IN_PROGRESS_DO_YOU_WANT_TO_EXIT(), LocaleContent.getRECORDING(), JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				}
				if(AssistiveScreenshot.isRecording || AssistiveScreenshot.isVideoPaused){
					if(result == 0){
						exitIt();
					}
				}else if(!(AssistiveScreenshot.isRecording || AssistiveScreenshot.isVideoPaused)){
					exitIt();
				}
			}

			public void exitIt() {
				ImageMaker.stopVideo();
				AssistiveScreenshot.timer.cancel();	
				if(AssistiveScreenshot.x != 0 && AssistiveScreenshot.y != 0){
					AssistiveScreenshot.storeTofile("lastlocation",AssistiveScreenshot.x+","+AssistiveScreenshot.y,"Last position");
				}
				if(ClickFrame.frame != null){
					ClickFrame.frame.dispose();
				}
				AssistiveScreenshot.frame.dispose();
				System.exit(0);
			}
		});
       
        delay2sec.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		getSetTimer();
        		SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try 
		        		{
		        			Delay2Task dt = new Delay2Task();
		        			AssistiveScreenshot.delay2Timer = new Timer();
		        			AssistiveScreenshot.delay2Timer.scheduleAtFixedRate(dt,0, 1000);
		        			if(PopUpDemo.documentMode.isSelected()){
		        				AssistiveScreenshot.createTestcaseDoc(2000,null);
		        			}else{
		        				ImageMaker.execute(2000,null,null);
		        			}
		        			AssistiveScreenshot.dragDelay = true;
		        			AssistiveScreenshot.assistiveButton.setVisible(true);
		        			AssistiveScreenshot.delayButton.setVisible(false);
		        		}catch(LocationNotFoundException e){
		        			Common.log(e);
		        			Common.setProperLocationMessage();
		                	AssistiveScreenshot.showLocationChanger(true);
		        			AssistiveScreenshot.showButton();
		        		}
						catch (Exception e1) {
							Common.log(e1);
		        			Common.showUnfortunateMessage();
		        			AssistiveScreenshot.showButton();
		        		}
					}
				});
                 TimerTask task = new TimerTask() {
					@Override
					public void run() {
						AssistiveScreenshot.assistiveButton.addActionListener(AssistiveScreenshot.mainActionListener);
						AssistiveScreenshot.delayButton.setVisible(false);
        				AssistiveScreenshot.assistiveButton.setVisible(true);
					}
				};
        		Timer timer = new Timer();
        		timer.schedule(task, 3000);
        	}

        });
        ActionListener delay5secActionListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		getSetTimer();
        		SwingUtilities.invokeLater(new Runnable() {
        			public void run() {
        				try {
        					AssistiveScreenshot.delay5Timer = new Timer();
        					Delay5Task dt = new Delay5Task();
        					AssistiveScreenshot.delay5Timer.scheduleAtFixedRate(dt, 0, 1000);
        					if(PopUpDemo.documentMode.isSelected()){
        						AssistiveScreenshot.createTestcaseDoc(5000,null);
        					}else{
        						ImageMaker.execute(5000,null,null);
        					}
        					AssistiveScreenshot.dragDelay = true;
        				} catch(LocationNotFoundException e){
        					Common.log(e);
		        			Common.setProperLocationMessage();
		                	AssistiveScreenshot.showLocationChanger(true);
		        			AssistiveScreenshot.showButton();
		        		}catch (Exception e1) {
		        			Common.log(e1);
        					Common.showUnfortunateMessage();
        					AssistiveScreenshot.showButton();
        				}
        			}
        		});
        		TimerTask task = new TimerTask() {
        			@Override
        			public void run() {
        				AssistiveScreenshot.assistiveButton.addActionListener(AssistiveScreenshot.mainActionListener);
        				AssistiveScreenshot.delayButton.setVisible(false);
        				AssistiveScreenshot.assistiveButton.setVisible(true);
        			}
        		};
        		Timer timer = new Timer();
        		timer.schedule(task, 6000);
        	}
        };
        delay5sec.addActionListener(delay5secActionListener);
        
        ActionListener delayCustomActionListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(getSeconds(false,null)){
        			getSetTimer();
        			SwingUtilities.invokeLater(new Runnable() {
        				public void run() {
        					try {
        						AssistiveScreenshot.delayCustomTimer = new Timer();
        						DelayCustomTask dt = new DelayCustomTask();
        						AssistiveScreenshot.delayCustomTimer.scheduleAtFixedRate(dt, 0, 1000);
        						if(PopUpDemo.documentMode.isSelected()){
        							AssistiveScreenshot.createTestcaseDoc(AssistiveScreenshot.customDelayTime*1000,null);
        						}else{
        							ImageMaker.execute(AssistiveScreenshot.customDelayTime*1000,null,null);
        						}
        						AssistiveScreenshot.dragDelay = true;
        					} catch(LocationNotFoundException e){
        						Common.log(e);
    		        			Common.setProperLocationMessage();
    		                	AssistiveScreenshot.showLocationChanger(true);
    		        			AssistiveScreenshot.showButton();
    		        		}catch (Exception e1) {
    		        			Common.log(e1);
        						Common.showUnfortunateMessage();
        						AssistiveScreenshot.showButton();
        					}

        				}
        			});
        			TimerTask task = new TimerTask() {
        				@Override
        				public void run() {
        					AssistiveScreenshot.assistiveButton.addActionListener(AssistiveScreenshot.mainActionListener);
        					AssistiveScreenshot.delayButton.setVisible(false);
        					AssistiveScreenshot.assistiveButton.setVisible(true);
        				}
        			};
        			Timer timer = new Timer();
        			timer.schedule(task, (AssistiveScreenshot.customDelayTime*1000)+1000);
        		}
        	}

        	public boolean getSeconds(boolean errorOccured,String oldInputValue) {
        		String input = null;
        		if(errorOccured){
        			input = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getDELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS(),oldInputValue);
        		}else{
        			input = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getDELAY_TIME_IN_SECS_MAXIMUM_DELAY_TIME_30_SECS(),10);
        		}
        		if(input == null){
        			return false;
        		}
        		else if(input.isEmpty()){
        			Common.delayValueEmptyMessage();
        			AssistiveScreenshot.customDelayTime = 0;
        			AssistiveScreenshot.storeTofile("customDelayTime",AssistiveScreenshot.customDelayTime+"","customDelayTime");
        			getSeconds(true,"0");
        		}
        		else if(isInteger(input)){
        			if(input != null && !input.isEmpty()){
        				int tempInput = Integer.parseInt(input);
        				if(tempInput>0 && tempInput<=30){
        					AssistiveScreenshot.customDelayTime = tempInput;
        					DelayCustomTask.delay = tempInput;
        					AssistiveScreenshot.storeTofile("customDelayTime",AssistiveScreenshot.customDelayTime+"","customDelayTime");
        					return true;
        				}else{
        					Common.delayTimeValidationMessage();
        					AssistiveScreenshot.customDelayTime = 0;
        					AssistiveScreenshot.storeTofile("customDelayTime",AssistiveScreenshot.customDelayTime+"","customDelayTime");
        					return getSeconds(true,input);
        				}
        			}
        		}else{
        			try{
        				if(!input.matches("-?\\d+(\\.\\d+)?")){
        					Common.numberTypeValidationMessage();
        					return getSeconds(true,input);
        				}
        				else if(Long.valueOf(input) < 0 || Long.valueOf(input) > 30){
        					Common.delayTimeValidationMessage();
        					AssistiveScreenshot.customDelayTime = 0;
        					AssistiveScreenshot.storeTofile("customDelayTime",AssistiveScreenshot.customDelayTime+"","customDelayTime");
        					return getSeconds(true,input);	
        				}else{
        					Common.numberTypeValidationMessage();
        					return getSeconds(true,input);
        				}
        			}catch(Exception e){
        				Common.log(e);
        				if(input != null && input.contains(".")){
        					Common.decimalNotAllowedException();
        					return getSeconds(true,input);
        				}else if(input != null){
        					Common.delayTimeValidationMessage();
        					AssistiveScreenshot.customDelayTime = 0;
        					AssistiveScreenshot.storeTofile("customDelayTime",AssistiveScreenshot.customDelayTime+"","customDelayTime");
        					return getSeconds(true,input);	
        				}
        			}
        		}
        		return false;
        	}

        };
		
        delayCustomSec.addActionListener(delayCustomActionListener);
        
        videoQualityNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            		videoQualityNormal.setSelected(true);
            		videoQualityHd.setSelected(false);
            		AssistiveScreenshot.videoQuallity = 2;
            		AssistiveScreenshot.storeTofile("quality",LocaleContent.getNORMAL(),"video quality");
            	}
        });
        
        videoQualityHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            		videoQualityNormal.setSelected(false);
            		videoQualityHd.setSelected(true);
            		AssistiveScreenshot.videoQuallity = 3;
            		AssistiveScreenshot.storeTofile("quality",LocaleContent.getHD(),"video quality");
            	}
        });
        documentMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(e.getStateChange() == 1){
					delayOptionsMenu.setEnabled(true);
					fullScreenshot.setEnabled(true);
					advanceFullScreenshot.setEnabled(true);
					rectangleSnip.setEnabled(true);
					AssistiveScreenshot.storeTofile("documentmode","true","ods mode");
            	}else{
            		AssistiveScreenshot.storeTofile("documentmode","false","ods mode");
            	}
            }
        });
        ActionListener fullScreenshotListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AssistiveScreenshot.hideButton();
        		AssistiveScreenshot.isAdvanceFullScreenShot  = false;
        		AssistiveScreenshot.isFullScreenShot   = true;
        		startFS();
        		AssistiveScreenshot.isFullScreenShot = false;
        		AssistiveScreenshot.isAdvanceFullScreenShot = false;
        		AssistiveScreenshot.showButton();
        	}

        };
        ActionListener afullScreenshotListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AssistiveScreenshot.hideButton();
        		AssistiveScreenshot.isAdvanceFullScreenShot  = false;
        		AssistiveScreenshot.isFullScreenShot   = true;
        		startAFS();
        		AssistiveScreenshot.isFullScreenShot = false;
        		AssistiveScreenshot.isAdvanceFullScreenShot = false;
        		AssistiveScreenshot.showButton();
        	}
        };

			
        fullScreenshot.addActionListener(fullScreenshotListener);
        advanceFullScreenshot.addActionListener(afullScreenshotListener);
        
        dnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssistiveScreenshot.hideButton();
				AssistiveScreenshot.frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
        rectangleSnip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AssistiveScreenshot.isRectangularSnip = true;
			        SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			                new ScreenCaptureRectangle();
			            }
			        });
			    
				} catch (Exception e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
			}
		});
        snipVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AssistiveScreenshot.isRectangularVideoSnip  = true;
			        SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			                new ScreenCaptureRectangle();
			            }
			        });
			    
				} catch (Exception e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
			}
		});
    }
    public void getSetTimer() {
    	AssistiveScreenshot.assistiveButton.removeActionListener(AssistiveScreenshot.mainActionListener);
    	AssistiveScreenshot.assistiveButton.setVisible(false);
    	AssistiveScreenshot.frame.setVisible(false);
    	AssistiveScreenshot.delayButton.setVisible(true);
    	AssistiveScreenshot.frame.setVisible(true);
    }
	public static void pauseVideo() {
		ImageMaker.pauseVideo();
		pause.setVisible(false);
		resume.setVisible(true);
		stop.setVisible(true);
	}
	
    public static void resumeVideo() {
		ImageMaker.startVideo(AssistiveScreenshot.rectangle);
		if(ImageMaker.file != null){
			AssistiveScreenshot.isRecording = true;
			resume.setVisible(false);
			pause.setVisible(true);
			stop.setVisible(true);
		}else{
			Common.setProperLocationMessage();
			AssistiveScreenshot.showLocationChanger(true);
		}
	}
    
    public static void openSavedLocation() {
		try {
			String savedLoc = AssistiveScreenshot.openSavedLocation();
			File file = new File (savedLoc);
			Desktop desktop = Desktop.getDesktop();
			if(file.exists()){
				desktop.open(file);
			}else{
				throw new IOException();
			}
		} catch (IOException e1) {
			Common.log(e1);
			Common.setProperLocationMessage();
			AssistiveScreenshot.showLocationChanger(false);
			AssistiveScreenshot.showButton();
		}
	}
    
	public void selectImageMode() {
		image = new JCheckBoxMenuItem(LocaleContent.getSCREENSHOT_MODE(), true);
		video= new JCheckBoxMenuItem(LocaleContent.getSCREEN_RECORDER_MODE(), true);
		video.setSelected(false);
		image.setSelected(true);
		splitVideo.setEnabled(false);
		videoQualityMenu.setEnabled(false);
		recorderOptionsMenu.setEnabled(false);
		screenshotOptionsMenu.setEnabled(true);

		delayOptionsMenu.setEnabled(true);
		advanceFullScreenshot.setEnabled(true);
		fullScreenshot.setEnabled(true);
		rectangleSnip.setEnabled(true);
		showPreviewMenu.setEnabled(true);
		
		AssistiveScreenshot.setImageToAssisitveButton(false);
	}
	public void startAFS() {
		AssistiveScreenshot.isAdvanceFullScreenShot = true;
		AssistiveScreenshot.isFullScreenShot = false;
		String url;
		url = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getENTER_URL(),getDataFromClipboard());

		if(Objects.nonNull(url) && url.isEmpty()){
			Common.urlValidationMessage();
			startAFS();
		}
		else if(Objects.nonNull(url) && !url.isEmpty()){
			if(isValidURL(url)){
				String folderPath = "";
				if(AssistiveScreenshot.prop.get("currentlocation") != null && !String.valueOf(AssistiveScreenshot.prop.get("currentlocation")).isEmpty()){
					folderPath = String.valueOf(AssistiveScreenshot.prop.get("currentlocation"));
				}
				if(folderPath.equals("")){
					File file = new File(AssistiveScreenshot.currentDirectory+"screenshots"+AssistiveScreenshot.slash+AssistiveScreenshot.slash);
					if(!file.exists()){
						file.mkdir();
					}
					folderPath = AssistiveScreenshot.currentDirectory+"screenshots"+AssistiveScreenshot.slash+AssistiveScreenshot.slash;
				}
				Calendar now = Calendar.getInstance();
				ScrollScreenShot.takeFullScreenShot(url, folderPath+ImageMaker.formatter.format(now.getTime())+".jpg");
			}else{
				Common.showInvalidUrl();
					startAFS();
			}
		}
	
	}

	public void startFS() {
		String url;
		Map<String, String> resultMap = getData();
		if (Integer.parseInt(resultMap.get("result")) == JOptionPane.YES_OPTION)
		{
			url = resultMap.get("url");
			if(isInteger(resultMap.get("time"))){
				if(resultMap.get("time") != null && !resultMap.get("time").isEmpty()){
					int tempInput = Integer.parseInt(resultMap.get("time"));
					if(tempInput>=0){
						AssistiveScreenshot.fstimedelay = tempInput;
						AssistiveScreenshot.storeTofile("fstimedelay",AssistiveScreenshot.fstimedelay+"","fstimedelay");
					}else{
						Common.numberLengthValidationMessage();
						startFS();
					}
				}else{
					Common.timeValidationMessage();
					startFS();
				}
			}else{
					Common.timeBlankValidationMessage();
					AssistiveScreenshot.storeTofile("fstimedelay","0","fstimedelay");
					AssistiveScreenshot.fstimedelay = 0;
					startFS();
					return;
				}
			if(Objects.nonNull(url) && url.isEmpty()){
				Common.urlValidationMessage();
				startFS();
    		}
    		else if(Objects.nonNull(url) && !url.isEmpty()){
    			if(isValidURL(url)){
    				String folderPath = "";
    				if(AssistiveScreenshot.prop.get("currentlocation") != null && !String.valueOf(AssistiveScreenshot.prop.get("currentlocation")).isEmpty()){
						folderPath = String.valueOf(AssistiveScreenshot.prop.get("currentlocation"));
					}
					if(folderPath.equals("")){
						File file = new File(AssistiveScreenshot.currentDirectory+"screenshots"+AssistiveScreenshot.slash+AssistiveScreenshot.slash);
						if(!file.exists()){
							file.mkdir();
						}
						folderPath = AssistiveScreenshot.currentDirectory+"screenshots"+AssistiveScreenshot.slash+AssistiveScreenshot.slash;
					}
    				Calendar now = Calendar.getInstance();
    				ScrollScreenShot.takeFullScreenShot(url, folderPath+ImageMaker.formatter.format(now.getTime())+".jpg");
    			}else{
    				Common.showInvalidUrl();
    				startFS();
    			}
    		}
		
		}
	}

	public Map<String, String> getData() {
		final JTextField urlTextbox = new JTextField(getDataFromClipboard());
		urlTextbox.addAncestorListener( new RequestFocusListener() );
		JTextField timeTextbox = new JTextField(String.valueOf(AssistiveScreenshot.fstimedelay));
		Object[] msg = {LocaleContent.getURL(), urlTextbox, LocaleContent.getTIME_TAKEN_TO_ENTER_CREDENTIALS_IN_SECS(), timeTextbox};
		Integer result = JOptionPane.showConfirmDialog(
				AssistiveScreenshot.frame,
				msg,
				"Use default layout",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("result", result.toString());
		resultMap.put("url", urlTextbox.getText());
		resultMap.put("time", timeTextbox.getText());
		return resultMap;
	}

	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	    	Common.log(e);
	        return false; 
	    } catch(NullPointerException e) {
	    	Common.log(e);
	        return false;
	    }
	    return true;
	}

	private String getDataFromClipboard() {
		try {
			String data = (String) Toolkit.getDefaultToolkit()
			.getSystemClipboard().getData(DataFlavor.stringFlavor);
			if(data != null && isValidURL(data)){
				return data;
			}
		} catch (HeadlessException e) {
			Common.log(e);
			e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			Common.log(e);
			e.printStackTrace();
		} catch (IOException e) {
			Common.log(e);
			e.printStackTrace();
		}
		return "";
	}

	private boolean isValidURL(String url) {
		try 
		{ 
			new URL(url).toURI(); 
			return true ; 
		}catch(Exception e){
			Common.log(e);
			return false;
		}
	}
	
}

class Handler extends WindowAdapter {
	  @Override public void windowStateChanged(WindowEvent e) {
	    if (e.getNewState() == Frame.ICONIFIED) {
	    	AssistiveScreenshot.createTrayIcon();
	    }else if(e.getNewState() == Frame.NORMAL){
	    	AssistiveScreenshot.removeTrayIcon();
	    	AssistiveScreenshot.showButton();
	    }
	  }
	}