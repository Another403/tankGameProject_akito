package coreGame.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class wall implements collidible {
	protected float x, y;
	protected BufferedImage img;
	
	protected boolean visible;
	
	protected Rectangle hitbox;
	
	public wall(float y, float x, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
		
		this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
		this.visible = true;
	}
	
	@Override
	public Rectangle getHitBox() {
		return this.hitbox.getBounds();
	}
	
	@Override
	public String toString() {
		return "Wall-> x: " + x + " y";
	}
	
	void drawImage(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(img, (int)x, (int)y, null);
	}
	
	@Override
	public void handleCollision(collidible with) {
		//
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}
}