/**
 * @(#)AceFighter2_1 -> ItemEngine -> Scatter Fire Weapon class
 *
 * The Scatter Fire Weapon is a Weapon that fires projectiles in a shotgun-like
 * manner. It fires them out in a cone shape to maximize its range of effect.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 31, 2008
 */
import java.util.*;
import java.io.*;
 
public class ScatterFireWeapon extends WeaponEntity
{
	private static final int NUM_PROJECTILES = 30;
	
	public ScatterFireWeapon(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Weapons/ScatterFireWeapon.png"));
		setTimeToUse(1000);
		setFiringRate(3);
	}
	
	/**
	 * Initialize Projectiles
	 * Initializes the projectiles of the Scatter Fire Weapon
	 */
	public void initializeProjectiles()
	{
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			getProjectiles().add(new ProjectileEntity(0,0,10,10));
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
		return "Scatter Fire Weapon";
	}
}
