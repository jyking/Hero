package test.initializer;

import cn.com.kk.factory.FlowComponentFactory;
import cn.com.kk.initializer.Initializer;

/**
 * HeroInitializer.java<br>
 * ≤‚ ‘¿‡
 *
 * @author tuqiang<br>
 * @since 2012-5-9<br>
 * 
 */
public class HeroInitializer implements Initializer {

	@Override
	public void initialize(FlowComponentFactory factory) throws Exception
	{
		System.out.println("123");
	}

}
