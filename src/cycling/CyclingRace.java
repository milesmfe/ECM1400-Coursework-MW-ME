package cycling;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CyclingRace extends CyclingObject {

    private HashMap<Integer, CyclingRaceStage> stagesByID = new HashMap<Integer, CyclingRaceStage>();

    public CyclingRace(String name, String description) {
        super(name, description);
    }

    public int addStage(int id, String name, String description, double length, LocalDateTime startTime) {
        stagesByID.put(id, new CyclingRaceStage(name, description, length, startTime));
        return id;
    }
}
