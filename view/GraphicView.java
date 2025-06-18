package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.World;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A graphical view of the world.
 * <p>
 * This class is responsible for rendering the maze, player, enemies, and dots using Java2D.
 * It implements the {@link View} interface and updates its display whenever the world changes.
 */
public class GraphicView extends JPanel implements View {

	/** The total width of the view in pixels. */
	private final int WIDTH;
	/** The total height of the view in pixels. */
	private final int HEIGHT;

	/** The size of a single field (cell) in the maze. */
	private Dimension fieldDimension;
	/** The current world state to display. */
	private World world;

	/** The image used to represent the player. */
	private BufferedImage playerImage;
	/** A map of enemy names to their images. */
	private Map<String, BufferedImage> enemyImages = new HashMap<>();

	/**
	 * Constructs a new GraphicView with the given size and field dimensions.
	 * Loads all necessary images for the player and enemies.
	 *
	 * @param width the width in pixels
	 * @param height the height in pixels
	 * @param fieldDimension the size of one field (cell)
	 */
	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;

		// Load player image
		try {
			playerImage = ImageIO.read(getClass().getResource("/resources/pacman.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Spielerbild konnte nicht geladen werden: " + e.getMessage());
			playerImage = null;
		}
		// Load enemy images
		try {
			enemyImages.put("red", ImageIO.read(getClass().getResource("/resources/redGhost.png")));
			enemyImages.put("pink", ImageIO.read(getClass().getResource("/resources/pinkGhost.png")));
			enemyImages.put("cyan", ImageIO.read(getClass().getResource("/resources/cyanGhost.png")));
			enemyImages.put("orange", ImageIO.read(getClass().getResource("/resources/orangeGhost.png")));
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Gegnerbild konnte nicht geladen werden: " + e.getMessage());
		}
	}
	/**
	 * The rectangle representing the player's position and size on the board.
	 */
	private final Rectangle player = new Rectangle(1, 1);

	/**
	 * Paints the entire game view, including the maze, player, and enemies.
	 *
	 * @param g the Graphics context to draw on
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (world == null) return;

		// Draw background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		drawFields(g);
		drawEnemies(g);
		drawPlayer(g);
	}

	/**
	 * Draws all fields (walls, dots, empty, start, goal) of the maze.
	 *
	 * @param g the Graphics context
	 */
	private void drawFields(Graphics g) {
		var fields = world.getFields();
		int w = fieldDimension.width;
		int h = fieldDimension.height;
		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				int px = x * w;
				int py = y * h;
				switch (fields[x][y]) {
					case WALL:
						drawWall(g, px, py, w, h);
						break;
					case DOT:
						drawDot(g, px, py, w, h);
						break;
					case EMPTY:
					default:
						g.setColor(Color.BLACK);
						g.fillRect(px, py, w, h);
						break;
				}
				if (x == world.getStartX() && y == world.getStartY()) {
					drawStart(g, px, py, w, h);
				}
				if (x == world.getGoalX() && y == world.getGoalY()) {
					drawGoal(g, px, py, w, h);
				}
			}
		}
	}

	/**
	 * Draws a wall at the given position.
	 *
	 * @param g the Graphics context
	 * @param x the x position in pixels
	 * @param y the y position in pixels
	 * @param w the width of the wall
	 * @param h the height of the wall
	 */
	private void drawWall(Graphics g, int x, int y, int w, int h) {
		int margin = Math.max(3, w / 8);
		int arc = Math.max(8, w / 3);
		int wallX = x + margin;
		int wallY = y + margin;
		int wallW = w - 2 * margin;
		int wallH = h - 2 * margin;
		g.setColor(Color.BLACK);
		((Graphics2D) g).fillRoundRect(wallX, wallY, wallW, wallH, arc, arc);
		g.setColor(Color.BLUE);
		((Graphics2D) g).setStroke(new java.awt.BasicStroke(0.8f));
		((Graphics2D) g).drawRoundRect(wallX, wallY, wallW, wallH, arc, arc);
		((Graphics2D) g).setStroke(new java.awt.BasicStroke(1));
	}

	/**
	 * Draws a dot at the given position.
	 *
	 * @param g the Graphics context
	 * @param x the x position in pixels
	 * @param y the y position in pixels
	 * @param w the width of the field
	 * @param h the height of the field
	 */
	private void drawDot(Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		g.setColor(new Color(255, 182, 193));
		int dotW = w / 6;
		int dotH = h / 6;
		g.fillRect(x + (w - dotW) / 2, y + (h - dotH) / 2, dotW, dotH);
	}

	/**
	 * Draws the start field at the given position.
	 *
	 * @param g the Graphics context
	 * @param x the x position in pixels
	 * @param y the y position in pixels
	 * @param w the width of the field
	 * @param h the height of the field
	 */
	private void drawStart(Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.GREEN);
		g.drawRect(x+2, y+2, w-4, h-4);
	}

	/**
	 * Draws the goal field at the given position.
	 *
	 * @param g the Graphics context
	 * @param x the x position in pixels
	 * @param y the y position in pixels
	 * @param w the width of the field
	 * @param h the height of the field
	 */
	private void drawGoal(Graphics g, int x, int y, int w, int h) {
		g.setColor(Color.RED);
		g.drawRect(x+4, y+4, w-8, h-8);
	}

	/**
	 * Draws all enemies on the board.
	 *
	 * @param g the Graphics context
	 */
	private void drawEnemies(Graphics g) {
		int w = fieldDimension.width;
		int h = fieldDimension.height;
		for (var enemy : world.getEnemies()) {
			int ex = enemy.getX() * w;
			int ey = enemy.getY() * h;
			BufferedImage enemyImg = enemyImages.get(enemy.getName());
			if (enemyImg != null) {
				g.drawImage(enemyImg, ex, ey, w, h, null);
			} else {
				g.setColor(Color.PINK);
				g.fillOval(ex + w/6, ey + h/6, 2*w/3, 2*h/3);
			}
		}
	}

	/**
	 * Draws the player on the board.
	 *
	 * @param g the Graphics context
	 */
	private void drawPlayer(Graphics g) {
		if (playerImage != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			int centerX = player.x + player.width / 2;
			int centerY = player.y + player.height / 2;
			double angle = 0;
			if (world != null && world.getPlayerDirection() != null) {
				switch (world.getPlayerDirection()) {
					case RIGHT: angle = 0; break;
					case DOWN:  angle = Math.PI / 2; break;
					case LEFT:  angle = Math.PI; break;
					case UP:    angle = -Math.PI / 2; break;
					case NONE:  angle = 0; break;
				}
			}
			g2d.rotate(angle, centerX, centerY);
			g2d.drawImage(
				playerImage,
				player.x, player.y,
				player.width, player.height,
				null
			);
			g2d.dispose();
		} else {
			g.setColor(Color.YELLOW);
			g.fillArc(player.x, player.y, player.width, player.height, 30, 300);
		}
	}

	/**
	 * Updates the view with the new world state and repaints the component.
	 *
	 * @param world the updated world
	 */
	@Override
	public void update(World world) {
		this.world = world;
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (world.getPlayerX() * fieldDimension.width),
			(int) (world.getPlayerY() * fieldDimension.height)
		);
		repaint();
	}

}
