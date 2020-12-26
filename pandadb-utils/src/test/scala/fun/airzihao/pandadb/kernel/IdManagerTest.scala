package fun.airzihao.pandadb.kernel

import fun.airzihao.pandadb.Utils.timing
import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:06 2020/12/25
 * @Modified By:
 */
class IdManagerTest {
  val manager = new MetaIdManager(255)

  @Test
  def test(): Unit = {
    timing(for (i<-1 to 100000000) manager.getId("bob"))
    timing(for (i<-1 to 100000000) PDBMetaData.getPropId("bob"))
  }

}
