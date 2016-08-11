/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package org.apache.spark.examples;

import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WordCount {
	
	private final static Logger LOGGER = Logger.getLogger(WordCount.class.getName()); 
	
    @SuppressWarnings("serial")
	public static void main(String[] args) {
    	
    	LOGGER.setLevel(Level.INFO); 
    	
    	String inputPath = args[0];
        String outputPath = args[1];
        
        LOGGER.info("inputPath: " + inputPath);
        LOGGER.info("outputPath: " + outputPath);
        
        SparkConf sparkConf = new SparkConf().setAppName("Word count");
        
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        	
    	JavaRDD<String> textFile = ctx.textFile(inputPath);
    	JavaRDD<String> words = textFile.flatMap(new FlatMapFunction<String, String>() {
    	  public Iterable<String> call(String s) { return Arrays.asList(s.split(" ")); }
    	});
    	JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
    	  public Tuple2<String, Integer> call(String s) { return new Tuple2<String, Integer>(s, 1); }
    	});
    	JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
    	  public Integer call(Integer a, Integer b) { return a + b; }
    	});
    	counts.saveAsTextFile(outputPath); 
        
        ctx.close();
    }
}
