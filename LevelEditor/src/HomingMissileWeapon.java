import java.io.File;

/**
 * @(#)AceFighter4_9 -> ItemEngine -> Homing Missile Weapon class
 *
 * The Homing Missile Weapon allows a player to fire homing missiles.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 04, 2008
 */
public class HomingMissileWeapon extends WeaponEntity
{
	private static final int NUM_PROJECTILES = 3;
	
	public HomingMissileWeapon(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Weapons/HomingMissileWeapon.png"));
		setTimeToUse(1000);
		setFiringRate(20);
	}
	
	public void initializeProjectiles()
	{
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			getProjectiles().add(new HomingMissileProjectile(0,0,30,15));
		}
	}
	
	public void useWeapon(double faceAngle)
	{
		if(getFiringRateCounter()==getFiringRate())
		{
			setFiringRateCounter(0);
			for(int i = 0; i<getProjectiles().size(); i++)
			{		
				if(!(getProjectiles().get(i).alive()))	//if this bullet doesnt already exist
				{
					//projectiles.get(i).setAlive(true);
					getProjectiles().get(i).getSprite().setXCoord(getOwner().getSprite().getXCoord());
					getProjectiles().get(i).getSprite().setYCoord(getOwner().getSprite().getYCoord());
					getProjectiles().get(i).getSprite().setXVeloc(Math.cos(Math.toRadians(getOwner().getSprite().getFaceAngle()))*getProjectiles().get(i).getSpeed());	//5 is just for now.		
					getProjectiles().get(i).getSprite().setYVeloc(Math.sin(Math.toRadians(getOwner().getSprite().getFaceAngle()))*getProjectiles().get(i).getSpeed());
					getProjectiles().get(i).getSprite().setFaceAngle(faceAngle);
					getProjectiles().get(i).setAlive(true);
					//only make 1 bullet at a time.
					i = getProjectiles().size();
				}
			}
		}
		else
		{
			setFiringRateCounter(getFiringRateCounter()+1);
		}
	}
	
	public void collisionEffect(PlayerEntity player)
	{
	 	if(available())
	 	{
		 	super.collisionEffect(player);
		 	for(int i = 0; i<getProjectiles().size();i++)
		 	{
		 		getProjectiles().get(i).setOpponents(player.getOpponents());
		 	}
	 	}
	}
	
	public String toString()
	{
		return "Homing Missile Weapon";
	}
}
