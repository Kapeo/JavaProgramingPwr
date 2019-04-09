package lab_02;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassLoader extends JFrame implements ActionListener {
	JButton load = new JButton("LOAD");
	JButton unload = new JButton("UNLOAD");
	JButton swap = new JButton("SWAP");
	JButton convert = new JButton("CONVERT");
	JTextArea list = new JTextArea();
	JTextField class_to_load = new JTextField();
	JTextField class_to_swap1 = new JTextField();
	JTextField class_to_swap2 = new JTextField();
	JTextField field1 = new JTextField();
	JTextField field2 = new JTextField();

	public ClassLoader() {
		super("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLocation(50, 50);
		setLayout(new GridLayout());
		// lewy panel

		JPanel panel1 = new JPanel(new GridLayout(3, 1));
		add(panel1);
		//panel do wyswietlania listy 
		JPanel listing = new JPanel(new GridLayout(2, 1));
		JTextArea l1= new JTextArea("To jest lista zaladowanych klas. Klasy numerujemy od 0,\n"
				+ " we wierszu load/unload wpisujemy nazwe,\n"
				+ " we wierszu swap wpisujemy numer na liscie.");
		l1.setEditable(false);
		l1.setBackground(UIManager.getColor("Label.background"));
		listing.add(l1);
		listing.add(list);
		panel1.add(listing);
		
		// panel do ladowania klas
		JPanel loading = new JPanel(new GridLayout(1, 3));
		loading.add(load);
		loading.add(unload);
		loading.add(class_to_load);
		panel1.add(loading);

		// panel do zamiany
		JPanel swaping = new JPanel(new GridLayout(1, 3));
		swaping.add(swap);
		swaping.add(class_to_swap1);
		swaping.add(class_to_swap2);
		panel1.add(swaping);

		// prawy panel
		JTextArea l2= new JTextArea("To panel do konwersji. W pierwszym textfield wpisujmy tekst do zamiany,\n "
				+ "w drugim dostaniemy wynik po kliknieciu conwert,\n"
				+ " konwersja odbywa sie zawsze zgodnie z pierwsza zaladowana klasa");
		l2.setEditable(false);
		l2.setBackground(UIManager.getColor("Label.background"));
		JPanel panel2 = new JPanel(new GridLayout(4, 1));
		add(panel2);
		panel2.add(l2);
		panel2.add(field1);

		panel2.add(field2);

		panel2.add(convert);

		// action listenery
		load.addActionListener(this);
		unload.addActionListener(this);
		swap.addActionListener(this);
		convert.addActionListener(this);

		pack();
		setVisible(true);

	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == load) {
			try {
				load_class(class_to_load.getText());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			class_list();
			//System.out.println(class_list());
		} else if (source == unload) {
			try {
				unload_class(class_to_load.getText());
				class_list();
				//System.out.println(class_list());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (source == swap) {
			class_to_swap1.getText();
			class_to_swap2.getText();
			swap_list(Integer.parseInt(class_to_swap1.getText()),Integer.parseInt(class_to_swap2.getText()));
		} else if (source == convert) {
			try {
				Method launch = klasy.get(0).getMethod("launch");
				String text_in = field1.getText();
				launch.invoke(klasy.get(0).newInstance());
				field2.setText(connectClient(text_in));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	static LinkedList<Class> klasy = new LinkedList<Class>();

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,
			NoSuchMethodException, SecurityException {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ClassLoader();
			}
		});

	}

	public static String connectClient(String text_in) throws IOException {
		// laczenie sie z zaladowanymi klasami
		String host = "127.0.0.1";
		int port = 2239;
		Socket socket = new Socket(host, port);
		// tworzenie strumieni danychdostarczanych do socketu
		InputStream input = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		out.println(text_in);
		out.flush();
		String resp = in.readLine();
		System.out.print(resp);
		// in.close();
		// out.close();
		// socket.close();
		// large.stop();
		return resp;
	}

	
	//metoda ladujace klasy
	public static void load_class(String class_name) throws MalformedURLException, ClassNotFoundException {
		File file = new File("C:\\Users\\LENOVO\\eclipse-workspace\\lab_02\\klasy");
		URL[] urlsToLoadFrom = new URL[] { file.toURI().toURL() };
		URLClassLoader loader1 = new URLClassLoader(urlsToLoadFrom);
		klasy.add(Class.forName("lab_02."+class_name, false, loader1));

	}


	//metoda wyladowujace klasy
	public void unload_class(String class_name) throws ClassNotFoundException {
		klasy.remove(Class.forName("lab_02."+class_name));
	}
	
	//metoda zwracajaca liste zaladowanych klas
	public String class_list()
	{
		String lista="";
		for(int i=0; i<klasy.size();i++)
		{
			lista=lista+i+ ". " + klasy.get(i).toString()+"\n";
		}
		list.setText(lista);
		return lista;
	}
	
	//metoda przestawiaj¹ca elementy listy
	public void swap_list(int one, int two)
	{
		Collections.swap(klasy, one, two);
		class_list();
		
	}
	
	
}
