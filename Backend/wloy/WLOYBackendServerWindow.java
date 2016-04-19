package wloy;

import java.awt.BorderLayout;
import javax.swing.*;

public class WLOYBackendServerWindow {
	
	// CONSTANTS - Window Data
	private static final String WINDOW_NAME = "WLOY Backend Server";
	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 300;
	
	// WINDOW ELEMENTS
	JFrame frame;
	JPanel panelRequests;
	WLOYRequestTable tableRequests;
	
	/**
	 * initializes a backend server window
	 */
	public WLOYBackendServerWindow()
	{
		// Initialize requests panel
		panelRequests = new JPanel(new BorderLayout());
		tableRequests = new WLOYRequestTable();
		JScrollPane requestsScrollPane = new JScrollPane(tableRequests, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelRequests.add(requestsScrollPane);
		
		// Make this all visible
		frame = new JFrame(WINDOW_NAME);
		frame.setContentPane(panelRequests); // TODO: Make main menu the main content pane
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// main - used only for debugging right now
	public static void main(String[] args)
	{
		WLOYBackendServerWindow window = new WLOYBackendServerWindow();
		window.tableRequests.addRequest(new WLOYRequest("No Surprises", "Radiohead"));
		window.tableRequests.addRequest(new WLOYRequest("Slow Show", "The National"));
	}
}
