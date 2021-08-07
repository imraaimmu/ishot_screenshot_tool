package tool.ishot;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/** Getting a Rectangle of interest on the screen.
Requires the MotivatedEndUser API - sold separately. */
public class ScreenCaptureRectangle {

    Rectangle captureRect;

    static  JFrame frame = new JFrame();
    static SelectionPane selectionPane;
    ScreenCaptureRectangle() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(3);
        frame.setLayout(new BorderLayout(5, 5));
        frame.add(new SnipItPane());
        frame.setBounds(getVirtualBounds());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Rectangle getVirtualBounds() {
        Rectangle bounds = new Rectangle(0, 0, 0, 0);
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

    public static void startProcess(){
        int x = ScreenCaptureRectangle.selectionPane.getX();
        int y = ScreenCaptureRectangle.selectionPane.getY();
        int width = ScreenCaptureRectangle.selectionPane.getWidth();
        int height = ScreenCaptureRectangle.selectionPane.getHeight();
        Rectangle rectangle = new Rectangle(x,y,width+width%2,height+height%2);
        try {
            if(rectangle.getWidth()>0 && rectangle.getHeight() > 0){
                AssistiveScreenshot.rectangle = rectangle;
                if(AssistiveScreenshot.isRectangularVideoSnip){
                    if(AssistiveScreenshot.startVideoRecording(rectangle)){
                        new InvisibleFrame(x - 2, y - 2, width + width % 2 + 4, height + height % 2 + 4);
                        new RectangleOutline(x - 2, y - 2, width + width % 2 + 4, height + height % 2 + 4);
                    }else{
                        AssistiveScreenshot.cancelRectangularThing();
                    }
                }else{
                    AssistiveScreenshot.hideButton();
                    ScreenCaptureRectangle.frame.setVisible(false);
                    AssistiveScreenshot.hideButton();
                    BufferedImage  image = null; 
                    image = new Robot().createScreenCapture(rectangle);
                    AssistiveScreenshot.showButton();
                    if(PopUpDemo.documentMode.isSelected()){
                        if(PopUpDemo.allScreen.isSelected()){
                            image = new Robot().createScreenCapture(ScreenCaptureRectangle.getVirtualBounds());
                        }
                        AssistiveScreenshot.createTestcaseDoc(0,image);
                    }else{
                        ImageMaker.execute(0, rectangle, image);
                    }
                    AssistiveScreenshot.isRectangularSnip = false;
                    AssistiveScreenshot.showButton();
                    SwingUtilities.getWindowAncestor(ScreenCaptureRectangle.selectionPane).dispose();
                }
            }
        } catch(LocationNotFoundException e){
            Common.setProperLocationMessage();
            AssistiveScreenshot.showLocationChanger(true);
            AssistiveScreenshot.showButton();
        }catch (Exception e1) {
            Common.log(e1);
            e1.printStackTrace();
        }
    }


    public static int showImage(BufferedImage actualImage) {
        PreviewPanel.img = actualImage;
        try {
            AssistiveScreenshot.hideButton();
            new PreviewPanel();
        } catch (IOException e) {
            Common.log(e);
            Common.previewIssueMessage();
            AssistiveScreenshot.issavepreview = 0;
            e.printStackTrace();
        }
        AssistiveScreenshot.showButton();
        return AssistiveScreenshot.issavepreview;
    }

    public static void repaint(BufferedImage orig, BufferedImage copy, Rectangle rect) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(orig,0,0, null);
        g.setColor(Color.RED);
        g.draw(rect);
        g.dispose();
    }
}

class SnipItPane
extends JPanel {
    private static final long serialVersionUID = 208298250012208979L;
    static public Point mouseAnchor;
    static Point dragPoint;

    Rectangle bounds = new Rectangle();
    static SnipItPane staticSnipItPane;
    public SnipItPane() {
        this.setOpaque(false);
        this.setLayout(null);
        ScreenCaptureRectangle.selectionPane = new SelectionPane();
        this.add(ScreenCaptureRectangle.selectionPane);
        this.setCursor(new Cursor(1));
        MouseAdapter adapter = new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                SnipItPane.mouseAnchor = e.getPoint();
                SnipItPane.dragPoint = null;
                ScreenCaptureRectangle.selectionPane.setLocation(SnipItPane.mouseAnchor);
                ScreenCaptureRectangle.selectionPane.setSize(0,0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                SnipItPane.dragPoint = e.getPoint();
                int width = SnipItPane.dragPoint.x-SnipItPane.mouseAnchor.x;
                int height = SnipItPane.dragPoint.y-SnipItPane.mouseAnchor.y;
                int x = SnipItPane.mouseAnchor.x;
                int y = SnipItPane.mouseAnchor.y;
                if (width < 0) {
                    x = SnipItPane.dragPoint.x;
                    width *= -1;
                }
                if (height < 0) {
                    y = SnipItPane.dragPoint.y;
                    height *= -1;
                }
                ScreenCaptureRectangle.selectionPane.setBounds(x, y, width, height);
                                ScreenCaptureRectangle.selectionPane.revalidate();
                staticSnipItPane = SnipItPane.this;
                                staticSnipItPane.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            ScreenCaptureRectangle.startProcess();
                        } catch (Exception e) {
                            Common.log(e);
                            e.printStackTrace();
                        }
                    }
                };
                EventQueue.invokeLater(runnable);
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        bounds.setBounds(0, 0, this.getWidth(), this.getHeight());
        Area area = new Area(bounds);
        area.subtract(new Area(ScreenCaptureRectangle.selectionPane.getBounds()));
        g2d.setColor(new Color(192, 192, 192, 64));
        g2d.fill(area);
    }
}

class SelectionPane
extends JPanel {
    private static final long serialVersionUID = 4519191722350181212L;

    @SuppressWarnings("serial")
    public SelectionPane() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.setCursor(Cursor.getDefaultCursor());
        ScreenCaptureRectangle.frame.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(27, 0), "Cancel");
        ScreenCaptureRectangle.frame.getRootPane().getActionMap().put("Cancel", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(ScreenCaptureRectangle.selectionPane).dispose();
                AssistiveScreenshot.showButton();
                ScreenCaptureRectangle.frame.dispose();
                RectangleOutline.close();
                AssistiveScreenshot.cancelRectangularThing();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g.setColor(Color.RED);
        float[] dash1 = new float[]{3.0f};
        BasicStroke dashed = new BasicStroke(3.0f, 0, 0, 10.0f, dash1, 0.0f);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(dashed);
        g2d.drawRect(0, 0, this.getWidth() - 3, this.getHeight() - 3);
        g2d.dispose();
    }
}

