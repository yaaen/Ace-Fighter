/**
 * @(#)AceFighter2_6 -> Menu Engine -> Weapon Inventory Screen
 *
 * The Weapon Inventory Screen is used by the human player to equip his character
 * with newly earned weapons. The user can also unequip and upgrade weapons as
 * he pleases.
 *
 * @author Chris Braunschweiler
 * @version 1.00 April 15, 2008
 */
 
import java.util.*; 
public class WeaponInventoryScreen extends MenuScreen
{
	private MenuHandler menuHandler;
	private ArrayList<WeaponContainer> weaponContainers;	//the list of weapon containers
															//this is used to add the weapons from the player profile
															//to the weapon containers
	private ArrayList<MenuEntity> menuEntities;	//the menu entities of this screen
	
	public WeaponInventoryScreen(MenuHandler menuHandler)
	{
		super();
		setName("WeaponInventoryScreen");
		this.menuHandler = menuHandler;
		weaponContainers = new ArrayList<WeaponContainer>();
		menuEntities = new ArrayList<MenuEntity>();	
		populateWeaponContainers();
		addWeaponContainers();
		menuEntities.add(new MenuLink(600,300,100,50,menuHandler,new CharacterAttributeScreen(menuHandler, this)));
		menuEntities.add(new MenuText(100,100,0,0,"Manupians: ", "Manupians", menuHandler));
		menuEntities.add(new MenuText(100,150,0,0,"Lives: ", "Lives", menuHandler));
		menuEntities.add(new GameLauncher(500, 400, 100, 50,"Go Battle",menuHandler,true,false));
		setMenuEntities(menuEntities);
	}
	
	/**
	 * Populate Weapon Containers
	 * Places the player profile's weapons into the weapon containers.
	 */
	private void populateWeaponContainers()
	{
		weaponContainers.clear();	//clear all weapons before adding currently owned weapons
		ArrayList<WeaponEntity> weapons = menuHandler.getPlayerProfile().getWeapons();
		for(int i = 0; i<weapons.size(); i++)
		{
			weaponContainers.add(new WeaponContainer((100*i),300,50,50,weapons.get(i),menuHandler));
		}
	}
	
	/**
	 * Add Weapon Containers
	 * Adds all the Weapon Containers to the list of menu entities.
	 */
	private void addWeaponContainers()
	{
		for(int i = 0; i<weaponContainers.size(); i++)
		{
			menuEntities.add(weaponContainers.get(i));
		}
	}
	
	//Inherited Methods
	/**
	 * Load Screen
	 * Loads all the weapons into the weapon containers.
	 */
	public void loadScreen()
	{
		populateWeaponContainers();
		addWeaponContainers();
		setMenuEntities(menuEntities);
	}
}
