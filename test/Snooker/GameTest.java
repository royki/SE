package Snooker;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.junit.Test;

import Simulation.Simulator;
import ball.AbstractBall;
import ball.BouncingBall;

public class GameTest {
	Simulator environment = mock(Simulator.class);
	final double w = 800;
	final double h = 400;

	@Test
	public void testpouch() throws Exception {
		AbstractBall ball = new BouncingBall(new Point2D.Double(w / 2, w / 2),
				1, Math.PI + Math.PI / 4, true, Color.black, h, w, 0.0002, 20);
		while (ball.speed > 0.1)
			ball.move();
		assertTrue("Did not go to pouch", ball.exist == false);
	}

	@Test
	public void testGame() throws Exception {
		environment=new Simulator(2);
		environment=new Simulator(1);
		environment=new Simulator(0);
	}
}
