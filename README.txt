Overview
The Labyrinth Game project is a Java-based maze game where the player navigates a labyrinth, 
avoids enemies, and collects items. 
The game features both console and graphical views, and supports various gameplay mechanics 
such as movement, enemy AI, and different field types.

Features
- Navigate a labyrinth as the player character.
- Avoid or interact with enemies (e.g., ghosts).
- Collect items and reach objectives.
- Multiple view options: Console and Graphic.
- Start menu and game state management.

Project Structure
- /controller -> game logic and control flow (e.g., Controller.java, Labyrinth.java)
- /model -> core game data and logic (e.g., World.java, Enemy.java, Direction.java, FieldType.java)
- /view -> user interface (console and graphical views, start menu)
- /resources -> images and assets for the graphical view

How to Run
1. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).
2. Run the Labyrinth Class to start the game.
3. Follow the on-screen instructions to play.

How to play
- Select a difficulty
- select start
- move with WASD or arrow keys 
- restart game by pressing R
- exit game by pressing esc
- avoid the ghost and reach the goal (red rectangle) without getting caught by the ghosts

Class choices:
- The game logic is separated into controller, model, and view packages for clarity and maintainability.
- The model contains core game entities such as World, Enemy, and Direction.
- The controller manages game flow and user input.
- The view package provides both console and graphical interfaces.

Documentation:
The documentation can be found at ./doc and viewed via index.html
The project includes in-code documentation.

Used Java version: openjdk version "21.0.7" 
Used IDE: VS-Code
