/**
 * @(#)AceFighter1_7 -> Menu Engine -> GameLauncher
 *
 * A Game launcher starts or resumes the game. Depending on whether the
 * startGame boolean is true or the resumeGame boolean is true, it either starts 
 * the next (or first) battle or resumes the game from a paused state. These decisions, 
 * of course, are handled outside the menu engine.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
 
public class GameLauncher extends MenuEntity
{
	private String displayText;
	private MenuHandler menuHandler;	//the menu handler
	private boolean startGame;	//true if this launcher starts next battle
	private boolean resumeGame;	//true if this launcher simply resumes current battle
	
	public GameLauncher(int xCoord, int yCoord, int width, int height, String displayText,MenuHandler menuHandler, 
		boolean startGame, boolean resumeGame)
	{
		super(xCoord,yCoord,width,height);
		this.displayText = displayText;
		this.menuHandler = menuHandler;
		this.startGame = startGame;
		this.resumeGame = resumeGame;
		if(startGame==resumeGame)	//if both booleans are either true or false
		{
			//Make GameLauncher inactive -> If game launcher doesn't respond to clicks, check to see if it's
			//been initialized properly -> if the 2 booleans (startGame and resumeGame) have different values
			startGame = false;
			resumeGame = false;
		}
	}
	
	public void update()
	{
	}
	
	/**
	 * Action
	 * When a Game Launcher is clicked on, it either starts a new game by setting the MenuHandler's
	 * startGame boolean to true or it resumes the game by setting the menu handler's resumeGame
	 * boolean to true.
	 */
	public void action()
	{
		if(startGame)	//if this menu launcher starts a new game
		{
			menuHandler.setStartGame(true);
		}
		if(resumeGame)	//if this menu launcher resumes a game
		{
			menuHandler.setResumeGame(true);
		}
		menuHandler.setGuiEvent("GUIEvent_GameLauncher");
	}
	
	public String toString()
	{
		return displayText;
	}
}
