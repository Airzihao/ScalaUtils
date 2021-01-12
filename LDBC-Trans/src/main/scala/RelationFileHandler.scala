import java.io.File
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:07 2021/1/12
 * @Modified By:
 */
class RelationFileHandler(file: File) extends FileHandler {
  override val srcCSVFile: File = file
  override val targetCSVFile: File = LDBCTransformer.getOutputFileBySrcName(srcCSVFile.getName)
  override val csvReader: CSVReader = new CSVReader(srcCSVFile)
  override val readerIter: Iterator[CSVLine] = csvReader.getAsCSVLines
  override val csvWriter: CSVWriter = new CSVWriter(targetCSVFile)

  val headline = readerIter.next().getAsArray
  val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

  val relType: String = LDBCTransformer.getTypeFromRelationFileName(srcCSVFile.getName)
  val fromLabel: String = LDBCTransformer.getFromToLabelsFromFileName(srcCSVFile.getName)._1
  val toLabel: String = LDBCTransformer.getFromToLabelsFromFileName(srcCSVFile.getName)._2
  val fromLabelIndex: Int = headline.indexWhere(item => item.toLowerCase.contains("id"))
  val toLabelIndex = headline.indexWhere(item => item.toLowerCase.contains("id"), fromLabelIndex + 1)
  val fromLabelSerialNum: Int = MetaData.getLabelSerialNum(fromLabel)
  val toLabelSerialNum: Int = MetaData.getLabelSerialNum(toLabel)

  var innerCount: Long = 0L

  override val notifyProgress: Runnable = new Runnable {
    override def run(): Unit = {
      LDBCTransformer.globlaRelCount.addAndGet(innerCount)
    }
  }

  override def handle(): Unit = {
    // init: write head
    val targetHeadLine: String = MetaData.headLineMap(srcCSVFile.getName.replace("_0_0.csv", ""))
    csvWriter.write(targetHeadLine)

    service.scheduleAtFixedRate(notifyProgress, 0, 5, TimeUnit.SECONDS)
    readerIter.foreach(csvLine => {
      transferId(csvLine)
      insertLabelOrType(csvLine, 0, relType)
      insertRelId(csvLine, 0 ,MetaData.getRelId)
      csvWriter.write(csvLine.getAsString)
      innerCount += 1
    })
    csvWriter.close
    notifyProgress.run()
    service.shutdown()
  }

  override def transferId(csvLine: CSVLine): Unit = {
    val srcFromId: Long = csvLine.getAsArray(fromLabelIndex).toLong
    val srcToId: Long = csvLine.getAsArray(toLabelIndex).toLong
    val targetFromId: String = MetaData.getTransedId(srcFromId, fromLabelSerialNum).toString
    val targetToId: String = MetaData.getTransedId(srcToId, toLabelSerialNum).toString
    csvLine.replaceElemAtIndex(fromLabelIndex, targetFromId)
    csvLine.replaceElemAtIndex(toLabelIndex, targetToId)
  }

  def insertRelId(csvLine: CSVLine, index: Int = 0, relId: Long): Unit = {
    csvLine.insertElemAtIndex(index, relId.toString)
  }

}
