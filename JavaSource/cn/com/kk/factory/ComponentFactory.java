package cn.com.kk.factory;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import cn.com.kk.service.Service;
import cn.com.kk.xml.XMLDocumentLoader;

/**
 * Factory.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-24<br>
 * 
 */
public class ComponentFactory {

	/**
	 * �Ѿ�ʵ�������������,�������������ͬʱ����
	 */
	private static Map<String, ComponentFactory> factories;

	/**
	 * ��������
	 */
	private String name;
	
	/**
	 * ����
	 */
	protected Map<String, Service> serviceCache;

	protected Document document;
	
	public ComponentFactory()
	{

	}

	/**
	 * ���������ļ�
	 * @param fileName
	 * @throws Exception
	 */
	public void loadXML(String fileName) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		this.document = loader.loadXMLDocument(fileName);
	}

	public static void addComponentFactory(ComponentFactory factory)
	{
		if (factories == null)
		{
			factories = new HashMap<String, ComponentFactory>();
		}

		factories.put(factory.getName(), factory);
	}

	public static void removeComponentFactory(String name)
	{
		if (factories != null)
		{
			factories.remove(name);
		}
	}

	/**
	 * ȡ�Ѿ�ʵ�������������.
	 * 
	 * @param name
	 * @return com.ecc.emp.component.factory.ComponentFactory
	 */
	public static ComponentFactory getComponentFactory(String name)
	{
		if (factories == null)
		{
			return null;
		}
		return (ComponentFactory) factories.get(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Document getDocument()
	{
		return document;
	}

	public Service getService( String serviceId )
	{
		return this.serviceCache.get(serviceId);
	}
	
}
