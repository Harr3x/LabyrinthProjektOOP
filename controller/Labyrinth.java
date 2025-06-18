package controller;

import java.awt.Dimension;

import javax.swing.JFrame;

import model.World;
import view.ConsoleView;
import view.GraphicView;

/**
 * Main class of the game. Responsible for initializing and connecting the MVC components.
 * Creates the {@link World}, the views, and the {@link Controller}, and starts the game.
 */
public class Labyrinth {

    /** Width of the game board */
    private static final int BOARD_WIDTH = 28;
    /** Height of the game board */
    private static final int BOARD_HEIGHT = 30;
    /** Size of a field. */
    private static final int FIELD_SIZE = 30;

    /**
     * Entry point of the program. Initializes the world, the views, and the controller.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Dimension of the game board
                int width = BOARD_WIDTH;
                int height = BOARD_HEIGHT;
                // Create a new game world.
                World world = new World(width, height);

                // Size of a field in the graphical view.
                Dimension fieldDimensions = new Dimension(FIELD_SIZE, FIELD_SIZE);
                // Create and register graphical view.
                GraphicView gview = new GraphicView(
                        width * fieldDimensions.width,
                        height * fieldDimensions.height,
                        fieldDimensions);
                gview.setPreferredSize(new Dimension( // für swing
                        width * fieldDimensions.width,
                        height * fieldDimensions.height));
                world.registerView(gview);
                gview.setVisible(true);

                // Create and register console view.
                ConsoleView cview = new ConsoleView();
                world.registerView(cview);
                // Create controller and initialize JFrame.
                Controller controller = new Controller(world);
                controller.setTitle("Päc-Man");
                controller.setResizable(false);
                controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                controller.getContentPane().add(gview);
                controller.pack();
                controller.setVisible(true);
                controller.requestFocusInWindow();
            }
        });
    }
}
