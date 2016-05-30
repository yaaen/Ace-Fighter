package FileHandling;

public class ConfigurationEntity {
	private String cutsceneFilePath;	//the path to the cutscene file used to save/load cutscenes
	
	public ConfigurationEntity(){
		cutsceneFilePath = null;
	}
	
	public String getCutsceneFilePath(){
		return cutsceneFilePath;
	}
	
	public void setCutceneFilePath(String cutsceneFilePath){
		this.cutsceneFilePath = cutsceneFilePath;
	}
}
