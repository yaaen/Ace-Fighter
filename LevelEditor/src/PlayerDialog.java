/**
 * @(#)LevelEditor1_0 -> Opponent Dialog class
 *
 * This is the Dialog that appears when adding an opponent to the level or
 * when editing the properties of an opponent in a level.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 01, 2008
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class PlayerDialog extends JFrame
{
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel generalPanel = new JPanel();
	private JPanel firstGeneralPanel = new JPanel();	//first panel within the General panel
	private JPanel secondGeneralPanel = new JPanel();	//second panel within the General panel
	private JPanel thirdGeneralPanel = new JPanel();
	private JPanel characterPanel = new JPanel();
	private JPanel firstCharacterPanel = new JPanel();	//the first panel within the Character panel
	private JPanel secondCharacterPanel = new JPanel();
	private JPanel thirdCharacterPanel = new JPanel();
	private JPanel morePanel = new JPanel();
	private JPanel firstMorePanel = new JPanel();
	private JPanel secondMorePanel = new JPanel();
	
	//General components
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton opponentRadioButton = new JRadioButton("Opponent", true);	//if this is selected, an opponent will be added
	private JRadioButton allyRadioButton = new JRadioButton("Ally", false);		//if this is selected, an ally will be added
	
	//Position panel components
	private JTextField xCoordText = new JTextField("0",5);
	private JTextField yCoordText = new JTextField("0",5);
	
	//Dimension panel components
	private JTextField widthText = new JTextField("0",5);
	private JTextField heightText = new JTextField("0",5);
	
	//Weapons panel components
	private JButton addWeaponButton = new JButton("Add Weapon");
	private JTextArea weaponsTextArea = new JTextArea(5,15);
	private WeaponsDialog weaponsDialog = null;
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	
	//Graphical panel components
	private JButton loadImage = new JButton("Load an Image");
	private ImageDialog imageDialog = null;
	
	private JButton loadButton = new JButton("Load Character File");
	private JButton cancelButtonTab1 = new JButton("Cancel");
	private JButton okButtonTab1 = new JButton("OK");
	private JTextField nameText = new JTextField("Nobody",5);
	private JComboBox difficultySelection = new JComboBox(new String[] {"Dummy", "Easy", "Medium", "Hard", "Expert"});
	private JTextField livesText = new JTextField("1",5);
	private JTextField healthText = new JTextField("50",5);
	private JTextField speedText = new JTextField("10",5);
	private JTextField jumpText = new JTextField("8",5);
	private JTextField firingText = new JTextField("5",5);
	private JTextField specialText = new JTextField("30",5);
	private JTextArea infoTextArea = new JTextArea(8,10);
	private JScrollPane infoPane = new JScrollPane(infoTextArea);
	private JButton cancelButtonTab2 = new JButton("Cancel");
	private JButton okButtonTab2 = new JButton("OK");
	private JTextField scoreText = new JTextField("10",5);
	private JComboBox powerupSelection = new JComboBox(new String[] {"None", "Health Pack", "Extra Life", "Score Multiplier",
																	"Health Expander", "Special Expander"});
	private	JLabel healthGainLabel = new JLabel("Health Gain: ");
	private JTextField healthGainText = new JTextField("5",5);
	private JLabel scoreMultiplierLabel = new JLabel("Multiply score by: ");
	private JComboBox scoreMultiplierSelection = new JComboBox(new String[] {"x2","x3","x4","x5","x10"});
	private JLabel healthExpanderLabel = new JLabel("Expand Health by: ");
	private JTextField healthExpanderText = new JTextField("5",5);
	private JLabel specialExpanderLabel = new JLabel("Expand special by: ");
	private JTextField specialExpanderText = new JTextField("5",5);
	private GUI gui;	//the gui
	private LevelEntity level;	//the level
	private File currentDirectory;	//the current directory
	
	public PlayerDialog(GUI gui, LevelEntity level, File currentDirectory)
	{
		super("Opponent Properties");
		this.gui = gui;
		this.level = level;
		this.currentDirectory = currentDirectory;
		createGeneralTab();
		createCharacterTab();
		createMoreTab();
		tabbedPane.addTab("General", generalPanel);
		tabbedPane.addTab("Character", characterPanel);
		tabbedPane.addTab("More", morePanel);
		getContentPane().add(tabbedPane);
		pack();
		setSize(500,650);
		setResizable(false);
		setVisible(true);	
	}
	
	public void createGeneralTab()
	{
		generalPanel.setLayout(new GridLayout(3,0));
		generalPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstGeneralPanel.setLayout(new GridLayout(3,0));
		secondGeneralPanel.setLayout(new GridLayout(1,0));
		thirdGeneralPanel.setLayout(new GridLayout(2,0));
		addRadioButtonPanel();
		addPositionalPanel();
		addDimensionPanel();
		addWeaponsPanel();
		addGraphicalPanel();
		addButtonsToGeneralPanel();
		generalPanel.add(firstGeneralPanel);
		generalPanel.add(secondGeneralPanel);
		generalPanel.add(thirdGeneralPanel);
	}
	
	public void createCharacterTab()
	{
		characterPanel.setLayout(new GridLayout(2,0));
		characterPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstCharacterPanel.setLayout(new GridLayout(2,0));
	//	secondCharacterPanel.setLayout(new GridLayout(2,0));
		secondCharacterPanel.setLayout(new BorderLayout());
		addFirstInfoPanel();
		addSecondInfoPanel();
		addDisplayInfoPanel();
		addButtonsToCharacterPanel();
		characterPanel.add(firstCharacterPanel);
		characterPanel.add(secondCharacterPanel);	
	}
	
	public void createMoreTab()
	{
		firstMorePanel.setLayout(new GridLayout(2,0));
		firstMorePanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		addFirstMorePanel();
		addSecondMorePanel();
		morePanel.add(firstMorePanel);
		morePanel.add(secondMorePanel);
	}
	
	/********************Panels for the General Tab****************************/
	
	public void addRadioButtonPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		radioButtonGroup.add(opponentRadioButton);
		radioButtonGroup.add(allyRadioButton);
		outerPanel.add(opponentRadioButton);
		outerPanel.add(allyRadioButton);
		firstGeneralPanel.add(outerPanel);
	}
	
	public void addPositionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Position"));
		JLabel xCoordLabel = new JLabel("XCoord: ");
		innerPanel.add(xCoordLabel);
		//JTextField xCoordText = new JTextField(5);
		innerPanel.add(xCoordText);
		JLabel yCoordLabel = new JLabel("YCoord: ");
		innerPanel.add(yCoordLabel);
		//JTextField yCoordText = new JTextField(5);
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
		//JTextArea weaponsTextArea = new JTextArea(5,10);
		weaponsTextArea.setEditable(false);
		JScrollPane weaponsPane = new JScrollPane(weaponsTextArea);
		textAreaPanel.add(weaponsPane);
		innerPanel.add(buttonPanel, BorderLayout.NORTH);
		innerPanel.add(labelPanel, BorderLayout.CENTER);
		innerPanel.add(textAreaPanel, BorderLayout.SOUTH);
		outerPanel.add(innerPanel);
		secondGeneralPanel.add(outerPanel);
	}

	public void addGraphicalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(0,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graphical"));
		loadImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				imageDialog = new ImageDialog(currentDirectory);
			}
		});
		innerPanel.add(loadImage);
		outerPanel.add(innerPanel);
		thirdGeneralPanel.add(outerPanel);
	}
	
	public void addButtonsToGeneralPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(1,0));
		JPanel loadPanel = new JPanel();
		//JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButton);
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
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel topPanel = new JPanel();
		JLabel nameLabel = new JLabel("Name: ");
		topPanel.add(nameLabel);
		//JTextField nameText = new JTextField(5);
		topPanel.add(nameText);
		JLabel difficultyLabel = new JLabel("AI Difficulty: ");
		topPanel.add(difficultyLabel);
		//JComboBox difficultySelection = new JComboBox(new String[] {"Dummy", "Easy", "Medium", "Hard", "Expert"});
		topPanel.add(difficultySelection);
		JPanel bottomPanel = new JPanel();
		JLabel livesLabel = new JLabel("Lives: ");
		bottomPanel.add(livesLabel);
		//JTextField livesText = new JTextField(5);
		bottomPanel.add(livesText);
		JLabel healthLabel = new JLabel("Health: ");
		bottomPanel.add(healthLabel);
		//JTextField healthText = new JTextField(5);
		bottomPanel.add(healthText);
		innerPanel.add(topPanel);
		innerPanel.add(bottomPanel);
		outerPanel.add(innerPanel);
		firstCharacterPanel.add(outerPanel);
	}
	
	public void addSecondInfoPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel topPanel = new JPanel();
		JLabel speedLabel = new JLabel("Speed: ");
		topPanel.add(speedLabel);
		//JTextField speedText = new JTextField(5);
		topPanel.add(speedText);
		JLabel jumpLabel = new JLabel("Jump: ");
		topPanel.add(jumpLabel);
		//JTextField jumpText = new JTextField(5);
		topPanel.add(jumpText);
		JPanel bottomPanel = new JPanel();
		JLabel firingLabel = new JLabel("Firing: ");
		bottomPanel.add(firingLabel);
		//JTextField firingText = new JTextField(5);
		bottomPanel.add(firingText);
		JLabel specialLabel = new JLabel("Special: ");
		bottomPanel.add(specialLabel);
		//JTextField specialText = new JTextField(5);
		bottomPanel.add(specialText);
		innerPanel.add(topPanel);
		innerPanel.add(bottomPanel);
		outerPanel.add(innerPanel);
		firstCharacterPanel.add(outerPanel);
	}
	
	public void addDisplayInfoPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Character Info"));
		JPanel labelPanel = new JPanel();
		JLabel infoLabel = new JLabel("Character Information");
		labelPanel.add(infoLabel);
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BorderLayout());
		//JTextArea infoTextArea = new JTextArea(8,10);
		infoTextArea.setEditable(false);
		//JScrollPane infoPane = new JScrollPane(infoTextArea);
		textAreaPanel.add(infoPane);
		innerPanel.add(textAreaPanel);
		outerPanel.add(innerPanel);
		secondCharacterPanel.add(outerPanel, BorderLayout.NORTH);
	}
	
	public void addButtonsToCharacterPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		JPanel loadPanel = new JPanel();
		//JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButton);
		JPanel okAndCancelPanel = new JPanel();
		//JButton cancelButton = new JButton("Cancel");
		okAndCancelPanel.add(cancelButtonTab2);
		//JButton okButton = new JButton("OK");
		okAndCancelPanel.add(okButtonTab2);
		innerPanel.add(loadPanel);
		innerPanel.add(okAndCancelPanel);
		outerPanel.add(innerPanel);
		secondCharacterPanel.add(outerPanel, BorderLayout.SOUTH);
	}
	
	public void addFirstMorePanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		innerPanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel scorePanel = new JPanel();
		JPanel powerupPanel = new JPanel();
		JLabel scoreLabel = new JLabel("Earnable Score: ");
		JLabel powerupLabel = new JLabel("Earnable Powerup: ");
		powerupSelection.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clickedPowerupSelection();
			}
		});
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreText);
		powerupPanel.add(powerupLabel);
		powerupPanel.add(powerupSelection);
		innerPanel.add(scorePanel);
		innerPanel.add(powerupPanel);
		outerPanel.add(innerPanel);
		firstMorePanel.add(outerPanel);
	}
	
	public void addSecondMorePanel()
	{
		JPanel panel = new JPanel();
//		JLabel healthGainLabel = new JLabel("Health Gain: ");
		healthGainLabel.setVisible(false);
//		JLabel scoreMultiplierLabel = new JLabel("Multiply score by: ");
		scoreMultiplierLabel.setVisible(false);
		healthGainText.setVisible(false);
		scoreMultiplierSelection.setVisible(false);
		healthExpanderLabel.setVisible(false);
		healthExpanderText.setVisible(false);
		specialExpanderLabel.setVisible(false);
		specialExpanderText.setVisible(false);
		panel.add(healthGainLabel);
		panel.add(healthGainText);
		panel.add(scoreMultiplierLabel);
		panel.add(scoreMultiplierSelection);
		panel.add(healthExpanderLabel);
		panel.add(healthExpanderText);
		panel.add(specialExpanderLabel);
		panel.add(specialExpanderText);
		secondMorePanel.add(panel);
	}
	
	public void spawnWeaponsDialog()
	{
		weaponsDialog = new WeaponsDialog(this, weapons);
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
	
	public void clickedPowerupSelection()
	{
		if(powerupSelection.getSelectedItem().equals("Health Pack"))
		{
			healthGainLabel.setVisible(true);
			healthGainText.setVisible(true);
			scoreMultiplierLabel.setVisible(false);
			scoreMultiplierSelection.setVisible(false);
			healthExpanderLabel.setVisible(false);
			healthExpanderText.setVisible(false);
			specialExpanderLabel.setVisible(false);
			specialExpanderText.setVisible(false);
		}
		else if(powerupSelection.getSelectedItem().equals("Score Multiplier"))
		{
			healthGainLabel.setVisible(false);
			healthGainText.setVisible(false);
			scoreMultiplierLabel.setVisible(true);
			scoreMultiplierSelection.setVisible(true);
			healthExpanderLabel.setVisible(false);
			healthExpanderText.setVisible(false);
			specialExpanderLabel.setVisible(false);
			specialExpanderText.setVisible(false);
		}
		else if(powerupSelection.getSelectedItem().equals("Health Expander"))
		{
			healthGainLabel.setVisible(false);
			healthGainText.setVisible(false);
			scoreMultiplierLabel.setVisible(false);
			scoreMultiplierSelection.setVisible(false);
			healthExpanderLabel.setVisible(true);
			healthExpanderText.setVisible(true);
			specialExpanderLabel.setVisible(false);
			specialExpanderText.setVisible(false);
		}
		else if(powerupSelection.getSelectedItem().equals("Special Expander"))
		{
			healthGainLabel.setVisible(false);
			healthGainText.setVisible(false);
			scoreMultiplierLabel.setVisible(false);
			scoreMultiplierSelection.setVisible(false);
			healthExpanderLabel.setVisible(false);
			healthExpanderText.setVisible(false);
			specialExpanderLabel.setVisible(true);
			specialExpanderText.setVisible(true);
		}
		else
		{
			healthGainLabel.setVisible(false);
			healthGainText.setVisible(false);
			scoreMultiplierLabel.setVisible(false);
			scoreMultiplierSelection.setVisible(false);
			healthExpanderLabel.setVisible(false);
			healthExpanderText.setVisible(false);
			specialExpanderLabel.setVisible(false);
			specialExpanderText.setVisible(false);
		}
	}
	
	public void confirmDialog()
	{
		try
		{		
			Integer number = Integer.parseInt(xCoordText.getText());
			int playerX = number.intValue();
			number = Integer.parseInt(yCoordText.getText());
			int playerY = number.intValue();
			number = Integer.parseInt(widthText.getText());
			int playerWidth = number.intValue();
			number = Integer.parseInt(heightText.getText());
			int playerHeight = number.intValue();
			AIEntity player = new AIEntity(playerX,playerY,playerWidth,playerHeight);
			if(weaponsDialog!=null)
				player.setSpecialWeapons(weapons);
			if(imageDialog!=null)
			{
				currentDirectory = imageDialog.getCurrentDirectory();
				gui.setCurrentDirectory(currentDirectory);
				Image image = imageDialog.getImage();
				player.getSprite().setImage(image);
				player.getSprite().setImageFile(imageDialog.getImageFile());
				player.getSprite().setImageWidth(imageDialog.getImageWidth());
				player.getSprite().setImageHeight(imageDialog.getImageHeight());
				player.getSprite().setFrameDelay(imageDialog.getFrameDelay());
			}
			player.setName(nameText.getText());
			player.setDifficulty((String)difficultySelection.getSelectedItem());
			Integer integer = Integer.parseInt(livesText.getText());
			player.setLives(integer.intValue());
			integer = Integer.parseInt(healthText.getText());
			player.setInitialHealth(integer.intValue());
			integer = Integer.parseInt(speedText.getText());
			player.setSpeed(integer.intValue());
			integer = Integer.parseInt(jumpText.getText());
			player.setJumpSpeed(integer.intValue());
			integer = Integer.parseInt(firingText.getText());
			player.setFiringRate(integer.intValue());
			integer = Integer.parseInt(specialText.getText());
			player.setSpecial(integer.intValue());
			integer = Integer.parseInt(scoreText.getText());
			player.setScoreValue(integer.intValue());
			String selectedPowerup = (String)powerupSelection.getSelectedItem();
			if(selectedPowerup.equals("Health Pack"))
			{
				integer = Integer.parseInt(healthGainText.getText());
				int healthGain = integer.intValue();					
				player.setPowerup(new HealthPackEntity(playerX,playerY,50,50,healthGain));
			}
			if(selectedPowerup.equals("Score Multiplier"))
			{
				int factor = 1;
				String selectedFactor = (String)scoreMultiplierSelection.getSelectedItem();
				if(selectedFactor.equals("x2"))
					factor = 2;
				if(selectedFactor.equals("x3"))
					factor = 3;
				if(selectedFactor.equals("x4"))
					factor = 4;
				if(selectedFactor.equals("x5"))
					factor = 5;
				if(selectedFactor.equals("x10"))
					factor = 10;
				player.setPowerup(new ScoreMultiplierEntity(playerX,playerY,50,50,factor));
			}
			if(selectedPowerup.equals("Health Expander"))
			{
				integer = Integer.parseInt(healthExpanderText.getText());
				int expansion = integer.intValue();
				player.setPowerup(new HealthExpanderEntity(playerX,playerY,50,50,expansion));
			}
			if(selectedPowerup.equals("Special Expander"))
			{
				integer = Integer.parseInt(specialExpanderText.getText());
				int expansion = integer.intValue();
				player.setPowerup(new SpecialExpanderEntity(playerX,playerY,50,50,expansion));
			}
			if(selectedPowerup.equals("Extra Life"))
			{
				player.setPowerup(new ExtraLifeEntity(playerX,playerY,50,50));
			}
			if(opponentRadioButton.isSelected())
				level.getOpponents().add(player);
			if(allyRadioButton.isSelected())
				level.getAllies().add(player);
			this.dispose();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields!");
		}
	}
}
