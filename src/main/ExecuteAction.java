package main;

import java.util.logging.Logger;

public class ExecuteAction {
	private static final Logger LOGGER = Logger.getLogger(ExecuteAction.class.getName());

	public ExecuteAction(String action, String object, String input){
		LOGGER.addHandler(LogFile.fh);
		WebActions wa = new WebActions();
		wa.setMessage("");
		GetProperties gp = new GetProperties();
		KudosScripts ks = new KudosScripts();
		KUDOSValidations kv = new KUDOSValidations();
		switch (action.toUpperCase()){
			case "LAUNCH":
				wa.Launch(object, input);
				break;
			
			case "NEWLAUNCH":
				wa.NewLaunch(object, input);
				break;
				
			case "CLICK":
				wa.Click(object, input);
				break;
				
			case "CLICKGMAIL":
				wa.ClickGmail(object, input);
				break;
				
			case "TYPE":
				wa.Type(object, input);
				break;
				
			case "SELECT":
				wa.Select(object, input);
				break;
				
			case "TICK":
				wa.Tick(object, input);
				break;
				
			case "HOVER":
				wa.Hover(object, input);
				break;
				
			case "SCROLL":
				wa.Scroll(object, input);
				break;
				
			case "UPLOAD":
				wa.Upload(object, input);
				break;
				
			case "SWITCHTAB":
				wa.SwitchTab(object, input);
				break;
				
			case "SWITCHTOIFRAME":
				wa.SwitchToIFrame(input);
				break;
				
			case "SWITCHTOPARENTFRAME":
				wa.SwitchToParentFrame(input);
				break;
				
			case "CLICKPOPUP":
				wa.ClickPopUp(object, input);
				break;
				
			case "SLEEP":
				wa.Sleep(input);
				break;
				
			case "EXWAIT":
				wa.explicitWait(object, input);
				break;
				
			case "INVWAIT":
				wa.waitForInvisible(object, input);
				break;
				
			case "KEYS":
				wa.KeyStroke(object, input);
				break;
				
			case "OBJECTEXISTS":
				wa.ObjectExists(object, input);
				break;
			
			case "CLOSE":
				wa.Close();
				break;
				
			case "GETINNERHTML":
				gp.getInnerHTMLProperty(object, input);
				break;
				
			case "GETATTRIBUTE":
				gp.getAttributeProperty(object, input);
				break;
				
			case "GETTEXT":
				gp.getTextProperty(object, input);
				break;
				
			//these are kudos centered actions
			case "GETORDERID":
				ks.getOrderID(object, input);
				break;
				
			case "GETTOPUPID":
				ks.getTopUpID(object, input);
				break;
				
			case "GETTOPUPSTATUS":
				ks.getTopUpStatus(object, input);
				break;
				
			case "VALIDATE":
				kv.validate(object, input);
				break;
				
			default:
				LOGGER.severe("----------ExecuteAction ERROR : " + action + " does not match any action");
				break;
		}
	}
}
