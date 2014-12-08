package com.example.nudelvisualization.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@SuppressWarnings("serial")
public class ConfigServlet extends HttpServlet {
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
		String areas = req.getParameter( "areas" );
        String items = req.getParameter( "items" );
        String years = req.getParameter( "years" );
        String dataSeries = req.getParameter( "dataseries" );

        int BUFFER = 1024 * 100;
        
        String filename = "savedConfig.txt";
        
        String contents = areas + "\n" + items + "\n" + years + "\n" + dataSeries;
        
        resp.setContentType( "application/octet-stream" );
        resp.setHeader( "Content-Disposition:", "attachment;filename=" + "\"" + filename + "\"" );
        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentLength( contents.length() );
        resp.setBufferSize( BUFFER );
        
        outputStream.write(contents.getBytes());
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		String result = items.get(0).getString();
		System.out.println(result);
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(result.getBytes());
	}
}
