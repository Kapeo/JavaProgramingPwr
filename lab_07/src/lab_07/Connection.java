package lab_07;

import java.sql.*;

public class Connection {

	private java.sql.Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:lab_07.db";
		java.sql.Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void selectTodaysVisits(String today) {
		String sql = "SELECT VISITS.VISIT_ID, DOCTORS.SURNAME as a, PATIENTS.SURNAME as b, VISITS.ROOMNR  FROM VISITS "
				+ "JOIN DOCTORS ON VISITS.DOCTOR_ID = DOCTORS.DOCTOR_ID "
				+ "JOIN PATIENTS ON (VISITS.PATIENT_ID = PATIENTS.PATIENT_ID) " + "WHERE VISITS.DATE LIKE ? ";

		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// set the corresponding param
			pstmt.setString(1, today + "%");
			// update
			// pstmt.executeUpdate();
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				System.out.println("ID " + rs.getInt("VISIT_ID") + "\tDOCTOR " + rs.getString("a") + "\tPATIENT "
						+ rs.getString("b") + "\tROOM " + rs.getInt("ROOMNR"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectTodaysDocsVisits(String today, int doc_id) {
		String sql = "SELECT VISITS.VISIT_ID,  PATIENTS.SURNAME as b, VISITS.ROOMNR,  VISITS.FINISHED  FROM VISITS "
				+ "JOIN DOCTORS ON VISITS.DOCTOR_ID = DOCTORS.DOCTOR_ID "
				+ "JOIN PATIENTS ON (VISITS.PATIENT_ID = PATIENTS.PATIENT_ID) "
				+ "WHERE VISITS.DATE LIKE ? AND DOCTORS.DOCTOR_ID = ?";

		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// set the corresponding param
			pstmt.setString(1, today + "%");
			pstmt.setInt(2, doc_id);
			// update
			// pstmt.executeUpdate();
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				System.out.println("ID " + rs.getInt("VISIT_ID") + "\tPATIENT " + rs.getString("b") + "\tROOM "
						+ rs.getInt("ROOMNR") + "\tIS_FINISHED " + rs.getString("FINISHED"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertDoctor(Doctor doctor) {
		String sql = "INSERT INTO DOCTORS ( NAME, SURNAME) VALUES (?,?)";
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, doctor.getName());
			pstmt.setString(2, doctor.getSurname());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertPatient(Patient patient) {
		String sql = "INSERT INTO PATIENTS ( NAME, SURNAME) VALUES (?,?)";
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, patient.getName());
			pstmt.setString(2, patient.getSurname());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertVisit(Visit visit) {
		String sql = "INSERT INTO VISITS (PATIENT_ID, DOCTOR_ID, DATE, ROOMNR, FINISHED, DIAGNOSIS) VALUES (?,?,?,?,?,?)";

		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, visit.getPatientId());
			pstmt.setInt(2, visit.getDoctorId());
			pstmt.setString(3, visit.getDate());
			pstmt.setInt(4, visit.getRoomNr());
			pstmt.setString(5, visit.getFinished());
			pstmt.setString(6, visit.getDiagnosis());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateVisit(int vis_id, String diag) {
		String sql = "UPDATE VISITS SET DIAGNOSIS = ? , " + "FINISHED = 'yes' "  + "WHERE VISIT_ID = ?";
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, diag);
			pstmt.setInt(2, vis_id);
			pstmt.executeUpdate(); }
		catch (SQLException e)  {
			 System.out.println(e.getMessage());
		}
	  }

	public int selectDoctorId(String surname) {
		String sql = "SELECT DOCTOR_ID FROM DOCTORS WHERE SURNAME = ?";
		int pomocnicza = 0;
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, surname);
			ResultSet rs = pstmt.executeQuery();
			pomocnicza = rs.getInt("DOCTOR_ID");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return pomocnicza;
	}

	public int selectPatientId(String surname) {
		String sql = "SELECT PATIENT_ID FROM PATIENTS WHERE SURNAME = ?";
		int pomocnicza1 = 0;
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, surname);
			ResultSet rs = pstmt.executeQuery();
			pomocnicza1 = rs.getInt("PATIENT_ID");
			System.out.println(pomocnicza1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return pomocnicza1;
	}

	public boolean daily_overflow(String date, int doctor_id) {
		String sql = "SELECT COUNT(VISIT_ID) AS A FROM VISITS WHERE DATE = ? AND DOCTOR_ID = ?";
		int pomocnicza2 = 0;
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, date + "%");
			pstmt.setInt(2, doctor_id);
			ResultSet rs = pstmt.executeQuery();
			pomocnicza2 = rs.getInt("A");
			System.out.println("Wizyt w tym dniu: " + pomocnicza2);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		if (pomocnicza2 > 8) {
			return true;
		} else {
			return false;
		}

	}

	public void selectPatientHistory(String surname) {
		String sql = "SELECT VISITS.VISIT_ID, DOCTORS.SURNAME as a, PATIENTS.SURNAME as b, "
				+ "VISITS.DATE, VISITS.DIAGNOSIS FROM VISITS " + "JOIN DOCTORS ON VISITS.DOCTOR_ID = DOCTORS.DOCTOR_ID "
				+ "JOIN PATIENTS ON (VISITS.PATIENT_ID = PATIENTS.PATIENT_ID) " + "WHERE PATIENTS.SURNAME = ? ";

		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, surname);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("\tPATIENT " + rs.getString("b") + " VISIT_ID " + rs.getInt("VISIT_ID") + "\tDOCTOR "
						+ rs.getString("a") + "\tDATE " + rs.getString("DATE") + "\tDIAGNOSIS "
						+ rs.getString("DIAGNOSIS"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteVisit(int visitId) {
		String sql = "DELETE FROM VISITS WHERE VISIT_ID = ?";
		try (java.sql.Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, visitId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
