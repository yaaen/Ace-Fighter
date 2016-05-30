/**
 * @(#)LevelEditor1_3 -> Level Entity class
 *
 * This class represents the actual level with all its elements and components. It
 * consists of lists of platforms, opponents, allies etc... Basically the level
 * that's being edited and displayed and that will also be played in.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 13, 2008
 */
 
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

public class LevelEntity implements Serializable
{
	/**********Basic Level Attributes****************/
	private String staticOrDynamic;	//determines whether the level is side-scrolling or static
	private double gravity;			//the gravity of the level
	private int backgroundX;		//the xCoord of the background image
	private int backgroundY;		//the yCoord of the background image
	private int backgroundWidth;	//the width of the background image
	private int backgroundHeight;	//the height of the background image
	private transient Image backgroundImage;	//the background image
	private File backgroundImageFile;	//the image file of the image. Used to serialize image
	private Color backgroundColor;	//the background color of the level
	private int humanX;				//the starting x position of the human player
	private int humanY;				//the starting y position of the human player
	private boolean levelFinished;	//true if the level has been completed (played through)
	private int itemSpawnTime;		//the amount of time between item spawns
	private int leftBound;		//the left edge of the update field. everything between this edge and the right bound will be
								//updated during the game loop iteration. This is supposed to optimize the game engine so that only the
								//things that are close to the player are updated
	private int rightBound;		//the right edge of the update field.
	private int lowerBound;
	private int upperBound;
	private int deadLine;	//whoever falls below this line is dead (gets respawned if they have lives left). It simply represents a y coordinate.
	private boolean cutscene;	//true if a cutscene is running
	
	/**********Level components*********************/
	private HumanEntity human;	//the avatar of the human player
	private ArrayList<PlatformEntity> platforms;	//the platforms in the level
	private ArrayList<AnimatedPlatformEntity> animatedPlatforms;	//the animated platforms of the level
	private ArrayList<WallEntity> walls;	//the walls in the level
	private ArrayList<AnimatedWallEntity> animatedWalls;	//the animated walls in the level
	private ArrayList<AIEntity> opponents;	//the opponents in the level
	private ArrayList<AIEntity> allies;	//the allies in the level
	private ArrayList<BossEntity> bosses;	//the bosses in the level
	private ArrayList<CheckPointEntity> checkpoints;	//the checkpoints in the game
	private ArrayList<PortalEntity> portals;	//the portals in the level
	private ArrayList<PowerUpEntity> powerups;	//the powerups that spawn after killing opponents
	private ArrayList<WeaponEntity> weapons;	//the list of weapons that spawn in the level
	private ArrayList<DecorationEntity> foregroundDecorations;	//the foreground images used for decoration
	private ArrayList<DecorationEntity> backgroundDecorations;	//the background images used for decoration
	
	public LevelEntity()
	{
		staticOrDynamic = "Static";
		gravity = 0.2;
		backgroundX = 0;
		backgroundY = 0;
		backgroundWidth = 0;
		backgroundHeight = 0;
		backgroundImage = null;
		backgroundImageFile = null;
		backgroundColor = new Color(255,255,255);
		humanX = 0;
		humanY = 0;
		levelFinished = false;
		itemSpawnTime = 100;
		leftBound = 0;
		rightBound = 0;
		lowerBound = 0;
		upperBound = 0;
		deadLine = 0;
		human = new HumanEntity(0,0,50,50);
		platforms = new ArrayList<PlatformEntity>();
		animatedPlatforms = new ArrayList<AnimatedPlatformEntity>();
		walls = new ArrayList<WallEntity>();
		animatedWalls = new ArrayList<AnimatedWallEntity>();
		opponents = new ArrayList<AIEntity>();
		allies = new ArrayList<AIEntity>();
		bosses = new ArrayList<BossEntity>();
		checkpoints = new ArrayList<CheckPointEntity>();
		portals = new ArrayList<PortalEntity>();
		powerups = new ArrayList<PowerUpEntity>();
		weapons = new ArrayList<WeaponEntity>();
		foregroundDecorations = new ArrayList<DecorationEntity>();
		backgroundDecorations = new ArrayList<DecorationEntity>();
		cutscene = false;
	}
	
	//Accessor Methods
	public String staticOrDynamic()
	{
		return staticOrDynamic;
	}
	
	public double getGravity()
	{
		return gravity;
	}
	
	public int getBackgroundX()
	{
		return backgroundX;
	}
	
	public int getBackgroundY()
	{
		return backgroundY;
	}
	
	public int getBackgroundWidth()
	{
		return backgroundWidth;
	}
	
	public int getBackgroundHeight()
	{
		return backgroundHeight;
	}
	
	public Image getBackgroundImage()
	{
		return backgroundImage;
	}
	
	public File getBackgroundImageFile()
	{
		return backgroundImageFile;
	}
	
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}
	
	public int getHumanX()
	{
		return humanX;
	}
	
	public int getHumanY()
	{
		return humanY;
	}
	
	public boolean levelFinished()
	{
		return levelFinished;
	}
	
	public int getItemSpawnTime()
	{
		return itemSpawnTime;
	}
	
	public int getLeftBound()
	{
		return leftBound;
	}
	
	public int getRightBound()
	{
		return rightBound;
	}
	
	public int getLowerBound()
	{
		return lowerBound;
	}
	
	public int getUpperBound()
	{
		return upperBound;
	}
	
	public int getDeadLine()
	{
		return deadLine;
	}
	
	public boolean cutscene()
	{
		return cutscene;
	}
	
	public HumanEntity getHuman()
	{
		return human;
	}
	
	public ArrayList<PlatformEntity> getPlatforms()
	{
		return platforms;
	}
	
	public ArrayList<AnimatedPlatformEntity> getAnimatedPlatforms()
	{
		return animatedPlatforms;
	}
	
	public ArrayList<WallEntity> getWalls()
	{
		return walls;
	}
	
	public ArrayList<AnimatedWallEntity> getAnimatedWalls()
	{
		return animatedWalls;
	}
	
	public ArrayList<AIEntity> getOpponents()
	{
		return opponents;
	}
	
	public ArrayList<AIEntity> getAllies()
	{
		return allies;
	}
	
	public ArrayList<BossEntity> getBosses()
	{
		return bosses;
	}
	
	public ArrayList<CheckPointEntity> getCheckPoints()
	{
		return checkpoints;
	}
	
	public ArrayList<PortalEntity> getPortals()
	{
		return portals;
	}
	
	public ArrayList<PowerUpEntity> getPowerUps()
	{
		return powerups;
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}
	
	public ArrayList<DecorationEntity> getForegroundDecorations()
	{
		return foregroundDecorations;
	}
	
	public ArrayList<DecorationEntity> getBackgroundDecorations()
	{
		return backgroundDecorations;
	}
	
	//Mutator Methods
	public void setStaticOrDynamic(String staticOrDynamic)
	{
		this.staticOrDynamic = staticOrDynamic;
	}
	
	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}
	
	public void setBackgroundX(int backgroundX)
	{
		this.backgroundX = backgroundX;
	}
	
	public void setBackgroundY(int backgroundY)
	{
		this.backgroundY = backgroundY;
	}
	
	public void setBackgroundWidth(int backgroundWidth)
	{
		this.backgroundWidth = backgroundWidth;
	}
	
	public void setBackgroundHeight(int backgroundHeight)
	{
		this.backgroundHeight = backgroundHeight;
	}
	
	public void setBackgroundImage(Image backgroundImage)
	{
		this.backgroundImage = backgroundImage;
	}
	
	public void setBackgroundImageFile(File backgroundImageFile)
	{
		this.backgroundImageFile = backgroundImageFile;
	}
	
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
	
	public void setHumanX(int humanX)
	{
		this.humanX = humanX;
	}
	
	public void setHumanY(int humanY)
	{
		this.humanY = humanY;
	}
	
	public void setItemSpawnTime(int itemSpawnTime)
	{
		this.itemSpawnTime = itemSpawnTime;
	}
	
	public void setDeadLine(int deadLine)
	{
		this.deadLine = deadLine;
	}
	
	public void setCutscene(boolean cutscene)
	{
		this.cutscene = cutscene;
	}
	
	public void setWeapons(ArrayList<WeaponEntity> weapons)
	{
		this.weapons = weapons;
	}
	
	/**********Methods needed to run and maintain the level************/
	
	public void update()
	{
		leftBound = (int)human.getSprite().getXCoord()-1100;
		rightBound = (int)human.getSprite().getXCoord()+1100;
		lowerBound = (int)human.getSprite().getYCoord()+1000;
		upperBound = (int)human.getSprite().getYCoord()-1000;
		updateEntities();
		checkCollisions();
		checkOutOfBounds();
		if(staticOrDynamic().equals("Static"))	//if level type is static
		{
			updatePortals();
		}
		checkIfLevelFinished();
	}
	
	/**
	 * Update Entities
	 * Updates all the game entities by calling their respective update() methods.
	 */
	public void updateEntities()
	{
		//Update human-controlled player
		human.update(getGravity());
		//Update Opponents
		for(int i = 0; i<opponents.size();i++)
		{
			if(opponents.get(i).alive()==false)	//get a dead opponent's powerup (if he has one)
			{
				if(opponents.get(i).getPowerup()!=null)	//if opponent has a powerup to be spawned
				{
					PowerUpEntity newPowerup = opponents.get(i).getPowerup();
					//Go through list of powerups and find this player's powerup
					for(PowerUpEntity powerup: powerups)
					{
						if(powerup==newPowerup&&
							powerup.alreadyBeenSpawned()==false)	//if correct powerup was found and that powerup hasnt already been spawned
						{
							powerup.spawn((int)opponents.get(i).getSprite().getXCoord(), 
								(int)opponents.get(i).getSprite().getYCoord());	//spawn powerup
						}
					}
				}
			}
			if(opponents.get(i).dying())
			{
				//Add their score value to human's score
				human.setScore(human.getScore()+opponents.get(i).retrieveScore());
			}
			if(opponents.get(i).getSprite().getXCoord()>=leftBound&&
				opponents.get(i).getSprite().getXCoord()<=rightBound&&
				opponents.get(i).getSprite().getYCoord()>=upperBound&&
				opponents.get(i).getSprite().getYCoord()<=lowerBound)
			{
				opponents.get(i).update(getGravity());
			}
		}
		//Update allies
		for(int i = 0; i<allies.size(); i++)
		{
			allies.get(i).update(getGravity());
		}
		//Update bosses
		for(BossEntity boss: bosses)
		{
			boolean okToUpdate = false;
			for(BossComponent component: boss.getBossComponents()){
				if(component.getSprite().getXCoord()>=leftBound&&
					component.getSprite().getXCoord()<=rightBound&&
					component.getSprite().getYCoord()>=upperBound&&
					component.getSprite().getYCoord()<=lowerBound)
				{
					okToUpdate = true;
				}
			}
			if(boss.alive()||boss.isDying())
			{
				if(okToUpdate)
					boss.update(getGravity());
			}
		}
		//Update all powerups
		for(ItemEntity powerup: powerups)
		{
			powerup.update(getGravity());
		}
		//Update Items
		for(int i = 0; i<weapons.size(); i++)
		{
			weapons.get(i).update(getGravity());
		}
		//Update platforms
		for(int i = 0; i<platforms.size();i++)
		{
			if(platforms.get(i).getSprite().getXCoord()>=leftBound&&
				platforms.get(i).getSprite().getXCoord()<=rightBound&&
				platforms.get(i).getSprite().getYCoord()>=upperBound&&
				platforms.get(i).getSprite().getYCoord()<=lowerBound)
			{
				platforms.get(i).update();
			}
		}
		for(int i = 0; i<animatedPlatforms.size(); i++)
		{
			animatedPlatforms.get(i).update();
		}
		for(int i = 0; i<walls.size(); i++)
		{
			walls.get(i).update();
		}
		for(int i = 0; i<animatedWalls.size(); i++)
		{
			animatedWalls.get(i).update();
		}
		for(int i = 0; i<checkpoints.size(); i++)
		{
			checkpoints.get(i).update();
		}
		for(int i = 0; i<portals.size(); i++)
		{
			portals.get(i).update();
		}
	}
	
	/**
	 * Update Portals
	 * Checks whether the portals need to be spawned. This method call is only
	 * called if a level is static. This is so that when the player kills all opponents in
	 * a static level, the portals are spawned. It also prevents the player from "escaping" from
	 * the level before he defeated all opponents.
	 */
	public void updatePortals()
	{
		boolean allDead = true;
		for(AIEntity opponent: opponents)
		{
			//Check if there are any alive opponents left in the level
			if(opponent.alive()==true)
			{
				allDead = false;
			}
		}
		for(BossEntity boss: bosses)
		{
			if(boss.alive())
			{
				allDead = false;
			}
		}
		if(allDead)	//if all opponents have been killed/defeated
		{
			//spawn all the portals
			for(PortalEntity portal: portals)
			{
				portal.setAlive(true);
			}
		}
	}
	
	/**
	 * Check Collisions
	 * Checks whether the game entities in the level are colliding.
	 */
	public void checkCollisions()
	{
		//Collisions involving platforms
		checkPlatformCollisions();
		checkAnimatedPlatformCollisions();
		checkWallCollisions();
		checkAnimatedWallCollisions();
		if(!cutscene())
		{
			checkHumanEnemyCollisions();
			checkAllyEnemyCollisions();
		}
		checkItemPlayerCollisions();
		
		//Check collisions between human and checkpoints
		for(CheckPointEntity checkpoint: checkpoints)
		{
			if(colliding(human.getSprite(),checkpoint.getSprite()))
			{
				checkpoint.collisionEffect(human);
			}
		}
		//Check collisions between human and portals
		for(PortalEntity portal: portals)
		{
			if(portal.alive())
			{
				if(colliding(human.getSprite(), portal.getSprite()))
				{
					portal.collisionEffect(human);
				}
			}
		}
	}
	
	/**Check Platform Collisions
	 *Checks collisions between platforms and remaining game entities
	 */
	public void checkPlatformCollisions()
	{
		//Collisions involving platforms
		for(int i = 0; i<platforms.size();i++)
		{
			//Collisions between human and platforms
			if(human.alive())
			{
				if(colliding(human.getSprite(), platforms.get(i).getSprite()))
				{
					human.collisionEffect(platforms.get(i));
					platforms.get(i).collisionEffect(human);
				}
				for(int j = 0; j<human.getProjectiles().size(); j++)
				{
					if(human.getProjectiles().get(j).alive())
					{
						if(colliding(human.getProjectiles().get(j).getSprite(), platforms.get(i).getSprite()))
						{
							human.getProjectiles().get(j).collisionEffect(platforms.get(i));
						}
					}
				}
				
				//Collisions between human's weapon projectiles and platforms
				ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
				for(int j = 0; j<humanWeapons.size(); j++)
				{
					ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
					for(ProjectileEntity projectile: weaponProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
							{
								projectile.collisionEffect(platforms.get(i));
							}
						}
					}
				}
				//Collision between human's special weapon projectiles and platforms
				ArrayList<WeaponEntity> humanSpecialWeapons = human.getSpecialWeapons();
				for(WeaponEntity weapon: humanSpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
							{
								projectile.collisionEffect(platforms.get(i));
							}
						}
					}
				}
			}
			
			//Collisions between opponents and platforms
			for(int j = 0; j<opponents.size();j++)
			{
				if(opponents.get(j).alive())
				{
					if(colliding(opponents.get(j).getSprite(),platforms.get(i).getSprite()))
					{
						opponents.get(j).collisionEffect(platforms.get(i));
						platforms.get(i).collisionEffect(opponents.get(j));
						opponents.get(j).setCurrentPlatform(platforms.get(i));	//tell AI which platform it's currently standing on
					}
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(j).getProjectiles();
					for(ProjectileEntity projectile: opponentProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
							{
								projectile.collisionEffect(platforms.get(i));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(j).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
								{
									projectile.collisionEffect(platforms.get(i));
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(j).getSpecialWeapons();
					for(WeaponEntity weapon: opponentSpecialWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
								{
									projectile.collisionEffect(platforms.get(i));
								}
							}
						}
					}
				}
			}
			//Collisions between powerups and platforms
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), platforms.get(i).getSprite()))
				{
					powerup.collisionEffect(platforms.get(i));
				}
			}
			
			for(int j = 0;j<bosses.size();j++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
				for(int k = 0; k< bossComponents.size(); k++)
				{
					ArrayList<WeaponEntity> bossWeapons = bossComponents.get(k).getWeapons();
					for(int l = 0; l<bossWeapons.size(); l++)
					{
						ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(l).getProjectiles();
						for(int m =0; m<bossProjectiles.size(); m++)
						{
							if(bossProjectiles.get(m).alive())
							{
								if(colliding(bossProjectiles.get(m).getSprite(),platforms.get(i).getSprite()))
								{
									bossProjectiles.get(m).collisionEffect(platforms.get(i));
								}
							}
						}
					}
				}
			}
			
			//Collisions betweena allies and platforms
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),platforms.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(platforms.get(i));
					platforms.get(i).collisionEffect(allies.get(j));
				}
				ArrayList<ProjectileEntity> allyProjectiles = allies.get(j).getProjectiles();
				for(ProjectileEntity projectile: allyProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
						{
							projectile.collisionEffect(platforms.get(i));
						}
					}
				}
				ArrayList<WeaponEntity> allyTempWeapons = allies.get(j).getTemporaryWeapons();
				for(WeaponEntity weapon: allyTempWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
							{
								projectile.collisionEffect(platforms.get(i));
							}
						}
					}
				}
				ArrayList<WeaponEntity> allySpecialWeapons = allies.get(j).getSpecialWeapons();
				for(WeaponEntity weapon: allySpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),platforms.get(i).getSprite()))
							{
								projectile.collisionEffect(platforms.get(i));
							}
						}
					}
				}
			}
			//Collisions between items and platforms
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),platforms.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(platforms.get(i));
				}
			}
		}
	}
	
	/*public void checkAnimatedPlatformCollisions()
	{
		//Collisions involving animatedPlatforms
		for(int i = 0; i<animatedPlatforms.size();i++)
		{
			//Collisions between human and animatedPlatforms
			if(colliding(human.getSprite(), animatedPlatforms.get(i).getSprite()))
			{
				human.collisionEffect(animatedPlatforms.get(i));
				animatedPlatforms.get(i).collisionEffect(human);
			}
			for(int j = 0; j<human.getProjectiles().size(); j++)
			{
				if(human.getProjectiles().get(j).alive())
				{
					if(colliding(human.getProjectiles().get(j).getSprite(), animatedPlatforms.get(i).getSprite()))
					{
						human.getProjectiles().get(j).collisionEffect(animatedPlatforms.get(i));
					}
				}
			}
			//Collisions between human's weapon projectiles and platforms
			ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
			for(int j = 0; j<humanWeapons.size(); j++)
			{
				ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
				for(ProjectileEntity projectile: weaponProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
						{
							projectile.collisionEffect(animatedPlatforms.get(i));
						}
					}
				}
			}
			
			//Collisions between opponents and animatedPlatforms
			for(int j = 0; j<opponents.size();j++)
			{
				if(colliding(opponents.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
				{
					opponents.get(j).collisionEffect(animatedPlatforms.get(i));
					animatedPlatforms.get(i).collisionEffect(opponents.get(j));
					opponents.get(j).setCurrentPlatform(platforms.get(i));
				}
			}
			//Collisions between powerups and animatedPlatforms
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), animatedPlatforms.get(i).getSprite()))
				{
					powerup.collisionEffect(animatedPlatforms.get(i));
				}
			}
			//Collisions betweena allies and animatedPlatforms
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(animatedPlatforms.get(i));
					animatedPlatforms.get(i).collisionEffect(allies.get(j));
				}
			}
			//Collisions between items and animatedPlatforms
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(animatedPlatforms.get(i));
				}
			}
		}
	}*/
	
	public void checkAnimatedPlatformCollisions()
	{
		//Collisions involving animatedPlatforms
		for(int i = 0; i<animatedPlatforms.size();i++)
		{
			//Collisions between human and animatedPlatforms
			if(human.alive())
			{
				if(colliding(human.getSprite(), animatedPlatforms.get(i).getSprite()))
				{
					human.collisionEffect(animatedPlatforms.get(i));
					animatedPlatforms.get(i).collisionEffect(human);
				}
				for(int j = 0; j<human.getProjectiles().size(); j++)
				{
					if(human.getProjectiles().get(j).alive())
					{
						if(colliding(human.getProjectiles().get(j).getSprite(), animatedPlatforms.get(i).getSprite()))
						{
							human.getProjectiles().get(j).collisionEffect(animatedPlatforms.get(i));
						}
					}
				}
				
				//Collisions between human's weapon projectiles and animatedPlatforms
				ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
				for(int j = 0; j<humanWeapons.size(); j++)
				{
					ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
					for(ProjectileEntity projectile: weaponProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedPlatforms.get(i));
							}
						}
					}
				}
				//Collision between human's special weapon projectiles and animatedPlatforms
				ArrayList<WeaponEntity> humanSpecialWeapons = human.getSpecialWeapons();
				for(WeaponEntity weapon: humanSpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedPlatforms.get(i));
							}
						}
					}
				}
			}
			
			//Collisions between opponents and animatedPlatforms
			for(int j = 0; j<opponents.size();j++)
			{
				if(opponents.get(j).alive())
				{
					if(colliding(opponents.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
					{
						opponents.get(j).collisionEffect(animatedPlatforms.get(i));
						animatedPlatforms.get(i).collisionEffect(opponents.get(j));
						opponents.get(j).setCurrentPlatform(animatedPlatforms.get(i));	//tell AI which platform it's currently standing on
					}
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(j).getProjectiles();
					for(ProjectileEntity projectile: opponentProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedPlatforms.get(i));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(j).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
								{
									projectile.collisionEffect(animatedPlatforms.get(i));
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(j).getSpecialWeapons();
					for(WeaponEntity weapon: opponentSpecialWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
								{
									projectile.collisionEffect(animatedPlatforms.get(i));
								}
							}
						}
					}
				}
			}
			//Collisions between powerups and animatedPlatforms
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), animatedPlatforms.get(i).getSprite()))
				{
					powerup.collisionEffect(animatedPlatforms.get(i));
				}
			}
			
			for(int j = 0;j<bosses.size();j++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
				for(int k = 0; k< bossComponents.size(); k++)
				{
					ArrayList<WeaponEntity> bossWeapons = bossComponents.get(k).getWeapons();
					for(int l = 0; l<bossWeapons.size(); l++)
					{
						ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(l).getProjectiles();
						for(int m =0; m<bossProjectiles.size(); m++)
						{
							if(bossProjectiles.get(m).alive())
							{
								if(colliding(bossProjectiles.get(m).getSprite(),animatedPlatforms.get(i).getSprite()))
								{
									bossProjectiles.get(m).collisionEffect(animatedPlatforms.get(i));
								}
							}
						}
					}
				}
			}
			
			//Collisions betweena allies and animatedPlatforms
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(animatedPlatforms.get(i));
					animatedPlatforms.get(i).collisionEffect(allies.get(j));
				}
				ArrayList<ProjectileEntity> allyProjectiles = allies.get(j).getProjectiles();
				for(ProjectileEntity projectile: allyProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
						{
							projectile.collisionEffect(animatedPlatforms.get(i));
						}
					}
				}
				ArrayList<WeaponEntity> allyTempWeapons = allies.get(j).getTemporaryWeapons();
				for(WeaponEntity weapon: allyTempWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedPlatforms.get(i));
							}
						}
					}
				}
				ArrayList<WeaponEntity> allySpecialWeapons = allies.get(j).getSpecialWeapons();
				for(WeaponEntity weapon: allySpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedPlatforms.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedPlatforms.get(i));
							}
						}
					}
				}
			}
			//Collisions between items and animatedPlatforms
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),animatedPlatforms.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(animatedPlatforms.get(i));
				}
			}
		}
	}
	
	/*public void checkWallCollisions()
	{
		//Collisions involving walls
		for(int i = 0; i<walls.size();i++)
		{
			if(human.alive())
			{
				//Collisions between human and walls
				if(colliding(human.getSprite(), walls.get(i).getSprite()))
				{
					human.collisionEffect(walls.get(i));
					walls.get(i).collisionEffect(human);
				}
				for(int j = 0; j<human.getProjectiles().size(); j++)
				{
					if(human.getProjectiles().get(j).alive())
					{
						if(colliding(human.getProjectiles().get(j).getSprite(), walls.get(i).getSprite()))
						{
							human.getProjectiles().get(j).collisionEffect(walls.get(i));
						}
					}
				}
				
				//Collisions between human's weapon projectiles and platforms
				ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
				for(int j = 0; j<humanWeapons.size(); j++)
				{
					ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
					for(ProjectileEntity projectile: weaponProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
				}
			}
			
			//Collisions between opponents and walls
			for(int j = 0; j<opponents.size();j++)
			{
				if(opponents.get(j).alive())
				{
					if(colliding(opponents.get(j).getSprite(),walls.get(i).getSprite()))
					{
						opponents.get(j).collisionEffect(walls.get(i));
						walls.get(i).collisionEffect(opponents.get(j));
					}
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(j).getProjectiles();
					for(ProjectileEntity projectile: opponentProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(j).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
								{
									projectile.collisionEffect(walls.get(i));
								}
							}
						}
					}
				}
			}
			//Collisions between powerups and walls
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), walls.get(i).getSprite()))
				{
					powerup.collisionEffect(walls.get(i));
				}
			}
			//Collisions betweena allies and walls
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),walls.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(walls.get(i));
					walls.get(i).collisionEffect(allies.get(j));
				}
			}
			//Collisions between items and walls
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),walls.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(walls.get(i));
				}
			}
		}
	}*/
	
	public void checkWallCollisions()
	{
		//Collisions involving walls
		for(int i = 0; i<walls.size();i++)
		{
			//Collisions between human and walls
			if(human.alive())
			{
				if(colliding(human.getSprite(), walls.get(i).getSprite()))
				{
					human.collisionEffect(walls.get(i));
					walls.get(i).collisionEffect(human);
				}
				for(int j = 0; j<human.getProjectiles().size(); j++)
				{
					if(human.getProjectiles().get(j).alive())
					{
						if(colliding(human.getProjectiles().get(j).getSprite(), walls.get(i).getSprite()))
						{
							human.getProjectiles().get(j).collisionEffect(walls.get(i));
						}
					}
				}
				
				//Collisions between human's weapon projectiles and walls
				ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
				for(int j = 0; j<humanWeapons.size(); j++)
				{
					ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
					for(ProjectileEntity projectile: weaponProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
				}
				//Collision between human's special weapon projectiles and walls
				ArrayList<WeaponEntity> humanSpecialWeapons = human.getSpecialWeapons();
				for(WeaponEntity weapon: humanSpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
				}
			}
			
			//Collisions between opponents and walls
			for(int j = 0; j<opponents.size();j++)
			{
				if(opponents.get(j).alive())
				{
					if(colliding(opponents.get(j).getSprite(),walls.get(i).getSprite()))
					{
						opponents.get(j).collisionEffect(walls.get(i));
						walls.get(i).collisionEffect(opponents.get(j));
						opponents.get(j).setCurrentPlatform(walls.get(i));	//tell AI which platform it's currently standing on
					}
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(j).getProjectiles();
					for(ProjectileEntity projectile: opponentProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(j).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
								{
									projectile.collisionEffect(walls.get(i));
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(j).getSpecialWeapons();
					for(WeaponEntity weapon: opponentSpecialWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
								{
									projectile.collisionEffect(walls.get(i));
								}
							}
						}
					}
				}
			}
			//Collisions between powerups and walls
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), walls.get(i).getSprite()))
				{
					powerup.collisionEffect(walls.get(i));
				}
			}
			
			for(int j = 0;j<bosses.size();j++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
				for(int k = 0; k< bossComponents.size(); k++)
				{
					ArrayList<WeaponEntity> bossWeapons = bossComponents.get(k).getWeapons();
					for(int l = 0; l<bossWeapons.size(); l++)
					{
						ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(l).getProjectiles();
						for(int m =0; m<bossProjectiles.size(); m++)
						{
							if(bossProjectiles.get(m).alive())
							{
								if(colliding(bossProjectiles.get(m).getSprite(),walls.get(i).getSprite()))
								{
									bossProjectiles.get(m).collisionEffect(walls.get(i));
								}
							}
						}
					}
				}
			}
			
			//Collisions betweena allies and walls
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),walls.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(walls.get(i));
					walls.get(i).collisionEffect(allies.get(j));
				}
				ArrayList<ProjectileEntity> allyProjectiles = allies.get(j).getProjectiles();
				for(ProjectileEntity projectile: allyProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
						{
							projectile.collisionEffect(walls.get(i));
						}
					}
				}
				ArrayList<WeaponEntity> allyTempWeapons = allies.get(j).getTemporaryWeapons();
				for(WeaponEntity weapon: allyTempWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
				}
				ArrayList<WeaponEntity> allySpecialWeapons = allies.get(j).getSpecialWeapons();
				for(WeaponEntity weapon: allySpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),walls.get(i).getSprite()))
							{
								projectile.collisionEffect(walls.get(i));
							}
						}
					}
				}
			}
			//Collisions between items and walls
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),walls.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(walls.get(i));
				}
			}
		}
	}
	
	/*public void checkAnimatedWallCollisions()
	{
		//Collisions involving animatedWalls
		for(int i = 0; i<animatedWalls.size();i++)
		{
			//Collisions between human and animatedWalls
			if(colliding(human.getSprite(), animatedWalls.get(i).getSprite()))
			{
				human.collisionEffect(animatedWalls.get(i));
				animatedWalls.get(i).collisionEffect(human);
			}
			for(int j = 0; j<human.getProjectiles().size(); j++)
			{
				if(human.getProjectiles().get(j).alive())
				{
					if(colliding(human.getProjectiles().get(j).getSprite(), animatedWalls.get(i).getSprite()))
					{
						human.getProjectiles().get(j).collisionEffect(animatedWalls.get(i));
					}
				}
			}
			
			//Collisions between human's weapon projectiles and platforms
			ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
			for(int j = 0; j<humanWeapons.size(); j++)
			{
				ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
				for(ProjectileEntity projectile: weaponProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
						{
							projectile.collisionEffect(animatedWalls.get(i));
						}
					}
				}
			}
			
			//Collisions between opponents and animatedWalls
			for(int j = 0; j<opponents.size();j++)
			{
				if(colliding(opponents.get(j).getSprite(),animatedWalls.get(i).getSprite()))
				{
					opponents.get(j).collisionEffect(animatedWalls.get(i));
					animatedWalls.get(i).collisionEffect(opponents.get(j));
				}
			}
			//Collisions between powerups and animatedWalls
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), animatedWalls.get(i).getSprite()))
				{
					powerup.collisionEffect(animatedWalls.get(i));
				}
			}
			//Collisions betweena allies and animatedWalls
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),animatedWalls.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(animatedWalls.get(i));
					animatedWalls.get(i).collisionEffect(allies.get(j));
				}
			}
			//Collisions between items and animatedWalls
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),animatedWalls.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(animatedWalls.get(i));
				}
			}
		}
	}*/
	
	public void checkAnimatedWallCollisions()
	{
		//Collisions involving animatedWalls
		for(int i = 0; i<animatedWalls.size();i++)
		{
			//Collisions between human and animatedWalls
			if(human.alive())
			{
				if(colliding(human.getSprite(), animatedWalls.get(i).getSprite()))
				{
					human.collisionEffect(animatedWalls.get(i));
					animatedWalls.get(i).collisionEffect(human);
				}
				for(int j = 0; j<human.getProjectiles().size(); j++)
				{
					if(human.getProjectiles().get(j).alive())
					{
						if(colliding(human.getProjectiles().get(j).getSprite(), animatedWalls.get(i).getSprite()))
						{
							human.getProjectiles().get(j).collisionEffect(animatedWalls.get(i));
						}
					}
				}
				
				//Collisions between human's weapon projectiles and animatedWalls
				ArrayList<WeaponEntity> humanWeapons = human.getTemporaryWeapons();
				for(int j = 0; j<humanWeapons.size(); j++)
				{
					ArrayList<ProjectileEntity> weaponProjectiles = humanWeapons.get(j).getProjectiles();
					for(ProjectileEntity projectile: weaponProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedWalls.get(i));
							}
						}
					}
				}
				//Collision between human's special weapon projectiles and animatedWalls
				ArrayList<WeaponEntity> humanSpecialWeapons = human.getSpecialWeapons();
				for(WeaponEntity weapon: humanSpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedWalls.get(i));
							}
						}
					}
				}
			}
			
			//Collisions between opponents and animatedWalls
			for(int j = 0; j<opponents.size();j++)
			{
				if(opponents.get(j).alive())
				{
					if(colliding(opponents.get(j).getSprite(),animatedWalls.get(i).getSprite()))
					{
						opponents.get(j).collisionEffect(animatedWalls.get(i));
						animatedWalls.get(i).collisionEffect(opponents.get(j));
						opponents.get(j).setCurrentPlatform(animatedWalls.get(i));	//tell AI which platform it's currently standing on
					}
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(j).getProjectiles();
					for(ProjectileEntity projectile: opponentProjectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedWalls.get(i));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(j).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
								{
									projectile.collisionEffect(animatedWalls.get(i));
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(j).getSpecialWeapons();
					for(WeaponEntity weapon: opponentSpecialWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
								{
									projectile.collisionEffect(animatedWalls.get(i));
								}
							}
						}
					}
				}
			}
			//Collisions between powerups and animatedWalls
			for(PowerUpEntity powerup: powerups)
			{
				if(colliding(powerup.getSprite(), animatedWalls.get(i).getSprite()))
				{
					powerup.collisionEffect(animatedWalls.get(i));
				}
			}
			
			for(int j = 0;j<bosses.size();j++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
				for(int k = 0; k< bossComponents.size(); k++)
				{
					ArrayList<WeaponEntity> bossWeapons = bossComponents.get(k).getWeapons();
					for(int l = 0; l<bossWeapons.size(); l++)
					{
						ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(l).getProjectiles();
						for(int m =0; m<bossProjectiles.size(); m++)
						{
							if(bossProjectiles.get(m).alive())
							{
								if(colliding(bossProjectiles.get(m).getSprite(),animatedWalls.get(i).getSprite()))
								{
									bossProjectiles.get(m).collisionEffect(animatedWalls.get(i));
								}
							}
						}
					}
				}
			}
			
			//Collisions betweena allies and animatedWalls
			for(int j = 0; j<allies.size();j++)
			{
				if(colliding(allies.get(j).getSprite(),animatedWalls.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(animatedWalls.get(i));
					animatedWalls.get(i).collisionEffect(allies.get(j));
				}
				ArrayList<ProjectileEntity> allyProjectiles = allies.get(j).getProjectiles();
				for(ProjectileEntity projectile: allyProjectiles)
				{
					if(projectile.alive())
					{
						if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
						{
							projectile.collisionEffect(animatedWalls.get(i));
						}
					}
				}
				ArrayList<WeaponEntity> allyTempWeapons = allies.get(j).getTemporaryWeapons();
				for(WeaponEntity weapon: allyTempWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedWalls.get(i));
							}
						}
					}
				}
				ArrayList<WeaponEntity> allySpecialWeapons = allies.get(j).getSpecialWeapons();
				for(WeaponEntity weapon: allySpecialWeapons)
				{
					ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
					for(ProjectileEntity projectile: projectiles)
					{
						if(projectile.alive())
						{
							if(colliding(projectile.getSprite(),animatedWalls.get(i).getSprite()))
							{
								projectile.collisionEffect(animatedWalls.get(i));
							}
						}
					}
				}
			}
			//Collisions between items and animatedWalls
			for(int j = 0; j<weapons.size(); j++)
			{
				if(colliding(weapons.get(j).getSprite(),animatedWalls.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(animatedWalls.get(i));
				}
			}
		}
	}
	
	/** Check Human Enemy Collisions
	 *Checks collisions between human's projectiles and human's enemies. The enemies of the human
	 *player are all the opponents and all the bosses.
	 */
	public void checkHumanEnemyCollisions()
	{
		if(human.alive())
		{
			//Check collisions involving human player's projectiles
			for(int i = 0; i<human.getProjectiles().size();i++)
			{
				if(human.getProjectiles().get(i).alive())
				{			
					//Collisions between human player's projectiles and opponents
					for(int j=0; j<opponents.size(); j++)
					{
						if(opponents.get(j).alive())	//only check collisions between projectiles and alive opponents
						{				
							if(colliding(human.getProjectiles().get(i).getSprite(), opponents.get(j).getSprite()))
							{
								human.getProjectiles().get(i).collisionEffect(opponents.get(j));
								opponents.get(j).collisionEffect(human.getProjectiles().get(i));
							}
						}
					}
					//Collision between human player's projectiles and bosses
					for(int j = 0; j<bosses.size(); j++)
					{
						if(bosses.get(j).alive())
						{
							ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
							for(int k = 0; k<bossComponents.size(); k++)
							{
								if(bossComponents.get(k).alive())
								{
									if(colliding(human.getProjectiles().get(i).getSprite(),
										bossComponents.get(k).getSprite()))
									{
										human.getProjectiles().get(i).collisionEffect(bossComponents.get(k));
										bossComponents.get(k).collisionEffect(human.getProjectiles().get(i));
									}
								}
							}
						}
					}
				}
			}
			//check collisions between human's temporary weapons (picked up items) and enemies
			ArrayList<WeaponEntity> humanTempWeapons = human.getTemporaryWeapons();
			for(int i=0; i<humanTempWeapons.size();i++)
			{
				ArrayList<ProjectileEntity> projectiles = humanTempWeapons.get(i).getProjectiles();
				for(int j=0; j<projectiles.size();j++)
				{
					if(projectiles.get(j).alive())
					{
						for(int k=0; k<opponents.size(); k++)
						{
							if(opponents.get(k).alive())	//only check collisions between projectiles and alive opponents
							{				
								if(colliding(projectiles.get(j).getSprite(), opponents.get(k).getSprite()))
								{
									projectiles.get(j).collisionEffect(opponents.get(k));
									opponents.get(k).collisionEffect(projectiles.get(j));
								}
							}
						}
						//Collision between human player's projectiles and bosses
						for(int k = 0; k<bosses.size(); k++)
						{
							if(bosses.get(k).alive())
							{
								ArrayList<BossComponent> bossComponents = bosses.get(k).getBossComponents();
								for(int l = 0; l<bossComponents.size(); l++)
								{
									if(bossComponents.get(l).alive())
									{
										if(colliding(projectiles.get(j).getSprite(),
											bossComponents.get(l).getSprite()))
										{
											projectiles.get(j).collisionEffect(bossComponents.get(l));
											bossComponents.get(l).collisionEffect(projectiles.get(j));
										}
									}
								}
							}
						}
					}
				}
			}
			//check collisions between human's special weapons and enemies
			ArrayList<WeaponEntity> humanSpecialWeapons = human.getSpecialWeapons();
			for(int i=0; i<humanSpecialWeapons.size();i++)
			{
				ArrayList<ProjectileEntity> projectiles = humanSpecialWeapons.get(i).getProjectiles();
				for(int j=0; j<projectiles.size();j++)
				{
					if(projectiles.get(j).alive())
					{
						for(int k=0; k<opponents.size(); k++)
						{
							if(opponents.get(k).alive())	//only check collisions between projectiles and alive opponents
							{				
								if(colliding(projectiles.get(j).getSprite(), opponents.get(k).getSprite()))
								{
									projectiles.get(j).collisionEffect(opponents.get(k));
									opponents.get(k).collisionEffect(projectiles.get(j));
								}
							}
						}
						//Collision between human player's projectiles and bosses
						for(int k = 0; k<bosses.size(); k++)
						{
							if(bosses.get(k).alive())
							{
								ArrayList<BossComponent> bossComponents = bosses.get(k).getBossComponents();
								for(int l = 0; l<bossComponents.size(); l++)
								{
									if(bossComponents.get(l).alive())
									{
										if(colliding(projectiles.get(j).getSprite(),
											bossComponents.get(l).getSprite()))
										{
											projectiles.get(j).collisionEffect(bossComponents.get(l));
											bossComponents.get(l).collisionEffect(projectiles.get(j));
										}
									}
								}
							}
						}
					}
				}
			}
			//Check whether human is getting hit by any of the enemies' projectiles
			for(int i = 0; i<opponents.size(); i++)
			{
				if(opponents.get(i).alive())
				{			
					ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(i).getProjectiles();
					for(int j = 0; j<opponentProjectiles.size(); j++)
					{
						if(opponentProjectiles.get(j).alive())
						{					
							if(colliding(opponentProjectiles.get(j).getSprite(),human.getSprite()))
							{
								opponentProjectiles.get(j).collisionEffect(human);
								human.collisionEffect(opponentProjectiles.get(j));
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(i).getTemporaryWeapons();
					for(WeaponEntity weapon: opponentTempWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),human.getSprite()))
								{
									projectile.collisionEffect(human);
									human.collisionEffect(projectile);
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(i).getSpecialWeapons();
					for(WeaponEntity weapon: opponentSpecialWeapons)
					{
						ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
						for(ProjectileEntity projectile: projectiles)
						{
							if(projectile.alive())
							{
								if(colliding(projectile.getSprite(),human.getSprite()))
								{
									projectile.collisionEffect(human);
									human.collisionEffect(projectile);
								}
							}
						}
					}
				}
			}
			//Check if human is getting hit by any of bosses projectiles
			for(int i = 0;i<bosses.size();i++)
			{
				if(bosses.get(i).alive()){
					ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
					for(int j = 0; j< bossComponents.size(); j++)
					{
						ArrayList<WeaponEntity> bossWeapons = bossComponents.get(j).getWeapons();
						for(int k = 0; k<bossWeapons.size(); k++)
						{
							ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(k).getProjectiles();
							for(int l =0; l<bossProjectiles.size(); l++)
							{
								if(bossProjectiles.get(l).alive())
								{
									if(colliding(bossProjectiles.get(l).getSprite(),human.getSprite()))
									{
										bossProjectiles.get(l).collisionEffect(human);
										human.collisionEffect(bossProjectiles.get(l));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	//Checks collisions between allies' projectiles and enemies and enemies' projectiles
	//and allies.
	public void checkAllyEnemyCollisions()
	{
		for(int h = 0; h< allies.size(); h++)
		{
			if(allies.get(h).alive())
			{
				//Check collisions involving allies.get(h) player's projectiles
				for(int i = 0; i<allies.get(h).getProjectiles().size();i++)
				{
					if(allies.get(h).getProjectiles().get(i).alive())
					{			
						//Collisions between allies.get(h) player's projectiles and opponents
						for(int j=0; j<opponents.size(); j++)
						{
							if(opponents.get(j).alive())	//only check collisions between projectiles and alive opponents
							{				
								if(colliding(allies.get(h).getProjectiles().get(i).getSprite(), opponents.get(j).getSprite()))
								{
									allies.get(h).getProjectiles().get(i).collisionEffect(opponents.get(j));
									opponents.get(j).collisionEffect(allies.get(h).getProjectiles().get(i));
								}
							}
						}
						//Collision between allies.get(h) player's projectiles and bosses
						for(int j = 0; j<bosses.size(); j++)
						{
							if(bosses.get(j).alive())
							{
								ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
								for(int k = 0; k<bossComponents.size(); k++)
								{
									if(bossComponents.get(k).alive())
									{
										if(colliding(allies.get(h).getProjectiles().get(i).getSprite(),
											bossComponents.get(k).getSprite()))
										{
											allies.get(h).getProjectiles().get(i).collisionEffect(bossComponents.get(k));
											bossComponents.get(k).collisionEffect(allies.get(h).getProjectiles().get(i));
										}
									}
								}
							}
						}
					}
				}
				//check collisions between allies.get(h)'s temporary weapons (picked up items) and enemies
				ArrayList<WeaponEntity> allyTempWeapons = allies.get(h).getTemporaryWeapons();
				for(int i=0; i<allyTempWeapons.size();i++)
				{
					ArrayList<ProjectileEntity> projectiles = allyTempWeapons.get(i).getProjectiles();
					for(int j=0; j<projectiles.size();j++)
					{
						if(projectiles.get(j).alive())
						{
							for(int k=0; k<opponents.size(); k++)
							{
								if(opponents.get(k).alive())	//only check collisions between projectiles and alive opponents
								{				
									if(colliding(projectiles.get(j).getSprite(), opponents.get(k).getSprite()))
									{
										projectiles.get(j).collisionEffect(opponents.get(k));
										opponents.get(k).collisionEffect(projectiles.get(j));
									}
								}
							}
							//Collision between allies.get(h) player's projectiles and bosses
							for(int k = 0; k<bosses.size(); k++)
							{
								if(bosses.get(k).alive())
								{
									ArrayList<BossComponent> bossComponents = bosses.get(k).getBossComponents();
									for(int l = 0; l<bossComponents.size(); l++)
									{
										if(bossComponents.get(l).alive())
										{
											if(colliding(projectiles.get(j).getSprite(),
												bossComponents.get(l).getSprite()))
											{
												projectiles.get(j).collisionEffect(bossComponents.get(l));
												bossComponents.get(l).collisionEffect(projectiles.get(j));
											}
										}
									}
								}
							}
						}
					}
				}
				//check collisions between allies.get(h)'s special weapons and enemies
				ArrayList<WeaponEntity> allySpecialWeapons = allies.get(h).getSpecialWeapons();
				for(int i=0; i<allySpecialWeapons.size();i++)
				{
					ArrayList<ProjectileEntity> projectiles = allySpecialWeapons.get(i).getProjectiles();
					for(int j=0; j<projectiles.size();j++)
					{
						if(projectiles.get(j).alive())
						{
							for(int k=0; k<opponents.size(); k++)
							{
								if(opponents.get(k).alive())	//only check collisions between projectiles and alive opponents
								{				
									if(colliding(projectiles.get(j).getSprite(), opponents.get(k).getSprite()))
									{
										projectiles.get(j).collisionEffect(opponents.get(k));
										opponents.get(k).collisionEffect(projectiles.get(j));
									}
								}
							}
							//Collision between allies.get(h) player's projectiles and bosses
							for(int k = 0; k<bosses.size(); k++)
							{
								if(bosses.get(k).alive())
								{
									ArrayList<BossComponent> bossComponents = bosses.get(k).getBossComponents();
									for(int l = 0; l<bossComponents.size(); l++)
									{
										if(bossComponents.get(l).alive())
										{
											if(colliding(projectiles.get(j).getSprite(),
												bossComponents.get(l).getSprite()))
											{
												projectiles.get(j).collisionEffect(bossComponents.get(l));
												bossComponents.get(l).collisionEffect(projectiles.get(j));
											}
										}
									}
								}
							}
						}
					}
				}
				//Check whether allies.get(h) is getting hit by any of the enemies' projectiles
				for(int i = 0; i<opponents.size(); i++)
				{
					if(opponents.get(i).alive())
					{			
						ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(i).getProjectiles();
						for(int j = 0; j<opponentProjectiles.size(); j++)
						{
							if(opponentProjectiles.get(j).alive())
							{					
								if(colliding(opponentProjectiles.get(j).getSprite(),allies.get(h).getSprite()))
								{
									opponentProjectiles.get(j).collisionEffect(allies.get(h));
									allies.get(h).collisionEffect(opponentProjectiles.get(j));
								}
							}
						}
						ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(i).getTemporaryWeapons();
						for(WeaponEntity weapon: opponentTempWeapons)
						{
							ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
							for(ProjectileEntity projectile: projectiles)
							{
								if(projectile.alive())
								{
									if(colliding(projectile.getSprite(),allies.get(h).getSprite()))
									{
										projectile.collisionEffect(allies.get(h));
										allies.get(h).collisionEffect(projectile);
									}
								}
							}
						}
						ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(i).getSpecialWeapons();
						for(WeaponEntity weapon: opponentSpecialWeapons)
						{
							ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
							for(ProjectileEntity projectile: projectiles)
							{
								if(projectile.alive())
								{
									if(colliding(projectile.getSprite(),allies.get(h).getSprite()))
									{
										projectile.collisionEffect(allies.get(h));
										allies.get(h).collisionEffect(projectile);
									}
								}
							}
						}
					}
				}
				//Check if allies.get(h) is getting hit by any of bosses projectiles
				for(int i = 0;i<bosses.size();i++)
				{
					if(bosses.get(i).alive()){
						ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
						for(int j = 0; j< bossComponents.size(); j++)
						{
							ArrayList<WeaponEntity> bossWeapons = bossComponents.get(j).getWeapons();
							for(int k = 0; k<bossWeapons.size(); k++)
							{
								ArrayList<ProjectileEntity> bossProjectiles = bossWeapons.get(k).getProjectiles();
								for(int l =0; l<bossProjectiles.size(); l++)
								{
									if(bossProjectiles.get(l).alive())
									{
										if(colliding(bossProjectiles.get(l).getSprite(),allies.get(h).getSprite()))
										{
											bossProjectiles.get(l).collisionEffect(allies.get(h));
											allies.get(h).collisionEffect(bossProjectiles.get(l));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**Check Item Player Collisions
	 *Checks collisions between players and the items (powerups and weapons)
	 */
	public void checkItemPlayerCollisions()
	{
		//Check collisions between powerups and players
		for(PowerUpEntity powerup: powerups)
		{
			if(powerup.available())
			{
				if(human.alive())
				{
					//check collisions between human and powerups
					if(colliding(human.getSprite(), powerup.getSprite()))
					{
						powerup.collisionEffect(human);
					}
				}
			}
		}
		
		//Check collisions involving the Items and players
		for(int i = 0; i<weapons.size(); i++)
		{
			if(weapons.get(i).available())
			{
				if(human.alive())
				{
					//Check collisions between human and items
					if(colliding(human.getSprite(), weapons.get(i).getSprite()))
					{
						human.collisionEffect(weapons.get(i));
						weapons.get(i).collisionEffect(human);
					}
				}
				for(AIEntity opponent: opponents)
				{
					if(opponent.alive())
					{
						if(colliding(opponent.getSprite(),weapons.get(i).getSprite()))
						{
							opponent.collisionEffect(weapons.get(i));
							weapons.get(i).collisionEffect(opponent);
						}
					}
				}
				for(AIEntity ally: allies)
				{
					if(ally.alive())
					{
						if(colliding(ally.getSprite(),weapons.get(i).getSprite()))
						{
							ally.collisionEffect(weapons.get(i));
							weapons.get(i).collisionEffect(ally);
						}
					}
				}
			}
		}
	}
	
	//Collision Test method
	public boolean colliding(Sprite sprite1, Sprite sprite2)
	{
		if(sprite1.getBounds().intersects(sprite2.getBounds()))
			return true;
		else
			return false;
	}
	
	/*
	 * Check Out Of Bounds
	 * Checks if any players have fallen out of bounds. A player is out of bounds
	 * if he/she has fallen below the deadline. If a player falls below the
	 * deadline, he/she is killed and respanwed if they have lives left.
	 */
	public void checkOutOfBounds()
	{
		//Check if human has fallen below deadline
		if(getHuman().getSprite().getYCoord()>=deadLine)
			getHuman().die();
		//Check if any opponents have fallen below deadline
		for(int i = 0; i< opponents.size(); i++)
		{
			if(opponents.get(i).getSprite().getYCoord()>=deadLine)
				opponents.get(i).die();
		}
		//Check if any allies have fallen below deadlines
		for(int i = 0; i<allies.size(); i++)
		{
			if(allies.get(i).getSprite().getYCoord()>=deadLine)
				allies.get(i).die();
		}
	}
	
	/**
	 * Check If Level Finished
	 * Checks if the level has been finished. The level is finished once the player runs through one
	 * of the portals.
	 */
	public void checkIfLevelFinished()
	{
		for(PortalEntity portal: portals)
		{
			if(portal.alive())
			{
				if(portal.levelCompleted())
				{
					levelFinished = true;
				}
			}
		}
	}
}
