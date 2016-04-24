package wloy;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WLOYSchedulePanel extends JPanel {
	
	// CONSTANTS - Buttons
	private static final String BUTTON_DATA_ENTRY_TEXT = "SAVE";
	private static final Color BUTTON_EMPTY_COLOR = Color.WHITE;
	private static final String BUTTON_EMPTY_TEXT = "---";
	private static final Color BUTTON_EMPTY_TEXT_COLOR = Color.BLACK;
	private static final Color BUTTON_SHOW_COLOR = Color.BLACK;
	private static final Color BUTTON_SHOW_TEXT_COLOR = Color.WHITE;
	
	// CONSTANTS - Grid Dimensions
	private static final int GRID_ROWS = 25;
	private static final int GRID_COLUMNS = 8;
	
	// CONSTANTS - Labels
	private static final String LABEL_HOUR = "HOUR";
	private static final String LABEL_SUNDAY = "SUNDAY";
	private static final String LABEL_MONDAY = "MONDAY";
	private static final String LABEL_TUESDAY = "TUESDAY";
	private static final String LABEL_WEDNESDAY = "WEDNESDAY";
	private static final String LABEL_THURSDAY = "THURSDAY";
	private static final String LABEL_FRIDAY = "FRIDAY";
	private static final String LABEL_SATURDAY = "SATURDAY";
	private static final String LABEL_AM = "A.M.";
	private static final String LABEL_PM = "P.M.";
	
	// CONSTANTS - Other
	private static final int NOT_EDITING = -1;
	
	// DATA MEMBERS
	private JPanel[][] panelGrid;
	private JTextField[][] fieldGrid;
	private JPanel panelDataEntry;
	private JTextField fieldDataEntry;
	private JButton buttonDataEntry;
	private int editingRow;
	private int editingColumn;
	
	// METHODS
	public WLOYSchedulePanel()
	{
		super(new GridLayout(GRID_ROWS, GRID_COLUMNS));
		
		// Initialize panel grid
		panelGrid = new JPanel[GRID_ROWS][GRID_COLUMNS];
		for(int row = 0; row < panelGrid.length; row++)
			for(int col = 0; col < panelGrid[row].length; col++)
			{
				panelGrid[row][col] = new JPanel(new GridLayout(1, 1));
				add(panelGrid[row][col]);
			}
		
		// Add top level labels
		JTextField labelHour = new JTextField(LABEL_HOUR);
		labelHour.setEditable(false);
		JTextField labelSunday = new JTextField(LABEL_SUNDAY);
		labelSunday.setEditable(false);
		JTextField labelMonday = new JTextField(LABEL_MONDAY);
		labelMonday.setEditable(false);
		JTextField labelTuesday = new JTextField(LABEL_TUESDAY);
		labelTuesday.setEditable(false);
		JTextField labelWednesday = new JTextField(LABEL_WEDNESDAY);
		labelWednesday.setEditable(false);
		JTextField labelThursday = new JTextField(LABEL_THURSDAY);
		labelThursday.setEditable(false);
		JTextField labelFriday = new JTextField(LABEL_FRIDAY);
		labelFriday.setEditable(false);
		JTextField labelSaturday = new JTextField(LABEL_SATURDAY);
		labelSaturday.setEditable(false);
		add(labelHour, 0, 0);
		add(labelSunday, 0, 1);
		add(labelMonday, 0, 2);
		add(labelTuesday, 0, 3);
		add(labelWednesday, 0, 4);
		add(labelThursday, 0, 5);
		add(labelFriday, 0, 6);
		add(labelSaturday, 0, 7);
		
		// Add hour labels on first column
		int row = 1;
		for(int ampm = 0; ampm < 2; ampm++)
			for(int hour = 0; hour < 12; hour++)
			{
				int printedHour = hour;
				if(printedHour == 0)
					printedHour = 12;
				
				String ampmText;
				if(ampm == 0)
					ampmText = LABEL_AM;
				else
					ampmText = LABEL_PM;
				
				JTextField labelHourRow = new JTextField(printedHour + " " + ampmText);
				labelHourRow.setEditable(false);
				add(labelHourRow, row, 0);
				row++;
			}
		
		// Add textfields for each timeslot
		fieldGrid = new JTextField[GRID_ROWS][GRID_COLUMNS];
		for(row = 1; row < GRID_ROWS; row++)
			for(int col = 1; col < GRID_COLUMNS; col++)
			{
				fieldGrid[row - 1][col - 1] = new JTextField();
				add(fieldGrid[row - 1][col - 1], row, col);
			}
	}
	
	/**
	 * add a component to a particular spot on the grid
	 * 
	 * @param component component to be added
	 * @param row row in the grid to add the component
	 * @param col column in the grid to add the component
	 */
	public void add(Component component, int row, int col)
	{
		//panelGrid[row][col].removeAll();
		panelGrid[row][col].add(component);
	}
}
