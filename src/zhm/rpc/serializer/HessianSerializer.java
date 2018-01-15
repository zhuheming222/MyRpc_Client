/**
 * Hessian.java
 * zhm.rpc.serializer
 * 2017年12月18日下午10:31:15
 *
 */
package zhm.rpc.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * @author zhuheming
 * Hessian
 * 2017年12月18日下午10:31:15
 */
public class HessianSerializer implements ISerializer {

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#serialize(java.lang.Object)
	 */
	@Override
	public <T> byte[] serialize(T obj) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baops=new ByteArrayOutputStream();
		HessianOutput hop=new HessianOutput(baops);
		try {
			hop.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baops.toByteArray();
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#deserialize(byte[], java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		T obj = null;
		ByteArrayInputStream ois=new ByteArrayInputStream(data);
		HessianInput hip=new HessianInput(ois);
		try {
			obj= (T) hip.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#send(java.net.Socket, java.lang.Object)
	 */
	@Override
	public void send(Socket socket, Object obj) throws IOException{
		// TODO Auto-generated method stub
		//ByteArrayOutputStream baops=new ByteArrayOutputStream();
		HessianOutput hop=new HessianOutput(socket.getOutputStream());
		try {
			hop.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			hop.flush();
		}
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#receive(java.net.Socket)
	 */
	@Override
	public Object receive(Socket socket) throws IOException{
		// TODO Auto-generated method stub
		Object obj = null;
		HessianInput hip=new HessianInput(socket.getInputStream());
		try {
			obj= (Object) hip.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

}
