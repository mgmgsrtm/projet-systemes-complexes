## Stratégie d’implémentation de l’évaluation
### 1. Indicateurs
#### 1.1 Couverture de la zone
Indicateur utilisé
<span style="color:red;">coverage </span> = explored / explorable
* explored : nombre de cellules explorées
* explorable : nombre de cellules accessibles

Variables à implémenter
* explored
* explorable
Remarque : explorable correspond au nombre total de cellules non interdites (zones avec radiation ≤ seuil interdit).

#### 1.2 Rapidité de détection
Améliorations à ajouter
* Génération dynamique des bovins
* Enregistrement du temps d’apparition

Indicateurs utilisés
<span style="color:red;">rapidity_score</span> = detection_rate / mean_delay <br>
avec :
detection_rate = detected_cows / total_cows
mean_delay = Σ delay / detected_cows 
<br>

* delay = detectionTime - cowSpawnTime

Variables à implémenter
* cowID (identifiant unique pour chaque bovin)
* timestamp (temps d’apparition et de détection)

#### 1.3 Coordination entre drones
**Indicateur 1** : Taux de redondance
<span style="color:red;">duplicate_rate</span> = nombre de détections redondantes
                 / nombre total d’événements de détection
* Détection redondante : deuxième détection (ou plus) du même cowID
* Événements totaux : toutes les détections rapportées par les drones

Variables à implémenter
* Compteur des détections redondantes
* Compteur des détections totales

**Indicateur 2** : Score de coordination
<span style="color:red;">coordination_score</span> =
 α * (1 - duplicate_rate)　\+ β * avoidance_rate
avec :
avoidance_rate = conflits évités / conflits potentiels

Variables à implémenter
* potentialConflicts → nombre de fois où plusieurs drones visent la même cellule
* avoidedConflicts → nombre de conflits effectivement évités

<br><br>

### 2. Efficacité globale du système
Le nombre de drones est fixé à 7 (conformément aux contraintes de l’interface utilisateur).
L’efficacité globale est définie comme une combinaison des indicateurs suivants :
* coverage
* rapidity_score
* 1 - duplicate_rate
* coordination_score

<br><br>

### 3. Scénarios de simulation
**Scénario A** – Impact de la taille de la carte
Tous les paramètres sont fixés sauf :
* Taille de la carte (3 tailles différentes)

Objectif :
mesurer l’impact de la taille de l’environnement sur l’efficacité globale du système

**Scénario B** – Dynamique environnementale
Tous les paramètres sont fixés sauf :
* Demi-vie radioactive
* Fréquence d’apparition des hotspots (3 configurations différentes)

Objectif :
analyser l’impact de la dynamique environnementale sur l’efficacité globale du système


---

Résultat