package cn.com.kk.flow;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;

/**
 * Action.java<br>
 * 业务逻辑中处理步骤的接口定义
 * 
 * @author tuqiang<br>
 * @since 2012-4-24<br>
 * 
 */
public interface Action {

	/**
	 * 逻辑处理步骤地执行入口
	 * 
	 * @param context
	 * @return java.lang.String
	 */
	public String execute(Context context) throws HException;

	/**
	 * 返回逻辑处理步骤的事务声明
	 * 
	 * @return com.ecc.emp.transaction.EMPTransactionDef
	 */
//	public EMPTransactionDef getTransactionDef();

	public String getDestAction(Context context, String retValue);

	public String getDestAction(Context context, Exception exception);

	/**
	 * 取报文格式定义
	 * 
	 * @param fmtId
	 * @return
	 */
//	public FormatElement getFormat(String fmtId);

	/**
	 * Action的ID
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 得到带label的步骤全名
	 */
	public String getFullName();

	public String getLabel();

	public void setFlow(Flow flow);
}
