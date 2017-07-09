package main;

import java.util.List;
import java.util.logging.Logger;

public class KudosScripts {
	LogFile lf;
	private static final Logger LOGGER = Logger.getLogger(KudosScripts.class.getName());
	
	public void getTopUpStatus(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		if (!input.contains("|")) {
			LOGGER.info("---------------KudosScripts.getTopUpStatus SKIP. Get Topup status should have an input of '{message1}|{message2}'");
			wa.setMessage("SKIP--getTopUpStatus. Get Topup status should have an input of '{message1}|{message2}'");
			return;
		}
		String msg[] = input.split("\\|");
		String input1 = msg[0];
		String input2 = msg[1];
		
		input1 = re.getInputData(input1);
		input2 = re.getInputData(input2);

		if (input1.contains("SKIP") || input2.contains("SKIP")){
			LOGGER.info("---------------KudosScripts.getTopUpStatus SKIP");
			wa.setMessage("SKIP--getTopUpStatus");
			return;
		}
		
		String topupstatus = "";
		try {
			UITables uit = new UITables();
			List<String[]> list = uit.getTableData(obj);
			for (String[] strings : list) {
				if (strings[1].equals(input1)) {
					topupstatus = strings[5];
					break;
				}
			}
			if (input.contains("{") && input.contains("}")) {
				input2 = msg[1].replace("{", "").replace("}", "");
			}
			re.setInputData(input2, topupstatus);
			wa.setMessage("DONE--KudosScripts.getTopUpStatus. topup status = " + topupstatus);
		} catch (Exception e) {
			wa.setMessage("FAILED--KudosScripts.getTopUpStatus. Exception error");
		}
	}
	
	public void getTopUpID(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		if (!input.contains("|")) {
			LOGGER.info("---------------KudosScripts.getTopUpID SKIP. Get Topup ID should have an input of '{message1}|{message2}'");
			wa.setMessage("SKIP--getTopUpID. Get Topup ID should have an input of '{message1}|{message2}'");
			return;
		}
		String msg[] = input.split("\\|");
		String input1 = msg[0];
		String input2 = msg[1];
		
		input1 = re.getInputData(input1);
		input2 = re.getInputData(input2);

		if (input1.contains("SKIP") || input2.contains("SKIP")){
			LOGGER.info("---------------KudosScripts.getTopUpID SKIP");
			wa.setMessage("SKIP--getTopUpID");
			return;
		}
		
		String topupid = "";
		try {
			UITables uit = new UITables();
			List<String[]> list = uit.getTableData(obj);
			for (String[] strings : list) {
				if (strings[1].equals(input1)) {
					topupid = strings[0];
					break;
				}
			}
			if (input.contains("{") && input.contains("}")) {
				input2 = msg[1].replace("{", "").replace("}", "");
			}
			re.setInputData(input2, topupid);
			wa.setMessage("DONE--KudosScripts.getTopUpID. topup id = " + topupid);
		} catch (Exception e) {
			wa.setMessage("FAILED--KudosScripts.getTopUpID. Exception error");
		}
	}
	
	public void getOrderID(String obj, String input){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		if (input.contains("{") && input.contains("}")) {
			input = input.replace("{", "").replace("}", "");
		}
		if (input.equals("SKIP")) {
			LOGGER.info("---------------KudosScripts.getOrderID " + obj + " SKIP");
			wa.setMessage("SKIP--KudosScripts.getOrderID");
			return;
		}
		try {
			UITables uit = new UITables();
			List<String[]> list = uit.getTableData(obj);
			String[] data = list.get(0);
			String orderid = data[0];
			re.setInputData(input, orderid);
			wa.setMessage("PASS--KudosScripts.getOrderID. order id = " + orderid);
		} catch (Exception e) {
			wa.setMessage("FAILED--KudosScripts.getOrderID. Execption error");
		}
	}
	
	public void getFirstTableData(String obj){
		WebActions wa = new WebActions();
		ReadExcel re = new ReadExcel();
		
		UITables uit = new UITables();
		List<String[]> list = uit.getTableData(obj);
		String[] data = list.get(0);
		String orderid = data[0];
	} 
}
