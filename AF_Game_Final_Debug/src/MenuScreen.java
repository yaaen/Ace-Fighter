/**
 * @(#)AceFighter1_7 -> Menu Engine -> MenuScreen
 *
 * A Menu Screen is well...a menu screen. A screen in Ace Fighter's menu.
 * This can be a title screen, a character selection screen, a pause menu screen,
 * an inventory screen etc... A Menu Screen consists of a name, background image, and
 * a list of MenuEntities.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
 
import java.util.*; 
 
abstract class MenuScreen 
{
	private String name;	//the name of the menu screen
	//private Image image;	//the background image
	private ArrayList<MenuEntity> menuEntities;	//the list of menu entities
	
	public MenuScreen()
	{
		name = "";
		menuEntities = new ArrayList<MenuEntity>();
	}
	
	//Accessor Methods
	public String getName()
	{
		return name;
	}
	
	public ArrayList<MenuEntity> getMenuEntities()
	{
		return menuEntities;
	}
	
	//Mutator Methods
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setMenuEntities(ArrayList<MenuEntity> menuEntities)
	{
		this.menuEntities = menuEntities;
	}
	
	//Mouse-related methods to check whether mouse hovered over anything or clicked on
	//anything.
	/**
	 * Mouse Moved
	 * @param The x and y coordinates of the mouse.
	 * Reports mouse movement to the menu entities.
	 */
	public void mouseMoved(int x, int y)
	{
		for(int i = 0; i<menuEntities.size();i++)
		{
			menuEntities.get(i).mouseMoved(x,y);
		}
	}
	
	/**
	 * Mouse Pressed
	 * @param The x and y coordinates of the mouse.
	 * Reports mouse presses (clicks) to the menu entities.
	 */
	public void mousePressed(int x, int y)
	{
		for(int i = 0; i<menuEntities.size(); i++)
		{
			menuEntities.get(i).mousePressed(x,y);
		}
	}
	
	/**
	 * Load Screen
	 * Loads the contents of the screen, if they need to be loaded.
	 */
	abstract void loadScreen();
}
