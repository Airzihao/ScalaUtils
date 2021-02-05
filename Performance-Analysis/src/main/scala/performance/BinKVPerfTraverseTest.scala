package performance

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:01 2021/2/5
 * @Modified By:
 */
object BinKVPerfTraverseTest {
//  def time: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
  def main(args: Array[String]): Unit = {
    val file: File = new File(args(0))
    val performanceTest = new PerfUtils(file, 20)
    println(s"start to traverse")
    performanceTest.traverseFile

    println(s"start to traverse with deserialize")
    performanceTest.traverseFileWithDeserialize

    println("finish.")

  }

}
