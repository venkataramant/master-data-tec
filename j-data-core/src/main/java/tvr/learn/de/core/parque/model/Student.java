package tvr.learn.de.core.parque.model;

public class Student {

	private int rollNumber;
	private String firstName;
	private double GPA;
	private boolean active;

	public Student() {
	}

	public Student(int rollNumber, String firstName, double gPA, boolean active) {
		this.rollNumber = rollNumber;
		this.firstName = firstName;
		GPA = gPA;
		this.active = active;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double gPA) {
		GPA = gPA;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
