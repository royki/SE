package ball;

import java.awt.Color;
import java.awt.geom.Point2D;


public class BallFactory {
	private double speed;
	private double direction;
	private Point2D[] position;
	private Point2D single_position;
	private boolean exist;
	private Color color;
	private final double friction;
	private final double height;
	private final double width;
	private final double diameter;

	public BallFactory(Point2D[] position, double speed, double direction,
			boolean exist, Color color, double friction, double height,
			double width, double diameter) {
		this.position = position;
		this.speed = speed;
		this.direction = direction;
		this.exist = exist;
		this.color = color;
		this.friction = friction;
		this.height = height;
		this.width = width;
		this.diameter = diameter;
	}

	public BallFactory(Point2D position, double speed, double direction,
			boolean exist, Color color, double friction, double height,
			double width, double diameter) {
		this.single_position = position;
		this.speed = speed;
		this.direction = direction;
		this.exist = exist;
		this.color = color;
		this.friction = friction;
		this.height = height;
		this.width = width;
		this.diameter = diameter;
	}

	public AbstractBall[] createBalls() {
		AbstractBall[] balls = new AbstractBall[position.length];
		for (int i = 0; i < position.length; i++) {
			balls[i] = new BouncingBall(position[i], speed, direction, exist,
					color, height, width, friction, diameter);
		}
		return balls;
	}

	public AbstractBall createBall() {
		AbstractBall balls = new BouncingBall(single_position, speed,
				direction, exist, color, height, width, friction, diameter);
		return balls;
	}
}
