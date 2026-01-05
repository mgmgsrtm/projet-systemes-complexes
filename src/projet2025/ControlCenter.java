package projet2025;

import java.util.ArrayList;
import java.util.List;

public class ControlCenter {

    int totalCowsDetected;
    int totalCellsExplored;

    List<String> eventLog;

    List<Drone> drones = new ArrayList<>();  //Le ControlCenter a une vision globale du groupe de drones.
    
    
    GlobalCellInfo[][] globalMap;

    public ControlCenter(int matrice) {
    	
    	globalMap = new GlobalCellInfo[matrice][matrice];
    	
    	for (int x = 0; x < matrice; x++) {
    	    for (int y = 0; y < matrice; y++) {
    	        globalMap[x][y] = new GlobalCellInfo();
    	    }
    	}

        this.totalCowsDetected = 0;
        this.totalCellsExplored = 0;
        this.eventLog = new ArrayList<>();
    }


    public void registerDrone(Drone d) {
        drones.add(d);
    }
    
    
    public void updateCellInfo(int x, int y, double radiation, boolean hasCow) {
        GlobalCellInfo info = globalMap[x][y];
        info.explored = true;
        info.radiationLevel = radiation;
        info.hasCow = hasCow;
    }
    
    
    public void reportCow(int droneId, int x, int y, double radiation) {
        totalCowsDetected++;
        boolean dangerous = radiation > 1;
        eventLog.add(
            "Drone " + droneId +
            " detected cow at (" + x + "," + y + ")" +
            " radiation=" + radiation +
            (dangerous ? " [DANGEROUS]" : "")
        );
    }
    
    
    public GlobalCellInfo[][] getGlobalMapCopy() {
        GlobalCellInfo[][] copy = new GlobalCellInfo[globalMap.length][globalMap[0].length];

        for (int x = 0; x < globalMap.length; x++) {
            for (int y = 0; y < globalMap[0].length; y++) {
                copy[x][y] = globalMap[x][y]; 
            }
        }
        return copy;
    }

    public boolean isCellOccupied(int x, int y, Drone requester) {
        for (Drone d : drones) {
            if (d != requester && d.getX() == x && d.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // méthode permettant d’afficher l’état courant de tous les drones du groupe.

    public void printDroneStatus(){
        for (Drone d: drones) {
            System.out.println("Drone " + d.getId() + "(" + d.getX() + "," + d.getY() + ")" + "state: " +d.getState());        }
    }
}