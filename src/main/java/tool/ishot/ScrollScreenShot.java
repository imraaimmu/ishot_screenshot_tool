package tool.ishot;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScrollScreenShot {

	public static void takeFullScreenShot(String url,String currentDirectory){
		String userChoice = "";
		String selectedElement = "";
		if(AssistiveScreenshot.isAdvanceFullScreenShot){
			String[] sport = new String[] {LocaleContent.getID(), LocaleContent.getCLASS()};
			userChoice = (String) JOptionPane.showInputDialog(AssistiveScreenshot.frame,
					LocaleContent.getPLEASE_SELECT(),LocaleContent.getPLEASE_SELECT(), JOptionPane.INFORMATION_MESSAGE, 
					null, sport,LocaleContent.getID());
			if(userChoice != null){
				selectedElement = getSelectorValue(userChoice);
				if(Objects.isNull(selectedElement) || (selectedElement != null && selectedElement.isEmpty())){
					return;
				}
			}else{
				return;
			}
		}
		System.setProperty("webdriver.chrome.driver",AssistiveScreenshot.currentDirectory+"properties\\chromedriver2.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		WebDriver driver = null;
		WebElement myWebElement = null;
		Screenshot myScreenshot = null;
		AShot aShot = new AShot();
		try{
			driver =  new ChromeDriver(options);
			if(AssistiveScreenshot.isAdvanceFullScreenShot){
				AssistiveScreenshot.isAdvanceFullScreenShot = false;
				boolean isFine = false;
				if(userChoice.equals(LocaleContent.getID())){
					driver.get(url);
					new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.id(selectedElement)));
					myWebElement = driver.findElement(By.id(selectedElement));
					isFine = true;
				}else if(userChoice.equals(LocaleContent.getCLASS())){
					driver.get(url);
					myWebElement = new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(ByClassName.className(selectedElement)));
					isFine = true;
				}
				if(isFine){
					aShot.coordsProvider(new WebDriverCoordsProvider());
					((JavascriptExecutor) driver).executeScript(
							"var style = document.createElement(\"style\"); style.innerHTML = \"::-webkit-scrollbar {display: none;}\"; document.body.appendChild(style);");
					myScreenshot = aShot.shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver,myWebElement);
					try {
						boolean isShowPreview = PopUpDemo.showPreviewMenu.isSelected();
						int result = 1;
						if(PopUpDemo.documentMode.isSelected()){
							AssistiveScreenshot.createTestcaseDoc(0,myScreenshot.getImage());
						}else{
							String file = FileNameUtil.askName(null);
							if(file != null && !file.isEmpty()){
								if(isShowPreview){
									result = ScreenCaptureRectangle.showImage(myScreenshot.getImage());
									if(result == 0){
										ImageIO.write(myScreenshot.getImage(),"PNG",new File (file+".jpg") );
									}
								}else{
									ImageIO.write(myScreenshot.getImage(),"PNG",new File (file+".jpg") );
								}
							}
						}
					} catch (IOException e1) {
						Common.log(e1);
						Common.thirtySecondsMessage();
					}
				}
			}else{
				driver.get(url);
				if(AssistiveScreenshot.fstimedelay != 0){
					Thread.sleep(AssistiveScreenshot.fstimedelay*1000);
				}
				((JavascriptExecutor) driver).executeScript(
						"var style = document.createElement(\"style\"); style.innerHTML = \"::-webkit-scrollbar {display: none;}\"; document.body.appendChild(style);");
				myScreenshot = aShot.shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
				try {
					boolean isShowPreview = PopUpDemo.showPreviewMenu.isSelected();
					int result = 1;
					if(PopUpDemo.documentMode.isSelected()){
						AssistiveScreenshot.createTestcaseDoc(0,myScreenshot.getImage());
					}else{
						String file = FileNameUtil.askName(null);
						if(file != null && !file.isEmpty()){
							if(isShowPreview){
								result = ScreenCaptureRectangle.showImage(myScreenshot.getImage());
								if(result == 0){
									ImageIO.write(myScreenshot.getImage(),"PNG",new File (file+".jpg"));
								}
							}else{
								ImageIO.write(myScreenshot.getImage(),"PNG",new File (file+".jpg"));
							}
						}
					}
				} catch (IOException e1) {
					Common.log(e1);
					e1.printStackTrace();
				}
			}
			if(Objects.nonNull(driver)){
				driver.quit();
			}
			AssistiveScreenshot.showButton();
		}catch(Exception e){
			Common.log(e);
			if(e.getMessage().contains("By.className")){
				Common.classNotFoundMessage();
			}else if(e.getMessage().contains("By.id")){
				Common.idNotFoundMessage();
			}else{
				Common.checkChromeDriver();
			}
		}finally {
			if(Objects.nonNull(driver)){
				driver.quit();
			}
			AssistiveScreenshot.showButton();
		}


	}

	public static String getSelectorValue(String userChoice) {
		String selectedElement;
		selectedElement = JOptionPane.showInputDialog(AssistiveScreenshot.frame, (Object)LocaleContent.getENTER_SELECTOR() +userChoice ,"");
		if(selectedElement == null){
			return selectedElement;	
		}
		if(selectedElement.isEmpty()){
			if(userChoice.equals(LocaleContent.getID())){
				Common.selectorIdBlankMessage();
			}else{
				Common.selectorClassBlankMessage();
			}
			return getSelectorValue(userChoice);
		}
		return selectedElement;
	}
}

