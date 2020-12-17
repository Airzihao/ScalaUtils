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

  val nodeValue = new NodeValue(1234567, Array(1), Map("sss"->123, "1"->"qwe", "flag" -> true))
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
    println("deserial")
    val chillBytes: Array[Byte] = chillSerializer.serialize(nodeValue)
    val nodeBytes: Array[Byte] = nodeValueSerializer.serialize(nodeValue)

    val chillNodeValue = chillSerializer.deserialize(chillBytes, classOf[NodeValue])
    Assert.assertEquals(nodeValue.id, chillNodeValue.id)
    Assert.assertArrayEquals(nodeValue.labelIds, chillNodeValue.labelIds)
    Assert.assertEquals(nodeValue.properties, chillNodeValue.properties)
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
