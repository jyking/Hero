package cn.com.kk.xml.parser;

import java.util.Map;

/**
 * T.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-5-10<br>
 * 
 */
public class T {
	
	private String user;
	
	private String age;
	
	private String school;

	private Map<String,String> m;
	
	public Map<String, String> getM()
	{
		return m;
	}

	public void setM(Map<String, String> m)
	{
		this.m = m;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getSchool()
	{
		return school;
	}

	public void setSchool(String school)
	{
		this.school = school;
	}
}
