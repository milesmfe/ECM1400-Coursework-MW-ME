package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 
 * CyclingPortal is the completed version of BadMiniCyclingPortal containing my
 * solution to the assignment.
 * 
 * @author Miles Edwards
 * @version 1.0
 *
 */

public class CyclingPortal implements MiniCyclingPortalInterface {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * All portal data is stored in a collection of maps, "Tables".
	 * 
	 * Data is stored as serialisable objects while the program is being executed.
	 * 
	 * These objects may then be serialised and stored in a json file or sent over a network.
	 * 
	 * "Tables" are necessary so that objects, and their enclosed relations,
	 * may be referenced easily using the corresponding ID number.
	 * 
	 */

	private HashMap<Integer, Race> raceTable = new HashMap<Integer, Race>();
	private HashMap<Integer, Stage> stageTable = new HashMap<Integer, Stage>();
	private HashMap<Integer, Segment> segmentTable = new HashMap<Integer, Segment>();
	private HashMap<Integer, Team> teamTable = new HashMap<Integer, Team>();
	private HashMap<Integer, Rider> riderTable = new HashMap<Integer, Rider>();

	@Override
	public int[] getRaceIds() {
		Integer[] ids = raceTable.keySet().toArray(new Integer[raceTable.size()]);
		return Arrays.stream(ids).mapToInt(Integer::intValue).toArray();
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Race newRace = new Race(name, description);
		raceTable.put(newRace.getId(), newRace);
		return newRace.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		return raceTable.get(raceId).getDetails();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		raceTable.remove(raceId);
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		int count = 0;
		for (Stage stage : stageTable.values().toArray(new Stage[stageTable.size()])) {
			if (stage.getRaceId() == raceId) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
			Stage newStage = new Stage(stageName, description, length, startTime, type, raceId);
			stageTable.put(newStage.getId(), newStage);
			return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		ArrayList<Integer> stages = new ArrayList<Integer>();
		for (Stage stage : stageTable.values().toArray(new Stage[stageTable.size()])) {
			if (stage.getRaceId() == raceId) {
				stages.add(stage.getId());
			}
		}	
		return Arrays.stream(stages
			.toArray(new Integer[stages.size()]))
			.mapToInt(Integer::intValue)
			.toArray();
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		return stageTable.get(stageId).getLength();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		stageTable.remove(stageId);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		Segment newSegment = new Segment(stageId, location, type, averageGradient);
		segmentTable.put(newSegment.getId(), newSegment);
		return newSegment.getId();
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Segment newSegment = new Segment(stageId, location, SegmentType.SPRINT);
		segmentTable.put(newSegment.getId(), newSegment);
		return newSegment.getId();
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		segmentTable.remove(segmentId);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		ArrayList<Integer> segments = new ArrayList<Integer>();
		for (Segment segment : segmentTable.values().toArray(new Segment[segmentTable.size()])) {
			if (segment.getStageId() == stageId) {
				segments.add(segment.getId());
			}
		}	
		return Arrays.stream(segments
			.toArray(new Integer[segments.size()]))
			.mapToInt(Integer::intValue)
			.toArray();
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		Team newTeam = new Team(name, description);
		teamTable.put(newTeam.getId(), newTeam);
		return newTeam.getId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		teamTable.remove(teamId);
	}

	@Override
	public int[] getTeams() {
		Integer[] ids = teamTable.keySet().toArray(new Integer[teamTable.size()]);
		return Arrays.stream(ids).mapToInt(Integer::intValue).toArray();
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		ArrayList<Integer> riders = new ArrayList<Integer>();
		for (Rider rider : riderTable.values().toArray(new Rider[riderTable.size()])) {
			if (rider.getTeamId() == teamId) {
				riders.add(rider.getId());
			}
		}	
		return Arrays.stream(riders
			.toArray(new Integer[riders.size()]))
			.mapToInt(Integer::intValue)
			.toArray();
	}

	@Override
	public int createRider(int teamId, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		Rider newRider = new Rider(name, yearOfBirth, teamId);
		riderTable.put(newRider.getId(), newRider);
		return newRider.getId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		riderTable.remove(riderId);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		raceTable.clear();
		stageTable.clear();
		segmentTable.clear();
		teamTable.clear();
		riderTable .clear();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.close();
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(filename);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		CyclingPortal deserialized = (CyclingPortal) objectInputStream.readObject();
		objectInputStream.close();
		raceTable = deserialized.raceTable;
		stageTable = deserialized.stageTable;
		segmentTable = deserialized.segmentTable;
		teamTable = deserialized.teamTable;
		riderTable  = deserialized.riderTable;
	}

}
