package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.*;

import Control.*;
import Entities.*;
import FileHandling.*;
public class MainGUI extends JFrame implements iView{
	
	private iControl control;
	
	private JPanel outerPanel;
	private JPanel mainPanel;
	private JPanel cutsceneOverviewPanel;
	private JPanel cutsceneDetailPanel;
	private JPanel fileHandlerPanel;
	private JList cutscenesList;	//Shows all the cutscenes as a list and allows indivudal cutscenes
									//to be added/removed/edited
	private DefaultListModel listModel;
	private JTextArea scriptArea;	//Shows the script as a text file (read-only)
	private JTable cutsceneEventsTable;	//Contains cutscene events
	private MyTableModel tableModel;
	private JButton addDialogButton;
	private JButton addCommandButton;
	private JButton addFinishButton;
	private JButton editButton;
	private JButton removeButton;
	
	public MainGUI(){
		super("Ace Fighter Cutscene Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		control = new Control();
		control.addListeningView(this);
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		cutsceneOverviewPanel = new JPanel();
		cutsceneOverviewPanel.setLayout(new GridLayout(1,1));
		cutsceneDetailPanel = new JPanel();
		cutsceneDetailPanel.setLayout(new GridLayout(1,1));
		fileHandlerPanel = new JPanel();
		getContentPane().add(outerPanel);
		addDropDownMenus();
		addSaveLoadPanel();
		addCutscenesListPanel();
		addCutsceneDetailPanel();
		mainPanel.add(cutsceneOverviewPanel);
		mainPanel.add(cutsceneDetailPanel);
		outerPanel.add(mainPanel,BorderLayout.CENTER);
		loadProgramConfiguration();
		pack();
		setSize(700,520);
		setResizable(false);
		setVisible(true);
	}
	
	private void loadProgramConfiguration(){
		try{
			control.loadConfiguration();
			control.loadCutscenes();
		}
		catch(FileHandlingException e){
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n Try setting the File path in the Program Preferences to\n fix the problem.");
		}
	}
	
	void addDropDownMenus(){
		JMenuBar menuBar = new JMenuBar();
		JMenu editMenu = new JMenu("Edit");
		JMenuItem preferencesItem = new JMenuItem("Preferences");
		preferencesItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnPreferencesWindow();
			}
		});
		editMenu.add(preferencesItem);
		menuBar.add(editMenu);
		this.setJMenuBar(menuBar);
	}
	
	void addSaveLoadPanel(){
		JPanel panel = new JPanel();
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					control.loadCutscenes();
				} catch (FileHandlingException e1) {
					JOptionPane.showMessageDialog(null, "File Handling Error: " + e1.getMessage());
				}
			}
		});
		panel.add(loadButton);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					control.saveCutscenes();
				}
				catch(Exception e1){
					JOptionPane.showMessageDialog(null,"Error: " + e1.getMessage());
				}
			}
		});
		panel.add(saveButton);
		outerPanel.add(panel,BorderLayout.NORTH);
	}
	
	void addCutscenesListPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Cutscenes"));
		listModel = new DefaultListModel();
		cutscenesList = new JList(listModel);
		cutscenesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cutscenesList.setBorder(BorderFactory.createEtchedBorder());
		cutscenesList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				control.setCurrentCutscene((String)cutscenesList.getSelectedValue());
			}
		});
		JScrollPane listScroller = new JScrollPane(cutscenesList);
		panel.add(listScroller,BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnCutsceneWindow();
			}
		});
		buttonPanel.add(addButton);
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				control.removeCutscene(cutscenesList.getSelectedIndex());
			}
		});
		buttonPanel.add(removeButton);
		panel.add(buttonPanel,BorderLayout.SOUTH);
		cutsceneOverviewPanel.add(panel);
	}
	
	void addCutsceneDetailPanel(){
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Cutscene Events"));
		panel.setLayout(new BorderLayout());
		JPanel topButtonPanel = new JPanel();
		addDialogButton = new JButton("Add Dialog");
		addDialogButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnDialogWindow();
			}
		});
		addDialogButton.setEnabled(false);
		topButtonPanel.add(addDialogButton);
		addCommandButton = new JButton("Add Command");
		addCommandButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnCommandWindow();
			}
		});
		addCommandButton.setEnabled(false);
		topButtonPanel.add(addCommandButton);
		panel.add(topButtonPanel,BorderLayout.NORTH);
		JPanel tablePanel = new JPanel();
		tableModel = new MyTableModel();
		tableModel.addColumn("Type");
		tableModel.addColumn("Condition");
		tableModel.addColumn("Actor");
		cutsceneEventsTable = new JTable(tableModel);
		setProperties(cutsceneEventsTable);
		JScrollPane tableScroller = new JScrollPane(cutsceneEventsTable);
		tablePanel.add(tableScroller);
		panel.add(tablePanel,BorderLayout.CENTER);
		JPanel bottomButtonPanel = new JPanel();
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int row = cutsceneEventsTable.getSelectedRow();
				int column = cutsceneEventsTable.getSelectedColumn();
				if(row>=0&&column>=0){
					if(cutsceneEventsTable.getValueAt(row, 0).equals("Dialog")){
						DialogEntity dialog = control.getDialog(row);
						editDialog(dialog);
					}
					else if(cutsceneEventsTable.getValueAt(row, 0).equals("Command"))
					{
						CommandEntity command = control.getCommand(row);
						editCommand(command);
					}
					else if(cutsceneEventsTable.getValueAt(row, 0).equals("Finish"))
					{
						FinishEntity finish = control.getFinish(row);
						editFinish(finish);
					}
				}
			}
		});
		editButton.setEnabled(false);
		bottomButtonPanel.add(editButton);
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int row = cutsceneEventsTable.getSelectedRow();
				int column = cutsceneEventsTable.getSelectedColumn();
				if(row>=0&&column>=0){
					Object type = tableModel.getValueAt(row,
							0);
					if(type.equals("Dialog")){
						control.removeCutsceneEntity(row);
					}
					else if(type.equals("Command")){
						control.removeCutsceneEntity(row);
					}
					else if(type.equals("Finish")){
						control.removeCutsceneEntity(row);
					}
				}
			}
		});
		removeButton.setEnabled(false);
		bottomButtonPanel.add(removeButton);
		addFinishButton = new JButton("Finish");
		addFinishButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spawnFinishWindow();
			}
		});
		addFinishButton.setEnabled(false);
		bottomButtonPanel.add(addFinishButton);
		panel.add(bottomButtonPanel,BorderLayout.SOUTH);
		tabbedPane.addTab("Overview",null,panel,"Provides an Overview of current cutscene");
		JPanel scriptPanel = new JPanel();
		scriptPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Script"));
		scriptArea = new JTextArea(20,22);
		scriptArea.setEditable(false);
		scriptArea.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane scriptScroller = new JScrollPane(scriptArea);
		scriptPanel.add(scriptScroller);
		tabbedPane.addTab("Script",null,scriptPanel,"Shows detailed script of current cutscene");
		cutsceneDetailPanel.add(tabbedPane);
	}

	public void updateCutsceneEvents(Cutscene cutscene) {
		if(cutscene!=null){
			//Update table
			tableModel.clear();
			for(int i = 0; i<cutscene.getCutsceneEntities().size();i++){
				CutsceneEntity entity = cutscene.getCutsceneEntities().get(i);
				String type = entity.getType();
				String condition = entity.getCondition();
				String actor = entity.getActor();
				tableModel.addRow(new Object[]{type,condition,actor});
			}
			activateDeactivateButtons();
			updateCutsceneEventsScriptArea(cutscene);
			repaint();
		}
	}

	public void updateCutscenes(ArrayList<Cutscene> cutscenes) {
		listModel.clear();
		for(Cutscene cutscene: cutscenes){
			listModel.addElement(cutscene.getAlias());
		}
		activateDeactivateButtons();
		repaint();
	}
	
	//Private helper methods
	private void updateCutsceneEventsScriptArea(Cutscene cutscene){
		String scriptOutputText = "";
		for(CutsceneEntity cutsceneEntity:cutscene.getCutsceneEntities()){
			if(cutsceneEntity.getType().equals("Dialog")){
				scriptOutputText+=cutsceneEntity.getActor()+" says:\n\n";
				for(String dialogLine:((DialogEntity)cutsceneEntity).getDialog()){
					scriptOutputText+=dialogLine+"\n";
				}
			}
			else if(cutsceneEntity.getType().equals("Command")){
				scriptOutputText+=cutsceneEntity.getActor()+" ";
				String command = ((CommandEntity)cutsceneEntity).getCommand();
				int duration = ((CommandEntity)cutsceneEntity).getDuration();
				if(command.equals("MoveX")){
					if(duration>=0){
						scriptOutputText+="moves to the right "+Math.abs(duration) + " units.\n";
					}
					else{
						scriptOutputText+="moves to the left "+Math.abs(duration) + " units.\n";
					}
				}
				else if(command.equals("MoveY")){
					scriptOutputText+="jumps ("+ Math.abs(duration) + " units).\n";
				}
				else if(command.equals("Fire")){
					if(duration>=0){
						scriptOutputText+="fires to the right ("+Math.abs(duration) + " units).\n";
					}
					else{
						scriptOutputText+="fires to the left ("+Math.abs(duration) + " units).\n";
					}
				}
			}
			else if(cutsceneEntity.getType().equals("Finish")){
				scriptOutputText+="Cutscene Ends";
			}
			scriptOutputText+="\n*************************************\n";
		}
		scriptArea.setText(scriptOutputText);
	}
	
	private void activateDeactivateButtons(){
		if(control.existsCurrentCutscene()){
			addDialogButton.setEnabled(true);
			addCommandButton.setEnabled(true);
			cutsceneEventsTable.setVisible(true);
			addFinishButton.setEnabled(true);
		}
		else{
			addDialogButton.setEnabled(false);
			addCommandButton.setEnabled(false);
			cutsceneEventsTable.setVisible(false);
			addFinishButton.setEnabled(false);
		}
		if(control.existCutsceneEvents()){
			editButton.setEnabled(true);
			removeButton.setEnabled(true);
		}
		else{
			editButton.setEnabled(false);
			removeButton.setEnabled(false);
		}
	}
	
	private void setProperties(JTable table){
		table.setBorder(BorderFactory.createEtchedBorder());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(270,245));
	}
	
	private void spawnPreferencesWindow(){
		new PreferencesWindow(control);
	}
	
	private void spawnCutsceneWindow(){
		new CutsceneWindow(control,cutscenesList.getSelectedIndex());
	}
	
	private void spawnDialogWindow(){
		new DialogWindow(control,cutsceneEventsTable.getSelectedRow(),true);
	}
	
	private void spawnCommandWindow(){
		new CommandWindow(control,cutsceneEventsTable.getSelectedRow(),true);
	}
	
	private void spawnFinishWindow(){
		new FinishWindow(control,cutsceneEventsTable.getSelectedRow(),true);
	}
	
	private void editDialog(DialogEntity dialog){
		DialogWindow dw = new DialogWindow(control,cutsceneEventsTable.getSelectedRow(),false);
		dw.setWindowContent(dialog);
	}
	
	private void editCommand(CommandEntity command){
		CommandWindow cw = new CommandWindow(control,cutsceneEventsTable.getSelectedRow(),false);
		cw.setWindowContent(command);
	}
	
	private void editFinish(FinishEntity finish){
		FinishWindow fw = new FinishWindow(control,cutsceneEventsTable.getSelectedRow(),false);
		fw.setWindowContent(finish);
	}

	public static void main(String []args){
		new MainGUI();
	}
}
