/**
 * BeanLoader.java
 * zhm.rpc.ioc
 * 2017��12��21������10:04:48
 *
 */
package zhm.rpc.ioc;

import java.util.Map;

import org.dom4j.Document;

import zhm.rpc.serializer.ISerializer;

/**
 * @author zhuheming
 * BeanLoader ��xml�н����л����ü��ز�ʵ�������ڴ��У��Ա����ʹ��
 * 2017��12��21������10:04:48
 */
public interface BeanLoader {

	/*
	 * ����xml�����ļ�ʵ������Ӧ�����л��࣬������Ĭ�����л�ʵ��
	 * 
	 * */
	public Map<String,ISerializer> createBean();

}
