package com.example.modulechooser.view;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.modulechooser.model.Module;

public class ReserveModulesPane extends BorderPane {
	
	private Accordion accordian;
	private TitledPane term1;
	private TitledPane term2;
	private VBox vbox1 ,vbox2, vbox3, vbox4 ,vbox5, vbox6;
	private HBox hbox1 , hbox2, hbox3 , hbox4;
	private ListView<String> unselectedTerm1, reservedTerm1, unselectedTerm2, reservedTerm2;
	private Label label1, label2, label3, label4, label5, label6;
	private Button addTerm1Btn, removeTerm1Btn, confirmTerm1Btn, addTerm2Btn, removeTerm2Btn, confirmTerm2Btn;
	public ReserveModulesPane() {
		super();
		this.accordian = new Accordion();
		this.term1 = new TitledPane("Term 1 Modules", vbox1);
		this.term2 = new TitledPane("Term 2 Modules", vbox4);
		this.vbox1 = new VBox(10);
		this.vbox2 = new VBox(10);
		this.vbox3 = new VBox(10);
		this.vbox4 = new VBox(10);
		this.vbox5 = new VBox(10);
		this.vbox6 = new VBox(10);
		this.hbox1 = new HBox(20);
		this.hbox2 = new HBox(20);
		this.hbox3 = new HBox(20);
		this.hbox4 = new HBox(20);
		this.unselectedTerm1 = new ListView<String>();
		this.reservedTerm1 = new ListView<String>();
		this.unselectedTerm2 = new ListView<String>();
		this.reservedTerm2 = new ListView<String>();
		this.label1 = new Label("Unselected Term 1 Modules");
		this.label2 = new Label("Reserved Term 1 Modules");
		this.label3 = new Label("Reserve 0 Credits Worth of Term 1 Modules");
		this.label4 = new Label("Unselected Term 2 Modules");
		this.label5 = new Label("Reserved Term 2 Modules");
		this.label6 = new Label("Reserve 0 Credits Worth of Term 2 Modules");
		this.addTerm1Btn = new Button("Add");
		this.addTerm1Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (reservedTerm1.getItems().size() < 2) {
					String item = unselectedTerm1.getSelectionModel().getSelectedItem();
					unselectedTerm1.getItems().remove(item);
					unselectedTerm2.getItems().remove(item);
					reservedTerm1.getItems().add(item);
				} else {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Credit hours completed");
					a.show();
				}
			}
		});
		this.removeTerm1Btn = new Button("Remove");
		this.removeTerm1Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
					String item = reservedTerm1.getSelectionModel().getSelectedItem();
					reservedTerm1.getItems().remove(item);
					unselectedTerm1.getItems().add(item);
					unselectedTerm2.getItems().add(item);
					
	
			}
		});
		this.confirmTerm1Btn = new Button("Confirm");
		this.confirmTerm1Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
			}
		});
		
		this.addTerm2Btn = new Button("Add");
		this.addTerm2Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (reservedTerm2.getItems().size() < 2) {
					String item = unselectedTerm2.getSelectionModel().getSelectedItem();
					unselectedTerm1.getItems().remove(item);
					
					unselectedTerm2.getItems().remove(item);
					reservedTerm2.getItems().add(item);
				} else {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Credit hours completed");
					a.show();
				}

			}
		});
		this.removeTerm2Btn = new Button("Remove");
		this.removeTerm2Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String item = reservedTerm2.getSelectionModel().getSelectedItem();
				reservedTerm2.getItems().remove(item);
				unselectedTerm2.getItems().add(item);
				unselectedTerm1.getItems().add(item);
							
			}
		});
		this.confirmTerm2Btn = new Button("Confirm");
		this.confirmTerm2Btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		vbox2.getChildren().addAll(label1, unselectedTerm1);
		vbox3.getChildren().addAll(label2,reservedTerm1);
		hbox1.getChildren().addAll(vbox2,vbox3);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(label3, addTerm1Btn ,removeTerm1Btn ,confirmTerm1Btn);
		hbox2.setAlignment(Pos.CENTER);
		
		
		vbox1.getChildren().addAll(hbox1 ,hbox2);
		
		
		term1.setContent(vbox1);
		
		vbox5.getChildren().addAll(label4, unselectedTerm2);
		vbox6.getChildren().addAll(label5,reservedTerm2);
		hbox3.getChildren().addAll(vbox5,vbox6);
		hbox3.setAlignment(Pos.CENTER);
		
		hbox4.getChildren().addAll(label6, addTerm2Btn ,removeTerm2Btn ,confirmTerm2Btn);
		hbox4.setAlignment(Pos.CENTER);
		
		vbox4.getChildren().addAll(hbox3 ,hbox4);
		
		term2.setContent(vbox4);
		
		
		
		accordian.getPanes().addAll(term1 ,term2);
		accordian.setExpandedPane(term1);
		
		setCenter(accordian);
	}
	public void addDataToListViews(ArrayList<Module> unselectedModules1, ArrayList<Module> unselectedModules2) {
		for(Module module : unselectedModules1) {
			unselectedTerm1.getItems().add(module.toString());
		}
		for(Module module : unselectedModules1) {
			unselectedTerm2.getItems().add(module.toString());
		}
	}
	
	
	public void openNextPane() {
		accordian.setExpandedPane(term2);
	}
	public void addConfirmHandlers1(EventHandler<ActionEvent> handler) {
		this.confirmTerm1Btn.setOnAction(handler);
	}
	
	public void addConfirmHandlers2(EventHandler<ActionEvent> handler) {
		this.confirmTerm2Btn.setOnAction(handler);
	}
	public ArrayList<Module> getReservedSelectedModules1(ArrayList<Module> modules) {
		ArrayList<Module> selectedModules = new ArrayList<Module>();
		ObservableList<String> list2 = reservedTerm1.getItems();
		for (String string : list2) {
			String[] splitted = string.split(",");
			String id = splitted[0];
			for(Module module: modules) {
				if(module.getModuleCode().equals(id)) {
					selectedModules.add(module);
				}
			}
		}

		return selectedModules;

	}
	public ArrayList<Module> getReservedSelectedModules2(ArrayList<Module> modules) {
		ArrayList<Module> selectedModules = new ArrayList<Module>();
		ObservableList<String> list2 = reservedTerm2.getItems();
		for (String string : list2) {
			String[] splitted = string.split(",");
			String id = splitted[0];
			for(Module module: modules) {
				if(module.getModuleCode().equals(id)) {
					selectedModules.add(module);
				}
			}
		}

		return selectedModules;
	}
	
	

}
