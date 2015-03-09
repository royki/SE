package CPU;

import java.awt.Color;
import java.awt.geom.Point2D;

import ball.AbstractBall;
import ball.BouncingBall;

public class SmartCPU {
	double height;
	double width;
	double diameter;
	AbstractBall white;
	AbstractBall[] red;
	boolean found;
	double friction;
	double elasticity;

	public SmartCPU(AbstractBall aim, AbstractBall[] ball, double friction,
			double elasticity, double height, double width, double diameter) {
		this.white = aim;
		this.red = ball;
		found = false;
		this.friction = friction;
		this.elasticity = elasticity * 0.9;
		this.diameter = diameter;
		this.height = height;
		this.width = width;
		int k = ((int) (Math.random() * 10)) % 10;
		for (int i = 0; i < 10; i++) {
			if (red[(i + k + 10) % 10].exist == false)
				continue;
			if (direct_shot((i + k + 10) % 10) == true) {
				if (white.speed < 0) {
					white.speed *= -1;
					white.direction += Math.PI;
				}
				if (white.speed < 0.9 && white.speed > 0.5)
					white.speed *= 1.2;
				else
					white.speed *= 1.3;
				found = true;
				break;
			}
		}
		if (found == false) {
			white.speed = 1;
			white.direction = make_random_hit();
		}
	}

	double make_random_hit() {
		int k = ((int) (Math.random() * 10)) % 10;
		for (int i = 0; i < 10; i++) {
			if (red[(i + k + 10) % 10].exist == false)
				continue;
			return (Math.atan2(
					red[(i + k + 10) % 10].position.getY()
							- white.position.getY(),
					red[(i + k + 10) % 10].position.getX()
							- white.position.getX()));
		}
		return Math.random() * 2 * Math.PI;
	}

	boolean direct_shot(int x) {
		if (is_shot_possible(x) == true) {
			if (is_pouch1_possible(x) == true) {
				if (make_shot(1, x) == true)
					return true;
			}
			if (is_pouch2_possible(x) == true) {
				if (make_shot(2, x) == true)
					return true;
			}
			if (is_pouch3_possible(x) == true) {
				if (make_shot(3, x) == true)
					return true;
			}
			if (is_pouch4_possible(x) == true) {
				if (make_shot(4, x) == true)
					return true;
			}
		}
		return false;
	}

	private boolean make_shot(int i, int x) {
		switch (i) {
		case 1:
			double angle_of_collision = Math.atan2(
					(diameter) - red[x].position.getY(), (diameter)
							- red[x].position.getX());
			double collision_point_x = red[x].position.getX() + diameter
					* Math.cos(angle_of_collision + Math.PI);
			double collision_point_y = red[x].position.getY() + diameter
					* Math.sin(angle_of_collision + Math.PI);
			double ball_speed_to_move = Math.sqrt(2
					* friction
					* dist(diameter, diameter, red[x].position.getX(),
							red[x].position.getY()));
			double angle_of_line = Math.atan2(collision_point_y
					- white.position.getY(),
					collision_point_x - white.position.getX());
			if (Math.cos(angle_of_line - angle_of_collision) == 0)
				return false;
			else {
				double aim_speed_at_collision = ball_speed_to_move
						/ (Math.cos(angle_of_line - angle_of_collision) * elasticity);
				white.direction = angle_of_line;
				double required_speed = Math.sqrt((Math.pow(
						aim_speed_at_collision, 2))
						+ 2
						* friction
						* Math.sqrt(Math.pow(
								(white.position.getX() - collision_point_x), 2)
								+ Math.pow(white.position.getY()
										- collision_point_y, 2)));
				if (required_speed > 1.1)
					return false;
				white.speed = required_speed;
				return true;
			}
		case 2:
			angle_of_collision = Math.atan2(
					(diameter) - red[x].position.getY(), (height - diameter)
							- red[x].position.getX())
					+ 2 * Math.PI;
			collision_point_x = red[x].position.getX() + diameter
					* Math.cos(angle_of_collision + Math.PI);
			collision_point_y = red[x].position.getY() + diameter
					* Math.sin(angle_of_collision + Math.PI);
			ball_speed_to_move = Math.sqrt(2
					* friction
					* dist(height - diameter, diameter, red[x].position.getX(),
							red[x].position.getY()));
			angle_of_line = Math.atan2(
					collision_point_y - white.position.getY(),
					collision_point_x - white.position.getX());
			if (Math.cos(angle_of_line - angle_of_collision) == 0)
				return false;
			else {
				double aim_speed_at_collision = ball_speed_to_move
						/ (Math.cos(angle_of_line - angle_of_collision) * elasticity);
				white.direction = angle_of_line;
				double required_speed = Math.sqrt((Math.pow(
						aim_speed_at_collision, 2))
						+ 2
						* friction
						* Math.sqrt(Math.pow(
								(white.position.getX() - collision_point_x), 2)
								+ Math.pow(white.position.getY()
										- collision_point_y, 2)));
				if (required_speed > 1.1)
					return false;
				white.speed = required_speed;
			}
			return true;
		case 3:
			angle_of_collision = Math.atan2((width - diameter)
					- red[x].position.getY(),
					(diameter) - red[x].position.getX());
			collision_point_x = red[x].position.getX() + diameter
					* Math.cos(angle_of_collision + Math.PI);
			collision_point_y = red[x].position.getY() + diameter
					* Math.sin(angle_of_collision + Math.PI);
			ball_speed_to_move = Math.sqrt(2
					* friction
					* dist(diameter, width - diameter, red[x].position.getX(),
							red[x].position.getY()));
			angle_of_line = Math.atan2(
					collision_point_y - white.position.getY(),
					collision_point_x - white.position.getX());
			if (Math.cos(angle_of_line - angle_of_collision) == 0)
				return false;
			else {
				double aim_speed_at_collision = ball_speed_to_move
						/ (Math.cos(angle_of_line - angle_of_collision) * elasticity);
				white.direction = angle_of_line;
				double required_speed = Math.sqrt((Math.pow(
						aim_speed_at_collision, 2))
						+ 2
						* friction
						* Math.sqrt(Math.pow(
								(white.position.getX() - collision_point_x), 2)
								+ Math.pow(white.position.getY()
										- collision_point_y, 2)));
				if (required_speed > 1.1)
					return false;
				white.speed = required_speed;
			}
			return true;
		case 4:
			angle_of_collision = Math.atan2((width - diameter)
					- red[x].position.getY(), (height - diameter)
					- red[x].position.getX());
			collision_point_x = red[x].position.getX() + diameter
					* Math.cos(angle_of_collision + Math.PI);
			collision_point_y = red[x].position.getY() + diameter
					* Math.sin(angle_of_collision + Math.PI);
			ball_speed_to_move = Math.sqrt(2
					* friction
					* dist(height - diameter, width - diameter,
							red[x].position.getX(), red[x].position.getY()));
			angle_of_line = Math.atan2(
					collision_point_y - white.position.getY(),
					collision_point_x - white.position.getX());
			if (Math.cos(angle_of_line - angle_of_collision) == 0)
				return false;
			else {
				double aim_speed_at_collision = ball_speed_to_move
						/ (Math.cos(angle_of_line - angle_of_collision) * elasticity);
				white.direction = angle_of_line;
				double required_speed = Math.sqrt((Math.pow(
						aim_speed_at_collision, 2))
						+ 2
						* friction
						* Math.sqrt(Math.pow(
								(white.position.getX() - collision_point_x), 2)
								+ Math.pow(white.position.getY()
										- collision_point_y, 2)));
				if (required_speed > 1.1)
					return false;
				white.speed = required_speed;
			}
			return true;
		default:
			return false;
		}
	}

	private double dist(double x0, double y0, double x, double y) {
		return Math.sqrt(Math.pow((x - x0), 2) + Math.pow(y - y0, 2));
	}

	private boolean is_pouch4_possible(int x) {
		AbstractBall temp_ball = new BouncingBall(new Point2D.Double(height
				- diameter, width - diameter), 0, Math.PI / 4, true,
				Color.BLACK, height, width, friction, diameter);
		while (dist(temp_ball, red[x]) > diameter) {
			double angle = (Math.atan2(temp_ball.position.getY()
					- red[x].position.getY(), temp_ball.position.getX()
					- red[x].position.getX()));
			temp_ball.position.setLocation(
					temp_ball.position.getX() - Math.cos(angle),
					temp_ball.position.getY() - Math.sin(angle));
			for (int i = 0; i < 10; i++) {
				if (red[i].exist == false || i == x)
					continue;
				if (dist(temp_ball, red[i]) < 1.5 * diameter)
					return false;
			}
		}
		double hit_angle = Math.atan2(
				red[x].position.getY() - white.position.getY(),
				red[x].position.getX() - white.position.getX())
				- Math.atan2(width - diameter - red[x].position.getY(), height
						- diameter - red[x].position.getX());
		if (Math.cos(hit_angle) > 0.2)
			return true;
		else
			return false;
	}

	private boolean is_pouch3_possible(int x) {
		AbstractBall temp_ball = new BouncingBall(new Point2D.Double(diameter,
				width - diameter), 0, 3 * Math.PI / 4, true, Color.BLACK,
				height, width, friction, diameter);
		while (dist(temp_ball, red[x]) > diameter) {
			double angle = (Math.atan2(temp_ball.position.getY()
					- red[x].position.getY(), temp_ball.position.getX()
					- red[x].position.getX()));
			temp_ball.position.setLocation(
					temp_ball.position.getX() - Math.cos(angle),
					temp_ball.position.getY() - Math.sin(angle));
			for (int i = 0; i < 10; i++) {
				if (red[i].exist == false || i == x)
					continue;
				if (dist(temp_ball, red[i]) < 1.5 * diameter)
					return false;
			}
		}
		double hit_angle = Math.atan2(
				red[x].position.getY() - white.position.getY(),
				red[x].position.getX() - white.position.getX())
				- Math.atan2(width - diameter - red[x].position.getY(),
						diameter - red[x].position.getX());
		if (Math.cos(hit_angle) > 0.2)
			return true;
		else
			return false;
	}

	private boolean is_pouch2_possible(int x) {
		AbstractBall temp_ball = new BouncingBall(new Point2D.Double(height
				- diameter, diameter), 0, 3 * Math.PI / 4 + Math.PI, true,
				Color.BLACK, height, width, friction, diameter);
		while (dist(temp_ball, red[x]) > diameter) {
			double angle = (Math.atan2(temp_ball.position.getY()
					- red[x].position.getY(), temp_ball.position.getX()
					- red[x].position.getX()));
			temp_ball.position.setLocation(
					temp_ball.position.getX() - Math.cos(angle),
					temp_ball.position.getY() - Math.sin(angle));
			for (int i = 0; i < 10; i++) {
				if (red[i].exist == false || i == x)
					continue;
				if (dist(temp_ball, red[i]) < 1.5 * diameter)
					return false;
			}
		}
		double hit_angle = Math.atan2(
				red[x].position.getY() - white.position.getY(),
				red[x].position.getX() - white.position.getX())
				- Math.atan2(diameter - red[x].position.getY(), height
						- diameter - red[x].position.getX());
		if (Math.cos(hit_angle) > 0.2)
			return true;
		else
			return false;
	}

	private boolean is_pouch1_possible(int x) {
		AbstractBall temp_ball = new BouncingBall(new Point2D.Double(diameter,
				diameter), 0, Math.PI / 4 + Math.PI, true, Color.BLACK, height,
				width, friction, diameter);
		while (dist(temp_ball, red[x]) > diameter) {
			double angle = (Math.atan2(temp_ball.position.getY()
					- red[x].position.getY(), temp_ball.position.getX()
					- red[x].position.getX()));
			temp_ball.position.setLocation(
					temp_ball.position.getX() - Math.cos(angle),
					temp_ball.position.getY() - Math.sin(angle));
			for (int i = 0; i < 10; i++) {
				if (red[i].exist == false || i == x)
					continue;
				if (dist(temp_ball, red[i]) < 1.5 * diameter)
					return false;
			}
		}
		double hit_angle = Math.atan2(
				red[x].position.getY() - white.position.getY(),
				red[x].position.getX() - white.position.getX())
				- Math.atan2(diameter - red[x].position.getY(), diameter
						- red[x].position.getX());
		if (Math.cos(hit_angle) > 0.2)
			return true;
		else
			return false;
	}

	boolean is_shot_possible(int x) {
		AbstractBall temp_aim = new BouncingBall(new Point2D.Double(
				white.position.getX(), white.position.getY()), white.speed,
				white.direction, white.exist, white.color, height, width,
				friction, diameter);
		while (dist(temp_aim, red[x]) > diameter) {
			double angle = (Math.atan2(temp_aim.position.getY()
					- red[x].position.getY(), temp_aim.position.getX()
					- red[x].position.getX()));
			temp_aim.position.setLocation(
					temp_aim.position.getX() - Math.cos(angle),
					temp_aim.position.getY() - Math.sin(angle));
			for (int i = 0; i < 10; i++) {
				if (red[i].exist == false || i == x)
					continue;
				if (dist(temp_aim, red[i]) < 1.1 * diameter) {
					return false;
				}
			}
		}
		return true;
	}

	double dist(AbstractBall temp_aim, AbstractBall red) {
		return Math
				.sqrt(((temp_aim.position.getX() - red.position.getX()) * (temp_aim.position
						.getX() - red.position.getX()))
						+ ((temp_aim.position.getY() - red.position.getY()) * (temp_aim.position
								.getY() - red.position.getY())));
	}

	public double getSpeed() {
		return (white.speed > 1.0) ? 1.0 : white.speed;
	}

	public double getDirection() {
		return white.direction;
	}
}
