import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class _server {
	private static final int PORT = 1749;

	public static void main(String args[]) {
		ServerSocket server = null;
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		ArrayList<Integer> plist = null;
		try {
			server = new ServerSocket(PORT);
			socket = server.accept();

			in = new ObjectInputStream(socket.getInputStream());
			try {
				plist = primes((ArrayList<Integer>) in.readObject());
			} catch (ClassNotFoundException classnot) {
				System.out.println("bad list");
			}
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			out.writeObject(plist);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				if (server != null) {
					server.close();
				}
				if (socket != null) {
					socket.close();
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
	}

	private static ArrayList<Integer> primes(ArrayList<Integer> list) {
		ArrayList<Integer> plist = new ArrayList<Integer>();
		for (Integer num : list) {
			boolean check = true;
			for (int i = 2; i < num; i++) {
				if (num % i == 0) {
					check = false;
				}
			}
			if (check == true) {
				plist.add(num);
			}
		}
		return plist;
	}
}