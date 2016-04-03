/**
 * 
 */
package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Basheer
 *
 */
@Controller
@RequestMapping(value = "/compile")
public class CompileController extends BaseController
{

	/**
	 * Convert the CHTML directory into HTML
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ModelAndView compileCode() throws Exception
	{
		ModelAndView mav = new ModelAndView("home");
		bindContentToView(mav);

		File chtmlDir = new File(chtmlDirectory);
		File folder = new File(chtmlDir.getAbsolutePath());
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++)
		{
			addToHtmlDirectory(convertToHtml(listOfFiles[i]));
		}

		return mav;
	}

	/**
	 * Convert CHTML to HTML
	 * 
	 * @param chtml
	 * @return
	 * @throws Exception
	 */
	public File convertToHtml(File chtml) throws Exception
	{

		String content = FileUtils.readFileToString(chtml);
		Pattern pattern = Pattern.compile("<include\\sfile=\"([a-zA-Z0-9]*\\.chtml)\"\\s*\\/>");
		Matcher matcher = pattern.matcher(content);
		String results = content;
		while (matcher.find())
		{
			String filename = matcher.group(1);
			String replacement = getReplacementString(filename);

			Pattern pattern2 = Pattern.compile("<include\\sfile=\"" + filename + "\"\\s*\\/>");
			Matcher matcher2 = pattern2.matcher(results);
			results = matcher2.replaceAll(replacement);
		}
		File resultFile = new File(chtml.getName().substring(0, chtml.getName().indexOf('.')) + ".html");
		FileUtils.writeStringToFile(resultFile, results);
		return resultFile;
	}

	/**
	 * Get the string to replace the \<include\>
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private String getReplacementString(String fileName) throws IOException
	{
		if (fileName != null)
		{

			if (!fileName.equalsIgnoreCase("null"))
			{
				File dir = new File(chtmlDirectory);
				if (!dir.exists())
					dir.mkdirs(); // Create the file on server

				// Get the file
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				String content = FileUtils.readFileToString(serverFile);
				return content;
			}
		}
		return "";
	}

	/**
	 * Add file to the Html directory
	 * 
	 * @param htmlFile
	 * @throws IOException
	 */
	private void addToHtmlDirectory(File htmlFile) throws IOException
	{
		if (htmlFile != null)
		{
			byte[] bytes = FileUtils.readFileToByteArray(htmlFile);
			File dir = new File(htmlDirectory);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			System.out.println(htmlFile.getName());
			File serverFile = new File(dir.getAbsolutePath() + File.separator + htmlFile.getName());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
		}
	}
}
