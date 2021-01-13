package fun.airzihao.ldbc

import java.io.File
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:07 2021/1/12
 * @Modified By:
 */
class NodeFileHandler(file: File) extends FileHandler {
  override val srcCSVFile: File = file
  override val targetCSVFile: File = LDBCTransformer.getOutputFileBySrcName(srcCSVFile.getName, LDBCTransformer.targetNodeDir)
  override val csvReader: CSVReader = new CSVReader(srcCSVFile)
  override val readerIter: Iterator[CSVLine] = csvReader.getAsCSVLines
  override val csvWriter: CSVWriter = new CSVWriter(targetCSVFile)

  val srcHead: Array[String] = readerIter.next().getAsArray
  override val dateIndex: Int = srcHead.indexOf("creationDate")
  val idIndex = srcHead.indexOf("id")
  val labelIndex = idIndex + 1
  val label = LDBCTransformer.getNodeLabelFromFileName(file.getName)
  val labelSerialNum = MetaData.getLabelSerialNum(label)
  val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

  override val notifyProgress: Runnable = new Runnable {
    override def run(): Unit = {
      LDBCTransformer.globalNodeCount.addAndGet(innerBatchCount)
      innerCount += innerBatchCount
      innerBatchCount = 0
    }
  }

  override def handle(): Unit = {
    // init: write head
    val targetHeadLine: String = MetaData.headLineMap(srcCSVFile.getName.replace("_0_0.csv", ""))
    csvWriter.write(targetHeadLine)

    service.scheduleAtFixedRate(notifyProgress, 0, 1, TimeUnit.SECONDS)
    readerIter.foreach(csvLine => {
      transferDate(csvLine)
      transferId(csvLine)
      insertLabelOrType(csvLine, labelIndex, label)
      csvWriter.write(csvLine.getAsString)
      innerBatchCount += 1
    })
    csvWriter.close
    notifyProgress.run()
    service.shutdown()
  }

  override def transferId(csvLine: CSVLine): Unit = {
    val srcId: Long = csvLine.getAsArray(idIndex).toLong
    val targetId: String = MetaData.getTransedId(srcId, labelSerialNum).toString
    csvLine.replaceElemAtIndex(idIndex, targetId)
  }

}
