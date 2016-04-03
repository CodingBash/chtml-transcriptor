package edu.ilstu.chtmltranscriptor.controllers;

import java.io.File;
import java.util.Arrays;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController
{
	protected static final String chtmlDirectory = System.getProperty("catalina.home") + File.separator + "tmpFiles"
			+ File.separator + "chtml";
	protected static final String htmlDirectory = System.getProperty("catalina.home") + File.separator + "tmpFiles"
			+ File.separator + "html";

	protected void bindContentToView(ModelAndView mav)
	{
		getChtmlFileList(mav);
	}

	private void getChtmlFileList(ModelAndView mav)
	{
		File rootFolder = new File(chtmlDirectory);
		File[] listOfFile = rootFolder.listFiles();
		if (listOfFile != null)
		{
			Object[] files = Arrays.stream(rootFolder.listFiles()).toArray();

			if (files != null)
			{
				mav.addObject("fileList", files);

			}
		}
	}
}
