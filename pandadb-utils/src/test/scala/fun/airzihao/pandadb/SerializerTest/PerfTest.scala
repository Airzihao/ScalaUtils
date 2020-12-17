package fun.airzihao.pandadb.SerializerTest

import fun.airzihao.pandadb.Serializer.{ChillSerializer, NodeValue, NodeValueSerializer}
import fun.airzihao.pandadb.Utils.timing
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:32 2020/12/17
 * @Modified By:
 */
class PerfTest {

  val nodeValue = new NodeValue(123456, Array(1), Map(1->1, 2->"dasdadadasdadbb", 3->true))
  val chillSerializer = new ChillSerializer
  val nodeValueSerializer = new NodeValueSerializer

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
    timing(_repeatDeserialize(chillSerializer.deserialize(chillBytes, classOf[NodeValue]), 10000000))
    timing(_repeatDeserialize(nodeValueSerializer.deserialize(nodeBytes), 10000000))
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
