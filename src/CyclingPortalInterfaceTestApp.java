import java.io.IOException;
import cycling.CyclingPortal;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidNameException;

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
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException, IOException, ClassNotFoundException {
		System.out.println("The system compiled and started the execution...");

//		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		CyclingPortal portal = new CyclingPortal();

		/* portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");
		portal.createRace("name", "description");

		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");
		portal.createTeam("name", "description");


		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);
		portal.createRider(15, "name", 5);

		portal.saveCyclingPortal("data.json"); */

		portal.loadCyclingPortal("data.json");

		for (int id : portal.getRaceIds()) {
			System.out.println(id);
		}
		
		for (int id : portal.getTeamRiders(15)) {
			System.out.println(id);
		}

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
