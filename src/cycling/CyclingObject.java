package cycling;

import java.io.Serializable;

public class CyclingObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;
    private int id;
    private CyclingState state;

    public CyclingObject() {
        id = idCounter++;
    }

    public CyclingState getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public CyclingState setState(CyclingState _state) {
        state = _state;
        return state;
    }
}
