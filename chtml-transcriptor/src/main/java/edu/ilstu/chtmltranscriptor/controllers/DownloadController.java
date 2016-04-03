package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloadController extends BaseController
{

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	 	public String download(HttpServletRequest request, HttpServletResponse response)
	 	{
	 		ModelAndView mav = new ModelAndView("home");
	 		File file = new File(htmlDirectory + File.separator + "page.html");
	 		// File[] listOfFiles = folder.listFiles();
	 
	 		response.setContentType("text/html");
	 		response.addHeader("Content-Disposition", "attachment; filename=page.html");
	 
	 		byte[] buffer = new byte[1024];
	 		FileInputStream fis = null;
	 		BufferedInputStream bis = null;
	 		try
	 		{
	 			fis = new FileInputStream(file);
	 			bis = new BufferedInputStream(fis);
	 			OutputStream os = response.getOutputStream();
	 			int i = bis.read(buffer);
	 			while (i != -1)
	 			{
	 				os.write(buffer, 0, i);
	 				i = bis.read(buffer);
	 			}
	 		} catch (IOException e)
	 		{
	 			e.printStackTrace();
	 		} finally
	 		{
	 			if (bis != null)
	 			{
	 				try
	 				{
	 					bis.close();
	 				} catch (IOException e)
	 				{
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 			}
	 			if (fis != null)
	 			{
	 				try
	 				{
	 					fis.close();
	 				} catch (IOException e)
	 				{
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 			}
	 		}
	 
	 		return null;
	 	}
}
