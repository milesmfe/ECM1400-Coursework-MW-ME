package cycling;

public class Rider extends CyclingObject {
    
    private String name;
    private int yearOfBirth;
    private int teamId;

    public Rider(String _name, int _yearOfBirth, int _teamId) {
        name = _name;
        yearOfBirth = _yearOfBirth;
        teamId = _teamId;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public int getTeamId() {
        return teamId;
    }

    public String setName(String _name) {
        name = _name;
        return name;
    }

    public int setYearOfBirth(int _yearOfBirth) {
        yearOfBirth = _yearOfBirth;
        return yearOfBirth;
    }

    public int setTeamId(int _teamId) {
        teamId = _teamId;
        return teamId;
    }

}
