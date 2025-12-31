package projet2025;

public class Drone {
	
	int id;         //droneID
    int x, y;       //positionnement
	Environment environment;
	ControlCenter controlCenter;
	DroneState state = DroneState.MOVING;
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
		//determination des coordonnées suivantes (nextx, nexty)
		int nextx = x + (int)(Math.random() * 3) - 1;
		int nexty = y + (int)(Math.random() * 3) - 1;

		if (!environment.isInside(nextx, nexty)) return; //le pas de simulation est annulé si la coordonnée suivante en dehors de la grille
		Cell next = environment.getCell(nextx, nexty);
		if (environment.isForbidden(next)) return; //niveau de radiation trop élevé, le déplacement est annulé
		//mise à jour de la position actuelle du drone
		x = nextx;
		y = nexty;
		exploreCell(next);
	    if (next.hasCow && !next.cowHandled) {
	        startAnalyse(next);
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

}
