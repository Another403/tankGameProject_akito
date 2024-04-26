package coreGame.game;

import coreGame.resource;
import coreGame.sound;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class powerups implements collidible {
	private float x, y;
	private BufferedImage img;
	protected Rectangle hitbox;
	protected boolean visible;
	
	public powerups(float x, float y, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		this.visible = true;
		this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
	}
	
	void drawImage(Graphics g) {
		if (this.visible) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(img, (int)x, (int)y, null);
		}
	}
	
	public abstract void doPowerUp(tank t);
	
	public abstract void setVisible(boolean b);
	
	@Override
	public Rectangle getHitBox() {
		return this.hitbox.getBounds();
	}
	
	@Override
	public void handleCollision(collidible with) {
		if (with instanceof tank) {
			(new sound(resource.getClip("powerup"))).playSound();
			this.doPowerUp((tank) with);
			this.setVisible(false);
		}
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}
}