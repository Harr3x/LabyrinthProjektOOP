package view;

import model.World;
import model.FieldType;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(World world) {
		var fields = world.getFields();

		for (int y = 0; y < fields[0].length; y++) {
			for (int x = 0; x < fields.length; x++) {
				if (x == world.getPlayerX() && y == world.getPlayerY()) {
					System.out.print("[P]");
				} else if (isEnemyAt(world, x, y)) {
					System.out.print("[E]");
				} else if (fields[x][y] == FieldType.WALL) {
					System.out.print("[#]");
				} else if (x == world.getStartX() && y == world.getStartY()) {
					System.out.print("[S]");
				} else if (x == world.getGoalX() && y == world.getGoalY()) {
					System.out.print("[G]");
				} else if (fields[x][y] == FieldType.DOT) {
					System.out.print("[.]");
				} else if (fields[x][y] == FieldType.EMPTY) {
					System.out.print("[ ]");
				}
			}
			// A newline between every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

	// Hilfsmethode zum Prüfen, ob ein Enemy auf (x, y) steht
	private boolean isEnemyAt(World world, int x, int y) {
		for (var enemy : world.getEnemies()) {
			if (enemy.getX() == x && enemy.getY() == y) {
				return true;
			}
		}
		return false;
	}



}


