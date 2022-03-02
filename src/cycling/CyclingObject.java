package cycling;

public class CyclingObject {

    private String name;
    private String description;

    public CyclingObject(String _name, String _description) {
        name = _name;
        description = _description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String setName(String _name) {
        name = _name;
        return name = _name;
    }
    
    public String setDescription(String _description) {
        description = _description;
        return description = _description;
    }
    
}
