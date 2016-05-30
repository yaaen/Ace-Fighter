/**
 * @(#)AceFighter1_5 -> Boss Engine -> BossEntity
 *
 * The BossEntity is the boss as a whole. It's the sum of all the boss components. It basically
 * consists of a list of boss components. The Boss Entity is responsible for delivering
 * commands to the various boss components.
 *
 * @author Chris Braunschweiler
 * @version 1.00 March 18, 2008
 */

import java.io.*;
import java.util.*;

public class BossEntity implements Serializable
{
	private ArrayList<BossComponent> bossComponents;	//the list of boss components that make up this boss entity
	private ArrayList<Command> commands;	//the list of commands to be executed
	private int currentCommandPointer;		//the index of the current command to be executed
	private boolean alive;		//true if the boss entity, as a whole, is alive. A boss entity dies
								//once the Cockpit dies.
	private boolean dying;	//true if any of the components of the boss are dying and displaying a dying
							//animation
	
	//Variables necessary to read the AI file
	private transient FileInputStream scriptStream;
	private transient BufferedReader br;
	private File bossAIFile;		//the file that contains the boss AI information (movement patterns etc of components and such)
	
	public BossEntity(ArrayList<BossComponent> bossComponents, File bossAIFile)
	{
		this.bossComponents = bossComponents;
		commands = new ArrayList<Command>();
		currentCommandPointer = 0;
		alive = true;
		setDying(false);
		this.bossAIFile = bossAIFile;
		loadAIData();
		System.out.println("BossEntity Consturctor**********");
	}
	
	//Accessor Methods
	public ArrayList<BossComponent> getBossComponents()
	{
		return bossComponents;
	}
	
	public boolean alive()
	{
		return alive;
	}
	
	//Mutator Methods
	public void setBossComponents(ArrayList<BossComponent> bossComponents)
	{
		this.bossComponents = bossComponents;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	//Methods necessary to load the AI data from the file
	/**
	 * Read AI Data
	 * @param The name of the file from which to read commands.
	 * Reads the commands from the specified file and loads them into the list of commands.
	 */
	public void loadAIData()
	{
		if(bossAIFile!=null)
		{		
			if(bossAIFile.exists())
			{
				try
				{
					scriptStream = new FileInputStream(bossAIFile);
					br = new BufferedReader(new InputStreamReader(scriptStream));
					String nextLine = "";
					nextLine = br.readLine();
					Integer integer = Integer.parseInt(nextLine);
					int numCommands = integer.intValue();	
					for(int i = 0; i<numCommands; i++)
					{
						readAICommand();
					}
					
				}
				catch(IOException e)
				{
					System.out.println("Boss AI File reading failed. See BossEntity's loadAIData()");
				}
			}	
		}	
	}
	
	/*The readAICommand() method reads one AI Command for the boss at a time.
	 *IMPORTANT NOTE: Since a Scanner is used to scan through the lines, the names of Components or Component Types CANNOT
	 *have spaces in them. For example, a component name such as "Turret 1" is UNACCEPTABLE. A component name
	 *such as "Turret1" is ACCEPTABLE. NO SPACES!
	 */
	private void readAICommand()
	{
		Scanner lineScanner;	//used to pick out individual components of the line
		String nextLine = "";
		try
		{
			nextLine = br.readLine();
			lineScanner = new Scanner(nextLine);
			String componentType = lineScanner.next();
			String action = lineScanner.next();
			int duration = lineScanner.nextInt();
			commands.add(new Command(componentType, action, duration));
		}
		catch(IOException e)
		{
			System.out.println("Error in readAICommand() in BossEntity: IOException");
		}
	}
	
	//Update Method
	public void update(double gravity)
	{
		setDying(false);
		for(int i = 0; i<bossComponents.size();i++){
			if(bossComponents.get(i).dying()){
				setDying(true);
			}
		}
		if(alive)
		{
			int cockpitIndex = 0;
			boolean killRemainingComponents = false; //true if remaining components should be killed and their dying animations displayed.
													//This is true if the cockpit gets killed.
			//Update the states of all the boss components
			for(int i = 0; i<bossComponents.size(); i++)
			{
				bossComponents.get(i).update(gravity);
				//check if cockpit has died -> if boss has died
				if(bossComponents.get(i).getType().equals("Cockpit"))
				{
					cockpitIndex = i;
					if(bossComponents.get(i).alive()==false)	//if cockpit is dead
					{
						alive = false;		//boss entity as a whole is dead
						killRemainingComponents = true;
					}
				}
			}
			if(killRemainingComponents){
				for(int i = 0; i<bossComponents.size(); i++){
					if(i!=cockpitIndex&&bossComponents.get(i).alive()){
						bossComponents.get(i).die();
					}
				}
			}

			//Execute the next command in the list of commands
			if(commands.size()>0)	//if there are any AI commands for the boss to execute, execute them
				executeCommand();
		}
		else if(isDying()){
			for(int i = 0; i<bossComponents.size(); i++){
				bossComponents.get(i).update(gravity);
			}
		}
	}
	
	/**
	 * Execute Command
	 * Executes the next command in the list of commands. If no command is currently
	 * being executed, this fetches the next command, and sends it to all the boss components.
	 */
	public void executeCommand()
	{
		boolean readyForNextCommand = false;
		if(currentCommandPointer>=commands.size())	//make boss execute commands in a loop
		{
			currentCommandPointer=0;
		}
		Command currentCommand = commands.get(currentCommandPointer);
		for(int i = 0; i<bossComponents.size(); i++)
		{	 
			bossComponents.get(i).executeCommand(currentCommand);
			if(bossComponents.get(i).readyForCommand())	//if bossComponent is ready for next command (done with current command), increment pointer
			{
				bossComponents.get(i).setReadyForCommand(false);
				bossComponents.get(i).setElapsedExecutionTime(0);	//reset elapsedExecutionTime
				readyForNextCommand = true;
			}
			
		}
		if(readyForNextCommand)
		{
			currentCommandPointer++;
		}
	}
	
	//For testing and debugging
	public String toString()
	{
		String s = "";
		for(int i = 0; i<bossComponents.size();i++)
		{
			s += bossComponents.get(i).toString();
		}
		return s;
	}

	public void setDying(boolean dying) {
		this.dying = dying;
	}

	public boolean isDying() {
		return dying;
	}
}
