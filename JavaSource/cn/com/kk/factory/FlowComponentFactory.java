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
 * ҵ���߼��������ʵ�� ���ڴ���ҵ���߼�XMLʵ����
 * 
 * @author tuqiang<br>
 * @since 2012-4-25<br>
 * 
 */
public class FlowComponentFactory extends ComponentFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlowComponentFactory.class.getName());

	/**
	 * FLOW���
	 */
	private Map<String, Flow> flowCache;

	/**
	 * ���ڵ�
	 */
	private Context rootContext;

	/**
	 * ���ʿ�����
	 */
	private AccessManager accessManage;

	/**
	 * ������ʼ��
	 */
	private Initializer initializer;

	private ContextParser contextParser;
	private FlowParser flowParser;
	private ServiceParser serParser;

	public FlowComponentFactory()
	{
		this.flowCache = new HashMap<String, Flow>();
		this.serviceCache = new HashMap<String, Service>();
		// ָ��Ĭ�Ϸ��ʿ�����
		this.accessManage = new DefAccessManager();
		// ָ��Ĭ�ϳ�ʼ����
		this.initializer = new DefInitializer();
		// ָ��Ĭ�Ͻ�����
		this.contextParser = new ContextParser(this);
		this.flowParser = new FlowParser(this);
		this.serParser = new ServiceParser(this);
	}

	/**
	 * ��ȡFLOW���̶��� ���û��,�������¼��������ļ�
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
		// ���û���������¼��������ļ�
		return initFlowCache(flowId);
	}

	/**
	 * ��ʼ���������
	 * 
	 * @param factoryName
	 *            ��������
	 * @param fileName
	 *            �ļ�����
	 * @throws Exception
	 */
	public void initFlowComponentFactory(String factoryName, String fileName) throws Exception
	{
		LOGGER.info("Initialize [" + factoryName + "] factory...");
		// 1.0 ���ù�������
		this.setName(factoryName);

		// 2.0 ���������ļ�
		String filePath = HTools.Sys.getUserDir() + fileName;
		LOGGER.info("Loading [" + filePath + "]...");
		this.loadXML(filePath);

		// ��������
		Node node = XMLUtil.findElementNode(this.document, factoryName);
		// 3.0������ʼ��
		initFactoryInit(node);

		// 3.2 ���ʿ�����ʵ����
		initFactoryAccess(node);

		// 4.0 �����ʼ��
		Node sNode = XMLUtil
				.findElementNode(this.document, HConstants.XML_Factory.NODE_FILEPATH, HConstants.HSettings.SET_SERVER);
		String serverFile = HTools.Sys.getUserDir() + XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_VALUE, sNode);
		LOGGER.info("Loading [" + serverFile + "]...");
		initServices(serverFile);

		// 5.0 ROOTCONTEXT��ʼ��
		// 1.1 ���������ļ�
		Node dNode = XMLUtil.findElementNode(this.document, HConstants.XML_Factory.NODE_FILEPATH, HConstants.HSettings.SET_DATA);
		String dataFile = HTools.Sys.getUserDir() + XMLUtil.getNodeAttributeValue(HConstants.XML_Factory.PROPERTY_VALUE, dNode);
		LOGGER.info("Loading [" + dataFile + "]...");
		this.createRootContext(factoryName, dataFile);

		// 6.0 ���ó�ʼ���ӿ�
		LOGGER.info("Initialize [" + HConstants.XML_Factory.ID_INIT + "]...");
		this.initializer.initialize(this);
		LOGGER.info("Initialize [" + factoryName + "] factory end...");
	}

	/**
	 * ��ʼ�����ʿ�����
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
	 * ��ʼ��������ʼ��ʵ����
	 * 
	 * @param node
	 * @throws Exception
	 */
	private void initFactoryInit(Node node) throws Exception
	{
		LOGGER.info("Loading [" + HConstants.XML_Factory.ID_INIT + "]...");
		// 3.1 ����������ʼ��ʵ����
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
	 * ��ʼ��FLOW���
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
			// ��ȡFLOW�ļ�
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
	 * ��ʼ��services
	 * 
	 * @param serviceCache2
	 * @param serverFile
	 * @throws Exception
	 */
	private void initServices(String serverFile) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document doc = loader.loadXMLDocument(serverFile);
		// 1.0 ȡ��SERVER����
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
	 * ����ROOTCONTEXT
	 * 
	 * @param id
	 * @param fileName
	 * @throws Exception
	 */
	private void createRootContext(String id, String fileName) throws Exception
	{
		XMLDocumentLoader loader = new XMLDocumentLoader();
		Document doc = loader.loadXMLDocument(fileName);
		// 1.0 ȡ��context����
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
