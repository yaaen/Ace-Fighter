import java.io.File;

/**
 * @(#)AceFighter6_9 -> Item Engine -> SpecialExpanderEntity class
 *
 * Expands the player's special bar.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 2, 2008
 */

public class SpecialExpanderEntity extends PowerUpEntity
{
	private int amount;	//the amount by which this powerup expands the special of the player
	
	public SpecialExpanderEntity(int xCoord, int yCoord, int width, int height,
			int amount)
	{
		super(xCoord,yCoord,width,height);
		this.amount = amount;
		getSprite().setImageFile(new File("Images/Powerups/SpecialExpanderPowerup.png"));
	}
	
	//Overridden Methods
	/**
	 * Collision Effect
	 * @param The player that collides with (picks up) the item.
	 * Upon collision, the player's total special gets boosted by the amount specified
	 * in the amount field.
	 */
	public void collisionEffect(PlayerEntity player)
	{
		super.collisionEffect(player);
		player.setMaximumSpecial(player.getMaximumSpecial()+amount);
	}
	
	public String toString()
	{
		return "Total special expanded by "+ amount;
	}
}
