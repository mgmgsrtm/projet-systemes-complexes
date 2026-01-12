# Projet IA pour les Systèmes Complexes
MIASHS 2025

Système Autonome de Drones Coopératifs pour la Surveillance d’Environnements Sensibles

---

#### Tâches à prioriser avant la remise
- [done] Actuellement  7 drones partent plusieurs fois et plusieurs drones détectent à répétition la même vache → modifier le comportement afin de ne pas la signaler si elle existe déjà en comparaison avec la globalMap
- [done] Faire en sorte que les drones partent avec un décalage dans le temps.
- [done] Pour l’affichage dans l’interface, réorganiser les méthodes print Environnement ainsi que le roll de GlobalCellInfo.
- <span style="color: red;">Visualiser l’efficacité de l’exploration, les indicateurs et l’évaluation.</span>


<span style="color: red;">L’instruction ci-dessous doit être revue pour l’évaluation et la définition des indicateurs selon le PDF reçu du professeur</span>

7. Tests et simulations. 
Concevoir et exécuter différents scénarios de simulation permettant d’évaluer :
• la couverture de la zone,
• la rapidité de détection,
• la coordination entre les drones,
• l’efficacité du système global.　</span>

##### À l’étude
- Les nouveaux hotspots qui apparaissent en cours de route ne sont pas visualisés sur la carte
- (déplacement possible des vaches??...depend de supprimer ou non attribut cowHandled)

---
#### Auteurs
Haruka MIURA, Aleksandra BASANGOVA

---


README.md

## 1. Description de Projet

Ce projet modélise une mission de cartographie et de surveillance réalisée par des drones dans un environnement soumis à des risques radiologiques.
L’environnement évolue de manière autonome, notamment par l’apparition de zones dangereuses.
Dans l’environnement que nous considérons, l’exploration est réalisée par un essaim de drones qui évitent les zones à forte radioactivité et signalent les bovins présents dans les zones de pâturage.
Les décisions sont prises sous information imparfaite, les drones ne mettant à jour leur carte globale qu’après un retour à la base.

<br>


## 2. Modélisation 



#### la détection des anomalies

Les anomalies environnementales sont modélisées sous forme de :

- zones à radiation élevée (hotspots) 
- niveaux :
  - zone sûre  
  - zone à accès limité  
  - zone interdite  

Le système permet :

- de détecter la présence d’anomalies,
- d’estimer leur intensité (niveau de radiation),
- de localiser les zones nécessitant une intervention.
Les drones signalent la présence de bovins dans les zones de pâturage.
<br><br>

#### Système de communication et synchronisation

Le système respecte les contraintes suivantes :

- Chaque drone peut transmettre ses données au centre de contrôle depuis sa position(情報：牛の有無、放射線).
- Les drones **ne reçoivent les mises à jour globales qu’en retournant à la base**.
- Le centre de contrôle maintient :
  - une carte globale,
  - un journal d’événements,
  - l’état des drones.

Cela simule un environnement à **information imparfaite**.

<br><br>



#### Gestion énergétique et contraints

Le système intègre un modèle énergétique :

- Autonomie maximale : **180 secondes**
- Retour automatique à la base après expiration
- Temps de recharge : **20 secondes**
- Analyse d’une anomalie : **10 secondes**
- Le déplacement consomme peu d’énergie, 帰還時のMap上の移動は省いた。

Les drones alternent entre :

- en attente,
- deplacement,
- analyser,
- retour,
- recharge

<br><br>

#### Modélisation de l’évolution de l’environnement

L’environnement est dynamique :

- Les hotspots apparaissent aléatoirement
- Propagation locale autour du point central
- Décroissance radioactive modélisée par une loi exponentielle (demi-vie)
- Apparition de nouveaux hotspots


<br><br>


####  Interface utilisateur

Une interface textuelle en Java permet :

TODO


---

## 3. Architecture du système

#### Principales classes

| Classe | Rôle |
|--------|------|
| `Environment` | Modélisation de la grille et dynamique environnementale |
| `Cell` | Représentation d’une cellule |
| `Drone` | Agent autonome |
| `ControlCenter` | Fusion des informations |
| `GlobalCellInfo` | Carte globale |
| `SimulationFacade` | Lancement et orchestration |

---

## 4. Scénarios de simulation

//TODO

Différents scénarios sont testés en faisant varier :

- le nombre de drones,
- l’intervalle de lancement,
- la fréquence des hotspots,
- la taille de la carte.

---

## 5. Indicateurs d’évaluation

//TODO

#### 5.1 Couverture de la zone

#### 5.2 Rapidité de détection

#### 5.3 Coordination entre drones

- Taux de redondance (explorations multiples)

---

## 6. Lancement de la simulation

//TODO

```
javac projet2025/*.java
java projet2025.SimulationFacade
```
---

## 7. Paramètres

Dans main() :

```
new SimulationFacade(x, 7, y);
```
|Paramètre | Description|
|--------|------|
|x |taille de la carte|
|7 | nombre de drones |
|y |intervalle de lancement |

---

## 8. Résultats

//TODO

---

## 9. Améliorations futures

//TODO