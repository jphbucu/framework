package ExclusionTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xmlToExcel {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	}

	public List<String[]> createXMLArray(String reportpath, String sectestpath, String sectestfilename, String xml) throws IOException{
		List<String[]> list = new ArrayList<String[]>();
		VerifyExclusion ve = new VerifyExclusion();
		//String xml = mFindFiles.getFileName(reportpath, "xml");
		int exclusionRowCount = ve.getRowCount(sectestpath, sectestfilename, "Result");
		try {
			File fXmlFile = new File(reportpath + xml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			String alert = "";
			String riskdesc = "";
			String status = "";
			String uri = "";
			String param = "";
			String attack = "";
			String evidence = "";
			String desc = "";
			String solution = "";
			int num = 1;

			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("alertitem");
					
			//System.out.println("----------------------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
						
				//System.out.println("\nAlert Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					alert = eElement.getElementsByTagName("alert").item(0).getTextContent();
					//System.out.println("alert : " + alert);
					
					riskdesc = eElement.getElementsByTagName("riskdesc").item(0).getTextContent();
					//System.out.println("riskdesc : " + riskdesc);
					
					desc = eElement.getElementsByTagName("desc").item(0).getTextContent();
					//System.out.println("desc : " + desc);
					
					solution = eElement.getElementsByTagName("solution").item(0).getTextContent();
					//System.out.println("solution : " + solution);
					
					for (int i = 0; i < eElement.getElementsByTagName("instance").getLength(); i++) {
						//System.out.println("\n");
						
						uri = eElement.getElementsByTagName("uri").item(i).getTextContent();
						//System.out.println("uri : " + uri);
						
						try {
							param = eElement.getElementsByTagName("param").item(i).getTextContent();
							//System.out.println("param : " + param);
						} catch (Exception e) {
							param = "none";
						}
						
						try {
							attack = eElement.getElementsByTagName("attack").item(i).getTextContent();
							//System.out.println("attack : " + attack);
						} catch (Exception e) {
							// TODO: handle exception
							attack = "none";
						}
						
						try {
							evidence = eElement.getElementsByTagName("evidence").item(i).getTextContent();
							//System.out.println("attack : " + attack);
						} catch (Exception e) {
							// TODO: handle exception
							evidence = "none";
						}
						
						String result[] = {alert, riskdesc, uri, param, attack, evidence};
						//System.out.println("Res " + num + " : " + result[0] + "|" + result[1] + "|" + result[2] + "|" + result[3] + "|" + result[4]);
						status = ve.VerifyExclusionList(exclusionRowCount, "Result", sectestpath + sectestfilename, result);
						
						String data[] = {num + "", status, alert, riskdesc, uri, param, attack, evidence, desc, solution};
						list.add(data);
						
						num++;
					}
					//System.out.println("****************************");
				}
				
			}
//			for (String[] strArr : list) {
//				for (String str : strArr) {
//					System.out.println("item: " + str);
//				}
//	            System.out.println(Arrays.toString(strArr));
//	        }
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
