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
 * ҵ���߼�����ʹ�õ���Դ�����Ķ��壬ͨ����ʵ�ֶ�ҵ���߼����������漰�����ݶ���ķ��ʣ�
 * �Լ�����ķ��ʡ�����һ�����νṹ��ͨ�����εĽṹ��ʵ�ֹ�����Դ�Ĺ�����ʣ�
 * Ҳ������Ҷ�ӽڵ��ϵ�Context���Է��ʵ����ڵ��ϵ���Դ�����磺getDataValue�ķ��������ȴӱ��ڵ�
 * �����Ƿ��д����ݶ��壬���û�У����ڸ��ڵ���ҡ�
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class Context implements Serializable {

	private String name;

	/**
	 * ���ӵ��˽ڵ�������ӽڵ�
	 */
	private List childs;

	/**
	 * ���ڵ�Context
	 */
	private Context parent;

	/**
	 * �˽ڵ��϶��������ģ�ͣ�Ӧ����KeyedCollection
	 */
	private DataElement dataElement;

	/**
	 * �ڵ��϶�������з���
	 */
	private Map services;

	/**
	 * ���ڱ����û��Զ���Ķ���
	 */
	private Map attributes;

	/**
	 * Format ����
	 */
	private Map formats;

	private String parentContextName;

	/**
	 * ����һ���յ�Context
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
	 * ���ӵ�ĳ����Context�ڵ���
	 * 
	 * @param context
	 */
	public void chainedTo(Context context)
	{
		this.parent = context;
		context.addChildContext(this);
	}

	/**
	 * ����IDΪname�����ݶ��壬�����ڱ��ڵ��ڲ��ң�������Ҳ���������丸�ڵ��в���
	 * 
	 * @param name
	 *            ������'.'�ָ�ķ�ʽ������Ȳ���
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
	 * ȡ��IDΪname�����ݶ����ֵ�������ڱ��ڵ��ڲ��ң�������Ҳ���������丸�ڵ��в���
	 * 
	 * @param name
	 *            ������'.'�ָ�ķ�ʽ������Ȳ���
	 * @return java.lang.Object
	 * @deprecated
	 */
	public Object getValueAt(String name) throws ObjectNotFoundException, InvalidArgumentException
	{
		return this.getDataValue(name);
	}

	/**
	 * �趨IDΪname�����ݶ����ֵ�����ȱ��ڵ��ڲ������ݲ��趨��ֵ��������Ҳ�����Ӹ��ڵ����
	 * 
	 * @param name
	 *            ������'.'�ָ�ķ�ʽ������Ȳ���
	 * @param value
	 * @deprecated
	 */
	public void setValueAt(String name, Object value) throws ObjectNotFoundException, InvalidArgumentException
	{
		setDataValue(name, value);
	}

	/**
	 * ȡ��IDΪname�����ݶ����ֵ�������ڱ��ڵ��ڲ��ң�������Ҳ���������丸�ڵ��в���
	 * 
	 * @param name
	 *            ������'.'�ָ�ķ�ʽ������Ȳ���
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
	 * �趨IDΪname�����ݶ����ֵ�����ȱ��ڵ��ڲ������ݲ��趨��ֵ��������Ҳ�����Ӹ��ڵ����
	 * 
	 * @param name
	 *            ������'.'�ָ�ķ�ʽ������Ȳ���
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
	 * �����ݶ��������һ���µ�����Ԫ�أ��������Ԫ��ID�ظ����׳��쳣
	 * 
	 * @param dataElement
	 */
	public void addDataElement(DataElement dataElement) throws DuplicatedDataNameException
	{
		((KeyedCollection) this.dataElement).addDataElement(dataElement);
	}

	/**
	 * �����ݶ��������һ���µ�����Ԫ����IDΪnameֵΪvalue��ϵͳ������һ���µ�DataField������ӵ����ݶ����У�
	 * �������Ԫ��ID�ظ����׳��쳣
	 * 
	 * @param name
	 * @param value
	 */
	public void addDataField(String name, Object value) throws InvalidArgumentException, DuplicatedDataNameException
	{

		((KeyedCollection) dataElement).addDataField(name, value);

	}

	/**
	 * ��IDΪname�����ݴ����ݶ�����ɾ��
	 * 
	 * @param name
	 */
	public void removeDataElement(String name)
	{
		((KeyedCollection) dataElement).removeDataElement(name);

	}

	/**
	 * �趨�û��Զ���Ķ������ڱ���򴫵��û��������
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
	 * ȡ���û�����Ķ���
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
	 * ȡContext��Ӧ�����ݶ���ģ��
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
	 * ��¡һ���µ�Context����
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
	 * ȡ��IDΪserviceId�ķ�������������ڱ��ڵ��ڲ��ң�������Ҳ���������丸�ڵ����
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
	 * ��ӷ�����
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
	 * ����Context�����ҵ�ָ��ID��Context
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
	 * ����ӽڵ�
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
	 * ɾ���ӽڵ�
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
	 * ��ֹ��Context���������Ͽ��븸�������ӣ�Ȼ���������Service��terminate������
	 * ���ŵ����������ӵ���Context����Context��terminate����
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
	 * �趨Format���壬�ڲ���������Flow����
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
