package lab_01;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Monitor extends JFrame implements IMonitor, Runnable {
	private JTextArea lista_monitora = new JTextArea();
	ICentrala nCentrala;
	Registry reg;
	IBramka nBramka = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Monitor monitor1 = new Monitor();
	}

	public Monitor() {
		super("Monitor");
		try {
			IMonitor nMonitor = (IMonitor) UnicastRemoteObject.exportObject(this, PortFactory.getPort());
			reg = LocateRegistry.getRegistry("localhost", 2000);
			nCentrala = (ICentrala) reg.lookup("Server");
			nCentrala.zarejestrujMonitor(this);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLocation(50, 50);
		add(lista_monitora);
		// lista_monitora.setEditable(false);
		Thread t1 = new Thread(this);
		t1.start();
		setVisible(true);
	}

	@Override
	public void koniecznaAktualizacja() throws RemoteException {
		update_lista_monitora();

	}

	public void update_lista_monitora() throws RemoteException {
		String list = "";
		for (IBramka ibramka : nCentrala.getZarejestrowaneBramki()) {
			if (ibramka != null ) {
				nBramka = ibramka;
				int[] temp = nBramka.getStatystyka();
				list = list + "BRAMKA " + nCentrala.getZarejestrowaneBramki().indexOf(ibramka) + " WEJSC: " + temp[0]
						+ " WYJSC: " + temp[1] + "\n";
			}
		}
		lista_monitora.setText(list);
	}
	
	 @Override
	    public void run() {
	        while (true){
	            try {
	                Thread.sleep(3000);
	                update_lista_monitora();
	            } catch (InterruptedException | RemoteException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
