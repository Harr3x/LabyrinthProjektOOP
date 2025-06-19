package view;

import javax.swing.*;

import controller.Labyrinth;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class StartMenu extends JFrame {
    private JRadioButton easyButton;
    private JRadioButton difficultButton;
    private JButton startButton;
    private Boolean isHard;
    private Boolean gameState;
    private JLabel statusLabel;
    private Image backgroundImage;

    public StartMenu(int width, int height) {
        setTitle("PÄC-MAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(new BorderLayout());

        // Hintergrundbild laden
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/resources/MenuBG.jpg")).getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }

        // Status-Label initialisieren und oben einfügen
        statusLabel = new JLabel("PÄC-MAN", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 48));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(0, 0, 0, 255));
        statusPanel.setOpaque(true);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);

        // Panel für die Buttons und Titel in der Mitte (vertikal anordnen)
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

        // 1. Zeile: Play-Button
        gbc.gridy = 0;
        startButton = new JButton("Play");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setPreferredSize(new Dimension(260, 60));
        startButton.setBackground(new Color(255, 215, 0)); // kräftiges gelb
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        centerPanel.add(startButton, gbc);
        startButton.addActionListener(e -> startClicked());

        // 2. Zeile: Abstand nach unten (Platzhalter)
        gbc.gridy = 1;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)), gbc); // 40px Abstand

        // 3. Zeile: Titel für Difficulty
        gbc.gridy = 2;
        JLabel titleDifficulty = new JLabel("Difficulty:", SwingConstants.CENTER);
        titleDifficulty.setFont(new Font("Arial", Font.BOLD, 18));
        titleDifficulty.setOpaque(true);
        titleDifficulty.setBackground(new Color(255, 215, 0));
        titleDifficulty.setForeground(Color.BLACK);
        titleDifficulty.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        titleDifficulty.setPreferredSize(new Dimension(180, 38));
        centerPanel.add(titleDifficulty, gbc);

        // 4. Zeile: Easy-Button (als JButton)
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

        // 5. Zeile: Hard-Button (als JButton)
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

        // 6. Zeile: Abstand nach unten (Platzhalter)
        gbc.gridy = 5;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)), gbc); // 30px Abstand

        add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame(boolean isHard) {
        System.out.println("Spiel gestartet. Schwierigkeit: " + (isHard ? "Schwer" : "Leicht"));
        Labyrinth.startGame(isHard);
    
        //dispose(); 
        setVisible(false); // Hide the start menu
    }

    // Passe easySelected() und difficultSelected() an, damit repaint ausgelöst wird
    private void easySelected() {
        isHard = false;
        System.out.println("Schwierigkeit auf leicht gesetzt");
        repaint();
    }

    private void difficultSelected() {
        isHard = true;
        System.out.println("Schwierigkeit auf schwer gesetzt");
        repaint();
    }

    private void startClicked() {;
        startGame(isHard);
    }

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

    public void setGameState(Boolean gameState){
        this.gameState = gameState;
        gameWon();
        setVisible(true);
    }

    public Boolean getGameState(){
        return gameState;
    }
}
