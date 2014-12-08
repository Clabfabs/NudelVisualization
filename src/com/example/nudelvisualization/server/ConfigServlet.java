package com.example.nudelvisualization.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConfigServlet extends HttpServlet {
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
		
        String areas = req.getParameter( "areas" );
        String items = req.getParameter( "items" );
        String years = req.getParameter( "years" );
        String dataSeries = req.getParameter( "dataseries" );


        int BUFFER = 1024 * 100;
        
        String filename = "savedConfig.nudelvis";
        
        String contents = areas + "\n" + items + "\n" + years + "\n" + dataSeries;
        
        resp.setContentType( "application/octet-stream" );
        resp.setHeader( "Content-Disposition:", "attachment;filename=" + "\"" + filename + "\"" );
        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentLength( contents.length() );
        resp.setBufferSize( BUFFER );
        
        outputStream.write(contents.getBytes());
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		
		ServletOutputStream outputStream = resp.getOutputStream();
		
		
		outputStream.write("test".getBytes());
		
		
		
	}
}
