package gadany.hind.tp3_spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class App2 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("TP word count").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> rddLines = sc.textFile("words.txt");
        JavaRDD<String> rddWords=rddLines.flatMap((line)-> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String,Integer> rddPairWords= rddWords.mapToPair((word)->new Tuple2<>(word,1));
        JavaPairRDD<String,Integer> rddWordCount=rddPairWords.reduceByKey((a,b)-> (Integer) (a+b));
        rddWordCount.foreach((word)->System.out.println(word._1+" "+word._2));
    }
}
