/**
 * @(#)LevelEditor1_0 -> Platform Dialog class
 *
 * This is the dialog that appears when adding a platform.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 April 28, 2008
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
public class PlatformDialog extends JFrame
{
	private JPanel mainPanel = new JPanel();
	private JPanel firstPanel = new JPanel();
	private JPanel secondPanel = new JPanel();
	private JPanel thirdPanel = new JPanel();
	private JPanel fourthPanel = new JPanel();
	
	//Coordinate panel components
	private JLabel xCoordLabel = new JLabel("XCoord: ");
	private JTextField xCoordText = new JTextField("0", 5);
	private JLabel yCoordLabel = new JLabel("YCoord: ");
	private JTextField yCoordText = new JTextField("0", 5);
	
	//Dimension panel components
	private JLabel widthLabel = new JLabel("Width: ");
	private JTextField widthText = new JTextField("0", 5);
	private JLabel heightLabel = new JLabel("Height: ");
	private JTextField heightText = new JTextField("0", 5);
	
	//Special panel components
	private JLabel propertyLabel = new JLabel("Property: ");
	private JComboBox propertySelection = new JComboBox(new String[] {"None", "Lava", "Electric", "Water", "ConveyorbeltRight",
																		"ConveyorbeltLeft"});
	private JLabel damageLabel = new JLabel("Damage: ");
	private JTextField damageText = new JTextField("0",5);
	
	//Movement panel components
	private JLabel directionLabel = new JLabel("Direction: ");
	private JComboBox directionSelection = new JComboBox(new String[] {"None", "Horizontal", "Vertical"});
	private JLabel speedLabel = new JLabel("Speed: ");
	private JTextField speedText = new JTextField("0",5);
	private JLabel rangeLabel = new JLabel("Range: ");
	private JTextField rangeText = new JTextField("0",5);
	
	//Graphical panel components
	private JButton loadImage = new JButton("Load an Image");
	private ImageDialog imageDialog = null;
	
	//Buttons
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	
	private GUI gui;	//the gui
	private LevelEntity level;	//the level
	private File currentDirectory;	//the current directory the user (JFileChooser) is in
	
	public PlatformDialog(GUI gui, LevelEntity level, File currentDirectory)
	{
		super("Platform Properties");
		this.gui = gui;
		this.level = level;
		this.currentDirectory = currentDirectory;
		mainPanel.setLayout(new GridLayout(4,0));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		firstPanel.setLayout(new GridLayout(2,0));
		secondPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		secondPanel.setLayout(new GridLayout(2,0));
		thirdPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
		thirdPanel.setLayout(new GridLayout(1,0));
		fourthPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		fourthPanel.setLayout(new FlowLayout());
		addPositionalPanel();
		addDimensionPanel();
		addSpecialPanel();
		addMovementPanel();
		addGraphicalPanel();
		addButtonPanel();
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		mainPanel.add(thirdPanel);
		mainPanel.add(fourthPanel);
		getContentPane().add(mainPanel);
		pack();
		setSize(500,850);
		setResizable(false);
		setVisible(true);
	}
	
	public void addPositionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Position"));
		JPanel innerPanel = new JPanel();
		innerPanel.add(xCoordLabel);
		innerPanel.add(xCoordText);
		innerPanel.add(yCoordLabel);
		innerPanel.add(yCoordText);
		outerPanel.add(innerPanel);
		firstPanel.add(outerPanel);
	}
	
	public void addDimensionPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dimensions"));
		JPanel innerPanel = new JPanel();
		innerPanel.add(widthLabel);
		innerPanel.add(widthText);
		innerPanel.add(heightLabel);
		innerPanel.add(heightText);
		outerPanel.add(innerPanel);
		firstPanel.add(outerPanel);
	}
	
	public void addSpecialPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Special Properties"));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout());
		innerPanel.add(propertyLabel);
		innerPanel.add(propertySelection);
		innerPanel.add(damageLabel);
		innerPanel.add(damageText);
		outerPanel.add(innerPanel);
		secondPanel.add(outerPanel);
	}
	
	public void addMovementPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movement"));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		JPanel topPanel = new JPanel();
		topPanel.add(directionLabel);
		topPanel.add(directionSelection);
		topPanel.add(speedLabel);
		topPanel.add(speedText);
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(rangeLabel);
		bottomPanel.add(rangeText);
		innerPanel.add(topPanel);
		innerPanel.add(bottomPanel);
		outerPanel.add(innerPanel);
		secondPanel.add(outerPanel);
	}
	
	public void addGraphicalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graphical"));
		JPanel innerPanel = new JPanel();
		loadImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				imageDialog = new ImageDialog(currentDirectory);
			}
		});
		innerPanel.add(loadImage);
		outerPanel.add(innerPanel);
		thirdPanel.add(outerPanel);
	}
	
	public void addButtonPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new FlowLayout());
		outerPanel.setBorder(BorderFactory.createEmptyBorder(20,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout());
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				closeDialog();
			}
		});
		innerPanel.add(cancelButton);
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addPlatform();
			}
		});
		innerPanel.add(okButton);
		outerPanel.add(innerPanel);
		fourthPanel.add(outerPanel);
	}
	
	/***********Additional Methods**********/
	public void closeDialog()
	{
		this.dispose();
	}
	
	/*Add Platform
	 *Adds a platform to the level with all the properties specified in the dialog(s).
	 *This is executed when the "ok" button of the dialog is clicked.
	 */
	public void addPlatform()
	{
		try
		{	
			Integer number = Integer.parseInt(xCoordText.getText());
			int platformX = number.intValue();
			number = Integer.parseInt(yCoordText.getText());
			int platformY = number.intValue();
			number = Integer.parseInt(widthText.getText());
			int platformWidth = number.intValue();
			number = Integer.parseInt(heightText.getText());
			int platformHeight = number.intValue();
			String property = (String)propertySelection.getSelectedItem();
			number = Integer.parseInt(damageText.getText());
			int damage = number.intValue();
			String movementDirection = (String)directionSelection.getSelectedItem();
			number = Integer.parseInt(speedText.getText());
			int movementSpeed = number.intValue();
			number = Integer.parseInt(rangeText.getText());
			int movementRange = number.intValue();
			if(imageDialog!=null)
			{
				currentDirectory = imageDialog.getCurrentDirectory();
				gui.setCurrentDirectory(currentDirectory);
				if(imageDialog.animated())	//if platform is an animated platform
				{
					AnimatedPlatformEntity platform = new AnimatedPlatformEntity(platformX,platformY,platformWidth,platformHeight);
					platform.setProperty(property);
					platform.setDamage(damage);
					platform.setMovement(movementDirection);
					platform.setMovementSpeed(movementSpeed);
					platform.setRange(movementRange);
					Image platformImage = imageDialog.getImage();
					platform.getSprite().setImage(platformImage);
					platform.getSprite().setImageFile(imageDialog.getImageFile());
					platform.getSprite().setImageWidth(imageDialog.getImageWidth());
					platform.getSprite().setImageHeight(imageDialog.getImageHeight());
					platform.getSprite().setFrameDelay(imageDialog.getFrameDelay());
					level.getAnimatedPlatforms().add(platform);
					gui.getLevelCanvas().scrollDisplayArea(platformX,platformY,true);
				}
				else	//if platform is not an animated platform
				{
					PlatformEntity platform = new PlatformEntity(platformX,platformY,platformWidth,platformHeight);
					platform.setProperty(property);
					platform.setDamage(damage);
					platform.setMovement(movementDirection);
					platform.setMovementSpeed(movementSpeed);
					platform.setRange(movementRange);
					Image platformImage = imageDialog.getImage();
					platform.getSprite().setImage(platformImage);
					platform.getSprite().setImageFile(imageDialog.getImageFile());
					platform.getSprite().setImageWidth(imageDialog.getImageWidth());
					platform.getSprite().setImageHeight(imageDialog.getImageHeight());
					level.getPlatforms().add(platform);
					gui.getLevelCanvas().scrollDisplayArea(platformX,platformY,true);
				}
			}
			else
			{
				PlatformEntity platform = new PlatformEntity(platformX,platformY,platformWidth,platformHeight);
				platform.setProperty(property);
				platform.setDamage(damage);
				platform.setMovement(movementDirection);
				platform.setMovementSpeed(movementSpeed);
				platform.setRange(movementRange);
				level.getPlatforms().add(platform);
				gui.getLevelCanvas().scrollDisplayArea(platformX,platformY,true);
			}
			closeDialog();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields.");
		}
	}
	
	//Accessor Methods
	/* Get Current Directory
	 * Allows the GUI to know which directory was last visited so that the next
	 * time a JFileChooser opens, the directory that was last visited, is set as
	 * the starting point for the JFileChooser.
	 */
	public File getCurrentDirectory()
	{
		return currentDirectory;
	}
}
