package cn.com.kk.data;

import cn.com.kk.exception.HException;
import cn.com.kk.thrift.User;

/**
 * Test.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-21<br>
 * 
 */
public class Test {
	public static void main(String[] args) throws HException
	{
		User us = new User();
		DataField field1 = new DataField("name");
		DataField field2 = new DataField("age");
		
		KeyedCollection kColl0 = new KeyedCollection("ICOLL数据模型");
		kColl0.addDataField(field1);
		kColl0.addDataField(field2);
		
		IndexedCollection iColl = new IndexedCollection();
		iColl.addDataElement(kColl0);
		
		KeyedCollection kColl = new KeyedCollection("context数据模型");
		kColl.addDataField(field1);
		kColl.addDataField(field2);
		kColl.addDataElement(iColl);
		System.out.println(kColl.getDataValue("age"));
		Context context = new Context("root");
		context.setDataElement(kColl);
		context.setDataValue("name", us);
//		context.addDataField("name", "张三1");
//		context.addDataField("age", "271");
		
		System.out.println(1);
	}
}
