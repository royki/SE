package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Simulation.Simulator;

public class Launcher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Launcher() {

		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("Home");
		file.setMnemonic(KeyEvent.VK_F);

		JMenu filePlay = new JMenu("Play");
		filePlay.setMnemonic(KeyEvent.VK_N);

		JMenuItem cpu = new JMenuItem("1 Player vs Computer (Hard)");
		cpu.setMnemonic(KeyEvent.VK_C);
		cpu.setToolTipText("Play against Computer");
		cpu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		cpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Simulator game1p=new Simulator();
				game1p.game(1);
			}
		});
		
		JMenuItem easycpu = new JMenuItem("1 Player vs Computer (Easy)");
		easycpu.setMnemonic(KeyEvent.VK_C);
		easycpu.setToolTipText("Play against Computer");
		easycpu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		easycpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Simulator game1p=new Simulator();
				game1p.game(0);
			}
		});
		JMenuItem auto = new JMenuItem("Computer vs Computer");
		auto.setMnemonic(KeyEvent.VK_C);
		auto.setToolTipText("Computer against Computer: Demo");
		auto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		auto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Simulator game2p=new Simulator();
				game2p.game(2);
			}
		});
		
		JMenuItem player = new JMenuItem("1 Player vs 2 player");
		player.setMnemonic(KeyEvent.VK_C);
		player.setToolTipText("Play against Computer");
		player.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		player.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Simulator game2p=new Simulator();
				game2p.game(3);
			}
		});
;
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.setMnemonic(KeyEvent.VK_C);
		fileExit.setToolTipText("Exit application");
		fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		filePlay.add(cpu);
		filePlay.add(easycpu);
		filePlay.add(player);
		filePlay.add(auto);
		file.add(filePlay);
		file.addSeparator();
		file.add(fileExit);

		menubar.add(file);

		setJMenuBar(menubar);

		setTitle("Snooker");
		setSize(360, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new PicturePanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}

	@SuppressWarnings("serial")
	private class PicturePanel extends JPanel implements ActionListener {
		//BufferedImage img;
		Image img=new ImageIcon("img/snooker.jpg").getImage();

		public PicturePanel() {
			//img = getImage("img/snooker.jpg");
			this.setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
			
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

		private BufferedImage getImage(String filename) {
			try {
				InputStream in = getClass().getResourceAsStream(filename);
				return ImageIO.read(in);
			} catch (IOException e) {
				System.out.println("The image was not loaded.");
				System.exit(1);
			}
			return null;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
