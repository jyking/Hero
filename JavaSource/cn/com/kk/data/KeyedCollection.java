package cn.com.kk.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.kk.exception.DuplicatedDataNameException;
import cn.com.kk.exception.InvalidArgumentException;
import cn.com.kk.exception.ObjectNotFoundException;

/**
 * KeyedCollection.java<br>
 * 结构化数据定义实现类，使用HashMap来存放所有的数据定义
 * 
 * @author tuqiang<br>
 * @since 2012-4-21<br>
 * 
 */
public class KeyedCollection extends DataElement implements Map {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyedCollection.class.getName());

	/**
	 * @since 2006-09-05 存放的所有数据元素
	 */
	private Map dataElements;

	private List dataNames;

	public KeyedCollection()
	{
		super();
		dataElements = new HashMap();
		this.dataNames = new ArrayList();
	}

	public KeyedCollection(String name)
	{
		super(name);
		dataElements = new HashMap();
		this.dataNames = new ArrayList();

	}

	/**
	 * 取数据定义的值
	 * 
	 * @param dataName
	 * @return java.lang.Object
	 */
	public Object getDataValue(String dataName) throws ObjectNotFoundException, InvalidArgumentException
	{

		DataField field = (DataField) this.getDataElement(dataName);
		if (field == null)
			throw new ObjectNotFoundException("DataField named[" + dataName + "] not defined in KeyedCollection ["
					+ getName() + "]!");
		return field.getValue();
	}

	/**
	 * 取数据定义的值
	 * 
	 * @param dataName
	 *            允许以'.'分割的方式进行深度查找
	 * @return java.lang.Object
	 * @deprecated please use getDataValue
	 */
	public Object getValueAt(String dataName) throws ObjectNotFoundException, InvalidArgumentException
	{
		return getDataValue(dataName);

	}

	/**
	 * 设定数据定义值
	 * 
	 * @param dataName
	 *            允许以'.'分割的方式进行深度查找
	 * @param value
	 */
	public void setDataValue(String dataName, Object value) throws InvalidArgumentException, ObjectNotFoundException
	{
		DataField field = (DataField) this.getDataElement(dataName);
		if (field == null)
			throw new ObjectNotFoundException("DataField named[" + dataName + "] not defined in KeyedCollection ["
					+ getName() + "]!");
		field.setValue(value);

	}

	/**
	 * 设定数据定义值
	 * 
	 * @param dataName
	 * @param value
	 * @deprecated please use setDataValue
	 */
	public void setValueAt(String dataName, Object value) throws InvalidArgumentException, ObjectNotFoundException
	{
		setDataValue(dataName, value);

	}

	/**
	 * 取第idx个数据元素
	 * 
	 * @param idx
	 * @return
	 * @throws InvalidArgumentException
	 */
	public DataElement getDataElement(int idx) throws InvalidArgumentException
	{
		if (idx < 0 || idx >= dataNames.size())
			throw new InvalidArgumentException("idx out of bound while get dataElement from keyedCollection!");

		String dataName = (String) this.dataNames.get(idx);
		return (DataElement) this.dataElements.get(dataName);
	}

	/**
	 * 取数据元素定义
	 * 
	 * @param dataName
	 *            允许以'.'分割的方式进行深度查找
	 * @return cn.com.kk.core.DataElement
	 */
	public DataElement getDataElement(String dataName) throws InvalidArgumentException
	{
		if (dataName == null)
			throw new InvalidArgumentException("dataName should not be null when get dataElement from KeyedCollection!");

		int idx = dataName.indexOf('.');

		if (idx != -1)
		{
			String kCollName = dataName.substring(0, idx);
			KeyedCollection kColl = (KeyedCollection) this.dataElements.get(kCollName);
			if (kColl == null && this.isAppend())
			{
				kColl = new KeyedCollection();
				kColl.setName(kCollName);
				try
				{
					this.addDataElement(kColl);
				} catch (DuplicatedDataNameException ee)
				{
					LOGGER.error("getDataElement(String) auto Apeend dataElement-kcoll in " + this.getName(), ee);
					return null;
				}
			}
			if (kColl == null)
			{
				return null;
			}
			return kColl.getDataElement(dataName.substring(idx + 1));
		}

		else
		{
			DataElement field = (DataElement) this.dataElements.get(dataName);
			if (field == null && this.isAppend())
			{
				field = new DataElement();
				field.setName(dataName);
				try
				{
					this.addDataElement(field);
				} catch (DuplicatedDataNameException ee)
				{
					LOGGER.error("getDataElement(String) auto Apeend dataElement-field in " + this.getName(), ee);
					return null;
				}
			}
			if (field != null)
				return field;
			else
				return null;
		}

	}

	/**
	 * @param dataName
	 * @return cn.com.kk.core.DataElement
	 * @deprecated
	 */
	public DataElement getElementAt(String dataName) throws InvalidArgumentException
	{
		return getDataElement(dataName);
	}

	/**
	 * 删除数据定义
	 * 
	 * @param dataName
	 * @return cn.com.kk.core.DataElement
	 * @deprecated
	 */
	public DataElement removeElementAt(String dataName)
	{
		return removeDataElement(dataName);
	}

	/**
	 * 删除数据定义
	 * 
	 * @param dataName
	 *            允许以'.'分割的方式进行深度查找
	 * @return
	 */
	public DataElement removeDataElement(String dataName)
	{

		if (dataName == null)
			return null;

		int idx = dataName.indexOf('.');
		if (idx != -1)
		{
			String kCollName = dataName.substring(0, idx);
			KeyedCollection kColl = (KeyedCollection) this.dataElements.get(kCollName);
			if (kColl == null)
			{
				return null; // throw new
				// ObjectNotFoundException("KeyCollection named
				// [" + kCollName + "] not defined in
				// KeyedCollection[" + this.getName() + "] when
				// getValueAt[" + dataName+ "]!");
			}
			return kColl.removeDataElement(dataName.substring(idx + 1));

		} else
		{
			this.dataNames.remove(dataName);
			return (DataElement) dataElements.remove(dataName);
		}
	}

	/**
	 * @return Object
	 */
	public Object clone()
	{
		KeyedCollection kColl = new KeyedCollection(getName());
		kColl.setAppend(this.getAppend());
		for (int i = 0; i < this.dataNames.size(); i++)
		{
			DataElement element = (DataElement) this.dataElements.get(dataNames.get(i));
			element = (DataElement) element.clone();
			try
			{
				kColl.addDataElement(element);
			} catch (Exception e)
			{

			}
		}
		return kColl;
	}

	/**
	 * @roseuid 44EA9FDC010C
	 */
	public void reset()
	{

	}

	/**
	 * 添加数据元素
	 * 
	 * @param dataElement
	 */
	public void addDataElement(DataElement dataElement) throws DuplicatedDataNameException
	{
		if (dataNames.contains(dataElement.getName()))
		{
			String tmp = dataElement.getClass().toString();
			int idx = tmp.lastIndexOf(".");
			if (idx != -1)
			{
				tmp = tmp.substring(idx + 1);
			}
			throw new DuplicatedDataNameException(tmp + " named [" + dataElement.getName()
					+ "] still exist in KeyedCollection [" + getName() + "]!");
		}
		if (this.isAppend())
		{
			dataElement.setAppend(this.getAppend());
		}
		dataElements.put(dataElement.getName(), dataElement);
		dataNames.add(dataElement.getName());

	}

	/**
	 * 添加数据元素
	 * 
	 * @param field
	 * @throws DuplicatedDataNameException
	 */
	public void addDataField(DataField field) throws DuplicatedDataNameException
	{
		addDataElement(field);
	}

	public void addKeyedCollection(KeyedCollection kColl) throws InvalidArgumentException, DuplicatedDataNameException
	{
		// if( dataNames.contains( kColl.getName() ))
		// throw new InvalidArgumentException("DataField named [" +
		// kColl.getName() + "] still exist in KeyedCollection [" + getName() +
		// "]!");
		//
		// dataElements.put(kColl.getName(), kColl );
		// dataNames.add( kColl.getName() );
		addDataElement(kColl);

	}

	public void addIndexedCollection(IndexedCollection iColl) throws InvalidArgumentException,
			DuplicatedDataNameException
	{
		// if( dataNames.contains( iColl.getName() ))
		// throw new InvalidArgumentException("DataField named [" +
		// iColl.getName() + "] still exist in KeyedCollection [" + getName() +
		// "]!");
		//
		// dataElements.put(iColl.getName(), iColl );
		// dataNames.add( iColl.getName() );
		addDataElement(iColl);

	}

	/**
	 * @param name
	 * @param value
	 */
	public void addDataField(String name, Object value) throws InvalidArgumentException, DuplicatedDataNameException
	{
		if (name == null)
			throw new InvalidArgumentException("Datafield's name should not be null!");

		DataField field = new DataField(name, value);
		addDataField(field);
	}

	/**
	 * @return java.lang.String
	 */
	public String toString()
	{
		return toString(0);
	}

	public String toString(int tabCount)
	{
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < tabCount; i++)
			buf.append("\t");

		buf.append("<kColl id=\"");
		buf.append(getName());
		buf.append("\" append=\"" + this.getAppend() + "\">\n");

		for (int i = 0; i < this.dataNames.size(); i++)
		{
			String dataName = (String) dataNames.get(i);
			DataElement element = (DataElement) this.dataElements.get(dataName);
			buf.append(element.toString((tabCount + 1)));
			buf.append("\n");
		}

		for (int i = 0; i < tabCount; i++)
			buf.append("\t");

		buf.append("</kColl>");

		return buf.toString();
	}

	/**
	 * @return int
	 */
	public int size()
	{
		if (this.dataNames == null)
			return 0;
		else
			return dataNames.size();
	}

	/**
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		if (dataNames == null || dataNames.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean containsKey(Object key)
	{
		if (this.dataElements == null)
			return false;
		else
			return dataElements.containsKey(key);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean containsValue(Object value)
	{
		if (this.dataElements == null)
			return false;
		else
			return dataElements.containsValue(value);
	}

	/**
	 * @param arg0
	 * @return java.lang.Object
	 */
	public Object get(Object key)
	{
		if (dataElements == null)
			return null;
		else
		{
			DataElement tmp = (DataElement) dataElements.get(key);
			if (tmp == null && this.isAppend())
			{
				tmp = new DataElement();
				tmp.setName(key + "");
				try
				{
					this.addDataElement(tmp);
				} catch (Exception ee)
				{
					LOGGER.error("get(key) auto Apeend dataElement in " + this.getName(), ee);
					return null;
				}
			}
			return tmp;
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return java.lang.Object
	 */
	public Object put(Object key, Object value)
	{
		try
		{
			this.addDataField((String) key, value);
		} catch (Exception e)
		{

		}
		return value;
	}

	/**
	 * @param arg0
	 * @return java.lang.Object
	 */
	public Object remove(Object key)
	{
		if (this.dataElements != null)
		{
			this.dataNames.remove(key);
			return dataElements.remove(key);
		}

		return null;
	}

	/**
	 * @param arg0
	 */
	public void putAll(Map mapValue)
	{

	}

	public void clear()
	{
		if (this.dataElements != null)
		{
			dataElements.clear();
			this.dataNames.clear();
		}
	}

	/**
	 * @return java.util.Set
	 */
	public Set keySet()
	{
		if (dataElements != null)
			return dataElements.keySet();
		return null;
	}

	/**
	 * @return java.util.Collection
	 */
	public Collection values()
	{
		if (dataElements != null)
			return dataElements.values();
		return null;
	}

	/**
	 * @return java.util.Set
	 */
	public Set entrySet()
	{
		if (dataElements != null)
			return dataElements.entrySet();

		return null;
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean equals(Object arg0)
	{
		return super.equals(arg0);
	}

	/**
	 * @return int
	 */
	public int hashCode()
	{
		return super.hashCode();
	}

	public void setAppend(String append)
	{
		super.setAppend(append);
		Iterator nameList = this.dataNames.iterator();
		while (nameList.hasNext())
		{
			((DataElement) this.dataElements.get((String) nameList.next())).setAppend(append);
		}
	}
}
