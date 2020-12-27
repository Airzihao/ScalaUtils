package fun.airzihao.pandadb

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:21 2020/12/16
 * @Modified By:
 */
object Utils {
  def timing[T](f: => T): T = {
    val t1 = System.currentTimeMillis()
    val t = f
    val t2 = System.currentTimeMillis()
    println(s"time cost: ${t2 - t1} ms")
    t
  }

  def timingByMicroSec[T](f: => T): T = {
    val t1 = System.nanoTime()
    val t = f
    val t2 = System.nanoTime()
    println(s"time cost: ${((t2 - t1)/1000).toInt} us")
    t
  }
}
