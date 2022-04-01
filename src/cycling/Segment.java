package cycling;

public class Segment extends CyclingObject {
    
    private int stageId;
    private Double location;
    private SegmentType type;
    private Double averageGradient;

    public Segment(int _stageId, Double _location, SegmentType _type, Double _averageGradient) {
        stageId = _stageId;
        location = _location;
        type = _type;
        averageGradient = _averageGradient;
    }

    public Segment(int _stageId, Double _location, SegmentType _type) {
        stageId = _stageId;
        location = _location;
        type = _type;
    }

    public Double getLocation() {
        return location;
    }

    public SegmentType getType() {
        return type;
    }

    public Double getAverageGradient() {
        return averageGradient;
    }

    public int getStageId() {
        return stageId;
    }

    public Double setLocation(Double newLocation) {
        location = newLocation;
        return location;
    }

    public SegmentType setType(SegmentType newType) {
        type = newType;
        return type;
    }

    public Double setAverageGradient(Double newAverageGradient) {
        averageGradient = newAverageGradient;
        return averageGradient;
    }

    public int setStageId(int newStageId) {
        stageId = newStageId;
        return stageId;
    }
    
}
