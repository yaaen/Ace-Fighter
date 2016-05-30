/**
 * @(#)AceFighter4_3 -> ItemEngine -> Gravity Ball Weapon class
 *
 * The Gravity Ball Weapon fires one gravity ball. A gravity ball has a gravitational
 * field which pulls in all the opponents towards the gravity ball. Once the opponents
 * collide with the gravity ball, they take damage and get thrown into the air.
 *
 * @author Chris Braunschweiler
 * @version 1.00 July 13, 2008
 */
import java.util.*;
import java.io.*;

public class GravityBallWeapon extends WeaponEntity
{
	private static final int NUM_PROJECTILES = 1;
	
	public GravityBallWeapon(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImageFile(new File("Images/Weapons/GravityBallWeapon.png"));
		setTimeToUse(1000);
	}
	
	public void initializeProjectiles()
	{
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			getProjectiles().add(new GravityBall(0,0,40,40,getOpponents()));
		}
	}
	
	public void useWeapon(double faceAngle)
	{
		for(int i = 0; i<getProjectiles().size(); i++)
		{		
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
		return "Gravity Ball Weapon";
	}
}
