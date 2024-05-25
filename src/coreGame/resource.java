package coreGame;

import coreGame.game.gameWorld;
import coreGame.menu.startMenuPanel;

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
	
	public static void initImages() {
        try {
            resource.images.put("tank1img", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/Tank_1.png")));
            resource.images.put("tank2img", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/Tank_2.png")));

            resource.images.put("floor", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/floor.png")));

            resource.images.put("bullet", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/bullet.png")));

            resource.images.put("wall", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/wall.png")));
            resource.images.put("cactus", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/cactus.png")));

            resource.images.put("addLife", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/addLife.png")));
            resource.images.put("addSpeed", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/addSpeed.png")));
            resource.images.put("resetHealth", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/resetHealth.png")));
            resource.images.put("slowRotate", ImageIO.read(gameWorld.class.getClassLoader().getResource("resources/images/slowRotate.png")));
            //resource.images.put("background", ImageIO.read(startMenuPanel.class.getClassLoader().getResource("resources/images/menus/start.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static void initClips() {
        try {
            AudioInputStream as;
            Clip clip;

            as = AudioSystem.getAudioInputStream(resource.class.getClassLoader().getResource("resources/sounds/bullet.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            resource.clips.put("bullet", clip);

            as = AudioSystem.getAudioInputStream(resource.class.getClassLoader().getResource("resources/sounds/sand.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            resource.clips.put("sand", clip);

            as = AudioSystem.getAudioInputStream(resource.class.getClassLoader().getResource("resources/sounds/SoundShot.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            resource.clips.put("shoot_tank", clip);

            as = AudioSystem.getAudioInputStream(resource.class.getClassLoader().getResource("resources/sounds/powerup.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            resource.clips.put("powerup", clip);
            
            as = AudioSystem.getAudioInputStream(resource.class.getClassLoader().getResource("resources/sounds/SoundInGame.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            resource.clips.put("soundingame", clip);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(-2);
        }

    }

    public static void initResources() {
        resource.initImages();
        resource.initClips();
    }
}