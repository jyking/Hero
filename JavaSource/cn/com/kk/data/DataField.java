package cn.com.kk.data;

public class DataField extends DataElement {
	public DataField()
	{
		required = false;
	}

	public DataField(String name)
	{
		super(name);
		required = false;
	}

	public DataField(String name, Object value)
	{
		super(name);
		required = false;
		setValue(value);
	}

	public void setDescription(String desc)
	{
		description = desc;
	}

	public void setDefaultValue(Object value)
	{
		defaultValue = value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getDescription()
	{
		return description;
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}

	public Object getValue()
	{
		return value;
	}

	public void setDataType(String type)
	{
		dataType = type;
	}

	public String getDataType()
	{
		return dataType;
	}

	public Object clone()
	{
		DataField field = new DataField();
		field.setName(getName());
		field.setValue(value);
		field.setDataType(dataType);
		field.setAppend(this.getAppend());
		return field;
	}

	public void reset()
	{
	}

	public String toString()
	{
		return toString(0);
	}

	public String toString(int tabCount)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < tabCount; i++)
			buf.append("\t");
		buf.append("<field id=\"");
		buf.append(getName());
		if (value != null)
		{
			buf.append("\" value=\"");
			buf.append(formatXML(value.toString()));
		}
		if (dataType != null)
		{
			buf.append("\" dataType=\"");
			buf.append(dataType);
		}
		buf.append("\"/>");
		return buf.toString();
	}

	private String formatXML(String str)
	{
		if (str == null || str.length() == 0)
		{
			return str;
		} else
		{
			String result = str;
			result = result.replaceAll("\\\'", "&apos;");
			result = result.replaceAll("\\\"", "&quot;");
			result = result.replaceAll("\\&", "&amp;");
			result = result.replaceAll("\\<", "&lt;");
			result = result.replaceAll("\\>", "&gt;");
			return result;
		}
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	private Object value;
	private Object defaultValue;
	private String description;
	private String dataType;
	private boolean required;
}