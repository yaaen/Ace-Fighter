package Entities;

public abstract class CutsceneEntity {
	
	private String type;
	private String condition;
	private String actor;
	
	public CutsceneEntity(String actor, String condition){
		this.actor = actor;
		this.condition = condition;
	}
	
	public String getType(){
		return type;
	}
	
	public String getCondition(){
		return condition;
	}
	
	public String getActor(){
		return this.actor;
	}
	
	protected void setType(String type){
		this.type = type;
	}
}
