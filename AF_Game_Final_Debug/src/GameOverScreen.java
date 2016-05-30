/**
 * @(#)AF_Applet1_6 -> Menu Engine -> GameOverScreen
 *
 * This screen is displayed when the player runs out of lives.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 12, 2008
 */
import java.util.*;
import java.awt.*;
public class GameOverScreen extends MenuScreen
{
	private ArrayList<MenuEntity> menuEntities;	//list of menu entities
	
	public GameOverScreen(MenuHandler menuHandler)
	{
		super();
		setName("EndOfGameScreen");
		menuEntities = new ArrayList<MenuEntity>();
		MenuText text1 = new MenuText(450,200,0,0,"Game Over","None",menuHandler);
		text1.setFont(new Font("Arial",Font.PLAIN,18));
		menuEntities.add(text1);
		menuEntities.add(new MenuText(450, 300, 0, 0, "Total Score: "+ menuHandler.getPlayerProfile().getScore(), "None", menuHandler));
		menuEntities.add(new MenuText(400,550,0,0,"Refresh your browser to play again!","None",menuHandler));
		menuEntities.add(new MenuText(410,570,0,0,"Copyright Chris Braunschweiler","None",menuHandler));
		setMenuEntities(menuEntities);
	}
	
	//Inherited Methods
	public void loadScreen()
	{
	}
}
