package coreGame.menu;

import coreGame.launcher;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class endGamePanel extends JPanel {
	private BufferedImage background;
	private JButton start;
	private JButton exit;
	private launcher lf;
	
	public endGamePanel(launcher lf) {
		this.lf = lf;
		
		String winner = "gameEnd.png"; //tank2end
		
		if (lf.getWinner())
			winner = "gameEnd.png"; //tank1end
		
		try {
			background = ImageIO.read(this.getClass().getClassLoader().getResource("resources/images/" + winner));
		} catch (IOException e) {
			System.out.println("Error: no such background detected");
			e.printStackTrace();
			System.exit(-3);
		}
		
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		
		start = new JButton("Restart Game");
		start.setFont(new Font("Courier New", Font.BOLD, 24));
		start.setBounds(150, 300, 250, 50);
		start.addActionListener((actionEvent -> {
			this.lf.setFrame("game");
		}));
		
		exit = new JButton("Exit");
		exit.setFont(new Font("Courier New", Font.BOLD, 24));
		exit.setBounds(150, 400, 250, 50);
		exit.addActionListener((actionEvent -> {
			this.lf.closeGame();
		}));
		
		this.add(start);
		this.add(exit);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }
}