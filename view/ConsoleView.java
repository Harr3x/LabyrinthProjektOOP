package view;

import model.World;
import model.FieldType;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 * Legend:
 * [P] = Player
 * [E] = Enemy
 * [#] = Wall
 * [S] = Start
 * [G] = Goal
 * [.] = Dot
 * [ ] = Empty
 */
public class ConsoleView implements View {

	/**
	 * Prints the current state of the world to the console.
	 * @param world the {@link World} whose state should be printed
	 */
	@Override
	public void update(World world) {
		var fields = world.getFields();

		for (int y = 0; y < fields[0].length; y++) {
			for (int x = 0; x < fields.length; x++) {
				System.out.print(getFieldSymbol(x, y, world)); // Print symbol for each field
			}
			// A newline between every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

	/**
	 * Returns the symbol for the field at position (x, y) in the world.
	 * @param x the x position
	 * @param y the y position
	 * @param world the {@link World}
	 * @return the symbol as a String
	 */
	private String getFieldSymbol(int x, int y, World world) {
		var fields = world.getFields();
		if (x == world.getPlayerX() && y == world.getPlayerY()) {
			return "[P]"; // Player position
		} else if (isEnemyAt(world, x, y)) {
			return "[E]"; // Enemy position
		} else if (fields[x][y] == FieldType.WALL) {
			return "[#]"; // Wall
		} else if (x == world.getStartX() && y == world.getStartY()) {
			return "[S]"; // Start position
		} else if (x == world.getGoalX() && y == world.getGoalY()) {
			return "[G]"; // Goal position
		} else if (fields[x][y] == FieldType.DOT) {
			return "[.]"; // Dot
		} else if (fields[x][y] == FieldType.EMPTY) {
			return "[ ]"; // Empty field
		}
		return "[?]"; // Unknown field type
	}

	/**
	 * Checks if an enemy is at the given position.
	 * @param world the {@link World}
	 * @param x the x position
	 * @param y the y position
	 * @return true if an enemy is at (x, y), false otherwise
	 */
	private boolean isEnemyAt(World world, int x, int y) {
		for (var enemy : world.getEnemies()) {
			if (enemy.getX() == x && enemy.getY() == y) {
				return true; // Found an enemy at (x, y)
			}
		}
		return false;
	}

}


