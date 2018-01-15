/**
 * jdkDefaultSerializer.java
 * zhm.rpc.serializer
 * 2017年12月12日下午10:00:07
 *
 */
package zhm.rpc.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author zhuheming
 * jdkDefaultSerializer
 * 2017年12月12日下午10:00:07
 */
public class JdkDefaultSerializer implements ISerializer {

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#serialize(java.lang.Object)
	 */
	@Override
	public <T> byte[] serialize(T obj) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream ops;
		try {
			ops = new ObjectOutputStream(baos);
			ops.writeObject(obj);
			//ops.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#deserialize(byte[], java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		// TODO Auto-generated method stub
		ByteArrayInputStream bais=new ByteArrayInputStream(data);
		ObjectInputStream oos;
		try {
			oos=new ObjectInputStream(bais);
			return (T)oos.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public void send(Socket socket,Object obj) throws IOException {
		//ByteArrayOutputStream baos=new ByteArrayOutputStream(socket.getOutputStream());
		ObjectOutputStream ops = null;
		try {
			ops = new ObjectOutputStream(socket.getOutputStream());
			ops.writeObject(obj);
			//ops.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ops.flush();
		}
	}
	
	public Object receive(Socket socket) throws IOException{
		//ByteArrayOutputStream baos=new ByteArrayOutputStream(socket.getOutputStream());
		//ByteArrayInputStream bais=new ByteArrayInputStream(data);
		ObjectInputStream oos = null;
		try {
			oos=new ObjectInputStream(socket.getInputStream());
			return oos.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//oos.flush();
		}
		return oos;
	}

}
