import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import scraping.CollageData;

public class ServletTestCase extends Mockito {
	
	@Test
	public void testCollageData() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);       
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    
	    when(request.getParameter("extra")).thenReturn("");
	    when(request.getParameter("query")).thenReturn("cake");
	    
	    StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new CollageData().service(request, response);
        
        verify(request, atLeast(1)).getParameter("extra"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(""));
        
        verify(request, atLeast(1)).getParameter("query");
        writer.flush();
        assertTrue(stringWriter.toString().contains("cake"));
        
	}



}
