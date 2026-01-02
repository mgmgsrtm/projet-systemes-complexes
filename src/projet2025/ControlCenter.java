package projet2025;

import java.util.ArrayList;
import java.util.List;

public class ControlCenter {

    int totalCowsDetected;
    int totalCellsExplored;

    List<String> eventLog;

    List<Drone> drones = new ArrayList<>();  //Le ControlCenter a une vision globale du groupe de drones.

    public ControlCenter() {
        this.totalCowsDetected = 0;
        this.totalCellsExplored = 0;
        this.eventLog = new ArrayList<>();
    }


    public void registerDrone(Drone d) {
        drones.add(d);
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

    // méthode permettant d’afficher l’état courant de tous les drones du groupe.

    public void printDroneStatus(){
        for (Drone d: drones) {
            System.out.println("Drone " + d.getId() + "(" + d.getX() + "," + d.getY() + ")" + "state: " +d.getState());        }
    }
}