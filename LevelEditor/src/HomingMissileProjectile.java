import java.io.File;

/**
 * @(#)AceFighter4_9 -> Projectile Engine -> Homing Missile Projectile class
 *
 * A Homing Missile Projectile follows the opponents. It acts as a heatseeking missile
 * which follows opponents. Very cool stuff.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 04, 2008
 */
import java.util.*;
import java.io.*;
public class HomingMissileProjectile extends ProjectileEntity
{
	private Actor prey;	//the player after which the missile is currently
								//going after
	private int rangeOfEffect;	//the range of effect of the missile. Players
								//that are within that range will be targeted
								//and followed by the missile
	
	public HomingMissileProjectile(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Projectiles/HomingMissile.png"));
		setDamage(20);
		prey = null;
		rangeOfEffect = 250;
	}
	
	/*Overrides the update(gravity) method so that players can be followed.
	 */
	public void update(double gravity)
	{
		super.update(gravity);
		if(alive())
		{
			ArrayList<PlayerEntity> opponents = getOpponents();
			for(int i = 0; i<opponents.size(); i++)
			{
				if(opponents.get(i).alive())
				{
					double distanceToOpponent = distanceTo(opponents.get(i).getSprite());
					if(distanceToOpponent<=rangeOfEffect)	//If opponent is within the range of effect of gravity ball
					{
						//follow that player
						prey = opponents.get(i);
						followPlayer(prey);
					}
				}
			}	
		}
	}
	
	/*Follow Player
	 * @The player which should be followed.
	 * Follows the player passed in the parameter.
	 */
	public void followPlayer(Actor player)
	{
		if(player!=null)
		{		
			//Turn missile towards prey and change missile's velocity so that it moves towards prey player
			double preyX = player.getSprite().getXCoord();
			double preyY = player.getSprite().getYCoord();
			double xDiff = preyX-this.getSprite().getXCoord();
			double yDiff = preyY-this.getSprite().getYCoord();			
			double quotient = yDiff/xDiff;
			double computeAngle = Math.toDegrees(Math.atan(yDiff/xDiff));
			double newAngle = this.getSprite().getFaceAngle()+computeAngle;
			if(computeAngle<360)
			{
				computeAngle = (360+(int)computeAngle);			
			}
			if(computeAngle>360)
			{
				computeAngle = computeAngle-360;
			}
			if(xDiff<0)
			{
				computeAngle = computeAngle-180;
			}
			this.getSprite().setFaceAngle((int)computeAngle);
			if(this.getSprite().getXCoord()<preyX)	//if missile is on the left of prey
			{
				//increase xVeloc till hit max
				if(this.getSprite().getXVeloc()<5)
					this.getSprite().setXVeloc(this.getSprite().getXVeloc()+1);
			}
			if(this.getSprite().getXCoord()>preyX)	//if missie is on the right of prey
			{
				//decrease xVeloc till hit -max
				if(this.getSprite().getXVeloc()>-5)
					this.getSprite().setXVeloc(this.getSprite().getXVeloc()-1);
			}
			if(this.getSprite().getYCoord()<preyY)	//if missile is above prey
			{
				//increase yVeloc till hit max
				if(this.getSprite().getYVeloc()<5)
					this.getSprite().setYVeloc(this.getSprite().getYVeloc()+1);
			}
			if(this.getSprite().getYCoord()>preyY)	//if missile is below prey
			{
				//decrease yVeloc till hit -max
				if(this.getSprite().getYVeloc()>-5)
					this.getSprite().setYVeloc(this.getSprite().getYVeloc()-1);
			}
		}
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
