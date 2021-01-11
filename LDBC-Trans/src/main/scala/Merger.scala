
import java.io.{BufferedOutputStream, File, FileOutputStream}
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.collection.mutable
import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 15:16 2021/1/11
 * @Modified By:
 */

class Merger (personFile: File, emailFile: File, languageFile: File, targetFile: File){
  val personIter = new CSVReader(personFile).iter
  personIter.next()
  val emailIter = new CSVReader(emailFile).iter
  emailIter.next()
  val languageIter = new CSVReader(languageFile).iter
  languageIter.next()

  val emailMap = getMergedMap(emailIter)
  val languageMap = getMergedMap(languageIter)

  def getMergedMap(iter: Iterator[String]): MapForMerge = {
    val mergeMap = new MapForMerge
    iter.foreach(line => {
      val lineArr = line.split(LDBCTramsformer.spliter)
      mergeMap.put(lineArr(1), lineArr(2))
    })
    mergeMap
  }

  def merge(): Unit = {
    val csvWriter = new CSVWriter(targetFile)
    personIter.foreach(line => {
      val lineArr = line.split(LDBCTramsformer.spliter)
      val id = lineArr(1)
      val email: String = emailMap.getAsString(id)
      val language: String = languageMap.getAsString(id)
      val finalLine = s"$line${LDBCTramsformer.spliter}$email${LDBCTramsformer.spliter}$language"
      csvWriter.write(finalLine)
    })
    csvWriter.close
  }

}

class MapForMerge {
  var map: mutable.Map[String, Array[String]] = mutable.Map[String, Array[String]]()

  def put(key: String, value: String): Unit = {
    if(map.contains(key)) {
      map(key) = map(key) ++ Array(value)
    } else {
      map += (key -> Array(value))
    }
  }

  def getAsString(key: String): String = {
    if(map.contains(key)) _arr2String(map(key))
    else ""
  }

  private def _mapItem2String(kv: (String, Array[String]), spliter: String = "|"): String = {
    kv._1 + spliter + _arr2String(kv._2)
  }

  private def _arr2String(arr: Array[String]): String = {
    if(arr.length > 1) arr.mkString("{", ",", "}")
    else arr.head
  }

  def output(targetFile: File): Unit = {
    val writer = new CSVWriter(targetFile)
    map.foreach(kv => {
      writer.write(_mapItem2String(kv).getBytes())
    })
  }

}
