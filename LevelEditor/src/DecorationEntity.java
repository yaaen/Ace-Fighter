/**
 * @(#)LevelEditor1_0 -> Decoration Entity class
 *
 * A Decoration Entity is either a background decoration or a foreground decoration. Decorations are simply
 * images that are there for decoration. They cannot be interacted with. Foreground decorations appear in front of
 * everything and background decorations appear behind everything.
 * 
 * @author Chris Braunschweiler
 * @version 1.00 July 06, 2008
 */
import java.io.*;
public class DecorationEntity extends MovableObject implements Serializable
{
	private Sprite sprite;	//the sprite
	
	public DecorationEntity(int xCoord, int yCoord, int width, int height)
	{
		sprite = new Sprite(xCoord,yCoord,width,height);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
}
