# Projet IA pour les Systèmes Complexes
MIASHS 2025

Système Autonome de Drones Coopératifs pour la Surveillance d’Environnements Sensibles

---
#### Auteurs
Haruka MIURA, Aleksandra BASANGOVA

---


## 1. Description de Projet

Ce projet consiste à développer un système de simulation multi-drones destiné à la surveillance d’une zone contaminée. Sept drones autonomes explorent un environnement représenté sous forme de grille afin de détecter des anomalies (vaches laissés), cartographier les niveaux de radiation et transmettre les informations à un centre de contrôle.
L’objectif principal de drone est de localiser un maximum de vaches et d’enregistrer leurs positions avec la plus grande précision possible, tout en évitant les zones fortement irradiées. Ces informations serviront à faciliter de futures opérations de secours menées par des humains ou des machines.
Une autre mission essentielle consiste à surveiller l’évolution de l’environnement, notamment les evolution de radiation dues à la demi-vie radioactive et à l’apparition de nouveaux hotspots, afin de maintenir une cartographie toujours à jour.
Enfin, l’efficacité globale du système est évaluée à travers plusieurs indicateurs de performance tels que la couverture de la zone, la rapidité de détection et la coordination entre les drones.

<br>


## 2. Modélisation 



#### la détection des anomalies

Les anomalies environnementales sont modélisées sous forme de 
zones à radiation élevée (hotspots)
il y a trois niveaux de danger :
		- zone sûre
		- zone à accès limité
		- zone interdite
Les anomalies sont également représentées par des objets de type Cow, placés aléatoirement dans l’environnement. Lorsqu’un drone entre dans une cellule contenant une vache, il passe en mode ANALYSING pendant 10 secondes afin de confirmer la détection.
Le système permet :
- de détecter la présence d’anomalies,
- d’estimer leur intensité (niveau de radiation),
- de localiser les zones nécessitant une intervention.

Les drones signalent la présence de bovins dans les zones de pâturage. Une fois la détection confirmée, l’information est transmise au centre de contrôle avec les coordonnées exactes et le niveau de radiation mesuré.

<br><br>

#### Système de communication et synchronisation

Chaque drone est connecté à un ControlCenter qui centralise toutes les informations détectées. Le système respecte les contraintes suivantes :
- Chaque drone peut transmettre ses données au centre de contrôle depuis sa position (présence de vache, niveau de radiation).
- Les drones ne reçoivent les mises à jour de la cartographie qu’en retournant à la base.
- Le centre de contrôle maintient :
  - une carte globale,
  - un journal des détections,
  - l’état de chaque drone.

Cela permet de simuler un environnement à information **imparfaite**.

Le centre de contrôle gère pas directement des conflits potentiels (plusieurs drones visant la même cellule). L’évitement des collisions repose non pas sur des captures directs entre les drones, mais sur la consultation des positions des drones auprès du centre de contrôle. Chaque drone adapte ensuite sa trajectoire en fonction des informations reçues.


<br><br>



#### Gestion énergétique et contraints

Chaque drone dispose :
- d’un temps de mission limité (180 secondes)
- d’un mode RETURNING pour rentrer à la base (le trajet de retour est simplifié : seul l’état change, sans déplacement réel),
- d’un mode CHARGING nécessitant 20 secondes de recharge.

En principe, un drone ne doit jamais entrer dans une zone interdite, car on suppose que la radiation pourrait endommager ses composants. Cependant, en cas d’apparition soudaine d’un hotspot, si un drone détecte un niveau de radiation interdit, il déclenche immédiatement une évacuation d’urgence.

Les drones alternent entre :

- en attente
- deplacement
- analyser
- retour
- recharge

<br><br>

#### Modélisation de l’évolution de l’environnement

Au démarrage de la simulation, 4 à 5 hotspots sont générés aléatoirement dans la matrice. Les vaches apparaissent également de manière aléatoire.
L’environnement évolue dynamiquement :
- décroissance radioactive basée sur une demi-vie,
- apparition aléatoire de nouveaux hotspots,
- déplacement lent des vaches toutes les 40 secondes vers des cellules adjacentes.

À l’origine, le projet devait utiliser des humains, mais ce choix a été abandonné en raison de problématiques éthiques et de complexité (blessure, décès, mobilité, secours, etc.). Les humains ont donc été remplacés par des vaches, supposées paître tranquillement dans une zone rurale.
Les vaches ne se multiplient pas : leur nombre reste constant tout au long de la simulation.



<br><br>


####  Interface utilisateur

lors de l'execution de main de class SimulationFacade, l'interface console affiche  :

- la carte réelle de l’environnement
- la carte connue du centre de contrôle
- les positions des drones
- les niveaux de radiation
- les événements de détection
- les valeurs des indicateurs et global score


**Affichage de la carte dans la console**
Chaque cellule de la grille est représentée par un symbole indiquant son état :

C : bovin 
c : bovin dans ~
! : bovin dans X
X : niveau de radiation interdit
~ : niveau de radiation limmitée
D : Drone
? : celulle non explorée


---

## 3. Architecture du système

#### Principales classes

| Classe | Rôle |
|--------|------|
| `Environment` | Gestion de la grille et de l’évolution dynamique de l’environnement (radiation, hotspots, déplacement des vaches) |
| `Cell` | Représentation d’une cellule de la grille (radiation, exploration, présence de vache, etc.) |
| `Drone` | Agent autonome chargé de l’exploration et de la détection des anomalies |
| `ControlCenter` | Centralisation et fusion des informations transmises par les drones |
| `GlobalCellInfo` | Stockage de la carte globale connue par le centre de contrôle |
| `Cow` | Représentation des vaches (anomalies) avec position, temps de génération et état de détection |
| `Evaluation` | Calcul des indicateurs de performance (coverage, rapidité, duplication, coordination, score global) |
| `SimulationFacade` | Lancement, configuration et orchestration de la simulation |


---

## 4. Scénarios de simulation

Différents scénarios sont testés :

- Variation de la taille de la carte
10×10, 20×20, 30×30
- Positionnement de la base
centre ou coin supérieur gauche
- Variation de l’intervalle de départ des drones
0, 5, 10 secondes 

Paramètres restant constants :
- 7 drones,
- durée de simulation identique : 5 minutes.

---

## 5. Indicateurs d’évaluation

**5-1. indicateurs**
Plusieurs indicateurs sont calculés :
-  **1. Coverage　score** :	Pourcentage de cellules explorées.
<br>
-  **2. DtectionRate** :  nb de bovins detecte / nb de bovins dans la zone
-  **3. Rapidity score**	
  Basé sur : le taux de détection, le délai moyen de localisation.
<br>
-  **4. Coordination score**	
  Combinaison de : (1 - duplicate_rate) , avoidance_rate.
   Basé sur : 
**Duplicate rate**	
  Nombre de détections redondantes / nombre total de détections.
**Avoidance rate**	
  Conflits évités / conflits potentiels.

**5-2. Conception du score global**

L’indicateur de coordination mesure uniquement :
- l’absence de collisions,
- les détections redondantes d’une même vache.

Ainsi, quelle que soit la configuration, il atteint presque toujours une valeur élevée (≈ 0,97). Cependant, une bonne coordination ne signifie pas forcément une bonne performance globale, surtout dans un objectif de recherche et de sauvetage. (Dans ce projet, l’objectif principal est détecter le plus de vaches possible, le plus rapidement possible.) Par conséquent, Coverage et Detection Rate, Rapidity sont prioritaires, main la coordination joue un rôle secondaire.

**globalScore** = 
0.25 × detectionRate 
\+ 0.20 × rapidityScore
\+ 0.45 × coverage
\+ 0.10 × coordinationScore


---
## 6. Résultats

| Carte | Base | Intervalle | Résultat moyen (20 exécutions) |
|-------|------|------------|---------------------------------|
|10×10 | coin |	0 |	1.325 |
|10×10 | coin	| 5	| 1.205 |
|10×10 | coin |	10 | 1.127 |
|10×10 | centre	| 0	| 1.525 |
|10×10 | centre | 5 | 1.274 |
|10×10 | centre | 10 | 1.146 |
|20×20 | coin |	0	| 0.920 |
|20×20 | coin |	5	| 0.870 |
|20×20 | coin |	10 | 0.866 |
|20×20 | centre |	0	| 0.977 |
|20×20 | centre	| 5	| 0.952 |
|20×20 | centre	| 10 | 0.919 |
|30×30 | coin	| 0	| 0.534 |
|30×30 | coin	| 5	| 0.557 |
|30×30 | coin	| 10 | 0.525 |
|30×30 | centre |	0	| 0.713 |
|30×30 | centre	| 5	| 0.716 |
|30×30 | centre | 10 | 0.667 |


Carte 10×10 est trop petite pour une évaluation pertinente :
- exploration quasi immédiate,
- détection très rapide,
Par consequence nous avons meanDelay extrêmement faible et rapidityScore élevé.
Le facteur ×100 dans la formule peut rendre la rapidité trop dominante. La formule doit avoir besoin d’amélioration.

Tendances générales observées (20×20, 30×30)
- Plus la carte est grande, plus le score diminue → logique (exploration plus difficile)
- Base au centre > base dans un coin → plus efficace
- Intervalle : 0 > 5 > 10→ le départ simultané est parfois plus efficace car l’algorithme évite seulement les collisions

**Carte 20×20**
Résultats très influencés par :
- le nombre de vaches
- nombre et position des hotspots aléatoires

La base centrale donne de meilleurs résultats et moins de variations.

**Carte 30×30**
La position de la base devient déterminante.
Difficile de détecter toutes les vaches.
Moins de doublons car :les vaches se déplacent, elles sont rarement redétectées.


---

## 7. Améliorations futures

//TODO