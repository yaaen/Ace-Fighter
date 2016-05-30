/**
 * @(#)AceFighter1_1 -> Platform Engine -> PlatformEntity class
 *
 * This class provides the basic framework for the Platform Engine. The Platform
 * Engine allows for having platforms in the game. Platforms may be static, they 
 * might move either on the horizontal axis or on the vertical axis, they can
 * animate, and they can possess special properties such as icy which causes a player
 * to slip when he steps on the platform.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */

import java.util.*; 
import java.io.*;
 
public class PlatformEntity implements Serializable
{
	private Sprite sprite;	//the Sprite used to position and display the Platform
	private String property;	//The property of the platform. This determines how
								//entities (particularly players) that come into contact
								//with the platform, react. A value of 'None' means the
								//platform doesn't possess a special property.
	private int damage;			//the damage this platform can do to anything (player) that it touches
	private String movement;	//Determines how the platform moves (if at all). A
								//value of 'None' means that the platform does not move.
								//A value of 'Horizontal' means the platform moves along
								//the x axis (side to side). A value of 'Vertical' means
								//the platform moves along the y axis (Up and down).
	private int range;			//The distance the platform moves (if it moves) from the original starting position.
	private int movementSpeed;	//The speed at wich the platform moves
	private int originalX;		//The starting x position. X position at initialization of platform.
	private int originalY;		//The starting y position. Y position at initialization of platform.
	
	public PlatformEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new Sprite(xCoord, yCoord, width, height);
		property = "None";
		damage = 0;
		movement = "None";
		range = 0;
		movementSpeed = 0;
		originalX = xCoord;
		originalY = yCoord;
	}
	
	//Accessors
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public String getProperty()
	{
		return property;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public String getMovement()
	{
		return movement;
	}
	
	public int getRange()
	{
		return range;
	}
	
	public int getMovementSpeed()
	{
		return movementSpeed;
	}
	
	//Mutators
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void setProperty(String property)
	{
		this.property = property;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public void setMovement(String movement)
	{
		this.movement = movement;
	}
	
	public void setRange(int range)
	{
		this.range = range;
	}
	
	public void setMovementSpeed(int movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}
	
	public void setOriginalX(int originalX)
	{
		this.originalX = originalX;
	}
	
	public void setOriginalY(int originalY)
	{
		this.originalY = originalY;
	}
	
	/**Updates the state of the platform
	 *If this platform is a moving platform, for example, it updates the position of the
	 *platform in the proper axis.
	 */
	public void update()
	{
		if(movement.equals("Horizontal"))
		{
			if(sprite.getXCoord()>=originalX+range || sprite.getXCoord()<originalX)	//Change direction once end of range reached
				movementSpeed*=-1;
			sprite.setXVeloc(movementSpeed);
		}
		if(movement.equals("Vertical"))
		{
			if(sprite.getYCoord()>=originalY+range || sprite.getYCoord()<originalY)	//Change direction once end of range reached
				movementSpeed*=-1;
			sprite.setYVeloc(movementSpeed);
		}
		sprite.setXCoord(sprite.getXCoord()+sprite.getXVeloc());
		sprite.setYCoord(sprite.getYCoord()+sprite.getYVeloc());
	}
	
	//Collision Effect methods. These methods are called when the Platform collides with other entities.
	/*
	public void collisionEffect(ProjectileEntity projectile)
	{
	}*/
	
	public void collisionEffect(PlayerEntity player)
	{
		if(property.equals("Lava"))	//if platform is lava
		{
			player.setInAir(true);
			player.getSprite().setYVeloc(-20);		//fire player up at a speed of 5 pixels per loop iteration
			Random random = new Random();	//randomly generate an x velocity between 0 and 5
			int direction;
			if(random.nextInt()>=5)
				direction = 1;
			else
				direction = -1;
			player.getSprite().setXVeloc(direction*random.nextInt(10));
		}
		if(property.equals("Electric"))	//if platform is electrically charged
		{
			player.setInAir(true);
			player.getSprite().setYVeloc(-15);
			Random random = new Random();
			int direction;
			if(random.nextInt()>=5)
				direction = 1;
			else
				direction = -1;
			player.getSprite().setXVeloc(direction*random.nextInt(15));
		}
		if(property.equals("Ice"))	//if platform is icy
		{
			if(player.getSprite().getYCoord()+player.getSprite().getHeight()-30<=this.getSprite().getYCoord())
			{
				//create a slipping effect
				if(player.getSprite().getYVeloc()>getSprite().getYVeloc())
				{
					player.getSprite().setYCoord(getSprite().getYCoord()-player.getSprite().getHeight());
					if(player.getSprite().getXVeloc()==0)
					{
						player.setInAir(false);
					}
					if(!player.running())
					{
						if(player.getSprite().getXVeloc()>0)
						{
							player.setRelationalVelocity(player.getSpeed());
							player.getSprite().setXVeloc(player.getSprite().getXVeloc()-1);
						}
						if(player.getSprite().getXVeloc()<0)
						{
							player.setRelationalVelocity(-player.getSpeed());
							player.getSprite().setXVeloc(player.getSprite().getXVeloc()+1);
						}
					}
					if(player.running())
					{
						player.setRelationalVelocity(getSprite().getXVeloc());
					}
					player.getSprite().setYVeloc(getSprite().getYVeloc());	//player's Y velocity stops (stops falling etc)
				}
			}
		}
		if(property.equals("ConveyorbeltLeft"))	//if platform is a conveyor belt to the left
		{
			if(player.getSprite().getYCoord()+player.getSprite().getHeight()-30<=this.getSprite().getYCoord())
			{
				if(player.getSprite().getYVeloc()>getSprite().getYVeloc())
				{
					player.setInAir(false);	//player is no longer in the air
					player.getSprite().setYCoord(getSprite().getYCoord()-player.getSprite().getHeight());
					if(!player.running())	//keeps collision effect from interfering with keyboard controls
					{
						player.getSprite().setXVeloc(-5);	//move player to the left
					}
					if(player.running())	//if player is running
					{
						//Set player's relational velocity equal to platform's velocity so that when player tries to run,
						//his running speed is relative to the current speed of the platform
						player.setRelationalVelocity(-5);	//move player to the left
					}
					player.getSprite().setYVeloc(getSprite().getYVeloc());	//player's Y velocity stops (stops falling etc)
				}
			}
		}
		if(property.equals("ConveyorbeltRight"))
		{
			if(player.getSprite().getYCoord()+player.getSprite().getHeight()-30<=this.getSprite().getYCoord())
			{
				if(player.getSprite().getYVeloc()>getSprite().getYVeloc())
				{
					player.setInAir(false);	//player is no longer in the air
					player.getSprite().setYCoord(getSprite().getYCoord()-player.getSprite().getHeight());
					if(!player.running())	//keeps collision effect from interfering with keyboard controls
					{
						player.getSprite().setXVeloc(5);	//move player to the left
					}
					if(player.running())	//if player is running
					{
						//Set player's relational velocity equal to platform's velocity so that when player tries to run,
						//his running speed is relative to the current speed of the platform
						player.setRelationalVelocity(5);	//move player to the left
					}
					player.getSprite().setYVeloc(getSprite().getYVeloc());	//player's Y velocity stops (stops falling etc)
				}
			}
		}
		if(property.equals("Water"))
		{
			if(player.getSprite().getYVeloc()>getSprite().getYVeloc())	//if player is falling onto platform
			{
				player.setInAir(false);
				//water slows down the falling speed
				if(player.getSprite().getYVeloc()>4)	//as long as player is faster than the sinking speed
				{
					//slow player's fall down
					player.getSprite().setYVeloc(player.getSprite().getYVeloc()-1);
				}
			}
		}
		if(property.equals("None"))	//if the platform has no property
		{
			if(player.getSprite().getYCoord()+player.getSprite().getHeight()-30<=this.getSprite().getYCoord())
			{
				if(player.getSprite().getYVeloc()>getSprite().getYVeloc())	//if player is falling onto platform
				{			
					player.setInAir(false);	//player is no longer in the air
					player.getSprite().setYCoord(getSprite().getYCoord()-player.getSprite().getHeight());
					if(!player.running())	//keeps collision effect from interfering with keyboard controls
					{
						player.getSprite().setXVeloc(getSprite().getXVeloc());
					}
					if(player.running())	//if player is running
					{
						//Set player's relational velocity equal to platform's velocity so that when player tries to run,
						//his running speed is relative to the current speed of the platform
						player.setRelationalVelocity(getSprite().getXVeloc());
					}
					player.getSprite().setYVeloc(getSprite().getYVeloc());	//player's Y velocity stops (stops falling etc)
				}
			}
		}
	}
	
	/*public void collisionEffect(ItemEntity item)
	{
	}*/
	
	//For testing and debugging
	public String toString()
	{
		String s = ("xCoord: " + getSprite().getXCoord() + "\n" +
			"yCoord: " + getSprite().getYCoord() + "\n" +
			"width: " + getSprite().getWidth() + "\n" +
			"height: " + getSprite().getHeight() + "\n" +
			"property: " + property + "\n" +
			"damage: " + damage + "\n" +
			"movement: " + movement + "\n" +
			"range: " + range + "\n" +
			"movementSpeed: " + movementSpeed);
		return s;
	}
}
