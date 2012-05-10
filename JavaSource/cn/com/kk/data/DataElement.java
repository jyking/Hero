package cn.com.kk.data;

/**
 * DataElement.java<br>
 * 数据元素定义基类
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class DataElement {
	private String name;

	private String append = "false";

	public DataElement()
	{
		super();
	}

	public DataElement(String name)
	{
		super();
		this.name = name;
	}

	/**
	 * @return Object
	 */
	public Object clone()
	{
		DataElement element = new DataElement(name);
		element.setAppend(this.getAppend());
		return element;
	}

	public void reset()
	{

	}

	/**
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setId(String id)
	{
		name = id;
	}

	/**
	 * @return java.lang.String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @since 2006-09-05
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
		buf.append("<DataElement id=\"");
		buf.append(name);
		buf.append("\" append=\"" + this.getAppend() + "\"/>");
		return buf.toString();
	}

	public boolean isAppend()
	{
		if (append != null)
		{
			return append.equalsIgnoreCase("true");
		} else
		{
			return false;
		}
	}

	public void setAppend(String append)
	{
		this.append = append;
	}

	public String getAppend()
	{
		return this.append;
	}

	public void setDynamic(String append)
	{
		setAppend(append);
	}

}
