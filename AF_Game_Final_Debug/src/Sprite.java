/**
 * @(#)AceFighter1_0 -> Sprite Engine -> Sprite Class
 *
 * The Sprite engine is responsible for providing the framework necessary to represent
 * several of Ace Fighter's main engines on screen. It maintains the positions, speeds, 
 * sizes, angles and images of the several engines of Ace Fighter.
 *
 * The Sprite Engine consists of a few classes: The Sprite class (this class), the
 * Animated Sprite class, the PlayerSprite class and the BossSprite class.
 *
 * The Sprite class (this class) simply maintains the basic information necessary to represent
 * a game entity on screen (position, velocity, dimensions, angle and image). It also contains
 * a mechanism used to test for collisions between different game entities. The task of collision
 * testing, however, is not dealt with in this engine. This engine simply provides a means for
 * the collision engine to test for collisions.
 *
 * See the other Sprite classes for their functionalities.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */

import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

public class Sprite implements Serializable
{
	//Private variables
	private double xCoord;	//the x position of the Sprite
	private double yCoord;	//the y position of the Sprite
	private double xVeloc;	//the horizontal velocity of the Sprite
	private double yVeloc; //the vertical velocity of the Sprite
	private int relationalXDifference;	//used for the side scrolling levels
	private int relationalYDifference;	//used for the side scrolling levels
										//Collisions with walls affects the x coordinates of
										//players (not the velocities). In side scrolling levels, the relational points (see GameEngine)
										//are affected by the player's velocities, and not the coordinates. So in side scrolling
										//levels, the walls dont work when a player simply runs through it since the collision
										//with the wall doesn't affect the player's velocity. These variables provide a way
										//for the affected Sprite to store information about the coordinate change as a result of the collision.
										//The GameEngine which is responsible for the scrolling, can obtain this information about the coordinate
										//change and use it to create the side scrolling effect.
	private int width;	//the width of the Sprite
	private int height;	//the height of the Sprite
	private double faceAngle;	//the angle the Sprite is facing
	private transient Image image;	//the image used to display the Sprite
	private File imageFile;	//the image file of the sprite. Images cannot be serialized but files can. If the file is serialized, the image
							//can be reloaded after an object is read out of a file again by using the Image File and not the Image itself.
	private int imageWidth;	//the width of the image used to display the Sprite
	private int imageHeight;	//the height of the image
	
	//Constructor
	public Sprite(int xCoord, int yCoord, int width, int height)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		xVeloc = 0;
		yVeloc = 0;
		relationalXDifference = 0;
		relationalYDifference = 0;
		this.width = width;
		this.height = height;
		faceAngle = 0;
		image = null;
		imageFile = null;
		imageWidth = width;
		imageHeight = height;
	}
	
	//Accessor Methods
	public double getXCoord()
	{
		return xCoord;
	}
	
	public double getYCoord()
	{
		return yCoord;
	}
	
	public double getXVeloc()
	{
		return xVeloc;
	}
	
	public double getYVeloc()
	{
		return yVeloc;
	}
	
	public int getRelationalXDifference()
	{
		return relationalXDifference;
	}
	
	public int getRelationalYDifference()
	{
		return relationalXDifference;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public double getFaceAngle()
	{
		return faceAngle;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public File getImageFile()
	{
		return imageFile;
	}
	
	public int getImageWidth()
	{
		return imageWidth;
	}
	
	public int getImageHeight()
	{
		return imageHeight;
	}
	
	//Mutator methods
	public void setXCoord(double xCoord)
	{
		this.xCoord = xCoord;
	}
	
	public void setYCoord(double yCoord)
	{
		this.yCoord = yCoord;
	}
	
	public void setXVeloc(double xVeloc)
	{
		this.xVeloc = xVeloc;
	}
	
	public void setYVeloc(double yVeloc)
	{
		this.yVeloc = yVeloc;
	}
	
	public void setRelationalXDifference(int relationalXDifference)
	{
		this.relationalXDifference = relationalXDifference;
	}
	
	public void setRelationalYDifference(int relationalYDifference)
	{
		this.relationalYDifference = relationalYDifference;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setFaceAngle(double faceAngle)
	{
		this.faceAngle = faceAngle;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
	public void setImageFile(File imageFile)
	{
		this.imageFile = imageFile;
	}
	
	public void setImageWidth(int imageWidth)
	{
		this.imageWidth = imageWidth;
	}
	
	public void setImageHeight(int imageHeight)
	{
		this.imageHeight = imageHeight;
	}
	
	//Method needed by collision engine
	/**
	 * @return Returns a bounding rectangle of the size slightly larger than this sprite.
	 * The bounding rectangle returned is slightly larger than this sprite so that it creates a bit of
	 * a buffer.
	 */
	public Rectangle2D getBounds()
	{
		Rectangle2D r;
		r = new Rectangle2D.Double(getXCoord(),getYCoord(), width, height);
		return r;
	}
}
