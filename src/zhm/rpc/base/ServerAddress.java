/**
 * ServerAddress.java
 * zhm.rpc.base
 * 2018年2月6日下午8:23:36
 *
 */
package zhm.rpc.base;

/**
 * @author zhuheming
 * ServerAddress
 * 2018年2月6日下午8:23:36
 */
public class ServerAddress {
	
	private String serverIp=null;
	private int serverPort=0;
	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}
	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/*
	 * 是否可用
	 * */
	public boolean isUseful(){
		if(this.serverIp!=null&&this.serverPort!=0){
			return true;
		}else{
			return false;
		}
	}

}
