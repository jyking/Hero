package test.access;

import cn.com.kk.accessmanager.AccessManager;
import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * HeroAccessManager.java<br>
 *
 * @author tuqiang<br>
 * @since 2012-5-9<br>
 * 
 */
public class HeroAccessManager implements AccessManager {

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
