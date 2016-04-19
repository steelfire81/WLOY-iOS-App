package wloy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class WLOYBackendServerThread extends Thread {

	// CONSTANTS - Error Messages
	private static final String ERR_INVALID_MESSAGE = "ERROR: Invalid message type";
	private static final String ERR_NUM_FORMAT = "ERROR: Could not parse message";
	
	// CONSTANTS - Feedback
	private static final String FEEDBACK_POSITIVE = "POSITIVE";
	
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
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Read message
			String message = "";
			String readChars;
			while((readChars = input.readLine()) != null)
				message += readChars + "\n";
			
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
			 * [Identifier (Int)]
			 * [Connection time (Int)]
			 * [FEEDBACK_POSITIVE or FEEDBACK_NEGATIVE]
			 * [Song Title (String)]
			 * [Artist (String])
			 * [Show (String)]
			 * [DJ (String)]
			 * 
			 * Request:
			 * HEADER_REQUEST
			 * [Identifier (Int)]
			 * [Connection time (Int)]
			 * [Song Title (String)]
			 * [Artist (String)]
			 */
			Scanner messageScan = new Scanner(message);
			String header = messageScan.nextLine();
			try
			{
				// All messages contain an identifier and time connected to update connection information
				int id = Integer.parseInt(messageScan.nextLine());
				int timeConnected = Integer.parseInt(messageScan.nextLine());
				parent.connectionMessageReceived(id, timeConnected);
				
				switch(header)
				{
					case HEADER_CONNECTION:
						break; // No additional information, do nothing
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
						System.err.println(message);
						System.err.println();
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
			ioe.printStackTrace();
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
