package cn.com.kk.xml.parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.kk.data.Context;
import cn.com.kk.data.DataElement;
import cn.com.kk.data.DataField;
import cn.com.kk.data.HConstants;
import cn.com.kk.data.KeyedCollection;
import cn.com.kk.factory.ComponentFactory;
import cn.com.kk.xml.XMLUtil;

/**
 * ContextParser.java<br>
 * CONTEXT������
 * 
 * @author tuqiang<br>
 * @since 2012-5-10<br>
 * 
 */
public class ContextParser extends ComponentParser {

	
	
	public ContextParser(ComponentFactory factory)
	{
		super(factory);
	}

	public Object parseTheElement(Node elementNode) throws Exception
	{
		// TODO ����field����ICOLL
		return null;
	}

	/**
	 * ����XML����CONTEXT����ģ��
	 * 
	 * @param node
	 * @return
	 * @throws Exception 
	 */
	public Context createContextMode(Node node,String contextName) throws Exception
	{
		Context context = new Context();
		// ��NODEģ��ת��ΪCONTEXT
		// ��������ģ��
		Node dataNode = XMLUtil.findElementNode(node, HConstants.XML_Context.NODE_KCOLL);
		NodeList dataList = dataNode.getChildNodes();
		KeyedCollection kColl = new KeyedCollection(contextName);
		for (int i = 0; i < dataList.getLength(); i++)
		{
			Node dNode = dataList.item(i);
			if (dNode instanceof Element)
			{
				Object dataMode = this.parseTheElement(dNode);
				if (dataMode instanceof DataField)
				{
					kColl.addDataField((DataField) dataMode);
				} else
				{
					kColl.addDataElement((DataElement) dataMode);
				}
			}
		}
		context.setDataElement(kColl);

		// ���ط���
		NodeList nList = node.getChildNodes();
		for (int i = 0; i < nList.getLength(); i++)
		{
			Node sNode = nList.item(i);
			if (sNode instanceof Element && HConstants.XML_Context.NODE_SERVER.equalsIgnoreCase(sNode.getNodeName()))
			{
				String serviceId = XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_ID, sNode);
				context.addService(serviceId, this.componentFactory.getService(serviceId));
			}
		}
		return context;
	}

	@Override
	protected boolean isInnerAttribute(String name)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
