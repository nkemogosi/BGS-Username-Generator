package main;

public class Student {
	private String forename;
	private String surname;
	private String username;

	private static final String emailSuffix = "@bexleygs.co.uk";

	public Student(String surname, String forename) {
		super();
		this.forename = forename;
		this.surname = surname;
	}

	public String getEmail() {
		return username + Student.emailSuffix;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	private String password;

	public void setUsername(String username) {
		this.username = username;

	}

	@Override
	public String toString() {
		return surname + ", " + forename + ", " + username;
	}

}
