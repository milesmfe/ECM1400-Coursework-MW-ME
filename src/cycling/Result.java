package cycling;

import java.time.LocalTime;

public class Result extends CyclingObject {
    
    private int stageId;
    private int riderId;
    private LocalTime[] checkpoints;

    public Result(int _stageId, int _riderId, LocalTime... _checkpoints) {
        stageId = _stageId;
        riderId = _riderId;
        checkpoints = _checkpoints;
    }

    public int getStageId() {
        return stageId;
    }

    public int getRiderId() {
        return riderId;
    }

    public LocalTime[] getCheckpoints() {
        return checkpoints;
    }

    public int setStageId(int _stageId) {
        stageId = _stageId;
        return stageId;
    }

    public int setRidereId(int _riderId) {
        riderId = _riderId;
        return riderId;
    }

    public LocalTime[] setCheckpoints(LocalTime[] _checkpoints) {
        checkpoints = _checkpoints;
        return checkpoints;
    }
}
