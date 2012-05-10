package test;

import java.util.List;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import cn.com.kk.thrift.Interface;
import cn.com.kk.thrift.Interface.Client;
import cn.com.kk.thrift.User;
import cn.com.kk.thrift.UserNotFound;

public class MyClient extends Client {

	public MyClient(TProtocol prot) {
		super(prot);
	}

	public static void main(String[] args) {

		String address = "127.0.0.1";
		int port = 9900;
		int clientTimeout = 30000;
		TTransport transport = new TFramedTransport(new TSocket(address, port, clientTimeout));
		TProtocol protocol = new TCompactProtocol(transport);
		Interface.Client client = new Interface.Client(protocol);

		try {
			transport.open();
			List<User> sc = client.querySchoolUser("123");
		} catch (TApplicationException e) {
			System.out.println(e.getMessage() + " " + e.getType());
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} 
		transport.close();

	}
}
