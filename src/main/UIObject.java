package main;

import java.util.logging.Logger;

import org.openqa.selenium.By;

public class UIObject {
	static By uiobject;
	private static final Logger LOGGER = Logger.getLogger(UIObject.class.getName());
	LogFile lf;
		
	public UIObject(String obj){
		if (!obj.contains("|")) {
			obj = this.getObjectFromDB(obj);
		}
		
		ReadExcel re = new ReadExcel();
		String additionalObject = "";
		if (obj.contains("{") && obj.contains("}")) {
			additionalObject = obj.substring(obj.indexOf("{"), obj.indexOf("}") + 1);
			additionalObject = re.getInputData(additionalObject);
			LOGGER.info("---------------UIObject Add " + additionalObject + " object to " + obj);
			obj = obj.replaceAll("\\{.*\\}", additionalObject);
		}

		if (obj.equals("")) {
			//TODO log error string obj is BLANK for x action
			LOGGER.severe("---------------UIObject ERROR. String obj is blank.");
		}
		else if (obj.contains("|")) {
			this.createUIObject(obj);
		}
	}
	
	public void createUIObject(String obj){
		String locator = "";
		String property = "";
		try {
			String param[] = obj.split("\\|");
			locator = param[0];
			property = param[1];
		} catch (Exception e) {
			LOGGER.severe("---------------UIObject.createUIObject ERROR " + lf.getBaseError(e.toString()));
		}
		
		try {
			switch (locator.toUpperCase()) {
			case "ID":
				uiobject = By.id(property);
				break;
				
			case "NAME":
				uiobject = By.name(property);
				break;
				
			case "XPATH":
				uiobject = By.xpath(property);
				break;
				
			case "CSS":
				uiobject = By.cssSelector(property);
				break;
				
			case "LINKTEXT":
				uiobject = By.linkText(property);
				break;
				
			case "CLASSNAME":
				uiobject = By.className(property);
				break;
				
			case "PARTIALLINKTEXT":
				uiobject = By.partialLinkText(property);
				break;
				
			case "TAGNAME":
				uiobject = By.tagName(property);
				break;
				
			default:
				uiobject = By.name(property);
				break;
			}
			
		} catch (Exception e) {
			LOGGER.severe("---------------UIObject.createUIObject ERROR, could not create object. " + lf.getBaseError(e.toString()));
		}
		LOGGER.info("---------------UIObject.createUIObject Object " + uiobject.toString() + " is created.");
	}
	
	public String getObjectFromDB(String obj){
		ReadExcel re = new ReadExcel();
		obj = re.getObjectDB(obj);
		return obj;
	}
	
	public By getUIObject(){
		return uiobject;
	}
}
