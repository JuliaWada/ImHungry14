package scraper;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Servlet implementation class CollageData
 */
@WebServlet("/collagedata")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Random random = new Random();
				int num = 0;
//				System.out.println("Inside of collage data");
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				String query = request.getParameter("query").trim();
//				System.out.println("Inside of Collage Data: " + query);
				CollageScraper scraper = new CollageScraper();
				ArrayList<String> collageResults = scraper.scrapeCollage(query);
				for(int i =0; i<collageResults.size(); i++) {
//					System.out.println(StringEscapeUtils.unescapeJava(collageResults.get(i)));
					String unescaped = StringEscapeUtils.unescapeJava(collageResults.get(i));
					
					num = random.nextInt(91) - 45;
					out.println("<img class=\"collageImg\" src=" + unescaped +
							" style=\"transform:rotate(" + num + "deg); max-height: 200px\">" );
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
