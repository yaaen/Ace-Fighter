/**
 * @(#)AceFighter5_5 -> Animation Engine -> Spawning Animation class
 *
 * A Spawning Animation plays when a player spawns.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 07, 2008
 */
import java.io.File;
import java.io.Serializable;
public class SpawningAnimation extends AnimationEntity
{
	private Actor owningActor;	//the player to which this dying animation belongs
	
	public SpawningAnimation(int xCoord, int yCoord, int width, int height,
			Actor owningActor)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setStripSize(19);
		getSprite().setNumCols(10);
		getSprite().setImageFile(new File("Images/Misc/SpawningAnimation.png"));
		this.owningActor = owningActor;
	}
	
	public void startAnimation(int xCoord, int yCoord)
	{
		if(alive())
		{
			terminateAnimation();
		}
		else
		{
			owningActor.setSpawning(true);
			getSprite().setXCoord(xCoord);
			getSprite().setYCoord(yCoord);
			getSprite().setCurrentFrame(getSprite().getStartFrame());
			setAlive(true);
		}
	}
	
	public void terminateAnimation()
	{
		setAlive(false);
		owningActor.setSpawning(false);
	}
}
