package model;

public class Enemy {
    private int enemyX;
    private int enemyY;
    private String name;

    public Enemy (int startX, int startY, String name) {
        this.enemyX = startX;
        this.enemyY = startY;
        this.name = name;
    }
    public int getX() {
        return enemyX;
    }
    public int getY() {
        return enemyY;
    }

    public String getName() {
        return name;
    }

    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX;
    }

    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }

    public void setName(String name) {
        this.name = name;
    }
}
