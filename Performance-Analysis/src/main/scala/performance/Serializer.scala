package performance

import io.netty.buffer.{ByteBuf, Unpooled}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:50 2021/2/5
 * @Modified By:
 */
class Serializer {
  def serialize(id: Long): Array[Byte] = {
    val byteBuf = Unpooled.buffer()
    byteBuf.writeLong(id)
    byteBuf.writeInt(1)
    byteBuf.writeLong(id)
    val bytes = exportBytes(byteBuf)
    byteBuf.release()
    bytes
  }

  def deserialize(bytes: Array[Byte]): (Long, Int, Long) = {
    val byteBuf = Unpooled.wrappedBuffer(bytes)
    val id: Long = byteBuf.readLong()
    val propId: Int = byteBuf.readInt()
    val propValue: Long = byteBuf.readLong()
    byteBuf.release()
    (id, propId, propValue)
  }

  def exportBytes(byteBuf: ByteBuf): Array[Byte] = {
    val dst = new Array[Byte](byteBuf.writerIndex())
    byteBuf.readBytes(dst)
    dst
  }

}
