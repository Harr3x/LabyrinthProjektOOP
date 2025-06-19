package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import model.Direction;
import model.World;

/**
 * The controller listens for key and mouse events on the main window and controls the game logic.
 * This class extends {@link JFrame} and implements {@link KeyListener}, {@link ActionListener}, and {@link MouseListener}.
 * It forwards relevant input to the {@link World} object.
 */
public class Controller extends JFrame implements KeyListener, ActionListener, MouseListener {

	/** The world that is updated upon every key press. */
	private World world;
	private JFrame frame;
	private boolean isHard;

	/**
	 * Creates a new controller instance.
	 *
	 * @param world the {@link World} to be updated whenever the player should move.
	 */
	public Controller(World world, boolean isHard) {
		// Remember the world
		this.world = world;
		this.isHard = isHard;
		
		// Listen for key events
		addKeyListener(this);
		// Listen for mouse events.
		// Not used in the current implementation.
		addMouseListener(this);
	}


	/////////////////// Key Events ////////////////////////////////

	/**
	 * Called when a key is pressed. Controls the player according to the pressed key.
	 * @param e the {@link KeyEvent} containing information about the pressed key.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Check if we need to do something. Tells the world to move the player.
		switch (e.getKeyCode()) {

		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			world.movePlayer(Direction.UP, isHard);
			break;

		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			world.movePlayer(Direction.DOWN, isHard);
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			world.movePlayer(Direction.LEFT, isHard);
			break;

		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			world.movePlayer(Direction.RIGHT, isHard);
			break;

		case KeyEvent.VK_ESCAPE:
			// Exit the game when the escape key is pressed.
			System.exit(0);
			break;
		
		case KeyEvent.VK_R:
			// Reset the game when the 'R' key is pressed.
			world.restart(null);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	

	/////////////////// Action Events ////////////////////////////////

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	/////////////////// Mouse Events ////////////////////////////////

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
