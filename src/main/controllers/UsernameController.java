package main.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.FileSaver;
import main.Main;
import main.Student;
import main.UsernameGenerator;

public abstract class UsernameController extends Application {

	private UsernameGenerator uGen = null;
	FileChooser fileChooser = new FileChooser();
	private FileSaver fileSaver = null;
	Stage stage = new Stage();
	List<Student> students = new ArrayList<>();

	private String yearGroup;
	private String yearOfAddition;

	@FXML
	private Button previewBtn;

	@FXML
	private Button saveBtn;

	@FXML
	private Button generateBtn;

	@FXML
	private TextField previewSurnameTextField;

	@FXML
	private TextField previewForenameTextField;

	@FXML
	private TextField previewUsernameTextField;

	@FXML
	protected ListView<Student> listView;

	@FXML
	private Label infoLabel;

	@FXML
	protected Label progressLabel;

	public UsernameController(String yearGroup, String yearOfAddition) {
		this.yearGroup = yearGroup;
		this.yearOfAddition = yearOfAddition;
		this.uGen = new UsernameGenerator(yearOfAddition);
		this.fileSaver = new FileSaver(stage, this.yearGroup, this.yearOfAddition);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			String infoText = "New Entries For Year " + getYearGroup() + " Students - "
					+ Calendar.getInstance().get(Calendar.YEAR);
			URL url = getClass().getResource("/main/resources/views/fxml/MultiUsernames.fxml");
			if (this instanceof SingleUsernameController) {
				url = getClass().getResource("/main/resources/views/fxml/SingleUsername.fxml");
				infoText = "Single Entry For Year " + getYearGroup() + " Student - " + getYearOfAddition();

			}
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(this);

			BorderPane root = (BorderPane) loader.load();
			root.setTop(new FXMLLoader(getClass().getResource("/main/resources/views/fxml/HelpBar.fxml")).load());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/css/application.css").toExternalForm());
			previewBtn.setDisable(true);
			infoLabel.setText(infoText);
			// disableButtons(false, true);
			primaryStage.getIcons().add(Main.logo);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void openPreview(Student student, boolean multi) {
		Stage secondaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/views/fxml/Preview.fxml"));
		loader.setController(this);
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/css/application.css").toExternalForm());
			previewSurnameTextField.setText(student.getSurname());
			previewForenameTextField.setText(student.getForename());
			previewUsernameTextField.setText(student.getUsername());

			Button updateUsernameBtn = (Button) loader.getNamespace().get("updateUsernameBtn");
			updateUsernameBtn.setDisable(true);

			previewUsernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.isBlank() || newValue.equals(student.getUsername())) {
					updateUsernameBtn.setDisable(true);
				} else {
					updateUsernameBtn.setDisable(false);
				}
			});
			updateUsernameBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					updateUsername(student);

				}
			});
			// disableButtons(true, true);
			secondaryStage.setOnCloseRequest(E -> {
				// disableButtons(multi, false);
				if (multi) {
					listView.setDisable(false);
				}
			});
			secondaryStage.getIcons().add(Main.logo);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateUsername(Student student) {
		int studentIndex = students.indexOf(student);
		String newUsername = previewUsernameTextField.getText().trim();

		students.get(studentIndex).setUsername(newUsername);
	}

	public void show() {
		try {
			start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disableButtons(boolean disableGenerate, boolean disableOthers) {
		generateBtn.setDisable(disableGenerate);
		previewBtn.setDisable(disableOthers);
		saveBtn.setDisable(disableOthers);

	}

	public abstract void generate();

	public void save() {
		File file = fileChooser.showSaveDialog(stage);

		if (fileSaver.start(students, file)) {
			progressLabel.setText("Usernames saved");
		} else {
			progressLabel.setText("Something went wrong with saving");
		}

	}

	public String getYearGroup() {
		return yearGroup;
	}

	public String getYearOfAddition() {
		return yearOfAddition;
	}

	public UsernameGenerator getUGen() {
		return uGen;
	}

}
