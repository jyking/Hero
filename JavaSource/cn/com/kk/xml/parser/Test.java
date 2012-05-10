package cn.com.kk.xml.parser;

import java.lang.reflect.Method;

/**
 * Test.java<br>
 *
 * @author tuqiang<br>
 * @since 2012-5-10<br>
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception
	{
		T t = new T();
		String setMethod = "setUser";
		String setMethod1 = "setM";
		Method st = t.getClass().getMethod(setMethod1, new Class[]{String.class});
		st.invoke(t, new Object[]{"1"});
		System.out.println(1);
	}
}
