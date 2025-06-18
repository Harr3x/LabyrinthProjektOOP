package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import model.Enemy;
import model.World;

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
	private int powerup = 0;

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(WIDTH, HEIGHT);
	}

	/** The background rectangle. */
	private final Rectangle bg;
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
	                    g.setColor(Color.BLUE);
	                    g.fillRect(px, py, w, h);
	                    break;
	                case DOT:
	                    g.setColor(Color.BLACK);
	                    g.fillRect(px, py, w, h);
	                    g.setColor(Color.YELLOW);
	                    g.fillOval(px + w/3, py + h/3, w/3, h/3);
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
	    g.setColor(Color.PINK);
	    for (var enemy : world.getEnemies()) {
	        int ex = enemy.getX() * w;
	        int ey = enemy.getY() * h;
	        g.fillOval(ex + w/6, ey + h/6, 2*w/3, 2*h/3);
	    }

	    // Spieler zeichnen
	    g.setColor(Color.YELLOW);
	    g.fillArc(player.x, player.y, player.width, player.height, 30, 300);
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
