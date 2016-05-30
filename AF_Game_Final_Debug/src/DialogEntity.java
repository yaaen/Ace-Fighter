/**
 * @(#)AceFighter1_8 -> Cutscene Engine -> DialogEntity
 *
 * The Dialog Entity contains information necessary for a player to execute
 * a dialog in a cutscene. This means that a dialog entity contains  the name
 * of the speaker, the number of lines of dialog he has to speak, and the actaul
 * lines of dialog the respective player speaks.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 20, 2008
 */

import java.util.*;

public class DialogEntity 
{
	private String speaker;	//the speaker of the dialog
	private int numLines;	//the number of lines of dialog
	private ArrayList<String> dialogLines;	//the actual lines of dialog
	
	public DialogEntity(String speaker, int numLines, ArrayList<String> dialogLines)
	{
		this.speaker = speaker;
		this.numLines = numLines;
		this.dialogLines = dialogLines;
	}
	
	//Accessor methods
	public String getSpeaker()
	{
		return speaker;
	}
	
	public int getNumLines()
	{
		return numLines;
	}
	
	public ArrayList<String> getDialogLines()
	{
		return dialogLines;
	}
}
