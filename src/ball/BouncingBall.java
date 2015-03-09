package ball;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.geom.Point2D;


public class BouncingBall extends AbstractBall {

	private double height;
	private double width;
	private double friction;
	
	public BouncingBall(Point2D point2d, double speed, double direction,
			boolean exist, Color color, double height, double width,double friction,double diameter) {
		super(point2d, direction, direction, exist, color,diameter);
		this.height = height;
		this.width = width;
		this.friction=friction;
	}
	
	@Override
	public void move() {
		boundary_check();
		score();
		if (exist == true) {
			if (speed <= friction)
				speed = 0.0;
			else
				speed -= friction;
			position.setLocation(position.getX()+speed * cos(direction), position.getY()+speed * sin(direction));
		}
	}

	private void score() {
		final double vision = 1.35;
		if (exist == true) {
			if (position.getX() < vision * diameter
					&& position.getY() < vision * diameter)
				exist = false;
			else if (position.getX() < vision * diameter
					&& position.getY() > width - vision * diameter)
				exist = false;
			else if (position.getX() > height - vision * diameter
					&& position.getY() > width - vision * diameter)
				exist = false;
			else if (position.getX() > height - vision * diameter
					&& position.getY() < vision * diameter)
				exist = false;
			if (exist == false)
				speed = 0.0;
		}
		periodicity();
	}

	private void boundary_check() {
		final double wall=1.1;
		if (exist == true) {
			if (position.getX() < wall*diameter) {
				direction = PI - direction;
				position.setLocation(wall*diameter, position.getY());
			}
			if (position.getX() > height - wall*diameter) {
				position.setLocation(height - wall*diameter, position.getY());
				direction = PI - direction;
			}
			if (position.getY() > width - wall*diameter) {
				position.setLocation(position.getX(), width - wall*diameter);
				direction = 2 * PI - direction;
			}
			if (position.getY() < wall*diameter) {
				position.setLocation(position.getX(), wall*diameter);
				direction = 2 * PI - direction;
			}
		}
		periodicity();
	}

	private void periodicity() {
		if (exist == true) {
			if (speed < 0) {
				speed *= -1;
				direction += PI;
			}
			if (direction < 0)
				direction += 2 * PI;
			if (direction > 2 * PI)
				direction -= 2 * PI;
		}
	}
}
