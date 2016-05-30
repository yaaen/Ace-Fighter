/**
 * @(#)AceFighter1_0 -> Sprite Engine -> BossSprite class
 *
 * This class is used to animate the Boss. It contains additional constants
 * that specify start frames for the different boss components.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
class BossSprite extends PlayerSprite
{
	//Private variables
	//Static animation variables - constants
	//Cockpit animation variables
	private static final int COCKPIT_RIGHT_START_FRAME = 0;	//where frame of animation (or animation strip) starts when cockpit is moving to the right
	private static final int COCKPIT_LEFT_START_FRAME = 4;	//where frame of animation starts when cockpit is moving to the left
	private static final int COCKPIT_UP_START_FRAME = 8;	//where frame of animation starts when cockpit is moving up
	private static final int COCKPIT_DAMAGE_INCREMENT = 12;	//the number of frames the current frame needs to increase by in order to
												//show that it's been hit. For example, if the cock pit is hit while moving up,
												//it needs to flash a frame of it being hit while moving up (maybe this frame is
												//exactly the same as the non-hit frame except that its color is different). In order
												//to show or flash this hit-frame, the current frame is simply increased by the
												//cockpitDamageIncrement.	
	
	//Turret animation variables
	private static final int TURRET_RIGHT_START_FRAME = 0;	//where frame of animation starts when turret moves to the right
	private static final int TURRET_LEFT_START_FRAME = 4;	//where frame of animation starts when turrent moves to the left
	private static final int TURRET_DAMAGE_INCREMENT = 8;	
	
	//Shield animation variables
	private static final int SHIELD_RIGHT_START_FRAME = 0;	//where animation strip starts when shield is moving to the right
	private static final int SHIELD_LEFT_START_FRAME = 4;
	private static final int SHIELD_DAMAGE_INCREMENT = 8;
	
	//Additional variables needed for animation
	//Since parts of the boss don't always animate (for example, a turret only animates when it's firing), these states
	//are needed to determine when to animate the different boss components
	private boolean animateCockpit;	//true if the cockpit should be animated
	private boolean animateTurret;	//true if the turret should be animated
	private boolean animateShield;	//true if the shield should be animated
	private boolean faceRight;	//If the respective BossComponent is facing to the right
	private boolean faceLeft;	//If the respective BossComponent is facing to the left
	private boolean faceUp;		//If the respective BossComponent is facing up
	
	private String bossComponentType;
	private boolean animating;
	
	public BossSprite(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		animateCockpit = false;
		animateTurret = false;
		animateShield = false;
		faceRight = false;
		faceLeft = false;
		faceUp = false;
		bossComponentType = "";
		animating = false;
	}
	
	//Accessor Methods
	public String getBossComponentType()
	{
		return bossComponentType;
	}
	
	public boolean animating()
	{
		return animating;
	}
	
	//Mutator Methods
	public void setBossComponentType(String bossComponentType)
	{
		this.bossComponentType = bossComponentType;
	}
	
	public void setAnimating(boolean animating)
	{
		this.animating = animating;
	}
	
	//Animation Methods
	public void animate()
	{
		if(bossComponentType.equals("Cockpit"))
			updateCockpitAnimation();
		if(bossComponentType.equals("Turret"))
			updateTurretAnimation();
		if(bossComponentType.equals("Shield"))
			updateShieldAnimation();		
	}
	
	//Animates the Cockpit
	public void updateCockpitAnimation()
	{
		if(!afterCollision())
		{
			if(getDirection()>0)	//if facing or running to the right
			{
				if(!movingHorizontally()&&!movingVertically())	//if player is not running and not jumping, reset the current frame to the first frame
				{
					setStartFrame(COCKPIT_RIGHT_START_FRAME);
					setCurrentFrame(COCKPIT_RIGHT_START_FRAME);
				}
				if(!movingHorizontally()&&!movingVertically()&&colliding())
				{
					setRollbackFrame(getCurrentFrame());	
					setCurrentFrame(COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT);
					setColliding(false);
					setCollisionPause(true);
				}
				if(movingHorizontally()&&!colliding())	//if boss component is moving to the right
				{
					//loop proper animation
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(COCKPIT_RIGHT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(COCKPIT_RIGHT_START_FRAME);
					}
					else if(getCurrentFrame()<COCKPIT_RIGHT_START_FRAME)
					{
						setCurrentFrame((COCKPIT_RIGHT_START_FRAME+getStripSize()-1));
					}
				}
				if(movingHorizontally()&&colliding())
				{
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+COCKPIT_DAMAGE_INCREMENT);
					}
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1))
					{
						setCurrentFrame((COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT));
					}
					else if(getCurrentFrame()<(COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
					{
						setCurrentFrame((COCKPIT_RIGHT_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1));
					}
					setColliding(false);
					setCollisionPause(true);
				}
			}
			if(getDirection()<0)	//if facing or running to the left
			{
				if(!movingHorizontally()&&!movingVertically())	//if player is not running and not jumping, reset the current frame to the first frame
				{
					setStartFrame(COCKPIT_LEFT_START_FRAME);
					setCurrentFrame(COCKPIT_LEFT_START_FRAME);
				}
				if(!movingHorizontally()&&!movingVertically()&&colliding())
				{
					setRollbackFrame(getCurrentFrame());
					setCurrentFrame((COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT));
					setColliding(false);
					setCollisionPause(true);
				}
				if(movingHorizontally()&&!colliding())	//if boss component is moving to the right
				{
					if(getCurrentFrame()<COCKPIT_LEFT_START_FRAME)
					{
						setMovingHorizontally(false);
						setCurrentFrame(COCKPIT_LEFT_START_FRAME);
					}
					else
					{
						setCurrentFrame(getCurrentFrame()+1);
						if(getCurrentFrame()>(COCKPIT_LEFT_START_FRAME+getStripSize()-1))
						{
							setCurrentFrame(COCKPIT_LEFT_START_FRAME);
						}
						else if(getCurrentFrame()<COCKPIT_LEFT_START_FRAME)
						{
							setCurrentFrame((COCKPIT_LEFT_START_FRAME+getStripSize()-1));
						}
					}	
				}
				if(movingHorizontally()&&colliding())
				{
					if(getCurrentFrame()<COCKPIT_LEFT_START_FRAME)
					{
						setCurrentFrame(COCKPIT_LEFT_START_FRAME);
					}
					else
					{
						setRollbackFrame(getCurrentFrame());
						if(getCurrentFrame()<(COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
						{
							setCurrentFrame(getCurrentFrame()+COCKPIT_DAMAGE_INCREMENT);
						}
						setCurrentFrame(getCurrentFrame()+1);
						if(getCurrentFrame()>(COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1))
						{
							setCurrentFrame(COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT);
						}
						else if(getCurrentFrame()<(COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
						{
							setCurrentFrame(COCKPIT_LEFT_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1);
						}
						setColliding(false);
						setCollisionPause(true);
					}
				}
			}
			if(movingVertically()&&!colliding())
			{
				if(getCurrentFrame()<COCKPIT_UP_START_FRAME)
				{
					setMovingVertically(false);
					setCurrentFrame(COCKPIT_UP_START_FRAME);
				}
				else
				{
					//loop proper animation
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(COCKPIT_UP_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(COCKPIT_UP_START_FRAME);
					}
					else if(getCurrentFrame()<COCKPIT_UP_START_FRAME)
					{
						setCurrentFrame(COCKPIT_UP_START_FRAME+getStripSize()-1);
					}
				}
			}	
			if(movingVertically()&&colliding())
			{
				if(getCurrentFrame()<COCKPIT_UP_START_FRAME)
				{
					setMovingVertically(false);
					setCurrentFrame(COCKPIT_UP_START_FRAME);
				}
				else
				{
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(COCKPIT_UP_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+COCKPIT_DAMAGE_INCREMENT);
					}
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(COCKPIT_UP_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1))
					{
						setCurrentFrame((COCKPIT_UP_START_FRAME+COCKPIT_DAMAGE_INCREMENT));
					}
					else if(getCurrentFrame()<(COCKPIT_UP_START_FRAME+COCKPIT_DAMAGE_INCREMENT))
					{
						setCurrentFrame((COCKPIT_UP_START_FRAME+COCKPIT_DAMAGE_INCREMENT+getStripSize()-1));
					}
					setColliding(false);
					setCollisionPause(true);
				}
			}
		}
	}
	
	//Animates the Turret.
	public void updateTurretAnimation()
	{
		if(!afterCollision())
		{
			if(getDirection()>0)	//if turret is facing to the right
			{
				if(!firing()&&!colliding())
				{
					setCurrentFrame(TURRET_RIGHT_START_FRAME);
				}
				if(!firing()&&colliding())
				{
					if(getCurrentFrame()>(TURRET_RIGHT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(TURRET_RIGHT_START_FRAME);
					}
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+TURRET_DAMAGE_INCREMENT);
					}
					setColliding(false);
					setCollisionPause(true);
				}
				if(firing()&&!colliding())
				{
					//loop proper animation
					//The Turret animates as follows: When it fires, it loops through its firing animation once.
					//Once it reaches the end of the firing animation cycle, it sets firing to false, causing the
					//animation to stop. Thus when one projectile is fired, it goes through the entire firing animation
					//and stops once it reaches the end. This makes it so that the firing animation is not cut off
					//when the boss stops firing in the middle of the animation.
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(TURRET_RIGHT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(TURRET_RIGHT_START_FRAME);
						setFiring(false);
					}
					else if(getCurrentFrame()<TURRET_RIGHT_START_FRAME)
					{
						setCurrentFrame((TURRET_RIGHT_START_FRAME+getStripSize()-1));
					}	
				}
				if(firing()&&colliding())
				{
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+TURRET_DAMAGE_INCREMENT);
					}
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT+getStripSize()-1))
					{
						setCurrentFrame((TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT));
						setFiring(false);
					}
					else if(getCurrentFrame()<(TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT))
					{
						setCurrentFrame((TURRET_RIGHT_START_FRAME+TURRET_DAMAGE_INCREMENT+getStripSize()-1));
					}
					setColliding(false);
					setCollisionPause(true);
				}
			}
			if(getDirection()<0)	//if turret is facing to the left
			{
				if(!firing()&&!colliding())
				{
					setCurrentFrame(TURRET_LEFT_START_FRAME);
				}
				if(!firing()&&colliding())
				{
					if(getCurrentFrame()<TURRET_LEFT_START_FRAME)
					{
						setCurrentFrame(TURRET_LEFT_START_FRAME);
					}
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+TURRET_DAMAGE_INCREMENT);
					}
					setColliding(false);
					setCollisionPause(true);
				}
				if(firing()&&!colliding())
				{
					//loop proper animation
					//The Turret animates as follows: When it fires, it loops through its firing animation once.
					//Once it reaches the end of the firing animation cycle, it sets firing to false, causing the
					//animation to stop. Thus when one projectile is fired, it goes through the entire firing animation
					//and stops once it reaches the end. This makes it so that the firing animation is not cut off
					//when the boss stops firing in the middle of the animation.
					if(getCurrentFrame()<TURRET_LEFT_START_FRAME)	//if turret was just firing tot he right and switched in the middle of firing
					{
						//stop the firing and make turret face left
						setFiring(false);
						setCurrentFrame(TURRET_LEFT_START_FRAME);
					}
					else
					{
						setCurrentFrame(getCurrentFrame()+1);
						if(getCurrentFrame()>(TURRET_LEFT_START_FRAME+getStripSize()-1))
						{
							setCurrentFrame(TURRET_LEFT_START_FRAME);
							setFiring(false);
						}	
						else if(getCurrentFrame()<TURRET_LEFT_START_FRAME)
						{
							setCurrentFrame((TURRET_LEFT_START_FRAME+getStripSize()-1));
						}
					}
				}
				if(firing()&&colliding())
				{
					if(getCurrentFrame()<TURRET_LEFT_START_FRAME)
					{
						//stop the firing and make turret face left
						setFiring(false);
						setCurrentFrame(TURRET_LEFT_START_FRAME);
					}
					else
					{
						setRollbackFrame(getCurrentFrame());
						if(getCurrentFrame()<(TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT))
						{
							setCurrentFrame(getCurrentFrame()+TURRET_DAMAGE_INCREMENT);
						}
						setCurrentFrame(getCurrentFrame()+1);
						if(getCurrentFrame()>(TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT+getStripSize()-1))
						{
							setCurrentFrame((TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT));
							setFiring(false);
						}
						else if(getCurrentFrame()<(TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT))
						{
							setCurrentFrame((TURRET_LEFT_START_FRAME+TURRET_DAMAGE_INCREMENT+getStripSize()-1));
						}
						setColliding(false);
						setCollisionPause(true);
					}
				}
			}
		}
	}
	
	//Animates the Shield.
	public void updateShieldAnimation()
	{
		if(!afterCollision())
		{
			if(getDirection()>0)	//if facing to the right
			{
				if(!colliding())
				{
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(SHIELD_RIGHT_START_FRAME+getStripSize()-1))
					{
						setCurrentFrame(SHIELD_RIGHT_START_FRAME);
					}
					else if(getCurrentFrame()<SHIELD_RIGHT_START_FRAME)
					{
						setCurrentFrame((SHIELD_RIGHT_START_FRAME+getStripSize()-1));
					}
				}
				if(colliding())
				{
					setRollbackFrame(getCurrentFrame());
					if(getCurrentFrame()<(SHIELD_RIGHT_START_FRAME+SHIELD_DAMAGE_INCREMENT))
					{
						setCurrentFrame(getCurrentFrame()+SHIELD_DAMAGE_INCREMENT);
					}
					setCurrentFrame(getCurrentFrame()+1);
					if(getCurrentFrame()>(SHIELD_RIGHT_START_FRAME+SHIELD_DAMAGE_INCREMENT+getStripSize()-1))
					{
						setCurrentFrame((SHIELD_RIGHT_START_FRAME+SHIELD_DAMAGE_INCREMENT));
					}
					else if(getCurrentFrame()<(SHIELD_RIGHT_START_FRAME+SHIELD_DAMAGE_INCREMENT))
					{
						setCurrentFrame((SHIELD_RIGHT_START_FRAME+SHIELD_DAMAGE_INCREMENT+getStripSize()-1));
					}
					setColliding(false);
					setCollisionPause(true);
				}
			}
			if(getDirection()<0)	//if facing to the left
			{
				if(!colliding())
				{
					if(getCurrentFrame()<SHIELD_LEFT_START_FRAME)
					{
						setCurrentFrame(SHIELD_LEFT_START_FRAME);
					}
					else
					{
						setCurrentFrame(getCurrentFrame()+1);
						if(getCurrentFrame()>(SHIELD_LEFT_START_FRAME+getStripSize()-1))
						{
							setCurrentFrame(SHIELD_LEFT_START_FRAME);
						}
						else if(getCurrentFrame()<SHIELD_LEFT_START_FRAME)
						{
							setCurrentFrame((SHIELD_LEFT_START_FRAME+getStripSize()-1));
						}
					}
				}
				if(colliding())
				{
					if(getCurrentFrame()<SHIELD_LEFT_START_FRAME)
					{
						setCurrentFrame(SHIELD_LEFT_START_FRAME);
					}
					else
					{
						setRollbackFrame(getCurrentFrame());
						if(getCurrentFrame()<(SHIELD_LEFT_START_FRAME+SHIELD_DAMAGE_INCREMENT))
						{
							setCurrentFrame(getCurrentFrame()+SHIELD_DAMAGE_INCREMENT);
						}
						setCurrentFrame(getCurrentFrame()+1);	
						if(getCurrentFrame()>(SHIELD_LEFT_START_FRAME+SHIELD_DAMAGE_INCREMENT+getStripSize()-1))
						{
							setCurrentFrame((SHIELD_LEFT_START_FRAME+SHIELD_DAMAGE_INCREMENT));
						}
						else if(getCurrentFrame()<(SHIELD_LEFT_START_FRAME+SHIELD_DAMAGE_INCREMENT))
						{
							setCurrentFrame(SHIELD_LEFT_START_FRAME+SHIELD_DAMAGE_INCREMENT+getStripSize()-1);
						}
						setColliding(false);
						setCollisionPause(true);
					}
				}		
			}
		}
	}
	
	/**
	 * This method is used to create the collision animation in the Boss sprite. When a Boss Component gets hit by a projectile,
	 * this method gets called to create the Collision animation.
	 */
	public void rollbackFrame()
	{
		if(bossComponentType.equals("Cockpit"))
		{
			if(getCurrentFrame()>=COCKPIT_DAMAGE_INCREMENT)
			{
				setCurrentFrame(getCurrentFrame()-COCKPIT_DAMAGE_INCREMENT);
			}	
		}
		if(bossComponentType.equals("Turret"))
		{
			if(getCurrentFrame()>=TURRET_DAMAGE_INCREMENT)
			{
				setCurrentFrame(getCurrentFrame()-TURRET_DAMAGE_INCREMENT);
			}
		}
		if(bossComponentType.equals("Shield"))
		{
			if(getCurrentFrame()>=SHIELD_DAMAGE_INCREMENT)
			{
				setCurrentFrame(getCurrentFrame()-SHIELD_DAMAGE_INCREMENT);
			}
		}
		setAfterCollision(false);
	}
}
