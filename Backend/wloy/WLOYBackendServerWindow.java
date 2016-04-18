package wloy;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WLOYBackendServerWindow {

	// CONSTANTS - Button Text
	private static final String BUTTON_DELETE_REQUEST_TEXT = "DELETE";
	
	// CONSTANTS - Window Data
	private static final String WINDOW_NAME = "WLOY Backend Server";
	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 300;
	
	// CONSTANTS - Other
	private static final String[] REQUEST_COLUMN_NAMES = {"TITLE", "ARTIST", "DELETE"};
	
	// WINDOW ELEMENTS
	JFrame frame;
	JPanel panelRequests;
	JTable tableRequests;
	ArrayList<JButton> requestButtons;
	
	public WLOYBackendServerWindow()
	{
		// Initialize object stores
		requestButtons = new ArrayList<JButton>();
		
		// Initialize requests panel
		panelRequests = new JPanel(new BorderLayout());
		tableRequests = new JTable();
		tableRequests.setModel(new DefaultTableModel(new Object[0][0], REQUEST_COLUMN_NAMES));
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
	
	public void addRequest(WLOYRequest request)
	{
		DefaultTableModel requestTableModel = (DefaultTableModel) tableRequests.getModel();
		JButton newRequestButton = new JButton(BUTTON_DELETE_REQUEST_TEXT);
		// TODO: Add action listener to button
		requestButtons.add(newRequestButton);
		Object[] newRow = {request.getTitle(), request.getArtist(), newRequestButton};
		requestTableModel.addRow(newRow);
		requestTableModel.fireTableDataChanged();
	}
	
	public static void main(String[] args)
	{
		WLOYBackendServerWindow window = new WLOYBackendServerWindow();
		window.addRequest(new WLOYRequest("No Surprises", "Radiohead"));
	}
}
