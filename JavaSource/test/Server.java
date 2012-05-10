package test;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.kk.container.InterfaceImpl;
import cn.com.kk.thrift.Interface;

/**
 * Server.java<br>
 *
 * @author tuqiang<br>
 * @since 2012-4-19<br>
 * 
 */
public class Server {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TSimpleServer.class.getName());

	//日志初始化
	static
	{
		DOMConfigurator.configure(System.getProperty("user.dir")+"\\HeroContent\\config\\logging.xml");
	}
	
	public void start1(){

		try {
			TNonblockingServerSocket socket  =new TNonblockingServerSocket(9901);
			InterfaceImpl impl = new InterfaceImpl();
			impl.initContainer("factory2",System.getProperty("user.dir")+"\\HeroContent\\config\\hero.xml");
			Interface.Processor<Interface.Iface> processor = new Interface.Processor<Interface.Iface>(impl);
			THsHaServer.Args arg = new THsHaServer.Args(socket);
			arg.protocolFactory(new TCompactProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			
			TServer server = new THsHaServer(arg);
			LOGGER.error("start on 9901 ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	private void start(){
		try {
			TNonblockingServerSocket socket  =new TNonblockingServerSocket(9900);
			InterfaceImpl impl = new InterfaceImpl();
			impl.initContainer("factory1","\\HeroContent\\config\\hero.xml");
			Interface.Processor<Interface.Iface> processor = new Interface.Processor<Interface.Iface>(impl);
			THsHaServer.Args arg = new THsHaServer.Args(socket);
			arg.protocolFactory(new TCompactProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			
			TServer server = new THsHaServer(arg);
			LOGGER.error("start on 9900 ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server().start();
	}

}
