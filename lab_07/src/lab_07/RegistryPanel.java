package lab_07;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class RegistryPanel {

	public static void main(String[] args) {
		Connection conn = new Connection();
		int wyjscie = 0;
		// menu
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2016/11/16 12:08
		Doctor doctors;
		Patient patients;
		Visit visits;
		System.out.println("1. Dodaj lekarza");
		System.out.println("2. Dodaj pacjenta");
		System.out.println("3. Zarejstruj wizyte");
		System.out.println("4. Wyswietl dzisiejsze wizyty");
		System.out.println("5. Wyswietl historie pacjenta");
		System.out.println("6. Usuñ wizyte");
		System.out.println("9. Wyjscie");
		Scanner scanner = new Scanner(System.in);
		while (wyjscie != 1) {
			int wybor = scanner.nextInt();
			switch (wybor) {
			case 1:
				System.out.println("podaj dane(imie, nazwisko)");
				Scanner scanner2 = new Scanner(System.in);
				String name = scanner2.nextLine();
				String surname = scanner2.nextLine();
				doctors = new Doctor(name, surname);
				conn.insertDoctor(doctors);
				break;
			case 2:
				System.out.println("podaj dane(imie, nazwisko)");
				Scanner scanner3 = new Scanner(System.in);
				String name_p = scanner3.nextLine();
				String surname_p = scanner3.nextLine();
				patients= new Patient(name_p, surname_p);
				conn.insertPatient(patients);
				break;
			case 3:
				Scanner scanner4 = new Scanner(System.in);
				System.out.println("podaj nazwisko pacjenta");
				String patient_surname = scanner4.nextLine();
				int patient_id=conn.selectPatientId(patient_surname);
				//System.out.println(patient_id);
				System.out.println("podaj nazwisko lekarza");
				String doctor_surname = scanner4.nextLine();
				int doctor_id=conn.selectDoctorId(doctor_surname);
				//System.out.println(doctor_id);
				System.out.println("podaj date yyyy/MM/dd HH:mm");
				String visit_date = scanner4.nextLine();
				String temp = visit_date.substring(0, 10);
				if (conn.daily_overflow(temp, doctor_id) == true){
					System.out.println("Limit wizyt przekroczony wybierz inny dzien");
					break;
				}
				System.out.println("podaj nr pokoju");
				int room_nr = scanner4.nextInt();
				visits = new Visit(visit_date, doctor_id, patient_id, room_nr);
				conn.insertVisit(visits);
				break;
			case 4:
				String today = dateFormat.format(date).toString().substring(0, 10);
				System.out.println(today);
				conn.selectTodaysVisits(today);
				break;
			case 5:
				Scanner scanner5 = new Scanner(System.in);
				System.out.println("podaj nazwisko pacjenta");
				String patient_surname1 = scanner5.nextLine();
				conn.selectPatientHistory(patient_surname1);
				break;
			case 6:
				System.out.println("ktora wizyte usunac (id)");
				Scanner scanner7 = new Scanner(System.in);
				int delete_visit_id = scanner7.nextInt();
				conn.deleteVisit(delete_visit_id);
				break;
			case 9:
				wyjscie = 1;
				break;
			default:
				System.out.println("nie poprawny wybór");
			}
		}

	}

}
