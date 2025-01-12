package gadany.hind.tp3_spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class App1 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("TP3 RDD").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Integer> values = Arrays.asList(12,10,10,19,1,2,3,4,5,6,7,8,9,10);
        JavaRDD<Integer> rdd1  = sc.parallelize(values);
        JavaRDD<Integer> rdd2=rdd1.map(elem->elem+1);
        JavaRDD<Integer> rdd3=rdd2.filter(elem->elem>=10);
        List<Integer> result=rdd3.collect();
        result.forEach(System.out::println);
    }
}
