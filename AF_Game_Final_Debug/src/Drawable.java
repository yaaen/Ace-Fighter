/**
 * @(#)AceFighter3_7 -> Main Engine -> Drawable interface
 *
 * Any object that implements this interface is drawable. In other words, any
 * object that implements this interface can be displayed by the main engine.
 * The method needed to display an object is the getSprite() method. Obtaining
 * an object's sprite, allows the main engine to display the sprite's image,
 * frame etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 July 3, 2008
 */
public interface Drawable
{
	public Sprite getSprite();
}
