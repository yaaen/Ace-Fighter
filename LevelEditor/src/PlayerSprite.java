/**
 * @(#)AceFighter1_0 -> Sprite Engine -> PlayerSprite class
 *
 * This class is used to provide animation for players. The difference between 
 * this class and the AnimatedSprite class is that this class can support
 * animation that's controlled by the players. That means, when players jump,
 * a jumping animation needs to be played, when players run to the right, a 
 * running animation towards the right needs to be displayed etc...
 * To support these added features, this class contains a few additional constants
 * that hold the indices of the different start frames.
 *
 * For example:
 *		1) RUN_LEFT_START_FRAME holds frame index (frame number) of starting frame of
 *		animation strip that animates player running to the left.
 *
 * Since this class contains constants that are hard coded, the entities that use
 * this class will have to provide images that conform to the standard defined by this
 * class. That means, images used by this class will have to have strip sizes equal
 * to the strip sizes defined by this class etc...
 *
 * This class currently contains fields to support collision animation. Collision animation will
 * create an animation effect when a PlayerSprite collides with something. That means, if a player
 * gets hit by a projectile, it will display the collision using this PlayerSprite class. The collision
 * animations will make hits visible and create the kind of effect that occurred when you hit the boss (eggman)
 * in sonic one.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
 
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;

class PlayerSprite extends AnimatedSprite
{
	//Private variables
	//Static animation variables - constants
	private static final int RUN_RIGHT_START_FRAME = 0;	//start frame for running-right animation strip
	private static final int RIGHT_FIRE_START_FRAME = 4;	//start frame for firing to the right animation strip
	private static final int RUN_LEFT_START_FRAME = 8;	//start frame for running-left animation strip
	private static final int LEFT_FIRE_START_FRAME = 12;	//start frame for firing to the left animation strip
	private static final int NUM_COLS = 4;	//the number of frames on one row in the animation strip
	private static final int STRIP_SIZE = 4;	//the number of frames in an animation strip
	private static final int FIRING_FRAME_DIFFERENCE = 4;	//If player is running, for example, and then starts firing (while still
															//running), the current frame number needs to be increased by this difference.
	private static final int COLLISION_FRAME_DIFFERENCE = 16;	//the amount of frames that currentFrame should jump to get to collision animation
																//frames
	//Additional variables necessary for collision animation
	private int rollbackFrame;		//the rollback frame is the frame of animation that the sprite rolls back to after it's done
									//colliding. It is used so that once the collision is over, it can display the frame of animation
									//that was displayed before the collision occurred.
	private int direction;			//if it's positive, run right. If it's negative, run left.
									//Also used to determine in which direction the player is facing
									//Even if the player stands still, if the direction is positive (ie: 1),
									//the player faces to the right
	
	//Booleans necessary for player animation
	private boolean firing;					//true if the player is firing
	private boolean movingHorizontally;		//true if the player is running
	private boolean movingVertically;		//true if player is jumping
	private boolean colliding;
	private boolean collisionPause;			//the collisionPause is there to create a pause between when the collision occurred and
											//when the collision ends. This way, the engine has enough time to display the collision
											//frame (ie: One gameloop iteration or so).
	private boolean afterCollision;			//after collision is true right after the collision has occurred.
											//it is used to roll back to the frame of animation that was in place before
											//the respective sprite collided.
	private int collisionCounter;	//keeps track of how long the collision animation should be displayed
	private static final int COLLISION_TIME = 5;	//specifies the amount of time the collision animation is displayed
													//makes sure that the collision animation frames are displayed for a
													//a couple of game loop iterations so that it's actually visible that
													//a player got hit. Collision animation only happens when a player collides
													//with a projectile. It doesn't occur when a player collides with a platform.
	
	
									
	public PlayerSprite(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		setStartFrame(0);
		setCurrentFrame(0);
		setNumCols(NUM_COLS);
		setStripSize(STRIP_SIZE);
		rollbackFrame = 0;
		direction = 1;
		firing = false;
		movingHorizontally = false;
		movingVertically = false;
		colliding = false;
		collisionPause = false;
		afterCollision = false;
		collisionCounter = 0;
	}
	
	//Accessor Methods
	public int getRollbackFrame()
	{
		return rollbackFrame;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public boolean firing()
	{
		return firing;
	}
	
	public boolean movingHorizontally()
	{
		return movingHorizontally;
	}
	
	public boolean movingVertically()
	{
		return movingVertically;
	}
	
	public boolean colliding()
	{
		return colliding;
	}
	
	public boolean collisionPause()
	{
		return collisionPause;
	}
	
	public boolean afterCollision()
	{
		return afterCollision;
	}
	
	//Mutator Methods
	public void setRollbackFrame(int rollbackFrame)
	{
		this.rollbackFrame = rollbackFrame;
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	public void setFiring(boolean firing)
	{
		this.firing = firing;
	}
	
	public void setMovingHorizontally(boolean movingHorizontally)
	{
		this.movingHorizontally = movingHorizontally;
	}
	
	public void setMovingVertically(boolean movingVertically)
	{
		this.movingVertically = movingVertically;
	}
	
	public void setColliding(boolean colliding)
	{
		this.colliding = colliding;
	}
	
	public void setCollisionPause(boolean collisionPause)
	{
		this.collisionPause = collisionPause;
	}
	
	public void setAfterCollision(boolean afterCollision)
	{
		this.afterCollision = afterCollision;
	}
	
	/**
	 * Performs player animation based on state of player (running, firing, facing left, facing right, etc...)
	 */
	public void animate()
	{
		if(colliding)
		{
			if(collisionCounter>=COLLISION_TIME)	//stop animating collision
			{
				colliding = false;
				collisionCounter = 0;
				setCurrentFrame(getCurrentFrame()-COLLISION_FRAME_DIFFERENCE);
			}
			else
			{
				collisionCounter++;
				animateCollision();
			}
		}
		if(!colliding)
		{
			if(direction>0)	//if facing or running to the right
			{
				if(!movingHorizontally&&!movingVertically)	//if player is not running and not jumping, reset the current frame to the first frame
				{
					setStartFrame(RUN_RIGHT_START_FRAME);
					setCurrentFrame(RUN_RIGHT_START_FRAME);
				}
				if(firing && movingHorizontally)	//if player is firing and running at the same time, play fire-while-running animation strip
				{
					if(getCurrentFrame()<RIGHT_FIRE_START_FRAME)				
					{
						setCurrentFrame(getCurrentFrame()+FIRING_FRAME_DIFFERENCE);
					}
					
					//animate manually
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(RIGHT_FIRE_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(RIGHT_FIRE_START_FRAME);
					}
					else if(getCurrentFrame()<RIGHT_FIRE_START_FRAME)
					{
						setCurrentFrame((RIGHT_FIRE_START_FRAME+getStripSize()-1));
					}
				}
				if(firing&&!movingHorizontally)	//if firing and not running, display the the firing frame
				{
					setCurrentFrame(RIGHT_FIRE_START_FRAME);
				}
				if(movingHorizontally && !firing)	//if player is only running and not firing, play the running-only animation strip
				{
					//animate manually
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(RUN_RIGHT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(RUN_RIGHT_START_FRAME);
					}
					else if(getCurrentFrame()<RUN_RIGHT_START_FRAME)
					{
						setCurrentFrame((RUN_RIGHT_START_FRAME+getStripSize()-1));
					}
				}
				if(movingVertically&&firing)	//if jumping and firing simultaneously, display fire-while-jumping frame
				{
					setCurrentFrame(RIGHT_FIRE_START_FRAME+1);
				}
				if(movingVertically&&!firing)	//if jumping and not firing, display jumping-only frame
				{
					setCurrentFrame(RUN_RIGHT_START_FRAME+1);
				}
			}
			if(direction<0)	//if facing or running to the left
			{
				if(!movingHorizontally&&!movingVertically)
				{
					setStartFrame(RUN_LEFT_START_FRAME+1);
					setCurrentFrame(RUN_LEFT_START_FRAME+1);
				}
				if(firing&&movingHorizontally)
				{
					if(getCurrentFrame()<LEFT_FIRE_START_FRAME)
					{
						setCurrentFrame(getCurrentFrame()+FIRING_FRAME_DIFFERENCE);
					}
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(LEFT_FIRE_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(LEFT_FIRE_START_FRAME);
					}
					else if(getCurrentFrame()<LEFT_FIRE_START_FRAME)
					{
						setCurrentFrame((LEFT_FIRE_START_FRAME+getStripSize()-1));
					}
				}
				if(firing&&!movingHorizontally)
				{
					setCurrentFrame(LEFT_FIRE_START_FRAME+1);
				}
				if(movingHorizontally&&!firing)
				{
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(RUN_LEFT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(RUN_LEFT_START_FRAME);
					}
					else if(getCurrentFrame()<RUN_LEFT_START_FRAME)
					{
						setCurrentFrame(RUN_LEFT_START_FRAME+getStripSize()-1);
					}
				}
				if(movingVertically&&firing)
				{
					setCurrentFrame(LEFT_FIRE_START_FRAME);
				}
				if(movingVertically&&!firing)
				{
					setCurrentFrame(RUN_LEFT_START_FRAME);
				}
			}
		}
	}
	
	/*Animate Collision
	 * Animates a collision by doing the same thing as the regular animate() method except
	 * that all the frames are offset by the amount defined in COLLISION_FRAME_DIFFERENCE.
	 */
	public void animateCollision()
	{
		if(direction>0)	//if facing or running to the right
		{
			if(!movingHorizontally&&!movingVertically)	//if player is not running and not jumping, reset the current frame to the first frame
			{
				setStartFrame(RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE);
				setCurrentFrame(RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE);
			}
			if(firing && movingHorizontally)	//if player is firing and running at the same time, play fire-while-running animation strip
			{
				if(getCurrentFrame()<RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE)				
				{
					setCurrentFrame(getCurrentFrame()+FIRING_FRAME_DIFFERENCE+COLLISION_FRAME_DIFFERENCE);
				}
				
				//animate manually
				setCurrentFrame(getCurrentFrame()+1);
				if(getCurrentFrame()>(RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1))
				{
					setCurrentFrame(RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE);
				}
				else if(getCurrentFrame()<RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE)
				{
					setCurrentFrame((RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1));
				}
			}
			if(firing&&!movingHorizontally)	//if firing and not running, display the the firing frame
			{
				setCurrentFrame(RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE);
			}
			if(movingHorizontally && !firing)	//if player is only running and not firing, play the running-only animation strip
			{
				//animate manually
				setCurrentFrame(getCurrentFrame()+1);
				if(getCurrentFrame()>(RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1))
				{
					setCurrentFrame(RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE);
				}
				else if(getCurrentFrame()<RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE)
				{
					setCurrentFrame((RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1));
				}
			}
			if(movingVertically&&firing)	//if jumping and firing simultaneously, display fire-while-jumping frame
			{
				setCurrentFrame(RIGHT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+1);
			}
			if(movingVertically&&!firing)	//if jumping and not firing, display jumping-only frame
			{
				setCurrentFrame(RUN_RIGHT_START_FRAME+COLLISION_FRAME_DIFFERENCE+1);
			}
		}
		if(direction<0)	//if facing or running to the left
		{
			if(!movingHorizontally&&!movingVertically)
			{
				setStartFrame(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE+1);
				setCurrentFrame(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE+1);
			}
			if(firing&&movingHorizontally)
			{
				if(getCurrentFrame()<LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE)
				{
					setCurrentFrame(getCurrentFrame()+FIRING_FRAME_DIFFERENCE+COLLISION_FRAME_DIFFERENCE);
				}
				setCurrentFrame(getCurrentFrame()+COLLISION_FRAME_DIFFERENCE+1);
				if(getCurrentFrame()>(LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1))
				{
					setCurrentFrame(LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE);
				}
				else if(getCurrentFrame()<LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE)
				{
					setCurrentFrame((LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1));
				}
			}
			if(firing&&!movingHorizontally)
			{
				setCurrentFrame(LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE+1);
			}
			if(movingHorizontally&&!firing)
			{
				setCurrentFrame(getCurrentFrame()+1);
				if(getCurrentFrame()>(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1))
				{
					setCurrentFrame(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE);
				}
				else if(getCurrentFrame()<RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE)
				{
					setCurrentFrame(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE+getStripSize()-1);
				}
			}
			if(movingVertically&&firing)
			{
				setCurrentFrame(LEFT_FIRE_START_FRAME+COLLISION_FRAME_DIFFERENCE);
			}
			if(movingVertically&&!firing)
			{
				setCurrentFrame(RUN_LEFT_START_FRAME+COLLISION_FRAME_DIFFERENCE);
			}
		}
	}
	
	/*Collide
	 * Lets PlayerSprite know that it should now play the collision animation.
	 */
	public void collide()
	{
		if(!colliding) //if not already colliding
		{
			colliding = true;
			collisionCounter = 0;
			setCurrentFrame(getCurrentFrame()+COLLISION_FRAME_DIFFERENCE);
		}
	}
}
