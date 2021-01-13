

import fun.airzihao.ldbc.{CSVLine, CSVReader, CSVWriter}
import org.junit.Test

import java.io.{File, FileWriter}
import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:08 2021/1/12
 * @Modified By:
 */
class CSVWriterTest {
  val csvWriter = new CSVWriter(new File("./test-out.csv"))

  @Test
  def test(): Unit = {
    val csvLine = new CSVLine(Array("filed1", "filed2"))
    for(i<-1 to 39) {
      csvWriter.write(csvLine.getAsString)
    }
  }

  @Test
  def test1(): Unit = {
    val csvsReader = new CSVReader(new File("./output/social_network-0.003/dynamic/person_0_0.csv"))
    val iter = csvsReader.getAsCSVLines
    iter.foreach(line => csvWriter.write(line.getAsString))
    csvWriter.close
  }

}
