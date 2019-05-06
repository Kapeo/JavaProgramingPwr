package lab_07;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DoctorPanel {

	public static void main(String[] args) {
		Connection conn = new Connection();
		Doctor doctors;
		int wyjscie = 0;
		System.out.println("podaj dane(imie, nazwisko)");
		Scanner scanner2 = new Scanner(System.in);
		String name = scanner2.nextLine();
		String surname = scanner2.nextLine();
		doctors = new Doctor(name, surname);
		int doc_id = conn.selectDoctorId(surname);
		System.out.println("1. Dzisiejsze wizyty");
		System.out.println("2. Przeprowadz wizyte");
		System.out.println("9. Wyjscie");
		Scanner scanner = new Scanner(System.in);
		while (wyjscie != 1) {
			int wybor = scanner.nextInt();
			switch (wybor) {
			case 1:
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				String today = dateFormat.format(date).toString();
				System.out.println(today);
				System.out.println(doc_id);
				conn.selectTodaysDocsVisits(today, doc_id);
				break;
			case 2:
				System.out.println("Wybierz wizyte (id)");
				Scanner scanner21 = new Scanner(System.in);
				int vis_id = scanner21.nextInt();
				System.out.println("Jaka diagnoza?");
				Scanner scanner22 = new Scanner(System.in);
				String diag = scanner22.nextLine();
				conn.updateVisit(vis_id, diag);
				System.out.println(diag);
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
