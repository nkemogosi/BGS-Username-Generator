package main;

import java.util.List;
import java.util.Random;

public class UsernameGenerator {

	String yearOfAddition;
	String[] nouns = { "stone", "blood", "thing", "light", "cough", "story", "power", "point", "anger", "night",
			"glory", "candy", "puppy", "phone", "vegan", "brick", "birth", "grace", "queen", "pasta", "plant", "knife",
			"magic", "jelly", "media", "honor", "cycle", "truth", "zebra", "train", "bully", "llama", "brain", "mango", // used
																														// to
																														// make
																														// one
																														// time
																														// only
																														// passwords
			"grammar", "penny", "honey", "color", "cloud", "scarf", "state", "value", "mouse", "bread", "paper",
			"beard", "place", "chair", "badge", "piano" };
	String[] adjectives = { "happy", "green", "sandy", "south", "light", "smart", "black", "dirty", "royal", "purple",
			"daily", "north", "white", "sweet", "clear", "young", "sound", "human", "right", "local", "brave", "solid",
			"early", "given", "plain", "quiet", "lucky", "clean", "short", "worse", "wrong", "close", "heavy", "small",
			"first", "false", "roman", "rough", "ready", "round", "loose", "fancy", "bexley", "tired", "nasty", "loved",
			"proud", "eager", "angry", "noble" };

	public UsernameGenerator(String yearOfAddition) {
		this.yearOfAddition = yearOfAddition;
	}

	private List<Student> sort(List<Student> students) {
		for (int x = 0; x < students.size(); x++) {
			for (int y = 1; y < (students.size() - x); y++) {
				String s1 = students.get(y - 1).getSurname() + students.get(y - 1).getForename();
				String s2 = students.get(y).getSurname() + students.get(y).getForename();
// this sorts the users by alphabethical order
				if (s1.compareToIgnoreCase(s2) > 0) {
					Student s = students.get(y - 1);
					students.set((y - 1), students.get(y));
					students.set(y, s);
				}
			}
		}
		return students;
	}

	public List<Student> createUserNames(List<Student> students) {
		if (students != null && !students.isEmpty()) {
			students = sort(students);
			for (Student student : students) {
				createUsername(student, students);
			}
		}

		return students;
	}

	private String removeSpaces(String word) {
		StringBuilder noSpace = new StringBuilder();
		for (char c : word.toCharArray()) {
			if (Character.isLetter(c)) {
				noSpace.append(c); // String builder is faster than
// concatenation using (+) in a loop
			}
		}
		return noSpace.toString().toLowerCase();
	}

	public Student createUsername(Student currentStudent, List<Student> students) {
		Student lastStudent;
		int number = 0;
		int pos = 0;
		if (students != null) {// this is mainly to check whether the multi student entry menu is used or the
								// single student entry is used
			boolean found = false;
			while (pos <

					students.size() && !found) {
				if (currentStudent.equals(students.get(pos))) {
					found = true;
				} else {
					pos++;
				}
			}
		}
		if (pos != 0) {
			lastStudent = students.get(pos - 1);
			if ((lastStudent.getForename().charAt(0) == (currentStudent.getForename().charAt(0)))
					&& (lastStudent.getSurname().matches(currentStudent.getSurname()))) {
				number = getNumber(lastStudent.getUsername());
			}
		}
		String suffix = currentStudent.getForename().substring(0, 1).toLowerCase();
		String sName = removeSpaces(currentStudent.getSurname());

		currentStudent.setUsername(yearOfAddition.substring(yearOfAddition.length() - 2) + sName + "_" + suffix);
		if (number > 0) {
			currentStudent.setUsername(currentStudent.getUsername() + number);
		}
		currentStudent.setPassword(createPassword());

		return currentStudent;
	}

	private String createPassword() {
		Random r = new Random();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(adjectives[r.nextInt(adjectives.length)]);
		sBuilder.append(nouns[r.nextInt(nouns.length)]);
		sBuilder.append(r.nextInt(100));
		return sBuilder.substring(0, 1).toUpperCase() + sBuilder.substring(1);
		// creating passwords
		// from the two
		// arrays
	}

	private int getNumber(String userName) {
		int number = 0;
		if (Character.isDigit(userName.charAt((userName.length() - 1)))) {
			number = Integer.parseInt(userName.substring((userName.indexOf("_") + 2))); // this is for when a username
																						// matches, it gets the number
																						// at the end of the older
																						// username
//and adds 1 to it to give the newly created username to mark it for possible  changes
		}
		return number + 1;
	}
}
