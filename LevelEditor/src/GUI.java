/**
 * @(#)LevelEditor1_0 -> GUI class
 *
 * This class represents the GUI of the Level Editor. It displays the
 * editor.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 April 28, 2008
 */

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
public class GUI extends JFrame
{
	//GUI Elements
	private LevelCanvas levelCanvas = new LevelCanvas();
	private JScrollPane mainScrollPane = new JScrollPane(levelCanvas);
	private JPopupMenu popup = new JPopupMenu();
	
	//Remaining elements	
	private LevelEntity level = new LevelEntity();	//the level
	private File currentDirectory = null;	//the current directory the JFileChooser is in. If it's null, then the JFileChooser
											//has not been opened yet (it will be the first time it's opened)
	private boolean editMode = true;	//if this variable is true, then no images are drawn, only colored blocks.
								//this allows the user to edit the level without displaying images, speeding up
								//the program.
	private JTextField deadLineText;
	private int deadLineY = 500;	//y coordinate of the deadline (the deadline is always horizontal)
	
	public GUI()
	{
		super("Level Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		generateDropDownMenu();
		setLayout(new BorderLayout());
		mainScrollPane.setBorder(BorderFactory.createEtchedBorder());
		mainScrollPane.setPreferredSize(new Dimension(800,600));
		getContentPane().add(mainScrollPane, BorderLayout.CENTER);
		JPanel topPanel = new JPanel();
		JLabel infoLabel = new JLabel("Use the buttons on the right to add components to the level.");
		topPanel.add(infoLabel);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		JTabbedPane rightHandPane = new JTabbedPane();
		JPanel generalPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(8,0));
		JButton addPlatform = new JButton("Add Platform");
		addPlatform.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generatePlatformDialog();
			}
		});
		buttonPanel.add(addPlatform);
		JButton addWall = new JButton("Add Wall");
		addWall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateWallDialog();
			}
		});
		buttonPanel.add(addWall);
		JButton addOpponent = new JButton("Add Opponent");
		addOpponent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generatePlayerDialog();
			}
		});
		buttonPanel.add(addOpponent);
		JButton addAlly = new JButton("Add Ally");
		addAlly.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generatePlayerDialog();
			}
		});
		buttonPanel.add(addAlly);
		JButton addBoss = new JButton("Add Boss");
		addBoss.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateBossDialog();
			}
		});
		buttonPanel.add(addBoss);
		JButton addCheckpoint = new JButton("Add Checkpoint");
		addCheckpoint.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateCheckPointDialog();
			}
		});
		buttonPanel.add(addCheckpoint);
		JButton addExit = new JButton("Add Exit");
		addExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generatePortalDialog();
			}
		});
		buttonPanel.add(addExit);
		JButton addForegroundImage = new JButton("Add Decoration");
		addForegroundImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateDecorationDialog();
			}
		});
		buttonPanel.add(addForegroundImage);
		generalPanel.add(buttonPanel);
		JPanel morePanel = new JPanel();
		morePanel.setLayout(new GridLayout(2,1));
		JPanel topMorePanel = new JPanel();
		topMorePanel.setLayout(new GridLayout(1,2));
		JPanel runningTogglePanel = new JPanel();
		runningTogglePanel.setLayout(new GridLayout(2,0));
		JButton toggleOn = new JButton("Update Loop ON");
		toggleOn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				levelCanvas.turnUpdateLoopOn();
			}
		});
		runningTogglePanel.add(toggleOn);
		JButton toggleOff = new JButton("Update Loop OFF");
		toggleOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				levelCanvas.turnUpdateLoopOff();
			}
		});
		JPanel editModePanel = new JPanel();
		editModePanel.setLayout(new GridLayout(2,1));
		JRadioButton edit = new JRadioButton("Edit Mode", true);
		edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				editMode = true;
			}
		});
		JRadioButton preview = new JRadioButton("Preview Mode", false);
		preview.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				editMode = false;
			}
		});
		ButtonGroup editPreviewGroup = new ButtonGroup();
		editPreviewGroup.add(edit);
		editPreviewGroup.add(preview);
		editModePanel.add(edit);
		editModePanel.add(preview);
		//morePanel.add(editModePanel);
		//buttonPanel.add(editModePanel);
		runningTogglePanel.add(toggleOff);
		topMorePanel.add(editModePanel);
		topMorePanel.add(runningTogglePanel);
		//morePanel.add(runningTogglePanel);
		JPanel bottomMorePanel = new JPanel();
		bottomMorePanel.setLayout(new GridLayout(3,1));
		JPanel deadLinePanel = new JPanel();
		deadLinePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Deadline"));
		deadLinePanel.setLayout(new GridLayout(2,1));
		JPanel topDeadLinePanel = new JPanel();
		topDeadLinePanel.setLayout(new GridLayout(1,3));
		JLabel deadLineLabel = new JLabel("Deadline (Y): ");
		deadLineText = new JTextField("500",5);
		JButton setDeadLineButton = new JButton("Apply");
		setDeadLineButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				applyDeadLineY();
			}
		});
		JPanel bottomDeadLinePanel = new JPanel();
		bottomDeadLinePanel.setLayout(new GridLayout(1,2));
		JButton lowerDeadLineButton = new JButton("Lower");
		lowerDeadLineButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lowerDeadLine();
			}
		});
		JButton raiseDeadLineButton = new JButton("Raise");
		raiseDeadLineButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				raiseDeadLine();
			}
		});
		//deadLinePanel.add(deadLineLabel);
		//deadLinePanel.add(deadLineText);
		//deadLinePanel.add(setDeadLineButton);
		topDeadLinePanel.add(deadLineLabel);
		topDeadLinePanel.add(deadLineText);
		topDeadLinePanel.add(setDeadLineButton);
		//deadLinePanel.add(lowerDeadLineButton);
		//deadLinePanel.add(raiseDeadLineButton);
		bottomDeadLinePanel.add(lowerDeadLineButton);
		bottomDeadLinePanel.add(raiseDeadLineButton);
		deadLinePanel.add(topDeadLinePanel);
		deadLinePanel.add(bottomDeadLinePanel);
		bottomMorePanel.add(deadLinePanel);
		JPanel miscPanel = new JPanel();
		JButton clearBossesButton = new JButton("Delete all Bosses");
		clearBossesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearBosses();
			}
		});
		miscPanel.add(clearBossesButton);
		bottomMorePanel.add(miscPanel);
		morePanel.add(topMorePanel);
		morePanel.add(bottomMorePanel);
		//morePanel.add(deadLinePanel);
		//rightHandPane.addTab("General", generalPanel);
		rightHandPane.addTab("General",buttonPanel);
		rightHandPane.addTab("More",morePanel);
		//buttonPanel.add(runningTogglePanel);
		//getContentPane().add(buttonPanel, BorderLayout.EAST);
		getContentPane().add(rightHandPane,BorderLayout.EAST);
		popup.add("Delete Object").addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				levelCanvas.deleteObject();
			}
		});
		popup.add("Get Info").addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				levelCanvas.displayObjectInfo();
			}
		});
		pack();
		setSize(1400,800);
		setResizable(false);
		setVisible(true);
		repaint();
	}
	
	public void applyDeadLineY()
	{
		try
		{
			Integer number = Integer.parseInt(deadLineText.getText());
			deadLineY = number.intValue();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields!");
		}
	}
	
	public void lowerDeadLine()
	{
		deadLineY+=5;
	}
	
	public void raiseDeadLine()
	{
		deadLineY-=5;
	}
	
	public void clearBosses(){
		level.getBosses().clear();
	}
	
	/**
	 * Generate Drop Down Menu
	 * Generates the drop down menu.
	 */
	public void generateDropDownMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveGame();
			}
		});
		fileMenu.add(saveItem);
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadGame();
			}
		});
		fileMenu.add(openItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		JMenuItem clearAllItem = new JMenuItem("Clear all");
		editMenu.add(clearAllItem);
		menuBar.add(editMenu);
		JMenu levelMenu = new JMenu("Level");
		JMenuItem setPropertiesItem = new JMenuItem("Set Level Properties");
		setPropertiesItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				generateLevelDialog();
			}
		});
		levelMenu.add(setPropertiesItem);
		menuBar.add(levelMenu);
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}
	
	/** Generate Platform Dialog
	 * This method simply generates the Dialog Box used to add a platform to the level.
	 * This method is called by the action performed method of the respective Button (The Add Platform button).
	 */
	public void generatePlatformDialog()
	{
		PlatformDialog dialog = new PlatformDialog(this,level,currentDirectory);
	}
	
	/** Generate Wall Dialog
	 * This method simply generates the Dialog box necessar to add a wall to the level.
	 * This method is called by the action performed method of the respective Button.
	 */
	public void generateWallDialog()
	{
		WallDialog dialog = new WallDialog(this,level,currentDirectory);
	}
	
	/**Generates Opponent Dialog.
	 */
	public void generatePlayerDialog()
	{
		PlayerDialog dialog = new PlayerDialog(this,level,currentDirectory);
	}
	
	/**Generates a Boss Dialog.
	 */
	public void generateBossDialog()
	{
		BossDialog dialog = new BossDialog(this,level,currentDirectory);
	}
	
	public void generateCheckPointDialog()
	{
		CheckPointDialog dialog = new CheckPointDialog(this,level,currentDirectory);
	}
	
	/**Generates a Portal Dialog.
	 */
	public void generatePortalDialog()
	{
		PortalDialog dialog = new PortalDialog(this,level,currentDirectory);
	}
	
	/**Generates a Foreground Dialog
	 */
	public void generateDecorationDialog()
	{
		DecorationDialog dialog = new DecorationDialog(this,level,currentDirectory);
	}
	
	/**Generates a Level Dialog
	 */
	public void generateLevelDialog()
	{
		LevelDialog dialog = new LevelDialog(this,level,currentDirectory);
	}
	
	public LevelCanvas getLevelCanvas()
	{
		return levelCanvas;
	}
	
	public void saveGame()
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser(currentDirectory);
			int r = fileChooser.showSaveDialog(this);
			File file = null;
			if(r==JFileChooser.APPROVE_OPTION)
			{
				file = fileChooser.getSelectedFile();
				currentDirectory = file;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(level);
			fileOutputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Saving the level failed!");
		}
	}
	
	public void loadGame()
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser(currentDirectory);
			int r = fileChooser.showOpenDialog(this);
			File file = null;
			if(r==JFileChooser.APPROVE_OPTION)
			{
				file = fileChooser.getSelectedFile();
				currentDirectory = file;
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			level = (LevelEntity)objectInputStream.readObject();
			fileInputStream.close();
			loadImages();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Loading the level failed!");
		}
	}
	
	/** Load Images
	 *Loads the Images of all the level components (if the level components have images to load). Images cannot be serialized but files
	 *can. Therefore, the Image File (not the image itself) of each level component (platform, opponent etc...) is serialized as well.
	 *When an object is regenerated from the file (read from the file), the object's image can be reloaded using the File of the image.
	 *That's what this method does. It loads the image based on the files of the components.
	 */
	public void loadImages()
	{
		//Load background image
		File backgroundImageFile = level.getBackgroundImageFile();
		loadBackgroundImage(backgroundImageFile);
		//Load all the level component images
		ArrayList<PlatformEntity> platforms = level.getPlatforms();
		for(int i = 0; i<platforms.size(); i++)
		{
			File file = platforms.get(i).getSprite().getImageFile();
			loadImage(file,platforms.get(i).getSprite());
		}
		ArrayList<AnimatedPlatformEntity> animatedPlatforms = level.getAnimatedPlatforms();
		for(int i = 0; i<animatedPlatforms.size(); i++)
		{
			File file = animatedPlatforms.get(i).getSprite().getImageFile();
			loadImage(file,animatedPlatforms.get(i).getSprite());
		}
		ArrayList<WallEntity> walls = level.getWalls();
		for(int i = 0; i<walls.size(); i++)
		{
			File file = walls.get(i).getSprite().getImageFile();
			loadImage(file,walls.get(i).getSprite());
		}
		ArrayList<AnimatedWallEntity> animatedWalls = level.getAnimatedWalls();
		for(int i = 0; i<animatedWalls.size(); i++)
		{
			File file = animatedWalls.get(i).getSprite().getImageFile();
			loadImage(file,animatedWalls.get(i).getSprite());
		}
		ArrayList<AIEntity> opponents = level.getOpponents();
		for(int i = 0; i<opponents.size(); i++)
		{
			File file = opponents.get(i).getSprite().getImageFile();
			loadImage(file,opponents.get(i).getSprite());
		}
		ArrayList<AIEntity> allies = level.getAllies();
		for(int i = 0; i<allies.size(); i++)
		{
			File file = allies.get(i).getSprite().getImageFile();
			loadImage(file,allies.get(i).getSprite());
		}
		ArrayList<BossEntity> bosses = level.getBosses();
		for(int i = 0; i<bosses.size(); i++)
		{
			ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
			for(int j = 0; j<bossComponents.size(); j++)
			{
				File file = bossComponents.get(j).getSprite().getImageFile();
				loadImage(file,bossComponents.get(j).getSprite());
			}
		}
		ArrayList<PortalEntity> portals = level.getPortals();
		for(int i = 0; i<portals.size(); i++)
		{
			File file = portals.get(i).getSprite().getImageFile();
			loadImage(file,portals.get(i).getSprite());
		}
		ArrayList<DecorationEntity> foregroundDecorations = level.getForegroundDecorations();
		for(int i = 0; i<foregroundDecorations.size(); i++)
		{
			File file = foregroundDecorations.get(i).getSprite().getImageFile();
			loadImage(file,foregroundDecorations.get(i).getSprite());
		}
		ArrayList<DecorationEntity> backgroundDecorations = level.getBackgroundDecorations();
		for(int i = 0; i<backgroundDecorations.size(); i++)
		{
			File file = backgroundDecorations.get(i).getSprite().getImageFile();
			loadImage(file,backgroundDecorations.get(i).getSprite());
		}
	}
	
	/** Load Background Image
	 *@param The file of the background image.
	 *Loads the background image from the given image file.
	 */
	public void loadBackgroundImage(File file)
	{
		try
		{
			if(file!=null)
			{
				BufferedImage image = ImageIO.read(file);
				if(image!=null)
					level.setBackgroundImage(image);
				else
					JOptionPane.showMessageDialog(null,"Background Image loading failed. The image is null.");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Background Image loading failed.");
		}
	}
	
	/** Load Image
	 *@param The Image File from which to load the Image
	 *@param The Sprite which will receive the loaded image.
	 *Loads the image from the image file into the sprite. Special thanks to the writers of "Objects First with Java" for
	 *the algorithm on how to load an image from a file.
	 */
	public void loadImage(File file, Sprite sprite)
	{
		try
		{
			if(file!=null)
			{
				BufferedImage image = ImageIO.read(file);
				if(image!=null)
					sprite.setImage(image);
				else
					JOptionPane.showMessageDialog(null,"Image loading failed for Sprite: " + sprite + "\n" +
						"The image is null.");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Image loading failed for Sprite: " + sprite);
		}
	}
	
	class LevelCanvas extends JPanel implements Runnable, MouseListener, MouseMotionListener
	{
		private Thread gameloop;
		private boolean running = true;	//true if the level is being updated
		private boolean controllingObject = false;	//true if mouse is already controlling (moving) an object, false otherwise
		private Dimension area;	//area taken up by level graphics
		private int mouseX;	//the current x position of the mouse
		private int mouseY; //the current y position of the mouse
		//The deadline is the line below which players die. It's there to allow players to die when they fall off
		//the level. The y position of the deadline is kept track of in the GUI (above)
		//since the y-coordinate of the deadline can be changed by the editor.
		private int deadLineX1;	//left x-coordinate of deadline
		private int deadLineX2;	//right x-coordinate of the deadline
		
		public LevelCanvas()
		{
			addMouseListener(this);
			addMouseMotionListener(this);
			start();
			area = new Dimension(0,0);
			mouseX = 0;
			mouseY = 0;
		}
		
		public void turnUpdateLoopOn()
		{
			running = true;
		}
		
		public void turnUpdateLoopOff()
		{
			running = false;
		}
		
		public void start()
		{
			gameloop = new Thread(this);
			gameloop.start();
		}
		
		public void stop()
		{
			gameloop = null;
		}
		
		public void run()
		{
			Thread t = Thread.currentThread();
			while(t==gameloop)
			{
				try
				{
					Thread.sleep(20);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				if(running)
					updateLevel();
				repaint();
			}
		}
		
		/* Update Level
		 * Updates all the components of the level.
		 */
		public void updateLevel()
		{
			//Update the platforms
			ArrayList<PlatformEntity> platforms = new ArrayList<PlatformEntity>();
			if(level!=null)
			{
				platforms = level.getPlatforms();
				for(int i = 0; i<platforms.size(); i++)
				{
					platforms.get(i).update();
				}
				//Update the animated platforms
				ArrayList<AnimatedPlatformEntity> animatedPlatforms = level.getAnimatedPlatforms();
				for(int i = 0; i<animatedPlatforms.size();i++)
				{
					animatedPlatforms.get(i).update();
				}
				ArrayList<WallEntity> walls = level.getWalls();
				for(int i = 0; i<walls.size(); i++)
				{
					walls.get(i).update();
				}
				ArrayList<AnimatedWallEntity> animatedWalls = level.getAnimatedWalls();
				for(int i = 0; i<animatedWalls.size();i++)
				{
					animatedWalls.get(i).update();
				}
				ArrayList<CheckPointEntity> checkpoints = level.getCheckPoints();
				for(int i = 0; i<checkpoints.size(); i++)
				{
					checkpoints.get(i).update();
				}
				ArrayList<PortalEntity> portals = level.getPortals();
				for(int i = 0; i<portals.size(); i++)
				{
					portals.get(i).update();
				}
			}
			//Update Bosses
			ArrayList<BossEntity> bosses = new ArrayList<BossEntity>();
			if(level!=null)
			{
				bosses = level.getBosses();
				for(int i = 0; i<bosses.size();i++)
				{
					bosses.get(i).update(0);
				}
			}
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			drawLevel(g);
		}
		
		public void drawLevel(Graphics g)
		{
			Graphics2D g2d = (Graphics2D)g;
			//Draw the background
			/*if(level.getBackgroundImage()!=null)
				g2d.drawImage(level.getBackgroundImage(),level.getBackgroundX(),level.getBackgroundY(),level.getBackgroundWidth(),level.getBackgroundHeight(),this);
			else
			{
				g2d.setColor(Color.WHITE);
				g2d.fillRect(this.getX(),this.getY(),this.getSize().width, this.getSize().height);
			}*/
			if(!editMode)	//draw background color if not in edit mode (if in preview mode)
			{
				g2d.setColor(level.getBackgroundColor());
				g2d.fillRect(mouseX-2000,mouseY-2000,10000,10000);
			}
			g2d.setColor(Color.BLACK);
			//Draw the background decorations. They're drawn immediately after the background image so that they appear behind everything
			//but still appear in front of the background
			ArrayList<DecorationEntity> backgroundDecorations = level.getBackgroundDecorations();
			for(int i = 0; i<backgroundDecorations.size(); i++)
			{
				drawStaticSprite(Color.YELLOW, backgroundDecorations.get(i).getSprite(),g2d);
			}
			//Draw platforms
			ArrayList<PlatformEntity> platforms = level.getPlatforms();
			for(int i = 0; i<platforms.size(); i++)
			{
				/*if(platforms.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.BLACK);
					g2d.fillRect((int)platforms.get(i).getSprite().getXCoord(), (int)platforms.get(i).getSprite().getYCoord(),
						platforms.get(i).getSprite().getWidth(), platforms.get(i).getSprite().getHeight());
				}
				else	//platform has an image
				{
					g2d.drawImage(platforms.get(i).getSprite().getImage(),(int)platforms.get(i).getSprite().getXCoord(),
						(int)platforms.get(i).getSprite().getYCoord(),(int)platforms.get(i).getSprite().getImageWidth(),
						(int)platforms.get(i).getSprite().getImageHeight(),this);
				}*/
				drawStaticSprite(Color.BLACK,platforms.get(i).getSprite(),g2d);
			}
			//Draw the animated platforms
			ArrayList<AnimatedPlatformEntity> animatedPlatforms = level.getAnimatedPlatforms();
			for(int i = 0; i<animatedPlatforms.size();i++)
			{
				/*if(animatedPlatforms.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.BLACK);
					g2d.fillRect((int)animatedPlatforms.get(i).getSprite().getXCoord(),(int)animatedPlatforms.get(i).getSprite().getYCoord(),
						animatedPlatforms.get(i).getSprite().getWidth(),animatedPlatforms.get(i).getSprite().getHeight());
				}
				else
				{
					drawFrame(animatedPlatforms.get(i).getSprite().getImage(), g2d, (int)animatedPlatforms.get(i).getSprite().getXCoord(),
						(int)animatedPlatforms.get(i).getSprite().getYCoord(), animatedPlatforms.get(i).getSprite().getNumCols(), 
						animatedPlatforms.get(i).getSprite().getCurrentFrame(),animatedPlatforms.get(i).getSprite().getImageWidth(), 
						animatedPlatforms.get(i).getSprite().getImageHeight());
				}*/
				drawAnimatedSprite(Color.BLACK,animatedPlatforms.get(i).getSprite(),g2d);
			}
			//Draw the Walls
			ArrayList<WallEntity> walls = level.getWalls();
			for(int i = 0; i<walls.size(); i++)
			{
				/*if(walls.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.BLUE);
					g2d.fillRect((int)walls.get(i).getSprite().getXCoord(),(int)walls.get(i).getSprite().getYCoord(),
						walls.get(i).getSprite().getWidth(),walls.get(i).getSprite().getHeight());
				}
				else	//wall has an image
				{
					g2d.drawImage(walls.get(i).getSprite().getImage(),(int)walls.get(i).getSprite().getXCoord(),
						(int)walls.get(i).getSprite().getYCoord(),(int)walls.get(i).getSprite().getImageWidth(),
						(int)walls.get(i).getSprite().getImageHeight(),this);
				}*/
				drawStaticSprite(Color.BLUE,walls.get(i).getSprite(),g2d);
			}
			//Draw the Animated Walls
			ArrayList<AnimatedWallEntity> animatedWalls = level.getAnimatedWalls();
			for(int i = 0; i<animatedWalls.size();i++)
			{
				/*if(animatedWalls.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.BLUE);
					g2d.fillRect((int)animatedWalls.get(i).getSprite().getXCoord(),(int)animatedWalls.get(i).getSprite().getYCoord(),
						animatedWalls.get(i).getSprite().getWidth(),animatedWalls.get(i).getSprite().getHeight());
				}
				else
				{
					drawFrame(animatedWalls.get(i).getSprite().getImage(), g2d, (int)animatedWalls.get(i).getSprite().getXCoord(),
						(int)animatedWalls.get(i).getSprite().getYCoord(), animatedWalls.get(i).getSprite().getNumCols(), 
						animatedWalls.get(i).getSprite().getCurrentFrame(),animatedWalls.get(i).getSprite().getImageWidth(), 
						animatedWalls.get(i).getSprite().getImageHeight());
				}*/
				drawAnimatedSprite(Color.BLUE,animatedWalls.get(i).getSprite(),g2d);
			}
			//Draw the opponents
			ArrayList<AIEntity> opponents = level.getOpponents();
			for(int i = 0; i<opponents.size(); i++)
			{
			/*	if(opponents.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.RED);
					g2d.fillRect((int)opponents.get(i).getSprite().getXCoord(),(int)opponents.get(i).getSprite().getYCoord(),
						opponents.get(i).getSprite().getWidth(),opponents.get(i).getSprite().getHeight());
				}
				else
				{
					drawFrame(opponents.get(i).getSprite().getImage(), g2d, (int)opponents.get(i).getSprite().getXCoord(),
						(int)opponents.get(i).getSprite().getYCoord(), opponents.get(i).getSprite().getNumCols(), 
						opponents.get(i).getSprite().getCurrentFrame(),opponents.get(i).getSprite().getImageWidth(), 
						opponents.get(i).getSprite().getImageHeight());
				}*/
				drawAnimatedSprite(Color.RED,opponents.get(i).getSprite(),g2d);
			}
			//Draw the allies
			ArrayList<AIEntity> allies = level.getAllies();
			for(int i = 0; i<allies.size(); i++)
			{
				/*if(allies.get(i).getSprite().getImage()==null)
				{
					g2d.setColor(Color.GREEN);
					g2d.fillRect((int)allies.get(i).getSprite().getXCoord(),(int)allies.get(i).getSprite().getYCoord(),
						allies.get(i).getSprite().getWidth(),allies.get(i).getSprite().getHeight());
				}
				else
				{
					drawFrame(allies.get(i).getSprite().getImage(), g2d, (int)allies.get(i).getSprite().getXCoord(),
						(int)allies.get(i).getSprite().getYCoord(), allies.get(i).getSprite().getNumCols(), 
						allies.get(i).getSprite().getCurrentFrame(),allies.get(i).getSprite().getImageWidth(), 
						allies.get(i).getSprite().getImageHeight());
				}*/
				drawAnimatedSprite(Color.GREEN,allies.get(i).getSprite(),g2d);
			}
			//Draw the bosses
			ArrayList<BossEntity> bosses = level.getBosses();
			for(int i = 0; i<bosses.size();i++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
				for(int j = 0; j<bossComponents.size(); j++)
				{
					BossComponent bossComponent = bossComponents.get(j);
					drawAnimatedSprite(Color.ORANGE, bossComponent.getSprite(),g2d);
				}
			}
			//Draw checkpoints
			ArrayList<CheckPointEntity>checkpoints = level.getCheckPoints();
			for(int i = 0; i<checkpoints.size(); i++)
			{
				drawAnimatedSprite(Color.ORANGE,checkpoints.get(i).getSprite(),g2d);
			}
			//Draw the Portals
			ArrayList<PortalEntity> portals = level.getPortals();
			for(int i = 0; i<portals.size(); i++)
			{
				drawAnimatedSprite(Color.BLUE,portals.get(i).getSprite(),g2d);
			}
			//Draw the foreground decorations. They need to be drawn last so that they appear in front of everything else.
			ArrayList<DecorationEntity> foregroundDecorations = level.getForegroundDecorations();
			for(int i = 0; i<foregroundDecorations.size(); i++)
			{
				drawStaticSprite(Color.CYAN, foregroundDecorations.get(i).getSprite(),g2d);
			}
			//Draw the deadline
			g2d.setColor(Color.BLACK);
			g2d.drawString("Deadline: Players below this line are dead.", deadLineX1+500,deadLineY-50);
			g2d.drawLine(deadLineX1-1000,deadLineY , deadLineX1+2000, deadLineY);
			level.setDeadLine(deadLineY);
		}
		
		/**Draw Static Sprite
		 *Draws non-animated Sprite.
		 */
		public void drawStaticSprite(Color color, Sprite sprite, Graphics2D g2d)
		{
			if(sprite.getImage()==null || editMode)
			{			
					g2d.setColor(color);
					g2d.fillRect((int)sprite.getXCoord(), (int)sprite.getYCoord(),
						sprite.getWidth(), sprite.getHeight());
			}
			else
			{
				g2d.drawImage(sprite.getImage(),(int)sprite.getXCoord(),
					(int)sprite.getYCoord(),(int)sprite.getImageWidth(),
					(int)sprite.getImageHeight(),this);
			}
		}
		
		/**Draw Animated Sprite
		 *Draws the animated sprite
		 */
		public void drawAnimatedSprite(Color color, AnimatedSprite sprite, Graphics2D g2d)
		{
				if(sprite.getImage()==null || editMode)
				{
					g2d.setColor(color);
					g2d.fillRect((int)sprite.getXCoord(),(int)sprite.getYCoord(),
						sprite.getWidth(),sprite.getHeight());
				}
				else
				{
					drawFrame(sprite.getImage(),g2d,(int)sprite.getXCoord(),
						(int)sprite.getYCoord(),sprite.getNumCols(),
						sprite.getCurrentFrame(),sprite.getImageWidth(),
						sprite.getImageHeight());
				}
		}
		
		
		/** Scroll Area
		 * The code for this method and the scrolling functionality was obtained from the
		 * ScrollDemo2 class. The ScrollDemo2 class is part of a Demo Project used to illustrate
		 * the functionality/implementation of the JScrollPane and Graphics objects.
		 *
		 * ------------------------------------------------------------------------------
		 * * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
		 *
		 * Redistribution and use in source and binary forms, with or without
		 * modification, are permitted provided that the following conditions
		 * are met:
		 *
		 *   - Redistributions of source code must retain the above copyright
		 *     notice, this list of conditions and the following disclaimer.
		 *
		 *   - Redistributions in binary form must reproduce the above copyright
		 *     notice, this list of conditions and the following disclaimer in the
		 *     documentation and/or other materials provided with the distribution.
		 *
		 *   - Neither the name of Sun Microsystems nor the names of its
		 *     contributors may be used to endorse or promote products derived
		 *     from this software without specific prior written permission.
		 *
		 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
		 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
		 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
		 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
		 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
		 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
		 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
		 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
		 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
		 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
		 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
		 *
		 * - Message from Sun
		 *
		 * ------------------------------------------------------------------------------
		 * 
		 * Scrolls the display area in the desired direction.
		 * @param The x and y coordinates that should be within the viewable area.
		 * The display area is scrolled so that the x and y coordinates are within the viewable
		 * area.
		 * @param Boolean. This boolean is true if the area needs to be resized as a whole, false otherwise.
		 * An example when the boolean is true is when a platform is added that's outside of the field of view.
		 * The platform is added and the overall size of the display area is increased to accommodate the
		 * addition of the new object. An example of when the boolean is false is when the mouse is dragged
		 * along the level (without holding on to an object). This allows the user to scroll through the
		 * level without increasing the size of it.
		 */
		public void scrollDisplayArea(int x, int y, boolean resizeArea)
		{
			final int W = 200;
	        final int H = 200;
	        int focusX = x - W/2;
	        int focusY = y - H/2;
	        Rectangle rect = new Rectangle(focusX, focusY, W, H);
	        this.scrollRectToVisible(rect);
	        int this_width = (focusX + W + 2);
	        if (this_width > area.width) 
	        {
	        	area.width = this_width; //changed=true;
	        }
			int this_height = (focusY + H + 2);
	        if (this_height > area.height) 
	        {
	        	area.height = this_height; //changed=true;
	        }
	     	if(resizeArea)
	     	{	
		        //Update client's preferred size because
		        //the area taken up by the graphics has
		        //gotten larger or smaller (if cleared).
		        this.setPreferredSize(area);
		        //Let the scroll pane know to update itself
		        //and its scrollbars.
		        this.revalidate();
	        }
		}
		
		/*drawFrame() method.
		 *Special thanks to Jonathan S. Harbour from the University of Advancing
	 	 *Technology for the algorithm in this method.
	 	 */
		public void drawFrame(Image source, Graphics2D dest, int destX, int destY,
							int numCols, int currentFrame, int width, int height)
		{
			int fx = (currentFrame%numCols)*width;
			int fy = (currentFrame/numCols)*height;
			dest.drawImage(source, destX, destY, destX+width, destY+height,		//this method allows you to pick a certain part of the image to draw
						fx, fy, fx+width, fy+height, this);
		}
		
		//Methods used for moving/manipulating objects with the mouse.
		/** Contains Points
		 *@param The Sprite which should be checked.
		 *@param The x and y coordinates that could potentially be within the given Sprite.
		 *@return true if the passed x and y coordinates are within the passed Sprite.
		 *Checks whether the given points are within the given Sprite.
		 */
		public boolean containsPoints(Sprite sprite, int x, int y)
		{	
			if(sprite.getXCoord()<x && sprite.getYCoord()<y)
			{
				if(sprite.getXCoord()+sprite.getWidth()>x &&
					sprite.getYCoord()+sprite.getHeight()>y)
				{
						return true;
				}
				return false;
			}
			else
				return false;
		}
		
		public void checkIfObjectActive(MovableObject object, int x, int y)
		{
			if(containsPoints(object.getSprite(), x, y))
			{
				object.setActive(true);
				controllingObject = true;
			}
		}
		
		/******************Platform-related Methods************************************/
		
		/** Check for active platforms
		 * Checks whether the mouse is currently clicking on platforms (dragging platforms).
		 */
		public void checkForActivePlatforms(ArrayList<PlatformEntity> platforms, int x, int y)
		{
			for(int i = 0; i<platforms.size(); i++)
			{
				checkIfObjectActive(platforms.get(i),x,y);	//if an object has been activated
			}
		}
		
		/** Move Clicked Platforms
		 *@param The list of Platforms which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Platforms and moves them accordingly.
		 */
		public void moveActivePlatforms(ArrayList<PlatformEntity> platforms, int x, int y)
		{
			for(int i = 0; i<platforms.size(); i++)
			{
				if(platforms.get(i).active())
				{
					platforms.get(i).getSprite().setXCoord(x);
					platforms.get(i).getSprite().setYCoord(y);
					platforms.get(i).setOriginalX(x);
					platforms.get(i).setOriginalY(y);
				}
			}
		}
		
		/******************Animated Platform-related Methods******************************/
		
		/** Check for active Animated platforms
		 * Checks whether the mouse is currently clicking on platforms (dragging platforms).
		 */
		public void checkForActiveAnimatedPlatforms(ArrayList<AnimatedPlatformEntity> platforms, int x, int y)
		{
			for(int i = 0; i<platforms.size(); i++)
			{
				checkIfObjectActive(platforms.get(i),x,y);	//if an object has been activated	
			}
		}
		
		/** Move Clicked Animated Platforms
		 *@param The list of Platforms which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Platforms and moves them accordingly.
		 */
		public void moveActiveAnimatedPlatforms(ArrayList<AnimatedPlatformEntity> platforms, int x, int y)
		{
			for(int i = 0; i<platforms.size(); i++)
			{
				if(platforms.get(i).active())
				{
					platforms.get(i).getSprite().setXCoord(x);
					platforms.get(i).getSprite().setYCoord(y);
					platforms.get(i).setOriginalX(x);
					platforms.get(i).setOriginalY(y);
				}
			}
		}
		
		//Movement methods for Walls
		/** Check for active Walls
		 * Checks whether the mouse is currently clicking on Walls (dragging Walls).
		 */
		public void checkForActiveWalls(ArrayList<WallEntity> walls, int x, int y)
		{
			for(int i = 0; i<walls.size(); i++)
			{
				checkIfObjectActive(walls.get(i),x,y);	//if an object has been activated
			}
		}
		
		/** Move Clicked Platforms
		 *@param The list of Platforms which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Platforms and moves them accordingly.
		 */
		public void moveActiveWalls(ArrayList<WallEntity> walls, int x, int y)
		{
			for(int i = 0; i<walls.size(); i++)
			{
				if(walls.get(i).active())
				{
					walls.get(i).getSprite().setXCoord(x);
					walls.get(i).getSprite().setYCoord(y);
					walls.get(i).setOriginalX(x);
					walls.get(i).setOriginalY(y);
				}
			}
		}
		
		public void checkForActiveAnimatedWalls(ArrayList<AnimatedWallEntity> walls, int x, int y)
		{
			for(int i = 0; i<walls.size(); i++)
			{
				checkIfObjectActive(walls.get(i),x,y);	//if an object has been activated	
			}
		}
		
		/** Move Clicked Animated Walls
		 *@param The list of Walls which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Walls and moves them accordingly.
		 */
		public void moveActiveAnimatedWalls(ArrayList<AnimatedWallEntity> walls, int x, int y)
		{
			for(int i = 0; i<walls.size(); i++)
			{
				if(walls.get(i).active())
				{
					walls.get(i).getSprite().setXCoord(x);
					walls.get(i).getSprite().setYCoord(y);
					walls.get(i).setOriginalX(x);
					walls.get(i).setOriginalY(y);
				}
			}
		}
		
		/** Check for Active Players
		 */
		public void checkForActivePlayers(ArrayList<AIEntity> players, int x, int y)
		{
			for(int i = 0; i<players.size(); i++)
			{
				checkIfObjectActive(players.get(i),x,y);	//if an object has been activated	
			}
		}
		
		/** Move Clicked Players
		 *@param The list of Players which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Players and moves them accordingly.
		 */
		public void moveActivePlayers(ArrayList<AIEntity> players, int x, int y)
		{
			for(int i = 0; i<players.size(); i++)
			{
				if(players.get(i).active())
				{
					players.get(i).getSprite().setXCoord(x);
					players.get(i).getSprite().setYCoord(y);
					players.get(i).setOriginalX(x);
					players.get(i).setOriginalY(y);
				}
			}
		}
		
		/**Check for active boss components
		 */
		public void checkForActiveBossComponents(ArrayList<BossComponent> bossComponents, int x, int y)
		{
			for(int i = 0; i<bossComponents.size(); i++)
			{
				checkIfObjectActive(bossComponents.get(i),x,y);	//if an object has been activated	
			}
		}
		
		/** Move Clicked Players
		 *@param The list of Players which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Players and moves them accordingly.
		 */
		public void moveActiveBossComponents(ArrayList<BossComponent> bossComponents, int x, int y)
		{
			for(int i = 0; i<bossComponents.size(); i++)
			{
				if(bossComponents.get(i).active())
				{
					bossComponents.get(i).getSprite().setXCoord(x);
					bossComponents.get(i).getSprite().setYCoord(y);
				}
			}
		}
		
		public void checkForActiveCheckPoints(ArrayList<CheckPointEntity>checkpoints,int x, int y)
		{
			for(int i = 0; i<checkpoints.size(); i++)
			{
				checkIfObjectActive(checkpoints.get(i),x,y);	//if an object has been activated	
			}
		}
		
		public void moveActiveCheckPoints(ArrayList<CheckPointEntity> checkpoints, int x, int y)
		{
			for(int i = 0; i<checkpoints.size(); i++)
			{
				if(checkpoints.get(i).active())
				{
					checkpoints.get(i).getSprite().setXCoord(x);
					checkpoints.get(i).getSprite().setYCoord(y);
				}
			}
		}
		
		public void checkForActivePortals(ArrayList<PortalEntity> portals, int x, int y)
		{
			for(int i = 0; i<portals.size(); i++)
			{
				checkIfObjectActive(portals.get(i),x,y);	//if an object has been activated	
			}
		}
		
		/** Move Clicked Animated Walls
		 *@param The list of Walls which should be checked for movement (movement with the mouse).
		 *@param The current x and y coordinates of the mouse.
		 *Checks whether the mouse is moving any of the Walls and moves them accordingly.
		 */
		public void moveActivePortals(ArrayList<PortalEntity> portals, int x, int y)
		{
			for(int i = 0; i<portals.size(); i++)
			{
				if(portals.get(i).active())
				{
					portals.get(i).getSprite().setXCoord(x);
					portals.get(i).getSprite().setYCoord(y);
				}
			}
		}
		
		public void checkForActiveDecorations(ArrayList<DecorationEntity> decorations, int x, int y)
		{
			for(int i = 0; i<decorations.size(); i++)
			{
				checkIfObjectActive(decorations.get(i),x,y);
			}
		}
		
		public void moveActiveDecorations(ArrayList<DecorationEntity> decorations, int x, int y)
		{
			for(int i = 0; i<decorations.size(); i++)
			{
				if(decorations.get(i).active())
				{
					decorations.get(i).getSprite().setXCoord(x);
					decorations.get(i).getSprite().setYCoord(y);
				}
			}
		}
		
		/** Delete Object
		 * Deletes the selected Object.
		 */
		public void deleteObject()
		{
			//Check if user is trying to delete a platform
			for(int i = 0; i<level.getPlatforms().size(); i++)
			{
				if(containsPoints(level.getPlatforms().get(i).getSprite(), mouseX,mouseY))
				{
					level.getPlatforms().remove(level.getPlatforms().get(i));
				}
			}
			//Check if user is trying to delete an animated Platform
			for(int i = 0; i<level.getAnimatedPlatforms().size(); i++)
			{
				if(containsPoints(level.getAnimatedPlatforms().get(i).getSprite(), mouseX,mouseY))
				{
					level.getAnimatedPlatforms().remove(level.getAnimatedPlatforms().get(i));
				}
			}
			//Check if user is trying to delete a Wall
			for(int i = 0; i<level.getWalls().size(); i++)
			{
				if(containsPoints(level.getWalls().get(i).getSprite(), mouseX,mouseY))
				{
					level.getWalls().remove(level.getWalls().get(i));
				}
			}
			//Check if user is trying to delete an Animated Wall
			for(int i = 0; i<level.getAnimatedWalls().size(); i++)
			{
				if(containsPoints(level.getAnimatedWalls().get(i).getSprite(), mouseX,mouseY))
				{
					level.getAnimatedWalls().remove(level.getAnimatedWalls().get(i));
				}
			}
			//Check if user is trying to delete an opponent
			for(int i = 0; i<level.getOpponents().size(); i++)
			{
				if(containsPoints(level.getOpponents().get(i).getSprite(),mouseX,mouseY))
				{
					level.getOpponents().remove(level.getOpponents().get(i));
				}
			}
			//Check if user is trying to delete an ally
			for(int i = 0; i<level.getAllies().size();i++)
			{
				if(containsPoints(level.getAllies().get(i).getSprite(),mouseX,mouseY))
				{
					level.getAllies().remove(level.getAllies().get(i));
				}
			}
			//Check if user is trying to delete a boss component
			ArrayList<BossEntity> bosses = level.getBosses();
			for(int i = 0; i<bosses.size(); i++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
				for(int j = 0; j<bossComponents.size(); j++)
				{
					if(containsPoints(bossComponents.get(j).getSprite(),mouseX,mouseY))
					{
						bossComponents.remove(bossComponents.get(j));
					}
				}
			}
			for(int i = 0; i<level.getCheckPoints().size();i++)
			{
				if(containsPoints(level.getCheckPoints().get(i).getSprite(),mouseX,mouseY))
				{
					level.getCheckPoints().remove(level.getCheckPoints().get(i));
				}
			}
			//Check if user is trying to delete a portal (exit)
			for(int i = 0; i<level.getPortals().size();i++)
			{
				if(containsPoints(level.getPortals().get(i).getSprite(),mouseX,mouseY))
				{
					level.getPortals().remove(level.getPortals().get(i));
				}
			}
			for(int i = 0; i<level.getForegroundDecorations().size();i++)
			{
				if(containsPoints(level.getForegroundDecorations().get(i).getSprite(),mouseX,mouseY))
				{
					level.getForegroundDecorations().remove(level.getForegroundDecorations().get(i));
				}
			}
			for(int i = 0; i<level.getBackgroundDecorations().size();i++)
			{
				if(containsPoints(level.getBackgroundDecorations().get(i).getSprite(),mouseX,mouseY))
				{
					level.getBackgroundDecorations().remove(level.getBackgroundDecorations().get(i));
				}
			}
		}
		
		public void displayObjectInfo()
		{
			//Get info of a platform
			for(int i = 0; i<level.getPlatforms().size(); i++)
			{
				if(containsPoints(level.getPlatforms().get(i).getSprite(), mouseX,mouseY))
				{
					PlatformEntity selectedPlatform = level.getPlatforms().get(i);
					InfoBox box = new InfoBox(selectedPlatform.getSprite());
				}
			}
			//Get info of an animated platform
			for(int i = 0; i<level.getAnimatedPlatforms().size(); i++)
			{
				if(containsPoints(level.getAnimatedPlatforms().get(i).getSprite(), mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getAnimatedPlatforms().get(i).getSprite());
				}
			}
			//Get info of a wall
			for(int i = 0; i<level.getWalls().size(); i++)
			{
				if(containsPoints(level.getWalls().get(i).getSprite(), mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getWalls().get(i).getSprite());
				}
			}
			////Get info of a Animated Wall
			for(int i = 0; i<level.getAnimatedWalls().size(); i++)
			{
				if(containsPoints(level.getAnimatedWalls().get(i).getSprite(), mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getAnimatedWalls().get(i).getSprite());
				}
			}
			////Get info of an opponent
			for(int i = 0; i<level.getOpponents().size(); i++)
			{
				if(containsPoints(level.getOpponents().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getOpponents().get(i).getSprite(),
							level.getOpponents().get(i).getName());
				}
			}
			////Get info of an ally
			for(int i = 0; i<level.getAllies().size();i++)
			{
				if(containsPoints(level.getAllies().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getAllies().get(i).getSprite(),
							level.getAllies().get(i).getName());
				}
			}
			////Get info of a boss component
			ArrayList<BossEntity> bosses = level.getBosses();
			for(int i = 0; i<bosses.size(); i++)
			{
				ArrayList<BossComponent> bossComponents = bosses.get(i).getBossComponents();
				for(int j = 0; j<bossComponents.size(); j++)
				{
					if(containsPoints(bossComponents.get(j).getSprite(),mouseX,mouseY))
					{
						InfoBox box = new InfoBox(bossComponents.get(j).getSprite());
					}
				}
			}
			for(int i = 0; i<level.getCheckPoints().size();i++)
			{
				if(containsPoints(level.getCheckPoints().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getCheckPoints().get(i).getSprite());
				}
			}
			//Get info of a portal (exit)
			for(int i = 0; i<level.getPortals().size();i++)
			{
				if(containsPoints(level.getPortals().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getPortals().get(i).getSprite());
				}
			}
			for(int i = 0; i<level.getForegroundDecorations().size();i++)
			{
				if(containsPoints(level.getForegroundDecorations().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getForegroundDecorations().get(i).getSprite());
				}
			}
			for(int i = 0; i<level.getBackgroundDecorations().size();i++)
			{
				if(containsPoints(level.getBackgroundDecorations().get(i).getSprite(),mouseX,mouseY))
				{
					InfoBox box = new InfoBox(level.getBackgroundDecorations().get(i).getSprite());
				}
			}
		}
		
		//Methods from Mouse interfaces
		public void mouseExited(MouseEvent e)
		{
		}
		
		public void mouseEntered(MouseEvent e)
		{
		}
		
		public void mouseReleased(MouseEvent e)
		{
			controllingObject = false;
			for(int i = 0; i<level.getPlatforms().size();i++)	//release all platforms
			{
				level.getPlatforms().get(i).setActive(false);
			}
			for(int i = 0; i<level.getAnimatedPlatforms().size();i++)
			{
				level.getAnimatedPlatforms().get(i).setActive(false);
			}
			for(int i = 0; i<level.getWalls().size();i++)
			{
				level.getWalls().get(i).setActive(false);
			}
			for(int i = 0; i<level.getAnimatedWalls().size();i++)
			{
				level.getAnimatedWalls().get(i).setActive(false);
			}
			for(int i = 0; i<level.getOpponents().size();i++)
			{
				level.getOpponents().get(i).setActive(false);
			}
			for(int i = 0; i<level.getAllies().size(); i++)
			{
				level.getAllies().get(i).setActive(false);
			}
			for(int i = 0; i<level.getBosses().size(); i++)
			{
				ArrayList<BossComponent> bossComponents = level.getBosses().get(i).getBossComponents();
				for(int j = 0; j<bossComponents.size(); j++)
				{
					bossComponents.get(j).setActive(false);
				}
			}
			for(int i = 0; i<level.getCheckPoints().size();i++)
			{
				level.getCheckPoints().get(i).setActive(false);
			}
			for(int i = 0; i<level.getPortals().size(); i++)
			{
				level.getPortals().get(i).setActive(false);
			}
			for(int i = 0; i<level.getForegroundDecorations().size();i++)
			{
				level.getForegroundDecorations().get(i).setActive(false);
			}
			for(int i = 0; i<level.getBackgroundDecorations().size();i++)
			{
				level.getBackgroundDecorations().get(i).setActive(false);
			}
			if(e.isPopupTrigger())	//bring up right-click menu
			{
				popup.show(this, (int)e.getX()-10, (int)e.getY()-2);
			}
			mouseX = e.getX()+5;
			mouseY = e.getY()+5;
		}
		
		public void mousePressed(MouseEvent e)
		{
			running = false;
			if(e.isPopupTrigger())	//triggers the popup menu when the user clicks the right mouse button (right-click menu)
			{
				popup.show(this, (int)e.getX()-10, (int)e.getY()-2);
			}
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
		public void mouseClicked(MouseEvent e)
		{
		}
		
		public void mouseMoved(MouseEvent e)
		{
		}
		
		public void mouseDragged(MouseEvent e)
		{
			if(!controllingObject) //if mouse is not already moving an object - makes sure only 1 object can be moved at a time
			{
				checkForActivePlatforms(level.getPlatforms(),e.getX(),e.getY());
				checkForActiveAnimatedPlatforms(level.getAnimatedPlatforms(), e.getX(),e.getY());
				checkForActiveWalls(level.getWalls(),e.getX(),e.getY());
				checkForActiveAnimatedWalls(level.getAnimatedWalls(),e.getX(),e.getY());
				checkForActivePlayers(level.getOpponents(),e.getX(),e.getY());
				checkForActivePlayers(level.getAllies(),e.getX(),e.getY());
				ArrayList<BossEntity> bosses = level.getBosses();
				for(int i = 0; i<bosses.size(); i++)
				{
					checkForActiveBossComponents(bosses.get(i).getBossComponents(),e.getX(),e.getY());
				}
				checkForActiveCheckPoints(level.getCheckPoints(),e.getX(),e.getY());
				checkForActivePortals(level.getPortals(),e.getX(),e.getY());
				checkForActiveDecorations(level.getForegroundDecorations(),e.getX(),e.getY());
				checkForActiveDecorations(level.getBackgroundDecorations(),e.getX(),e.getY());
			}
			moveActivePlatforms(level.getPlatforms(),e.getX(),e.getY());
			moveActiveAnimatedPlatforms(level.getAnimatedPlatforms(),e.getX(),e.getY());
			moveActiveWalls(level.getWalls(),e.getX(),e.getY());
			moveActiveAnimatedWalls(level.getAnimatedWalls(),e.getX(),e.getY());
			moveActivePlayers(level.getOpponents(),e.getX(),e.getY());
			moveActivePlayers(level.getAllies(),e.getX(),e.getY());
			ArrayList<BossEntity> bosses = level.getBosses();
			for(int i = 0; i<bosses.size(); i++)
			{
				moveActiveBossComponents(bosses.get(i).getBossComponents(), e.getX(),e.getY());
			}
			moveActiveCheckPoints(level.getCheckPoints(),e.getX(),e.getY());
			moveActivePortals(level.getPortals(),e.getX(),e.getY());
			moveActiveDecorations(level.getForegroundDecorations(),e.getX(),e.getY());
			moveActiveDecorations(level.getBackgroundDecorations(),e.getX(),e.getY());
			if(controllingObject)
				scrollDisplayArea(e.getX(),e.getY(),true);
			else
				scrollDisplayArea(e.getX(),e.getY(), false);
			running = false;
			//Move the deadline so that it's visible
			deadLineX1 = e.getX()-500;
			deadLineX2 = e.getX()+2000;
		}
	}
	
	//Mutator Methods
	public void setCurrentDirectory(File currentDirectory)
	{
		this.currentDirectory = currentDirectory;
	}
}
