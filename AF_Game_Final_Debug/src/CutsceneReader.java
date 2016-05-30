/**
 * @(#)AceFighter1_8 -> Cutscene Engine -> Cutscene Reader
 *
 * The Cutscene Reader reads the text file containing all the cutscene data.
 * It then formats that data so that the Cutscene Engine can place it into its
 * data structures and be ready to execute the cutscenes.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 20, 2008
 */
 
import java.io.*;
import java.util.*;
class CutsceneReader 
{
	//Variables for reading the script
	private String filename;	//the name of the level script file
	InputStream scriptStream;
	BufferedReader br;

	//Variables for storing the Cutscene script data
	private ArrayList<String> cutsceneEvents;	//the list of Cutscene Events. A cutscene event is formatted
												//as follows: KeyPress_A Dialog. The first word of the String is the condition
												//to be met and the second word is the cutscene event that is executed when that
												//condition is met.
	private ArrayList<Command> cutsceneCommands;	//the list of CutsceneCommands. A command consists of: PlayerName, Action, Duration.
	private ArrayList<DialogEntity> cutsceneDialogs;	//the list of Cutscene dialogs that are gonna be executed. A DialogEvent consits of:
													//the name of the speaker, the number of lines of dialog to be spoken, and the
													//list of Strings that represent the lines of dialog that are going to be spoken.
	
	//Constructor
	public CutsceneReader(String filename)
	{
		this.filename = filename;
		if(filename==null)
			System.out.println("CutsceneFile name is null");
		//try
		//{
		//try
		//{
			scriptStream = this.getClass().getResourceAsStream(filename);
		
			//scriptStream = new FileInputStream(new File(filename));
			//scriptStream = this.getClass().getResourceAsStream(filename);
			if(scriptStream==null)
			{
				System.out.println("Script Stream is null");
				System.out.println("Desired File might not exist.");
			}
			br = new BufferedReader(new InputStreamReader(scriptStream));
			cutsceneEvents = new ArrayList<String>();
			cutsceneCommands = new ArrayList<Command>();
			cutsceneDialogs = new ArrayList<DialogEntity>();
		/*}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}*/
	}
	
	//Accessor Methods
	//Returns the list of Cutscene Events.
	public ArrayList<String> getCutsceneEvents()
	{
		return cutsceneEvents;
	}
	
	//Returns the list of Cutscene Commands.
	public ArrayList<Command> getCutsceneCommands()
	{
		return cutsceneCommands;
	}
	
	//Returns the list of Cutscene Dialogs.
	public ArrayList<DialogEntity> getCutsceneDialogs()
	{
		return cutsceneDialogs;
	}
	
	//Methods for reading the cutscene script file
	public void readCutsceneData()
	{
		String nextLine = "";
		try
		{
			nextLine = br.readLine();
			String numCutsceneEventsString = nextLine;
			Integer integer = Integer.parseInt(numCutsceneEventsString);
			int numEvents = integer.intValue();
			for(int i = 0; i<numEvents; i++) //read all events of this Cutscene
			{
				nextLine = br.readLine();
				//System.out.println("Line: " + nextLine);
				String cutsceneEvent = nextLine;
				Scanner scanner = new Scanner(cutsceneEvent);
				String condition = scanner.next();
				String event = scanner.next();
				if(event.equals("Command"))	//if event is a Command (such as: Player3 Fire 500)
					readCommandData();
				if(event.equals("Dialog"))	//if event is a Dialog
					readDialogData();
				cutsceneEvents.add(cutsceneEvent);
			}
		}
		catch(IOException e)
		{
			System.out.println("Error in readCutsceneData() in CutsceneReader: IOException");
		}
	}
	
	public void readCommandData()
	{
		//System.out.println("**readCommand**");
		String nextLine = "";
		try
		{
			nextLine = br.readLine();
			//System.out.println("**Line: " + nextLine);
			String actionCommand = nextLine;
			Scanner scanner = new Scanner(nextLine);
			String playerName = scanner.next();
			String action = scanner.next();
			String durationString = scanner.next();
			Integer longNumber = Integer.parseInt(durationString);
			int duration = longNumber.intValue();
			cutsceneCommands.add(new Command(playerName, action, duration));
		}
		catch(IOException e)
		{
			System.out.println("Error in readCommandData() in CutsceneReader: IOException");
		}
	}
	
	public void readDialogData()
	{
		//System.out.println("##readDialog##");
		String nextLine = "";
		try
		{
			nextLine = br.readLine();
			//System.out.println("##Line: " + nextLine);
			String playerName = nextLine;
			nextLine = br.readLine();
			Integer integer = Integer.parseInt(nextLine);
			int numLines = integer.intValue();
			ArrayList<String> dialogLines = new ArrayList<String>();
			for(int i = 0; i<numLines; i++)
			{
				nextLine = br.readLine();
				dialogLines.add(nextLine);
			}
			cutsceneDialogs.add(new DialogEntity(playerName, numLines, dialogLines));
		}
		catch(IOException e)
		{
			System.out.println("Error in readDialogData() in CutsceneReader: IOException");
		}
	}
}

