/**
 * @(#)AceFighter1_7 -> Menu Engine -> CharacterSelector
 *
 * A CharacterSelector chooses a character. When the player starts the game, he
 * can choose his character. This class enables that functionality. It contains
 * a String called characterFileName and a PlayerProfile field. When its action() 
 * method is called, it calls the PlayerProfile's loadCharacterFile() method and 
 * passes its characterFileName field as the parameter.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
 
public class CharacterSelector extends MenuEntity
{
	private String characterFileName;	//the name of the character file that this loads when clicked on
	private MenuHandler menuHandler;	//uses this to access the player profile. That way it can load the contents of the character file
										//into the player profile
	
	public CharacterSelector(int xCoord, int yCoord, int width, int height, String characterFileName,
		MenuHandler menuHandler)
	{
		super(xCoord,yCoord,width,height);
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
	}
}
