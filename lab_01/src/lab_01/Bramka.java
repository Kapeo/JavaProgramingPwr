package lab_01;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Bramka extends JFrame implements IBramka {
	private JButton bramka_start = new JButton("bramka_start");
	private JButton bramka_stop = new JButton("bramka_stop");
	JButton wejscie = new JButton("wejscie");
	JButton wyjscie = new JButton("wyjscie");
	private JLabel statystyki = new JLabel();
	private int numer = -1;
	int ilosc_wejsc = 0;
	int ilosc_wyjsc = 0;

	ICentrala nCentrala;
	IBramka nBramka;

	public static void main(String[] args) {
		Bramka bramka1 = new Bramka();

	}

	public Bramka() {
		super();
		BramkaIns bramka = new BramkaIns();
		setTitle("bramka nr ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 200);
		setLocation(50, 50);
		setLayout(new GridLayout(3, 2));
		add(bramka_start);
		add(bramka_stop);
		add(wejscie);
		add(wyjscie);
		add(statystyki);
		wejscie.setEnabled(false);
		wyjscie.setEnabled(false);

		Registry reg;
		try {
			nBramka = (IBramka) UnicastRemoteObject.exportObject(this, PortFactory.getPort());
			reg = LocateRegistry.getRegistry("localhost", 2000);
			nCentrala = (ICentrala) reg.lookup("Server");

		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bramka_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wejscie.setEnabled(true);
				wyjscie.setEnabled(true);
				try {
					bramka.setNumer(nCentrala.zarejestrujBramke(nBramka));
					setTitle("bramka nr "+ bramka.getNumer());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		bramka_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wejscie.setEnabled(false);
				wyjscie.setEnabled(false);
				try {
					nCentrala.wyrejestrujBramke(bramka.getNumer());
					ilosc_wejsc=0;
					ilosc_wyjsc=0;
					update_statystyki();
					bramka.setNumer(-1);
					setTitle("bramka nr "+ bramka.getNumer());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		wejscie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ilosc_wejsc++;
				update_statystyki();
			}
		});

		wyjscie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ilosc_wyjsc++;
				update_statystyki();
			}
		});

		// pack();
		setVisible(true);

	}

	@Override
	public int[] getStatystyka() throws RemoteException {
		int[] a = {ilosc_wejsc, ilosc_wyjsc};
		return a;

	}

	@Override
	public boolean zamknijBramke() throws RemoteException {
		wejscie.setEnabled(false);
		wyjscie.setEnabled(false);
		ilosc_wejsc=0;
		ilosc_wyjsc=0;
		return true;
	}

	@Override
	public int getNumer() throws RemoteException {
		return numer;
	}

	public void update_statystyki() {
		statystyki.setText("LICZBA WEJSC: " + ilosc_wejsc + " LICZBA WYJSC: " + ilosc_wyjsc);
	}


}
