package cn.com.kk.xml.parser;

import org.w3c.dom.Node;

import cn.com.kk.factory.ComponentFactory;

/**
 * ServiceParser.java<br>
 * SERVICE解析器
 *
 * @author tuqiang<br>
 * @since 2012-5-10<br>
 * 
 */
public class ServiceParser extends ComponentParser {

	public ServiceParser(ComponentFactory factory)
	{
		super(factory);
		// TODO Auto-generated constructor stub
	}

	public T parseTheElement(Node elementNode) throws Exception
	{
		// TODO 返回服务实例
		return null;
	}

	protected boolean isInnerAttribute(String name)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
