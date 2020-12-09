import java.io.{BufferedOutputStream, File, FileOutputStream, FileWriter}

import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:54 2020/12/8
 * @Modified By:
 */
object DataWrapper {
  var srcNodeFile: File = new File("D:\\PySpace\\PyUtils\\src\\Graph\\nodes-1B.csv")
  var tarNodeFile: File = new File("G:\\dataset\\nodes-1B-wrapped.csv")
  var srcEdgeFile: File = new File("D:\\PySpace\\PyUtils\\src\\Graph\\edges-1B.csv")
  var tarEdgeFile: File = new File("G:\\dataset\\edges-1B-wrapped.csv")

  def wrapNode(nodeId: Long): String = {
    val label: String = s"label${nodeId%10}"
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
    val flag: Boolean = nodeId%2 match {
      case 0 => false
      case 1 => true
    }
    s"$nodeId,$label,$id_p,$idStr,$flag"
  }
  def wrapEdge(edgeId: Long, srcId: Long, tarId: Long): String = {
    val fromId: Long = srcId
    val toId: Long = tarId
    val fromId_p = fromId
    val toIdStr = toId.toString
    val edgeType: String = s"type${fromId%10}"
    val weight: Int = (fromId%10).toInt
    s"$edgeId,$fromId,$toId,$edgeType,$fromId_p,$toIdStr,$weight"
  }

  def wrapAllNodes(): Unit = {
    val src = Source.fromFile(srcNodeFile)
    val bos = new BufferedOutputStream(new FileOutputStream(tarNodeFile))
    var count = 0
    src.getLines().foreach(nodeId => {
      if(count % 10000000 == 0) {
        bos.flush()
        println(s"${count/10000000}% nodes wrapped.")
      }
      bos.write((wrapNode(nodeId.toLong)+"\n").getBytes)
      count += 1
    })
    bos.flush()
  }

  def wrapAllEdges(): Unit = {
    val src = Source.fromFile(srcEdgeFile)
    val bos = new BufferedOutputStream(new FileOutputStream(tarEdgeFile))
    var count: Long = 0
    src.getLines().foreach(line => {
      if(count % 10000000 == 0) {
        bos.flush()
        println(s"${count/20000000}% edges wrapped.")
      }
      val lineArr = line.replace("\n","").split(",")
      bos.write((wrapEdge(count, lineArr(0).toLong, lineArr(1).toLong)+"\n").getBytes)
      count += 1
    })
    bos.flush()
  }

  def main(args: Array[String]): Unit = {
    srcNodeFile = new File("")
    srcEdgeFile = new File("")
    tarNodeFile = new File("")
    tarEdgeFile = new File("")
    wrapAllNodes()
    wrapAllEdges()
  }
}
