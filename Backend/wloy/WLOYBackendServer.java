package wloy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class WLOYBackendServer {

	// CONSTANTS - Ports
	public static final int PORT = 4444;
	
	// CONSTANTS - Error Messages
	private static final String ERR_FILE_OUTPUT = "ERROR: Could not output data files";
	
	// CONSTANTS - Filenames
	private static final String FILENAME_CONNECTION = "connections-";
	private static final String FILENAME_EXTENSION = ".csv";
	private static final String FILENAME_FEEDBACK = "feedback-";
	
	// DATA MEMBERS
	private Hashtable<Integer, WLOYListener> activeListeners;
	private ArrayList<WLOYListener> finishedListeners;
	private ArrayList<WLOYRequest> requests;
	private ArrayList<WLOYFeedback> feedback;
	private WLOYBackendServerWindowEngine windowEngine;
	
	// METHODS
	/**
	 * initializes a WLOYBackendServer
	 * 
	 * @param port port on which to listen for connections
	 * @throws IOException thrown if socket could not be opened
	 */
	public WLOYBackendServer(int port, WLOYBackendServerWindowEngine we) throws IOException
	{
		windowEngine = we;
		
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
			exportData(); // debug for now
		}
		socket.close();
		
		// On close, export all data
		try
		{
			exportData();
		}
		catch(IOException ioe)
		{
			System.err.println(ERR_FILE_OUTPUT);
		}
	}
	
	/**
	 * saves listener and feedback data as .csv files
	 */
	private void exportData() throws IOException
	{
		String connectionFilename = getDatedFilename(FILENAME_CONNECTION);
		String feedbackFilename = getDatedFilename(FILENAME_FEEDBACK);
		
		// Create connection file
		File connectionFile = new File(connectionFilename);
		
		// Create list of all listeners (connected and disconnected)
		ArrayList<WLOYListener> allListeners = new ArrayList<WLOYListener>(activeListeners.values());
		for(int i = 0; i < finishedListeners.size(); i++)
			allListeners.add(finishedListeners.get(i));
		
		// Output to connection file
		PrintWriter connectionFileOutput = new PrintWriter(connectionFile);
		String connectionHeaderRow = "";
		for(int i = 0; i < WLOYListener.CSV_COLUMN_HEADERS.length; i++)
			connectionHeaderRow += WLOYListener.CSV_COLUMN_HEADERS[i] + ",";
		connectionHeaderRow = connectionHeaderRow.substring(0, connectionHeaderRow.length() - 1); // remove last comma
		connectionFileOutput.println(connectionHeaderRow);
		for(int i = 0; i < allListeners.size(); i++)
			connectionFileOutput.println(allListeners.get(i));
		connectionFileOutput.close();
		
		// Create feedback file
		File feedbackFile = new File(feedbackFilename);
		
		// Output to feedback file
		PrintWriter feedbackFileOutput = new PrintWriter(feedbackFile);
		String feedbackHeaderRow = "";
		for(int i = 0; i < WLOYFeedback.CSV_COLUMN_HEADERS.length; i++)
			feedbackHeaderRow += WLOYFeedback.CSV_COLUMN_HEADERS[i] + ",";
		feedbackHeaderRow = feedbackHeaderRow.substring(0, feedbackHeaderRow.length() - 1); // remove last comma
		feedbackFileOutput.println(feedbackHeaderRow);
		for(int i = 0; i < feedback.size(); i++)
			feedbackFileOutput.println(feedback.get(i));
		feedbackFileOutput.close();
	}
	
	/**
	 * returns a filename for an exported file
	 * 
	 * @param head start of filename
	 * @return appropriate filename with date/time of export
	 */
	private String getDatedFilename(String head)
	{
		Calendar calendar = Calendar.getInstance();
		return head + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +
				"-" + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + FILENAME_EXTENSION; 
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
			if(existingListener == null)
			{
				// Insert listener with information
				WLOYListener newListener = new WLOYListener(id);
				activeListeners.put(new Integer(id), newListener);
			}
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
		
		// DEBUG
		System.out.println("Listeners:");
		ArrayList<WLOYListener> listenerList= new ArrayList<WLOYListener>(activeListeners.values());
		for(int i = 0; i < listenerList.size(); i++)
			System.out.println(listenerList.get(i));
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
		windowEngine.feedbackReceived(fb);
		System.out.println("Received new feedback: " + fb); // DEBUG
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
		windowEngine.requestReceived(request);
		System.out.println("Received new request: " + request); // DEBUG
	}
}
