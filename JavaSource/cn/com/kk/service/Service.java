package cn.com.kk.service;

/**
 * Service.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public interface Service {
	
	public void terminate();

	public String getName();

	public String getAlias();

	/**
	 * �Ƿ�Ψһʵ����
	 * 
	 * @return
	 */
	public boolean isSingleton();
}
