package cn.com.kk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.kk.exception.DuplicatedDataNameException;
import cn.com.kk.exception.InvalidArgumentException;
import cn.com.kk.exception.ObjectNotFoundException;
import cn.com.kk.service.Service;

/**
 * Context.java<br>
 * 业务逻辑定义使用的资源上下文定义，通过它实现对业务逻辑处理定义中涉及的数据定义的访问，
 * 以及服务的访问。它是一颗树形结构，通过树形的结构，实现公有资源的共享访问，
 * 也就是在叶子节点上的Context可以访问到父节点上的资源。比如：getDataValue的方法，首先从本节点
 * 查找是否有此数据定义，如果没有，则在父节点查找。
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class Context implements Serializable {

	private String name;

	/**
	 * 连接到此节点的所有子节点
	 */
	private List childs;

	/**
	 * 父节点Context
	 */
	private Context parent;

	/**
	 * 此节点上定义的数据模型，应该是KeyedCollection
	 */
	private DataElement dataElement;

	/**
	 * 节点上定义的所有服务
	 */
	private Map services;

	/**
	 * 用于保存用户自定义的对象
	 */
	private Map attributes;

	/**
	 * Format 定义
	 */
	private Map formats;

	private String parentContextName;

	/**
	 * 构造一个空的Context
	 */
	public Context()
	{
		super();
	}

	public Context(String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setId(String value)
	{
		name = value;
	}

	/**
	 * 链接到某个父Context节点上
	 * 
	 * @param context
	 */
	public void chainedTo(Context context)
	{
		this.parent = context;
		context.addChildContext(this);
	}

	/**
	 * 查找ID为name的数据定义，首先在本节点内查找，如果查找不到，则从其父节点中查找
	 * 
	 * @param name
	 *            允许以'.'分割的方式进行深度查找
	 * @return cn.com.kk.core.DataElement
	 * @throws cn.com.kk.core.ObjectNotFoundException
	 */
	public DataElement getDataElement(String name) throws ObjectNotFoundException, InvalidArgumentException
	{
		KeyedCollection kColl = (KeyedCollection) dataElement;

		DataElement element = kColl.getDataElement(name);
		if (element != null)
			return element;
		else if (parent != null)
		{
			element = parent.getDataElement(name);
			if (element != null)
				return element;
		}

		throw new ObjectNotFoundException("DataElement named [" + name + "] not defined in Context [" + getName()
				+ "]!");
	}

	/**
	 * 取得ID为name的数据定义的值，首先在本节点内查找，如果查找不到，则从其父节点中查找
	 * 
	 * @param name
	 *            允许以'.'分割的方式进行深度查找
	 * @return java.lang.Object
	 * @deprecated
	 */
	public Object getValueAt(String name) throws ObjectNotFoundException, InvalidArgumentException
	{
		return this.getDataValue(name);
	}

	/**
	 * 设定ID为name的数据定义的值，首先本节点内查找数据并设定其值，如果查找不到则从父节点查找
	 * 
	 * @param name
	 *            允许以'.'分割的方式进行深度查找
	 * @param value
	 * @deprecated
	 */
	public void setValueAt(String name, Object value) throws ObjectNotFoundException, InvalidArgumentException
	{
		setDataValue(name, value);
	}

	/**
	 * 取得ID为name的数据定义的值，首先在本节点内查找，如果查找不到，则从其父节点中查找
	 * 
	 * @param name
	 *            允许以'.'分割的方式进行深度查找
	 * @return java.lang.Object
	 */
	public Object getDataValue(String name) throws ObjectNotFoundException, InvalidArgumentException
	{
		DataElement element = this.getDataElement(name);
		if (element instanceof DataField)
			return ((DataField) element).getValue();
		else
			throw new InvalidArgumentException("Data field named [" + name
					+ "] is not a dataField in get dataValue from context [" + this.getName() + "].");
	}

	/**
	 * 设定ID为name的数据定义的值，首先本节点内查找数据并设定其值，如果查找不到则从父节点查找
	 * 
	 * @param name
	 *            允许以'.'分割的方式进行深度查找
	 * @param value
	 */
	public void setDataValue(String name, Object value) throws ObjectNotFoundException, InvalidArgumentException
	{
		DataElement element = getDataElement(name);
		if (element instanceof DataField)
			((DataField) element).setValue(value);
		else
			throw new InvalidArgumentException("Data field named [" + name
					+ "] is not a dataField in set dataValue from context [" + this.getName() + "].");
		// DataField field = (DataField)element;
		// field.setValue( value );
	}

	/**
	 * 向数据定义中添加一个新的数据元素，如果数据元素ID重复将抛出异常
	 * 
	 * @param dataElement
	 */
	public void addDataElement(DataElement dataElement) throws DuplicatedDataNameException
	{
		((KeyedCollection) this.dataElement).addDataElement(dataElement);
	}

	/**
	 * 向数据定义中添加一个新的数据元素其ID为name值为value，系统将创建一个新的DataField对象并添加到数据定义中，
	 * 如果数据元素ID重复将抛出异常
	 * 
	 * @param name
	 * @param value
	 */
	public void addDataField(String name, Object value) throws InvalidArgumentException, DuplicatedDataNameException
	{

		((KeyedCollection) dataElement).addDataField(name, value);

	}

	/**
	 * 将ID为name的数据从数据定义中删除
	 * 
	 * @param name
	 */
	public void removeDataElement(String name)
	{
		((KeyedCollection) dataElement).removeDataElement(name);

	}

	/**
	 * 设定用户自定义的对象，用于保存或传递用户定义对象
	 * 
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value)
	{
		if (this.attributes == null)
			attributes = new HashMap();
		attributes.put(name, value);
	}

	/**
	 * 取得用户定义的对象
	 * 
	 * @param name
	 * @return Object
	 */
	public Object getAttribute(String name)
	{
		if (attributes == null)
			return null;
		else
			return attributes.get(name);
	}

	/**
	 * 取Context对应的数据定义模型
	 * 
	 * @return cn.com.kk.core.DataElement
	 */
	public DataElement getDataElement()
	{
		return dataElement;
	}

	public void setDataElement(DataElement dataElement)
	{
		this.dataElement = dataElement;
	}

	/**
	 * 克隆一个新的Context对象
	 * 
	 * @return Object
	 */
	public Object clone()
	{
		Context context = new Context(name);
		context.setDataElement((DataElement) dataElement.clone());
		context.setFormats(formats);
		context.setServices(services);// Context ref's service are single
		context.setParentContextName(this.parentContextName);
		if (this.parent != null)
			context.chainedTo(parent);
		return context;
	}

	/**
	 * 取得ID为serviceId的服务定义对象，首先在本节点内查找，如果查找不到，则从其父节点查找
	 * 
	 * @param serviceId
	 * @return com.ecc.emp.service.Service
	 */
	public Object getService(String serviceId)
	{

		Object service = null;
		if (services != null)
			service = services.get(serviceId);

		if (service != null)
			return service;

		if (parent != null)
			return parent.getService(serviceId);

		return null;
	}

	/**
	 * 添加服务定义
	 * 
	 * @param serviceId
	 * @param service
	 */
	public void addService(String serviceId, Object service)
	{
		if (services == null)
			services = new HashMap();
		services.put(serviceId, service);
	}

	public void setServices(Map services)
	{
		this.services = services;
	}

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<context id=\"" + getName() + "\"");
		if (this.getParentContextName() != null)
		{
			buffer.append(" parent=\"");
			buffer.append(getParentContextName());
			buffer.append("\"");
		}
		buffer.append(">\n");

		if (dataElement != null)
		{
			buffer.append("\t<refKColl refId=\"");
			buffer.append(dataElement.getName());
			buffer.append("\"/>");
		}

		if (services != null)
		{
			Object[] allSvcs = services.values().toArray();

			for (int i = 0; i < allSvcs.length; i++)
			{
				Object service = allSvcs[i];

				String name = service.toString();
				if (service instanceof Service)
					name = ((Service) service).getName();
				buffer.append("\t<refService refId=\"");
				buffer.append(name);
				buffer.append("\"/>");

			}
		}
		buffer.append(">\n");
		buffer.append("</context>");

		return buffer.toString();
	}

	/**
	 * 遍历Context树，找到指定ID的Context
	 * 
	 * @param contextId
	 * @return
	 */
	public Context getContextNamed(String contextId)
	{
		if (contextId.equals(this.getName()))
			return this;

		if (childs == null)
			return null;

		for (int i = 0; i < this.childs.size(); i++)
		{
			Context aContext = (Context) childs.get(i);
			if (contextId.equals(aContext.getName()))
				return aContext;
			aContext = aContext.getContextNamed(contextId);
			if (aContext != null)
				return aContext;
		}
		return null;

	}

	/**
	 * 添加子节点
	 * 
	 * @param context
	 */
	public synchronized void addChildContext(Context context)
	{
		if (childs == null)
			childs = new ArrayList();
		if (childs.contains(context))
			return;

		childs.add(context);
	}

	/**
	 * 删除子节点
	 * 
	 * @param context
	 */
	public synchronized void removeChildContext(Context context)
	{
		if (childs != null)
			childs.remove(context);
	}

	public void unChain()
	{
		if (parent == null)
			return;

		parent.removeChildContext(this);
		parent = null;

	}

	/**
	 * 终止此Context，首先它断开与父结点的链接，然后逐个掉用Service的terminate方法，
	 * 接着掉用所有链接到此Context的子Context的terminate方法
	 * 
	 */
	public void terminate()
	{
		unChain();

		if (services != null)
		{
			Object[] names = services.keySet().toArray();
			for (int i = 0; i < names.length; i++)
			{
				Object service = services.get(names[i]);
				if (service instanceof Service)
					((Service) service).terminate();
			}
		}
		if (childs == null)
			return;

		for (int i = 0; i < childs.size(); i++)
		{
			Context childCtx = (Context) childs.get(i);
			childCtx.terminate();
		}
	}

	public void setParent(String parent)
	{
		parentContextName = parent;
	}

	// no use type attr, to compatable with old setting files
	public void setType(String value)
	{

	}

	public String getParentContextName()
	{
		return parentContextName;
	}

	public void setParentContextName(String parentContextName)
	{
		this.parentContextName = parentContextName;
	}

	public Context getParent()
	{
		return parent;
	}

	public void setParent(Context parent)
	{
		this.parent = parent;
	}

	/**
	 * 设定Format定义，内部方法，由Flow调用
	 * 
	 * @param fmts
	 */
	public void setFormats(Map fmts)
	{
		this.formats = fmts;
	}

//	public FormatElement getFormat(String name)
//	{
//		if (formats == null)
//			return null;
//		return (FormatElement) formats.get(name);
//	}

	public int getChildCount()
	{
		return this.childs.size();
	}
}
