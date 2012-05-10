package cn.com.kk.accessmanager;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * AccessManager.java<br>
 * 访问控制器
 *
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public interface AccessManager {

	/**
	 * 检查是否开启访问控制
	 * @param context
	 * @param flowId
	 * @return true 开启
	 */
	public boolean checkAccess( Context context,String flowId );
	
	/**
	 * FLOW执行之前
	 * @param context
	 */
	public void beginAccess( Context context,String flowId ) throws HException;
	
	/**
	 * FLOW执行之后
	 * @param context
	 * @param beginTimeStamp 交易开始时间
	 */
	public void endAccess( Context context,String flowId,long beginTimeStamp );
}
