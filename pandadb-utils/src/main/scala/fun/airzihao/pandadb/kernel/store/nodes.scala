package fun.airzihao.pandadb.kernel.store

import cn.pandadb.kernel.util.serializer.BaseSerializer
case class StoredNode(id: Long, labelIds: Array[Int]=null) {
}

class StoredNodeWithProperty(override val id: Long,
                             override val labelIds: Array[Int],
                             val properties:Map[Int,Any])
  extends StoredNode(id, labelIds){
}

trait NodeStoreSPI {
  def allLabels(): Array[String];

  def allLabelIds(): Array[Int];

  def getLabelName(labelId: Int): String;

  def getLabelId(labelName: String): Int;

  def addLabel(labelName: String): Int;

  def allPropertyKeys(): Array[String];

  def allPropertyKeyIds(): Array[Int];

  def getPropertyKeyName(keyId: Int): String;

  def getPropertyKeyId(keyName: String): Int;

  def addPropertyKey(keyName: String): Int;

  def getNodeById(nodeId: Long): StoredNodeWithProperty;

  def getNodesByLabel(labelId: Int): Iterator[StoredNodeWithProperty];

  def getNodeIdsByLabel(labelId: Int): Iterator[Long];

  //  def getNodeLabelIdsBytes(nodeId: Long): Array[Byte];
  //
  //  def getNodePropertiesBytes(nodeId: Long): Array[Byte];

  //  def createNodeId(): Long;

  def nodeAddLabel(nodeId: Long, labelId: Int): Unit;

  def nodeRemoveLabel(nodeId: Long, labelId: Int): Unit;

  def nodeSetProperty(nodeId: Long, propertyKeyId: Int, propertyValue: Any): Unit;

  def nodeRemoveProperty(nodeId: Long, propertyKeyId: Int): Any;

  def deleteNode(nodeId: Long): Unit;


  def serializeLabelIdsToBytes(labelIds: Array[Int]): Array[Byte] = {
    BaseSerializer.intArray2Bytes(labelIds)
  }

  def deserializeBytesToLabelIds(bytes: Array[Byte]): Array[Int] = {
    BaseSerializer.bytes2IntArray(bytes)
  }

  def serializePropertiesToBytes(properties: Map[Int, Any]): Array[Byte] = {
    BaseSerializer.map2Bytes(properties)
  }

  def deserializeBytesToProperties(bytes: Array[Byte]): Map[Int, Any] = {
    BaseSerializer.bytes2Map(bytes)
  }

  def allNodes(): Iterator[StoredNodeWithProperty]

  def deleteNodesByLabel(labelId: Int): Unit

  def addNode(node: StoredNodeWithProperty): Unit

  def getLabelIds(labelNames: Set[String]): Set[Int]

  def close(): Unit
}