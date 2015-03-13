package cs545.SEO;

import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) {
        Crawler p = new Crawler();
        try {
            p.readMyFile("test/pagecounts-20140701-060000.txt");
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }
}
