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
	 * 已经实例化的组件工厂,允许多个组件工厂同时存在
	 */
	private static Map<String, ComponentFactory> factories;

	/**
	 * 工厂名称
	 */
	private String name;
	
	/**
	 * 服务
	 */
	protected Map<String, Service> serviceCache;

	protected Document document;
	
	public ComponentFactory()
	{

	}

	/**
	 * 加载配置文件
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
	 * 取已经实例化的组件工厂.
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
