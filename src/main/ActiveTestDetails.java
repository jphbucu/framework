package main;

public class ActiveTestDetails {
	static String currentTest;
	static int currentLine;
	static int currentDataset;
	static String objectDBFileName;
	static String resultFileName;
	static String currentSheetName;
	
	public ActiveTestDetails(){
		
	}
	
	public void setCurrentTest(String testName){
		ActiveTestDetails.currentTest = testName;
	}
	
	public void setCurrentLine(int line){
		ActiveTestDetails.currentLine = line;
	}
	
	public void setCurrentDataset(int dataset){
		ActiveTestDetails.currentDataset = dataset;
	}
	
	public void setObjectDBFileName(String file){
		ActiveTestDetails.objectDBFileName = file;
	}
	
	public void setResultFileName(String filename){
		ActiveTestDetails.resultFileName = filename;
	}
	
	public void setCurrentSheetName(String sheetname){
		ActiveTestDetails.currentSheetName = sheetname;
	}
}
