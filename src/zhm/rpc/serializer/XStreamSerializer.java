/**
 * xStreamSerializer.java
 * zhm.rpc.serializer
 * 2017年12月12日下午10:44:20
 *
 */
package zhm.rpc.serializer;

import java.io.IOException;
import java.net.Socket;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author zhuheming
 * xStreamSerializer
 * 2017年12月12日下午10:44:20
 */
public class XStreamSerializer implements ISerializer {

	private static XStream xStream = new XStream(new DomDriver("UTF-8"));
	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#serialize(java.lang.Object)
	 */
	@Override
	public <T> byte[] serialize(T obj) {		
        return xStream.toXML(obj).getBytes();
		//return null;
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#deserialize(byte[], java.lang.Class)
	 */
	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		// TODO Auto-generated method stub
		return (T) xStream.fromXML(data.toString());
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#send(java.net.Socket, java.lang.Object)
	 */
	@Override
	public void send(Socket socket, Object obj)throws IOException {
		// TODO Auto-generated method stub
		//ByteArrayOutputStream ops = null;
		try {
			//ops = new ByteArrayOutputStream(socket.getOutputStream());
			socket.getOutputStream().write(xStream.toXML(obj).getBytes());
			//ops.writeObject(obj);
			//ops.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//ops.flush();
			socket.getOutputStream().flush();
		}
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#receive(java.net.Socket)
	 */
	@Override
	public Object receive(Socket socket)throws IOException {
		byte[] b=null;
		socket.getInputStream().read(b);
		return (Object) xStream.fromXML(b.toString());
	}

}
