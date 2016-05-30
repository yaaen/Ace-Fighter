/**
 * @(#)LevelEditor1_0 -> Boss Dialog class
 *
 * This dialog appears when adding a boss to a level. The dialog allows a player to
 * add several boss components to the boss.
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

public class BossDialog extends JFrame
{
	private JPanel mainPanel = new JPanel();
	
	private JButton addCockpit = new JButton("Add Cockpit");
	private JButton addTurret = new JButton("Add Turret");
	private JButton addShield = new JButton("Add Shield");
	private JButton loadAIFile = new JButton("Load AI File");
	private JTextField AIFileText = new JTextField("None", 5);
	private JTextArea displayArea = new JTextArea(9,20);
	private JScrollPane infoPane = new JScrollPane(displayArea);
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	
	private GUI gui;	//the gui
	private LevelEntity level;	//the level
	private File currentDirectory;	//the current directory
	
	private ArrayList<BossComponent> bossComponents;
	private File bossAIFile = null;	//the AI file of the boss
	
	public BossDialog(GUI gui, LevelEntity level, File currentDirectory)
	{
		super("Add Boss");
		this.gui = gui;
		this.level = level;
		this.currentDirectory = currentDirectory;
		bossComponents = new ArrayList<BossComponent>();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		addBossButtonPanel();
		addInfoDisplayPanel();
		addConfirmCancelPanel();
		getContentPane().add(mainPanel);
		pack();
		setSize(400,400);
		setResizable(false);
		setVisible(true);
	}
	
	public void addBossButtonPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,0));
		JPanel topPanel = new JPanel();
		//JButton addCockpit = new JButton("Add Cockpit");
		addCockpit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateCockpitDialog();
			}
		});
		topPanel.add(addCockpit);
		//JButton addTurret = new JButton("Add Turret");
		addTurret.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateTurretDialog();
			}
		});
		topPanel.add(addTurret);
		//JButton addShield = new JButton("Add Shield");
		addShield.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateShieldDialog();
			}
		});
		topPanel.add(addShield);
		JPanel bottomPanel = new JPanel();
		//JButton loadAIFile = new JButton("Load AI File");
		loadAIFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadAIFile();
			}
		});
		bottomPanel.add(loadAIFile);
		innerPanel.add(topPanel);
		innerPanel.add(bottomPanel);
		outerPanel.add(innerPanel);
		mainPanel.add(outerPanel, BorderLayout.NORTH);
	}
	
	public void addInfoDisplayPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Boss Info"));
		//JTextArea displayArea = new JTextArea(7,10);
		displayArea.setEditable(false);
		//JScrollPane infoPane = new JScrollPane(displayArea);
		innerPanel.add(infoPane);
		outerPanel.add(innerPanel);
		mainPanel.add(outerPanel, BorderLayout.CENTER);
	}
	
	public void addConfirmCancelPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));
		JPanel innerPanel = new JPanel();
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
		mainPanel.add(outerPanel, BorderLayout.SOUTH);
	}
	
	public void generateCockpitDialog()
	{
		BossComponentDialog dialog = new BossComponentDialog(gui,this,currentDirectory,bossComponents, "Cockpit");
	}
	
	public void generateTurretDialog()
	{
		BossComponentDialog dialog = new BossComponentDialog(gui,this,currentDirectory,bossComponents,"Turret");
	}
	
	public void generateShieldDialog()
	{
		ShieldDialog dialog = new ShieldDialog(gui,this,currentDirectory,bossComponents);
	}
	
	public void loadAIFile()
	{
		JFileChooser aiFileChooser;
		if(currentDirectory==null)	//if this is the first time the file chooser is opened
		{
			aiFileChooser = new JFileChooser();
		}
		else	//if file chooser has been opened before, launch it in the directory that user was last in
		{
			aiFileChooser = new JFileChooser(currentDirectory);
		}
		int r = aiFileChooser.showOpenDialog(this);
		if(r==JFileChooser.APPROVE_OPTION)
		{
			currentDirectory = aiFileChooser.getSelectedFile();
			AIFileText.setText(aiFileChooser.getSelectedFile().getName());
			bossAIFile = aiFileChooser.getSelectedFile();
			displayBossInfo();
		}
	}
	
	public void displayBossInfo()
	{
		String output = "";
		for(int i = 0; i<bossComponents.size(); i++)
		{
			output += "Boss Component " + (i+1) + "'s Information: \n\n" + bossComponents.get(i).toString() + "\n\n";
		}
		output += "Additional Information: \n\n";
		if(bossAIFile==null)
			output+="A Boss AI file has not been loaded.";
		else
		{
			output+="A Boss AI file has been loaded.\n";
			output+="The filename is: " + bossAIFile.getName();
		}
		displayArea.setText(output);
	}
	
	public void confirmDialog()
	{
		BossEntity boss = new BossEntity(bossComponents, bossAIFile);
		level.getBosses().add(boss);
		this.dispose();
	}
}
