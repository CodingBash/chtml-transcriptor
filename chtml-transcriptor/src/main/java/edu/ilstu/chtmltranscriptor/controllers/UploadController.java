/**
 * 
 */
package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Basheer
 *
 */
@Controller
@RequestMapping(value = "/uploadFile")
public class UploadController extends BaseController
{
	/**
	 * Upload CHTML files and save in CHTML directory
	 * 
	 * @param name
	 * @param file
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file)
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
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				mav.addObject("message", "You successfully uploaded file=" + name);
				return mav;
			} catch (Exception e)
			{
				mav.addObject("message", "You failed to upload " + name + " => " + e.getMessage());
				return mav;
			}
		} else
		{
			mav.addObject("message", "You failed to upload " + name + " because the file was empty.");
			return mav;
		}
	}
}
