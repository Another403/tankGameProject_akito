package coreGame.game;

import coreGame.constants;
import coreGame.resource;
import coreGame.sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class tank implements collidible{
	private float x, y;
	private boolean isReverse;
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
		this.updateHitBox((int)this.x, (int)this.y);
	}
	
	public void setY(float y) {
		this.y = y;
		this.updateHitBox((int)this.x, (int)this.y);
	}
	
	private float vx, vy;
	private float angle;
	
	private Rectangle hitbox;
	
	private int screen_x, screen_y;
	
	private float speed = 4f;
	private float rotationSpeed = 3.0f;
	
	private BufferedImage img;
	private boolean upPressed;
	private boolean downPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean shootPressed;
	
	private bullet b;
	
	ArrayList<bullet> ammo = new ArrayList<>();
	
	float shootDelay = 120f;
	float coolDown = 0f;
	float fireRate = 1f;
	
	private int hp = 100;
	private int lives = 3;
	
	private boolean isDead;
	
	public tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.angle = angle;
		this.img = img;
		
		this.isDead = false;
		this.isReverse = false;
		this.hitbox = new Rectangle((int)x, (int)y, this.img.getWidth(), this.img.getHeight());
	}

	void toggleUpPressed() {
		this.upPressed = true;
	}
	
	void toggleDownPressed() {
		this.downPressed = true;
	}
	
	void toggleLeftPressed() {
		this.leftPressed = true;
	}
	
	void toggleRightPressed() {
		this.rightPressed = true;
	}
	
	void toggleShootPressed() {
		this.shootPressed = true;
	}
	
	void unToggleUpPressed() {
		this.upPressed = false;
	}
	
	void unToggleDownPressed() {
		this.downPressed = false;
	}
	
	void unToggleLeftPressed() {
		this.leftPressed = false;
	}
	
	void unToggleRightPressed() {
		this.rightPressed = false;
	}
	
	void unToggleShootPressed() {
		this.shootPressed = false;
	}

	public  boolean isIntersect(Rectangle rect1, Rectangle rect2) {
       
        if (rect1.x >= rect2.x + rect2.width || 
            rect2.x >= rect1.x + rect1.width || 
            rect1.y >= rect2.y + rect2.height || 
            rect2.y >= rect1.y + rect1.height) {
            return false;
        }
        return true;
    }
	public  boolean isIntersectRight(Rectangle rect1, Rectangle rect2) {

        if (rect1.x +rect1.width < rect2.x + rect2.width && 
            rect1.x +rect1.width > rect2.x ) {
            return true;
        }
        return false;
    }
	public  boolean isIntersectLeft(Rectangle rect1, Rectangle rect2) {
	       
        if (rect1.x +rect1.width > rect2.x + rect2.width && 
            rect1.x  < rect2.x + rect2.width ) {
            return true;
        }
        return false;
    }
	public  boolean isIntersectBelow(Rectangle rect1, Rectangle rect2) {
	    
        if (rect1.y +rect1.height > rect2.y  && 
            rect1.y  +rect1.height< rect2.y + rect2.height ) {
            return true;
        }
        return false;
    }
	public  boolean isIntersectUp(Rectangle rect1, Rectangle rect2) {
	    
        if (rect1.y +rect1.height > rect2.y +rect2.height && 
            rect1.y  < rect2.y + rect2.height ) {
            return true;
        }
        return false;
    }
	
	void update(tank other) {
		
		if (this.downPressed) {
			
			
			this.moveBackwards();
			this.isReverse = true;
		}
		
		if (this.upPressed) {

			this.moveForwards();
			this.isReverse = false;
		}
		if (this.leftPressed) {
			if(angle<-360) {
				angle=-360+angle*(angle/360);
			}
			this.rotateLeft();
		}
		if (this.rightPressed) {
			if(angle>360) {
				angle=360-angle*(angle/360);
			}
			this.rotateRight();
		}
		
		if (this.shootPressed && this.coolDown >= this.shootDelay) {
			this.coolDown = 0;
			b = new bullet(x, y, angle, resource.getImage("bullet"));
			this.ammo.add(b);
			(new sound(resource.getClip("bullet"))).playSound();
		}
		
		if (this.getHitBox().intersects(other.getHitBox())) {
			handleCollision(other);
		}
		this.coolDown += this.fireRate;
		this.ammo.forEach(b -> b.update());
		this.updateHitBox((int)x, (int)y);
		this.shootOther(other);
	}
	public void setSpeed(float speed) {
		this.speed=speed;
	}
	public float getSpeed() {
		return speed;
	}
	@Override
	public void handleCollision(collidible with) {
		if (with instanceof wall) {
			if(isReverse) this.moveForwards();
			else {
				this.moveBackwards();
			}
			
			if(this.leftPressed &&this.downPressed)speed=0;
			else if(this.leftPressed &&this.upPressed)speed=0;
			else if(this.rightPressed &&this.downPressed)speed=0;
			else if(this.rightPressed &&this.upPressed)speed=0;
			
		}
		
		if (with instanceof tank) {
			if (isReverse) this.moveForwards();
			else this.moveBackwards();
		}
	}
	public void shootOther(collidible other) {
		for (int i = 0; i < this.ammo.size(); i++) {
			bullet b = this.ammo.get(i);
			
			if (b.getHitBox().intersects(other.getHitBox())) {
				b.handleCollision(other);
				this.ammo.remove(b);
			}
		}
	}
	
	private void updateHitBox(int x, int y) {
		this.hitbox.x = x;
		this.hitbox.y = y;
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	private void rotateLeft() { this.angle -= this.rotationSpeed; }
	private void rotateRight() { this.angle += this.rotationSpeed; }
	
	public void moveBackwards() {
		vx = Math.round(this.speed * Math.cos(Math.toRadians(angle)));
		vy = Math.round(this.speed * Math.sin(Math.toRadians(angle)));
		x -= vx;
		y -= vy;
		
		this.checkBorder();
	}
	
	public void moveForwards() {
		vx = Math.round(this.speed * Math.cos(Math.toRadians(angle)));
		vy = Math.round(this.speed * Math.sin(Math.toRadians(angle)));
		x += vx;
		y += vy;
		
		this.checkBorder();
	}
	
	private void checkBorder() {
		if (x < 30) x = 30;
		if (x >= constants.MAP_WIDTH - 88)
			x = constants.MAP_WIDTH - 88;
		
		if (y < 40) y = 40;
		if (y >= constants.MAP_HEIGHT - 80)
			y = constants.MAP_HEIGHT - 80;
		
		this.hitbox.setLocation((int)this.x, (int)this.y);
		check_screen();
	}
	
	public ArrayList<bullet> getAmmo() {
		return this.ammo;
	}
	
	public void getShot() {
		this.hp -= 11;
		
		if (this.hp <= 0) {
			this.resetHp();
			this.lives--;
		}
		
		if (this.lives <= 0)
			this.isDead = true;
	}
	
	public void addLife() { this.lives += 1; }
	public void addSpeed() { this.speed += 2f; }
	public void resetHp() { this.hp = 100; }
	public void slowRotate() { this.rotationSpeed -= 0.25; }
	
	
	void drawImage(Graphics g) {
		AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
		rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
		Graphics2D g2d = (Graphics2D)g;
		
		if (b != null) {
			b.drawImage(g2d);
			this.ammo.forEach(b -> b.drawImage(g2d));
		}
		
		//health bar
		g2d.drawImage(this.img, rotation, null);
		g2d.setColor(Color.RED);
		
		if (this.hp >= 70)
			g2d.setColor(Color.GREEN);
		else if (this.hp >= 40)
			g2d.setColor(Color.ORANGE);
		else 
			g2d.setColor(Color.RED);
		
		g2d.drawRect((int)x - 25, (int)y - 30, 100, 25);
		g2d.fillRect((int)x - 25, (int)y - 30, this.hp, 25);
		
		//lives bar
		for (int i = 0; i < this.lives; i++) {
			g2d.drawOval((int)(x - 25) + i * 20, (int)y + 55, 15, 15);
			g2d.fillOval((int)(x - 25) + i * 20, (int)y + 55, 15, 15);
		}

		

	}
	
	@Override
	public Rectangle getHitBox() {
        return this.hitbox.getBounds();
	}

	
	public int getScreen_x() {
		return this.screen_x;
	}
	
	public int getScreen_y() {
		return this.screen_y;
	}
	
	public void check_screen() {
		this.screen_x = (int)this.getX() - constants.GAME_SCREEN_WIDTH / 4;
		this.screen_y = (int)this.getY() - constants.GAME_SCREEN_HEIGHT / 2;
		
		if (this.screen_x < 0) screen_x = 0;
		
		if (this.screen_y < 0) screen_y = 0;
		
		if (this.screen_x > constants.MAP_WIDTH - constants.GAME_SCREEN_WIDTH / 2)
            this.screen_x = constants.MAP_WIDTH - constants.GAME_SCREEN_WIDTH / 2;

        if (this.screen_y > constants.MAP_HEIGHT - constants.GAME_SCREEN_HEIGHT)
            this.screen_y = constants.MAP_HEIGHT - constants.GAME_SCREEN_HEIGHT;
	}
	
	@Override
	public boolean isVisible() {
		return this.lives > 0;
	}
	
	@Override
	public String toString() {
		return "x = " + x + ", y = " + y + ", angle = " + angle;
	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isReverse() {
		return isReverse;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}
	
}