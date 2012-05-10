package cn.com.kk.initializer;

import cn.com.kk.factory.FlowComponentFactory;

/**
 * Initializer.java<br>
 * ���������ʼ���ӿ�
 * 
 * @author tuqiang<br>
 * @since 2012-4-25<br>
 * 
 */
public interface Initializer {
	public void initialize(FlowComponentFactory factory) throws Exception;
}
