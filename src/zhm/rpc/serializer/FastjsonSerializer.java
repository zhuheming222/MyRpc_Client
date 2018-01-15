/**
 * FastjsonSerializer.java
 * zhm.rpc.serializer
 * 2017年12月12日下午11:17:11
 *
 */
package zhm.rpc.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @author zhuheming
 * FastjsonSerializer
 * 2017年12月12日下午11:17:11
 */
public class FastjsonSerializer implements ISerializer {
	
	private static final String charsetName="UTF-8";

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#serialize(java.lang.Object)
	 */
	@Override
	public <T> byte[] serialize(T obj) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(obj).getBytes();
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.serializer.ISerializer#deserialize(byte[], java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		// TODO Auto-generated method stub		
		return (T) JSON.parse(data.toString());
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
			socket.getOutputStream().write(JSON.toJSONString(obj).getBytes());
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
	 * 考虑使用ArrayList<Byte>方式存放byte[]，并且重写InputStream的read方法，避免循环byte[]的处理方式
	 */
	@Override
	public Object receive(Socket socket)throws IOException {
		byte[] b=new byte[100];
		//List<Byte> b1= new ArrayList();
		//(Byte[])b1.toArray()
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int rc=0;
		while((rc=socket.getInputStream().read(b,0,100))>0){
			baos.write(b, 0, rc);
			if(rc<100)break;
		}
		return (Object) JSON.parse(baos.toString(charsetName));
	}
}
