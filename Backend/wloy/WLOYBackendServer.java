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
	Hashtable<Integer, WLOYListener> activeListeners;
	ArrayList<WLOYListener> finishedListeners;
	
	// METHODS
	private WLOYBackendServer(int port) throws IOException
	{
		// Initialize data stores
		activeListeners = new Hashtable<Integer, WLOYListener>();
		finishedListeners = new ArrayList<WLOYListener>();
		
		// Start socket
		ServerSocket socket = new ServerSocket(port);
		System.out.println("Socket opened on port " + port);
		System.out.println("Server address: " + InetAddress.getLocalHost());
		System.out.println();
		
		boolean active = true;
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
	
	// main - initialize a server
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
