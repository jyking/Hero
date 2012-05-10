package cn.com.kk.tools;

/**
 * HTools.java<br>
 * 系统工具类
 * 
 * @author tuqiang<br>
 * @since 2012-5-8<br>
 * 
 */
public class HTools {

	/**
	 * 系统工具
	 */
	public static class Sys {
		/**
		 * 获取系统更路径
		 * 
		 * @return
		 */
		public static String getUserDir()
		{
			return System.getProperty("user.dir");
		}

		/**
		 * 获取系统文件分隔符
		 * @return
		 */
		public static String getFileSeparator()
		{
			return System.getProperty("file.separator");
		}
	}
}
