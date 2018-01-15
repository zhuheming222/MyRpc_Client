/**
 * cosumeProxy.java
 * zhm.rpc.proxy
 * 2017年9月29日上午12:03:30
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
 * 2017年9月29日上午12:03:30
 */
public class ConsumeProxy {
	
	private static BeanLoaderImpl seriBean=new BeanLoaderImpl();
	
	//匿名内部类方法实现需要调用，所以host和port需要为final
	@SuppressWarnings("unchecked")
	public static <T> T consume(Class<T> interfaceClass,final String host,final int port){
		
		//先建立一个InvocationHandler接口的对象（实现内部方法）
		//InvocationHandler invocationHandler=;
		
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},new InvocationHandler(){
			//invoke这个内部类是如何被调用的  method 和args如何传入，这里只新建，调用对应方法时执行。
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
				//建立连接
				SocketConnect sc=new SocketConnect();
				Socket socket=sc.connect(host, port);
				//发送方法和参数
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				ops.writeUTF(method.getName());
				ops.writeObject(args);
				//接收返回
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Object getObject=ois.readObject();
				return getObject;
			}
	});
	}
	
	/**
	 * 1.根据传入的第二个参数interfaces动态生成一个类，实现interfaces中的接口，并且继承Proxy类，重写了hashcode，toString,equals等方法
	 * 2.传入的第一个参数classloader将刚生成的类加载到jvm中
	 * 3.利用第三个参数，调用$Proxy0的$Proxy0(InvocationHandler)构造函数 创建$Proxy0的对象，
	 *   并且用interfaces参数遍历其所有接口的方法，并生成Method对象method，初始化对象的几个Method成员变量args
	 * 4.invoke需要实现，因为需要使用  Method对象method，Method的成员变量args来invoke得到结果Object
	 * 
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	public static <T> T consumeSerializer(final Class<T> interfaceClass,final String host,final int port){
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
				System.out.println("args Num:"+args.length);
				//建立连接
				System.out.println("socket connect");
				SocketConnect sc=new SocketConnect();
				Socket socket=sc.connect(host, port);
				//发送方法和参数
				System.out.println("send object");
				ObjectOutputStream ops=new ObjectOutputStream(socket.getOutputStream());
				System.out.println("socket getSendBufferSize ");
				
				ops.writeUTF(method.getName());
				ops.flush();
				
				System.out.println("socket getSendBufferSize ");
				///
				seriBean.getDefaultBean().send(socket,args);
				///
				//接收返回
				System.out.println("receive object");
				Object getObject=seriBean.getDefaultBean().receive(socket);
//				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
//				ByteArrayOutputStream bos= new ByteArrayOutputStream();
//				int len=-1;
//				//将字节数组缓冲到输出流
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
