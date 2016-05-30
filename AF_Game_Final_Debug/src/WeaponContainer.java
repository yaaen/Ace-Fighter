/**
 * @(#)AceFighter2_6 -> Menu Engine -> Weapon Container
 *
 * This menu entity contains Weapon that the player has. It can either hold
 * an equipped Weapon or simply Weapon in the player's possession. If the weapon
 * is not equipped and the player clicks on the weapon container, the weapon's state
 * changes to 'equipped'.
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 15, 2008
 */

import java.util.*; 
public class WeaponContainer extends MenuEntity
{
	private WeaponEntity weapon;		//the weapon that it holds
	private MenuHandler menuHandler;	//allows weapon container to manipulate the player profile
	
	public WeaponContainer(int xCoord, int yCoord, int width, int height, WeaponEntity weapon,
		MenuHandler menuHandler)
	{
		super(xCoord,yCoord,width,height);
		this.weapon = weapon;
		this.menuHandler = menuHandler;
	}
	
	public void update()
	{
	}
	
	/**
	 * Action
	 * When a Weapon Container is clicked on and the contained weapon is not
	 * equipped, the weapon becomes equipped.
	 */
	public void action()
	{
		ArrayList<WeaponEntity> weapons = menuHandler.getPlayerProfile().getWeapons();
		for(int i = 0; i<weapons.size(); i++)
		{
			System.out.println("*******Weapon");
			if(weapons.get(i).equals(weapon))	//if the weapon container which has been clicked on's weapon equals this weapon
												//ie: if this weapon container has been clicked on
												//ie: If the weapon container that contains the weapon that player wants to click on
												//has been clicked on
			{
				if(weapons.get(i).equipped()==false)	//if this weapon container's weapon is not already equiped
				{
					weapons.get(i).setEquipped(true);	//equip it
				}
				else if(weapons.get(i).equipped())	//if the weapon is already equipped and the user clicks on the weapon container
				{
					weapons.get(i).setEquipped(false);	//unequip it
				}
			}
		}
		menuHandler.setGuiEvent("GUIEvent_WeaponContainer");
	}
}
