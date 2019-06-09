package lab_09;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lab_09.Cryptography;

public class Main extends JFrame {
	String text = "";
	KeyPairGenerator keyGen;
	KeyPair pair;
	Signature dsa;
	PrivateKey priv;
	PublicKey pub;
	boolean verifies;
	byte[] sig = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
	byte b;
	FileInputStream fis;

	public Main() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		super();
		 /* generate a key pair */

        keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024, new SecureRandom());
        KeyPair pair = keyGen.generateKeyPair();

        /* create a Signature object to use for signing and verifying */

        dsa = Signature.getInstance("SHA/DSA");

        priv = pair.getPrivate();
        pub = pair.getPublic();


		Cryptography crypter = new Cryptography("RSA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 500);
		setLocation(50, 50);
		setLayout(new GridLayout(1, 2));
		JPanel choice_panel = new JPanel();
		choice_panel.setLayout(new GridLayout(7, 2));
		JButton read = new JButton("ODCZYTAJ");

		JTextField read_value = new JTextField();
		JButton crypt = new JButton("ZASZYFRUJ");
		JButton decrypt = new JButton("ODSZYFRUJ");
		JButton sign = new JButton("PODPISZ");
		JButton verify = new JButton("ZWERYFIKUJ");
		JLabel info = new JLabel("PLIK DO PRACY");
		JTextField info_value = new JTextField("cryptography.jar");
		JLabel is_signed = new JLabel("PODPISANY");
		JLabel value = new JLabel("-----------------------");
		JLabel choice = new JLabel("ALGORYTM: ");
		String[] algorithms = { "DiffieHellman", "DSA", "RSA" };
		JLabel key = new JLabel("KLUCZ: ");
		String[] keys = {"private", "public"};
		JComboBox key_value = new JComboBox(keys);
		JComboBox algorithm_choice = new JComboBox(algorithms);
		algorithm_choice.setSelectedIndex(2);
		key_value.setSelectedIndex(0);

		choice_panel.add(read);
		choice_panel.add(read_value);
		choice_panel.add(crypt);
		choice_panel.add(decrypt);
		choice_panel.add(sign);
		choice_panel.add(verify);
		choice_panel.add(info);
		choice_panel.add(info_value);
		choice_panel.add(is_signed);
		choice_panel.add(value);
		choice_panel.add(choice);
		choice_panel.add(algorithm_choice);
		choice_panel.add(key);
		choice_panel.add(key_value);
		add(choice_panel);

		JPanel text_panel = new JPanel();
		JTextArea file_text = new JTextArea();
		file_text.setPreferredSize(new Dimension(500, 500));
		file_text.setEditable(false);
		text_panel.add(file_text);
		add(text_panel);
		setVisible(true);


		// action listeners
		read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = read_value.getText();
				int number = 0;
				BufferedReader fileReader = null;
				try {
					fileReader = new BufferedReader(new FileReader(filePath));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("File not found");
				}
				String linia="";
				try {
					while ((linia=fileReader.readLine()) != null) {
							text += linia + "\n";
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("File not found");
				}
				file_text.setText(text);
			}
		});
		crypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cipherText = "";
				try {
					cipherText = crypter.encrypt(text, key_value.getSelectedIndex());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				text=cipherText;
				file_text.setText(text);
			}
		});
		decrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String decipheredMessage = "";
				try {
					decipheredMessage = crypter.decrypt(text, key_value.getSelectedIndex());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				text=decipheredMessage;
				file_text.setText(text);
			}
		});
		sign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String file = info_value.getText();
		            dsa.initSign(priv);

		            /* Update and sign the data */

		            fis = new FileInputStream(file);
		            while (fis.available() != 0) {
		                b = (byte) fis.read();
		                dsa.update(b);
		                };

		            fis.close();

		            /* Now that all the data to be signed has been read in, sign it */
		            sig = dsa.sign();

				} catch (Exception e2) {
		            System.err.println("Caught exception " + e2.toString());
		        }

			}
		});
		verify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					/* Verify the signature */

		            /* Initialize the Signature object for verification */

				try {
					String file = info_value.getText();
					dsa.initVerify(pub);

					/* Update and verify the data */

					fis = new FileInputStream(file);

					while (fis.available() != 0) {
						b = (byte) fis.read();
						dsa.update(b);
					}
					fis.close();
						verifies = dsa.verify(sig);
					} catch (Exception e1) {
					}
		            //System.out.println("signature verifies: " + verifies);
		            value.setText(Boolean.toString(verifies));
		            if(!verifies) value.setForeground(Color.RED);
		            if(verifies) value.setForeground(Color.GREEN);
			}
		});
	}

	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		// TODO Auto-generated method stub
		Main main = new Main();
	}
}
