package main;

import java.util.logging.Logger;

public class SetFilePaths {
	static String driverLocation;
	static String testLocation;
	static String resultLocation;
	static String objectdbLocation;
	private String path;
	
	LogFile lf;
	
	private static final Logger LOGGER = Logger.getLogger(SetFilePaths.class.getName());
	
	public SetFilePaths(String loc){
		LOGGER.addHandler(LogFile.fh);
		LOGGER.info("----------START of SetFilePaths");
		this.path = loc;
		this.setDriverLocation();
		this.setObjectDBLocation();
		this.setResultLocation();
		this.setTestLocation();
		LOGGER.info("----------END of SetFilePaths");
	}
	
	public void setDriverLocation(){
		SetFilePaths.driverLocation = path.substring(path.length()-1).equals("\\") ? path + "drivers\\" : path + "\\drivers\\";
	}
	
	public void setTestLocation(){
		SetFilePaths.testLocation = path.substring(path.length()-1).equals("\\") ? path + "tests\\" : path + "\\tests\\";
	}
	
	public void setResultLocation(){
		SetFilePaths.resultLocation = path.substring(path.length()-1).equals("\\") ? path + "results\\" : path + "\\results\\";
	}
	
	public void setObjectDBLocation(){
		SetFilePaths.objectdbLocation = path.substring(path.length()-1).equals("\\") ? path + "objectdb\\" : path + "\\objectdb\\";
	}
}
