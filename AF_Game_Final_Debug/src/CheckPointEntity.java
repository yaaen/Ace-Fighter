/**
 * @(#)AceFighter6_7 -> Check Point Entity
 *
 * The Checkpoint Entity allows a player from respawning at a certain point in the level.
 * If a player is going through a level (side-scrolling level), and he passes
 * through a CheckPointEntity, he will get respawned at that check point if he dies.
 * This prevents a player from having to repeat the whole level if he dies near the end
 * (provided there's a checkpoint near the end).
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 28, 2008
 */
import java.io.*;
public class CheckPointEntity extends MovableObject implements Serializable
{
	//Static constants used for animation. All images used by CheckPointEntity must conform
	//to this standard.
	private static final int NUM_COLS = 2;
	private static final int STRIP_SIZE = 2;
	
	private AnimatedSprite sprite;		//the sprite used for positioning, animation etc...
	private boolean visited;	//true if the player has already visited this portal

	public CheckPointEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new AnimatedSprite(xCoord, yCoord, width, height);
		sprite.setNumCols(NUM_COLS);
		sprite.setStripSize(STRIP_SIZE);
		visited = false;
	}
	
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	public boolean visited()
	{
		return visited;
	}
	
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	
	public void update()
	{
	 	sprite.animate();
	}
	
	public void collisionEffect(PlayerEntity player)
	{
		if(!visited)
		{
			visited = true;
			player.setOriginalX((int)this.getSprite().getXCoord());
			player.setOriginalY((int)this.getSprite().getYCoord());
		}
	}
}
