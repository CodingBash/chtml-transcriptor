package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home()
	{
		ModelAndView mav = new ModelAndView("home");
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

}
