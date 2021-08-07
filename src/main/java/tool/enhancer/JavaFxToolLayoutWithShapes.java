package tool.enhancer;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.control.RangeSlider;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;
import tool.enhancer.locale.EnhancerLocaleContent;
import tool.ishot.AssistiveScreenshot;
import tool.ishot.Common;
import tool.splitter.CommonMethods;
import tool.splitter.FFMPEGNotFoundException;
import tool.splitter.locale.SplitterLocaleContent;

public class JavaFxToolLayoutWithShapes
extends Application {
   
	public static final Logger logger = Logger.getLogger(JavaFxToolLayoutWithShapes.class.getName());
	private static String resolution;
    static Scene scene1;
    Scene scene2;
    MediaPlayer.Status status;
    public static Stage window;
    static double currentTimeRange = 0.0;
    static int lastClickedIndex = -1;
    static double currentTimeInSecond = 0.0;
    static Double dragXField = 0.0;
    static Double dragYField = 0.0;
    public static String sourceFilePath = "";
    VBox joinVideoVBox = new VBox();
    HBox nextToVideoHBox = new HBox();
    static List<String> deleteTemp = new ArrayList<String>();
    static AtomicInteger countIterator = new AtomicInteger();
    static Map<Integer, String> textMap = new LinkedHashMap<Integer, String>();
    static Map<Integer, Text> textLabelMap = new LinkedHashMap<Integer, Text>();
    static Map<Integer, String> fontNameMap = new LinkedHashMap<Integer, String>();
    static Map<Integer, Long> translateXMap = new LinkedHashMap<Integer, Long>();
    static Map<Integer, Long> translateYMap = new LinkedHashMap<Integer, Long>();
    static Map<Integer, Integer> fontSizeMap = new LinkedHashMap<Integer, Integer>();
    static Map<Integer, Color> colorPickerMap = new LinkedHashMap<Integer, Color>();
    static Map<Integer, Double> finalValueMap = new LinkedHashMap<Integer, Double>();
    static Map<Integer, Double> initialValueMap = new LinkedHashMap<Integer, Double>();
    static Map<Integer, RangeSlider> hSliderMap = new LinkedHashMap<Integer, RangeSlider>();
    static Map<Integer, List<Node>> rectangleMap = new LinkedHashMap<Integer, List<Node>>();
    static Map<Integer, Boolean> checkIsShapeMap = new LinkedHashMap<Integer, Boolean>();
    static Map<Integer, Color> previousShapeColorMap = new LinkedHashMap<Integer, Color>();
    static Map<Integer, Integer> selectedTextIndexMap = new LinkedHashMap<Integer, Integer>();
    static Map<Integer, Pair<Boolean, String>> isSubmittedMap = new HashMap<Integer, Pair<Boolean, String>>();
    static Map<Integer, Integer> previousShapeSizeMap = new LinkedHashMap<Integer, Integer>();
    static Map<Integer, Pair<Label, ImageView>> textOrImageSliderMap = new LinkedHashMap<Integer, Pair<Label, ImageView>>();
    static Label videoTotalTimeLabel = null;
    static Label videoCurrentTimeLabel = null;
    Button exportButton = null;
    Button addButton = null;
    public static TextField destPathField = null;
    public static TextField sourcePathField = null;
    public static TextField fileLocationField = null;
    Button shapeCombo = null;
    Button previousButton = null;
    Color colorPickerValue = null;
    public static Dialog progressDialog = null;
    public static MediaPlayer mediaPlayer = null;
	protected static Background sourceTextBackground;
    Timeline blinkTimeline = null;
    MediaPlayer tempMediaPlayer;
    Media tempMedia;
    MediaView tempView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
			this.startNextPage(primaryStage, "", "", 0, false);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void startNextPage(Stage primaryStage, String filePath, String destFileName, long duration, boolean isFromCapture) throws Throwable {
    	EnhancerLocaleContent.setLocaleContent();
    	AssistiveScreenshot.initializeLogger();
    	videoCurrentTimeLabel= new Label();
    	videoTotalTimeLabel= new Label();
    	destPathField = new TextField();
        sourcePathField = new TextField();
        fileLocationField = new TextField();
    	exportButton = new Button(EnhancerLocaleContent.EXPORT);
        addButton = new Button(EnhancerLocaleContent.ADD_TEXT);
        shapeCombo = new Button(EnhancerLocaleContent.ADD_SHAPE);
        previousButton = new Button(EnhancerLocaleContent.BACK);
        this.window = primaryStage;
        this.window.setTitle(EnhancerLocaleContent.VIDEO_EDITOR);
        Platform.setImplicitExit(false);
        GridPane gridPane1 = this.createFileLocationDetails();
        this.addContentsNextToVideo();
        this.setInitialPage(gridPane1, this.window, filePath, destFileName, duration, isFromCapture);
        this.scene1 = new Scene(gridPane1, 800.0, 500.0);
        this.window.setScene(this.scene1);
        this.window.setMaxWidth(1350.0);
        this.window.setMinWidth(1350);
        this.window.setWidth(1400.0);
        this.window.setMinHeight(800);
        this.window.setHeight(1050.0);
        this.window.setMaxHeight(1050);
        this.window.setX(300.0);
        this.window.setY(0.0);
        this.window.show();
//        gridPane1.setStyle("-fx-background-color: #D3D3D3;");first page color
        this.window.getIcons().add(new Image(this.getClass().getResourceAsStream("images/VideoEditor.png")));
        if (filePath != null && !filePath.isEmpty()) {
            this.deleteTemp.add(filePath.replace(".mp4", ".wmv"));
        }
        primaryStage.setOnCloseRequest(event -> {
        	if(!exportButton.isDisabled()){
        		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, EnhancerLocaleContent.DO_YOU_WANT_TO_EXIT, new ButtonType[0]);
        		Button exitButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        		Button cancelButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        		exitButton.setText("Exit");
        		exitButton.setCursor(Cursor.HAND);
        		cancelButton.setCursor(Cursor.HAND);
        		closeConfirmation.setHeaderText(EnhancerLocaleContent.CONFIRMATION);
        		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        		closeConfirmation.initOwner(this.window);
        		closeConfirmation.setX(this.window.getX() + 500.0);
        		closeConfirmation.setY(this.window.getY() + 400.0);
        		Optional closeResponse = closeConfirmation.showAndWait();
        		if (!ButtonType.OK.equals(closeResponse.get())) {
        			event.consume();
        		} else {
        			exitApp();
        		}
        	}else{
        		exitApp();
        	}
        }
        );
    }

	public void exitApp() {
		if(mediaPlayer != null)
			mediaPlayer.dispose();
		this.deleteTemp.forEach(action -> {
			File file = new File(action);
			try {
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				);
		JavaFxToolLayoutWithShapes.window.hide();
		this.window.setScene(this.scene1);
	}

    private void addContentsNextToVideo() {
        GridPane nextToVideoGridPane = new GridPane();
        this.previousButton.setPrefHeight(40);
        this.previousButton.setPrefWidth(120);
        this.previousButton.setMaxHeight(40);
        this.previousButton.setMaxWidth(120);
        this.previousButton.setCancelButton(true);
        this.previousButton.setCursor(Cursor.HAND);
//		JavaFxToolLayoutWithShapes.addImageToButton("images/backbutton.png",previousButton);
        nextToVideoGridPane.add(this.previousButton, 0, 0);
        nextToVideoGridPane.setPadding(new Insets(8.0, 10.0, 10.0, 10.0));
        Label headerLabel = new Label("");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15.0));
        nextToVideoGridPane.add(headerLabel, 0, 2);
        GridPane.setHalignment(headerLabel, HPos.LEFT);
        GridPane.setMargin(headerLabel, new Insets(20.0, 0.0, 20.0, 0.0));
        this.addButton.setPrefWidth(120.0);
        this.addButton.setPrefHeight(40.0);
        this.addButton.setMaxWidth(120.0);
        this.addButton.setMaxHeight(40.0);
        this.addButton.setCursor(Cursor.HAND);
        nextToVideoGridPane.add(this.addButton, 0, 4);
        Label firstEmptylabel = new Label(" ");
        nextToVideoGridPane.add(firstEmptylabel, 0, 5);
        this.shapeCombo.setPrefWidth(120.0);
        this.shapeCombo.setPrefHeight(40.0);
        this.shapeCombo.setMaxWidth(120.0);
        this.shapeCombo.setMaxHeight(40.0);
        this.shapeCombo.setCursor(Cursor.HAND);
        nextToVideoGridPane.add(this.shapeCombo, 0, 6);
        Label secondEmptylabel = new Label(" ");
        nextToVideoGridPane.add(secondEmptylabel, 0, 7);
        this.exportButton.setPrefWidth(120.0);
        this.exportButton.setPrefHeight(40.0);
        this.exportButton.setMaxWidth(120.0);
        this.exportButton.setMaxHeight(40.0);
        this.exportButton.setCursor(Cursor.HAND);
        this.exportButton.setDisable(true);
//        this.exportButton.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), null)));
        this.exportButton.setDefaultButton(true);
//        this.exportButton.setTextFill(Color.WHITE);
        nextToVideoGridPane.add(this.exportButton, 0, 8);
        HBox nexttovideoHBox = new HBox();
        nexttovideoHBox.getChildren().add(nextToVideoGridPane);
        this.nextToVideoHBox = nexttovideoHBox;
    }

    private static void addImageToButton(String imageString, Button button) {
//			BufferedImage image = AssistiveScreenshot.resizeImage(ImageIO.read(JavaFxToolLayoutWithShapes.class.getResourceAsStream(imageString)), 2, 20,20);
//			ImageView iv = new ImageView(SwingFXUtils.toFXImage(image, null ));
//			button.setGraphic(iv);
	}

	private void setExport(MediaPlayer mediaPlayer) throws FileNotFoundException {
		exportButton.setDisable(true);
        this.textSlider();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderForShape = new StringBuilder();
        AtomicInteger iteratorIndex = new AtomicInteger();
        AtomicInteger iteratorIndexForShape = new AtomicInteger();
        drawBox(stringBuilderForShape, iteratorIndexForShape);
        String shapeFilter = stringBuilderForShape.toString();
        drawText(stringBuilder, iteratorIndex);
        String textFilter = stringBuilder.toString();
        String sourceFilePathSplit = this.sourcePathField.getText();
        char charFromText = sourceFilePathSplit.charAt(0);
        String sourcefilePath = sourceFilePathSplit.replace("\\", "/");
        if (charFromText == '/') {
            sourcefilePath = sourcefilePath.replaceFirst("/", "");
        }
        String outputFileName = this.fileLocationField.getText();
        String destDirectoryPathSplit = this.destPathField.getText();
        String destDirectoryPath = destDirectoryPathSplit.replaceFirst("/", "");
        String destPath = destDirectoryPath.replace("\\", "/");
        if (!destDirectoryPath.endsWith("/")) {
            destPath = destPath.concat("/");
        }
        String outputFilePath = destPath.concat(outputFileName).concat(".").concat("mp4");
        String filters = "";
        if (textFilter != null && !textFilter.isEmpty()) {
            filters = (shapeFilter != null && !shapeFilter.isEmpty()) ? String.valueOf(shapeFilter) + "," + textFilter : textFilter;
        } else if (shapeFilter != null && !shapeFilter.isEmpty()) {
            filters = shapeFilter;
        }
//        String ffmpeg = " -y -i \"" + sourcefilePath + "\"" + " " + "-vf" + " " + "\"" + "scale=1220x700 , " + filters + "\"" + " " + "-codec:v libx264 -crf 18 -preset ultrafast" + " " + "-vsync 0" + " " + "\"" + outputFilePath + "\"";
		try{
        	
     String[] metadata = CommonMethods.executeFfmpegCmd(sourcefilePath);
   	 String bitrate = " ";
   	 if(metadata.length >= 5){
   		 bitrate = " -b:v "+metadata[4].split("kb/s")[0].trim() +"k ";
   	 }
        String ffmpeg = " -y -i \"" + sourcefilePath + "\"" + " " + "-filter_complex" + " " + "\"" + "[0:v]" + filters + "\"" + " -crf 30 -preset ultrafast -r 30 "+bitrate+ "\"" + outputFilePath + "\"";
        this.executeTool(ffmpeg,true);
        closeProgressDialog(outputFilePath,true,mediaPlayer);
        }catch(FFMPEGNotFoundException e){
        	JavaFxToolLayoutWithShapes.ffmpegNotFoundException(this.window);
        	showExportFailedMessage();
        }
    }

	public void drawBox(StringBuilder stringBuilderForShape, AtomicInteger iteratorIndexForShape) {
		if (MapUtils.isNotEmpty(this.rectangleMap)) {
            this.rectangleMap.entrySet().stream().forEach(nodeMap -> {
                Rectangle rectangleNode = (Rectangle)((List)nodeMap.getValue()).get(0);
                rectangleNode.getX();
                rectangleNode.getY();
                rectangleNode.getWidth();
                rectangleNode.getHeight();
                rectangleNode.getStroke();
                rectangleNode.getStrokeWidth();
                double initialValue = this.initialValueMap.get(nodeMap.getKey());
                double finalValue = this.finalValueMap.get(nodeMap.getKey());
                String[] res = JavaFxToolLayoutWithShapes.resolution.split("x");
                Double resolutionY = 0.0;
                if(res.length > 1 && res[1].split(" ") != null){
                	resolutionY = Double.valueOf(res[1].split(" ")[0]);
                }
                String drawbox = "drawbox=x=" + ((rectangleNode.getX()/1220.0)*Double.valueOf(res[0])) + ":" + "y=" + ((rectangleNode.getY()/700.0)*Double.valueOf(resolutionY)) + ":" + "w=" + ((rectangleNode.getWidth()/1220.0)*Double.valueOf(res[0])) + ":" + "h=" + ((rectangleNode.getHeight()/700.0)*Double.valueOf(resolutionY)) + ":" + "color=" + rectangleNode.getStroke() + ":" + "thickness=" + rectangleNode.getStrokeWidth() + ":" + "enable='between(t," + initialValue + "," + finalValue + ")" + "'";
//                String boxblue = ",boxblur=10[bg];[0:v]crop=x=" + ((rectangleNode.getX()/1220.0)*Double.valueOf(res[0])-5) + ":" + "y=" + ((rectangleNode.getY()/700.0)*Double.valueOf(resolutionY)-5) + ":" + "w=" + ((rectangleNode.getWidth()/1220.0)*Double.valueOf(res[0])+5) + ":" + "h=" + ((rectangleNode.getHeight()/700.0)*Double.valueOf(resolutionY)+5) + "[fg];[bg][fg]overlay="+((rectangleNode.getX()/1220.0)*Double.valueOf(res[0])+5.0)+":"+((rectangleNode.getY()/700.0)*Double.valueOf(resolutionY)+5.0+":" + "enable='between(t," + initialValue + "," + finalValue + ")" + "'");
            	stringBuilderForShape.append(drawbox);
                if (this.rectangleMap.size() - 1 > iteratorIndexForShape.get()) {
                    stringBuilderForShape.append(",");
                }
                iteratorIndexForShape.getAndIncrement();
            }
            );
        }
	}

	public void drawText(StringBuilder stringBuilder, AtomicInteger iteratorIndex) {
		if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
            this.selectedTextIndexMap.values().stream().forEach(selectedTextIndex -> {
                String selectedText = this.textMap.get(selectedTextIndex);
                String fontName = this.fontNameMap.get(selectedTextIndex);
                String ttfFontName = EnumFont.getTTFFontName(fontName);
                Integer fontSize = this.fontSizeMap.get(selectedTextIndex);
                Color colorPicked = this.colorPickerMap.get(selectedTextIndex);
                Long translateX = this.translateXMap.get(selectedTextIndex);
                Long translateY = this.translateYMap.get(selectedTextIndex);
                String escapedText = selectedText;
                if (selectedText.contains("'") || selectedText.contains("\"") || selectedText.contains("\\")) {
                    String backSlashText = selectedText.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
                    String singleQuoteText = backSlashText.replaceAll("'", "'\\\\\\\\\\\\''");
                    escapedText = singleQuoteText.replaceAll("\"", "'\\\\\\\\\\\\\"'");
                }
                double initialValue = this.initialValueMap.get(selectedTextIndex);
                double finalValue = this.finalValueMap.get(selectedTextIndex);
                String fontFile = "'C\\:\\\\Windows\\\\Fonts\\\\" + ttfFontName + ".ttf" + "'";
                String[] res = JavaFxToolLayoutWithShapes.resolution.split("x");
                Double resolutionY = 0.0;
                if(res.length > 1 && res[1].split(" ") != null){
                	resolutionY = Double.valueOf(res[1].split(" ")[0]);
                }
                String drawText = "drawtext=fontfile=" + fontFile + ":" + "fontsize=" + (fontSize*1.60)+ ":" + "fontcolor=" + colorPicked 
                						+ ":" + "text=" + "'" + escapedText + "'" + ":" + "x=" + ((translateX/1220.0)*Double.valueOf(res[0])) 
                							+ ":" + "y=(" + ((translateY/650.0)*Double.valueOf(resolutionY))+"-th)" + ":" 
                								+ "enable='between(t," + initialValue + "," + finalValue + ")" + "'";
                stringBuilder.append(drawText);
                if (this.textMap.size() - 1 > iteratorIndex.get()) {
                    stringBuilder.append(",");
                }
                iteratorIndex.getAndIncrement();
            }
            );
        }
	}

    public void getVideoFile(String sourcefilePath, Pane textOrShapePane, HBox topHbox, VBox mainVbox, VBox belowleftVBox){
        if (sourcefilePath.isEmpty()) {
            this.sourceFilePath = this.sourcePathField.getText();
        }
        String sourceSplitPath = this.sourceFilePath.replace("\\", "/");
        StackPane stackPane = new StackPane();
        HBox timerHBox = new HBox();
        File file = new File(sourceSplitPath);
        String url = file.toURI().toString();
        MediaView view = new MediaView();
        Media media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        view.setMediaPlayer(mediaPlayer);
        view.setFitWidth(1220.0);
        view.setFitHeight(700.0);
        
        HBox mediaviewHbox = new HBox();
        mediaviewHbox.getChildren().addAll(view);
        mediaviewHbox.setPrefWidth(1220.0);
        mediaviewHbox.setMinWidth(1220.0);
        mediaviewHbox.setMaxHeight(1220.0);
        Slider timeSlider = new Slider();
        timeSlider.setCache(true);
        timeSlider.setCacheHint(CacheHint.SPEED);
        this.totalAndCurrentDuration(mediaPlayer, timeSlider);
        mediaPlayer.setOnPlaying(() -> {
            if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
                this.selectedTextIndexMap.values().stream().forEach(selectTextIndex -> {
                    this.textLabelMap.get(selectTextIndex).setVisible(false);
                }
                );
            }
            if (MapUtils.isNotEmpty(this.rectangleMap)) {
                this.rectangleMap.entrySet().stream().forEach(nodes -> {
                    this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                        node.setVisible(false);
                    }
                    );
                }
                );
            }
        }
        );
        mediaPlayer.setOnError(() -> {
        	this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
        	this.window.setScene(this.scene1);
        	throw mediaPlayer.getError();
        });
        	
        ImageView playImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/icons8-play-64.png")));
        playImage.setFitWidth(21.0);
        playImage.setFitHeight(21.0);
        playImage.setCursor(Cursor.HAND);
        playImage.setVisible(true);
        ImageView pauseImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/icons8-pause-64.png")));
        pauseImage.setFitWidth(21.0);
        pauseImage.setFitHeight(21.0);
        pauseImage.setCursor(Cursor.HAND);
        pauseImage.setVisible(false);
        ImageView stopImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/icons8-rewind-64.png")));
        stopImage.setFitWidth(21.0);
        stopImage.setFitHeight(21.0);
        stopImage.setCursor(Cursor.HAND);
        stopImage.setVisible(true);
        ImageView restartImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/icons8-replay-64.png")));
        restartImage.setFitWidth(21.0);
        restartImage.setFitHeight(21.0);
        restartImage.setCursor(Cursor.HAND);
        restartImage.setVisible(true);
        HBox videoPlayStopHBox = new HBox(5.0);
        videoPlayStopHBox.setPadding(new Insets(4.0, 5.0, 4.0, 0.0));
        videoPlayStopHBox.getChildren().addAll(stopImage, restartImage);
        HBox playAndPauseHBox = new HBox();
        playAndPauseHBox.setPadding(new Insets(4.0, 5.0, 4.0, 5.0));
        playAndPauseHBox.getChildren().add(playImage);
        HBox timesliderHBox = new HBox();
        timesliderHBox.setPrefHeight(12.0);
        timesliderHBox.setAlignment(Pos.CENTER);
        timesliderHBox.setStyle("-fx-background-color: #070707;");
        timesliderHBox.setPrefWidth(1220.0);
        timeSlider.setMinWidth(982.0);
        InvalidationListener sliderChangeListener = event -> {
            Duration seekTo = Duration.seconds(timeSlider.getValue());
            mediaPlayer.seek(seekTo);
        };
        timeSlider.valueProperty().addListener(sliderChangeListener);
        mediaPlayer.currentTimeProperty().addListener(event -> {
            timeSlider.valueProperty().removeListener(sliderChangeListener);
            this.currentTimeInSecond = mediaPlayer.getCurrentTime().toSeconds();
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            double currentTimeValue = Double.parseDouble(numberFormat.format(this.currentTimeInSecond));
            int currentTimeForSlider = (int)currentTimeValue;
            timeSlider.setValue((int)currentTimeValue);
            this.currentTimeRange = timeSlider.getValue();
            if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
                this.selectedTextIndexMap.values().stream().forEach(selectTextIndex -> {
                    String initialValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getLowValue());
                    String finalValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getHighValue());
                    double lowSliderValue = Double.parseDouble(initialValue);
                    double highSliderValue = Double.parseDouble(finalValue);
                    if ((double)currentTimeForSlider > lowSliderValue && (double)currentTimeForSlider <= highSliderValue) {
                        this.textLabelMap.get(selectTextIndex).setVisible(true);
                    } else {
                        this.textLabelMap.get(selectTextIndex).setVisible(false);
                    }
                }
                );
            }
            this.rectangleMap.entrySet().stream().forEach(nodes -> {
                String initialValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getLowValue());
                String finalValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getHighValue());
                double lowSliderValue = Double.parseDouble(initialValue);
                double highSliderValue = Double.parseDouble(finalValue);
                if (currentTimeValue > lowSliderValue && currentTimeValue <= highSliderValue) {
                    this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                        node.setVisible(true);
                    }
                    );
                } else {
                    this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                        node.setVisible(false);
                    }
                    );
                }
            }
            );
            timeSlider.valueProperty().addListener(sliderChangeListener);
            String getLabel = this.currentTimeLabel(currentTimeValue);
            this.videoCurrentTimeLabel.setText(getLabel);
        }
        );
        Label timeSeperatorLabel = new Label("/");
        timeSeperatorLabel.setStyle("-fx-text-fill: #f8f8ff;-fx-font-size: 15px;-fx-font-weight: 600;");
        this.videoCurrentTimeLabel.setStyle("-fx-text-fill: #f8f8ff;-fx-font-size: 12px;-fx-font-weight: 600;");
        this.videoTotalTimeLabel.setStyle("-fx-text-fill: #f8f8ff;-fx-font-size: 12px;-fx-font-weight: 600;");
        timesliderHBox.getChildren().addAll(playAndPauseHBox, videoPlayStopHBox, timeSlider, this.videoCurrentTimeLabel, timeSeperatorLabel, this.videoTotalTimeLabel);
        stackPane.setOnMouseEntered(event -> {
            this.status = mediaPlayer.getStatus();
            if (this.status == MediaPlayer.Status.READY || this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
                timesliderHBox.setVisible(true);
            }
            this.joinVideoVBox.setOnMouseEntered(events -> {
                if (this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
                	timesliderHBox.setVisible(true);
                }
            }
            );
        }
        );
        stackPane.setOnMouseExited(event -> {
            this.status = mediaPlayer.getStatus();
            if (this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
                timesliderHBox.setVisible(true);
            }
        }
        );
        playImage.setOnMouseClicked(event -> {
            this.setPlayImage(mediaPlayer, playImage, pauseImage, playAndPauseHBox, timeSeperatorLabel, timeSlider);
        }
        );
        playImage.setOnMouseEntered(event -> {
            Tooltip playToolTip = new Tooltip(EnhancerLocaleContent.PLAY);
            playToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(playImage, playToolTip);
        }
        );
        playImage.setOnMousePressed(event -> {
            playImage.setFitHeight(18.0);
            playImage.setFitWidth(18.0);
        }
        );
        playImage.setOnMouseReleased(event -> {
            playImage.setFitHeight(21.0);
            playImage.setFitWidth(21.0);
        }
        );
        pauseImage.setOnMouseClicked(event -> {
            this.setPauseImage(timerHBox, mediaPlayer, playImage, pauseImage, playAndPauseHBox, timeSlider);
        }
        );
        pauseImage.setOnMouseEntered(event -> {
            Tooltip pauseToolTip = new Tooltip(EnhancerLocaleContent.PAUSE);
            pauseToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(pauseImage, pauseToolTip);
        }
        );
        pauseImage.setOnMousePressed(event -> {
            pauseImage.setFitHeight(18.0);
            pauseImage.setFitWidth(18.0);
        }
        );
        pauseImage.setOnMouseReleased(event -> {
            pauseImage.setFitHeight(21.0);
            pauseImage.setFitWidth(21.0);
        }
        );
        stopImage.setOnMouseClicked(event -> {
            timerHBox.setVisible(true);
            mediaPlayer.seek(Duration.ONE);
            mediaPlayer.stop();
            this.status = mediaPlayer.getStatus();
            if (this.status == MediaPlayer.Status.PLAYING) {
                playAndPauseHBox.getChildren().remove(pauseImage);
                playAndPauseHBox.getChildren().add(playImage);
                playImage.setVisible(true);
                pauseImage.setVisible(false);
            }
        }
        );
        stopImage.setOnMouseEntered(event -> {
            Tooltip stopToolTip = new Tooltip(EnhancerLocaleContent.STOP);
            stopToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(stopImage, stopToolTip);
        }
        );
        stopImage.setOnMousePressed(event -> {
            stopImage.setFitHeight(18.0);
            stopImage.setFitWidth(18.0);
        }
        );
        stopImage.setOnMouseReleased(event -> {
            stopImage.setFitHeight(21.0);
            stopImage.setFitWidth(21.0);
        }
        );
        restartImage.setOnMouseClicked(event -> {
            timeSeperatorLabel.setVisible(true);
            mediaPlayer.seek(Duration.ONE);
            mediaPlayer.play();
            this.videoTotalDuration(mediaPlayer);
            this.status = mediaPlayer.getStatus();
            try {
                timeSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
            }
            catch (NullPointerException exception) {
                JavaFxToolLayoutWithShapes.log("Invalid Time Slider,search ## in code", exception);//##
            }
            if (this.status == MediaPlayer.Status.READY || this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
                if (playAndPauseHBox.getChildren().get(0) != pauseImage) {
                    playAndPauseHBox.getChildren().add(pauseImage);
                }
                playAndPauseHBox.getChildren().remove(playImage);
                playImage.setVisible(false);
                pauseImage.setVisible(true);
            }
        }
        );
        restartImage.setOnMouseEntered(event -> {
            Tooltip restartToolTip = new Tooltip(EnhancerLocaleContent.RESTART);
            restartToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(restartImage, restartToolTip);
        }
        );
        restartImage.setOnMousePressed(event -> {
            restartImage.setFitHeight(18.0);
            restartImage.setFitWidth(18.0);
        }
        );
        restartImage.setOnMouseReleased(event -> {
            restartImage.setFitHeight(21.0);
            restartImage.setFitWidth(21.0);
        }
        );
        this.joinVideoVBox = new VBox();
        this.joinVideoVBox.setOnMouseEntered(event -> {
            this.status = mediaPlayer.getStatus();
            if (this.status == MediaPlayer.Status.READY || this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
                timerHBox.setVisible(true);
            }
        }
        );
        this.joinVideoVBox.setOnMouseExited(event -> {
            this.status = mediaPlayer.getStatus();
            if (this.status == MediaPlayer.Status.PLAYING) {
                timerHBox.setVisible(false);
            }
        }
        );
        this.scene2.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                if (playImage.isVisible()) {
                    this.setPlayImage(mediaPlayer, playImage, pauseImage, playAndPauseHBox, timeSeperatorLabel, timeSlider);
                } else {
                    this.setPauseImage(timerHBox, mediaPlayer, playImage, pauseImage, playAndPauseHBox, timeSlider);
                }
            }
            keyEvent.consume();
        }
        );
        this.previousButton.setOnAction(actionEvent -> {
        	if(!exportButton.isDisabled()){
        		 Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, EnhancerLocaleContent.ARE_YOU_SURE_YOU_WANT_TO_GO_BACK, new ButtonType[0]);
                 Button exitButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                 exitButton.setText(EnhancerLocaleContent.CONFIRM);
                 closeConfirmation.setHeaderText(EnhancerLocaleContent.THIS_ACTION_WILL_DISCARD_CURRENT_CHANGES);
                 closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                 closeConfirmation.initOwner(this.window);
                 closeConfirmation.setX(this.window.getX() + 500.0);
                 closeConfirmation.setY(this.window.getY() + 400.0);
                 Optional closeResponse = closeConfirmation.showAndWait();
                 if (ButtonType.OK.equals(closeResponse.get())) {
                	 goBack();
                 }
        	}else{
        		goBack();
        	}
        }
        );
        this.exportButton.setOnAction(actionEvent -> {
        	try {
        		this.setExport(mediaPlayer);
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        	}
        }
        );
        this.joinVideoVBox.setAlignment(Pos.TOP_CENTER);
        this.joinVideoVBox.setPadding(new Insets(4.0, 4.0, 0.0, 3.0));
        stackPane.getChildren().add(view);
        stackPane.getChildren().add(textOrShapePane);
        this.joinVideoVBox.getChildren().add(stackPane);
        this.joinVideoVBox.getChildren().addAll(mediaviewHbox, timesliderHBox);
        this.joinVideoVBox.setMaxWidth(1220.0);
        String cssLayout = "-fx-background-color: darkgray;\n";
        this.joinVideoVBox.setStyle(cssLayout);
        Pair<MediaPlayer, Slider> playerPair = new Pair<MediaPlayer, Slider>(mediaPlayer, timeSlider);
        GridPane secondGridPane = this.createTextDetails();
    	this.addContentsBelowVideo(secondGridPane, playerPair, belowleftVBox, textOrShapePane);
    	topHbox.getChildren().addAll(this.joinVideoVBox, this.nextToVideoHBox);
    	mainVbox.getChildren().addAll(topHbox, belowleftVBox);
    	if(progressDialog != null){
    		progressDialog.close();
    	}
    	this.window.setScene(this.scene2);
    }

	public static void goBack() {
		dragXField = 0.0;
		dragYField = 0.0;
		countIterator = new AtomicInteger();
		currentTimeRange = 0.0;
		lastClickedIndex = -1;
		currentTimeInSecond = 0.0;
//            sourcePathField.setText(sourcePathField.getText().replace(".wmv", ".mp4"));
		videoCurrentTimeLabel = new Label();
		videoTotalTimeLabel = new Label();
		deleteTemp = new ArrayList<String>();
		countIterator = new AtomicInteger();
		textMap = new LinkedHashMap<Integer, String>();
		textLabelMap = new LinkedHashMap<Integer, Text>();
		fontNameMap = new LinkedHashMap<Integer, String>();
		translateXMap = new LinkedHashMap<Integer, Long>();
		translateYMap = new LinkedHashMap<Integer, Long>();
		fontSizeMap = new LinkedHashMap<Integer, Integer>();
		colorPickerMap = new LinkedHashMap<Integer, Color>();
		finalValueMap = new LinkedHashMap<Integer, Double>();
		initialValueMap = new LinkedHashMap<Integer, Double>();
		hSliderMap = new LinkedHashMap<Integer, RangeSlider>();
		rectangleMap = new LinkedHashMap<Integer, List<Node>>();
		checkIsShapeMap = new LinkedHashMap<Integer, Boolean>();
		previousShapeColorMap = new LinkedHashMap<Integer, Color>();
		selectedTextIndexMap = new LinkedHashMap<Integer, Integer>();
		isSubmittedMap = new HashMap<Integer, Pair<Boolean, String>>();
		previousShapeSizeMap = new LinkedHashMap<Integer, Integer>();
		textOrImageSliderMap = new LinkedHashMap<Integer, Pair<Label, ImageView>>();
		mediaPlayer.stop();
		window.setScene(scene1);
	}

    public void showExportFailedMessage() {
    	Alert exportConfirmation = new Alert(Alert.AlertType.INFORMATION);
    	exportConfirmation.setTitle(EnhancerLocaleContent.EXPORT);
    	exportConfirmation.setHeaderText(EnhancerLocaleContent.EXPORT_FAILED);
    	exportConfirmation.initModality(Modality.APPLICATION_MODAL);
    	exportConfirmation.initOwner(this.window);
    	exportConfirmation.setX(this.window.getX() + 500.0);
    	exportConfirmation.setY(this.window.getY() + 400.0);
    	exportConfirmation.show();
    }

	public void showExportSuccessMessage(MediaPlayer mediaPlayer) {
		Alert exportConfirmation = new Alert(Alert.AlertType.INFORMATION);
		exportConfirmation.setTitle(EnhancerLocaleContent.EXPORT);
		exportConfirmation.setHeaderText(EnhancerLocaleContent.EXPORTED_TO + " " + this.destPathField.getText());
		exportConfirmation.initModality(Modality.APPLICATION_MODAL);
		exportConfirmation.initOwner(this.window);
		exportConfirmation.setX(this.window.getX() + 500.0);
		exportConfirmation.setY(this.window.getY() + 400.0);
		exportConfirmation.show();
//		mediaPlayer.seek(Duration.ONE);
//		mediaPlayer.play();
//		AtomicInteger iteratorIndex = new AtomicInteger(1);
//		KeyFrame[] arrkeyFrame = new KeyFrame[1];
//		arrkeyFrame[0] = new KeyFrame(Duration.seconds(1.0), ev -> {
//			if (iteratorIndex.get() == 1) {
//				mediaPlayer.pause();
//			}
//			iteratorIndex.getAndIncrement();
//		}
//		, new KeyValue[0]);
//		Timeline timeline = new Timeline(arrkeyFrame);
//		timeline.setCycleCount(1);
//		timeline.play();
	}

    private void setPauseImage(HBox timerHBox, MediaPlayer mediaPlayer, ImageView playImage, ImageView pauseImage, HBox playAndPauseHBox, Slider timeSlider) {
        timerHBox.setVisible(true);
        mediaPlayer.pause();
        this.status = mediaPlayer.getStatus();
        if (this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
            playAndPauseHBox.getChildren().remove(pauseImage);
            playAndPauseHBox.getChildren().add(playImage);
            timeSlider.setValue((int)mediaPlayer.getCurrentTime().toSeconds());
            this.currentTimeRange = timeSlider.getValue();
            playImage.setVisible(true);
            pauseImage.setVisible(false);
        }
    }

    private void setPlayImage(MediaPlayer mediaPlayer, ImageView playImage, ImageView pauseImage, HBox playAndPauseHBox, Label timeSeperatorLabel, Slider timeSlider) {
        timeSeperatorLabel.setVisible(true);
        mediaPlayer.play();
        this.videoTotalDuration(mediaPlayer);
        try {
            timeSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
        }
        catch (NullPointerException exception) {
        	JavaFxToolLayoutWithShapes.log("Invalid Time Slider,search ### in code", exception);//###
        }
        this.status = mediaPlayer.getStatus();
        if (this.status == MediaPlayer.Status.READY || this.status == MediaPlayer.Status.PLAYING || this.status == MediaPlayer.Status.PAUSED || this.status == MediaPlayer.Status.STOPPED) {
            playAndPauseHBox.getChildren().remove(playImage);
            playAndPauseHBox.getChildren().add(pauseImage);
            timeSlider.setValue((int)mediaPlayer.getCurrentTime().toSeconds());
            this.currentTimeRange = timeSlider.getValue();
            playImage.setVisible(false);
            pauseImage.setVisible(true);
        }
    }

    private String currentTimeLabel(double currentTime) {
        int secondsLeft = (int)(currentTime % 3600.0 % 60.0);
        int minutes = (int)Math.floor(currentTime % 3600.0 / 60.0);
        int hours = (int)Math.floor(currentTime / 3600.0);
        String hh = hours < 10 ? "0" + hours : String.valueOf(hours);
        String mm = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        String ss = secondsLeft < 10 ? "0" + secondsLeft : String.valueOf(secondsLeft);
        return String.valueOf(hh) + ":" + mm + ":" + ss;
    }

    private void textAction() {
        if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
            this.selectedTextIndexMap.values().stream().forEach(selectTextIndex -> {
                this.textLabelMap.get(selectTextIndex).setOnMousePressed(mouseEvent -> {
                    if (mouseEvent.getSceneX() > 1220.0 || mouseEvent.getSceneX() < 01.0 || mouseEvent.getSceneY() > 700.0 || mouseEvent.getSceneY() < 4.0) {
                        return;
                    }
                    this.dragXField = this.textLabelMap.get(selectTextIndex).getTranslateX() - mouseEvent.getSceneX();
                    this.dragYField = this.textLabelMap.get(selectTextIndex).getTranslateY() - mouseEvent.getSceneY();
                    this.textLabelMap.get(selectTextIndex).setCursor(Cursor.MOVE);
                    if(blinkTimeline != null){
                    	blinkTimeline.stop();
                    	this.textLabelMap.get(selectTextIndex).setStroke(null);
                    }
                }
                );
                this.textLabelMap.get(selectTextIndex).setOnMouseReleased(mouseEvent -> {
                    if (mouseEvent.getSceneX() > 1220.0 || mouseEvent.getSceneX() < 01.0 || mouseEvent.getSceneY() > 700.0 || mouseEvent.getSceneY() < 4.0) {
                        return;
                    }
                    this.textLabelMap.get(selectTextIndex).setCursor(Cursor.HAND);
                }
                );
                this.textLabelMap.get(selectTextIndex).setOnMouseDragged(mouseEvent -> {
                    if (mouseEvent.getSceneX() > 1220.0 || mouseEvent.getSceneX() < 01.0 || mouseEvent.getSceneY() > 700.0 || mouseEvent.getSceneY() < 4.0) {
                        return;
                    }
                    this.textLabelMap.get(selectTextIndex).setTranslateX(mouseEvent.getSceneX() + this.dragXField);
                    this.textLabelMap.get(selectTextIndex).setTranslateY(mouseEvent.getSceneY() + this.dragYField);
                    long translateX = Math.round(this.textLabelMap.get(selectTextIndex).getTranslateX() + 40.0);
                    long translateY = Math.round(this.textLabelMap.get(selectTextIndex).getTranslateY() + 256.0);
                    this.translateXMap.put(selectTextIndex, translateX);
                    this.translateYMap.put(selectTextIndex, translateY);
                }
                );
                this.textLabelMap.get(selectTextIndex).setOnMouseEntered(mouseEvent -> {
                    if (mouseEvent.getSceneX() > 1220.0 || mouseEvent.getSceneX() < 01.0 || mouseEvent.getSceneY() > 700.0 || mouseEvent.getSceneY() < 4.0) {
                        return;
                    }
                    this.textLabelMap.get(selectTextIndex).setCursor(Cursor.HAND);
                }
                );
            }
            );
        }
    }

    private void textSlider() {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        double currentTimeValue = Double.parseDouble(numberFormat.format(this.currentTimeInSecond));
        if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
            this.selectedTextIndexMap.values().stream().forEach(selectTextIndex -> {
                String initialValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getLowValue());
                String finalValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getHighValue());
                double lowSliderValue = Double.parseDouble(initialValue);
                double highSliderValue = Double.parseDouble(finalValue);
                this.initialValueMap.put(selectTextIndex, lowSliderValue);
                this.finalValueMap.put(selectTextIndex, highSliderValue);
            }
            );
        }
        this.rectangleMap.entrySet().stream().forEach(nodes -> {
            String initialValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getLowValue());
            String finalValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getHighValue());
            double lowSliderValue = Double.parseDouble(initialValue);
            double highSliderValue = Double.parseDouble(finalValue);
            if (currentTimeValue > lowSliderValue && currentTimeValue <= highSliderValue) {
                this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                    node.setVisible(true);
                }
                );
            } else {
                this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                    node.setVisible(false);
                }
                );
            }
            this.initialValueMap.put((Integer)nodes.getKey(), lowSliderValue);
            this.finalValueMap.put((Integer)nodes.getKey(), highSliderValue);
        }
        );
    }

    private void videoTotalDuration(MediaPlayer mediaPlayer) {
        String getLabel = this.currentTimeLabel(mediaPlayer.getTotalDuration().toSeconds());
        this.videoTotalTimeLabel.setText(getLabel);
    }

    private void totalAndCurrentDuration(MediaPlayer mediaPlayer, Slider timeSlider) {
        mediaPlayer.totalDurationProperty().addListener(event -> {
            String getLabel = this.currentTimeLabel(mediaPlayer.getTotalDuration().toSeconds());
            this.videoTotalTimeLabel.setText(getLabel);
            timeSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));
        }
        );
        mediaPlayer.cycleDurationProperty().addListener(event -> {
            String getLabel = this.currentTimeLabel(mediaPlayer.getCurrentTime().toSeconds());
            this.videoCurrentTimeLabel.setText(getLabel);
        }
        );
        InvalidationListener sliderChangeListener = event -> {
            Duration seekTo = Duration.seconds(timeSlider.getValue());
            mediaPlayer.seek(seekTo);
            String getLabel = this.currentTimeLabel(seekTo.toSeconds());
            this.videoCurrentTimeLabel.setText(getLabel);
            this.currentTimeRange = timeSlider.getValue();
        };
        timeSlider.valueProperty().addListener(sliderChangeListener);
        mediaPlayer.currentTimeProperty().addListener(event -> {
            timeSlider.valueProperty().removeListener(sliderChangeListener);
            this.currentTimeInSecond = mediaPlayer.getCurrentTime().toSeconds();
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            double currentTimeValue = Double.parseDouble(numberFormat.format(this.currentTimeInSecond));
            timeSlider.setValue((int)currentTimeValue);
        }
        );
    }

    private GridPane createFileLocationDetails() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40.0, 40.0, 40.0, 40.0));
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(120.0, 100.0, 100.0);
        columnOneConstraints.setHalignment(HPos.LEFT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(100.0, 100.0, 700.0);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    private GridPane createTextDetails() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20.0, 20.0, 20.0, 10.0));
        gridPane.setHgap(20.0);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(20, 20, 20);
        columnOneConstraints.setHalignment(HPos.LEFT);
        gridPane.getColumnConstraints().addAll(columnOneConstraints);
        return gridPane;
    }

    private void addContentsBelowVideo(GridPane gridPane, Pair<MediaPlayer, Slider> playerPair, VBox belowleftVBox, Pane textOrShapePane) {
        LinkedHashMap closeImageMap = new LinkedHashMap();
        LinkedHashMap settingImageMap = new LinkedHashMap();
        Image textImageFilePath = new Image(this.getClass().getResourceAsStream("images/textLabel.jpg"));
        Image shapeImageFilePath = new Image(this.getClass().getResourceAsStream("images/shape.jpg"));
        this.addButton.setOnAction(actionEvent -> {
            this.exportButton.setDisable(true);
            this.isSubmittedMap.put(this.countIterator.get(), new Pair<Boolean, String>(false, "Text"));
            ImageView imageView = this.generateSlider(gridPane, playerPair, textImageFilePath, closeImageMap, settingImageMap, false, textOrShapePane);
            imageView.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
            		imageView.getX(), imageView.getY(), imageView.getX(), imageView.getY(), MouseButton.PRIMARY, 1,
                    true, true, true, true, true, true, true, true, true, true, null));
         }
        );
        this.shapeCombo.setOnMouseClicked(event -> {
            ImageView imageView = addShape(gridPane, playerPair, textOrShapePane, closeImageMap, settingImageMap, shapeImageFilePath);
            imageView.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
            		imageView.getX(), imageView.getY(), imageView.getX(), imageView.getY(), MouseButton.PRIMARY, 1,
                    true, true, true, true, true, true, true, true, true, true, null));
        }
        );
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMaxWidth(1228.0);
        scrollPane.setPrefHeight(275.0);
        scrollPane.setStyle("-fx-border-color: black;\n;"+"-fx-border-width: 2;\n;");
        belowleftVBox.getChildren().add(scrollPane);
    }

	public ImageView addShape(GridPane gridPane, Pair<MediaPlayer, Slider> playerPair, Pane textOrShapePane,
			LinkedHashMap closeImageMap, LinkedHashMap settingImageMap, Image shapeImageFilePath) {
		Circle resizeHandleNW = new Circle(8.0, Color.DARKRED);
		Circle resizeHandleSE = new Circle(8.0, Color.DARKRED);
		Circle moveHandle = new Circle(8.0, Color.DARKRED);
		Rectangle rectangle = this.createDraggableRectangle(resizeHandleNW, resizeHandleSE, moveHandle);
		rectangle.setFill(null);
		rectangle.setStroke(Color.BLACK);
		rectangle.setId(String.valueOf(this.countIterator.get()));
		rectangle.setX(550.0);
		rectangle.setY(220.0);
		this.rectangleMap.put(this.countIterator.get(), Arrays.asList(rectangle, resizeHandleNW, resizeHandleSE, moveHandle));
		textOrShapePane.getChildren().add(this.rectangleMap.get(this.countIterator.get()).get(0));
		this.exportButton.setDisable(false);
		this.isSubmittedMap.put(this.countIterator.get(), new Pair<Boolean, String>(true, "Shape"));
		return this.generateSlider(gridPane, playerPair, shapeImageFilePath, closeImageMap, settingImageMap, true, textOrShapePane);
	}

    @SuppressWarnings("unchecked")
	private ImageView generateSlider(GridPane gridPane, Pair<MediaPlayer, Slider> playerPair, Image shapeImageFilePath, Map<Integer, ImageView> closeImageMap, Map<Integer, ImageView> settingImageMap, boolean isShape, Pane textOrShapePane) {
        MediaPlayer mediaPlayer = playerPair.getKey();
        Slider timeSlider = playerPair.getValue();
        Label textSplitLabel = new Label(String.format("%8s", ""));
        if (isShape) {
            this.checkIsShapeMap.put(this.countIterator.get(), true);
            ImageView textView = new ImageView(shapeImageFilePath);
            textView.setId(String.valueOf(this.countIterator.get()));
            textView.setFitWidth(21.0);
            textView.setFitHeight(21.0);
            textView.setVisible(true);
            this.textOrImageSliderMap.put(Integer.valueOf(this.countIterator.get()), new Pair<Label, ImageView>(null, textView));
            gridPane.add(this.textOrImageSliderMap.get(this.countIterator.get()).getValue(), 0, this.countIterator.get());
        } else {
            this.textOrImageSliderMap.put(this.countIterator.get(), new Pair<Label, ImageView>(textSplitLabel, null));
            Text text = new Text();
            text.setId(String.valueOf(this.countIterator.get()));
            this.textLabelMap.put(this.countIterator.get(), text);
            this.selectedTextIndexMap.put(this.countIterator.get(), this.countIterator.get());
            this.checkIsShapeMap.put(this.countIterator.get(), false);
            gridPane.add(this.textOrImageSliderMap.get(this.countIterator.get()).getKey(), 0, this.countIterator.get());
        }
        this.isSubmittedMap.entrySet().stream().filter(pairValue -> ((String)((Pair)pairValue.getValue()).getValue()).contains("Text")).forEach(pairValue -> {
            Label isTextPresent = this.textOrImageSliderMap.get(pairValue.getKey()).getKey();
            if (isTextPresent.getText().contains(String.format("%8s", ""))) {
                this.exportButton.setDisable(true);
            } else {
                this.exportButton.setDisable(false);
            }
        }
        );
        Duration totalDuration = mediaPlayer.getTotalDuration();
        int totalTime  = 0;
        if(totalDuration !=null)
        totalTime = (int)totalDuration.toSeconds();
        RangeSlider hSlider = new RangeSlider(0.0, totalTime, this.currentTimeRange == totalTime ? this.currentTimeRange -1.0 : this.currentTimeRange, this.currentTimeRange != totalTime ? this.currentTimeRange + 1.0 : this.currentTimeRange);
        hSlider.setShowTickMarks(true);
        hSlider.setShowTickLabels(true);
        if(totalTime<26){
        	hSlider.setMajorTickUnit(1.0);
        }
        hSlider.setPrefWidth(1045.5);
        hSlider.setPadding(new Insets(0,0,0,63));
        StringConverter<Number> stringConverter = new StringConverter<Number>() {

            @Override
            public String toString(Number object) {
                long seconds = object.longValue();
                long minutes = TimeUnit.SECONDS.toMinutes(seconds);
                long remainingseconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
                return String.format("%02d", minutes) + ":" + String.format("%02d", remainingseconds);
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        };
        hSlider.setLabelFormatter(stringConverter);
        this.hSliderMap.put(this.countIterator.get(), hSlider);
        gridPane.add(this.hSliderMap.get(this.countIterator.get()), 1, this.countIterator.get());
        ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("images/widget.png")));
        imageView.setId(String.valueOf(this.countIterator.get()));
        imageView.setFitWidth(21.0);
        imageView.setFitHeight(21.0);
        imageView.setVisible(true);
        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseEntered(event -> {
            Tooltip settingToolTip = new Tooltip(EnhancerLocaleContent.SETTINGS);
            settingToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(imageView, settingToolTip);
        }
        );
        imageView.setOnMousePressed(event -> {
            imageView.setFitHeight(18.0);
            imageView.setFitWidth(18.0);
        }
        );
        imageView.setOnMouseReleased(event -> {
            imageView.setFitHeight(20.0);
            imageView.setFitWidth(20.0);
        }
        );
        settingImageMap.put(this.countIterator.get(), imageView);
        gridPane.add(settingImageMap.get(this.countIterator.get()), 2, this.countIterator.get());
        ImageView closeImage = addCloseImage(gridPane, closeImageMap, settingImageMap, textOrShapePane);
        imageView.setOnMouseClicked(event -> {
            if (((ImageView)event.getSource()).getId() != null && !((ImageView)event.getSource()).getId().isEmpty()) {
                this.lastClickedIndex = Integer.parseInt(((ImageView)event.getSource()).getId());
            }
            if (isShape) {
                this.dialogForShape(timeSlider,closeImage);
            } else {
                String previousText = this.textLabelMap.get(this.lastClickedIndex).getText();
                String previousFontName = this.fontNameMap.get(this.lastClickedIndex);
                Integer previousFontSize = this.fontSizeMap.get(this.lastClickedIndex);
                Color previousFontColor = this.colorPickerMap.get(this.lastClickedIndex);
                this.dialogPopUp(previousText, previousFontName, previousFontSize, previousFontColor, textOrShapePane, timeSlider,closeImage);
                this.textAction();
            }
        }
        );
        this.countIterator.getAndIncrement();
        return imageView;
    }

	public ImageView addCloseImage(GridPane gridPane, Map<Integer, ImageView> closeImageMap,
			Map<Integer, ImageView> settingImageMap, Pane textOrShapePane) {
		ImageView closeimage = new ImageView(new Image(this.getClass().getResourceAsStream("images/closeIcon.jpg")));
        closeimage.setFitWidth(21.0);
        closeimage.setId(String.valueOf(this.countIterator.get()));
        closeimage.setFitHeight(21.0);
        closeimage.setVisible(true);
        closeimage.setCursor(Cursor.HAND);
        closeimage.setOnMouseEntered(event -> {
            Tooltip settingToolTip = new Tooltip(EnhancerLocaleContent.CLOSE);
            settingToolTip.setStyle("-fx-background-color: #ffa500;-fx-text-fill: black;-fx-font-weight: bold;");
            Tooltip.install(closeimage, settingToolTip);
        }
        );
        closeimage.setOnMouseClicked(event -> {
            triggerCloseButton(gridPane, closeImageMap, settingImageMap, textOrShapePane, event);
        }
        );
        closeImageMap.put(this.countIterator.get(), closeimage);
        gridPane.add(closeImageMap.get(this.countIterator.get()), 3, this.countIterator.get());
        return closeimage;
	}

	public void triggerCloseButton(GridPane gridPane, Map<Integer, ImageView> closeImageMap,
			Map<Integer, ImageView> settingImageMap, Pane textOrShapePane, MouseEvent event) {
		if (((ImageView)event.getSource()).getId() != null && !((ImageView)event.getSource()).getId().isEmpty()) {
		    String idValue = ((ImageView)event.getSource()).getId();
		    int count = Integer.parseInt(idValue);
		    Boolean isShapeSlider = this.checkIsShapeMap.get(count);
		    AtomicInteger integer = new AtomicInteger();
		    while (integer.get() < this.isSubmittedMap.size()) {
		        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node).intValue() == integer.get());
		        integer.getAndIncrement();
		    }
		    if (this.isSubmittedMap.size() - 1 != count) {
		        this.isSubmittedMap.remove(count);
		    }
		    HashMap<Integer, Pair<Boolean, String>> tempMap = new HashMap<Integer, Pair<Boolean, String>>();
		    Iterator<Map.Entry<Integer, Pair<Boolean, String>>> localSubmitIterator = this.isSubmittedMap.entrySet().iterator();
		    if (isShapeSlider.booleanValue()) {
		        List list = textOrShapePane.getChildren().stream().filter(node -> Integer.parseInt(node.getId()) == count).collect(Collectors.toList());
		        list.stream().forEach(index -> {
		            boolean bl = textOrShapePane.getChildren().remove(index);
		        }
		        );
		    } else if (MapUtils.isNotEmpty(this.textLabelMap)) {
		        textOrShapePane.getChildren().remove(this.textLabelMap.remove(count));
		    }
		    while (localSubmitIterator.hasNext()) {
		        Map.Entry<Integer, Pair<Boolean, String>> localEntry = localSubmitIterator.next();
		        if (localEntry.getKey() > count) {
		            tempMap.put(localEntry.getKey() - 1, localEntry.getValue());
		            this.textOrImageSliderMap.put(localEntry.getKey() - 1, this.textOrImageSliderMap.get(localEntry.getKey()));
		            if (Objects.nonNull(this.textOrImageSliderMap.get(localEntry.getKey() - 1).getKey())) {
		                gridPane.add(this.textOrImageSliderMap.get(localEntry.getKey() - 1).getKey(), 0, localEntry.getKey() - 1);
		            } else {
		                gridPane.add(this.textOrImageSliderMap.get(localEntry.getKey() - 1).getValue(), 0, localEntry.getKey() - 1);
		            }
		            this.hSliderMap.put(localEntry.getKey() - 1, this.hSliderMap.get(localEntry.getKey()));
		            settingImageMap.put(localEntry.getKey() - 1, (ImageView)settingImageMap.get(localEntry.getKey()));
		            this.textLabelMap.put(localEntry.getKey() - 1, this.textLabelMap.get(localEntry.getKey()));
		            if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
		                this.selectedTextIndexMap.put(localEntry.getKey() - 1, this.selectedTextIndexMap.get(localEntry.getKey()));
		                this.selectedTextIndexMap.put(localEntry.getKey(), null);
		            }
		            if (MapUtils.isNotEmpty(this.rectangleMap)) {
		                this.rectangleMap.put(localEntry.getKey() - 1, this.rectangleMap.get(localEntry.getKey()));
		                this.rectangleMap.put(localEntry.getKey(), null);
		            }
		            ((ImageView)settingImageMap.get(localEntry.getKey() - 1)).setId(String.valueOf(localEntry.getKey() - 1));
		            this.hSliderMap.put(localEntry.getKey(), null);
		            settingImageMap.put(localEntry.getKey(), null);
		            this.textOrImageSliderMap.put(localEntry.getKey(), null);
		            this.textLabelMap.put(localEntry.getKey(), null);
		            gridPane.add(this.hSliderMap.get(localEntry.getKey() - 1), 1, localEntry.getKey() - 1);
		            gridPane.add((Node)settingImageMap.get(localEntry.getKey() - 1), 2, localEntry.getKey() - 1);
		            gridPane.add((Node)closeImageMap.get(localEntry.getKey() - 1), 3, localEntry.getKey() - 1);
		            continue;
		        }
		        if (localEntry.getKey() == count) {
		            this.hSliderMap.put(localEntry.getKey(), null);
		            settingImageMap.put(localEntry.getKey(), null);
		            this.textOrImageSliderMap.put(localEntry.getKey(), null);
		            this.textLabelMap.put(localEntry.getKey(), null);
		            if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
		                this.selectedTextIndexMap.put(localEntry.getKey(), null);
		            }
		            if (!MapUtils.isNotEmpty(this.rectangleMap)) continue;
		            this.rectangleMap.put(localEntry.getKey(), null);
		            continue;
		        }
		        tempMap.put(localEntry.getKey(), localEntry.getValue());
		        if (Objects.nonNull(this.textOrImageSliderMap.get(localEntry.getKey()).getKey())) {
		            gridPane.add(this.textOrImageSliderMap.get(localEntry.getKey()).getKey(), 0, localEntry.getKey());
		        } else {
		            gridPane.add(this.textOrImageSliderMap.get(localEntry.getKey()).getValue(), 0, localEntry.getKey());
		        }
		        ((ImageView)settingImageMap.get(localEntry.getKey())).setId(String.valueOf(localEntry.getKey()));
		        gridPane.add(this.hSliderMap.get(localEntry.getKey()), 1, localEntry.getKey());
		        gridPane.add((Node)settingImageMap.get(localEntry.getKey()), 2, localEntry.getKey());
		        gridPane.add((Node)closeImageMap.get(localEntry.getKey()), 3, localEntry.getKey());
		        this.textLabelMap.put(localEntry.getKey(), this.textLabelMap.get(localEntry.getKey()));
		        if (MapUtils.isNotEmpty(this.selectedTextIndexMap)) {
		            this.selectedTextIndexMap.put(localEntry.getKey(), this.selectedTextIndexMap.get(localEntry.getKey()));
		        }
		        if (!MapUtils.isNotEmpty(this.rectangleMap)) continue;
		        this.rectangleMap.put(localEntry.getKey(), this.rectangleMap.get(localEntry.getKey()));
		    }
		    if (this.isSubmittedMap.size() - 1 == count) {
		        this.isSubmittedMap.remove(count);
		    }
		    this.textLabelMap.values().stream().filter(Objects::nonNull).forEach(textNode -> {
		        if (Integer.parseInt(textNode.getId()) >= count) {
		            textNode.setId(String.valueOf(Integer.parseInt(textNode.getId()) - 1));
		        } else {
		            textNode.setId(textNode.getId());
		        }
		    }
		    );
		    Map tempRectangleMap = new LinkedHashMap<Integer, List<Node>>();
		    if (MapUtils.isNotEmpty(this.rectangleMap)) {
		        tempRectangleMap = this.rectangleMap.entrySet().stream().filter(rectangle -> CollectionUtils.isNotEmpty((Collection)rectangle.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		    }
		    this.rectangleMap = tempRectangleMap;
		    this.selectedTextIndexMap = this.selectedTextIndexMap.entrySet().stream().filter(textIndex -> Objects.nonNull(textIndex.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getKey));
		    this.isSubmittedMap = tempMap;
		    this.exportButton.setDisable(false);
		    Optional<Boolean> booleanOptional = this.isSubmittedMap.values().stream().map(Pair::getKey).filter(pairValue -> pairValue == false).findFirst();
		    if (this.isSubmittedMap.size() == 0 || booleanOptional.isPresent() && !booleanOptional.get().booleanValue()) {
		        this.exportButton.setDisable(true);
		    }
		    textOrShapePane.getChildren().stream().forEach(node -> {
		        if (Integer.parseInt(node.getId()) >= count) {
		            node.setId(String.valueOf(Integer.parseInt(node.getId()) - 1));
		        } else {
		            node.setId(node.getId());
		        }
		    }
		    );
		    this.checkIsShapeMap.remove(count);
		    LinkedHashMap<Integer, Boolean> localShapeMap = new LinkedHashMap<Integer, Boolean>();
		    for (Map.Entry<Integer, Boolean> localEntry : this.checkIsShapeMap.entrySet()) {
		        if (localEntry.getKey() >= count) {
		            localShapeMap.put(localEntry.getKey() - 1, this.checkIsShapeMap.get(localEntry.getKey()));
		            continue;
		        }
		        localShapeMap.put(localEntry.getKey(), this.checkIsShapeMap.get(localEntry.getKey()));
		    }
		    this.checkIsShapeMap = localShapeMap;
		}
		this.countIterator.getAndDecrement();
	}

    private void setInitialPage(GridPane gridPane, Stage primaryStage, String filePath, String destFileName, long duration, boolean isFromCapture) throws Throwable {
        Label headerLabel = new Label(EnhancerLocaleContent.VIDEO_EDITOR);
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
        gridPane.add(headerLabel, 0, 0, 3, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20.0, 0.0, 20.0, 0.0));
        Label sourcePathLabel = new Label(EnhancerLocaleContent.SOURCE_FILE+" : ");
        gridPane.add(sourcePathLabel, 0, 1);
        this.sourcePathField.setPrefHeight(40.0);
        String filePathNew = filePath.replace("\\", "/");
        this.sourceFilePath = filePath;
        this.sourcePathField.setText(filePathNew);
        this.sourcePathField.setPromptText("eg : D:/videos/VideoFile.mp4");
        this.sourcePathField.setEditable(false);
        this.sourcePathField.setFocusTraversable(false);
        
//        setDragAndDropToSourceText();
        
        gridPane.add(this.sourcePathField, 1, 1);
        ImageView fileImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/fileImage.jpg")));
        fileImage.setFitWidth(20.0);
        fileImage.setFitHeight(20.0);
        Button sourcePathButton = new Button(EnhancerLocaleContent.CHOOSE_FILE);
        sourcePathButton.setPrefHeight(40.0);
        sourcePathButton.setPrefWidth(120.0);
        sourcePathButton.setCursor(Cursor.HAND);
        gridPane.add(sourcePathButton, 2, 1);
        Label fileLocationLabel = new Label(EnhancerLocaleContent.OUTPUT_FILE_NAME+" : ");
        gridPane.add(fileLocationLabel, 0, 2);
        this.fileLocationField.setPrefHeight(40.0);
        this.fileLocationField.setText(destFileName.replace("\\", "/"));
        this.fileLocationField.setPromptText("eg : OutputFileName");
        gridPane.add(this.fileLocationField, 1, 2);
        Label destPathLabel = new Label(EnhancerLocaleContent.DESTINATION_PATH+" : ");
        gridPane.add(destPathLabel, 0, 3);
        this.destPathField.setPrefHeight(40.0);
        setDestinationPath();
        this.destPathField.setPromptText("eg : D:/edited/videos/Develop");
        this.destPathField.setFocusTraversable(false);
        this.destPathField.setEditable(false);
        gridPane.add(this.destPathField, 1, 3);
        ImageView folderImage = new ImageView(new Image(this.getClass().getResourceAsStream("images/folder.png")));
        folderImage.setFitWidth(20.0);
        folderImage.setFitHeight(20.0);
        Button destPathButton = new Button(EnhancerLocaleContent.CHOOSE_DIRECTORY);
//        Button destPathButton = new Button("Directory Path", null);
        destPathButton.setPrefHeight(40.0);
        destPathButton.setPrefWidth(120.0);
        destPathButton.setCursor(Cursor.HAND);
        gridPane.add(destPathButton, 2, 3);
        Button submitButton = new Button(EnhancerLocaleContent.LAUNCH_EDITOR);
        submitButton.setDefaultButton(true);
        submitButton.setPrefHeight(40.0);
        submitButton.setPrefWidth(100.0);
        submitButton.setCursor(Cursor.HAND);
        gridPane.add(submitButton, 0, 5, 3, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20.0, 0.0, 20.0, 0.0));
        AtomicBoolean isCapture = new AtomicBoolean();
        if (isFromCapture) {
            isCapture.set(true);
        } else {
            isCapture.set(false);
        }
        submitButton.setOnAction(actionEvent -> {
        	this.exportButton.setDisable(true);
        	String name = this.sourcePathField.getText(); 
            processInputFile(duration, submitButton, isCapture, name);
        }
        );
        sourcePathButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(EnhancerLocaleContent.SELECT_SOURCE_FILE);
            if(Objects.nonNull(sourcePathField.getText()) && !sourcePathField.getText().isEmpty()){
            	fileChooser.setInitialDirectory(new File(FilenameUtils.getFullPath(sourcePathField.getText())));
            }else{
            	fileChooser.setInitialDirectory(new File("."));
            }
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(EnhancerLocaleContent.VIDEO_FILES, "*.mp4","*.wmv");
            fileChooser.getExtensionFilters().add(extFilter);
            try {
                File openDialog = fileChooser.showOpenDialog(primaryStage);
                if (Objects.nonNull(openDialog)) {
                    String sourceFile = openDialog.toURI().toURL().getPath();
                   checkAndConvertInputFile(sourceFile);
                }
            }
            catch (MalformedURLException exception) {
                JavaFxToolLayoutWithShapes.log("Invalid File Chooser ",exception);
            }
        }
        ); 
        destPathButton.setOnAction(actionEvent -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(EnhancerLocaleContent.OPEN_RESOURCE_DIRECTORY);
            if(Objects.nonNull(destPathField.getText()) && !destPathField.getText().isEmpty()){
            	directoryChooser.setInitialDirectory(new File(FilenameUtils.getFullPath(destPathField.getText())));
            }else{
            	directoryChooser.setInitialDirectory(new File("."));
            }
            try {
                File destFile = directoryChooser.showDialog(primaryStage);
                if (Objects.nonNull(this.destPathField.getText()) && !this.destPathField.getText().isEmpty() && Objects.nonNull(destFile)) {
                    Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, EnhancerLocaleContent.DO_YOU_WANT_TO_CHANGE_DESTINATION_PATH, new ButtonType[0]);
                    Button exitButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                    exitButton.setText(EnhancerLocaleContent.CONFIRM);
                    closeConfirmation.setHeaderText(EnhancerLocaleContent.CONFIRMATION_TO_CHANGE_PATH);
                    closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                    closeConfirmation.initOwner(this.window);
                    closeConfirmation.setX(this.window.getX() + 500.0);
                    closeConfirmation.setY(this.window.getY() + 400.0);
                    Optional closeResponse = closeConfirmation.showAndWait();
                    if (ButtonType.OK.equals(closeResponse.get())) {
                        String sourceFile = destFile.toURI().toURL().getPath();
                        String fileWithSpace = sourceFile.replace("%20", " ");
                        this.destPathField.setText(fileWithSpace.replace("\\", "/"));
                    }
                }else if(Objects.nonNull(destFile)){
                	String sourceFile = destFile.toURI().toURL().getPath();
                    String fileWithSpace = sourceFile.replace("%20", " ");
                    this.destPathField.setText(fileWithSpace.replace("\\", "/"));
                }
            }
            catch (MalformedURLException exception) {
                JavaFxToolLayoutWithShapes.log("Invalid File Chooser ",exception);
            }
        }
        );
    }

	public static void setDestinationPath() throws Throwable {
		String temp = new File("").getAbsolutePath().concat("\\properties\\capture.txt");
        if (new File(temp).exists()) {
            try {
                Throwable throwable = null;
                Object var17_18 = null;
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(temp));
                    try {
                        String storedDestPath = bufferedReader.readLine();
                        destPathField.setText(storedDestPath);
                    }
                    finally {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    }
                }
                catch (Throwable var17_19) {
                    if (throwable == null) {
                        throwable = var17_19;
                    } else if (throwable != var17_19) {
                        throwable.addSuppressed(var17_19);
                    }
                    throw throwable;
                }
            }
            catch (IOException iOException) {
                JavaFxToolLayoutWithShapes.log("Runtime Exception : ", iOException);//##
            }
        }else{
        	destPathField.setText("");
        }
	}

	public void setDragAndDropToSourceText() {
		sourcePathField.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                	 String fileExtension = FilenameUtils.getExtension(event.getDragboard().getFiles().get(0).getPath());
                	 if(fileExtension.equals("wmv") || fileExtension.equals("mp4")){
                		 checkAndConvertInputFile(db.getFiles().get(0).getPath());
                	 }
                }
                event.consume();
            }});
        
        sourcePathField.setOnDragEntered(new EventHandler
                
                <DragEvent>() {
                 public void handle(DragEvent event) {
                     if (event.getGestureSource() != sourcePathField &&
                             event.getDragboard().hasFiles()) {
                    	 String fileExtension = FilenameUtils.getExtension(event.getDragboard().getFiles().get(0).getPath());
                    	 if(fileExtension.equals("wmv") || fileExtension.equals("mp4")){
                    		 event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    		 JavaFxToolLayoutWithShapes.sourceTextBackground = sourcePathField.getBackground();
                    		 sourcePathField.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), null)));
                    		 Platform.runLater(()->sourcePathField.setCursor(Cursor.CLOSED_HAND));
                    	 }else{
                    		 JavaFxToolLayoutWithShapes.sourceTextBackground = sourcePathField.getBackground();
                    		 sourcePathField.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, new CornerRadii(10), null)));
                    		 sourcePathField.setText("");
                    	 }
                     }
                     event.consume();
                 }
             });
        
        sourcePathField.setOnDragExited(new EventHandler
                
                <DragEvent>() {
                 public void handle(DragEvent event) {
                     if (event.getGestureSource() != sourcePathField &&
                             event.getDragboard().hasFiles()) {
                    	 sourcePathField.setBackground(JavaFxToolLayoutWithShapes.sourceTextBackground);
                     }
                     
                     event.consume();
                 }
             });
        
        sourcePathField.setOnDragDetected(e-> {
            Dragboard db = sourcePathField.startDragAndDrop(TransferMode.MOVE);
            sourcePathField.setCursor(Cursor.HAND);
            e.consume();
        });

        sourcePathField.setOnDragDone(event->{
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, do something and reset Cursor */
                if (event.getTransferMode() == TransferMode.MOVE) {                        
                    //b.setCursor(Cursor.DEFAULT);
                }
                sourcePathField.setCursor(Cursor.DEFAULT);
                event.consume();
        });
	}

	public void checkAndConvertInputFile(String sourceFile) {
		String filePathWithSpace = sourceFile.replace("%20", " ");
		String ffmpegFilePath = filePathWithSpace.replaceFirst("/", "");
		if (ffmpegFilePath.contains(".wmv") || ffmpegFilePath.contains(".WMV") || ffmpegFilePath.contains(".avi")) {
			try{
				String[] metadata = CommonMethods.executeFfmpegCmd(ffmpegFilePath);
				String bitrate = " ";
				if(metadata.length == 5 && metadata[4] != null){
					bitrate = " -b:v "+metadata[4].split("kb/s")[0].trim() +"k ";
				}
				if(metadata[1] != null){
					JavaFxToolLayoutWithShapes.resolution= metadata[1];
				}
				if(metadata[1] != null){
					String[] res = metadata[1].split("x");
					if(Integer.parseInt(res[0].trim()) <= 1920 && Integer.parseInt(res[1].trim()) <= 1080){
						String format = ffmpegFilePath.contains(".wmv") ? ffmpegFilePath.replace(".wmv", ".mp4") : (ffmpegFilePath.contains(".WMV") ? ffmpegFilePath.replace(".WMV", ".mp4") : ffmpegFilePath.replace(".avi", ".mp4"));
						String ffmpeg = " -y -i \"" + ffmpegFilePath + "\"" + " " + "-crf 25 -preset ultrafast -r 30" + bitrate + "\"" + format + "\"";
						this.executeTool(ffmpeg,false);
						closeProgressDialog(format, false,null);
						this.sourceFilePath = format;
						this.deleteTemp.add(format);
					}else{
						this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
						return;
					}
				}else{
					this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
					return;
				}
			}catch(FFMPEGNotFoundException e){
				this.sourcePathField.setText("");
				JavaFxToolLayoutWithShapes.ffmpegNotFoundException(this.window);
			}
		} else {
			try{
				String[] metadata = CommonMethods.executeFfmpegCmd(ffmpegFilePath);
				if(metadata[1] != null){
					JavaFxToolLayoutWithShapes.resolution= metadata[1];
					if(metadata[1] != null){
						String[] res = metadata[1].split("x");
						Double resolutionY = 0.0;
		                if(res.length > 1 && res[1].split(" ") != null){
		                	resolutionY = Double.valueOf(res[1].split(" ")[0]);
		                }
						if(Integer.parseInt(res[0].trim()) <= 1920 && resolutionY <= 1080){
							this.sourceFilePath = ffmpegFilePath;
						}else{
							this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
							return;
						}
					}else{
						this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
						return;
					}
				}else{
					this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.THIS_FILE_ISN_T_PLAYABLE_THAT_MIGHT_BE_BECAUSE_THE_FILE_TYPE_IS_UNSUPPORTED_THE_FILE_EXTENSION_IS_INCORRECT_OR_THE_FILE_IS_CORRUPT);
					return;
				}
			}catch(FFMPEGNotFoundException e){
				this.sourcePathField.setText("");
				JavaFxToolLayoutWithShapes.ffmpegNotFoundException(this.window);
			}
		}
		if(mediaPlayer != null)
		mediaPlayer.dispose();
		this.sourcePathField.setText(filePathWithSpace);
		this.fileLocationField.requestFocus();
	}

	public void processInputFile(long duration, Button submitButton, AtomicBoolean isCapture, String name) {
		if (!Common.isInValidName(name)) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.PLEASE_PROVIDE_PROPER_FILE_PATH);
		} else if (name.isEmpty()) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.PLEASE_SELECT_FILE_PATH);
		} else if (this.fileLocationField.getText().isEmpty()) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.FILENAME_SHOULD_NOT_BE_EMPTY);
		}else if (Common.isInValidName(this.fileLocationField.getText())) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.INVALID_FILENAME);
		}else if (!((this.destPathField.getText() == null) || this.destPathField.getText().isEmpty() || this.destPathField.getText().contains("/") || this.destPathField.getText().contains("\\"))) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.PLEASE_PROVIDE_PROPER_DESTINATION_PATH);
		} else if ((this.destPathField.getText() != null) && !this.destPathField.getText().isEmpty() && !new File(this.destPathField.getText()).isDirectory()) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.WRONG_DESTINATION_PATH);
		} else if (!name.isEmpty() && !new File(name).isFile()) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.WRONG_SOURCE_PATH);
		} else if ((this.destPathField.getText() == null) || this.destPathField.getText().isEmpty()) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.PLEASE_SELECT_DESTINATION_PATH);
		} else if (!(name.isEmpty() || name.contains(".wmv") || name.contains(".WMV") || name.contains(".avi") || name.contains(".mp4"))) {
		    this.showAlert(Alert.AlertType.ERROR, this.window, EnhancerLocaleContent.ERROR, EnhancerLocaleContent.PLEASE_SELECT_MP4_OR_WMV_EXTENSIONS);
		} else if (isCapture.get() || name.toLowerCase().contains(".wmv") || name.toLowerCase().contains(".avi")) {
			Platform.runLater(()->submitButton.setDisable(true));
			showLoading(true);
			int seconds;
			if (!isCapture.get()) {
				long localDuration = new File(name).length();
				seconds = Integer.parseInt(String.valueOf(localDuration / 1000000));
			} else {
				seconds = Integer.parseInt(String.valueOf(duration / 1000000));
			}
			int localSeconds = seconds < 2 ? 3 : seconds;
			isCapture.set(false);
			AtomicInteger iteratorIndex = new AtomicInteger(1);
			KeyFrame[] arrkeyFrame = new KeyFrame[1];
			arrkeyFrame[0] = new KeyFrame(Duration.seconds(1.0), ev -> {
				if (iteratorIndex.get() == localSeconds) {
					progressDialog.close();
					Pane textOrShapePane = new Pane();
					VBox belowleftVBox = new VBox();
					HBox topHbox = new HBox();
					VBox mainVbox = new VBox();
					mainVbox.setStyle("-fx-background-color: gray");
					belowleftVBox.setStyle("-fx-background-color: gray");
					topHbox.setStyle("-fx-background-color: gray");
					this.scene2 = new Scene(mainVbox, 1024.0, 986.0);
					belowleftVBox.setPrefWidth(1220.0);
					belowleftVBox.setMinWidth(1220.0);
					belowleftVBox.setPadding(new Insets(8.0, 10.0, 10.0, 0.0));
					this.getVideoFile(this.sourceFilePath, textOrShapePane,topHbox,mainVbox,belowleftVBox);
					Platform.runLater(()->submitButton.setDisable(false));
				}
				iteratorIndex.getAndIncrement();
			}
			, new KeyValue[0]);
			Timeline timeline = new Timeline(arrkeyFrame);
			timeline.setCycleCount(localSeconds);
			timeline.play();
		} else {
		    Pane textOrShapePane = new Pane();
		    VBox belowleftVBox = new VBox();
		    HBox topHbox = new HBox();
		    VBox mainVbox = new VBox();
		    mainVbox.setStyle("-fx-background-color: gray");
		    belowleftVBox.setStyle("-fx-background-color: gray");
		    topHbox.setStyle("-fx-background-color: gray");
		    this.scene2 = new Scene(mainVbox, 1024.0, 986.0);
		    belowleftVBox.setPrefWidth(1220.0);
		    belowleftVBox.setPadding(new Insets(8.0, 10.0, 10.0, 0.0));
		    this.getVideoFile(this.sourceFilePath, textOrShapePane, topHbox, mainVbox, belowleftVBox);
		}
		try {
			if(this.destPathField.getText() != null){
				String content = this.destPathField.getText();
				String destinationFilPath = new File("").getAbsolutePath().concat("\\properties\\capture.txt");
				File file = new File(destinationFilPath);
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				Platform.runLater(()->submitButton.setDisable(false));
			}
		}
		catch (IOException iOException) {
			JavaFxToolLayoutWithShapes.log("Runtime Exception : ",iOException);
		}
	}

	public void closeProgressDialog(String fileName, boolean isExport, MediaPlayer object) {
		Timeline fiveSecondsWonder = null;
		Map<String,Timeline> timelineMap = new HashMap<>();
		KeyFrame timeLine = new KeyFrame(Duration.millis(1100), new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
				File file = new File(fileName);
				if(file.exists()){
					String url = file.toURI().toString();
					tempView = new MediaView();
					tempMedia = new Media(url);
					tempMediaPlayer = new MediaPlayer(tempMedia);
					tempView.setMediaPlayer(tempMediaPlayer);
					 tempMediaPlayer.setOnError(() -> {
				        	System.out.println("Media loading failed.");
				     });
					 tempMediaPlayer.setOnPlaying(() -> {
						 if(JavaFxToolLayoutWithShapes.progressDialog != null && JavaFxToolLayoutWithShapes.progressDialog.isShowing()){
								JavaFxToolLayoutWithShapes.progressDialog.close();
								if(isExport){
									showExportSuccessMessage(object);
									object.stop();
									try {
										Runtime.getRuntime().exec("explorer.exe /select," + FilenameUtils.normalize(fileName));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								timelineMap.get("timeline").stop();
								tempMediaPlayer.stop();
								tempMediaPlayer.dispose();
							}
					 });
					 tempMediaPlayer.play();
				}
		    }
		});
		
		fiveSecondsWonder = new Timeline(timeLine);
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();
		timelineMap.put("timeline", fiveSecondsWonder);
	}

	public void showLoading(boolean isExport) {
		ButtonType okButtonType = new ButtonType(EnhancerLocaleContent.OK, ButtonBar.ButtonData.OK_DONE);
		GridPane content = new GridPane();
		if(progressDialog == null){
			progressDialog = new Dialog();
			progressDialog.initModality(Modality.WINDOW_MODAL);
			progressDialog.initStyle(StageStyle.UNDECORATED);
			progressDialog.setResizable(false);
			Stage stage = (Stage)progressDialog.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			progressDialog.getDialogPane().getButtonTypes().add(okButtonType);
			Node okButton = progressDialog.getDialogPane().lookupButton(okButtonType);
			okButton.setVisible(false);
		}
		ProgressIndicator indicator = new ProgressIndicator();
		indicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
		indicator.setPrefSize(75.0, 75.0);
		content.setHgap(10.0);
		content.setVgap(10.0);
		content.setAlignment(Pos.CENTER);
		content.add(new ImageView(new Image(this.getClass().getResourceAsStream("images/pleaseWait.png"))), 5, 3);
		content.add(indicator, 9, 3);
		content.setPadding(new Insets(10.0, 140.0, 10.0, 20.0));
		progressDialog.getDialogPane().setStyle("-fx-background-color: transparent;");
		if(!isExport){
			content.add(new Label(EnhancerLocaleContent.CONVERTING_WMV_TO_MP4_VIDEO_EDITOR_SUPPORTS_ONLY_MP4_FILES), 5, 5);
		}
		progressDialog.getDialogPane().setContent(content);

		if(progressDialog.getOwner() != this.window){
			progressDialog.initOwner(this.window);
			progressDialog.setX(this.window.getX() + 400.0);
			progressDialog.setY(this.window.getY() + 400.0);
		}else{
			progressDialog.setX(progressDialog.getOwner().getX() + 400.0);
			progressDialog.setY(progressDialog.getOwner().getY() + 400.0);
		}
		//		double centerXPosition = this.scene1.getWindow().getX() + (this.scene1.getWindow().getWidth()/3);
		//		double centerYPosition = this.scene1.getWindow().getY() + (this.scene1.getWindow().getHeight()/2.4);
		//		dialog.setX(centerXPosition);
		//		dialog.setY(centerYPosition);
		progressDialog.show();
	}

	public boolean executeTool(String ffmpeg, boolean isExport) {
		String filePath = new File("").getAbsolutePath();
		String[] metaData = new String[5];
		String[] streamData = new String[10];
		try {
			showLoading(isExport);
			Process proc = Runtime.getRuntime().exec("\"" +CommonMethods.FFMPEG_EXE_PATH+"\""  + ffmpeg);
			String s;
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader Errorreader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			boolean a= true;
			while ((s = Errorreader.readLine()) != null) {
				if(s.contains("Output")){
					return true;
				}
			}
			if(a){
				while ((s = reader.readLine()) != null) {
					if(s.contains("Output")){
						return true;
					}
				}
			}
		}
		catch (IOException e) {
			Common.log(e);
			e.printStackTrace();
		}
		return false;
	}

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private void dialogForShape(Slider timeSlider, ImageView closeImage) {
        Dialog<Pair> dialog = new Dialog<Pair>();
        dialog.setTitle(EnhancerLocaleContent.CUSTOMIZE_SHAPE);
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        ButtonType submitButtonType = new ButtonType(EnhancerLocaleContent.SET, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType(EnhancerLocaleContent.CANCEL, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, cancelButtonType);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);
        gridPane.setPadding(new Insets(20.0, 10.0, 10.0, 10.0));
        Label colorPickerLabel = new Label(EnhancerLocaleContent.SHAPE_COLOR+" : ");
        colorPickerLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(colorPickerLabel, 0, 0);
        ColorPicker colorPicker = new ColorPicker();
        if (Objects.nonNull(this.previousShapeColorMap.get(this.lastClickedIndex))) {
            Color lastClickedColor = this.previousShapeColorMap.get(this.lastClickedIndex);
            colorPicker.setValue(lastClickedColor);
        } else {
            colorPicker.setValue(Color.BLACK);
        }
        colorPicker.setPrefWidth(100.0);
        colorPicker.setPrefHeight(30.0);
        colorPicker.setCursor(Cursor.HAND);
        gridPane.add(colorPicker, 1, 0);
        Label shapeSizeLabel = new Label(EnhancerLocaleContent.SHAPE_THICKNESS+" : ");
        shapeSizeLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(shapeSizeLabel, 0, 1);
        ComboBox<Integer> shapeComboBox = new ComboBox<Integer>();
        shapeComboBox.setCursor(Cursor.HAND);
        shapeComboBox.setPrefWidth(100.0);
        shapeComboBox.setPrefHeight(30.0);
        IntStream.rangeClosed(1, 10).forEach(shapeValues -> {
            if (shapeValues == 1) {
                shapeComboBox.setValue(shapeValues);
            }
            shapeComboBox.getItems().add(shapeValues);
        }
        );
        if (Objects.nonNull(this.previousShapeSizeMap.get(this.lastClickedIndex))) {
            shapeComboBox.setValue(this.previousShapeSizeMap.get(this.lastClickedIndex));
        }
        gridPane.add(shapeComboBox, 1, 1);
        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().autosize();
        Node cancelButton = dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.setCursor(Cursor.HAND);
        Node okButton = dialog.getDialogPane().lookupButton(submitButtonType);
        okButton.setDisable(false);
        okButton.setCursor(Cursor.HAND);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                List<Node> nodeList = this.rectangleMap.get(this.lastClickedIndex);
                Optional<Node> nodeOptional = nodeList.stream().findFirst();
                if (nodeOptional.isPresent()) {
                    Rectangle rectangleNode = (Rectangle)nodeOptional.get();
                    rectangleNode.setStroke((Paint)colorPicker.getValue());
                    rectangleNode.setStrokeWidth(((Integer)shapeComboBox.getValue()).intValue());
                    this.previousShapeColorMap.put(this.lastClickedIndex, (Color)colorPicker.getValue());
                    this.previousShapeSizeMap.put(this.lastClickedIndex, (Integer)shapeComboBox.getValue());
                }
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                String initialValue = numberFormat.format(this.hSliderMap.get(this.lastClickedIndex).getLowValue());
                String finalValue = numberFormat.format(this.hSliderMap.get(this.lastClickedIndex).getHighValue());
                double lowSliderValue = Double.parseDouble(initialValue);
                double highSliderValue = Double.parseDouble(finalValue);
                stage.setAlwaysOnTop(false);
                if (timeSlider.getValue() != lowSliderValue && (timeSlider.getValue() <= lowSliderValue || timeSlider.getValue() > highSliderValue)) {
                    Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    Button confirmButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                    confirmButton.setText(EnhancerLocaleContent.CONFIRM);
                    closeConfirmation.setHeaderText(EnhancerLocaleContent.DO_YOU_WANT_TO_GO_TO_THE_STARTING_OF_THE_SEEKER);
                    closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                    closeConfirmation.initOwner(this.window);
                    closeConfirmation.setX(this.window.getX() + 500.0);
                    closeConfirmation.setY(this.window.getY() + 400.0);
                    Optional closeResponse = closeConfirmation.showAndWait();
                    if (ButtonType.OK.equals(closeResponse.get())) {
                        timeSlider.setValue(lowSliderValue);
                        stage.setAlwaysOnTop(true);
                        this.rectangleMap.get(this.lastClickedIndex).stream().forEach(node -> {
                            node.setVisible(true);
                        }
                        );
                        this.selectedTextIndexMap.values().stream().forEach(selectTextIndex -> {
                            this.setTextLabelVisible(timeSlider, numberFormat, selectTextIndex);
                        }
                        );
                        this.rectangleMap.entrySet().stream().filter(nodes -> (Integer)nodes.getKey() != this.lastClickedIndex).forEach(nodes -> {
                            this.setShapeVisible(timeSlider, numberFormat, nodes);
                        }
                        );
                    } else {
                        this.rectangleMap.get(this.lastClickedIndex).stream().forEach(node -> {
                            node.setVisible(false);
                        }
                        );
                    }
                }
                return new Pair<String, String>(null,null);
            }else{
            	closeImage.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
            			closeImage.getX(), closeImage.getY(), closeImage.getX(), closeImage.getY(), MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true, null));
            	return new Pair<String, String>(null,null);
            }
        }
        );
        dialog.show();
    }

    private void dialogPopUp(String previousText, String previousFontName, Integer previousFontSize, Color previousFontColor, Pane textOrShapePane, Slider timeSlider, ImageView closeImage) {
        Dialog<Pair> dialog = new Dialog<Pair>();
        dialog.setTitle(EnhancerLocaleContent.CUSTOMIZE_TEXT);
        dialog.setResizable(false);
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        ButtonType okButtonType = new ButtonType(EnhancerLocaleContent.SET, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType(EnhancerLocaleContent.CANCEL, ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);
        gridPane.setPadding(new Insets(20.0, 10.0, 10.0, 10.0));
        StackPane stackedPane = new StackPane();
        stackedPane.setPadding(new Insets(20.0, 5.0, 5.0, 5.0));
        Label label = new Label(EnhancerLocaleContent.ENTER_TEXT+" : ");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(label, 0, 0);
        Label fontLabel = new Label(EnhancerLocaleContent.SELECT_FONT+" : ");
        fontLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(fontLabel, 0, 2);
        Label fontNameLabel = new Label(EnhancerLocaleContent.FONT_NAME);
        fontNameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(fontNameLabel, 1, 1);
        Label fontSizeLabel = new Label(EnhancerLocaleContent.FONT_SIZE);
        fontSizeLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(fontSizeLabel, 2, 1);
        List<String> fontNameList = EnumFont.getAllFontNames();
        AtomicInteger fontNameIterator = new AtomicInteger();
        ComboBox<String> fontNameComboBox = new ComboBox<String>();
        fontNameComboBox.setCursor(Cursor.HAND);
        fontNameList.stream().forEach(fontValues -> {
            if (fontNameIterator.get() == 2) {
                fontNameComboBox.setValue(fontValues);
            }
            fontNameComboBox.getItems().add((String)fontValues);
            fontNameIterator.getAndIncrement();
        }
        );
        if (previousFontName != null && !previousFontName.isEmpty()) {
            fontNameComboBox.setValue(previousFontName);
        }
        AtomicInteger fontSizeIterator = new AtomicInteger(1);
        ComboBox<Integer> fontSizecomboBox = new ComboBox<Integer>();
        fontSizecomboBox.setCursor(Cursor.HAND);
        IntStream.rangeClosed(1, 100).forEach(size -> {
            if (fontSizeIterator.get() == 20) {
                fontSizecomboBox.setValue(size);
            }
            fontSizecomboBox.getItems().add(size);
            fontSizeIterator.getAndIncrement();
        }
        );
        if (Objects.nonNull(previousFontSize)) {
            fontSizecomboBox.setValue(previousFontSize);
        }
        ComboBoxListViewSkin skin = new ComboBoxListViewSkin(fontSizecomboBox);
        fontSizecomboBox.setSkin(skin);
        ListView listView = ((ComboBoxListViewSkin)fontSizecomboBox.getSkin()).getListView();
        listView.scrollTo((Integer)fontSizecomboBox.getValue());
        ColorPicker colorPicker = new ColorPicker();
        if (Objects.nonNull(previousFontColor)) {
            colorPicker.setValue(previousFontColor);
        } else if (Objects.nonNull(this.colorPickerValue)) {
            colorPicker.setValue(this.colorPickerValue);
        } else {
            colorPicker.setValue(Color.BLACK);
        }
        colorPicker.setPrefWidth(100.0);
        colorPicker.setPrefHeight(30.0);
        colorPicker.setCursor(Cursor.HAND);
        Label colorPickerLabel = new Label(EnhancerLocaleContent.CHOOSE_COLOR+" : ");
        colorPickerLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14.0));
        gridPane.add(colorPickerLabel, 3, 1);
        gridPane.add(fontNameComboBox, 1, 2);
        gridPane.add(fontSizecomboBox, 2, 2);
        gridPane.add(colorPicker, 3, 2);
        TextField textField = new TextField(){

            @Override
            public void paste() {
            }
        };
        textField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
        	TextField textLength = (TextField)keyEvent.getSource();
            if (textLength.getText().length() >= 90) {
                keyEvent.consume();
            }
        }
        );
        textField.setText(previousText);
        textField.setMaxSize(250.0, 20.0);
        textField.setPromptText("");
        gridPane.add(textField, 1, 0);
        Node cancelButton = dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.setCursor(Cursor.HAND);
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        if (textField.getText() == null || textField.getText().isEmpty()) {
            okButton.setDisable(true);
            okButton.setCursor(Cursor.DEFAULT);
        }
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
            okButton.setCursor(Cursor.HAND);
        }
        );
        VBox firstvBox1 = new VBox();
        VBox secondvBox2 = new VBox();
        VBox mainvBox = new VBox();
        firstvBox1.getChildren().add(gridPane);
        secondvBox2.getChildren().add(stackedPane);
        mainvBox.getChildren().addAll(firstvBox1, secondvBox2);
        dialog.getDialogPane().setContent(mainvBox);
        dialog.getDialogPane().autosize();
        Platform.runLater(() -> {
            textField.requestFocus();
        }
        );
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String textImageFile;
                this.exportButton.setDisable(false);
                this.isSubmittedMap.put(this.lastClickedIndex, new Pair<Boolean, String>(true, "Text"));
                Optional<Boolean> booleanOptional = this.isSubmittedMap.values().stream().filter(pairValue -> ((String)pairValue.getValue()).contains("Text") && (Boolean)pairValue.getKey() == false).map(Pair::getKey).findFirst();
                if (booleanOptional.isPresent() && !booleanOptional.get().booleanValue()) {
                    this.exportButton.setDisable(true);
                }
                this.textLabelMap.get(this.lastClickedIndex).setVisible(true);
                blinkLabel(colorPicker.getValue());
                this.textLabelMap.get(this.lastClickedIndex).maxWidth(1200);
                if(!this.textLabelMap.containsKey(this.lastClickedIndex) ||(this.textLabelMap.containsKey(this.lastClickedIndex) && this.textLabelMap.get(this.lastClickedIndex).getX() == 0)){
                	this.textLabelMap.get(this.lastClickedIndex).setX(40.0);
                }
                
                if(!this.textLabelMap.containsKey(this.lastClickedIndex) || (this.textLabelMap.containsKey(this.lastClickedIndex) && this.textLabelMap.get(this.lastClickedIndex).getY() == 0)){
                	this.textLabelMap.get(this.lastClickedIndex).setY(270.0);
                }
                if(!this.translateXMap.containsKey(this.lastClickedIndex) || (this.translateXMap.containsKey(this.lastClickedIndex) && this.translateXMap.get(this.lastClickedIndex) == 0)){
                	this.translateXMap.put(this.lastClickedIndex, Long.valueOf(40));
                }
                if(!this.translateYMap.containsKey(this.lastClickedIndex) || (this.translateYMap.containsKey(this.lastClickedIndex) && this.translateYMap.get(this.lastClickedIndex) == 0)){
                	this.translateYMap.put(this.lastClickedIndex, Long.valueOf(270));
                }
                this.textMap.put(this.lastClickedIndex, textField.getText());
                this.fontNameMap.put(this.lastClickedIndex, (String)fontNameComboBox.getValue());
                this.fontSizeMap.put(this.lastClickedIndex, (Integer)fontSizecomboBox.getValue());
                this.colorPickerMap.put(this.lastClickedIndex, (Color)colorPicker.getValue());
                this.colorPickerValue = (Color)colorPicker.getValue();
                this.textLabelMap.get(this.lastClickedIndex).setText(textField.getText());
                this.textLabelMap.get(this.lastClickedIndex).setFont(Font.font((String)fontNameComboBox.getValue(), FontPosture.REGULAR, (double)((Integer)fontSizecomboBox.getValue()).intValue()));
                this.textLabelMap.get(this.lastClickedIndex).setFill((Paint)colorPicker.getValue());
                this.textLabelMap.get(this.lastClickedIndex).setStyle("-fx-max-width:10;");
                if (textOrShapePane.getChildren().contains(this.textLabelMap.get(this.lastClickedIndex))) {
                    textOrShapePane.getChildren().remove(this.textLabelMap.get(this.lastClickedIndex));
                }
                textOrShapePane.getChildren().add(this.textLabelMap.get(this.lastClickedIndex));
                String textIdLabel = this.textLabelMap.get(this.lastClickedIndex).getText();
                if (textIdLabel.length() > 8) {
                    String splitLabel = textIdLabel.subSequence(0, 8).toString();
                    textImageFile = splitLabel.concat("...");
                } else {
                    textImageFile = textIdLabel;
                } 
                if (Objects.nonNull(this.textOrImageSliderMap.get(this.lastClickedIndex).getKey())) {
                	this.textOrImageSliderMap.get(this.lastClickedIndex).getKey().setEllipsisString(textIdLabel);
                	this.textOrImageSliderMap.get(this.lastClickedIndex).getKey().setMaxWidth(5.0);
                	this.textOrImageSliderMap.get(this.lastClickedIndex).getKey().setTooltip(new Tooltip(textIdLabel));
                    this.textOrImageSliderMap.get(this.lastClickedIndex).getKey().setText(textImageFile);
                    this.textOrImageSliderMap.get(this.lastClickedIndex).getKey().setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16.0));
                }
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                String initialValue = numberFormat.format(this.hSliderMap.get(this.lastClickedIndex).getLowValue());
                String finalValue = numberFormat.format(this.hSliderMap.get(this.lastClickedIndex).getHighValue());
                double lowSliderValue = Double.parseDouble(initialValue);
                double highSliderValue = Double.parseDouble(finalValue);
                stage.setAlwaysOnTop(false);
                if (timeSlider.getValue() != lowSliderValue && (timeSlider.getValue() <= lowSliderValue || timeSlider.getValue() > highSliderValue)) {
                    Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    Button confirmButton = (Button)closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                    confirmButton.setText(EnhancerLocaleContent.CONFIRM);
                    closeConfirmation.setHeaderText(EnhancerLocaleContent.DO_YOU_WANT_TO_GO_TO_THE_STARTING_OF_THE_SEEKER);
                    closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                    closeConfirmation.initOwner(this.window);
                    closeConfirmation.setX(this.window.getX() + 500.0);
                    closeConfirmation.setY(this.window.getY() + 400.0);
                    Optional closeResponse = closeConfirmation.showAndWait();
                    if (ButtonType.OK.equals(closeResponse.get())) {
                        timeSlider.setValue(lowSliderValue);
                        stage.setAlwaysOnTop(true);
                        this.textLabelMap.get(this.lastClickedIndex).setVisible(true);
                        this.selectedTextIndexMap.values().stream().filter(selectTextIndex -> selectTextIndex != this.lastClickedIndex).forEach(selectTextIndex -> {
                            this.setTextLabelVisible(timeSlider, numberFormat, selectTextIndex);
                        }
                        );
                        this.rectangleMap.entrySet().stream().forEach(nodes -> {
                            this.setShapeVisible(timeSlider, numberFormat, nodes);
                        }
                        );
                    } else {
                        this.textLabelMap.get(this.lastClickedIndex).setVisible(false);
                    }
                }
                return new Pair<String, String>(textField.getText(), (String)fontNameComboBox.getValue());
            }else if(dialogButton == cancelButtonType){
            	closeImage.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
            			closeImage.getX(), closeImage.getY(), closeImage.getX(), closeImage.getY(), MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true, null));
            	return new Pair<String, String>(textField.getText(), (String)fontNameComboBox.getValue());
            }
            return null;
        }
        );
        dialog.showAndWait();
    }

    public void blinkLabel(Color color) {
    	this.textLabelMap.get(this.lastClickedIndex).setStroke(Color.YELLOW);
    	blinkTimeline = new Timeline(
    			new KeyFrame(Duration.seconds(0.5), evt ->{ 
    				this.textLabelMap.get(this.lastClickedIndex).setVisible(false);
    				this.textLabelMap.get(this.lastClickedIndex).setStroke(Color.YELLOW);
    			}),
    			new KeyFrame(Duration.seconds(1), evt -> {this.textLabelMap.get(this.lastClickedIndex).setVisible(true);
    			this.textLabelMap.get(this.lastClickedIndex).setStroke(Color.YELLOW);
    			}));
    	blinkTimeline.setCycleCount(3);
    	blinkTimeline.play();
    	blinkTimeline.setOnFinished(new EventHandler<ActionEvent>() { 
    		@Override 
    		public void handle(ActionEvent e) {
    			textLabelMap.get(lastClickedIndex).strokeProperty().setValue(null);
    		} 
    	});
    }


    private void setShapeVisible(Slider timeSlider, DecimalFormat numberFormat, Map.Entry<Integer, List<Node>> nodes) {
        String firstValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getLowValue());
        String lastValue = numberFormat.format(this.hSliderMap.get(nodes.getKey()).getHighValue());
        double lowValue = Double.parseDouble(firstValue);
        double highValue = Double.parseDouble(lastValue);
        if (timeSlider.getValue() > lowValue && timeSlider.getValue() <= highValue) {
            this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                node.setVisible(true);
            }
            );
        } else {
            this.rectangleMap.get(nodes.getKey()).stream().forEach(node -> {
                node.setVisible(false);
            }
            );
        }
    }

    private void setTextLabelVisible(Slider timeSlider, DecimalFormat numberFormat, Integer selectTextIndex) {
        String firstValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getLowValue());
        String lastValue = numberFormat.format(this.hSliderMap.get(selectTextIndex).getHighValue());
        double lowValue = Double.parseDouble(firstValue);
        double highValue = Double.parseDouble(lastValue);
        if (timeSlider.getValue() > lowValue && timeSlider.getValue() <= highValue) {
            this.textLabelMap.get(selectTextIndex).setVisible(true);
        } else {
            this.textLabelMap.get(selectTextIndex).setVisible(false);
        }
    }

    private Rectangle createDraggableRectangle(Circle resizeHandleNW, Circle resizeHandleSE, Circle moveHandle) {
        double handleRadius = 10.0;
        Rectangle rect = new Rectangle(100.0, 100.0, 200.0, 150.0);
        resizeHandleNW.centerXProperty().bind(rect.xProperty());
        resizeHandleNW.centerYProperty().bind(rect.yProperty());
        resizeHandleNW.setId(String.valueOf(this.countIterator.get()));
        resizeHandleSE.centerXProperty().bind(rect.xProperty().add(rect.widthProperty()));
        resizeHandleSE.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));
        resizeHandleSE.setId(String.valueOf(this.countIterator.get()));
        moveHandle.centerXProperty().bind(rect.xProperty().add(rect.widthProperty().divide(2)));
        moveHandle.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));
        moveHandle.setId(String.valueOf(this.countIterator.get()));
        rect.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (Objects.isNull((Pane)resizeHandleNW.getParent())) {
                for (Circle pointCircle : Arrays.asList(resizeHandleNW, resizeHandleSE, moveHandle)) {
                    Pane currentParent = (Pane)pointCircle.getParent();
                    if (!Objects.isNull(currentParent)) continue;
                    ((Pane)newParent).getChildren().add(pointCircle);
                }
            }
        }
        );
        Wrapper<Point2D> mouseLocation = new Wrapper<Point2D>(null);
        this.setUpDragging(resizeHandleNW, mouseLocation,false);
        this.setUpDragging(resizeHandleSE, mouseLocation,false);
        this.setUpDragging(moveHandle, mouseLocation,true);
        resizeHandleNW.setOnMouseDragged(event -> {
            if (Objects.nonNull(mouseLocation.value)) {
                double newY;
                double deltax = event.getSceneX() - ((Point2D)mouseLocation.value).getX();
                double deltaY = event.getSceneY() - ((Point2D)mouseLocation.value).getY();
                double newX = rect.getX() + deltax;
                if (newX >= 10.0 && newX <= rect.getX() + rect.getWidth() - 10.0) {
                    rect.setX(newX);
                    rect.setWidth(rect.getWidth() - deltax);
                }
                if ((newY = rect.getY() + deltaY) >= 10.0 && newY <= rect.getY() + rect.getHeight() - 10.0) {
                    rect.setY(newY);
                    rect.setHeight(rect.getHeight() - deltaY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        }
        );
        resizeHandleSE.setOnMouseDragged(event -> {
            if (Objects.nonNull(mouseLocation.value)) {
                double newMaxY;
                double deltax = event.getSceneX() - ((Point2D)mouseLocation.value).getX();
                double deltaY = event.getSceneY() - ((Point2D)mouseLocation.value).getY();
                double newMaxX = rect.getX() + rect.getWidth() + deltax;
                if (newMaxX >= rect.getX() && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - 10.0) {
                    rect.setWidth(rect.getWidth() + deltax);
                }
                if ((newMaxY = rect.getY() + rect.getHeight() + deltaY) >= rect.getY() && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - 10.0) {
                    rect.setHeight(rect.getHeight() + deltaY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        }
        );
        moveHandle.setOnMouseDragged(event -> {
            if (Objects.nonNull(mouseLocation.value)) {
                double deltax = event.getSceneX() - ((Point2D)mouseLocation.value).getX();
                double deltaY = event.getSceneY() - ((Point2D)mouseLocation.value).getY();
                double newX = rect.getX() + deltax;
                double newMaxX = newX + rect.getWidth();
                if (newX >= 10.0 && newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - 10.0) {
                    rect.setX(newX);
                }
                double newY = rect.getY() + deltaY;
                double newMaxY = newY + rect.getHeight();
                if (newY >= 10.0 && newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - 10.0) {
                    rect.setY(newY);
                }
                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        }
        );
        return rect;
    }

    private void setUpDragging(Circle circle, Wrapper<Point2D> mouseLocation, boolean moveHandle) {
    	//Creating the mouse event handler 
    	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
    	   @Override 
    	   public void handle(MouseEvent e) { 
    		if(moveHandle){
           		circle.getParent().setCursor(Cursor.MOVE);
           	}else{
           		circle.getParent().setCursor(Cursor.NW_RESIZE);
           	} 
    	   } 
    	};   
    	//Adding event Filter 
    	circle.addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler);
        circle.setOnDragDetected(event -> {
        	if(moveHandle){
        		circle.getParent().setCursor(Cursor.MOVE);
        	}else{
        		circle.getParent().setCursor(Cursor.NW_RESIZE);
        	}
            mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
        }
        );
        circle.setOnMouseExited(event -> {
            circle.getParent().setCursor(Cursor.DEFAULT);
        }); 
        circle.setOnMouseReleased(event -> {
            circle.getParent().setCursor(Cursor.DEFAULT);
            mouseLocation.value = null;
        }
        );
    }

    public static void main(String[] args) {
        JavaFxToolLayoutWithShapes.launch(args);
    }

    private static class Wrapper<T> {
        T value;

        private Wrapper() {
        }

        /* synthetic */ Wrapper(Wrapper wrapper) {
            Wrapper<T> wrapper2;
//            wrapper2();
        }
    }

    public static String getFileSize(String outputFilePath) {
        double fileSize = new File(outputFilePath).length();
        DecimalFormat forat = new DecimalFormat("##.00");
        if (fileSize < 1024.0) {
            return String.valueOf(fileSize) + "bytes";
        }
        if ((fileSize /= 1024.0) < 1024.0) {
            String resultSize = forat.format(fileSize);
            return String.valueOf(resultSize) + "KB";
        }
        String resultSize = forat.format(fileSize /= 1024.0);
        return String.valueOf(resultSize) + "MB";
    }
	public static void ffmpegNotFoundException(Window window) {
		  Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(EnhancerLocaleContent.ERROR);
	        alert.setHeaderText(null);
	        alert.setContentText(SplitterLocaleContent.FFMPEG_NOT_FOUND_1);
	        alert.initOwner(window);
	        alert.show();
		JavaFxToolLayoutWithShapes.logger.info(SplitterLocaleContent.FFMPEG_NOT_FOUND_2+" "+"'"+AssistiveScreenshot.currentDirectory+"properties\\\\' "+SplitterLocaleContent.FFMPEG_NOT_FOUND_3+"\n"
				+ SplitterLocaleContent.FFMPEG_NOT_FOUND_4);
		try {
			Desktop.getDesktop().open(new File(AssistiveScreenshot.currentDirectory+"properties\\log\\"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void log(String msg,Exception e) {
		JavaFxToolLayoutWithShapes.logger.log(Level.SEVERE, msg, e);
	}
}
