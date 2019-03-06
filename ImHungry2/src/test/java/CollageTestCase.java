import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import scraping.CollageScraper;

public class CollageTestCase {

	@Test
	public void testGetTenImages() throws IOException {
		CollageScraper scraper = new CollageScraper();
		scraper.scrapeCollage("ramen");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("\\\"https://www.halfbakedharvest.com/wp-content/uploads/2018/09/20-Minute-Thai-Peanut-Chicken-Ramen-1.jpg\\\"");
		expected.add("\\\"https://www.modernhoney.com/wp-content/uploads/2018/07/Homemade-Chicken-Ramen-1.jpg\\\"");
		expected.add("\\\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/151010_Sapporo_ramen_at_Susukino_Sapporo_Hokkaido_Japan01s.jpg/270px-151010_Sapporo_ramen_at_Susukino_Sapporo_Hokkaido_Japan01s.jpg\\\"");
		expected.add("\\\"https://sharedappetite.com/wp-content/uploads/2018/02/spicy-lamb-coconut-curry-ramen-5-copy.jpg\\\"");
		expected.add("\\\"http://seonkyounglongest.com/wp-content/uploads/2018/04/shoyu-ramen-1.jpg\\\"");
		expected.add("\\\"https://www.closetcooking.com/wp-content/uploads/2018/03/TonkotsuRamen8000837-min.jpg\\\"");
		expected.add("\\\"http://glebekitchen.com/wp-content/uploads/2017/09/curryramenclose.jpg\\\"");
		expected.add("\\\"http://seonkyounglongest.com/wp-content/uploads/2018/04/chicken-ramen-02-copy-625x1000.jpg?x61413\\\"");
		expected.add("\\\"https://images.britcdn.com/wp-content/uploads/2018/08/Miso-Lit-Ramen.jpg?w\\\\u003d1000\\\\u0026auto\\\\u003dformat\\\"");
		expected.add("\\\"https://peasandcrayons.com/wp-content/uploads/2018/01/healthy-spicy-sriracha-shrimp-ramen-noodle-soup-bowls-recipe-7075.jpg\\\"");
		ArrayList<String> actual = new ArrayList<String>();
		actual = scraper.scrapeCollage("ramen");
		//assertArrayEquals(expected.toArray(), actual.toArray());
		assertEquals(expected.size(), actual.size());
	}

}
