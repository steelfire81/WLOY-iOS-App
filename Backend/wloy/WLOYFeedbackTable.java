package wloy;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class WLOYFeedbackTable extends JTable {

	/**
	 * handles data stored in the table; only change is that cells
	 * are not editable
	 */
	private class WLOYFeedbackTableModel extends DefaultTableModel {
		
		// METHODS
		/**
		 * initializes a WLOYFeedbackTableModel
		 */
		public WLOYFeedbackTableModel()
		{
			super(new Object[0][0], WLOYFeedbackTable.FEEDBACK_COLUMN_NAMES);
		}
		
		/**
		 * says whether or not a cell is editable
		 * 
		 * @return <b>false</b> always
		 */
		@Override
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	}
	
	
	/**
	 * handles coloring of table's feedback cells
	 */
	private class WLOYFeedbackCellRenderer extends DefaultTableCellRenderer {
		
		/**
		 * gets the feedback cell and colors it appropriately
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
		{
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			if(label.getText().equals(WLOYFeedbackTable.FEEDBACK_POSITIVE))
				label.setBackground(WLOYFeedbackTable.FEEDBACK_POSITIVE_COLOR);
			else if(label.getText().equals(WLOYFeedbackTable.FEEDBACK_NEGATIVE))
				label.setBackground(WLOYFeedbackTable.FEEDBACK_NEGATIVE_COLOR);
			return label;
		}
	}
	
	
	// CONSTANTS
	private static final int COLUMN_FEEDBACK = 4;
	private static final String[] FEEDBACK_COLUMN_NAMES = {"TITLE", "ARTIST", "SHOW", "DJ", "FEEDBACK"};
	private static final String FEEDBACK_POSITIVE = "POSITIVE";
	private static final Color FEEDBACK_POSITIVE_COLOR = Color.GREEN;
	private static final String FEEDBACK_NEGATIVE = "NEGATIVE";
	private static final Color FEEDBACK_NEGATIVE_COLOR = Color.RED;
	
	// DATA MEMBERS
	private WLOYFeedbackTableModel model;
	
	// METHODS
	/**
	 * initializes a feedback table
	 */
	public WLOYFeedbackTable()
	{
		model = new WLOYFeedbackTableModel();
		setModel(model);
		getColumnModel().getColumn(COLUMN_FEEDBACK).setCellRenderer(new WLOYFeedbackCellRenderer());
	}
	
	/**
	 * adds feedback to the table
	 * 
	 * @param feedback feedback to be added to the table
	 */
	public void addFeedback(WLOYFeedback feedback)
	{
		String type;
		if(feedback.isPositive())
			type = FEEDBACK_POSITIVE;
		else
			type = FEEDBACK_NEGATIVE;
		
		Object[] newRow = {feedback.getTitle(), feedback.getArtist(), feedback.getShow(), feedback.getDJ(), type};
		model.addRow(newRow);
	}
}
