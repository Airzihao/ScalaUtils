package fun.airzihao.pandadb.SerializerTest

import fun.airzihao.pandadb.Serializer.{NodeValue, NodeValueSerializer}
import org.junit.{Assert, Test}
import fun.airzihao.pandadb.Utils.timing

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:31 2020/12/17
 * @Modified By:
 */
class NodeValueSerializerTest {
  val nodeValue = new NodeValue(123456, Array(1), Map("a"->1, "aa"->"dasdadadasdadbb", "flag"->true))
  val nodeValueSerializer = new NodeValueSerializer

  @Test
  def serializePERF(): Unit = {
    println("serialize")
    timing(for (i<-1 to 10000000) nodeValueSerializer.serialize(nodeValue))
  }
  @Test
  def deSerializePERF(): Unit = {
    println("deserialize")
    val bytesArr = nodeValueSerializer.serialize(nodeValue)
    timing(for (i<-1 to 10000000) nodeValueSerializer.deserialize(bytesArr))

  }

  @Test
  def correntTest(): Unit = {
    val bytesArr = nodeValueSerializer.serialize(nodeValue)
    val node = nodeValueSerializer.deserialize(bytesArr)
    Assert.assertEquals(nodeValue.id, node.id)
    Assert.assertArrayEquals(nodeValue.labelIds, node.labelIds)
    Assert.assertEquals(nodeValue.properties, node.properties)
  }
}
