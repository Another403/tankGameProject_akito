package coreGame;

//import coreGame.game.gameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class resource {
	public static Map<String, BufferedImage> images = new HashMap<>();
	public static Map<String, Clip> clips = new HashMap<>();
	
	public static BufferedImage getImage(String key) {
		return resource.images.get(key);
	}
	
	public static Clip getClip(String key) {
		return resource.clips.get(key);
	}
}