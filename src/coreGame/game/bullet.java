package coreGame.game;

import coreGame.resource;
import coreGame.constants;
import coreGame.sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class bullet implements collidible {
	private float x, y;
	private float angle;
	
	private float R = 6;
	
	private Rectangle hitbox;
	private BufferedImage img;
	private boolean visible;
	
	public bullet(float x, float y, float angle, BufferedImage img) {
		this.x =x-R/2+20;
		this.y = y-R/2+20;
		this.angle = angle;
		this.img = img;
		
		this.visible = true;
		this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
	}
	
	void setX(float x) { this.x = x; }
	void setY(float y) { this.y = y; }
	
	void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	void update() { this.moveForwards(); }
	
	private void moveForwards() {
		x += Math.round(R * Math.cos(Math.toRadians(angle)));
		y += Math.round(R * Math.sin(Math.toRadians(angle)));
		checkBorders();
	}
	
	private void checkBorders() {
		if (x < 30 || x > constants.MAP_WIDTH - 88)
			this.visible = false;
		if (y < 30 || y > constants.MAP_HEIGHT - 88)
			this.visible = false;
		
		this.updateHitBox((int)x, (int)y);
	}
	
	private void updateHitBox(int x, int y) {
		this.hitbox.x = x;
		this.hitbox.y = y;
	}
	
	@Override
	public String toString() {
		return "x = " + x + ",  y = " + y + ", angle =" + angle;
	}
	
	void drawImage(Graphics g) {
		if(visible) {
			AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
			rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(this.img, rotation, null);
		}
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	@Override
	public Rectangle getHitBox() {
		return this.hitbox.getBounds();
	}
	
	@Override
	public void handleCollision(collidible with) {
		if (with instanceof cactus) {
			((cactus) with).setVisible(false);
			this.visible = false;
		}
		
		if (with instanceof wall) {
			this.visible = false;
		}
		
		if (with instanceof tank) {
			((tank) with).getShot();
			(new sound(resource.getClip("shoot_tank"))).playSound();
			this.visible = false;
		}
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}
}