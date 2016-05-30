/**
 * @(#)LevelEditor4_6 -> ColorSelector class
 *
 * This class allows a player to select a background color for the level using
 * a JColorChooser. The reason background colors, instead of images are used are
 * because images are too big and cause the game to slow down too much. Since
 * there can be foreground and background decorations, an illusion of a background
 * image can be made with a few scattered background decorations.
 * 
 * NOTE: A large portion of the code in this class was obtained from:
 * 
 * http://java.sun.com/docs/books/tutorial/uiswing/components/colorchooser.html
 * 
 * @author Chris Braunschweiler
 * @version 1.00 Oct 3, 2008
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class ColorSelector extends JFrame implements ChangeListener
{
	protected JColorChooser tcc;
	private Color color;

    public ColorSelector() 
    {
    	super("Background Color");
		pack();
		setSize(400,650);
		setResizable(false);
		setVisible(true);
        
        //Set up color chooser for setting the color
		color = JColorChooser.showDialog(this,"Choose Background Color",Color.BLUE);

		
		
        /*tcc = new JColorChooser(Color.BLUE);
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder("Choose Level Background Color"));
        add(tcc, BorderLayout.CENTER);*/
    }

    public void stateChanged(ChangeEvent e) {
        //color = tcc.getColor();
    }
    
    public Color getColor()
    {
    	return color;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     *
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ColorChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ColorSelector();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }*/
}
