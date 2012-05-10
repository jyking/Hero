package cn.com.kk.accessmanager;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * AccessManager.java<br>
 * ���ʿ�����
 *
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public interface AccessManager {

	/**
	 * ����Ƿ������ʿ���
	 * @param context
	 * @param flowId
	 * @return true ����
	 */
	public boolean checkAccess( Context context,String flowId );
	
	/**
	 * FLOWִ��֮ǰ
	 * @param context
	 */
	public void beginAccess( Context context,String flowId ) throws HException;
	
	/**
	 * FLOWִ��֮��
	 * @param context
	 * @param beginTimeStamp ���׿�ʼʱ��
	 */
	public void endAccess( Context context,String flowId,long beginTimeStamp );
}
