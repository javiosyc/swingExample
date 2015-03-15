import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {
	private JButton helloButton;
	private JButton goodbyeButton;

	private StringListener textListener;

	public Toolbar() {

		setBorder(BorderFactory.createEtchedBorder());
		helloButton = new JButton("hello");
		goodbyeButton = new JButton("goodbye");

		helloButton.addActionListener(this);
		goodbyeButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(helloButton);
		add(goodbyeButton);

	}

	public void setStringListener(StringListener stringListener) {
		this.textListener = stringListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == helloButton) {
			if (textListener != null) {
				textListener.textEmitted("Hello\n");
			}
		} else {
			if (textListener != null) {
				textListener.textEmitted("goodbye\n");
			}

		}
	}
}
