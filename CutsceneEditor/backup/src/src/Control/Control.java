package Control;

import java.util.ArrayList;
import Entities.*;
import GUI.*;
import FileHandling.*;

public class Control implements iControl{
	private ArrayList<Cutscene> cutscenes;
	private Cutscene currentCutscene;
	private ArrayList<iView> views;
	private iFileHandler fileHandler;
	
	public Control(){
		cutscenes = new ArrayList<Cutscene>();
		currentCutscene = null;
		views = new ArrayList<iView>();
		fileHandler = new FileHandlerImpl();
	}

	public void addCommand(CommandEntity command) {
		currentCutscene.getCutsceneEntities().add(command);	
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void addCutscene(Cutscene cutscene, int index) throws CutsceneException{
		for(Cutscene theCutscene:cutscenes){
			if(theCutscene.getAlias().equals(cutscene.getAlias())){
				throw new CutsceneException("A Cutscene with the given Alias already exists.");
			}
		}
		cutscenes.add(index,cutscene);
		for(iView view:views){
			view.updateCutscenes(cutscenes);
		}
	}

	public void addDialog(DialogEntity dialog, int index) {
		currentCutscene.getCutsceneEntities().add(index,dialog);
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void removeCommand(int index) {
		currentCutscene.getCutsceneEntities().remove(index);
		//notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void removeCutscene(int index) {
		if(index>=0){
			cutscenes.remove(index);
			currentCutscene = null;
			for(iView view:views){
				view.updateCutscenes(cutscenes);
			}
		}
	}

	public void removeDialog(int index) {
		currentCutscene.getCutsceneEntities().remove(index);
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void setCommand(CommandEntity command, int index) {
		currentCutscene.getCutsceneEntities().set(index, command);
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void setDialog(DialogEntity dialog, int index) {
		currentCutscene.getCutsceneEntities().set(index, dialog);
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void addListeningView(iView listener) {
		views.add(listener);
	}

	public void setCurrentCutscene(String alias) {
		for(Cutscene cutscene:cutscenes){
			if(cutscene.getAlias().equals(alias)){
				this.currentCutscene = cutscene;
			}
		}
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public boolean existsCurrentCutscene() {
		return currentCutscene!=null;
	}

	public boolean existCutsceneEvents() {
		if(currentCutscene!=null){
			return !currentCutscene.getCutsceneEntities().isEmpty();
		}
		return false;
	}

	public void finishCurrentCutscene(FinishEntity finish) {
		currentCutscene.getCutsceneEntities().add(finish);	
		//Notify views
		for(iView view:views){
			view.updateCutsceneEvents(currentCutscene);
		}
	}

	public void loadCutscenes() throws FileHandlingException {
		cutscenes = fileHandler.readFile();
		//Notify views
		for(iView view:views){
			view.updateCutscenes(cutscenes);
		}
	}

	public void saveCutscenes() throws FileHandlingException {
		fileHandler.writeFile(cutscenes);
	}

	public void setFilePath(String filepath) {
		fileHandler.setFilePath(filepath);
	}
}
