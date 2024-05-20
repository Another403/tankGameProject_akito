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
	
	public endGamePanel (launcher lf) {
		this.lf = lf;
		
		String winner = ""; //tank2end
		
		if (lf.getWinner()) {
			winner = ""; //tank1end
		}
		
		try {
			background = ImageIO.read(this.getClass().getClassLoader().getResource("resources/images/" + winner));
		} catch (IOException e) {
			System.out.println("Error: no such background detected");
			e.printStackTrace();
			System.exit(-3);
		}
	}
}