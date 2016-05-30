/**
 * @(#)AF_Applet1_5 -> Menu Engine -> End Of Game Screen
 *
 * This screen is displayed when the player beats the game.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 12, 2008
 */

import java.util.*;
public class EndOfGameScreen extends MenuScreen
{
	private ArrayList<MenuEntity> menuEntities;	//list of menu entities
	
	public EndOfGameScreen(MenuHandler menuHandler)
	{
		super();
		setName("EndOfGameScreen");
		menuEntities = new ArrayList<MenuEntity>();
		menuEntities.add(new MenuText(100, 100, 0, 0, "Congratulations! You've completed this version of Ace Fighter!", "None", menuHandler));
		menuEntities.add(new MenuText(100, 200, 0, 0, "Total Score: "+ menuHandler.getPlayerProfile().getScore(), "None", menuHandler));
		menuEntities.add(new MenuText(400,550,0,0,"Copyright Chris Braunschweiler","None",menuHandler));
		setMenuEntities(menuEntities);
	}
	
	//Inherited Methods
	public void loadScreen()
	{
	}
}
