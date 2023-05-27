package ldm.scala.java

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._


object ScalaTest {
  def main(args: Array[String]): Unit = {
    println("df")
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    //读取数据
    val path: String = "E:\\java_workplace\\demoes\\src\\main\\resources\\hello.txt"
    val dataSet: DataSet[String] = env.readTextFile(path)

    //对数据分词、再按word分组，最后聚合
    val resultDataSet: DataSet[(String, Int)] = dataSet
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)

    dataSet.print()

  }
}
