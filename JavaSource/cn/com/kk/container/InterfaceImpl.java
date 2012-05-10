package cn.com.kk.container;

import java.util.List;

import org.apache.thrift.TException;

import cn.com.kk.data.Context;
import cn.com.kk.factory.ComponentFactory;
import cn.com.kk.factory.FlowComponentFactory;
import cn.com.kk.flow.Flow;
import cn.com.kk.thrift.User;
import cn.com.kk.thrift.UserNotFound;
import cn.com.kk.thrift.school;
import cn.com.kk.thrift.Interface.Iface;

/**
 * InterfaceImpl.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-18<br>
 * 
 */
public class InterfaceImpl extends HeroContainer implements Iface {

	@Override
	public User queryUser(String userName) throws UserNotFound, TException
	{
		System.out.println("开始查询" + userName + "的信息...");
		User user = new User();
		user.name = "张小三";
		UserNotFound ex = new UserNotFound();
		ex.code = "ECBC0001";
		ex.msg = "没有这个人";
		//throw ex;
		if("张小三".equals(userName)){
			return user;
		}else{
			throw ex;
		}
	}

	@Override
	public List<User> querySchoolUser(String addres) throws TException
	{
		return (List<User>) super.service("flowId");
	}

	@Override
	public String addUser(List<User> users) throws TException
	{
		return null;
	}

	@Override
	public school querySchool(String num) throws TException
	{
		school sc = new school();
		sc.user = new User();
		sc.addres = "123";
		sc.user.name = "张三";
		return sc;
	}

}
