package wloy;

public class WLOYBackendServer {

	// CONSTANTS - Ports
	private static final int PORT_DEFAULT = 4444;
	private static final int PORT_MIN = 1024;
	private static final int PORT_MAX = 49151;
	
	// CONSTANTS - Error Messages
	private static final String ERR_ARGS = "USAGE: WLOYBackendServer [port #]";
	private static final String ERR_PORT_LOW = "ERROR: Minimum port # is " + PORT_MIN;
	private static final String ERR_PORT_HIGH = "ERROR: Maximum port # is " + PORT_MAX;
	
	// METHODS
	private WLOYBackendServer(int port)
	{
		// TODO: Actually do something here
	}
	
	// main - initialize a server
	public static void main(String[] args)
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
