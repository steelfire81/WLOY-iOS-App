package wloy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Hashtable;

public class WLOYBackendServer {

	// CONSTANTS - Ports
	private static final int PORT_DEFAULT = 4444;
	private static final int PORT_MIN = 1024;
	private static final int PORT_MAX = 49151;
	
	// CONSTANTS - Error Messages
	private static final String ERR_ARGS = "USAGE: WLOYBackendServer [port #]";
	private static final String ERR_PORT_LOW = "ERROR: Minimum port # is " + PORT_MIN;
	private static final String ERR_PORT_HIGH = "ERROR: Maximum port # is " + PORT_MAX;
	
	// DATA MEMBERS
	private Hashtable<Integer, WLOYListener> activeListeners;
	private ArrayList<WLOYListener> finishedListeners;
	private ArrayList<WLOYRequest> requests;
	private ArrayList<WLOYFeedback> feedback;
	
	// METHODS
	/**
	 * initializes a WLOYBackendServer
	 * 
	 * @param port port on which to listen for connections
	 * @throws IOException thrown if socket could not be opened
	 */
	private WLOYBackendServer(int port) throws IOException
	{
		// Initialize data stores
		activeListeners = new Hashtable<Integer, WLOYListener>();
		finishedListeners = new ArrayList<WLOYListener>();
		requests = new ArrayList<WLOYRequest>();
		feedback = new ArrayList<WLOYFeedback>();
		
		// Start socket
		ServerSocket socket = new ServerSocket(port);
		System.out.println("Socket opened on port " + port);
		System.out.println("Server address: " + InetAddress.getLocalHost());
		System.out.println();
		
		boolean active = true; // TODO: Close this loop
		while(active)
		{
			WLOYBackendServerThread thread = new WLOYBackendServerThread(socket.accept(), this);
			System.out.println("Connection from " + thread.getAddress());
			thread.start();
		}
	}
	
	/**
	 * handles a client's message verifying a connection
	 * 
	 * @param id client's identifier
	 * @param timeConnected the time the client says it has been connected
	 */
	public void connectionMessageReceived(int id, int timeConnected)
	{
		if(timeConnected == 0) // New listener
		{
			// Check to make sure a listener with this id doesn't already exist
			WLOYListener existingListener = activeListeners.get(new Integer(id));
			if(existingListener != null)
			{
				activeListeners.remove(new Integer(id));
				finishedListeners.add(existingListener);
			}
			
			// Insert listener with information
			WLOYListener newListener = new WLOYListener(id);
			activeListeners.put(new Integer(id), newListener);
		}
		else // Existing listener
		{
			WLOYListener listener = activeListeners.get(new Integer(id));
			
			// Make sure listener exists
			if(listener == null)
			{
				System.err.println("ERROR: Received message from existing client #" + id + " but found no data");
				System.err.println("\tInitializing new listener #" + id + " with time " + timeConnected + " minutes");
				WLOYListener newListener = new WLOYListener(id);
				newListener.updateLastContact(timeConnected);
				activeListeners.put(new Integer(id), newListener);
			}
			else
				listener.updateLastContact(timeConnected);
		}
	}
	
	/**
	 * handles a client's message giving feedback for a song being played
	 * 
	 * @param positive <b>true</b> if feedback is positive, <b>false</b> if negative
	 * @param songTitle song's title
	 * @param artist song's artist
	 * @param show current show name
	 * @param dj dj name
	 */
	public void feedbackMessageReceived(boolean positive, String songTitle, String artist, String show, String dj)
	{
		WLOYFeedback fb = new WLOYFeedback(positive, songTitle, artist, show, dj);
		feedback.add(fb);
	}
	
	/**
	 * handle's a client's message requesting a song to be played
	 * 
	 * @param songTitle
	 * @param artist
	 */
	public void requestMessageReceived(String songTitle, String artist)
	{
		WLOYRequest request = new WLOYRequest(songTitle, artist);
		requests.add(request);
		System.out.println("Received new request: " + request);
	}
	
	/**
	 * initializes a server
	 * 
	 * @param args may contain a port number as the only argument, or may be empty
	 * @throws IOException thrown if server could not be initialized
	 */
	public static void main(String[] args) throws IOException
	{
		if(args.length == 0)
		{
			System.out.println("No port specified; using port " + PORT_DEFAULT);
			new WLOYBackendServer(PORT_DEFAULT);
		}
		else if(args.length == 1)
		{
			try
			{
				int port = Integer.parseInt(args[0]);
				if(port < PORT_MIN)
				{
					System.err.println(ERR_PORT_LOW);
					System.exit(1);
				}
				else if(port > PORT_MAX)
				{
					System.err.println(ERR_PORT_HIGH);
					System.exit(1);
				}
				else
					new WLOYBackendServer(port);
			}
			catch(NumberFormatException nfe)
			{
				System.err.println(ERR_ARGS);
				System.exit(1);
			}
		}
		else // Improper arguments
		{
			System.err.println(ERR_ARGS);
			System.exit(1);
		}
	}
}
