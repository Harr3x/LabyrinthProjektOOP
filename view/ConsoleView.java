package view;

import model.FieldType;
import model.World;

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
				if (fields[x][y] == FieldType.PLAYER) {
					System.out.print("O");
				} else if (fields[x][y] == FieldType.WALL) {
					System.out.print("#");
				} else if (x == world.getStartX() && y == world.getStartY()) {
					System.out.print("S");
				} else if (fields[x][y] == FieldType.GOAL) {
						System.out.print("G");
				} else {
					System.out.print(".");
				}
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

}
