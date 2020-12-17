import fun.airzihao.pandadb.Serializer.{ChillSerializer, NodeValue}
import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:56 2020/12/17
 * @Modified By:
 */
class ChillSerializerTest {
  val chillSerializer = new ChillSerializer

  @Test
  def test() = {
    val nodeValue = new NodeValue(1, Array(1), Map("sss"->123, "1"->"qwe"))
    val bytes = chillSerializer.serialize(nodeValue)
    val dNodeValue = chillSerializer.deserialize(bytes, classOf[NodeValue])
    println(dNodeValue)

  }

}
