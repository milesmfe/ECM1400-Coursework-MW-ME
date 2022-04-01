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
 * CyclingPortal class. Implements the MiniCyclingPortalInterface interface and initialises as
 * an empty platform with no initial racing teams nor races.
 * 
 * This solution follows the principles of an Object Oriented Database structure (OODb).
 * Relationships may be created between objects by cross storing and referencing ID numbers.
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
	 * Such, "Tables" are necessary so that objects, and their enclosed relations, may be referenced 
	 * easily using the corresponding ID number.
	 * 
	 * Data is stored as serialisable objects while the program is being executed.
	 * 
	 * These objects may then be serialised and stored in a json file or sent over a network.
	 * 
	 */

	private HashMap<Integer, Race> raceTable = new HashMap<Integer, Race>();
	private HashMap<Integer, Stage> stageTable = new HashMap<Integer, Stage>();
	private HashMap<Integer, Segment> segmentTable = new HashMap<Integer, Segment>();
	private HashMap<Integer, Team> teamTable = new HashMap<Integer, Team>();
	private HashMap<Integer, Rider> riderTable = new HashMap<Integer, Rider>();
	private HashMap<Integer, Result> resultTable = new HashMap<Integer, Result>();

	@Override
	public int[] getRaceIds() {

		// Fetch the raceTable keyset <Integer> and convert to int[].

		Integer[] ids = raceTable.keySet().toArray(new Integer[raceTable.size()]);
		return Arrays.stream(ids).mapToInt(Integer::intValue).toArray();
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {

		if (name == null || name.isEmpty() || name.length() > 30) {
			throw new InvalidNameException(String.format("""
					Error: The name, \"%s\" is invalid.\nYou must provide a name under 30 characters.%n""", name));
		} else {

			for (Race race : raceTable.values().toArray(new Race[raceTable.size()])) {
				if (race.getName() == name) {
					throw new IllegalNameException(String.format("""
						Error: The name, \"%s\" is unavailable.\nPlease try another.%n""", name));
				}
			}	
		}

		Race newRace = new Race(name, description);
		raceTable.put(newRace.getId(), newRace); // Write the new Race object to raceTable
		return newRace.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

		try {
			Race race = raceTable.get(raceId);
			int totalLength = 0;
			for (int stageId : getRaceStages(raceId)) {
				totalLength += getStageLength(stageId);
			}
			return String.format("""
					Details for Race #%d:\n*\tName: %s\n*\tDescription: %s\n*\tNo. Stages: %d\n*\tTotal Length: %d%n""", 
					raceId, race.getName(), race.getDescription(), getNumberOfStages(raceId), totalLength);
		} 	
		catch (Exception e) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Race exists with ID: #%d.%n""", raceId));
		}
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {

		try {
			raceTable.remove(raceId);
		}
		catch (Exception e) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Race exists with ID: #%d.%n""", raceId));		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {

		// Iterate through Stage objects stored in stageTable and count how many 
		// relate to the Race with a matching ID.

		if (!raceTable.containsKey(raceId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Race exists with ID: #%d.%n""", raceId));
		}

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

				if (!raceTable.containsKey(raceId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Race exists with ID: #%d.%n""", raceId));
				}

				if (length < 5.0) {
					throw new InvalidLengthException(String.format("""
							Error: Stage length is too short (%dkm). You must provide a value over 5km.%n""", length));
				}

				if (stageName == null || stageName.isEmpty() || stageName.length() > 30) {
					throw new InvalidNameException(String.format("""
							Error: The name, \"%s\" is invalid.\nYou must provide a name under 30 characters.%n""", stageName));
				} else {
		
					for (Stage stage : stageTable.values().toArray(new Stage[stageTable.size()])) {
						if (stage.getName() == stageName) {
							throw new IllegalNameException(String.format("""
								Error: The name, \"%s\" is unavailable.\nPlease try another.%n""", stageName));
						}
					}	
				}

				Stage newStage = new Stage(stageName, description, length, startTime, type, raceId);
				stageTable.put(newStage.getId(), newStage); // Write the new Stage object to stageTable
				return newStage.getId();
		}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		if (!raceTable.containsKey(raceId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Race exists with ID: #%d.%n""", raceId));
		}

		// Iterate through Stage objects stored in stageTable and copy the ID of any that relate 
		// to the Race with a matching ID into a new array.

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

		if (!stageTable.containsKey(stageId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Stage exists with ID: #%d.%n""", stageId));
		}

		return stageTable.get(stageId).getLength();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {

		if (!stageTable.containsKey(stageId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Stage exists with ID: #%d.%n""", stageId));
		}

		stageTable.remove(stageId);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

				if (stageTable.get(stageId).getState() == CyclingState.WAITING_FOR_RESULTS) {
					throw new InvalidStageTypeException("Error: Stage is already committed.");
				}

				if (stageTable.get(stageId).getStageType() == StageType.TT) {
					throw new InvalidStageTypeException("Error: Time-trial stages cannot contain any segment.");
				}

				if (!stageTable.containsKey(stageId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Stage exists with ID: #%d.%n""", stageId));
				}

				if (location > stageTable.get(stageId).getLength()) {
					throw new InvalidLocationException(String.format("""
							Error: Location (%f) is beyond Stage bounds (%f).%n""", location, 
							stageTable.get(stageId).getLength()));
				}

				Segment newSegment = new Segment(stageId, location, type, averageGradient);
				segmentTable.put(newSegment.getId(), newSegment);
				return newSegment.getId();
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

				if (!stageTable.containsKey(stageId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Stage exists with ID: #%d.%n""", stageId));
				}

				if (stageTable.get(stageId).getState() == CyclingState.WAITING_FOR_RESULTS) {
					throw new InvalidStageTypeException("Error: Stage is already committed.");
				}

				if (stageTable.get(stageId).getStageType() == StageType.TT) {
					throw new InvalidStageTypeException("Error: Time-trial stages cannot contain any segment.");
				}

				if (location > stageTable.get(stageId).getLength()) {
					throw new InvalidLocationException(String.format("""
							Error: Location (%f) is beyond Stage bounds (%f).%n""", location, 
							stageTable.get(stageId).getLength()));
				}

				Segment newSegment = new Segment(stageId, location, SegmentType.SPRINT);
				segmentTable.put(newSegment.getId(), newSegment);
				return newSegment.getId();
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {

		if (!segmentTable.containsKey(segmentId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Segment exists with ID: #%d.%n""", segmentId));
		}

		if (stageTable.get(segmentTable.get(segmentId).getStageId()).getState() == CyclingState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Error: Stage is already committed.");
		}

		segmentTable.remove(segmentId);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		
		if (!stageTable.containsKey(stageId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Stage exists with ID: #%d.%n""", stageId));
		}
		
		if (stageTable.get(stageId).getState() == CyclingState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Error: Stage is already committed.");
		}

		stageTable.get(stageId).setState(CyclingState.WAITING_FOR_RESULTS);
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {

		if (!stageTable.containsKey(stageId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Stage exists with ID: #%d.%n""", stageId));
		}

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

		if (name == null || name.isEmpty() || name.length() > 30) {
			throw new InvalidNameException(String.format("""
					Error: The name, \"%s\" is invalid.\nYou must provide a name under 30 characters.%n""", name));
		} else {
			for (Team team : teamTable.values().toArray(new Team[teamTable.size()])) {
				if (team.getName() == name) {
					throw new IllegalNameException(String.format("""
						Error: The name, \"%s\" is unavailable.\nPlease try another.%n""", name));
				}
			}	
		}

		Team newTeam = new Team(name, description);
		teamTable.put(newTeam.getId(), newTeam);
		return newTeam.getId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {

		if (!teamTable.containsKey(teamId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Team exists with ID: #%d.%n""", teamId));
		}

		teamTable.remove(teamId);
	}

	@Override
	public int[] getTeams() {
		Integer[] ids = teamTable.keySet().toArray(new Integer[teamTable.size()]);
		return Arrays.stream(ids).mapToInt(Integer::intValue).toArray();
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

		if (!teamTable.containsKey(teamId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Team exists with ID: #%d.%n""", teamId));
		}

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

				if (!teamTable.containsKey(teamId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Team exists with ID: #%d.%n""", teamId));
				}

				if (name == null || yearOfBirth < 1900) {
					throw new IllegalArgumentException("""
							Error: You must enter a name and provide a valid birth year.%n""");
				}

				Rider newRider = new Rider(name, yearOfBirth, teamId);
				riderTable.put(newRider.getId(), newRider);
				return newRider.getId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {

		if (!riderTable.containsKey(riderId)) {
			throw new IDNotRecognisedException(String.format("""
				Error: No such Rider exists with ID: #%d.%n""", riderId));
		}

		riderTable.remove(riderId);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {

				if (!stageTable.containsKey(stageId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Stage exists with ID: #%d.%n""", stageId));
				}

				if (!riderTable.containsKey(riderId)) {
					throw new IDNotRecognisedException(String.format("""
						Error: No such Rider exists with ID: #%d.%n""", riderId));
				}
		
				for (Result result : resultTable.values().toArray(new Result[resultTable.size()])) {
					if (result.getRiderId() == riderId && result.getStageId() == stageId) {
						throw new DuplicatedResultException(String.format("""
								Error: Rider with ID, #%d, has already registered 
								their result for stage with ID, #%d.%n""", riderId, stageId));
					}
				}

				int np2 = getStageSegments(stageId).length + 2;

				if (checkpoints.length != np2) {
					throw new InvalidCheckpointsException(String.format("""
							Error: There must be exactly, %d, checkpoints in this stage.""", np2));
				}

				if (stageTable.get(stageId).getState() != CyclingState.WAITING_FOR_RESULTS) {
					throw new InvalidStageStateException("Error: Stage must be committed first.");
				}				

				Result newResult = new Result(stageId, riderId, checkpoints);
				resultTable.put(newResult.getId(), newResult);
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		
		for (Result result : resultTable.values().toArray(new Result[resultTable.size()])) {
			if (result.getStageId() == stageId && result.getRiderId() == riderId) {
				return result.getCheckpoints();
			}
		}	
		throw new IDNotRecognisedException(String.format("""
			Error: No results for Rider with ID, #%d, in stage with ID, #%d.%n""", riderId, stageId));
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		for (Result result : resultTable.values().toArray(new Result[resultTable.size()])) {
			if (result.getStageId() == stageId && result.getRiderId() == riderId) {
				resultTable.remove(result.getId());
			}
		}	
		throw new IDNotRecognisedException(String.format("""
			Error: No results for Rider with ID, #%d, in stage with ID, #%d.%n""", riderId, stageId));
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		raceTable.clear();
		stageTable.clear();
		segmentTable.clear();
		teamTable.clear();
		riderTable .clear();
		resultTable.clear();
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
		resultTable = deserialized.resultTable;
	}
}
