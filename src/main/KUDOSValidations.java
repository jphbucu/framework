package main;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class KUDOSValidations {
	private static final Logger LOGGER = Logger.getLogger(KUDOSValidations.class.getName());
	
	public static void main(String[] args) {
//		double a = 101.00;
//		double b = 102;
//		double c = a - b;
//		String x = "100.00";
//		
//		if (NumberUtils.isParsable(x)) {
//			System.out.println("numeric");
//		}
//		else
//			System.out.println("not numeric");
		
		String a = "{abc}|{def}";
		if (a.contains("|")) {
			System.out.println("");
		}
		String msg[] = a.split("\\|");
		String message1 = msg[0];
		String message2 = msg[1];
		System.out.println("message1 " + message1);
		System.out.println("message2 " + message2);
	}
	
	public void validate(String validation, String input){
		switch (validation.toUpperCase()) {
		
		case "CONTAINSMESSAGE":
			this.containsMessage(input);
			break;
			
		case "COMPAREMESSAGE":
			this.compareMessage(input);
			break;
			
		case "LOGINMESSAGE":
			this.loginMessage(input);
			break;
			
		case "POINTSDEDUCTION":
			this.pointsDeduction(input);
			break;
			
		case "POINTSADDED":
			this.pointsAdded(input);
			break;
			
		case "ORDERMADE":
			this.orderMade(input);
			break;
			
		default:
			break;
		}
	}
	
	public void containsMessage(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (!input.contains("|")) {
			LOGGER.info("---------------KUDOSValidations.containsMessage SKIP. Contains message should have an input of '{message1}|{message2}'");
			wa.setMessage("SKIP--containsMessage. Contains message should have an input of '{message1}|{message2}'");
			return;
		}
		String msg[] = input.split("\\|");
		String message1 = msg[0];
		String message2 = msg[1];
		
		message1 = re.getInputData(message1);
		message2 = re.getInputData(message2);
		
		if (message1.contains("SKIP") || message2.contains("SKIP")){
			LOGGER.info("---------------KUDOSValidations.containsMessage SKIP");
			wa.setMessage("SKIP--containsMessage");
			return;
		}
		
		if (message1.contains(message2)) {
			LOGGER.info("--KUDOSValidations.containsMessage PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.containsMessage");
		}
		else {
			LOGGER.info("--KUDOSValidations.containsMessage FAILED. error message '" + message1 + "' does not contain input message '" + message2 + "'");
			wa.setMessage("FAILED--KUDOSValidations.containsMessage -- error message '" + message1 + "' does not contain input message '" + message2 + "'");
		}
	}
	
	public void compareMessage(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (!input.contains("|")) {
			LOGGER.info("---------------KUDOSValidations.compareMessage SKIP. Compare message should have an input of '{message1}|{message2}'");
			wa.setMessage("SKIP--compareMessage. Compare message should have an input of '{message1}|{message2}'");
			return;
		}
		String msg[] = input.split("\\|");
		String message1 = msg[0];
		String message2 = msg[1];
		
		message1 = re.getInputData(message1);
		message2 = re.getInputData(message2);
		
		if (message1.contains("SKIP") || message2.contains("SKIP")){
			LOGGER.info("---------------KUDOSValidations.compareMessage SKIP");
			wa.setMessage("SKIP--compareMessage");
			return;
		}
		
		if (message1.equals(message2)) {
			LOGGER.info("--KUDOSValidations.compareMessage PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.compareMessage");
		}
		else {
			LOGGER.info("--KUDOSValidations.compareMessage FAILED. error message '" + message1 + "' does not match input message '" + message2 + "'");
			wa.setMessage("FAILED--KUDOSValidations.compareMessage -- error message '" + message1 + "' does not match input message '" + message2 + "'");
		}
	}
	
	public void loginMessage(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.loginMessage SKIP");
			wa.setMessage("SKIP--loginMessage");
			return;
		}
		
		String message = re.getDatasetForInputData("message");
		input = re.getInputData(input);
		
		if (message.equals(input)) {
			LOGGER.info("--KUDOSValidations.loginMessage PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.loginMessage");
		}
		else {
			LOGGER.info("--KUDOSValidations.loginMessage FAILED. Login message '" + message + "' does not match input message '" + input + "'");
			wa.setMessage("FAILED--KUDOSValidations.loginMessage -- Login message '" + message + "' does not match input message '" + input + "'");
		}
	}
	
	public void pointsDeduction(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.pointsDeduction SKIP");
			wa.setMessage("SKIP--pointsDeduction");
			return;
		}
		
		String sp = re.getDatasetForInputData("startingpoints").replace(",", "");
		double startingpoints = 0;
		if (NumberUtils.isParsable(sp)) {
			startingpoints = Double.parseDouble(sp);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsDeduction FAILED. Starting point is not a number or is null : " + sp);
			wa.setMessage("FAILED--KUDOSValidations.pointsDeduction. Starting point is not a number or is null : " + sp);
			return;
		}
		
		String pp = re.getDatasetForInputData("pointspayment").replace(",", "");
		double pointspayment = 0;
		if (NumberUtils.isParsable(pp)) {
			pointspayment = Double.parseDouble(pp);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsDeduction FAILED. Points payment is not a number or is null : " + pp);
			wa.setMessage("FAILED--KUDOSValidations.pointsDeduction. Points payment is not a number or is null : " + pp);
			return;
		}
		
		
		String np = re.getDatasetForInputData("newpoints").replace(",", "");
		double newpoints = 0;
		if (NumberUtils.isParsable(pp)) {
			newpoints = Double.parseDouble(np);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsDeduction FAILED. New points is not a number or is null : " + np);
			wa.setMessage("FAILED--KUDOSValidations.pointsDeduction. New points is not a number or is null : " + np);
			return;
		}
		
		double finalpoints = startingpoints - pointspayment;
		
		if (finalpoints == newpoints) {
			LOGGER.info("--KUDOSValidations.pointsDeduction PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.pointsDeduction");
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsDeduction FAILED. finalpoints = " + finalpoints + " | newpoints = " + newpoints);
			wa.setMessage("FAILED--KUDOSValidations.pointsDeduction");
		}
	}
	
	public void pointsAdded(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (!input.contains("|")) {
			LOGGER.info("---------------KUDOSValidations.pointsAdded SKIP. Points added should have an input of '{input1}|{input2}|{input3}'");
			wa.setMessage("SKIP--pointsAdded. Points added should have an input of '{input1}|{input2}|{input3}'");
			return;
		}
		
		String arr[] = input.split("\\|");
		String startingpoints = arr[0];
		String pointstoadd = arr[1];
		String newpoints = arr[2];
		
		input = re.getInputData(startingpoints);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.pointsAdded SKIP");
			wa.setMessage("SKIP--pointsAdded");
			return;
		}
		
		input = re.getInputData(pointstoadd);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.pointsAdded SKIP");
			wa.setMessage("SKIP--pointsAdded");
			return;
		}
		
		input = re.getInputData(newpoints);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.pointsAdded SKIP");
			wa.setMessage("SKIP--pointsAdded");
			return;
		}
		
		
		startingpoints = re.getInputData(startingpoints).replace(",", "");
		double sp = 0;
		if (NumberUtils.isParsable(startingpoints)) {
			sp = Double.parseDouble(startingpoints);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsAdded FAILED. Starting point is not a number or is null : " + sp);
			wa.setMessage("FAILED--KUDOSValidations.pointsAdded. Starting point is not a number or is null : " + sp);
			return;
		}
		
		pointstoadd = re.getInputData(pointstoadd).replace(",", "");
		double pa = 0;
		if (NumberUtils.isParsable(pointstoadd)) {
			pa = Double.parseDouble(pointstoadd);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsAdded FAILED. Points to add is not a number or is null : " + pa);
			wa.setMessage("FAILED--KUDOSValidations.pointsAdded. Points to add is not a number or is null : " + pa);
			return;
		}
		
		
		newpoints = re.getInputData(newpoints).replace(",", "");
		double np = 0;
		if (NumberUtils.isParsable(newpoints)) {
			np = Double.parseDouble(newpoints);
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsAdded FAILED. New points is not a number or is null : " + np);
			wa.setMessage("FAILED--KUDOSValidations.pointsAdded. New points is not a number or is null : " + np);
			return;
		}
		
		double finalpoints = sp + pa;
		
		if (finalpoints == np) {
			LOGGER.info("--KUDOSValidations.pointsAdded PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.pointsAdded");
		}
		else {
			LOGGER.info("--KUDOSValidations.pointsAdded FAILED. finalpoints = " + finalpoints + " | newpoints = " + np);
			wa.setMessage("FAILED--KUDOSValidations.pointsAdded");
		}
	}
	
	public void orderMade(String input){
		ReadExcel re = new ReadExcel();
		WebActions wa = new WebActions();
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KUDOSValidations.pointsDeduction SKIP");
			wa.setMessage("SKIP--WebActions.CLICK");
			return;
		}
		
		String soid = re.getDatasetForInputData("startingorderid");
		int startingorderid = 0;
		if (NumberUtils.isParsable(soid)) {
			startingorderid = Integer.parseInt(soid);
		}
		else if (soid.equals("You don't have orders history yet.")) {
			startingorderid = 0;
		}
		else {
			LOGGER.info("--KUDOSValidations.orderMade FAILED. Starting order id is not a number or is null : " + soid);
			wa.setMessage("FAILED--KUDOSValidations.orderMade. Starting order id is not a number or is null : " + soid);
			return;
		}
		
		String noid = re.getDatasetForInputData("neworderid");
		int neworderid = 0;
		if (NumberUtils.isParsable(noid)) {
			neworderid = Integer.parseInt(noid);
		}
		else {
			LOGGER.info("--KUDOSValidations.orderMade FAILED. Starting order id is not a number or is null : " + noid);
			wa.setMessage("FAILED--KUDOSValidations.orderMade. Starting order id is not a number or is null : " + noid);
			return;
		}
		
		if (neworderid > startingorderid) {
			LOGGER.info("--KUDOSValidations.orderMade PASSED.");
			wa.setMessage("PASSED--KUDOSValidations.orderMade");
		}
		else {
			LOGGER.info("--KUDOSValidations.orderMade FAILED. startingorderid = " + startingorderid + " | neworderid = " + neworderid);
			wa.setMessage("FAILED--KUDOSValidations.orderMade");
		}
	}
	
	public void validate3(String obj, String input){
		WebDriver wadriver = UIDriver.uidriver;
		
        WebElement table_element = wadriver.findElement(By.id("your-orders-slide"));
        List<WebElement> tr_collection=table_element.findElements(By.xpath("id('your-orders-slide')/div/table/tbody/tr"));//*[@id="your-orders-slide"]/div/table/tbody/tr[1]

        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        int row_num,col_num;
        row_num=1;
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
            col_num=1;
            for(WebElement tdElement : td_collection)
            {
                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
                col_num++;
            }
            row_num++;
        } 
	}
	
	public void validate2(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------Validations_KUDOS.validate " + obj + " SKIP");
			return;
		}
		new UIObject(obj);
		By object = UIObject.uiobject;
		WebDriver wadriver = UIDriver.uidriver;
		String text = wadriver.findElement(object).getAttribute("style");
		if (text.contains("default_profile_pic.jpg")) {
			System.out.println("default pic");
			//TODO
		}
		else
		System.out.println("there is a picture");
	}
	
	public void validate1(String obj, String input){
		ReadExcel re = new ReadExcel();
		input = re.getInputData(input);
		if (input.equals("SKIP")) {
			LOGGER.info("---------------Validations_KUDOS.validate " + obj + " SKIP");
			return;
		}
		
		new UIObject(obj);
		By object = UIObject.uiobject;
		WebDriver wadriver = UIDriver.uidriver;
		WebElement element;
		element = wadriver.findElement(object);
		JavascriptExecutor executor = (JavascriptExecutor) wadriver;
		Object aa=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
		System.out.println(aa.toString());
		System.out.println("text " + element.getText());
	}
}
