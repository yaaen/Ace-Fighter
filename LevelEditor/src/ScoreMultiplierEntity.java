import java.io.File;

/**
 * @(#)AceFighter6_8 -> Item Engine -> ScoreMultiplierEntity class
 *
 * The ScoreMultiplierEntity multiplies the player's current score by a factor when it is
 * picked up by the player.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 1, 2008
 */
public class ScoreMultiplierEntity extends PowerUpEntity
{
	private int factor;	//by which amount to multiply the score
	
	public ScoreMultiplierEntity(int xCoord, int yCoord, int width, int height, int factor)
	{
		super(xCoord, yCoord, width, height);
		this.factor = factor;
		getSprite().setImageFile(new File("Images/Powerups/ScoreMultiplierPowerup.png"));
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
		player.setScore(player.getScore()*factor);
	}
	
	public String toString()
	{
		return "x"+factor + " score multiplier";
	}
}
