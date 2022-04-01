package cycling;

import java.io.Serializable;

public class CyclingObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;
    private int id;

    public CyclingObject() {
        id = idCounter++;
    }

    public int getId() {
        return id;
    }
}
