package fun.airzihao.pandadb.Serializer

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:29 2020/12/17
 * @Modified By:
 */
class NodeValue(override val id:Long, override val labelIds: Array[Int], override val  properties: Map[String, Any])
  extends StoredNodeWithProperty(id, labelIds, properties ) {
}
class StoredNodeWithProperty(override val id: Long,
                             override val labelIds: Array[Int],
                             val properties:Map[String,Any])
  extends StoredNode(id, labelIds){
}
case class StoredNode(id: Long, labelIds: Array[Int]=null) {
}

object NodeValue {
  def parseFromBytes(bytes: Array[Byte]): NodeValue = {
    val valueMap = ByteUtils.mapFromBytes(bytes)
    val id = valueMap.get("_id").get.asInstanceOf[Long]
    val labels = valueMap.get("_labels").get.asInstanceOf[Array[Int]]
    val props = valueMap.get("_props").get.asInstanceOf[Map[String, Any]]
    new NodeValue(id, labels, props)
  }

  def toBytes(nodeValue: NodeValue): Array[Byte] = {
    ByteUtils.mapToBytes(Map[String,Any]("_id"->nodeValue.id,
      "_labels"->nodeValue.labelIds,
      "_props"->nodeValue.properties ))
  }

  def toBytes(id: Long, labels: Array[Int], properties: Map[String, Any]): Array[Byte] = {
    ByteUtils.mapToBytes(Map[String,Any]("_id"->id,
      "_labels"->labels,
      "_props"->properties ))
  }
}
