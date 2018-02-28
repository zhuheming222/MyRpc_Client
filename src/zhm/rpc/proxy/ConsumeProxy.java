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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.regex.Pattern;

import zhm.rpc.base.ServerAddress;
import zhm.rpc.connect.SocketConnect;
import zhm.rpc.ioc.BeanLoaderImpl;

/**
 * @author zhuheming cosumeProxy 2017��9��29������12:03:30
 */
public class ConsumeProxy {
	
	private static final int PROVIDER_PORT=8989;
	private static final String PROVIDER_IP="127.0.0.1";
	

	private static BeanLoaderImpl seriBean = new BeanLoaderImpl();

	// �����ڲ��෽��ʵ����Ҫ���ã�����host��port��ҪΪfinal
	@SuppressWarnings("unchecked")
	public static <T> T consume(Class<T> interfaceClass, final String host, final int port) {

		// �Ƚ���һ��InvocationHandler�ӿڵĶ���ʵ���ڲ�������
		// InvocationHandler invocationHandler=;

		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
				new InvocationHandler() {
					// invoke����ڲ�������α����õ� method ��args��δ��룬����ֻ�½������ö�Ӧ����ʱִ�С�
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// ��������
						SocketConnect sc = new SocketConnect();
						Socket socket = sc.connect(host, port);
						// ���ͷ����Ͳ���
						ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
						ops.writeUTF(method.getName());
						ops.writeObject(args);
						// ���շ���
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						Object getObject = ois.readObject();
						return getObject;
					}
				});
	}

	/**
	 * 1.���ݴ���ĵڶ�������interfaces��̬����һ���࣬ʵ��interfaces�еĽӿڣ����Ҽ̳�Proxy�࣬��д��hashcode��toString,equals�ȷ���
	 * 2.����ĵ�һ������classloader�������ɵ�����ص�jvm��
	 * 3.���õ���������������$Proxy0��$Proxy0(InvocationHandler)���캯�� ����$Proxy0�Ķ���
	 * ������interfaces�������������нӿڵķ�����������Method����method����ʼ������ļ���Method��Ա����args
	 * 4.invoke��Ҫʵ�֣���Ϊ��Ҫʹ�� Method����method��Method�ĳ�Ա����args��invoke�õ����Object
	 * 
	 * 
	 **/
	@SuppressWarnings("unchecked")
	public static <T> T consumeSerializer(final Class<T> interfaceClass,final String serverName) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//���������method��ע�����õ������ip�Ͷ˿�
						ServerAddress sa = getAddress(getRegistryServerUrl("/"+serverName+"/"+method.getName()));
						if (sa.isUseful()) {
							System.out.println("args Num:" + args.length);
							// ��������
							System.out.println("socket connect");
							SocketConnect sc = new SocketConnect();
							Socket socket = sc.connect(sa.getServerIp(),sa.getServerPort());
							// ���ͷ����Ͳ���
							System.out.println("send object");
							ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
							System.out.println("socket getSendBufferSize ");

							ops.writeUTF(method.getName());
							ops.flush();

							System.out.println("socket getSendBufferSize ");
							seriBean.getDefaultBean().send(socket, args);
							// ���շ���
							System.out.println("receive object");
							Object getObject = seriBean.getDefaultBean().receive(socket);

							return getObject;
						}else{
							return null;
						}
					}
				});
	}

	/**
	 * ��ע�����õ������ip��port
	 **/
	public static String getRegistryServerUrl(String url) {

		if (url == null) {
			return null;
		}

		SocketConnect sc = new SocketConnect();
		Socket socket = sc.connect(PROVIDER_IP, PROVIDER_PORT);
		// ���ͷ����Ͳ���
		ObjectOutputStream ops;
		String getString = "";
		try {
			ops = new ObjectOutputStream(socket.getOutputStream());
			ops.writeUTF(url);
			ops.flush();
			// ���շ���
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			getString = ois.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getString;
	}

	/**
	 * �ַ�����ֳ�ip��port
	 **/
	public static ServerAddress getAddress(String addressStr) {
		ServerAddress sa = new ServerAddress();
		if (addressStr != null) {
			String[] addressList = addressStr.split(":");
			if (addressList.length > 1) {
				if (isNumeric(addressList[1])) {
					sa.setServerIp(addressList[0]);
					sa.setServerPort(Integer.parseInt(addressList[1]));
					return sa;
				}
			}
		}
		return null;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
