package cs545.SEO;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class Crawler {
	
	//Source: http://stackoverflow.com/questions/24548956/how-to-parse-large-text-file-with-apache-tika-1-5
    public void readMyFile(String fname) throws IOException, SAXException,
            TikaException {
        System.out.println("Working...");

        File f = new File(fname);
        // InputStream stream = TikaInputStream.get(new File(fname));
        InputStream stream = new BufferedInputStream(new FileInputStream(fname));

        Metadata meta = new Metadata();
        BodyContentHandler content = new BodyContentHandler(Integer.MAX_VALUE);
        AutoDetectParser parser = new AutoDetectParser();

        String mime = new Tika().detect(f);
        meta.set(Metadata.CONTENT_TYPE, mime);

        System.out.println("trying to parse...");
        try {
            parser.parse(stream, content, meta, new ParseContext());
        } finally {
            stream.close();
        }
    }

    
}