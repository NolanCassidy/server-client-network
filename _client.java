import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class _client {
	private static final int PORT = 1749;

	public static void main(String args[]) {
		ArrayList<Integer> list = input();
		System.out.println("Send: " + list);
		ArrayList<Integer> plist = run(list);
		System.out.println("Receive: " + plist);
	}

	public static ArrayList<Integer> input(){
		System.out.println("Enter an integer (“!” to send):  ");
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (input.hasNextInt()) {
			System.out.println("Enter an integer (“!” to send):  ");
			int num = input.nextInt();
			list.add(num);
		}
		input.close();
		return list;
	}

	static ArrayList<Integer> run(ArrayList<Integer> l) {
		ArrayList<Integer> list = l;
		Socket client = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		ArrayList<Integer> plist = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			client = new Socket(address, PORT);
			out = new ObjectOutputStream(client.getOutputStream());
			out.flush();
			out.writeObject(list);
			out.flush();
		}catch (IOException ioException) {
			ioException.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			out.flush();
			in = new ObjectInputStream(client.getInputStream());
			try {
				plist = (ArrayList<Integer>) in.readObject();
			} catch (ClassNotFoundException classNot) {
				System.out.println("bad list");
			}
		}catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				if (client != null) {
					client.close();
				}
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return plist;
	}
}