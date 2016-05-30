import java.awt.Image;

/**
 * @(#)AceFighter_Game -> Menu Engine -> MenuIcon
 *
 * Simply represents a deocrative menu icon such as a picture of an item or something
 * of that sort.
 *
 * @author Chris Braunschweiler
 * @version 1.00 November 6, 2008
 */

public class MenuIcon extends MenuEntity
{
	public MenuIcon(int xCoord, int yCoord, int width, int height, Image image)
	{
		super(xCoord,yCoord,width,height);
		getSprite().setImage(image);
		getSprite().setImageWidth(width);
		getSprite().setImageHeight(height);
	}

	@Override
	void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}
}
