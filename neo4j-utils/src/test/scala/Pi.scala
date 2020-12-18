/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 13:41 2020/10/31
 * @Modified By:
 */
object Pi {

  val numSteps: Long = Math.pow(10,6).asInstanceOf[Int];
  val step_len:Double = 1/numSteps
  def serialPi(): Double = {
    var i = 0;
    var pi: Double = 0;
    for (i <- 0 until numSteps.asInstanceOf[Int] ) {
      val x = (i + 0.5)*step_len;
      pi += 4/(1+x*x)
    }
    pi *= step_len
    return pi
  }

  def main(args: Array[String]): Unit = {
    println(serialPi())
  }
}
