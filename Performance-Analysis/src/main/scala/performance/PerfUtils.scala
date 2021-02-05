package performance

import performance.BinaryKVPerformanceTest.time

import java.io.{BufferedInputStream, File, FileInputStream}
import java.nio.channels.FileChannel
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:43 2021/2/5
 * @Modified By:
 */
class PerfUtils(file: File, objLength: Int) {
//  val bis: BufferedInputStream = new BufferedInputStream(new FileInputStream(file))

//  channel.close()




  def traverseFile = {
    val fis: FileInputStream = new FileInputStream(file)
    val bis: BufferedInputStream = new BufferedInputStream(fis, 1024*1024)
    val channel: FileChannel = fis.getChannel
    val channelSize: Long = channel.size()
    var i: Long = 0L
    val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val logger = new Runnable {
      override def run(): Unit = println(s"processed item: $i, ${time}")
    }
    service.scheduleWithFixedDelay(logger, 0, 10, TimeUnit.SECONDS)
    var readedLength: Long  = 0L
    while (readedLength < channelSize) {
      val bytes: Array[Byte] = new Array[Byte](objLength)
      bis.read(bytes)
      readedLength += objLength
      i+=1
    }
    logger.run()
    service.shutdown()
  }

  def traverseFileWithDeserialize = {
    val fis: FileInputStream = new FileInputStream(file)
    val bis: BufferedInputStream = new BufferedInputStream(fis, 1024*1024)
    val channel: FileChannel = fis.getChannel
    val channelSize: Long = channel.size()
    var i: Long = 0L
    val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val logger = new Runnable {
      override def run(): Unit = println(s"processed item: $i, ${time}")
    }
    service.scheduleWithFixedDelay(logger, 0, 10, TimeUnit.SECONDS)
    val serializer = new Serializer
    var readedLength: Long = 0L
    while (readedLength < channelSize) {
      val bytes: Array[Byte] = new Array[Byte](objLength)
      bis.read(bytes,0,objLength)
      serializer.deserialize(bytes)
      readedLength += objLength
      i += 1
    }
    logger.run()
    service.shutdown()
  }

}
