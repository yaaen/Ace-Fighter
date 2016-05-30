/**
 * @(#)AceFighter1_3 -> Projectile Engine -> ProjectileEntity class
 *
 * The Projectile Engine allows for Projectiles in Ace Fighter. Projectiles
 * are usually contained within ItemEntities. When Projectile Entities are fired,
 * they are set alive and their positions and states are updated accordingly.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 17, 2008
 */

import java.io.*; 
import java.util.ArrayList;
public class ProjectileEntity implements Serializable
{
	private Sprite sprite;	//the sprite used for positioning and display and collision etc...
	private int damage;		//the damage the projectile can inflict on someone that collides with it
	private int speed;		//the speed the projectile moves at. Default speed for projectiles is 5 pixels per second.
	private int range;		//the distance the projectile can fly before it dies
	private double originalX;	//the x position at which this projectile was fired -> used to see if projectile has exceeded
							//its flying range
	private double originalY;	//the y position at which this projectile was fired -> used to see if projectile has exceeded
							//its flying range
	private boolean alive;	//true if the projectile is alive
	private boolean exploding;	//true if the projectile is exploding
	private AnimationEntity explosion;	//allows the projectile to set off an explosion
	private ArrayList<PlayerEntity> opponents;	//the list of opponents. Allows the gravity ball to only pull in the opponents
	
	public ProjectileEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new Sprite(xCoord, yCoord, width, height);
		sprite.setImageFile(new File("Images/Projectiles/StandardProjectile.png"));
		damage = 5;
		speed = 10;	//default speed for projectiles.
		range = 1000;	//default range is 1000 pixels
		originalX = 0;
		originalY = 0;
		alive = false;
		exploding = false;
		explosion = new ExplosionEntity(xCoord,yCoord,50,50,this);
		opponents = new ArrayList<PlayerEntity>();
	}
	
	//Accessor Methods
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	protected int getRange()
	{
		return range;
	}
	
	public boolean alive()
	{
		return alive;
	}
	
	public boolean exploding()
	{
		return exploding;
	}
	
	public AnimationEntity getExplosion()
	{
		return explosion;
	}
	
	public ArrayList<PlayerEntity> getOpponents()
	{
		return opponents;
	}
	
	//Mutator Methods
	/**
	 * Set Sprite
	 * This method is protected because it should only be used within the Projectile
	 * Engine.
	 */
	protected void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	protected void setRange(int range)
	{
		this.range = range;
	}
	
	/**
	 * Set Alive
	 * @param True if this projectile's alive status is being set to true, false otherwise.
	 * This method is used to bring a projectile to life. It is primarily used to fire the projectile.
	 * In addition to simply setting the alive variable to true, this method also sets the originalX and
	 * originalY values. This makes sure that the projectile only remains alive as long as it hasnt exceeded
	 * its flying range. When a projectile is fired, the originalX and originalY are set equal to the position
	 * of the projectile at that point in time (when it's fired). Then as the projectile moves, it checks
	 * whether is has flown its range. Once it has flown its range, its alive status is set to false.
	 */
	public void setAlive(boolean alive)
	{
		originalX = this.getSprite().getXCoord();
		originalY = this.getSprite().getYCoord();
		this.alive = alive;
	}
	
	/*The exploding boolean is used to draw exploding projectiles that might
	 * already be dead. Since most projectiles die when they explode, their
	 * explosions wouldnt be able to be drawn (displayed on screen) after they're dead
	 * since the game only displays things that are alive. Thus, the exploding
	 * boolean is used. Projectiles that are dead can still be exploding. The game
	 * engine only draws explosions of projectiles that are exploding.
	 */
	public void setExploding(boolean exploding)
	{
		this.exploding = exploding;
	}
	
	public void setOpponents(ArrayList<PlayerEntity> opponents)
	{
		this.opponents = opponents;
	}
	
	/**
	 * Calculate Distance
	 * @param The 2 points whose distance from one another is to be calculated.
	 * @return The distance between 2 points.
	 * Calculates the distance between 2 points.
	 */
	public double getDistance(double x1, double y1, double x2, double y2)
	{
		double result;
		double xDiff = x2-x1;
		double yDiff = y2-y1;
		result = Math.sqrt((xDiff*xDiff)+(yDiff*yDiff));
		return Math.abs(result);	//return distance as an absolute value
	}
	
	/* Distance To
	 * @param The sprite to which the distance should be calculated.
	 * Calculates the distance between this projectile and the sprite passed in the
	 * parameter.
	 */
	public int distanceTo(Sprite sprite)
	{
		double xDist = this.getSprite().getXCoord()-sprite.getXCoord();
		xDist = Math.abs(xDist);
		double yDist = this.getSprite().getYCoord()-sprite.getYCoord();
		yDist = Math.abs(yDist);
		xDist = xDist*xDist;
		yDist = yDist*yDist;
		double distance = Math.sqrt(xDist+xDist);
		return (int)distance;
	}
	
	/**
	 * Update
	 * @param The gravity of the level.
	 * This method updates the state of the projectile. This state update is primarily a position
	 * update.
	 */
	public void update(double gravity)
	{
		if(alive)
		{
			getSprite().setXCoord(getSprite().getXCoord()+getSprite().getXVeloc());
			getSprite().setYCoord(getSprite().getYCoord()+getSprite().getYVeloc());
			if(getDistance(getSprite().getXCoord(), getSprite().getYCoord(), originalX, originalY)>=range)	//check if projectile has flown its range
			{
				explosion.terminateAnimation();
				alive = false;	//kill projectile
			}
		}
		if(exploding)
		{
			if(explosion.alive())
			{
				explosion.update();
			}
		}
	}
	
	//Collision Effect methods
	/**
	 * Collision Effect
	 * @param The player the projectile collides with.
	 * Upon collision with the specified player, the standard projectile slows down the player's
	 * horizontal velocity and the projectile dies.
	 */
	public void collisionEffect(PlayerEntity player)
	{
		alive = false;	//projectile dies
	}
	
	/**
	 * Collision Effect
	 * @param The platform the projectile collides with.
	 * Upon collision with a platform, the standard projectile simply dies.
	 */
	public void collisionEffect(PlatformEntity platform)
	{
		alive = false;
	}
	
	/**
	 * Collision Effect
	 * @param The BossComponent with which the projectile collides.
	 * Upon collision with the boss component, the projectile dies.
	 */
	public void collisionEffect(BossComponent bossComponent)
	{
		alive = false;
	}
}
