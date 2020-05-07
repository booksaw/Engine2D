package main.java.com.booksaw.editor.mouse;

import java.awt.event.MouseEvent;

/**
 * This is used to track what activity the mouse is doing
 * 
 * @author booksaw
 *
 */
public enum MouseFunction {

	/**
	 * This means that anything goes, buttons and actions are all registered
	 * correctly
	 */
	GENERAL(new MouseEventFunction() {

		@Override
		public void run(MouseEvent e, MouseEventType type) {
			// do nothing
		}

	}),
	/**
	 * This is for when the user is selecting an object, if the code is not directly
	 * associated with this option, cancel events
	 */
	OBJECTSELECT(new MouseEventFunction() {

		@Override
		public void run(MouseEvent e, MouseEventType type) {
		}

	});

	public static MouseFunction activeFunction = GENERAL;

	public MouseEventFunction function;

	private MouseFunction(MouseEventFunction e) {
		function = e;
	}

}
