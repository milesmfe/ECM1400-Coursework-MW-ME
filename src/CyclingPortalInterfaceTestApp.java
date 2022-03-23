import java.time.LocalDateTime;

import cycling.BadCyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidNameException;
import cycling.StageType;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException
	 * @throws IllegalNameException
	 * @throws InvalidLengthException
	 * @throws IDNotRecognisedException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException {
		System.out.println("The system compiled and started the execution...");

//		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		CyclingPortalInterface portal = new BadCyclingPortal();

		portal.createRace("Test", "This is a test");	
		portal.addStageToRace(0, "Test", "description", 3.3, LocalDateTime.now(), StageType.FLAT);
		for (int id : portal.getRaceIds()) {
			System.out.println(id);
		}
		System.out.println(portal.getStageLength(1));
		for (int stageid : portal.getRaceStages(0)) {
			System.out.println(stageid);
		}

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
