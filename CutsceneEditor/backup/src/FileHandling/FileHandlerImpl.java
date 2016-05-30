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
			
			
		}
		else{
			throw new FileHandlingException("Unable to read file: Filepath not set.");
		}
		return null;
	}

	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}

	public void writeFile(ArrayList<Cutscene> cutscenes) throws FileHandlingException{
		writeAliases(cutscenes);
		if(filepath!=null){
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
						
					}
					else if(cutsceneEvent.getType().equals("Finish")){
						
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
	
	private void writeDialog(PrintWriter pw,CutsceneEntity cutsceneEvent){
		pw.println(cutsceneEvent.getActor());
		pw.println(((DialogEntity)cutsceneEvent).getNumLines());
		pw.println(((DialogEntity)cutsceneEvent).getDialog());
	}

}
