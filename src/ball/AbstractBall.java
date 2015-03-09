package ball;

import java.awt.Color;
import java.awt.geom.Point2D;

public abstract class AbstractBall {
	protected double diameter;
	public Point2D position;
	public double speed;
	public double direction;
	public boolean exist;
	public Color color;

	public AbstractBall(Point2D position, double speed, double direction,
			boolean exist, Color color, double diameter) {
		setPosition(position);
		setSpeed(speed);
		setDirection(direction);
		setExist(exist);
		setColor(color);
		this.diameter = diameter;
	}

	public abstract void move();

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getDiameter() {
		return diameter;
	}

}
