package cycling;

import java.util.ArrayList;
import java.util.HashMap;

public class Race extends CyclingObject {

    private ArrayList<Integer> stagesById = new ArrayList<Integer>();
    private HashMap<Integer, Stage> stageTable = new HashMap<Integer, Stage>();

    public Stage getStage(int id) {
		return stageTable.get(id);
	}

    public Race(String name, String description) {
        super(name, description);
    }

    public int[] getStages() {
        return stagesById.stream().mapToInt(i -> i).toArray();
    }

    public void addStage(Stage stage) {
        stageTable.put(stage.getId(), stage);
        stagesById.add(stage.getId());
    }

    public void removeStage(int stageId) {
        stageTable.remove(stageId);
        stagesById.remove(stageId);
    }

    public String getDetails() {
        return String.format("Name: %s, Description: %s%n", getName(), getDescription());
    }
}
