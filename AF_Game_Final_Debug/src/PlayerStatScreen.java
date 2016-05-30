/**
 * @(#)AceFighter5_7 -> Menu Engine -> Player Stat Screen
 *
 * The Player Statistics Screen (short Player Stat Screen) is displayed in between
 * levels. It shows the player's score, health, special and special weapons. It
 * gives the player a quick overview of their character but it does not let
 * the player edit anything. It also contains a button to launch the next
 * battle.
 *
 * @author Chris Braunschweiler
 * @version 1.00 Septemeber 09, 2008
 */
import java.util.*;
import java.awt.*;
public class PlayerStatScreen extends MenuScreen
{
	private MenuHandler menuHandler;
	private ArrayList<MenuEntity> menuEntities;	//the menu entities of this screen
	
	public PlayerStatScreen(MenuHandler menuHandler)
	{
		super();
		setName("PlayerStatScreen");
		this.menuHandler = menuHandler;
		menuEntities = new ArrayList<MenuEntity>();
		MenuText text = new MenuText(450,50,0,0,"Player Statistics","None",menuHandler);
		text.setFont(new Font("Arial",Font.PLAIN,18));
		menuEntities.add(text);
		GameLauncher launcher = new GameLauncher(450, 530, 100, 50,"Next Level!", menuHandler,true,false);
		launcher.setFont(new Font("Arial",Font.BOLD,16));
		menuEntities.add(launcher);
		//menuEntities.add(new GameLauncher(500, 530, 100, 50,"Go Battle!", menuHandler,true,false));
		text = new MenuText(200,130,0,0,"Player Stats","None",menuHandler);
		text.setFont(new Font("Arial",Font.BOLD,15));
		menuEntities.add(text);
		//menuEntities.add(new MenuText(100,100,0,0,"Player Stats","None",menuHandler));
		menuEntities.add(new MenuText(200,180,0,0,"Name ","Name",menuHandler));
		menuEntities.add(new MenuText(200,230,0,0,"Score ", "Score", menuHandler));
		menuEntities.add(new MenuText(200,280,0,0,"Lives ", "Lives",menuHandler));
		menuEntities.add(new MenuText(200,330,0,0,"Health ", "Health", menuHandler));
		menuEntities.add(new MenuText(200,380,0,0,"Special ", "Special",menuHandler));
		text = new MenuText(450,130,0,0,"Available Weapons","None",menuHandler);
		text.setFont(new Font("Arial",Font.BOLD,15));
		menuEntities.add(text);
		//menuEntities.add(new MenuText(300,100,0,0,"Available Weapons","None",menuHandler));
		populateWeaponsList();
		equipWeapons();
		setMenuEntities(menuEntities);
	}
	
	public void populateWeaponsList()
	{
		ArrayList<WeaponEntity> weapons = menuHandler.getPlayerProfile().getWeapons();
		for(int i = 0; i<weapons.size();i++)
		{
			menuEntities.add(new MenuText(450, (180+(i*50)), 0,0, weapons.get(i).toString(), "Nonen",menuHandler));
			//Obtain image of weapon's projectile to display as an icon
			WeaponEntity weapon = weapons.get(i);
			ArrayList<ProjectileEntity> projectiles = weapon.getProjectiles();
			if(projectiles.size()>0)
			{
				int factor = 1;
				if(projectiles.get(0) instanceof RocketProjectile ||
						projectiles.get(0) instanceof HomingMissileProjectile)
				{
					factor = 2;
				}
				menuEntities.add(new MenuIcon(700,(165+(i*50)),projectiles.get(0).getSprite().getWidth()*factor,
						projectiles.get(0).getSprite().getHeight()*factor,projectiles.get(0).getSprite().getImage()));
			}
		}
	}
	
	public void equipWeapons()
	{
		ArrayList<WeaponEntity> weapons = menuHandler.getPlayerProfile().getWeapons();
		for(WeaponEntity weapon: weapons)
		{
			weapon.setEquipped(true);
		}
	}
	
	//Inherited Methods
	/**
	 * Load Screen
	 * Loads all the weapons into the weapon containers.
	 */
	public void loadScreen()
	{
		setMenuEntities(menuEntities);
	}
}
