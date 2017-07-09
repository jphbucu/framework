package main;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class UIDriver {
	private static final Logger LOGGER = Logger.getLogger(WebActions.class.getName());
	static WebDriver uidriver;
	LogFile lf;
	
	public UIDriver(String browser){
		LOGGER.addHandler(LogFile.fh);
		switch (browser.toUpperCase()) {
		case "CHROME":
			this.getChromeDriver();
			break;
			
		case "FIREFOX":
			this.getFirefoxDriver();
			break;
			
		case "IE":
			this.getIEDriver();
			break;
			
		case "SAFARI":
			this.getSafariDriver();
			break;
			
		default:
			this.getChromeDriver();
			break;
		}	
	}
	
	public void getChromeDriver(){
		//TODO check if driver exist, terminate if not
		System.setProperty("webdriver.chrome.driver", SetFilePaths.driverLocation  + "chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		Map<String, Object> prefs = new LinkedHashMap<>();
		prefs.put("credentials_enable_service", Boolean.valueOf(false));
		prefs.put("profile.password_manager_enabled", Boolean.valueOf(false));
		chromeOptions.setExperimentalOption("prefs", prefs);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		uidriver = new ChromeDriver(capabilities);
	}
	
	public void getFirefoxDriver(){
		//TODO check if driver exist, terminate if not
		System.setProperty("webdriver.gecko.driver", SetFilePaths.driverLocation + "geckodriver.exe");
		uidriver = new FirefoxDriver();
	}
	
	public void getIEDriver(){
		//TODO check if driver exist, terminate if not
		System.setProperty("webdriver.ie.driver", SetFilePaths.driverLocation + "IEDriverServer.exe");
		uidriver = new InternetExplorerDriver();
	}
	
	public void getSafariDriver(){
		//TODO check if driver exist, terminate if not
		uidriver = new SafariDriver();
	}
}
