package gadany.hind.tp3_spark.ex_ventes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class TotalVentesParVille {
    public static void main(String[] args) {
        // Configuration Spark
        SparkConf conf = new SparkConf().setAppName("TotalVentesParVille").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Chargement du fichier ventes.txt en tant que RDD
        JavaRDD<String> lines = sc.textFile("ventes.txt");

        // Transformation du RDD pour calculer le total des ventes par ville
        JavaPairRDD<String, Double> VentesParVille = lines.mapToPair(line -> {
                    String[] parts = line.split(" ");
                    String ville = parts[1];
                    Double prix = Double.parseDouble(parts[3]);
                    return new Tuple2<>(ville, prix);
        });
        JavaPairRDD<String,Double> totalVentesParVille= VentesParVille.reduceByKey(Double::sum);


        // Affichage du résultat
        totalVentesParVille.foreach(result ->
                System.out.println("Ville: " + result._1 + ", Total des ventes: " + result._2)
        );


    }
    }

