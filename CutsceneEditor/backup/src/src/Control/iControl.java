package Control;

import Entities.*;
import GUI.*;
import FileHandling.*;
public interface iControl {
	/**
	 * Adds a new Cutscene to the list of Cutscenes.
	 * @param cutscene The cutscene to be added to the list of cutscenes.
	 * @throws An Exception if a cutscene with the same alias of the passed cutscene already exists
	 * in the list.
	 */
	void addCutscene(Cutscene cutscene, int index)throws CutsceneException;
	
	/**
	 * Removes the specified cutscene from the list of cutscenes.
	 * @param The index of the cutscene to be removed.
	 */
	void removeCutscene(int index);
	
	/**
	 * Sets the current cutscene to the one with the alias passed in the parameters. The current cutscene
	 * is the cutscene which is currently being viewed in the GUI and being edited. The aliases
	 * are unique.
	 * @param The alias of the new current cutscene.
	 */
	void setCurrentCutscene(String alias);
	
	/**
	 * Adds a new Dialog to the current cutscene.
	 * @param dialog The new dialog to be added.
	 */
	void addDialog(DialogEntity dialog, int index);
	
	/**
	 * Adds a new Command to the current cutscene.
	 * @param command The new Command to be added.
	 */
	void addCommand(CommandEntity command);
	
	/**
	 * Finishes the current cutscene by inserting a finish event at the end of the current
	 * cutscene.
	 * @param finish
	 */
	void finishCurrentCutscene(FinishEntity finish);
	
	/**
	 * Removes the dialog at the specified index from the current cutscene.
	 * @param index The index of the dialog to be removed.
	 */
	void removeDialog(int index);
	
	/**
	 * Removes the command at the specified index from the current cutscene.
	 * @param index The index of the command to be removed.
	 */
	void removeCommand(int index);
	
	/**
	 * Sets the dialog at the specified index to the new dialog passed in the parameters. It
	 * basically replaces the dialog at the specified index with the new dialog passed in the parameters.
	 * @param dialog The new dialog which should be set.
	 * @param index The index of the dialog which is to be replaced.
	 */
	void setDialog(DialogEntity dialog, int index);
	
	/**
	 * Sets the command at the specified index to the new command passed in the parameters. It
	 * basically replaces the command at the specified index with the new command passed in the parameters.
	 * @param command The new command which should be set.
	 * @param index The index of the command which is to be replaced.
	 */
	void setCommand(CommandEntity command, int index);
	
	/**
	 * Adds a new listening View to this iControl.
	 * @param listener The new listener.
	 */
	void addListeningView(iView listener);
	
	/**
	 * Returns true if a cutscene is selected, false otherwise.
	 * @return true if a cutscene is selected.
	 */
	boolean existsCurrentCutscene();
	
	/**
	 * Returns true if there are cutscene events, false otherwise.
	 * @return ture if there are cutscene events.
	 */
	boolean existCutsceneEvents();
	
	/**
	 * Saves cutscenes to an already specified file.
	 * @throws Exception. Throws an exception if no save file has been specified.
	 */
	void saveCutscenes()throws FileHandlingException;
	
	/**
	 * Loads cutscenes from an already specified file.
	 * @throws Exception. Throws an exception if no load file has been specified.
	 */
	void loadCutscenes()throws FileHandlingException;
	
	/**
	 * Sets the file path used for saving and loading cutscenes.
	 * @param filepath
	 */
	void setFilePath(String filepath);
}
