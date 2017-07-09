package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeStamp {
	static String timestamp;
	
	public TimeStamp(String format){
		System.out.println("-----START of TimeStamp");
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		timestamp = dateFormat.format(cal.getTime()).toString();
		System.out.println("-----timestamp = " + timestamp);
		System.out.println("-----END of TimeStamp");
	}
	
	public String getTimeStamp(){
		return timestamp;
	}
}
