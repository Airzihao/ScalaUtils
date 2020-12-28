package fun.airzihao.pandadb.SerializerTest

import cn.pandadb.kernel.util.serializer.{ChillSerializer, NodeSerializer}
import fun.airzihao.pandadb.Utils.timing
import fun.airzihao.pandadb.kernel.store.StoredNodeWithProperty
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:32 2020/12/17
 * @Modified By:
 */
class PerfTest {

  val nodeValue = new StoredNodeWithProperty(123456, Array(1), Map(1->1, 3->true, 2->"sadd"))
  val chillSerializer = ChillSerializer
  val nodeValueSerializer = NodeSerializer

  @Test
  def testSerialize(): Unit = {
    println("serialize")
    timing(_repeatSerialize(chillSerializer.serialize(nodeValue), 10000000))
    timing(_repeatSerialize(nodeValueSerializer.serialize(nodeValue), 10000000))
  }

  @Test
  def testDeserialize(): Unit = {
    println("deserialize")
    val chillBytes: Array[Byte] = chillSerializer.serialize(nodeValue)
    val nodeBytes: Array[Byte] = nodeValueSerializer.serialize(nodeValue)
    timing(_repeatDeserialize(chillSerializer.deserialize(chillBytes, classOf[StoredNodeWithProperty]), 10000000))
    timing(_repeatDeserialize(nodeValueSerializer.deserializeNodeValue(nodeBytes), 10000000))
  }

  private def _repeatSerialize[Array[Byte]](f: => Array[Byte], repeatTime: Int) = {
    for (i<-1 to repeatTime) {
      f
    }
  }

  private def _repeatDeserialize[NodeValue](f: => NodeValue, repeatTime: Int) = {
    for (i<-1 to repeatTime) {
      f
    }
  }
}
