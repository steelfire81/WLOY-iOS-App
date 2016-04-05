package wloy;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class WLOYBackendServerThread extends Thread {

	// CONSTANTS - Error Messages
	private static final String ERR_INVALID_MESSAGE = "ERROR: Invalid message type";
	private static final String ERR_NUM_FORMAT = "ERROR: Could not parse message";
	
	// CONSTANTS - Feedback
	private static final String FEEDBACK_POSITIVE = "POSITIVE";
	private static final String FEEDBACK_NEGATIVE = "NEGATIVE";
	
	// CONSTANTS - Message Headers
	private static final String HEADER_CONNECTION = "CONNECTION";
	private static final String HEADER_FEEDBACK = "FEEDBACK";
	private static final String HEADER_REQUEST = "REQUEST";
	
	// CONSTANTS - Other
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
			
			/* Parse Message
			 * Messages can be connection messages, feedback messages, or request messages.  Messages are
			 * formatted as follows:
			 * 
			 * Connection:
			 * HEADER_CONNECTION
			 * [Identifier (int)]
			 * [Connection time (int)]
			 * 
			 * Feedback:
			 * HEADER_FEEDBACK
			 * [FEEDBACK_POSITIVE or FEEDBACK_NEGATIVE]
			 * [Song Title (String)]
			 * [Artist (String])
			 * [Show (String)]
			 * [DJ (String)]
			 * 
			 * Request:
			 * HEADER_REQUEST
			 * [Song Title (String)]
			 * [Artist (String)]
			 */
			Scanner messageScan = new Scanner(message);
			String header = messageScan.nextLine();
			try
			{
				switch(header)
				{
					case HEADER_CONNECTION:
						int id = Integer.parseInt(messageScan.nextLine());
						int timeConnected = Integer.parseInt(messageScan.nextLine());
						parent.connectionMessageReceived(id, timeConnected);
						break;
					case HEADER_FEEDBACK:
						String feedback = messageScan.nextLine();
						String songTitle = messageScan.nextLine();
						String artist = messageScan.nextLine();
						String show = messageScan.nextLine();
						String dj = messageScan.nextLine();
						parent.feedbackMessageReceived(feedback.equals(FEEDBACK_POSITIVE), songTitle, artist, show, dj);
						break;
					case HEADER_REQUEST:
						String requestTitle = messageScan.nextLine();
						String requestArtist = messageScan.nextLine();
						parent.requestMessageReceived(requestTitle, requestArtist);
						break;
					default:
						System.err.println(ERR_INVALID_MESSAGE);
				}
			}
			catch(NumberFormatException nfe)
			{
				System.err.println(ERR_NUM_FORMAT);
			}
			
			messageScan.close();
					
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
