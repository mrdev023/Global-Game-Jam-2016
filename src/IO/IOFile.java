package IO;

import java.io.*;
import java.util.*;

import Tools.*;

public class IOFile {

	public static String getStringByFile(String url){
		String fileData = "";
		try {
			BufferedInputStream ois = new BufferedInputStream(new FileInputStream(new File(url)));
			Scanner fEntree = new Scanner(ois);
			String line, tab[];
			
			while(fEntree.hasNext()){
				line = fEntree.nextLine();
				fileData += line + " ";
			}
			fEntree.close();
			ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileData;
	}
	
	public static void saveFileByString(String data,String url){
		try {
			BufferedOutputStream oos = new BufferedOutputStream(new FileOutputStream(new File(url)));
			oos.write(data.getBytes());
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
