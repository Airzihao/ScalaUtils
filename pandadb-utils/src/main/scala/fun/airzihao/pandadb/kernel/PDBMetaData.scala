package fun.airzihao.pandadb.kernel

import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

import cn.pandadb.kernel.kv.RocksDBStorage
import cn.pandadb.kernel.util.serializer.{BaseSerializer, ChillSerializer}

import scala.collection.generic.AtomicIndexFlag

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 19:47 2020/12/3
 * @Modified By:
 */
object PDBMetaData {
  //  private var _propIdMap: Map[String, Int] = Map[String, Int]()
  //  private var _rPropIdMap: Map[Int, String] = Map[Int, String]()
  //  private var _labelIdMap: Map[String, Int] = Map[String, Int]()
  //  private var _rLabelIdMap: Map[Int, String] = Map[Int, String]()
  //  private var _typeIdMap: Map[String, Int] = Map[String, Int]()
  //  private var _rTypeIdMap: Map[Int, String] = Map[Int, String]()
  //
  //  private var _propCounter: Int = 0
  //  private var _labelCounter: Int = 0
  //  private var _typeCounter: Int = 0

  private var _propIdManager: MetaIdManager = new MetaIdManager(255)
  private var _labelIdManager: MetaIdManager = new MetaIdManager(255)
  private var _typeIdManager: MetaIdManager = new MetaIdManager(255)

  private var _nodeIdAllocator: AtomicLong = new AtomicLong(0)
  private var _relationIdAllocator: AtomicLong = new AtomicLong(0)
  private var _indexIdAllocator: AtomicInteger = new AtomicInteger(0)


  def availableNodeId: Long = _nodeIdAllocator.getAndIncrement()
  def availableRelId: Long = _relationIdAllocator.getAndIncrement()
  def availabelIndexId: Int = _indexIdAllocator.getAndIncrement()

  //  def isPropExists(prop: String): Boolean = _propIdMap.contains(prop)
  //  def isLabelExists(label: String): Boolean = _labelIdMap.contains(label)
  //  def isTypeExists(edgeType: String): Boolean = _typeIdMap.contains(edgeType)

  //  def addProp(prop: String): Int = {
  //
  //  }
  //
  //  def addLabel(label: String): Int = {
  //    if(!isLabelExists(label)){
  //      _labelIdMap += (label -> _labelCounter)
  //      _rLabelIdMap += (_labelCounter -> label)
  //      _labelCounter += 1
  //      _labelCounter - 1
  //    } else _labelIdMap(label)
  //  }
  //
  //  def addType(edgeType: String): Int = {
  //    if(!isTypeExists(edgeType)){
  //      _typeIdMap += (edgeType -> _typeCounter)
  //      _rTypeIdMap += (_typeCounter -> edgeType)
  //      _typeCounter += 1
  //      _typeCounter - 1
  //    } else _typeIdMap(edgeType)
  //  }

  def getPropId(prop: String): Int = {
    _propIdManager.getId(prop)
  }

  def getPropName(propId: Int): String = {
    _propIdManager.getName(propId)
  }

  def getLabelId(label: String): Int = {
    _labelIdManager.getId(label)
  }

  def getLabelName(labelId: Int): String = {
    _labelIdManager.getName(labelId)
  }

  def getTypeId(edgeType: String): Int = {
    _typeIdManager.getId(edgeType)
  }

  def getTypeName(typeId: Int): String = {
    _typeIdManager.getName(typeId)
  }

//  def persist(dbPath: String): Unit = {
//    val rocksDB = RocksDBStorage.getDB(s"${dbPath}/metadata")
//    rocksDB.put("nodeId".getBytes(), BaseSerializer.serialize(_nodeIdAllocator.get()))
//    rocksDB.put("relationId".getBytes(), BaseSerializer.serialize(_relationIdAllocator.get()))
//    rocksDB.put("indexId".getBytes(), BaseSerializer.serialize(_indexIdAllocator.get()))


//    val metaData: Map[String, Any] = Map("_propIdMap" -> _propIdMap, "_rPropIdMap" -> _rPropIdMap,
//      "_labelIdMap" -> _labelIdMap, "_rLabelIdMap" -> _rLabelIdMap,
//      "_typeIdMap" -> _typeIdMap, "_rTypeIdMap" -> _rTypeIdMap,
//      "_propCounter" -> _propCounter, "_labelCounter" -> _labelCounter, "_typeCounter" -> _typeCounter)
//    val bytes: Array[Byte] = ChillSerializer.serialize(metaData)
//    rocksDB.put("meta".getBytes(), bytes)
//  }
//
//  def init(dbPath: String): Unit = {
//    val rocksDB = RocksDBStorage.getDB(s"${dbPath}/metadata")
//    val bytes: Array[Byte] = rocksDB.get("meta".getBytes())
//    val metaData: Map[String, Any] = ChillSerializer.deserialize(bytes, classOf[Map[String, Any]])
//    _propIdMap = metaData("_propIdMap").asInstanceOf[Map[String, Int]]
//    _rPropIdMap = metaData("_rPropIdMap").asInstanceOf[Map[Int, String]]
//    _labelIdMap = metaData("_labelIdMap").asInstanceOf[Map[String, Int]]
//    _rLabelIdMap = metaData("_rLabelIdMap").asInstanceOf[Map[Int, String]]
//    _typeIdMap = metaData.get("_typeIdMap").get.asInstanceOf[Map[String, Int]]
//    _rTypeIdMap = metaData.get("_rTypeIdMap").get.asInstanceOf[Map[Int, String]]
//    _propCounter = metaData.get("_propCounter").get.asInstanceOf[Int]
//    _labelCounter = metaData.get("_labelCounter").get.asInstanceOf[Int]
//    _typeCounter = metaData.get("_typeCounter").get.asInstanceOf[Int]
//  }

}
