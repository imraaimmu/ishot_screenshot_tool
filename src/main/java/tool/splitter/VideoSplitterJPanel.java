package tool.splitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import tool.ishot.AssistiveScreenshot;
import tool.ishot.Common;
import tool.ishot.FileNameUtil;
import tool.ishot.LocationNotFoundException;
import tool.splitter.locale.SplitterLocaleContent;

public class VideoSplitterJPanel {
	String[] inputFileMetaData;
	String[] outputFileMetaData;
	int maxMin;
	int maxSec;
	String inputStartTime;
	String inputEndTime;
	JLabel startTimeErrorLabel;
	JLabel outputFilenameLabel;
	JLabel outputFileDurationLabel;
	JLabel outputFileSizeLabel;
	JLabel outputTitleLabel;
	JLabel inputFileNameLabel;
	JLabel inputFileDurationLabel;
	JLabel inputFileSizeLabel;
	JPanel resultDetailsJPane;
	JPanel jPanelCrop;
	JEditorPane sourceFilePathTextField;
	static JButton closeButton;
	JButton backButton;
	static JButton resetButton;
	protected static File latestChoosenFile;
	JButton splitButton;
	String filePath;
	final JNumberTextField startMinTextField = new JNumberTextField();
	final JNumberTextField startSecTextField = new JNumberTextField();
	final JNumberTextField endMinTextField = new JNumberTextField();
	final JNumberTextField endSecTextField = new JNumberTextField();
	final JLabel sourceFileErrorLabel = new JLabel();
	final JTextField outputFileNameTextField = new JTextField();
	final static JButton sourceFileChooserButton = new JButton();
	private JLabel endTimeErrorLabel;

	public void processChoosenFile(final JTextField txtOutFile, final JFileChooser fileChooser,
			File selectedFile) {
		filePath = selectedFile.getAbsolutePath().replace("//", "/");
		sourceFilePathTextField.setText(selectedFile.getAbsolutePath());
		inputFileMetaData = CommonMethods.executeFfmpegCmd(filePath);
		if(inputFileMetaData[0] != null) {
			this.updateMaxMinSec();
			this.updateDefaultTime();
			VideoSplitterJPanel.this.setDataOnPanel(false);
			this.setEnableOnPanel(true);
			String[] timeArray = inputFileMetaData[0].split(":");
			if(Integer.valueOf(timeArray[2]) > 0) {
				this.setTimerNull(true);
				txtOutFile.setText(selectedFile.getName().substring(0,selectedFile.getName().lastIndexOf(".")));
				startMinTextField.setBackground(Color.white);
				startMinTextField.setForeground(Color.black);
				startSecTextField.setBackground(Color.white);
				startSecTextField.setForeground(Color.black);
				endMinTextField.setBackground(Color.white);
				endMinTextField.setForeground(Color.black);
				endSecTextField.setBackground(Color.white);
				endSecTextField.setForeground(Color.black);
				startTimeErrorLabel.setVisible(false);
				endTimeErrorLabel.setVisible(false);
				startMinTextField.requestFocus();
				startMinTextField.grabFocus();
			}else {
				System.out.println("0 size input file");
				this.setTimerNull(false);
				sourceFileErrorLabel.setText(SplitterLocaleContent.INVALID_FILE);
				sourceFilePathTextField.setText("");
				setErrorForInputFile();

			}
		}else{
			System.out.println("0 size input file");
			sourceFilePathTextField.setText("");
			this.setTimerNull(false);
			sourceFileErrorLabel.setText(SplitterLocaleContent.INVALID_FILE);
			setErrorForInputFile();
		
		}
	}

	private void updateDefaultTime() {
		startMinTextField.setText("");
		startSecTextField.setText("00");
		endMinTextField.setText(getTimeValueString(String.valueOf(this.maxMin)));
		endSecTextField.setText(getTimeValueString(String.valueOf(this.maxSec)));
	}

	private void setTimerNull(boolean b) {
		jPanelCrop.setVisible(b);
		resetButton.setVisible(b);
		splitButton.setVisible(b);
	}

	private void setEnableOnPanel(boolean b) {
		inputFileNameLabel.setVisible(b);
		inputFileDurationLabel.setVisible(b);
		inputFileSizeLabel.setVisible(b);
		sourceFileErrorLabel.setVisible(!b);
	}

	private void updateMaxMinSec() {
		maxMin = Integer.parseInt(inputFileMetaData[0].substring(3, 5).trim());
		maxSec = Integer.parseInt(inputFileMetaData[0].substring(6).trim());
	}

	public JPanel getVideoSplitterJPanel(boolean isImageSelected) {
		MatteBorder normalBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);
		Font calibriFont = Font.getFont("Calibri");
		final JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		this.jPanelCrop = new JPanel();
		this.jPanelCrop.setLayout(null);
		setBoundsForJPanelCrop();
		jPanel.add(this.jPanelCrop);
		this.resultDetailsJPane = new JPanel();
		this.resultDetailsJPane.setLayout(null);
		this.resultDetailsJPane.setBounds(ComponentBounds.resultDetailsJPaneBounds);
		jPanel.add(this.resultDetailsJPane);
		this.outputTitleLabel = new JLabel();
		this.outputTitleLabel.setBounds(ComponentBounds.outputTitleLabelBounds);
		this.resultDetailsJPane.add(this.outputTitleLabel);
		this.outputFilenameLabel = new JLabel();
		this.outputFilenameLabel.setBounds(ComponentBounds.outputFilenameLabelBounds);
		this.resultDetailsJPane.add(this.outputFilenameLabel);
		this.outputFileDurationLabel = new JLabel();
		this.outputFileDurationLabel.setBounds(ComponentBounds.outputFileDurationBounds);
		this.resultDetailsJPane.add(this.outputFileDurationLabel);
		this.outputFileSizeLabel = new JLabel();
		this.outputFileSizeLabel.setBounds(ComponentBounds.outputFileSizeBounds);
		this.resultDetailsJPane.add(this.outputFileSizeLabel);
		this.inputFileNameLabel = new JLabel();
		this.inputFileNameLabel.setBounds(ComponentBounds.inputFileNameBounds);
		jPanel.add(this.inputFileNameLabel);
		this.inputFileDurationLabel = new JLabel();
		this.inputFileDurationLabel.setBounds(ComponentBounds.inputFileDurationLabelBounds);
		jPanel.add(this.inputFileDurationLabel);
		this.inputFileSizeLabel = new JLabel();
		this.inputFileSizeLabel.setBounds(ComponentBounds.inputFileSizeLabelBounds);
		jPanel.add(this.inputFileSizeLabel);
		outputFileNameTextField.setBounds(ComponentBounds.outputFileNameTextFieldBounds);
		outputFileNameTextField.setBorder(normalBorder);
		this.jPanelCrop.add(outputFileNameTextField);
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText(SplitterLocaleContent.SELECT_VIDEO_FILE);
		fileChooser.setDialogTitle(SplitterLocaleContent.SELECT_VIDEO_TO_SPLIT);
		File chooserDir = null;
		try {
			chooserDir = new File(FileNameUtil.getFolderPath("", isImageSelected));
		} catch (LocationNotFoundException e1) {
			Common.log(e1);
			e1.printStackTrace();
		} catch (IOException e1) {
			Common.log(e1);
			e1.printStackTrace();
		}
		fileChooser.setCurrentDirectory(chooserDir.exists() ? chooserDir : new File(CommonMethods.HOME_DIRECTORY));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("VIDEO FILES", "wmv","mp4");
		fileChooser.setFileFilter(filter);
		JLabel sourcePathLabel = new JLabel(SplitterLocaleContent.SOURCE_PATH);
		sourcePathLabel.setBounds(ComponentBounds.sourcePathLabelBounds);
		jPanel.add(sourcePathLabel);
		this.sourceFilePathTextField = new JEditorPane();
		this.sourceFilePathTextField.setBounds(ComponentBounds.sourceFilePathTextFieldBounds);
		this.sourceFilePathTextField.setBorder(normalBorder);
		this.sourceFilePathTextField.setEditable(false);
		jPanel.add(this.sourceFilePathTextField);
		JLabel extraOutputFileNameLabel = new JLabel("_vsOut");
		extraOutputFileNameLabel.setBounds(ComponentBounds.extraOutputFileNameLabelBounds);
		this.jPanelCrop.add(extraOutputFileNameLabel);
		sourceFileErrorLabel.setBounds(ComponentBounds.sourceFileErrorLabelBounds);
		sourceFileErrorLabel.setFont(calibriFont);
		sourceFileErrorLabel.setForeground(Color.RED);
		jPanel.add(sourceFileErrorLabel);
		ImageIcon defaultIcon = CommonMethods.createImageIcon("browse.png");
		if (defaultIcon != null) {
			sourceFileChooserButton.setIcon(defaultIcon);
		}
		sourceFileChooserButton.setBounds(ComponentBounds.sourceFileChooserButtonBounds);
		jPanel.add(sourceFileChooserButton);
		sourceFileChooserButton.addActionListener(new ActionListener(){
			Thread thread;

			public void actionPerformed(ActionEvent e) {
				this.thread = new Thread(new Runnable(){

					public void run() {
						block8 : {
						try {
							try {
								if(latestChoosenFile != null){
									fileChooser.setCurrentDirectory(VideoSplitterJPanel.latestChoosenFile);
								}
								int result = fileChooser.showOpenDialog(jPanel);
								if (result != 0) {
									break block8;
								}
								sourceFileErrorLabel.setText("");
								startTimeErrorLabel.setVisible(false);
								endTimeErrorLabel.setVisible(false);
								sourceFileChooserButton.setEnabled(false);
								maxMin = 0;
								maxSec = 0;
								sourceFilePathTextField.setText("");
								File selectedFile = fileChooser.getSelectedFile();
								if (selectedFile.getName().toLowerCase().endsWith("wmv") || selectedFile.getName().toLowerCase().endsWith("mp4")) {
									VideoSplitterJPanel.latestChoosenFile = selectedFile;
									extraOutputFileNameLabel.setText("_vsOut"+"."+FilenameUtils.getExtension(selectedFile.getName().toLowerCase()));
									processChoosenFile(outputFileNameTextField, fileChooser, selectedFile);
									break block8;
								}
								sourceFileErrorLabel.setText(SplitterLocaleContent.CHOOSE_A_VIDEO_FILE_SHOULD_END_WITH_WMV);
								sourceFilePathTextField.setText("");
								sourceFileChooserButton.setFocusable(true);
								setErrorForInputFile();
							}
							catch (Exception e) {
								throw e;
							}
						}catch (FFMPEGNotFoundException e) {
							AssistiveScreenshot.ffmpegNotFoundException();
						}
						finally {
							sourceFileChooserButton.setEnabled(true);
							thread.interrupt();
						}
					}
					}


				});
				this.thread.start();
			}

		});

		this.sourceFilePathTextField.setDropTarget(new DropTarget() {

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>)
							evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					if(droppedFiles.size() > 1){
						JOptionPane.showMessageDialog(VideoCutter.frame, SplitterLocaleContent.DROP_1_FILE_ONLY, SplitterLocaleContent.ERROR, 2);
					}else if(droppedFiles.size() == 1){
						if (droppedFiles.get(0).getName().toLowerCase().endsWith("wmv") || droppedFiles.get(0).getName().toLowerCase().endsWith("mp4")) {
							extraOutputFileNameLabel.setText("_vsOut"+"."+FilenameUtils.getExtension(droppedFiles.get(0).getName().toLowerCase()));
							VideoSplitterJPanel.latestChoosenFile = droppedFiles.get(0);
							processChoosenFile(outputFileNameTextField, fileChooser, droppedFiles.get(0));
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									startMinTextField.setBackground(Color.white);
									startMinTextField.setForeground(Color.black);
									startSecTextField.setBackground(Color.white);
									startSecTextField.setForeground(Color.black);
									endMinTextField.setBackground(Color.white);
									endMinTextField.setForeground(Color.black);
									endSecTextField.setBackground(Color.white);
									endSecTextField.setForeground(Color.black);
									startTimeErrorLabel.setVisible(false);
									endTimeErrorLabel.setVisible(false);
									startMinTextField.requestFocus();
									startMinTextField.grabFocus();
								}
							});
							return;
						}
						setEnableOnPanel(false);
						sourceFileErrorLabel.setText(SplitterLocaleContent.CHOOSE_A_VIDEO_FILE_SHOULD_END_WITH_WMV);
						sourceFilePathTextField.setText("");
						sourceFileChooserButton.setFocusable(true);
						setTimerNull(false);
						sourceFileChooserButton.setEnabled(true);
					}
				}catch(FFMPEGNotFoundException e){
					AssistiveScreenshot.ffmpegNotFoundException();
				}
				catch (Exception ex) {
					Common.log(ex);
					ex.printStackTrace();
				}
			}
		});


		JLabel startTimeLabel = new JLabel(SplitterLocaleContent.START_TIME);
		setBoundsForStartLabel(startTimeLabel);
		this.jPanelCrop.add(startTimeLabel);
		setBoundsForStartMin();
		this.jPanelCrop.add(startMinTextField);
		JLabel startMinuteLabel = new JLabel(SplitterLocaleContent.MIN);
		startMinuteLabel.setBounds(ComponentBounds.startMinuteLabelBounds);
		this.jPanelCrop.add(startMinuteLabel);
		startSecTextField.setBounds(ComponentBounds.startSecTextFieldBounds);
		startSecTextField.setMaxLength(2);
		startSecTextField.setAllowNegative(false);
		this.jPanelCrop.add(startSecTextField);
		JLabel startSecLabel = new JLabel(SplitterLocaleContent.SEC);
		startSecLabel.setBounds(ComponentBounds.startSecLabelBounds);
		this.jPanelCrop.add(startSecLabel);
		JLabel endTimeLabel = new JLabel(SplitterLocaleContent.END_TIME);
		setBoundsForEndLabel(endTimeLabel);
		this.jPanelCrop.add(endTimeLabel);
		endMinTextField.setBounds(ComponentBounds.endMinTextFieldBounds);
		endMinTextField.setMaxLength(2);
		endMinTextField.setAllowNegative(false);
		this.jPanelCrop.add(endMinTextField);
		
		InputVerifier fileNameInputVerifier = new InputVerifier(){
			@Override
			public boolean verify(JComponent input) {
				validate();
				return true;
			}

		};
		KeyAdapter fileNameValidationListener = new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				validate();
			}

		};
		outputFileNameTextField.addKeyListener(fileNameValidationListener);
		outputFileNameTextField.setInputVerifier(fileNameInputVerifier);
		
		InputVerifier startInputVerifier = new InputVerifier(){
			@Override
			public boolean verify(JComponent input) {
				checkEmptyFieldAndFillZero();
				validate();
				return true;
			}

				private void checkEmptyFieldAndFillZero() {
					if (startMinTextField.getText().trim().isEmpty()) {
						startMinTextField.setText("00");
					}
					if (startSecTextField.getText().trim().isEmpty()) {
						startSecTextField.setText("00");
					}
				}
				
		};
		InputVerifier endInputVerifier = new InputVerifier(){
			@Override
			public boolean verify(JComponent input) {
				checkEmptyFieldAndFillZero();
				validate();
				return true;
			}
			
			private void checkEmptyFieldAndFillZero() {
				if (endMinTextField.getText().trim().isEmpty()) {
					endMinTextField.setText("00");
				}
				if (endSecTextField.getText().trim().isEmpty()) {
					endSecTextField.setText("00");
				}
			}
		};
		
		
		startMinTextField.setInputVerifier(startInputVerifier);
		startSecTextField.setInputVerifier(startInputVerifier);
		endMinTextField.setInputVerifier(endInputVerifier);
		endSecTextField.setInputVerifier(endInputVerifier);
		
		 KeyAdapter startTimeValidationListener = new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				validate();
			}

		};
		
		startMinTextField.addKeyListener(startTimeValidationListener);
		startSecTextField.addKeyListener(startTimeValidationListener);
		JLabel endMinLabel = new JLabel(SplitterLocaleContent.MIN);
		endMinLabel.setBounds(ComponentBounds.endMinLabelBounds);
		this.jPanelCrop.add(endMinLabel);
		endSecTextField.setBounds(ComponentBounds.endSecTextFieldBounds);
		endSecTextField.setMaxLength(2);
		endSecTextField.setAllowNegative(false);
		this.jPanelCrop.add(endSecTextField);
		KeyAdapter endTimeValidationListener = new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				validate();
			}
		};
		endMinTextField.addKeyListener(endTimeValidationListener);
		endSecTextField.addKeyListener(endTimeValidationListener);
		JLabel endSecLabel = new JLabel(SplitterLocaleContent.SEC);
		endSecLabel.setBounds(ComponentBounds.endSecLabelBounds);
		this.jPanelCrop.add(endSecLabel);
		this.startTimeErrorLabel = new JLabel();
		setBoundsForStartErrorLabel();
		this.startTimeErrorLabel.setForeground(Color.RED);
		this.startTimeErrorLabel.setFont(calibriFont);
		this.jPanelCrop.add(this.startTimeErrorLabel);
		this.endTimeErrorLabel = new JLabel();
		setBoundsForEndErrorLabel();
		this.endTimeErrorLabel.setForeground(Color.RED);
		this.endTimeErrorLabel.setFont(calibriFont);
		this.jPanelCrop.add(this.endTimeErrorLabel);
		JLabel outputFileLabel = new JLabel(SplitterLocaleContent.OUTPUT_FILE);
		outputFileLabel.setBounds(ComponentBounds.outputFileLabelBounds);
		this.jPanelCrop.add(outputFileLabel);
		this.splitButton = new JButton(SplitterLocaleContent.SPLIT);
		this.splitButton.setBounds(ComponentBounds.splitButtonBounds);
//		this.splitButton.setEnabled(false);
		jPanel.add(this.splitButton);
		this.splitButton.addActionListener(new ActionListener(){
			Thread thread;

			public void actionPerformed(ActionEvent e) {
				this.thread = new Thread(new Runnable(){

					public void run() {
						try {
							try {
								startTimeErrorLabel.setText("");
								endTimeErrorLabel.setText("");
								SwingUtilities.invokeLater(()->splitButton.setEnabled(false));
								if (this.timeValidation()) {
									String endTimeInputFormat = this.getTimeValue(true);
									String outputFolderPath = FileNameUtil.getOuputFolderForVS();
									CommonMethods.createDirectoryIfNotExist(outputFolderPath);
									inputStartTime = this.getTimeValue(false);
									inputEndTime = endTimeInputFormat;
									endTimeInputFormat = VideoSplitterJPanel.this.timeDiff(inputStartTime, endTimeInputFormat);
									if(outputFileNameTextField.getText().trim().equals("")){
										endTimeErrorLabel.setText(SplitterLocaleContent.OUTPUT_FILENAME_SHOULD_NOT_BE_EMPTY);
										endTimeErrorLabel.setVisible(true);
										splitButton.setEnabled(false);
										thread.interrupt();
										outputFileNameTextField.grabFocus();
										outputFileNameTextField.requestFocus();
										return;
									}else if(Common.isInValidName(outputFileNameTextField.getText())){
										endTimeErrorLabel.setText(SplitterLocaleContent.INVALID_FILE_NAME);
										endTimeErrorLabel.setVisible(true);
										splitButton.setEnabled(false);
										thread.interrupt();
										outputFileNameTextField.grabFocus();
										outputFileNameTextField.requestFocus();
										return;
									}
									String outputFileName = CommonMethods.getOutputFileName(outputFolderPath, String.valueOf(outputFileNameTextField.getText().trim()) + "_vsOut");
									String outputFilePath = String.valueOf(outputFolderPath) + outputFileName + "."+FilenameUtils.getExtension(filePath);
									String endTimeForCommand = this.setCommandEndTime(endTimeInputFormat);
//									String command = "cmd /c " + CommonMethods.FFMPEG_EXE_PATH + " -i \"" + filePath + " \" -ss " + inputStartTime + "  -t " + endTimeForCommand + " -vcodec copy \"" + outputFilePath + "\"";
									String command = "\"" +CommonMethods.FFMPEG_EXE_PATH+"\"" + " -ss "+inputStartTime +" -i \"" + filePath + "\"" +" -preset ultrafast -c:v copy -t " + endTimeForCommand + " \"" + outputFilePath + "\"";
									Runtime.getRuntime().exec(command);
									Thread.sleep(1000);
									outputFileMetaData = CommonMethods.executeFfmpegCmd(outputFilePath);
									if(VideoSplitterJPanel.this.setDataOnPanel(true)){
										JOptionPane.showMessageDialog(VideoCutter.frame, SplitterLocaleContent.SPLIT_COMPLETED, SplitterLocaleContent.SUCCESS, 1);
										try {
											Runtime.getRuntime().exec("explorer.exe /select," + FilenameUtils.normalize(outputFilePath));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}else{
										JOptionPane.showMessageDialog(VideoCutter.frame, SplitterLocaleContent.SPLIT_FAILED, SplitterLocaleContent.ERROR, 2);
									}
									closeButton.setVisible(true);
									backButton.setVisible(true);
									splitButton.setVisible(false);
									grabFocusToCloseButton();
								}
							}
							catch(FFMPEGNotFoundException e){
								AssistiveScreenshot.ffmpegNotFoundException();
							}
							catch (Exception e) {
								Common.log(e);
								System.err.println(e);
								thread.interrupt();
							}
						}
						finally {
							SwingUtilities.invokeLater(()->splitButton.setEnabled(true));
							thread.interrupt();
						}
					}

					private String setCommandEndTime(String endTimeInputFormat) {
						String[] timeFragment = endTimeInputFormat.split(":");
						int sec = Integer.parseInt(timeFragment[2]);
						int min = Integer.parseInt(timeFragment[1]);
						int seconds = ((min * 60) + sec);
						return String.valueOf(seconds);
					}

					private String getTimeValue(boolean isEndDuration) {
						if(endMinTextField.getText().length() == 1){
							endMinTextField.setText("0"+endMinTextField.getText());
						}
						if(endSecTextField.getText().length() == 1){
							endSecTextField.setText("0"+endSecTextField.getText());
						}
						if(startMinTextField.getText().length() == 1){
							startMinTextField.setText("0"+startMinTextField.getText());
						}
						if(startSecTextField.getText().length() == 1){
							startSecTextField.setText("0"+startSecTextField.getText());
						}
						if (isEndDuration) {
							return "00:" + endMinTextField.getText() + ":" + endSecTextField.getText();
						}
						return "00:" + startMinTextField.getText() + ":" + startSecTextField.getText();
					}

					private boolean timeValidation() {
						String MsgContent = "";
						String strMin = startMinTextField.getText();
						String strSec = startSecTextField.getText();
						String endMin = endMinTextField.getText();
						String endSec = endSecTextField.getText();
						if (!(strMin.isEmpty() || strSec.isEmpty() || endMin.isEmpty() || endSec.isEmpty())) {
							return this.verifyStartTime(strMin, strSec, endMin, endSec);
						}
						if (strMin.isEmpty() && strSec.isEmpty() && endMin.isEmpty() && endSec.isEmpty()) {
							MsgContent = SplitterLocaleContent.START_TIME_AND_END_TIME_SHOULD_NOT_BE_EMPTY;
						} else if (strMin.isEmpty() && strSec.isEmpty()) {
							MsgContent = SplitterLocaleContent.START_TIME_SHOULD_NOT_BE_EMPTY;
						} else if (endMin.isEmpty() && endSec.isEmpty()) {
							MsgContent = SplitterLocaleContent.END_TIME_SHOULD_NOT_BE_EMPTY;
						} else {
							if (maxMin == 0 && strMin.isEmpty() && endMin.isEmpty()) {
								startMinTextField.setText("00");
								endMinTextField.setText("00");
								return this.verifyStartTime("0", strSec, "0", endSec);
							}
							if (strMin.isEmpty()) {
								MsgContent = String.valueOf(MsgContent) + SplitterLocaleContent.ENTER_VALUE_FOR_STARTING_MINUTE+"\n";
							}
							if (strSec.isEmpty()) {
								MsgContent = String.valueOf(MsgContent) + SplitterLocaleContent.ENTER_VALUE_FOR_STARTING_SECOND+"\n";
							}
							if (endMin.isEmpty()) {
								MsgContent = String.valueOf(MsgContent) + SplitterLocaleContent.ENTER_VALUE_FOR_ENDING_MINUTE+"\n";
							}
							if (endSec.isEmpty()) {
								MsgContent = String.valueOf(MsgContent) + SplitterLocaleContent.ENTER_VALUE_FOR_ENDING_SECOND;
							}
						}
						JOptionPane.showMessageDialog(VideoCutter.frame, MsgContent, SplitterLocaleContent.ERROR, 2);
						return false;
					}

					private boolean verifyStartTime(String strMin, String strSec, String endMin, String endSec) {
						int strMinInt = Integer.parseInt(strMin);
						int strSecInt = Integer.parseInt(strSec);
						int endMinInt = Integer.parseInt(endMin);
						int endSecInt = Integer.parseInt(endSec);
						if (strMinInt > endMinInt || strSecInt >= endSecInt && strMinInt == endMinInt) {
							JOptionPane.showMessageDialog(VideoCutter.frame, SplitterLocaleContent.START_TIME_IS_GREATER_THAN_END_TIME, SplitterLocaleContent.ERROR, 2);
							return false;
						}
						return true;
					}
				});
				this.thread.start();
			}

		});
		resetButton = new JButton(SplitterLocaleContent.RESET);
		resetButton.setBounds(ComponentBounds.resetButtonBounds);
		jPanel.add(resetButton);
		this.backButton = new JButton(SplitterLocaleContent.BACK);
		this.backButton.setBounds(ComponentBounds.backButtonBounds);
		jPanel.add(this.backButton);
		closeButton = new JButton(SplitterLocaleContent.CLOSE);
		closeButton.setVisible(false);
		this.backButton.setVisible(false);
		closeButton.setBounds(ComponentBounds.closeButtonBounds);
		jPanel.add(closeButton);
		resetButton.addActionListener(new ActionListener(){
			Thread thread;

			public void actionPerformed(ActionEvent e) {
				this.thread = new Thread(new Runnable(){
					public void run() {
						startMinTextField.setText("");
						startSecTextField.setText("");
						endMinTextField.setText("");
						endSecTextField.setText("");
						startMinTextField.setBackground(Color.white);
						startMinTextField.setForeground(Color.black);
						startSecTextField.setBackground(Color.white);
						startSecTextField.setForeground(Color.black);
						endMinTextField.setBackground(Color.white);
						endMinTextField.setForeground(Color.black);
						endSecTextField.setBackground(Color.white);
						endSecTextField.setForeground(Color.black);
						startTimeErrorLabel.setVisible(false);
						endTimeErrorLabel.setVisible(false);
						endSecTextField.setText("");
						endMinTextField.setText("");
						endSecTextField.setText("");
						sourceFilePathTextField.setText("");
						VideoSplitterJPanel.this.resetExistingInputs();
					}
				});
				this.thread.start();
			}

		});
		this.backButton.addActionListener(new ActionListener(){
			Thread thread;
			public void actionPerformed(ActionEvent e) {
				this.thread = new Thread(new Runnable(){
					public void run() {
						VideoSplitterJPanel.this.jPanelCrop.setVisible(true);
						VideoSplitterJPanel.this.resultDetailsJPane.setVisible(false);
						VideoSplitterJPanel.this.backButton.setVisible(false);
						closeButton.setVisible(false);
						splitButton.setVisible(true);
						startMinTextField.requestFocus();
						startMinTextField.grabFocus();
					}
				});
				this.thread.start();
			}

		});
		closeButton.addActionListener(new ActionListener(){
			Thread thread;

			public void actionPerformed(ActionEvent e) {
				this.thread = new Thread(new Runnable(){

					public void run() {
						VideoCutter.closeVideoSplitterFrame();
					}
				});
				this.thread.start();
			}

		});
		this.resetExistingInputs();
		return jPanel;
	}

	public void validate() {
		boolean isInvalidFileName = outputFileNameValidtor();
		startTimeValidator(isInvalidFileName);
		endTimeValidatior(isInvalidFileName);
	}
	
	public void setErrorForInputFile() {
		setEnableOnPanel(false);
		setTimerNull(false);
	}
	
	public void setBoundsForEndErrorLabel() {
		this.endTimeErrorLabel.setBounds(ComponentBounds.endTimeErrorLabelBounds);
	}

	public void setBoundsForStartErrorLabel() {
		this.startTimeErrorLabel.setBounds(ComponentBounds.startTimeErrorLabelBounds);
	}

	public void setBoundsForEndLabel(JLabel endTimeLabel) {
		endTimeLabel.setBounds(ComponentBounds.endTimeLabelBounds);
	}

	public void setBoundsForStartLabel(JLabel startTimeLabel) {
		startTimeLabel.setBounds(ComponentBounds.startTimeLabelBounds);
	}
	public void setBoundsForJPanelCrop() {
		this.jPanelCrop.setBounds(ComponentBounds.jPanelCropBounds);
	}
	
	public void setBoundsForStartMin() {
		startMinTextField.setBounds(ComponentBounds.startMinTextFieldBounds);
		startMinTextField.setMaxLength(2);
		startMinTextField.setAllowNegative(false);
	}
	
	public boolean startTimeValidator(boolean isInvalidFileName) {
		if (!startMinTextField.getText().trim().isEmpty()) {
			VideoSplitterJPanel.this.checkStartMaxMin(startMinTextField.getText().trim(), endMinTextField.getText().trim(),startSecTextField.getText().trim(),endSecTextField.getText().trim());
		}
		if(startMinTextField.getBackground() == Color.white && startSecTextField.getBackground() == Color.white && endMinTextField.getBackground() == Color.white && endSecTextField.getBackground() == Color.white  && !startMinTextField.getText().isEmpty() && !startSecTextField.getText().isEmpty() && !endMinTextField.getText().isEmpty() && !endSecTextField.getText().isEmpty() && !isInvalidFileName){
			splitButton.setEnabled(true);
			return true;
		}else{
			splitButton.setEnabled(false);
			return false;
		}
	}
	
	public boolean endTimeValidatior(boolean isInvalidFileName) {
		if (!endMinTextField.getText().trim().isEmpty()) {
			VideoSplitterJPanel.this.checkEndMaxMin(startMinTextField.getText().trim(), endMinTextField.getText().trim(),endSecTextField.getText().trim(),startSecTextField.getText().trim());
		}
		if(startMinTextField.getBackground() == Color.white && startSecTextField.getBackground() == Color.white && endMinTextField.getBackground() == Color.white && endSecTextField.getBackground() == Color.white  && !startMinTextField.getText().isEmpty() && !startSecTextField.getText().isEmpty() && !endMinTextField.getText().isEmpty() && !endSecTextField.getText().isEmpty() && !isInvalidFileName){
			splitButton.setEnabled(true);
			return true;
		}else{
			splitButton.setEnabled(false);
			return false;
		}
	}

	public boolean outputFileNameValidtor() {
		if(outputFileNameTextField.getText().isEmpty()){
			endTimeErrorLabel.setText(SplitterLocaleContent.OUTPUT_FILENAME_SHOULD_NOT_BE_EMPTY);
			endTimeErrorLabel.setVisible(true);
			splitButton.setEnabled(false);
			return true;
		}
		else if(Common.isInValidName(outputFileNameTextField.getText())){
			endTimeErrorLabel.setText(SplitterLocaleContent.INVALID_FILE_NAME);
			endTimeErrorLabel.setVisible(true);
			splitButton.setEnabled(false);
			return true;
		}else if(outputFileNameTextField.getText().length() > 100){
			endTimeErrorLabel.setText(SplitterLocaleContent.FILE_NAME_SHOULD_BE_LESS_THAN_100_CHAR);
			endTimeErrorLabel.setVisible(true);
			splitButton.setEnabled(false);
			return true;
		}
		else{
			endTimeErrorLabel.setVisible(false);
			splitButton.setEnabled(true);
		}
		return false;
	}
	
	public void removeErrorFromStartSec() {
		startSecTextField.setBackground(Color.white);
		startSecTextField.setForeground(Color.black);
	}

	public void setErrorToStartSec() {
		startSecTextField.setBackground(Color.red);
		startSecTextField.setForeground(Color.white);
	}

	public void removeErrorFromStartMin() {
		startMinTextField.setBackground(Color.white);
		startMinTextField.setForeground(Color.black);
	}

	public void setErrorToStartMin() {
		startMinTextField.setBackground(Color.red);
		startMinTextField.setForeground(Color.white);
	}
	public void removeErrorFromEndSec() {
		endSecTextField.setBackground(Color.white);
		endSecTextField.setForeground(Color.black);
	}

	public void setErrorToEndSec() {
		endSecTextField.setBackground(Color.red);
		endSecTextField.setForeground(Color.white);
	}

	public void removeErrorFromEndMin() {
		endMinTextField.setBackground(Color.white);
		endMinTextField.setForeground(Color.black);
	}

	public void setErrorToEndMin() {
		endMinTextField.setBackground(Color.red);
		endMinTextField.setForeground(Color.white);
	}
	private boolean setDataOnPanel(boolean flag) {
		this.jPanelCrop.setVisible(!flag);
		this.resultDetailsJPane.setVisible(flag);
		if (flag) {
			if(this.outputFileMetaData[0] == null){
				return false;
			}
			this.outputTitleLabel.setText(SplitterLocaleContent.OUTPUT_FILE_DETAILS+" :");
			this.outputFilenameLabel.setText(SplitterLocaleContent.OUTPUT_FILE_NAME+"         " + this.outputFileMetaData[3]);this.outputFilenameLabel.setToolTipText(this.outputFileMetaData[3]);
			this.outputFileDurationLabel.setText(SplitterLocaleContent.OUTPUT_FILE_DURATION+"    " + this.outputFileMetaData[0] );
//					+ "  (" + (this.inputStartTime.length() == 1 ? 00 : this.inputStartTime) + " to " + (this.inputEndTime.length() == 1 ? 00 : this.inputEndTime) + ")");
			this.outputFileSizeLabel.setText(SplitterLocaleContent.OUTPUT_FILE_SIZE+"            " + this.outputFileMetaData[2]);
		} else {
			this.inputFileNameLabel.setText(SplitterLocaleContent.FILE_NAME+"                     " + this.inputFileMetaData[3]);this.inputFileNameLabel.setToolTipText(this.inputFileMetaData[3]);
			this.inputFileDurationLabel.setText(SplitterLocaleContent.FILE_DURATION+"                 " + this.inputFileMetaData[0]);
			this.inputFileSizeLabel.setText(SplitterLocaleContent.FILE_SIZE+"                         " + this.inputFileMetaData[2]);
		}
		return true;
	}

	private boolean checkStartMaxMin(String txtStartMinString, String txtEndMinString, String seconds, String endSecondsString) {
		this.startTimeErrorLabel.setVisible(false);
		int startMin = Integer.parseInt(String.valueOf(CommonMethods.getStringText(txtStartMinString)));
		int startSeconds = Integer.parseInt(String.valueOf(CommonMethods.getStringText(seconds)));
		int endSeconds = Integer.parseInt(String.valueOf(CommonMethods.getStringText(endSecondsString)));
		if (startSeconds > 59) {
			this.startTimeErrorLabel.setText("'" + startSeconds + "'  "+SplitterLocaleContent.IS_GREATER_THAN_THE_59);
			this.startTimeErrorLabel.setVisible(true);
			setErrorToStartSec();
			return true;
		}
		int overallSeconds = (startMin*60) + startSeconds;
		
		if(startMin> 59){
			this.startTimeErrorLabel.setText(SplitterLocaleContent.START_TIME_IS_GREATER_THAN_END_TIME);
			this.startTimeErrorLabel.setVisible(true);
			setErrorToStartMin();
			return true;
		}
		
		String errorMsg = "";
		if (overallSeconds > ((this.maxMin*60)+this.maxSec)) {
			errorMsg = "'00:"+ this.getTimeValueString(String.valueOf(startMin))+":" +this.getTimeValueString(seconds) + "' "+SplitterLocaleContent.IS_GREATER_THAN_THE_ACTUAL_END_TIME+" " + "'00:"+this.getTimeValueString(String.valueOf(this.maxMin)+":"+this.getTimeValueString(String.valueOf(this.maxSec))+"'");
		} else if (CommonMethods.isNotNullEmpty(txtEndMinString) && CommonMethods.isNotNullEmpty(endSecondsString) && CommonMethods.isNotNullEmpty(txtStartMinString) && CommonMethods.isNotNullEmpty(seconds) && ((Integer.parseInt(txtEndMinString)*60)+endSeconds) <= (overallSeconds)) {
			errorMsg = SplitterLocaleContent.START_TIME_IS_GREATER_THAN_END_TIME;
		}
		if (errorMsg.isEmpty()) {
			removeErrorFromStartMin();
			removeErrorFromStartSec();
			return false;
		}
		setErrorToStartMin();
		setErrorToStartSec();
		this.startTimeErrorLabel.setText(errorMsg);
		this.startTimeErrorLabel.setVisible(true);
		return true;
	}

	private boolean checkEndMaxMin(String txtStartMinString, String txtEndMinString,String endSecondsString, String startSecondsString) {
		int endMinute = Integer.parseInt(String.valueOf(CommonMethods.getStringText(txtEndMinString)));
		int startMinute = Integer.parseInt(String.valueOf(CommonMethods.getStringText(txtStartMinString)));
		int startSecond = Integer.parseInt(String.valueOf(CommonMethods.getStringText(startSecondsString)));
		int endSecond = Integer.parseInt(String.valueOf(CommonMethods.getStringText(endSecondsString)));
		if (endSecond > 59) {
			this.endTimeErrorLabel.setText("'" + endSecond + "'  "+SplitterLocaleContent.IS_GREATER_THAN_THE_59);
			this.endTimeErrorLabel.setVisible(true);
			setErrorToEndSec();
			return true;
		}
		if(endMinute > 59){
			this.endTimeErrorLabel.setText(SplitterLocaleContent.START_TIME_IS_GREATER_THAN_END_TIME);
			this.endTimeErrorLabel.setVisible(true);
			setErrorToEndMin();
			return true;
		}
		String errorMsg = "";
		if ((endMinute*60)+endSecond > (this.maxMin*60)+this.maxSec) {
			errorMsg = "'00:"+ this.getTimeValueString(String.valueOf(endMinute))+":" +this.getTimeValueString(endSecondsString) + "' "+SplitterLocaleContent.IS_GREATER_THAN_THE_ACTUAL_START_TIME+" " + "'00:"+this.getTimeValueString(String.valueOf(this.maxMin)+":"+this.getTimeValueString(String.valueOf(this.maxSec))+"'");
		} else if (CommonMethods.isNotNullEmpty(txtStartMinString) && CommonMethods.isNotNullEmpty(txtEndMinString) &&CommonMethods.isNotNullEmpty(startSecondsString) && CommonMethods.isNotNullEmpty(endSecondsString) && ((endMinute*60)+endSecond) <= (startMinute*60)+startSecond) {
			errorMsg = SplitterLocaleContent.START_TIME_IS_GREATER_THAN_END_TIME;
		}
		if (errorMsg.isEmpty()) {
			removeErrorFromEndMin();
			removeErrorFromEndSec();
			return false;
		}
		setErrorToEndMin();
		setErrorToEndSec();
		this.endTimeErrorLabel.setText(errorMsg);
		this.endTimeErrorLabel.setVisible(true);
		return true;
	}

	private void resetExistingInputs() {
		this.jPanelCrop.setVisible(false);
		this.inputFileNameLabel.setVisible(false);
		this.inputFileDurationLabel.setVisible(false);
		this.inputFileSizeLabel.setVisible(false);
		this.resultDetailsJPane.setVisible(false);
		this.startTimeErrorLabel.setVisible(false);
		this.endTimeErrorLabel.setVisible(false);
		this.sourceFilePathTextField.grabFocus();
		closeButton.setVisible(false);
		this.backButton.setVisible(false);
		resetButton.setVisible(false);
		this.splitButton.setVisible(false);
		grabInitialFocus();
	}

	private String timeDiff(String timeStr, String timeEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date dateEnd = null;
		Date dateStr = null;
		try {
			dateStr = sdf.parse(timeStr);
			dateEnd = sdf.parse(timeEnd);
		}
		catch (ParseException e) {
			Common.log(e);
			e.printStackTrace();
		}
		long diff = dateEnd.getTime() - dateStr.getTime();
		long diffSec = diff / 1000;
		long diffMin = diffSec / 60;
		return "00:" + this.getTimeValueString(Long.toString(diffMin)) + ":" + this.getTimeValueString(Long.toString(diffSec -= diffMin * 60));
	}

	private String getTimeValueString(String curTextInput) {
		return curTextInput.length() == 2 ? curTextInput : "0" + curTextInput;
	}
	
	public static void grabInitialFocus(){
		sourceFileChooserButton.grabFocus();
		sourceFileChooserButton.requestFocus();
	}
	
	public static void grabFocusToCloseButton(){
		closeButton.grabFocus();
		closeButton.requestFocus();
	}

}
