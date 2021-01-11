import java.io.File
import java.util.Date

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:18 2021/1/11
 * @Modified By:
 */
object LDBCTramsformer {
  val srcDir: File = new File("./output/social_network-0.003")
  val targetDir: File = new File("./output/sn-0.003-output")


  def getTempFile = new File(s"workDir/${new Date()}")

  val spliter: String = "\\|"

  def append(srcStr: String, appendStr: String, spliter: String = "|"): String = {
    srcStr + spliter + appendStr
  }

  def mergePersonCsv: Unit = {
    val personFile = new File(s"$srcDir/dynamic/person_0_0.csv")
    val emailFile = new File(s"$srcDir/dynamic/person_email_emailaddress_0_0.csv")
    val languageFile = new File(s"$srcDir/dynamic/person_speaks_language_0_0.csv")
    val targetFile = new File(s"$targetDir/person-output.csv")
    val merger = new Merger(personFile, emailFile, languageFile, targetFile)
    merger.merge()
  }

  def transferId(dir: File): Unit = {
    dir.listFiles().foreach(file => {
      if(_isNodeFile(file.getName) && _needToTransId(file.getName)) {
        val labelName = _getNodeLabelFromFileName(file.getName)
        val transfer = new NodeFileIdTransfer(file, _getOutputFileBySrcName(file.getName), labelName)
        transfer.transfer
      }
      if(_isRelationFile(file.getName) && _needToTransId(file.getName)) {
        val labelNamePair = _getFromToLabelsFromFileName(file.getName)
        val transfer = new RelationFileIdTransfer(file, _getOutputFileBySrcName(file.getName), labelNamePair._1, labelNamePair._2)
        transfer.transfer
      }
    })
  }

  private def _isNodeFile(name: String): Boolean = {
    name.split("_").length == 3
  }
  private def _isRelationFile(name: String): Boolean = {
    name.split("_").length == 5 && name != "person_email_emailaddress_0_0.csv" && name != "person_speaks_language_0_0.csv"
  }

  private def _getNodeLabelFromFileName(fileName: String): String = {
    fileName.split("_").head
  }

  private def _getFromToLabelsFromFileName(fileName: String): (String, String) = {
    val arr = fileName.replace("_0_0.csv", "").split("_")
    (arr(0), arr(2))
  }

  private def _getOutputFileBySrcName(srcName: String): File = {
    val fileName = srcName.replace("_0_0.csv", "-output.csv")
    new File(s"$targetDir/$fileName")
  }

  private def _needToTransId(fileName: String): Boolean = {
    fileName.contains("organisation") || fileName.contains("place") || fileName.contains("tag") || fileName.contains("tagclass")
  }

  def main(args: Array[String]): Unit = {
    mergePersonCsv
    transferId(new File(s"$srcDir/static"))
    transferId(new File(s"$srcDir/dynamic"))
    MetaData.persistToFile(new File(s"$targetDir/metadata"))
  }

}
