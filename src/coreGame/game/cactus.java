package coreGame.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class cactus extends wall {
	public cactus(float y, float x, BufferedImage img) {
		super(y, x, img);
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
}