package main.java.com.booksaw.editor.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.com.booksaw.Engine2D.logging.Logger;
import main.java.com.booksaw.Engine2D.modifiers.Modifier;
import main.java.com.booksaw.editor.Constants;
import main.java.com.booksaw.editor.SelectionManager;

public class ObjectModifierPanel extends Panel implements KeyListener, ActionListener, ChangeListener {

	public static ObjectModifierPanel modifierPanel;

	public ObjectModifierPanel(Panel parent) {
		super(parent);
		modifierPanel = this;
	}

	private transient JPanel table;

	@Override
	protected void createPanel(JPanel panel) {
		border = false;

		panel.setBackground(Constants.componentBackground);
		update();
	}

	public void update() {
		panel.removeAll();
		if (SelectionManager.getSelection() == null) {
			JLabel label = new JLabel("Select an option");
			label.setForeground(Color.white);
			panel.add(label);
			panel.setForeground(Color.white);
			return;
		}
		table = new JPanel(new GridLayout(0, 1));
		table.setBackground(Constants.componentBackground);
		for (Entry<String, Modifier> modifier : SelectionManager.getSelection().getModifiers().entrySet()) {
			JPanel container = new JPanel(new GridLayout(1, 2));
			container.setBackground(Constants.componentBackground);
			container.setBorder(
					new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(new Insets(2, 2, 2, 2))));

			// setting up the label
			JLabel label = new JLabel(modifier.getValue().getDescription());
			label.setForeground(Color.WHITE);
			container.add(label);

			// setting up the text area for entering information

			JComponent component = modifier.getValue().getComponent(this);

			component.addKeyListener(this);
			container.add(component);
			table.add(container);
		}
		JPanel wrapper = new JPanel(new GridLayout());
		wrapper.setBounds(0, 0, panel.getWidth(), 25 * SelectionManager.getSelection().getModifiers().size());
		wrapper.add(table);
		panel.add(wrapper);
		panel.setLayout(null);
		panel.validate();
		panel.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		SelectionManager.getSelection().getModifier(e.getComponent().getName())
				.handleInput((JComponent) (e.getComponent()));
		SelectionManager.getSelection().reset();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SelectionManager.getSelection().getModifier(((JComponent) e.getSource()).getName())
				.handleInput((JComponent) (((JComponent) e.getSource())));
		SelectionManager.getSelection().reset();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Logger.Log("running");
		SelectionManager.getSelection().getModifier(((JComponent) e.getSource()).getName())
				.handleInput((JComponent) (((JComponent) e.getSource())));
		SelectionManager.getSelection().reset();
	}

}
