package cn.com.kk.exception;

/**
 * DuplicatedDataNameException.java<br>
 * ���ݶ������ظ��쳣
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class DuplicatedDataNameException extends HException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1276429429796083206L;

	public DuplicatedDataNameException()
	{
		super();
	}

	public DuplicatedDataNameException(String message)
	{
		super(message);
	}

}
