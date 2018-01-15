/**
 * BeanLoader.java
 * zhm.rpc.ioc
 * 2017年12月21日下午10:04:48
 *
 */
package zhm.rpc.ioc;

import java.util.Map;

import org.dom4j.Document;

import zhm.rpc.serializer.ISerializer;

/**
 * @author zhuheming
 * BeanLoader 从xml中将序列化配置加载并实例化到内存中，以便后续使用
 * 2017年12月21日下午10:04:48
 */
public interface BeanLoader {

	/*
	 * 根据xml配置文件实例化对应的序列化类，并设置默认序列化实例
	 * 
	 * */
	public Map<String,ISerializer> createBean();

}
