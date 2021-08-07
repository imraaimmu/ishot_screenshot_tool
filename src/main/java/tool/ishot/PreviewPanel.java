package tool.ishot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
class PreviewPanel extends JFrame implements WindowListener
{
	public static BufferedImage img = null;
	JDialog hiddenDialogForModality = null;
	public PreviewPanel() throws IOException
	{
		setTitle(LocaleContent.getPREVIEW());
		setIconImage(ImageIO.read(AssistiveScreenshot.class.getResourceAsStream(Common.ASSISTIVE_ICON)));
		getContentPane(). setBackground(new Color(255,255,255));
		ImageIcon image = new ImageIcon(img);
		JLabel label = new JLabel(image);

		JScrollPane scrollPane = new JScrollPane(label);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(50);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(50);

		JLabel preview1 = new JLabel("                                                  ");
		preview1.setForeground(Color.WHITE);

		JPanel panelNORTH = new JPanel();
		BoxLayout boxLayoutnorth = new BoxLayout(panelNORTH,BoxLayout.Y_AXIS);
		panelNORTH.setLayout(boxLayoutnorth);
		panelNORTH.setBackground(Color.WHITE);
		panelNORTH.add(new JLabel("    "));
		panelNORTH.add(new JLabel("    "));
		panelNORTH.add(preview1);
		panelNORTH.add(new JLabel("    "));
		panelNORTH.add(new JLabel("    "));
		add(panelNORTH,BorderLayout.NORTH);
		JLabel preview2 = new JLabel("                                                  ");
		add(preview2,BorderLayout.EAST);
		JLabel preview3 = new JLabel("                                                  ");
		add(preview3,BorderLayout.WEST);
		JLabel preview4 = new JLabel("                                                  ");
		add(preview4,BorderLayout.SOUTH);
		JButton yesButton = new JButton(LocaleContent.getSAVE());
		JButton noButton = new JButton(LocaleContent.getCANCEL());

		ActionListener yesact = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssistiveScreenshot.issavepreview = 0;
				dispose();
			}
		};
		KeyListener canlis = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE){
					AssistiveScreenshot.issavepreview = 1;
					dispose();
				}
			}
		};
		KeyListener oklis = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE){
					AssistiveScreenshot.issavepreview = 0;
					dispose();
				}
			}
		};
		ActionListener noact = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssistiveScreenshot.issavepreview = 1;
				dispose();
			}
		};
		yesButton.addActionListener(yesact);
		yesButton.addKeyListener(oklis);
		noButton.addActionListener(noact);
		noButton.addKeyListener(canlis);
		JPanel panely = new JPanel();
		BoxLayout boxLayouty = new BoxLayout(panely,BoxLayout.Y_AXIS);
		JPanel panel = new JPanel();
		BoxLayout boxLayoutx = new BoxLayout(panel,BoxLayout.X_AXIS);
		panely.setLayout(boxLayouty);
		panel.setLayout(boxLayoutx);
		panel.setBackground(Color.WHITE);
		JLabel labelempty = new JLabel("                                                                                                            ");
		panel.add(labelempty);
		panel.add(yesButton);
		panel.add(new JLabel("      "));
		panel.add(noButton);
		panely.add(new JLabel("    "));
		panely.add(new JLabel("    "));
		panely.add(panel);
		panely.add(new JLabel("    "));
		panely.add(new JLabel(LocaleContent.getON_SAVING_SCREENSHOT_WILL_BE_SAVED_AND_COPIED_TO_CLIPBOARD()));
		panely.add(new JLabel("    "));
		panely.add(new JLabel("    "));
		panely.setBackground(Color.WHITE);
		add(scrollPane, BorderLayout.CENTER);
		add(panely, BorderLayout.SOUTH);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		pack();
		setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		setVisible(true);
		yesButton.grabFocus();
		addWindowListener(this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setAlwaysOnTop(false);
				setAlwaysOnTop(true);
			}
		});
		hiddenDialogForModality = new JDialog(AssistiveScreenshot.frame, true);
		final class MyWindowCloseListener extends WindowAdapter {
			@Override
			public void windowClosed(final WindowEvent e) {
				dispose();
				hiddenDialogForModality.dispose();
			}
		}
		final MyWindowCloseListener myWindowCloseListener = new MyWindowCloseListener();
		addWindowListener(myWindowCloseListener);
		final Dimension smallSize = new Dimension(80, 80);
		hiddenDialogForModality.setMinimumSize(smallSize);
		hiddenDialogForModality.setSize(smallSize);
		hiddenDialogForModality.setMaximumSize(smallSize);
		hiddenDialogForModality.setLocation(-smallSize.width * 2, -smallSize.height * 2);
		hiddenDialogForModality.setVisible(true);
		removeWindowListener(myWindowCloseListener);
	}





	public void windowOpened(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {
		AssistiveScreenshot.issavepreview = 1;
		hiddenDialogForModality.dispose();
	}

	public void windowClosed(WindowEvent e) {
		hiddenDialogForModality.dispose();
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
