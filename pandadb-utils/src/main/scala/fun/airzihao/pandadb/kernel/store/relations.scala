package fun.airzihao.pandadb.kernel.store


case class StoredRelation(id: Long, from: Long, to: Long, typeId: Int) {
}

class StoredRelationWithProperty(override val id: Long,
                                 override val from: Long,
                                 override val to: Long,
                                 override val typeId: Int,
                                 val properties:Map[Int,Any])
  extends StoredRelation(id, from, to, typeId) {
}

trait RelationStoreSPI {
  def allRelationTypes(): Array[String];

  def allRelationTypeIds(): Array[Int];

  def getRelationTypeName(relationTypeId: Int): String;

  def getRelationTypeId(relationTypeName: String): Int;

  def addRelationType(relationTypeName: String): Int;

  def allPropertyKeys(): Array[String];

  def allPropertyKeyIds(): Array[Int];

  def getPropertyKeyName(keyId: Int): String;

  def getPropertyKeyId(keyName: String): Int;

  def addPropertyKey(keyName: String): Int;

  //  def getRelationById(relId: Long): Array[Byte];
  //  def getRelationProperties(relId: Long): Map[Int, Any];
  def getRelationById(relId: Long): StoredRelationWithProperty;

  def getRelationIdsByRelationType(relationTypeId: Int): Iterator[Long];

  //  def relationSetRelationType(relationId: Long, relationTypeId:Int): Unit;
  //  def relationRemoveRelationType(relationId: Long, relationTypeId:Int): Unit;

  def relationSetProperty(relationId: Long, propertyKeyId: Int, propertyValue: Any): Unit;

  def relationRemoveProperty(relationId: Long, propertyKeyId: Int): Any;

  def deleteRelation(relationId: Long): Unit;

  //  def serializeRelationToBytes(relationship: StoredRelationWithProperty): Array[Byte];
  //  def deserializeBytesToRelation(bytes: Array[Byte]): StoredRelationWithProperty;

  def findToNodeIds(fromNodeId: Long): Iterator[Long];

  def findToNodeIds(fromNodeId: Long, relationType: Int): Iterator[Long];

  //  def findToNodeIds(fromNodeId: Long, relationType: Int, category: Long): Iterator[Long];

  def findFromNodeIds(toNodeId: Long): Iterator[Long];

  def findFromNodeIds(toNodeId: Long, relationType: Int): Iterator[Long];

  //  def findFromNodeIds(toNodeId: Long, relationType: Int, category: Long): Iterator[Long];
  def addRelation(relation: StoredRelation): Unit

  def addRelation(relation: StoredRelationWithProperty): Unit

  def allRelationsWithProperty(): Iterator[StoredRelationWithProperty]

  def allRelations(): Iterator[StoredRelation]

  def findOutRelations(fromNodeId: Long): Iterator[StoredRelation]

  def findOutRelations(fromNodeId: Long, edgeType: Int): Iterator[StoredRelation]

  def findInRelations(toNodeId: Long): Iterator[StoredRelation]

  def findInRelations(toNodeId: Long, edgeType: Int): Iterator[StoredRelation]

  def close(): Unit
}