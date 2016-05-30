package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

import Control.*;
import Entities.*;
public class CommandWindow extends JFrame{

	private iControl control;
	private JPanel mainPanel;
	private JComboBox conditionCombo;
	private JTextField keyText;
	private JTextField actorText;
	private JComboBox commandCombo;
	private JTextField durationText;
	private int index;
	private boolean addNewItem;
	
	public CommandWindow(iControl control, int index, boolean addNewItem){
		super("Add new Command...");
		this.control = control;
		this.index = index;
		this.addNewItem = addNewItem;
		mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		addGuiContent();
		pack();
		setSize(400,200);
		setResizable(false);
		setVisible(true);
	}
	
	public void setWindowContent(CommandEntity command){
		String conditionString = command.getCondition();
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
		actorText.setText(command.getActor());
		String commandAction = command.getCommand();
		if(commandAction.equals("MoveX")){
			if(command.getDuration()>=0){
				commandCombo.setSelectedItem("Move Right");
			}
			else{
				commandCombo.setSelectedItem("Move Left");
			}
		}
		else if(commandAction.equals("Fire")){
			if(command.getDuration()>=0){
				commandCombo.setSelectedItem("Fire Right");
			}
			else{
				commandCombo.setSelectedItem("Fire Left");
			}
		}
		else if(commandAction.equals("MoveY")){
			commandCombo.setSelectedItem("Jump");
		}
		durationText.setText(""+command.getDuration());
	}
	
	private void addGuiContent(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
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
		JLabel actorLabel = new JLabel("Actor:");
		actorText = new JTextField("Enter Actor Name");
		JLabel commandLabel = new JLabel("Command:");
		String[] commands = new String[]{"Move Left","Move Right", "Jump", "Fire Left", "Fire Right"};
		commandCombo = new JComboBox(commands);
		durationText = new JTextField("100");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String actor = actorText.getText();
				String condition = "NoCondition";
				if(conditionCombo.getSelectedItem().equals("Key Press")){
					condition = "KeyPress_"+keyText.getText();
				}
				String command = "MoveX";
				int direction = 1;
				if(commandCombo.getSelectedItem().equals("Move Left")){
					direction = -1;
				}
				else if(commandCombo.getSelectedItem().equals("Jump")){
					command = "MoveY";
				}
				else if(commandCombo.getSelectedItem().equals("Fire Left")){
					direction = -1;
					command = "Fire";
				}
				else if(commandCombo.getSelectedItem().equals("Fire Right")){
					command = "Fire";
				}
				int duration = 0;
				try{
					duration = Math.abs(Integer.parseInt(durationText.getText()));
					duration*=direction;
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Syntax Error: Only enter numeric values in duration field");
				}
				if(addNewItem)
					control.addCommand(new CommandEntity(actor,condition,command,duration),index);
				else
					control.setCommand(new CommandEntity(actor,condition,command,duration),index);
				closeWindow();
			}
		});
		panel1.add(conditionLabel);
		panel1.add(conditionCombo);
		panel1.add(keyText);
		panel2.add(actorLabel);
		panel2.add(actorText);
		panel3.add(commandLabel);
		panel3.add(commandCombo);
		panel3.add(durationText);
		panel4.add(cancelButton);
		panel4.add(okButton);
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		mainPanel.add(panel);
	}
	
	private void closeWindow(){
		this.dispose();
	}
}
