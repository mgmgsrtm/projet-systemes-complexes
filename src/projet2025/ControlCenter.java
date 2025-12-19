package projet2025;

import java.util.ArrayList;
import java.util.List;

public class ControlCenter {

    int totalCowsDetected;
    int totalCellsExplored;

    List<String> eventLog;

    public ControlCenter() {
        this.totalCowsDetected = 0;
        this.totalCellsExplored = 0;
        this.eventLog = new ArrayList<>();
    }

    public void reportCow(int droneId, int x, int y, double radiation) {
        totalCowsDetected++;
        eventLog.add(
            "Drone " + droneId +
            " detected cow at (" + x + "," + y + ")" +
            " radiation=" + radiation
        );
    }
}