import java.io.File;
import java.util.Random;

/**
 * @(#)AceFighter4_7 -> Projectile Engine -> Rocket Projectile class
 *
 * A Rocket Projectile represents a rocket in Ace Fighter. It flies at a relatively
 * high speed and explodes upon collision with platforms or players.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 03, 2008
 */
public class RocketProjectile extends ProjectileEntity
{	
	public RocketProjectile(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Projectiles/Rocket.png"));
		setDamage(15);
		setSpeed(15);
	}
	
	public void collisionEffect(PlatformEntity platform)
	{
		getExplosion().startAnimation((int)getSprite().getXCoord(),(int)getSprite().getYCoord());
		super.collisionEffect(platform);
	}
	
	public void collisionEffect(PlayerEntity player)
	{
		int horizontalSpeed = 10;	//the speed at which things fly away when hit by rocket
		int verticalSpeed = -20;
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
