import java.io.File;

/**
 * @(#)AceFighter4_7 -> ItemEngine -> Rocket Weapon class
 *
 * The Rocket Weapon allows a player to fire rockets.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 03, 2008
 */

public class RocketWeapon extends WeaponEntity
{
	private static final int NUM_PROJECTILES = 10;
	
	public RocketWeapon(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Weapons/RocketWeapon.png"));
		setTimeToUse(1000);
		setFiringRate(15);
	}
	
	public void initializeProjectiles()
	{
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			getProjectiles().add(new RocketProjectile(0,0,25,15));
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
	
	public String toString()
	{
		return "Rocket Weapon";
	}
}
