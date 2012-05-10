package cn.com.kk.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.kk.data.HConstants;

/**
 * Test.java<br>
 * ≤‚ ‘¿‡
 * 
 * @author tuqiang<br>
 * @since 2012-5-7<br>
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document document = loader.loadXMLDocument("D:\\øÏ≈Ã\\Project\\Hero\\HeroContent\\config\\hero.xml");
		Node node = XMLUtil.findElementNode(document, "factory1");
		Node n2 = XMLUtil.findElementNode(document, "factory1", "factory1");
		
		Node initNode = XMLUtil.findElementNode(node, HConstants.XML_Factory.ID_INIT);
		String property = XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_CLASS, initNode);
		
		NamedNodeMap nodeMap = initNode.getAttributes();
		
		for (int i = 0; i < nodeMap.getLength(); i++)
		{
			Node n = nodeMap.item(i);
			System.out.println(n.getNodeName());
			System.out.println(n.getNodeValue());
		}
		
	}
}
