package tool.splitter;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;

import tool.ishot.AssistiveScreenshot;
import tool.ishot.Common;

public class CommonMethods {
    public static String SYSTEM_DOMAIN_NAME = System.getProperties().get("user.name").toString().toLowerCase();
    public static String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static String FFMPEG_EXE_PATH = AssistiveScreenshot.currentDirectory + "properties"+"\\\\"+"ffmpeg.exe";
    public static String HOME_DIRECTORY = System.getProperty("user.home");

    public static void createDirectoryIfNotExist(String directoryPath) {
        File directoryFile = new File(directoryPath);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }
    }

    public static void openSelectedFile(String inputFilePath) {
        try {
            File inputFile;
            if (CommonMethods.isNotNullEmpty(inputFilePath) && (inputFile = new File(inputFilePath)).exists()) {
                Desktop.getDesktop().open(inputFile);
            }
        }
        catch (IOException e) {
        	Common.log(e);
            System.err.println("Unable to open log file...Open manually...");
        }
    }

    public static boolean isNotNullEmpty(String inputString) {
        if (inputString != null && !inputString.isEmpty()) {
            return true;
        }
        return false;
    }

    public static ImageIcon createImageIcon(String imageName) {
        URL imgURL = VideoCutter.class.getResource("icons/" + imageName);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        System.err.println("Couldn't find image source : " + imageName);
        return null;
    }

    public static String getStringText(String curStringTxt) {
        return CommonMethods.isNotNullEmpty(curStringTxt) ? curStringTxt : "0";
    }

    public static String getOutputFileName(String outputFolderPath, String currentFileName) {
        File currentDirectoryFile = new File(outputFolderPath);
        int existIndex = 0;
        File[] arrfile = currentDirectoryFile.listFiles();
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File fileName = arrfile[n2];
            String fileNameString = fileName.getName();
            if (fileNameString.startsWith(currentFileName)) {
                ++existIndex;
            }
            ++n2;
        }
        if (existIndex > 0) {
            currentFileName = String.valueOf(currentFileName) + "_" + existIndex;
        }
        return currentFileName;
    }

    public static String getFileSize(String outputFilePath) {
        double fileSize = new File(outputFilePath).length();
        DecimalFormat forat = new DecimalFormat("##.00");
        if (fileSize < 1024.0) {
            return String.valueOf(fileSize) + " bytes";
        }
        if ((fileSize /= 1024.0) < 1024.0) {
            String resultSize = forat.format(fileSize);
            return String.valueOf(resultSize) + " KB";
        }
        String resultSize = forat.format(fileSize /= 1024.0);
        return String.valueOf(resultSize) + " MB";
    }

    public static String[] executeFfmpegCmd(String filePath) throws FFMPEGNotFoundException{
    	if(!new File(FFMPEG_EXE_PATH).exists()){
    		throw new FFMPEGNotFoundException(); 
    	}
        String[] metaData = new String[5];
        String[] streamData = new String[10];
        String[] cmd={FFMPEG_EXE_PATH,"-i",filePath,"2>&1"};
        try {
            String s;
            Process commandPromptStatus = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(commandPromptStatus.getInputStream()));
            BufferedReader Errorreader = new BufferedReader(new InputStreamReader(commandPromptStatus.getErrorStream()));
            boolean a= true;
            while ((s = Errorreader.readLine()) != null) {
            	a = false;
                if (s.trim().startsWith("Duration")) {
                    metaData[0] = s.substring(12, 20).trim();
                }
                if(s.trim().contains("bitrate:")){
                	metaData[4] = s.trim().substring(s.trim().indexOf("bitrate:")+9, s.trim().length()).trim(); 
                }
                if (s.trim().startsWith("Stream")) {
                    streamData = s.trim().split(",");
//                    metaData[5] = EditorLocale.getResolution(s.trim());
                }
                metaData[1] = streamData[2];
                metaData[2] = CommonMethods.getFileSize(filePath);
                metaData[3] = new File(filePath).getName();
            
            }
            if(a){
            while ((s = reader.readLine()) != null) {
                if (s.trim().startsWith("Duration")) {
                    metaData[0] = s.substring(12, 20).trim();
                }
                if(s.trim().contains("bitrate:")){
                	metaData[4] = s.trim().substring(s.trim().indexOf("bitrate:")+9, s.trim().length()).trim(); 
                }
                if (s.trim().startsWith("Stream")) {
                    streamData = s.trim().split(",");
//                    metaData[5] = EditorLocale.getResolution(s.trim());
                }
                metaData[1] = streamData[2];
                metaData[2] = CommonMethods.getFileSize(filePath);
                metaData[3] = new File(filePath).getName();
            }
            }
        }
        catch (IOException e) {
        	Common.log(e);
            e.printStackTrace();
        }
        return metaData;
    }

}
