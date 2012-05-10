package cn.com.kk.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IndexedCollection.java<br>
 * 多条数据集合的实现类，代表数组类型的数据定义，数组中的每条记录又是 数据结构数据。
 * 
 * @author tuqiang<br>
 * @since 2012-4-21<br>
 * 
 */
public class IndexedCollection extends DataElement implements List {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexedCollection.class.getName());

	/**
	 * 数据记录定义
	 */
	private DataElement dataElement;

	/**
	 * 数组中的数据列表
	 */
	private List datas;

	public IndexedCollection()
	{
		super();
		datas = new ArrayList();
	}

	public IndexedCollection(String name)
	{
		super(name);
		datas = new ArrayList();
	}

	/**
	 * 取得第idx条数据记录（从0开始）
	 * 
	 * @param idx
	 * @return cn.com.kk.core.DataElement
	 */
	public DataElement getElementAt(int idx)
	{
		if (this.isAppend() && idx >= datas.size())
		{
			Class c = cn.com.kk.data.KeyedCollection.class;
			if (datas.size() > 0)
			{
				c = datas.get(0).getClass();
			}
			try
			{
				DataElement tmp = (DataElement) c.newInstance();
				tmp.setAppend(this.getAppend());
				while (datas.size() <= idx)
				{
					datas.add(tmp.clone());
				}
			} catch (Exception e)
			{
				LOGGER.error("getElementAt(idx) auto Apeend dataElement in " + this.getName(), e);
				return null;
			}
		}
		return (DataElement) datas.get(idx);
	}

	/**
	 * 数据记录条数
	 * 
	 * @return int
	 */
	public int size()
	{
		return datas.size();
	}

	/**
	 * 删除第idx条数据
	 * 
	 * @param idx
	 * @return cn.com.kk.core.DataElement
	 */
	public DataElement removeElementAt(int idx)
	{
		if (idx > datas.size())
			return null;

		return (DataElement) datas.remove(idx);
	}

	/**
	 * 清除所有数据记录
	 * 
	 */
	public void removeAll()
	{
		datas.clear();
	}

	/**
	 * 添加数据记录
	 * 
	 * @param element
	 */
	public void addDataElement(DataElement element)
	{
		if (this.isAppend())
		{
			element.setAppend(this.getAppend());
		}
		datas.add(element);
	}

	/**
	 * 设定数据记录定义
	 * 
	 * @param element
	 */
	public void setDataElement(DataElement element)
	{
		element.setAppend(this.getAppend());
		this.dataElement = element;
	}

	/**
	 * 取数据记录定义
	 * 
	 * @return
	 */
	public DataElement getDataElement()
	{
		return dataElement;
	}

	/**
	 * @return Object
	 */
	public Object clone()
	{
		IndexedCollection iColl = new IndexedCollection(getName());
		iColl.setDataElement(dataElement);
		iColl.setAppend(this.getAppend());
		for (int i = 0; i < datas.size(); i++)
		{
			DataElement element = (DataElement) datas.get(i);
			iColl.addDataElement((DataElement) element.clone());
		}
		return iColl;
	}

	public void reset()
	{

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
		buf.append("<iColl id=\"");
		buf.append(getName());
		buf.append("\" append=\"" + this.getAppend() + "\">\n");

		if (datas.size() == 0)
		{
			if (dataElement != null)
			{
				buf.append(dataElement.toString((tabCount + 1)));
				buf.append("\n");
			}
		} else
		{
			for (int i = 0; i < datas.size(); i++)
			{
				DataElement element = (DataElement) datas.get(i);
				buf.append(element.toString(tabCount + 1));
				buf.append("\n");
				if (i > 10)
				{
					for (int k = 0; k < tabCount; k++)
						buf.append("\t");
					buf.append("more datas... size=" + datas.size());
					buf.append("\n");
					break;
				}
			}
		}

		for (int i = 0; i < tabCount; i++)
			buf.append("\t");
		buf.append("</iColl>");

		return buf.toString();

	}

	/**
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		return datas.isEmpty();
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean contains(Object arg0)
	{
		return datas.contains(arg0);
	}

	/**
	 * @return java.util.Iterator
	 */
	public Iterator iterator()
	{
		return datas.iterator();
	}

	/**
	 * @return java.lang.Object[]
	 */
	public Object[] toArray()
	{
		return datas.toArray();
	}

	/**
	 * @param arg0
	 * @return java.lang.Object[]
	 */
	public Object[] toArray(Object[] arg0)
	{
		return datas.toArray(arg0);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean add(Object arg0)
	{
		if (arg0 instanceof DataElement)
		{
			((DataElement) arg0).setAppend(this.getAppend());
		}
		return datas.add(arg0);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean remove(Object arg0)
	{
		return datas.remove(arg0);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean containsAll(Collection arg0)
	{
		return datas.containsAll(arg0);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean addAll(Collection arg0)
	{
		Iterator list = arg0.iterator();
		while (list.hasNext())
		{
			Object arg = list.next();
			if (arg instanceof DataElement)
			{
				((DataElement) arg).setAppend(this.getAppend());
			}
		}
		return datas.addAll(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return boolean
	 */
	public boolean addAll(int arg0, Collection arg1)
	{
		Iterator list = arg1.iterator();
		while (list.hasNext())
		{
			Object arg = list.next();
			if (arg instanceof DataElement)
			{
				((DataElement) arg).setAppend(this.getAppend());
			}
		}
		return datas.addAll(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean removeAll(Collection arg0)
	{
		return datas.removeAll(arg0);
	}

	/**
	 * @param arg0
	 * @return boolean
	 */
	public boolean retainAll(Collection arg0)
	{
		return datas.retainAll(arg0);
	}

	public void clear()
	{
		datas.clear();
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

	/**
	 * @param arg0
	 * @return java.lang.Object
	 */
	public Object get(int arg0)
	{
		if (this.isAppend() && arg0 >= datas.size())
		{
			Class c = cn.com.kk.data.KeyedCollection.class;
			if (datas.size() > 0)
			{
				c = datas.get(0).getClass();
			}
			try
			{
				DataElement tmp = (DataElement) c.newInstance();
				tmp.setAppend(this.getAppend());
				while (datas.size() <= arg0)
				{
					datas.add(tmp.clone());
				}
			} catch (Exception e)
			{
				LOGGER.error("get(idx) auto Apeend dataElement in " + this.getName(), e);
				return null;
			}
		}
		return datas.get(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return java.lang.Object
	 */
	public Object set(int arg0, Object arg1)
	{
		if (arg1 instanceof DataElement)
		{
			((DataElement) arg1).setAppend(this.getAppend());
		}
		return datas.set(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public void add(int arg0, Object arg1)
	{
		if (arg1 instanceof DataElement)
		{
			((DataElement) arg1).setAppend(this.getAppend());
		}
		datas.add(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return java.lang.Object
	 */
	public Object remove(int arg0)
	{
		return datas.remove(arg0);
	}

	/**
	 * @param arg0
	 * @return int
	 */
	public int indexOf(Object arg0)
	{
		return datas.indexOf(arg0);
	}

	/**
	 * @param arg0
	 * @return int
	 */
	public int lastIndexOf(Object arg0)
	{
		return datas.lastIndexOf(arg0);
	}

	/**
	 * @return java.util.ListIterator
	 */
	public ListIterator listIterator()
	{
		return datas.listIterator();
	}

	/**
	 * @param arg0
	 * @return java.util.ListIterator
	 */
	public ListIterator listIterator(int arg0)
	{
		return datas.listIterator(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return java.util.List
	 */
	public List subList(int arg0, int arg1)
	{
		return datas.subList(arg0, arg1);
	}

	public void setAppend(String append)
	{
		super.setAppend(append);
		for (int i = 0; i < datas.size(); i++)
		{
			if (datas.get(i) instanceof DataElement)
			{
				((DataElement) datas.get(i)).setAppend(append);
			}
		}
	}
}
