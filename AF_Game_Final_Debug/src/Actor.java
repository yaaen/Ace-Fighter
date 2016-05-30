/**
 * @(#)AceFighter6_0 -> Actor Engine -> Actor class
 *
 * The Actor class is used to summarize some of the characteristics and attributes of the
 * Player Engine and the Boss Engine. Since the Player and Boss engines share a lot of
 * the same functionalities and methods, this class will be used to summarize these
 * common attributes and actions/methods etc... It is an abstract class since an
 * Actor cannot be initialized. Some of the methods are also not implemented in this
 * class.
 *
 * An AIEntity also has the ability to execute commands (for the cutscenes).
 *
 * @author Chris Braunschweiler
 * @version 1.00 September 15, 2008
 */
import java.io.Serializable;
import java.util.*;
public abstract class Actor extends MovableObject implements Serializable
{
	private PlayerSprite sprite;
	private boolean spawning;
	private boolean dying; //true if the actor is dying. Used to decide when to play
							//the dying animation
	private AnimationEntity spawningAnimation;
	private AnimationEntity dyingAnimation;
	private String name;
	private int health;
	private int originalHealth;
	private int healthBarLength;
	private boolean alive;
	private boolean firing;
	private ArrayList<WeaponEntity> weapons;
	private boolean readyForCommand;	//true if the actor is ready to execute the next command
	private int elapsedExecutionTime;	//the amount of time the actor has been executing current command for
	protected ArrayList<PlayerEntity> opponents;
	
	public Actor(int xCoord, int yCoord, int width, int height)
	{
		sprite = new PlayerSprite(xCoord, yCoord, width, height);
		spawning = false;
		dying = false;
		spawningAnimation = new SpawningAnimation(xCoord,yCoord,50,50,this);
		dyingAnimation = new DyingAnimation(xCoord,yCoord,50,50,this);
		name = "";
		health = 0;
		originalHealth = 0;
		healthBarLength = 100;
		alive = true;
		firing = false;
		weapons = new ArrayList<WeaponEntity>();
		readyForCommand = false;
		elapsedExecutionTime = 0;
		opponents = new ArrayList<PlayerEntity>();
	}
	
	//Accessors
	public PlayerSprite getSprite()
	{
		return sprite;
	}
	
	public boolean spawning()
	{
		return spawning;
	}
	
	public boolean dying()
	{
		return dying;
	}
	
	public AnimationEntity getSpawningAnimation()
	{
		return spawningAnimation;
	}
	
	public AnimationEntity getDyingAnimation()
	{
		return dyingAnimation;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaximumHealth()
	{
		return originalHealth;
	}
	
	public int getHealthBarLength()
	{
		return healthBarLength;
	}
	
	public boolean alive()
	{
		return alive;
	}
	
	public boolean firing()
	{
		return firing;
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}
	
	public boolean readyForCommand()
	{
		return readyForCommand;
	}
	
	public int getElapsedExecutionTime()
	{
		return elapsedExecutionTime;
	}
	
	//Mutator methods
	public void setSpawning(boolean spawning)
	{
		this.spawning = spawning;
	}
	
	public void setDying(boolean dying)
	{
		this.dying = dying;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	/**
	 * This can only set the initial health of
	 * actor and should not be used to decrement health if actor is dying.
	 */
	public void setInitialHealth(int health)
	{
		originalHealth = health;
		this.health = health;
	}
	
	/* Set Maximum Health
	 * Basically the same as the above method except that it only
	 * expands the maximum possible health by the amount specified
	 * in the paramters. It's primarily used by the healthBarExpander
	 * power up.
	 */
	public void setMaximumHealth(int health)
	{
		originalHealth = health;
	}
	
	public void setHealthBarLength(int healthBarLength)
	{
		this.healthBarLength = healthBarLength;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public void setFiring(boolean firing)
	{
		this.firing = firing;
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
	}
	
	//Other common methods
	public void update(double gravity)
	{
		if(alive)
		{	
			sprite.animate();
		}
		if(dying)
		{
			dyingAnimation.update();
		}
		if(spawning)
		{
			spawningAnimation.update();
		}
	}
	
	public void fire()
	{
		for(int i = 0; i<weapons.size(); i++)
		{
			//use each item's functionality
			weapons.get(i).useWeapon(getSprite().getFaceAngle());
		}
	}
	
	/*Get Current Health Bar Length
	 * Returns the current length of the health bar which reflects the current health
	 * of the player. The length of the health bar is defined as a fix length (for example
	 * 100 pixels). That means that a player with 50 max health will have a health bar
	 * of length 100 pixels and a player with  300 max health will also have a health bar
	 * of length 100 pixels. The actual maximum possible health of a player does not affect
	 * the length of the health bar. This prevents players with a lot of health from having
	 * a really long health bar. In order to know how much health the player actually has
	 * (how much of the health bar should be filled out), a small computation has to be done.
	 * The current length of the health bar (reflects the current health of the player) can
	 * be obtained by dividing the actual current health by the total possible health and multiplying
	 * the result by the total health bar length.
	 * 
	 * current health bar length = (currentHealth/totalPossibleHealth)*healthBarLength
	 * So that's it. Nice and simple.
	 */
	public int getCurrentHealthBarLength()
	{
		double currentHealth = getHealth();
		double maximumPossibleHealth = originalHealth;
		double quotient = currentHealth/maximumPossibleHealth;
		return (int)(healthBarLength*quotient);
	}
	
	abstract void spawn();
	
	abstract void executeCommand(Command command);
	
	public void collisionEffect(ProjectileEntity projectile)
	{
		if(alive)
		{
			health-=projectile.getDamage();
			getSprite().collide();
		}
	}

	public ArrayList<PlayerEntity> getOpponents() {
		return opponents;
	}

	public void setOpponents(ArrayList<PlayerEntity> opponents) {
		this.opponents = opponents;
	}
}
