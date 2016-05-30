/**
 * @(#)AceFighter5_5 -> Animation Engine -> Animation Entity class
 *
 * An Animation Entity is simply an animation. For example, an explosion is a type
 * of animation entity. It's simply there for decorative purposes. Once it's called to life,
 * it animates. Once its animation is finished, it dies. Another type of animation entity
 * is the spawning animation and the dying animation. The spawning animation plays
 * when a player spawns, while the dying animation plays while a player is dying.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 07, 2008
 */
import java.io.File;
import java.io.Serializable;
public abstract class AnimationEntity implements Serializable
{
	private AnimatedSprite sprite;
	private boolean alive;
	
	public AnimationEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new AnimatedSprite(xCoord,yCoord,width,height);
		alive = false;
	}
	
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	public boolean alive()
	{
		return alive;
	}
	
	protected void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public void update()
	{
		if(alive)
		{
			if(sprite.getCurrentFrame()>=sprite.getStripSize()-1)	//if animation is over
			{
				//kill animation
				terminateAnimation();
			}
			else
			{
				sprite.animate();
			}
		}
	}
	
	abstract void startAnimation(int xCoord, int yCoord);
	
	abstract void terminateAnimation();

}
