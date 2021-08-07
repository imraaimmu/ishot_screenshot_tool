package tool.ishot;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FindMeDialog extends JDialog {

	private static final String NO = "No";
	private static final String YES = "Yes";
	private JPanel myPanel = null;
	private JButton yesButton = null;
	private JButton noButton = null;

	public FindMeDialog(JFrame frame, boolean modal, String myMessage) {
		super(frame, modal);
		myPanel = new JPanel();
		getContentPane().add(myPanel);
		myPanel.add(new JLabel(myMessage));
		yesButton = new JButton(YES);
		myPanel.add(yesButton);
		noButton = new JButton(NO);
		myPanel.add(noButton);
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}
}
