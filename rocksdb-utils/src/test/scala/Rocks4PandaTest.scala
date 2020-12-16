import org.rocksdb.RocksDB
/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 13:34 2020/12/15
 * @Modified By:
 */
class Rocks4PandaTest {
//  org.rocksdb.RocksDB
  val path = ""
  val rocksDB = RocksDB.open(path)

  val rocksDBReadOnly = RocksDB.openReadOnly(path)


  rocksDB.compactRange()


}
