package scraping;



import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Servlet implementation class CollageData
 */
@WebServlet("/collageData")
public class CollageData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollageData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Random random = new Random();
		int num = 0;
//		System.out.println("Inside of collage data");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String extra = request.getParameter("extra");
		if(extra == null || extra.equals("")) {
			extra = "poo";
		}
		String query = request.getParameter("query").trim();
		String numResults = request.getParameter("numResults");
//		System.out.println("Inside of Collage Data: " + query);
		if(extra.equals("settingVariables")) {
			System.out.println("Setting session");
			HttpSession session2 = request.getSession();
			session2.setAttribute("query", query);
			session2.setAttribute("numResults", numResults);
		} else {
			CollageScraper scraper = new CollageScraper();
			ArrayList<String> collageResults = scraper.scrapeCollage(query);
			for(int i =0; i<collageResults.size(); i++) {
				System.out.println("Unescaped: " + collageResults.get(i));
				System.out.println("Escaped: " + StringEscapeUtils.unescapeJava(collageResults.get(i)));
				String unescaped = StringEscapeUtils.unescapeJava(collageResults.get(i));
				
				num = random.nextInt(91) - 45;
				out.println("<img class=\"collageImg\" src=" + StringEscapeUtils.unescapeJava(collageResults.get(i)) +
						" style=\"transform:rotate(" + num + "deg); max-height: 150px\">" );
			}
		}
	
	}
}
