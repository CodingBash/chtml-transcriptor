package edu.ilstu.chtmltranscriptor.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import edu.ilstu.chtmltranscriptor.constants.Application;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String chtmlDirectory = System.getProperty("catalina.home") + File.separator + "tmpFiles"
			+ File.separator + "chtml";
	private static final String htmlDirectory = System.getProperty("catalina.home") + File.separator + "tmpFiles"
			+ File.separator + "html";

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

	@RequestMapping(value = "/compile", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ModelAndView compileCode() throws Exception
	{
		File chtmlDir = new File(chtmlDirectory);
		File folder = new File(chtmlDir.getAbsolutePath());
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++)
		{
			addToHtmlDirectory(convertToHtmlTest2(listOfFiles[i]));
		}
		return new ModelAndView("home");
	}

	public File convertToHtmlTest2(File chtml) throws Exception
	{

		String content = FileUtils.readFileToString(chtml);
		// <include\sfile="([a-zA-Z0-9]*\.chtml)"\s*\/>
		Pattern pattern = Pattern.compile("<include\\sfile=\"([a-zA-Z0-9]*\\.html)\"\\s*\\/>");
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
		// TODO to be implemented

		// content.replaceAll("<include\\sfile=\"([a-zA-Z0-9]*\\.chtml)\"\\s*\\/>",
		// getReplacementString("filename") );

		// SAXBuilder builder = new SAXBuilder();
		// Document document = null;
		// document = builder.build(chtml);
		// Element rootNode = document.getRootElement();
		// Element bodyNode = rootNode.getChild("body");
		// List<Element> includes = null;
		// if (bodyNode != null)
		// {
		// includes = bodyNode.getChildren("include");
		// }
		// if (includes != null)
		// {
		// for (Element e : includes)
		// {
		// // TODO: Add index; position
		// document.getRootElement()
		// document.removeContent(e);
		//
		// }
		// }
		// String content = document.toString();
		// System.out.println(content);
		// return chtml;
	}

	private String getReplacementString(String fileName) throws IOException
	{
		if (fileName != null)
		{

			if (!fileName.equalsIgnoreCase("null"))
			{
				// Get element corresponding with the filename

				// Get the chtml directory
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

	public File convertToHtml(File chtml) throws Exception
	{

		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		document = builder.build(chtml);
		Element rootNode = document.getRootElement();
		Element bodyNode = rootNode.getChild("body");
		List<Element> includes = null;
		if (bodyNode != null)
		{
			includes = bodyNode.getChildren("include");
		}
		if (includes != null)
		{
			for (Element e : includes)
			{
				// TODO: Add index; position
				document.getRootElement();
				document.removeContent(e);

			}
		}
		String content = document.toString();
		System.out.println(content);
		return chtml;
	}

	private Element getReplacement(Element e) throws IOException, JDOMException
	{
		String fileName = e.getAttributeValue("file");
		if (fileName != null)
		{

			if (!fileName.equalsIgnoreCase("null"))
			{
				// Get element corresponding with the filename

				// Get the chtml directory
				File dir = new File(chtmlDirectory);
				if (!dir.exists())
					dir.mkdirs(); // Create the file on server

				// Get the file
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

				// 2nd attempt
				byte[] bytes = FileUtils.readFileToByteArray(serverFile);
				String xml = new String(bytes, "utf-8");
				StringReader stringReader = new StringReader(xml);
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(stringReader);
				Element elem = doc.getRootElement();
				return elem;
			}
		}
		return null;
	}

	public void replaceElements(Map<Element, String> elementMap)
			throws IOException, JDOMException, ParserConfigurationException, SAXException
	{
		for (Entry<Element, String> entry : elementMap.entrySet())
		{
			// File name
			String fileName = entry.getValue();
			if (fileName != null)
			{

				if (!fileName.equalsIgnoreCase("null"))
				{
					// Get element corresponding with the filename
					Element element = entry.getKey();

					// Get the chtml directory
					File dir = new File(chtmlDirectory);
					if (!dir.exists())
						dir.mkdirs(); // Create the file on server

					// Get the file
					File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

					// 2nd attempt
					byte[] bytes = FileUtils.readFileToByteArray(serverFile);
					String xml = new String(bytes, "utf-8");
					StringReader stringReader = new StringReader(xml);
					SAXBuilder builder = new SAXBuilder();
					Document doc = builder.build(stringReader);
					Element elem = doc.getRootElement();
				}
			}
		}
	}

	/**
	 * Add file to directory
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

	public File convert(MultipartFile file) throws Exception
	{
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}