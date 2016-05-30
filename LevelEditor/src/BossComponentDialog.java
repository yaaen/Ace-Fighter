/**
 * @(#)LevelEditor1_0 -> Cockpit Dialog class
 *
 * This dialog appears when adding a BossComponent (either a Cockpit or a Turret) to a Boss.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 06, 2008
 */
 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BossComponentDialog extends JFrame
{
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel generalPanel = new JPanel();
	private JPanel firstGeneralPanel = new JPanel();	//first panel within the General panel
	private JPanel secondGeneralPanel = new JPanel();	//second panel within the General panel
	private JPanel thirdGeneralPanel = new JPanel();
	private JPanel additionalPanel = new JPanel();
	private JPanel firstAdditionalPanel = new JPanel();	//the first panel within the Character panel
	private JPanel secondAdditionalPanel = new JPanel();
	private JPanel thirdAdditionalPanel = new JPanel();
	
	private JTextField xCoordText = new JTextField("0",4);
	private JTextField yCoordText = new JTextField("0",4);
	private JTextField widthText = new JTextField("50",5);
	private JTextField heightText = new JTextField("50",5);
	private JTextField imageText = new JTextField("None",5);
	private JButton loadImage = new JButton("Load Image");
	private JButton loadButtonTab1 = new JButton("Load Character File");
	private JButton cancelButtonTab1 = new JButton("Cancel");
	private JButton okButtonTab1 = new JButton("OK");
	private JTextField nameText = new JTextField("Nameless",5);
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton damageable = new JRadioButton("Damageable", true);
	private JRadioButton notDamageable = new JRadioButton("Not Damageable", false);
	private JTextField materialIntegrityText = new JTextField("0",5);
	private JButton addWeaponButton = new JButton("Add Weapon");
	private JTextArea weaponsTextArea = new JTextArea(4,10);
	private JScrollPane weaponsPane = new JScrollPane(weaponsTextArea);
	private JButton loadButtonTab2 = new JButton("Load Character File");
	private JButton cancelButtonTab2 = new JButton("Cancel");
	private JButton okButtonTab2 = new JButton("OK");
	
	private ImageDialog imageDialog = null;
	private WeaponsDialog weaponsDialog = null;
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	
	private GUI gui;
	private BossDialog bossDialog;	//the boss dialog -> allows boss dialog to be updated once component is added (to display components)
	private File currentDirectory;
	
	private ArrayList<BossComponent> bossComponents;	//the list of boss components that the resulting cockpit of this dialog should be added to
	private String componentType;	//determines what type of boss component will be added by this dialog (either a cockpit or a turret)
	
	public BossComponentDialog(GUI gui, BossDialog bossDialog, File currentDirectory, ArrayList<BossComponent> bossComponents, String componentType)
	{
		super("Component Properties");
		this.gui = gui;
		this.bossDialog = bossDialog;
		this.currentDirectory = currentDirectory;
		this.bossComponents = bossComponents;
		this.componentType = componentType;
		createGeneralTab();
		createAdditionalTab();
		tabbedPane.addTab("General", generalPanel);
		tabbedPane.addTab("Additional", additionalPanel);
		getContentPane().add(tabbedPane);
		pack();
		setSize(352,550);
		setResizable(false);
		setVisible(true);	
	}
	
	public void createGeneralTab()
	{
		generalPanel.setLayout(new GridLayout(3,0));
		generalPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstGeneralPanel.setLayout(new GridLayout(2,0));
		secondGeneralPanel.setLayout(new GridLayout(1,0));
		thirdGeneralPanel.setLayout(new GridLayout(2,0));
		addPositionalPanel();
		addDimensionPanel();
		addGraphicalPanel();
		addButtonsToGeneralPanel();
		generalPanel.add(firstGeneralPanel);
		generalPanel.add(secondGeneralPanel);
		generalPanel.add(thirdGeneralPanel);
	}
	
	public void createAdditionalTab()
	{
		additionalPanel.setLayout(new GridLayout(3,0));
		additionalPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstAdditionalPanel.setLayout(new GridLayout(1,0));
		secondAdditionalPanel.setLayout(new GridLayout(1,0));
		thirdAdditionalPanel.setLayout(new GridLayout(1,0));
		addFirstInfoPanel();
		addWeaponsPanel();
		addButtonsToAdditionalPanel();
		additionalPanel.add(firstAdditionalPanel);
		additionalPanel.add(secondAdditionalPanel);	
		additionalPanel.add(thirdAdditionalPanel);
	}
	
	/********************Panels for the General Tab****************************/
	
	public void addPositionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Position"));
		JLabel xCoordLabel = new JLabel("XCoord: ");
		innerPanel.add(xCoordLabel);
		//JTextField xCoordText = new JTextField(4);
		innerPanel.add(xCoordText);
		JLabel yCoordLabel = new JLabel("YCoord: ");
		innerPanel.add(yCoordLabel);
		//JTextField yCoordText = new JTextField(4);
		innerPanel.add(yCoordText);
		outerPanel.add(innerPanel);
		firstGeneralPanel.add(outerPanel);
	}
	
	public void addDimensionPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dimensions"));
		JLabel widthLabel = new JLabel("Width: ");
		innerPanel.add(widthLabel);
		//JTextField widthText = new JTextField(5);
		innerPanel.add(widthText);
		JLabel heightLabel = new JLabel("Height: ");
		innerPanel.add(heightLabel);
		//JTextField heightText = new JTextField(5);
		innerPanel.add(heightText);
		outerPanel.add(innerPanel);
		firstGeneralPanel.add(outerPanel);
	}
	
	public void addGraphicalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Graphical"));
		JPanel innerPanel = new JPanel();
		loadImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				imageDialog = new ImageDialog(currentDirectory);
			}
		});
		innerPanel.add(loadImage);
		borderPanel.add(innerPanel);
		outerPanel.add(borderPanel);
		secondGeneralPanel.add(outerPanel);
	}
	
	public void addButtonsToGeneralPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(1,0));
		JPanel loadPanel = new JPanel();
		//JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButtonTab1);
		JPanel okAndCancelPanel = new JPanel();
		//JButton cancelButton = new JButton("Cancel");
		okAndCancelPanel.add(cancelButtonTab1);
		//JButton okButton = new JButton("OK");
		okButtonTab1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmDialog();
			}
		});
		okAndCancelPanel.add(okButtonTab1);
		innerPanel.add(okAndCancelPanel);
		outerPanel.add(innerPanel);
		thirdGeneralPanel.add(outerPanel);
	}
	
	/*******************Panels for the Character Tab***************************/
	public void addFirstInfoPanel()
	{		
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(3,0));
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "General"));
		JPanel panel1 = new JPanel();
		JLabel nameLabel = new JLabel("Name: ");
		panel1.add(nameLabel);
		//JTextField nameText = new JTextField(5);
		panel1.add(nameText);
		JPanel panel2 = new JPanel();
		radioButtonGroup.add(damageable);
		radioButtonGroup.add(notDamageable);
		//JRadioButton damageable = new JRadioButton("Damageable", true);
		panel2.add(damageable);
		//JRadioButton notDamageable = new JRadioButton("Not Damageable", false);
		panel2.add(notDamageable);
		JPanel panel3 = new JPanel();
		JLabel materialIntegrityLabel = new JLabel("Material Integrity: ");
		panel3.add(materialIntegrityLabel);
		//JTextField materialIntegrityText = new JTextField(5);
		panel3.add(materialIntegrityText);
		innerPanel.add(panel1);
		innerPanel.add(panel2);
		innerPanel.add(panel3);
		outerPanel.add(innerPanel);
		firstAdditionalPanel.add(outerPanel);
	}
	
	public void addWeaponsPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Weapons"));
		JPanel buttonPanel = new JPanel();
		//JButton addWeaponButton = new JButton("Add Weapon");
		addWeaponButton.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				spawnWeaponsDialog();
			}
		});
		buttonPanel.add(addWeaponButton);
		JPanel labelPanel = new JPanel();
		JLabel weaponsLabel = new JLabel("Weapons:");
		labelPanel.add(weaponsLabel);
		JPanel textAreaPanel = new JPanel();
		//JTextArea weaponsTextArea = new JTextArea(4,10);
		weaponsTextArea.setEditable(false);
		//JScrollPane weaponsPane = new JScrollPane(weaponsTextArea);
		textAreaPanel.add(weaponsPane);
		innerPanel.add(buttonPanel, BorderLayout.NORTH);
		innerPanel.add(labelPanel, BorderLayout.CENTER);
		innerPanel.add(textAreaPanel, BorderLayout.SOUTH);
		outerPanel.add(innerPanel);
		secondAdditionalPanel.add(outerPanel);
	}
	
	public void addButtonsToAdditionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		JPanel loadPanel = new JPanel();
		//JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButtonTab2);
		JPanel okAndCancelPanel = new JPanel();
		//JButton cancelButton = new JButton("Cancel");
		okAndCancelPanel.add(cancelButtonTab2);
		//JButton okButton = new JButton("OK");
		okButtonTab2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmDialog();
			}
		});
		okAndCancelPanel.add(okButtonTab2);
		innerPanel.add(okAndCancelPanel);
		outerPanel.add(innerPanel);
		thirdAdditionalPanel.add(outerPanel);
	}
	
	public void spawnWeaponsDialog()
	{
		weaponsDialog = new WeaponsDialog(this,weapons);
	}
	
	public void confirmDialog()
	{
		try
		{		
			BossComponent component;
			Integer integer = Integer.parseInt(xCoordText.getText());
			int xCoord = integer.intValue();
			integer = Integer.parseInt(yCoordText.getText());
			int yCoord = integer.intValue();
			integer = Integer.parseInt(widthText.getText());
			int width = integer.intValue();
			integer = Integer.parseInt(heightText.getText());
			int height = integer.intValue();
			if(componentType.equals("Cockpit"))
				component = new Cockpit(xCoord,yCoord,width,height);
			else
				component = new Turret(xCoord,yCoord,width,height);
			if(imageDialog!=null)
			{
				currentDirectory = imageDialog.getCurrentDirectory();
				gui.setCurrentDirectory(currentDirectory);
				Image image = imageDialog.getImage();
				component.getSprite().setImage(image);
				component.getSprite().setImageFile(imageDialog.getImageFile());
				component.getSprite().setImageWidth(imageDialog.getImageWidth());
				component.getSprite().setImageHeight(imageDialog.getImageHeight());
				component.getSprite().setFrameDelay(imageDialog.getFrameDelay());
			}
			component.setName(nameText.getText());
			if(damageable.isSelected())
			{
				component.setDamageable(true);
				integer = Integer.parseInt(materialIntegrityText.getText());
				int materialIntegrity = integer.intValue();
				component.setInitialHealth(materialIntegrity);	
			}
			if(notDamageable.isSelected())
				component.setDamageable(false);
			if(weaponsDialog!=null)
			{
				component.setWeapons(weapons);
				for(WeaponEntity weapon: weapons)
				{
					weapon.setOwner(component);
				}
			}
			bossComponents.add(component);	//add component to list of boss components so that the boss can access them
			bossDialog.displayBossInfo();
			this.dispose();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields");
		}
	}
	
	/** Append Weapons To Text Area
	 *@param The weapons to be added to the text area.
	 *Appends the given list of weapons to the text area so that the user can easily see what weapons the player is equipped with.
	 */
	public void refreshWeaponsTextArea(ArrayList<WeaponEntity> weapons)
	{
		for(int i = 0; i<weapons.size(); i++)
		{
			weaponsTextArea.append(weapons.get(i).toString() + "\n");
		}
	}
}

