package cycling;

    /**
     * 
     * An extension of CyclingObject with additional functionality to represent 
     * Cycling Races
     * 
     * @author Miles Edwards
     * @author Max Ward
     * @version 1.0
     * 
     */

public class Race extends CyclingObject {

    public Race(String name, String description) {
        super(name, description);
    }

    public String getDetails() {
        return String.format("Name: %s, Description: %s%n", getName(), getDescription());
    }
}
