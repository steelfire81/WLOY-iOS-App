package wloy;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class WLOYBackendServerThread extends Thread {

	// CONSTANTS
	private static final String THREAD_NAME = "WLOY App Listener Thread";
	
	// DATA MEMBERS
	private Socket socket = null;
	private WLOYBackendServer parent;
	
	/**
	 * creates a backend server thread
	 * 
	 * @param clientSocket socket this thread will listen to
	 * @param server parent server of this thread
	 */
	public WLOYBackendServerThread(Socket clientSocket, WLOYBackendServer server)
	{
		super(THREAD_NAME);
		socket = clientSocket;
		parent = server;
	}
	
	/**
	 * reads input from the connection
	 */
	@Override
	public void run()
	{
		try
		{
			DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			String message = input.readUTF();
			// TODO: Parse message
		}
		catch(IOException ioe)
		{
			System.err.println("ERROR: Problem retrieving information from " + getAddress());
		}
	}
	
	/**
	 * get the IP address to which this thread is connected
	 * 
	 * @return the socket's connected IP address
	 */
	public InetAddress getAddress()
	{
		return socket.getInetAddress();
	}
}
