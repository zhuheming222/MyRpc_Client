/**
 * ITransfer.java
 * zhm.rpc.io
 * 2018��1��10������4:02:35
 *
 */
package zhm.rpc.io;

import java.net.Socket;

/**
 * @author zhuheming
 * ITransfer
 * 2018��1��10������4:02:35
 */
public interface ITransfer {
	public void send(Socket socket,Object obj);
	
	public Object receive(Socket socket);
}
