package resources.stuff;

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
 * Servlet implementation class collageData
 */
@WebServlet("/collageData")
public class collageData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collageData() {
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
		System.out.println("Inside of collage data");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String query = request.getParameter("query").trim();
		System.out.println("Inside of Collage Data: " + query);
		CollageScraper scraper = new CollageScraper();
		ArrayList<String> collageResults = scraper.scrape(query);
		for(int i =0; i<collageResults.size(); i++) {
			System.out.println(StringEscapeUtils.unescapeJava(collageResults.get(i)));
			String fuck = StringEscapeUtils.unescapeJava(collageResults.get(i));
			
			num = random.nextInt(91) - 45;
			out.println("<img class=\"collageImg\" src=" + fuck +
					" style=\"transform:rotate(" + num + "deg); max-height: 200px\">" );
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
