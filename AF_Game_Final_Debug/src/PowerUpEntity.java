/**
 * @(#)AceFighter2_4 -> Item Engine -> PowerUpEntity class
 *
 * This class represents a Power Up. A power up is slightly different
 * from a regular ItemEntity. The main difference is that a power up can
 * only be spawned once. It's usually spawned after an opponent is killed.
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 9, 2008
 */
 
public class PowerUpEntity extends ItemEntity
{
	private boolean alreadyBeenSpawned;	//true if this power up has already been spawned before
	
	public PowerUpEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		alreadyBeenSpawned = false;
	}
	
	//Accessor
	/** already been spawned
	 * @return True if this item has already been spawned, false otherwise.
	 */
	public boolean alreadyBeenSpawned()
	{
		return alreadyBeenSpawned;
	}
	
	//Mutator
	public void setAlreadyBeenSpawned(boolean alreadyBeenSpawned)
	{
		this.alreadyBeenSpawned = alreadyBeenSpawned;
	}
	
	//Overridden method(s)
	/** Spawn
	 * @param The x and y coords at which to spawn the power up.
	 * In addition to simply spawning the item, this version of the spawn()
	 * method also sets the alreadyBeenSpawned boolean to true.
	 */
	public void spawn(int xCoord, int yCoord)
	{
		super.spawn(xCoord,yCoord);
		alreadyBeenSpawned = true;
	}
}
