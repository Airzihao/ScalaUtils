import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 14:50 2021/1/25
 * @Modified By:
 */
object RuntimeMoniter {


  val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

  val _maxTotalMemory: AtomicLong = new AtomicLong(0)
  val memQuerier: Runnable = new Runnable {
    override def run(): Unit = {
      val totalMemory = Runtime.getRuntime().totalMemory()
      if(_maxTotalMemory.get() < totalMemory) _maxTotalMemory.set(totalMemory)
    }
  }

  def start = service.scheduleWithFixedDelay(memQuerier, 0, 1, TimeUnit.SECONDS)
  def close = service.shutdown()

  def getPeakUsedMemory: String = {
    memoryTransfer(_maxTotalMemory.get())
  }



  def totalMemory: Long = {
    val totalMemory = Runtime.getRuntime().totalMemory()
    totalMemory
  }

  def freeMemory: Long = {
    val freeMemory = Runtime.getRuntime.freeMemory()
    freeMemory
  }

  def memoryTransfer(memoryInByte: Long): String = {
    val TB: Long = (memoryInByte/1024/1024/1024/1024)
    val GB: Long = (memoryInByte - TB*1024*1024*1024*1024)/1024/1024/1024
    val MB: Long = (memoryInByte - TB*1024*1024*1024*1024 - GB*1024*1024*1024)/1024/1024
    val kB: Long = (memoryInByte - TB*1024*1024*1024*1024 - GB*1024*1024*1024 - MB*1024*1024)/1024

    val map: Map[String, Int] = Map("TB" -> TB.toInt, "GB" -> GB.toInt, "MB" -> MB.toInt, "kB" -> kB.toInt)

    map.filter(kv => kv._2 > 0).map(kv => s"${kv._2}${kv._1}").mkString(" ")
  }

  def main(args: Array[String]): Unit = {
    RuntimeMoniter.start
    Thread.sleep(1000)
    println(getPeakUsedMemory)
    val array: Array[Int] = new Array[Int](500000000)
    Thread.sleep(1000)
    println(getPeakUsedMemory)
    RuntimeMoniter.close

  }

}
