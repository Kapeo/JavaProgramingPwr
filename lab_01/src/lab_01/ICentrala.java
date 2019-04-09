package lab_01;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICentrala extends Remote {
	// do rejestrowania bramki w systemie, wynik – numer przydzielony bramce
	// (bramka musi wpisaæ sobie ten numer po wywo³aniu zdalnym)
	// lub wartoœæ ujemna, gdy operacja nieudana.
	public int zarejestrujBramke(IBramka bramka) throws RemoteException;

	// do wyrejestrowywania bramek
	public boolean wyrejestrujBramke(int nrBramki) throws RemoteException;

	// do pobierania listy bramek aktywnych (w celu monitorowania ich "aktywnoœci")
	public ArrayList<IBramka> getZarejestrowaneBramki() throws RemoteException;

	// do rejestracji w centrali namiastki monitora (w celu póŸniejszego
	// informowania o koniecznoœci aktualizacji)
	public void zarejestrujMonitor(IMonitor o) throws RemoteException;

	public void wyrejestrujMonitor() throws RemoteException;

	public Object getMonitor() throws RemoteException;
}