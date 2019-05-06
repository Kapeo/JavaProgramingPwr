package lab_07;

public class Doctor {
	String name;
	String surname;
	int doctorId;

	public Doctor(String name, String surname){
		this.name=name;
		this.surname=surname;
		//this.doctorId=doctorId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
}
