package main.java.com.booksaw.editor.panels;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.objects.GameObject;

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

		tree = new JTree(objects);
		addListeners(tree);
		Logger.Log("run " + parent);

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

}
