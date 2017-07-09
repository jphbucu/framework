package main;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GetPassedFailed {
	int passed;
	int failed;
	int skipped;
	
	public GetPassedFailed(String sheetName){
		try {
			InputStream inp = new FileInputStream(ActiveTestDetails.resultFileName);//ActiveTestDetails.resultFileName
	        XSSFWorkbook workbook = new XSSFWorkbook(inp);
	        XSSFSheet sheet = workbook.getSheet(sheetName);
	        XSSFRow row;
		    XSSFCell cell;
		    row = sheet.getRow(0);
		    int noOfColumns = row.getLastCellNum();
		    for (int h = 1; h < noOfColumns; h++) {
		    	String msg = "";
		    	for(int i = 1;i < sheet.getPhysicalNumberOfRows();i++){
			    	row = sheet.getRow(i);
			    	cell = row.getCell(h);
			    	if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			    		msg += cell.toString();
				    }
			    }
		    	if (msg.contains("FAILED")) {
					failed++;
				}
		    	else if (msg.contains("SKIPPED")) {
					skipped++;
				}
		    	else {
					passed++;
				}
			}
		    workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int getPassedCount(){
		return passed;
	}
	
	public int getFailedCount(){
		return failed;
	}
	
	public int getSkippedCount(){
		return skipped;
	}

	public static void main(String[] args){
		GetPassedFailed gpf = new GetPassedFailed("TC0001 TestMerc.xlsx");
		System.out.println("Passed " + gpf.getPassedCount());
		System.out.println("Failed " + gpf.getFailedCount());
		System.out.println("Skipped " + gpf.getSkippedCount());
	}
}
