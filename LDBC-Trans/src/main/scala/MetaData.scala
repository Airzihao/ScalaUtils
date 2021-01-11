import java.io.{File, FileWriter}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:23 2021/1/11
 * @Modified By:
 */
object MetaData {
  var labelSerialMap: Map[String, Int] = Map[String, Int]()
  var labelSerialIndex: Int = 1

  val prefix: Long = 100000000000000L

  def getLabelSerialNum(label: String): Int = {
    if(labelSerialMap.contains(label)) labelSerialMap(label)
    else {
      labelSerialMap += (label -> labelSerialIndex)
      val serialNum = labelSerialIndex
      labelSerialIndex += 1
      serialNum
    }
  }

  def getTransedId(srcId: Long, label: String): Long = {
    srcId + getLabelSerialNum(label)*prefix
  }
  def getTransedId(srcId: Long, serialNum: Int): Long = {
    srcId + prefix * serialNum
  }

  def persistToFile(file: File) = {
    val fileWriter = new FileWriter(file)
    fileWriter.write(s"prefix:$prefix\n")
    fileWriter.write(s"labelSerialIndex:$labelSerialIndex\n")
    labelSerialMap.foreach(kv => fileWriter.write(s"${kv._1}:${kv._2}\n"))
    fileWriter.flush()
  }

}
