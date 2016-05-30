/**
 * @(#)AceFighter1_7 -> Menu Engine -> Title Screen
 *
 * The Title Screen is the first screen the user sees when the game is booted.
 * It simply displays a title screen and has a link to the next screen (character
 * selection screen).
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */

import java.awt.Color;
import java.awt.Font;
import java.util.*;
public class TitleScreen extends MenuScreen
{
	private ArrayList<MenuEntity> menuEntities;		//the list of menu entities of this screen
	
	public TitleScreen(MenuHandler menuHandler)
	{
		super();
		setName("TitleScreen");
		menuEntities = new ArrayList<MenuEntity>();
		MenuEntity gameStarter = new GameStarter(440,522,120,42,"","Load/Characters/CharacterFile.txt",menuHandler);
		gameStarter.setFont(new Font("Arial",Font.PLAIN,18));
		gameStarter.setColor(Color.BLACK);
		menuEntities.add(gameStarter);
		setMenuEntities(menuEntities);
	}
	
	//Inherited Methods
	public void loadScreen()
	{
	}
}
