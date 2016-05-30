/**
 * @(#)AceFighter1_7 -> Menu Engine -> MenuEntity
 *
 * A MenuEntity is an object on a menu screen such as a link to another screen, a
 * character selector, item container, etc... This class is abstract because it
 * does not represent a specific menu entity. It contains an abstract method
 * called action() that responds to clicks. Since this class is not a specific
 * menu entity, the method action() can not be implemented and has to be implemented
 * by each individual menu entity that extends this class (ie: CharacterSelector, MenuLink
 * etc...).
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
import java.awt.*;
import java.awt.Graphics2D;
abstract class MenuEntity 
{
	//Static constants for animation. All images used by MenuEntities must conform to this standard
	//unless they define their own standard.
	private static final int NUM_COLS = 2;
	private static final int STRIP_SIZE = 2;
	
	private AnimatedSprite sprite;	//the sprite used for positioning as well as the rollover effect when
									//the mouse hovers over the menu entity.
	private boolean rolloverEnabled;	//true if there should be a rollover effect when moving over the menu entity
									//with the mouse, false otherwise.
	private String name;		//the name of the Menu Entity
	private Font font;	//the font of the text of the menu entity
	private Color color;	//the color of the menu entitie's text
	
	public MenuEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new AnimatedSprite(xCoord,yCoord,width,height);
		sprite.setNumCols(NUM_COLS);
		sprite.setStripSize(STRIP_SIZE);
//		sprite.setFrameWidth(width);
//		sprite.setFrameHeight(height);
		name = "";
		font = new Font("Arial",Font.PLAIN,12);
		color = Color.YELLOW;
	}
	
	//Accessor methods
	public AnimatedSprite getSprite()
	{
		return sprite;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	//Mutator Methods
	/**
	 * Set Name
	 * @param The name of the menu entity.
	 * This method changes the name of the menu entity to that passed in
	 * the parameter. It's protected so that only menu entities can use it.
	 */
	protected void setName(String name)
	{
		this.name = name;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Mouse Moved
	 * @param The x and y coordinates of the mouse.
	 * This method checks whether the mouse is hovering over the
	 * menu entity and reacts accordingly. When a mouse hovers over a menu entity, its sprite
	 * displays the rollover frame. If the mouse is not hovering over the entity, the non-rollover frame
	 * is displayed.
	 */
	public void mouseMoved(int x, int y)
	{
		if(isRolloverEnabled())
		{
			if(sprite.getBounds().contains((double)x, (double)y))	//if coordinates of mouse are within bounds of the menu entity (if mouse hovering over entity)
			{
				sprite.setCurrentFrame(sprite.getStripSize()-1);	//make current frame 2nd (of 2) frames
			}
			else	//if mouse not over menu entity
			{
				sprite.setCurrentFrame(sprite.getStartFrame());		//make current frame the first frame in strip
			}
		}
		update();
	}
	
	/**
	 * Mouse Clicked
	 * @param The x and y coordinates of the mouse.
	 * This method checks whether the menu entity has been clicked on. If it has been clicked on, its
	 * respective action() method is called.
	 */
	public void mousePressed(int x, int y)
	{
		if(sprite.getBounds().contains((double)x, (double)y))	//if mouse clicked on menu entity
		{
			action();	//execute appropriate action
		}
	}
	
	/**
	 * Update
	 * Determines what happens when the mouse rolls over the menu entity.
	 * This is typically called by the mouseMoved() method.
	 */
	abstract void update();
	
	/**
	 * Action
	 * The action taken when the menu entity is clicked on.
	 */
	abstract void action();

	public void setRolloverEnabled(boolean rolloverEnabled) {
		this.rolloverEnabled = rolloverEnabled;
	}

	public boolean isRolloverEnabled() {
		return rolloverEnabled;
	}
}
