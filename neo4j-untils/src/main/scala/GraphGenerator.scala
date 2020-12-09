import java.io.{BufferedOutputStream, File, FileOutputStream}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 8:58 2020/12/9
 * @Modified By:
 */
object GraphGenerator {
  var tarNodeFile: File = new File("")
  var tarEdgeFile: File = new File("")

  def generateNodes(tarNodeFile: File, count: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarNodeFile))
    for(i<-1 to count) {
      if(i%10000000 == 0) {
        bos.flush()
        println(s"${i/10000000}% nodes generated.")
      }
      bos.write((s"$i\n").getBytes)
    }
    bos.flush()
  }
  def generateEdges(tarEdgeFile: File, nodeCount: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarEdgeFile))
    for (i <-1 to nodeCount-2) {
      if(i%10000000 == 0) {
        bos.flush()
        println(s"${i/10000000}% edges generated.")
      }
      val edge1 = s"$i,${i+1}\n"
      val edge2 = s"$i,${i+2}\n"
      bos.write(edge1.getBytes)
      bos.write(edge2.getBytes)
    }
    bos.flush()

  }

  def main(args: Array[String]): Unit = {
    generateNodes(new File("./nodes.csv"), 100)
    generateEdges(new File("./edges.csv"), 100)
  }

}
