package com.example.modulechooser.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class ModuleChooserRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private ModuleChooserMenuBar mstmb;
	private SelectModulePane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane ovs;
	private TabPane tp;

	public ModuleChooserRootPane() {
		// create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// create panes
		cspp = new CreateStudentProfilePane();
		smp = new SelectModulePane();
		rmp = new ReserveModulesPane();
		ovs = new OverviewSelectionPane();
		
		
		// create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Reserve Modules", rmp);
		Tab t4 = new Tab("Overview Selection", ovs);

		// add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);
		
		// create menu bar
		mstmb = new ModuleChooserMenuBar();

		// add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);

	}

	// methods allowing sub-containers to be accessed by the com.example.modulechooser.controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}

	public ModuleChooserMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}

	public SelectModulePane  getModuleSelectorPane() {
		return smp;
	}
	public ReserveModulesPane getReserveModulesPane() {
		return rmp;
	}
	public OverviewSelectionPane getOverviewSelectionPane() {
		return ovs;
	}

	
	
	// method to allow the com.example.modulechooser.controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
