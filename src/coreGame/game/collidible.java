package coreGame.game;

import java.awt.*;

public interface collidible {
	Rectangle getHitBox();
	void handleCollision(collidible with);
	boolean isVisible();
}