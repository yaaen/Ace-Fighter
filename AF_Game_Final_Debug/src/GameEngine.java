/**
 * @(#)AceFighter2_0 -> Main Engine -> GameEngine class
 *
 * The Main Engine (GameEngine) is the heart of Ace Fighter. It initializes,
 * runs and terminates the game. In order to do its job, the main engine contains
 * instances of all other major engines. It then delegates tasks to these engines
 * in order to launch, run and terminate the game. The main engine is responsible
 * for displaying everything since it's the core applet class. Because it is
 * the core applet class, it is able to load images and display images.
 *
 * AceFighter3_2 Update -> Serializing
 * 
 * In Version AceFighter3_2 the first steps towards object serialization were taken. Object
 * serialization will be used to load the levels. The levels will no longer be saved in
 * level files which are saved through a fileoutput writer. They are from now on saved
 * using object serialization (the level editor generates the level files) and read using
 * object serialization. This should make the level files more flexible and should speed
 * up the loading of a level.
 * 
 * AceFighter7_1_10 Update -> Successful deployment as a web applet.
 * 
 * Version AceFighter7_1_10 is the first version where the applet was successfully deployed
 * on the web. The text files were successfully read, the images were successfully loaded and
 * displayed, and the objects were successfully serialized and loaded. Cutscenes were
 * not tested, and not all collisions, optimizations, images were programmed to load.
 * The images which were supposed to load, loaded perfectly fine, but the code itself
 * didn't load all the images. That means, images for animating walls and things like that
 * weren't loaded in this version yet (not because of an error, but because the code
 * itself simply didn't load the images yet). Oh and the applet was also successfully
 * jarred, and signed and such. No problems with permissions either :D.
 * 
 * The successor version of this is AF_Applet_1_0.
 * 
 * AF_Applet2_2 Note: This version is ready for the first demo. The successor of this version is 
 * AF_Applet_Demo.
 * 
 * AF_Applet_Demo_1_0 Note: This version is ready for the first demo. All the platform properties have been
 * created and tested. Tests on AnimatedPlatforms and AnimatedWalls have not been performed.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 26, 2008
 */
 
import java.awt.*;
import java.applet.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class GameEngine extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener
{
	//Private variables
	//Game Engines and other necessary variables obtained from engines

	private PlayerProfile playerProfile;	//the player profile used to track the information of the human player
	private boolean resetPlayer;	//True if the player's attributes should be reset (ie: Health reset to maximum health etc.). This should only
									//be true when an inventory is reached.
	private boolean shootingEnabled;	//true if shooting is enabled in this level, false otherwise
	private LevelEntity level;	//the level that's currently running
	private MenuHandler menuHandler;	//the menu handler
	private GameScriptReader scriptReader;	//the game script reader
	private CutsceneHandler cutsceneHandler;	//the cutscene handler
	private boolean cutscene;	//true if a cutscene is running
	private int relationalPointX;	//this point moves as the level scrolls. It starts out at the top left corner (0,0)
									//When the level scrolls to the right for instance, this point allows for drawing the
									//objects that are farther right. This point is subtracted from the objects to be drawn
									//in order to show objects that were initially off screen, as the level scrolls over
	private int relationalPointY;
	private Sprite crossHair;	//the crosshair of the user. Shows user where his mouse is
	
	private int currentTime;		//the current time in the game (used to spawn items)
	private int ITEM_SPAWN_TIME = 200;		//an item gets spawned every 1000 loop iterations
	private int renderFieldLeftBound = 0;
	private int renderFieldRightBound = 0;	//anything within the left and right bounds of the render field gets drawn. This is
										//used to optimize the side-scrolling levels so that only the things within the human player's
										//vicinity are displayed. There's no use in drawing things that aren't seen by the player (off-screen)
	private int renderFieldLowerBound = 0;
	private int renderFieldUpperBound = 0;
	//Variables related to the graphical user interface (display variables, graphics variables, etc...)
	private BufferedImage backbuffer;
	private Graphics2D g2d;
	private AffineTransform identity = new AffineTransform();
	private AffineTransform trans = new AffineTransform();
	
	//Other variables
	private Thread gameloop;
	private boolean gameOn;	//if this is true, the game is displayed. If it's false, the menu is displayed
	private boolean gameOver;	//true if the game is over (player is out of lives)
	
	//Test variables - ONLY USED FOR TESTING ENGINE!
	private Image menuBackground;
	
	//Initialization of the engines
	public void init() 
	{
		System.out.println("***********Initializing....************");
		System.out.println("Win Fix...?");
		//Initialize all private variables
	//	human = new HumanEntity(0,0,50,50);
		playerProfile = new PlayerProfile();
	//	playerProfile.loadCharacterFile("Load/Test/Characters/CharacterFile.txt");
	//	opponents = new ArrayList<AIEntity>();
	//	powerups = new ArrayList<PowerUpEntity>();
	//	allies = new ArrayList<AIEntity>();
	//	bosses = new ArrayList<BossEntity>();
	//	weapons = new ArrayList<WeaponEntity>();
	//	platforms = new ArrayList<PlatformEntity>();
	//	portals = new ArrayList<PortalEntity>();*/
		shootingEnabled = true;
		level = new LevelEntity();
//		collisionEntity = new CollisionEntity();
	//	backgroundImage = null;
	//	staticOrDynamic = "";
	//	gravity = 0;
		menuHandler = new MenuHandler(playerProfile);
		scriptReader = new GameScriptReader("Load/GameScripts/GameScript1.txt");
		//scriptReader = new GameScriptReader("GameScript1.txt");
		cutsceneHandler = new CutsceneHandler();
		cutscene = false;
		relationalPointX = 0;
		relationalPointY = 0;
		crossHair = new Sprite(0,0,50,50);
		currentTime = 0;
		gameOn = false;	//at first the menu is displayed
		gameOver = false;
		//menuBackground = loadSingleImage(new File("Images/Screens/DemoTitleScreen.png"));
		menuBackground = loadSingleImage(new File("Images/Screens/StartScreen.png"));
		setSize(1000,600);
		backbuffer = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		setVisible(true);
		repaint();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	
	//Loading images and graphics
	/*public Image loadImage(String filename)
	{
		return getImage(getCodeBase(), filename);
	}*/

	//Methods used for displaying the game and the menu
	public void paint(Graphics g) 
	{
		g.drawImage(backbuffer,0,0,this);
	}
	
	public void update(Graphics g)
	{
		if(gameOn)	//display game
		{
			displayGame();
		}
		if(!gameOn)	//display menu
		{
			displayMenu();
		}
		paint(g);
	}
	
	public void displayGame()
	{
		if(level.staticOrDynamic().equals("Dynamic"))
		{
			renderFieldLeftBound = (int)level.getHuman().getSprite().getXCoord()-1100;
			renderFieldRightBound = (int)level.getHuman().getSprite().getXCoord()+600;
			renderFieldLowerBound = (int)level.getHuman().getSprite().getYCoord()+1000;
			renderFieldUpperBound = (int)level.getHuman().getSprite().getYCoord()-1000;
		}
		else	//level is static
		{
			renderFieldLeftBound = -500;
			renderFieldRightBound = 1500;
			renderFieldLowerBound = 1500;
			renderFieldUpperBound = -500;
		}
		//g2d.drawImage(level.getBackgroundImage(),0,0,getSize().width,getSize().height,this);
		//g2d.setColor(new Color(3,34,97));
		g2d.setColor(level.getBackgroundColor());
		g2d.fillRect(0,0,this.getSize().width,this.getSize().height);
		//Display the level
		//Display background decorations
		ArrayList<DecorationEntity> backgroundDecorations = level.getBackgroundDecorations();
		for(int i = 0; i<backgroundDecorations.size(); i++)
		{
			if(backgroundDecorations.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					backgroundDecorations.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					backgroundDecorations.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					backgroundDecorations.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawStaticSprite(backgroundDecorations.get(i).getSprite());
			}
		}
		
		ArrayList<BossEntity> bosses = level.getBosses();
		//Display bosses
		for(BossEntity boss: bosses)
		{
			if(boss.alive()||boss.isDying())
			{
				int i = 0;
				//Display all components of boss
				for(BossComponent bossComponent: boss.getBossComponents())
				{
					i++;
					if(bossComponent.alive()&&!bossComponent.dying()&&
							!bossComponent.spawning())
					{
						if(bossComponent.getSprite().getXCoord()>=renderFieldLeftBound&&
								bossComponent.getSprite().getXCoord()<=renderFieldRightBound&&
								bossComponent.getSprite().getYCoord()>=renderFieldUpperBound&&
								bossComponent.getSprite().getYCoord()<=renderFieldLowerBound)
						{
							drawAnimatedSprite(bossComponent.getSprite());
							ArrayList<WeaponEntity> bossWeapons = bossComponent.getWeapons();
							for(WeaponEntity weapon: bossWeapons)
							{
								ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
								for(ProjectileEntity projectile: projectiles)
								{
									if(projectile.alive())
									{
										drawDynamicSprite(projectile.getSprite());
									}
								}
							}
							if(bossComponent.damageable()){
								int dir = 1;
								if(i%2==0){
									dir = -1;
								}
								drawHealthBar(bossComponent, Color.YELLOW,true,(int)(bossComponent.getSprite().getXCoord())-(120*dir)-relationalPointX,
										(int)bossComponent.getSprite().getYCoord()-bossComponent.getSprite().getHeight()/2+10-relationalPointY);
							}
						}
					}
					if(bossComponent.spawning())
					{
						if(bossComponent.getSpawningAnimation().alive())
						{
							drawAnimatedSprite(bossComponent.getSpawningAnimation().getSprite());
						}
					}
					if(bossComponent.dying())
					{
						if(bossComponent.getDyingAnimation().alive())
						{
							drawAnimatedSprite(bossComponent.getDyingAnimation().getSprite());
						}
					}
				}
			}
		}
		//Display Platforms
		ArrayList<PlatformEntity> platforms = level.getPlatforms();
		for(int i = 0; i<platforms.size(); i++)
		{
			if(platforms.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
				platforms.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
				platforms.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
				platforms.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawStaticSprite(platforms.get(i).getSprite());
			}
		}
		//Display Animated Platforms
		ArrayList<AnimatedPlatformEntity> animatedPlatforms = level.getAnimatedPlatforms();
		for(int i = 0; i<animatedPlatforms.size(); i++)
		{
			if(animatedPlatforms.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					animatedPlatforms.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					animatedPlatforms.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					animatedPlatforms.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawAnimatedSprite(animatedPlatforms.get(i).getSprite());
			}

		}
		//Display Walls
		ArrayList<WallEntity> walls = level.getWalls();
		for(int i = 0; i<walls.size(); i++)
		{
			if(walls.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					walls.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					walls.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					walls.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawStaticSprite(walls.get(i).getSprite());
			}
		}
		//Display Animated Walls
		ArrayList<AnimatedWallEntity> animatedWalls = level.getAnimatedWalls();
		for(int i = 0; i<animatedWalls.size(); i++)
		{
			if(animatedWalls.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					animatedWalls.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					animatedWalls.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					animatedWalls.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawAnimatedSprite(animatedWalls.get(i).getSprite());
			}
		}
		//Display Items
		ArrayList<WeaponEntity> weapons = level.getWeapons();
		for(int i =0; i<weapons.size(); i++)
		{
			if(weapons.get(i).available())
			{
				if(weapons.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
						weapons.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
						weapons.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
						weapons.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
				{
					drawAnimatedSprite(weapons.get(i).getSprite());
				}
			}
			if(weapons.get(i).justBeenPickedUp())
			{
				g2d.setColor(Color.YELLOW);
				g2d.drawString(weapons.get(i).toString(),
						(int)weapons.get(i).getSprite().getXCoord()-relationalPointX,
						(int)weapons.get(i).getSprite().getYCoord()-10-relationalPointY);	
			}
		}
		//Display checkpoints
		ArrayList<CheckPointEntity> checkpoints = level.getCheckPoints();
		for(CheckPointEntity checkpoint: checkpoints)
		{
			if(checkpoint.getSprite().getXCoord()>=renderFieldLeftBound&&
					checkpoint.getSprite().getXCoord()<=renderFieldRightBound&&
					checkpoint.getSprite().getYCoord()>=renderFieldUpperBound&&
					checkpoint.getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawAnimatedSprite(checkpoint.getSprite());
			}
		}
		ArrayList<PortalEntity> portals = level.getPortals();
		//Display Portals
		for(PortalEntity portal: portals)
		{
			if(portal.alive())
			{
				if(portal.getSprite().getXCoord()>=renderFieldLeftBound&&
						portal.getSprite().getXCoord()<=renderFieldRightBound&&
						portal.getSprite().getYCoord()>=renderFieldUpperBound&&
						portal.getSprite().getYCoord()<=renderFieldLowerBound)
				{
					drawAnimatedSprite(portal.getSprite());
				}
			}
		}
		//Display Opponents
		ArrayList<AIEntity> opponents = level.getOpponents();
		for(int i = 0; i<opponents.size();i++)
		{
			if(opponents.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					opponents.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					opponents.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					opponents.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				if(opponents.get(i).alive()&&!opponents.get(i).dying()&&
						!opponents.get(i).spawning())
				{
					drawAnimatedSprite(opponents.get(i).getSprite());
					//Display opponent projectiles
					ArrayList<ProjectileEntity> projectiles = opponents.get(i).getProjectiles();
					for(int j = 0; j<projectiles.size();j++)
					{
						if(projectiles.get(j).alive())
						{
							drawDynamicSprite(projectiles.get(j).getSprite());
						}
						if(projectiles.get(j).exploding())
						{
							if(projectiles.get(j).getExplosion().alive())
							{
								drawAnimatedSprite(projectiles.get(j).getExplosion().getSprite());
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = opponents.get(i).getTemporaryWeapons();
					for(int j = 0; j<opponentTempWeapons.size(); j++)
					{
						ArrayList<ProjectileEntity> tempProjectiles = opponentTempWeapons.get(j).getProjectiles();
						for(int k = 0; k<tempProjectiles.size(); k++)
						{
							ProjectileEntity projectile = tempProjectiles.get(k);
							if(projectile.alive())
							{
								drawDynamicSprite(projectile.getSprite());
							}
							if(projectile.exploding())
							{
								if(projectile.getExplosion().alive())
								{
									drawAnimatedSprite(projectile.getExplosion().getSprite());
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = opponents.get(i).getSpecialWeapons();
					for(int j = 0; j<opponentSpecialWeapons.size(); j++)
					{
						ArrayList<ProjectileEntity> specialProjectiles = opponentSpecialWeapons.get(j).getProjectiles();
						for(int k = 0; k<specialProjectiles.size(); k++)
						{
							ProjectileEntity projectile = specialProjectiles.get(k);
							if(projectile.alive())
							{
								drawDynamicSprite(projectile.getSprite());
							}
							if(projectile.exploding())
							{
								if(projectile.getExplosion().alive())
								{
									drawAnimatedSprite(projectile.getExplosion().getSprite());
								}
							}
						}
					}
					drawHealthBar(opponents.get(i),Color.YELLOW,false,0,0);
					if(opponents.get(i).getLives()>1)
						g2d.drawString("x"+opponents.get(i).getLives(), (int)opponents.get(i).getSprite().getXCoord()+opponents.get(i).getSprite().getWidth()/2, (int)opponents.get(i).getSprite().getYCoord()-50);
				}
				if(opponents.get(i).spawning())
				{
					if(opponents.get(i).getSpawningAnimation().alive())
					{
						drawAnimatedSprite(opponents.get(i).getSpawningAnimation().getSprite());
					}
				}
				if(opponents.get(i).dying())
				{
					if(opponents.get(i).getDyingAnimation().alive())
					{
						drawAnimatedSprite(opponents.get(i).getDyingAnimation().getSprite());
						g2d.setColor(Color.YELLOW);
						g2d.drawString(""+opponents.get(i).getOriginalScoreValue(),
								(int)opponents.get(i).getSprite().getXCoord()-relationalPointX,
								(int)opponents.get(i).getSprite().getYCoord()-10-relationalPointY);
					}
				}
			}
		}
		//Display powerups spawned by dead opponents
		ArrayList<PowerUpEntity> powerups = level.getPowerUps();
		for(ItemEntity powerup: powerups)
		{
			if(powerup.getSprite().getXCoord()>=renderFieldLeftBound&&
					powerup.getSprite().getXCoord()<=renderFieldRightBound&&
					powerup.getSprite().getYCoord()>=renderFieldUpperBound&&
					powerup.getSprite().getYCoord()<=renderFieldLowerBound)
			{
				if(powerup.available())
				{
					drawAnimatedSprite(powerup.getSprite());
				}
				if(powerup.justBeenPickedUp())
				{
					g2d.setColor(Color.YELLOW);
					g2d.drawString(powerup.toString(),
							(int)powerup.getSprite().getXCoord()-relationalPointX,
							(int)powerup.getSprite().getYCoord()-10-relationalPointY);	
				}
			}
		}
		ArrayList<AIEntity> allies = level.getAllies();
		for(int i = 0; i<allies.size();i++)
		{
			if(allies.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					allies.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					allies.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					allies.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				if(allies.get(i).alive()&&!allies.get(i).dying()&&
						!allies.get(i).spawning())
				{
					drawAnimatedSprite(allies.get(i).getSprite());
					//Display opponent projectiles
					ArrayList<ProjectileEntity> projectiles = allies.get(i).getProjectiles();
					for(int j = 0; j<projectiles.size();j++)
					{
						if(projectiles.get(j).alive())
						{
							drawDynamicSprite(projectiles.get(j).getSprite());
						}
						if(projectiles.get(j).exploding())
						{
							if(projectiles.get(j).getExplosion().alive())
							{
								drawAnimatedSprite(projectiles.get(j).getExplosion().getSprite());
							}
						}
					}
					ArrayList<WeaponEntity> opponentTempWeapons = allies.get(i).getTemporaryWeapons();
					for(int j = 0; j<opponentTempWeapons.size(); j++)
					{
						ArrayList<ProjectileEntity> tempProjectiles = opponentTempWeapons.get(j).getProjectiles();
						for(int k = 0; k<tempProjectiles.size(); k++)
						{
							ProjectileEntity projectile = tempProjectiles.get(k);
							if(projectile.alive())
							{
								drawDynamicSprite(projectile.getSprite());
							}
							if(projectile.exploding())
							{
								if(projectile.getExplosion().alive())
								{
									drawAnimatedSprite(projectile.getExplosion().getSprite());
								}
							}
						}
					}
					ArrayList<WeaponEntity> opponentSpecialWeapons = allies.get(i).getSpecialWeapons();
					for(int j = 0; j<opponentSpecialWeapons.size(); j++)
					{
						ArrayList<ProjectileEntity> specialProjectiles = opponentSpecialWeapons.get(j).getProjectiles();
						for(int k = 0; k<specialProjectiles.size(); k++)
						{
							ProjectileEntity projectile = specialProjectiles.get(k);
							if(projectile.alive())
							{
								drawDynamicSprite(projectile.getSprite());
							}
							if(projectile.exploding())
							{
								if(projectile.getExplosion().alive())
								{
									drawAnimatedSprite(projectile.getExplosion().getSprite());
								}
							}
						}
					}
					drawHealthBar(allies.get(i),Color.GREEN,false,0,0);
					if(allies.get(i).getLives()>1)
						g2d.drawString("x"+allies.get(i).getLives(), (int)allies.get(i).getSprite().getXCoord()+allies.get(i).getSprite().getWidth()/2, (int)allies.get(i).getSprite().getYCoord()-50);
				
				}
				if(allies.get(i).spawning())
				{
					if(allies.get(i).getSpawningAnimation().alive())
					{
						drawAnimatedSprite(allies.get(i).getSpawningAnimation().getSprite());
					}
				}
				if(allies.get(i).dying())
				{
					if(allies.get(i).getDyingAnimation().alive())
					{
						drawAnimatedSprite(allies.get(i).getDyingAnimation().getSprite());
						/*g2d.drawString(""+allies.get(i).getOriginalScoreValue(),
								(int)allies.get(i).getSprite().getXCoord(),
								(int)allies.get(i).getSprite().getYCoord()-10);*/
					}
				}
			}
		}
		//Display Human player
		if(level.getHuman().alive()&&!level.getHuman().dying()&&
				!level.getHuman().spawning())
		{
			drawAnimatedSprite(level.getHuman().getSprite());
		}
		//Display human player's projectiles
		ArrayList<ProjectileEntity> humanProjectiles = level.getHuman().getProjectiles();
		for(int i = 0; i<humanProjectiles.size(); i++)
		{
			if(humanProjectiles.get(i).alive())	//draw any projectiles that have been fired
			{
				drawDynamicSprite(humanProjectiles.get(i).getSprite());
			}
		}
		//Display human's temporary weapon's projectiles
		ArrayList<WeaponEntity> humanTempWeapons = level.getHuman().getTemporaryWeapons();
		for(int i = 0; i<humanTempWeapons.size(); i++)
		{
			for(ProjectileEntity projectile:humanTempWeapons.get(i).getProjectiles())
			{
				//System.out.println("PAINT: projectile "+projectile + " alive? "+projectile.alive());
				if(projectile.alive())
				{
					drawDynamicSprite(projectile.getSprite());
				}
				if(projectile.exploding())
				{
					if(projectile.getExplosion().alive())
					{
						drawAnimatedSprite(projectile.getExplosion().getSprite());
					}
				}
			}
		}
		//Display human's special weapons
		ArrayList<WeaponEntity> humanSpecialWeapons = level.getHuman().getSpecialWeapons();
		for(int i = 0; i<humanSpecialWeapons.size(); i++)
		{
			for(ProjectileEntity projectile: humanSpecialWeapons.get(i).getProjectiles())
			{
				if(projectile.alive())
				{
					drawDynamicSprite(projectile.getSprite());
				}
				if(projectile.exploding())
				{
					if(projectile.getExplosion().alive())
					{
						drawAnimatedSprite(projectile.getExplosion().getSprite());
					}
				}
			}
		}
		//Draw human's spawning/dying animation
		if(level.getHuman().spawning())
		{
			if(level.getHuman().getSpawningAnimation().alive())
			{
				drawAnimatedSprite(level.getHuman().getSpawningAnimation().getSprite());
			}
		}
		if(level.getHuman().dying())
		{
			if(level.getHuman().getDyingAnimation().alive())
			{
				drawAnimatedSprite(level.getHuman().getDyingAnimation().getSprite());
			}
		}
		//Display foreground decorations
		ArrayList<DecorationEntity> foregroundDecorations = level.getForegroundDecorations();
		for(int i = 0; i<foregroundDecorations.size(); i++)
		{
			if(foregroundDecorations.get(i).getSprite().getXCoord()>=renderFieldLeftBound&&
					foregroundDecorations.get(i).getSprite().getXCoord()<=renderFieldRightBound&&
					foregroundDecorations.get(i).getSprite().getYCoord()>=renderFieldUpperBound&&
					foregroundDecorations.get(i).getSprite().getYCoord()<=renderFieldLowerBound)
			{
				drawStaticSprite(foregroundDecorations.get(i).getSprite());
			}
		}
		
		if(shootingEnabled)	//if the player can shoot in this level
		{
			//draw the crosshairs of the user
			drawCrossHair();
		}
		
		//Display cutscene dialog last
		if(cutscene && cutsceneHandler.executeCutsceneDialog())
		{
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(0,500,getSize().width,100);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0,500,getSize().width,100);
			DialogEntity dialog = cutsceneHandler.getCurrentCutsceneDialog();
			setFontAndColor(new Font("Arial",Font.BOLD,25),Color.BLACK);
			g2d.drawString(dialog.getSpeaker(),50,525);
			setFontAndColor(new Font("Arial",Font.PLAIN,20),Color.BLACK);
			for(int i = 0; i<dialog.getDialogLines().size();i++)
			{
				g2d.drawString(dialog.getDialogLines().get(i), 50, (550+(i*20)));
			}
			//Draw a little exclamation point above player who's currently speaking
			for(PlayerEntity player: level.getAllies())
			{
				if(player.getName().equals(dialog.getSpeaker()))
				{
					setFontAndColor(new Font("Arial",Font.BOLD,20), Color.YELLOW);
					g2d.drawString("!",(int)(player.getSprite().getXCoord()+player.getSprite().getWidth()/2)-relationalPointX,
							(int)(player.getSprite().getYCoord()-30)-relationalPointY);
				}
			}
			for(PlayerEntity player: level.getOpponents())
			{
				if(player.getName().equals(dialog.getSpeaker()))
				{
					setFontAndColor(new Font("Arial",Font.BOLD,20), Color.YELLOW);
					g2d.drawString("!",(int)(player.getSprite().getXCoord()+player.getSprite().getWidth()/2)-relationalPointX,
							(int)(player.getSprite().getYCoord()-30)-relationalPointY);
				}
			}
		}
		
		//The HUD is displayed last so that it appears on top of everything else.
		drawHUD();
	}
	
	/*Draw HUD
	 * Draws the human player's HUD (Head Up Display).
	 */
	public void drawHUD()
	{
		//Display a black border and a yellow box. This is what makes up the background
		//of the HUD.
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0,0,(int)this.getSize().getWidth(),61);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(1,1,(int)this.getSize().getWidth()-2,60);
		setFontAndColor(new Font("Arial",Font.PLAIN,15),Color.BLACK);
		g2d.drawString(level.getHuman().getName(),5,15);
		g2d.drawString("Score: "+level.getHuman().getScore(), 5, 50);
		g2d.drawString("Health: ", 125, 15);
		//Draw Player's health bar.
		//Drawing the maximum possible heatlh (the outlining bar)
		g2d.setColor(Color.RED);
		g2d.drawRect(185,5,level.getHuman().getHealthBarLength()+1,11);
		//Drawing the actual health the player has (the filled out part of the bar)
		if(level.getHuman().getSprite().colliding())	//make health bar flash red when player gets hit
		{
			g2d.setColor(Color.RED);
		}
		else
			g2d.setColor(Color.BLACK);
		g2d.fillRect(186,6,level.getHuman().getCurrentHealthBarLength(), 10);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Special: ", 125,50);
		//Draw special bar
		g2d.setColor(Color.GREEN);
		g2d.drawRect(185,40,level.getHuman().getSpecialBarLength()+1,11);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(186,41,level.getHuman().getCurrentSpecialBarLength(),10);
		//Draw temp weapons currently in possession
		g2d.drawString("Temporary Weapons: ",375,15);
		ArrayList<WeaponEntity> tempWeapons = level.getHuman().getTemporaryWeapons();
		for(int i = 0; i<tempWeapons.size(); i++)
		{
			if(level.getHuman().specialEquipped()==false)
			{
				g2d.setColor(Color.BLACK);
				g2d.drawRect(520, 2, 350, 24);
				g2d.setColor(Color.RED);
				g2d.drawRect(521,3,349,22);
			}
			if(i<=5)	//only draw first 5 items
			{
				Sprite sprite = tempWeapons.get(i).getProjectiles().get(0).getSprite();
				if(sprite.getImage()==null)
				{			
					g2d.setColor(Color.ORANGE);
					g2d.fillRect(525+(i*50), 5,sprite.getWidth(), sprite.getHeight());
				}
				else
				{
					if(tempWeapons.get(i).getProjectiles().get(0) instanceof GravityBall)
						g2d.drawImage(sprite.getImage(),525+(i*50),5,sprite.getWidth()/2,sprite.getHeight()/2,this);
					else
						g2d.drawImage(sprite.getImage(),525+(i*50),5,sprite.getWidth(),sprite.getHeight(),this);
				}
			}
			else
			{
				setFontAndColor(new Font("Arial",Font.PLAIN,15),Color.BLACK);
				g2d.drawString("...",510+(i*50),15);
			}
		}
		g2d.setColor(Color.BLACK);
		g2d.drawString("Special Weapons: ",375,50);
		ArrayList<WeaponEntity> specialWeapons = level.getHuman().getSpecialWeapons();
		for(int i = 0; i<specialWeapons.size(); i++)
		{
			if(level.getHuman().specialEquipped())
			{
				g2d.setColor(Color.BLACK);
				g2d.drawRect(520, 33, 375, 24);
				g2d.setColor(Color.RED);
				g2d.drawRect(521,34,373,22);
			}
			g2d.setColor(Color.BLACK);
			if(i<=5)	//only draw first 5 items
			{
				Sprite sprite = specialWeapons.get(i).getProjectiles().get(0).getSprite();
				if(sprite.getImage()==null)
				{			
					g2d.setColor(Color.ORANGE);
					g2d.fillRect(520+(i*50), 36,sprite.getWidth(), sprite.getHeight());
				}
				else
				{
					if(specialWeapons.get(i).getProjectiles().get(0) instanceof GravityBall) //dont draw gravity ball at its full size since it's kinda big
						g2d.drawImage(sprite.getImage(),520+(i*50),34,sprite.getWidth()/2,sprite.getHeight()/2,this);
					else
						g2d.drawImage(sprite.getImage(),520+(i*50),34,sprite.getWidth(),sprite.getHeight(),this);
				}
			}
			else
			{
				setFontAndColor(new Font("Arial",Font.PLAIN,15),Color.BLACK);
				g2d.drawString("...",510+(i*50),15);
			}
		}
		setFontAndColor(new Font("Arial",Font.PLAIN,15),Color.BLACK);
		g2d.drawString("Lives: x"+level.getHuman().getLives(),900,15);
	}
	
	/*Draw Health Bar
	 * @param The Player whose health bar should be drawn.
	 * Draws the specified player's health bar. It centers the health bar above the
	 * player.
	 * If useSpecifiedPos is true, the healthbar will be displayed at the position specified by xPos and yPos.
	 */
	public void drawHealthBar(Actor actor, Color color, boolean useSpecifiedPos, int xPos, int yPos)
	{
		int shiftDistance = 0;	
		if(actor.getHealthBarLength()>actor.getSprite().getWidth())	//if health bar wider than player
		{
			shiftDistance = (actor.getHealthBarLength()/2)-(actor.getSprite().getWidth()/2);
			shiftDistance*=-1;
		}
		else
		{
			shiftDistance = (actor.getSprite().getWidth()/2)-(actor.getHealthBarLength()/2);
		}
		//Drawing the maximum possible heatlh (the outlining bar)
		int barX = (int)actor.getSprite().getXCoord()+shiftDistance-relationalPointX;
		int barY = (int)actor.getSprite().getYCoord()-20-relationalPointY;
		if(useSpecifiedPos){
			barX = xPos;
			barY = yPos;
		}
		g2d.setColor(Color.BLACK);
		g2d.drawRect(barX-1,barY-1,actor.getHealthBarLength()+1,11);
		//Drawing the actual health the player has (the filled out part of the bar)
		g2d.setColor(color);
		g2d.fillRect(barX,barY,actor.getCurrentHealthBarLength(), 10);
	}
	
	/**Draw Static Sprite
	 *Draws non-animated Sprite.
	 */
	public void drawCrossHair()
	{
		if(crossHair.getImage()==null)
		{			
			g2d.setColor(Color.ORANGE);
			g2d.fillRect((int)crossHair.getXCoord(), (int)crossHair.getYCoord(),
				crossHair.getWidth(), crossHair.getHeight());
		}
		else
		{
			g2d.drawImage(crossHair.getImage(),(int)crossHair.getXCoord(),
				(int)crossHair.getYCoord(),(int)crossHair.getImageWidth(),
				(int)crossHair.getImageHeight(),this);
		}
	}
	
	/**Draw Static Sprite
	 *Draws non-animated Sprite.
	 */
	public void drawStaticSprite(Sprite sprite)
	{
		if(sprite.getImage()==null)
		{			
			g2d.setColor(Color.ORANGE);
			g2d.fillRect((int)sprite.getXCoord()-relationalPointX, (int)sprite.getYCoord()-relationalPointY,
				sprite.getWidth(), sprite.getHeight());
		}
		else
		{
			g2d.drawImage(sprite.getImage(),(int)sprite.getXCoord()-relationalPointX,
				(int)sprite.getYCoord()-relationalPointY,(int)sprite.getImageWidth(),
				(int)sprite.getImageHeight(),this);
		}
	}
	
	/**Draw Dynamic Sprite
	 * Draws a sprite and includes such things as the sprite's orientation (angle) etc.
	 * It cannot draw anything that has a specified framewidth or frameheight. It can
	 * also not draw any animating sprites.
	 */
	public void drawDynamicSprite(Sprite sprite)
	{
		if(sprite.getImage()==null)
		{			
			g2d.setColor(Color.ORANGE);
			g2d.fillRect((int)sprite.getXCoord()-relationalPointX, (int)sprite.getYCoord()-relationalPointY,
				sprite.getWidth(), sprite.getHeight());
		}
		else
		{
			trans.setTransform(identity);
			trans.translate(sprite.getXCoord()-relationalPointX, 
					sprite.getYCoord()-relationalPointY);
			trans.rotate(Math.toRadians(sprite.getFaceAngle()));
			g2d.drawImage(sprite.getImage(), trans, this);
		}
	}
	
	/**Draw Animated Sprite
	 *Draws the animated sprite
	 */
	public void drawAnimatedSprite(AnimatedSprite sprite)
	{
		if(sprite.getImage()==null)
		{
			g2d.setColor(Color.ORANGE);
			g2d.fillRect((int)sprite.getXCoord()-relationalPointX,(int)sprite.getYCoord()-relationalPointY,
				sprite.getWidth(),sprite.getHeight());
		}
		else
		{
			drawFrame(sprite.getImage(),g2d,(int)sprite.getXCoord()-relationalPointX,
				(int)sprite.getYCoord()-relationalPointY,sprite.getNumCols(),
				sprite.getCurrentFrame(),sprite.getImageWidth(),
				sprite.getImageHeight());
		}
	}
	
	public void displayMenu()
	{
		if(menuHandler.getCurrentScreen().getName().equals("TitleScreen"))
		{
			g2d.drawImage(menuBackground,0,0,getSize().width, getSize().height,this);	
		}
		else
		{
			g2d.setColor(Color.BLUE);
			g2d.fillRect(0,0,this.getSize().width, this.getSize().height);
		}
		//Testing the displaying of menu entities and menu screens
		for(int i = 0; i<menuHandler.getCurrentScreen().getMenuEntities().size();i++)
		{
			MenuEntity menuEntity = menuHandler.getCurrentScreen().getMenuEntities().get(i);
			setFontAndColor(menuEntity.getFont(),menuEntity.getColor());
			
			if(menuHandler.getCurrentScreen().getMenuEntities().get(i).getSprite().getImage()!=null)
			{
				drawStaticSprite(menuHandler.getCurrentScreen().getMenuEntities().get(i).getSprite());
			}
			else
			{
				//Draw the entity as a String by calling its toString() method.
				//g2d.setColor(Color.BLACK);
				g2d.drawString(menuHandler.getCurrentScreen().getMenuEntities().get(i).toString(), 
					(int)menuHandler.getCurrentScreen().getMenuEntities().get(i).getSprite().getXCoord(),
					(int)menuHandler.getCurrentScreen().getMenuEntities().get(i).getSprite().getYCoord()+
					(int)menuHandler.getCurrentScreen().getMenuEntities().get(i).getSprite().getHeight()/2);
			}
		}
		//Display cutscene dialog last
		if(cutscene && cutsceneHandler.executeCutsceneDialog())
		{
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(0,500,getSize().width,100);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0,500,getSize().width,100);
			DialogEntity dialog = cutsceneHandler.getCurrentCutsceneDialog();
			setFontAndColor(new Font("Arial",Font.BOLD,25),Color.BLACK);
			g2d.drawString(dialog.getSpeaker(),50,525);
			setFontAndColor(new Font("Arial",Font.PLAIN,20),Color.BLACK);
			for(int i = 0; i<dialog.getDialogLines().size();i++)
			{
				g2d.drawString(dialog.getDialogLines().get(i), 50, (550+(i*20)));
			}
		}
	}
	
	/*Set Font And Color
	 * Sets the font and color to the values passed in the parameters.
	 */
	public void setFontAndColor(Font font, Color color)
	{
		g2d.setFont(font);
		g2d.setColor(color);
	}
	
	/*drawFrame() method.
	 *Special thanks to Jonathan S. Harbour from the University of Advancing
 	 *Technology for the algorithm in this method.
 	 */
	public void drawFrame(Image source, Graphics2D dest, int destX, int destY,
						int numCols, int currentFrame, int width, int height)
	{
		int fx = (currentFrame%numCols)*width;
		int fy = (currentFrame/numCols)*height;
		dest.drawImage(source, destX, destY, destX+width, destY+height,		//this method allows you to pick a certain part of the image to draw
					fx, fy, fx+width, fy+height, this);
	}
	
	//Game Initialization
	/**
	 * Load Next Game Segment
	 * Calls GameScriptReader's readSegmentHeader() method and based on whether the upcoming segment
	 * is a battle or an inventory, the engine either loads a battle or an inventory.
	 */
	public void loadNextGameSegment()
	{
		String segment = scriptReader.readSegmentHeader();
		if(segment.equals("Battle"))
		{
			loadBattle();
		}
		if(segment.equals("Inventory"))
		{
			resetPlayer = true;
			loadInventory();
		}
		if(segment.equals("Finish"))	//The game has been completed!
		{
			//load end-of-game screen!
			turnOffGame();
			playerProfile.loadHumanContents(level.getHuman());	//load human contents back into player profile
			menuHandler.loadEndOfGameScreen();
		}
	}
	
	public void loadLevel(String levelFileName)
	{
		try
		{
			/*File file = new File(levelFileName);
			FileInputStream input = new FileInputStream(file);
			//FileInputStream input = new FileInputStream(levelFileName);*/
			InputStream input = this.getClass().getResourceAsStream(levelFileName);
			//InputStream input = new FileInputStream(new File(levelFileName));
			ObjectInputStream objectIn = new ObjectInputStream(input);
			level = (LevelEntity)objectIn.readObject();
			input.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Load Images
	 *Loads the Images of all the level components (if the level components have images to load). Images cannot be serialized but files
	 *can. Therefore, the Image File (not the image itself) of each level component (platform, opponent etc...) is serialized as well.
	 *When an object is regenerated from the file (read from the file), the object's image can be reloaded using the File of the image.
	 *That's what this method does. It loads the image based on the files of the components.
	 */
	public void loadImages()
	{
		//Load background image
		File backgroundImageFile = level.getBackgroundImageFile();
		loadBackgroundImage(backgroundImageFile);
		//loadImage(new File("Load/Other/Crosshair.png"),crossHair);
		loadImage(new File("Images/Misc/Crosshair.png"),crossHair);
		//Load all the level component images
		//Load human player image
		level.getHuman().getSprite().setImageWidth(60);
		level.getHuman().getSprite().setImageHeight(60);
		File imageFile = playerProfile.getImageFile();
		loadImage(imageFile,level.getHuman().getSprite());
		File humanSpawnDieFile;
		humanSpawnDieFile = level.getHuman().getSpawningAnimation().getSprite().getImageFile();
		loadImage(humanSpawnDieFile,level.getHuman().getSpawningAnimation().getSprite());
		humanSpawnDieFile = level.getHuman().getDyingAnimation().getSprite().getImageFile();
		loadImage(humanSpawnDieFile,level.getHuman().getDyingAnimation().getSprite());
		//Load human's projectiles' images
		ArrayList<ProjectileEntity> humanProjectiles = level.getHuman().getProjectiles();
		for(int i = 0; i<humanProjectiles.size(); i++)
		{
			File file = humanProjectiles.get(i).getSprite().getImageFile();
			loadImage(file,humanProjectiles.get(i).getSprite());
		}
		ArrayList<WeaponEntity> humanSpecialWeapons = level.getHuman().getSpecialWeapons();
		for(int i = 0; i<humanSpecialWeapons.size();i++)
		{
			ArrayList<ProjectileEntity> projectiles = humanSpecialWeapons.get(i).getProjectiles();
			for(ProjectileEntity projectile: projectiles)
			{
				File file = projectile.getSprite().getImageFile();
				loadImage(file,projectile.getSprite());
				if(projectile.getExplosion()!=null){
					file = projectile.getExplosion().getSprite().getImageFile();
					loadImage(file,projectile.getExplosion().getSprite());
				}
			}
		}
		ArrayList<PlatformEntity> platforms = level.getPlatforms();
		for(int i = 0; i<platforms.size(); i++)
		{
			File file = platforms.get(i).getSprite().getImageFile();
			loadImage(file,platforms.get(i).getSprite());
		}
		ArrayList<AnimatedPlatformEntity> animatedPlatforms = level.getAnimatedPlatforms();
		for(int i = 0; i<animatedPlatforms.size(); i++)
		{
			File file = animatedPlatforms.get(i).getSprite().getImageFile();
			loadImage(file,animatedPlatforms.get(i).getSprite());
		}
		ArrayList<WallEntity> walls = level.getWalls();
		for(int i = 0; i<walls.size(); i++)
		{
			File file = walls.get(i).getSprite().getImageFile();
			loadImage(file,walls.get(i).getSprite());
		}
		ArrayList<AnimatedWallEntity> animatedWalls = level.getAnimatedWalls();
		for(int i = 0; i<animatedWalls.size(); i++)
		{
			File file = animatedWalls.get(i).getSprite().getImageFile();
			loadImage(file,animatedWalls.get(i).getSprite());
		}
		ArrayList<AIEntity> opponents = level.getOpponents();
		for(int i = 0; i<opponents.size(); i++)
		{
			File file = opponents.get(i).getSprite().getImageFile();
			loadImage(file,opponents.get(i).getSprite());
			file = opponents.get(i).getSpawningAnimation().getSprite().getImageFile();
			loadImage(file,opponents.get(i).getSpawningAnimation().getSprite());
			file = opponents.get(i).getDyingAnimation().getSprite().getImageFile();
			loadImage(file,opponents.get(i).getDyingAnimation().getSprite());
			ArrayList<ProjectileEntity> opponentProjectiles = opponents.get(i).getProjectiles();
			for(ProjectileEntity projectile: opponentProjectiles)
			{
				file = projectile.getSprite().getImageFile();
				loadImage(file,projectile.getSprite());
			}
			for(WeaponEntity specialWeapon: opponents.get(i).getSpecialWeapons())
			{
				for(ProjectileEntity projectile: specialWeapon.getProjectiles())
				{
					file = projectile.getSprite().getImageFile();
					loadImage(file,projectile.getSprite());
					if(projectile.getExplosion()!=null){
						file = projectile.getExplosion().getSprite().getImageFile();
						loadImage(file,projectile.getExplosion().getSprite());
					}
				}
			}
			PowerUpEntity powerup = opponents.get(i).getPowerup();
			if(powerup!=null){
				file = powerup.getSprite().getImageFile();
				loadImage(file,powerup.getSprite());
			}
		}
		ArrayList<AIEntity> allies = level.getAllies();
		for(int i = 0; i<allies.size(); i++)
		{
			File file = allies.get(i).getSprite().getImageFile();
			loadImage(file,allies.get(i).getSprite());
			file = allies.get(i).getSpawningAnimation().getSprite().getImageFile();
			loadImage(file,allies.get(i).getSpawningAnimation().getSprite());
			file = allies.get(i).getDyingAnimation().getSprite().getImageFile();
			loadImage(file,allies.get(i).getDyingAnimation().getSprite());
			ArrayList<ProjectileEntity> allyProjectiles = allies.get(i).getProjectiles();
			for(ProjectileEntity projectile: allyProjectiles)
			{
				file = projectile.getSprite().getImageFile();
				loadImage(file,projectile.getSprite());
			}
			for(WeaponEntity specialWeapon: allies.get(i).getSpecialWeapons())
			{
				for(ProjectileEntity projectile: specialWeapon.getProjectiles())
				{
					file = projectile.getSprite().getImageFile();
					loadImage(file,projectile.getSprite());
					if(projectile.getExplosion()!=null){
						file = projectile.getExplosion().getSprite().getImageFile();
						loadImage(file,projectile.getExplosion().getSprite());
					}
				}
			}
		}
		ArrayList<BossEntity> bosses = level.getBosses();
		for(int i = 0; i<bosses.size(); i++)
		{
			ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
			for(int j = 0; j<bossComponents.size(); j++)
			{
				File file = bossComponents.get(j).getSprite().getImageFile();
				loadImage(file,bossComponents.get(j).getSprite());
				file = bossComponents.get(j).getSpawningAnimation().getSprite().getImageFile();
				loadImage(file,bossComponents.get(j).getSpawningAnimation().getSprite());
				file = bossComponents.get(j).getDyingAnimation().getSprite().getImageFile();
				loadImage(file,bossComponents.get(j).getDyingAnimation().getSprite());
				ArrayList<WeaponEntity> bossWeapons = bossComponents.get(j).getWeapons();
				for(WeaponEntity weapon: bossWeapons)
				{
					for(ProjectileEntity projectile: weapon.getProjectiles())
					{
						file = projectile.getSprite().getImageFile();
						loadImage(file,projectile.getSprite());
					}
				}
			}
		}
		ArrayList<WeaponEntity> weapons = level.getWeapons();
		for(int i = 0; i<weapons.size(); i++)
		{
			File file = weapons.get(i).getSprite().getImageFile();
			loadImage(file,weapons.get(i).getSprite());
			ArrayList<ProjectileEntity> weaponProjectiles = weapons.get(i).getProjectiles();
			for(ProjectileEntity projectile: weaponProjectiles)
			{
				file = projectile.getSprite().getImageFile();
				loadImage(file,projectile.getSprite());
				if(projectile.getExplosion()!=null){
					file = projectile.getExplosion().getSprite().getImageFile();
					loadImage(file,projectile.getExplosion().getSprite());
				}
			}
		}
		ArrayList<CheckPointEntity> checkpoints = level.getCheckPoints();
		for(int i = 0; i<checkpoints.size(); i++)
		{
			File file = checkpoints.get(i).getSprite().getImageFile();
			loadImage(file,checkpoints.get(i).getSprite());
		}
		ArrayList<PortalEntity> portals = level.getPortals();
		for(int i = 0; i<portals.size(); i++)
		{
			File file = portals.get(i).getSprite().getImageFile();
			loadImage(file,portals.get(i).getSprite());
		}
		ArrayList<DecorationEntity> foregroundDecorations = level.getForegroundDecorations();
		for(int i = 0; i<foregroundDecorations.size(); i++)
		{
			File file = foregroundDecorations.get(i).getSprite().getImageFile();
			loadImage(file,foregroundDecorations.get(i).getSprite());
		}
		ArrayList<DecorationEntity> backgroundDecorations = level.getBackgroundDecorations();
		for(int i = 0; i<backgroundDecorations.size(); i++)
		{
			File file = backgroundDecorations.get(i).getSprite().getImageFile();
			loadImage(file,backgroundDecorations.get(i).getSprite());
		}
	}
	
	/** Load Background Image
	 *@param The file of the background image.
	 *Loads the background image from the given image file.
	 */
	public void loadBackgroundImage(File file)
	{
		try
		{
			if(file!=null)
			{
				String imagePath = file.getPath();
				char oldChar = '\\';
				char newChar = '/';
				imagePath = imagePath.replace(oldChar, newChar);
				File imgFile = new File(imagePath);
				BufferedImage image = ImageIO.read(imgFile);
				if(image!=null)
					level.setBackgroundImage(image);
				else
					JOptionPane.showMessageDialog(null,"Background Image loading failed. The image is null.");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Background Image loading failed.");
		}
	}
	
	/** Load Image
	 *@param The Image File from which to load the Image
	 *@param The Sprite which will receive the loaded image.
	 *Loads the image from the image file into the sprite. Special thanks to the writers of "Objects First with Java" for
	 *the algorithm on how to load an image from a file.
	 */
	public void loadImage(File file, Sprite sprite)
	{
		//try
		//{
			if(file!=null)
			{	
				//Image image = getImage(getCodeBase(), "Images/"+file.getName());
				String imagePath = file.getPath();
				char oldChar = '\\';
				char newChar = '/';
				imagePath = imagePath.replace(oldChar, newChar);
				Image image = getImage(getCodeBase(), imagePath);
				//System.out.println("Image file name: " + imagePath);
				if(image!=null)
				{
					sprite.setImage(image);
				}
				else
				{
					System.out.println("Image loading failed for Sprite: " + sprite);
					//JOptionPane.showMessageDialog(null,"Image loading failed for Sprite: " + sprite + "\n" +
						//"The image is null.");
				}
			}
		//}
		/*catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Image loading failed for Sprite: " + sprite);
		}*/
	}
	
	/**Load Single Image
	 *@param The file of the image.
	 *@return The image that was loaded from the given file.
	 *Loads a single image given the file of the image.
	 */
	public Image loadSingleImage(File file)
	{
		//try
		//{
			if(file!=null)
			{
				//Image image = getImage(getCodeBase(), "Images/"+file.getName());
				String imagePath = file.getPath();
				char oldChar = '\\';
				char newChar = '/';
				imagePath = imagePath.replace(oldChar, newChar);
				Image image = getImage(getCodeBase(), imagePath);
				//Image image = getImage(getCodeBase(), file.getPath());
				//BufferedImage image = ImageIO.read(file);
				if(image!=null)
					return image;
				else
					JOptionPane.showMessageDialog(null,"Loading single image failed. The image is null.");
			}
	//	}
		/*catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Single image loading failed -> See loadSingleImage() in main engine.");
		}*/
		return null;
	}
	
	public void loadBattle()
	{
		BattleDataPacket packet = scriptReader.readBattleSegment();
		loadLevel(packet.getLevelFileName());
		level.getHuman().getSprite().setXCoord(level.getHumanX());
		level.getHuman().getSprite().setYCoord(level.getHumanY());
		if(resetPlayer){
			resetPlayer = false;
			level.getHuman().loadAndResetPlayerContents(playerProfile);
		}
		else{
			level.getHuman().loadPlayerContents(playerProfile);
		}
		loadImages();
		//Initialize AI players
		for(AIEntity ally: level.getAllies())	//add bad guys to allies' list of opponents
		{
			for(AIEntity opponent: level.getOpponents())
			{
				ally.getOpponents().add(opponent);
			}
			ally.setLevelWeapons(level.getWeapons());
			ally.finalizeSpecialWeapons();
		}
		for(AIEntity opponent: level.getOpponents())	//add allies and human player to bad guys' list of opponents
		{
			for(AIEntity ally: level.getAllies())
			{
				opponent.getOpponents().add(ally);
			}
			opponent.getOpponents().add(level.getHuman());
			level.getHuman().getOpponents().add(opponent);
			opponent.setLevelWeapons(level.getWeapons());
			opponent.finalizeSpecialWeapons();
		}
		level.getHuman().finalizeSpecialWeapons();
		for(BossEntity boss: level.getBosses())
		{
			for(BossComponent bossComponent: boss.getBossComponents())
			{
				for(AIEntity ally: level.getAllies())
				{
					bossComponent.getOpponents().add(ally);
				}
				bossComponent.getOpponents().add(level.getHuman());
				bossComponent.finalizeWeapons();
			}
		}
		//Initialize powerups from opponents
		for(AIEntity opponent: level.getOpponents())
		{
			if(opponent.getPowerup()!=null)	//if opponent has a powerup
			{
				level.getPowerUps().add(opponent.getPowerup());
			}
		}
		if(level.staticOrDynamic().equals("Dynamic"))	//if level is dynamic
		{
			ArrayList<PortalEntity> portals = level.getPortals();
			//In dynamic levels, portals are all alive from the start
			for(PortalEntity portal: portals)
			{
				portal.setAlive(true);
			}
		}
		if(packet.getCutscene().equals("Cutscene"))
		{
			ArrayList<AIEntity> opponents = level.getOpponents();
			ArrayList<AIEntity> allies = level.getAllies();
			ArrayList<AIEntity> cutsceneActors = new ArrayList<AIEntity>();
			for(int i = 0; i<opponents.size(); i++){
				cutsceneActors.add(opponents.get(i));
			}
			for(int i = 0; i<allies.size();i++){
				cutsceneActors.add(allies.get(i));
			}
			cutsceneHandler.resetCutsceneHandler();	//reset handler before loading new information into it
			cutsceneHandler.setPlayers(cutsceneActors);
			cutsceneHandler.initializeCutsceneHandler();
			cutscene = true;
		}
		if(packet.getCutscene().equals("NoCutscene"))
		{
			cutscene = false;
		}
		level.setCutscene(cutscene);
		if(packet.getShootingEnabled().equals("ShootingDisabled"))
			shootingEnabled = false;
		if(packet.getShootingEnabled().equals("ShootingEnabled"))
			shootingEnabled = true;
		turnOnGame();
	}
	
	public void loadInventory()
	{
		turnOffGame();
		InventoryDataPacket packet = scriptReader.readInventorySegment();
		if(packet.getCutscene().equals("Cutscene"))
		{
			cutsceneHandler.resetCutsceneHandler();	//reset handler before loading new information into it
			cutsceneHandler.initializeCutsceneHandler();		
			cutscene = true;
		}
		if(packet.getCutscene().equals("NoCutscene"))
		{
			cutscene = false;
		}
		playerProfile.loadHumanContents(level.getHuman());	//load human contents back into player profile
		//Add contents of data packet to the player profile
		playerProfile.addInventoryDataPacketContents(packet);
		//menuHandler.goToScreen("WeaponInventoryScreen");
		loadMenuImages();
		menuHandler.loadInventory();
	}
	
	//Loads all the projectile images which are used by the menu
	public void loadMenuImages()
	{
		ArrayList<WeaponEntity> menuWeapons = playerProfile.getWeapons();
		for(WeaponEntity weapon: menuWeapons)
		{
			for(ProjectileEntity projectile: weapon.getProjectiles())
			{
				File file = projectile.getSprite().getImageFile();
				loadImage(file,projectile.getSprite());
			}
		}
	}
	
	/**
	 * Turn On Game
	 * Turns the game on. It changes gameOn's value to true and starts the thread.
	 */
	public void turnOnGame()
	{
		gameOn = true;
		start();
	}
	
	/**
	 * Turn Off Game
	 * Turns the game off. It changes gameOn's value to false.
	 */
	public void turnOffGame()
	{
		relationalPointX = 0;
		relationalPointY = 0;
		gameOn = false;
		stop();
	}
	
	/**
	 * Check If Game Started Or Resumed
	 * Checks with Menu Handler if game was started or resumed.
	 */
	public void checkIfGameStartedOrResumed()
	{
		if(menuHandler.startGame())	//user clicked on a button that launches the game
		{
			//launch the game
			if(cutscene)	//if a cutscene is running
			{
				//turn it off
				cutscene = false;
				cutsceneHandler.terminateCutsceneHandler();
			}
			loadNextGameSegment();
		}
		if(menuHandler.resumeGame())	//user clicked on a button that simply resumes game (ie: player clicked on continue button in pause menu)
		{
			//resume the game
			turnOnGame();
		}
	}
	
	//Methods related to running/maintaining the game
	public void start()
	{
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	public void stop()
	{
		gameloop = null;
	}
	
	public void run()
	{
		Thread t = Thread.currentThread();
		while(t==gameloop)
		{
			try
			{
				Thread.sleep(20);
				if(gameOn)
				{
					gameUpdate();
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void gameUpdate()
	{
		//Update game engines
		if(cutscene)
		{
			cutsceneHandler.update();
			if(cutsceneHandler.cutsceneFinished()){
				cutscene = false;
				level.setCutscene(false);
			}
		}
		level.update();
		//updateEntities();
		//Check collisions
//		checkCollisions();
		//Update level movement if level is of type 'Dynamic'
		if(level.staticOrDynamic().equals("Dynamic"))	//if level is of type 'Dynamic', move everything if it needs to be moved ->side scrolling effect
		{
			updateEnvironment();
		}
		if(level.levelFinished())
		{
			endLevel();
		}
	/*	if(level.staticOrDynamic().equals("Static"))	//if level type is static
		{
			updatePortals();
		}*/
		//Respawn any items if it's time to respawn
		itemUpdate();
//		checkIfLevelFinished();	//check if the level has been completed
		currentTime++;
		if(level.getHuman().getLives()<=0)	//human player has lost all of his lives
		{
			//game over
			turnOffGame();
			gameOver = true;
			playerProfile.loadHumanContents(level.getHuman());	//load human contents back into player profile
			menuHandler.loadGameOverScreen();
		}
		repaint();
	}
	
	/**
	 * Update Entities
	 * Updates all the game entities by calling their respective update() methods.
	 */
	/*public void updateEntities()
	{
		//Update human-controlled player
		human.update(level.getGravity());
		//Update Opponents
		ArrayList<AIEntity> opponents = level.getOpponents();
		for(int i = 0; i<opponents.size();i++)
		{
			if(opponents.get(i).alive()==false)	//get a dead opponent's powerup (if he has one)
			{
				/*if(opponents.get(i).getPowerup()!=null)	//if opponent has a powerup to be spawned
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
				}*
			}
			opponents.get(i).update(level.getGravity());
		}
		//Update all powerups
		ArrayList<PowerUpEntity> powerups = level.getPowerUps();
		for(ItemEntity powerup: powerups)
		{
			if(powerup.available())	//update all available powerups
			{
				powerup.update(level.getGravity());
			}
		}
		//Update allies
		ArrayList<AIEntity> allies = level.getAllies();
		for(int i = 0; i<allies.size(); i++)
		{
			allies.get(i).update(level.getGravity());
		}
		
		//Update bosses
		ArrayList<BossEntity> bosses = level.getBosses();
		for(BossEntity boss: bosses)
		{
			if(boss.alive())
			{
				boss.update();
			}
		}
		//Update platforms
		ArrayList<PlatformEntity> platforms = level.getPlatforms();
		for(int i = 0; i<platforms.size();i++)
		{
			platforms.get(i).update();
		}
		//Update Items
		ArrayList<WeaponEntity> weapons = level.getWeapons();
		for(int i = 0; i<weapons.size(); i++)
		{
			weapons.get(i).update(level.getGravity());
		}
	}*/
	
	/**
	 * Check Collisions - HANDLEND IN THE LEVEL ENTITY NOW!
	 * Uses the CollisionEntity to see if game entities are colliding.
	 */
	/*public void checkCollisions()
	{
		//Collisions involving platforms
		ArrayList<PlatformEntity> platforms = level.getPlatforms();
		for(int i = 0; i<platforms.size();i++)
		{
			//Collisions between human and platforms
			if(collisionEntity.colliding(human.getSprite(), platforms.get(i).getSprite()))
			{
				human.collisionEffect(platforms.get(i));
				platforms.get(i).collisionEffect(human);
			}
			for(int j = 0; j<human.getProjectiles().size(); j++)
			{
				if(collisionEntity.colliding(human.getProjectiles().get(j).getSprite(), platforms.get(i).getSprite()))
				{
					human.getProjectiles().get(j).collisionEffect(platforms.get(i));
				}
			}
			
			//Collisions between opponents and platforms
			ArrayList<AIEntity> opponents = level.getOpponents();
			for(int j = 0; j<opponents.size();j++)
			{
				if(collisionEntity.colliding(opponents.get(j).getSprite(),platforms.get(i).getSprite()))
				{
					opponents.get(j).collisionEffect(platforms.get(i));
					platforms.get(i).collisionEffect(opponents.get(j));
				}
			}
			//Collisions between powerups and platforms
			ArrayList<PowerUpEntity> powerups = level.getPowerUps();
			for(PowerUpEntity powerup: powerups)
			{
				if(collisionEntity.colliding(powerup.getSprite(), platforms.get(i).getSprite()))
				{
					powerup.collisionEffect(platforms.get(i));
				}
			}
			//Collisions betweena allies and platforms
			ArrayList<AIEntity> allies = level.getAllies();
			for(int j = 0; j<allies.size();j++)
			{
				if(collisionEntity.colliding(allies.get(j).getSprite(),platforms.get(i).getSprite()))
				{
					allies.get(j).collisionEffect(platforms.get(i));
					platforms.get(i).collisionEffect(allies.get(j));
				}
			}
			//Collisions between items and platforms
			ArrayList<WeaponEntity> weapons = level.getWeapons();
			for(int j = 0; j<weapons.size(); j++)
			{
				if(collisionEntity.colliding(weapons.get(j).getSprite(),platforms.get(i).getSprite()))
				{
					weapons.get(j).collisionEffect(platforms.get(i));
				}
			}
		}
		//Check collisions involving projectiles
		//Check collisions involving human player's projectiles
		for(int i = 0; i<human.getProjectiles().size();i++)
		{
			if(human.getProjectiles().get(i).alive())
			{			
				//Collisions between human player's projectiles and opponents
				ArrayList<AIEntity> opponents = level.getOpponents();
				for(int j=0; j<opponents.size(); j++)
				{
					if(opponents.get(j).alive())	//only check collisions between projectiles and alive opponents
					{				
						if(collisionEntity.colliding(human.getProjectiles().get(i).getSprite(), opponents.get(j).getSprite()))
						{
							human.getProjectiles().get(i).collisionEffect(opponents.get(j));
							opponents.get(j).collisionEffect(human.getProjectiles().get(i));
						}
					}
				}
				//Collision between human player's projectiles and bosses
				ArrayList<BossEntity> bosses = level.getBosses();
				for(int j = 0; j<bosses.size(); j++)
				{
					if(bosses.get(j).alive())
					{
						ArrayList<BossComponent> bossComponents = bosses.get(j).getBossComponents();
						for(int k = 0; k<bossComponents.size(); k++)
						{
							if(bossComponents.get(k).alive())
							{
								if(collisionEntity.colliding(human.getProjectiles().get(i).getSprite(),
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
		
		//Check collisions between powerups and players
		for(PowerUpEntity powerup: powerups)
		{
			if(powerup.available())
			{
				//check collisions between human and powerups
				if(collisionEntity.colliding(human.getSprite(), powerup.getSprite()))
				{
					powerup.collisionEffect(human);
				}
			}
		}
		
		//Check collisions involving the Items and players
		for(int i = 0; i<weapons.size(); i++)
		{
			if(weapons.get(i).available())
			{
				//Check collisions between human and items
				if(collisionEntity.colliding(human.getSprite(), weapons.get(i).getSprite()))
				{
					human.collisionEffect(weapons.get(i));
					weapons.get(i).collisionEffect(human);
				}
			}
		}
		//Check collisions between human and portals
		ArrayList<PortalEntity> portals = level.getPortals();
		for(PortalEntity portal: portals)
		{
			if(portal.alive())
			{
				if(collisionEntity.colliding(human.getSprite(), portal.getSprite()))
				{
					portal.collisionEffect(human);
				}
			}
		}
	}*/
	
	/**
	 * Update Environment
	 * Moves relational points to create scrolling effect. The relational points
	 * are then used to draw the objects in the level. At first (at the start of the level) the relational points
	 * are 0,0. When the player moves, they increase/decrease accordingly to represent the relative
	 * location of all objects in the game. If a platform is at position 1500,0 at the start of the level (when relational
	 * points are 0,0), then the platform will not be displayed. As player moves to the right, the relational points increase
	 * accordingly. Once relational points are at 1000,0, the relational position of the platform relative to the relational
	 * points is 500,0. When the platform is displayed, it will be displayed at position 500,0.
	 */
	public void updateEnvironment()
	{
		if(level.getHuman().spawning())
		{
			//update position of relational points
			if(level.getHuman().getSprite().getXVeloc()>0 ||
					level.getHuman().getSprite().getXVeloc()<0)
			{
				relationalPointX += level.getHuman().getSprite().getXVeloc();
			}
			if(level.getHuman().getSprite().getYVeloc()>0||
					level.getHuman().getSprite().getYVeloc()<0)
			{
				relationalPointY += level.getHuman().getSprite().getYVeloc();
			}
			relationalPointX += level.getHuman().getSpawnShiftX();
			relationalPointY += level.getHuman().getSpawnShiftY();
			level.getHuman().getSprite().setXCoord(500+relationalPointX);	//ensures human stays centered
			level.getHuman().getSprite().setYCoord(300+relationalPointY);	//ensures human stays centered
		}
		else
		{
			//update position of relational points
			if(level.getHuman().getSprite().getXVeloc()>0 ||
					level.getHuman().getSprite().getXVeloc()<0)
				relationalPointX += level.getHuman().getSprite().getXVeloc();
			relationalPointX += level.getHuman().getSprite().getRelationalXDifference();
			relationalPointY += level.getHuman().getSprite().getYVeloc();
			level.getHuman().getSprite().setXCoord(500+relationalPointX);	//ensures human stays centered
			level.getHuman().getSprite().setYCoord(300+relationalPointY);	//ensures human stays centered
		}
	}
	
	/**
	 * Update Portals
	 * Checks whether the portals need to be spawned. This method call is only
	 * called if a level is static. This is so that when the player kills all opponents in
	 * a static level, the portals are spawned. It also prevents the player from "escaping" from
	 * the level before he defeated all opponents.
	 */
	/*public void updatePortals()
	{
		boolean allDead = true;
		ArrayList<AIEntity> opponents = level.getOpponents();
		for(AIEntity opponent: opponents)
		{
			//Check if there are any alive opponents left in the level
			if(opponent.alive()==true)
			{
				allDead = false;
			}
		}
		if(allDead)	//if all opponents have been killed/defeated
		{
			//spawn all the portals
			ArrayList<PortalEntity> portals = level.getPortals();
			for(PortalEntity portal: portals)
			{
				portal.setAlive(true);
			}
		}
	}*/
	
	/**
	 * Item Update
	 * Checks if new items need to be spawned and spawns any new items if it's time.
	 */
	public void itemUpdate()
	{
		if(level.getWeapons().size()>0)	//if there are any items to spawn
		{	
			Random random = new Random();
			int spawnX = 0;
			if(relationalPointX>0)
			{
			 	spawnX = random.nextInt(relationalPointX+this.getSize().width);
			}
			else
			{
				spawnX = random.nextInt(this.getSize().width);
			}
			int spawnY = 50;
			if(currentTime>=ITEM_SPAWN_TIME)
			{
				Random itemIndex = new Random();
				int index = itemIndex.nextInt(level.getWeapons().size());
				for(int i = 0; i<level.getWeapons().size(); i++)
				{
					if(i==index)
					{
						level.getWeapons().get(i).spawn(spawnX, spawnY);
					}
				}
				currentTime=0;	//reset current time counter
			}
		}
	}
	
	/**
	 * Check If Level Finished
	 * Checks if the level has been finished. The level is finished once the player runs through one
	 * of the portals.
	 */
	/*public void checkIfLevelFinished()
	{
		ArrayList<PortalEntity> portals = level.getPortals();
		for(PortalEntity portal: portals)
		{
			if(portal.alive())
			{
				if(portal.levelCompleted())
				{
					if(cutscene)	//if a cutscene is running
					{
						//turn it off
						cutscene = false;
						cutsceneHandler.terminateCutsceneHandler();
					}
					turnOffGame();	//stop the game
					menuHandler.resetMenu();	//resets menu
					loadNextGameSegment();
				}
			}
		}
	}*/
	
	/** End Level
	 *Terminates the level. When the level is completed, this method runs all the procedures necessary to
	 *cleanly terminate the level. This includes turning off any running cutscenes, switching back to
	 *the menu, and loading the next game segment.
	 */
	public void endLevel()
	{
		if(cutscene)	//if a cutscene is running
		{
			//turn it off
			cutscene = false;
			cutsceneHandler.terminateCutsceneHandler();
		}
		turnOffGame();	//stop the game
		menuHandler.resetMenu();	//resets menu
		playerProfile.loadHumanContents(level.getHuman());	//load human contents back into player profile
		loadNextGameSegment();
	}
	
	//Mouse Control-related Methods (From MouseListener and MouseMotionListener interface)
	public void mouseExited(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(!gameOver){
			if(gameOn)
			{
				if(shootingEnabled)
					level.getHuman().mouseReleased();
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(!gameOver){
			if(!gameOn)
			{
				String event = menuHandler.mousePressed(e.getX(), e.getY());
				if(cutscene)	//if a cutscene is running
				{
					//report the menu click to the cutscene handler
					cutsceneHandler.reportEvent(event);	
				}
				checkIfGameStartedOrResumed();
			}
			if(gameOn)
			{
				if(shootingEnabled)
					level.getHuman().mousePressed(e.getX()+relationalPointX, e.getY()+relationalPointY);
			}
			repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseMoved(MouseEvent e)
	{
		if(!gameOver){
			if(!gameOn)
			{
				menuHandler.mouseMoved(e.getX(), e.getY());
				if(cutscene)
				{
					cutsceneHandler.update();
				}
			}
			if(gameOn)
			{
				if(shootingEnabled)
				{
					crossHair.setXCoord(e.getX()-crossHair.getWidth()/4);
					crossHair.setYCoord(e.getY()-crossHair.getHeight()/4);
				}
			}
			repaint();
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(!gameOver){
			if(gameOn)
			{
				if(shootingEnabled)
				{
					level.getHuman().mousePressed(e.getX()+relationalPointX,e.getY()+relationalPointY);
					crossHair.setXCoord(e.getX()-crossHair.getWidth()/4);
					crossHair.setYCoord(e.getY()-crossHair.getHeight()/4);
				}
			}
		}
	}
	
	//Keyboard Control-related Methods (From KeyListener interface)
	public void keyReleased(KeyEvent e)
	{
		if(!gameOver){
			if(gameOn)
			{
				if(level.getHuman().alive())
				{
					level.getHuman().keyReleased(e.getKeyChar());
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(!gameOver){
			if(cutscene)
			{
				cutsceneHandler.reportEvent("KeyPress_" +e.getKeyChar());
				if(e.getKeyChar()=='e'||e.getKeyChar()=='E'){	//player skips cutscene
					cutscene = false;
					level.setCutscene(false);
				}
			}
			if(gameOn)
			{
				if(level.getHuman().alive())
				{
					level.getHuman().keyPressed(e.getKeyChar());
				}
			}
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
}
