package cn.com.kk.tools;

/**
 * HTools.java<br>
 * ϵͳ������
 * 
 * @author tuqiang<br>
 * @since 2012-5-8<br>
 * 
 */
public class HTools {

	/**
	 * ϵͳ����
	 */
	public static class Sys {
		/**
		 * ��ȡϵͳ��·��
		 * 
		 * @return
		 */
		public static String getUserDir()
		{
			return System.getProperty("user.dir");
		}

		/**
		 * ��ȡϵͳ�ļ��ָ���
		 * @return
		 */
		public static String getFileSeparator()
		{
			return System.getProperty("file.separator");
		}
	}
}
