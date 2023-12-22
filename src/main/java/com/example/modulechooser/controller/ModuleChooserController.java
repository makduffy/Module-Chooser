package com.example.modulechooser.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.example.modulechooser.model.Course;
import com.example.modulechooser.model.Schedule;
import com.example.modulechooser.model.Module;
import com.example.modulechooser.model.StudentProfile;
import com.example.modulechooser.view.ModuleChooserRootPane;
import com.example.modulechooser.view.OverviewSelectionPane;
import com.example.modulechooser.view.ReserveModulesPane;
import com.example.modulechooser.view.SelectModulePane;
import com.example.modulechooser.view.CreateStudentProfilePane;
import com.example.modulechooser.view.ModuleChooserMenuBar;

public class ModuleChooserController {

	//fields to be used throughout class
	private ModuleChooserRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private ModuleChooserMenuBar mstmb;
	private SelectModulePane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane ovp;
	private StudentProfile sp;
	private Course compSci;
	private Course softEng;
	ArrayList<Module> modules;  

	public ModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getModuleSelectorPane();
		rmp = view.getReserveModulesPane();
		ovp = view.getOverviewSelectionPane();
		sp = new StudentProfile();

		//add courses to combobox in create student profile pane using the generateAndGetCourses helper method below
		modules = new ArrayList<Module>();
		cspp.addCoursesToComboBox(generateAndGetCourses());
		
		
		
		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		
		
		smp.addSubmitModulesHandler(new SubmitSelectedModules());
		rmp.addConfirmHandlers1(new ConfirmReservedModules1());
		rmp.addConfirmHandlers2(new ConfirmReservedModules2());
		ovp.addSaveOevrViewHandler(new SaveOverviewHandler());
		
		
		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addSaveHandler(new SaveStudentData());
		mstmb.addLoadHandler(new LoadStudentData());
		mstmb.addAboutHandler(new About());
	}
	
	private class SaveOverviewHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			PrintWriter writer1 =null;      
	         try {
				writer1 = new PrintWriter(new File("overview.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         String data = "";
	         data = data + ovp.getProfileText() + "\n";
	         data = data + ovp.getSelectedModulesText() + "\n";
	         data = data + ovp.getResModulesText() + "\n";
	         writer1.write(data);                                                   
	                         writer1.flush();  
	         writer1.close();  	
		}
		
	}
	
	private class About implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("Info");
			a.setContentText("This application captures the details of an individual\n "
					+ "second year undergraduate computing student and allows them \n"
					+ "to select their final year module options");
			a.show();
		
		}
		
	}

	private class SaveStudentData implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			FileOutputStream f = null;
			ObjectOutputStream o = null;

			// Write objects to file
			try {
				f = new FileOutputStream(new File("User.txt"));
				o = new ObjectOutputStream(f);
				o.writeObject(sp);
				o.close();
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		
	}

	
	private class LoadStudentData implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			FileInputStream fi = null;
			ObjectInputStream oi = null;

			// Read objects
			try {
				fi = new FileInputStream(new File("User.txt"));
				oi = new ObjectInputStream(fi);
				sp = (StudentProfile) oi.readObject();
				oi.close();
				fi.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	private class ConfirmReservedModules1 implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			ArrayList<Module> reservedSelectedModules = rmp.getReservedSelectedModules1(modules);
			for(Module module: reservedSelectedModules) {
				sp.addReservedModule(module);
			}
			rmp.openNextPane();
		}
		
	}
	
	private class ConfirmReservedModules2 implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			ArrayList<Module> reservedSelectedModules = rmp.getReservedSelectedModules2(modules);
			for(Module module: reservedSelectedModules) {
				sp.addReservedModule(module);
			}
			view.changeTab(3);
			ovp.printModuleData(sp);
		}
		
	}
	
	private class SubmitSelectedModules implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			ArrayList<Module> selectedModules1 = smp.getSelectedModules1(modules);
			ArrayList<Module> selectedModules2 = smp.getSelectedModules2(modules);
			for(Module module: modules) {
				if(module.getDelivery()==Schedule.YEAR_LONG) {
					sp.addSelectedModule(module);
				}
			}
			for(Module module : selectedModules1) {
				sp.addSelectedModule(module);
			}
			for(Module module : selectedModules2) {
				sp.addSelectedModule(module);
			}
			ArrayList<Module> unselectedModules1 = smp.getUnselectedModules1(modules);
			ArrayList<Module> unselectedModules2 = smp.getUnselectedModules2(modules);
			rmp.addDataToListViews(unselectedModules1, unselectedModules2);
			view.changeTab(2);
		}
		
	}
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			sp.setStudentCourse(cspp.getSelectedCourse());
			sp.setStudentPnumber(cspp.getStudentPnumber());
			sp.setStudentName(cspp.getStudentName());
			sp.setStudentEmail(cspp.getStudentEmail());
			sp.setSubmissionDate(cspp.getStudentDate());
			if(sp.getStudentCourse().getCourseName().equals("Computer Science")){
				smp.addModulesToListViews(compSci);
				
			}
			else {
				smp.addModulesToListViews(softEng);
			}
			view.changeTab(1);
			ovp.setProfileData(sp);
			
		}
	}


	//helper method - generates course and module data and returns courses within an array
	private Course[] generateAndGetCourses() {
//		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
//		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
//		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);	
//		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
//		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
//		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);	
//		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);	
//		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
//		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
//		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
//		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
//		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
//		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
//		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
//		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
//		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
//		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
//		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Schedule.TERM_2);

		compSci = new Course("Computer Science");
		softEng = new Course("Software Engineering");
		  try {
              FileReader reader = new FileReader("data.txt");
              Scanner scanner = new Scanner(reader);
              while (scanner.hasNext()){
                  String single = scanner.nextLine();
                  String[] line = single.split(",");
                  if (line[4].equals("T1")){
                	  Module module = new Module(line[0], line[1], Integer.valueOf(line[2]),Boolean.valueOf(line[3]), Schedule.TERM_1);
                	  modules.add(module);
                	  if(line[5].equals("CS")) {
                		  compSci.addModuleToCourse(module);
                	  }
                	  else if(line[5].equals("SE")) {
                		  softEng.addModuleToCourse(module);
                	  }
                	  else {
                		  compSci.addModuleToCourse(module);
                		  softEng.addModuleToCourse(module);
                	  }
                  }else if(line[4].equals("T2")){
                	  Module module = new Module(line[0], line[1], Integer.valueOf(line[2]),Boolean.valueOf(line[3]), Schedule.TERM_2);
                	  modules.add(module);
                	  if(line[5].equals("CS")) {
                		  compSci.addModuleToCourse(module);
                	  }
                	  else if(line[5].equals("SE")) {
                		  softEng.addModuleToCourse(module);
                	  }
                	  else {
                		  compSci.addModuleToCourse(module);
                		  softEng.addModuleToCourse(module);
                	  } 	  
                  }
                  else {
                	  Module module = new Module(line[0], line[1], Integer.valueOf(line[2]),Boolean.valueOf(line[3]), Schedule.YEAR_LONG);
                	  modules.add(module);
                	  if(line[5].equals("CE")) {
                		  compSci.addModuleToCourse(module);
                	  }
                	  else if(line[5].equals("SE")) {
                		  softEng.addModuleToCourse(module);
                	  }
                	  else {
                		  compSci.addModuleToCourse(module);
                		  softEng.addModuleToCourse(module);
                	  } 
                  }
              }
          }catch (Exception e){
              e.printStackTrace();
          }
		
//		compSci.addModuleToCourse(imat3423);
//		compSci.addModuleToCourse(ctec3451);
//		compSci.addModuleToCourse(ctec3902_CompSci);
//		compSci.addModuleToCourse(ctec3110);
//		compSci.addModuleToCourse(ctec3605);
//		compSci.addModuleToCourse(ctec3606);
//		compSci.addModuleToCourse(ctec3410);
//		compSci.addModuleToCourse(ctec3904);
//		compSci.addModuleToCourse(ctec3905);
//		compSci.addModuleToCourse(ctec3906);
//		compSci.addModuleToCourse(ctec3911);
//		compSci.addModuleToCourse(imat3410);
//		compSci.addModuleToCourse(imat3406);
//		compSci.addModuleToCourse(imat3611);
//		compSci.addModuleToCourse(imat3613);
//		compSci.addModuleToCourse(imat3614);
//		compSci.addModuleToCourse(imat3428_CompSci);
//
//		softEng.addModuleToCourse(imat3423);
//		softEng.addModuleToCourse(ctec3451);
//		softEng.addModuleToCourse(ctec3902_SoftEng);
//		softEng.addModuleToCourse(ctec3110);
//		softEng.addModuleToCourse(ctec3605);
//		softEng.addModuleToCourse(ctec3606);
//		softEng.addModuleToCourse(ctec3410);
//		softEng.addModuleToCourse(ctec3904);
//		softEng.addModuleToCourse(ctec3905);
//		softEng.addModuleToCourse(ctec3906);
//		softEng.addModuleToCourse(ctec3911);
//		softEng.addModuleToCourse(imat3410);
//		softEng.addModuleToCourse(imat3406);
//		softEng.addModuleToCourse(imat3611);
//		softEng.addModuleToCourse(imat3613);
//		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
