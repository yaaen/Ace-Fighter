/**
 * @(#)AceFighter1_5 -> Boss Engine -> BossComponent
 *
 * The BossComponent is a part of the Boss Entity. A Boss Entity consists of a
 * list of BossComponents. There are 3 different types of BossComponents: Turrets,
 * Shields and Cockpits.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */

import java.util.*;
import java.io.*;

public class BossComponent extends Actor implements Serializable
{
	private BossSprite sprite;	//the sprite used for animation
	private String type;		//the type of BossComponent
	//private String name;		//The name of the BossComponent
	private boolean damageable;	//true if this boss component takes damage when
								//hit with a projectile
	//private int materialIntegrity;	//the material integrity is the health of this
									//boss component. If the material integrity is 0,
									//that means the boss component is broke (dead)
	//private boolean alive;			//true if the boss component is alive, false otherwise
									//when the material integrity becomes 0, the boss component's
									//alive boolean is set to false
	//private ArrayList<WeaponEntity> weapons;	//the weapons of this boss component
	//private boolean readyForCommand;	//true if the boss component is ready to execute
										//the next command
	//private int elapsedExecutionTime;	//the amount of time the current command has been
										//in execution for
										
	//Static variables
	private static final int MOVEMENT_SPEED = 5;	//the speed at which boss components move when they're
													//executing commands
										
	public BossComponent(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		sprite = new BossSprite(xCoord,yCoord,width,height);
		type = "";
		//name = "";
		damageable = false;
		//materialIntegrity = 0;
		//alive = true;
		//weapons = new ArrayList<WeaponEntity>();
		//readyForCommand = false;
		//elapsedExecutionTime = 0;
	}
	
	//Accessor Methods
	public BossSprite getSprite()
	{
		return sprite;
	}
	
	public String getType()
	{
		return type;
	}
	
	/*public String getName()
	{
		return name;
	}*/
	
	public boolean damageable()
	{
		return damageable;
	}
	
	/*public int getMaterialIntegrity()
	{
		return materialIntegrity;
	}*/
	
	/*public boolean alive()
	{
		return alive;
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}*
	
	public boolean readyForCommand()
	{
		return readyForCommand;
	}
	
	public int getElapsedExecutionTime()
	{
		return elapsedExecutionTime;
	}*/
	
	//Mutator Methods
	public void setType(String type)
	{
		this.type = type;
	}
	
	/*public void setName(String name)
	{
		this.name = name;
	}*/
	
	public void setDamageable(boolean damageable)
	{
		this.damageable = damageable;
	}
	
	/*public void setMaterialIntegrity(int materialIntegrity)
	{
		this.materialIntegrity = materialIntegrity;
	}*/
	
	/*public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public void setWeapons(ArrayList<WeaponEntity> weapons)
	{
		this.weapons = weapons;
	}
	
	public void setReadyForCommand(boolean readyForCommand)
	{
		this.readyForCommand = readyForCommand;
	}
	
	public void setElapsedExecutionTime(int elapsedExecutionTime)
	{
		this.elapsedExecutionTime = elapsedExecutionTime;
	}*/
	
	public void spawn()
	{
		getSpawningAnimation().startAnimation((int)sprite.getXCoord(),(int)sprite.getYCoord());
	}
	
	public void die()
	{
		if(!dying())	//if player is not already dying. Player can't die twice at the same time
		{
			getDyingAnimation().startAnimation((int)sprite.getXCoord(), (int)sprite.getYCoord());//start dying animation
		}
	}
	
	/*Finalize Weapons
	 * Completes any unfinished tasks pertaining to the special weapons.
	 */
	public void finalizeWeapons()
	{
		for(int i = 0; i<getWeapons().size(); i++)
		{
			getWeapons().get(i).setOpponents(this.getOpponents());
			for(ProjectileEntity projectile: getWeapons().get(i).getProjectiles())
			{
				projectile.setOpponents(this.getOpponents());
			}
		}
	}
	
	/**
	 * Update
	 * Updates sprite animation and executes commands.
	 */
	public void update(double gravity)
	{
		if(alive()&&!spawning()&&!dying()){
			sprite.animate();
			if(firing())
				fire();
			for(WeaponEntity weapon: getWeapons())
			{
				for(ProjectileEntity projectile: weapon.getProjectiles())
				{
					projectile.update(gravity);
				}
			}
			if(spawning())
			{
				getSpawningAnimation().update();
			}
		}
		if(dying())
		{
			getDyingAnimation().update();
		}
	}
	
	/**
	 * Collision Effect
	 * @param The projectile this boss component is colliding with.
	 * If the boss component is damageable, take damage. Also check if
	 * the boss component is dead by checking whether material integrity is
	 * less than or equal to 0.
	 */
	public void collisionEffect(ProjectileEntity projectile)
	{
		if(damageable)
		{
			//materialIntegrity = materialIntegrity - projectile.getDamage();
			setHealth(getHealth()-projectile.getDamage());
			//if(materialIntegrity<=0)
			if(getHealth()<=0)
			{
				setAlive(false);
				die();
			}
		}
	}
	
	/**Command Related Methods
	 * The following methods are necessary so that the boss component can execute
	 * the various commands sent to it by the boss entity.
	 */
	
	/** Execute Command
	 * @param The command to be executed.
	 * This method executes the command passed to it. It uses the movement methods
	 * defined below to execute any commands having to do with movement.
	 */ 
	public void executeCommand(Command command)
	{
		String componentType = command.getNameOrType();
	 	String action = command.getAction();
	 	int duration = command.getDuration();
	 	int absDuration = Math.abs(duration);	//absolute value of the duration
	 	if(componentType.equals("Boss")||componentType.equals(this.getType())||
	 		componentType.equals(this.getName()))
	 	{
	 		if(getElapsedExecutionTime()<absDuration)	//if current command is not finished executing
	 		{
	 			//execute commands
	 			if(action.equals("MoveX"))
	 			{
	 				if(duration>0)
	 				{
	 					moveRight(MOVEMENT_SPEED);
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 				}
	 				if(duration<0)
	 				{
	 					moveLeft(MOVEMENT_SPEED);
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 				}
	 				setFiring(false);
	 				getSprite().setFiring(false);
	 			}
	 			if(action.equals("MoveY"))
	 			{
	 				if(duration>0)
	 				{
	 					moveDown(MOVEMENT_SPEED);
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 				}
	 				if(duration<0)
	 				{
	 					moveUp(MOVEMENT_SPEED);
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 				}
	 				setFiring(false);
	 				getSprite().setFiring(false);
	 			}
	 			if(action.equals("Fire"))
	 			{
	 				if(alive())
	 				{
		 				if(duration>0)
		 				{
		 					getSprite().setDirection(1);
		 					getSprite().setFaceAngle(0);
		 				}
		 				if(duration<0)
		 				{
		 					getSprite().setDirection(-1);
		 					getSprite().setFaceAngle(180);
		 				}
		 				setFiring(true);
		 				getSprite().setFiring(true);
	 				}
	 				setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 			}
	 		}
	 		else if(getElapsedExecutionTime()>=absDuration)	//if current command is finished executing
	 		{
	 			setReadyForCommand(true);
	 		}
	 	}
	}
	 
	/**
	 * Move Up
	 * @param The Speed at which to move this boss component.
	 * Moves the boss component up at the speed specified in the parameter.
	 */
	protected void moveUp(int speed)
	{
		getSprite().setAnimating(true);
		getSprite().setMovingHorizontally(false);
		getSprite().setMovingVertically(true);
		getSprite().setYCoord(getSprite().getYCoord()-speed);
	}
	
	/**
	 * Move Down
	 * @param The speed at which to move this boss component.
	 * Moves the boss component down at the speed passed in the parameter.
	 */
	protected void moveDown(int speed)
	{
		getSprite().setAnimating(true);
		getSprite().setMovingHorizontally(false);
		getSprite().setMovingVertically(false);
		getSprite().setYCoord(getSprite().getYCoord()+speed);
	}
	
	/**
	 * Move Left
	 * @param The speed at which to move this boss component.
	 * Moves boss component to the left at the speed specified in the parameter.
	 */
	protected void moveLeft(int speed)
	{
		getSprite().setDirection(-1);
		getSprite().setAnimating(true);
		getSprite().setMovingHorizontally(true);
		getSprite().setMovingVertically(false);
		getSprite().setXCoord(getSprite().getXCoord()-speed);
	}
	
	/**
	 * Move Right
	 * @param The speed at which to move this boss component.
	 * Moves the boss component to the right at the speed passed in the parameter.
	 */
	protected void moveRight(int speed)
	{
		getSprite().setDirection(1);
		getSprite().setAnimating(true);
		getSprite().setMovingHorizontally(true);
		getSprite().setMovingVertically(false);
		getSprite().setXCoord(getSprite().getXCoord()+speed);
	}
	
	//For testing and debugging
	public String toString()
	{
		String s = ("xCoord: " + getSprite().getXCoord() + "\n" +
			"yCoord: " + getSprite().getYCoord() + "\n" +
			"width: " + getSprite().getWidth() + "\n" +
			"height: " + getSprite().getHeight() + "\n" +
			"type: " + type + "\n" + 
			"name: " + getName() + "\n" +
			"damageable? " + damageable);
		return s;
	}
}
