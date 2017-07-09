package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	private static final Logger LOGGER = Logger.getLogger(ReadExcel.class.getName());
	LogFile lf;
	
	public ReadExcel(){
		
	}
	
	public List<String[]> getExcelData(String fileLoc) throws IOException{
		LOGGER.info("----------START of ReadExcel.getExcelData for " + ActiveTestDetails.currentTest);
		List<String[]> list = new ArrayList<String[]>();
		InputStream inp = new FileInputStream(fileLoc);
	    XSSFWorkbook wb = new XSSFWorkbook(inp);
	    XSSFSheet sheet = wb.getSheetAt(0);
	    XSSFRow row; 
	    int rowNum = this.getRowCount(fileLoc);
	    XSSFCell cell;
	    String action = null;
	    String parameter = null;
	    String input = null;
	    
	    for (int i = 1; i < rowNum; i++) {
	    	row = sheet.getRow(i);
	    	
	    	cell = row.getCell(0);
	    	if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	    		action = "";
		    }
		    else action = cell.toString();
	    	
	    	cell = row.getCell(1);
	    	if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	    		parameter = "";
		    }
		    else parameter = cell.toString();
	    	
	    	cell = row.getCell(2);
	    	if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	    		input = "";
		    }
		    else input = cell.toString();
	    	
			String data[] = {action, parameter, input};
			list.add(data);
		}
	    wb.close();
	    LOGGER.info("----------END of ReadExcel.getExcelData for " + ActiveTestDetails.currentTest);
	    return list;
	}
	
	public int getRowCount(String fileLoc) throws IOException{
		//LOGGER.addHandler(LogFile.fh);
		int rowCount = 0;
		try {
			InputStream inp = new FileInputStream(fileLoc);
		    XSSFWorkbook wb = new XSSFWorkbook(inp);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;
		    for(int i = 0;i < sheet.getPhysicalNumberOfRows();i++){
		    	row = sheet.getRow(i);
		    	cell = row.getCell(0);
		    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			    	rowCount++;
			    }
		    }
		    wb.close();
		} catch (Exception e) {
			LOGGER.severe("---------------ReadExcel.getRowCount ERROR " + lf.getBaseError(e.toString()));
		}
	    return rowCount;
	}
	
	public int getDatasetCount(String fileLoc){
		//LOGGER.addHandler(LogFile.fh);
		int rowCount = 0;
		try {
			InputStream inp = new FileInputStream(fileLoc);
		    XSSFWorkbook wb = new XSSFWorkbook(inp);
		    XSSFSheet sheet = wb.getSheetAt(1);
		    XSSFRow row;
		    XSSFCell cell;
		    for(int i = 0;i < sheet.getPhysicalNumberOfRows();i++){
		    	row = sheet.getRow(i);
		    	cell = row.getCell(0);
		    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			    	rowCount++;
			    }
		    }
		    wb.close();
		} catch (Exception e) {
			LOGGER.severe("---------------ReadExcel.getDatasetCount ERROR " + lf.getBaseError(e.toString()));
		}
	    return rowCount;
	}
	
	public String getInputData(String input){
		String inputdata = "";
		if (input.contains("{") && input.contains("}")) {
			inputdata = input.replace("{", "").replace("}", "");
			inputdata = this.getDatasetForInputData(inputdata);
		}
		else {
			inputdata = input;
		}
		return inputdata;
	}
	
	public String getDatasetForInputData(String input){
		//LOGGER.addHandler(LogFile.fh);
		String result = "";
		//int dset = ActiveTestDetails.currentDataset;
		FindFiles ff = new FindFiles();
		int columnValue = 0;
		try {
			InputStream inp = new FileInputStream(ff.getFilePath(SetFilePaths.testLocation, ActiveTestDetails.currentTest));
		    XSSFWorkbook wb = new XSSFWorkbook(inp);
		    XSSFSheet sheet = wb.getSheetAt(1);
		    XSSFRow row;
		    XSSFCell cell;
		    row = sheet.getRow(0);
		    int noOfColumns = row.getLastCellNum();
		    for(int i = 0;i < noOfColumns;i++){
		    	cell = row.getCell(i);
		    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			    	String cellValue = cell.toString();
			    	if (cellValue.equalsIgnoreCase(input)) {
						columnValue = i;
					}
			    }
		    }
		    //TODO what to do if input is not found?
		    row = sheet.getRow(ActiveTestDetails.currentDataset);
		    cell = row.getCell(columnValue);
		    if ((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
		    	result = "";
		    }
		    else {
		    	result = cell.toString();
			}
		    wb.close();
		} catch (Exception e) {
			LOGGER.severe("---------------ReadExcel.getDatasetForInputData [" + input + "] ERROR " + lf.getBaseError(e.toString()));
		}
		return result;
	}
	
	public void setInputData(String input, String data){
		//LOGGER.addHandler(LogFile.fh);
		FindFiles ff = new FindFiles();
		int columnValue = 0;
		try {
			InputStream inp = new FileInputStream(ff.getFilePath(SetFilePaths.testLocation, ActiveTestDetails.currentTest));
		    XSSFWorkbook wb = new XSSFWorkbook(inp);
		    XSSFSheet sheet = wb.getSheetAt(1);
		    XSSFRow row;
		    XSSFCell cell;
		    row = sheet.getRow(0);
		    int noOfColumns = row.getLastCellNum();
		    for(int i = 0;i < noOfColumns;i++){
		    	cell = row.getCell(i);
		    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			    	String cellValue = cell.toString();
			    	if (cellValue.equalsIgnoreCase(input)) {
						columnValue = i;
					}
			    }
		    }
		    row = sheet.getRow(ActiveTestDetails.currentDataset);
		    cell = row.getCell(columnValue);
		    if (cell == null)
				cell = row.createCell(columnValue);
			cell.setCellValue(data);
			inp.close();
			FileOutputStream out =new FileOutputStream(new File(ff.getFilePath(SetFilePaths.testLocation, ActiveTestDetails.currentTest)));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch (Exception e) {
			LOGGER.severe("---------------ReadExcel.setInputData [" + input + "] ERROR " + lf.getBaseError(e.toString()));
		}
	}
	
	public String getObjectDB(String obj){
		String newObj = "";
		boolean found = false;
		try {
			InputStream inp = new FileInputStream(SetFilePaths.objectdbLocation + ActiveTestDetails.objectDBFileName);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			
			int rowNum = this.getRowCount(SetFilePaths.objectdbLocation + ActiveTestDetails.objectDBFileName);
			for (int i = 1; i < rowNum; i++) {
				row = sheet.getRow(i);
				cell = row.getCell(0);
				if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
					String cellValue = cell.toString();
			    	if (cellValue.equalsIgnoreCase(obj)) {
			    		cell = row.getCell(1);
			    		found = true;
			    		if ((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
					    	newObj = "";
					    }
					    else {
					    	newObj = cell.toString();
						}
					}
			    }
			}
		    wb.close();
		    if (found == false) {
		    	LOGGER.warning("ReadExcel.getObjectDB " + obj + "Object not found in " + ActiveTestDetails.objectDBFileName);
				
			}
		} catch (Exception e) {
			LOGGER.severe("---------------ReadExcel.getObjectDB ERROR " + lf.getBaseError(e.toString()));
		}
		return newObj;
	}
}
