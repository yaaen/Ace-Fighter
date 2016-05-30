package GUI;

import javax.swing.*;

import Control.iControl;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

import Entities.*;
public class FinishWindow extends JFrame{

	private iControl control;
	private JPanel mainPanel;
	private JComboBox conditionCombo;
	private JTextField keyText;
	private int index;
	private boolean addNewItem;
	
	public FinishWindow(iControl control, int index, boolean addNewItem){
		super("Add Cutscene Terminator...");
		this.control = control;
		this.index = index;
		this.addNewItem = addNewItem;
		mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		addGuiContent();
		pack();
		setSize(400,100);
		setResizable(false);
		setVisible(true);
	}
	
	public void setWindowContent(FinishEntity finish){
		String conditionString = finish.getCondition();
		if(!conditionString.equals("NoCondition")){
			StringTokenizer st = new StringTokenizer(conditionString,"_");
			String condition = st.nextToken();
			String key = st.nextToken();
			conditionCombo.setSelectedItem("Key Press");
			keyText.setText(key);
			keyText.setEnabled(true);
		}
		else{
			conditionCombo.setSelectedItem("No Condition");
			keyText.setEnabled(false);
		}
	}
	
	private void addGuiContent(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JPanel panel1 = new JPanel();
		JLabel conditionLabel = new JLabel("Condition:");
		keyText = new JTextField("Key");
		keyText.setEnabled(false);
		String[] conditions = new String[]{"No Condition","Key Press"};
		conditionCombo = new JComboBox(conditions);
		conditionCombo.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().equals("Key Press")){
					//Activate textfield
					keyText.setEnabled(true);
				}
				else{
					//Deactivate textfield
					keyText.setEnabled(false);
				}
			}
		});
		panel1.add(conditionLabel);
		panel1.add(conditionCombo);
		panel1.add(keyText);
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
		panel2.add(cancelButton);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String condition = "NoCondition";
				if(conditionCombo.getSelectedItem().equals("Key Press")){
					condition = "KeyPress_"+keyText.getText();
				}
				if(addNewItem)
					control.finishCurrentCutscene(new FinishEntity(condition));
				else
					control.setFinish(new FinishEntity(condition), index);
				closeWindow();
			}
		});
		panel2.add(okButton);
		panel.add(panel2);
		mainPanel.add(panel);
	}
	
	private void closeWindow(){
		this.dispose();
	}
}
