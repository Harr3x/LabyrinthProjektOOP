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
 */
public class GraphicView extends JPanel implements View {

	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;

	private Dimension fieldDimension;
	private World world;

	private BufferedImage playerImage;
	private Map<String, BufferedImage> enemyImages = new HashMap<>();

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;

		// Spielerbild laden
		try {
			playerImage = ImageIO.read(getClass().getResource("/resources/pacman.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Spielerbild konnte nicht geladen werden: " + e.getMessage());
			playerImage = null;
		}
		
		// Gegnerbilder laden
	    try {
	        enemyImages.put("red", ImageIO.read(getClass().getResource("/resources/redGhost.png")));
	        enemyImages.put("pink", ImageIO.read(getClass().getResource("/resources/pinkGhost.png")));
	        enemyImages.put("cyan", ImageIO.read(getClass().getResource("/resources/cyanGhost.png")));
	        enemyImages.put("orange", ImageIO.read(getClass().getResource("/resources/orangeGhost.png")));
	    } catch (IOException | IllegalArgumentException e) {
	        System.err.println("Gegnerbild konnte nicht geladen werden: " + e.getMessage());
	    }
	}
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(1, 1);

	/**
	 * Creates a new instance.
	 */
	@Override
	public void paint(Graphics g) {
	    super.paint(g);
	    if (world == null) return;

	    // Hintergrund
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, WIDTH, HEIGHT);

	    var fields = world.getFields();
	    int w = fieldDimension.width;
	    int h = fieldDimension.height;

	    // Felder zeichnen
	    for (int x = 0; x < world.getWidth(); x++) {
	        for (int y = 0; y < world.getHeight(); y++) {
	            int px = x * w;
	            int py = y * h;

	            switch (fields[x][y]) {
	                case WALL:
	                    // Größe und Rundung der Wand festlegen
	                    int margin = Math.max(3, w / 8); // Abstand zum Rand
	                    int arc = Math.max(8, w / 3);    // Rundungsradius

	                    int wallX = px + margin;
	                    int wallY = py + margin;
	                    int wallW = w - 2 * margin;
	                    int wallH = h - 2 * margin;

	                    // Schwarze Füllung
	                    g.setColor(Color.BLACK);
	                    ((Graphics2D) g).fillRoundRect(wallX, wallY, wallW, wallH, arc, arc);

	                    // Blauer Rand
	                    g.setColor(Color.BLUE);
	                    ((Graphics2D) g).setStroke(new java.awt.BasicStroke(1.5f));
	                    ((Graphics2D) g).drawRoundRect(wallX, wallY, wallW, wallH, arc, arc);
	                    // Stroke zurücksetzen (optional)
	                    ((Graphics2D) g).setStroke(new java.awt.BasicStroke(1));
	                    break;
	                case DOT:
	                    g.setColor(Color.BLACK);
	                    g.fillRect(px, py, w, h);
	                    // Punktfarbe auf helles Pink ändern, als noch kleineres Rechteck zeichnen
	                    g.setColor(new Color(255, 182, 193)); // Light Pink (RGB)
	                    int dotW = w / 6;
	                    int dotH = h / 6;
	                    g.fillRect(px + (w - dotW) / 2, py + (h - dotH) / 2, dotW, dotH);
	                    break;
	                case EMPTY:
	                default:
	                    g.setColor(Color.BLACK);
	                    g.fillRect(px, py, w, h);
	                    break;
	            }

	            // Startfeld markieren
	            if (x == world.getStartX() && y == world.getStartY()) {
	                g.setColor(Color.GREEN);
	                g.drawRect(px+2, py+2, w-4, h-4);
	            }
	            // Zielfeld markieren
	            if (x == world.getGoalX() && y == world.getGoalY()) {
	                g.setColor(Color.RED);
	                g.drawRect(px+4, py+4, w-8, h-8);
	            }
	        }
	    }

	    // Gegner zeichnen
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

	    // Spieler zeichnen
	    if (playerImage != null) {
	        Graphics2D g2d = (Graphics2D) g.create();

	        // Mittelpunkt berechnen
	        int centerX = player.x + player.width / 2;
	        int centerY = player.y + player.height / 2;

	        // Winkel je nach Richtung bestimmen
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

	        // Um Mittelpunkt rotieren
	        g2d.rotate(angle, centerX, centerY);

	        // Bild zeichnen
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
	

	@Override
	public void update(World world) {
		this.world = world;

		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (world.getPlayerX() * fieldDimension.width),
			(int) (world.getPlayerY() * fieldDimension.height)
		);
		repaint();
	}

}
