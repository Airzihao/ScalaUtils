package fun.airzihao.pandadb.Serializer

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:10 2020/12/21
 * @Modified By:
 */
case class StoredRelation(id: Long, from: Long, to: Long, typeId: Int, category: Int) {
}

class StoredRelationWithProperty(override val id: Long,
                                 override val from: Long,
                                 override val to: Long,
                                 override val typeId: Int,
                                 override val category: Int,
                                 val properties:Map[Int,Any])
  extends StoredRelation(id, from, to, typeId, category) {
}
