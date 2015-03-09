package Simulation;

import ball.AbstractBall;

public class KeepPreviousState {
	double temp_aim_x;
	double temp_aim_y;
	double[] temp_ball_x;
	double[] temp_ball_y;
	boolean[] temp_ball_exist;
	public KeepPreviousState(AbstractBall aim, AbstractBall[] ball){
		this.temp_aim_x = aim.position.getX();
		this.temp_aim_y = aim.position.getY();
		this.temp_ball_x = new double[10];
		this.temp_ball_y = new double[10];
		this.temp_ball_exist = new boolean[10];
		for (int i = 0; i < 10; i++) {
			this.temp_ball_x[i] = ball[i].position.getX();
			this.temp_ball_y[i] = ball[i].position.getY();
			this.temp_ball_exist[i] = ball[i].exist;
		}
	}
	public void RestoreState(AbstractBall aim, AbstractBall[] ball){
		aim.position.setLocation(temp_aim_x, temp_aim_y);
		for (int i = 0; i < 10; i++) {
			ball[i].position.setLocation(temp_ball_x[i],
					temp_ball_y[i]);
			ball[i].exist = temp_ball_exist[i];
		}
	}
}
