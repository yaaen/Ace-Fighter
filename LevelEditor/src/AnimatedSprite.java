/**
 * @(#)AceFighter1_0 -> Sprite Engine -> AnimatedSprite class
 *
 * This class extends the Sprite class and is responsible for Sprite Animation.
 * In order to realize animation, additional fields are needed. These fields are:
 *		1) StartFrame - The starting frame of animation
 *		2) CurrentFrame - The current frame of animation
 *		3) StripSize - The size of the animation strip
 *		4) NumCols - The number of Columns of the animation strip -> The number of frames on one row.
 *
 *		Example: An animation strip that looks like this
 *
 *				FFFF
 *				F
 *		
 *		Where F represents one frame, has the following values:
 *			1) StartFrame = 0
 *			2) CurrentFrame = Whichever frame is currently being displayed - any value from 0 to StripSize-1
 *			3) StripSize = 5
 *			4) NumCols = 4
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 16, 2008
 */
 
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;

public class AnimatedSprite extends Sprite
{
	//Private variables
	private int startFrame;			//the starting frame of animation (where the animation strip starts)
	private int currentFrame;		//the current frame being displayed
	private int stripSize;			//the size of the animation strip
	private int numCols;			//the number of columns in the animation strip
	//private int frameWidth;			//the width of a frame of animation
	//private int frameHeight;		//the height of a frame of animation
	private int frameDelay;			//the number of loop iterations that should pass between frame updates
	private int currentTime;		//the current loop iteration --> used for the frame delay
	

	//Constructor
	public AnimatedSprite(int xCoord, int yCoord, int width, int height)
	{
		super(xCoord,yCoord,width,height);
		startFrame = 0;
		currentFrame = 0;
		stripSize = 0;
		numCols = 0;
//		frameWidth = 0;
//		frameHeight = 0;
		frameDelay = 0;
		currentTime = 0;
	}
	
	//Accessor methods
	public int getStartFrame()
	{
		return startFrame;
	}
	
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	
	public int getStripSize()
	{
		return stripSize;
	}
	
	public int getNumCols()
	{
		return numCols;
	}
	
/*	public int getFrameWidth()
	{
//		return frameWidth;
	}
	
	public int getFrameHeight()
	{
		return frameHeight;
	}
	*/
	public int getFrameDelay()
	{
		return frameDelay;
	}
	
	//Mutator methods
	public void setStartFrame(int startFrame)
	{
		this.startFrame = startFrame;
	}
	
	public void setCurrentFrame(int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	
	public void setStripSize(int stripSize)
	{
		this.stripSize = stripSize;
	}
	
	public void setNumCols(int numCols)
	{
		this.numCols = numCols;
	}
	
/*	public void setFrameWidth(int frameWidth)
	{
		this.frameWidth = frameWidth;
	}
	
	public void setFrameHeight(int frameHeight)
	{
		this.frameHeight = frameHeight;
	}*/
	
	public void setFrameDelay(int frameDelay)
	{
		this.frameDelay = frameDelay;
	}
	
	/**Used to animate the Sprite
	 * Iterates through the animation strip. When it reaches the end, it goes back to the
	 * beginning frame.
	 */
	public void animate()
	{
		if(currentTime>frameDelay)	//if it's time to update the frame of animation, update it
		{
			currentTime = 0;		
			boolean frameChanged = false;	//makes sure frame 0 also gets displayed
											//Without this, when frame changes back to beginning of animation strip, it first
											//gets incremented again at the end before it even has a chance to be displayed.
			if(currentFrame>=(startFrame+stripSize-1))
			{
				currentFrame = startFrame;
				frameChanged = true;
			}
			if(!frameChanged)	//only change frame if it hasnt already been changed
				currentFrame++;	
		}
		currentTime++;
	}
}
