/**
 * @(#)AceFighter1_1 -> Platform Engine -> AnimatedWallEntity class
 *
 * Allows for Animated Walls.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
 
public class AnimatedWallEntity extends WallEntity
{
	//Animation Constants. All AnimatedPlatforms follow this standard. That means that all
	//Images used by AnimatedPlatforms have to conform to this standard.
	private static final int NUM_COLS = 1;
	private static final int STRIP_SIZE = 2
	;	
	private AnimatedSprite sprite;	//The AnimatedSprite used for Animated Platforms
	
	public AnimatedWallEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
		sprite = new AnimatedSprite(xCoord,yCoord,width,height);
		sprite.setNumCols(NUM_COLS);
		sprite.setStripSize(STRIP_SIZE);
//		sprite.setFrameWidth(width);
//		sprite.setFrameHeight(height);
		setSprite(sprite);
	}
	
	//Overridden Accessors
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	/**
	 *Updates the movement of the platform (if it moves). It also updates the Animation
	 *of the platform.
	 */
	public void update()
	{
		super.update();
		sprite.animate();
	}
}
