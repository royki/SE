package Simulation;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import ball.AbstractBall;

public class Collision {
	AbstractBall aim;
	AbstractBall[] ball;
	double elasticity;
	double diameter;
	double friction;
	public Collision(AbstractBall aim, AbstractBall[] ball, double elasticity,
			double diameter,double friction) {
		this.aim=aim;
		this.ball=ball;
		this.elasticity=elasticity;
		this.diameter=diameter;
		this.friction=friction;
	}

	void ball_ball_collision() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == i || ball[j].exist == false || ball[i].exist == false)
					continue;
				if (dist(ball[i], ball[j]) < diameter - 0.1) {
					if (ball[j].speed < friction)
						ball[j].direction = ball[i].direction;
					if (ball[i].speed < friction)
						ball[i].direction = ball[j].direction;
					double angle_of_collision = (Math.atan2(
							ball[j].position.getY() - ball[i].position.getY(),
							ball[j].position.getX() - ball[i].position.getX()));
					double original_aim_speed = ball[j].speed;
					double original_ball_speed = ball[i].speed;
					ball[j].speed = vector_component_add((elasticity
							* original_ball_speed * cos(ball[i].direction
							- angle_of_collision))
							+ (1 - elasticity)
							* (original_aim_speed * cos(ball[j].direction
									- angle_of_collision)), original_aim_speed
							* sin(ball[j].direction - angle_of_collision));
					ball[i].speed = vector_component_add((elasticity
							* original_aim_speed * cos(ball[j].direction
							- angle_of_collision))
							+ (1 - elasticity)
							* (original_ball_speed * cos(ball[i].direction
									- angle_of_collision)), original_ball_speed
							* sin(ball[i].direction - angle_of_collision));
					if (ball[j].speed != 0)
						ball[j].direction = angle_of_collision
								+ Math.atan2(
										original_aim_speed
												* sin(ball[j].direction
														- angle_of_collision),
										(elasticity * original_ball_speed * cos(ball[i].direction
												- angle_of_collision))
												+ (1 - elasticity)
												* (original_aim_speed * cos(ball[j].direction
														- angle_of_collision)));
					if (ball[i].speed != 0)
						ball[i].direction = angle_of_collision
								+ Math.atan2(
										original_ball_speed
												* sin(ball[i].direction
														- angle_of_collision),
										(elasticity * original_aim_speed * cos(ball[j].direction
												- angle_of_collision))
												+ (1 - elasticity)
												* (original_ball_speed * cos(ball[i].direction
														- angle_of_collision)));
					ball_adjust(i, j);
				}
			}
		}
	}

	void ball_adjust(int i, int j) {
		if (dist(ball[i], ball[j]) < diameter) {
			double centre_x = (ball[i].position.getX() + ball[j].position
					.getX()) / 2;
			double centre_y = (ball[i].position.getY() + ball[j].position
					.getY()) / 2;
			double angle_of_line = Math.atan2(ball[i].position.getY()
					- ball[j].position.getY(), ball[i].position.getX()
					- ball[j].position.getX());
			double x1 = centre_x + ((diameter + 0.1) * cos(angle_of_line) / 2);
			double x2 = centre_x - ((diameter + 0.1) * cos(angle_of_line) / 2);
			double y1 = centre_y + ((diameter + 0.1) * sin(angle_of_line) / 2);
			double y2 = centre_y - ((diameter + 0.1) * sin(angle_of_line) / 2);
			if (pow(ball[i].position.getX() - x1, 2)
					+ pow(ball[i].position.getY() - y1, 2) > pow(
					ball[i].position.getX() - x2, 2)
					+ pow(ball[i].position.getY() - y2, 2)) {
				ball[i].position.setLocation(x2, y2);
				ball[j].position.setLocation(x1, y1);
			} else {
				ball[i].position.setLocation(x1, y1);
				ball[j].position.setLocation(x2, y2);
			}
		}
	}

	void aim_ball_collision() {
		for (int i = 0; i < 10; i++) {
			if (dist(aim, ball[i]) < diameter - 0.1 && ball[i].exist == true) {
				if (aim.speed < friction)
					aim.direction = ball[i].direction;
				if (ball[i].speed < friction)
					ball[i].direction = aim.direction;
				double angle_of_collision = (Math.atan2(aim.position.getY()
						- ball[i].position.getY(), aim.position.getX()
						- ball[i].position.getX()));
				double original_aim_speed = aim.speed;
				double original_ball_speed = ball[i].speed;
				aim.speed = vector_component_add((elasticity
						* original_ball_speed * cos(ball[i].direction
						- angle_of_collision))
						+ (1 - elasticity)
						* (original_aim_speed * cos(aim.direction
								- angle_of_collision)), original_aim_speed
						* sin(aim.direction - angle_of_collision));
				ball[i].speed = vector_component_add((elasticity
						* original_aim_speed * cos(aim.direction
						- angle_of_collision))
						+ (1 - elasticity)
						* (original_ball_speed * cos(ball[i].direction
								- angle_of_collision)), original_ball_speed
						* sin(ball[i].direction - angle_of_collision));
				if (aim.speed != 0)
					aim.direction = angle_of_collision
							+ Math.atan2(
									original_aim_speed
											* sin(aim.direction
													- angle_of_collision),
									(elasticity * original_ball_speed * cos(ball[i].direction
											- angle_of_collision))
											+ (1 - elasticity)
											* (original_aim_speed * cos(aim.direction
													- angle_of_collision)));
				if (ball[i].speed != 0)
					ball[i].direction = angle_of_collision
							+ Math.atan2(
									original_ball_speed
											* sin(ball[i].direction
													- angle_of_collision),
									(elasticity * original_aim_speed * cos(aim.direction
											- angle_of_collision))
											+ (1 - elasticity)
											* (original_ball_speed * cos(ball[i].direction
													- angle_of_collision)));
				ball_adjust(i);
			}
		}
	}

	void ball_adjust(int i) {
		if (dist(ball[i], aim) < diameter) {
			double centre_x = (ball[i].position.getX() + aim.position.getX()) / 2;
			double centre_y = (ball[i].position.getY() + aim.position.getY()) / 2;
			double angle_of_line = Math.atan2(ball[i].position.getY()
					- aim.position.getY(), ball[i].position.getX()
					- aim.position.getX());
			double x1 = centre_x + ((diameter + 0.1) * cos(angle_of_line) / 2);
			double x2 = centre_x - ((diameter + 0.1) * cos(angle_of_line) / 2);
			double y1 = centre_y + ((diameter + 0.1) * sin(angle_of_line) / 2);
			double y2 = centre_y - ((diameter + 0.1) * sin(angle_of_line) / 2);
			if (pow(ball[i].position.getX() - x1, 2)
					+ pow(ball[i].position.getY() - y1, 2) > pow(
					ball[i].position.getX() - x2, 2)
					+ pow(ball[i].position.getY() - y2, 2)) {
				ball[i].position.setLocation(x2, y2);
				aim.position.setLocation(x1, y1);
			} else {
				ball[i].position.setLocation(x1, y1);
				aim.position.setLocation(x2, y2);
			}
		}
	}

	private double vector_component_add(double d, double e) {
		return sqrt(d * d + e * e);
	}

	double dist(AbstractBall point1, AbstractBall point2) {
		return sqrt(pow(point1.position.getX() - point2.position.getX(), 2)
				+ pow(point1.position.getY() - point2.position.getY(), 2));
	}

}
