package lab_06;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.WeakHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.javafx.collections.MappingChange.Map;

public class WeakReflectionApp extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	LinkedList<String> file_names = new LinkedList();
	File folder = new File("img");
	File[] listOfFiles = folder.listFiles();
	WeakHashMap<String, ImageIcon> map = new WeakHashMap<>();
	//HashMap<Integer, SoftReference<ImageIcon>> map = new HashMap<Integer, SoftReference<ImageIcon>>();

	int iterator1 = 0;
	int iterator2 = 1;
	JTextArea file_field = new JTextArea();
	JButton left = new JButton("<<<<<<");
	JButton right = new JButton(">>>>>>");
	ImageIcon temp;
	ImageIcon temp1;
	JLabel img1;
	JLabel img2;

	public void fileNamesUpdate() {
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				file_names.add(listOfFiles[i].getName());
			}
		}
	}

	public WeakReflectionApp() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(50, 50);
		setLayout(new GridLayout(3, 1));
		JPanel panel1 = new JPanel(new GridLayout(1, 1));
		file_field.setText("C:/Users/LENOVO/eclipse-workspace/lab_06/img");
		fileNamesUpdate();
		panel1.add(file_field);
		JPanel panel2 = new JPanel(new GridLayout(1, 2));
		ImageIcon icon1 = new ImageIcon(file_field.getText() + "/" + file_names.get(iterator1));
		map.put(file_names.get(iterator1), icon1);
		img1 = new JLabel(icon1);
		ImageIcon icon2 = new ImageIcon(file_field.getText() + "/" + file_names.get(iterator2));
		img2 = new JLabel(icon2);
		map.put(file_names.get(iterator2), icon2);
		panel2.add(img1);
		panel2.add(img2);
		JPanel panel3 = new JPanel(new GridLayout(1, 2));
		left.addActionListener(this);
		right.addActionListener(this);
		panel3.add(left);
		panel3.add(right);
		add(panel1);
		add(panel2);
		add(panel3);
		setVisible(true);
	}

	public int checkIterator(int iterator) {
		if (iterator < 0) {
			iterator = file_names.size() - 1;
		} else if (iterator >= file_names.size()) {
			iterator = 0;
		}
		return iterator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == right) {
			iterator1++;
			iterator2++;
			iterator1 = checkIterator(iterator1);
			iterator2 = checkIterator(iterator2);
		}
		if (source == left) {
			iterator1--;
			iterator2--;
			iterator1 = checkIterator(iterator1);
			iterator2 = checkIterator(iterator2);
		}
		System.out.println(iterator1 + " //// " + iterator2);
		if (map.containsKey(file_names.get(iterator1))) {
				ImageIcon icon = (map.get(file_names.get(iterator1)));
				img1.setIcon(icon);
				System.out.println("tu1.1 ");
			//System.out.println("tu1 ");
		}
		if (map.containsKey(file_names.get(iterator2))) {
			
				img2.setIcon(map.get(file_names.get(iterator2)));
				System.out.println("tu2.1 ");
			
			//System.out.println("tu2");
		}
		if (map.containsKey(file_names.get(iterator1))==false) {
			temp = new ImageIcon(file_field.getText() + "/" + file_names.get(iterator1));
			map.put(file_names.get(iterator1), temp);
			img1.setIcon(temp);
			System.out.println("tu3");
		}
		if (map.containsKey(file_names.get(iterator2))==false) {
			temp1 = new ImageIcon(file_field.getText() + "/" + file_names.get(iterator2));
			map.put(file_names.get(iterator2), temp1);
			img2.setIcon(temp1);
			System.out.println("tu4");
		}

	}

	public static void main(String[] args) {
		WeakReflectionApp app = new WeakReflectionApp();
	}
}
