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
	private static final String BUTTON_TO_SCHEDULE_TEXT = "SWITCH TO SCHEDULE";
	private static final String BUTTON_TO_STATS_TEXT = "SWITCH TO STATS";
	
	// CONSTANTS - Label Text
	private static final String LABEL_FEEDBACK = "FEEDBACK";
	private static final String LABEL_REQUESTS = "REQUESTS";
	
	// CONSTANTS - Window Data
	private static final String WINDOW_NAME = "WLOY Backend Server";
	private static final int WINDOW_WIDTH_SCHEDULE = 1200;
	private static final int WINDOW_HEIGHT_SCHEDULE = 1000;
	private static final int WINDOW_WIDTH_DJ = 1200;
	private static final int WINDOW_HEIGHT_DJ = 400;
	
	// WINDOW ELEMENTS
	JButton buttonExportData;
	JButton buttonLoadSchedule;
	JButton buttonSaveSchedule;
	JButton buttonSwitchToStats;
	JButton buttonSwitchToSchedule;
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
		buttonSwitchToStats = new JButton(BUTTON_TO_STATS_TEXT);
		buttonSwitchToStats.addActionListener(engine);
		panelScheduleButtons.add(buttonLoadSchedule);
		panelScheduleButtons.add(buttonSaveSchedule);
		panelSchedule.add(panelScheduleSubpanel, BorderLayout.CENTER);
		panelSchedule.add(panelScheduleButtons, BorderLayout.SOUTH);
		panelSchedule.add(buttonSwitchToStats, BorderLayout.NORTH);
		
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
		buttonSwitchToSchedule = new JButton(BUTTON_TO_SCHEDULE_TEXT);
		buttonSwitchToSchedule.addActionListener(engine);
		JPanel panelDJDataSubpanel = new JPanel(new GridLayout(1, 2));
		panelDJDataSubpanel.add(panelRequests);
		panelDJDataSubpanel.add(panelFeedback);
		panelDJData.add(buttonExportData, BorderLayout.SOUTH);
		panelDJData.add(panelDJDataSubpanel, BorderLayout.CENTER);
		panelDJData.add(buttonSwitchToSchedule, BorderLayout.NORTH);
		
		// Make this all visible
		frame = new JFrame(WINDOW_NAME);
		frame.setContentPane(panelSchedule);
		frame.setSize(WINDOW_WIDTH_SCHEDULE, WINDOW_HEIGHT_SCHEDULE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * change the view to the schedule
	 */
	public void switchToSchedule()
	{
		frame.setVisible(false);
		frame.setContentPane(panelSchedule);
		frame.setSize(WINDOW_WIDTH_SCHEDULE, WINDOW_HEIGHT_SCHEDULE);
		frame.setVisible(true);
	}
	
	/**
	 * change the view to DJ stats
	 */
	public void switchToStats()
	{
		frame.setVisible(false);
		frame.setContentPane(panelDJData);
		frame.setSize(WINDOW_WIDTH_DJ, WINDOW_HEIGHT_DJ);
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
