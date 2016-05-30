package GUI;

import javax.swing.*;

import Control.iControl;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
public class PreferencesWindow extends JFrame{
	
	private iControl control;
	private JPanel mainPanel;
	private JTextField pathText;
	private String currentDirectory;
	
	public PreferencesWindow(iControl control){
		super("Edit Preferences");
		this.control = control;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		this.getContentPane().add(mainPanel);
		loadPreferences();
		addFileHandlingPanel();
		addButtonPanel();
		pack();
		setSize(400,300);
		setResizable(false);
		setVisible(true);
	}
	
	void addFileHandlingPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"File Handling"));
		JLabel label = new JLabel("File path:");
		pathText = new JTextField("File path...");
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnFileChooser();
			}
		});
		panel.add(label);
		panel.add(pathText);
		panel.add(browseButton);
		mainPanel.add(panel);
	}
	
	void addButtonPanel(){
		JPanel panel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				control.setFilePath(pathText.getText());
				//TODO: Save new preferences in config file
				closeWindow();
			}
		});
		panel.add(cancelButton);
		panel.add(okButton);
		mainPanel.add(panel);
	}
	
	//Loads preferences from config file.
	private void loadPreferences(){
		currentDirectory = "Blah";
	}
	
	private void spawnFileChooser(){
		JFileChooser fileChooser = new JFileChooser(new File(currentDirectory));
		int r = fileChooser.showOpenDialog(this);
		File file = null;
		if(r==JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			currentDirectory = file.getAbsolutePath();
			pathText.setText(currentDirectory);
		}
	}
	
	private void closeWindow(){
		this.dispose();
	}
}
