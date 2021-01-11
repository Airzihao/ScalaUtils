import java.io.{BufferedOutputStream, File, FileOutputStream}
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:46 2021/1/11
 * @Modified By:
 */
class CSVReader(file: File, spliter: String = LDBCTramsformer.spliter) {
  val iter = Source.fromFile(file).getLines()
  def getAsArray: Iterator[Array[String]] = Source.fromFile(file).getLines().map(item => item.split(spliter))
}

class CSVWriter(target: File) {
  val bos = new BufferedOutputStream(new FileOutputStream(target))
  val flusher = new Runnable {
    override def run(): Unit = bos.flush()
  }
  val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
  service.scheduleAtFixedRate(flusher, 100, 100, TimeUnit.MILLISECONDS)

  def close = {
    bos.flush()
    service.shutdown()
  }

  def write(bytes: Array[Byte]) = bos.write(bytes)
  def write(lineArr: Array[String]) = bos.write(s"${lineArr.mkString(",")}\n".getBytes())
  def write(line: String) = bos.write(s"$line\n".getBytes)
}
