import java.io.File;

/**
 * @(#)AceFighter1_4 -> Item Engine -> HealthPackEntity class
 *
 * A HealthPackEntity is a Power Up. It simply gives the player who picks
 * it up more health.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
 
public class HealthPackEntity extends PowerUpEntity
{
	private int healthGain;		//the health gained when picking up the Health Pack
	
	public HealthPackEntity(int xCoord, int yCoord, int width, int height, int healthGain)
	{
		super(xCoord, yCoord, width, height);
		this.healthGain = healthGain;
		getSprite().setImageFile(new File("Images/Powerups/HealthPackPowerup.png"));
	}
	
	//Overridden Methods
	/**
	 * Collision Effect
	 * @param The player that collides with (picks up) the item.
	 * Upon collision, the player's health gets boosted by the amount specified
	 * in the healthGain field.
	 */
	public void collisionEffect(PlayerEntity player)
	{
		super.collisionEffect(player);
		player.setHealth(player.getHealth()+healthGain);
	}
	
	public String toString()
	{
		return "Gained " + healthGain + " health";
	}
}
