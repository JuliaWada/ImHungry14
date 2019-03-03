package scraping;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CollageScraper {

    //We need a real browser user agent or Google will block our request with a 403 - Forbidden
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

/**
 * this function uses a default google url with the search term appended to it. Jsoup then finds the image urls within html
 * and parses it. The html has the links configured as json so parsing was used to get only the URL string. 
 * escapeJava is used to account for utf-8 encoding on the links    
 * @param query a String that defines the search terms from the users input passed from the front end
 * @return an ArrayList<String> that has all 10 links 
 */
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
        	Elements result = doc.select("div.rg_meta");
            final String outer = result.outerHtml();
            String split[] = outer.split("\"ou\":");
            String splitAgain[];
            for(int j=0; j<11; j++) {
            	splitAgain = split[j].split(",\"");
            	if(j != 0) {
            		splitAgain[j] = StringEscapeUtils.escapeJava(splitAgain[j]);
            		toReturn.add(splitAgain[0]);
            	}            	
            }
            
        System.out.println("toReturn size: " + toReturn.size());
        return toReturn;
    }
}