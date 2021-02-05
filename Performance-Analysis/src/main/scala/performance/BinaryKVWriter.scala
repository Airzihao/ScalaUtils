package performance

import java.io.{BufferedOutputStream, File, FileOutputStream}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:57 2021/2/5
 * @Modified By:
 */
class BinaryKVWriter(file: File) {
  val bos: BufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), 10*1024*1024)

  def write(bytes: Array[Byte]) = {
    bos.write(bytes)
    bos.flush()
  }

  def close = bos.close()

}
