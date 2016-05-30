/**
 * @(#)AceFighter1_7 -> Menu Engine -> Character Select Screen
 *
 * The Character Select Screen allows the user to choose a character and start the game.
 * It contains character selectors and a game launcher.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */

import java.util.*;

public class CharacterSelectScreen extends MenuScreen
{
	private ArrayList<MenuEntity> menuEntities;	//list of menu entities
	
	public CharacterSelectScreen(MenuHandler menuHandler, MenuScreen previousScreen)
	{
		super();
		setName("CharacterSelectScreen");
		menuEntities = new ArrayList<MenuEntity>();
		menuEntities.add(new CharacterSelector(300,100,50,50,"Load/Test/Characters/CharacterFile.txt",menuHandler));
		menuEntities.add(new GameLauncher(300,200,100,50,"Go Battle!",menuHandler,true,false));
		menuEntities.add(new MenuLink(300,300,100,50,menuHandler,previousScreen));
		menuEntities.add(new MenuText(100, 100, 0, 0, "Select your character", "None", menuHandler));
		menuEntities.add(new MenuText(100, 200, 0, 0, "Start Game", "None", menuHandler));
		setMenuEntities(menuEntities);
	}
	
	//Inherited Methods
	public void loadScreen()
	{
	}
}
