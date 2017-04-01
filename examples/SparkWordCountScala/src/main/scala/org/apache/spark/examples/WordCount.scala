package org.apache.spark.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object WordCount {
  def main(args: Array[String]) {
    
    val conf = new SparkConf().setAppName("Word Count")
    val sc = new SparkContext(conf) 

    val textFile = sc.textFile(args(0))
    val counts = textFile.flatMap(line => line.split(" "))
                     .map(word => (word, 1))
                     .reduceByKey(_ + _)
    
    val output = counts.collect()
    output.foreach(println)
    
    sc.stop()
  }
}
