/**
 * @(#)AceFighter1_8 -> Cutscene Engine -> Cutscene Handler
 *
 * This class is responsible for loading, and maintaining/running the
 * cutscenes. It keeps track of all the cutscene events, cutscene commands,
 * cutscene dialogs etc...
 *
 * Important note regarding Cutscene Engine. Note that the currentCutsceneDialogPointer is
 * initialized to -1. This is because the counter is first incremented before the dialog is
 * displayed. Just keep that in mind. This basically means that the first cutscene event
 * in any cutscene has to be dialog and cannot be a command. Because if a command is the first
 * even in a cutscene and a user interrupts that event, the engine crashes because the dialog pointer
 * will still be -1 and thus an index out of bounds exception will be thrown, I believe.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 20, 2008
 */

import java.util.*;

public class CutsceneHandler 
{
	private CutsceneReader cutsceneReader;	//reads all the data from the cutscene file
	private ArrayList<String> cutsceneEvents;	//the cutscene events in this cutscene
	private ArrayList<Command> cutsceneCommands;	//the commands in this cutscene
	private ArrayList<DialogEntity> cutsceneDialogs;	//the dialogs in this cutscene
	private boolean executeCutsceneCommand;		//true if a cutscene command needs to be executed
	private boolean executeCutsceneDialog;		//true if a cutscene dialog needs to be executed
	private boolean busy;			//true if cutscene handler is currently executing a cutscene event
	private int currentCutsceneEventPointer;	//points to current cutscene event
	private int currentCutsceneCommandPointer;	//points to current cutscene command
	private int currentCutsceneDialogPointer;	//points to current cutscene dialog
	private ArrayList<AIEntity> players;	//the players that can participate in cutscenes
	private boolean cutsceneFinished;		//true if the cutscene is finished
	
	public CutsceneHandler()
	{
		cutsceneReader = new CutsceneReader("Load/Cutscenes/Cutscene1.txt");
		cutsceneEvents = new ArrayList<String>();
		cutsceneCommands = new ArrayList<Command>();
		cutsceneDialogs = new ArrayList<DialogEntity>();
		executeCutsceneCommand = false;
		executeCutsceneDialog = false;
		busy = false;
		currentCutsceneEventPointer = 0;
		currentCutsceneCommandPointer = 0;
		currentCutsceneDialogPointer = -1;
		players = new ArrayList<AIEntity>();
		cutsceneFinished = false;
	}
	
	//Accessors
	/**
	 * Execute Cutscene Dialog
	 * @return true if a cutscene dialog is being executed, false otherwise.
	 * This is needed by the main engine so that it can display cutscene dialogs
	 */
	public boolean executeCutsceneDialog()
	{
		return executeCutsceneDialog;
	}
	
	/**
	 * Get Current Cutscene Dialog
	 * @return The current dialog being spoken in the cutscene.
	 * This is needed by the main engine so that i can display cutscene dialogs.
	 */
	public DialogEntity getCurrentCutsceneDialog()
	{
		return cutsceneDialogs.get(currentCutsceneDialogPointer);
	}
	
	/**
	 * Cutscene Finished
	 * @return True if the cutscene that was running is now finished, false otherwise.
	 * This is used by the main engine to see if the cutscene is over.
	 */
	public boolean cutsceneFinished()
	{
		return cutsceneFinished;
	}
	
	//Mutator Methods
	/**
	 * Set Players
	 * @param The list of players the menu handler should deal with.
	 * Sets the menu handlers list of players to the list passed in the parameter.
	 */
	public void setPlayers(ArrayList<AIEntity> players)
	{
		this.players = players;
		for(AIEntity player: players)
		{
			player.setCutscene(true);
		}
	}
	
	/**
	 * Initialize Cutscene Handler
	 * Reads the data from the cutscene file using the cutscene reader, and
	 * places all information into data structures so that it's ready to
	 * execute cutscenes.
	 */
	public void initializeCutsceneHandler()
	{
		cutsceneReader.readCutsceneData();
		//Retrieve the list of CutsceneEvents and store them in the list of CutsceneEvents
		for(int i = 0; i<cutsceneReader.getCutsceneEvents().size(); i++)
		{
			cutsceneEvents.add(cutsceneReader.getCutsceneEvents().get(i));
		}
		//Retrieve the list of CutsceneCommands and store them in the engine
		for(int i = 0; i<cutsceneReader.getCutsceneCommands().size(); i++)
		{
			cutsceneCommands.add(cutsceneReader.getCutsceneCommands().get(i));
		}
		//Retrieve the list of CutsceneDialogs and store them in the engine
		for(int i = 0; i<cutsceneReader.getCutsceneDialogs().size(); i++)
		{
			cutsceneDialogs.add(cutsceneReader.getCutsceneDialogs().get(i));
		}
	}
	
	/**
	 * Reset Cutscene Handler
	 * Resets all the parameters (resets pointers to 0, clears lists etc...) so that
	 * next cutscene can be started successfully.
	 */
	public void resetCutsceneHandler()
	{
		cutsceneEvents.clear();
		cutsceneDialogs.clear();
		cutsceneCommands.clear();
		//Clear cutscene data from CutsceneReader so that reader does not fill up memory with running cutscene data
		cutsceneReader.getCutsceneEvents().clear();
		cutsceneReader.getCutsceneCommands().clear();
		cutsceneReader.getCutsceneDialogs().clear();
		//reset all cutscene pointers
		currentCutsceneEventPointer = 0;
		currentCutsceneCommandPointer = 0;
		currentCutsceneDialogPointer = -1;
		cutsceneFinished = false;
	}
	
	//Methods necessary to execute cutscenes
	
	/**
	 * Update
	 * Updates the state of the cutscene engine.
	 */
	public void update()
	{
		if(executeCutsceneCommand)
			executeCommand();
		executeCutsceneEvent("NoCondition");
	}
	
	/**
	 * Execute Cutscene Event
	 * @param The event that has happened.
	 * Checks whether the reported event meets the condition of the next cutscene event. If it does,
	 * it executes the next cutscene event.
	 */
	public void executeCutsceneEvent(String reportedEvent)
	{
		if(!busy)	//if no current event in progress
		{
			if(currentCutsceneEventPointer<cutsceneEvents.size())	//prevents ArrayList access outside of array list
			{
				String cutsceneEvent = cutsceneEvents.get(currentCutsceneEventPointer);
				Scanner csEventScanner = new Scanner(cutsceneEvent);
				String condToMeet = csEventScanner.next();	//the condition that needs to be met
				if(reportedEvent.equals(condToMeet))
				{
					String eventToExecute = csEventScanner.next();
					if(eventToExecute.equals("Command"))
					{
						executeCutsceneCommand = true;
						busy = true;		//Engine is now busy executing current Command.
					}
					if(eventToExecute.equals("Dialog"))
					{
						executeCutsceneDialog = true;
						currentCutsceneDialogPointer++;
					}
					if(eventToExecute.equals("Finish"))
					{
						//end the cutscene!
						cutsceneFinished = true;
						executeCutsceneCommand = false;
						executeCutsceneDialog = false;
						//let players know that cutscene is over
						for(AIEntity player: players)
							player.setCutscene(false);
					}
					currentCutsceneEventPointer++;
				}
			}
		}
	}
	
	/**
	 * Execute Command
	 * Executes a CutsceneCommand. 
	 * It goes through the list of players and calls each player's executeCommand() method. 
	 * Once the correct player(s) is/are found, he/she/they execute the corresponding Command. 
	 */
	public void executeCommand()
	{
		boolean readyForNextCommand = false;
		Command currentCommand = cutsceneCommands.get(currentCutsceneCommandPointer);	//get current Command
		for(int i = 0; i<players.size(); i++)
		{
			players.get(i).executeCommand(currentCommand);
			if(players.get(i).readyForCommand())
			{
				players.get(i).setReadyForCommand(false);
				players.get(i).setElapsedExecutionTime(0);
				readyForNextCommand = true;
			}
		}
		if(readyForNextCommand)
		{
			busy = false;
			currentCutsceneCommandPointer++;	//increment pointer to current Command
			executeCutsceneCommand = false;
		}
	}
	
	/**
	 * Report Event
	 * Reports an event to the cutscene handler. The cutscene handler then executes the appropriate action.
	 */
	public void reportEvent(String event)
	{
		if(event.equals("KeyPress_e")||event.equals("KeyPress_E"))	//allows a player to skip cutscenes
		{
			terminateCutsceneHandler();
		}
		else
			executeCutsceneEvent(event);
	}
	
	/**
	 * Terminate CutsceneHandler
	 * Turns off Cutscene handler explicitly.
	 */
	public void terminateCutsceneHandler()
	{
		cutsceneFinished = true;
		executeCutsceneCommand = false;
		executeCutsceneDialog = false;
		busy = false;
		//let players know that cutscene is over
		for(AIEntity player: players){
			player.setCutscene(false);
			player.setFiring(false);
		}
	}
}
