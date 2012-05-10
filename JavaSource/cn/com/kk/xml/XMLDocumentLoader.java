package cn.com.kk.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * XMLDocumentLoader.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-25<br>
 * 
 */
public class XMLDocumentLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(XMLDocumentLoader.class.getName());

	/**
	 */
	public XMLDocumentLoader()
	{

	}

	/**
	 * @param url
	 * @return org.w3c.dom.Document
	 */
	public Document loadXMLDocunent(URL url) throws Exception
	{
		InputStream in = url.openStream();
		return this.loadXMLDocument(in);

	}

	public InputStream getInputStream(String fileName) throws Exception
	{

		String xml_source = fileName;

		if (fileName.indexOf(":") != -1)
		{
			if (fileName.indexOf(":") <= 2)
			{
				xml_source = "file:///" + fileName;
				java.net.URL aURL = new java.net.URL(xml_source);
				xml_source = aURL.toString();
			}
			java.net.URL aURL = new java.net.URL(xml_source);
			return aURL.openStream();
		} else
		{
			FileInputStream fi = new FileInputStream(fileName);
			return fi;
		}

	}

	public Document loadXMLDocument(String fileName) throws Exception
	{
		String xml_source;
		InputStream in;

		try
		{
			FileInputStream fi = new FileInputStream(fileName);
			Document document = this.loadXMLDocument(fi);
			fi.close();
			return document;

		} catch (Exception e)
		{
			LOGGER.error("load [" + fileName + "] failed...", e);
		}

		if (fileName.indexOf(":") != -1)
		{
			if (fileName.indexOf(":") <= 2)
			{
				xml_source = "file:///" + fileName;
				java.net.URL aURL = new java.net.URL(xml_source);
				xml_source = aURL.toString();
			} else
			{
				xml_source = fileName;
			}
		} else
		{
			xml_source = fileName;
		}

		try
		{
			java.net.URL aURL = new java.net.URL(xml_source); // getClass().getResource(
																// xml_source );
			in = aURL.openStream();
		} catch (Exception e)
		{
			in = getClass().getResourceAsStream(xml_source);
		}
		if (in != null)
		{
			return loadXMLDocument(in);
		}

		throw new Exception("Resource " + fileName + " not found while load the XML document!");
	}

	public Document loadXMLDocument(InputStream in) throws Exception
	{

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{

			DocumentBuilder builder = factory.newDocumentBuilder();

			org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource();
			inputSource.setByteStream(in);

			Document document = builder.parse(inputSource);
			return document;

		} catch (SAXException sxe)
		{
			throw sxe;

		} catch (IOException ioe)
		{
			throw ioe;
		}
	}

}
