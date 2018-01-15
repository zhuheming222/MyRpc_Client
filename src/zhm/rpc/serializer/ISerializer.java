/**
 * ISerializer.java
 * zhm.rpc.serializer
 * 2017��12��12������9:33:47
 *
 */
package zhm.rpc.serializer;

import java.io.IOException;
import java.net.Socket;

/**
 * @author zhuheming
 * ISerializer
 * 2017��12��12������9:33:47
 */
public interface ISerializer {

	//���л�
	public <T> byte[] serialize(T obj);
	
	//�����л�
	public <T> T deserialize(byte[] data,Class<T> clazz);
	
	//����
	public void send(Socket socket,Object obj)throws IOException ;
	
	//����
	public Object receive(Socket socket)throws IOException ;
}
