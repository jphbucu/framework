package main;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GetProperties {
	LogFile lf;
	static String attribute;
	static String text;
	static String innerhtml;
	private static final Logger LOGGER = Logger.getLogger(GetProperties.class.getName());
	
	public void getInnerHTMLProperty(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();

		String msg = re.getInputData(input);
		if (msg.equals("SKIP")) {
			LOGGER.info("---------------GetProperties.getInnerHTMLProperty " + obj + " SKIP");
			wa.setMessage("SKIP--GetProperties.GETINNERHTML");
			return;
		}
		
		if (input.contains("{") && input.contains("}")) {
			input = input.replace("{", "").replace("}", "");
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = wa.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				innerhtml = wadriver.findElement(object).getAttribute("innerHTML").toString();
				re.setInputData(input, innerhtml);
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------GetProperties.getInnerHTMLProperty " + object.toString() + " Passed!");
				wa.setMessage("DONE--GetProperties.GETINNERHTML");
			} catch (Exception e) {
				LOGGER.severe("---------------GetProperties.getInnerHTMLProperty " + object.toString() + " Failed... " + lf.getBaseError(e.toString()));
				wa.setMessage("FAILED--GetProperties.GETINNERHTML--" + lf.getBaseError(e.toString()));
			}
		}
		else {
			LOGGER.severe("---------------GetProperties.getInnerHTMLProperty " + object.toString() + " Failed. Object does not exist in the application");
			wa.setMessage("FAILED--GetProperties.GETINNERHTML--Object does not exist in the application");
			return;
		}
	}
	
	public void getAttributeProperty(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		String msg[] = input.split("\\|");
		
		input = msg[1];
		if (input.contains("{") && input.contains("}")) {
			input = input.replace("{", "").replace("}", "");
		}
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------GetProperties.getAttributeProperty " + obj + " SKIP");
			wa.setMessage("SKIP--GetProperties.GETATTRIBUTE");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = wa.VerifyObjectExists(wadriver, object);
		String attributename = msg[0];
		attributename = re.getInputData(attributename);
		if (exists == true) {
			try {
				attribute = wadriver.findElement(object).getAttribute(attributename);
				re.setInputData(input, attribute);
				LOGGER.info("---------------GetProperties.getAttributeProperty " + object.toString() + " Passed!");
				wa.setMessage("DONE--GetProperties.GETATTRIBUTE");
			} catch (Exception e) {
				LOGGER.severe("---------------GetProperties.getAttributeProperty " + object.toString() + " Failed... " + lf.getBaseError(e.toString()));
				wa.setMessage("FAILED--GetProperties.GETATTRIBUTE--" + lf.getBaseError(e.toString()));
			}
		}
		else {
			LOGGER.severe("---------------GetProperties.getAttributeProperty " + object.toString() + " Failed. Object does not exist in the application");
			wa.setMessage("FAILED--GetProperties.GETATTRIBUTE--Object does not exist in the application");
			return;
		}
	}
	
	public void getTextProperty(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		
		String msg = re.getInputData(input);
		if (msg.equals("SKIP")) {
			LOGGER.info("---------------GetProperties.getTextProperty " + obj + " SKIP");
			wa.setMessage("SKIP--GetProperties.GETTEXT");
			return;
		}
		
		if (input.contains("{") && input.contains("}")) {
			input = input.replace("{", "").replace("}", "");
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		
		WebDriver wadriver = UIDriver.uidriver;
		boolean exists = wa.VerifyObjectExists(wadriver, object);
		if (exists == true) {
			try {
				text = wadriver.findElement(object).getText();
				re.setInputData(input, text);
				wadriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				LOGGER.info("---------------GetProperties.getTextProperty " + object.toString() + " Passed!");
				wa.setMessage("DONE--GetProperties.GETTEXT");
			} catch (Exception e) {
				LOGGER.severe("---------------GetProperties.getTextProperty " + object.toString() + " Failed... " + lf.getBaseError(e.toString()));
				wa.setMessage("FAILED--GetProperties.GETTEXT--" + lf.getBaseError(e.toString()));
			}
		}
		else {
			LOGGER.severe("---------------GetProperties.getTextProperty " + object.toString() + " Failed. Object does not exist in the application");
			wa.setMessage("FAILED--GetProperties.GETTEXT--Object does not exist in the application");
			return;
		}
	}
}
