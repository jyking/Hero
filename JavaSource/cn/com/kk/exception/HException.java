package cn.com.kk.exception;

/**
 * HException.java<br>
 * HEROϵͳ�е��쳣��װ����������HERO�쳣�ĸ��࣬�����ڲ����������Աcause���� ���������г����쳣ʱ�Ķ��η�װ�����ⶪʧ��Ҫ����Ϣ
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class HException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4842267182569970959L;

	/**
	 * ������, 20000 ϵͳ�ڲ�����
	 */
	private String errorCode = "20000";

	/**
	 * ��ʾ���û��Ĵ�����Ϣ�������Ƕ�������Դ�е���ԴID
	 */
	private String showMessage;

	/**
	 * ������쳣�������쳣��ͨ���ڶ�ϵͳ�������׳��쳣���ж��η�װʱʹ��
	 */
	private Throwable cause;

	public HException()
	{

	}

	public HException(String message)
	{
		super(message);
	}

	public HException(String errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public HException(String message, Throwable cause)
	{
		super(message, cause);
		this.cause = cause;

	}

	/**
	 * @param message
	 * @param cause
	 */
	public HException(String errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.errorCode = errorCode;
		this.cause = cause;

	}

	/**
	 * @param cause
	 */
	public HException(Throwable cause)
	{
		super(cause);
		this.cause = cause;
	}

	public String getMessage()
	{
		String msg = super.getMessage();

		String causeMsg = null;
		if (cause != null)
		{
			causeMsg = cause.getMessage();
		}

		if (msg != null)
		{
			if (causeMsg != null)
			{
				return msg + " caused by: " + causeMsg;
			} else
			{
				return msg;
			}
		} else
		{
			return causeMsg;
		}
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getShowMessage()
	{
		if (showMessage == null)
			return super.getMessage();

		return showMessage;
	}

	public void setShowMessage(String showMessage)
	{
		this.showMessage = showMessage;
	}

	/**
	 * @return java.lang.String
	 */
	public String toString()
	{
		if (cause == null)
		{
			return super.toString();
		} else
		{
			return super.toString() + " cause: " + cause.toString();
		}
	}

}
