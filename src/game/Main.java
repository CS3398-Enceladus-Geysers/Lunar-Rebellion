package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The main driver class for the game, also a {@link KeyListener}.
 */
public class Main implements KeyListener {
	/** A list of all the scenes. */
	enum ScenesEnum {
	LEVEL, MAIN_MENU, OVERWORLD, SETTINGS, TITLE, START_MENU
	}

	/**
	 * A {@link HashSet} which represents the currently pressed keys for as long as
	 * they remain pressed.
	 */
	public static final HashSet<Integer> CURRENTLY_PRESSED_KEYS = new HashSet<Integer>();
	/**
	 * The FPS limit for this game. The main {@link Thread} sleeps until it is time
	 * for the next frame.
	 */
	public static final int FPS_LIMIT = 30;
	/** This determines how big the game is. */
	public static final Integer SIZE_FACTOR = 60;
	/** This is the dimensions for the panel which is always displayed. */
	public static final Dimension GAME_PANEL_DIMENSION = new Dimension(16 * SIZE_FACTOR, 9 * SIZE_FACTOR);
	private static final JFrame GAME_WINDOW = new JFrame("Lunar Rebellion");
	// TODO Add javadocs on everything.
	private static Player player;
	/** This variable tells us which scene we're currently in. */
	private static ScenesEnum scene;
	/**
	 * This is a Map for holding scenes, which are just JPanels which get switched
	 * from title to game, etc.
	 */
	private static final EnumMap<ScenesEnum, Scene> SCENES_MAP = new EnumMap<ScenesEnum, Scene>(ScenesEnum.class);

	private static final Scene currentScene() {
		return SCENES_MAP.get(scene);
	}

	/**
	 * @return the {@link Player} object.
	 */
	public static final Player getPlayer() {
		return player;
	}

	/**
	 * Just instantiate a Main Object here.
	 * 
	 * @param args arguments passed by the command line, which are ignored.
	 * @throws Exception if a file cannot be loaded.
	 */
	public static void main(String[] args) throws Exception {
		new Main();
	}

	/**
	 * Here's the meat of the program.
	 * 
	 * @throws Exception if a file cannot be loaded.
	 */
	public Main() throws Exception {
		for (ScenesEnum s : ScenesEnum.values()) {
			if (s != ScenesEnum.LEVEL)
				SCENES_MAP.put(s, new Scene());
		}
		constructScenes();
		transitionScene(ScenesEnum.LEVEL);
		GAME_WINDOW.addKeyListener(this);
		GAME_WINDOW.pack();
		GAME_WINDOW.setResizable(false);
		GAME_WINDOW.setLocationRelativeTo(null);
		GAME_WINDOW.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GAME_WINDOW.setVisible(true);
		long lastFrameTime = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - lastFrameTime < 1000.0 / FPS_LIMIT)
				Thread.sleep((int) (1000.0 / FPS_LIMIT - System.currentTimeMillis() + lastFrameTime));
			lastFrameTime = System.currentTimeMillis();
			currentScene().act();
		}
	}

	private final void constructScenes() throws Exception {
		// Start level construction.
		SCENES_MAP.put(ScenesEnum.LEVEL, new Scene(1.0 / 2, 2.0 / 3));
		Scene level = SCENES_MAP.get(ScenesEnum.LEVEL);
		player = new Player(level.getCameraLocation());
		level.setPlayer(player);
		level.addGameObject(player);
		Graphic healthbarGraphic = new Graphic(4.0 / 60, 3.0 / 60, 150, 50) {
			private static final long serialVersionUID = 3237106029139727237L;
			int lastHP;

			@Override
			public void act() {
				if (player.getHP() != lastHP)
					repaint();
				lastHP = player.getHP();
			}

			@Override
			public void paintComponent(Graphics g) {
				// TODO Draw something based on health.
				g.drawRect(1, 1, 150, 25);
				g.setColor(Color.red);
				g.fillRect(1, 1, player.getHP(), 25);
			}
		};
		level.addGraphic(healthbarGraphic);
		Terrain dirt1 = new Terrain(level.getCameraLocation(), 0, 100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				8, 1);
		level.addGameObject(dirt1);
		Obstacle obs1 = new Obstacle(level.getCameraLocation(), 800.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60,"assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs1);
		Terrain dirt3 = new Terrain(level.getCameraLocation(), 900.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60,
				"assets/block.png", 2, 1);
		level.addGameObject(dirt3);
		Terrain dirt4 = new Terrain(level.getCameraLocation(), 1100.0 / 60, 0.0 / 60, 100.0 / 60, 100.0 / 60,
				"assets/block.png", 1, 1);
		level.addGameObject(dirt4);
		Terrain dirt5 = new Terrain(level.getCameraLocation(), 1100.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/blockBottom.png",
				1,1);
		level.addGameObject(dirt5);
		Terrain dirt6 = new Terrain(level.getCameraLocation(), 1200.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt6);
		Obstacle obs2 = new Obstacle(level.getCameraLocation(), 1300.0/60, 100.0/60, 100.0/60, 100.0/60, "assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs2);
		Terrain dirt7 = new Terrain(level.getCameraLocation(), 1300.0 / 60, -120.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt7);
		Terrain dirt8 = new Terrain(level.getCameraLocation(), 1400.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt8);
		Terrain dirt9 = new Terrain(level.getCameraLocation(), 1500.0 / 60, 100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/blockBottom.png",
				1,1);
		level.addGameObject(dirt9);
		Terrain dirt10 = new Terrain(level.getCameraLocation(), 1500.0 / 60, 0.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt10);
		Terrain dirt11 = new Terrain(level.getCameraLocation(), 1600.0 / 60, 0.0 / 60, 100.0 / 60, 100.0 / 60, "assets/blockBottom.png",
				1,2);
		level.addGameObject(dirt11);
		Terrain dirt12 = new Terrain(level.getCameraLocation(), 1600.0 / 60, -100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt12);
		Terrain dirt13 = new Terrain(level.getCameraLocation(), 1700.0 / 60, -100.0 / 60, 100.0 / 60, 100.0 / 60, "assets/blockBottom.png",
				1,3);
		level.addGameObject(dirt13);
		Terrain dirt14 = new Terrain(level.getCameraLocation(), 1700.0 / 60, -200.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				1,1);
		level.addGameObject(dirt14);
		Obstacle obs3 = new Obstacle(level.getCameraLocation(), 1800.0/60, -100.0/60, 100.0/60, 100.0/60, "assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs3);
		Obstacle obs4 = new Obstacle(level.getCameraLocation(), 1900.0/60, -100.0/60, 100.0/60, 100.0/60, "assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs4);
		Obstacle obs5 = new Obstacle(level.getCameraLocation(), 2000.0/60, -100.0/60, 100.0/60, 100.0/60, "assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs5);
		Obstacle obs6 = new Obstacle(level.getCameraLocation(), 2100.0/60, -100.0/60, 100.0/60, 100.0/60, "assets/spikes.png", 1, 1, 20);
		level.addGameObject(obs6);
		Terrain dirt15 = new Terrain(level.getCameraLocation(), 1800.0 / 60, 0.0 / 60, 100.0 / 60, 100.0 / 60, "assets/blockBottom.png",
				4,4);
		level.addGameObject(dirt15);
		Terrain dirt16 = new Terrain(level.getCameraLocation(), 1920.0 / 60, -300.0 / 60, 100.0 / 60, 100.0 / 60, "assets/block.png",
				4,1);
		level.addGameObject(dirt16);
		Terrain portal = new Terrain(level.getCameraLocation(), 2200.0 / 60, -400.0 / 60, 100.0 / 60, 100.0 / 60, "assets/portal.png",
				1,1);
		level.addGameObject(portal);
		
		
		
		//Graphic background = new ImageGraphic("assets/space.png",0,0,16,9);
		//level.addGraphic(background);
		// End level construction.

		// Start of Title construction
		Scene title = SCENES_MAP.get(ScenesEnum.TITLE);
		Graphic titleScene = new Graphic(0, 0, 150, 50) {

			private static final long serialVersionUID = 3237106029139727237L;

			@Override
			public void act() {
				// TODO Auto-generated method stub
				repaint();
			}

			@Override
			public void paintComponent(Graphics t) {

				t.setFont(new Font("Arial", Font.BOLD, 24));
				t.setColor(Color.blue);
				t.drawString("Lunar Rebellion", 0, 10);

			}
		};

		title.addGraphic(titleScene);

		/*
		 * Graphic startButton = new ClickableGraphic(900.0 / 60, 100.0 / 60, 100.0 /
		 * 60, 100.0 / 60) { private static final long serialVersionUID =
		 * 3237106029139727237L;
		 * 
		 * @Override public void mouseClicked(MouseEvent arg0) {
		 * transitionScene(ScenesEnum.START_MENU);
		 * 
		 * }
		 * 
		 * @Override public void paintComponent(Graphics m) { m.drawRect(60, 60, 60,
		 * 60);
		 * 
		 * }
		 * 
		 * };
		 */

		// title.addGraphic(startButton);
	}

	/** We can use this method to listen for keyboard input from our window. */
	@Override
	public void keyPressed(KeyEvent e) {
		CURRENTLY_PRESSED_KEYS.add(e.getKeyCode());
	}

	/** We can use this method to listen for keyboard input from our window. */
	@Override
	public void keyReleased(KeyEvent e) {
		CURRENTLY_PRESSED_KEYS.remove(e.getKeyCode());
	}

	/** Let's not use this one, it's for typing. */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/** Swap out scenes to the scene specified in the parameter. */
	private void transitionScene(ScenesEnum scene) {
		if (Main.scene != null)
			GAME_WINDOW.remove(SCENES_MAP.get(Main.scene));
		Main.scene = scene;
		GAME_WINDOW.add(SCENES_MAP.get(scene));
		GAME_WINDOW.repaint();
	}
}