/**
 * @(#)AceFighter1_5 -> Command class
 *
 * The Command class is used by the Boss Engine in order to execute commands. The Boss Entity
 * sends a command to its boss components which in turn analyze the contents of the command
 * in order to see if and what they should execute.
 *
 * The Command class is also used by the Cutscene Engine in order to have players execute
 * Cutscene commands. Commands include running, firing, jumping etc...
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */
import java.io.*;
public class Command implements Serializable
{
	private String nameOrType;	//the name of the player/boss component or the type of the boss component
	private String action;		//the action to be taken
	private int duration;		//the duration of the action
	
	public Command(String nameOrType, String action, int duration)
	{
		this.nameOrType = nameOrType;
		this.action = action;
		this.duration = duration;
	}
	
	//Accessor Methods
	public String getNameOrType()
	{
		return nameOrType;
	}
	
	public String getAction()
	{
		return action;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	//Mutator Methods
	public void setNameOrType(String nameOrType)
	{
		this.nameOrType = nameOrType;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
}
