/**
 * ProtoStuffSerializer.java
 * zhm.rpc.serializer
 * 2017年12月18日下午9:48:52
 *
 */
package zhm.rpc.serializer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.alibaba.fastjson.JSON;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @author zhuheming ProtoStuffSerializer 2017年12月18日下午9:48:52
 * @param <T>
 */
public class ProtoStuffSerializer<T> implements ISerializer {

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

	private static Objenesis objenesis = new ObjenesisStd(true);

	private Class<T> socketClazz = null;

	public void setClass(Class<T> clazz) {
		this.socketClazz = clazz;
	}

	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			cachedSchema.put(clazz, schema);
		}
		return schema;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zhm.rpc.serializer.ISerializer#serialize(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> byte[] serialize(T obj) {
		// TODO Auto-generated method stub
		Class<T> clazz = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		Schema<T> schema = (Schema<T>) getSchema(clazz);
		return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zhm.rpc.serializer.ISerializer#deserialize(byte[], java.lang.Class)
	 */
	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		// TODO Auto-generated method stub
		// 先建一个空对象
		T obj = (T) objenesis.newInstance(clazz);
		Schema<T> schame = (Schema<T>) getSchema(clazz);
		// 给对象赋值
		ProtostuffIOUtil.mergeFrom(data, obj, schame);
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void send(Socket socket, Object obj) throws IOException {
		// TODO Auto-generated method stub
		// ByteArrayOutputStream ops = null;

		Class<T> clazz = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		Schema<T> schema = (Schema<T>) getSchema(clazz);
		// return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		try {
			// ops = new ByteArrayOutputStream(socket.getOutputStream());
			socket.getOutputStream().write(ProtostuffIOUtil.toByteArray((T) obj, schema, buffer));
			// ops.writeObject(obj);
			// ops.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// ops.flush();
			socket.getOutputStream().flush();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zhm.rpc.serializer.ISerializer#receive(java.net.Socket)
	 */
	@Override
	public Object receive(Socket socket) throws IOException {
		if (socketClazz == null) {
			throw new IOException("init Clazz First!");
		}
		byte[] b = null;
		socket.getInputStream().read(b);
		T obj = (T) objenesis.newInstance(socketClazz);
		Schema<T> schame = (Schema<T>) getSchema(socketClazz);
		// 给对象赋值
		ProtostuffIOUtil.mergeFrom(b, obj, schame);

		return obj;
	}

}
