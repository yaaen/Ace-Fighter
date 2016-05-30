/**
 * @(#)AceFighter1_9 -> Script Engine -> Inventory Data Packet class
 *
 * The Inventory Data Packet contains all the information required to load
 * an inventory. Such information includes: The weapons earned, the stat points (manupians)
 * earned, and the lives earned.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 25, 2008
 */
 
public class InventoryDataPacket extends DataPacket
{
	private int manupians;	//the manupians earned
	private int lives;		//the lives earned
	
	public InventoryDataPacket()
	{
		manupians = 0;
		lives = 0;
	}
	
	public int getManupians()
	{
		return manupians;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	//Mutator Methods
	public void setManupians(int manupians)
	{
		this.manupians = manupians;
	}
	
	public void setLives(int lives)
	{
		this.lives = lives;
	}
}
