package fun.airzihao.pandadb.importer

import java.io.{File, FileInputStream}

import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 15:09 2020/12/18
 * @Modified By:
 */
class Importer {

  def estLineCount(file: File): Int = {
    val fis: FileInputStream = new FileInputStream(file)
    //1MB file
    val bytes: Array[Byte] = new Array[Byte](10*1024*1024)
    fis.read(bytes)
    val countPer10MB = new String(bytes, "utf-8").split("\n").length
    val lineCount = file.length()/10/1024/1024*countPer10MB
    lineCount.toInt
  }

}

class ImporterTest {
  @Test
  def test(): Unit = {
    val file = new File("G://dataset//nodes-1B-wrapped.csv")
    val importer = new Importer
    importer.estLineCount(file)
  }
}