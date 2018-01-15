package zhm.rpc.connect;

//服务消费者和提供者的连接器
public interface IConnect {

	//建立连接
	public <T> T connect(String host,int port);
	
	//关闭连接
	public void close(Object connectObject);
}
