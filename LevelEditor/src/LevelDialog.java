/**
 * @(#)LevelEditor1_2 -> Level Dialog class
 *
 * This dialog appears when editing the properties of the level. Properties
 * that can be edited in this dialog are whether the level is side-scrolling
 * or static, the gravity and a background image can be set.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 12, 2008
 */

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class LevelDialog extends JFrame
{
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel mainPanel = new JPanel();
	private JPanel firstPanel = new JPanel();
	private JPanel secondPanel = new JPanel();
	private JPanel thirdPanel = new JPanel();
	private JPanel fourthPanel = new JPanel();
	private JPanel morePanel = new JPanel();
	private JPanel weaponsPanel = new JPanel();
	
	private ButtonGroup levelTypeButtonGroup = new ButtonGroup();
	private JRadioButton staticButton = new JRadioButton("Static", true);
	private JRadioButton sideScrollingButton = new JRadioButton("Side-Scrolling", false);
	private JSlider gravitySlider = new JSlider(1,10);
	private JLabel gravityLabel = new JLabel("Gravity: ");
	private JTextField gravityText = new JTextField("0.2",5);
	private JTextField xCoordText = new JTextField("0",5);
	private JTextField yCoordText = new JTextField("0",5);
	private JTextField imageText = new JTextField("Image Name", 5);
	private JButton loadButton = new JButton("Load Image");
	private JButton removeImageButton = new JButton("Remove Image");
	private JButton addWeaponButton = new JButton("Add Weapon");
	private JButton removeWeaponsButton = new JButton("Remove Weapons");
	private JTextArea weaponsTextArea = new JTextArea(4,15);
	private JTextField spawnText = new JTextField("100",5);
	private ArrayList<WeaponEntity> weapons = new ArrayList<WeaponEntity>();
	private WeaponsDialog weaponsDialog = null;
	private JTextField humanXText = new JTextField("0",5);
	private JTextField humanYText = new JTextField("0",5);
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	
	private ImageDialog imageDialog = null;
	private ColorSelector colorSelector = null;
	private Color backgroundColor;	//the background color of the level
	
	private GUI gui;
	private LevelEntity level;
	private File currentDirectory;
	
	public LevelDialog(GUI gui, LevelEntity level, File currentDirectory)
	{
		super("Level Properties");
		this.gui = gui;
		this.level = level;
		this.currentDirectory = currentDirectory;
		tabbedPane.addTab("General", mainPanel);
		tabbedPane.addTab("More", morePanel);
		createFirstTab();
		createSecondTab();
		getContentPane().add(tabbedPane);
		pack();
		setSize(400,650);
		setResizable(false);
		setVisible(true);
	}
	
	public void createFirstTab()
	{
		mainPanel.setLayout(new GridLayout(3,0));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstPanel.setLayout(new GridLayout(2,0));
		firstPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		secondPanel.setLayout(new GridLayout(1,0));
		secondPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		//thirdPanel.setLayout(new GridLayout(1,0));
		//thirdPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		fourthPanel.setLayout(new GridLayout(1,0));
		fourthPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		addLevelTypePanel();
		addGravityPanel();
		addBackgroundPanel();
		//addHumanPlayerPanel();
		addButtonPanel();
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
	//	mainPanel.add(thirdPanel);
		mainPanel.add(fourthPanel);
	}
	
	public void createSecondTab()
	{
		morePanel.setLayout(new GridLayout(3,0));
		morePanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		weaponsPanel.setLayout(new GridLayout(1,0));
		weaponsPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		thirdPanel.setLayout(new GridLayout(1,0));
		thirdPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));	
		addWeaponsPanel();
		addHumanPlayerPanel();
		morePanel.add(weaponsPanel);
		morePanel.add(thirdPanel);
	}
	
	public void addLevelTypePanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Level-Type"));
		JPanel innerPanel = new JPanel();
		//JRadioButton staticButton = new JRadioButton("Static", true);
		levelTypeButtonGroup.add(staticButton);
		levelTypeButtonGroup.add(sideScrollingButton);
		innerPanel.add(staticButton);
		//JRadioButton sideScrollingButton = new JRadioButton("Side-Scrolling", false);
		innerPanel.add(sideScrollingButton);
		outerPanel.add(innerPanel);
		firstPanel.add(outerPanel);
	}
	
	public void addGravityPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Gravity"));
		JPanel innerPanel = new JPanel();
		JLabel lowLabel = new JLabel("Low");
		//innerPanel.add(lowLabel);
		//JSlider gravitySlider = new JSlider();
		//innerPanel.add(gravitySlider);
		innerPanel.add(gravityLabel);
		innerPanel.add(gravityText);
		JLabel highLabel = new JLabel("High");
		//innerPanel.add(highLabel);
		outerPanel.add(innerPanel);
		firstPanel.add(outerPanel);
	}
	
	public void addBackgroundPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Background"));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		/*JPanel positionPanel = new JPanel();
		JLabel xCoordLabel = new JLabel("XCoord: ");
		positionPanel.add(xCoordLabel);
		//JTextField xCoordText = new JTextField(5);
		positionPanel.add(xCoordText);
		JLabel yCoordLabel = new JLabel("YCoord: ");
		positionPanel.add(yCoordLabel);
		//JTextField yCoordText = new JTextField(5);
		positionPanel.add(yCoordText);
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		JLabel imageLabel = new JLabel("Image: ");
		northPanel.add(imageLabel);
		//JTextField imageText = new JTextField(5);
		northPanel.add(imageText);
		JPanel centerPanel = new JPanel();
		//JButton loadButton = new JButton("Load Image");
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				imageDialog = new ImageDialog(currentDirectory);
			}
		});
		centerPanel.add(loadButton);
		removeImageButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeBackgroundImage();
			}
		});
		centerPanel.add(removeImageButton);
		imagePanel.add(northPanel, BorderLayout.NORTH);
		imagePanel.add(centerPanel, BorderLayout.CENTER);
		innerPanel.add(positionPanel, BorderLayout.NORTH);
//		innerPanel.add(dimensionPanel, BorderLayout.CENTER);
		innerPanel.add(imagePanel, BorderLayout.CENTER);*/
		JButton addBackgroundColorButton = new JButton("Add Background Color");
		addBackgroundColorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addBackgroundColor();
			}
		});
		innerPanel.add(addBackgroundColorButton);
		outerPanel.add(innerPanel);
		secondPanel.add(outerPanel);
	}
	
	public void addWeaponsPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		//outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Available Weapons"));
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
		removeWeaponsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Clear weapons list
				level.getWeapons().clear();
			}
		});
		buttonPanel.add(removeWeaponsButton);
		JPanel textAreaPanel = new JPanel();
		//JTextArea weaponsTextArea = new JTextArea(5,10);
		weaponsTextArea.setEditable(false);
		JScrollPane weaponsPane = new JScrollPane(weaponsTextArea);
		textAreaPanel.add(weaponsPane);
		JPanel spawnPanel = new JPanel();
		JLabel spawnLabel = new JLabel("Item Spawn Time: ");
		spawnPanel.add(spawnLabel);
		spawnPanel.add(spawnText);
		innerPanel.add(buttonPanel, BorderLayout.NORTH);
		innerPanel.add(textAreaPanel, BorderLayout.CENTER);
		innerPanel.add(spawnPanel,BorderLayout.SOUTH);
		outerPanel.add(innerPanel);
		weaponsPanel.add(outerPanel);
	}
	
	public void addHumanPlayerPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Human Player Info"));
		JPanel innerPanel = new JPanel();
		JLabel humanXLabel = new JLabel("Human X: ");
		//JTextField humanXText = new JTextField("0",5);
		JLabel humanYLabel = new JLabel("Human Y: ");
		//JTextField humanYText = new JTextField("0",5);
		innerPanel.add(humanXLabel);
		innerPanel.add(humanXText);
		innerPanel.add(humanYLabel);
		innerPanel.add(humanYText);
		outerPanel.add(innerPanel);
		thirdPanel.add(outerPanel);
	}
	
	public void addButtonPanel()
	{
		JPanel outerPanel = new JPanel();
		JPanel innerPanel = new JPanel();
		//JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				closeDialog();
			}
		});
		innerPanel.add(cancelButton);
		//JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmDialog();
			}
		});
		innerPanel.add(okButton);
		outerPanel.add(innerPanel);
		fourthPanel.add(outerPanel);
	}
	
	public void addBackgroundColor()
	{
		//colorSelector = new ColorSelector();
		//Set up color chooser for setting the color
		backgroundColor = JColorChooser.showDialog(this,"Choose Background Color",Color.BLUE);
	}
	
	public void removeBackgroundImage()
	{
		level.setBackgroundImage(null);
	}
	
	public void spawnWeaponsDialog()
	{
		weaponsDialog = new WeaponsDialog(this,weapons);
	}
	
	/** Append Weapons To Text Area
	 *@param The weapons to be added to the text area.
	 *Appends the given list of weapons to the text area so that the user can easily see what weapons the player is equipped with.
	 */
	public void refreshWeaponsTextArea()
	{
		String s = "";
		for(int i = 0; i<weapons.size(); i++)
		{
			s+=weapons.get(i).toString() + "\n";
		}
		weaponsTextArea.setText(s);
	}
	
	public void closeDialog()
	{
		this.dispose();
	}
	
	public void confirmDialog()
	{
		try
		{		
			boolean sideScrolling = false;
			if(staticButton.isSelected())
				sideScrolling = false;
			if(sideScrollingButton.isSelected())
				sideScrolling = true;
			Double doubleValue = Double.parseDouble(gravityText.getText());
			double gravity = doubleValue.doubleValue();
			Integer integer = Integer.parseInt(xCoordText.getText());
			int backgroundX = integer.intValue();
			integer = Integer.parseInt(yCoordText.getText());
			int backgroundY = integer.intValue();
			if(imageDialog!=null)
			{
				currentDirectory = imageDialog.getCurrentDirectory();
				gui.setCurrentDirectory(currentDirectory);
				Image backgroundImage = imageDialog.getImage();
				int backgroundWidth = imageDialog.getImageWidth();
				int backgroundHeight = imageDialog.getImageHeight();
				level.setBackgroundX(backgroundX);
				level.setBackgroundY(backgroundY);
				level.setBackgroundWidth(backgroundWidth);
				level.setBackgroundHeight(backgroundHeight);
				level.setBackgroundImage(backgroundImage);
				level.setBackgroundImageFile(imageDialog.getImageFile());
				
			}
			
			level.setBackgroundColor(backgroundColor);
			
			if(weaponsDialog!=null)
			{
				level.setWeapons(weapons);
			}
			integer = Integer.parseInt(spawnText.getText());
			level.setItemSpawnTime(integer.intValue());
			integer = Integer.parseInt(humanXText.getText());
			int humanX = integer.intValue();
			integer = Integer.parseInt(humanYText.getText());
			int humanY = integer.intValue();
			level.setHumanX(humanX);
			level.setHumanY(humanY);
			
			//Setting the level's properties
			if(sideScrolling)
				level.setStaticOrDynamic("Dynamic");
			else
				level.setStaticOrDynamic("Static");
			level.setGravity(gravity);
			closeDialog();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null,"Illegal Argument in numeric fields!");
		}
	}
}
