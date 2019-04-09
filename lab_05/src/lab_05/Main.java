package lab_05;

import java.util.Arrays;

public class Main {


	private Double[] a = {3.0,1.0,1.0};
	private Double[] b = {4.0,12.0,1.0};
	private Double c;

	public Double[] getA() {
		return a;
	}

	public Double[] getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public void setA(Double[] a) {
		this.a = a;
	}

	public void setB(Double[] b) {
		this.b = b;
	}

	public void setC(double c) {
		this.c = c;
	}

	public native Double multi01(Double[] a, Double[] b);

	public native Double multi02(Double[] a);

	public native void multi03();

	private void multi04() {
		Double sum = 0.0;

        for (int i=0;i<a.length;i++){
            sum += a[i] * b[i];
        }

        c = sum;
	}

	private native void sayHello();

	public static void main(String[] args) {
		System.loadLibrary("hello");
		Main test = new Main();
		test.sayHello();

	    //Double[] a_test = {4.0,10.0,1.0};
	    //Double[] b_test = {3.0,1.0,1.0};
	   test.setA(new Double[]{4.0,10.0,1.0});
	   System.out.println(Arrays.deepToString(test.getA()));
		//test.setB(b_test);

		System.out.println("multi01 : " + test.multi01(test.getA(),test.getB()));
		System.out.println("multi02 : " + test.multi02(test.getA()));
	}

}
