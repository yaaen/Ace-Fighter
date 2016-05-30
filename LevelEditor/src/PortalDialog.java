/**
 * @(#)LevelEditor1_1 -> Portal Dialog class
 *
 * This dialog appears when adding a portal aka exit to a level.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 11, 2008
 */
 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import java.awt.event.*;
import java.io.*;
public class PortalDialog extends JFrame
{
	private JPanel mainPanel = new JPanel();
	private JPanel firstPanel = new JPanel();
	private JPanel secondPanel = new JPanel();
	private JPanel thirdPanel = new JPanel();
	private JPanel fourthPanel = new JPanel();
	
	private JTextField xCoordText = new JTextField("0",4);
	private JTextField yCoordText = new JTextField("0",4);
	private JTextField widthText = new JTextField("50",5);
	private JTextField heightText = new JTextField("100",5);
	private ImageDialog imageDialog = null;
	private JButton loadImage = new JButton("Load Image");
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	
	private GUI gui;	//the gui
	private LevelEntity level;	//the level
	private File currentDirectory;	//the current directory the user (JFileChooser) is in
	
	public PortalDialog(GUI gui, LevelEntity level, File currentDirectory)
	{
		super("Portal Properties");
		this.gui = gui;
		this.level = level;
		this.currentDirectory = currentDirectory;
		mainPanel.setLayout(new GridLayout(4,0));
		firstPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		firstPanel.setLayout(new GridLayout(1,0));
		secondPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		secondPanel.setLayout(new GridLayout(1,0));
		thirdPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		thirdPanel.setLayout(new GridLayout(1,0));
		addPositionalPanel();
		addDimensionPanel();
		addGraphicalPanel();
		addButtonPanel();
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		mainPanel.add(thirdPanel);
		mainPanel.add(fourthPanel);
		getContentPane().add(mainPanel);
		pack();
		setSize(350,420);
		setResizable(false);
		setVisible(true);
	}
	
	public void addPositionalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(1,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Position"));
		JPanel innerPanel = new JPanel();
		JLabel xCoordLabel = new JLabel("XCoord: ");
		innerPanel.add(xCoordLabel);
		//JTextField xCoordText = new JTextField(4);
		innerPanel.add(xCoordText);
		JLabel yCoordLabel = new JLabel("YCoord: ");
		innerPanel.add(yCoordLabel);
		//JTextField yCoordText = new JTextField(4);
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
		JLabel widthLabel = new JLabel("Width: ");
		innerPanel.add(widthLabel);
		//JTextField widthText = new JTextField(5);
		innerPanel.add(widthText);
		JLabel heightLabel = new JLabel("Height: ");
		innerPanel.add(heightLabel);
		//JTextField heightText = new JTextField(5);
		innerPanel.add(heightText);
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
		//JButton cancelButton = new JButton("Cancel");
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
	
	public void confirmDialog()
	{
		try
		{	
			Integer integer = Integer.parseInt(xCoordText.getText());
			int xCoord = integer.intValue();
			integer = Integer.parseInt(yCoordText.getText());
			int yCoord = integer.intValue();
			integer = Integer.parseInt(widthText.getText());
			int width = integer.intValue();
			integer = Integer.parseInt(heightText.getText());
			int height = integer.intValue();
			PortalEntity portal = new PortalEntity(xCoord,yCoord,width,height);
			if(imageDialog!=null)
			{
				currentDirectory = imageDialog.getCurrentDirectory();
				gui.setCurrentDirectory(currentDirectory);
				portal.getSprite().setImage(imageDialog.getImage());
				portal.getSprite().setImageFile(imageDialog.getImageFile());
				portal.getSprite().setImageWidth(imageDialog.getImageWidth());
				portal.getSprite().setImageHeight(imageDialog.getImageHeight());
				portal.getSprite().setFrameDelay(imageDialog.getFrameDelay());
			}
			level.getPortals().add(portal);
			gui.getLevelCanvas().scrollDisplayArea(xCoord,yCoord,true);
			this.dispose();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal argument in the numeric fields.");
		}	
	}
}
