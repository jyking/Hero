package cn.com.kk.flow;

import cn.com.kk.data.Context;
import cn.com.kk.service.Service;

/**
 * Flow.java<br>
 * 
 * @author tuqiang<br>
 * @since 2012-4-24<br>
 * 
 */
public interface Flow {

	/**
	 * 数据模型更新
	 * @param context
	 */
	public void modeUpdate( Context context );
	
	/**
	 * 获取流程返回结果
	 * @return
	 */
	public Object getResult( Context context );
	
	/**
	 * 流程结束施放资源
	 */
	public void close();

	/**
	 * 执行OP流程
	 * @param context
	 * @return java.lang.String
	 */
	public String execute(Context context) throws Exception;

	/**
	 * 获取FLOW下的服务
	 * @param serviceId
	 * @return
	 */
	public Service getService(String serviceId);

	/**
	 * 获取ACTION流程
	 * @param name
	 * @return
	 */
	public Action getAction(String name);

	/**
	 * 获取FLOW名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 获取FLOW的CONTEXT模型
	 * @return
	 */
	public Context getContext();

}
