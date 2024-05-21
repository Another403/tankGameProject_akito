package coreGame.menu;

import coreGame.launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class startMenuPanel extends JPanel {
	
	private BufferedImage background;
	private JButton start;
	private JButton exit;
	private launcher lf;
	
	public startMenuPanel(launcher lf) {
		this.lf = lf;
		
		try {
			background = ImageIO.read(this.getClass().getClassLoader().getResource("resources/images/start.png"));
		} catch (IOException e) {
			System.out.println("Can't read menu background");
			e.printStackTrace();
			System.exit(-2);
		}
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		
		start = new JButton("Start");
		start.setFont(new Font("Courier New", Font.BOLD, 24));
		start.setBounds(150, 300, 150, 50);
		start.addActionListener((actionEvent -> {
			this.lf.setFrame("game");
		}));
		
		exit = new JButton("Start");
		exit.setSize(new Dimension(200, 100));
		exit.setFont(new Font("Courier New", Font.BOLD, 24));
		exit.setBounds(150, 400, 150, 50);
		exit.addActionListener((actionEvent -> {
			this.lf.closeGame();
		}));
		
		this.add(start);
		this.add(exit);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, null);
    }
}