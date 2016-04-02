package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ilstu.chtmltranscriptor.constants.Application;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home()
	{
		ModelAndView mav = new ModelAndView("home");
		File rootFolder = new File(Application.ROOT);
		File[] listOfFile = rootFolder.listFiles();
		if (listOfFile != null)
		{
			Object[] files = Arrays.stream(rootFolder.listFiles()).toArray();

			if (files != null)
			{
				mav.addObject("files", files);

			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes)
	{
		if (name.contains("/"))
		{
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:upload";
		}
		if (name.contains("/"))
		{
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:upload";
		}

		if (!file.isEmpty())
		{
			try
			{
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(Application.ROOT + "/" + name)));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + name + "!");
			} catch (Exception e)
			{
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		} else
		{
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

		return "redirect:upload";
	}

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file)
	{

		if (!file.isEmpty())
		{
			try
			{
				ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
				String myString = IOUtils.toString(stream, "UTF-8");
				convertToHtml(myString);
				///
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e)
			{
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else
		{
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	public File convertToHtml(String chtml)
	{
		int index = 0;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		while (index != -1)
		{
			index = chtml.indexOf("<include");
			if (index != -1)
			{
				indexList.add(chtml.indexOf("<include"));
			}
		}

		return new File(chtml);
	}
}
