package Entities;

public class CommandEntity extends CutsceneEntity{
	
	private String command;
	private int duration;
	
	public CommandEntity(String actor, String condition, String command, int duration){
		super(actor,condition);
		setType("Command");
		this.command = command;
		this.duration = duration;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public int getDuration(){
		return this.duration;
	}
}
