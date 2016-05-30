package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import Control.*;
import Entities.*;
public class CutsceneWindow extends JFrame{
	
	private iControl control;
	private JPanel mainPanel;
	JTextField aliasText;
	private int index;
	
	public CutsceneWindow(iControl control, int index){
		super("New Cutscene...");
		this.control = control;
		this.index = index;
		mainPanel = new JPanel();
		addGuiContent();
		this.getContentPane().add(mainPanel);
		pack();
		setSize(200,100);
		setResizable(false);
		setVisible(true);
	}
	
	private void addGuiContent(){
		JLabel label = new JLabel("Alias:");
		aliasText = new JTextField("Enter Alias");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					control.addCutscene(new Cutscene(aliasText.getText()),index);
					closeWindow();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});
		mainPanel.add(label);
		mainPanel.add(aliasText);
		mainPanel.add(cancelButton);
		mainPanel.add(okButton);
	}
	
	private void closeWindow(){
		this.dispose();
	}
}
