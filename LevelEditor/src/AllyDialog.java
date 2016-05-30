/**
 * @(#)LevelEditor1_0 -> Ally Dialog class
 *
 * This is the Dialog that appears when adding an ally to the level or
 * when editing the properties of an ally in a level.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 06, 2008
 */

/*import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
public class AllyDialog extends JFrame
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
	
	public AllyDialog()
	{
		super("Ally Properties");
		createGeneralTab();
		createCharacterTab();
		tabbedPane.addTab("General", generalPanel);
		tabbedPane.addTab("Character", characterPanel);
		getContentPane().add(tabbedPane);
		pack();
		setSize(400,650);
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
	
	/********************Panels for the General Tab****************************
	
	public void addPositionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Position"));
		JLabel xCoordLabel = new JLabel("XCoord: ");
		innerPanel.add(xCoordLabel);
		JTextField xCoordText = new JTextField(5);
		innerPanel.add(xCoordText);
		JLabel yCoordLabel = new JLabel("YCoord: ");
		innerPanel.add(yCoordLabel);
		JTextField yCoordText = new JTextField(5);
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
		JTextField widthText = new JTextField(5);
		innerPanel.add(widthText);
		JLabel heightLabel = new JLabel("Height: ");
		innerPanel.add(heightLabel);
		JTextField heightText = new JTextField(5);
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
		JButton addWeaponButton = new JButton("Add Weapon");
		addWeaponButton.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent e)
			{
				new WeaponsDialog();
			}
		});
		buttonPanel.add(addWeaponButton);
		JPanel labelPanel = new JPanel();
		JLabel weaponsLabel = new JLabel("Weapons:");
		labelPanel.add(weaponsLabel);
		JPanel textAreaPanel = new JPanel();
		JTextArea weaponsTextArea = new JTextArea(5,10);
		weaponsTextArea.setLineWrap(true);
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
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new FlowLayout());
		borderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graphical"));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new FlowLayout());
		JLabel imageLabel = new JLabel("Image: ");
		imagePanel.add(imageLabel);
		JTextField imageText = new JTextField(5);
		imagePanel.add(imageText);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton browseButton = new JButton("Browse");
		buttonPanel.add(browseButton, BorderLayout.CENTER);
		innerPanel.add(imagePanel, BorderLayout.NORTH);
		innerPanel.add(buttonPanel, BorderLayout.CENTER);
		borderPanel.add(innerPanel);
		outerPanel.add(borderPanel);
		thirdGeneralPanel.add(outerPanel);
	}
	
	public void addButtonsToGeneralPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(1,0));
		JPanel loadPanel = new JPanel();
		JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButton);
		JPanel okAndCancelPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		okAndCancelPanel.add(cancelButton);
		JButton okButton = new JButton("OK");
		okAndCancelPanel.add(okButton);
		innerPanel.add(okAndCancelPanel);
		outerPanel.add(innerPanel);
		thirdGeneralPanel.add(outerPanel);
	}
	
	/*******************Panels for the Character Tab***************************
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
		JTextField nameText = new JTextField(5);
		topPanel.add(nameText);
		JLabel difficultyLabel = new JLabel("AI Difficulty: ");
		topPanel.add(difficultyLabel);
		JComboBox difficultySelection = new JComboBox(new String[] {"Dummy", "Easy", "Medium", "Hard", "Expert"});
		topPanel.add(difficultySelection);
		JPanel bottomPanel = new JPanel();
		JLabel livesLabel = new JLabel("Lives: ");
		bottomPanel.add(livesLabel);
		JTextField livesText = new JTextField(5);
		bottomPanel.add(livesText);
		JLabel healthLabel = new JLabel("Health: ");
		bottomPanel.add(healthLabel);
		JTextField healthText = new JTextField(5);
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
		JTextField speedText = new JTextField(5);
		topPanel.add(speedText);
		JLabel jumpLabel = new JLabel("Jump: ");
		topPanel.add(jumpLabel);
		JTextField jumpText = new JTextField(5);
		topPanel.add(jumpText);
		JPanel bottomPanel = new JPanel();
		JLabel firingLabel = new JLabel("Firing: ");
		bottomPanel.add(firingLabel);
		JTextField firingText = new JTextField(5);
		bottomPanel.add(firingText);
		JLabel specialLabel = new JLabel("Special: ");
		bottomPanel.add(specialLabel);
		JTextField specialText = new JTextField(5);
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
		JTextArea infoTextArea = new JTextArea(8,10);
		infoTextArea.setEditable(false);
		JScrollPane infoPane = new JScrollPane(infoTextArea);
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
		JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadButton);
		JPanel okAndCancelPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		okAndCancelPanel.add(cancelButton);
		JButton okButton = new JButton("OK");
		okAndCancelPanel.add(okButton);
		innerPanel.add(loadPanel);
		innerPanel.add(okAndCancelPanel);
		outerPanel.add(innerPanel);
		secondCharacterPanel.add(outerPanel, BorderLayout.SOUTH);
	}
}*/
