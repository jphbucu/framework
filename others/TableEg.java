package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableEg {
	public static void main(String[] args) throws IOException {
		TableEg te = new TableEg();
		ArrayList<String[]> list = te.ParseHTML("C:\\Task_2\\test.html");
		String testfilename = te.getFileNameFromList(list);
		//System.out.println("testfilename = " + testfilename);
		String resultfilename = "C:\\Task_2\\testing\\" + testfilename + ".xlsx";
		te.createExcel(resultfilename);
		
		String columnNames[] = {"Action", "Object", "Input"};
		list.set(0, columnNames);
		int i = 0;
		for (String[] strings : list) {
			te.updateExccel(resultfilename, strings, i);
			i++;
		}
	}
	
	public String getFileNameFromList(ArrayList<String[]> list){
		String filename = "";
		for (String[] strings : list) {
			filename = strings[0];
			break;
		}
		return filename;
	}
	
	public ArrayList<String[]> ParseHTML(String file){
		ArrayList<String[]> list = new ArrayList<>();
		try {
			File input = new File(file);
			Document doc = Jsoup.parse(input, "UTF-8", "http://test.com/");
			Elements tableElements = doc.select("table");
			Elements tableRowElements = tableElements.select(":not(thead) tr");
			for (int i = 0; i < tableRowElements.size(); i++) {
				Element row = tableRowElements.get(i);
				Elements rowItems = row.select("td");
				String rowI[] = new String[rowItems.size()];
				for (int j = 0; j < rowItems.size(); j++) {
					String text = rowItems.get(j).text();
					String arr[] = text.split("=");
					if (arr.length < 2) {
						rowI[j] = text;
					}
					else {
						rowI[j] = arr[0] + "|" +  text.substring(arr[0].length() + 1, text.length());
					}
				}
				list.add(rowI);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
//	public ArrayList<String[]> ParseHTML(String file){
//		ArrayList<String[]> list = new ArrayList<>();
//		try {
//			File input = new File(file);
//			Document doc = Jsoup.parse(input, "UTF-8", "http://test.com/");
//			Elements tableElements = doc.select("table");
//			Elements tableRowElements = tableElements.select(":not(thead) tr");
//			for (int i = 0; i < tableRowElements.size(); i++) {
//				Element row = tableRowElements.get(i);
//				Elements rowItems = row.select("td");
//				String rowI[] = new String[rowItems.size()];
//				for (int j = 0; j < rowItems.size(); j++) {
//					rowI[j] = rowItems.get(j).text();
//				}
//				list.add(rowI);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	public void createExcel(String resultfileName){	//, String columnNames[]
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet(); 
	        sheet.createRow((short)0);
	        FileOutputStream fileOut = new FileOutputStream(resultfileName);
	        workbook.write(fileOut);
	        fileOut.close();
	        workbook.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
	    }
	}
	
	public void updateExccel(String filePath, String input[], int rowNum){
		try {
			InputStream inp = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);
			for (int i = 0; i < input.length; i++) {
				XSSFCell cell = row.getCell(i);
				if (cell == null)
					cell = row.createCell(i);
				cell.setCellValue(input[i]);
			}
			inp.close();
			FileOutputStream out = new FileOutputStream(new File(filePath));
	        wb.write(out);
	        out.close();
	        wb.close();
		} catch ( Exception ex ) {
	        //TODO logger
	    }
	}
}