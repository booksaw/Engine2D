package main.java.test.com.booksaw.platformer2D;

import java.awt.Color;
import java.awt.Graphics;

import main.java.com.booksaw.Engine2D.GameManager;
import main.java.com.booksaw.Engine2D.rendering.RenderedComponent;

/**
 * Testing the rendering of the engine
 * 
 * @author booksaw
 *
 */
public class RenderTest extends RenderedComponent {

	@Override
	public void paint(Graphics g, GameManager manager) {
		g.setColor(Color.ORANGE);
		g.fillRect(manager.camera.offsetX, manager.camera.offsetY, (int) (1000 * manager.camera.scale),
				(int) (1000 * manager.camera.scale));
	}

}
