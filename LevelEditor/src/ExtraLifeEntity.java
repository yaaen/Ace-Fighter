import java.io.File;

/**
 * @(#)AceFighter6_9 -> Item Engine -> ExtraLifeEntity class
 *
 * Adds a life to the player who picks it up.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 2, 2008
 */

public class ExtraLifeEntity extends PowerUpEntity
{
	public ExtraLifeEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Powerups/ExtraLifePowerup.png"));
	}
	
	public void collisionEffect(PlayerEntity player)
	{
		super.collisionEffect(player);
		player.setLives(player.getLives()+1);
	}
	
	public String toString()
	{
		return "1UP";
	}
}
