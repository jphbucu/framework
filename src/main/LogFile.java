package main;

import java.util.logging.FileHandler;
//import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogFile {
	static FileHandler fh;
	
	public LogFile(){
		System.out.println("-----START of LogFile setup");
		try {
			fh = new FileHandler(GetStormProperties.stormHome + "logs\\" + TimeStamp.timestamp + ".log");
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
		} catch (Exception e) {
			System.out.println("----------ERROR generating .log file");
			System.exit(0);
		}
        System.out.println("-----END of LogFile setup");
	}
	
	public String getBaseError(String e){
		String a[] = e.split("\n");
		return a[0];
	}
}
