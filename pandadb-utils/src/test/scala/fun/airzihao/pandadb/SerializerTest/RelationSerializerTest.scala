package fun.airzihao.pandadb.SerializerTest

import cn.pandadb.kernel.util.serializer.RelationSerializer
import fun.airzihao.pandadb.Serializer.{StoredRelation, StoredRelationWithProperty}
import fun.airzihao.pandadb.Utils.timing
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 9:15 2020/12/21
 * @Modified By:
 */
class RelationSerializerTest {
  val relationSerializer = RelationSerializer

  val relId: Long = 123
  val fromId: Long = 123456
  val toId: Long = 654321
  val typeId: Int = 9
  val category = 100
  val props: Map[Int, Any] = Map(1->"test", 2->true, 3->123)
  val relWithProps = new StoredRelationWithProperty(relId, fromId, toId, typeId, category, props)
  val relWithoutProps = new StoredRelation(relId, fromId, toId, typeId, category)

  @Test
  def testSerialize()= {
    val keyBytes = relationSerializer.serialize(relId)
    val valueBytes = relationSerializer.serialize(relId, fromId, toId, typeId, category, props)
    println("serialize relation.")
    timing(for(i<-1 to 10000000) relationSerializer.serialize(relId, fromId, toId, typeId, category, props))
  }

  @Test
  def testDeserialize() = {
    val valueBytes = relationSerializer.serialize(relId, fromId, toId, typeId, category, props)
    println("deserialize relation with prop")
    timing(for(i<-1 to 10000000) relationSerializer.deserializeRelWithProps(valueBytes))
    println("deserialize relation without prop")
    timing(for(i<-1 to 10000000) relationSerializer.deserializeRelWithoutProps(valueBytes))
  }

  @Test
  def correctTest() = {
    val valueBytes = relationSerializer.serialize(relId, fromId, toId, typeId, category, props)
    Assert.assertEquals(relWithProps, relationSerializer.deserializeRelWithProps(valueBytes))
    Assert.assertEquals(relWithoutProps, relationSerializer.deserializeRelWithoutProps(valueBytes))
  }

}
