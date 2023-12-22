package com.example.modulechooser.view;

import java.util.Iterator;

import com.example.modulechooser.model.Module;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.modulechooser.model.StudentProfile;

public class OverviewSelectionPane extends BorderPane {
	
	private TextArea studentProfile ,selectedModules , reservedModules;
	private ScrollPane profileScroll , selectedScroll, reservedscroll;
	private VBox vbox1;
	private HBox hbox1;
	private Button save;
	public OverviewSelectionPane() {
		
		this.studentProfile = new TextArea();
		this.selectedModules = new TextArea();
		this.reservedModules = new TextArea();
	
		this.profileScroll = new ScrollPane();
		this.profileScroll.setContent(studentProfile);
		
		
		this.selectedScroll = new ScrollPane();
		this.selectedScroll.setContent(selectedModules);
		
		this.reservedscroll = new ScrollPane();
		this.reservedscroll.setContent(reservedModules);
		
		this.vbox1 = new VBox(10);
		this.hbox1 = new HBox(20);
	
		this.save = new Button("Save Overview");
		this.save.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		hbox1.getChildren().addAll(selectedScroll , reservedscroll);
		
		
		vbox1.getChildren().addAll(profileScroll , hbox1, save);
		vbox1.setMaxWidth(700);
		vbox1.setAlignment(Pos.BOTTOM_CENTER);
		profileScroll.setMaxWidth(980);
		
		studentProfile.setPrefWidth(980);
		
		setPadding(new Insets(20));
		setCenter(vbox1);
	
	}
	public void setProfileData(StudentProfile sp) {
		String data = "";
		data = data + "Name: " + sp.getStudentName().getFullName() + "\n";
		data = data + "PNo: " + sp.getStudentPnumber() + "\n";
		data = data + "Email: " + sp.getStudentEmail() + "\n";
		data = data + "Date: " + sp.getSubmissionDate() + "\n";
		data = data + "Course: " + sp.getStudentCourse() + "\n";
		studentProfile.setText(data);
	}
	public void printModuleData(StudentProfile sp) {
		Iterator<Module> iterator = sp.getAllSelectedModules().iterator();
		String data = "Selected Modules\n==========\n";
		while(iterator.hasNext()) {
			data = data + iterator.next().actualToString() + "\n";
			selectedModules.setText(data);
		}
		
		iterator = sp.getAllReservedModules().iterator();
		data = "Reserved Modules\n==========\n";
		while(iterator.hasNext()) {
			data = data + iterator.next().actualToString() + "\n";
			reservedModules.setText(data);
		}
	}
	public String getProfileText() {
		return studentProfile.getText();
	}
	public String getSelectedModulesText() {
		return selectedModules.getText();
	}
	public String getResModulesText() {
		return reservedModules.getText();
	}
	public void addSaveOevrViewHandler(EventHandler<ActionEvent> handler) {
		save.setOnAction(handler);
	}
	
	
	
}
