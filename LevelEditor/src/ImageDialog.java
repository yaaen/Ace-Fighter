/**
 * @(#)LevelEditor1_5 -> Image Dialog class
 *
 * This is the dialog that appears when loading an image. It allows
 * the user to load an image and allows the user to specify the
 * width and height of the image.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 May 16, 2008
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.image.*;
import javax.imageio.*;
public class ImageDialog extends JFrame
{
	private JPanel mainPanel = new JPanel();
	private JPanel firstPanel = new JPanel();
	private JPanel secondPanel = new JPanel();
	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton staticButton = new JRadioButton("Static", true);
	private JRadioButton animatedButton = new JRadioButton("Animated", false);
	private JLabel imageLabel = new JLabel("Image: ");
	private JTextField imageText = new JTextField(5);
	private JButton browseButton = new JButton("Browse");
	private JLabel widthLabel = new JLabel("Image Width: ");
	private JTextField widthText = new JTextField("0",5);
	private JLabel heightLabel = new JLabel("Image Height: ");
	private JTextField heightText = new JTextField("0",5);
	private JLabel frameDelayLabel = new JLabel("Frame Delay: ");
	private JTextField frameDelayText = new JTextField("0",5);
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");
	
	private File currentDirectory;	//the current directory that the file chooser is in
	private boolean animated = false;
	private Image objectImage = null;
	private File imageFile = null;	//the image file and not the image itself. Files can be serialized while images cannnot
	private int imageWidth = 0;
	private int imageHeight = 0;
	private int frameDelay = 0;
	
	public ImageDialog(File currentDirectory)
	{
		super("Load Image");
		this.currentDirectory=currentDirectory;
		mainPanel.setLayout(new GridLayout(2,0));
		addGraphicalPanel();
		addButtonPanel();
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		getContentPane().add(mainPanel);
		pack();
		setSize(450,500);
		setResizable(false);
		setVisible(true);
	}
	
	public void addGraphicalPanel()
	{
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new GridLayout(5,0));
		outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graphical"));
		JPanel panel1 = new JPanel();
		radioButtonGroup.add(staticButton);
		radioButtonGroup.add(animatedButton);
		panel1.add(staticButton);
		panel1.add(animatedButton);
		JPanel panel2 = new JPanel();
		panel2.add(imageLabel);
		panel2.add(imageText);
		JPanel panel3 = new JPanel();
		browseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chooseImage();
			}
		});
		panel3.add(browseButton);
		JPanel panel4 = new JPanel();
		panel4.add(widthLabel);
		panel4.add(widthText);
		panel4.add(heightLabel);
		panel4.add(heightText);
		JPanel panel5 = new JPanel();
		panel5.add(frameDelayLabel);
		panel5.add(frameDelayText);
		outerPanel.add(panel1);
		outerPanel.add(panel2);
		outerPanel.add(panel3);
		outerPanel.add(panel4);
		outerPanel.add(panel5);
		firstPanel.add(outerPanel);
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
				cancelAction();
			}
		});
		innerPanel.add(cancelButton);
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmAction();
			}
		});
		innerPanel.add(okButton);
		outerPanel.add(innerPanel);
		secondPanel.add(outerPanel);
	}
	
	/* Cancel Action
	 * Cancels the image loading. Sets image back to null and width and
	 * height of image back to 0.
	 */
	public void cancelAction()
	{
		objectImage = null;
		imageWidth = 0;
		imageHeight = 0;
		closeDialog();
	}
	
	/* Confirm Action
	 * Loads the image information (width and height) into the global
	 * variables so that external classes can access it.
	 */
	public void confirmAction()
	{
		try
		{		
			if(staticButton.isSelected())	//object is a non-animating object
			{
				animated = false;
			}
			if(animatedButton.isSelected())	//object is an animated object
			{
				animated = true;
			}
			Integer num = Integer.parseInt(widthText.getText());
			imageWidth = num.intValue();
			num = Integer.parseInt(heightText.getText());
			imageHeight = num.intValue();
			num = Integer.parseInt(frameDelayText.getText());
			frameDelay = num.intValue();
			closeDialog();
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Illegal Argument in numeric fields");
		}
	}
	
	public void closeDialog()
	{
		this.dispose();
	}
	
	/* Choose Image
	 * Allows the user to choose an image. It opens a JFileChooser.
	 */
	public void chooseImage()
	{
		JFileChooser imageChooser;
		if(currentDirectory==null)	//if this is the first time the file chooser is opened
		{
			imageChooser = new JFileChooser();
		}
		else	//if file chooser has been opened before, launch it in the directory that user was last in
		{
			imageChooser = new JFileChooser(currentDirectory);
		}
		int r = imageChooser.showOpenDialog(this);
		if(r==JFileChooser.APPROVE_OPTION)
		{
			currentDirectory = imageChooser.getSelectedFile();
			imageFile = imageChooser.getSelectedFile();
			imageFile = new File(imageFile.getPath());
			String completePathName = imageFile.getPath();
			String shortPathName = completePathName.substring(completePathName.lastIndexOf("Images/"));
			imageFile = new File(shortPathName);
			imageText.setText(imageChooser.getSelectedFile().getName());
			objectImage = loadImage(imageChooser.getSelectedFile());
		}
	}
	
	/* Load Image
	 * Loads an image from the given file.
	 * Special thanks to the writers of "Objects First with Java" for
	 * the algorithm on how to load an image from a file.
	 */
	public Image loadImage(File file)
	{
		try
		{
			BufferedImage image = ImageIO.read(file);
			if(image==null||(image.getWidth(null)<0))
			{
				//Loading of the image failed
				return null;
			}
			return image;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//Accessor methods
	/* Get Current Directory.
	 * Returns the directory the user was last in with the JFileChooser. This prevents
	 * the user from having to find the directory he was last in each time he opens
	 * the file chooser.
	 */
	public File getCurrentDirectory()
	{
		return currentDirectory;
	}
	
	public boolean animated()
	{
		return animated;
	}
	
	public Image getImage()
	{
		return objectImage;
	}
	
	public File getImageFile()
	{
		return imageFile;
	}
	
	public int getImageWidth()
	{
		return imageWidth;
	}
	
	public int getImageHeight()
	{
		return imageHeight;
	}
	
	public int getFrameDelay()
	{
		return frameDelay;
	}
}
