package cn.com.kk.data;

/**
 * HConstants.java<br>
 * 系统常量类
 * 
 * @author tuqiang<br>
 * @since 2012-5-7<br>
 * 
 */
public class HConstants {

	private HConstants()
	{
		// 禁用构造函数
	}

	/**
	 * FACTORY XML配置
	 */
	public static class XML_Factory {
		/**
		 * 容器初始化节点
		 */
		public static final String ID_INIT = "initializer";

		/**
		 * 访问控制器节点
		 */
		public static final String ID_ACCESS = "accessManager";

		/**
		 * 实现类属性
		 */
		public static final String PROPERTY_CLASS = "class";

		/**
		 * 配置文件路径节点名称
		 */
		public static final String NODE_FILEPATH = "filePath";

		/**
		 * 节点值
		 */
		public static final String PROPERTY_VALUE = "value";

		/**
		 * FLOW 流程存放路径节点ID
		 */
		public static final String ID_OPERATIONS = "operations";
		
		/**
		 * XML ID属性
		 */
		public static final String PROPERTY_ID = "id";

	}

	/**
	 * CONTEXT XML配置
	 */
	public static class XML_Context {
		public static final String NODE_DATA = "data";

		public static final String NODE_KCOLL = "kColl";

		public static final String NODE_FIELD = "field";
		
		public static final String NODE_SERVER = "service";
	}

	/**
	 * HERO系统配置
	 * 
	 */
	public static class HSettings {
		/**
		 * data.xml文件配置ID
		 */
		public static final String SET_DATA = "data";

		/**
		 * services.xml文件配置
		 */
		public static final String SET_SERVER = "services";
	}

	/**
	 * BEAN注入XML常量
	 */
	public static class ImpClass {
		public static String SET_ATTR_METHOD = "setAttributes";
	}

}
