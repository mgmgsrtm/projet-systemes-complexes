package projet2025;

public class SimulationFacade {
	
	Environment env;
	ControlCenter cc;
	Drone[] drones;
    int intervalLaunch;

	public SimulationFacade(int matrice, int nbDrone, int intervalLaunch) {
		this.env = new Environment(matrice, matrice);
		this.cc = new ControlCenter(matrice);
        this.intervalLaunch = intervalLaunch;

		drones = new Drone[nbDrone];
       
        for (int i = 0; i < nbDrone; i++) {
            drones[i] = new Drone(i + 1, env, cc);
        }
	}

	
	public void startSimulation(int duration) {
		env.initializeEnvironment();
        env.printEnvironment();
        env.printIfCowMap();
        
        printSimulation();
        
        for (int t = 0; t < duration; t++) { // 300 秒

            // logique du départ des drones à intervalles réguliers　chaque (intervalLaunch) 
            for (int i = 0; i < drones.length; i++){
                if (t >= intervalLaunch * i  && drones[i].getState() == Drone.DroneState.WAITING){
                    drones[i].state = Drone.DroneState.MOVING;
                    System.out.println("Drone id:" + drones[i].getId() + " started." );
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
            
            try {
                Thread.sleep(1000); // Chaque itération de la boucle correspond à une seconde de temps simulé
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
	}
	
	
	public void printSimulation() {
		env.printRadLevelMap();
        env.printMap(drones); // Affichage global de l’environnement (vue observateur, non accessible aux drones)
        System.out.println("totalCellsExplored: " + cc.totalCellsExplored);
        System.out.println("totalCowsDetected: " + cc.totalCowsDetected);
        cc.printDroneStatus();
        System.out.println(cc.eventLog);
        for (Drone d :drones) {
        	System.out.println("time: " + d.missionTime);
        }

	}
	
	
	
	public static void main(String[] args) {
		
		SimulationFacade simulation = new SimulationFacade(20, 7, 10);
		simulation.startSimulation(300);
		
	
    }

}
