package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileHandler {

	public FileHandler() {
		// TODO Auto-generated constructor stub
	}

	public static List<Student> asStudents(File file) throws IOException, InvalidFormatException {
		List<Student> students = null;
		if (file != null) {
			if (file.getAbsolutePath().endsWith(".xlsx")) {
				students = readFromXLSX(file);
			} else if (file.getAbsolutePath().endsWith(".csv")) {
				students = readFromCSV(file);
			}
		}

		return students;

	}

	private static List<Student> readFromCSV(File file) throws IOException {
		List<Student> students = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String l = reader.readLine();

			if (l.replaceAll(" ", "").toLowerCase().equals("surname,forename")) {
				students = new ArrayList<>();
				while ((l = reader.readLine()) != null) {
					String[] newLine = l.split(",");
					String surname = newLine[0];
					String forename = newLine[1];
					if (!(surname.isBlank() || forename.isBlank())) {
						students.add(new Student(newLine[0], newLine[1]));
					}

				}
				reader.close();
			}

		} catch (FileNotFoundException e) {
			students = null;
		}
		return students;

	}

	private static List<Student> readFromXLSX(File file) throws IOException {
		List<Student> students = null;
		if (file != null) {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			XSSFRow headerRow = sheet.getRow(0);
			String headerSurname = headerRow.getCell(0).getStringCellValue().toLowerCase();
			String headerForename = headerRow.getCell(1).getStringCellValue().toLowerCase();
			if (headerSurname.equals("surname") && headerForename.equals("forename")) {
				// Get iterator to all the rows in current sheet
				sheet.removeRow(headerRow);
				students = new ArrayList<>();
				for (Row row : sheet) {
					String surname = row.getCell(0).getStringCellValue();
					String forename = row.getCell(1).getStringCellValue();
					if (!(surname.isBlank() || forename.isBlank())) {
						students.add(new Student(surname, forename));
					}
					System.out.println(surname + "," + forename);

				}
			}
			workBook.close();

		}

		return students;
	}

}
