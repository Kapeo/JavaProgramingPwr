package lab_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class toNoSpaces implements Runnable {
	public static void launch() {
		toNoSpaces nospaces = new toNoSpaces();
		Thread thread_nospaces = new Thread(nospaces);
		thread_nospaces.start();
	}

	int port = 2239;
	String host = "127.0.0.1";
	ServerSocket s1;
	Socket ss;
	PrintWriter out;
	BufferedReader in;

	void setInput(int port) {
		this.port = port;
	}

	void setOutput(String host, int port) {
		this.host = host;
		this.port = port;
	}

	void start() throws UnknownHostException, IOException {
		s1 = new ServerSocket(port);
		ss = s1.accept();
		out = new PrintWriter(ss.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(ss.getInputStream()));
		String text_in;
		text_in = in.readLine();
		// konwersja tekstu
		String converted_text = text_in.replaceAll(" ", "_");
		// odeslanie przekonwertowanego tekstu do klienta
		out.println(converted_text);
	}

	void stop() throws IOException {
		s1.close();
		ss.close();
		out.close();
		in.close();
	}

	@Override
	public void run() {
		try {
			start();
			stop();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
