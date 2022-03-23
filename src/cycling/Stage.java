package cycling;

import java.time.LocalDateTime;

     /**
     * 
     * An extension of CyclingObject with additional functionality to represent 
     * Cycling Stages
     * 
     * @author Miles Edwards
     * @author Max Ward
     * @version 1.0
     * 
     */

public class Stage extends CyclingObject {

    private double length;
    private LocalDateTime startTime;
    private int raceId;
    private StageType stageType;
    
    public Stage(String name, String description, double _length, LocalDateTime _startTime, 
    StageType type, int _raceId) {
        
        super(name, description);
        length = _length;
        startTime = _startTime;
        stageType = type;
        raceId = _raceId;
    }

    public double getLength() {
        return length;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StageType getStageType() {
        return stageType;
    }

    public int getRaceId() {
        return raceId;
    }

    public double setLength(double _length) {
        length = _length;
        return length;
    }

    public LocalDateTime setStartTime(LocalDateTime _startTime) {
        startTime = _startTime;
        return startTime;
    }

    public StageType setStageType(StageType type) {
        stageType = type;
        return stageType;
    }

    public int setRaceId(int _raceId) {
        raceId = _raceId;
        return raceId;
    }
}
