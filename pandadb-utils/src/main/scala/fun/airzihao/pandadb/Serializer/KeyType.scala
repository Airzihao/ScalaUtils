package fun.airzihao.pandadb.Serializer

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:10 2020/12/21
 * @Modified By:
 */
object KeyType extends Enumeration {
  type KeyType = Value

  val Node = Value(1)     // [keyType(1Byte),nodeId(8Bytes)] -> nodeValue(id, labels, properties)
  val NodeLabelIndex = Value(2)     // [keyType(1Byte),labelId(4Bytes),nodeId(8Bytes)] -> null
  val NodePropertyIndexMeta = Value(3)     // [keyType(1Byte),labelId(4Bytes),properties(x*4Bytes)] -> null
  val NodePropertyIndex = Value(4)  // // [keyType(1Bytes),indexId(4),propValue(xBytes),valueLength(xBytes),nodeId(8Bytes)] -> null

  val Relation = Value(5)  // [keyType(1Byte),relationId(8Bytes)] -> relationValue(id, fromNode, toNode, labelId, category)
  val RelationLabelIndex = Value(6) // [keyType(1Byte),labelId(4Bytes),relationId(8Bytes)] -> null
  val OutEdge = Value(7)  // [keyType(1Byte),fromNodeId(8Bytes),relationLabel(4Bytes),category(8Bytes),toNodeId(8Bytes)] -> relationValue(id,properties)
  val InEdge = Value(8)   // [keyType(1Byte),toNodeId(8Bytes),relationLabel(4Bytes),category(8Bytes),fromNodeId(8Bytes)] -> relationValue(id,properties)
  val NodePropertyFulltextIndexMeta = Value(9)     // [keyType(1Byte),labelId(4Bytes),properties(x*4Bytes)] -> null

  val NodeLabel = Value(10) // [KeyType(1Byte), LabelId(4Byte)] --> LabelName(String)
  val RelationLabel = Value(11) // [KeyType(1Byte), LabelId(4Byte)] --> LabelName(String)
  val PropertyName = Value(12) // [KeyType(1Byte),PropertyId(4Byte)] --> propertyName(String)

  val NewNode = Value(13)     // [keyType(1Byte),labelId(4Bytes),nodeId(8Bytes)] -> nodeValue(id, labels, properties)

}
