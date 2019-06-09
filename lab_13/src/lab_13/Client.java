package lab_13;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame {
	public Client() throws IOException, MalformedObjectNameException {
		super();
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/jmxrmi");

		JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
		MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
		// ObjectName should be same as your MBean name
		ObjectName mbeanName = new ObjectName("lab_13:type=basic,name=slogan");

		// Get MBean proxy instance that will be used to make calls to
		// registered MBean
		SloganMBean mbeanProxy = (SloganMBean) MBeanServerInvocationHandler.newProxyInstance(mbeanServerConnection,
				mbeanName, SloganMBean.class, true);

		setSize(800, 400);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel label = new JLabel("Slogan1");
		label.setBounds(35, 33, 56, 16);
		panel.add(label);

		JLabel label_1 = new JLabel("Slogan2");
		label_1.setBounds(35, 85, 56, 16);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Slogan3");
		label_2.setBounds(35, 136, 56, 16);
		panel.add(label_2);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(103, 30, 395, 22);
		panel.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(103, 82, 395, 22);
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(103, 133, 395, 22);
		panel.add(textField_2);

		JLabel label_3 = new JLabel("Duration1");
		label_3.setBounds(520, 33, 56, 16);
		panel.add(label_3);

		JLabel label_4 = new JLabel("Duration2");
		label_4.setBounds(520, 85, 56, 16);
		panel.add(label_4);

		JLabel label_5 = new JLabel("Duration3");
		label_5.setBounds(520, 136, 56, 16);
		panel.add(label_5);

		textField_3 = new JTextField();
		textField_3.setText("0");
		textField_3.setColumns(10);
		textField_3.setBounds(606, 30, 116, 22);
		panel.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setText("0");
		textField_4.setColumns(10);
		textField_4.setBounds(606, 82, 116, 22);
		panel.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setText("0");
		textField_5.setColumns(10);
		textField_5.setBounds(606, 133, 116, 22);
		panel.add(textField_5);

		JButton button = new JButton("START/STOP");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mbeanProxy.startStop();
			}
		});
		button.setBounds(35, 191, 202, 92);
		panel.add(button);

		JButton changeButton = new JButton("CHANGE");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				mbeanProxy.setSlogan1(textField.getText());
				mbeanProxy.setDuration1(Integer.parseInt(textField_3.getText()));
				mbeanProxy.setSlogan2(textField_1.getText());
				mbeanProxy.setDuration2(Integer.parseInt(textField_4.getText()));
				mbeanProxy.setSlogan3(textField_2.getText());
				mbeanProxy.setDuration3(Integer.parseInt(textField_5.getText()));
			}
		});
		changeButton.setBounds(291, 191, 207, 92);
		panel.add(changeButton);
		setVisible(true);
		// jmxConnector.close();
	}

	public static final String HOST = "localhost";
	public static final String PORT = "1236";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	public static void main(String[] args) throws MalformedObjectNameException, IOException {
		Client cli = new Client();

	}
}
