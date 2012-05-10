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
	 * ����ģ�͸���
	 * @param context
	 */
	public void modeUpdate( Context context );
	
	/**
	 * ��ȡ���̷��ؽ��
	 * @return
	 */
	public Object getResult( Context context );
	
	/**
	 * ���̽���ʩ����Դ
	 */
	public void close();

	/**
	 * ִ��OP����
	 * @param context
	 * @return java.lang.String
	 */
	public String execute(Context context) throws Exception;

	/**
	 * ��ȡFLOW�µķ���
	 * @param serviceId
	 * @return
	 */
	public Service getService(String serviceId);

	/**
	 * ��ȡACTION����
	 * @param name
	 * @return
	 */
	public Action getAction(String name);

	/**
	 * ��ȡFLOW����
	 * @return
	 */
	public String getName();
	
	/**
	 * ��ȡFLOW��CONTEXTģ��
	 * @return
	 */
	public Context getContext();

}
