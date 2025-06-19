package model;

/**
 * Represents the type of a field in the game world.
 * EMPTY: A free field where the player and enemies can move.
 * WALL:  An impassable field (the maze walls).
 * DOT:   A field with a collectible dot.
 */
public enum FieldType {
    /** A free field where the player and enemies can move. */
    EMPTY,
    /** An impassable field (the maze walls). */
    WALL,
    /** A field with a collectible dot (for points). */
    DOT // Player can collect this for points
}
