package fun.airzihao.pandadb.kernel

import scala.collection.mutable

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 19:14 2020/12/24
 * @Modified By:
 */
trait IdManager {

  protected var MAX_SIZE: Int
  protected var _count: Int = 0
  protected var _availableIdQueue: mutable.Queue[Int] = mutable.Queue[Int]()
  protected var _id2Name: mutable.Map[Int, String] = mutable.Map[Int, String]()
  protected var _name2Id: mutable.Map[String, Int] = mutable.Map[String, Int]()

  def getId(name: String): Int = {
    if(_name2Id.contains(name)) _name2Id.get(name).get
    else _addName(name)
  }

  def getName(id: Int): String = {
    if(_id2Name.contains(id)) _id2Name.get(id).get
    else throw new Exception(s"The id $id does not exist.")
  }

  def recycleId(id: Int): Boolean = {
    if(_id2Name.contains(id)) {
      val name: String = _id2Name.remove(id).get
      _name2Id.remove(name)
    }
    _availableIdQueue.enqueue(id)
    true
  }

  def recycleName(name: String): Boolean = {
    if(_name2Id.contains(name)) {
      val id = _name2Id(name)
      recycleId(id)
    }
    true
  }

  private def _insert(id: Int, name: String): Unit = {
    _id2Name.put(id, name)
    _name2Id.put(name, id)
  }

  private def _addName(name: String): Int = {
    if(_availableIdQueue.length>0){
      val id: Int = _availableIdQueue.dequeue()
      _insert(id, name)
      id
    }
    else {
      val id = _count
      _count += 1
      _insert(id, name)
      id
    }
  }

}
