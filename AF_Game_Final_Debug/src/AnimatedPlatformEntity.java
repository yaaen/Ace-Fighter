/**
 * @(#)AceFighter1_1 -> Platform Engine -> AnimatedPlatformEntity class
 *
 * This class allows for Animating Platforms. The only difference between this
 * class and its superclass is that this uses an AnimatedSprite instead of a
 * regular Sprite. The rest is the same.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
 
public class AnimatedPlatformEntity extends PlatformEntity
{
	//Animation Constants. All AnimatedPlatforms follow this standard. That means that all
	//Images used by AnimatedPlatforms have to conform to this standard.
	private static final int NUM_COLS = 1;
	private static final int STRIP_SIZE = 2;	
	private AnimatedSprite sprite;	//The AnimatedSprite used for Animated Platforms
	
	public AnimatedPlatformEntity(int xCoord, int yCoord, int width, int height)
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
	 *Updates the movement of the wall (if it moves). It also updates the Animation
	 *of the wall.
	 */
	public void update()
	{
		super.update();
		sprite.animate();
	}
}
