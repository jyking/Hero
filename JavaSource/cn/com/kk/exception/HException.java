package cn.com.kk.exception;

/**
 * HException.java<br>
 * HERO系统中的异常封装，他是所有HERO异常的父类，在它内部包含了类成员cause用于 对在运行中出现异常时的二次封装，避免丢失必要的信息
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
	 * 错误码, 20000 系统内部错误
	 */
	private String errorCode = "20000";

	/**
	 * 显示给用户的错误信息，可以是多语言资源中的资源ID
	 */
	private String showMessage;

	/**
	 * 引起此异常的真正异常，通常在对系统运行中抛出异常进行二次封装时使用
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
