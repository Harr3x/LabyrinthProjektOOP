package view;

import javax.swing.*;

import controller.Labyrinth;

import java.awt.*;

/**
 * The start menu window for the game. Allows the user to select difficulty and start the game.
 */
public class StartMenu extends JFrame {
    /** Radio button for easy mode (not used in current UI). */
    private JRadioButton easyButton;
    /** Radio button for hard mode (not used in current UI). */
    private JRadioButton difficultButton;
    /** The button to start the game. */
    private JButton startButton;
    /** True if hard mode is selected, false for easy. */
    private Boolean isHard;
    /** The current game state (true = win, false = lose, null = not started). */
    private Boolean gameState;
    /** The label displaying the status or result. */
    private JLabel statusLabel;
    /** The background image for the menu. */
    private Image backgroundImage;

    /**
     * Constructs the start menu window with the given width and height.
     * @param width the width of the window
     * @param height the height of the window
     */
    public StartMenu(int width, int height) {
        setTitle("PÄC-MAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(new BorderLayout());

        // Load background image
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/MenuBG.jpg")).getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }

        // Initialize status label and add it at the top
        statusLabel = new JLabel("PÄC-MAN", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 48));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(0, 0, 0, 255));
        statusPanel.setOpaque(true);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);

        // Panel for the buttons and title in the center (vertical layout)
        JPanel centerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1st row: Play button
        gbc.gridy = 0;
        startButton = new JButton("Play");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setPreferredSize(new Dimension(260, 60));
        startButton.setBackground(new Color(255, 215, 0)); // strong yellow
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        centerPanel.add(startButton, gbc);
        startButton.addActionListener(e -> startClicked());
        startButton.setOpaque(true);

        // 2nd row: vertical space (placeholder)
        gbc.gridy = 1;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)), gbc); // 40px space

        // 3rd row: Title for difficulty
        gbc.gridy = 2;
        JLabel titleDifficulty = new JLabel("Difficulty:", SwingConstants.CENTER);
        titleDifficulty.setFont(new Font("Arial", Font.BOLD, 18));
        titleDifficulty.setOpaque(true);
        titleDifficulty.setBackground(new Color(255, 215, 0));
        titleDifficulty.setForeground(Color.BLACK);
        titleDifficulty.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        titleDifficulty.setPreferredSize(new Dimension(180, 38));
        centerPanel.add(titleDifficulty, gbc);

        // 4th row: Easy button (as JButton)
        gbc.gridy = 3;
        JButton easyBtn = new JButton("Easy");
        easyBtn.setFont(new Font("Arial", Font.BOLD, 18));
        easyBtn.setBackground(new Color(255, 215, 0));
        easyBtn.setForeground(Color.BLACK);
        easyBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        easyBtn.setFocusPainted(false);
        easyBtn.setPreferredSize(new Dimension(180, 38));
        easyBtn.setContentAreaFilled(true);
        easyBtn.setOpaque(true);
        easyBtn.addActionListener(e -> easySelected());
        centerPanel.add(easyBtn, gbc);

        // 5th row: Hard button (as JButton)
        gbc.gridy = 4;
        JButton hardBtn = new JButton("Hard");
        hardBtn.setFont(new Font("Arial", Font.BOLD, 18));
        hardBtn.setBackground(new Color(255, 215, 0));
        hardBtn.setForeground(Color.BLACK);
        hardBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        hardBtn.setFocusPainted(false);
        hardBtn.setPreferredSize(new Dimension(180, 38));
        hardBtn.setContentAreaFilled(true);
        hardBtn.setOpaque(true);
        hardBtn.addActionListener(e -> difficultSelected());
        centerPanel.add(hardBtn, gbc);

        // 6th row: vertical space (placeholder)
        gbc.gridy = 5;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)), gbc); // 30px space

        add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Starts the game with the selected difficulty.
     * @param isHard true for hard mode, false for easy
     */
    public void startGame(Boolean isHard) {
        if (isHard == null) {
            isHard = false; // Default to easy if not
        }
        System.out.println("Spiel gestartet. Schwierigkeit: " + (isHard ? "Schwer" : "Leicht"));
        Labyrinth.startGame(isHard);
    
        //dispose(); 
        setVisible(false); // Hide the start menu
    }

    /**
     * Sets the difficulty to easy and repaints the menu.
     */
    private void easySelected() {
        isHard = false;
        System.out.println("Schwierigkeit auf leicht gesetzt");
        repaint();
    }

    /**
     * Sets the difficulty to hard and repaints the menu.
     */
    private void difficultSelected() {
        isHard = true;
        System.out.println("Schwierigkeit auf schwer gesetzt");
        repaint();
    }

    /**
     * Handles the start button click and starts the game.
     */
    private void startClicked() {;
        startGame(isHard);
    }

    /**
     * Updates the status label based on the game state (win/lose/reset).
     */
    private void gameWon(){
        if (statusLabel == null) return;
        if (gameState == null){
            statusLabel.setText("PÄC-MAN");
        }
        else if (gameState == true){
            statusLabel.setText("DU HAST GEWONNEN!");
        }
        else if (gameState == false){
            statusLabel.setText("DU HAST VERLOREN!");
        }
        revalidate();
        repaint();
    }

    /**
     * Sets the game state and updates the status label.
     * @param gameState true for win, false for lose, null for reset
     */
    public void setGameState(Boolean gameState){
        this.gameState = gameState;
        gameWon();
        setVisible(true);
    }

    /**
     * Returns the current game state.
     * @return the game state (true = win, false = lose, null = not started)
     */
    public Boolean getGameState(){
        return gameState;
    }
}
