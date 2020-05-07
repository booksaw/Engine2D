package main.java.com.booksaw.editor.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Used to detect and manage calls when a mouse event is registered
 * 
 * @author booksaw
 *
 */
public class MouseListener implements MouseMotionListener, java.awt.event.MouseListener {

	public static MouseListener listener;

	public MouseListener() {
		listener = this;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.DRAGGED);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.MOVED);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.CLICKED);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.RELEASED);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.ENTERED);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		MouseFunction.activeFunction.function.run(e, MouseEventType.EXITED);
	}

}
