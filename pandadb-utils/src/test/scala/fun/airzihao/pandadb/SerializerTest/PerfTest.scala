package fun.airzihao.pandadb.SerializerTest

import fun.airzihao.pandadb.Serializer.{ChillSerializer, NodeValue, OriginalSerializer}
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
  val originalSerializer = new OriginalSerializer

  @Test
  def testSerialize(): Unit = {
    println("serial")
    timing(_repeatSerialize(chillSerializer.serialize(nodeValue), 1000000))
    timing(_repeatSerialize(originalSerializer.node2Bytes(nodeValue), 1000000))
  }

  @Test
  def testDeserialize(): Unit = {
    println("deserial")
    val chillBytes: Array[Byte] = chillSerializer.serialize(nodeValue)
    val originBytes: Array[Byte] = originalSerializer.node2Bytes(nodeValue)
    val chillNodeValue = chillSerializer.deserialize(chillBytes)
    val originNodeValue = originalSerializer.parseFromBytes(originBytes)
    Assert.assertEquals(chillNodeValue.id, originNodeValue.id)
    Assert.assertArrayEquals(chillNodeValue.labelIds, originNodeValue.labelIds)
    Assert.assertEquals(chillNodeValue.properties, originNodeValue.properties)
    timing(_repeatDeserialize(chillSerializer.deserialize(chillBytes), 1000000))
    timing(_repeatDeserialize(originalSerializer.parseFromBytes(originBytes), 1000000))
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
