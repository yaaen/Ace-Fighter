/**
 * @(#)AceFighter1_9 -> Script Engine -> Data Packet class
 *
 * The Data Packet class is used to 'encapsulate' or 'package' data required to
 * load the main engine in a compact and concise way. The Data Packet contains
 * the main fields shared between the BattleDataPacket and the InventoryDataPacket.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 25, 2008
 */
import java.util.*;

public class DataPacket 
{
	private String packetType;	//the type of packet -> either Battle or Inventory
	private String cutscene;	//specifies whether a cutscene will be loaded or not -> values are: 'Cutscene' and 'NoCutscene'
	private ArrayList<WeaponEntity> weapons;	//the weapons available in the battle or the weapons earned for the inventory
	
	public DataPacket()
	{
		packetType = "";
		cutscene = "";
		weapons = new ArrayList<WeaponEntity>();
	}
	
	//Accessor Methods
	public String getPacketType()
	{
		return packetType;
	}
	
	public String getCutscene()
	{
		return cutscene;
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}
	
	//Mutator Methods
	public void setPacketType(String packetType)
	{
		this.packetType = packetType;
	}
	
	public void setCutscene(String cutscene)
	{
		this.cutscene = cutscene;
	}
	
	public void setWeapons(ArrayList<WeaponEntity> weapons)
	{
		this.weapons = weapons;
	}
}
