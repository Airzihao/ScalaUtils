package performance

import io.netty.buffer.Unpooled

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:00 2021/2/5
 * @Modified By:
 */
object BinaryKVPerformanceTest {

  def time: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)


  def main(args: Array[String]): Unit = {
    val outputFile: File = {
      val file = new File(args(0))
      if (file.exists()) throw new Exception(s"$file exists.")
      file.createNewFile()
      file
    }

    val kvFileWriter: BinaryKVWriter = new BinaryKVWriter(outputFile)
    val serializer = new Serializer
    val totalCount: Long = args(1).toLong
    var i: Long = 1L

    val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val logger = new Runnable {
      override def run(): Unit = println(s"$i, ${time}")
    }
    service.scheduleWithFixedDelay(logger, 0, 10, TimeUnit.SECONDS)

    while (i <= totalCount) {
      val buf = Unpooled.buffer()
      (1 to 10).foreach(_ => {
        buf.writeBytes(serializer.serialize(i))
        i += 1
      })
      kvFileWriter.write(serializer.exportBytes(buf))
      buf.release()
    }
    logger.run()
    service.shutdown()
  }

}
