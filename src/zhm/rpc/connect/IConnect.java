package zhm.rpc.connect;

//���������ߺ��ṩ�ߵ�������
public interface IConnect {

	//��������
	public <T> T connect(String host,int port);
	
	//�ر�����
	public void close(Object connectObject);
}
