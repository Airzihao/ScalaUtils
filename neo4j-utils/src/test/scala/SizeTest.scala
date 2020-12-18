import com.carrotsearch.sizeof.RamUsageEstimator
import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 8:51 2020/10/23
 * @Modified By:
 */
class SizeTest {
  @Test
  def perfTest(): Unit ={
    val s1: Array[Char] = Array('a','a')
    val s2: String = "aa"
    val s3: String = "aaa"
    val s4: String = "aaaa"
    val s5: String = "aaaaaaaa"
    val list: List[Any] = List(s1,s2,s3,s4,s5)
    list.foreach(s => println(RamUsageEstimator.sizeOf(s)))
  }

  @Test
  def correctTest(): Unit = {

  }


}
