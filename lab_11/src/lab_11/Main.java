package lab_11;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.LinkedList;

import javax.crypto.NoSuchPaddingException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;

public class Main extends JFrame {

	public Main() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);
		setLocation(50, 50);
		getContentPane().setLayout(new GridLayout(2, 1));
		LinkedList<String> arg_heading = new LinkedList<String>();
		arg_heading.add("1");
		arg_heading.add("2");
		arg_heading.add("3");
		arg_heading.add("4");
		arg_heading.add("5");
		arg_heading.add("6");


		LinkedList<String> arg_color = new LinkedList<String>();
		arg_color.add("red");
		arg_color.add("blue");
		arg_color.add("green");
		LinkedList<String> arg_style = new LinkedList<String>();
		arg_style.add("bold");
		arg_style.add("italic");

		JTextField text_field = new JTextField();
		JPanel choice_panel = new JPanel();
		JLabel label2 = new JLabel();
		label2.setForeground(Color.GRAY);
		JComboBox param = new JComboBox();
		for (String s : arg_heading) {
			param.addItem(s);

		}
		JComboBox file_name = new JComboBox();
		file_name.addItem("heading.js");
		file_name.addItem("color.js");
		file_name.addItem("style.js");


		file_name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (String.valueOf(file_name.getSelectedItem()) == "heading.js") {

					param.removeAllItems();
					for (String s : arg_heading) {
						param.addItem(s);

					}
				}
				if (String.valueOf(file_name.getSelectedItem()) == "color.js") {

					param.removeAllItems();
					for (String s : arg_color) {
						param.addItem(s);

					}
				}
				if (String.valueOf(file_name.getSelectedItem()) == "style.js") {

					param.removeAllItems();
					for (String s : arg_style) {
						param.addItem(s);

					}
				}
			}
		});
		JButton button = new JButton("ZASTOSUJ");
		file_name.setPreferredSize(new Dimension(250, 20));
		param.setPreferredSize(new Dimension(250, 20));
		button.setPreferredSize(new Dimension(250, 100));
		choice_panel.add(file_name);
		choice_panel.add(param);
		choice_panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
				try {
					engine.eval(new FileReader(String.valueOf(file_name.getSelectedItem())));
				} catch (ScriptException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Invocable invocable = (Invocable) engine;
				Object result = null;
				try {
					String func = String.valueOf(file_name.getSelectedItem()).substring(0,String.valueOf(file_name.getSelectedItem()).length()-3);
					result = invocable.invokeFunction(func, text_field.getText(), String.valueOf(param.getSelectedItem()));
					System.out.println(result);
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block;
					e1.printStackTrace();
				} catch (ScriptException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				label2.setText((String) result);
			}
		});

		getContentPane().add(choice_panel);

		JPanel text_panel = new JPanel();
		getContentPane().setLayout(new GridLayout(1, 2));

		text_field.setPreferredSize(new Dimension(250, 100));
		label2.setPreferredSize(new Dimension(250, 100));
		text_panel.add(text_field);
		text_panel.add(label2);
		getContentPane().add(text_panel);

		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();

	}

}
