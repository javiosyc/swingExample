package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import model.EmploymentCategory;

public class EmploymentCategoryEditor extends AbstractCellEditor implements
		TableCellEditor {

	private JComboBox<EmploymentCategory> combox;

	public EmploymentCategoryEditor() {
		combox = new JComboBox<EmploymentCategory>(EmploymentCategory.values());
	}

	@Override
	public Object getCellEditorValue() {
		return combox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		combox.setSelectedItem(value);
		
		combox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
				
			}
		});
		return combox;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		return true;
	}

}
