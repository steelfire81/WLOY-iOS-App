package fakeclient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FakeClientWindow {

	// CONSTANTS
	private static final String BUTTON_CONNECT_TEXT = "Connect";
	private static final String BUTTON_NEGATIVE_TEXT = "Send Negative Feedback";
	private static final String BUTTON_POSITIVE_TEXT = "Send Positive Feedback";
	private static final String BUTTON_REQUEST_TEXT = "Send Request";
	private static final String LABEL_ARTIST = "Artist";
	private static final String LABEL_DJ = "DJ";
	private static final String LABEL_FEEDBACK = "FEEDBACK MESSAGE";
	private static final String LABEL_IP = "IP:";
	private static final String LABEL_PORT = "PORT:";
	private static final String LABEL_REQUEST = "REQUEST MESSAGE";
	private static final String LABEL_SHOW = "Show";
	private static final String LABEL_TITLE = "Title";
	private static final int MAIN_PANEL_ROWS = 7;
	private static final String WINDOW_NAME = "Fake WLOY App Client";
	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 200;
	
	// WINDOW ELEMENTS
	JButton buttonConnect;
	JButton buttonPositive;
	JButton buttonNegative;
	JButton buttonRequest;
	JTextField fieldIP;
	JTextField fieldPort;
	JTextField fieldFeedbackTitle;
	JTextField fieldFeedbackArtist;
	JTextField fieldFeedbackShow;
	JTextField fieldFeedbackDJ;
	JTextField fieldRequestTitle;
	JTextField fieldRequestArtist;
	
	/**
	 * initialize a fake client window
	 */
	private FakeClientWindow()
	{
		// Initialize engine
		FakeClientEngine engine = new FakeClientEngine(this);
		
		// Initialize connection panel
		JPanel panelConnection = new JPanel(new GridLayout(1, 5));
		JLabel labelIP = new JLabel(LABEL_IP);
		fieldIP = new JTextField();
		JLabel labelPort = new JLabel(LABEL_PORT);
		fieldPort = new JTextField();
		buttonConnect = new JButton(BUTTON_CONNECT_TEXT);
		buttonConnect.addActionListener(engine);
		panelConnection.add(labelIP);
		panelConnection.add(fieldIP);
		panelConnection.add(labelPort);
		panelConnection.add(fieldPort);
		panelConnection.add(buttonConnect);
		
		// Initialize feedback panel
		JPanel panelFeedback = new JPanel(new GridLayout(MAIN_PANEL_ROWS, 1));
		JLabel labelFeedback = new JLabel(LABEL_FEEDBACK);
		JPanel panelFeedbackTitle = new JPanel(new GridLayout(1, 2));
		JLabel labelFeedbackTitle = new JLabel(LABEL_TITLE);
		fieldFeedbackTitle = new JTextField();
		panelFeedbackTitle.add(labelFeedbackTitle);
		panelFeedbackTitle.add(fieldFeedbackTitle);
		JPanel panelFeedbackArtist = new JPanel(new GridLayout(1, 2));
		JLabel labelFeedbackArtist = new JLabel(LABEL_ARTIST);
		fieldFeedbackArtist = new JTextField();
		panelFeedbackArtist.add(labelFeedbackArtist);
		panelFeedbackArtist.add(fieldFeedbackArtist);
		JPanel panelFeedbackShow = new JPanel(new GridLayout(1, 2));
		JLabel labelFeedbackShow = new JLabel(LABEL_SHOW);
		fieldFeedbackShow = new JTextField();
		panelFeedbackShow.add(labelFeedbackShow);
		panelFeedbackShow.add(fieldFeedbackShow);
		JPanel panelFeedbackDJ = new JPanel(new GridLayout(1, 2));
		JLabel labelFeedbackDJ = new JLabel(LABEL_DJ);
		fieldFeedbackDJ = new JTextField();
		panelFeedbackDJ.add(labelFeedbackDJ);
		panelFeedbackDJ.add(fieldFeedbackDJ);
		buttonPositive = new JButton(BUTTON_POSITIVE_TEXT);
		buttonPositive.addActionListener(engine);
		buttonNegative = new JButton(BUTTON_NEGATIVE_TEXT);
		buttonNegative.addActionListener(engine);
		panelFeedback.add(labelFeedback);
		panelFeedback.add(panelFeedbackTitle);
		panelFeedback.add(panelFeedbackArtist);
		panelFeedback.add(panelFeedbackShow);
		panelFeedback.add(panelFeedbackDJ);
		panelFeedback.add(buttonPositive);
		panelFeedback.add(buttonNegative);
		
		// Initialize request panel
		JPanel panelRequest = new JPanel(new GridLayout(MAIN_PANEL_ROWS, 1));
		JLabel labelRequest = new JLabel(LABEL_REQUEST);
		JPanel panelRequestTitle = new JPanel(new GridLayout(1, 2));
		JLabel labelRequestTitle = new JLabel(LABEL_TITLE);
		fieldRequestTitle = new JTextField();
		panelRequestTitle.add(labelRequestTitle);
		panelRequestTitle.add(fieldRequestTitle);
		JPanel panelRequestArtist = new JPanel(new GridLayout(1, 2));
		JLabel labelRequestArtist = new JLabel(LABEL_ARTIST);
		fieldRequestArtist = new JTextField();
		panelRequestArtist.add(labelRequestArtist);
		panelRequestArtist.add(fieldRequestArtist);
		buttonRequest = new JButton(BUTTON_REQUEST_TEXT);
		buttonRequest.addActionListener(engine);
		panelRequest.add(labelRequest);
		panelRequest.add(panelRequestTitle);
		panelRequest.add(panelRequestArtist);
		panelRequest.add(buttonRequest);
		
		// Initialize main panel
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.add(panelConnection, BorderLayout.NORTH);
		JPanel panelCentral = new JPanel(new GridLayout(1, 2));
		panelCentral.add(panelFeedback);
		panelCentral.add(panelRequest);
		panelMain.add(panelCentral, BorderLayout.CENTER);
		engine.initialize();
		
		// Make everything visible
		JFrame frame = new JFrame(WINDOW_NAME);
		frame.setContentPane(panelMain);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new FakeClientWindow();
	}
}
