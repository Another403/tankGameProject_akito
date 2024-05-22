package coreGame;

import coreGame.menu.*;
import coreGame.game.gameWorld;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class launcher {
	private JPanel mainPanel;
	private JPanel startPanel;
	private gameWorld gamePanel;
	private JPanel endPanel;
	private JFrame jf;
	
	private CardLayout cl;
	
	private boolean isWinner;
	
	public boolean getWinner() {
		return this.isWinner;
	}
	
	public void setWinner(boolean w) {
		this.isWinner = w;
		
		this.endPanel = new endGamePanel(this);
		this.mainPanel.add(endPanel, "end");
	}
	
	public launcher() {
		this.jf = new JFrame();
		this.jf.setTitle("2P Tank Wars");
		this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.isWinner = true;
	}
	
	public void setFrame(String type) {
		this.jf.setVisible(false);
		switch (type) {
			case "start":
				this.jf.setSize(constants.MENU_SCREEN_WIDTH, constants.MENU_SCREEN_HEIGHT);
				break;
			case "game":
				this.gamePanel.InitializeGame();
				this.jf.setSize(constants.GAME_SCREEN_WIDTH, constants.GAME_SCREEN_HEIGHT);
				(new Thread(this.gamePanel)).start();
				break;
			case "end":
				this.jf.setSize(constants.MENU_SCREEN_WIDTH, constants.MENU_SCREEN_HEIGHT);
				break;
		}
		this.cl.show(mainPanel, type);
		this.jf.setVisible(true);
	}
	
	private void initUIComponents() {
		this.mainPanel = new JPanel();
		this.startPanel = new startMenuPanel(this);
		this.gamePanel = new gameWorld(this);
		
		this.cl = new CardLayout();
		this.mainPanel.setLayout(cl);
		
		this.mainPanel.add(startPanel, "start");
		this.mainPanel.add(gamePanel, "game");
		
		this.jf.add(mainPanel);
		this.jf.setResizable(false);
		this.setFrame("start");
	}
	
	public JFrame getJf() {
		return jf;
	}
	
	public void closeGame() {
		this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		(new launcher()).initUIComponents();
	}
}