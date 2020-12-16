import java.io.{BufferedInputStream, BufferedReader, File, FileInputStream, FileReader, InputStreamReader}

import scala.io.Source
import Utils.timing
import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:20 2020/12/16
 * @Modified By:
 */
class LoadFileTest {

  val filePath = "C:/structure-nodes-1B.csv"
  val file = new File(filePath)

  @Test
  def testSource(): Unit ={
    //228.2s
    val iter = Source.fromFile(filePath).getLines()
    println("read by source")
    timing(_source(iter))
  }

  @Test
  def test2(): Unit = {
    println("bufferedreader.")
    timing(_bufferReaderTest())
  }

  private def _source(iter: Iterator[String]): Int = {
    var i = 0
    while (iter.hasNext){
      val line = iter.next()
      i+=1
    }
    i
  }

  private def _bufferReaderTest(): Unit = {
    val fin = new FileInputStream(file)
    val reader = new InputStreamReader(fin)
    val bufReader = new BufferedReader(reader)
    var line = ""
    while (line != null) {
      line = bufReader.readLine()
      println(line)
    }
  }



}
