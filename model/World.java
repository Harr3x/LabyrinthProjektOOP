package model;

import java.util.ArrayList;
import view.View;

import controller.Labyrinth;

import java.util.Random;
import java.awt.Frame;

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
	/** The player's starting x position. */
	private int startX;
	/** The player's starting y position. */
	private int startY;
	/** The goal's x position. */
	private int goalX;
	/** The goal's y position. */
	private int goalY;
	/** The field layout of the world. */
	private final FieldType[][] fields;
	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();
	/** List of enemies in the world. */
	private final ArrayList<Enemy> enemies = new ArrayList<>();
	/** The current direction of the player. */
	private Direction playerDirection = Direction.RIGHT; // Standardrichtung


	/**
	 * Creates a new world with the given size.
	 * @param width the width of the world
	 * @param height the height of the world
	 * @param isHard true if the game should be in hard mode, false otherwise
	 */
	public World(int width, int height, boolean isHard) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		this.fields = new FieldType[width][height];
		build_maze();

		randomStartGoal();
		playerX= startX;
		playerY= startY;
		setEnemies(isHard);
	}

	///////////////////////////////////////////////////////////////////////////
	//--------------------------Getters and Setters--------------------------//
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the width of the world.
	 * @return the width of the world
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 * @return the height of the world
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the player's x position.
	 * @return the player's x position
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * Sets the player's x position.
	 * @param playerX the player's x position
	 */
	public void setPlayerX(int playerX) {
		this.playerX = playerX;
		updateViews();
	}

	/**
	 * Returns the player's y position.
	 * @return the player's y position
	 */
	public int getPlayerY() {
		return playerY;
	}

	/**
	 * Returns the field layout of the world.
	 * @return the field array
	 */
	public FieldType[][] getFields(){
		return fields;
	}

	/**
	 * Sets the player's y position.
	 * @param playerY the player's y position
	 */
	public void setPlayerY(int playerY) {
		this.playerY = playerY;
		updateViews();
	}

	/**
	 * Returns the player's x position at the start.
	 * @return the player's x position at the start
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Returns the player's y position at the start.
	 * @return the player's y position at the start
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Sets the start x position.
	 * @param startX the start x position
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Sets the start y position.
	 * @param startY the start y position
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Returns the goal's x position.
	 * @return the goal's x position
	 */
	public int getGoalX() {
		return goalX;
	}

	/**
	 * Returns the goal's y position.
	 * @return the goal's y position
	 */
	public int getGoalY() {
		return goalY;
	}

	/**
	 * Sets the goal's x position.
	 * @param goalX the goal's x position
	 */
	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	/**
	 * Sets the goal's y position.
	 * @param goalY the goal's y position
	 */
	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}

	/**
	 * Returns the list of enemies in the world.
	 * @return the list of enemies
	 */
	public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

	/**
	 * Returns the current direction of the player.
	 * @return the player's direction
	 */
	public Direction getPlayerDirection() {
        return playerDirection;
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * Randomly sets the start and goal positions on the board.
	 */
	public void randomStartGoal() {
		Random rand = new Random();

		int startX, startY, goalX, goalY;

		// Find a random start position on a DOT field
		do {
			startX = rand.nextInt(width);
			startY = rand.nextInt(height);
		} while (fields[startX][startY] != FieldType.DOT);

		// Find a random goal position on a DOT field, far enough (at least 50 blocks) from the start
		do {
			goalX = rand.nextInt(width);
			goalY = rand.nextInt(height);
		} while (fields[goalX][goalY] != FieldType.DOT  && Math.abs(goalX-startX)+Math.abs(goalY-startY)<50);

		setStartX(startX);
		setStartY(startY);
		setGoalX(goalX);
		setGoalY(goalY);
	}

	/**
	 * Builds the maze layout and initializes the field array.
	 */
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

    	// Fill the fields array based on the maze definition
    	for (int y = 0; y < maze.length; y++) {
        	for (int x = 0; x < maze[0].length; x++) {
				// 1 = WALL, 0 = DOT, 2 = EMPTY
            	fields[x][y] = (maze[y][x] == 1) ? FieldType.WALL : FieldType.DOT;
				if (maze[y][x] == 2){
					fields[x][y] = FieldType.EMPTY;
				}
    		}
		} 
	}

	/**
	 * Sets up the enemies on the board.
	 * @param isHard true if the game should be in hard mode, false otherwise
	 */
	public void setEnemies(boolean isHard){
    	enemies.clear();
		int count_enemies = 4;
    	int enemiesX;
    	int enemiesY;
    	Random rand_enemies = new Random();
    	// List of color names for the enemies
    	String[] names = {"red", "cyan", "orange", "pink"};
    	for (int i = 0; i < count_enemies && i < names.length; i++) {
    	    do {
    	        enemiesX = rand_enemies.nextInt(width - 2) + 1;
    	        enemiesY = rand_enemies.nextInt(height - 2) + 1;
    	    } while (fields[enemiesX][enemiesY] != FieldType.DOT || (enemiesX == playerX && enemiesY == playerY));
    	    enemies.add(new Enemy(enemiesX, enemiesY, names[i]));
    	}
	}


	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * @param direction where to move
	 * @param isHard true if the game should be in hard mode, false otherwise
	 */
	public void movePlayer(Direction direction, boolean isHard) {
        // Calculate new position with wrap-around at the edges
        int newPositionX = (getPlayerX() + direction.deltaX + width) % width;
        int newPositionY = (getPlayerY() + direction.deltaY + height) % height;
        // Only move if the new position is not a wall
        if (fields[newPositionX][newPositionY] != FieldType.WALL) {
            playerDirection = direction; // Remember the direction
            fields[getPlayerX()][getPlayerY()] = FieldType.EMPTY;
            setPlayerX(newPositionX);
            setPlayerY(newPositionY);
			checkGamestate();
            moveEnemies(isHard);
            checkGamestate();
        } 
    }


	/**
	 * Restarts the game and shows the start menu.
	 * Closes all open frames and sets the game state in the start menu.
	 *
	 * @param gameState the state of the game (true for win, false for lose, null for reset)
	 */
	public void restart(Boolean gameState) {
		System.out.println("Restarting the game...");
		// Close all open frames (windows)
		for (Frame frame : Frame.getFrames()) {
            frame.dispose();
		}
		Labyrinth.getStartMenu().setGameState(gameState);
		Labyrinth.getStartMenu().setVisible(true);
	}

	/**
	 * Checks the current game state (win/lose) and restarts if necessary.
	 */
	public void checkGamestate() {
		Boolean gameState = null;
    // Check if the goal has been reached
    if (playerX == goalX && playerY == goalY) {
		gameState = true;
		restart(gameState);
		return;
    }
    // Check if an enemy is on the player
    for (Enemy enemy : enemies) {
        if (enemy.getX() == playerX && enemy.getY() == playerY) {
			gameState = false;
            restart(gameState);
            return;
        }
    }
}



	/**
	 * Moves all enemies according to the current difficulty.
	 * @param isHard true if the game should be in hard mode, false otherwise
	 */
	public void moveEnemies(boolean isHard) {
		// direction of enemies random
		if (isHard == false) {
			Random rand_enemies = new Random();
			for (Enemy enemy : enemies) {
				int [] dx = {-1,1,0,0};
				int [] dy = {0,0,1,-1};
				ArrayList<Integer> directions_dx_dy = new ArrayList<>();

				// Add all possible directions (0: left, 1: right, 2: down, 3: up)
				for (int i=0; i<4; i++){
					directions_dx_dy.add(i);
				}
				java.util.Collections.shuffle(directions_dx_dy, rand_enemies);
				for ( int dir: directions_dx_dy){
					int newenemyX = enemy.getX() + dx[dir];
					int newenemyY = enemy.getY() + dy[dir];

					// Only move if the new position is not a wall
					if (fields[newenemyX][newenemyY] != FieldType.WALL) {
						enemy.setEnemyX(newenemyX);
						enemy.setEnemyY(newenemyY);
						break; 
					}
				}	
			}
			updateViews();
		} else {
			// enemies chasing the player
			int [] dx = {-1,1,0,0};
			int [] dy = {0,0,1,-1};
			
			for (Enemy enemy : enemies) {
				ArrayList<int[]> preferred_dir_x_y  = new ArrayList<>();
				int enemyX = enemy.getX();
				int enemyY = enemy.getY();
				int diffX = playerX - enemyX;
				int diffY = playerY - enemyY;

				// Prefer moving towards the player: first in the direction with the largest difference
				if (Math.abs(diffX)>Math.abs(diffY)){
					if (diffX>0){
						preferred_dir_x_y.add(new int[]{1, 0}); // right
					}else{
						preferred_dir_x_y.add(new int[]{-1, 0}); // left
					}
					if (diffY>0){
						preferred_dir_x_y.add(new int[] {0,1}); // down
					}else{
						preferred_dir_x_y.add(new int[] {0,-1}); // up
					}
				}else{
					if (diffY>0){
						preferred_dir_x_y.add(new int[] {0,1}); // down
					}else{
						preferred_dir_x_y.add(new int[] {0,-1}); // up
					}
					if (diffX>0){
						preferred_dir_x_y.add(new int[]{1, 0}); // right
					}else{
						preferred_dir_x_y.add(new int[]{-1, 0}); // left
					}
				}
				// Add remaining directions not already in the preferred list
				for (int i = 0; i < 4; i++) {
        			int[] dir = {dx[i], dy[i]};
        			boolean alreadyInList = false;
					for (int[] d : preferred_dir_x_y) {
						if (d[0] == dir[0] && d[1] == dir[1]) {
							alreadyInList = true;
							break;
						}
					}
					if (!alreadyInList) {
						preferred_dir_x_y.add(dir);
					}
				}
				// Try to move in preferred directions, but only if the field is not a wall and not occupied by another enemy
				for (int[] direction: preferred_dir_x_y){
					int newenemyX = enemyX + direction[0];
					int newenemyY = enemyY + direction[1];

            		if (fields[newenemyX][newenemyY] != FieldType.WALL  ) {
                		boolean isFieldFree = true;
						for (Enemy other : enemies) {
        					if (other != enemy && other.getX() == newenemyX && other.getY() == newenemyY) {
            					isFieldFree = false;
            					break;
        					}
    					}

						if (isFieldFree) {
							enemy.setEnemyX(newenemyX);
							enemy.setEnemyY(newenemyY);
							break;
						}
                	}
				}
			}
			updateViews();
		}
	}


	///////////////////////////////////////////////////////////////////////////
	//--------------------------View Management------------------------------//
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Adds the given view of the world and updates it once. Once registered through
	 * this method, the view will receive updates whenever the world changes.
	 * @param view the view to be registered
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
