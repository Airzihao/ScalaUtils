import LDBCTransformer.{globalNodeCount, globlaRelCount}

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

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

  def insertLabelOrType(csvLine: CSVLine, index: Int, label: String): Unit = {
    csvLine.insertElemAtIndex(index, label)
  }

  def transferId(csvLine: CSVLine): Unit
  def handle(): Unit
  val notifyProgress: Runnable
}