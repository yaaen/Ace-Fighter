/** Movable Object
 * Objects that can be moved by the mouse, extend this class.
 * This class contains a boolean specifying whether the object is currently
 * being moved.
 */
public abstract class MovableObject 
{
	private boolean active;	//true if the object is currently being moved (activated by the mouse)
	
	public MovableObject()
	{
		active = false;
	}
	
	public boolean active()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	abstract Sprite getSprite();
}
