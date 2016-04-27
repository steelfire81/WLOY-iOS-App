package wloy;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class WLOYBackendServerWindow {
	
	// CONSTANTS - Button Text
	private static final String BUTTON_EXPORT_DATA_TEXT = "EXPORT";
	private static final String BUTTON_LOAD_SCHEDULE_TEXT = "LOAD";
	private static final String BUTTON_SAVE_SCHEDULE_TEXT = "SAVE";
	
	// CONSTANTS - Label Text
	private static final String LABEL_FEEDBACK = "FEEDBACK";
	private static final String LABEL_REQUESTS = "REQUESTS";
	
	// CONSTANTS - Schedule
	private static final int SCHEDULE_WIDTH = 8;
	private static final int SCHEDULE_HEIGHT = 5;
	private static final String SCHEDULE_LABEL_HOUR = "HOUR";
	private static final String SCHEDULE_LABEL_SUNDAY = "SUNDAY";
	private static final String SCHEDULE_LABEL_MONDAY = "MONDAY";
	private static final String SCHEDULE_LABEL_TUESDAY = "TUESDAY";
	private static final String SCHEDULE_LABEL_WEDNESDAY = "WEDNESDAY";
	private static final String SCHEDULE_LABEL_THURSDAY = "THURSDAY";
	private static final String SCHEDULE_LABEL_FRIDAY = "FRIDAY";
	private static final String SCHEDULE_LABEL_SATURDAY = "SATURDAY";
	private static final String SCHEDULE_LABEL_AM = "A.M.";
	private static final String SCHEDULE_LABEL_PM = "P.M.";
	
	// CONSTANTS - Window Data
	private static final String WINDOW_NAME = "WLOY Backend Server";
	private static final int WINDOW_WIDTH_SCHEDULE = 1200;
	private static final int WINDOW_HEIGHT_SCHEDULE = 1000;
	private static final int WINDOW_WIDTH_DJ = 800;
	private static final int WINDOW_HEIGHT_DJ = 300;
	
	// WINDOW ELEMENTS
	JButton buttonExportData;
	JButton buttonLoadSchedule;
	JButton buttonSaveSchedule;
	JFrame frame;
	JPanel panelDJData;
	JPanel panelSchedule;
	WLOYSchedulePanel panelScheduleSubpanel;
	WLOYRequestTable tableRequests;
	WLOYFeedbackTable tableFeedback;
	
	// DATA MEMBERS
	private WLOYBackendServerWindowEngine engine;
	
	/**
	 * initializes a backend server window
	 */
	public WLOYBackendServerWindow()
	{
		// Initialize engine
		engine = new WLOYBackendServerWindowEngine(this);
		
		// Initialize schedule panel
		panelSchedule = new JPanel(new BorderLayout());
		panelScheduleSubpanel = new WLOYSchedulePanel();
		JPanel panelScheduleButtons = new JPanel(new GridLayout(1, 2));
		buttonLoadSchedule = new JButton(BUTTON_LOAD_SCHEDULE_TEXT);
		buttonLoadSchedule.addActionListener(engine);
		buttonSaveSchedule = new JButton(BUTTON_SAVE_SCHEDULE_TEXT);
		buttonSaveSchedule.addActionListener(engine);
		panelScheduleButtons.add(buttonLoadSchedule);
		panelScheduleButtons.add(buttonSaveSchedule);
		panelSchedule.add(panelScheduleSubpanel, BorderLayout.CENTER);
		panelSchedule.add(panelScheduleButtons, BorderLayout.SOUTH);
		
		// Initialize requests panel
		JPanel panelRequests = new JPanel(new BorderLayout());
		JTextField labelRequests = new JTextField(LABEL_REQUESTS);
		labelRequests.setEditable(false);
		tableRequests = new WLOYRequestTable();
		JScrollPane requestsScrollPane = new JScrollPane(tableRequests, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelRequests.add(labelRequests, BorderLayout.NORTH);
		panelRequests.add(requestsScrollPane, BorderLayout.CENTER);
		
		// Initialize feedback panel
		JPanel panelFeedback = new JPanel(new BorderLayout());
		JTextField labelFeedback = new JTextField(LABEL_FEEDBACK);
		labelFeedback.setEditable(false);
		tableFeedback = new WLOYFeedbackTable();
		JScrollPane feedbackScrollPane = new JScrollPane(tableFeedback, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelFeedback.add(labelFeedback, BorderLayout.NORTH);
		panelFeedback.add(feedbackScrollPane, BorderLayout.CENTER);
		
		// Initialize DJ data panel
		panelDJData = new JPanel(new BorderLayout());
		buttonExportData = new JButton(BUTTON_EXPORT_DATA_TEXT);
		buttonExportData.addActionListener(engine);
		JPanel panelDJDataSubpanel = new JPanel(new GridLayout(1, 2));
		panelDJDataSubpanel.add(panelRequests);
		panelDJDataSubpanel.add(panelFeedback);
		panelDJData.add(buttonExportData, BorderLayout.NORTH);
		panelDJData.add(panelDJDataSubpanel, BorderLayout.CENTER);
		
		// Make this all visible
		frame = new JFrame(WINDOW_NAME);
		frame.setContentPane(panelSchedule);
		frame.setSize(WINDOW_WIDTH_SCHEDULE, WINDOW_HEIGHT_SCHEDULE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// main - used only for debugging right now
	public static void main(String[] args)
	{
		WLOYBackendServerWindow window = new WLOYBackendServerWindow();
		window.tableRequests.addRequest(new WLOYRequest("No Surprises", "Radiohead"));
		window.tableRequests.addRequest(new WLOYRequest("Slow Show", "The National"));
		window.tableFeedback.addFeedback(new WLOYFeedback(true, "No Surprises", "Radiohead", "The Radiohead Hour", "DJ Radiohead"));
		window.tableFeedback.addFeedback(new WLOYFeedback(false, "We Built This City", "Starship", "The Bad Song Show", "DJ Bad Taste"));
	}
}
