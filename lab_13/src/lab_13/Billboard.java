package lab_13;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Billboard extends JFrame implements Runnable {
	private JTextField slogan1;
	private JTextField slogan2;
	private JTextField slogan3;
	private JTextField duration1;
	private JTextField duration2;
	private JTextField duration3;
	private JTextField sloganOut;

	Thread thread;
	Slogan sloganObj;
	AtomicBoolean running = new AtomicBoolean(false);
	int interval;

	public Billboard() {
		super();
		ObjectName objectName = null;
		try {
			objectName = new ObjectName("lab_13:type=basic,name=slogan");
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		sloganObj = new Slogan();
		try {
			server.registerMBean(sloganObj, objectName);
		} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
		setSize(800, 450);
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Slogan1");
		lblNewLabel.setBounds(35, 33, 56, 16);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Slogan2");
		lblNewLabel_1.setBounds(35, 85, 56, 16);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Slogan3");
		lblNewLabel_2.setBounds(35, 136, 56, 16);
		panel.add(lblNewLabel_2);

		slogan1 = new JTextField();
		slogan1.setBounds(103, 30, 395, 22);
		panel.add(slogan1);
		slogan1.setColumns(10);

		slogan2 = new JTextField();
		slogan2.setBounds(103, 82, 395, 22);
		panel.add(slogan2);
		slogan2.setColumns(10);

		slogan3 = new JTextField();
		slogan3.setBounds(103, 133, 395, 22);
		panel.add(slogan3);
		slogan3.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Duration1");
		lblNewLabel_3.setBounds(520, 33, 56, 16);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Duration2");
		lblNewLabel_4.setBounds(520, 85, 56, 16);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Duration3");
		lblNewLabel_5.setBounds(520, 136, 56, 16);
		panel.add(lblNewLabel_5);

		duration1 = new JTextField();
		duration1.setText("0");
		duration1.setBounds(606, 30, 116, 22);
		panel.add(duration1);
		duration1.setColumns(10);

		duration2 = new JTextField();
		duration2.setText("0");
		duration2.setBounds(606, 82, 116, 22);
		panel.add(duration2);
		duration2.setColumns(10);

		duration3 = new JTextField();
		duration3.setText("0");
		duration3.setBounds(606, 133, 116, 22);
		panel.add(duration3);
		duration3.setColumns(10);

		sloganOut = new JTextField();
		sloganOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		sloganOut.setBounds(35, 196, 463, 183);
		panel.add(sloganOut);
		sloganOut.setColumns(10);

		JButton runBtn = new JButton("START/STOP");
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sloganObj.startStop();
				System.out.println(sloganObj.getRunning());
				if (sloganObj.getRunning()) {
					sloganObj.setSlogan1(slogan1.getText());
					sloganObj.setSlogan2(slogan2.getText());
					sloganObj.setSlogan3(slogan3.getText());
					sloganObj.setDuration1(Integer.parseInt(duration1.getText()));
					sloganObj.setDuration2(Integer.parseInt(duration2.getText()));
					sloganObj.setDuration3(Integer.parseInt(duration3.getText()));
				}
			}
		});

		runBtn.setBounds(520, 196, 202, 183);
		panel.add(runBtn);
		getContentPane().add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		Billboard bill = new Billboard();

	}

	@Override
	public void run() {
		while (true) {
			System.out.print("");
			while (sloganObj.getRunning()) {

				sloganOut.setText(sloganObj.getSlogan1());
				try {
					Thread.sleep(sloganObj.getDuration1() * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sloganOut.setText(sloganObj.getSlogan2());
				try {
					Thread.sleep(sloganObj.getDuration2() * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sloganOut.setText(sloganObj.getSlogan3());
				try {
					Thread.sleep(sloganObj.getDuration3() * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
