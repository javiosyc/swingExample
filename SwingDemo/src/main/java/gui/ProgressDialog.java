package gui;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {

	private static final long serialVersionUID = -7848719395171401070L;
	private static final int DEFAULT_MAXIMUM = 10;
	private static final String MESSAGE = "retrieving messages...";
	private static final String PROGRESS_MESSAGE_FORMATE = "%d%% complete";
	private static final int COMPLETE_LATENCY = 500;

	private JButton cancelButton;
	private JProgressBar progressBar;

	private ProgressDialogListener listener;

	public ProgressDialog(Window parent, String title) {
		super(parent, ModalityType.APPLICATION_MODAL);

		cancelButton = new JButton("cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString(MESSAGE);
		progressBar.setMaximum(DEFAULT_MAXIMUM);

		// progressBar.setIndeterminate(true);

		setLayout(new FlowLayout());

		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;
		progressBar.setPreferredSize(size);

		add(progressBar);
		add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listener != null) {
					listener.progressDialogCancelled();
				}
			}
		});

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (listener != null) {
					listener.progressDialogCancelled();
				}
			}
		});

		pack();

		setLocationRelativeTo(parent);
	}

	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}

	public void setValue(int value) {
		int progress = 100 * value / progressBar.getMaximum();

		progressBar
				.setString(String.format(PROGRESS_MESSAGE_FORMATE, progress));
		progressBar.setValue(value);
	}

	@Override
	public void setVisible(final boolean visible) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (visible) {
					progressBar.setValue(0);
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					Container test = getParent();					
				} else {
					try {
						Thread.sleep(COMPLETE_LATENCY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setCursor(Cursor.getDefaultCursor());
				}
				ProgressDialog.super.setVisible(visible);
			}
		});
	}

	public void setListener(ProgressDialogListener listener) {
		this.listener = listener;
	}

}
