# Projet IA pour les Systèmes Complexes
MIASHS 2025

Système Autonome de Drones Coopératifs pour la Surveillance d’Environnements Sensibles

---

#### Tâches à prioriser avant la remise
- [done] Actuellement  7 drones partent plusieurs fois et plusieurs drones détectent à répétition la même vache → modifier le comportement afin de ne pas la signaler si elle existe déjà en comparaison avec la globalMap
- [done] Faire en sorte que les drones partent avec un décalage dans le temps.
- [done] Pour l’affichage dans l’interface, réorganiser les méthodes print Environnement ainsi que le roll de GlobalCellInfo.
- [done] déplacement possible des vaches??...depend de supprimer ou non attribut cowHandled
- <span style="color: red;">Visualiser l’efficacité de l’exploration, les indicateurs et l’évaluation.</span>

<span style="color: red;">L’instruction ci-dessous doit être revue pour l’évaluation et la définition des indicateurs selon le PDF reçu du professeur</span>

7. Tests et simulations. 
Concevoir et exécuter différents scénarios de simulation permettant d’évaluer :
• la couverture de la zone,
• la rapidité de détection,
• la coordination entre les drones,
• l’efficacité du système global.　</span>

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

lors de l'execution de main de class SimulationFacade, l'interface console affiche  :

la carte réelle de l’environnement,
la carte connue du centre de contrôle,
les positions des drones,
les niveaux de radiation,
les événements de détection.
resultat de simulation : valeurs des indicateurs 



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

Différents scénarios sont testés :

- Variation de la taille de la carte
(10×10, 20×20, 30×30 – départ en haut à gauche, intervalle 5).
- Positionnement de la base
(centre ou coin supérieur gauche – carte 20×20, intervalle 5).
- Variation de l’intervalle de départ des drones
(0, 5, 10 secondes – départ en haut à gauche).

Tous les autres paramètres restent constants :
- 7 drones,
- durée de simulation identique : 5 minutes.

---

## 5. Indicateurs d’évaluation

Plusieurs indicateurs sont calculés :
-  **Coverage** :	Pourcentage de cellules explorées.
-  **Rapidity score**	
  Basé sur : le taux de détection, le délai moyen de localisation.
-  **Duplicate rate**	
  Nombre de détections redondantes / nombre total de détections.
-  **Avoidance rate**	
  Conflits évités / conflits potentiels.
-  **Coordination score**	
  Combinaison de : (1 - duplicate_rate) , avoidance_rate.
---
## 6. Résultats

//TODO

---

## 7. Améliorations futures

//TODO