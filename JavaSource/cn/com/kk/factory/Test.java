package cn.com.kk.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.kk.data.HConstants;
import cn.com.kk.xml.XMLDocumentLoader;
import cn.com.kk.xml.XMLUtil;

/**
 * Test.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-5-10<br>
 * 
 */
public class Test {
	public static void main(String[] args) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document doc = loader.loadXMLDocument("D:\\øÏ≈Ã\\Project\\Hero\\HeroContent\\config\\services.xml");
		// 1.0 »°µ√SERVER≈‰÷√
		NodeList nodeList = doc.getChildNodes();
		
		NodeList nList = nodeList.item(0).getChildNodes();
		
		for (int i = 0; i < nList.getLength(); i++)
		{
			Node node = nList.item(i);
			if( node instanceof Element )
			System.out.println(XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_ID, node));
		}
	}
}
