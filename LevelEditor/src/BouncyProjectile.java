/**
 * @(#)AceFighter4_3 -> Projectile Engine -> Bouncy Projectile class
 *
 * A Bouncy Projectile is a type of projectile which, as the name implies, bounces off
 * of walls and platforms in a level.
 *
 * @author Chris Braunschweiler
 * @version 1.00 July 12, 2008
 */
import java.io.*;
public class BouncyProjectile extends ProjectileEntity
{
	private static final int NUM_BOUNCES = 5;	//a projectile can bounce 5 times before it dies
	private int currentBounce;
	
	public BouncyProjectile(int xCoord, int yCoord, int height, int width)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Projectiles/BouncyProjectile.png"));
		setDamage(8);
		currentBounce = 0;
	}
	
	public void collisionEffect(PlatformEntity platform)
	{
		if(currentBounce<NUM_BOUNCES)
		{
			if(platform instanceof PlatformEntity||platform instanceof AnimatedPlatformEntity)	//if projectile collides with a platform
			{
				//change y direction by inverting y velocity. Causes projectile to bounce away
				getSprite().setYVeloc(-getSprite().getYVeloc());
			}
			if(platform instanceof WallEntity || platform instanceof AnimatedWallEntity)
			{
				getSprite().setXVeloc(-getSprite().getXVeloc());
				getSprite().setYVeloc(-getSprite().getYVeloc());
			}
			currentBounce++;
		}
		else
		{
			//projectile has used up all its bounces -> it dies
			currentBounce = 0;
			super.collisionEffect(platform);
		}
	}
}
