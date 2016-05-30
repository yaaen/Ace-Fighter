/**
 * @(#)AF_Applet1_4 -> Menu Engine -> GameStarter
 *
 * Starts the game. This entity is on the start screen of the game. It selects Benny T
 * as the player's character and starts the game.
 *
 * @author Chris Braunschweiler
 * @version 1.00 October 12, 2008
 */

public class GameStarter extends MenuEntity
{
	private String displayText;
	private String characterFileName;	//the name of the character file that this loads when clicked on
	private MenuHandler menuHandler;	//uses this to access the player profile. That way it can load the contents of the character file
										//into the player profile
	
	public GameStarter(int xCoord, int yCoord, int width, int height, String displayText,String characterFileName,
			MenuHandler menuHandler)
	{
			super(xCoord,yCoord,width,height);
			this.displayText = displayText;
			this.characterFileName = characterFileName;
			this.menuHandler = menuHandler;
	}
	
	public void update()
	{
	}
	
	/**
	 * Action
	 * When a CharacterSelector is clicked on, the PlayerProfile's loadCharacterFile() method
	 * is called and the characterFileName is passed to it.
	 */
	public void action()
	{
		menuHandler.getPlayerProfile().loadCharacterFile(characterFileName);
		menuHandler.setGuiEvent("GUIEvent_CharacterSelector");
		menuHandler.setStartGame(true);
	}
	
	public String toString()
	{
		return displayText;
	}
}
