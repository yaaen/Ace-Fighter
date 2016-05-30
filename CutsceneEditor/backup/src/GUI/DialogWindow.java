package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import Control.*;
import Entities.*;
public class DialogWindow extends JFrame{
	
	private iControl control;
	private JPanel mainPanel;
	private JComboBox conditionCombo;
	private JTextField keyText;
	private JTextField actorText;
	private JTextArea dialogArea;
	int index;
	
	public DialogWindow(iControl control, int index){
		super("New Dialog...");
		this.control = control;
		this.index = index;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		addGuiContent();
		getContentPane().add(mainPanel);
		pack();
		setSize(600,200);
		setResizable(false);
		setVisible(true);
	}
	
	private void addGuiContent(){
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEtchedBorder());
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
		JLabel actorLabel = new JLabel("Speaker:");
		actorText = new JTextField("Enter Actor Name");
		topPanel.add(conditionLabel);
		topPanel.add(conditionCombo);
		topPanel.add(keyText);
		topPanel.add(actorLabel);
		topPanel.add(actorText);
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Dialog (Max. 3 Lines)"));
		dialogArea = new JTextArea(3,40);
		dialogArea.setBorder(BorderFactory.createEtchedBorder());
		dialogArea.setText("Enter Dialog here. Remember to hit enter at the end of each line \n"+
				"and keep it within 3 lines.");
		middlePanel.add(dialogArea);
		JPanel bottomPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
		bottomPanel.add(cancelButton);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String actor = actorText.getText();
				String condition = "NoCondition";
				if(conditionCombo.getSelectedItem().equals("Key Press")){
					condition = "KeyPress_"+keyText.getText();
				}
				String dialog = dialogArea.getText();
				int numLines = dialogArea.getLineCount();
				control.addDialog(new DialogEntity(actor,condition,numLines,dialog),index);
				closeWindow();
			}
		});
		bottomPanel.add(okButton);
		mainPanel.add(topPanel,BorderLayout.NORTH);
		mainPanel.add(middlePanel,BorderLayout.CENTER);
		mainPanel.add(bottomPanel,BorderLayout.SOUTH);
	}
	
	private void closeWindow(){
		this.dispose();
	}
}
