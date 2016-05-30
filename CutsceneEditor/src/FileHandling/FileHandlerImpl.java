package FileHandling;

import java.util.*;
import java.io.*;

import Entities.*;
public class FileHandlerImpl implements iFileHandler{

	private String filepath;	//the file path used to read/write files.
	
	public FileHandlerImpl(){
		filepath = null;
	}
	
	public String getFilePath() {
		return filepath;
	}

	public ArrayList<Cutscene> readFile() throws FileHandlingException{
		if(filepath!=null){
			BufferedReader br;
			try{
				br = new BufferedReader(new FileReader(filepath));
				ArrayList<Cutscene> cutscenes = new ArrayList<Cutscene>();
				int numEvents = 0;
				String eventsLine;
				while((eventsLine=br.readLine())!=null){
					try{
						Cutscene cutscene = new Cutscene();
						numEvents = Integer.parseInt(eventsLine);
						for(int i = 0; i<numEvents;i++){
							String conditionTypeLine = br.readLine();
							Scanner lineScanner = new Scanner(conditionTypeLine);
							String condition = lineScanner.next();
							String type = lineScanner.next();
							if(type.equals("Dialog")){
								cutscene.getCutsceneEntities().add(readDialog(br,condition));
							}
							else if(type.equals("Command")){
								cutscene.getCutsceneEntities().add(readCommand(br,condition));
							}
							else if(type.equals("Finish")){
								cutscene.getCutsceneEntities().add(new FinishEntity(condition));
							}
						}
						cutscenes.add(cutscene);
					}
					catch(NumberFormatException e){
						throw new FileHandlingException("Syntax error in fileheader (nr. Cutscene Events): " + e.getMessage());
					}
				}
				readAliases(cutscenes);
				br.close();
				return cutscenes;
			}
			catch(IOException e){
				throw new FileHandlingException("Unable to read file: " + e.getMessage());
			}
		}
		else{
			throw new FileHandlingException("Unable to read file: Filepath not set.");
		}
	}

	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}

	public void writeFile(ArrayList<Cutscene> cutscenes) throws FileHandlingException{
		if(filepath!=null){
			writeAliases(cutscenes);
			PrintWriter pw;
			try {
				pw = new PrintWriter(filepath);
			} catch (FileNotFoundException e) {
				throw new FileHandlingException("Unable to write file: " + e.getMessage());
			}
			for(Cutscene cutscene:cutscenes){
				ArrayList<CutsceneEntity>cutsceneEvents = cutscene.getCutsceneEntities();
				pw.println(cutsceneEvents.size());
				for(CutsceneEntity cutsceneEvent:cutsceneEvents){
					pw.println(cutsceneEvent.getCondition()+" "+cutsceneEvent.getType());
					if(cutsceneEvent.getType().equals("Dialog")){
						writeDialog(pw,cutsceneEvent);
					}
					else if(cutsceneEvent.getType().equals("Command")){
						writeCommand(pw,cutsceneEvent);
					}
				}
			}
			pw.flush();
			pw.close();
		}
		else{
			throw new FileHandlingException("Unable to write file: Filepath not set.");
		}
	}
	
	//Writes the cutscene aliases to a file.
	private void writeAliases(ArrayList<Cutscene> cutscenes)throws FileHandlingException{
		PrintWriter pw;
		try {
			pw = new PrintWriter("ext/aliases.txt");
		} catch (FileNotFoundException e) {
			throw new FileHandlingException("Unable to write ALIASES file: " + e.getMessage());
		}
		for(Cutscene cutscene:cutscenes){
			pw.println(cutscene.getAlias());
		}
		pw.flush();
		pw.close();
	}
	
	private void readAliases(ArrayList<Cutscene> cutscenes)throws FileHandlingException{
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader("ext/aliases.txt"));
			String currentLine;
			Iterator<Cutscene> cutsceneItr = cutscenes.iterator();
			while((currentLine=br.readLine())!=null&&cutsceneItr.hasNext()){
				cutsceneItr.next().setAlias(currentLine);
			}
			br.close();
		}
		catch(IOException e){
			throw new FileHandlingException("Unable to read ALIASES file: " + e.getMessage());
		}
	}
	
	private void writeDialog(PrintWriter pw,CutsceneEntity cutsceneEvent){
		pw.println(cutsceneEvent.getActor());
		pw.println(((DialogEntity)cutsceneEvent).getNumLines());
		for(String dialogLine:((DialogEntity)cutsceneEvent).getDialog()){
			pw.println(dialogLine);
		}
	}
	
	private DialogEntity readDialog(BufferedReader br, String condition)throws FileHandlingException{
		try{
			String actor = br.readLine();
			int numLines = 0;
			numLines = Integer.parseInt(br.readLine());
			DialogEntity newDialog = new DialogEntity(actor,condition,numLines);
			for(int i = 0; i<numLines;i++){
				newDialog.getDialog().add(br.readLine());
			}
			return newDialog;
		}
		catch(IOException e){
			throw new FileHandlingException("Syntax Error in Dialog: " + e.getMessage());
		}
	}
	
	private void writeCommand(PrintWriter pw, CutsceneEntity cutsceneEvent){
		pw.print(cutsceneEvent.getActor()+" ");
		pw.print(((CommandEntity)cutsceneEvent).getCommand()+" ");
		pw.println(((CommandEntity)cutsceneEvent).getDuration());
	}

	private CommandEntity readCommand(BufferedReader br, String condition)throws FileHandlingException{
		try{
			String commandLine = br.readLine();
			Scanner lineScanner = new Scanner(commandLine);
			String actor = lineScanner.next();
			String command = lineScanner.next();
			int duration = Integer.parseInt(lineScanner.next());
			return new CommandEntity(actor,condition,command,duration);
		}
		catch(IOException e){
			throw new FileHandlingException("Syntax Error in Command: " + e.getMessage());
		}
	}
}
