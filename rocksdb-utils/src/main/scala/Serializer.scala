import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import collection.JavaConverters._
import com.caucho.hessian.io.{Hessian2Input, Hessian2Output, HessianOutput}
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import com.esotericsoftware.kryo.serializers.{JavaSerializer, MapSerializer}
import org.objenesis.strategy.StdInstantiatorStrategy
//import com.romix.scala.serialization.kryo._

import scala.collection.mutable;

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 14:47 2020/12/16
 * @Modified By:
 */
trait Serializer {
  def map2BytesArr(props: Map[String, Any]): Array[Byte];
  def bytesArr2Map(byteArr: Array[Byte]): Map[String, Any];
}

class KyroSerializer extends Serializer {
  val kryo: Kryo = new Kryo()
  kryo.setReferences(false);
  kryo.setRegistrationRequired(false);
  kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
  val prop = Map(""->"").asJava
//  kryo.register(prop.asJava.getClass, new JavaSerializer)
  kryo.setRegistrationRequired(false)
  kryo.register(classOf[Array[Any]])
//  kryo.register(classOf[java.util.Map[String, Any]], new MapSerializer)
//  kryo.register(classOf[NodeValue])

  override def map2BytesArr(props: Map[String, Any]): Array[Byte] = {
//    val arr: Array[Any] = Array()
    val map = props.asJava
    val bos = new ByteArrayOutputStream()
    val output: Output = new Output(bos)
    val arr: Array[Any] = Array(1, "sss", "sdadasd a", false)
    kryo.writeObject(output, arr)
    output.flush();
    output.close();
    bos.toByteArray
  }

  def str2BytesArr(str: String): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val output: Output = new Output(bos)
    kryo.writeObject(output, str)
    bos.toByteArray
  }
  def bytesArr2Str(byteArr: Array[Byte]): String = {
    val bis = new ByteArrayInputStream(byteArr)
    val input: Input = new Input(bis)
    val a = kryo.readClassAndObject(input)
    a.toString
  }

  override def bytesArr2Map(byteArr: Array[Byte]): Map[String, Any] = {
//    val a = kryo.readClassAndObject(input)
    val bis = new ByteArrayInputStream(byteArr)
    val input: Input = new Input(bis)
    val a = kryo.readObject(input, classOf[Array[Any]])
    a.asInstanceOf[Map[String, Any]]
  }
}

class OriginalSerializer extends Serializer {
  override def map2BytesArr(props: Map[String, Any]): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(bos)
    oos.writeObject(props)
    oos.close()
    bos.toByteArray
  }

  override def bytesArr2Map(byteArr: Array[Byte]): Map[String, Any] = {
    val bis=new ByteArrayInputStream(byteArr)
    val ois=new ObjectInputStream(bis)
    ois.readObject.asInstanceOf[Map[String, Any]]
  }
}

class NodeValue(override val id:Long, override val labelIds: Array[Int], override val  properties: Map[String, Any])
  extends StoredNodeWithProperty(id, labelIds, properties ) {
}
class StoredNodeWithProperty(override val id: Long,
                             override val labelIds: Array[Int],
                             val properties:Map[String,Any])
  extends StoredNode(id, labelIds){
}
case class StoredNode(id: Long, labelIds: Array[Int]=null) {
}