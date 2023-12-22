package com.example.modulechooser.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.modulechooser.model.Course;
import com.example.modulechooser.model.Module;
import com.example.modulechooser.model.Schedule;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Consumer;


public class SelectModulePane extends BorderPane {
	private VBox vBox1, vBox2, vBox3;
	private HBox hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7;
	private Label label1, label2, label3, label4, label5, label6, label7, label8, label9;
	private TextField txt1, txt2;
	private ListView<String> unselectedModule1, unselectedModule2, selectedModule1, selectedModule2,
			selectedModuleYearLong;
	private Button addModule1Btn, addModule2Btn, removeModule1Btn, removeModule2Btn, resetBtn, submitBtn;

	public SelectModulePane() {
		label1 = new Label("Unselected Term 1 Modules");
		label2 = new Label("Unselected Term 2 Modules");
		label3 = new Label("Selected Term 1 Modules");
		label4 = new Label("Selected Term 2 Modules");
		label5 = new Label("Selected Year Long Modules");
		label6 = new Label("Term 1");
		label7 = new Label("Term 2");
		label8 = new Label("Current Term 1 Credits:");
		label9 = new Label("Current Term 2 Credits:");

		txt1 = new TextField("15");
		txt2 = new TextField("15");

		unselectedModule1 = new ListView<>();
		unselectedModule2 = new ListView<>();
		selectedModule1 = new ListView<>();
		selectedModule2 = new ListView<>();
		selectedModuleYearLong = new ListView<>();

		addModule1Btn = new Button("Add");
		addModule2Btn = new Button("Add");
		removeModule1Btn = new Button("Remove");
		removeModule2Btn = new Button("Remove");
		resetBtn = new Button("Reset");
		submitBtn = new Button("Submit");

		addModule1Btn.setOnAction(actionEvent -> {
			if (Integer.valueOf(txt1.getText()) < 60) {
				String item = unselectedModule1.getSelectionModel().getSelectedItem();
				unselectedModule1.getItems().remove(item);
				selectedModule1.getItems().add(item);
				calculateCredits("A", item, 1);
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("60 credit hours completed");
				a.show();
			}
		});
		addModule2Btn.setOnAction(actionEvent -> {
			if (Integer.valueOf(txt2.getText()) < 60) {
				String item = unselectedModule2.getSelectionModel().getSelectedItem();
				unselectedModule2.getItems().remove(item);
				selectedModule2.getItems().add(item);
				calculateCredits("A", item, 2);
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("60 credit hours completed");
				a.show();
			}
		});
		removeModule1Btn.setOnAction(actionEvent -> {
			String item = selectedModule1.getSelectionModel().getSelectedItem();
			unselectedModule1.getItems().add(item);
			selectedModule1.getItems().remove(item);
			calculateCredits("S", item, 1);

		});
		removeModule2Btn.setOnAction(actionEvent -> {
			String item = selectedModule2.getSelectionModel().getSelectedItem();
			unselectedModule2.getItems().add(item);
			selectedModule2.getItems().remove(item);
			calculateCredits("S", item, 2);
		});
		resetBtn.setOnAction(actionEvent -> {
			unselectedModule1.getItems().clear();
			unselectedModule2.getItems().clear();
			selectedModule1.getItems().clear();
			selectedModule2.getItems().clear();
			selectedModuleYearLong.getItems().clear();
			txt1.setText("0");
			txt2.setText("0");
		});
		
		
		vBox1 = new VBox(10);
		vBox2 = new VBox(10);
		vBox3 = new VBox();

		hBox1 = new HBox();
		hBox2 = new HBox();
		hBox3 = new HBox();
		hBox4 = new HBox();
		hBox5 = new HBox();
		hBox6 = new HBox(10);
		hBox7 = new HBox(10);

		unselectedModule1.setMaxHeight(100);
		unselectedModule1.setMaxWidth(350);
		unselectedModule2.setMaxHeight(100);
		unselectedModule2.setMaxWidth(350);
		selectedModule1.setMaxHeight(100);
		selectedModule1.setMaxWidth(350);
		selectedModule2.setMaxHeight(100);
		selectedModule2.setMaxWidth(350);
		selectedModuleYearLong.setMaxHeight(50);
		selectedModuleYearLong.setMaxWidth(350);
		vBox1.setSpacing(10);
		vBox1.setAlignment(Pos.CENTER);
		vBox2.setSpacing(10);
		vBox2.setAlignment(Pos.CENTER);

		hBox1.setSpacing(10);
		hBox1.setAlignment(Pos.CENTER);
		hBox2.setSpacing(10);
		hBox2.setAlignment(Pos.CENTER);

		hBox3.setSpacing(20);
		hBox3.setAlignment(Pos.CENTER);

		vBox3.setAlignment(Pos.CENTER);
		vBox3.setSpacing(10);
		hBox4.setAlignment(Pos.CENTER);
		hBox4.setSpacing(10);
		hBox5.setAlignment(Pos.CENTER);
		hBox5.setSpacing(30);

		hBox1.getChildren().addAll(label6, addModule1Btn, removeModule1Btn);

		hBox2.getChildren().addAll(label7, addModule2Btn, removeModule2Btn);
		hBox3.getChildren().addAll(vBox1, vBox2);
		hBox5.getChildren().addAll(resetBtn, submitBtn);
		hBox6.getChildren().addAll(label8, txt1);
		hBox7.getChildren().addAll(label9, txt2);

		vBox1.getChildren().addAll(label1, unselectedModule1, hBox1, label2, unselectedModule2, hBox2, hBox6);
		vBox2.getChildren().addAll(label5, selectedModuleYearLong, label3, selectedModule1, label4, selectedModule2,
				hBox7);
		vBox3.getChildren().addAll(hBox4, hBox5);

		setPadding(new Insets(10));

		setCenter(hBox3);
		setBottom(vBox3);
		setMargin(getCenter(), new Insets(10));
		setMargin(getBottom(), new Insets(10));
	}

	private void calculateCredits(String operationType, String item, int i) {
		String[] splitted = item.split(",");
		int credit = Integer.valueOf(splitted[2]);

		if (operationType.equals("A")) {
			if (i == 1) {
				txt1.setText(String.valueOf((Integer.valueOf(txt1.getText()) + credit)));

			} else {
				txt2.setText(String.valueOf((Integer.valueOf(txt2.getText()) + credit)));

			}
		} else {
			if (i == 1) {
				txt1.setText(String.valueOf((Integer.valueOf(txt1.getText()) - credit)));

			} else {
				txt2.setText(String.valueOf((Integer.valueOf(txt2.getText()) - credit)));

			}
		}
	}

	public ListView<String> getUnselectedEvent1() {
		return unselectedModule1;
	}

	public void setUnselectedEvent1(ListView<String> unselectedEvent1) {
		this.unselectedModule1 = unselectedEvent1;
	}

	public ListView<String> getUnselectedEvent2() {
		return unselectedModule2;
	}

	public void setUnselectedEvent2(ListView<String> unselectedEvent2) {
		this.unselectedModule2 = unselectedEvent2;
	}

	public ListView<String> getSelectedEvent1() {
		return selectedModule1;
	}

	public void setSelectedEvent1(ListView<String> selectedEvent1) {
		this.selectedModule1 = selectedEvent1;
	}

	public ListView<String> getSelectedEvent2() {
		return selectedModule2;
	}

	public void setSelectedEvent2(ListView<String> selectedEvent2) {
		this.selectedModule2 = selectedEvent2;
	}

	public ListView<String> getSelectedEventBoth() {
		return selectedModuleYearLong;
	}

	public void setSelectedEventBoth(ListView<String> selectedEventBoth) {
		this.selectedModuleYearLong = selectedEventBoth;
	}

	public Button getAddEvent1Btn() {
		return addModule1Btn;
	}

	public void setAddEvent1Btn(Button addEvent1Btn) {
		this.addModule1Btn = addEvent1Btn;
	}

	public Button getAddEvent2Btn() {
		return addModule2Btn;
	}

	public void setAddEvent2Btn(Button addEvent2Btn) {
		this.addModule2Btn = addEvent2Btn;
	}

	public Button getRemoveEvent1Btn() {
		return removeModule1Btn;
	}

	public void setRemoveEvent1Btn(Button removeEvent1Btn) {
		this.removeModule1Btn = removeEvent1Btn;
	}

	public Button getRemoveEvent2Btn() {
		return removeModule2Btn;
	}

	public void setRemoveEvent2Btn(Button removeEvent2Btn) {
		this.removeModule2Btn = removeEvent2Btn;
	}

	public Button getResetBtn() {
		return resetBtn;
	}

	public void setResetBtn(Button resetBtn) {
		this.resetBtn = resetBtn;
	}

	public Button getSubmitBtn() {
		return submitBtn;
	}

	public void setSubmitBtn(Button submitBtn) {
		this.submitBtn = submitBtn;
	}

	public void addModulesToListViews(Course course) {
		Collection<Module> modules = course.getAllModulesOnCourse();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.getDelivery() == Schedule.TERM_1) {
				unselectedModule1.getItems().add(module.toString());
				if (module.isMandatory()) {
					selectedModule1.getItems().add(module.toString());
				}
			} else if (module.getDelivery() == Schedule.TERM_2) {
				unselectedModule2.getItems().add(module.toString());
				if (module.isMandatory()) {
					selectedModule2.getItems().add(module.toString());
				}
			} else {
				selectedModuleYearLong.getItems().add(module.toString());
			}
		}
		calculateCreditsFirstTime();

	}

	public void calculateCreditsFirstTime() {
		ObservableList<String> list = selectedModule1.getItems();
		ObservableList<String> list2 = selectedModule2.getItems();

		for (String string : list) {
			String[] splitted = string.split(",");
			int credit = Integer.valueOf(splitted[2]);
			txt1.setText(String.valueOf((Integer.valueOf(txt1.getText()) + credit)));
		}
		for (String string : list2) {
			String[] splitted = string.split(",");
			int credit = Integer.valueOf(splitted[2]);
			txt2.setText(String.valueOf((Integer.valueOf(txt2.getText()) + credit)));
		}
	}

	public void addSubmitModulesHandler(EventHandler<ActionEvent> handler) {
		submitBtn.setOnAction(handler);
	}

	public ArrayList<Module> getSelectedModules1(ArrayList<Module> modules) {
		
		ArrayList<Module> selectedModules = new ArrayList<Module>();
		ObservableList<String> list = selectedModule1.getItems();

		for (String string : list) {
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
	public ArrayList<Module> getSelectedModules2(ArrayList<Module> modules) {
		
		ArrayList<Module> selectedModules = new ArrayList<Module>();
		ObservableList<String> list2 = selectedModule2.getItems();
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

	public ArrayList<Module> getUnselectedModules1(ArrayList<Module> modules) {
		
		ArrayList<Module> unSelectedModules = new ArrayList<Module>();
		
		ObservableList<String> list = unselectedModule1.getItems();
		for (String string : list) {
			String[] splitted = string.split(",");
			String id = splitted[0];
			for(Module module: modules) {
				if(module.getModuleCode().equals(id)) {
					unSelectedModules.add(module);
				}
			}
		}
		
		return unSelectedModules;
	}

	public ArrayList<Module> getUnselectedModules2(ArrayList<Module> modules) {
		ArrayList<Module> unSelectedModules = new ArrayList<Module>();
		
		ObservableList<String> list = unselectedModule2.getItems();
		for (String string : list) {
			String[] splitted = string.split(",");
			String id = splitted[0];
			for(Module module: modules) {
				if(module.getModuleCode().equals(id)) {
					unSelectedModules.add(module);
				}
			}
		}
		
		return unSelectedModules;
	
	}
}
