package lab_01;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

public interface IBramka extends Remote {
	// do pobierania informacji o statystyce � do wykorzystania przez Monitor
	// statystyka to tablica z dwiema warto�ciami: liczba wej��, liczba wyj��
	public int[] getStatystyka() throws RemoteException;

	// do zdalnego zatrzymywania bramek
	public boolean zamknijBramke() throws RemoteException;

	// do pobrania numeru bramki
	public int getNumer() throws RemoteException;
}