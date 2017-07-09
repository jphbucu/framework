package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class FindFiles {
	private static final Logger LOGGER = Logger.getLogger(FindFiles.class.getName());
	LogFile lf;

	public static void main(String[] args) throws IOException {
		FindFiles ff = new FindFiles();
//		List<String> list = new ArrayList<>();
//		ff.getFileName("C:\\Storm\\tests\\", list);
//		for (String string : list) {
//			System.out.println(string);
//		}
		System.out.println(ff.getFilePath("C:\\Storm\\tests\\", "TC0002 Checkout.xlsx"));
	}             
	
	public void getFileName(String filelocation, List<String> str) throws IOException{
		LOGGER.addHandler(LogFile.fh);
		LOGGER.info("----------START of FindFiles.getFileName");
		try {
			File f = new File(filelocation);
		    File[] files = f.listFiles();	
		    for(File file : files){
				if (file.isDirectory()) {
					getFileName(file.getAbsolutePath(), str);
		        } else {
		        	if (file.getName().contains(GetStormProperties.testFileType)) {
		        		str.add(file.getName());
					}
		        }
			}
		    if (str.isEmpty()) {
				LOGGER.severe("----------FindFiles.getFileName : No tests found. Terminating Execution.");
				System.exit(0);
			}
		    LOGGER.info("----------END of FindFiles.getFileName");
		} catch (Exception e) {
			LOGGER.severe("----------FindFiles.getFileName : ERROR " + lf.getBaseError(e.toString()));
		}
		LOGGER.info("----------END of FindFiles.getFileName");
	}
	
	public String getFilePath(String filePath, String fileName){
		File root = new File(filePath);
        try {
            boolean recursive = true;
            Collection files = FileUtils.listFiles(root, null, recursive);
            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file = (File) iterator.next();
                if (file.getName().equals(fileName))
                    return file.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
	}
	
//	public List<String> getFileName(String filelocation, String type) throws IOException{
//		//LOGGER.addHandler(LogFile.fh);
//		//LOGGER.info("----------START of FindFiles.getFileName");
//		List<String> fileNames = new ArrayList<String>();
//		try {
//			File f = new File(filelocation);
////		    FilenameFilter textFilter = new FilenameFilter() {
////		        public boolean accept(File dir, String name) {
////		            return name.contains(type);
////		        }
////		    };
//		    File[] files = f.listFiles();//f.listFiles(textFilter);	
//		    for(File file : files){
//				if (file.isDirectory()) {
//					getFileName(file.getAbsolutePath(), type);
//		        } else {
//		        	if (file.getName().contains(type)) {
//		        		 fileNames.add(file.getName());
//					}
//		        }
//			}
//		    if (fileNames.isEmpty()) {
//				//LOGGER.severe("----------FindFiles.getFileName : No tests found. Terminating Execution.");
//				System.exit(0);
//			}
//		    //LOGGER.info("----------END of FindFiles.getFileName");
//		    return fileNames;
//		} catch (Exception e) {
//			//LOGGER.severe("----------FindFiles.getFileName : ERROR " + lf.getBaseError(e.toString()));
//		}
//		//LOGGER.info("----------END of FindFiles.getFileName");
//		return fileNames;
//	}
}
