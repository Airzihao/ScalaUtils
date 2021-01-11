import java.io.File

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:41 2021/1/11
 * @Modified By:
 */

class NodeFileIdTransfer(srcFile: File, targetFile: File, nodeLabel: String) extends IdTransfer {
  override protected val csvReader: CSVReader = new CSVReader(srcFile)
  override protected val csvWriter: CSVWriter = new CSVWriter(targetFile)
  val labelPrefix = MetaData.getLabelSerialNum(nodeLabel)
  val iter = csvReader.getAsArray
  val idIndex = iter.next().indexOf("id")

  override def transfer: Unit = {
    iter.foreach(lineArr => {
      val transedLineArr: Array[String] = _replaceElemByIndex(lineArr, idIndex, MetaData.getTransedId(lineArr(idIndex).toLong, labelPrefix).toString)
      csvWriter.write(transedLineArr)
    })
    csvWriter.close
  }
}

class RelationFileIdTransfer(srcFile: File, targetFile: File, fromLabel: String, toLabel: String) extends IdTransfer {
  override protected val csvReader: CSVReader = new CSVReader(srcFile)
  override protected val csvWriter: CSVWriter = new CSVWriter(targetFile)
  val fromLabelPrefix = MetaData.getLabelSerialNum(fromLabel)
  val toLabelPrefix = MetaData.getLabelSerialNum(toLabel)
  val iter = csvReader.getAsArray
  val head = iter.next()
  val fromIdIndex = head.indexWhere(item => item.toLowerCase.contains("id"))
  val toIdIndex = head.indexWhere(item => item.toLowerCase.contains("id"), fromIdIndex)
  override def transfer: Unit = {
    iter.foreach(lineArr => {
    val transedFromIdLineArr: Array[String] = _replaceElemByIndex(lineArr, fromIdIndex, MetaData.getTransedId(lineArr(fromIdIndex).toLong, fromLabelPrefix).toString)
    val transedToIdLineArr: Array[String] = _replaceElemByIndex(transedFromIdLineArr, toIdIndex, MetaData.getTransedId(lineArr(toIdIndex).toLong, toLabelPrefix).toString)
    csvWriter.write(transedToIdLineArr)
  })
    csvWriter.close
  }
}

trait IdTransfer {
  protected val csvReader: CSVReader
  protected val csvWriter: CSVWriter

  protected def _replaceElemByIndex(arr: Array[String], index: Int, elem: String): Array[String] = {
    arr.zipWithIndex.map(pair => {
      if(pair._2 == index) elem
      else pair._1
    })
  }

  def transfer: Unit

}
