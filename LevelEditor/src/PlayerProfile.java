/**
 * @(#)AceFighter1_2 -> PlayerProfile class
 *
 * The Player Profile class is not part of the Player Engine but it is used
 * by the player engine. It's primary use is as a middle ground between the
 * Human entity and the Inventory. At the very beginning of the game, when the
 * player chooses his character, the contents of the character file are loaded into
 * the PlayerProfile. Then when the game is started, the contents of the PlayerProfile
 * are loaded into the HumanEntity. When the battle is over and the Inventory screen
 * is loaded, the contents of the HumanEntity are loaded back into the PlayerProfile.
 * This way, the Inventory can manipulate the contents of the PlayerProfile without
 * having to even know about the HumanEntity. Note that the PlayerProfile is only
 * used by the HumanEntity and no other class in the PlayerEngine.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 17, 2008
 */
 
import java.util.*;
import java.io.*;

public class PlayerProfile implements Serializable
{
	private String name;	//the name of the player
	private int lives;		//the number of lives of this player
	private int health;		//the amount of health of this player
	private int originalHealth;	//the original amount of health.
	private int maximumHealth; //the maximum amount of health of the player. Can be increased by the HealthExpanderEntity
	private int speed;		//the running speed of the player
	private int originalSpeed;	//the original speed the Player started out with.
								//Prevents a player from reducing one field below to what it started out as to
								//gain Manupians for other fields. Preserves integrity of character, basically.
	private int jumpSpeed;	//the jump Speed of the player
	private int originalJumpSpeed;	//original jump speed
	private int firingRate;	//the rate of fire of the standard weapon of this player
	private int originalFiringRate;	//original firing rate
	private int special;	//the amount of time the player can use his special weapon
	private int originalSpecial;	//the original special
	private int maximumSpecial;	//The maximum amount of special the player an have. Can be increased by a powerup.
	private long score;	//the cumulative score earned thus far by the player
	private int manupians;	//the cumulative manupians earned by the player thus far
	private File imageFile;	//the file of the Image for this player
	
	private ArrayList<WeaponEntity> weapons;	//the weapons currently in possession by the player
	
	public PlayerProfile()
	{
		name = "";
		lives = 0;
		health = 0;
		speed = 0;
		jumpSpeed = 0;
		firingRate = 0;
		special = 0;
		maximumSpecial = 0;
		score = 0;
		manupians = 0;
		imageFile = null;
		weapons = new ArrayList<WeaponEntity>();
	}
	
	//Accessor Methods
	public String getName()
	{
		return name;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaximumHealth()
	{
		return maximumHealth;
	}
	
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
	
	public int getMaximumSpecial(){
		return maximumSpecial;
	}
	
	public long getScore()
	{
		return score;
	}
	
	public int getManupians()
	{
		return manupians;
	}
	
	public File getImageFile()
	{
		return imageFile;
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}
	
	/** Load Character File
	 * @param The name of the file to be loaded into the Player Profile.
	 * This method loads the contents of the file with the given file name
	 * into the PlayerProfile.
	 */
	public void loadCharacterFile(String filename)
	{
		//try
		//{
			InputStream scriptStream = this.getClass().getResourceAsStream(filename);
			//InputStream scriptStream = new FileInputStream(new File(filename));
			if(scriptStream==null)
				System.out.println("Script Stream is null");
			BufferedReader br = new BufferedReader(new InputStreamReader(scriptStream));
		try
		{
			name = br.readLine();
			String line = br.readLine();
			Scanner lineScanner = new Scanner(line);	//read the number from the line
			lives = lineScanner.nextInt();
			line = br.readLine();
			lineScanner = new Scanner(line);
			health = lineScanner.nextInt();
			originalHealth = health;
			maximumHealth = health;
			line = br.readLine();
			lineScanner = new Scanner(line);
			speed = lineScanner.nextInt();
			originalSpeed = speed;
			line = br.readLine();
			lineScanner = new Scanner(line);
			jumpSpeed = lineScanner.nextInt();
			originalJumpSpeed = jumpSpeed;
			line = br.readLine();
			lineScanner = new Scanner(line);
			firingRate = lineScanner.nextInt();
			originalFiringRate = firingRate;
			line = br.readLine();
			lineScanner = new Scanner(line);
			special = lineScanner.nextInt();
			originalSpecial = special;
			maximumSpecial = special;
			String imageFileName = br.readLine();
			imageFile = new File(imageFileName);
		}
		catch(IOException e)
		{
			System.out.println("Error in PlayerProfile, loadCharacterFile(): IOException");
			System.out.println(e.getMessage());
		}
	}
	
	/** Load Human Contents
	 * @param The HumanEntity whose contents should be loaded into the PlayerProfile.
	 * This method loads the all the contents (speed, manupians, jumpSpeed etc) of the
	 * specified HumanEntity into the PlayerProfile.
	 */
	public void loadHumanContents(HumanEntity human)
	{
		name = human.getName();
		health = human.getHealth();
		maximumHealth = human.getMaximumHealth();
		lives = human.getLives();
		speed = human.getSpeed();
		jumpSpeed = human.getJumpSpeed();
		firingRate = human.getFiringRate();
		special = human.getSpecial();
		maximumSpecial = human.getMaximumSpecial();
		score = human.getScore();
		manupians = human.getManupians();
		weapons = human.getSpecialWeapons();
	}
	
	/**
	 * Add Inventory Data Packet Contents
	 * Adds the contents of the Inventory Data Packet to the Player Profile
	 */
	public void addInventoryDataPacketContents(InventoryDataPacket packet)
	{
		manupians += packet.getManupians();
		lives += packet.getLives();
		weapons.clear();	//Clear currently owned weapons before adding inventory weapons
		for(int i = 0; i<packet.getWeapons().size(); i++)
		{
			weapons.add(packet.getWeapons().get(i));
		}
	}
	
	/**
	 * Edit Attribute
	 * Edits the specified attribute.
	 * @param The attribute to edit
	 * @param The amount by which to modify the respective attribute.
	 */
	public void editAttribute(String attribute, int value)
	{
		if(attribute.equals("Health"))
		{
			if(value<0)	//if you're subtracting from an attribute
			{
				if(health>originalHealth)	//only allow user to subtract points from an attribute if there are points to be subtracted
											//A player cannot make an attribute weaker than it initially was when he first picked the character.
											//The integrity of the character is preserved.
				{
					health+=value;
					manupians-=value;
				}
			}
			else
			{
				if(manupians>0)	//if there are still manupians left to use
				{
					health+=value;
					manupians-=value;
				}
			}
			
			
		}
		if(attribute.equals("Speed"))
		{
			if(value<0)
			{
				if(speed>originalSpeed)
				{
					speed+=value;
					manupians-=value;
				}
			}
			else
			{
				if(manupians>0)
				{	
					speed+=value;
					manupians-=value;
				}
			}
		}
		if(attribute.equals("Jump Speed"))
		{
			if(value<0)
			{
				if(jumpSpeed>originalJumpSpeed)
				{
					jumpSpeed+=value;
					manupians-=value;
				}
			}
			else
			{
				if(manupians>0)
				{
					jumpSpeed+=value;
					manupians-=value;
				}
			}
		}
		if(attribute.equals("Firing Rate"))
		{
			if(value>0)
			{
				if(firingRate<originalFiringRate)
				{
					firingRate+=value;
					manupians+=value;
				}
			}
			else
			{
				if(manupians>0&&firingRate>1)
				{
					firingRate+=value;
					manupians+=value;
				}
			}
		}
		if(attribute.equals("Special"))
		{
			if(value<0)
			{
				if(special>originalSpecial)
				{
					special+=value;
					manupians-=value;
				}
			}
			else
			{
				if(manupians>0)
				{
					special+=value;
					manupians-=value;
				}
			}
		}
	}
}
