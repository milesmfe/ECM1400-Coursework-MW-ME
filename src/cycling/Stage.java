package cycling;

import java.time.LocalDateTime;

public class Stage extends CyclingObject {

    private double length;
    private LocalDateTime startTime;
    
    public Stage(String name, String description, double _length, LocalDateTime _startTime) {
        super(name, description);
        length = _length;
        startTime = _startTime;
    }

    public double getLength() {
        return length;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public double setLength(double _length) {
        length = _length;
        return length;
    }

    public LocalDateTime setStartTime(LocalDateTime _startTime) {
        startTime = _startTime;
        return startTime;
    }
}
