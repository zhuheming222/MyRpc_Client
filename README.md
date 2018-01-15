# MyRpc_Client
服务消费者 主要代码在src中， 
src\zhm\rpc\proxy 主要用于服务消费者发送请求的方法和参数，使用动态代理方式，根据接口生成代理对象，在执行代理对象方法时调用invoke，在invoke中请求 
src\zhm\rpc\base 主要用于基础的操作，包括dom4j方式读取xml文件。 
src\zhm\rpc\ioc 主要用于模仿spring 的ioc，主要用于模仿spring 的ioc， 
src\zhm\rpc\serializer 主要用于各个序列化方式的实现 
src\zhm\rpc\server 用于测试的方法，服务提供者提供的服务
src\zhm\rpc\connect socket连接相关
