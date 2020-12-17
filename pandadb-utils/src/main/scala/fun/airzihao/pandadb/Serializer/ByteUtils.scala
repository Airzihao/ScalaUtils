package fun.airzihao.pandadb.Serializer

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.charset.{Charset, StandardCharsets}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:29 2020/12/17
 * @Modified By:
 */
object ByteUtils {
  def setLong(bytes: Array[Byte], index: Int, value: Long): Unit = {
    bytes(index) = (value >>> 56).toByte
    bytes(index + 1) = (value >>> 48).toByte
    bytes(index + 2) = (value >>> 40).toByte
    bytes(index + 3) = (value >>> 32).toByte
    bytes(index + 4) = (value >>> 24).toByte
    bytes(index + 5) = (value >>> 16).toByte
    bytes(index + 6) = (value >>> 8).toByte
    bytes(index + 7) = value.toByte
  }

  def getLong(bytes: Array[Byte], index: Int): Long = {
    (bytes(index).toLong & 0xff) << 56 |
      (bytes(index + 1).toLong & 0xff) << 48 |
      (bytes(index + 2).toLong & 0xff) << 40 |
      (bytes(index + 3).toLong & 0xff) << 32 |
      (bytes(index + 4).toLong & 0xff) << 24 |
      (bytes(index + 5).toLong & 0xff) << 16 |
      (bytes(index + 6).toLong & 0xff) << 8 |
      bytes(index + 7).toLong & 0xff
  }

  def setDouble(bytes: Array[Byte], index: Int, value: Double): Unit =
    setLong(bytes, index, java.lang.Double.doubleToLongBits(value))

  def getDouble(bytes: Array[Byte], index: Int): Double =
    java.lang.Double.longBitsToDouble(getLong(bytes, index))

  def setInt(bytes: Array[Byte], index: Int, value: Int): Unit = {
    bytes(index) = (value >>> 24).toByte
    bytes(index + 1) = (value >>> 16).toByte
    bytes(index + 2) = (value >>> 8).toByte
    bytes(index + 3) = value.toByte
  }

  def getInt(bytes: Array[Byte], index: Int): Int = {
    (bytes(index) & 0xff) << 24 |
      (bytes(index + 1) & 0xff) << 16 |
      (bytes(index + 2) & 0xff) << 8 |
      bytes(index + 3) & 0xff
  }

  def setFloat(bytes: Array[Byte], index: Int, value: Float): Unit =
    setInt(bytes, index, java.lang.Float.floatToIntBits(value))

  def getFloat(bytes: Array[Byte], index: Int): Float =
    java.lang.Float.intBitsToFloat(getInt(bytes, index))

  def setShort(bytes: Array[Byte], index: Int, value: Short): Unit = {
    bytes(index) = (value >>> 8).toByte
    bytes(index + 1) = value.toByte
  }

  def getShort(bytes: Array[Byte], index: Int): Short = {
    (bytes(index) << 8 | bytes(index + 1) & 0xFF).toShort
  }

  def setByte(bytes: Array[Byte], index: Int, value: Byte): Unit = {
    bytes(index) = value
  }

  def getByte(bytes: Array[Byte], index: Int): Byte = {
    bytes(index)
  }

  def mapToBytes(map: Map[String, Any]): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(bos)
    oos.writeObject(map)
    oos.close()
    bos.toByteArray
  }

  def mapFromBytes(bytes: Array[Byte]): Map[String, Any] = {
    val bis=new ByteArrayInputStream(bytes)
    val ois=new ObjectInputStream(bis)
    ois.readObject.asInstanceOf[Map[String, Any]]
  }

  def longToBytes(num: Long): Array[Byte] = {
    val bytes = new Array[Byte](8)
    ByteUtils.setLong(bytes, 0, num)
    bytes
  }

  def doubleToBytes(num: Double): Array[Byte] = {
    val bytes = new Array[Byte](8)
    ByteUtils.setDouble(bytes, 0, num)
    bytes
  }

  def intToBytes(num: Int): Array[Byte] = {
    val bytes = new Array[Byte](4)
    ByteUtils.setInt(bytes, 0, num)
    bytes
  }

  def floatToBytes(num: Float): Array[Byte] = {
    val bytes = new Array[Byte](4)
    ByteUtils.setFloat(bytes, 0, num)
    bytes
  }

  def shortToBytes(num: Short): Array[Byte] = {
    val bytes = new Array[Byte](2)
    ByteUtils.setShort(bytes, 0, num)
    bytes
  }

  def byteToBytes(num: Byte): Array[Byte] = Array[Byte](num)

  def stringToBytes(str: String, charset: Charset = StandardCharsets.UTF_8): Array[Byte] = {
    str.getBytes(charset)
  }

  def stringFromBytes(bytes: Array[Byte], offset: Int = 0, length:Int = 0,
                      charset: Charset = StandardCharsets.UTF_8): String = {
    if (length > 0) new String(bytes, offset, length, charset)
    else new String(bytes, offset, bytes.length, charset)
  }

}