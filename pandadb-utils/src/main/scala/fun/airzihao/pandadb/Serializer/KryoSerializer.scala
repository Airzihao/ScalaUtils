//package fun.airzihao.pandadb.Serializer
//
//import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
//
//import com.esotericsoftware.kryo.Kryo
//import com.esotericsoftware.kryo.io.{Input, Output}
//import org.objenesis.strategy.StdInstantiatorStrategy
//import scala.collection.JavaConverters._
//
///**
// * @Author: Airzihao
// * @Description:
// * @Date: Created at 9:53 2020/12/17
// * @Modified By:
// */
//
//class KryoSerializer {
//  val kryo: Kryo = new Kryo()
//  kryo.setReferences(false)
//  kryo.setRegistrationRequired(true)
//  kryo.setInstantiatorStrategy(new StdInstantiatorStrategy)
//
////  def str2Bytes(str: String): Array[Byte] = {
////
////  }
//
//
//}
//
////class KyroSerializer extends Serializer {
////  val kryo: Kryo = new Kryo()
////  kryo.setReferences(false);
////  kryo.setRegistrationRequired(false);
////  kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
////  val prop = Map(""->"").asJava
////  //  kryo.register(prop.asJava.getClass, new JavaSerializer)
////  kryo.setRegistrationRequired(false)
////  kryo.register(classOf[Array[Any]])
////  //  kryo.register(classOf[java.util.Map[String, Any]], new MapSerializer)
////  //  kryo.register(classOf[NodeValue])
////
////  override def map2BytesArr(props: Map[String, Any]): Array[Byte] = {
////    //    val arr: Array[Any] = Array()
////    val map = props.asJava
////    val bos = new ByteArrayOutputStream()
////    val output: Output = new Output(bos)
////    val arr: Array[Any] = Array(1, "sss", "sdadasd a", false)
////    kryo.writeObject(output, arr)
////    output.flush();
////    output.close();
////    bos.toByteArray
////  }
////
////  def str2BytesArr(str: String): Array[Byte] = {
////    val bos = new ByteArrayOutputStream()
////    val output: Output = new Output(bos)
////    kryo.writeObject(output, str)
////    bos.toByteArray
////  }
////  def bytesArr2Str(byteArr: Array[Byte]): String = {
////    val bis = new ByteArrayInputStream(byteArr)
////    val input: Input = new Input(bis)
////    val a = kryo.readClassAndObject(input)
////    a.toString
////  }
////
////  override def bytesArr2Map(byteArr: Array[Byte]): Map[String, Any] = {
////    //    val a = kryo.readClassAndObject(input)
////    val bis = new ByteArrayInputStream(byteArr)
////    val input: Input = new Input(bis)
////    val a = kryo.readObject(input, classOf[Array[Any]])
////    a.asInstanceOf[Map[String, Any]]
////  }
////}
