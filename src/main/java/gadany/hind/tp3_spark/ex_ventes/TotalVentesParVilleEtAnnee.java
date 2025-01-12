package gadany.hind.tp3_spark.ex_ventes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class TotalVentesParVilleEtAnnee {

    public static void main(String[] args) {
        // Configuration Spark
        SparkConf conf = new SparkConf().setAppName("TotalVentesParVilleEtAnnee").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Année cible (peut être passée en paramètre)
        int targetYear = 2022;

        // Chargement du fichier ventes.txt en tant que RDD
        JavaRDD<String> lines = sc.textFile("ventes.txt");

        // Transformation du RDD : extraction (ville, prix) et filtrage par année
        JavaPairRDD<String, Double> ventesParVille = lines
                .filter(line -> {
                    String[] parts = line.split(" ");
                    String date = parts[0]; // La date est dans la 1ère colonne
                    int year = Integer.parseInt(date.split("-")[0]); // Extraire l'année
                    return year == targetYear; // Garder uniquement les lignes de l'année cible
                })
                .mapToPair(line -> {
                    String[] parts = line.split(" ");
                    String ville = parts[1]; // La ville est dans la 2ème colonne
                    Double prix = Double.parseDouble(parts[3]); // Le prix est dans la 4ème colonne
                    return new Tuple2<>(ville, prix);
                });

        // Calcul du total des ventes par ville pour l'année cible
        JavaPairRDD<String, Double> totalVentesParVille = ventesParVille.reduceByKey(Double::sum);

        // Affichage des résultats
        totalVentesParVille.foreach(result ->
                System.out.println("Ville: " + result._1 + ", Total des ventes pour " + targetYear + ": " + result._2)
        );


    }
}
