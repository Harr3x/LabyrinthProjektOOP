package controller;

import java.awt.Dimension;
import view.StartMenu;
import javax.swing.JFrame;

import model.World;
import view.ConsoleView;
import view.GraphicView;

/**
 * Main class of the game. Responsible for initializing and connecting the MVC components.
 * Creates the {@link World}, the views, and the {@link Controller}, and starts the game.
 */
public class Labyrinth {

    /** Width of the game board. */
    private static final int BOARD_WIDTH = 28;
    /** Height of the game board. */
    private static final int BOARD_HEIGHT = 30;
    /** Size of a field. */
    private static final int FIELD_SIZE = 25;

    /** The start menu of the game. */
    private static StartMenu startMenu;

    /**
     * Entry point of the program. Initializes the world, the views, and the controller.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            int width = BOARD_WIDTH * FIELD_SIZE; // Calculate window width in pixels
            int height = BOARD_HEIGHT * FIELD_SIZE; // Calculate window height in pixels
            Labyrinth.startMenu = new StartMenu(width, height); // Show start menu
        });
    }

    /**
     * Starts the game by creating the world, views, and controller, and initializing the game window.
     *
     * @param isHard true if the game should be in hard mode, false otherwise
     */
    public static void startGame(boolean isHard){
        // Dimension of the game board (in fields)
        int width = BOARD_WIDTH;
        int height = BOARD_HEIGHT;
        // Create a new game world (model)
        World world = new World(width, height, isHard);
        // Size of a field in the graphical view (in pixels)
        Dimension fieldDimensions = new Dimension(FIELD_SIZE, FIELD_SIZE);
        // Create and register graphical view (GUI)
        GraphicView gview = new GraphicView(
                width * fieldDimensions.width,
                height * fieldDimensions.height,
                fieldDimensions);
        gview.setPreferredSize(new Dimension(
                width * fieldDimensions.width,
                height * fieldDimensions.height));
        world.registerView(gview); // Register the graphical view as an observer
        gview.setVisible(true);
        // Create and register console view (prints to terminal)
        ConsoleView cview = new ConsoleView();
        world.registerView(cview); // Register the console view as an observer
        // Create controller and initialize JFrame (window)
        Controller controller = new Controller(world, isHard); // Handles user input
        controller.setTitle("Päc-Man");
        controller.setResizable(false);
        controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controller.getContentPane().add(gview); // Add graphical view to window
        controller.pack(); // Fit window to preferred size
        controller.setLocationRelativeTo(null); // Center window
        controller.setVisible(true);
        controller.requestFocusInWindow(); // Ensure key events are received
    }

    /**
     * Returns the start menu instance.
     *
     * @return the {@link StartMenu} instance
     */
    public static StartMenu getStartMenu() {
        return startMenu;
    }
}

