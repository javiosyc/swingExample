package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -111024640660768832L;
	private JButton saveButton;
	private JButton refreshButton;

	private ToolbarListener toolbarListener;

	public Toolbar() {
		
		//Get rid of the border if you want the toolbar draggable.
		setBorder(BorderFactory.createEtchedBorder());
		
		//setFloatable(false);
		
		saveButton = new JButton();
		saveButton.setIcon(Utils.createIcon("/images/Save16.gif"));
		saveButton.setToolTipText("Save");

		refreshButton = new JButton();
		refreshButton.setIcon(Utils.createIcon("/images/Refresh16.gif"));
		refreshButton.setToolTipText("RefreshButton");

		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(saveButton);
		// addSeparator();
		add(refreshButton);

	}


	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.toolbarListener = toolbarListener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == saveButton) {
			if (toolbarListener != null) {
				toolbarListener.saveEventOccured();
			}
		} else if (clicked == refreshButton) {
			if (toolbarListener != null) {
				toolbarListener.refreshEventOccured();
			}

		}
	}
}
