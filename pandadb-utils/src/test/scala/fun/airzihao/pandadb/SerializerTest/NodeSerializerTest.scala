package fun.airzihao.pandadb.SerializerTest


import cn.pandadb.kernel.util.serializer.NodeSerializer
import org.junit.{Assert, Test}
import fun.airzihao.pandadb.Utils.timing
import fun.airzihao.pandadb.kernel.store.StoredNodeWithProperty

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:31 2020/12/17
 * @Modified By:
 */
class NodeSerializerTest {
  val nodeValue = new StoredNodeWithProperty(123456, Array(1), Map(1->1, 2->"dasdadadasdadbb", 3->true))
  val nodeSerializer = NodeSerializer

  @Test
  def serializePERF(): Unit = {
    println("serialize")
    timing(for (i<-1 to 10000000) nodeSerializer.serialize(nodeValue))
  }
  @Test
  def deSerializePERF(): Unit = {
    println("deserialize")
    val bytesArr = nodeSerializer.serialize(nodeValue)
    timing(for (i<-1 to 10000000) nodeSerializer.deserializeNodeValue(bytesArr))

  }

  @Test
  def correntTest(): Unit = {
    val bytesArr = nodeSerializer.serialize(nodeValue)
    val node = nodeSerializer.deserializeNodeValue(bytesArr)
    Assert.assertEquals(nodeValue.id, node.id)
    Assert.assertArrayEquals(nodeValue.labelIds, node.labelIds)
    Assert.assertEquals(nodeValue.properties, node.properties)
  }
}
