package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import view.View;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class World {

	/** The world's width. */
	private final int width;
	/** The world's height. */
	private final int height;
	/** The player's x position in the world. */
	private int playerX;
	/** The player's y position in the world. */
	private int playerY;

	private int startX;
	private int startY;

	private final FieldType[][] fields;

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();

	/**
	 * Creates a new world with the given size
	 */
	public World(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		this.fields = new FieldType[width][height];
		// Build the field
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				fields[x][y] = FieldType.EMPTY;
			}
		}
		// Build the walls
		for (int x = 0; x < width; x++) {
			fields[x][0] = FieldType.WALL;
			fields[x][height-1] = FieldType.WALL;
		}
		for (int y = 0; y < height; y++) {
			fields[0][y] = FieldType.WALL;
			fields[width-1][y] = FieldType.WALL;
		}

		// Your labyrinth walls here
		

		randomStartGoal();

		playerX = startX;
		playerY = startY;
		// Place the player in the world
		fields[playerX][playerY] = FieldType.PLAYER;
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	/**
	 * Returns the width of the world.
	 * 
	 * @return the width of the world.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 * 
	 * @return the height of the world.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * Sets the player's x position.
	 * 
	 * @param playerX the player's x position.
	 */
	public void setPlayerX(int playerX) {
		playerX = Math.max(0, playerX);
		playerX = Math.min(getWidth() - 1, playerX);
		this.playerX = playerX;
		
		//updateViews();
	}

	/**
	 * Returns the player's y position.
	 * 
	 * @return the player's y position.
	 */
	public int getPlayerY() {
		return playerY;
	}

	public FieldType[][] getFields() {
		return fields;
	}

	/**
	 * Sets the player's y position.
	 * 
	 * @param playerY the player's y position.
	 */
	public void setPlayerY(int playerY) {
		playerY = Math.max(0, playerY);
		playerY = Math.min(getHeight() - 1, playerY);
		this.playerY = playerY;
		
		//updateViews();
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public void randomStartGoal() {
		Random rand = new Random();

		int startX;
		int startY;
		int goalX;
		int goalY;

		do {
			startX = rand.nextInt(width);
			startY = rand.nextInt(height);
		} while (fields[startX][startY] != FieldType.EMPTY);

		do {
			goalX = rand.nextInt(width);
			goalY = rand.nextInt(height);
		} while (fields[goalX][goalY] != FieldType.EMPTY && 
				(Math.abs(goalX - startX) + Math.abs(goalY - startY)) > 50);

		setStartX(startX);
		setStartY(startY);
		fields[goalX][goalY] = FieldType.GOAL;
	}

	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * 
	 * @param direction where to move.
	 */
	public void movePlayer(Direction direction) {	
		// The direction tells us exactly how much we need to move along
		// every direction
		fields[getPlayerX()][getPlayerY()] = FieldType.EMPTY;
		int newPositionX =  getPlayerX() + direction.deltaX;
		int newPositionY = getPlayerY() + direction.deltaY;

		if (0 <= newPositionX && newPositionX < width && 0 <= newPositionY && newPositionY < height
        && fields[newPositionX][newPositionY] != FieldType.WALL) {

			if (fields[newPositionX][newPositionY] == FieldType.GOAL) {
				System.exit(0);
			}


			fields[newPositionX][newPositionY] = FieldType.PLAYER;
			// Update the player's position
			setPlayerX(newPositionX);
			setPlayerY(newPositionY);
			updateViews();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// View Management

	/**
	 * Adds the given view of the world and updates it once. Once registered through
	 * this method, the view will receive updates whenever the world changes.
	 * 
	 * @param view the view to be registered.
	 */
	public void registerView(View view) {
		views.add(view);
		view.update(this);
	}

	/**
	 * Updates all views by calling their {@link View#update(World)} methods.
	 */
	private void updateViews() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).update(this);
		}
	}

}
