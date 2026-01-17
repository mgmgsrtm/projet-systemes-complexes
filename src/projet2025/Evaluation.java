package projet2025;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

        // variavle à utiliser 
        int explored;
        int explorable;

        int totalCows;
        int detectedCows;
        List<Integer> delays;
        double meanDelay;
        double detectionRate;

        int totalDetectionTentatives;
        int duplicateDetections;
        int totalCowsDetected;

        int potentialConflicts;
        int avoidedConflicts;

        // --- computed metrics ---
        double coverage;
        double rapidityScore;
        double duplicateRate;
        double coordinationScore;
        double avoidanceRate;


        public Evaluation(){
            coverage=0;
            rapidityScore=0;
            duplicateRate=0;
            coordinationScore=0;
            delays = new ArrayList<>();
        }

        public void computeMetrics() {
            computeCoverage();
            computeMeanDelay ();
            computeRapidity();
            duplicateRate();
            computeAvoidanceRate() ;
            computeCoordination();
        }


        private void computeCoverage() {
            // Calculer le taux de couverture
            if (explorable == 0) {
                coverage = 0; // Éviter division par zéro
                return;
            }
            coverage = (double) explored / (double) explorable;
        }

        private void computeMeanDelay () {
        	detectionRate = (double) detectedCows / totalCows;
        	double sum = 0;
        	 for (int d : delays) {
        		 sum += d;
        	 }
        	 meanDelay = sum / (double)(delays.size());
        }


        private void computeRapidity() { 
        	computeMeanDelay () ;
            // rapidity_score
            rapidityScore = 100*(detectionRate / meanDelay);
        }


        private void duplicateRate() { 
            if (totalCowsDetected == 0) {
                duplicateRate = 0;
                return;
            }
            duplicateRate = (double)duplicateDetections / ((double)totalCowsDetected + (double)duplicateDetections);
        }


        private void computeAvoidanceRate() {
            if (potentialConflicts == 0) {
                avoidanceRate = 1; //quand il n'a aucun chevauchement de drone
                return;
            }
            avoidanceRate = (double) avoidedConflicts / (double)potentialConflicts;
        }

        private void computeCoordination() {
            double alpha = 0.5; //要検討
            double beta  = 0.5; //要検討
            coordinationScore = alpha * (1 - duplicateRate) + beta  * avoidanceRate;
        }


        public void addDelay(int delay) {
            delays.add(delay);
        }






        //setters

        /**
         * Définir le nombre de cellules explorées
         * @param count nombre de cellules explorées
         */
        public void setExplored(int count){
            this.explored = count;
        }
        
        /**
         * Définir le nombre de cellules explorables
         * @param count nombre de cellules accessibles
         */
        public void setExplorable(int count){
            this.explorable = count;
        }
        
        public void setTotalCows(Environment env){
            this.totalCows = env.cows.size();
        }
        
        public void setDetectedCows(Environment env){
                this.detectedCows = this.delays.size();
        }

        public void settotalDetectionTentatives(int v){
            this.totalDetectionTentatives = v;
        }
        
        public void setDuplicateDetections(int v){
            this.duplicateDetections = v;
        }

        public void setTotalCowsDetected(int v){
            this.totalCowsDetected = v;
        }

        public void setPotentialConflicts(int n){
            this.potentialConflicts = n;
        }
        
        public void  setAvoidedConflicts(int n){
            this.avoidedConflicts = n;
        }


        @Override
        public String toString() {
            return "...";
        }

    }