package projet2025;

public class SimulationFacade {
	
	Environment env;
	ControlCenter cc;
	Drone[] drones;

	public SimulationFacade(int matrice, int nbDrone) {
		this.env = new Environment(matrice, matrice);
		this.cc = new ControlCenter();
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
        	for (Drone d :drones) {
        		d.step();
        	}
            env.updateEnvironment();

            if (t % 10 == 0) { //環境更新とドローンの移動は毎秒行われているが、10秒に1回だけ表示
                printSimulation();
            }
            
            try {
                Thread.sleep(1000); // 1秒待つ（＝実時間で1秒）
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
	}
	
	
	
	public static void main(String[] args) {
		
		SimulationFacade simulation = new SimulationFacade(20, 7);
		simulation.startSimulation(300);
		
	
    }

}
