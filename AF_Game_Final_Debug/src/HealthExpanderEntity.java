import java.io.File;

/**
 * @(#)AceFighter6_8 -> Item Engine -> HealthExpanderEntity class
 *
 * Expands the player's overall health when picked up. It doesn't restore
 * the player's health, but it increases the total amount of health the
 * player can have.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 1, 2008
 */

public class HealthExpanderEntity extends PowerUpEntity
{
	private int amount;	//the amount by which this powerup expands the player's health
	
	public HealthExpanderEntity(int xCoord, int yCoord, int width, int height, int amount)
	{
		super(xCoord, yCoord, width, height);
		this.amount = amount;
		getSprite().setImageFile(new File("Images/Powerups/HealthExpanderPowerup.png"));
	}
	
	//Overridden Methods
	/**
	 * Collision Effect
	 * @param The player that collides with (picks up) the item.
	 * Upon collision, the player's total health gets boosted by the amount specified
	 * in the amount field.
	 */
	public void collisionEffect(PlayerEntity player)
	{
		super.collisionEffect(player);
		player.setMaximumHealth(player.getMaximumHealth()+amount);
	}
	
	public String toString()
	{
		return "Total health expanded by "+ amount;
	}
}
