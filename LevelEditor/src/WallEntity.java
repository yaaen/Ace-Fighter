/**
 * @(#)AceFighter1_1 -> Platform Engine -> WallEntity class
 *
 * This class is used to create Walls in Ace Fighter. Since walls have different
 * collision properties than the other platforms, this class will handle collisions 
 * slightly differently than the regular Platform classes.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
 
public class WallEntity extends PlatformEntity
{
	public WallEntity(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
	}
	
	//Collision Effect methods. This is where the WallEntity differs from the rest of the
	//Platforms.
	public void collisionEffect(ProjectileEntity projectile)
	{
	}
	
	public void collisionEffect(PlayerEntity player)
	{
		if(this.getProperty().equals("None")==false)	//if the wall has a property, create collision effect according to the property
		{
			super.collisionEffect(player);
		}
		else
		{
			if(player.getSprite().getYCoord()+player.getSprite().getHeight()-10<this.getSprite().getYCoord())	//if player is falling onto wall from above
			{
				super.collisionEffect(player);	//treat it like player is colliding with a platform
			}
			else if(player.getSprite().getYCoord()>this.getSprite().getYCoord()+this.getSprite().getHeight()-10)	//if player collides with bottom of wall
			{
				player.getSprite().setYVeloc(-player.getSprite().getYVeloc());
			}
			else
			{
				if(this.getSprite().getXVeloc()>0 ||
						this.getSprite().getXVeloc()<0)	//if the wall is moving sideways
				{
					if((player.getSprite().getXCoord()<this.getSprite().getXCoord())&&
						(player.getSprite().getXCoord()+player.getSprite().getWidth()>=this.getSprite().getXCoord()))	//if player is on the left side
					{
						player.getSprite().setXCoord(player.getSprite().getXCoord()-50);
						player.getSprite().setRelationalXDifference(-50);
						player.setCollidingWithWall(true);
					}
					if((this.getSprite().getXCoord()<player.getSprite().getXCoord())&&
						(this.getSprite().getXCoord()+this.getSprite().getWidth()>=player.getSprite().getXCoord()))	//if player is on the right side
					{
						player.getSprite().setXCoord(player.getSprite().getXCoord()+50);
						player.getSprite().setRelationalXDifference(50);
						player.setCollidingWithWall(true);
					}
				}
				else	//if the wall is not moving
				{
					if(player.getSprite().getXVeloc()>0 &&
							(player.getSprite().getXCoord()<this.getSprite().getXCoord())&&
							(player.getSprite().getXCoord()+player.getSprite().getWidth()>=this.getSprite().getXCoord()))	//if player is moving to the right -> coming from the left
					{
						player.getSprite().setXCoord(player.getSprite().getXCoord()-50);
						player.getSprite().setRelationalXDifference(-50);
						player.setCollidingWithWall(true);
					}
					if(player.getSprite().getXVeloc()<0 &&
							(this.getSprite().getXCoord()<player.getSprite().getXCoord())&&
							(this.getSprite().getXCoord()+this.getSprite().getWidth()>=player.getSprite().getXCoord()))	//if player is coming from the right
					{
						player.getSprite().setXCoord(player.getSprite().getXCoord()+50);
						player.getSprite().setRelationalXDifference(50);
						player.setCollidingWithWall(true);
					}
				}
				if(player.inAir())
					player.getSprite().setXVeloc(-0.2*player.getSprite().getXVeloc());
			}
		}
	}
	
	public void collisionEffect(ItemEntity item)
	{
	}
}
