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

  def generateNodes(tarNodeFile: File, nodeCount: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarNodeFile))
    var count = 0
    for(i<-1 to nodeCount) {
      if(count%10000000 == 0) {
        bos.flush()
        println(s"${count/10000000}% nodes generated.")
      }
      bos.write((s"$i\n").getBytes)
      count += 1
    }
    bos.flush()
  }
  def generateEdges(tarEdgeFile: File, nodeCount: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarEdgeFile))
    var count = 0
    for (i <-1 to nodeCount-2) {
      if(count%10000000 == 0) {
        bos.flush()
        println(s"${count/10000000}% edges generated.")
      }
      val edge1 = s"$i,${i+1}\n"
      val edge2 = s"$i,${i+2}\n"
      bos.write(edge1.getBytes)
      bos.write(edge2.getBytes)
      count += 1
    }
    bos.flush()

  }

  def main(args: Array[String]): Unit = {
    generateNodes(new File("./nodes.csv"), 100)
    generateEdges(new File("./edges.csv"), 100)
  }

}
