package gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class ServerTreeCellEditor extends AbstractCellEditor implements
		TreeCellEditor {

	private static final long serialVersionUID = -5789096590943001310L;
	private ServerTreeCellRenderer renderer;
	private JCheckBox checkBox;
	private ServerInfo info;

	public ServerTreeCellEditor() {
		renderer = new ServerTreeCellRenderer();
	}

	@Override
	public Object getCellEditorValue() {

		info.setChecked(checkBox.isSelected());
		return info;
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {

		Component component = renderer.getTreeCellRendererComponent(tree,
				value, isSelected, expanded, leaf, row, true);

		if (leaf) {

			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
			info = (ServerInfo) treeNode.getUserObject();
			
			
			checkBox = (JCheckBox) component;

			ItemListener itemListener = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();
					checkBox.removeItemListener(this);
				}
			};
			checkBox.addItemListener(itemListener);
		}

		return component;
	}

	@Override
	public boolean isCellEditable(EventObject event) {
		// When you click on a node, first isCellEditable() will be called.
		// And, if that return false, then that's the end of a method and other
		// two methods won't be called.

		// If it return true, to say that the cell is editable.
		// What happens next is that the tree will call,
		// getTreeCellEditorComponent
		// and that's got to return some kind of component that allow you to
		// edit, like a checkbox for example, and getTreeCellEditorComponent()
		// then has to fire a editing stopped event (fireEditingStopped())
		// And that's what triggered the tree to getCellEditorValue()
		// And that's called retrieve the user object value for that tree node
		// Which is in this case is going to be a ServerInfo object

		if (!(event instanceof MouseEvent))
			return false;

		MouseEvent mouseEvent = (MouseEvent) event;

		JTree tree = (JTree) event.getSource();

		TreePath path = tree.getPathForLocation(mouseEvent.getX(),
				mouseEvent.getY());

		if (path == null)
			return false;

		Object lastComponent = path.getLastPathComponent();

		if (lastComponent == null)
			return false;

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent;

		return treeNode.isLeaf();
	}

}
