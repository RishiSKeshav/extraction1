package cs454.extraction.extraction1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Extractor
{
	public static JSONArray jsonArrayToPrint = new JSONArray();

	public static void main(String[] args) {
		
		File temp = new File(args[1]);
		
		File file = new File(temp.getParent()+"\\Extracter.json");

		// extract("I:\\books\\CS454(information Retrieval)\\data\\Crawler\\Crawler.json");
		//String path = "I:\\books\\CS454(information Retrieval)\\data\\Crawler\\Crawler.json";

		 String path =args[1];

		readJson(path);

		// extract(path);

		 writeFile(file);
	}

	@SuppressWarnings("unchecked")
	private static void readJson(String path) {
		JSONParser parser = new JSONParser();
		Object object;
		
		

		try
		{
			object = parser.parse(new FileReader(path));
			JSONArray jsonArray = (JSONArray) object;

			System.out.println(jsonArray.size());
			
			
			for (Object o : jsonArray)
			{
				Map<String, Object> meta = new HashMap<String, Object>();
				
				JSONObject jsonObject = (JSONObject) o;
				String localpath = jsonObject.get("localpath").toString();

				Metadata metadataOfFile = readFile(localpath);
				
				meta.put("title", jsonObject.get("title"));
				meta.put("last pulled", jsonObject.get("last pulled"));
				meta.put("url", jsonObject.get("url"));
				meta.put("localpath", jsonObject.get("localpath"));
				meta.put("metadata", metadataOfFile);
				
				System.out.println("Currently extracting from "+localpath);
				System.out.println();
				//System.out.println(metadataOfFile);
				/*if(metadataOfFile.get("Content-Type").contains("png") || metadataOfFile.get("Content-Type").contains("jpeg") || metadataOfFile.get("Content-Type").contains("gif"))
				{
					meta.put("Content-Type", metadataOfFile.get("Content-Type"));
					meta.put("Tiff Image Length", metadataOfFile.get("tiff:ImageLength"));
					meta.put("Image Length", metadataOfFile.get("Image Length"));
					meta.put("Image Width", metadataOfFile.get("Image Width"));
					
					
					System.out.println("Tiff Image Length: "+metadataOfFile.get("tiff:ImageLength"));
					System.out.println("Image Length: "+metadataOfFile.get("Image Length"));
					System.out.println("Image Width: "+metadataOfFile.get("Image Width"));
					System.out.println("Tiff Image Length: "+metadataOfFile.get("tiff:ImageLength"));
					System.out.println("Tiff Image Length: "+metadataOfFile.get("tiff:ImageLength"));
					
				}
				
				if(metadataOfFile.get("Content-Type").contains("pdf") || metadataOfFile.get("Content-Type").contains("doc") 
						|| metadataOfFile.get("Content-Type").contains("xlsx")
						|| metadataOfFile.get("Content-Type").contains("ppt"))
				{
					
					meta.put("Content-Type", metadataOfFile.get("Content-Type"));
					meta.put("Author", metadataOfFile.get("Author"));
					meta.put("Producer", metadataOfFile.get("Producer"));
					meta.put("Creator", metadataOfFile.get("dc:creator"));
					meta.put("Creation Date", metadataOfFile.get("meta:creation-date"));
					meta.put("Last-Modified", metadataOfFile.get("Last-Modified"));
					meta.put("Title", metadataOfFile.get("dc:title"));
					
					System.out.println("Author: "+metadataOfFile.get("Author"));
					System.out.println("Producer: "+metadataOfFile.get("producer"));
					System.out.println("Creator: "+metadataOfFile.get("dc:creator"));
					System.out.println("Creation Date: "+metadataOfFile.get("meta:creation-date"));
					System.out.println("Last-Modified: "+metadataOfFile.get("Last-Modified"));
					System.out.println("Title: "+metadataOfFile.get("dc:title"));
				}*/
				
				//System.out.println(metadataOfFile);
				jsonArrayToPrint.add(meta);
				

			}
		}

		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static Metadata readFile(String localpath) {
		Map<String,Object> metadata = new HashMap<String, Object>();
		
		
		File f= new File(localpath);
		
		
		BasicFileAttributes attr;
		try
		{
			Parser parser = new AutoDetectParser();
			
			long start = System.currentTimeMillis();
            BodyContentHandler handler = new BodyContentHandler(10000000);
            Metadata metadata1 = new Metadata();
            InputStream content = TikaInputStream.get(new File(f.getAbsolutePath()));
            try
			{
				parser.parse(content, handler, metadata1, new ParseContext());
			}
			catch (TikaException e)
			{
				
				e.printStackTrace();
			}
            catch (SAXException  e) {
				e.printStackTrace();
			}
            
            
			
			attr = Files.readAttributes(Paths.get(f.getAbsolutePath()), BasicFileAttributes.class);			
			
			//metadata.put("Author", author);
			//metadata.put("fileName", f.getName());
			//metadata.put("Path",f.getAbsolutePath());
			//metadata.put("Created date and time",attr.creationTime());
			
			
			return metadata1;
		}
			catch (IOException e)
			{
				System.out.println("Failed to read file ");				
				return null;
			}
		
		
    }

	@SuppressWarnings("unchecked")
	public static void extract(String path) {

		JSONParser parser = new JSONParser();
		Object object;

		try
		{
			object = parser.parse(new FileReader(path));
			JSONArray jsonArray = (JSONArray) object;

			for (Object o : jsonArray)
			{
				Map<String, Object> meta = new HashMap<String, Object>();

				JSONObject jsonObject = (JSONObject) o;
				/*
				 * System.out.println(
				 * "------------------------------------------------------------------"
				 * ); System.out.println(jsonObject.get("title"));
				 * System.out.println(jsonObject.get("last pulled"));
				 */
				// System.out.println(jsonObject.get("Content-Type"));

				JSONObject met = (JSONObject) jsonObject.get("met");
				JSONObject metadata = (JSONObject) met.get("metadata");

				// System.out.println(metadata.get("keywords"));

				/* System.out.println(metadata.get("keywords")); */

				meta.put("title", jsonObject.get("title"));
				meta.put("last pulled", jsonObject.get("last pulled"));
				meta.put("url", jsonObject.get("url"));
				meta.put("localpath", jsonObject.get("localpath"));

				String type = metadata.get("Content-Type").toString();

				if (type.equals("[\"image\\/jpeg\"]")
						|| type.equals("[\"image\\/png\"]")
						|| type.equals("[\"image\\/gif\"]"))
				{
					meta.put("Content-Type", metadata.get("Content-Type"));
					meta.put("Image Height", metadata.get("Image Height"));
					meta.put("Image Width", metadata.get("Image Width"));

					// System.out.println(metadata.get("Image Height"));
				}
				else if (type.equals("[\"application\\/pdf\"]"))
				{
					meta.put("Content-Type", metadata.get("Content-Type"));
					meta.put("date", metadata.get("date"));
					meta.put("Author", metadata.get("Author"));
					meta.put("producer", metadata.get("producer"));
					meta.put("creator", metadata.get("creator"));
				}
				else
				{
					meta.put("Content-Type", metadata.get("Content-Type"));
					meta.put("keywords", metadata.get("keywords"));
				}

				jsonArrayToPrint.add(meta);
			}

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeFile(File file1) {
		try
		{
			String path = file1.getAbsolutePath();

			Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		
			String pretJson = prettyGson.toJson(jsonArrayToPrint);

			FileWriter file = new FileWriter(path, true);
			file.write(pretJson.toString());
			file.write("\n\n");
			file.flush();
			file.close();

			System.out.println("Done");
		}
		catch (IOException e)
		{

		}

	}
}
