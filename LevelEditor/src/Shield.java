/**
 * @(#)AceFighter1_5 -> Boss Engine -> Shield
 *
 * The shield acts as a...well...shield. It protects other vital boss components
 * from projectiles. 
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
 
public class Shield extends BossComponent
{
	private String type;	//the type of Boss Component
	
	public Shield(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
		type = "Shield";
		setType(type);
		getSprite().setBossComponentType(type);
	}
}
