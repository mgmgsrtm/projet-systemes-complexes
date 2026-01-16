package projet2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControlCenter {

    int totalCowsDetected;
    int totalCellsExplored;

    int totalDetectionTentatives = 0;
    int duplicateDetections = 0;

    List<String> eventLog;

    List<Drone> drones = new ArrayList<>();  //Le ControlCenter a une vision globale du groupe de drones.
    
    Set<Integer> detectedCowIds = new HashSet<>(); //

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
    
    
    public void updateCellInfo(int x, int y, double radiation) {
        GlobalCellInfo info = globalMap[x][y];
        info.explored = true;
        info.radiationLevel = radiation;
    }
    
    
    public void reportCow(int droneId, int x, int y, double radiation, Cow cow ) {
        if (cow == null) return;
        totalDetectionTentatives++;

        if (!detectedCowIds.contains(cow.id)) {
            detectedCowIds.add(cow.id);
            globalMap[x][y].hasCow = true;
            totalCowsDetected++;  
            System.out.println(detectedCowIds); // 初回のみ
            boolean dangerous = radiation > 1;
            eventLog.add(
                "Drone " + droneId +
                " detected cow" + cow.id +"at (" + x + "," + y + ")" +
                " radiation=" + radiation +
                (dangerous ? " [DANGEROUS]" : "")
            );
        }else if (detectedCowIds.contains(cow.id) && !globalMap[x][y].hasCow) {
        	globalMap[x][y].hasCow = true;
            // totalCowsDetected++;
        	duplicateDetections++;
        }
        return; // この牛はすでに記録済みで移動なし
    }
    
    
    public GlobalCellInfo[][] getGlobalMapCopy() {
        GlobalCellInfo[][] copy = new GlobalCellInfo[globalMap.length][globalMap[0].length];

        for (int x = 0; x < globalMap.length; x++) {
            for (int y = 0; y < globalMap[0].length; y++) {
                copy[x][y] = copy[x][y] = new GlobalCellInfo(globalMap[x][y]); //生きた参照でなく、スナップショット
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


    public void printGlobalMap() {
        System.out.println("==== GLOBAL MAP (Control Center) ====");

        for (int y = 0; y < globalMap[0].length; y++) {
            for (int x = 0; x < globalMap.length; x++) {

                GlobalCellInfo c = globalMap[x][y];

                if (!c.explored) {
                    System.out.print("?|");
                }
                else if (c.radiationLevel > Environment.RADIATION_FORBIDDEN) {
                    System.out.print("X|");
                }
                else if (c.radiationLevel > Environment.RADIATION_LIMITED) {
                    System.out.print("~|");
                }
                else if (c.hasCow) {
                    System.out.print("C|");
                }
                else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        System.out.println("=== GLOBAL MAP (Control Center) ===");
        System.out.println();
    }
}