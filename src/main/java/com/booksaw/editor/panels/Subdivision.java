package main.java.com.booksaw.editor.panels;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import main.java.com.booksaw.editor.mouse.MouseFunction;

/**
 * A subdivision panel used to contain 2 sub panels, useful for managing the
 * layout
 */
public class Subdivision extends Panel implements ComponentListener, MouseMotionListener {

	private Panel p1, p2;
	private double percentage = 0.5;
	private boolean horizontal = true, movable;
	private Cursor defaultCursor;

	public Subdivision(Panel p1, Panel p2, boolean hoizontal, boolean movable, Panel parent) {
		super(parent);
		this.p1 = p1;
		this.p2 = p2;
		p1.setParent(this);
		p2.setParent(this);
		this.horizontal = hoizontal;
		this.movable = movable;
	}

	@Override
	protected void createPanel(JPanel panel) {

		panel.setLayout(null);
		panel.addComponentListener(this);
		defaultCursor = panel.getCursor();
		panel.addMouseMotionListener(this);
		panel.add(p1.getPanel());
		panel.add(p2.getPanel());
		setBounds();
	}

	private void setBounds() {

		if (horizontal) {
			p1.getPanel().setBounds(0, 0, (int) (panel.getWidth() * percentage), panel.getHeight());
			p2.getPanel().setBounds((int) (panel.getWidth() * percentage), 0,
					(int) (panel.getWidth() * (1 - percentage)), panel.getHeight());
		} else {
			p1.getPanel().setBounds(0, 0, panel.getWidth(), (int) (panel.getHeight() * percentage));
			p2.getPanel().setBounds(0, (int) (panel.getHeight() * percentage), panel.getWidth(),
					(int) (panel.getHeight() * (1 - percentage)));
		}
		p1.panel.validate();
		p2.panel.validate();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		setBounds();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	boolean over = false;

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (e.getComponent() != panel) {
			Point locOn = e.getLocationOnScreen();
			Point panelP = panel.getLocationOnScreen();
			p.x = locOn.x - panelP.x;
			if (p.x < 0 || p.x > panel.getWidth()) {
				return;
			}

			p.y = locOn.y - panelP.y;
			if (p.y < 0 || p.y > panel.getHeight()) {
				return;
			}
		}

		if (over && movable) {
			if (horizontal) {
				percentage = (double) p.x / panel.getWidth();
			} else {
				percentage = (double) p.y / panel.getHeight();
			}

			if (percentage < 0.05) {
				percentage = 0.05;
			} else if (percentage > 0.95) {
				percentage = 0.95;
			}
			setBounds();

		}
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Rectangle rectangle;
		Point p = e.getPoint();
		// if it has been called by a different component
		if (e.getComponent() != panel) {
			Point locOn = e.getLocationOnScreen();
			Point panelP = panel.getLocationOnScreen();
			p.x = locOn.x - panelP.x;
			if (p.x < 0 || p.x > panel.getWidth()) {
				return;
			}

			p.y = locOn.y - panelP.y;
			if (p.y < 0 || p.y > panel.getHeight()) {
				return;
			}
		}

		if (!movable || MouseFunction.activeFunction != MouseFunction.GENERAL) {
			return;
		}
		if (horizontal) {
			rectangle = new Rectangle((int) (panel.getWidth() * percentage) - 2, 0, 4, panel.getHeight());

			if (rectangle.contains(p)) {
				panel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				over = true;
			} else {
				panel.setCursor(defaultCursor);
				over = false;
			}
		} else {
			rectangle = new Rectangle(0, (int) (panel.getHeight() * percentage) - 2, panel.getWidth(), 4);

			if (rectangle.contains(p)) {
				panel.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				over = true;
			} else {
				panel.setCursor(defaultCursor);
				over = false;
			}
		}
	}

	@Override
	public void setParent(Panel parent) {
		super.setParent(parent);
		// used to ensure all listeners required are added
		p1.setParent(this);
		p2.setParent(this);
	}

}
