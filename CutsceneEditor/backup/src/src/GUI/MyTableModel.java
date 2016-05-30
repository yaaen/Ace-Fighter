package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
public class MyTableModel extends DefaultTableModel{
	private int numRows;
	
	public MyTableModel(){
		super();
		numRows = 0;
	}
	
	public void addRow(Object[] row){
		super.addRow(row);
		numRows++;
	}
	
	public void removeRow(int row){
		super.removeRow(row);
		numRows--;
	}
	
	public void clear(){
		while(numRows>0){
			removeRow(0);
		}
	}
	
	public boolean isCellEditable(int row, int col){
		return false;
	}
}
