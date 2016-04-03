/**
 * 
 */
package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.ilstu.chtmltranscriptor.models.MultiPartFileUploadBean;

/**
 * @author Basheer
 *
 */
@Controller
public class UploadController extends BaseController
{
	/**
	 * Upload CHTML files and save in CHTML directory
	 * 
	 * @param name
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam("file") MultipartFile file)
	{

		ModelAndView mav = new ModelAndView("home");
		bindContentToView(mav);
		if (!file.isEmpty())
		{
			try
			{
				byte[] bytes = file.getBytes();
				File dir = new File(chtmlDirectory);
				if (!dir.exists())
				{
					dir.mkdirs();
				}
				File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				mav.addObject("message", "You successfully uploaded file=" + file.getName());
				return mav;
			} catch (Exception e)
			{
				mav.addObject("message", "You failed to upload " + file.getName() + " => " + e.getMessage());
				return mav;
			}
		} else
		{
			mav.addObject("message", "You failed to upload " + file.getName() + " because the file was empty.");
			return mav;
		}
	}

	/**
	 * Upload CHTML files and save in CHTML directory
	 * 
	 * @param name
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(MultiPartFileUploadBean listOfFiles, BindingResult bindingResult)
	{
		ModelAndView mav = new ModelAndView("home");
		bindContentToView(mav);
		MultipartFile[] files = listOfFiles.getFiles();
		File dir = new File(chtmlDirectory);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		if (files != null)
		{
			for (MultipartFile file : files)
			{
				if (!file.isEmpty())
				{
					try
					{
						byte[] bytes = file.getBytes();

						File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();
					} catch (Exception e)
					{
						return mav;
					}
				} else
				{
					return mav;
				}
			}
		}
		bindContentToView(mav);
		return mav;
	}
}
