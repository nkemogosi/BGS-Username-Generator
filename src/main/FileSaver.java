package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.stage.Stage;

public class FileSaver {
	private String yearGroup;
	private String yearOfAddition;

	public FileSaver(Stage stage, String yearGroup, String yearOfAddition) {
		super();
		this.yearGroup = yearGroup;
		this.yearOfAddition = yearOfAddition;
	}

	public boolean start(List<Student> students, File file) {
		if (file != null) { // creates save dialog
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String timestamp = "-"+sdf.format(new Date());
			try {
//creating the files for gmail and AD. AD script is a .txt to prevent accidental  misclicks
				String filepathGmail = file.getAbsolutePath() + "Gmail" + timestamp + ".csv";
				writeGmail(students, new FileWriter(filepathGmail));
				String filepathSystem = file.getAbsolutePath() + "System" + timestamp + ".txt";
				writeSystem(students, new FileWriter(filepathSystem));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return false;
	}

	void writeGmail(List<Student> students, FileWriter fW) { // this creates a file in the format for creating Gmail
																// accounts in bulk
		try {
			BufferedWriter bW = new BufferedWriter(fW);
			for (Student s : students) {
				String stuInfo = s.getForename() + "," + s.getSurname() + "," + s.getEmail() + "," + s.getPassword();// writes
				// the
				// students
				// in to a
				// format
				// appropriate
				// for Gmail
				// csv files
				bW.write(stuInfo);
				bW.newLine();
			}
			bW.close();
		} catch (IOException e) {
			e.printStackTrace(); // if something is wrong with the buffer
		}
	}

	void writeSystem(List<Student> students, FileWriter fW) { // this creates the script for creating AD accounts in
																// bulk
		try {
			BufferedWriter bW = new BufferedWriter(fW);
			for (Student s : students) {
				String stuInfo = "dsadd user \"cn=" + s.getUsername() + ",ou=" + this.yearOfAddition + " - Year "
						+ this.yearGroup + ","
						+ "ou=students,ou=users,ou=Bexley Grammar School,dc=bexleygs,dc=internal\" -ln \""
						+ s.getSurname() + "\" -fn \"" + s.getForename() + "\" -display " + s.getUsername() + " -upn \""
						+ s.getEmail() + "\" -samID " + s.getUsername() + " -pwd " + s.getPassword()
						+ " -mustchpwd yes -acctexpires 1825" + " -memberof \"cn=" + this.yearOfAddition + "Students"
						+ ",ou=NEW Security Groups,ou=Groups,ou=Bexley Grammar School,dc=bexleygs,dc=internal\""
						+ " \"cn=All Students"
						+ ",ou=NEW Security  Groups,ou=Groups,ou=Bexley Grammar School,dc=bexleygs,dc=internal\""
						+ " -email \"" + s.getEmail() + "\"" + " hmdir \\\\bex-file-01\\studenthome$\\"
						+ this.yearOfAddition + "\\" + s.getUsername() + " -hmdrv N:";
				bW.write(stuInfo);
				bW.newLine();
			}
			bW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
