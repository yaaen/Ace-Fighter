/**
 * @(#)AceFighter2_7 -> Menu Engine -> AttributeModifier class
 *
 * This class is used to modify attributes of a player. The modifiers can either
 * add to or subtract from speed, jump, special, firing rate or other character-related
 * attributes.
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 22, 2008
 */
public class AttributeModifier extends MenuEntity
{
	private MenuHandler menuHandler;
	private String attribute;		//specifies the attribute which can be modified by this modifier
	private int value;	//the amount by which to modify the attribute. For example, if this value is 1, add a stat point to whatever
						//attribute. If it's -1, subtract 1 from whatever attribute.
	
	public AttributeModifier(int xCoord, int yCoord, int width, int height, MenuHandler menuHandler,
		String attribute, int value)
	{
		super(xCoord,yCoord,width,height);
		this.menuHandler = menuHandler;
		this.attribute = attribute;
		this.value = value;
	}
	
	public void update()
	{
	}
	
	/**
	 * Action
	 * When an AttributeModifier is clicked on, the given attribute specified by the attribute String field is
	 * modified. The modification entails either an addition to or subtraction from the given attribute. Whether
	 * an addition or subtraction is executed is specified by the plusOrMinus String field. In addition to modifying
	 * the attribute of the player profile, manupians are also added to or subtracted from accordingly in the
	 * player profile.
	 */
	public void action()
	{
		menuHandler.getPlayerProfile().editAttribute(attribute, value);
		menuHandler.setGuiEvent("GUIEvent_AttributeModifier");
	}
}
