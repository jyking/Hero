package cn.com.kk.exception;

/**
 * ObjectNotFoundException.java<br>
 * �����Ҳ����쳣����
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class ObjectNotFoundException extends HException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8432372223858630593L;

	public ObjectNotFoundException()
	{
		super();
	}

	public ObjectNotFoundException(String message)
	{
		super(message);
	}
}
