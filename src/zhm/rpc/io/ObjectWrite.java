/**
 * ObjectWrite.java
 * zhm.rpc.io
 * 2017年10月14日下午9:23:55
 *
 */
package zhm.rpc.io;

/**
 * @author zhuheming
 * ObjectWrite
 * 将Object写成Byte型二进制数据的类（自己写的序列化类，模仿java）
 * 
 * 思路：
 * 1.定一个序列化和反序列化的协议，约定类开始，Object开始，域类型等标记，约定基本类型
 * 先描述本对象的所有属性（按照名称顺序排列），直接用属性名长度，属性名，属性值类型，属性值（引用类型为类名）表示。
 * 再按照属性名称顺序，描述每个引用对象的属性，递归调用直到没有超类和引用类
 * 再描述每个超类的每个属性，递归调用直到没有超类。
 * 
 * short STREAM_MAGIC = (short)0x00 //开始位
 * short STREAM_MAGIC = (short)0xF0 //结束位，只有当对象符，属性描述符都闭环时才被认为是结束
 * Int VERSION_FLAG =  //版本号，用户自己填，必输，版本号对应才能反序列化
 * 
 * TC_OBJECT_BEGIN  (short)0x01 //对象开始，前一个必定是一个版本号或者对象结束符
 * 对象名长度  int型
 * 对象名  string
 * TC_OBJECT_END    (short)0xF1 //对象结束，前面必定有一个未结束的对象开始符
 * TC_PROPERTYS_BEGIN (short)0x02 //所有属性描述（包含一个对象的所有属性）开始，前一个必定是一个对象开始符或者属性描述结束符
 * TC_PROPERTYS_END (short)0xF2 //所有属性描述结束，前面必定有一个未结束的属性描述开始符
 * 
 * TC_PROPERTY_BEGIN (short)0x03 //单个属性描述开始，包含属性名长度，属性名，属性值类型，属性值（引用属性为包含类名），前一个必定是所有属性描述开始符或者单个属性结束符。
 * TC_PROPERTY_END (short)0xF3 //单个属性描述结束符，前面必定有一个未结束的单个属性描述开始符
 * 属性名长度  short类型，跟在TC_PROPERTY_BEGIN后面
 * 属性名        长度不定    跟在属性名长度后面
 * 属性值类型  byte类型   和java默认一致,跟在属性名后面
 * 属性值   引用类包含类描述
 * 
 * TC_CLASS_BEGIN  (short)0x04 超类描述开始，同一个对象中，在所有属性描述完成之后开始
 * TC_CLASS_END    (short)0xF4 超类描述结束，前面必定有一个未结束的超类描述开始符
 * 
 * TC_REFFRENCECLASS_BEGIN  (short)0x05 引用类描述开始，在属性值描述后，单个属性描述符结束符之前
 * TC_REFFRENCECLASS_END    (short)0xF5 引用类描述结束，前面必定有一个未结束的引用类描述开始符
 * 
 * 2.首先需要一个像JAVA里的SerialVersionUID来表示序列化ID，ID对的上才可以反序列化(Serializable 接口不考虑，暂时只考虑都可以序列化的情况)。
 * 
 * 3.开始编写序列化过程
 * 
 * 
 * 2017年10月14日下午9:23:55
 */
//public class ObjectWriteByte  {
//
// java.io.ObjectOutputStream bout
//}
