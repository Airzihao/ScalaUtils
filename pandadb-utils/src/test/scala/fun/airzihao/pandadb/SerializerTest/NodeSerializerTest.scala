package fun.airzihao.pandadb.SerializerTest

import cn.pandadb.kernel.util.serializer.{ChillSerializer, NodeSerializer}
import org.junit.{Assert, Test}
import fun.airzihao.pandadb.Utils.{timing, timingByUSec}
import fun.airzihao.pandadb.kernel.store.StoredNodeWithProperty

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:31 2020/12/17
 * @Modified By:
 */
class NodeSerializerTest {
  val propMap: Map[Int, Any] = Map(1-> "1988-06-15", 2-> Array("Aline17592188666832@earthling.net", "Aline17592188666832@gmail.com", "Aline17592188666832@yahoo.com"),
    3->"Aline", 4->"Oliveira", 5->"male", 6->Array("pt", "en"), 7->"Internet Explorer", 8->"198.17.121.2", 9->717592188666832L, 10->"2011-07-04")
  val nodeValue = new StoredNodeWithProperty(717592188666832L, Array(1), propMap)
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
    val bytesArr1 = ChillSerializer.serialize(nodeValue)
    timing(for (i<-1 to 10000000) nodeSerializer.deserializeNodeValue(bytesArr))
    timingByUSec(nodeSerializer.deserializeNodeValue(bytesArr))
    timingByUSec(ChillSerializer.deserialize(bytesArr1, classOf[StoredNodeWithProperty]))
    val node = nodeSerializer.deserializeNodeValue(bytesArr)
    println(node.properties)
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
