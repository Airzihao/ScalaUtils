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
  override val targetCSVFile: File = LDBCTransformer.getOutputFileBySrcName(srcCSVFile.getName)
  override val csvReader: CSVReader = new CSVReader(srcCSVFile)
  override val readerIter: Iterator[CSVLine] = csvReader.getAsCSVLines
  override val csvWriter: CSVWriter = new CSVWriter(targetCSVFile)
  val idIndex = readerIter.next().getAsArray.indexOf("id")
  val label = LDBCTransformer.getNodeLabelFromFileName(file.getName)
  val labelSerialNum = MetaData.getLabelSerialNum(label)
  val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

  var innerCount: Long = 0L

  override val notifyProgress: Runnable = new Runnable {
    override def run(): Unit = {
      LDBCTransformer.globalNodeCount.addAndGet(innerCount)
    }
  }

  override def handle(): Unit = {
    // init: write head
    val targetHeadLine: String = MetaData.headLineMap(srcCSVFile.getName.replace("_0_0.csv", ""))
    csvWriter.write(targetHeadLine)

    service.scheduleAtFixedRate(notifyProgress, 0, 1, TimeUnit.SECONDS)
    readerIter.foreach(csvLine => {
      transferId(csvLine)
      insertLabelOrType(csvLine, idIndex, label)
      csvWriter.write(csvLine.getAsString)
      innerCount += 1
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
