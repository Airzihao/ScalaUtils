package fun.airzihao.pandadb.SerializerTest

import cn.pandadb.kernel.util.serializer.BaseSerializer
import fun.airzihao.pandadb.Utils.timing
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 21:14 2020/12/17
 * @Modified By:
 */
class BaseSerializerTest {
  val serializer = BaseSerializer

  val arr: Array[Int] = Array(1, 2, 3)
  val map: Map[Int, Any] = Map(1 -> 1, 2 -> "two", 3 -> true, 4 -> 4.0)

  @Test
  def testArr(): Unit = {
    val bytesArr = serializer.intArray2Bytes(arr)
    Assert.assertArrayEquals(arr, serializer.bytes2IntArray(bytesArr))
    println("serialize arr.")
    timing(for (i<-1 to 10000000) serializer.intArray2Bytes(arr))
    println("deserialize arr")
    timing(for (i<-1 to 10000000) serializer.bytes2IntArray(bytesArr))
  }

  @Test
  def testMap(): Unit = {
    val bytesArr = serializer.map2Bytes(map)
    val a = serializer.bytes2Map(bytesArr)
    Assert.assertEquals(map, a)
    println("serialize map.")
    timing(for (i<-1 to 10000000) serializer.map2Bytes(map))
    println("deserialize map.")
    timing(for (i<-1 to 10000000) serializer.bytes2Map(bytesArr))
  }

}