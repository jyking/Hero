package cn.com.kk.accessmanager;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * DefAccessManager.java<br>
 * 默认访问控制器
 * 默认不做任何控制
 *
 * @author tuqiang<br>
 * @since 2012-5-7<br>
 * 
 */
public class DefAccessManager implements AccessManager {

	@Override
	public boolean checkAccess(Context context, String flowId)
	{
		return false;
	}

	@Override
	public void beginAccess(Context context, String flowId) throws HException
	{
		
	}

	@Override
	public void endAccess(Context context, String flowId, long beginTimeStamp)
	{
		
	}

}
