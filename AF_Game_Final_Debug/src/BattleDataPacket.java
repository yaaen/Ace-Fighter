/**
 * @(#)AceFighter1_9 -> Script Engine -> Battle Data Packet class
 *
 * The Battle Data Packet is the Data Packet that contains all the required information
 * to load a battle in Ace Fighter. It contains fields such as: The Level to load,
 * and the items which are available etc... 
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 25, 2008
 */
import java.util.*;

public class BattleDataPacket extends DataPacket
{
	private String shootingEnabled;	//a string containing information about whether the player can shoot
									//in the level or not. If the string says "ShootingEnabled", then
									//the player can shoot in the level. If the string says
									//"ShootingDisabled", the player can't use his weapons in the level.
									//Having the shooting be disabled is particularly useful for tutorial
									//levels where the focus is not shooting, but other things like platforming etc...
	private String levelFileName;	//the file name of the level file to be loaded
/*	private String staticOrDynamic;	//if value is "Static", level doesn't scroll. If value is "Dynamic", level scrolls
	private double gravity;			//the gravity of the level
	private String backgroundImageFileName;	//image file name of background image of level
	private ArrayList<PlatformEntity> platforms;	//the list of platforms in level
	private int humanXCoord;	//the spawing x coordinate of the human player
	private int humanYCoord;	//the y coord of the human player in the level when player spawns.
	private ArrayList<AIEntity> opponents;	//opponents in the level
	private ArrayList<AIEntity> allies;		//allies in the level
	private ArrayList<BossEntity> bosses;	//the bosses in the level
	private ArrayList<PortalEntity> portals;	//the portals in the level*/
	
	public BattleDataPacket()
	{
		super();
		shootingEnabled = "";
		levelFileName = "";
	/*	staticOrDynamic = "";
		gravity = 0;
		backgroundImageFileName = "";
		platforms = new ArrayList<PlatformEntity>();
		humanXCoord = 0;
		humanYCoord = 0;
		opponents = new ArrayList<AIEntity>();
		allies = new ArrayList<AIEntity>();
		bosses = new ArrayList<BossEntity>();
		portals = new ArrayList<PortalEntity>();*/
	}
	
	//Accessor Methods
	public String getShootingEnabled()
	{
		return shootingEnabled;
	}
	
	public String getLevelFileName()
	{
		return levelFileName;
	}
	
/*	public String getStaticOrDynamic()
	{
		return staticOrDynamic;
	}
	
	public double getGravity()
	{
		return gravity;
	}
	
	public String getBackgroundImageFileName()
	{
		return backgroundImageFileName;
	}
	
	public ArrayList<PlatformEntity> getPlatforms()
	{
		return platforms;
	}
	
	public int getHumanXCoord()
	{
		return humanXCoord;
	}
	
	public int getHumanYCoord()
	{
		return humanYCoord;
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
	
	public ArrayList<PortalEntity> getPortals()
	{
		return portals;
	}
	
	//Mutator Methods
	*/
	public void setShootingEnabled(String shootingEnabled)
	{
		this.shootingEnabled = shootingEnabled;
	}
	
	public void setLevelFileName(String levelFileName)
	{
		this.levelFileName = levelFileName;
	}/*	
	
	public void setStaticOrDynamic(String staticOrDynamic)
	{
		this.staticOrDynamic = staticOrDynamic;
	}
	
	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}
	
	public void setBackgroundImageFileName(String backgroundImageFileName)
	{
		this.backgroundImageFileName = backgroundImageFileName;
	}
	
	public void setPlatforms(ArrayList<PlatformEntity> platforms)
	{
		this.platforms = platforms;
	}
	
	public void setHumanXCoord(int humanXCoord)
	{
		this.humanXCoord = humanXCoord;
	}
	
	public void setHumanYCoord(int humanYCoord)
	{
		this.humanYCoord = humanYCoord;
	}
	
	public void setOpponents(ArrayList<AIEntity> opponents)
	{
		this.opponents = opponents;
	}
	
	public void setAllies(ArrayList<AIEntity> allies)
	{
		this.allies = allies;
	}
	
	public void setBosses(ArrayList<BossEntity> bosses)
	{
		this.bosses = bosses;
	}
	
	public void setPortals(ArrayList<PortalEntity> portals)
	{
		this.portals = portals;
	}*/
}
