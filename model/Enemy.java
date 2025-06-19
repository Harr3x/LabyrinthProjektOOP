package model;

/**
 * Represents an enemy in the game world.
 * Each enemy has a position (x, y) and a name (e.g. color or type).
 */
public class Enemy {
    /** The x position of the enemy on the board. */
    private int enemyX;
    /** The y position of the enemy on the board. */
    private int enemyY;
    /** The name or type of the enemy (e.g. "red", "pink"). */
    private String name;

    /**
     * Creates a new enemy at the given position with the given name.
     * @param startX the initial x position
     * @param startY the initial y position
     * @param name the name or type of the enemy
     */
    public Enemy (int startX, int startY, String name) {
        this.enemyX = startX;
        this.enemyY = startY;
        this.name = name;
    }

    /**
     * Returns the x position of the enemy.
     * @return the x position
     */
    public int getX() {
        return enemyX;
    }

    /**
     * Returns the y position of the enemy.
     * @return the y position
     */
    public int getY() {
        return enemyY;
    }

    /**
     * Returns the name or type of the enemy.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the x position of the enemy.
     * @param enemyX the new x position
     */
    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX; // Update enemy's x coordinate
    }

    /**
     * Sets the y position of the enemy.
     * @param enemyY the new y position
     */
    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY; // Update enemy's y coordinate
    }

    /**
     * Sets the name or type of the enemy.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name; // Update enemy's name/type
    }
}
