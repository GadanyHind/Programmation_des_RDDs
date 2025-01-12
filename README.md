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
```
Ville: Paris, Total des ventes: 1700.0
Ville: Lyon, Total des ventes: 1450.0
Ville: Marseille, Total des ventes: 1500.0
```

### 2. Application : Total des ventes par ville pour une année donnée

Cette application calcule le total des ventes des produits pour chaque ville, mais uniquement pour une année spécifique.

#### Fonctionnement :
1. Charger les données du fichier `ventes.txt`.
2. Filtrer les lignes pour conserver uniquement celles correspondant à l'année cible.
3. Transformer les données pour extraire les paires `(ville, prix)`.
4. Calculer le total des ventes pour chaque ville à l'aide d'une agrégation.
5. Afficher les résultats ou les sauvegarder dans un fichier de sortie.

#### Exemple de sortie pour l'année `2023` :
```
Ville: Paris, Total des ventes pour 2023: 1700.0
Ville: Lyon, Total des ventes pour 2023: 1000.0
Ville: Marseille, Total des ventes pour 2023: 700.0
```
  
