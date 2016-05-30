/**
 * @(#)AceFighter1_6 -> Portal Entity
 *
 * The Portal Entity is used to create portals in the levels. Portals are simply
 * the exits of the levels. When a player runs through a portal, he completes
 * the current level. The portal's make up is really quite simple. It has
 * an animated sprite to keep track of positioning and animation, and it has
 * a boolean that turns true once the level is completed (ie: once player runs
 * through portal).
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
import java.io.*;
 
public class PortalEntity extends MovableObject implements Serializable
{
	//Static constants used for animation. All images used by PortalEntity must conform
	//to this standard.
	private static final int NUM_COLS = 2;
	private static final int STRIP_SIZE = 2;
	
	private AnimatedSprite sprite;		//the sprite used for positioning, animation etc...
	private boolean levelCompleted;		//true once player runs through the portal (once level is completed)
	private boolean alive;				//true if portal is alive

	public PortalEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new AnimatedSprite(xCoord, yCoord, width, height);
		sprite.setNumCols(NUM_COLS);
		sprite.setStripSize(STRIP_SIZE);
//		sprite.setFrameWidth(width);
	//	sprite.setFrameHeight(height);
		levelCompleted = false;
		alive = false;
	}
	
	//Accessors
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	public boolean levelCompleted()
	{
		return levelCompleted;
	}
	
	public boolean alive()
	{
		return alive;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	/**
	 * Update
	 * Updates the state of the portal. This basically consists of animating the sprite.
	 */
	 public void update()
	 {
	 	sprite.animate();
	 }
	 
	 /**
	  * Collision Effect
	  * @param The player that's colliding with (running through) the portal.
	  * When a player runs through a portal (collides with a portal), the portal's
	  * levelCompleted boolean gets set to true indicating that the level has been
	  * completed.
	  */
	 public void collisionEffect(Actor player)
	 {
	 	levelCompleted = true; 	
	 } 
}
