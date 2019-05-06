package lab_07;

public class Visit {

	int visitId;
	String date;
	int doctorId;
	int patientId;
	String finished;
	String diagnosis;
	int roomNr;

	public Visit(String date, int doctorId, int patientId,  int roomNr) {
		this.date = date;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.finished = "no";
		this.diagnosis = "no diagnosis";
		this.roomNr = roomNr;
	}
	public int getVisitId() {
		return visitId;
	}
	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public int getRoomNr() {
		return roomNr;
	}
	public void setRoomNr(int roomNr) {
		this.roomNr = roomNr;
	}


}
