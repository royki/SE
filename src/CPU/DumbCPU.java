package CPU;

import ball.AbstractBall;

public class DumbCPU {
	AbstractBall white;
	AbstractBall[] red;

	public DumbCPU(AbstractBall aim, AbstractBall[] ball) {
		this.white = aim;
		this.red = ball;
		white.speed = 1;
		white.direction = make_random_hit();

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
	public double getSpeed() {
		return (white.speed > 1.0) ? 1.0 : white.speed;
	}

	public double getDirection() {
		return white.direction;
	}
}
