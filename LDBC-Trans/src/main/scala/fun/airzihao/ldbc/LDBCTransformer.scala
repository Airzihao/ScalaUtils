package fun.airzihao.ldbc

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:18 2021/1/11
 * @Modified By:
 */
object LDBCTransformer {
  var srcDir: File = new File("")
  var srcDynamicDir: File = new File("")
  var srcStaticDir: File = new File("")
  var targetDir: File = new File("")
  var targetNodeDir: File = new File("")
  var targetRelDir: File = new File("")

  val spliter: String = "\\|"

  def mergePersonCsv: Unit = {
    val personFile = new File(s"$srcDynamicDir/person_0_0.csv")
    val emailFile = new File(s"$srcDynamicDir/person_email_emailaddress_0_0.csv")
    val languageFile = new File(s"$srcDynamicDir/person_speaks_language_0_0.csv")
    val tempPersonFile = new File(s"$srcDynamicDir/person-output.csv")
    val merger = new Merger(personFile, emailFile, languageFile, tempPersonFile)
    merger.merge()
    merger.close
  }

  def isNodeFile(name: String): Boolean = {
    name.split("_").length == 3
  }

  def isRelationFile(name: String): Boolean = {
    // two excepted files
    name.split("_").length == 5 && name != "person_email_emailaddress_0_0.csv" && name != "person_speaks_language_0_0.csv"
  }

  def getNodeLabelFromFileName(fileName: String): String = {
    fileName.split("_").head
  }

  def getFromToLabelsFromFileName(fileName: String): (String, String) = {
    val arr = fileName.replace("_0_0.csv", "").split("_")
    (arr(0), arr(2))
  }

  def getTypeFromRelationFileName(fileName: String): String = {
    fileName.split("_")(1)
  }

  def getOutputFileBySrcName(srcName: String, targetPath: File): File = {
    val fileName = srcName.replace("_0_0.csv", "-output.csv")
    new File(s"$targetPath/$fileName")
  }

  val globalNodeCount: AtomicLong = new AtomicLong(0)
  val globlaRelCount: AtomicLong = new AtomicLong(0)
  val progressPrinter = new Runnable {
    override def run(): Unit = {
      val time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
      println(s"${globalNodeCount.get()} nodes transfered. $time")
      println(s"${globlaRelCount.get()} relations transfered. $time")
    }
  }

  def main(args: Array[String]): Unit = {

    srcDir = new File(args(0))
    targetDir = new File(args(1))
    srcDynamicDir = new File(s"$srcDir/dynamic")
    srcStaticDir = new File(s"$srcDir/static")
    targetDir = new File(args(1))
    targetNodeDir = new File(s"$targetDir/nodes")
    targetRelDir = new File(s"$targetDir/relations")
    targetDir.mkdirs()
    targetNodeDir.mkdir()
    targetRelDir.mkdir()


    val nodeFilesSet: Set[File] = {
      val set = srcDynamicDir.listFiles().filter(file => isNodeFile(file.getName)).toSet ++ srcStaticDir.listFiles().filter(file => isNodeFile(file.getName)).toSet
      set
    }
    val relFilesSet: Set[File] = {
      val set = srcDynamicDir.listFiles().filter(file => isRelationFile(file.getName)).toSet ++ srcStaticDir.listFiles().filter(file => isRelationFile(file.getName)).toSet
      set
    }


    val loggerService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    loggerService.scheduleAtFixedRate(progressPrinter, 0, 10, TimeUnit.SECONDS)

    mergePersonCsv
    println(s"person file merged.")

    val nodeTasks: Set[Future[Unit]] = nodeFilesSet.map(file => Future {
      new NodeFileHandler(file).handle()
    })
    nodeTasks.foreach(task => Await.result(task, Duration.Inf))
    //    nodeFilesSet.map(file => new NodeFileHandler(file).handle())
    println(s"node files transfered.")
    //
    val relTasks: Set[Future[Unit]] = relFilesSet.map(file => Future {
      new RelationFileHandler(file).handle()
    })
    relTasks.foreach(task => Await.result(task, Duration.Inf))
    //    relFilesSet.map(file => new RelationFileHandler(file).handle())
    println(s"relation files transfered.")
    progressPrinter.run()
    loggerService.shutdown()
    MetaData.persistToFile(new File(s"$targetDir/metadata"))

    val time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    println(s"transfer finished. $time")

  }

}
