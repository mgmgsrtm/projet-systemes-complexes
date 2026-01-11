#### Projet IA pour les Systèmes Complexes
MIASHS 2025

Système Autonome de Drones Coopératifs pour la Surveillance d’Environnements Sensibles

---

#### Description de Projet

Ce projet modélise une mission de cartographie et de surveillance réalisée par des drones dans un environnement soumis à des risques radiologiques.
L’environnement évolue de manière autonome, notamment par l’apparition de zones dangereuses.
Dans l’environnement que nous considérons, l’exploration est réalisée par un essaim de drones qui évitent les zones à forte radioactivité et signalent les bovins présents dans les zones de pâturage.
Les décisions sont prises sous information imparfaite, les drones ne mettant à jour leur carte globale qu’après un retour à la base.

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