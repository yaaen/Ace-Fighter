package FileHandling;

public interface iConfigurationHandler {
	void saveConfiguration(ConfigurationEntity configuration)throws FileHandlingException;
	
	ConfigurationEntity loadConfiguration()throws FileHandlingException;
}
