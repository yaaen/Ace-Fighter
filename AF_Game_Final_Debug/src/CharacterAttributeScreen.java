/**
 * @(#)AceFighter2_7 -> Menu Engine -> Character Attribute Screen class
 *
 * This class represents the Character Attribute Screen. This screen allows a user
 * to edit his character's attributes such as his speed, jump speed, firing rate,
 * special, health etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 22, 2008
 */

import java.util.*;
public class CharacterAttributeScreen extends MenuScreen
{
	private MenuHandler menuHandler;
	private ArrayList<MenuEntity> menuEntities;	//the list of menu entities
	
	public CharacterAttributeScreen(MenuHandler menuHandler, WeaponInventoryScreen previousScreen)
	{
		super();
		setName("CharacterAttributeScreen");
		this.menuHandler = menuHandler;
		menuEntities = new ArrayList<MenuEntity>();
		menuEntities.add(new MenuLink(100,300,100,50,menuHandler, previousScreen));
		menuEntities.add(new MenuText(300,100,0,0,"Manupians: ", "Manupians", menuHandler));
		menuEntities.add(new MenuText(400,200,0,0,"Health: ", "Health", menuHandler));
		menuEntities.add(new AttributeModifier(600,200,10,10,menuHandler,"Health",1));
		menuEntities.add(new AttributeModifier(650,200,10,10,menuHandler,"Health",-1));
		menuEntities.add(new MenuText(400,250,0,0,"Speed: ", "Speed", menuHandler));
		menuEntities.add(new AttributeModifier(600,250,10,10,menuHandler,"Speed",1));
		menuEntities.add(new AttributeModifier(650,250,10,10,menuHandler,"Speed",-1));
		menuEntities.add(new MenuText(400,300,0,0,"Jump Speed: ","Jump Speed", menuHandler));
		menuEntities.add(new AttributeModifier(600,300,10,10,menuHandler,"Jump Speed",1));
		menuEntities.add(new AttributeModifier(650,300,10,10,menuHandler,"Jump Speed",-1));
		menuEntities.add(new MenuText(400,350,0,0,"Firing Rate: ","Firing Rate", menuHandler));
		menuEntities.add(new AttributeModifier(600,350,10,10,menuHandler,"Firing Rate",1));
		menuEntities.add(new AttributeModifier(650,350,10,10,menuHandler,"Firing Rate",-1));
		menuEntities.add(new MenuText(400,400,0,0,"Special: ","Special", menuHandler));
		menuEntities.add(new AttributeModifier(600,400,10,10,menuHandler,"Special",1));
		menuEntities.add(new AttributeModifier(650,400,10,10,menuHandler,"Special",-1));
		setMenuEntities(menuEntities);
	}	
	
	public void loadScreen()
	{
		menuEntities.add(new MenuText(300,100,0,0,"Manupians: ", "Manupians", menuHandler));
	}
}
