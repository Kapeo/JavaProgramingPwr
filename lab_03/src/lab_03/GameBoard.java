package lab_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JFrame implements ActionListener {
	private int size = 30;
	private int toWin = 5;
	JButton[][] fields;
	int[] computerMove;
	int[] lastMove;
	// ComputerMoves movesGenerator = new ComputerMoves();
	int difficulty = 1;
	Method[] methods;
	Object o;

	public GameBoard() {
		super();
		File file = new File("C:\\Users\\LENOVO\\eclipse-workspace\\lab_03");
		URL[] urlsToLoadFrom = null;
		try {
			urlsToLoadFrom = new URL[] { file.toURI().toURL() };
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URLClassLoader loader1 = new URLClassLoader(urlsToLoadFrom);
		Class<?> movesGenerator = null;
		try {
			movesGenerator = loader1.loadClass("lab_03.ComputerMoves");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Constructor<?> constructor = null;
		try {
			constructor = movesGenerator.getConstructor();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			o = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		methods = movesGenerator.getDeclaredMethods();


		this.size = size;
		this.toWin = toWin;
		this.fields = new JButton[size][size];
		this.computerMove = new int[2];
		this.lastMove = new int[2];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 1500);
		setLocation(50, 50);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel choicePanel = new JPanel();
		choicePanel.setPreferredSize(new Dimension(1500, 100));
		JLabel label1 = new JLabel("RANDOM");
		choicePanel.add(label1);
		JCheckBox box1 = new JCheckBox();
		box1.setSelected(true);
		JCheckBox box2 = new JCheckBox();
		choicePanel.add(box1);
		box1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				difficulty = 1;
				box2.setSelected(false);
			}
		});
		JLabel label2 = new JLabel("CLOSEST");
		choicePanel.add(label2);
		choicePanel.add(box2);
		box2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				difficulty = 2;
				box1.setSelected(false);
			}
		});
		JButton reset = new JButton("RESET");
		choicePanel.add(reset);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		add(choicePanel);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fields[i][j] = new JButton();
				fields[i][j].setText(""); // Integer.toString(i) +
											// Integer.toString(j)
				fields[i][j].setPreferredSize(new Dimension(20, 20));
				fields[i][j].addActionListener(this);
				// board[i][j]=0;
				gamePanel.add(fields[i][j]);
			}
		}
		add(gamePanel);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton buttonClicked = (JButton) e.getSource();
		if (buttonClicked.getText().equals("")) {
			// buttonClicked.setText("X");
			fields = updateBoard(buttonClicked, fields);
			if (checkPlayerWin(fields) == true) {
				System.out.println("gracz wygral");
				JOptionPane.showMessageDialog(null, "PLAYER WIN!");
				restart();
			} else {

				Method methodChosen = null;
				for (Method method : methods) {
					if (method.isAnnotationPresent(ComputerAI.class)) {
						// System.out.println(method.getAnnotation(ComputerAI.class).level());
						if (method.getAnnotation(ComputerAI.class).level() == difficulty) {
							methodChosen = method;
						}
					}
				}
				try {
					computerMove = (int[]) methodChosen.invoke(o, fields, size, lastMove);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// computerMove = movesGenerator.strategyClosest(fields, size,
				// lastMove);
				fields[computerMove[0]][computerMove[1]].setText("O");
				if (checkComputerWin(fields) == true) {
					System.out.println("komputer wygral");
					JOptionPane.showMessageDialog(null, "COMPUTER WIN!");
					restart();
				}
			}
			// System.out.println("NEXT");
		}
	}

	public JButton[][] updateBoard(JButton button, JButton[][] fields) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (fields[i][j] == button) {
					fields[i][j].setText("X");
					lastMove[0] = i;
					lastMove[1] = j;
				}
			}
		}
		return fields;
	}

	public void restart() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fields[i][j].setText("");
			}
		}
	}

	public boolean checkPlayerWin(JButton[][] fields) {
		boolean ret = false;
		for (int i = 0; i < size - toWin + 1; i++) {
			for (int j = 0; j < size; j++) {
				if (fields[i][j].getText().equals("X") && fields[i + 1][j].getText().equals("X")
						&& fields[i + 2][j].getText().equals("X") && fields[i + 3][j].getText().equals("X")
						&& fields[i + 4][j].getText().equals("X")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - toWin + 1; j++) {
				if (fields[i][j].getText().equals("X") && fields[i][j + 1].getText().equals("X")
						&& fields[i][j + 2].getText().equals("X") && fields[i][j + 3].getText().equals("X")
						&& fields[i][j + 4].getText().equals("X")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size - 4; i++) {
			for (int j = 0; j < size - toWin + 1; j++) {
				if (fields[i][j].getText().equals("X") && fields[i + 1][j + 1].getText().equals("X")
						&& fields[i + 2][j + 2].getText().equals("X") && fields[i + 3][j + 3].getText().equals("X")
						&& fields[i + 4][j + 4].getText().equals("X")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size - toWin + 1; i++) {
			for (int j = toWin - 2; j < size; j++) {
				if (fields[i][j].getText().equals("X") && fields[i + 1][j - 1].getText().equals("X")
						&& fields[i + 2][j - 2].getText().equals("X") && fields[i + 3][j - 3].getText().equals("X")
						&& fields[i + 4][j - 4].getText().equals("X")) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public boolean checkComputerWin(JButton[][] fields) {
		boolean ret = false;
		for (int i = 0; i < size - toWin + 1; i++) {
			for (int j = 0; j < size; j++) {
				if (fields[i][j].getText().equals("O") && fields[i + 1][j].getText().equals("O")
						&& fields[i + 2][j].getText().equals("O") && fields[i + 3][j].getText().equals("O")
						&& fields[i + 4][j].getText().equals("O")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - toWin + 1; j++) {
				if (fields[i][j].getText().equals("O") && fields[i][j + 1].getText().equals("O")
						&& fields[i][j + 2].getText().equals("O") && fields[i][j + 3].getText().equals("O")
						&& fields[i][j + 4].getText().equals("O")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size - toWin + 1; i++) {
			for (int j = 0; j < size - toWin + 1; j++) {
				if (fields[i][j].getText().equals("O") && fields[i + 1][j + 1].getText().equals("O")
						&& fields[i + 2][j + 2].getText().equals("O") && fields[i + 3][j + 3].getText().equals("O")
						&& fields[i + 4][j + 4].getText().equals("O")) {
					ret = true;
				}
			}
		}
		for (int i = 0; i < size - toWin + 1; i++) {
			for (int j = toWin - 2; j < size; j++) {
				if (fields[i][j].getText().equals("O") && fields[i + 1][j - 1].getText().equals("O")
						&& fields[i + 2][j - 2].getText().equals("O") && fields[i + 3][j - 3].getText().equals("O")
						&& fields[i + 4][j - 4].getText().equals("O")) {
					ret = true;
				}
			}
		}
		return ret;
	}

}
