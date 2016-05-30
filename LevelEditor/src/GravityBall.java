/**
 * @(#)AceFighter4_3 -> Projectile Engine -> Gravity Ball class
 *
 * A Gravity Ball is a projectile with a magnetic (gravitational) field. That means that any players
 * that are within its range, are pulled towards the gravity ball. When they collide, the gravity ball
 * pushes them away a bit and hurts them.
 *
 * @author Chris Braunschweiler
 * @version 1.00 July 13, 2008
 */
import java.io.*;
import java.util.*;
public class GravityBall extends ProjectileEntity
{
	private int rangeOfEffect;	//the range of effect. The range of the gravitational field
	private double gravitationalPull;	//the gravitational pull of the gravity ball
	//private ArrayList<PlayerEntity> opponents;	//the list of opponents. Allows the gravity ball to only pull in the opponents
	
	public GravityBall(int xCoord, int yCoord, int width, int height, ArrayList<PlayerEntity> opponents)
	{
		super(xCoord,yCoord,width,height);
		//this.opponents = opponents;
		getSprite().setImageFile(new File("Images/Projectiles/GravityBall.png"));
		setDamage(8);
		rangeOfEffect = 200;
		gravitationalPull = 3.0;
	}
	
	public void update(double gravity)
	{
		super.update(gravity);
		
		if(alive())
		{
			//Pull in any players that are within the range of effect
			for(int i = 0; i<getOpponents().size(); i++)
			{
				if(getOpponents().get(i).alive())
				{
					double distanceToOpponent = distanceTo(getOpponents().get(i).getSprite());
					if(distanceToOpponent<=rangeOfEffect)	//If opponent is within the range of effect of gravity ball
					{
						pullInPlayer(getOpponents().get(i));
					}
				}
			}
		}
	}
	
	//Pulls player towards gravity ball
	public void pullInPlayer(PlayerEntity player)
	{
		//Compute the angle between the opponent and the gravity ball
		double xDiff = this.getSprite().getXCoord()-player.getSprite().getXCoord();
		double yDiff = this.getSprite().getYCoord()-player.getSprite().getYCoord();
		double quotient = yDiff/xDiff;
		double computeAngle = Math.toDegrees(Math.atan(yDiff/xDiff));
			
		if(computeAngle<360)
		{
			computeAngle = (360+(int)computeAngle);			
		}
		if(computeAngle>360)
		{
			computeAngle = computeAngle-360;
		}
		if(xDiff<0)
		{
			computeAngle = computeAngle-180;
		}

				
		//Use the angle to pull the opponent towards the gravity ball
		player.setInAir(true);	
		//pulling player towards Gravity Ball
		double xVelocityChange = (Math.cos(Math.toRadians(computeAngle))*gravitationalPull);	//the amount by which
																								// x velocity should
																								// change.
		double yVelocityChange = (Math.sin(Math.toRadians(computeAngle))*gravitationalPull);	//the amount by which
																								// y velocity should
																								// change.
																								
		player.getSprite().setXVeloc(player.getSprite().getXVeloc()+xVelocityChange);
		player.getSprite().setYVeloc(player.getSprite().getYVeloc()+yVelocityChange);
	}
	
	/*public int distanceTo(Sprite sprite)
	{
		double xDist = this.getSprite().getXCoord()-sprite.getXCoord();
		xDist = Math.abs(xDist);
		double yDist = this.getSprite().getYCoord()-sprite.getYCoord();
		yDist = Math.abs(yDist);
		xDist = xDist*xDist;
		yDist = yDist*yDist;
		double distance = Math.sqrt(xDist+xDist);
		return (int)distance;
	}*/
	
	public void collisionEffect(PlayerEntity player)
	{
		player.setInAir(true);
		player.getSprite().setYVeloc(-5);
		Random random = new Random();
		int decision = random.nextInt();
		int sign;
		if(decision>=.5)
			sign = -1;
		else
			sign = 1;
		player.getSprite().setXVeloc(5*sign);	//player gets thrown either to the left or to the right (decided randomly)
		super.collisionEffect(player);
	}
}
