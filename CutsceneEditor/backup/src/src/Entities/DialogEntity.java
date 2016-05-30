package Entities;

import java.util.*;
public class DialogEntity extends CutsceneEntity{
	private int numLines;
	private String dialog;
	
	public DialogEntity(String actor, String condition,int numLines, String dialog){
		super(actor,condition);
		setType("Dialog");
		this.numLines = numLines;
		this.dialog = dialog;
	}
	
	public int getNumLines(){
		return numLines;
	}
	
	public String getDialog(){
		return dialog;
	}
}
