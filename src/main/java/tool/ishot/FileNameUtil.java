package tool.ishot;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class FileNameUtil {

	private static final String FILE_NAME_SHOULD_NOT_EXCEED_MORE_THAN_100_CHARACTERS = "File name should not exceed more than 100 characters.";
	private static final String FILE_NAME_SHOULD_NOT_BE_EMPTY = "File name should not be empty.";
	private static final String FILE_NAME_DON_T_INCLUDE_EXTENSION = "File name :\n[Don't include extension]";
	private static final String INVALID_FILE_NAME = "Invalid File Name.";
	static Calendar now = null;

	private static String isValidPathName(String folderPath, String name) throws IOException {
		File f = null;
		try{
			if(name.contains("\\") || name.contains("/") || name.contains(":") || name.contains("*") || name.contains("?")|| name.contains("\"")|| name.contains("<")|| name.contains(">")|| name.contains("|")){
				throw new Exception();
			}
			f = new File(folderPath);
			f.createNewFile();
			f.delete();
			AssistiveScreenshot.storeTofile("filename", name, "userdefinedfilename");
			return folderPath;
		}catch(Exception e){
			Common.log(e);
			AssistiveScreenshot.deleteFromfile("filename", name, "userdefinedfilename");
			Common.showError(INVALID_FILE_NAME);
			return askNameOnce(name);
		}
	}

	public static String getFolderPath(String folderPath, boolean isImage ) throws IOException,LocationNotFoundException {
		if(AssistiveScreenshot.prop.get("currentlocation") != null && !String.valueOf(AssistiveScreenshot.prop.get("currentlocation")).isEmpty()){
			folderPath = String.valueOf(AssistiveScreenshot.prop.get("currentlocation"));
		}
		String mode = "videos"+AssistiveScreenshot.slash+AssistiveScreenshot.slash;
		if(isImage){
			mode  = "screenshots"+AssistiveScreenshot.slash+AssistiveScreenshot.slash;
		}
		if(folderPath.equals("")){
			File file = new File(AssistiveScreenshot.currentDirectory+mode);
			if(!file.exists()){
				file.mkdir();
			}
			folderPath = file.getPath()+AssistiveScreenshot.slash;
		}
		if(!(new File(folderPath).isDirectory() && new File(folderPath).exists())){
			throw new LocationNotFoundException();
		}
		return folderPath;
	}


	public static String askNameOnce(String tempName) throws IOException,LocationNotFoundException{
		String folderPath = "";
		String name = JOptionPane.showInputDialog(AssistiveScreenshot.frame, FILE_NAME_DON_T_INCLUDE_EXTENSION,tempName);
		if(name == null){
			AssistiveScreenshot.cancelClickedInAskName = true;
			return null;
		}else{
			if(name.trim().equals("")){
				Common.showError(FILE_NAME_SHOULD_NOT_BE_EMPTY);
				return askNameOnce("");
			}
			if(name.length() > 100){
				Common.showError(FILE_NAME_SHOULD_NOT_EXCEED_MORE_THAN_100_CHARACTERS);
				return askNameOnce(name);
			}
			folderPath = getFolderPath(folderPath,PopUpDemo.image.isSelected());
			return isValidPathName(folderPath + name,name);
		}
	}

	public static String askName(String tempName) throws IOException,LocationNotFoundException{
		String folderPath = "";
		if(PopUpDemo.userDefinedName.isSelected()){
			if(tempName == null && AssistiveScreenshot.prop.get("filename") != null){
				tempName = AssistiveScreenshot.prop.get("filename").toString();
			}
			String name = JOptionPane.showInputDialog(AssistiveScreenshot.frame, FILE_NAME_DON_T_INCLUDE_EXTENSION,tempName);
			if(name == null){
				AssistiveScreenshot.cancelClickedInAskName = true;
				return null;
			}else{
				if(name.trim().equals("")){
					Common.showError(FILE_NAME_SHOULD_NOT_BE_EMPTY);
					return askName("");
				}
				if(name.length() > 100){
					Common.showError(FILE_NAME_SHOULD_NOT_EXCEED_MORE_THAN_100_CHARACTERS);
					return askName(name);
				}
				now = Calendar.getInstance();
				folderPath = getFolderPath(folderPath,PopUpDemo.image.isSelected());
				if(PopUpDemo.documentMode.isSelected() && PopUpDemo.image.isSelected()){
					return isValidPathName(folderPath + name,name);
				}
				return isValidPathName(folderPath + name+"_"+ImageMaker.formatter.format(now.getTime()),name);
			}
		}else{
			now = Calendar.getInstance();
			folderPath = getFolderPath(folderPath,PopUpDemo.image.isSelected());
			if(folderPath != null && folderPath.equals("DirectoryNotFound")){
				return null;
			}
			return folderPath+ImageMaker.formatter.format(now.getTime());

		}
	}
	public static String getOuputFolderForVS() throws IOException{
		String folderPath = "";
		now = Calendar.getInstance();
		folderPath = getFolderPath(folderPath,false);
		if(folderPath != null && folderPath.equals("DirectoryNotFound")){
			return null;
		}
		return folderPath;
}

}
