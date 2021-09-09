package main.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import main.YearPicker;

public class MainController {

	@FXML
	private Button helpBtn;
	YearPicker yearPicker = new YearPicker();
	HelpPopup helpPopup = new HelpPopup();
	boolean showPopup = false;

	public void createNewYear7() {
		createNewStudents(7);
	}

	public void createNewYear12() {
		createNewStudents(12);
	}

	private void createNewStudents(int yearGroup) {
		String yearAddition = yearPicker.getYear(yearGroup);
		if (yearAddition != null) {
			new MultiUsernamesController(String.valueOf(yearGroup), yearAddition).show();
		}

	}

	public void help() {
		showPopup = !showPopup;
		if (showPopup) {
			System.out.println("open");
			Bounds bounds = helpBtn.getBoundsInLocal();
			Bounds screenBounds = helpBtn.localToScreen(bounds);
			helpPopup.show(screenBounds);
		} else {
			helpPopup.hide();
		}

	}

	public void createSingleStudent() {
		String yearGroup = yearPicker.getYearGroup();
		if (yearGroup != null) {
			String yearAddition = yearPicker.getYear(yearGroup);
			if (yearAddition != null) {
				new SingleUsernameController(yearGroup, yearAddition).show();
			}
		}

	}
}
