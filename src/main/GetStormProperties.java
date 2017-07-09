package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class GetStormProperties {
	static String stormHome;
	static String timeStampFormat;
	static String resultFileName;
	static String testFileType;
	static String objectDBName;
	static String executionType;
	static String defaultURL;
	
	LogFile lf;
	
	private static final Logger LOGGER = Logger.getLogger(GetStormProperties.class.getName());
	
	public GetStormProperties(){
		System.out.println("-----START of GetStormProperties");
		this.getStormHome();
		this.getPropertyTimeStampFormat();
		System.out.println("-----END of GetStormProperties");
	}
	
	public void getStormHome(){
		try {
			stormHome = System.getenv("STORM_HOME");
			stormHome = stormHome.substring(stormHome.length()-1).equals("\\") ? stormHome : stormHome + "\\";
			System.out.println("-----STORM_HOME = " + stormHome);
		} catch (Exception e) {
			System.out.println("----------ERROR getting environment variable STORM_HOME " + lf.getBaseError(e.toString()));
			System.exit(0);
		}
	}
	
	public void getPropertyTimeStampFormat(){
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			timeStampFormat = p.getProperty("timestampformat");
			if (timeStampFormat.equals("")) {
				timeStampFormat = "yyyyMMdd_hhmmss";
			}
			System.out.println("-----timeStampFormat = " + timeStampFormat);
		} catch (Exception e) {
			System.out.println("----------ERROR getting timeStampFormat " + lf.getBaseError(e.toString()));
			timeStampFormat = "yyyyMMdd_hhmmss";
		}
	}
	
	public void SetStormProperties(){
		LOGGER.addHandler(LogFile.fh);
		LOGGER.info("----------START of SetStormProperties");
		this.getPropertyResultFileName();
		this.getPropertyTestFileType();
		this.getPropertyObjectDBName();
		this.getPropertyExecutionType();
		this.getDefaultURL();
		LOGGER.info("----------END of SetStormProperties");
	}
	
	public void getPropertyResultFileName(){
		LOGGER.info("---------------START of getPropertyResultFileName");
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			resultFileName = p.getProperty("resultfilename");
			if (resultFileName.equals("")) {
				LOGGER.warning("---------------GetStormProperties.getPropertyResultFileName : resultFileName = \"\". Using the default resultFileName Result_File_");
				resultFileName = "Result_File_";
			}
			else
				LOGGER.info("---------------GetStormProperties.getPropertyResultFileName : resultFileName = " + resultFileName);
		} catch (Exception e) {
			LOGGER.severe("---------------GetStormProperties.getPropertyResultFileName : ERROR getting resultFileName with " + lf.getBaseError(e.toString()) + "Using the default resultFileName Result_File_");
			resultFileName = "Result_File_";
		}
		LOGGER.info("---------------END of GetStormProperties.getPropertyResultFileName");
	}
	
	public void getPropertyTestFileType(){
		LOGGER.info("---------------START of getPropertyTestFileType");
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			testFileType = p.getProperty("testfiletype");
			if (testFileType.equals("")) {
				LOGGER.warning("---------------GetStormProperties.getPropertyTestFileType : testFileType = \"\". Using the default testFileType .xlsx");
				testFileType = ".xlsx";
			}
			else
				LOGGER.info("---------------GetStormProperties.getPropertyTestFileType : testFileType = " + testFileType);
		} catch (Exception e) {
			LOGGER.severe("---------------GetStormProperties.getPropertyTestFileType : ERROR getting testFileType with " + lf.getBaseError(e.toString()) + ". Using the default testFileType .xlsx");
			testFileType = ".xlsx";
		}
		LOGGER.info("---------------END of GetStormProperties.getPropertyTestFileType");
	}
	
	public void getPropertyObjectDBName(){
		LOGGER.info("---------------START of getPropertyObjectDBName");
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			objectDBName = p.getProperty("objectdbname");
			if (objectDBName.equals("")) {
				LOGGER.warning("---------------GetStormProperties.getPropertyObjectDBName : objectDBName = \"\". All tests will not use an object DB.");
			}
			else {
				File f = new File(SetFilePaths.objectdbLocation + GetStormProperties.objectDBName);
				if(f.exists() && !f.isDirectory()) { 
					LOGGER.info("---------------GetStormProperties.getPropertyObjectDBName : objectDBName = " + objectDBName);
				}
				else
					LOGGER.warning("---------------GetStormProperties.getPropertyObjectDBName : " + objectDBName + " file not found on " + SetFilePaths.objectdbLocation + ". All tests will not use an object DB.");
			}
		} catch (Exception e) {
			LOGGER.severe("---------------GetStormProperties.getPropertyObjectDBName : ERROR getting objectDBName with " + lf.getBaseError(e.toString()));
		}
	}
	
	public void getPropertyExecutionType(){
		LOGGER.info("---------------START of getPropertyExeutionType");
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			executionType = p.getProperty("executiontype");
			if (executionType.equals("")) {
				LOGGER.warning("---------------GetStormProperties.getPropertyExeutionType : executionType = \"\". Tests will run on single execution");
				executionType = "single";
			}
			else {
				LOGGER.info("---------------GetStormProperties.getPropertyExeutionType : executionType = " + executionType);
			}
		} catch (Exception e) {
			LOGGER.severe("---------------GetStormProperties.getPropertyExeutionType : ERROR getting executionType with " + lf.getBaseError(e.toString()));
		}
	}
	
	public void getDefaultURL(){
		LOGGER.info("---------------START of getDefaultURL");
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(stormHome + "storm.properties"));
			defaultURL = p.getProperty("url");
			if (defaultURL.equals("")) {
				LOGGER.warning("---------------GetStormProperties.getDefaultURL : url = \"\". Tests will use url from datasets");
				defaultURL = "";
			}
			else {
				LOGGER.info("---------------GetStormProperties.getDefaultURL : defaultURL = " + defaultURL);
			}
		} catch (Exception e) {
			LOGGER.severe("---------------GetStormProperties.getDefaultURL : ERROR getting defaultURL with " + lf.getBaseError(e.toString()));
		}
	}
}
