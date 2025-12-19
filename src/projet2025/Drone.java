package projet2025;

public class Drone {
	
	int id;         //droneID
    int x, y;       //positionnement
	Environment environment;
	ControlCenter controlCenter;

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
