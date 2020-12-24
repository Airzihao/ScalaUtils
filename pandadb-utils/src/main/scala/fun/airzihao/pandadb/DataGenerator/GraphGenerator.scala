package fun.airzihao.pandadb.DataGenerator

import java.io.{BufferedOutputStream, File, FileOutputStream}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 8:58 2020/12/9
 * @Modified By:
 */
object GraphGenerator {

  val nameMap: Map[Int, String] = Map(0 -> "Alice Panda", 1 -> "Bob Panda", 2 -> "Chris Panda", 3 -> "David Panda", 4 -> "Ellis Panda", 5 -> "Frank Panda",
    6 -> "Gamma Panda", 7 -> "Harry Panda", 8 -> "Irving Panda", 9 -> "Jackson Panda")

  def generateNodes(tarNodeFile: File, nodeCount: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarNodeFile))
    //    var count = 0
    for (i <- 1 to nodeCount) {
      if (i % 10000000 == 0) {
        bos.flush()
        println(s"${i/10000000} kw of $nodeCount nodes generated.")
      }
      val nodeString = wrapNode(i)
      bos.write((s"$nodeString\n").getBytes)
    }
    bos.flush()
  }

  def generateEdges(tarEdgeFile: File, nodeCount: Int): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(tarEdgeFile))
    //    var count = 0
    var relId = 1
    for (i <- 1 to nodeCount - 2) {
      if (i % 10000000 == 0) {
        bos.flush()
        println(s"${i / 10000000}% edges generated.")
      }
      val edge1 = wrapEdge(relId, i, i + 1) + "\n"
      val edge2 = wrapEdge(relId + 1, i, i + 2) + "\n"
      relId += 2
      bos.write(edge1.getBytes)
      bos.write(edge2.getBytes)
    }
    bos.flush()

  }

  def wrapNode(nodeId: Long): String = {
    val label: String = s"label${nodeId % 10}"
    val id_p: Long = nodeId
    val idStr: String = {
      val strBuilder: StringBuilder = new StringBuilder
      // 48 for 0, 97 for a
      nodeId.toString.foreach(char => strBuilder.append(char match {
        case '0' => 'a'
        case '1' => 'b'
        case '2' => 'c'
        case '3' => 'd'
        case '4' => 'e'
        case '5' => 'f'
        case '6' => 'g'
        case '7' => 'h'
        case '8' => 'i'
        case '9' => 'j'
      }))
      strBuilder.toString()
    }
    val flag: Boolean = nodeId % 2 match {
      case 0 => false
      case 1 => true
    }
    val name: String = nameMap((nodeId % 10).toInt)
    s"$nodeId,$label,$id_p,$idStr,$flag,$name"
  }

  def wrapEdge(edgeId: Long, srcId: Long, tarId: Long): String = {
    val fromId: Long = srcId
    val toId: Long = tarId
    val fromId_p = fromId
    val toIdStr = toId.toString
    val edgeType: String = s"type${fromId % 10}"
    val category: Int = (fromId % 100).toInt
    val weight: Int = (fromId % 100).toInt
    s"$edgeId,$fromId,$toId,$edgeType,$category,$fromId_p,$toIdStr,$weight"
  }

  def main(args: Array[String]): Unit = {
    /*
    1. !!!!!!Set the path to a disk has more than 100GB available space!!!
    2. modify the nodeCount arg.
    3. node: 1,2,3,4,5,6
    4. edge: 1->2, 1->3, 2->3, 2->4, 3->4, 3->5 ...  two edges from a node.
     */
    val nodeCount: Int = 50000000
    generateNodes(new File("./nodes5kw.csv"), nodeCount)
    generateEdges(new File("./edges5kw.csv"), nodeCount)
  }

}
