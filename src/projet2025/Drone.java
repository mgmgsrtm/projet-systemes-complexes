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
	
	enum DroneState {
	    MOVING,
	    ANALYSING,
	    RETURNING
	}

	public Drone(int id, Environment environment, ControlCenter controlCenter) {
		this.id = id;
		this.environment = environment;
		this.controlCenter = controlCenter;
		this.x = environment.baseX; //pisitionnement initialle est base
        this.y = environment.baseY;
		this.state = DroneState.MOVING;
		controlCenter.registerDrone(this); // chaque drone s’enregistre automatiquement auprès de control center lors de sa création

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
		
		if (state == DroneState.ANALYSING) {
	        analyseRemainingTime--;

	        if (analyseRemainingTime <= 0) {
	            finishAnalyse();
	        }
	        return; // analyse中は移動しない
	    }

		//avancer ver une cellule non explorée
		List<Cell> neighbors = getNeighbors(); // avoir la liste des cellules voisines accessibles
		Cell nextc = chooseCextCell(neighbors); // parcourt la liste et sélectionner une cellule non explorée


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
		//TODO
		//voir si les cells autour sont explorable ou non
		if (!cell.explored) {
	        cell.explored = true;
	        controlCenter.totalCellsExplored++;
	    }
		
	}
	
	public void startAnalyse(Cell cell) {
        state = DroneState.ANALYSING;
        analyseRemainingTime = 10; // 10 secondes
    }

	private void finishAnalyse() {
	    Cell c = environment.getCell(x, y);

	    if (c.hasCow && !c.cowHandled) {
	        controlCenter.reportCow(id, x, y, c.radiationLevel);
	        c.cowHandled = true;
	    }
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
	private Cell chooseCextCell(List<Cell> neighbors) {
		//TODO 
		return null;
	}

}
