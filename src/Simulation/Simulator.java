package Simulation;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import CPU.DumbCPU;
import CPU.SmartCPU;
import ball.AbstractBall;
import ball.BallFactory;

@SuppressWarnings("unused")
public class Simulator extends JPanel {
	static double input_direction;
	int option;
	private static final long serialVersionUID = 1L;
	public static final double friction = 0.0002, diameter = 20,
			elasticity = 0.9;
	public double height, width;
	static AbstractBall curr_aim;
	double cpu_score = 0, user_score = 0;
	private int mouse_X = 0;
	private int mouse_Y = 0;
	int turn = 0;
	final JLabel labshow;
	AbstractBall[] ball;
	AbstractBall aim;
	int time;
	private S_Run run;
	Image image;
	boolean flag, perform_stroke;
	int val = 0;
	JFrame f;
	public Simulator() {
		labshow=new JLabel();
	}

	public Simulator(int choice) {
		option = choice;
		//image = ImageIO.read(new File("table.PNG"));
		 image= new ImageIcon("img/table.PNG").getImage();
		width = image.getHeight(null);
		height = image.getWidth(null);
		Point2D white_position = new Point2D.Double(height * 3 / 4, width / 2);
		BallFactory factory = new BallFactory(white_position, 0, 0, true,
				Color.white, friction, height, width, diameter);
		aim = factory.createBall();
		Point2D[] red_position = new Point2D[10];
		red_position[0] = new Point2D.Double((height / 4)
				- (sqrt(3) / 2 * diameter), (width / 2) - (diameter * 3 / 2));
		red_position[1] = new Point2D.Double((height / 4)
				- (sqrt(3) / 2 * diameter), (width / 2) - (diameter * 1 / 2));
		red_position[2] = new Point2D.Double((height / 4)
				- (sqrt(3) / 2 * diameter), (width / 2) + (diameter * 1 / 2));
		red_position[3] = new Point2D.Double((height / 4)
				- (sqrt(3) / 2 * diameter), (width / 2) + (diameter * 3 / 2));
		red_position[4] = new Point2D.Double(height / 4, (width / 2)
				- (diameter));
		red_position[5] = new Point2D.Double(height / 4, (width / 2));
		red_position[6] = new Point2D.Double(height / 4, (width / 2)
				+ (diameter));
		red_position[7] = new Point2D.Double((height / 4)
				+ (sqrt(3) / 2 * diameter), (width / 2) - (diameter * 1 / 2));
		red_position[8] = new Point2D.Double((height / 4)
				+ (sqrt(3) / 2 * diameter), (width / 2) + (diameter * 1 / 2));
		red_position[9] = new Point2D.Double((height / 4)
				+ (sqrt(3) * diameter), width / 2);
		factory = new BallFactory(red_position, 0, 0, true, Color.red,
				friction, height, width, diameter);
		ball = factory.createBalls();
		setBorder(new LineBorder(Color.red));
		this.setPreferredSize(new Dimension((int) height, (int) width));
		labshow=new JLabel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(labshow);
		this.add(buttonPanel, BorderLayout.SOUTH);
		repaint();
		Simulate();
		addMouseListerner();
	}

	public void Simulate() {
		
		Thread game = new Thread() {
			public void run() {
				while (count_balls() > 0) {
					int initial_count = count_balls();
					if (turn % 2 == 0) {
						if (option == 2) {
							smartcpu();
							flag = true;
							val = 1;
						} else {
							flag = false;
							while (val == 0)
								;
							input();
						}
					} else {
						if (option == 1 || option == 2) {
							smartcpu();
							flag = true;
							val = 1;
						} else if (option == 0) {
							dumbcpu();
							flag = true;
							val = 1;
						} else {
							flag = false;
							while (val == 0)
								;
							input();
						}
					}
					turn++;
					KeepPreviousState data = new KeepPreviousState(aim, ball);
					while (aim.speed > friction || ball_speed() > friction) {
						aim.move();
						for (int i = 0; i < ball.length; i++)
							ball[i].move();
						collision();
						repaint();
						try {
							Thread.sleep(3);
						} catch (InterruptedException ex) {
						}
					}
					Check_if_shot_made(initial_count);
					Check_if_bad_shot_made(data);
					repaint();
					update_score(initial_count);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
					}
					val = 0;
				}
				EndMessage();
			}
		};
		game.start();
	}

	private void EndMessage() {
		switch (option) {
		case 1:
			JOptionPane.showMessageDialog(null, "Player 1:" + user_score
					+ "\n CPU:" + cpu_score, "Message",
					JOptionPane.ERROR_MESSAGE);
			break;
		default:
			JOptionPane.showMessageDialog(null, "Player 1:" + user_score
					+ "\n Player 2:" + cpu_score, "Message",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void update_score(int initial_count) {
		if (turn % 2 == 0)
			user_score += initial_count - count_balls();
		else
			cpu_score += initial_count - count_balls();
	}

	private void Check_if_bad_shot_made(KeepPreviousState data) {
		if (aim.exist == false) {
			data.RestoreState(aim, ball);
			repaint();
			aim.exist = true;
		}
	}

	private void Check_if_shot_made(int initial_count) {
		if (count_balls() != initial_count) {
			if (aim.exist == true)
				turn--;
		}
	}

	private void dumbcpu() {
		Point2D original_position = new Point2D.Double(aim.position.getX(),
				aim.position.getY());
		DumbCPU auto = new DumbCPU(aim, ball);
		aim.speed = auto.getSpeed();
		aim.direction = auto.getDirection();
		aim.position = original_position;
	}

	private void smartcpu() {
		Point2D original_position = new Point2D.Double(aim.position.getX(),
				aim.position.getY());
		SmartCPU auto = new SmartCPU(aim, ball, friction, elasticity, height,
				width, diameter);
		aim.speed = auto.getSpeed();
		aim.direction = auto.getDirection();
		aim.position = original_position;
	}

	private double ball_speed() {
		double max = 0;
		for (int i = 0; i < 10; i++) {
			if (ball[i].exist == false)
				continue;
			if (max < ball[i].speed)
				max = ball[i].speed;
		}
		return max;
	}

	private void addMouseListerner() {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mouse_X = e.getX();
				mouse_Y = e.getY();
				repaint();
			}

			public void mousePressed(MouseEvent e) {
				mouse_X = e.getX();
				mouse_Y = e.getY();
				if (val == 0) {
					run = new S_Run();
					run.start();
				}
			}

			@SuppressWarnings("deprecation")
			public void mouseReleased(MouseEvent e) {
					run.stop();
					run = null;
					aim.speed=sqrt(pow((double)(time%100)/100,2));
					val = 1;
					Simulate();
				}

		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				mouse_X = e.getX();
				mouse_Y = e.getY();
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				mouse_X = e.getX();
				mouse_Y = e.getY();
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(image, 0, 0, null);
		double angle = Math.atan2(mouse_Y - aim.position.getY(), mouse_X
				- aim.position.getX());
		if (val == 0)
			g2.drawLine((int) aim.position.getX(), (int) aim.position.getY(),
					(int) (aim.position.getX() + height * cos(angle)),
					(int) (aim.position.getY() + height * sin(angle)));

		for (int i = 0; i < 10; i++) {
			if (ball[i].exist == false)
				continue;
			g.setColor(ball[i].color);
			g2.fill(new Ellipse2D.Double(
					(ball[i].position.getX() - (diameter / 2)),
					(ball[i].position.getY() - (diameter / 2)), (diameter),
					(diameter)));
		}
		if (aim.exist == true) {
			g.setColor(aim.color);
			g2.fill(new Ellipse2D.Double(
					(aim.position.getX() - (diameter / 2)), (aim.position
							.getY() - (diameter / 2)), (diameter), (diameter)));
		}
	}

	int count_balls() {
		int count = 0;
		for (int i = 0; i < ball.length; i++)
			if (ball[i].exist == true)
				count++;
		return count;
	}

	void input() {
		aim.speed=sqrt(pow((double)(time%100)/100,2));
		aim.direction = Math.atan2(mouse_Y - aim.position.getY(), mouse_X
				- aim.position.getX());
		time = 0;
	}

	void collision() {
		Collision collision = new Collision(aim, ball, elasticity, diameter,
				friction);
		collision.aim_ball_collision();
		collision.ball_ball_collision();
		repaint();
	}

	public void game(final int choice) {
		option = choice;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Snooker");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new Simulator(choice));
				frame.pack();
				frame.setVisible(true);
				f=frame;
			}
		});
	}

	class S_Run extends Thread {
		@Override
		public void run() {
			time = 0;
			while (true) {
				time++;
				labshow.setText(time % 100 + "");
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}

		}
	}

}
