import Utils.timing
import org.junit.{Assert, Test}
/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 15:04 2020/12/16
 * @Modified By:
 */
class SerializerTest {

  val kyroSerializer = new KyroSerializer
  val originalSerializer = new OriginalSerializer
//  val hessianSerializer = new HessianSerializer

  val props: Map[String, Any] = Map("id"->12312312, "idStr"->"abc", "flag" -> true)

  @Test
  def perfSerialTest() = {
    timing(_repeatSerial(kyroSerializer.map2BytesArr(props), 1000000))
//    timing(_repeatSerial(hessianSerializer.map2BytesArr(props), 1000000))
    timing(_repeatSerial(originalSerializer.map2BytesArr(props), 1000000))
  }

  @Test
  def correctTest() = {
    val kyroBytes = kyroSerializer.map2BytesArr(props)
//    val hessianBytes = hessianSerializer.map2BytesArr(props)
    val originalBytes = originalSerializer.map2BytesArr(props)
    Assert.assertEquals(props, kyroSerializer.bytesArr2Map(kyroBytes))
//    Assert.assertEquals(props, hessianSerializer.bytesArr2Map(hessianBytes))
//    Assert.assertEquals(props, originalSerializer.bytesArr2Map(kyroBytes))
  }


  private def _repeatSerial[Array[Byte]](f: => Array[Byte], repeatTime: Int) = {
    for (i<-1 to repeatTime) {
      f
    }
  }

}
