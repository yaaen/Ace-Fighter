/**
 * @(#)AceFighter1_5 -> Boss Engine -> Turret
 *
 * The Turret is used by the Boss Entity to fire at his opponents. The turret makes
 * use of a list of WeaponEntities to do so. 
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
 
public class Turret extends BossComponent
{
	private String type;
	
	public Turret(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
		type = "Turret";
		setType(type);
		getSprite().setBossComponentType(type);
	}
	
	//Overridden Methods
	/**
	 * Execute Command
	 * @param The Command to be executed.
	 * This version of executeCommand() differs from that of the BossComponent class in
	 * that it also contains mechanisms for firing the weapons when the fire command is
	 * encountered.
	 */
	/*public void executeCommand(Command command)
	{
		super.executeCommand(command);
		
		String componentType = command.getNameOrType();
	 	String action = command.getAction();
	 	int duration = command.getDuration();
	 	int absDuration = Math.abs(duration);	//absolute value of the duration
	 	if(componentType.equals("Boss")||componentType.equals(this.getType())||
	 		componentType.equals(this.getName()))
	 	{
	 		if(getElapsedExecutionTime()<absDuration)	//if current command is not finished executing
	 		{
	 			if(action.equals("Fire"))
	 			{
	 				if(duration>0)
	 				{
	 					getSprite().setDirection(1);
	 					getSprite().setFaceAngle(0);
	 				}
	 				if(duration<0)
	 				{
	 					getSprite().setDirection(-1);
	 					getSprite().setFaceAngle(180);
	 				}
	 				fire();
	 				setElapsedExecutionTime(getElapsedExecutionTime()+1);
	 				getSprite().setFiring(true);
	 			}
	 		}
	 	}
	}*/
	
	/**
	 * Fire
	 * Fires the projectiles of the weapons of this turret.
	 *
	private void fire()
	{
	}*/
}
