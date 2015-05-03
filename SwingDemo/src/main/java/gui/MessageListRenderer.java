package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Message;

public class MessageListRenderer implements ListCellRenderer {

	private JPanel panel;
	private JLabel label;
	private Color selectedColor;
	private Color normalColor;

	public MessageListRenderer() {
		panel = new JPanel();
		label = new JLabel();

		label.setFont(Utils.createFont("/fonts/test.ttf").deriveFont(
				Font.PLAIN, 18));
		selectedColor = new Color(210, 210, 255);
		normalColor = Color.white;

		label.setIcon(Utils.createIcon("/images/Infomation24.gif"));

		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		panel.add(label);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		Message message = (Message) value;
		label.setText(message.getTitle());
		panel.setBackground(cellHasFocus ? selectedColor : normalColor);
		return panel;
	}

}
