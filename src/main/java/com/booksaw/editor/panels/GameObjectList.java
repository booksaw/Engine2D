package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import main.java.com.booksaw.Engine2D.objects.GameObject;
import main.java.com.booksaw.editor.Constants;

/**
 * This class displays all available objects and manages the selection
 * 
 * @author booksaw
 *
 */
public class GameObjectList extends Panel {

	public GameObjectList(Panel parent) {
		super(parent);
	}

	JTree tree;

	@Override
	protected void createPanel(JPanel panel) {
		border = false;
		DefaultMutableTreeNode objects = new DefaultMutableTreeNode("Objects");

		for (GameObject object : GamePanel.manager.level.getGameObjects()) {
			objects.add(new DefaultMutableTreeNode(object));
		}
		panel.setBackground(Constants.mainBackground);
		tree = new JTree(objects);
		tree.setCellRenderer(new CellRenderer());
		tree.setBackground(Constants.componentBackground);
		tree.setOpaque(false);
		tree.setForeground(Color.WHITE);
		addListeners(tree);

		panel.setLayout(new GridLayout());
		panel.add(tree);
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
}
