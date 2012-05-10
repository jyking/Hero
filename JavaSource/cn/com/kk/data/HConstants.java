package cn.com.kk.data;

/**
 * HConstants.java<br>
 * ϵͳ������
 * 
 * @author tuqiang<br>
 * @since 2012-5-7<br>
 * 
 */
public class HConstants {

	private HConstants()
	{
		// ���ù��캯��
	}

	/**
	 * FACTORY XML����
	 */
	public static class XML_Factory {
		/**
		 * ������ʼ���ڵ�
		 */
		public static final String ID_INIT = "initializer";

		/**
		 * ���ʿ������ڵ�
		 */
		public static final String ID_ACCESS = "accessManager";

		/**
		 * ʵ��������
		 */
		public static final String PROPERTY_CLASS = "class";

		/**
		 * �����ļ�·���ڵ�����
		 */
		public static final String NODE_FILEPATH = "filePath";

		/**
		 * �ڵ�ֵ
		 */
		public static final String PROPERTY_VALUE = "value";

		/**
		 * FLOW ���̴��·���ڵ�ID
		 */
		public static final String ID_OPERATIONS = "operations";
		
		/**
		 * XML ID����
		 */
		public static final String PROPERTY_ID = "id";

	}

	/**
	 * CONTEXT XML����
	 */
	public static class XML_Context {
		public static final String NODE_DATA = "data";

		public static final String NODE_KCOLL = "kColl";

		public static final String NODE_FIELD = "field";
		
		public static final String NODE_SERVER = "service";
	}

	/**
	 * HEROϵͳ����
	 * 
	 */
	public static class HSettings {
		/**
		 * data.xml�ļ�����ID
		 */
		public static final String SET_DATA = "data";

		/**
		 * services.xml�ļ�����
		 */
		public static final String SET_SERVER = "services";
	}

	/**
	 * BEANע��XML����
	 */
	public static class ImpClass {
		public static String SET_ATTR_METHOD = "setAttributes";
	}

}
