package gui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Message;
import controller.MessageServer;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 3141060834695113186L;
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;

	private ProgressDialog progressDialog;
	private Set<Integer> selectedServers;
	private MessageServer messageServer;

	private SwingWorker<List<Message>, Integer> worker;

	public MessagePanel(JFrame parnet) {

		progressDialog = new ProgressDialog((Window) getParent(),
				"Message Downloading...");
		progressDialog.setListener(new ProgressDialogListener() {
			public void progressDialogCancelled() {
				System.out.println("cancalled...");
				if (worker != null) {
					worker.cancel(true);
				}
			}
		});

		messageServer = new MessageServer();
		selectedServers = getSelectedServer();

		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		serverTree = new JTree(createTree());

		serverTree.setCellRenderer(treeCellRenderer);

		serverTree.setEditable(true);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {

				serverTree.setEnabled(false);
				ServerInfo info = (ServerInfo) treeCellEditor
						.getCellEditorValue();

				System.out.println(info + ":" + info.getId() + ";"
						+ info.isChecked());

				int serverId = info.getId();

				if (info.isChecked()) {
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);
				}

				messageServer.setSelectedServer(selectedServers);
				retrieveMessages();
			}

			public void editingCanceled(ChangeEvent e) {

			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(serverTree), BorderLayout.CENTER);
	}

	protected void retrieveMessages() {

		progressDialog.setMaximum(messageServer.getMessageCount());
		progressDialog.setVisible(true);

		worker = new SwingWorker<List<Message>, Integer>() {

			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size() - 1);
				progressDialog.setValue(retrieved);
			}

			@Override
			protected void done() {
				serverTree.setEnabled(true);
				progressDialog.setVisible(false);
				if (isCancelled())
					return;
				try {
					List<Message> retrieveMessages = get();
					System.out.println("Retrieved " + retrieveMessages.size()
							+ " messages.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

			}

			@Override
			protected List<Message> doInBackground() throws Exception {
				List<Message> retrieveMessages = new ArrayList<Message>();
				int count = 0;
				for (Message message : messageServer) {
					if (isCancelled())
						break;
					retrieveMessages.add(message);
					count++;
					publish(count);
				}
				return retrieveMessages;
			}
		};

		worker.execute();

	}

	private Set<Integer> getSelectedServer() {
		Set<Integer> result = new TreeSet<Integer>();
		result.add(0);
		result.add(1);
		result.add(4);
		return result;
	}

	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");

		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(
				new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(
				new ServerInfo("Boston", 1, selectedServers.contains(1)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(
				new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));

		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UN");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(
				new ServerInfo("London", 3, selectedServers.contains(3)));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(
				new ServerInfo("Edinburgh", 4, selectedServers.contains(4)));

		branch2.add(server4);
		branch2.add(server5);

		top.add(branch1);
		top.add(branch2);

		return top;
	}
}

class ServerInfo {
	private String name;
	private int id;
	private boolean checked;

	public ServerInfo(String name, int id, boolean checked) {
		this.name = name;
		this.id = id;
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}