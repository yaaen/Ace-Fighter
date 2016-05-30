import java.io.File;
import java.util.Random;

/**
 * @(#)AceFighter4_8 -> Projectile Engine -> Grenade Projectile class
 *
 * A Grenade Projectile represents a grenade. A grenade bounces a little bit and
 * then eventually explodes when the fuse time runs out or when it collides with
 * an opponent.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 03, 2008
 */

public class GrenadeProjectile extends ProjectileEntity
{
	private static final int FUSE_TIME = 95;	//the amount of time it takes for a 
												//grenade to explode (if it doesn't 
												//collide with an opponent first)
	private int currentTime;
	private int currentFaceAngle;	//used to create the spinning effect of the grenade as
								//it's falling
	
	public GrenadeProjectile(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Projectiles/Grenade.png"));
		setDamage(14);
		currentTime = 0;
		currentFaceAngle = 0;
	}
	
	//Override update(gravity) to allow gravity to pull grenade down (creates falling
	//effect of grenade. Also rotate grenade
	public void update(double gravity)
	{
		super.update(gravity);
		if(alive())
		{
			if(currentFaceAngle>=360)
			{
				currentFaceAngle = 0;
			}
			getSprite().setFaceAngle(currentFaceAngle);
			currentFaceAngle++;
			getSprite().setYVeloc(getSprite().getYVeloc()+gravity);
			if(currentTime>=FUSE_TIME)
			{
				getExplosion().startAnimation((int)getSprite().getXCoord(),
						(int)getSprite().getYCoord());
				setAlive(false);
				currentTime = 0;
			}
			else
				currentTime++;
		}
	}
	
	//A grenade bounces off of platforms and walls
	public void collisionEffect(PlatformEntity platform)
	{
		if(platform instanceof PlatformEntity||platform instanceof AnimatedPlatformEntity)	//if projectile collides with a platform
		{
			//change y direction by inverting y velocity. Causes projectile to bounce away
			double partialYVeloc = 0.75*getSprite().getYVeloc();	//reduces y veloc on each bounce
			getSprite().setYVeloc(-partialYVeloc);
			//reduce the x velocity
			double partialXVeloc = 0.9*getSprite().getXVeloc();
			getSprite().setXVeloc(partialXVeloc);
		}
		if(platform instanceof WallEntity || platform instanceof AnimatedWallEntity)
		{
			getSprite().setXVeloc(-getSprite().getXVeloc());
			getSprite().setYVeloc(-getSprite().getYVeloc());
		}
	}
	
	public void collisionEffect(PlayerEntity player)
	{
		int horizontalSpeed = 8;	//the speed at which things fly away when hit by rocket
		int verticalSpeed = -15;
		Random random = new Random();
		int randomNumber = random.nextInt(10);
		if(randomNumber>=5)	//50% chance that player will fly to the left or to the right
		{
			horizontalSpeed*=-1;
		}
		player.getSprite().setXVeloc(horizontalSpeed);
		player.getSprite().setYVeloc(verticalSpeed);
		getExplosion().startAnimation((int)getSprite().getXCoord(), (int)getSprite().getYCoord());
		super.collisionEffect(player);
	}
}
