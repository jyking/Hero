package cn.com.kk.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.kk.accessmanager.AccessManager;
import cn.com.kk.accessmanager.DefAccessManager;
import cn.com.kk.data.Context;
import cn.com.kk.data.HConstants;
import cn.com.kk.flow.Flow;
import cn.com.kk.initializer.DefInitializer;
import cn.com.kk.initializer.Initializer;
import cn.com.kk.service.Service;
import cn.com.kk.tools.HTools;
import cn.com.kk.xml.XMLDocumentLoader;
import cn.com.kk.xml.XMLUtil;
import cn.com.kk.xml.parser.ContextParser;
import cn.com.kk.xml.parser.FlowParser;
import cn.com.kk.xml.parser.ServiceParser;

/**
 * FlowComponentFactory.java<br>
 * 业务逻辑组件工厂实现 用于处理业务逻辑XML实例化
 * 
 * @author tuqiang<br>
 * @since 2012-4-25<br>
 * 
 */
public class FlowComponentFactory extends ComponentFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlowComponentFactory.class.getName());

	/**
	 * FLOW组件
	 */
	private Map<String, Flow> flowCache;

	/**
	 * 根节点
	 */
	private Context rootContext;

	/**
	 * 访问控制器
	 */
	private AccessManager accessManage;

	/**
	 * 容器初始化
	 */
	private Initializer initializer;

	private ContextParser contextParser;
	private FlowParser flowParser;
	private ServiceParser serParser;

	public FlowComponentFactory()
	{
		this.flowCache = new HashMap<String, Flow>();
		this.serviceCache = new HashMap<String, Service>();
		// 指定默认访问控制器
		this.accessManage = new DefAccessManager();
		// 指定默认初始化类
		this.initializer = new DefInitializer();
		// 指定默认解析器
		this.contextParser = new ContextParser(this);
		this.flowParser = new FlowParser(this);
		this.serParser = new ServiceParser(this);
	}

	/**
	 * 获取FLOW流程对象 如果没有,则尝试重新加载配置文件
	 * 
	 * @param flowId
	 * @return
	 */
	public Flow getFlow(String flowId)
	{
		Flow flow = flowCache.get(flowId);
		if (null != flow)
		{
			return flow;
		}
		LOGGER.info("Begin to instance Flow [" + flowId + "]...");
		// 如果没有则尝试重新加载配置文件
		return initFlowCache(flowId);
	}

	/**
	 * 初始化组件工厂
	 * 
	 * @param factoryName
	 *            工厂名称
	 * @param fileName
	 *            文件名称
	 * @throws Exception
	 */
	public void initFlowComponentFactory(String factoryName, String fileName) throws Exception
	{
		LOGGER.info("Initialize [" + factoryName + "] factory...");
		// 1.0 设置工厂名称
		this.setName(factoryName);

		// 2.0 加载配置文件
		String filePath = HTools.Sys.getUserDir() + fileName;
		LOGGER.info("Loading [" + filePath + "]...");
		this.loadXML(filePath);

		// 工厂配置
		Node node = XMLUtil.findElementNode(this.document, factoryName);
		// 3.0工厂初始化
		initFactoryInit(node);

		// 3.2 访问控制器实现类
		initFactoryAccess(node);

		// 4.0 服务初始化
		Node sNode = XMLUtil
				.findElementNode(this.document, HConstants.XML_Factory.NODE_FILEPATH, HConstants.HSettings.SET_SERVER);
		String serverFile = HTools.Sys.getUserDir() + XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_VALUE, sNode);
		LOGGER.info("Loading [" + serverFile + "]...");
		initServices(serverFile);

		// 5.0 ROOTCONTEXT初始化
		// 1.1 加载配置文件
		Node dNode = XMLUtil.findElementNode(this.document, HConstants.XML_Factory.NODE_FILEPATH, HConstants.HSettings.SET_DATA);
		String dataFile = HTools.Sys.getUserDir() + XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_VALUE, dNode);
		LOGGER.info("Loading [" + dataFile + "]...");
		this.createRootContext(factoryName, dataFile);

		// 6.0 调用初始化接口
		LOGGER.info("Initialize [" + HConstants.XML_Factory.ID_INIT + "]...");
		this.initializer.initialize(this);
		LOGGER.info("Initialize [" + factoryName + "] factory end...");
	}

	/**
	 * 初始化访问控制器
	 * 
	 * @param node
	 */
	private void initFactoryAccess(Node node) throws Exception
	{
		LOGGER.info("Loading [" + HConstants.XML_Factory.ID_ACCESS + "]...");
		Node accessNode = XMLUtil.findElementNode(node, HConstants.XML_Factory.ID_ACCESS);

		if (null != accessNode)
		{
			String accessClass = XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_CLASS, accessNode);
			if (null != accessClass)
			{
				this.accessManage = (AccessManager) Class.forName(accessClass).newInstance();
			}
		}
		LOGGER.info("Loading [" + this.accessManage.getClass().getName() + "] end...");
	}

	/**
	 * 初始化容器初始化实现类
	 * 
	 * @param node
	 * @throws Exception
	 */
	private void initFactoryInit(Node node) throws Exception
	{
		LOGGER.info("Loading [" + HConstants.XML_Factory.ID_INIT + "]...");
		// 3.1 程序启动初始化实现类
		Node initNode = XMLUtil.findElementNode(node, HConstants.XML_Factory.ID_INIT);
		if (null != initNode)
		{
			String initClass = XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_CLASS, initNode);
			if (null != initClass)
			{
				this.initializer = (Initializer) Class.forName(initClass).newInstance();
			}
		}
		LOGGER.info("Loading [" + this.initializer.getClass().getName() + "] end...");
	}

	/**
	 * 初始化FLOW组件
	 * 
	 * @param flowFile
	 * @return
	 */
	private Flow initFlowCache(String flowId)
	{
		synchronized (this)
		{
			Flow flow = null;
			Node node = XMLUtil.findElementNode(this.document, HConstants.XML_Factory.NODE_FILEPATH,
					HConstants.XML_Factory.ID_OPERATIONS);
			// 获取FLOW文件
			String filePath = HTools.Sys.getUserDir()
					+ XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_VALUE, node) + this.getName()
					+ HTools.Sys.getFileSeparator() + flowId + ".xml";
			XMLDocumentLoader loader = new XMLDocumentLoader();
			try
			{
				Document doc = loader.loadXMLDocument(filePath);
				flow = this.flowParser.createFlow(doc);
				this.flowCache.put(flowId, flow);
			} catch (Exception e)
			{
				LOGGER.error("Loading [" + filePath + "] error.");
			}
			return flow;
		}
	}

	/**
	 * 初始化services
	 * 
	 * @param serviceCache2
	 * @param serverFile
	 * @throws Exception
	 */
	private void initServices(String serverFile) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document doc = loader.loadXMLDocument(serverFile);
		// 1.0 取得SERVER配置
		NodeList nodeList = doc.getChildNodes();
		NodeList nList = nodeList.item(0).getChildNodes();
		for (int i = 0; i < nList.getLength(); i++)
		{
			Node node = nList.item(i);
			if (node instanceof Element)
			{
				String id = XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_ID, node);
				Service service = (Service) this.serParser.parseTheElement(node);
				this.serviceCache.put(id, service);
			}
		}
	}

	/**
	 * 创建ROOTCONTEXT
	 * 
	 * @param id
	 * @param fileName
	 * @throws Exception
	 */
	private void createRootContext(String id, String fileName) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document doc = loader.loadXMLDocument(fileName);
		// 1.0 取得context配置
		Node node = XMLUtil.findElementNode(doc, HConstants.XML_Context.NODE_DATA, id);

		if (null != node)
		{
			this.rootContext = this.contextParser.createContextMode(node, getName());
		}
	}

	public Context getRootContext()
	{
		return rootContext;
	}

	public AccessManager getAccessManage()
	{
		return accessManage;
	}

}
