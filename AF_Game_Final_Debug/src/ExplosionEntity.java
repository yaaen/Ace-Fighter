import java.io.File;

/**
 * @(#)AceFighter4_7 -> Item Engine -> Explosion Entity class
 *
 * An Explosion Entity represents an explosion. An explosion is simply an animated
 * sprite that shows an explosion. It's only there for visual purposes. Once the
 * explosion animation has completely run through all of its frames, the explosion ends
 * (dies).
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 03, 2008
 */
import java.io.Serializable;
public class ExplosionEntity extends AnimationEntity
{
	private ProjectileEntity owningProjectile;
	
	public ExplosionEntity(int xCoord, int yCoord, int width, int height,
			ProjectileEntity owningProjectile)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setStripSize(19);
		getSprite().setNumCols(10);
		getSprite().setImageFile(new File("Images/Misc/Explosion.png"));
		this.owningProjectile = owningProjectile;
	}
	
	public void startAnimation(int xCoord, int yCoord)
	{
		if(alive())
		{
			terminateAnimation();
		}
		owningProjectile.setExploding(true);
		getSprite().setXCoord(xCoord);
		getSprite().setYCoord(yCoord);
		getSprite().setCurrentFrame(getSprite().getStartFrame());
		setAlive(true);
	}
	
	public void terminateAnimation()
	{
		setAlive(false);
		owningProjectile.setExploding(false);
	}
}
