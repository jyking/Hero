package cn.com.kk.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLUtil.java<br>
 * XML工具类,提供XML检索等服务
 * 
 * @author tuqiang<br>
 * @since 2012-5-7<br>
 * 
 */
public class XMLUtil {

	/**
	 * 从XML Document 中查找指定ID的节点(ElementNode)
	 * 
	 * @param doc
	 * @param id
	 * @return org.w3c.dom.Node
	 */
	public static Node findElementNode(Document doc, String id)
	{
		Element element = doc.getDocumentElement();
		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				if (id.equalsIgnoreCase(XMLUtil.getNodeAttributeValue("id", node)))
				{
					return node;
				}
			}
		}

		return null;
	}

	/**
	 * 查找节点中指定属性的值
	 * 
	 * @param attrName
	 * @param node
	 * @return
	 */
	public static String getNodeAttributeValue(String attrName, Node node)
	{
		if (node == null || attrName == null)
		{
			return null;
		}

		NamedNodeMap attrs = node.getAttributes();
		if (attrs == null)
		{
			return null;
		}

		Node attrNode = attrs.getNamedItem(attrName);
		if (attrNode != null)
		{
			return attrNode.getNodeValue();
		} else
		{
			return null;
		}
	}

	/**
	 * 从XML Document 中查找指定ID及节点名称的节点(ElementNode)
	 * 
	 * @param doc
	 * @param nodeName
	 * @param id
	 * @return org.w3c.dom.Node
	 * @roseuid 44FF70830271
	 */
	public static Node findElementNode(Document doc, String nodeName, String id)
	{
		if (id == null)
		{
			return null;
		}

		Element element = doc.getDocumentElement();
		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				if (nodeName.equals(node.getNodeName()) && id.equalsIgnoreCase(getNodeAttributeValue("id", node)))
				{
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * 从NODE中查找指定节点名称的节点
	 * 
	 * @param node
	 * @param nodeName
	 * @return
	 */
	public static Node findElementNode(Node node, String nodeName)
	{
		NodeList nlist = node.getChildNodes();
		for (int i = 0; i < nlist.getLength(); i++)
		{
			Node nd = nlist.item(i);
			if (nd.getNodeName().equalsIgnoreCase(nodeName))
			{
				return nd;
			}
		}
		return null;
	}

}
