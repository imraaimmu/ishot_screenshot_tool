package tool.ishot;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.dispatcher.SwingDispatchService;

public class RectangleOutline{
	static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	public static JFrame frame;
	public static int x;
	public static int y;
	public static int width;
	public static int height;
	private Color lastColor = Color.RED;

	public RectangleOutline(int x1, int y1, int width1, int height1) {
		x = x1;
		y = y1;
		width = width1;
		height = height1;


		EventQueue.invokeLater(new Runnable(){

			public void run() {
				GlobalScreen.setEventDispatcher(new SwingDispatchService());

				RectangleOutline.frame = new JFrame();
				RectangleOutline.frame.setUndecorated(true);
				RectangleOutline.frame.setBackground(new Color(0, 0, 0, 0));
				RectangleOutline.frame.setAlwaysOnTop(true);
				RectangleOutline.frame.setDefaultCloseOperation(3);
				RectangleOutline.frame.setLayout(new BorderLayout(5, 5));
				RectangleOutline.frame.add(new SnipItPane());
				RectangleOutline.frame.setBounds(RectangleOutline.getVirtualBounds());
				RectangleOutline.frame.setLocationRelativeTo(null);
				RectangleOutline.frame.setVisible(true);
				try {
					RectangleOutline.frame.setIconImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.VIDEO_START_ICON)));
				} catch (IOException e) {
					Common.log(e);
					e.printStackTrace();
				}


			}
		});
	}

	public static Rectangle getVirtualBounds() {
		Rectangle bounds = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] arrgraphicsDevice = ge.getScreenDevices();
		int n = arrgraphicsDevice.length;
		int n2 = 0;
		while (n2 < n) {
			GraphicsDevice gd = arrgraphicsDevice[n2];
			bounds.add(gd.getDefaultConfiguration().getBounds());
			++n2;
		}
		return bounds;
	}

	public static void close() {
		if(service!=null){
			service.shutdown();
		}
		if(frame!=null){
			frame.dispose();
		}
	}

	static void access$1(RectangleOutline rectangleOutline, Color color) {
		rectangleOutline.lastColor = color;
	}

	public class SelectionPane
	extends JPanel {
		private static final long serialVersionUID = 4519191722350181212L;

		public SelectionPane() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			if (RectangleOutline.service.isTerminated()) {
				RectangleOutline.service = Executors.newSingleThreadScheduledExecutor();
			}
			RectangleOutline.service.scheduleAtFixedRate(new Runnable(){

				public void run() {
					SelectionPane.this.repaint();
				}
			}, 0, 1, TimeUnit.SECONDS);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g.create();
			float[] dash1 = new float[]{3.0f, 6.0f};
			BasicStroke dashed = new BasicStroke(3.0f, 0, 0, 10.0f, dash1, 0.0f);
			if (RectangleOutline.this.lastColor.equals(Color.RED)) {
				RectangleOutline.access$1(RectangleOutline.this, Color.GREEN);
			} else {
				RectangleOutline.access$1(RectangleOutline.this, Color.RED);
			}
			g2d.setColor(RectangleOutline.this.lastColor);
			g2d.setStroke(dashed);
			g2d.drawRect(RectangleOutline.x, RectangleOutline.y, RectangleOutline.width, RectangleOutline.height);
			g2d.dispose();
		}

	}

	public class SnipItPane
	extends JPanel {
		SelectionPane selectionPane;
		private static final long serialVersionUID = 208298250012208979L;

		public SnipItPane() {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.selectionPane = new SelectionPane();
			this.add(this.selectionPane);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g.create();
			Rectangle bounds = new Rectangle(0, 0, this.getWidth(), this.getHeight());
			Area area = new Area(bounds);
			area.subtract(new Area(this.selectionPane.getBounds()));
			g2d.setColor(new Color(192, 192, 192, 64));
			g2d.fill(area);
		}
	}


}
