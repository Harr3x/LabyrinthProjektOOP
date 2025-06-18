package model;

import java.util.ArrayList;
import java.lang.reflect.Field;
import view.View;

import javax.swing.*;
import java.util.Random;

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

	private int goalX;
	private int goalY;
	private final FieldType[][] fields;
	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();
	private final ArrayList<Enemy> enemies = new ArrayList<>();

	private Direction playerDirection = Direction.RIGHT; // Standardrichtung


	/**
	 * Creates a new world with the given size.t
	 */
	public World(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		this.fields = new FieldType[width][height];
		build_maze();

		randomStartGoal();
		playerX= startX;
		playerY= startY;
		setEnemies(5);

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
		//playerX = Math.max(0, playerX);
		//playerX = Math.min(getWidth() - 1, playerX);
		this.playerX = playerX;
		
		updateViews();
	}

	/**
	 * Returns the player's y position.
	 * 
	 * @return the player's y position.
	 */


	public int getPlayerY() {
		return playerY;
	}
	
	/**
	 * Returns the field.
	 *
	 * @return the field.
	 */
	public FieldType[][] getFields(){
		return fields;
	}

	/**
	 * Sets the player's y position.
	 * 
	 * @param playerY the player's y position.
	 */
	public void setPlayerY(int playerY) {
		//playerY = Math.max(0, playerY);
		//playerY = Math.min(getHeight() - 1, playerY);
		this.playerY = playerY;
		
		updateViews();
	}
	/**
	 * Returns the player's x position at the start.
	 *
	 * @return the player's x position at the start.
	 */
	public int getStartX() {
		return startX;
	}
	/**
	 * Returns the player's y position at the start.
	 *
	 * @return the player's y position at the start.
	 */
	public int getStartY() {
		return startY;
	}
	/**
	 * Sets the start x position.
	 *
	 * @return the start x position.
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}
	/**
	 * Sets the start y position.
	 *
	 * @return the start y position.
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public int getGoalX() {
		return goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}


	public ArrayList<Enemy> getEnemies() {
    return enemies;
}

	public Direction getPlayerDirection() {
	    return playerDirection;
	}

	public void randomStartGoal() {
		Random rand = new Random();

		int startX, startY, goalX, goalY;

		do {
			startX = rand.nextInt(width);
			startY = rand.nextInt(height);
		} while (fields[startX][startY] != FieldType.DOT);

		do {
			goalX = rand.nextInt(width);
			goalY = rand.nextInt(height);
		} while (fields[goalX][goalY] != FieldType.DOT  && Math.abs(goalX-startX)+Math.abs(goalY-startY)<50);

		setStartX(startX);
		setStartY(startY);
		setGoalX(goalX);
		setGoalY(goalY);
	}

	public void build_maze() {
		int[][] maze = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
		{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
		{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
		{1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
		{2,2,2,2,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,2,2,2,2},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{0,0,0,0,0,0,0,0,0,0,1,1,2,2,2,2,1,1,0,0,0,0,0,0,0,0,0,0},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{2,2,2,2,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,2,2,2,2},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
		{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
		{1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
		{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
		{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
		{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
		{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };

    	for (int y = 0; y < maze.length; y++) {
        	for (int x = 0; x < maze[0].length; x++) {
				
            	fields[x][y] = (maze[y][x] == 1) ? FieldType.WALL : FieldType.DOT;
				if (maze[y][x] == 2){
					fields[x][y] = FieldType.EMPTY;
				}
    		}
		}	
	}

	public void setEnemies(int count_enemies){
		enemies.clear();
		int enemiesX;
		int enemiesY;
		Random rand_enemies = new Random();
		for (int i = 1; i < count_enemies; i++) {
			do { 
				enemiesX = rand_enemies.nextInt(width - 2)+1;
				enemiesY = rand_enemies.nextInt(height -2) +1 ;
			} while (fields[enemiesX][enemiesY] != FieldType.DOT || (enemiesX == playerX && enemiesY == playerY));
			enemies.add(new Enemy(enemiesX, enemiesY));
		}


	}


	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * 
	 * @param direction where to move.
	 */
	public void movePlayer(Direction direction) {	
    int newPositionX = (getPlayerX() + direction.deltaX + width) % width;
    int newPositionY = (getPlayerY() + direction.deltaY + height) % height;

    if (fields[newPositionX][newPositionY] != FieldType.WALL) {
        playerDirection = direction; // Richtung merken
        setPlayerX(newPositionX);
        setPlayerY(newPositionY);
        moveEnemies();
        if (newPositionX == getGoalX() && newPositionY == getGoalY()) {
			int choice = JOptionPane.showOptionDialog(
					null,
					" Du hast das Spiel gewonnen! \n Was möchtest du nun tun?",
					"Spiel gewonnen",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					new String[] { "Neustart", "Beenden" },
					"Neustart"
			);
			if (choice == JOptionPane.YES_OPTION) {
				restart();
			} else if (choice == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		} else {
			updateViews();
		}
	}
}


	public void restart() {
    System.out.println("Restarting the game...");
    // Reset all fields
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            fields[x][y] = FieldType.EMPTY;
        }
    }

    build_maze();
    randomStartGoal();

    // Set player position to start
    setPlayerX(getStartX());
    setPlayerY(getStartY());

    setEnemies(5);

    updateViews();
}



	public void moveEnemies() {
		Random rand_enemies = new Random();
		for (Enemy enemy : enemies) {
			int dx, dy;
			int enemyX, enemyY;
			int direction_random = rand_enemies.nextInt(4);
			if (direction_random == 0 ){
				dx = 1;
				dy = 0;
			} else if (direction_random == 1) {
				dx = -1;
				dy = 0;
			} else if (direction_random == 2){
				dx = 0;
				dy = 1;
			} else {
				dx = 0;
				dy = -1;
			}
			enemyX = enemy.getX() + dx;
			enemyY = enemy.getY() + dy;

			if (enemyX >0 && enemyX < (width-1) && enemyY >0 && enemyY < (height-1) && fields[enemyX][enemyY] != FieldType.WALL){
				enemy.setEnemyX(enemyX);
				enemy.setEnemyY(enemyY);
				// fields[enemyX][enemyY] = FieldType.ENEMY;
			}
			if (enemy.getX() == playerX && enemy.getY() == playerY) {
				int choice = JOptionPane.showOptionDialog(
						null,
						" Du hast das Spiel verloren! \n Was möchtest du nun tun?",
						"Spiel verloren",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						new String[] { "Neustart", "Beenden" },
						"Neustart"
				);
				if (choice == JOptionPane.YES_OPTION) {
					restart();
				} else if (choice == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
			
		}
		updateViews();


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
