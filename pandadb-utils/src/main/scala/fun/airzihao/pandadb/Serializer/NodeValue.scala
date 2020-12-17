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
