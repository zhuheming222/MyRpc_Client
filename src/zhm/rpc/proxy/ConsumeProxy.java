/**
 * cosumeProxy.java
 * zhm.rpc.proxy
 * 2017��9��29������12:03:30
 *
 */
package zhm.rpc.proxy;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import zhm.rpc.connect.SocketConnect;
import zhm.rpc.ioc.BeanLoaderImpl;

/**
 * @author zhuheming
 * cosumeProxy
 * 2017��9��29������12:03:30
 */
public class ConsumeProxy {
	
	private static BeanLoaderImpl seriBean=new BeanLoaderImpl();
	
	//�����ڲ��෽��ʵ����Ҫ���ã�����host��port��ҪΪfinal
	@SuppressWarnings("unchecked")
	public static <T> T consume(Class<T> interfaceClass,final String host,final int port){
		
		//�Ƚ���һ��InvocationHandler�ӿڵĶ���ʵ���ڲ�������
		//InvocationHandler invocationHandler=;
		
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},new InvocationHandler(){
			//invoke����ڲ�������α����õ�  method ��args��δ��룬����ֻ�½������ö�Ӧ����ʱִ�С�
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
				//��������
				SocketConnect sc=new SocketConnect();
				Socket socket=sc.connect(host, port);
				//���ͷ����Ͳ���
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				ops.writeUTF(method.getName());
				ops.writeObject(args);
				//���շ���
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Object getObject=ois.readObject();
				return getObject;
			}
	});
	}
	
	/**
	 * 1.���ݴ���ĵڶ�������interfaces��̬����һ���࣬ʵ��interfaces�еĽӿڣ����Ҽ̳�Proxy�࣬��д��hashcode��toString,equals�ȷ���
	 * 2.����ĵ�һ������classloader�������ɵ�����ص�jvm��
	 * 3.���õ���������������$Proxy0��$Proxy0(InvocationHandler)���캯�� ����$Proxy0�Ķ���
	 *   ������interfaces�������������нӿڵķ�����������Method����method����ʼ������ļ���Method��Ա����args
	 * 4.invoke��Ҫʵ�֣���Ϊ��Ҫʹ��  Method����method��Method�ĳ�Ա����args��invoke�õ����Object
	 * 
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	public static <T> T consumeSerializer(final Class<T> interfaceClass,final String host,final int port){
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
				System.out.println("args Num:"+args.length);
				//��������
				System.out.println("socket connect");
				SocketConnect sc=new SocketConnect();
				Socket socket=sc.connect(host, port);
				//���ͷ����Ͳ���
				System.out.println("send object");
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				System.out.println("socket getSendBufferSize ");
				
				ops.writeUTF(method.getName());
				ops.flush();
				
				System.out.println("socket getSendBufferSize ");
				///
				seriBean.getDefaultBean().send(socket,args);
				///
				//���շ���
				System.out.println("receive object");
				Object getObject=seriBean.getDefaultBean().receive(socket);
//				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
//				ByteArrayOutputStream bos= new ByteArrayOutputStream();
//				int len=-1;
//				//���ֽ����黺�嵽�����
//				System.out.println("write buffer");
//				byte[] buffer=new byte[8192];
//				while((len=ois.read(buffer))!=-1){
//					bos.write(buffer, 0, len);
//					if(len<8192)break;
//				}
//				System.out.println("object deserialize");
//				Object getObject=seriBean.getDefaultBean().deserialize(bos.toByteArray(),interfaceClass);
//				
				return getObject;
			}
	});
	}
}
