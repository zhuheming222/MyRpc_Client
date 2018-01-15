/**
 * ByteAndbyteAndArray.java
 * zhm.rpc.test
 * 2018年1月12日下午2:38:28
 *
 */
package zhm.rpc.test;

import java.util.ArrayList;

/**
 * @author zhuheming
 * ByteAndbyteAndArray
 * 2018年1月12日下午2:38:28
 */
public class ByteAndbyteAndArray {
	
	private static ArrayList<Byte> byteList1=new ArrayList<Byte>();
	
	public static void main(String args[]){
		byteList1.set(1,(byte) 1);
		byteList1.set(2,(byte) 2);
		byteList1.set(3,(byte) 3);
		
		Byte[] b2=(Byte[])byteList1.toArray();
		
		//byte[] b3=b2;
	}
}
