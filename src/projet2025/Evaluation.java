package projet2025;

import java.util.List;

public class Evaluation {

        // variavle à utiliser 
        int explored;
        int explorable;

        int totalCows;
        int detectedCows;
        List<Integer> delays;

        int totalDetectionEvents;
        int duplicateDetections;

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
            //TODO 
        }


        private void duplicateRate() { 
            //TODO 
        }



        private void computeCoordination() {
            //TODO
        }



        @Override
        public String toString() {
            return "...";
        }
    }
