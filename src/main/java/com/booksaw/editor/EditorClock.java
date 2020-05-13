package main.java.com.booksaw.editor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import main.java.com.booksaw.Engine2D.Clock;
import main.java.com.booksaw.Engine2D.gameUpdates.Updateable;
import main.java.com.booksaw.editor.panels.GamePanel;

/**
 * Used for any components which need updating when the level is not updating
 * (for example moving the editor camera)
 * 
 * @author booksaw
 *
 */
public class EditorClock extends Clock {

	List<Updateable> updates = new ArrayList<>();

	public void addUpdatable(Updateable update) {
		updates.add(update);
	}

	public void removeUpdatable(Updateable update) {
		updates.remove(update);
	}

	public EditorClock(int delay) {
		super(delay);
	}

	long previousUpdate = -1;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (GamePanel.manager == null || GamePanel.manager.isUpdating()) {
			return;
		}

		if (previousUpdate == -1) {
			previousUpdate = System.currentTimeMillis();
			return;
		}
		int difference = (int) (System.currentTimeMillis() - previousUpdate);

		if (difference > 5) {
			difference = 1;
		}
		for (Updateable update : updates) {
			update.update(difference);
		}
		previousUpdate = System.currentTimeMillis();
	}

}
