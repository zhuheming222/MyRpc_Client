/**
 * BeanLoaderImpl.java
 * zhm.rpc.ioc
 * 2017��12��21������10:12:18
 *
 */
package zhm.rpc.ioc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import zhm.rpc.base.ElementLoaderImpl;
import zhm.rpc.base.XmlDocumentHolder;
import zhm.rpc.serializer.ISerializer;

/**
 * @author zhuheming
 * BeanLoaderImpl
 * 2017��12��21������10:12:18
 */
public class BeanLoaderImpl implements BeanLoader {
	
	//������л�ʵ��
	private Map<String,ISerializer> beanMap=null;
	
	//Ĭ�����л�ʵ��
	private ISerializer defalultObject=null;

	/* (non-Javadoc)
	 * @see zhm.rpc.ioc.BeanLoader#createBean(org.dom4j.Document)
	 */
	
	@Override
	public Map<String, ISerializer> createBean() {
		// TODO Auto-generated method stub
		if(beanMap!=null) return beanMap;
		else{
			XmlDocumentHolder xmlDocumentHolder=new XmlDocumentHolder();
			ElementLoaderImpl elementLoaderImpl =new ElementLoaderImpl();
			// xmlDocumentHolder.getDocument("MyRPC.xml");
			//�õ�xml�����ļ���������Ϣ
			Map<String,Element> seMap=elementLoaderImpl.getDocument(xmlDocumentHolder.getDocument("D:\\workspace\\MyRPC_Client\\MyRPC.xml"));
			Iterator<Entry<String, Element>> it=seMap.entrySet().iterator();
			beanMap=new HashMap<String,ISerializer>();
			while(it.hasNext()){
				//����xml�����ļ�
				Entry<String, Element> entry=it.next();
				//�����SerializerList�����ǩ����ʼ��map
				if("Serializer".equalsIgnoreCase(entry.getKey())&&beanMap!=null){
					//���ȡ��XML�����õ�����
					List<Attribute> listAttr=entry.getValue().attributes();
					String codeStr=listAttr.get(0).getValue();
					String serObjStr=listAttr.get(1).getValue();
					Object serObj=null;
					//ʵ����
					try {
						Class clazz=Class.forName(serObjStr);
						serObj=clazz.newInstance();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					beanMap.put(codeStr, (ISerializer) serObj);
				}else if("CurrentSerializer".equalsIgnoreCase(entry.getKey())&&beanMap!=null){
					//���ȡ��XML�����õ�����
					List<Attribute> listAttr=entry.getValue().attributes();
					//String defalultObjectStr=listAttr.get(0).getValue();
					String serObjStr=listAttr.get(1).getValue();
					Object serObj=null;
					//ʵ����
					try {
						Class clazz=Class.forName(serObjStr);
						serObj=clazz.newInstance();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					defalultObject=(ISerializer)serObj;
				}else{
					return null;
				}
			}
		}
		return beanMap;
	}
	
	public ISerializer getDefaultBean(){
		if(beanMap==null){
			createBean();
		}
		return defalultObject;
	}

}
