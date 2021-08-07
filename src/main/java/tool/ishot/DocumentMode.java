package tool.ishot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Picture;

public class DocumentMode {

	public static boolean addImage(String fileName) throws IOException {
		HSSFWorkbook workbook = null;
		FileInputStream fileInputStream = null;
		try {
			int row = 0;
			fileInputStream = new FileInputStream(new File(fileName + ".ods"));
			workbook = new HSSFWorkbook(fileInputStream);
			List<HSSFPictureData> a = workbook.getAllPictures();
			row = a.size() == 0 ? 1 : a.size() *47;
			newExcel(fileName, row);
			fileInputStream.close();
		}
		catch (IOException ioException) {
			Common.log(ioException);
			File file = new File(fileName+".png");
			FileUtils.forceDelete(file);
			return false;
		}finally{
			if(Objects.nonNull(fileInputStream))
			fileInputStream.close();
			if(Objects.nonNull(workbook))
			workbook.close();
		}
		return true;
	}

	public static void newExcel(String fileName, int row) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(fileName+".png");
		HSSFWorkbook workbook = null;
		HSSFCreationHelper helper = null;
		HSSFPatriarch drawing = null;
		HSSFSheet worksheet = null;
		File file = new File(fileName+".png");
		byte[] byteStream = IOUtils.toByteArray(fileInputStream);
		fileInputStream.close();
		if (row == 1) {
			workbook = new HSSFWorkbook();
			helper = workbook.getCreationHelper();
			worksheet = workbook.createSheet(LocaleContent.getSCREENSHOTS());
			drawing = worksheet.createDrawingPatriarch();
		} else {
			FileInputStream odsInputStream = new FileInputStream(new File(fileName+ ".ods"));
			workbook = new HSSFWorkbook(odsInputStream);
			worksheet = workbook.getSheetAt(0);
			helper = workbook.getCreationHelper();
			drawing = worksheet.getDrawingPatriarch();
			odsInputStream.close();
		}

		ClientAnchor anchor = helper.createClientAnchor();
		int pictureIndex = workbook.addPicture(byteStream, 6);
		anchor.setCol1(1);
		anchor.setRow1(row);
		anchor.setRow2(row);
		anchor.setCol2(10);
		Picture picture = drawing.createPicture(anchor, pictureIndex);
		picture.resize(0.7);
		FileOutputStream fileOutputStream = null;
		try{
			fileOutputStream = new FileOutputStream(fileName+ ".ods");
			workbook.write(fileOutputStream);
			FileUtils.forceDelete(file);
		}finally {
			if(Objects.nonNull(fileOutputStream))
			fileOutputStream.close();
			if(Objects.nonNull(workbook))
			workbook.close();
		}
	}

}
