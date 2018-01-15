/**
 * SerializerType.java
 * zhm.rpc.serializer
 * 2017年12月18日下午10:49:07
 *
 */
package zhm.rpc.serializer;

/**
 * @author zhuheming
 * SerializerType
 * 2017年12月18日下午10:49:07
 */
public enum SerializerType {
    JdkDefaultSerializer("JdkDefaultSerializer"),
	FastjsonSerializer("FastjsonSerializer"),
	HessianSerializer("HessianSerializer"),
	ProtoStuffSerializer("ProtoStuffSerializer"),
	XStreamSerializer("XStreamSerializer");
	
	private String serializerType;
	
	private SerializerType(String serializerType){
		this.serializerType=serializerType;
	}
}
