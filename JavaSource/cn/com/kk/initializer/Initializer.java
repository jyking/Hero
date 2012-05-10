package cn.com.kk.initializer;

import cn.com.kk.factory.FlowComponentFactory;

/**
 * Initializer.java<br>
 * 组件工厂初始化接口
 * 
 * @author tuqiang<br>
 * @since 2012-4-25<br>
 * 
 */
public interface Initializer {
	public void initialize(FlowComponentFactory factory) throws Exception;
}
