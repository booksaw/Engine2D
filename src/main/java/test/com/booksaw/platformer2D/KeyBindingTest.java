package main.java.test.com.booksaw.platformer2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import main.java.com.booksaw.Engine2D.input.KeyMapping;
import main.java.com.booksaw.Engine2D.input.KeyboardManager;
import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

public class KeyBindingTest implements ActionListener {

	public KeyBindingTest() {
		Timer timer = new Timer(1, this);
		timer.start();

		// making a test key bind
		ArrayList<Integer> test = new ArrayList<>();
		test.add(KeyEvent.VK_W);
		KeyboardManager.keyboardManager.addKeyMapping(new KeyMapping("up", test));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (KeyboardManager.keyboardManager.isActive("up")) {
			Logger.Log(LogType.INFO, "w is pressed");

		}

	}

}
