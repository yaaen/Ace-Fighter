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
	private JTextField dialogLine1;
	private JTextField dialogLine2;
	private JTextField dialogLine3;
	private int index;
	private boolean addNewItem;	//true if new item should be added to list, false if item should
								//be inserted replacing the current item in the list.
	
	public DialogWindow(iControl control, int index, boolean addNewItem){
		super("New Dialog...");
		this.control = control;
		this.index = index;
		this.addNewItem = addNewItem;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		addGuiContent();
		getContentPane().add(mainPanel);
		pack();
		setSize(750,250);
		setResizable(false);
		setVisible(true);
	}
	
	public void setWindowContent(DialogEntity dialog){
		String conditionString = dialog.getCondition();
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
		actorText.setText(dialog.getActor());
		int current = 0;
		if(dialog.getNumLines()>0){
			dialogLine1.setText(dialog.getDialog().get(current));
		}
		if(dialog.getNumLines()>1){
			current++;
			dialogLine2.setText(dialog.getDialog().get(current));
		}
		if(dialog.getNumLines()>2){
			current++;
			dialogLine3.setText(dialog.getDialog().get(current));
		}
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
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new GridLayout(3,1));
		dialogLine1 = new JTextField(55);
		dialogLine1.setText("Enter Dialog Line 1 here...");
		dialogPanel.add(dialogLine1);
		dialogLine2 = new JTextField("Enter Dialog Line 2 here...");
		dialogPanel.add(dialogLine2);
		dialogLine3 = new JTextField("Enter Dialog Line 3 here...");
		dialogPanel.add(dialogLine3);
		middlePanel.add(dialogPanel);
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
				int numLines = 0;
				ArrayList<String> dialog = new ArrayList<String>();
				String firstLine = dialogLine1.getText();
				if(firstLine.length()>0){
					numLines++;
					dialog.add(firstLine);
				}
				String secondLine = dialogLine2.getText();
				if(secondLine.length()>0){
					numLines++;
					dialog.add(secondLine);
				}
				String thirdLine = dialogLine3.getText();
				if(thirdLine.length()>0){
					numLines++;
					dialog.add(thirdLine);
				}
				DialogEntity newDialog = new DialogEntity(actor,condition,numLines);
				newDialog.setDialog(dialog);
				if(addNewItem){
					control.addDialog(newDialog,index);
				}
				else{
					control.setDialog(newDialog,index);
				}
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
