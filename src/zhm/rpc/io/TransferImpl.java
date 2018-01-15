/**
 * TransferImpl.java
 * zhm.rpc.io
 * 2018年1月10日下午4:12:47
 *
 */
package zhm.rpc.io;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author zhuheming
 * TransferImpl
 * 2018年1月10日下午4:12:47
 */
public class TransferImpl implements ITransfer {

	/* (non-Javadoc)
	 * @see zhm.rpc.io.ITransfer#send(java.net.Socket, java.lang.Object)
	 */
	@Override
	public void send(Socket socket, Object obj) {
		// TODO Auto-generated method stub
		//System.out.println("send method"+obj.getName());
		//ops.writeObject(args);
		//DataOutputStream baps=new DataOutputStream(socket.getOutputStream());
		//object-->byteArray-->Data
		//baps.write(seriBean.getDefaultBean().serialize(args));
	}

	/* (non-Javadoc)
	 * @see zhm.rpc.io.ITransfer#receive(java.net.Socket)
	 */
	@Override
	public Object receive(Socket socket) {
		// TODO Auto-generated method stub
		return null;
	}

}
