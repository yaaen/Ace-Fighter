/**
 * @(#)AceFighter1_7 -> Menu Engine -> MenuHandler
 *
 * The MenuHandler runs the entire menu engine. The Menu Handler contains a list of
 * Menu Screens. It also has a current menu screen and uses it, along with the
 * screen's menu entities, to navigate through the menu, set different parameters
 * etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */

import java.util.*;

public class MenuHandler 
{
	private PlayerProfile playerProfile;	//the player profile that can be manipulated by the Menu Engine
	private MenuScreen currentScreen;	//the current screen to be displayed
	private boolean startGame;		//true if the game or battle should be started (ie: if a 'Start Game' button has been clicked on)
	private boolean resumeGame;		//true if the game should be resumed (ie: if game was paused and 'Continue' button was clicked)
	private String guiEvent;	//the gui event that has been triggered (if any). If a menu entity or something is clicked, a gui event
								//is triggered. This event needs to be sent to the main engine so that the main engine can pass it on
								//to any other engines that might need to know about it (ie: cutscene engine).
	
	public MenuHandler(PlayerProfile playerProfile)
	{
		this.playerProfile = playerProfile;
		currentScreen = new TitleScreen(this);
		startGame = false;
		resumeGame = false;
		guiEvent = "";
	}
	
	/**
	 * Reset Menu
	 * Sets the startGame and resumeGame booleans to false so that menu doesnt try to start
	 * or resume the game on the first mouse click. It makes sure that the mouse clicks actually
	 * have to hit a menu entity in order to trigger an event.
	 */
	public void resetMenu()
	{
		startGame = false;
		resumeGame = false;
	}
	
	//Accessor Methods
	public PlayerProfile getPlayerProfile()
	{
		return playerProfile;
	}
	
	public MenuScreen getCurrentScreen()
	{
		return currentScreen;
	}
	
	public boolean startGame()
	{
		return startGame;
	}
	
	public boolean resumeGame()
	{
		return resumeGame;
	}
	
	//Mutator methods
	public void setCurrentScreen(MenuScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}
	
	public void setStartGame(boolean startGame)
	{
		this.startGame = startGame;
	}
	
	public void setResumeGame(boolean resumeGame)
	{
		this.resumeGame = resumeGame;
	}
	
	public void setGuiEvent(String guiEvent)
	{
		this.guiEvent = guiEvent;
	}
	
	//Mouse-related methods
	/**
	 * Mouse Moved
	 * @param The x and y coordinates of the mouse.
	 * Reports mouse movement to the current menu screen.
	 */
	public void mouseMoved(int x, int y)
	{
		currentScreen.mouseMoved(x,y);
	}
	 
	 /**
	  * Mouse Pressed
	  * @param The x and y coordinates of the mouse.
	  * @return A String representing the event that occurs as a result of
	  * the click. When different menu entities are clicked on, they return
	  * a String with their name and some info specifying that a GUI event
	  * has happened. This information is then used by the main engine to
	  * inform the cutscene handler that a GUI event has happened. The Cutscene
	  * Handler can then react accordingly.
	  * Reports mouse presses to the current menu screen.
	  */
	public String mousePressed(int x, int y)
	{
		guiEvent = "";
		currentScreen.mousePressed(x,y);
		return guiEvent;
	}
	
	//Additional methods
	/**
	 * Load Inventory
	 * This method loads the inventory screen. This is called by the main engine when a battle is over and the
	 * inventory screen needs to be loaded.
	 * It makes it so that the main engine doesnt need to worry about the different menu screens.
	 */
	public void loadInventory()
	{
		currentScreen = new PlayerStatScreen(this);
		//currentScreen = new WeaponInventoryScreen(this);
	}
	
	public void loadGameOverScreen()
	{
		currentScreen = new GameOverScreen(this);
	}
	
	public void loadEndOfGameScreen()
	{
		currentScreen = new EndOfGameScreen(this);
	}
}
