/**
 * @(#)LevelEditor1_0 -> Shield Dialog class
 *
 * This Dialog appears when adding a Shield to a boss.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 10, 2008
 */
 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class ShieldDialog extends JFrame
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
	private JButton loadFileButtonTab1 = new JButton("Load Character File");
	private JButton cancelButtonTab1 = new JButton("Cancel");
	private JButton okButtonTab1 = new JButton("OK");
	private JTextField nameText = new JTextField("Nameless",5);
	private JRadioButton damageable = new JRadioButton("Damageable", true);
	private JRadioButton notDamageable = new JRadioButton("Not Damageable", false);
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JTextField materialIntegrityText = new JTextField("0",5);
	private JButton loadFileButtonTab2 = new JButton("Load Character File");
	private JButton cancelButtonTab2 = new JButton("Cancel");
	private JButton okButtonTab2 = new JButton("OK");
	private JButton loadImage = new JButton("Load Image");
	
	private ImageDialog imageDialog = null;
	
	private GUI gui;
	private BossDialog bossDialog;	//allows the boss dialog to display updated boss components
	private File currentDirectory;
	private ArrayList<BossComponent> bossComponents;
	
	public ShieldDialog(GUI gui, BossDialog bossDialog, File currentDirectory, ArrayList<BossComponent> bossComponents)
	{
		super("Shield Properties");
		this.gui = gui;
		this.bossDialog = bossDialog;
		this.currentDirectory = currentDirectory;
		this.bossComponents = bossComponents;
		createGeneralTab();
		createAdditionalTab();
		tabbedPane.addTab("General", generalPanel);
		tabbedPane.addTab("Additional", additionalPanel);
		getContentPane().add(tabbedPane);
		pack();
		setSize(352,500);
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
		additionalPanel.setLayout(new GridLayout(2,0));
		additionalPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		firstAdditionalPanel.setLayout(new GridLayout(1,0));
		secondAdditionalPanel.setLayout(new GridLayout(1,0));
		thirdAdditionalPanel.setLayout(new GridLayout(1,0));
		addFirstInfoPanel();
		addButtonsToAdditionalPanel();
		additionalPanel.add(firstAdditionalPanel);
		additionalPanel.add(secondAdditionalPanel);	
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
		loadPanel.add(loadFileButtonTab1);
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
		//JRadioButton damageable = new JRadioButton("Damageable", true);
		radioButtonGroup.add(damageable);
		radioButtonGroup.add(notDamageable);
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
	
	public void addButtonsToAdditionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		JPanel loadPanel = new JPanel();
		//JButton loadButton = new JButton("Load Character File");
		loadPanel.add(loadFileButtonTab2);
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
		secondAdditionalPanel.add(outerPanel);
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
			component = new Shield(xCoord,yCoord,width,height);
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
			bossComponents.add(component);
			bossDialog.displayBossInfo();
			this.dispose();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields.");
		}
	}
}
