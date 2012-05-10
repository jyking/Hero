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
 * Hero�������
 * 
 * @author tuqiang<br>
 * @since 2012-4-20<br>
 * 
 */
public class HeroContainer {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroContainer.class.getName());

	/**
	 * FLOW�������
	 */
	private FlowComponentFactory flowFactory;

	/**
	 * ������ʼ��
	 * 
	 * @param factoryName
	 * @throws Exception
	 */
	public void initContainer(String factoryName, String fileName) throws Exception
	{
		// 1.0 ��ʼ���������
		FlowComponentFactory flowFactory = new FlowComponentFactory();
		flowFactory.initFlowComponentFactory(factoryName, fileName);
		ComponentFactory.addComponentFactory(flowFactory);
	}

	/**
	 * ִ��FLOW����
	 * 
	 * @param factoryName
	 * @param flowId
	 * @return
	 */
	public Object service(String flowId)
	{
		Object result = null; // ����ֵ
		boolean access = false; // ���ʿ��ƿ���
		long beginTimeStamp = System.currentTimeMillis(); // ���׿�ʼʱ��

		LOGGER.info("Began to call the [" + flowId + "] ...");

		Flow hFlow = this.flowFactory.getFlow(flowId);
		Context context = (Context) hFlow.getContext().clone();
		try
		{
			if (null == hFlow)
			{
				throw new HException("û���ҵ�FLOW");
			}
			context.chainedTo(this.flowFactory.getRootContext());
			hFlow.modeUpdate(context);
			// 1.0 ���ʿ���
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
