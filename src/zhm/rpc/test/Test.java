package zhm.rpc.test;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import zhm.rpc.base.ServerAddress;
import zhm.rpc.base.XmlDocumentHolder;
import zhm.rpc.connect.SocketConnect;
import zhm.rpc.proxy.ConsumeProxy;
import zhm.rpc.server.IServer;

public class Test {
	
	//远程调用服务端的testMethod方法，客户端只有接口，没有方法实现。
	public static void main(String args[]) throws InterruptedException{
		//ServerAddress serverAddress=new ServerAddress();
		
		Object obj= ConsumeProxy.consumeSerializer(IServer.class,"SERVER1");
		if(obj!=null){
			System.out.println(obj.getClass().getInterfaces().toString());
			//System.out.println(obj.toString());
			System.out.println("not null!");
		}else{
			System.out.println("null!");
		}
		IServer rpc=(IServer)obj;
		
		for(int i=0;i<100;i++){
			
			System.out.println(rpc.testMethod(""+i));
			Thread.sleep(1000);
		}
	}
	


}
