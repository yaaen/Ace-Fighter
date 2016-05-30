/**
 * @(#)AceFighter1_2 -> Player Engine -> AIEntity class
 *
 * The AIEntity (AI = Artificial Intelligence) is a player controlled by
 * the computer. The major difference between the AIEntity and the rest of
 * the player engine is that the AIEntity contains information needed by the
 * computer to make decisions. It also contains a field that determines
 * how many points a player gets for killing an AIEntity.
 *
 * An AIEntity also has the ability to execute commands (for the cutscenes).
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 17, 2008
 */
import java.io.*;
import java.util.*;

public class AIEntity extends PlayerEntity
{
	private int originalScoreValue;	//amount of points you get for killing this AIEntity. Allows scoreValue to be
								//reset to original value after AIEntity has been killed and his score has been extracted.
	private int scoreValue;		//the amount of points you get for killing this AIEntity
	private PowerUpEntity powerup;	//the powerup that can be picked up after this AI is killed
	//private boolean readyForCommand;	//true if the player is ready to execute the next command
	//private int elapsedExecutionTime;	//the amount of time the player has been executing current command for
	private String difficulty;		//the difficulty of the AI - Dummy means the AI just stands there like a dummy. Other levels
									//describe functionality of the AI.
	private Actor currentTarget;	//the current target of the AIEntity. The AI focuses on the current target. That means it will only
										//react to and try to attack the current target.
	private int currentState = 0;	//the current state of the AIEntity. States determine the behavior of the AIEntity.
	private int previousState = 0;	//the state the AI was previously in. Used in checkState() method
	private static final int OFFENSIVE_STATE = 0;	//the offensive state. When the AIEntity is in this state, it acts very aggressively.
	private static final int DEFENSIVE_STATE = 1;	//the defensive state.
	private static final int ITEM_STATE = 2;	//the item state. When an item is within a reasonable distance, the AI switches to this state
	private static final int EASY_ATTACK_DISTANCE = 700;	//an easy AI will attack (stop running towards
															//and fire at) an opponent once the opponent is
															//within 700 pixels.
	private static final int MEDIUM_ATTACK_DISTANCE = 600;
	private static final int HARD_ATTACK_DISTANCE = 500;
	private static final int EXPERT_ATTACK_DISTANCE = 400;
	private ArrayList<WeaponEntity> levelWeapons;	//the weapons available in the level. Allows the AI to determine
													//if weapons have spawned and are available for pick-up.
	private WeaponEntity closestAvailableWeapon;	//the closest available weapon
	
	private boolean cutscene;	//true if there's currently a cutscene going on. The AIEntity should not be
								//performing AI decisions while a cutscene is executing since it can interfere
								//with the AI's cutscene commands.
	
	public AIEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		originalScoreValue = 0;
		scoreValue = 0;
		powerup = null;
		//readyForCommand = false;
		//elapsedExecutionTime = 0;
		difficulty = "";
		currentTarget = null;
		levelWeapons = new ArrayList<WeaponEntity>();
		closestAvailableWeapon = null;
		cutscene = false;
	}
	
	//Accessors
	public int getOriginalScoreValue()
	{
		return originalScoreValue;
	}
	
	public int getScoreValue()
	{
		return scoreValue;
	}
	
	public PowerUpEntity getPowerup()
	{
		return powerup;
	}
	
	/*public boolean readyForCommand()
	{
		return readyForCommand;
	}
	
	public int getElapsedExecutionTime()
	{
		return elapsedExecutionTime;
	}*/
	
	//Mutators
	public void setScoreValue(int scoreValue)
	{
		this.scoreValue = scoreValue;
		originalScoreValue = scoreValue;
	}
	
	public void setPowerup(PowerUpEntity powerup)
	{
		this.powerup = powerup;
	}
	
	/*public void setReadyForCommand(boolean readyForCommand)
	{
		this.readyForCommand = readyForCommand;
	}
	
	public void setElapsedExecutionTime(int elapsedExecutionTime)
	{
		this.elapsedExecutionTime = elapsedExecutionTime;
	}*/
	
	public void setDifficulty(String difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public void setLevelWeapons(ArrayList<WeaponEntity> levelWeapons)
	{
		this.levelWeapons = levelWeapons;
	}
	
	public void setCutscene(boolean cutscene)
	{
		this.cutscene = cutscene;
	}
	
	/*Retrieve Score
	 * Gets the score of this player. Additionally, it also sets the AIEntity's earnable
	 * score to 0 to avoid the score from being extracted over and over again. It makes
	 * sure that the score is only retrieved once every time the AIEntity dies.
	 */
	public int retrieveScore()
	{
		int result = scoreValue;
		scoreValue = 0;
		return result;
	}
	
	//Update the AIEntity
	public void update(double gravity)
	{
		super.update(gravity);
		if(alive())
		{
			if(!dying())
			{
				scoreValue = originalScoreValue;
			}
			//Call various other AI functions here
			/*if(currentState==OFFENSIVE_STATE)
				System.out.println("OFFENSIVE_STATE");
			if(currentState==DEFENSIVE_STATE)
				System.out.println("DEFENSIVE_STATE");
			if(currentState==ITEM_STATE)
				System.out.println("ITEM_STATE");*/
			if(!cutscene)	//make AI decisions as long as there's no cutscene going on
				makeMove();
		}
	}
	
	public void fire()
	{
		if(getSpecialWeapons().size()>0 && specialEquipped())
		{
			for(int i = 0; i<getSpecialWeapons().size(); i++)
			{
				//use each item's functionality
				getSpecialWeapons().get(i).useWeapon(getSprite().getFaceAngle());
			}
			setSpecial(1);	//AI can't run out of special (can always use special weapons
							//if he has some)
		}
		else
			super.fire();
	}
	
	//Various AI functions
	/**Make Move
	 * AI evaluates current situation of game and makes a move based on his difficulty and
	 * the surrounding environment etc...
	 */
	public void makeMove()
	{
		if(getOpponents().size()>0)
		{
			if(difficulty.equals("Easy"))
			{
				makeOffensiveMove(EASY_ATTACK_DISTANCE);
			}
			if(difficulty.equals("Medium"))
			{
				checkState();
				if(currentState==OFFENSIVE_STATE || currentState==DEFENSIVE_STATE)
					makeOffensiveMove(MEDIUM_ATTACK_DISTANCE);
				if(currentState==ITEM_STATE)
					attemptItemPickup();
			}
			if(difficulty.equals("Hard"))
			{
				checkState();
				if(currentState==OFFENSIVE_STATE)
					makeOffensiveMove(HARD_ATTACK_DISTANCE);
				if(currentState==DEFENSIVE_STATE)
					makeDefensiveMove();
				if(currentState==ITEM_STATE)
					attemptItemPickup();
			}
			if(difficulty.equals("Expert"))
			{
				checkState();
				if(currentState==OFFENSIVE_STATE)
					makeOffensiveMove(EXPERT_ATTACK_DISTANCE);
				if(currentState==DEFENSIVE_STATE)
					makeDefensiveMove();
				if(currentState==ITEM_STATE)
					attemptItemPickup();
			}
		}
		else	//if there are no opponents left in the level
		{
			this.getSprite().setMovingHorizontally(false);
	 		this.getSprite().setMovingVertically(false);
	 		setRunning(false);
	 		setFiring(false);
		}
	}
	
	/*Check State
	 * Checks to see if the AI should change his state.
	 */
	public void checkState()
	{
		if(currentState!=ITEM_STATE)
		{
			//See who is the closes
			selectCurrentTarget();
			if(currentTarget!=null)
			{
				int defenseDistance = 200;
				if(difficulty.equals("Expert"))
				{
					defenseDistance = 300;
				}
				//if current target is too close, change to defensive state
				if(distanceTo(currentTarget.getSprite())<=defenseDistance)
				{
					currentState = DEFENSIVE_STATE;
					previousState = currentState;
				}
				else
				{
					currentState = OFFENSIVE_STATE;
					previousState = currentState;
				}
			}
		}
		//Check for available weapons
		checkForAvailableWeapons();
		if(closestAvailableWeapon!=null)
		{
			if(distanceTo(closestAvailableWeapon.getSprite())<=400)
			{
				currentState = ITEM_STATE;
			}
		}
		else	//if there is no available weapon
		{
			currentState = previousState;	//switch back to state you were in before checking for weapons
		}
	}
	/**Make Offensive Move
	 *The AI makes an offensive move. An offensive move generally means that the AI will select a target and try to approach this target 
	 *until it is within a given distance. Once within that distance, the AI will fire upon the current target. The AI will select
	 *the closest opponent in the list of opponents as its current target.
	 */
	public void makeOffensiveMove(int attackDistance)
	{
		selectCurrentTarget();
		if(currentTarget!=null)
		{
			//first the AI checks if the target is even close enough to attack. This check
			//prevents the AI from attacking a player that's on the other side of the level (dynamic level, for example)
			if(distanceTo(currentTarget.getSprite())<=1000)	//if target is close enough for AI to attack
			{
				if(distanceTo(currentTarget.getSprite())<=attackDistance)	//if the target is within range
				{
					//Stop moving and fire at the target
					this.getSprite().setMovingHorizontally(false);
			 		this.getSprite().setMovingVertically(false);
			 		setRunning(false);
					fireAtTarget();
				}
				else	//if target is not within a reasonable distance
				{
					//stop shooting and move towards target
					setFiring(false);
					moveTowardsTarget();
				}
			}
		}
	}
	
	/*Make Defensive Move
	 * When the AI is in the defensive state, it basically just runs away while firing
	 * at the opponent.
	 */
	public void makeDefensiveMove()
	{
		if(currentTarget!=null)
		{
			fireAtTarget();
			moveAwayFromTarget();
		}
	}
	
	/*Attempt Item Pickup
	 * The AI attempts to pick up the closest available item. If the AI is an expert,
	 * then he'll also fire at his opponent while trying to pick up the item.
	 */
	public void attemptItemPickup()
	{
		if(difficulty.equals("Expert"))
		{
			//Fire at opponent while running towards item
			selectCurrentTarget();
			if(currentTarget!=null)
				fireAtTarget();
		}
		moveTowardsItem();
	}
	
	/**Select Current Target
	 *The AI selects the current target. The current target is the opponent on which the AI will focus. It selects a current target by
	 *checking which opponent in the list of opponents is the closest. The opponent which is closest is automatically the current target.
	 */
	public void selectCurrentTarget()
	{
		if(getOpponents().size()>0)	//if there are any opponents
		{
			currentTarget = getOpponents().get(0);	//make first opponent the current target
			//Go through list of opponents and find the opponent closest to the AIEntity
			for(int i = 0; i<getOpponents().size(); i++)
			{
				if(distanceTo(getOpponents().get(i).getSprite())<distanceTo(currentTarget.getSprite()))
				{
					currentTarget = getOpponents().get(i);
				}
			}
			if(currentTarget.alive()==false)	//if current target has died
			{
				//remove that opponent from the list of opponents
				getOpponents().remove(currentTarget);
				currentTarget = null;
			}
		}
	}
	
	/**Distance To
	 *@param The Sprite to which the distance should be measured.
	 *Measures the distance between this AIEntity and the Sprite passed in the parameter.
	 */
	public int distanceTo(Sprite sprite)
	{
		double xDist = this.getSprite().getXCoord()-sprite.getXCoord();
		xDist = Math.abs(xDist);
		double yDist = this.getSprite().getYCoord()-sprite.getYCoord();
		yDist = Math.abs(yDist);
		xDist = xDist*xDist;
		yDist = yDist*yDist;
		double distance = Math.sqrt(xDist+xDist);
		return (int)distance;
	}
	
	/**Fire At Target
	 *Fires at the current target.
	 */
	public void fireAtTarget()
	{
		if(getSpecialWeapons().size()>0)//if AI has special weapons, use them
			setSpecialEquipped(true);
		setFiring(true);
		double yDiff = currentTarget.getSprite().getYCoord()-this.getSprite().getYCoord();
		double xDiff = currentTarget.getSprite().getXCoord()-this.getSprite().getXCoord();
		double quotient = yDiff/xDiff;
		double computeAngle = Math.toDegrees(Math.atan(yDiff/xDiff));
		double newAngle = this.getSprite().getFaceAngle()+computeAngle;
		this.getSprite().setFaceAngle((int)computeAngle);
		if(this.getSprite().getFaceAngle()<360)
		{
			this.getSprite().setFaceAngle(360+(int)computeAngle);			
		}
		else
		{
			this.getSprite().setFaceAngle((int)computeAngle);
		}
		if(xDiff<0)
		{
			this.getSprite().setFaceAngle(this.getSprite().getFaceAngle()-180);
		}
		this.fire();
	}
	
	/**Move Towards Target
	 *Moves towards the current target.
	 */
	public void moveTowardsTarget()
	{
		if(currentTarget.getSprite().getXCoord()>this.getSprite().getXCoord())	//if target is on the right
		{
			//move towards the right
			this.getSprite().setXVeloc(this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		if(currentTarget.getSprite().getXCoord()<this.getSprite().getXCoord())	//if target is on the left
		{
			//move towards the left
			this.getSprite().setXVeloc(-this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		checkForEdges();
	}
	
	/*Move Away From Target
	 * The AI runs away from the target.
	 */
	public void moveAwayFromTarget()
	{
		if(currentTarget.getSprite().getXCoord()>this.getSprite().getXCoord())	//if target is on the right
		{
			//move towards the left
			this.getSprite().setXVeloc(-this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		if(currentTarget.getSprite().getXCoord()<this.getSprite().getXCoord())	//if target is on the left
		{
			//move towards the right
			this.getSprite().setXVeloc(this.getSpeed());
			setAnimating(true);
			setRunning(true);
		}
		checkForEdges();
	}
	
	/**Check For Available Weapons
	 * Goes through the list of level weapons and sees if any of them are available.
	 * If any of them are available, it selects the closest one.
	 */
	public void checkForAvailableWeapons()
	{
		ArrayList<WeaponEntity> availableWeapons = new ArrayList<WeaponEntity>();
		for(WeaponEntity weapon: levelWeapons)
		{
			if(weapon.available())
			{
				availableWeapons.add(weapon);
			}
		}
		findClosestWeapon(availableWeapons);
		if(closestAvailableWeapon!=null)
		{
			//check if closest available weapon has expired -> ie: is no longer available
			if(closestAvailableWeapon.available()==false)
			{
				closestAvailableWeapon = null;
			}
		}
	}
	
	
	public void findClosestWeapon(ArrayList<WeaponEntity> availableWeapons)
	{
		int closestDistance = 500;
		for(WeaponEntity weapon:availableWeapons)
		{
			if(distanceTo(weapon.getSprite())<closestDistance)
			{
				if(this.getSprite().getYCoord()-weapon.getSprite().getYCoord()<200 &&
						this.getSprite().getYCoord()-weapon.getSprite().getYCoord()>=0)
				{
					closestAvailableWeapon = weapon;
				}
			}
		}
	}
	
	/*Move Towards Item
	 * The AI moves towards the closest available item.
	 */
	public void moveTowardsItem()
	{
		if(closestAvailableWeapon!=null)
		{
			if(closestAvailableWeapon.getSprite().getXCoord()>(this.getSprite().getXCoord()+this.getSprite().getWidth()))	//if item is on the right
			{
				//move towards the right
				this.getSprite().setXVeloc(this.getSpeed());
				setAnimating(true);
				setRunning(true);
			}
			else if((closestAvailableWeapon.getSprite().getXCoord()+closestAvailableWeapon.getSprite().getWidth())<this.getSprite().getXCoord())	//if item is on the left
			{
				//move towards the left
				this.getSprite().setXVeloc(-this.getSpeed());
				setAnimating(true);
				setRunning(true);
			}
			//if(closestAvailableWeapon.getSprite().getXCoord()==this.getSprite().getXCoord())
			else
			{
				//check if item is above player and check if it's within reach
				if(this.getSprite().getYCoord()-closestAvailableWeapon.getSprite().getYCoord()<200 &&
						this.getSprite().getYCoord()-closestAvailableWeapon.getSprite().getYCoord()>=0)
				{
					//jump up and grab the item if it's within reach (and above player)
					getSprite().setYVeloc(-getJumpSpeed());
		 			this.setInAir(true);
		 			setAnimating(true);
					setRunning(false);
				}
				else	//item is not above (ie: below) player or it's out of player's reach
				{
					closestAvailableWeapon = null;	//ignore item
				}
			}
			checkForEdges();
		}
	}
	
	/** Check For Edges
	 *Checks whether the AI is near an edge of a platform. If the AI is near an edge, the AI jumps. The AI keeps track of the
	 *platform's edges on which it's currently standing. That means that the AI only keeps track of 1 platform at a time, the one
	 *it's currently standing on. It checks whether it's approaching the edges of that platform and jumps if it gets too close
	 *to the edge (while running).
	 */
	public void checkForEdges()
	{
		if(getCurrentPlatform()!=null)
		{	
			if(running() && !inAir())
			{
				boolean nearEdge = false;
				//Check distance to left edge of platform
				int distanceToEdge = (int)(this.getSprite().getXCoord()-getCurrentPlatform().getSprite().getXCoord());
				distanceToEdge = Math.abs(distanceToEdge);
				if(distanceToEdge<=50)	//if AI is 50 or less pixels away from the edge, and the AI is running, jump
				{
					nearEdge = true;
				}
				//Check distance to right edge of platform
				distanceToEdge = (int)((this.getSprite().getXCoord()+this.getSprite().getWidth())-
						(getCurrentPlatform().getSprite().getXCoord()+getCurrentPlatform().getSprite().getWidth()));
				distanceToEdge = Math.abs(distanceToEdge);
				if(distanceToEdge<=50)	//if AI is 50 or less pixels away from the edge, and the AI is running, jump
				{
					nearEdge = true;
				}
				if(nearEdge){
					getSprite().setYVeloc(-getJumpSpeed());
		 			this.setInAir(true);
		 			setAnimating(true);
					setRunning(false);
				}
			}
		}
	}
	
	//Necessary to execute cutscene commands
	/**
	 * Execute Command
	 * @param The command to be executed by this player.
	 * Executes the command passed in the parameter.
	 */
	public void executeCommand(Command command)
	{
		String playerName = command.getNameOrType();
	 	String action = command.getAction();
	 	int duration = command.getDuration();
	 	int absDuration = Math.abs(duration);	//absolute value of the duration
	 	if(playerName.equals("Players")||playerName.equals(this.getName()))
	 	{
	 		if(getElapsedExecutionTime()<absDuration)	//if current command is not finished executing
	 		{
	 			//execute commands
	 			if(action.equals("MoveX"))
	 			{
	 				if(duration>0)
	 				{
	 					getSprite().setXVeloc(getSpeed());
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 					setAnimating(true);
						setRunning(true);
	 				}
	 				if(duration<0)
	 				{
	 					getSprite().setXVeloc(-getSpeed());
	 					setElapsedExecutionTime(getElapsedExecutionTime()+5);
	 					setAnimating(true);
						setRunning(true);
	 				}
	 				setFiring(false);
	 			}
	 			if(action.equals("MoveY"))
	 			{
	 				getSprite().setYVeloc(-getJumpSpeed());
	 				this.setInAir(true);
	 				setElapsedExecutionTime(absDuration);
	 				setAnimating(true);
					setRunning(false);
					setFiring(false);
	 			}
	 			if(action.equals("Fire"))
	 			{
	 				if(duration>0)
	 				{
	 					getSprite().setDirection(1);
	 					getSprite().setFaceAngle(0);
	 				}
	 				if(duration<0)
	 				{
	 					getSprite().setDirection(-1);
	 					getSprite().setFaceAngle(180);
	 				}
					setFiring(true);	//let player entity know ai is firing
	 				setElapsedExecutionTime(getElapsedExecutionTime()+1);
	 			}
	 		}
	 		else if(getElapsedExecutionTime()>=absDuration)	//if current command is finished executing
	 		{
	 			setReadyForCommand(true);
	 			this.getSprite().setMovingHorizontally(false);
	 			this.getSprite().setMovingVertically(false);
	 			setFiring(false);
	 			setRunning(false);
	 		}
	 	}
	}
	
	//Various other AI functions...
	
	//For testing and debugging
	/*public String toString()
	{
		String s = ("xCoord: " + getSprite().getXCoord() + "\n" +
			"yCoord: " + getSprite().getYCoord() + "\n" +
			"width: " + getSprite().getWidth() + "\n" +
			"height: " + getSprite().getHeight() + "\n" +
			"Name: " + this.getName() + "\n" +
			"Lives: " + this.getLives() + "\n" +
			"Health: " + this.getHealth() + "\n" +
			"Speed: " + this.getSpeed() + "\n" +
			"JumpSpeed: " + this.getJumpSpeed() + "\n" +
			"Firing Rate: " + this.getFiringRate() + "\n" +
			"Special: " + this.getSpecial() + "\n" +
			"Score Value: " + scoreValue);
		return s;
	}*/
	
	public void collisionEffect(WeaponEntity weapon)
	{
		super.collisionEffect(weapon);
		closestAvailableWeapon = null;
	}
}
