/**
 * @(#)AceFighter1_9 -> Script Engine -> Game Script Reader class
 *
 * The Game Script reader reads through the game's main script and loads the corresponding
 * information. It then packages that information into a Data Packet (either a Battle- or
 * InventoryDataPacket) and returns that packet to the main engine. The main engine then proceeds
 * by unpacking that package and loading the corresponding data structures etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 25, 2008
 */
 
import java.io.*;
import java.util.*;
 
public class GameScriptReader 
{
	private InputStream scriptStream; 		//needed to read the game script
	private BufferedReader br;				//needed to read the game script
//	private LevelScriptReader levelReader;	//the level reader used to read the level data
	private DataPacket packet;				//the data packet to return to the game engine
	
	public GameScriptReader(String filename)
	{
		//try
		//{
			scriptStream = this.getClass().getResourceAsStream(filename);
			//scriptStream = new FileInputStream(new File(filename));
			if(scriptStream==null)
				System.out.println("GameScriptReader's Script Stream is null -> Message from GameScriptReader constructor");
			br = new BufferedReader(new InputStreamReader(scriptStream));
		//}
		/*catch(IOException e)
		{
			System.out.println(e.getMessage());
		}*/
	}
	
	/**
	 * Read Segment Header
	 * @return A String that says either "Battle" or "Inventory".
	 * The Read Segment Header method returns a string to the main engine. Based
	 * on the string returned, the main engine will either call readBattleSegment()
	 * or readInventorySegment().
	 */
	public String readSegmentHeader()
	{
		try
		{
			return br.readLine();
		}
		catch(IOException e)
		{
			System.out.println("Error in GameScriptReader, readScriptSegment(): IOException");
		}
		return null;
	}
	
	/**
	 * Read Battle Segment
	 * @return A Battle Data Packet which contains all the information necessary to load a battle.
	 * This method returns all the information necessary to load a battle to the main engine.
	 */
	public BattleDataPacket readBattleSegment()
	{
		try
		{
			BattleDataPacket packet = new BattleDataPacket();
			String cutscene = br.readLine();
			packet.setCutscene(cutscene);
			String shootingEnabled = br.readLine();
			packet.setShootingEnabled(shootingEnabled);
			String levelFileName = br.readLine();
			packet.setLevelFileName(levelFileName);
			//levelReader = new LevelScriptReader(levelFileName);
			//levelReader.readLevel(packet);
			String numWeapons = br.readLine();
			Integer integer = Integer.parseInt(numWeapons);
			ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
			for(int i = 0; i<integer.intValue();i++)
			{
				String weapon = br.readLine();
				if(weapon.equals("Scatter Fire"))
				{
					//add scatter fire to list of items
					weapons.add(new ScatterFireWeapon(0,0,50,50));
				}
			}
			packet.setWeapons(weapons);
			return packet;
		}
		catch(IOException e)
		{
			System.out.println("Error in GameScriptReader, readBattleSegment(): IOException");
		}
		return null;
	}
	
	/**
	 * Read Inventory Segment
	 * @return An Inventory Data Packet which contains all the information necessary to load an Inventory.
	 * This method returns all the information necessary to load an inventory.
	 */
	public InventoryDataPacket readInventorySegment()
	{
		try
		{
			InventoryDataPacket packet = new InventoryDataPacket();
			String cutscene = br.readLine();
			String numManupians = br.readLine();
			Integer integer = Integer.parseInt(numManupians);
			int manupians = integer.intValue();
			String numLives = br.readLine();
			integer = Integer.parseInt(numLives);
			int lives = integer.intValue();
			String numWeapons = br.readLine();
			integer = Integer.parseInt(numWeapons);
			ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
			for(int i = 0; i<integer.intValue();i++)
			{
				String weapon = br.readLine();
				if(weapon.equals("Scatter Fire"))
				{
					//add scatter fire to list of items
					weapons.add(new ScatterFireWeapon(0,0,50,50));
				}
				if(weapon.equals("Bouncy Fire"))
				{
					weapons.add(new BounceFireWeapon(0,0,50,50));
				}
				if(weapon.equals("Grenades"))
				{
					weapons.add(new GrenadeWeapon(0,0,50,50));
				}
				if(weapon.equals("Rockets"))
				{
					weapons.add(new RocketWeapon(0,0,50,50));
				}
				if(weapon.equals("Scatter Rockets"))
				{
					weapons.add(new ScatterRocketWeapon(0,0,50,50));
				}
				if(weapon.equals("Homing Missiles"))
				{
					weapons.add(new HomingMissileWeapon(0,0,50,50));
				}
				if(weapon.equals("Gravity Ball"))
				{
					weapons.add(new GravityBallWeapon(0,0,50,50));
				}
			}
			packet.setCutscene(cutscene);
			packet.setManupians(manupians);
			packet.setLives(lives);
			packet.setWeapons(weapons);
			return packet;
		}
		catch(IOException e)
		{
			System.out.println("Error in GameScriptReader, readInventorySegment(): IOException");
		}
		return null;
	}
}
