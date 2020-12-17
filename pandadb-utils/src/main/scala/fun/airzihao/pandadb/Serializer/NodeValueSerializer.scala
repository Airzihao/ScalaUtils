package fun.airzihao.pandadb.Serializer

import java.io.ByteArrayOutputStream

import fun.airzihao.pandadb.PDBMetaData
import io.netty.buffer.{ByteBuf, ByteBufAllocator}
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 14:57 2020/12/17
 * @Modified By:
 */

// byteBuf [id][byte:labelsLen][labels]...[propNum][propId][proptype][propLen(ifNeed)][propValue]
class NodeValueSerializer {
  val allocator: ByteBufAllocator = ByteBufAllocator.DEFAULT
  private val _typeMap: Map[String, Int] = Map("String"->1, "Int" -> 2, "Long" -> 3, "Double" -> 4, "Float" -> 5, "Boolean" -> 6)
  private val _rTypeMap: Map[Int, String] = _typeMap.map(kv => kv._2 -> kv._1)

  def serialize(nodeValue: NodeValue): Array[Byte] = {
    val byteBuf: ByteBuf = allocator.heapBuffer()
//    _writeLong(nodeValue.id, byteBuf)
    byteBuf.writeLong(nodeValue.id)
    _writeLabels(nodeValue.labelIds, byteBuf)
    byteBuf.writeByte(nodeValue.properties.size)
    nodeValue.properties.foreach(kv => _writeProp(kv._1, kv._2, byteBuf))
    val offset = byteBuf.writerIndex()
    val bos = new ByteArrayOutputStream()
    byteBuf.readBytes(bos, offset)
    byteBuf.release()
    bos.toByteArray
  }

  def deserialize(byteArr: Array[Byte]): NodeValue = {
    val byteBuf = allocator.heapBuffer()
    byteBuf.writeBytes(byteArr)

    val id = byteBuf.readLong()
    val labels: Array[Int] = _readLabels(byteBuf)
    val props: Map[String, Any] = _readProps(byteBuf)
    byteBuf.release()
    new NodeValue(id, labels, props)

  }

  private def _writeLabels(labels: Array[Int], byteBuf: ByteBuf): Unit = {
    val len = labels.length
    byteBuf.writeByte(len)
    labels.foreach(label =>
      byteBuf.writeInt(label))
  }
  private def _writeProp(key: String, value: Any, byteBuf: ByteBuf) = {
    // key to int
    val keyId: Int = PDBMetaData.getPropId(key)
    byteBuf.writeByte(keyId)

    value match {
      case s: Boolean => _writeBoolean(value.asInstanceOf[Boolean], byteBuf)
      case s: Int => _writeInt(value.asInstanceOf[Int], byteBuf)
      case s: Long => _writeLong(value.asInstanceOf[Long], byteBuf)
      case _ => _writeString(value.asInstanceOf[String], byteBuf)
    }

  }

  private def _writeString(value: String, byteBuf: ByteBuf): Unit = {
    val strInBytes: Array[Byte] = value.getBytes
    val length: Int = strInBytes.length
    // write type
    byteBuf.writeByte(_typeMap.get("String").get)
    byteBuf.writeByte(length)
    byteBuf.writeBytes(strInBytes)
  }
  private def _writeInt(value: Int, byteBuf: ByteBuf): Unit = {
    byteBuf.writeByte(_typeMap.get("Int").get)
    byteBuf.writeInt(value)
  }
  private def _writeLong(value: Long, byteBuf: ByteBuf) = {
    byteBuf.writeByte(_typeMap.get("Long").get)
    byteBuf.writeLong(value)
  }
  private def _writeBoolean(value: Boolean, byteBuf: ByteBuf) = {
    byteBuf.writeByte(_typeMap.get("Boolean").get)
    byteBuf.writeBoolean(value)
  }

  private def _readString(byteBuf: ByteBuf): String = {
    val len: Int = byteBuf.readByte().toInt
    val bos: ByteArrayOutputStream = new ByteArrayOutputStream()
    byteBuf.readBytes(bos, len)
    bos.toString
  }
  private def _readNodeId(byteBuf: ByteBuf): Long = {
    byteBuf.readLong()
  }
  private def _readLabels(byteBuf: ByteBuf): Array[Int] = {
    val len = byteBuf.readByte().toInt
    val labels: Array[Int] = new Array[Int](len).map(_ => byteBuf.readInt())
    labels
  }
  private def _readProps(byteBuf: ByteBuf): Map[String, Any] = {
    val propNum: Int = byteBuf.readByte().toInt
    val propsMap: Map[String, Any] = new Array[Int](propNum).map(item => {
      val propId: Int = byteBuf.readByte().toInt
      val propName: String = PDBMetaData.getPropName(propId)
      val propType: String = _rTypeMap.get(byteBuf.readByte().toInt).get.toLowerCase
      val propValue = propType match {
        case "int" => byteBuf.readInt()
        case "long" => byteBuf.readLong()
        case "boolean" => byteBuf.readBoolean()
        case "string" => _readString(byteBuf)
      }
      (propName -> propValue)
    }).toMap
    propsMap
  }
}