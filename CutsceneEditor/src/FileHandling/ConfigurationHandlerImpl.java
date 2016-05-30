package FileHandling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import Entities.Cutscene;

public class ConfigurationHandlerImpl implements iConfigurationHandler{

	public ConfigurationEntity loadConfiguration()throws FileHandlingException{
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader("ext/config.txt"));
			String filePath = br.readLine();
			ConfigurationEntity configuration = new ConfigurationEntity();
			configuration.setCutceneFilePath(filePath);
			br.close();
			return configuration;
		}
		catch(IOException e){
			throw new FileHandlingException("Unable to read CONFIG file: " + e.getMessage());
		}
	}

	public void saveConfiguration(ConfigurationEntity configuration)throws FileHandlingException{
		PrintWriter pw;
		try {
			pw = new PrintWriter("ext/config.txt");
			pw.println(configuration.getCutsceneFilePath());
		} catch (FileNotFoundException e) {
			throw new FileHandlingException("Unable to write CONFIG file: " + e.getMessage());
		}
		
		pw.flush();
		pw.close();
		
	}

}
