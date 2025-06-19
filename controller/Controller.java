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

	/**
	 * The world that is updated upon every key press.
	 */
	private World world;
	/**
	 * The frame for the game window.
	 */
	private JFrame frame; // Not used in this class, but declared for possible future use
	/**
	 * Indicates whether the game is in hard mode.
	 */
	private boolean isHard;

	/**
	 * Creates a new controller instance.
	 *
	 * @param world the {@link World} to be updated whenever the player should move.
	 * @param isHard true if the game should be in hard mode, false otherwise
	 */
	public Controller(World world, boolean isHard) {
		// Remember the world
		this.world = world;
		this.isHard = isHard;
		// Listen for key events
		addKeyListener(this); // Register this controller as a key listener
		// Listen for mouse events.
		addMouseListener(this); // Register this controller as a mouse listener
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
		case KeyEvent.VK_W: // Move up with 'W'
		case KeyEvent.VK_UP: // Move up with arrow key
			world.movePlayer(Direction.UP, isHard);
			break;
		case KeyEvent.VK_S: // Move down with 'S'
		case KeyEvent.VK_DOWN: // Move down with arrow key
			world.movePlayer(Direction.DOWN, isHard);
			break;
		case KeyEvent.VK_A: // Move left with 'A'
		case KeyEvent.VK_LEFT: // Move left with arrow key
			world.movePlayer(Direction.LEFT, isHard);
			break;
		case KeyEvent.VK_D: // Move right with 'D'
		case KeyEvent.VK_RIGHT: // Move right with arrow key
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

	/**
	 * Called when a key is released. Not used in this implementation.
	 * @param e the {@link KeyEvent} containing information about the released key.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Not used
	}

	/**
	 * Called when a key is typed. Not used in this implementation.
	 * @param e the {@link KeyEvent} containing information about the typed key.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Not used
	}

	/////////////////// Action Events ////////////////////////////////

	/**
	 * Called when an action is performed. Not used in this implementation.
	 * @param e the {@link ActionEvent} containing information about the action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Not used
	}

	/////////////////// Mouse Events ////////////////////////////////

	/**
	 * Called when the mouse is clicked. Not used in this implementation.
	 * @param e the {@link MouseEvent} containing information about the mouse click.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Not used
	}

	/**
	 * Called when the mouse is pressed. Not used in this implementation.
	 * @param e the {@link MouseEvent} containing information about the mouse press.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Not used
	}

	/**
	 * Called when the mouse is released. Not used in this implementation.
	 * @param e the {@link MouseEvent} containing information about the mouse release.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Not used
	}

	/**
	 * Called when the mouse enters the component. Not used in this implementation.
	 * @param e the {@link MouseEvent} containing information about the mouse entering.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used
	}

	/**
	 * Called when the mouse exits the component. Not used in this implementation.
	 * @param e the {@link MouseEvent} containing information about the mouse exiting.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Not used
	}

}
