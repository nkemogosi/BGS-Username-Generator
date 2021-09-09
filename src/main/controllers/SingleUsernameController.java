package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.Student;

public class SingleUsernameController extends UsernameController {

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField forenameTextField;

	public SingleUsernameController(String yearGroup, String yearOfAddition) {
		super(yearGroup, yearOfAddition);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		progressLabel.setText("");
		String surname = surnameTextField.getText();
		String forename = forenameTextField.getText();

		if (!(surname.isBlank() || forename.isBlank())) {
			Student student = new Student(surname, forename);
			this.students.clear();
			this.students.add(this.getUGen().createUsername(student, null));
			disableButtons(false, false);
			progressLabel.setText("Usernames generated");
		}

	}

	public void openPreviewWindow() {
		openPreview(students.get(0), false);

	}

}
