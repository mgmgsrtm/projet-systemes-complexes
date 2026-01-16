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


        public Evaluation(){
            coverage=0;
            rapidityScore=0;
            duplicateRate=0;
            coordinationScore=0;
            delays = new ArrayList<>();
        }

        public void computeMetrics() {
            computeCoverage();
            computeRapidity();
            duplicateRate();
            computeCoordination();
        }


        private void computeCoverage() {
            //TODO FOR ALEX
            
            // objectif final est donner
            //coverage = xxxx;
        }



        private void computeRapidity() { 
        	//rapidity_score= detection_rate / mean_delay 
        	//avec :
        	//detection_rate = detected_cows / total_cows
        	//mean_delay = Σ delay / detected_cows 
        	
        	// detection_rate
            double detectionRate = (double) detectedCows / totalCows;
            
            // mean_delay
            double sum = 0;
            for (int d : delays) {
                sum += d;
            }
            double meanDelay = sum / delays.size();
            
            // rapidity_score
            rapidityScore = detectionRate / meanDelay;
        }


        private void duplicateRate() { 
            if (totalCowsDetected == 0) {
                duplicateRate = 0;
                return;
            }
            duplicateRate = (double) duplicateDetections / totalCowsDetected + duplicateDetections;
        }



        private void computeCoordination() {
            //TODO
        }


        public void addDelay(int delay) {
            delays.add(delay);
        }






        //setters

        public void setExplored(){

        }
        
        public void setExplorable(){

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

        public void setPotentialConflicts(){

        }
        
        public void  setAvoidedConflicts(){

        }


        @Override
        public String toString() {
            return "...";
        }

    }

