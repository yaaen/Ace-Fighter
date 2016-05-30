/**
 * @(#)AceFighter1_2 -> Player Engine -> PlayerEntity class
 *
 * The PlayerEntity represents a player in the game. This player can either be
 * controlled by a user via keyboard and mouse or be controlled by the computer
 * (AI). PlayerEntities use PlayerSprites for positioning and animation. Player-
 * Entities contain several fields such as a list of opponents etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 17, 2008
 */
 
import java.util.*;
import java.io.*;

public abstract class PlayerEntity extends Actor implements Serializable
{
	private PlayerSprite sprite;	//the sprite used for animation, positioning etc...
	private boolean animating;		//true if the sprite should animate, false otherwise. Makes sure that outside engines can't control player animation even if they change player velocities.
//	private boolean spawning;		//true if the player is spawning. Used to decide when
									//to play the spawning animation.
	//private boolean dying;	//true if the player is dying. Used to decide when to play
							//the dying animation
	//private AnimationEntity spawningAnimation;
	//private AnimationEntity dyingAnimation;
	private boolean running;		//true if the player is running (not simply moving, but explicitly running)
	private double relationalVelocity;	//the relational velocity is used to allow the player to run on moving platforms.
										//Makes sure that player's velocity is in relation to the velocity of whatever platform he's standing
										//on. RelationalVelocity is in relation to the XVelocity.
	
	private int originalX;	//the original x coordinate at which player was initialized (allows player to be respawned at same place
							//as before)
	private int originalY;	//the original y coordinate at which player was initialized (allows player to be respawned at same place
							//as before)
	private boolean resettingPlayerPosition; //true if player's position is being reset (during a respawn, for example). This is used
						//to do the whole relationalPointDifference thing.
	private int spawnShiftX;	//the amount by which to shift the screen when the player is respawned (used by sidescrolling levels to scroll back
								//to a checkpoint if player dies)
	private int spawnShiftY;	//same as spawn shift x, except with y. Unlike relationalXDifference and relationalYDifference,
								//which are maintained in the Sprite class, these variables pertain only to players and are
								//thus kept track of in this class
	private boolean collidingWithWall;	//true if the player is currently colliding (used to check if player is colliding with walls only!).
								//If a player is colliding with a wall, his sprite's relationalPoints have to be changed to reflect the
								//coordinate change resulting from the collision. When a player is not colliding, his sprite's relationalPoints
								//are reset to 0.
	private int wallCollisionTime;	//amount of time that the player has been colliding with wall. Used to make
									//sure that collision is registered by game engine. Makes sure collision is happening for a full
									//loop iteration.
	//Attributes. In the HumanEntity, these are loaded from a PlayerProfile at the
	//start of the game and then loaded into the PlayerProfile for manipulation
	//through the Inventory screen
	//private String name;	//the name of the player
	private int lives;		//the number of lives of this player
	//private int health;		//the amount of health of this player
	private int speed;		//the running speed of the player
	private int jumpSpeed;	//the jump Speed of the player
	private int firingRate;	//the rate of fire of the standard weapon of this player
	private int fireRateCounter;	//used to fire projectiles at the rate specified by firingRate
	private int special;	//the amount of time the player can use his special weapon
	private int maximumSpecial;	//the current maximum amount of special the player can have
	private int originalSpecial;	//original amount of special time (time available to use special weapons)
	private int specialRechargeInterval;	//the interval at which the player's special power recharges.
									//the player's special bar recharges when it's not being used. It doesn't just
									//recharge by one every game loop iteration but it increases by 1 every couple of iterations.
									//This number determines how many gameloops have to pass before the special is incremented
									//again.
	private int specialCounter;	//keeps track of when to increment the special by 1. This value is modded (% - modulo)
								//by the specialRechargeInterval value. If the result is 0, the special is incremented.
	private int specialBarLength;	//length of special bar (in pixels)
	//private int originalHealth;	//the original amount of health the player had at initialization
	//private int healthBarLength; //the length in pixels of the health bar that should be displayed
	//private boolean alive;	//true if the player is alive, false otherwise
	private long score;	//the current score of the player
	private boolean inAir;	//true if the player is in the air (ie: has already jumped or been blown up)
	//private boolean firing;	//true if the player is firing. This is used to tell sprite when to do firing animation
	private boolean specialEquipped;	//true if the player has his special weapon equipped
	
	private ArrayList<WeaponEntity> temporaryWeapons;	//the weapons picked up in the level
	private ArrayList<WeaponEntity> specialWeapons;	//the special weapons that can be used
	
	private ArrayList<ProjectileEntity> projectiles;	//the standard projectiles that the player has
	
	private PlatformEntity currentPlatform;	//the platform the player is currently standing on. Used by the AIEntity
	
	public PlayerEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		sprite = new PlayerSprite(xCoord, yCoord, width, height);
		animating = false;
		//spawning = false;
		//dying = false;
		//spawningAnimation = new SpawningAnimation(xCoord,yCoord,50,50,this);
		//dyingAnimation = new DyingAnimation(xCoord,yCoord,50,50,this);
		running = false;
		relationalVelocity = 0;
		originalX = xCoord;
		originalY = yCoord;
		resettingPlayerPosition = false;
		spawnShiftX = 0;
		spawnShiftY = 0;
		collidingWithWall = false;
		wallCollisionTime = 0;
		//name = "";
		lives = 0;
		//health = 0;
		speed = 0;
		jumpSpeed = 0;
		firingRate = 0;
		fireRateCounter = 0;
		special = 0;
		maximumSpecial = 0;
		originalSpecial = 0;
		specialRechargeInterval = 5;
		specialCounter = 0;
		specialBarLength = 100;
		//originalHealth = 0;
		//healthBarLength = 100;
		//alive = true;
		inAir = false;
		//firing = false;
		specialEquipped = false;
		temporaryWeapons = new ArrayList<WeaponEntity>();
		specialWeapons = new ArrayList<WeaponEntity>();
		projectiles = new ArrayList<ProjectileEntity>();
		initializeProjectiles();
		opponents = new ArrayList<PlayerEntity>();
		currentPlatform = null;
	}
	
	//Accessor methods
	public PlayerSprite getSprite()
	{
		return sprite;
	}
	
	/*public boolean spawning()
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
	}*/
	
	protected boolean running()
	{
		return running;
	}
	
	public double getRelationalVelocity()
	{
		return relationalVelocity;
	}
	
	public int getSpawnShiftX()
	{
		return spawnShiftX;
	}
	
	public int getSpawnShiftY()
	{
		return spawnShiftY;
	}
	
	public boolean collidingWithWall()
	{
		return collidingWithWall;
	}
	
	/*public String getName()
	{
		return name;
	}*/
	
	public int getLives()
	{
		return lives;
	}
	
	/*public int getHealth()
	{
		return health;
	}*/
	
	/*public int getMaximumHealth()
	{
		return originalHealth;
	}*/
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getJumpSpeed()
	{
		return jumpSpeed;
	}
	
	public int getFiringRate()
	{
		return firingRate;
	}
	
	public int getSpecial()
	{
		return special;
	}
	
	public int getMaximumSpecial()
	{
		return maximumSpecial;
	}
	
	public int getSpecialBarLength()
	{
		return specialBarLength;
	}
	
	public long getScore()
	{
		return score;
	}
	
	/*public int getHealthBarLength()
	{
		return healthBarLength;
	}*/
	
	/*public boolean alive()
	{
		return alive;
	}*/
	
	public boolean inAir()
	{
		return inAir;
	}
	
	/*public boolean firing()
	{
		return firing;
	}*/
	
	public boolean specialEquipped()
	{
		return specialEquipped;
	}
	
	public ArrayList<WeaponEntity> getTemporaryWeapons()
	{
		return temporaryWeapons;
	}
	
	public ArrayList<WeaponEntity> getSpecialWeapons()
	{
		return specialWeapons;
	}
	
	public ArrayList<ProjectileEntity> getProjectiles()
	{
		return projectiles;
	}
	
	public PlatformEntity getCurrentPlatform()
	{
		return currentPlatform;
	}
	
	//Mutator Methods
	protected void setAnimating(boolean animating)
	{
		this.animating = animating;
	}
	
	/*public void setSpawning(boolean spawning)
	{
		this.spawning = spawning;
	}
	
	public void setDying(boolean dying)
	{
		this.dying = dying;
	}*/
	
	protected void setRunning(boolean running)
	{
		this.running = running;
	}
	
	public void setRelationalVelocity(double relationalVelocity)
	{
		this.relationalVelocity = relationalVelocity;
	}
	
	public void setOriginalX(int originalX)
	{
		this.originalX = originalX;
	}
	
	public void setOriginalY(int originalY)
	{
		this.originalY = originalY;
	}
	
	public void setCollidingWithWall(boolean collidingWithWall)
	{
		this.collidingWithWall = collidingWithWall;
	}
	
	/*public void setName(String name)
	{
		this.name = name;
	}*/
	
	public void setLives(int lives)
	{
		this.lives = lives;
	}
	
	/**
	 * This is visible to the outside. This can only set the initial health of
	 * player and should not be used to decrement health if player is dying.
	 */
	/*public void setInitialHealth(int health)
	{
		super.setHealth(health);
		originalHealth = health;
	}*/
	
	/**
	 * Sets the health of the player. It also makes sure that the player can't gain more
	 * health than his original health at the time he was spawned.
	 */
	public void setHealth(int health)
	{		
		super.setHealth(health);
		if(getHealth()>getMaximumHealth())	//prevents player from gaining too much health from health pack
			super.setHealth(getMaximumHealth());
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public void setJumpSpeed(int jumpSpeed)
	{
		this.jumpSpeed = jumpSpeed;
	}
	
	public void setFiringRate(int firingRate)
	{
		this.firingRate = firingRate;
	}
	
	public void setSpecial(int special)
	{
		this.special = special;
		//originalSpecial = special;
	}
	
	public void setMaximumSpecial(int special)
	{
		originalSpecial = special;
		maximumSpecial = special;
	}
	
	/*public void setHealthBarLength(int healthBarLength)
	{
		this.healthBarLength = healthBarLength;
	}*/
	
	/*public void setAlive(boolean alive)
	{
		this.alive = alive;
	}*/
	
	public void setScore(long score)
	{
		this.score = score;
	}
	
	public void setInAir(boolean inAir)
	{
		this.inAir = inAir;
	}
	
	/*public void setFiring(boolean firing)
	{
		this.firing = firing;
	}*/
	
	public void setSpecialEquipped(boolean specialEquipped)
	{
		this.specialEquipped = specialEquipped;
	}
	
	public void setTemporaryWeapons(ArrayList<WeaponEntity> temporaryWeapons)
	{
		this.temporaryWeapons = temporaryWeapons;
	}
	
	public void setSpecialWeapons(ArrayList<WeaponEntity> specialWeapons)
	{
		this.specialWeapons = specialWeapons;
		//Set this player as the owner of his weapons
		for(int i = 0; i<getSpecialWeapons().size(); i++)
		{
			getSpecialWeapons().get(i).setOwner(this);
		}
	}
	
	/*Finalize Special Weapons
	 * Completes any unfinished tasks pertaining to the special weapons.
	 */
	public void finalizeSpecialWeapons()
	{
		for(int i = 0; i<getSpecialWeapons().size(); i++)
		{
			getSpecialWeapons().get(i).setOpponents(this.getOpponents());
			for(ProjectileEntity projectile: getSpecialWeapons().get(i).getProjectiles())
			{
				projectile.setOpponents(this.getOpponents());
			}
		}
	}
	
	public void setCurrentPlatform(PlatformEntity currentPlatform)
	{
		this.currentPlatform = currentPlatform;
	}
	
	/*Get Current Special Bar Lenght
	 * Returns the current length of the special bar which reflects the current
	 * amount of special left that can be used to fire special weapons. This method
	 * works in the same way as the getCurrentHealthBarLength() method.
	 */
	public int getCurrentSpecialBarLength()
	{
		double currentSpecial = special;
		double maximumPossibleSpecial = originalSpecial;
		if(maximumPossibleSpecial==0){
			maximumPossibleSpecial = 100;
		}
		double quotient = currentSpecial/maximumPossibleSpecial;
		return (int)(specialBarLength*quotient);
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
	/*public int getCurrentHealthBarLength()
	{
		double currentHealth = getHealth();
		double maximumPossibleHealth = originalHealth;
		double quotient = currentHealth/maximumPossibleHealth;
		return (int)(healthBarLength*quotient);
	}*/
	
	/**
	 * Initialize Projectiles
	 * Initializes the player's projectiles.
	 */
	public void initializeProjectiles()
	{
		final int NUM_PROJECTILES = 20;
		for(int i = 0; i<NUM_PROJECTILES; i++)
		{
			projectiles.add(new ProjectileEntity(0,0,15,15));
		}
	}
	
	/**
	 * Update Method.
	 * @param The gravity of the current level.
	 * This method updates the state of the PlayerEntity. The gravity is used to update the y position
	 * with respect to the current level's gravity. Among other things updated are sprite animation,
	 * position with respect to both x and y velocity, check if player is dead and so forth.
	 */
	public void update(double gravity)
	{
		if(alive())	//only update player if he's alive
		{
			if(spawning())
			{
				getSpawningAnimation().update();
				if(resettingPlayerPosition)	//if player is being reset
				{
					//if(resettingPlayerTime>0)
						resettingPlayerPosition = false;
					//resettingPlayerTime++;
				}
				if(!resettingPlayerPosition)
				{
					//getSprite().setRelationalXDifference(0);
					//getSprite().setRelationalYDifference(0);
					spawnShiftX = 0;
					spawnShiftY = 0;
				}
			}
			else
			{
				sprite.animate();		//update sprite animation
				positionUpdate(gravity);
				animationUpdate();
				projectileUpdate(gravity);
				if(specialCounter%specialRechargeInterval==0)	//time to increment special
				{
					if(special<originalSpecial)	//keep special below maximum
						special++;
					specialCounter = 0;
				}
				specialCounter++;
				if(special<=0)
				{
					specialEquipped = false;
				}
				if(firing())
					fire();
				//Update state of temporary items and special weapons
				for(int i = 0; i<temporaryWeapons.size(); i++)
				{
					temporaryWeapons.get(i).update(gravity);
					if(temporaryWeapons.get(i).useExpired())	//lose item once it has expired
					{
						temporaryWeapons.remove(temporaryWeapons.get(i));
					}
				}
				for(int i = 0; i<specialWeapons.size(); i++)
				{
					specialWeapons.get(i).update(gravity);
				}
				if(collidingWithWall)
				{
					if(wallCollisionTime>=1)
						collidingWithWall = false;
					wallCollisionTime++;
				}
				if(!collidingWithWall)
				{
					getSprite().setRelationalXDifference(0);
					getSprite().setRelationalYDifference(0);
				}
				if(getHealth()<=0)	//check if player lost a life
				{
					die();
					/*
					lives--;					//take away a life
					health = originalHealth;	//restore health
					sprite.setXCoord(originalX);	//place player at original spawn position
					sprite.setYCoord(originalY);
					sprite.setXVeloc(0);
					sprite.setYVeloc(0);*/
				}
				if(lives<=0)	//check if player is out of lives (ie: completely dead)
				{
					setAlive(false);
				}
			}
		}
		if(dying())
		{
			getDyingAnimation().update();
		}
	}
	
	/*Spawn
	 * Spawns the player. It takes care of all the spawning details including starting
	 * the spawning animation.
	 */
	public void spawn()
	{
		if(lives>0)	//if player can still spawn
		{
			setHealth(getMaximumHealth());	//restore health
			resettingPlayerPosition = true;
			//sprite.setRelationalXDifference((int)(originalX-sprite.getXCoord()));
			//sprite.setRelationalYDifference((int)(originalY-sprite.getYCoord()));			
			spawnShiftX = (int)(originalX-sprite.getXCoord());
			spawnShiftY = (int)(originalY-sprite.getYCoord());			
			sprite.setXCoord(originalX);	//place player at original spawn position
			sprite.setYCoord(originalY);
			sprite.setXVeloc(0);
			sprite.setYVeloc(0);			
			getSpawningAnimation().startAnimation((int)sprite.getXCoord(),(int)sprite.getYCoord());
		}
	}
	
	/*Die
	 * Kills the player. It takes care of all the dying details including the starting
	 * of the dying animation.
	 */
	public void die()
	{
		if(!dying())	//if player is not already dying. Player can't die twice at the same time
		{
			getDyingAnimation().startAnimation((int)sprite.getXCoord(), (int)sprite.getYCoord());//start dying animation
			lives--;					//take away a life
		}
	}
	
	/**
	 * Position Update
	 * @param The gravity of the level.
	 * This method updates the player's position with respect to his x and y velocity. It also updates
	 * the y velocity with respect to the gravity of the level.
	 */
	private void positionUpdate(double gravity)
	{
		sprite.setXCoord(sprite.getXCoord()+sprite.getXVeloc());
		sprite.setYVeloc(sprite.getYVeloc()+gravity);	//update y velocity with respect to gravity
		sprite.setYCoord(sprite.getYCoord()+sprite.getYVeloc());	//update y coord with respect to y velocity
	}
	
	/**
	 * Animation Update
	 * This method updates the state of the sprite to match what the player entity is doing.
	 * For example, if the player entity is moving to the right, this method updates the sprite
	 * animation to show running-right animation.
	 */
	private void animationUpdate()
	{
		if(animating)
		{			
			if(sprite.getXVeloc()>0)	//if player is moving to the right
			{
				sprite.setDirection(1);
				sprite.setMovingHorizontally(true);
				sprite.setMovingVertically(false);
			}
			if(sprite.getXVeloc()<0)	//if player is moving to the left
			{
				sprite.setDirection(-1);
				sprite.setMovingHorizontally(true);
				sprite.setMovingVertically(false);
			}
			if(sprite.getXVeloc()==0)	//if player is standing still
			{
				sprite.setMovingHorizontally(false);
			}
			if(inAir())	//if player is jumping
			{
				sprite.setMovingHorizontally(false);
				sprite.setMovingVertically(true);
			}
			if(!inAir())	//if player is no longer jumping or being blown up
			{
				sprite.setMovingVertically(false);
			}
			if(firing())	//if the player is firing
			{
				sprite.setFiring(true);
			}
			if(!firing())	//if the player is not firing
			{
				sprite.setFiring(false);
			}
		}
	}
	
	/**
	 * Projectile Update
	 * Updates the projectiles of the player by calling their update() methods.
	 */
	public void projectileUpdate(double gravity)
	{
		for(int i = 0; i<projectiles.size();i++)
		{
			projectiles.get(i).update(gravity);
		}
		for(WeaponEntity weapon:getTemporaryWeapons())	//update projectiles of temporary weapon
		{
			for(ProjectileEntity projectile: weapon.getProjectiles())
			{
				projectile.update(gravity);
			}
		}
		for(WeaponEntity weapon:getSpecialWeapons())
		{
			for(ProjectileEntity projectile: weapon.getProjectiles())
			{
				projectile.update(gravity);
			}
		}
	}
	
	/**
	 * Fires the Player's weapons.
	 */
	public void fire()
	{
		if(specialWeapons.size()>0 && specialEquipped)
		{
			for(int i = 0; i<specialWeapons.size(); i++)
			{
				//use each item's functionality
				specialWeapons.get(i).useWeapon(getSprite().getFaceAngle());
			}
			special--;
		}
		else if(temporaryWeapons.size()>0)	//if player has any temporary items
		{
			for(int i = 0; i<temporaryWeapons.size(); i++)
			{
				//use each item's functionality
				temporaryWeapons.get(i).useWeapon(getSprite().getFaceAngle());
			}
		}
		else
		{
			if(fireRateCounter==firingRate)	//if firing rate allows a new projectile to be fired
			{
				fireRateCounter = 0;	//reset fire rate counter
				for(int i = 0; i<projectiles.size(); i++)
				{
					if(!projectiles.get(i).alive())	//if projectile to be fired is currently not alive
					{	
						//fire new projectile
						projectiles.get(i).setAlive(true);
						projectiles.get(i).getSprite().setXCoord(getSprite().getXCoord());
						projectiles.get(i).getSprite().setYCoord(getSprite().getYCoord());
						projectiles.get(i).getSprite().setXVeloc(Math.cos(Math.toRadians(getSprite().getFaceAngle()))*(projectiles.get(i).getSpeed()));		
						projectiles.get(i).getSprite().setYVeloc(Math.sin(Math.toRadians(getSprite().getFaceAngle()))*(projectiles.get(i).getSpeed()));
						/*projectiles.get(i).getSprite().setXVeloc(projectiles.get(i).getSprite().getXVeloc()+
								this.getSprite().getXVeloc());
						projectiles.get(i).getSprite().setYVeloc(projectiles.get(i).getSprite().getYVeloc()+
								this.getSprite().getYVeloc());*/
						//Only fire 1 projectile at a time
						i = projectiles.size();
					}
				}
			}
			else	//if it's not time to fire yet
			{
				//increment fire rate counter
				fireRateCounter++;
			}
		}
	}
	
	//Collision Effect methods
	public void collisionEffect(ProjectileEntity projectile)
	{
		if(alive())
		{
			setHealth(getHealth()-projectile.getDamage());
			getSprite().collide();
		}
	}
	
	public void collisionEffect(PlatformEntity platform)
	{
		if(alive())
		{
			setHealth(getHealth()-platform.getDamage());
			if(platform.getProperty().equals("Electric")||
					platform.getProperty().equals("Lava"))
			{
				getSprite().collide();
			}
		}
	}
	
	public void collisionEffect(WeaponEntity weapon)
	{
		temporaryWeapons.add(weapon);
	}
	
	abstract void executeCommand(Command command);
}
