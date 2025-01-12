# README : Applications Spark pour l'analyse des ventes

Ce projet contient deux applications Spark permettant d'analyser les données de ventes d'une entreprise à partir d'un fichier texte `ventes.txt`. Ces applications sont conçues pour être testées en local et peuvent être déployées sur un cluster Spark pour traiter des volumes de données importants.

## Structure du fichier d'entrée

Le fichier d'entrée `ventes.txt` doit avoir la structure suivante :

```
date ville produit prix
```

Chaque ligne représente une vente et contient les informations suivantes :
- **date** : La date de la vente au format `yyyy-MM-dd` (ex. `2023-01-01`).
- **ville** : La ville où la vente a été réalisée (ex. `Paris`).
- **produit** : Le produit vendu (ex. `Téléphone`).
- **prix** : Le prix de la vente en euros (ex. `500`).

Exemple de contenu du fichier `ventes.txt` :

```
2023-01-01 Paris Téléphone 500
2023-01-02 Lyon Ordinateur 1000
2023-01-03 Marseille Télévision 700
2023-01-04 Paris Ordinateur 1200
2022-12-31 Lyon Téléphone 450
2022-12-30 Marseille Télévision 800
```

## Applications développées

### 1. Application : Total des ventes par ville

Cette application calcule le total des ventes pour chaque ville à partir du fichier d'entrée.

#### Fonctionnement :
1. Charger les données du fichier `ventes.txt`.
2. Transformer les données pour extraire les paires `(ville, prix)`.
3. Calculer le total des ventes pour chaque ville à l'aide d'une agrégation.
4. Afficher les résultats ou les sauvegarder dans un fichier de sortie.

#### Exemple de sortie :
<img width="481" alt="Screenshot 2025-01-12 at 17 05 02" src="https://github.com/user-attachments/assets/d8034ea6-cc21-4b14-825e-847e4b398a4d" />

Voici le code correspondant :

```java
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
```

### 2. Application : Total des ventes par ville pour une année donnée

Cette application calcule le total des ventes des produits pour chaque ville, mais uniquement pour une année spécifique.

#### Fonctionnement :
1. Charger les données du fichier `ventes.txt`.
2. Filtrer les lignes pour conserver uniquement celles correspondant à l'année cible.
3. Transformer les données pour extraire les paires `(ville, prix)`.
4. Calculer le total des ventes pour chaque ville à l'aide d'une agrégation.
5. Afficher les résultats ou les sauvegarder dans un fichier de sortie.

#### Exemple de sortie pour l'année `2022` :

<img width="481" alt="Screenshot 2025-01-12 at 17 04 16" src="https://github.com/user-attachments/assets/c3152361-e396-4e38-87db-af0bd0ef123c" />

#### Exemple de sortie pour l'année `2023` :

<img width="481" alt="Screenshot 2025-01-12 at 17 04 49" src="https://github.com/user-attachments/assets/bcd3d2d7-04a9-42ad-9a0d-652eb952e620" />

Voici le code correspondant :

```java
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
```


## Déploiement sur un cluster Spark

1. Construisez un fichier JAR exécutable :
```bash
mvn package
```

2. Déployez le fichier JAR sur le cluster Spark et exécutez les applications en utilisant la commande suivante :
```bash
spark-submit --class <NomDeLaClassePrincipale> --master <ModeDuCluster> <CheminVersLeJAR> <Arguments>
```


