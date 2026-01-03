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
1. Actuellement  7 drones partent plusieurs fois et plusieurs drones détectent à répétition la même vache → modifier le comportement afin de ne pas la signaler si elle existe déjà en comparaison avec la globalMap（déplacement possible des vaches??）
2. Faire en sorte que les drones partent avec un décalage dans le temps.
3. Pour l’affichage dans l’interface, réorganiser les méthodes print Environnement ainsi que le roll de GlobalCellInfo.
4. Visualiser l’efficacité de l’exploration, les indicateurs et l’évaluation.

#### L’instruction ci-dessous doit être revue
7. Tests et simulations
Concevoir et exécuter différents scénarios de simulation permettant d’évaluer :
• la couverture de la zone,
• la rapidité de détection,
• la coordination entre les drones,
• l’efficacité du système global.



---
#### Auteurs
Haruka MIURA, Aleksandra BASANGOVA