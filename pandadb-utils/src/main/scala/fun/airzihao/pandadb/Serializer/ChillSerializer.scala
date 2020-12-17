package fun.airzihao.pandadb.Serializer

import com.twitter.chill.{Input, KryoBase, Output, ScalaKryoInstantiator}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:24 2020/12/17
 * @Modified By:
 */
class ChillSerializer {
  // setup
  val kryo: KryoBase = {
    val instantiator = new ScalaKryoInstantiator()
    instantiator.setRegistrationRequired(true)
    instantiator.newKryo
  }

//  def serializeNodeValue()
  // write
  def serialize(obj: Any): Array[Byte] = {
    val bytesArr = new Array[Byte](4096)
    val output = new Output(bytesArr)
    kryo.writeObject(output, obj)
    output.flush()
    output.close()
    bytesArr
  }

  def deserialize(bytesArr: Array[Byte]): NodeValue = {
    val input = new Input(bytesArr)
    kryo.readObject(input, classOf[NodeValue])
  }

//  // read
//  val input = new Input(buffer)
//  val data2 = kryo.readObject(input,classOf[NodeValue]).asInstanceOf[NodeValue]

}


