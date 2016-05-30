/**
 * @(#)AceFighter1_7 -> Menu Engine -> MenuLink
 *
 * A Menu Link acts as a link between 2 screens. A button that says 'ToInventory' that
 * leads to the Inventory screen, for example, would be a MenuLink. A MenuLink
 * has a screenReference field which is the name of the screen it links to.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 19, 2008
 */
 
public class MenuLink extends MenuEntity
{
	private MenuHandler menuHandler;	//the menu handler
	private MenuScreen screenReference;	//the screen this MenuLink references (links to)
	
	public MenuLink(int xCoord, int yCoord, int width, int height, MenuHandler menuHandler, MenuScreen screenReference)
	{
		super(xCoord, yCoord, width, height);
		this.menuHandler = menuHandler;
		this.screenReference = screenReference;
	}
	
	public void update()
	{
	}
	
	/**
	 * Action
	 * When a menu link is clicked on, it tells the menu handler to change the current screen to the
	 * screen this menu link references. In addition, it also tells the handler about the event that has occurred.
	 * In this case, it tells the menu handler that a MenuLink with a reference to the screen with the name specified
	 * in getName() has been clicked on.
	 */
	public void action()
	{
		menuHandler.setCurrentScreen(screenReference);
		menuHandler.setGuiEvent(("GUIEvent_MenuLink_"+screenReference.getName()));
	}
}
