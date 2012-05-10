package cn.com.kk.xml.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cn.com.kk.factory.ComponentFactory;
import cn.com.kk.flow.Flow;

/**
 * FlowParser.java<br>
 * FLOW组件解析器
 * 
 * @author tuqiang<br>
 * @since 2012-5-9<br>
 * 
 */
public class FlowParser extends ComponentParser {

	public FlowParser(ComponentFactory factory)
	{
		super(factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object parseTheElement(Node elementNode) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Flow createFlow(Document doc)
	{
		// TODO 创建FLOW
		return null;
	}

	@Override
	protected boolean isInnerAttribute(String name)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
