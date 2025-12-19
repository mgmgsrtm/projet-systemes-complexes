package projet2025;

public class Drone {
	
	int id;         //droneID
    int x, y;       //positionnement
	Environment environment;

	public Drone(int id, Environment environment) {
		this.id = id;
		this.environment = environment;
		this.x = environment.baseX; //pisitionnement initialle est base
        this.y = environment.baseY;
	}

	public int getBaseX() {
        return environment.baseX;
    }

    public int getBaseY() {
        return environment.baseY;
    }

}
