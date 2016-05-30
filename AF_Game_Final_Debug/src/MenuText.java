/**
 * @(#)AceFighter2_6 -> Menu Engine -> Menu Text
 *
 * The Menu Text is used to display text in the menu.
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 16, 2008
 */
 
public class MenuText extends MenuEntity
{
	private String text;	//the text to be displayed
	private String field;	//specifies the field of the player entity which it should display (if it should display one)
	private String outputText;	//the text that will be displayed
	private MenuHandler menuHandler;	//allows it to display character information by accessing menu handler's player profile
	
	public MenuText(int xCoord, int yCoord, int width, int height, String text, String field, MenuHandler menuHandler)
	{
		super(xCoord,yCoord,width,height);
		this.text = text;
		this.field = field;
		outputText = text;
		this.menuHandler = menuHandler;
		generateText();
	}
	
	/**
	 * Generate Text
	 * Displays all the text in the Menu Text.
	 */
	private void generateText()
	{
		if(field.equals("Name"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getName());
		}
		if(field.equals("Lives"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getLives());
		}
		if(field.equals("Health"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getHealth());
		}
		if(field.equals("Speed"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getSpeed());
		}
		if(field.equals("Jump Speed"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getJumpSpeed());
		}
		if(field.equals("Firing Rate"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getFiringRate());
		}
		if(field.equals("Special"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getSpecial());
		}
		if(field.equals("Score"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getScore());
		}
		if(field.equals("Manupians"))
		{
			outputText = (text + menuHandler.getPlayerProfile().getManupians());
		}
	}
	
	/** update
	 * Updates the Menu Text when the mouse is moved. This is used to display up to date
	 * information such as player information. When the user clicks on a button to increase
	 * his speed, for example, the menu text has to be updated to reflect the new speed.
	 */
	public void update()
	{
		generateText();	//generate the text again to display any updates in the player profile
	}
	
	/**
	 * Action
	 * This method does not do anything here. Menu Texts do not react to clicks.
	 */
	public void action()
	{
	}
	
	/**
	 * toString
	 * Since the menu text does not use a Sprite to be displayed, it overwrites to the toString() method
	 * to display textual information. The main engine will try to display the Menu Text by getting its
	 * sprite. However, if the sprite is null, it will call the Menu Text's toString() method.
	 */
	public String toString()
	{
		return outputText;
	}
}
