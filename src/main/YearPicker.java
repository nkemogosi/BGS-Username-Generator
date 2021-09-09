package main;

import java.util.Calendar;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class YearPicker {

	public YearPicker() {

	}

	public String getYearGroup() {
		Integer[] yearGroups = { 7, 8, 9, 10, 11, 12, 13 };
		ChoiceDialog<Integer> yearGroupChoice = new ChoiceDialog<>(yearGroups[0], yearGroups);
		yearGroupChoice.setTitle("Select Year Group");
		return getResult(yearGroupChoice);
	}

	public String getYear(String yearOfAddition) {
		return getYear(Integer.parseInt(yearOfAddition));
	}

	public String getYear(int yearOfAddition) {
		int startYear = Calendar.getInstance().get(Calendar.YEAR) - (yearOfAddition - 7);
		ChoiceDialog<Integer> yearAdditionChoice = new ChoiceDialog<>(null, new Integer[] { startYear, startYear - 1 });
		yearAdditionChoice.setTitle("Select Year of Admission");

		return getResult(yearAdditionChoice);
	}

	public String getResult(ChoiceDialog<Integer> dialog) {
		Optional<Integer> result = dialog.showAndWait();
		String stringResult = null;
		if (result.isPresent()) {
			stringResult = String.valueOf(result.get());
		}
		return stringResult;
	}

}
