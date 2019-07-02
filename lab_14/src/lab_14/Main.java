package lab_14;


/*
 * przy rozwiazywaniu tego zadania korzystalem z algorytmow dostepnych na stronie
 * https://eduinf.waw.pl/inf/alg/005_root/m0009.php
 */


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;


public class Main extends JFrame implements KeyListener {
	private JTextField aStr;
	private JTextField bStr;
	private JTextField cStr;
	private JTextField dStr;
	private JTextField x1Str;
	private JTextField x2Str;
	private JTextField x3Str;

	BigDecimal a;
	BigDecimal b;
	BigDecimal c;
	BigDecimal d;
	BigDecimal x3;
	BigDecimal x2;
	BigDecimal x1;
	BigDecimal f;
	BigDecimal g;
	BigDecimal h;



	public Main() {
		super();
		setSize(400, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		//getContentPane().add(panel, BorderLayout.CENTER);
		setContentPane(panel);
		panel.setLayout(null);

		JLabel aLabel = new JLabel("a =");
		aLabel.getAccessibleContext().setAccessibleDescription("aLabel");
		aLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		aLabel.setBounds(22, 61, 56, 16);
		panel.add(aLabel);

		JLabel bLabel = new JLabel("b = ");
		bLabel.getAccessibleContext().setAccessibleDescription("bLabel");
		bLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		bLabel.setBounds(22, 90, 56, 16);
		panel.add(bLabel);

		JLabel cLabel = new JLabel("c =");
		cLabel.getAccessibleContext().setAccessibleDescription("cLabel");
		cLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cLabel.setBounds(22, 119, 56, 16);
		panel.add(cLabel);

		JLabel dLabel = new JLabel("d =");
		dLabel.getAccessibleContext().setAccessibleDescription("dLabel");
		dLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dLabel.setBounds(22, 148, 56, 16);
		panel.add(dLabel);

		JLabel formula = new JLabel("a*(x^3) + b*(x^2) + c*x + d = 0");
		formula.getAccessibleContext().setAccessibleDescription("formula");
		formula.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formula.setBounds(22, 13, 320, 33);
		panel.add(formula);

		aStr = new JTextField();
		aLabel.setLabelFor(aStr);
		aStr.setToolTipText("a input");
		aStr.setText("0");
		aStr.setBounds(54, 58, 274, 22);
		panel.add(aStr);
		aStr.setColumns(10);

		bStr = new JTextField();
		bLabel.setLabelFor(bStr);
		bStr.setToolTipText("b input");
		bStr.setText("0");
		bStr.setBounds(54, 87, 274, 22);
		panel.add(bStr);
		bStr.setColumns(10);

		cStr = new JTextField();
		cLabel.setLabelFor(cStr);
		cStr.setToolTipText("c input");
		cStr.setText("0");
		cStr.setBounds(53, 116, 275, 22);
		panel.add(cStr);
		cStr.setColumns(10);

		dStr = new JTextField();
		dLabel.setLabelFor(dStr);
		dStr.setToolTipText("d input");
		dStr.setText("0");
		dStr.setBounds(54, 145, 274, 22);
		panel.add(dStr);
		dStr.setColumns(10);

		JLabel x1Label = new JLabel("x1 =");
		x1Label.getAccessibleContext().setAccessibleDescription("x1Label");
		x1Label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		x1Label.setBounds(22, 271, 56, 16);
		panel.add(x1Label);

		x1Str = new JTextField();
		x1Label.setLabelFor(x1Str);
		x1Str.setToolTipText("x1");
		x1Str.setEditable(false);
		x1Str.setBounds(64, 268, 264, 22);
		panel.add(x1Str);
		x1Str.setColumns(10);

		x2Str = new JTextField();
		x2Str.setToolTipText("x2");
		x2Str.setEditable(false);
		x2Str.setBounds(64, 304, 264, 22);
		panel.add(x2Str);
		x2Str.setColumns(10);

		JLabel x2Label = new JLabel("x2 =");
		x2Label.setLabelFor(x2Str);
		x2Label.getAccessibleContext().setAccessibleDescription("x2Label");
		x2Label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		x2Label.setBounds(22, 306, 56, 16);
		panel.add(x2Label);

		x3Str = new JTextField();
		x3Str.setToolTipText("x3");
		x3Str.setEditable(false);
		x3Str.setBounds(64, 338, 264, 22);
		panel.add(x3Str);
		x3Str.setColumns(10);

		JLabel x3Label = new JLabel("x3 =");
		x3Label.setLabelFor(x3Str);
		x3Label.getAccessibleContext().setAccessibleDescription("x3Label");
		x3Label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		x3Label.setBounds(22, 338, 56, 16);
		panel.add(x3Label);

		JButton calculate = new JButton("CALCULATE");
		calculate.addKeyListener(this);
		calculate.getAccessibleContext().
	    setAccessibleDescription(
	    "Clicking this component causes calculations");
		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculate();
			}
		});
		calculate.setBounds(22, 180, 306, 65);
		panel.add(calculate);

		setVisible(true);
	}


	public void calculate(){
		a = new BigDecimal(aStr.getText());
		b = new BigDecimal(bStr.getText());
		c = new BigDecimal(cStr.getText());
		d = new BigDecimal(dStr.getText());
		BigDecimal temp1;
		BigDecimal temp2;
		BigDecimal temp3;
		BigDecimal temp4;
		BigDecimal temp5;
		BigDecimal temp6;
		BigDecimal temp7;
		temp1 = c.divide(a, MathContext.DECIMAL32);
		temp2 = b.pow(2);
		temp3 = (a.pow(2)).multiply(new BigDecimal(3));
		temp4 = temp2.divide(temp3, MathContext.DECIMAL32);
		f= temp1.subtract(temp4);
		System.out.println("f = " + f);
		temp1 = (b.pow(3)).multiply(new BigDecimal(2));
		temp2 = (a.pow(3)).multiply(new BigDecimal(27));
		temp3 = temp1.divide(temp2, MathContext.DECIMAL32);
		temp4 = (b.multiply(c));
		temp5 = (a.pow(2)).multiply(new BigDecimal(3));
		temp6 = temp4.divide(temp5, MathContext.DECIMAL32);
		temp7 = d.divide(a, MathContext.DECIMAL32);
		g = (temp3.subtract(temp6)).add(temp7);
		System.out.println("g = " + g);
		temp1 = (g.pow(2)).divide(new BigDecimal(4), MathContext.DECIMAL32);
		temp2 = (f.pow(3)).divide(new BigDecimal(27), MathContext.DECIMAL32);
		h = temp1.add(temp2);
		System.out.println("h= " + h);


		if (h.compareTo(BigDecimal.ZERO)>0){
			double hDouble = h.doubleValue();
			double hDoubleSqrt = Math.sqrt(hDouble);
			BigDecimal hSqrt = BigDecimal.valueOf(hDoubleSqrt);
			System.out.println(hSqrt);

			temp1 = (g.divide(new BigDecimal(2), MathContext.DECIMAL32)).negate();
			temp2 = temp1.add(hSqrt);

			System.out.println(temp1);
			double help1 = temp2.doubleValue();
			double help1Sqrt = Math.cbrt(help1);
			temp3 = BigDecimal.valueOf(help1Sqrt);

			temp4 = temp1.subtract(hSqrt);
			double help2 = temp4.doubleValue();
			double help2Sqrt = Math.cbrt(help2);
			temp5 = BigDecimal.valueOf(help2Sqrt);

			temp6 = a.multiply(new BigDecimal(3));
			temp7 = b.divide(temp6, MathContext.DECIMAL32);

			x1=(temp3.add(temp5)).subtract(temp7);

			BigDecimal scaledx1 = x1.setScale(5, RoundingMode.HALF_UP);

			x1Str.setText(scaledx1.toString());
			x2Str.setText("");
			x3Str.setText("");
			return;
		}

		else if (f.compareTo(BigDecimal.ZERO)==0 && g.compareTo(BigDecimal.ZERO) == 0)
		{
			temp1 = d.divide(a, MathContext.DECIMAL32);
			double help3 = temp1.doubleValue();
			double help3Sqrt = Math.cbrt(help3);
			temp2 = BigDecimal.valueOf(help3Sqrt);
			x1 = temp2.negate();
			x2 = x1;
			x3 = x1;
			BigDecimal scaledx1 = x1.setScale(5, RoundingMode.HALF_UP);
			BigDecimal scaledx2 = x2.setScale(5, RoundingMode.HALF_UP);
			BigDecimal scaledx3 = x3.setScale(5, RoundingMode.HALF_UP);

			x1Str.setText(scaledx1.toString());
			x2Str.setText(scaledx2.toString());
			x3Str.setText(scaledx3.toString());
		}
		else {
			BigDecimal i;
			BigDecimal j;
			BigDecimal k;
			BigDecimal m = null;
			BigDecimal n;
			BigDecimal p;
			temp1 = ((g.pow(2)).divide(new BigDecimal(4), MathContext.DECIMAL32)).subtract(h);
			double help4 = temp1.doubleValue();
			double help4Sqrt = Math.sqrt(help4);


			i = BigDecimal.valueOf(help4Sqrt);

			double iDouble = i.doubleValue();
			double iDoubleSqrt = Math.cbrt(iDouble);
			j = BigDecimal.valueOf(iDoubleSqrt);

			temp2 = (g.divide(i.multiply(new BigDecimal(2)), MathContext.DECIMAL32)).negate();
			double help5 = temp2.doubleValue();
			double help5acos = Math.acos(help5);
			k = BigDecimal.valueOf(help5acos);

			temp3 = k.divide(new BigDecimal(3), MathContext.DECIMAL32);
			double help6 = temp3.doubleValue();
			double help6cos = Math.cos(help6);
			m = BigDecimal.valueOf(help6cos);

			double help6sin = Math.sin(help6);
			double help7 = help6sin*Math.sqrt(3);
			n = BigDecimal.valueOf(help7);

			p = (b.divide(a.multiply(new BigDecimal(3)), MathContext.DECIMAL32)).negate();

			x1 = ((m.multiply(j)).multiply(new BigDecimal(2))).add(p);
			x2 = ((m.add(n)).multiply(j.negate())).add(p);
			x3 = ((m.subtract(n)).multiply(j.negate())).add(p);

			BigDecimal scaledx1 = x1.setScale(5, RoundingMode.HALF_UP);
			BigDecimal scaledx2 = x2.setScale(5, RoundingMode.HALF_UP);
			BigDecimal scaledx3 = x3.setScale(5, RoundingMode.HALF_UP);


			x1Str.setText(scaledx1.toString());
			x2Str.setText(scaledx2.toString());
			x3Str.setText(scaledx3.toString());

			//System.out.println("i"+i);
			//System.out.println("j"+j);
			//System.out.println("k"+k);
			//System.out.println("m"+m);
			//System.out.println("n"+n);
			//System.out.println("p"+p);



		}


	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
	}

	@Override
	public void keyPressed (KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_D){
			calculate();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
