package main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UITables {
	LogFile lf;
	private static final Logger LOGGER = Logger.getLogger(UITables.class.getName());
	
	public List<String[]> getTableData(String obj){//, String input
		List<String[]> list = new ArrayList<>();
//		WebActions wa = new WebActions();
//		ReadExcel re = new ReadExcel();
//		input = re.getInputData(input);
//		if (input.equals("SKIP")) {
//			LOGGER.info("---------------WebActions.CLICK " + obj + " SKIP");
//			wa.setMessage("SKIP--WebActions.CLICK");
//			return null;
//		}
		String id = obj.substring(obj.indexOf("'")+1, obj.indexOf(")")-1);
		new UIObject(obj);
		By object = UIObject.uiobject;
		WebDriver wadriver = UIDriver.uidriver;
		WebElement table_element = wadriver.findElement(By.id(id));
		List<WebElement> tr_collection=table_element.findElements(object);//By.xpath("id('your-orders-slide')/div/table/tbody/tr"));
		for(WebElement trElement : tr_collection) {
		    List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
		    String data[] = new String[td_collection.size()];
		    int i = 0;
		    for(WebElement tdElement : td_collection){
		    	data[i] = tdElement.getText();
		    	i++;
		    }
		    list.add(data);
		}
		return list;
	}
}
