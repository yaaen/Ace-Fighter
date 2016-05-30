/**
 * @(#)AceFighter1_4 -> Item Engine -> WeaponEntity class
 *
 * A WeaponEntity is an Item that acts as a weapon. When a player picks up
 * a WeaponEntity or receives one at the end of battle, he can fire the projectiles
 * of the Item as if they were his standard projectiles. This of course means that
 * a WeaponEntity has a list of Projectiles among other things.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
 
import java.util.*;
 
public abstract class WeaponEntity extends ItemEntity
{
	private ArrayList<ProjectileEntity> projectiles;	//the projectiles of the Weapon
	private boolean equipped;	//true if the weapon is equipped. This is primarily used by the inventory.
	private boolean inUse;		//true if a player owns this weapon
	private int inUseTimer;		//counts how long a player has owned this weapon (so that a player
								//can't own it forever unless it's a special weapon)
	private int timeToUse;		//the amount of time this weapon can be used for
	private boolean unlimitedUse;	//true if a player can use the item for a period of time defined
									//outside of the Item Engine. An example use of this is when the
									//player equips the weapon in the inventory screen and thus can
									//use it as a special weapon in the following round. The player
									//can then use the weapon as long as his special bar hasn't run out.
									//The use of the weapon in that case is not limited by the inUseTimer.
	private boolean useExpired;		//true if the player has used up the item. When this is true, the player
									//that possessed the weapon, loses it.
	private ArrayList<PlayerEntity> opponents;	//the opponents of this weapon
	private int firingRate;	//the rate at which the projectiles of the weapon can be fired
	private int firingRateCounter;	//the counter used to see if it's time to fire a projectile
	
	public WeaponEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
		projectiles = new ArrayList<ProjectileEntity>();
		equipped = false;
		inUse = false;
		inUseTimer = 0;
		timeToUse = 150;	//default value is 150
		unlimitedUse = false;
		useExpired = false;
		opponents = new ArrayList<PlayerEntity>();
		initializeProjectiles();
		firingRate = 5;
		firingRateCounter = 0;
	}
	
	//Accessor Methods
	public ArrayList<ProjectileEntity> getProjectiles()
	{
		return projectiles;
	}
	
	public boolean equipped()
	{
		return equipped;
	}
	
	/**
	 * In Use
	 * @return True if the item is in use by a player, false otherwise.
	 * The player engine can use this to check whether the item is still in use by the
	 * player. If it's in use, then the player can use the item's projectiles, if it's
	 * not in use, the player can't use the projectiles of the item any longer.
	 */
	public boolean inUse()
	{
		return inUse;
 	}
	
	/**
	 * Unlimited Use
	 * This is primarily used by outside engines, therefore it has to be accessible.
	 */
	public boolean unlimitedUse()
	{
		return unlimitedUse;
	}
	
	/**
	 * Use Expired
	 * @return True if the use of this item has expired, false otherwise.
	 * This method is used to see if the item's use has been exhausted. When a player
	 * picks up an item, the use timer starts ticking. Once the timer runs out, this variable
	 * is set to true to let the owner know that he/she now loses the item.
	 */
	public boolean useExpired()
	{
		return useExpired;
	}
	
	public ArrayList<PlayerEntity> getOpponents()
	{
		return opponents;
	}
	
	public int getFiringRate()
	{
		return firingRate;
	}
	
	public int getFiringRateCounter()
	{
		return firingRateCounter;
	}
	
	//Mutator Methods
	public void setProjectiles(ArrayList<ProjectileEntity> projectiles)
	{
		this.projectiles = projectiles;
	}
	
	public void setEquipped(boolean equipped)
	{
		this.equipped = equipped;
	}
	
	public void setTimeToUse(int timeToUse)
	{
		this.timeToUse = timeToUse;
	}
	
	public void setUnlimitedUse(boolean unlimitedUse)
	{
		this.unlimitedUse = unlimitedUse;
	}
	
	public void setOpponents(ArrayList<PlayerEntity> opponents)
	{
		this.opponents = opponents;
	}
	
	public void setFiringRate(int firingRate)
	{
		this.firingRate = firingRate;
	}
	
	public void setFiringRateCounter(int firingRateCounter)
	{
		this.firingRateCounter = firingRateCounter;
	}
	
	/**
	 * Initialize Projectiles
	 * Initializes the projectiles of the respective Weapon.
	 */
	public abstract void initializeProjectiles();
	
	/**
	 * Spawn Weapon
	 * Spawns a weapon if the weapon can be spawned (ie: is not in use etc...)
	 * @param The x and y coordinates at which to spawn the item.
	 */
	public void spawn(int spawnX, int spawnY)
	{
		if(inUse()==false)	//if weapon is currently not in use
		{						
			getSprite().setXCoord(spawnX);
			getSprite().setYCoord(spawnY);
			getSprite().setYVeloc(0);
			setAvailable(true);
			useExpired = false;
		}
	}
	
	//Overridden methods
	/**
	 * Update Method
	 * @param The gravity of the level
	 * This method calls the super class' update() method to update the position etc.
	 * It also checks whether the Weapon is in use by anyone and updates the in use timer
	 * if it's in use.
	 */
	 public void update(double gravity)
	 {
	 	//if(!inUse)
	 	//{
	 		super.update(gravity);
	 	//}
	 	if(inUse && !unlimitedUse)	//update item if it's in use and has limited use
	 	{
	 		inUseTimer++;
	 		if(inUseTimer>=timeToUse)	//if item's use has expired
	 		{
	 			inUse = false;
	 			inUseTimer = 0;	//reset timer
	 			useExpired = true;	//its use has expired (player who owned it, loses item)
	 			//kill all projectiles
	 			for(ProjectileEntity projectile: projectiles)
	 			{
	 				projectile.setAlive(false);
	 				//Kill all explosions
	 				projectile.getExplosion().terminateAnimation();
	 			}
	 		}
	 	}
	 }
	 
	 /**
	  * Use Weapon
	  * Uses the functionality of the weapon
	  */
	 public abstract void useWeapon(double faceAngle);
	 
	 //Collision Effect methods
	 /**
	  * Collision Effect
	  * @param The player that collides with the Weapon (picks up the weapon).
	  * In addition to setting available to false and setting the player as the owner of
	  * the item, the Weapon's opponents have to be set equal to the player's opponents.
	  * Also the Weapon's inUse boolean is set to true -> The item is now in use.
	  */
	 public void collisionEffect(PlayerEntity player)
	 {
	 	if(available())
	 	{
		 	super.collisionEffect(player);
		 	opponents = player.getOpponents();
		 	inUse = true;	//item is now in use by the player
		 	//Kill all projectiles, just in case there are left over projeciles from the last
		 	//time the item was in use.
		 	for(ProjectileEntity projectile: projectiles)
 			{
 				projectile.setAlive(false);
 				//Kill all explosions
 				projectile.getExplosion().terminateAnimation();
 			}
	 	}
	 }
}
