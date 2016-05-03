package wloy;

import java.io.IOException;

public class WLOYBackendServerMainThread extends Thread {

	// CONSTANTS
	private static final String ERR_SERVER_OPEN = "ERROR: Could not open a server";
	
	// DATA MEMBERS
	private WLOYBackendServerWindowEngine windowEngine;
	
	// METHODS
	/**
	 * create a thread containing a WLOYBackendServer
	 * 
	 * @param we engine for the window to which this server belongs
	 */
	public WLOYBackendServerMainThread(WLOYBackendServerWindowEngine we)
	{
		windowEngine = we;
	}
	
	/**
	 * boot a WLOYBackendServer
	 */
	@Override
	public void run()
	{
		try
		{
			new WLOYBackendServer(WLOYBackendServer.PORT, windowEngine);
		}
		catch(IOException ioe)
		{
			System.err.println(ERR_SERVER_OPEN);
			System.exit(1);
		}
	}
}
