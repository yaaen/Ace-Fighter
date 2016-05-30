/**
 * @(#)AceFighter1_4 -> Item Engine -> ItemEntity class
 *
 * This class enables the use of Items in Ace Fighter. Items can either be weapons
 * that can be picked up throughout a level, earned at the end of a battle or
 * powerups that add health, add lives, or add points or multiply points (score multiplier).
 * All Items have an AnimatedSprite and an Owner.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
import java.io.*; 
public class ItemEntity implements Serializable
{
	//static animation variables. All Items conform to this animation standard by
	//default.
	private static final int NUM_COLS = 5;
	private static final int STRIP_SIZE = 13;
	
	private AnimatedSprite sprite;	//the sprite used for animation, positioning etc...
	private Actor owner;		//the owner of the item
	
	private boolean available;		//true if the item is available for pickup
	private int timer;				//counts the time this item has been available (So that items
									//don't stick around forever)
	private int timeToLive;			//the amount of time this item is available before it gets
									//cleared off of the stage again
	private boolean justBeenPickedUp;	//true if the item has just been picked up. This is used to display
										//the name of the item when the player picks it up to let the player know
										//what the item is called. It's part of the bells and whistles.
	private int justBeenPickedUpTimer;	//makes sure that the name of the item that's just been picked up is displayed
										//for a brief period of time after the item has been picked up
	
	public ItemEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new AnimatedSprite(xCoord, yCoord, width, height);
		sprite.setNumCols(NUM_COLS);
		sprite.setStripSize(STRIP_SIZE);
		sprite.setImageWidth(width);
		sprite.setImageHeight(height);
		owner = null;
		available = false;
		timer = 0;
		timeToLive = 200;	//default time to live is 100 game loop iterations
		justBeenPickedUp = false;
		justBeenPickedUpTimer = 0;
	}
	
	//Accessor Methods
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	public Actor getOwner()
	{
		return owner;
	}
	
	public boolean available()
	{
		return available;
	}
	
	public boolean justBeenPickedUp()
	{
		return justBeenPickedUp;
	}
	
	//Mutator Methods
	public void setSprite(AnimatedSprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void setOwner(Actor owner)
	{
		this.owner = owner;
	}
	
	public void setAvailable(boolean available)
	{
		this.available = available;
	}
	
	/**
	 * Spawn Item
	 * @param The x and y coordinates at which to spawn the item.
	 * Spawns the item at the specified x and y position.
	 */
	public void spawn(int xCoord, int yCoord)
	{
		available = true;
		sprite.setXCoord(xCoord);
		sprite.setYCoord(yCoord);
		sprite.setYVeloc(0);
	}
	
	public void update(double gravity)
	{
		if(available)
		{
			//Update position
			sprite.setYVeloc(sprite.getYVeloc()+gravity);
			sprite.setYCoord(sprite.getYCoord()+sprite.getYVeloc());
				
			//Update the animation
			sprite.animate();
			
			//Increment timer
			timer++;
			
			//Check if item has timed out
			if(timer>=timeToLive)
			{
				available = false;
				timer = 0;	//reset timer
			}
		}
		if(justBeenPickedUp)
		{
			justBeenPickedUpTimer++;
			if(justBeenPickedUpTimer>=50)	//display item's name for a brief period of time after it's been picked up
			{
				justBeenPickedUp = false;
				justBeenPickedUpTimer = 0;
			}
		}
	}
	
	//Collision Effect methods
	/**
	 * Collision Effect
	 * @param The Player the item collides with.
	 * When a player collides with an Item (picks up the item), the item
	 * is no longer available to the other players. The Item also belongs
	 * to the player that it collided with.
	 */
	public void collisionEffect(PlayerEntity player)
	{
		if(available)	//players can only collide with items that are available
		{
			available = false;	//item is no longer available if a player picks it up
			owner = player;
			justBeenPickedUp = true;	//display the item's name for a brief period of time
		}
	}
	
	/**
	 * Collision Effect
	 * @param The Platform the Item collides with.
	 * If an available Item collides with a platform, the item stops falling.
	 */
	public void collisionEffect(PlatformEntity platform)
	{
		if(available)
		{
			sprite.setYCoord(platform.getSprite().getYCoord()-this.getSprite().getHeight());
			sprite.setYVeloc(platform.getSprite().getYVeloc());	//item stops falling
		}
	}
}
