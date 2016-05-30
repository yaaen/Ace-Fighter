package FileHandling;

import Entities.*;
import java.util.*;
public interface iFileHandler {
	
	/**
	 * Returns the file path used to save and load files.
	 * @return File path.
	 */
	public String getFilePath();
	
	/**
	 * Sets the file path used to save and load files.
	 * @param filepath
	 */
	public void setFilePath(String filepath);
	
	/**
	 * Reads the file contents and returns the contents as a list of Cutscenes. It
	 * reads the file from the filepath set by the setFilePath() method.
	 * @return The list of cutscenes read from the file.
	 * @throws An exception if there is no file path set.
	 */
	public ArrayList<Cutscene> readFile()throws FileHandlingException;
	
	/**
	 * Writes the cutscenes into a file. The file will be written to the location denoted
	 * by the filepath set in the setFilePath() method.
	 * @param cutscenes: The cutscenes that are written to the file.
	 * @throws An exception if there is no file path set.
	 */
	public void writeFile(ArrayList<Cutscene> cutscenes) throws FileHandlingException;
}
