/**
 * @(#)LevelEditor1_0 -> Weapons Dialog class
 *
 * This is the Dialog that appears when adding a weapon to an opponent or an
 * ally.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 02, 2008
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.util.*;
public class WeaponsDialog extends JFrame
{
	private JPanel mainPanel = new JPanel();
	private JPanel listPanel = new JPanel();
	
	private JList weaponsList;
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();	//the list of weapons that this weapon dialog can contain. The weapons clicked
																				//on in the JList, make up the ArrayList of WeaponEntities.
	private PlayerDialog playerDialog;
	private BossComponentDialog bossComponentDialog;
	private LevelDialog levelDialog;
	
	public WeaponsDialog()
	{
		super("Weapons");
		playerDialog = null;
		bossComponentDialog = null;
		mainPanel.setLayout(new FlowLayout());
		addWeaponsList();
		mainPanel.add(listPanel);
		addButton();
		getContentPane().add(mainPanel);
		pack();
		setSize(200,300);
		setResizable(false);
		setVisible(true);
	}
	
	public WeaponsDialog(PlayerDialog playerDialog, ArrayList<WeaponEntity> weapons)
	{
		super("Weapons");
		this.playerDialog = playerDialog;
		this.weapons = weapons;
		mainPanel.setLayout(new FlowLayout());
		addWeaponsList();
		mainPanel.add(listPanel);
		addButton();
		getContentPane().add(mainPanel);
		pack();
		setSize(200,300);
		setResizable(false);
		setVisible(true);
	}
	
	public WeaponsDialog(BossComponentDialog bossComponentDialog, ArrayList<WeaponEntity> weapons)
	{
		super("Weapons");
		this.bossComponentDialog = bossComponentDialog;
		this.weapons = weapons;
		mainPanel.setLayout(new FlowLayout());
		addWeaponsList();
		mainPanel.add(listPanel);
		addButton();
		getContentPane().add(mainPanel);
		pack();
		setSize(200,300);
		setResizable(false);
		setVisible(true);
	}
	
	public WeaponsDialog(LevelDialog levelDialog, ArrayList<WeaponEntity> weapons)
	{
		super("Weapons");
		this.levelDialog = levelDialog;
		this.weapons = weapons;
		mainPanel.setLayout(new FlowLayout());
		addWeaponsList();
		mainPanel.add(listPanel);
		addButton();
		getContentPane().add(mainPanel);
		pack();
		setSize(200,300);
		setResizable(false);
		setVisible(true);
	}
	
	public void addWeaponsList()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons"));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		String[] weapons = {"Scatter Fire", "Bouncy Fire", "Grenades", "Rockets", 
				"Scatter Rockets", "Homing Missiles", "Gravity Ball"};
		weaponsList = new JList(weapons);
	//	weaponsList.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane weaponsPane = new JScrollPane(weaponsList);
	//	innerPanel.add(weaponsList);
		innerPanel.add(weaponsPane);
		outerPanel.add(innerPanel);
		listPanel.add(outerPanel);
	}
	
	public void addButton()
	{
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmDialog();
			}
		});
		buttonPanel.add(okButton);
		mainPanel.add(buttonPanel);
	}
	
	public ArrayList<WeaponEntity> getWeapons()
	{
		return weapons;
	}
	
	public void confirmDialog()
	{
		Object[] stringObjects = weaponsList.getSelectedValues();
		String[] weaponNames = new String[stringObjects.length];
		for(int i = 0;i<stringObjects.length; i++)
		{
			String weaponName = (String)stringObjects[i];
			weaponNames[i] = weaponName;
		}
		for(int i = 0; i<weaponNames.length; i++)
		{
			if(weaponNames[i].equals("Scatter Fire"))
			{
				weapons.add(new ScatterFireWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Bouncy Fire"))
			{
				weapons.add(new BounceFireWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Grenades"))
			{
				weapons.add(new GrenadeWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Rockets"))
			{
				weapons.add(new RocketWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Scatter Rockets"))
			{
				weapons.add(new ScatterRocketWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Homing Missiles"))
			{
				weapons.add(new HomingMissileWeapon(0,0,80,80));
			}
			if(weaponNames[i].equals("Gravity Ball"))
			{
				weapons.add(new GravityBallWeapon(0,0,80,80));
			}
		}
		if(playerDialog!=null)
		{
			playerDialog.refreshWeaponsTextArea(weapons);
		}
		if(bossComponentDialog!=null)
		{
			bossComponentDialog.refreshWeaponsTextArea(weapons);
		}
		if(levelDialog!=null)
		{
			levelDialog.refreshWeaponsTextArea();
		}
		this.dispose();
	}
}
