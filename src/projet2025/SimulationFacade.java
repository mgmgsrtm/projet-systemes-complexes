package projet2025;

import java.util.ArrayList;
import java.util.List;

public class SimulationFacade {
	
	Environment env;
	ControlCenter cc;
	Drone[] drones;
    int intervalLaunch;
    Evaluation evl;

	public SimulationFacade(int matrice, int nbDrone, int intervalLaunch) {
		this.env = new Environment(matrice, matrice);
		this.cc = new ControlCenter(matrice);
        this.intervalLaunch = intervalLaunch;
        this.evl =new Evaluation();

		drones = new Drone[nbDrone];
       
        for (int i = 0; i < nbDrone; i++) {
            drones[i] = new Drone(i + 1, env, cc, evl);
        }
	}

	
	public void startSimulation(int duration) {
		env.initializeEnvironment();
        env.printEnvironment();
        env.printIfCowMap();
        System.out.println(env.cows);
        
        printSimulation();
        
        for (int t = 0; t < duration; t++) { // 300 秒

            // logique du départ des drones à intervalles réguliers　chaque (intervalLaunch) 
            for (int i = 0; i < drones.length; i++){
                if (t >= intervalLaunch * i  && drones[i].getState() == Drone.DroneState.WAITING){
                    drones[i].state = Drone.DroneState.MOVING;
                    System.out.println("Drone id" + drones[i].getId() + " started." );
                }
            }

            // comportement dans les états autres que WAITING
        	for (Drone d :drones) {
        		d.step();
        	}
            env.updateEnvironment();

            //environnement et le déplacement du drone sont mis à jour chaque seconde
            //mais l'affichage est effectué toutes les 10 secondes
            if (t % 10 == 0) { 
                printSimulation();
            }
            
            if (t % 30 == 0) {
            	System.out.println("time t = "+ t);
            	cc.printGlobalMap();
            }
            
            // pour une simulation réelle de 5 minutes avec des ticks toutes les secondes
//          try {
//               Thread.sleep(1000); // Chaque itération de la boucle correspond à une seconde de temps simulé
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }

        }
        System.out.println();
        System.out.println("============== l'etat final  ==============");
        System.out.println();
        printSimulation();
        cc.printGlobalMap();
        
        System.out.println("=========== Resultat de simulation ==========");
        runEvaluation(env);
        printEvaluationResults();

	}
	
	
	public void printSimulation() {
		//env.printRadLevelMap();
        env.printMap(); // Affichage global de l’environnement (vue observateur, non accessible aux drones)
        cc.printDroneMap(env.width, env.height); // Affichage des positions des drones
        // System.out.println("totalCellsExplored: " + cc.totalCellsExplored);
        System.out.println("totalCowsDetected: " + cc.totalCowsDetected);
        cc.printDroneStatus();
        System.out.println(cc.eventLog);
        for (Drone d :drones) {
        	System.out.println("time: " + d.missionTime);
        }

	}


    private void runEvaluation(Environment env) {

        // --- Collecte des valeurs ---
        // L'environnement compte lui-même ses cellules explorées/explorables
    	
    	
        evl.setExplored(env.countExploredCells());
        evl.setExplorable(env.countExplorableCells());

        evl.setTotalCows(env);
        evl.setDetectedCows(env);

        evl.settotalDetectionTentatives(cc.totalDetectionTentatives);
        evl.setDuplicateDetections(cc.duplicateDetections);
        evl.setTotalCowsDetected(cc.totalCowsDetected);
    

        evl.setPotentialConflicts(cc.potentialConflicts);
        evl.setAvoidedConflicts(cc.avoidedConflicts);

        // --- calcul des intdicateurs ---
        evl.computeMetrics();


    }


    private void printEvaluationResults() {
        // Affichage des indicateurs de couverture
        System.out.println("explored cell = " + evl.explored);
        System.out.println("explorable cell= " + evl.explorable);
        System.out.println("Coverage score= " + evl.coverage);
        System.out.println();


        // Affichage des indicateurs de detection de bovins
        System.out.println("Le temps qu’il a fallu pour la première détection des vaches");
        System.out.println(evl.delays);
        System.out.println("le temps moyen de localisation d’une vache : " + evl.meanDelay);
        System.out.println("nombre de cows total dans la zone = " + evl.totalCows);
        System.out.println("detectedCows = " + evl.detectedCows);

        System.out.println("totalDetectionTentative = " + evl.totalDetectionTentatives);
        System.out.println("nb de duplicate detection = " + evl.duplicateDetections);
        System.out.println("Detection rate = " + evl.detectionRate);
        System.out.println("Detection rapidity score = " + evl.rapidityScore);
        System.out.println();

        System.out.println("Duplicate rate = " + evl.duplicateRate);
        System.out.println("Avoidance rate = " + evl.avoidanceRate);
        System.out.println("Coordination score = " + evl.coordinationScore);
        System.out.println();
        
        System.out.println("GLOBAL SCORE : " + evl.globalScore);
    }

    public double getGlobalScore() {
        return evl.globalScore;
    }
	
    
	//main No.1
	// Une exécution de la simulation
    
	// public static void main(String[] args) {
		
	// 	SimulationFacade simulation = new SimulationFacade(10, 7, 5);
	// 	simulation.startSimulation(300);
		
	
    // }

    
    
    
    //main No.2
    //Simulation répétée 20 fois pour l’évaluation

    public static void main(String[] args) {

        int RUNS = 20;
        double sum = 0;
        List<Double> results = new ArrayList<>();

        for (int i = 1; i <= RUNS; i++) {

            SimulationFacade sim =
                new SimulationFacade(30, 7, 0);

            sim.startSimulation(300);   // 実行
            double score = sim.getGlobalScore();  
            results.add(score);
            sum += score;
        }

        //20回実行した平均を計算
        double mean = sum / RUNS; 
        System.out.println();
        System.out.println("=========== GLOBAL RESULTS ===========");
        System.out.println("All scores = " + results);
        System.out.println("Mean global score = " + mean);
    }


}