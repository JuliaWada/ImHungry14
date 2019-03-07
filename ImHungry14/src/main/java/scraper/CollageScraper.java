package scraper;

import java.io.IOException;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.text.StringEscapeUtils;


public class CollageScraper {

    //We need a real browser user agent or Google will block our request with a 403 - Forbidden
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

   
public ArrayList<String> scrapeCollage (String query) {
	   ArrayList<String> toReturn = new ArrayList<String>();
        //Fetch the page
        Document doc = null;
		try {
			doc = Jsoup.connect("https://google.com/search?tbm=isch&q=" + query).userAgent(USER_AGENT).get();
		} catch (IOException ioe) {
			System.out.println("IOException in scrape:  " + ioe.getMessage());
			ioe.printStackTrace();
		}
        System.out.println("Getting the collage results");
        //Traverse the results
        	Elements result = doc.select("div.rg_meta");
            final String outer = result.outerHtml();
//            System.out.println("########################################");
//            System.out.println("Outer: " + outer);
            String split[] = outer.split("\"ou\":");
            String splitAgain[];
            for(int j=0; j<11; j++) {
//            	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44");
//            	System.out.println("parse: " + split[j]);
            	splitAgain = split[j].split(",\"");
//            	for(int k=0; k<splitAgain.length; k++) {
//            		System.out.println("JUST LINK*****: " + splitAgain[k]);
//            	}
            	if(j != 0) {
            		splitAgain[j] = StringEscapeUtils.escapeJava(splitAgain[j]);
            		toReturn.add(splitAgain[0]);
            	}            	
            }
            
        System.out.println("toReturn size: " + toReturn.size());
//        for(int i=0; i<10; i++) {
//        	System.out.println("Ending links: " + toReturn.get(i));
//        }
        	return toReturn;
    }
}