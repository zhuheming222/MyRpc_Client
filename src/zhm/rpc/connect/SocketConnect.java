package zhm.rpc.connect;

import java.net.Socket;


/**
 * socket������
 * @author zhuheming
 * socketConnet
 * 2017��9��28������11:29:24
 */
public class SocketConnect implements IConnect {

	@Override
	public Socket connect(String host, int port) {
		// TODO Auto-generated method stub
		//�ж��������
		if(!"".equalsIgnoreCase(host)&&port!=0){
			Socket socket=null;
			try{
				socket=new Socket(host,port);
				
			}catch(Exception e){
				e.getStackTrace();
			}
			return socket;
		}else{
			return null;
		}
		
	}
	
	public void close(Object connectObject){
		if(connectObject!=null){
			try{
				Socket socket=(Socket)connectObject;
				socket.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
	}
	

}
