package Entities;

import java.util.*;
public class Cutscene {

	private String alias;	//an alias for the cutscene
	private ArrayList<CutsceneEntity> cutsceneEntities;
	
	public Cutscene(String alias){
		this.alias = alias;
		cutsceneEntities = new ArrayList<CutsceneEntity>();
	}
	
	public String getAlias(){
		return alias;
	}
	
	public ArrayList<CutsceneEntity> getCutsceneEntities(){
		return cutsceneEntities;
	}
}
