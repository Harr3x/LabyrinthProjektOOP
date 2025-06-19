package model;

import java.util.ArrayList;
import view.StartMenu;
import view.View;

import javax.swing.*;

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

	public void setEnemies(boolean isHard){
    	enemies.clear();
		int count_enemies = 4;
    	int enemiesX;
    	int enemiesY;
    	Random rand_enemies = new Random();
    	// Liste der Farbnamen für die Gegner
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
	 * 
	 * @param direction where to move.
	 */
	public void movePlayer(Direction direction, boolean isHard) {
        int newPositionX = (getPlayerX() + direction.deltaX + width) % width;
        int newPositionY = (getPlayerY() + direction.deltaY + height) % height;
        if (fields[newPositionX][newPositionY] != FieldType.WALL) {
            playerDirection = direction; // Richtung merken
            fields[getPlayerX()][getPlayerY()] = FieldType.EMPTY;
            setPlayerX(newPositionX);
            setPlayerY(newPositionY);
			checkGamestate();
            moveEnemies(isHard);
            checkGamestate();
        } 
    }


	public void restart(Boolean gameState) {
		System.out.println("Restarting the game...");
		for (Frame frame : Frame.getFrames()) {
        frame.dispose();
		}
		Labyrinth.getStartMenu().setGameState(gameState);
		Labyrinth.getStartMenu().setVisible(true);
	}

	public void checkGamestate() {
		Boolean gameState = null;
    // Prüfe, ob das Ziel erreicht wurde
    if (playerX == goalX && playerY == goalY) {
		gameState = true;
		restart(gameState);
		return;
    }
    // Prüfe, ob ein Gegner auf dem Spieler steht
    for (Enemy enemy : enemies) {
        if (enemy.getX() == playerX && enemy.getY() == playerY) {
			gameState = false;
            restart(gameState);
            return;
        }
    }
}



	public void moveEnemies(boolean isHard) {
		if (isHard == false) {
			Random rand_enemies = new Random();
			for (Enemy enemy : enemies) {
				int [] dx = {-1,1,0,0};
				int [] dy = {0,0,1,-1};
				ArrayList<Integer> directions_dx_dy = new ArrayList<>();

				for (int i=0; i<4; i++){
					directions_dx_dy.add(i);
				}
				java.util.Collections.shuffle(directions_dx_dy, rand_enemies);
				for ( int dir: directions_dx_dy){
					int newenemyX = enemy.getX() + dx[dir];
					int newenemyY = enemy.getY() + dy[dir];


					if (fields[newenemyX][newenemyY] != FieldType.WALL) {
						enemy.setEnemyX(newenemyX);
						enemy.setEnemyY(newenemyY);
						break; 
					}
				}	
			}
			updateViews();
		} else {
			int [] dx = {-1,1,0,0};
			int [] dy = {0,0,1,-1};
			
			for (Enemy enemy : enemies) {
				ArrayList<int[]> preffered_dir_x_y  = new ArrayList<>();
				int enemyX = enemy.getX();
				int enemyY = enemy.getY();
				int diffX = playerX - enemyX;
				int diffY = playerY - enemyY;

				if (Math.abs(diffX)>Math.abs(diffY)){
					if (diffX>0){
						preffered_dir_x_y.add(new int[]{1, 0});
					}else{
						preffered_dir_x_y.add(new int[]{-1, 0});
					}
					if (diffY>0){
						preffered_dir_x_y.add(new int[] {0,1});
					}else{
						preffered_dir_x_y.add(new int[] {0,-1});
					}
				}else{
					if (diffY>0){
						preffered_dir_x_y.add(new int[] {0,1});
					}else{
						preffered_dir_x_y.add(new int[] {0,-1});
					}
					if (diffX>0){
						preffered_dir_x_y.add(new int[]{1, 0});
					}else{
						preffered_dir_x_y.add(new int[]{-1, 0});
					}
				}
				for (int i = 0; i < 4; i++) {
        			int[] dir = {dx[i], dy[i]};
        			boolean alreadyAdded = false;
					for (int[] d : preffered_dir_x_y) {
						if (d[0] == dir[0] && d[1] == dir[1]) {
							alreadyAdded = true;
							break;
						}
					}
					if (!alreadyAdded) {
						preffered_dir_x_y.add(dir);
					}
				}
				for (int[] direction: preffered_dir_x_y){
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
                		
           		    };

				
				};
		
			};
			updateViews();
		};
	};
	

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
