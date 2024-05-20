package coreGame.game;

import coreGame.launcher;
import coreGame.constants;
import coreGame.resource;
import coreGame.sound;
import coreGame.game.powerup.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class gameWorld extends JPanel implements Runnable {
	private BufferedImage world;
	private tank t1, t2;
	private launcher lf;
	private long tick = 0;

	private sound sand;
	
	List<wall> walls = new ArrayList<wall>();
	List<powerups> Powerups = new ArrayList<powerups>();
	
	public gameWorld(launcher lf) {
		this.lf = lf;
	}
	
	@Override
	public void run() {
		try {
			this.resetGame();
			sand.run();
			
			while (true) {
				this.tick++;
				
				this.t1.update(t2);
				this.t2.update(t1);
				
				for (int i = 0; i < walls.size(); i++) {
					wall x = this.walls.get(i);
					
					if (x.getHitBox().intersects(t1.getHitBox()))
						t1.handleCollision(x);
					
					if (x.getHitBox().intersects(t2.getHitBox()))
						t2.handleCollision(x);
				}
				
				for (int i = 0; i < Powerups.size(); i++) {
					powerups t = this.Powerups.get(i);
					
					if (t.getHitBox().intersects(t1.getHitBox())) {
						t1.handleCollision(t);
						Powerups.remove(t);
					}
					
					if (t.getHitBox().intersects(t2.getHitBox())) {
						t2.handleCollision(t);
						Powerups.remove(t);
					}
				}
				
				if (this.t1.getIsDead() || this.t2.getIsDead())  {
					sand.stopSound();
					
					if (t1.getIsDead()) lf.setWinner(false);
					
					if (t2.getIsDead()) lf.setWinner(true);
					
					this.repaint();
					Thread.sleep(1000 / 144);
				}
			}
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
			System.out.println(ignored);
		}
	}
	
	public void resetGame() {
		this.tick = 0;
		this.t1.setX(300);
		this.t1.setY(300);
		this.t2.setX(800);
		this.t2.setY(800);
	}
}