/**
 * @(#)AceFighter5_5 -> Animation Engine -> Dying Animation class
 *
 * A Dying Animation is the animation that's played when a player dies.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 07, 2008
 */
import java.io.File;
import java.io.Serializable;
public class DyingAnimation extends AnimationEntity
{
	private Actor owningActor;	//the player to which this dying animation belongs
	
	public DyingAnimation(int xCoord, int yCoord, int width, int height,
			Actor owningActor)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setStripSize(19);
		getSprite().setNumCols(10);
		getSprite().setImageFile(new File("Images/Misc/DyingAnimation.png"));
		this.owningActor = owningActor;
	}
	
	public void startAnimation(int xCoord, int yCoord)
	{
		if(alive())
		{
			terminateAnimation();
		}
		owningActor.setDying(true);
		getSprite().setXCoord(xCoord);
		getSprite().setYCoord(yCoord);
		getSprite().setCurrentFrame(getSprite().getStartFrame());
		setAlive(true);
	}
	
	public void terminateAnimation()
	{
		setAlive(false);
		owningActor.setDying(false);
		owningActor.spawn();
	}
}
