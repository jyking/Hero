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
	 * 是否唯一实例化
	 * 
	 * @return
	 */
	public boolean isSingleton();
}
