/**
 * @(#)AceFighter4_8 -> ItemEngine -> Scatter Rocket Weapon class
 *
 * The scatter rocket weapon fires out rockets in a fan-like fashion. It's basically
 * a scatter fire except that instead of firing standard projectiles, it fires
 * rockets.
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 04, 2008
 */
import java.io.File;
import java.util.*;
public class ScatterRocketWeapon extends WeaponEntity
{
	private static final int NUM_PROJECTILES = 20;
	
	public ScatterRocketWeapon(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Weapons/ScatterRocketWeapon.png"));
		setTimeToUse(1000);
		setFiringRate(4);
	}
	
	/**
	 * Initialize Projectiles
	 * Initializes the projectiles of the Scatter Fire Weapon
	 */
	public void initializeProjectiles()
	{
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			getProjectiles().add(new RocketProjectile(0,0,25,15));
		}
	}
	
	/**
	 * Use Weapon
	 * Fires the projectiles in a shotgun-like cone effect...utter annhiliation!
	 */
	public void useWeapon(double faceAngle)
	{
		if(getFiringRateCounter()==getFiringRate())
		{
			setFiringRateCounter(0);
			int first = 0;
			for(int i = 0; i<getProjectiles().size(); i++)
			{
				if(i%3==0)
				{	
					//First bullet
					first = i;
					//Middle bullet
					if(!(getProjectiles().get(i).alive()))	//if this bullet doesnt already exist
					{
						//projectiles.get(i).setAlive(true);
						getProjectiles().get(i).getSprite().setXCoord(getOwner().getSprite().getXCoord());
						getProjectiles().get(i).getSprite().setYCoord(getOwner().getSprite().getYCoord());
						getProjectiles().get(i).getSprite().setXVeloc(Math.cos(Math.toRadians(getOwner().getSprite().getFaceAngle()+30))*getProjectiles().get(i).getSpeed());	//5 is just for now.		
						getProjectiles().get(i).getSprite().setYVeloc(Math.sin(Math.toRadians(getOwner().getSprite().getFaceAngle()+30))*getProjectiles().get(i).getSpeed());
						getProjectiles().get(i).getSprite().setFaceAngle(faceAngle+30);
						getProjectiles().get(i).setAlive(true);
						//only make 1 bullet at a time.
						i = getProjectiles().size();
					}
	
				}
				else if(i==(first+1))
				{
					//Middle bullet
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
				else
				{
					//Third bullet
					//Middle bullet
					if(!(getProjectiles().get(i).alive()))	//if this bullet doesnt already exist
					{
						//projectiles.get(i).setAlive(true);
						getProjectiles().get(i).getSprite().setXCoord(getOwner().getSprite().getXCoord());
						getProjectiles().get(i).getSprite().setYCoord(getOwner().getSprite().getYCoord());
						getProjectiles().get(i).getSprite().setXVeloc(Math.cos(Math.toRadians(getOwner().getSprite().getFaceAngle()-30))*getProjectiles().get(i).getSpeed());	//5 is just for now.		
						getProjectiles().get(i).getSprite().setYVeloc(Math.sin(Math.toRadians(getOwner().getSprite().getFaceAngle()-30))*getProjectiles().get(i).getSpeed());
						getProjectiles().get(i).getSprite().setFaceAngle(faceAngle-30);
						getProjectiles().get(i).setAlive(true);
						//only make 1 bullet at a time.
						i = getProjectiles().size();
					}
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
		return "Scatter Rocket Weapon";
	}
}
