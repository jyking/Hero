package cn.com.kk.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.kk.data.Context;
import cn.com.kk.exception.HException;
import cn.com.kk.factory.ComponentFactory;
import cn.com.kk.factory.FlowComponentFactory;
import cn.com.kk.flow.Flow;

/**
 * RequestContainer.java<br>
 * Hero框架容器
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class HeroContainer {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroContainer.class.getName());

	/**
	 * FLOW流程组件
	 */
	private FlowComponentFactory flowFactory;

	/**
	 * 容器初始化
	 * 
	 * @param factoryName
	 * @throws Exception
	 */
	public void initContainer(String factoryName, String fileName) throws Exception
	{
		// 1.0 初始化组件工厂
		FlowComponentFactory flowFactory = new FlowComponentFactory();
		flowFactory.initFlowComponentFactory(factoryName, fileName);
		ComponentFactory.addComponentFactory(flowFactory);
	}

	/**
	 * 执行FLOW流程
	 * 
	 * @param factoryName
	 * @param flowId
	 * @return
	 */
	public Object service(String flowId)
	{
		Object result = null; // 返回值
		boolean access = false; // 访问控制开关
		long beginTimeStamp = System.currentTimeMillis(); // 交易开始时间

		LOGGER.info("Began to call the [" + flowId + "] ...");

		Flow hFlow = this.flowFactory.getFlow(flowId);
		Context context = (Context) hFlow.getContext().clone();
		try
		{
			if (null == hFlow)
			{
				throw new HException("没有找到FLOW");
			}
			context.chainedTo(this.flowFactory.getRootContext());
			hFlow.modeUpdate(context);
			// 1.0 访问控制
			access = this.flowFactory.getAccessManage().checkAccess(context, flowId);
			if (access)
			{
				this.flowFactory.getAccessManage().beginAccess(context, flowId);
			}
			hFlow.execute(context);
			result = hFlow.getResult(context);
		} catch (HException ex)
		{
			// TODO: handle exception
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			if (access)
			{
				this.flowFactory.getAccessManage().endAccess(context, flowId, beginTimeStamp);
			}
			context.terminate();
		}

		LOGGER.info("End of the call [" + flowId + "] ...");

		return result;
	}

}
