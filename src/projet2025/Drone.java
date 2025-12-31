package projet2025;

public class Drone {
	
	int id;         //droneID
    int x, y;       //positionnement
	int status;
	Environment environment;
	ControlCenter controlCenter;

	//status  1: actif, 2: recharge, 3: en analyse

	public Drone(int id, Environment environment, ControlCenter controlCenter) {
		this.id = id;
		this.environment = environment;
		this.controlCenter = controlCenter;
		status = 1;
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
		//determination des coordonnées suivantes (nextx, nexty)
		int nextx = x + (int)(Math.random() * 3) - 1;
		int nexty = y + (int)(Math.random() * 3) - 1;

		if (!environment.isInside(nextx, nexty)) return; //le pas de simulation est annulé si la coordonnée suivante en dehors de la grille
		Cell next = environment.getCell(nextx, nexty);
		if (environment.isForbidden(next)) return; //niveau de radiation trop élevé, le déplacement est annulé
		//mise à jour de la position actuelle du drone
		x = nextx;
		y = nexty;
		if (!next.explored) {
			environment.markExplored(x, y);
		}
		analyseCell(next);

	}


	public void exploreCell(Cell cell){
		//TODO
		//voir si les cells autour sont explorable ou non
	}

	public void analyseCell(Cell cell){
		if (cell.hasCow && !cell.cowHandled) {
			controlCenter.reportCow(id, x, y, cell.radiationLevel);
			cell.cowHandled = true;
		}
	}

}
