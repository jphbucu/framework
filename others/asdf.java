//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;
//
//public class TestAF {
//	
//	private static final Logger LOGGER = Logger.getLogger(TestAF.class.getName());
//	
//	private String fileName;
//	
//	private boolean initSuccessFlag;
//	
//	public void init() throws InitException {
//		// load general app config
//		
//		// load input files config
//		
//		// load action files config
//		
//		initSuccessFlag = true;
//	}
//	
//	public void start() {
//		if (initSuccessFlag == false) {
//			// do not start if init failed
//		}
//		
//		// start runners
//		
//		// start file
//		
//		//
//		
//		FileRunner fileRunner;
//		
//		for (int i=0; i<totalFiles; i++) {
//			fileRunner.processFile(fileName);
//		}
//	}
//	
//	public static void main(String[] args) throws IOException {
//		
//		TestAF testAF = new TestAF();
//		try { 
//			testAF.init();
//		} catch (InitException ie) {
//			LOGGER.severe("Cannot Start due to: " + ie);
//			return;
//		}
//		
//		testAF.start();
//		LOGGER.info("TestAF has started!");
//		/////
//		long startTime = System.nanoTime();
//		GetStormProperties gsp = new GetStormProperties();
//		new TimeStamp(GetStormProperties.timeStampFormat);
//		new LogFile();
//		LOGGER.addHandler(LogFile.fh);
//		
//		LOGGER.info("==================================================");
//		LOGGER.info("-----START of STORM Automation Framework " + LOGGER.getName());
//		new SetFilePaths(GetStormProperties.stormHome);
//		gsp.SetStormProperties();
//		
//		FindFiles ff = new FindFiles();
//		List<String> files = new ArrayList<>();
//		ff.getFileName(SetFilePaths.testLocation, files);
//		ReadExcel re = new ReadExcel();
//		
//		String resultfilename = SetFilePaths.resultLocation + GetStormProperties.resultFileName + TimeStamp.timestamp + ".xlsx";
//		Results rs = new Results();
//		rs.createExcel(resultfilename);
//		
//		ActiveTestDetails atd = new ActiveTestDetails();
//		atd.setResultFileName(resultfilename);
//		List<String> sheetNames = rs.getSheetNames(files);
//		rs.createSheets(sheetNames);
//		rs.updateResultFile("Result", 0, files);
//		int sn = 0;
//		int tcn = 1;
//		for (String fn : files) {
//			int line = 1;
//			atd.setObjectDBFileName(GetStormProperties.objectDBName);
//			atd.setCurrentTest(fn);
//			atd.setCurrentSheetName(sheetNames.get(sn));
//			sn++;
//			LOGGER.info("----------START execution for " + fn);
//			List<String[]> list = re.getExcelData(ff.getFilePath(SetFilePaths.testLocation, fn));
//			if (list.isEmpty()) {
//				//TODO if the test is empty, this should log as FAILED in the results file
//				LOGGER.severe("-----Test case " + fn + " is empty. Proceeding to the next test case");
//				continue;
//			}
//			List <String> actionObject = rs.getActionObject(list);
//			rs.updateResultFile(ActiveTestDetails.currentSheetName, 0, actionObject);
//			String action;
//			String parameter;
//			String input;
//			int dbset = re.getDatasetCount(ff.getFilePath(SetFilePaths.testLocation, fn));
//			if (dbset < 2) {
//				//this guarantees that even w/o dataset test will run at least once
//				dbset = 2;
//				LOGGER.warning("-----Test case " + fn + " has no dataset. Executing test as single");
//			}
//			for (int i = 1; i < dbset; i++) {
//				atd.setCurrentDataset(i);
//				String tn = re.getDatasetForInputData("testname");
//				rs.updateResultFile(ActiveTestDetails.currentSheetName, 0, i, tn);
//				LOGGER.info("Running Test set " + tn);
//				int r = 1;
//				for (String[] str : list) {
//					atd.setCurrentLine(line);
//					action = str[0];
//					parameter = str[1];
//					input = str[2];
//					new ExecuteAction(action, parameter, input);
//					rs.updateResultFile(ActiveTestDetails.currentSheetName, r, i, WebActions.message);
//					r++;
//					line++;
//				}
//				if (GetStormProperties.executionType.equals("single")) {break;}
//			}
//			GetPassedFailed gpf = new GetPassedFailed(ActiveTestDetails.currentSheetName);
//			rs.updateResultFile("Result", tcn, 1, (dbset-1) + "");//number of datasets
//			rs.updateResultFile("Result", tcn, 2, "" + gpf.getPassedCount());//passed
//			rs.updateResultFile("Result", tcn, 3, "" + gpf.getFailedCount());//failed
//			rs.updateResultFile("Result", tcn, 4, "" + gpf.getSkippedCount());//skipped
//			tcn++;
//			LOGGER.info("-----END execution for " + fn);
//		}
//		LOGGER.info("-----END of STORM Automation Framework " + LOGGER.getName());
//		LOGGER.info("==================================================");
//		long elapsedTime = System.nanoTime() - startTime;
//		elapsedTime = TimeUnit.NANOSECONDS.toSeconds(elapsedTime);
//		System.out.println(elapsedTime);
//	}
//}
//package framework;
//
//public class asdf {
//
//}
