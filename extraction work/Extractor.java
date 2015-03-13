package cs454.webCrawler.webCrawler;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.store.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Iterator;


/*code from 
 * http://www.java2s.com/Tutorial/Java/0180__File/Searchforfilesrecursively.htm
 * */
 
public class Extractor {
  @SuppressWarnings({ "rawtypes", "unchecked" })
public static void main(String[] args) throws Exception {
    File root = new File("E:\\books\\CS454(information Retrieval)\\data\\Crawler");
    JSONObject fileinfo= new JSONObject(); 
    String[] extensions = {"json"};
    boolean recursive = true;
    int i=0;
    Collection files = FileUtils.listFiles(root, extensions, recursive);
    JSONParser parser = new JSONParser();
    for (Iterator iterator = files.iterator(); iterator.hasNext();) {
      File file = (File) iterator.next();
      
      //BasicFileAttributes attr = Files.readAttributes(file.getAbsolutePath(), BasicFileAttributes.class);
      ////System.out.println("File = " + file.getAbsolutePath());
     // Path path= file.getAbsolutePath().toPath();
      
      BasicFileAttributes basicAttributes = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);
		 
		//System.out.println("date: "+basicAttributes.creationTime());
		/*
		fileinfo.put("path", file.getAbsolutePath());  
		fileinfo.put("date", basicAttributes.creationTime()); i++;
    */
		 try {
			 
	            //Object obj = parser.parse(new FileReader(file.getAbsolutePath() ));
			 Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
	            JSONObject jsonObject = (JSONObject) obj;
	 
	            String title = (String) jsonObject.get("title");
	            String Content= (String) jsonObject.get("type");
	            String url= (String) jsonObject.get("url");
	            String localpath= (String) jsonObject.get("localpath");
	            String last_pulled= (String) jsonObject.get("last pulled");
	            String keywords= (String) jsonObject.get("keywords");
	            String description= (String) jsonObject.get("description");
	            /*JSONArray localpath = (JSONArray) jsonObject.get("localpath");
	 */
	            System.out.println("title: " + title);
	            System.out.println("Content: " + Content);
	            System.out.println("url: "+url);
	            System.out.println("localpath: "+localpath);
	            System.out.println("last pulled: "+last_pulled);
	            System.out.println("description: "+description);
	            /*System.out.println("\nlocalpath :"+localpath );
	            /*
	            Iterator<String> iterator1 = companyList.iterator();
	            while (iterator1.hasNext()) {
	                System.out.println(iterator1.next());
	            }
	 */
	       
      
		 fileinfo.put("title", title);
		 fileinfo.put("content", Content); 
		 fileinfo.put("url", url); 
		 fileinfo.put("localpath", localpath); 
		 fileinfo.put("last_pulled", last_pulled); 
		 fileinfo.put("keywords", keywords); 
		 fileinfo.put("description", description);
		 /*
		 fileinfo.put("path", file.getAbsolutePath()); 
			fileinfo.put("date", basicAttributes.creationTime()); 
			*/
			i++;
		 
		FileWriter file1 = new FileWriter("metadata.json",true);
        try {
        	file1.write("\n");
            file1.write(fileinfo.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + fileinfo);
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } 
		  
        finally {
            file1.flush();
            file1.close();
        }   }
  finally { }
    
    }
  }
}
//}