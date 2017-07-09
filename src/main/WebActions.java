package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebActions {

	static String message;
	private static final Logger LOGGER = Logger.getLogger(WebActions.class.getName());
	
	public void setMessage(String msg){
		message = msg;
	}
	
	public void Launch(String browser, String url){
		ReadExcel re = new ReadExcel();
		url = re.getInputData(url);
		if (url.equals("SKIP")) {
			LOGGER.info("---------------WebActions.LAUNCH " + url + " SKIP");
			this.setMessage("SKIP--WebActions.LAUNCH");
			return;
		}
		new UIDriver(re.getInputData(browser));
		WebDriver wadriver = UIDriver.uidriver;
		if (!GetStormProperties.defaultURL.equals("")) {
			url = GetStormProperties.defaultURL;
		}
		try {
			wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wadriver.get(url);
			LOGGER.info("---------------WebActions.LAUNCH " + url + " Done!");
			this.setMessage("DONE--WebActions.LAUNCH");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.LAUNCH " + url + " Failed... Exception error " + e.toString());
			this.setMessage("FAILED--WebActions.LAUNCH--Exception error");
		}
	}
	
	public void NewLaunch(String browser, String url){
		ReadExcel re = new ReadExcel();
		url = re.getInputData(url);
		if (url.equals("SKIP")) {
			LOGGER.info("---------------WebActions.NewLaunch " + url + " SKIP");
			this.setMessage("SKIP--WebActions.NewLaunch");
			return;
		}
		//new UIDriver(re.getInputData(browser));
		WebDriver wadriver = UIDriver.uidriver;
		try {
			wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wadriver.get(url);
			LOGGER.info("---------------WebActions.NewLaunch " + url + " Done!");
			this.setMessage("DONE--WebActions.NewLaunch");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.NewLaunch " + url + " Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.NewLaunch--Exception error");
		}
	}
	
	public void Click(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.CLICK " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.CLICK");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				wadriver.findElement(object).click();
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.CLICK " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.CLICK");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.CLICK " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.CLICK--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.CLICK " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.CLICK--Object does not exist in the application");
			return;
		}
	}
	
	public void ClickGmail(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.ClickGmail " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.ClickGmail");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				List<WebElement> email = wadriver.findElements(object);

				for(WebElement emailsub : email){
					if(emailsub.getText().contains(input) == true){
						emailsub.click();
				        break;
				    }
				}
				LOGGER.info("---------------WebActions.ClickGmail " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.ClickGmail");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.ClickGmail " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.ClickGmail--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.CLICK " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.CLICK--Object does not exist in the application");
			return;
		}
	}
	
	public void Type(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.TYPE " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.TYPE");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				wadriver.findElement(object).sendKeys(Keys.DELETE);
				wadriver.findElement(object).sendKeys(Keys.chord(Keys.CONTROL, "a"), re.getInputData(input));
//				wadriver.findElement(object).sendKeys(re.getInputData(input));
				wadriver.findElement(object).sendKeys(Keys.TAB);
				wadriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.TYPE " + object.toString() + " with " + input + " Done!");
				this.setMessage("DONE--WebActions.TYPE");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.TYPE " + object.toString() + " Failed... Exception error " + e.toString());
				this.setMessage("FAILED--WebActions.TYPE--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.TYPE " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.TYPE--Object does not exist in the application");
			return;
		}
	}
	
	public void Select(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.SELECT " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.SELECT");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				this.WaitToBeClickable(wadriver, object);
				new Select(wadriver.findElement(object)).selectByVisibleText(input);
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.SELECT " + object.toString() + " with " + input + " Done!");
				this.setMessage("DONE--WebActions.SELECT");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.SELECT " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.SELECT--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.SELECT " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.SELECT--Object does not exist in the application");
			return;
		}
	}
	
	public void Tick(String obj, String input){
		//TODO Tick for checkbox should be checked or unchecked
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.TICK " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.TICK");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				wadriver.findElement(object).click();
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.TICK " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.TICK");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.TICK " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.TICK--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.TICK " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.TICK--Object does not exist in the application");
			return;
		}
	}
	
	public void Hover(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.Hover " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.HOVER");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				Actions actions = new Actions(wadriver);
				WebElement we = wadriver.findElement(object);
			    actions.moveToElement(we).click().perform();
				LOGGER.info("---------------WebActions.Hover " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.HOVER");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.Hover " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.HOVER--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.Hover " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.HOVER--Object does not exist in the application");
			return;
		}
	}
	
	public void Scroll(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.Scroll " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.SCROLL");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				WebElement element = wadriver.findElement(object);
				((JavascriptExecutor) wadriver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(500);
				LOGGER.info("---------------WebActions.Scroll " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.SCROLL");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.Scroll " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.SCROLL--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.Scroll " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.SCROLL--Object does not exist in the application");
			return;
		}
	}
	
	public void Upload(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.Upload " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.UPLOAD");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				WebElement fileInput = wadriver.findElement(object);
				fileInput.sendKeys(input);
				LOGGER.info("---------------WebActions.Upload " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.UPLOAD");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.Upload " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.UPLOAD--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.Upload " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.UPLOAD--Object does not exist in the application");
			return;
		}
	}
	
	public void SwitchTab(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.SwitchTab " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.SWITCHTAB");
			return;
		}

		WebDriver wadriver = UIDriver.uidriver;
		try {
		    ArrayList<String> tabs2 = new ArrayList<String> (wadriver.getWindowHandles());
		    wadriver.switchTo().window(tabs2.get(0));
		    wadriver.close();
		    wadriver.switchTo().window(tabs2.get(1));
			LOGGER.info("---------------WebActions.SwitchTab Done!");
			this.setMessage("DONE--WebActions.SWITCHTAB");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.SwitchTab Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.SWITCHTAB--Exception error");
		}
	}
	
	public void SwitchToIFrame(String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.SwitchToIFrame SKIP");
			this.setMessage("SKIP--WebActions.SWITCHTOIFRAME");
			return;
		}
		WebDriver wadriver = UIDriver.uidriver;
		try {
			wadriver.switchTo().frame(input);
			LOGGER.info("---------------WebActions.SwitchToIFrame Done!");
			this.setMessage("DONE--WebActions.SWITCHTOIFRAME");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.SwitchToIFrame Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.SWITCHTOIFRAME--Exception error");
		}
	}
	
	public void SwitchToParentFrame(String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.SwitchToParentFrame SKIP");
			this.setMessage("SKIP--WebActions.SWITCHTOPARENTFRAME");
			return;
		}
		WebDriver wadriver = UIDriver.uidriver;
		try {
			wadriver.switchTo().parentFrame();
			LOGGER.info("---------------WebActions.SwitchToParentFrame Done!");
			this.setMessage("DONE--WebActions.SWITCHTOPARENTFRAME");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.SwitchToParentFrame Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.SWITCHTOPARENTFRAME--Exception error");
		}
	}
	
	public void ClickPopUp(String obj, String input) {
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.CLICKPOPUP " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.CLICKPOPUP");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;

		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				String parentWindowHandler = wadriver.getWindowHandle();
				String subWindowHandler = null;
				Set<String> handles = wadriver.getWindowHandles();
				Iterator<String> iterator = handles.iterator();
				while (iterator.hasNext()){
				    subWindowHandler = iterator.next();
				}
				wadriver.switchTo().window(subWindowHandler); 
				wadriver.findElement(object).click();
				wadriver.switchTo().window(parentWindowHandler);
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.CLICKPOPUP " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.CLICKPOPUP");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.CLICKPOPUP " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.CLICKPOPUP--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.CLICKPOPUP " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.CLICKPOPUP--Object does not exist in the application");
			return;
		}
	}
	
	public void explicitWait(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.explicitWait " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.explicitWait");
			return;
		}
		int sec = (StringUtils.isNumeric(input)) ? Integer.parseInt(input) : 10;
		new UIObject(obj);
		By object = UIObject.uiobject;
		WebDriver wadriver = UIDriver.uidriver;
		WebDriverWait wait = new WebDriverWait(wadriver, sec);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(object));
			LOGGER.info("---------------WebActions.explicitWait " + object.toString() + " Done!");
			this.setMessage("DONE--WebActions.EXWAIT");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.explicitWait Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.EXWAIT--Exception error");
		}
	}
		
	public void waitForInvisible(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.waitForInvisible " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.waitForInvisible");
			return;
		}
		int sec = (StringUtils.isNumeric(input)) ? Integer.parseInt(input) : 10;
		new UIObject(obj);
		By object = UIObject.uiobject;
		WebDriver wadriver = UIDriver.uidriver;
		WebDriverWait wait = new WebDriverWait(wadriver, sec);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(object));
			LOGGER.info("---------------WebActions.waitForInvisible " + object.toString() + " Done!");
			this.setMessage("DONE--WebActions.INVWAIT");
		} catch (NullPointerException e) {
			LOGGER.severe("---------------WebActions.waitForInvisible Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.INVWAIT--Exception error");
		}
	}
	
	public void Sleep(String input){
		int sec = (StringUtils.isNumeric(input)) ? Integer.parseInt(input) : 1000;
		try {
			Thread.sleep(sec);
			LOGGER.info("---------------WebActions.SLEEP for " + sec + " Done!");
			this.setMessage("DONE--WebActions.SLEEP");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.SLEEP Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.SLEEP--Exception error");
		}
	}
	
	public void KeyStroke(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.TYPE " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.KEYSTROKE");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				switch (input.toUpperCase()) {
				case "ENTER":
					wadriver.findElement(object).sendKeys(Keys.ENTER);
					break;
					
				case "DELETE":
					wadriver.findElement(object).sendKeys(Keys.DELETE);
					break;
					
				case "TAB":
					wadriver.findElement(object).sendKeys(Keys.TAB);
					break;

				default:
					break;
				}
				wadriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				LOGGER.info("---------------WebActions.KeyStroke " + object.toString() + " with " + input + " Done!");
				this.setMessage("DONE--WebActions.KEYSTROKE");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.KeyStroke " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.KEYSTROKE--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.KeyStroke " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.KEYSTROKE--Object does not exist in the application");
			return;
		}
	}
	
	public void ObjectExists(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------WebActions.ObjectExists " + obj + " SKIP");
			this.setMessage("SKIP--WebActions.ObjectExists");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = this.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				LOGGER.info("---------------WebActions.ObjectExists " + object.toString() + " Done!");
				this.setMessage("DONE--WebActions.ObjectExists");
			} catch (Exception e) {
				LOGGER.severe("---------------WebActions.ObjectExists " + object.toString() + " Failed... Exception error" + e.toString());
				this.setMessage("FAILED--WebActions.ObjectExists--Exception error");
			}
		}
		else {
			LOGGER.severe("---------------WebActions.ObjectExists " + object.toString() + " Failed. Object does not exist in the application");
			this.setMessage("FAILED--WebActions.ObjectExists--Object does not exist in the application");
			return;
		}
	}
	
	public void Close(){
		try {
			WebDriver wadriver = UIDriver.uidriver;
			wadriver.close();
			wadriver.quit();
			LOGGER.info("---------------WebActions.CLOSE browser Done!");
			this.setMessage("DONE--WebActions.CLOSE");
		} catch (Exception e) {
			LOGGER.severe("---------------WebActions.CLOSE browser Failed... Exception error" + e.toString());
			this.setMessage("FAILED--WebActions.CLOSE--Exception error" );
		}
	}
	
	public boolean VerifyObjectExists(WebDriver wadriver, By object){
		wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		boolean exists = wadriver.findElements(object).size() != 0;
		wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public void WaitToBeClickable(WebDriver wadriver, By object){
		WebDriverWait wait = new WebDriverWait(wadriver, 10); 
		wait.until(ExpectedConditions.elementToBeClickable(object));
	}
	
	public boolean objectIsVisibleAndClickable(WebDriver wadriver, By object){
		boolean isTrue = false;
		WebElement element = wadriver.findElement(object);
		while(isTrue = false){
			wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
			isTrue = element != null && element.isDisplayed() && element.isEnabled();
			wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		}
		return isTrue;
	}
}
