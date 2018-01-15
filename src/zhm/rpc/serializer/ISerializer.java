/**
 * ISerializer.java
 * zhm.rpc.serializer
 * 2017年12月12日下午9:33:47
 *
 */
package zhm.rpc.serializer;

import java.io.IOException;
import java.net.Socket;

/**
 * @author zhuheming
 * ISerializer
 * 2017年12月12日下午9:33:47
 */
public interface ISerializer {

	//序列化
	public <T> byte[] serialize(T obj);
	
	//反序列化
	public <T> T deserialize(byte[] data,Class<T> clazz);
	
	//发送
	public void send(Socket socket,Object obj)throws IOException ;
	
	//接收
	public Object receive(Socket socket)throws IOException ;
}
