package cn.com.kk.xml.parser;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import cn.com.kk.factory.ComponentFactory;

/**
 * ComponentParser.java<br>
 * 组件解析器 负责将XML组装为JAVA BEAN
 * 
 * @author tuqiang<br>
 * @since 2012-5-9<br>
 * 
 */
public abstract class ComponentParser {

	protected ComponentFactory componentFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponentParser.class.getName());

	public ComponentParser(ComponentFactory factory)
	{
		this.componentFactory = factory;
	}

	/**
	 * 将elementNode 实例化为JAVA BEAN
	 * 
	 * @param document
	 * @param elementNode
	 * @return
	 * @throws Exception
	 */
	public abstract Object parseTheElement(Node elementNode) throws Exception;

	/**
	 * 将elementNode中定义的Attributes设定到bean中
	 * 
	 * @param bean
	 * @param elementNode
	 */
	public void setBeanAttributes(Object bean, Node elementNode)
	{
		NamedNodeMap attrs = elementNode.getAttributes();

		// 逐个查找setter 方法设定Bean的属性值
		for (int i = 0; i < attrs.getLength(); i++)
		{
			Node attr = attrs.item(i);
			String name = attr.getNodeName();
			String value = attr.getNodeValue();

			if (!isInnerAttribute(name))
			{
				this.setBeanProperty(bean, name, value);
			}
		}

	}

	/**
	 * 判断属性名称是否为内部使用的属性
	 * 
	 * @param name
	 * @return
	 */
	protected abstract boolean isInnerAttribute(String name);

	/**
	 * 查找Bean的相应的Setter方法，将Value通过相应的PropertyConverter转化到适当的类型， 设定到Bean中
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 */
	protected void setBeanProperty(Object bean, String name, Object value)
	{
		String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
		// 尝试以String作为入参设置
		try
		{
			Method setterMethod = bean.getClass().getMethod(methodName, new Class[]
			{ String.class });
			if (null != setterMethod)
			{
				setterMethod.invoke(bean, new Object[]
				{ value });
				LOGGER.info("Invok bean " + bean.getClass().getName() + " 's methos " + setterMethod.getName() + " with " + value);
				return;
			}
		} catch (Exception e)
		{
			LOGGER.error("Invok bean " + bean.getClass().getName() + " 's methos " + methodName + " with " + value + "failed!", e);
		}

		// 将Value通过相应的PropertyConverter转化到适当的类型设置
		try
		{
			Method[] methods = bean.getClass().getMethods();
			for (int i = 0; i < methods.length; i++)
			{
				if (methods[i].getName().equals(methodName))
				{
					Class<?>[] parameterTypes = methods[i].getParameterTypes();
					// SET方法参数只能有一个
					if (parameterTypes.length != 1)
					{
						continue;
					}

					if (!(parameterTypes[0].equals(value.getClass())))
					{
						PropertyEditor editor = PropertyEditorManager.findEditor(parameterTypes[0]);
						if (editor != null)
						{
							if (String.class.isAssignableFrom(value.getClass()))
							{
								editor.setAsText((String) value);
							} else
							{
								editor.setValue(value);
							}

							methods[i].invoke(bean, new Object[]
							{ editor.getValue() });
							LOGGER.info("Invok bean " + bean.getClass().getName() + " 's methos " + methods[i].getName()
									+ " with " + value);
							return;
						}
					} else
					{
						methods[i].invoke(bean, new Object[]
						{ value });
						LOGGER.info("Invok bean " + bean.getClass().getName() + " 's methos " + methods[i].getName() + " with "
								+ value);
						return;
					}
				}
			}
			LOGGER.error("Failed to set Bean [" + bean.getClass().getName() + "] 's property [" + name + "] value [" + value
					+ "]!");
		} catch (Exception e)
		{
			LOGGER.error("Failed to set Bean [" + bean.getClass().getName() + "] 's property [" + name + "] value [" + value
					+ "]!");
		}

	}
}
