package cn.com.kk.flow;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * Action.java<br>
 * ҵ���߼��д�����Ľӿڶ���
 * 
 * @author tuqiang<br>
 * @since 2012-4-24<br>
 * 
 */
public interface Action {

	/**
	 * �߼��������ִ�����
	 * 
	 * @param context
	 * @return java.lang.String
	 */
	public String execute(Context context) throws HException;

	/**
	 * �����߼����������������
	 * 
	 * @return com.ecc.emp.transaction.EMPTransactionDef
	 */
//	public EMPTransactionDef getTransactionDef();

	public String getDestAction(Context context, String retValue);

	public String getDestAction(Context context, Exception exception);

	/**
	 * ȡ���ĸ�ʽ����
	 * 
	 * @param fmtId
	 * @return
	 */
//	public FormatElement getFormat(String fmtId);

	/**
	 * Action��ID
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * �õ���label�Ĳ���ȫ��
	 */
	public String getFullName();

	public String getLabel();

	public void setFlow(Flow flow);
}
