package fun.airzihao.pandadb.SerializerTest

import fun.airzihao.pandadb.Serializer.Serializer
import fun.airzihao.pandadb.Utils.timing
import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 21:14 2020/12/17
 * @Modified By:
 */
class SerializerTest {
  val serializer = new Serializer

  val arr: Array[Int] = Array(1, 2, 3)
  val map: Map[Int, Any] = Map(1 -> 1, 2 -> "two", 3 -> true, 4 -> 4.0)

  @Test
  def testArr(): Unit = {
    val bytesArr = serializer.intArr2Bytes(arr)
    Assert.assertArrayEquals(arr, serializer.bytes2IntArr(bytesArr))
    println("serialize arr.")
    timing(for (i<-1 to 10000000) serializer.intArr2Bytes(arr))
    println("deserialize arr")
    timing(for (i<-1 to 10000000) serializer.bytes2IntArr(bytesArr))
  }

  @Test
  def testMap(): Unit = {
    val bytesArr = serializer.map2Bytes(map)
    Assert.assertEquals(map, serializer.bytes2Map(bytesArr))
    println("serialize map.")
    timing(for (i<-1 to 10000000) serializer.map2Bytes(map))
    println("deserialize map.")
    timing(for (i<-1 to 10000000) serializer.bytes2Map(bytesArr))
  }

}