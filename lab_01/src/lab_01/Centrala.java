package lab_01;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Centrala extends JFrame implements ICentrala {

	ArrayList<IBramka> Bramki = new ArrayList();
	private int licznik = 0;
	private IMonitor nMonitor = null;
	private JButton zamknij_bramke = new JButton("zamknij bramke");
	private JTextField bramka_do_wyr = new JTextField();
	private JTextArea lista_bramek = new JTextArea();

	public static void main(String[] args) {
		Centrala centrala1 = new Centrala();

	}

	public Centrala() {
		try {
			ICentrala nCentrala = (ICentrala) UnicastRemoteObject.exportObject(this, 3000);
			// UnicastRemoteObject.unexportObject(s, true);
			Registry reg = LocateRegistry.createRegistry(2000);
			// Registry reg = LocateRegistry.getRegistry(2000);

			reg.rebind("Server", nCentrala);
		} catch (

		RemoteException e) {
			e.printStackTrace();
		}
		setTitle("Centrala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocation(50, 50);
		setLayout(new GridLayout(1,2));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3,1));
		JLabel info = new JLabel("bramka do wyrejstrowania");
		panel1.add(info);
		panel1.add(bramka_do_wyr);
		panel1.add(zamknij_bramke);
		add(panel1);
		add(lista_bramek);
		zamknij_bramke.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int numer_do_wyr = Integer.parseInt(bramka_do_wyr.getText());
				lista_bramek.setText(update_list());
				try {
					Bramki.get(numer_do_wyr).zamknijBramke();
					Bramki.set(numer_do_wyr, null);
					lista_bramek.setText(update_list());
					if (nMonitor !=null) {
						nMonitor.koniecznaAktualizacja();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setVisible(true);
	}

	@Override
	public int zarejestrujBramke(IBramka bramka) throws RemoteException {
		Bramki.add(licznik, bramka);
		licznik++;
		lista_bramek.setText(update_list());
		int licznik1 = licznik - 1;
		if (nMonitor !=null) {
			nMonitor.koniecznaAktualizacja();
		}

		return licznik1;
	}

	@Override
	public boolean wyrejestrujBramke(int nrBramki) throws RemoteException {
		Bramki.set(nrBramki, null);
		lista_bramek.setText(update_list());
		if (nMonitor !=null) {
			nMonitor.koniecznaAktualizacja();
		}
		return false;
	}

	@Override
	public ArrayList<IBramka> getZarejestrowaneBramki() throws RemoteException {
		return Bramki;
	}

	@Override
	public void zarejestrujMonitor(IMonitor o) throws RemoteException {
		nMonitor = o;

	}

	@Override
	public void wyrejestrujMonitor() throws RemoteException {
		nMonitor=null;
	}

	@Override
	public Object getMonitor() throws RemoteException {
		return this;
	}

	public String update_list() {
		String list = "";
		for (Object ibramka : Bramki) {
			if (ibramka != null) {
				list = list + "bramka " + Bramki.indexOf(ibramka) +"\n";
			}
		}
		return list;
	}
}
