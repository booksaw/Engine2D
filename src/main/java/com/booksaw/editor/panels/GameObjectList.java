package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.SelectionManager;

/**
 * This class displays all available objects and manages the selection, only a
 * single list can be included per editor
 * 
 * @author booksaw
 *
 */
public class GameObjectList extends Panel implements TreeSelectionListener {

	public static GameObjectList gameObjectList;

	public GameObjectList(Panel parent) {
		super(parent);
		gameObjectList = this;
	}

	JTree tree;

	@Override
	protected void createPanel(JPanel panel) {

		panel.setBackground(Constants.componentBackground);
		panel.setLayout(new GridLayout());
		update();
	}

	public void update() {
		panel.removeAll();
		border = false;
		DefaultMutableTreeNode objects = new DefaultMutableTreeNode("Objects");

		for (GameObject object : GamePanel.manager.level.getGameObjects()) {
			objects.add(new DefaultMutableTreeNode(object));
		}
		tree = new JTree(objects);
		tree.setCellRenderer(new CellRenderer());
		tree.setBackground(Constants.componentBackground);
		tree.setOpaque(false);
		tree.addTreeSelectionListener(this);
		tree.setForeground(Color.WHITE);
		addListeners(tree);
		panel.add(tree);
		panel.validate();

	}

	public void setObject(GameObject object) {
		tree.setSelectionPath(tree.getNextMatch(object.toString(), 0, Position.Bias.Forward));
	}

	@Override
	public void setParent(Panel parent) {
		super.setParent(parent);
		// used to add the tree
		if (tree != null) {
			addListeners(tree);
		}
	}

	public class CellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 8242926831962919033L;

		@Override
		public Color getBackgroundNonSelectionColor() {
			return (null);
		}

		@Override
		public Color getBackgroundSelectionColor() {
			return Color.black;
		}

		@Override
		public Color getBackground() {
			return (null);
		}

		@Override
		public Color getForeground() {
			return Color.white;
		}

		@Override
		public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
				final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
			final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			this.setText(value.toString());
			return ret;
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// used to select the rendered object
		Object obj = (e.getOldLeadSelectionPath() != null) ? e.getOldLeadSelectionPath().getLastPathComponent() : null;
		if (obj != null && obj instanceof DefaultMutableTreeNode) {
			Object object = ((DefaultMutableTreeNode) obj).getUserObject();
			if (object != null && object instanceof GameObject) {
				SelectionManager.clearSelection();
			}
		}

		obj = (e.getNewLeadSelectionPath() != null) ? e.getNewLeadSelectionPath().getLastPathComponent() : null;
		if (obj != null && obj instanceof DefaultMutableTreeNode) {
			Object object = ((DefaultMutableTreeNode) obj).getUserObject();
			if (object != null && object instanceof GameObject) {
				SelectionManager.clearSelection();
				SelectionManager.select((GameObject) object, false);
				GamePanel.manager.pause(false);
			}
		}
	}
}
