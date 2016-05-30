import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//This contains brief information about a game object such as its position, its size etc...

public class InfoBox extends JFrame
{
	private JPanel outerPanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JLabel xCoordLbl;
	private JLabel yCoordLbl;
	private JLabel widthLbl;
	private JLabel heightLbl;
	private JButton okButton = new JButton("Ok");
	
	private Sprite sprite;	//the sprite used to display the information
	private String name;	//the name of the player (this only applies if a player was being clicked on)
	
	public InfoBox(Sprite sprite)
	{
		super("Object Inspector");
		this.sprite = sprite;
		packPanel();
		getContentPane().add(outerPanel);
		pack();
		setSize(200,300);
		setResizable(false);
		setVisible(true);	
	}
	
	public InfoBox(Sprite sprite, String name){
		super("Object Inspector");
		this.sprite = sprite;
		this.name = name;
		packPanel();
		getContentPane().add(outerPanel);
		pack();
		setSize(200,500);
		setResizable(false);
		setVisible(true);
	}
	
	public void packPanel()
	{
		outerPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		if(sprite!=null)
		{
			xCoordLbl = new JLabel("X Pos: "+sprite.getXCoord());
			yCoordLbl = new JLabel("Y Pos: "+sprite.getYCoord());
			widthLbl = new JLabel("Width: "+sprite.getWidth());
			heightLbl = new JLabel("Height: "+sprite.getHeight());
			JLabel nameLabel = new JLabel("Player name: ");
			if(name!=null){
				 nameLabel = new JLabel("Player name: " + name);
			}
			okButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					closeDialog();
				}
			});
			mainPanel.setLayout(new GridLayout(6,1));
			mainPanel.add(xCoordLbl);
			mainPanel.add(yCoordLbl);
			mainPanel.add(widthLbl);
			mainPanel.add(heightLbl);
			mainPanel.add(nameLabel);
			mainPanel.add(okButton);
		}
		else
		{
			JLabel errorLbl = new JLabel("No was Sprite passed");
			mainPanel.add(errorLbl);
		}
		outerPanel.add(mainPanel);
	}
	
	private void closeDialog()
	{
		this.dispose();
	}
}
