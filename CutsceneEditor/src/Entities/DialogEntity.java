package Entities;

import java.util.*;
public class DialogEntity extends CutsceneEntity{
	
	private int numLines;
	private ArrayList<String> dialog;
	
	public DialogEntity(String actor, String condition,int numLines){
		super(actor,condition);
		setType("Dialog");
		this.numLines = numLines;
		dialog = new ArrayList<String>();
	}
	
	public int getNumLines(){
		return numLines;
	}
	
	public ArrayList<String> getDialog(){
		return dialog;
	}
	
	public void setDialog(ArrayList<String> dialog){
		this.dialog = dialog;
	}
}
