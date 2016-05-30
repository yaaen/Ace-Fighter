/**
 * @(#)AceFighter1_5 -> Boss Engine -> Cockpit
 *
 * The Cockpit is the main part of the Boss. It's a Boss Component. If the Cockpit
 * dies, the boss dies as a whole. 
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
 
public class Cockpit extends BossComponent
{
	private String type;	//the type of boss component
	
	public Cockpit(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
		type = "Cockpit";
		setType(type);
		getSprite().setBossComponentType(type);
	}
}
