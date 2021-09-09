package main.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.FileHandler;
import main.Main;
import main.Student;

public class MultiUsernamesController extends UsernameController {
	@FXML
	private TextField filePathTextField;

	@FXML
	private Label progressLabel;
	@FXML
	private ImageView progressIcon;

	public MultiUsernamesController(String yearGroup, String yearOfAddition) {
		super(yearGroup, yearOfAddition);
	}

	@Override
	public void generate() {
		progressLabel.setText("");
		students = new ArrayList<>();
		String filePath = filePathTextField.getText();
		File file = (!filePath.isBlank()) ? new File(filePath) : openFileDialog();
		if (file != null) {
			progressIcon.setVisible(true);
			try {
				students = FileHandler.asStudents(file);
				students = getUGen().createUserNames(students);
				boolean disabled = (students == null || students.isEmpty());
				progressIcon.setVisible(false);
				if (!disabled) {
					progressLabel.setText("Usernames generated");
				} else {
					progressLabel.setText("Something went wrong with generating");
				}
				disableButtons(false, disabled);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public File openFileDialog() {
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			filePathTextField.setText(file.getAbsolutePath());
		}
		return file;

	}

	@SuppressWarnings("unchecked")
	public void openPreviewList() {
		Stage secondaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/views/fxml/PreviewList.fxml"));
		loader.setController(this);
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			listView = (ListView<Student>) loader.getNamespace().get("listView");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/css/application.css").toExternalForm());
			ObservableList<Student> items = FXCollections.observableArrayList(students);
			listView.setItems(items);
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

				@Override
				public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
					// Your action here
					openPreview(newValue, true);
					listView.setDisable(true);
				}
			});

			// disableButtons(true, true);
			secondaryStage.getIcons().add(Main.logo);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
