package coreGame.game.powerup;

import coreGame.game.powerups;
import coreGame.game.tank;

import java.awt.image.BufferedImage;

public class slowRotate extends powerups {
	public slowRotate(float y, float x, BufferedImage img) {
		super(y, x, img);
	}
	
	@Override
	public void doPowerUp(tank t) {
		t.slowRotate();
	}
	
	@Override
	public void setVisible(boolean b) {
		this.visible = b;
	}
}