/**
 * @(#)AceFighter1_2 -> Player Engine -> HumanEntity class
 *
 * The HumanEntity class represents a player controlled by a human user or
 * player of the game. This class differs from the PlayerEntity class in that
 * it contains information about the user inputs (key strokes, mouse movements, clicks etc).
 * It also contains a field that keeps track of the cumulative score, cumulative manupians,
 * and it contains a PlayerProfile field which is also used by the Inventory Screen.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 17, 2008
 */
import java.util.*;
public class HumanEntity extends PlayerEntity
{
	//private long score;	//the cumulative score earned thus far by the player
	private int manupians;	//the cumulative manupians earned by the player thus far
	
	//Booleans to handle inputs
	private boolean leftInput;		//true if button for running left was pressed
	private boolean rightInput;		//true if button for running right was pressed
	private boolean upInput;		//if button for jump was pressed
	private boolean fireInput;		//if button for firing weapon pressed
	
	public HumanEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord, yCoord, width, height);
//		score = 0;
		manupians = 0;
		leftInput = false;
		rightInput = false;
		upInput = false;
		fireInput = false;
	}
	
	//Accessor Methods
	/*public int getScore()
	{
		return score;
	}*/
	
	public int getManupians()
	{
		return manupians;
	}
	
	//Mutator Methods
	/*public void setScore(int score)
	{
		this.score = score;
	}*/
	
	public void setManupians(int manupians)
	{
		this.manupians = manupians;
	}
	
	/**
	 * Load And Reset Contents.
	 * @param playerProfile: The player profile used to update the player.
	 * This not only loads the player's attributes but it also resets the health to the maximum health
	 * and the special to the maximum special (and so on). All equipped special weapons are also
	 * unequipped when this is called. This is called after an inventory screen is reached.
	 */
	public void loadAndResetPlayerContents(PlayerProfile playerProfile){
		setName(playerProfile.getName());
		setLives(playerProfile.getLives());
		setInitialHealth(playerProfile.getMaximumHealth());
		setMaximumHealth(playerProfile.getMaximumHealth());
		setHealth(playerProfile.getMaximumHealth());
		setSpeed(playerProfile.getSpeed());
		setJumpSpeed(playerProfile.getJumpSpeed());
		setFiringRate(playerProfile.getFiringRate());
		setSpecial(playerProfile.getMaximumSpecial());
		setMaximumSpecial(playerProfile.getMaximumSpecial());
		//Add special weapons to human
		for(int i = 0; i<playerProfile.getWeapons().size(); i++)
		{
			if(playerProfile.getWeapons().get(i).equipped())
			{
				getSpecialWeapons().add(playerProfile.getWeapons().get(i));
			}
		}
		//Set this player as the owner of his weapons
		for(int i = 0; i<getSpecialWeapons().size(); i++)
		{
			getSpecialWeapons().get(i).setOwner(this);
		}
		//User iterator to iterate through list and remove equipped weapons
		/*Iterator<WeaponEntity> weaponIterator = playerProfile.getWeapons().iterator();
		while(weaponIterator.hasNext())
		{
			WeaponEntity currentWeapon = weaponIterator.next();
			if(currentWeapon.equipped())
			{
				weaponIterator.remove();
			}
		}*/
		setScore(playerProfile.getScore());
	}
	
	/**
	 * Update Player Contents
	 * @param playerProfile: The player profile used to update the player.
	 * This simply loads the player's contents before a level is loaded. It does not reset the health
	 * to the maximum health or the special to the maximum special etc... All currently equipped
	 * special weapons will remain equipped.
	 */
	public void loadPlayerContents(PlayerProfile playerProfile){
		setName(playerProfile.getName());
		setLives(playerProfile.getLives());
		setInitialHealth(playerProfile.getMaximumHealth());
		setMaximumHealth(playerProfile.getMaximumHealth());
		setHealth(playerProfile.getHealth());
		setSpeed(playerProfile.getSpeed());
		setJumpSpeed(playerProfile.getJumpSpeed());
		setFiringRate(playerProfile.getFiringRate());
		setSpecial(playerProfile.getSpecial());
		setMaximumSpecial(playerProfile.getMaximumSpecial());
		//Add special weapons to human
		for(int i = 0; i<playerProfile.getWeapons().size(); i++)
		{
			if(playerProfile.getWeapons().get(i).equipped())
			{
				getSpecialWeapons().add(playerProfile.getWeapons().get(i));
			}
		}
		//Set this player as the owner of his weapons
		for(int i = 0; i<getSpecialWeapons().size(); i++)
		{
			getSpecialWeapons().get(i).setOwner(this);
		}
		//User iterator to iterate through list and remove equipped weapons
		/*Iterator<WeaponEntity> weaponIterator = playerProfile.getWeapons().iterator();
		while(weaponIterator.hasNext())
		{
			WeaponEntity currentWeapon = weaponIterator.next();
			if(currentWeapon.equipped())
			{
				weaponIterator.remove();
			}
		}*/
		setScore(playerProfile.getScore());
	}
	
	/**
	 * @param The PlayerProfile from which to load contents.
	 * This method loads the contents of the specified PlayerProfile into the
	 * HumanEntity.
	 */
	public void loadPlayerProfileContents(PlayerProfile playerProfile)
	{
		setName(playerProfile.getName());
		setLives(playerProfile.getLives());
		setInitialHealth(playerProfile.getMaximumHealth());
		setMaximumHealth(playerProfile.getMaximumHealth());
		setHealth(playerProfile.getMaximumHealth());
		setSpeed(playerProfile.getSpeed());
		setJumpSpeed(playerProfile.getJumpSpeed());
		setFiringRate(playerProfile.getFiringRate());
		setSpecial(playerProfile.getSpecial());
		setMaximumSpecial(playerProfile.getSpecial());
		//Add equipped weapons to human
		for(int i = 0; i<playerProfile.getWeapons().size(); i++)
		{
			if(playerProfile.getWeapons().get(i).equipped())
			{
				getSpecialWeapons().add(playerProfile.getWeapons().get(i));
			}
		}
		//Set this player as the owner of his weapons
		for(int i = 0; i<getSpecialWeapons().size(); i++)
		{
			getSpecialWeapons().get(i).setOwner(this);
		}
		//Remove equipped weapons from player profile so that they can't be used in the next inventory screen
		/*for(int i = 0; i<playerProfile.getWeapons().size(); i++)
		{
			if(playerProfile.getWeapons().get(i).equipped())
			{
				playerProfile.getWeapons().remove(playerProfile.getWeapons().get(i));
			}
		}*/
		//User iterator to iterate through list and remove equipped weapons
		Iterator<WeaponEntity> weaponIterator = playerProfile.getWeapons().iterator();
		while(weaponIterator.hasNext())
		{
			WeaponEntity currentWeapon = weaponIterator.next();
			if(currentWeapon.equipped())
			{
				weaponIterator.remove();
			}
		}
		setScore(playerProfile.getScore());
	}
	
	//Updates the state of the HumanEntity
	public void update(double gravity)
	{
		super.update(gravity);
		movementInputUpdate();
		firingInputUpdate();
	}
	
	//Updates to the Player with respect to the inputs
	/**
	 * Movement Input Update
	 * Updates the player's movement with respect to states of the input booleans.
	 * If keys were pressed, then the states of the input booleans could have been changed
	 * in the keyPressed(), keyReleased() and mouseDragged() methods. This method responds
	 * to these changes and changes the player's velocity accordingly. The changes in
	 * the different velocities are then seen by the PlayerEntity's animationUpdate() method
	 * which in turn configures the PlayerSprite accordingly (ie: animates the player
	 * appropriately) to reflect the new state of the player.
	 */
	public void movementInputUpdate()
	{
		if((!(this.inAir())) && rightInput)	//running to the right
		{
			/*if(running())
			{
				getSprite().setXVeloc(getSprite().getXVeloc());	//if already running, simply maintain the speed
			}
			else
			{
				getSprite().setXVeloc(getSprite().getXVeloc()+this.getSpeed());	//if starting to run, add run speed to current speed
			}*/
			getSprite().setXVeloc(getRelationalVelocity()+this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		if((!this.inAir())  && leftInput)	//running to the left
		{	
			/*if(running())
			{
				getSprite().setXVeloc(getSprite().getXVeloc());	//if already running, maintain speed
			}
			else
			{
				getSprite().setXVeloc(getSprite().getXVeloc()-this.getSpeed());
			}*/
			getSprite().setXVeloc(getRelationalVelocity()-this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		if((!this.inAir()) && upInput)
		{
			this.getSprite().setYVeloc(this.getSprite().getYVeloc()-this.getJumpSpeed());
			this.setInAir(true);		
			setAnimating(true);
			setRunning(false);
		}
		if((!leftInput)&&(!rightInput)&&(!this.inAir()))
		{
			this.getSprite().setXVeloc(0);
			this.getSprite().setMovingHorizontally(false);
			this.getSprite().setMovingVertically(false);
			setRunning(false);
		}
		if((this.inAir())&&leftInput){	//Allow players to move left or right in mid-air. Makes platforming easier.
			getSprite().setXVeloc(getRelationalVelocity()-this.getSpeed());
		}
		if((this.inAir())&&rightInput){
			getSprite().setXVeloc(getRelationalVelocity()+this.getSpeed());
		}
	}
	
	/**
	 * Firing Input Update
	 * Updates the player's firing state. If the mousePressed() method reports that the player
	 * has been firing, that change gets recognized by this method. This method in turn
	 * sets the player's firing boolean to true. When this boolean is true, the PlayerEntity class
	 * calls the fire() method and animates the player appropriately. When the player is no longer
	 * firing (as reported by the mouseReleased() method, 
	 * the PlayerEntity's firing boolean is set to false and the fire() method is no longer called.
	 * The appropriate firing animation also stops.
	 */
	public void firingInputUpdate()
	{
		if(fireInput)
		{	
			this.setFiring(true);
		}
		if(!fireInput)
		{
			this.setFiring(false);
		}
	}
	
		/** Key Pressed
	 * @param The key that was pressed. The main engine's keyPressed() KeyListener method calls
	 * this method and passes the key that was pressed.
	 * This method then reacts based on the key pressed (Makes player run, jump, etc).
	 */
	public void keyPressed(char key)
	{
		switch(key)
		{
			case 'A':
				leftInput = true;
				break;
			case 'a':
				leftInput = true;
				break;
			case 'D':
				rightInput = true;
				break;
			case 'd':
				rightInput = true;
				break;
			case 'W':
				upInput = true;
				break;
			case 'w':
				upInput = true;
				break;
			case 'S':
				if(!specialEquipped())
					setSpecialEquipped(true);
				else
					setSpecialEquipped(false);
				break;
			case 's':
				if(!specialEquipped())
					setSpecialEquipped(true);
				else
					setSpecialEquipped(false);
				break;
		}
	}
	
	/** Key Released
	 * @param The key that was released.
	 * This method reacts to the key that was released (Stops player from running etc...)
	 */
	public void keyReleased(char key)
	{
		switch(key)
		{
			case 'A':
				leftInput = false;
				break;
			case 'a':
				leftInput = false;
				break;
			case 'D':
				rightInput = false;
				break;
			case 'd':
				rightInput = false;
				break;
			case 'W':
				upInput = false;
				break;
			case 'w':
				upInput = false;
				break;
		}
	}
	
	/** Mouse Pressed (also gets called when mouse is dragged).
	 * @param The x and y coordinates of the mouse during the instant that this method was called.
	 * This method computes the angle that the player has to face in order to fire to where the
	 * mouse is located. Allows player to aim and fire with his mouse.
	 */
	public void mousePressed(int mouseX, int mouseY)
	{
		double yDiff = mouseY-this.getSprite().getYCoord();
		double xDiff = mouseX-this.getSprite().getXCoord();
		double quotient = yDiff/xDiff;
		double computeAngle = Math.toDegrees(Math.atan(yDiff/xDiff));
		this.getSprite().setFaceAngle(computeAngle);
		if(this.getSprite().getFaceAngle()<360)
		{
			this.getSprite().setFaceAngle(360+computeAngle);			
		}
		else
		{
			this.getSprite().setFaceAngle((computeAngle));
		}
		if(xDiff<0)
		{
			this.getSprite().setFaceAngle(this.getSprite().getFaceAngle()-180);
		}
		fireInput = true;
	}
	
	/** Mouse Released
	 * Informs the HumanEntity that the mouse button is no longer being clicked. This in turn
	 * stops the player from firing.
	 */
	public void mouseReleased()
	{
		fireInput = false;
	}
	
	public void executeCommand(Command command)
	{
		//No implementation in this class
	}
}
