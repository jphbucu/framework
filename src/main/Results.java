package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Results {
	
	public static void main(String[] args) throws IOException {
		Results rs = new Results();
		rs.createExcel("C:\\Storm\\results\\result.xlsx");
		
	}
	
	public void createExcel(String resultfileName){	
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Result");  
	        XSSFRow rowhead = sheet.createRow((short)0);
	        rowhead.createCell(0).setCellValue("Test Case Name");
	        rowhead.createCell(1).setCellValue("Number of sets");
	        rowhead.createCell(2).setCellValue("Passed");
	        rowhead.createCell(3).setCellValue("Failed");
	        rowhead.createCell(4).setCellValue("Skipped");

	        FileOutputStream fileOut = new FileOutputStream(resultfileName);
	        workbook.write(fileOut);
	        fileOut.close();
	        workbook.close();
	        //TODO logger
		} catch ( Exception ex ) {
			//TODO logger
	    }
	}
	
	public void updateResultFile(String sht, int c, List<String> list){
		try {
			InputStream inp = new FileInputStream(ActiveTestDetails.resultFileName);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheet(sht);
			XSSFRow row;
			XSSFCell cell;
			
			for (int i = 0; i < list.size(); i++) {
				row = sheet.getRow(i + 1);
				if (row == null)
					row = sheet.createRow(i + 1);
				cell = row.getCell(c);
				if (cell == null)
					cell = row.createCell(c);
				cell.setCellValue(list.get(i));
			}

			inp.close();
			FileOutputStream out =new FileOutputStream(new File(ActiveTestDetails.resultFileName));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateResultFile(String sht, int r, int c, String data){
		try {
			InputStream inp = new FileInputStream(ActiveTestDetails.resultFileName);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheet(sht);
			XSSFRow row;
			XSSFCell cell;
			
			row = sheet.getRow(r);
			if (row == null)
				row = sheet.createRow(r);
			cell = row.getCell(c);
			if (cell == null)
				cell = row.createCell(c);
			cell.setCellValue(data);
			if (data.contains("FAILED")) {
				XSSFCellStyle style = wb.createCellStyle();
				 style.setFillForegroundColor(IndexedColors.RED.getIndex());
				 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				 row.getCell(c).setCellStyle(style);
			}

			inp.close();
			FileOutputStream out =new FileOutputStream(new File(ActiveTestDetails.resultFileName));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void createSheets(List<String> sheetNames) throws IOException{
		try {
			InputStream inp = new FileInputStream(ActiveTestDetails.resultFileName);
	        XSSFWorkbook workbook = new XSSFWorkbook(inp);
	        for(String str : sheetNames){
	        	XSSFSheet sheet = workbook.createSheet(str);
	        	XSSFRow rowhead = sheet.createRow((short)0);
		        rowhead.createCell(0).setCellValue("Actions");
	        }
	        FileOutputStream fileOut = new FileOutputStream(ActiveTestDetails.resultFileName);
	        workbook.write(fileOut);
	        fileOut.close();
	        workbook.close();
	    } catch ( Exception ex ) {
	        System.out.println(ex);
	        //TODO
	    }
	}
	
	public List<String> getSheetNames(List<String> list){
		List<String> sheetNames = new ArrayList<String>();
		for(String str : list){
			String s[] = str.split(" ");
			sheetNames.add(s[0]);
		}
		return sheetNames;
	}
	
	public List<String> getActionObject(List<String[]> list){
		List<String> actionObject = new ArrayList<String>();
		for(String str[] : list){
			actionObject.add(str[0] + ":" + str[1]);
		}
		return actionObject;
	}
	
	public void updateCellVal(String filePath, int rowNum, int cellNum, String cellVal){
		try {
			InputStream inp = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);
			XSSFCell cell = row.getCell(cellNum);
			if (cell == null)
				cell = row.createCell(cellNum);
			cell.setCellValue(cellVal);
			inp.close();
			FileOutputStream out =new FileOutputStream(new File(filePath));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch ( Exception ex ) {
	        //TODO logger
	    }
	}
	
	public void resizeExcelColumns(String filePath, int count){
		try {
			InputStream inp = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheetAt(0);
			for (int k = 0;k < count;k++){
				sheet.autoSizeColumn(k);
			}
			inp.close();
			FileOutputStream out = new FileOutputStream(new File(filePath));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch ( Exception ex ) {
	        System.out.println(ex);
	        //TODO
	    }
	}
	
	public int getRowCount(String fileLoc, String fileName, String sheetName) throws IOException{
		InputStream inp = new FileInputStream(fileLoc + fileName);
	    XSSFWorkbook wb = new XSSFWorkbook(inp);
	    XSSFSheet sheet = wb.getSheet(sheetName);
	    XSSFRow row;
	    XSSFCell cell;
	    int rowCount = 0;
	    
	    for(int i = 0;i < sheet.getPhysicalNumberOfRows();i++){
	    	row = sheet.getRow(i);
	    	cell = row.getCell(0);
	    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
		    	rowCount++;
		    }
	    }
	    wb.close();
	    return rowCount;
	}
	
	

//	public String createExcel(String resultfileName){	
//		try {
//	        
//	        XSSFWorkbook workbook = new XSSFWorkbook();
//	        XSSFSheet sheet = workbook.createSheet("Result");  
//
//	        XSSFRow rowhead = sheet.createRow((short)0);
//	        rowhead.createCell(0).setCellValue("Test Case Name");
//	        rowhead.createCell(1).setCellValue("Status");
//
//	        FileOutputStream fileOut = new FileOutputStream(resultfileName);
//	        workbook.write(fileOut);
//	        fileOut.close();
//	        workbook.close();
//	        System.out.println("Excel file has been generated!");
//
//	    } catch ( Exception ex ) {
//	        System.out.println(ex);
//	    }
//		return resultfileName;
//	}
	
//	
//	for (int k = 0;k < 8;k++){//TODO remove hard code
//		if (k != 4) {
//			sheet.autoSizeColumn(k);
//		}
//	}
	
}
