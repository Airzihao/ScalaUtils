package fun.airzihao.pandadb.Serializer

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

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

  def node2Bytes(nodeValue: NodeValue): Array[Byte] = {
    map2BytesArr(Map[String,Any]("_id"->nodeValue.id,
      "_labels"->nodeValue.labelIds,
      "_props"->nodeValue.properties ))
  }

  def parseFromBytes(bytes: Array[Byte]): NodeValue = {
    val valueMap = ByteUtils.mapFromBytes(bytes)
    val id = valueMap.get("_id").get.asInstanceOf[Long]
    val labels = valueMap.get("_labels").get.asInstanceOf[Array[Int]]
    val props = valueMap.get("_props").get.asInstanceOf[Map[String, Any]]
    new NodeValue(id, labels, props)
  }
}