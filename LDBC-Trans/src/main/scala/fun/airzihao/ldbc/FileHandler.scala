package fun.airzihao.ldbc

import java.io.File

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:22 2021/1/12
 * @Modified By:
 */
trait FileHandler {
  val srcCSVFile: File

  val targetCSVFile: File
  val csvReader: CSVReader
  val readerIter: Iterator[CSVLine]
  val csvWriter: CSVWriter
  val dateIndex: Int

  var innerCount: Long = 0L
  var innerBatchCount: Long = 0L

  def insertLabelOrType(csvLine: CSVLine, index: Int, label: String): Unit = {
    csvLine.insertElemAtIndex(index, label)
  }

  def transferId(csvLine: CSVLine): Unit

  def transferDate(csvLine: CSVLine): Unit = {
    if (dateIndex > -1) {
      val fullDate = csvLine.getAsArray(dateIndex)
      val transferedDate = fullDate.substring(0, 10)
      csvLine.replaceElemAtIndex(dateIndex, transferedDate)
    }
  }

  def handle(): Unit

  val notifyProgress: Runnable
}
