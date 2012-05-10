package cn.com.kk.exception;

/**
 * InvalidArgumentException.java<br>
 * �Ƿ������쳣����
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class InvalidArgumentException extends HException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -11361666026724035L;

	public InvalidArgumentException()
	{
		super();
	}

	public InvalidArgumentException(String message)
	{
		super(message);
	}

}
