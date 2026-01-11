package projet2025;

import java.util.ArrayList;
import java.util.List;

public class Drone {
	
	int id;         //droneID
    int x, y;       //positionnement
	Environment environment;
	ControlCenter controlCenter;
	DroneState state;
    int analyseRemainingTime = 0;
    boolean[][] localExplored;
    GlobalCellInfo [][] localGlobalView;
    
    int missionTime;
    static final int MAX_MISSION_TIME = 180;
    
    int chargingTime;
    static final int CHARGING_DURATION = 20;
	
	enum DroneState {
		WAITING,
	    MOVING,
	    ANALYSING,
	    RETURNING,
	    CHARGING
	}

	public Drone(int id, Environment environment, ControlCenter controlCenter) {
		this.id = id;
		this.environment = environment;
		this.controlCenter = controlCenter;
		this.x = environment.baseX; //pisitionnement initialle est base
        this.y = environment.baseY;
		this.state = DroneState.WAITING;
		controlCenter.registerDrone(this); // chaque drone s’enregistre automatiquement auprès de control center lors de sa création
		
		this.localExplored = new boolean[environment.width][environment.height]; //local環境の初期化
		localExplored[x][y] = true; //base は既知
		
		this.localGlobalView = controlCenter.getGlobalMapCopy();
		
		missionTime = 0;
	
		chargingTime = 0;
		
	}

	public int getX() { 
		return x; 
	}

	public int getY() { 
		return y; 
	}

	public DroneState getState() {
		return state; 
	}

	public int getId() {
		return id;
	}

	public int getBaseX() {
        return environment.baseX;
    }

    public int getBaseY() {
        return environment.baseY;
    }
    

	public void step() {
		if (state == DroneState.WAITING) {
	        return; // drone n'est pas encore parti
	    }
		if (state != DroneState.CHARGING) {
		    missionTime++;
		}
		if (missionTime >= MAX_MISSION_TIME && state == DroneState.MOVING) {
	        state = DroneState.RETURNING;
	    }
	    if (state == DroneState.RETURNING) {
	        returnToBase();
	        return;
	    }
	    if (state == DroneState.CHARGING) {
			 chargingTime++;   // 1 seconde de recharge
			 System.out.println("charging...");
		     System.out.println("time: " + this.chargingTime);
			 if (chargingTime >= CHARGING_DURATION) {
			        restart();    // il faut attendre 20 secondes pour restart
			}
			return;
		}
		if (state == DroneState.ANALYSING) {
			
			
	        analyseRemainingTime--;

	        if (analyseRemainingTime <= 0) {
	            finishAnalyse();
	        }
	        return; // analyse中は移動しない
	    }
		
		//avancer ver une cellule non explorée
		List<Cell> neighbors = getNeighbors(); // avoir la liste des cellules voisines accessibles
		Cell nextc = chooseNextCell(neighbors); // parcourt la liste et sélectionner une cellule non explorée

		if (controlCenter.isCellOccupied(nextc.x, nextc.y, this)) {
			neighbors.remove(nextc); 
			if (neighbors.isEmpty()) return;
			nextc = chooseNextCell(neighbors);
		}

		// //determination des coordonnées suivantes (nextx, nexty)
		// int nextx = x + (int)(Math.random() * 3) - 1;
		// int nexty = y + (int)(Math.random() * 3) - 1;

		// if (!environment.isInside(nextx, nexty)) return; //le pas de simulation est annulé si la coordonnée suivante en dehors de la grille
		// Cell next = environment.getCell(nextx, nexty);
		// if (environment.isForbidden(next)) return; //niveau de radiation trop élevé, le déplacement est annulé
		// //mise à jour de la position actuelle du drone

		x = nextc.x;
		y = nextc.y;
		exploreCell(nextc);
	    if (nextc.hasCow && !nextc.cowHandled) {
	        startAnalyse(nextc);
	    }

	}


	public void exploreCell(Cell cell){
		//voir si les cells autour sont explorable ou non
		 if (!localExplored[cell.x][cell.y]) {
			 localExplored[cell.x][cell.y] = true;
			 cell.explored = true;
		 }
		
	}
	
	public void startAnalyse(Cell cell) {
        state = DroneState.ANALYSING;
        analyseRemainingTime = 10; // 10 secondes
    }

	private void finishAnalyse() {
	    Cell c = environment.getCell(x, y);
	    
		GlobalCellInfo infoCell = localGlobalView[x][y];

	    //if (c.hasCow && !c.cowHandled) {
	    if (!infoCell.hasCow && c.hasCow) {
	        controlCenter.reportCow(id, x, y, c.radiationLevel);
	       // c.cowHandled = true;
	    }
	    // controlcenterに座標、放射能、座標を即時共有
	    controlCenter.updateCellInfo(x, y, c.radiationLevel, c.hasCow);
	    
	    state = DroneState.MOVING;
	}

	private List<Cell> getNeighbors() {
		List<Cell> neighbors = new ArrayList<>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				// stocke le cell voisin dans une liste pour lutiliser dans step()
				int nx = x + dx;
				int ny = y + dy;

				if (!environment.isInside(nx, ny)) continue;

				Cell c = environment.getCell(nx, ny);
				if (environment.isForbidden(c)) continue;

				neighbors.add(c);
			}
		}
		return neighbors;
	}

	// choirie un cell pour privilégier le déplacement vers le cell non exploré
	private Cell chooseNextCell(List<Cell> neighbors) {
		List<Cell> notExplored = new ArrayList<>();
		for (Cell c: neighbors){
			if (!localExplored[c.x][c.y]) {
			    notExplored.add(c);
			}
		}
		// si la liste n’est pas vide, le drone y déplace
		if (!notExplored.isEmpty()){
			return notExplored.get((int)(Math.random() * notExplored.size()));
		}

		//si la liste est vide, une cellule déjà explorée est choisie aléatoirement
		return neighbors.get((int)(Math.random() * neighbors.size()));
	}
	
	 private void returnToBase() {
		 //TODO
		 this.x = environment.baseX; //pisitionnement initialle est base
	     this.y = environment.baseY;
	     chargingTime = 0; 
	     this.state = DroneState.CHARGING;
	 }

	 
	 private void restart() {
		 System.out.println("dans methode restart");
		 missionTime = 0;
		 //mettre a jour de GlobalCellInfo
		 localGlobalView = controlCenter.getGlobalMapCopy();

		 //localMap  initialization
		 localExplored = new boolean[environment.width][environment.height];
		 localExplored[x][y] = true;
		    
		 state = DroneState.MOVING;
	 }

}
