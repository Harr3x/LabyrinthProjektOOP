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

    public StartMenu() {
        
        setTitle ("Startmenü - Labyrinth");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);

        setLayout(new BorderLayout());

        // Difficulty
        JLabel titleDiificultyButtons = new JLabel("Wähle eine Schwierigkeit:");
        add (titleDiificultyButtons, BorderLayout.NORTH);
        easyButton = new JRadioButton ("Leicht");
        difficultButton = new JRadioButton ("schwer");
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(difficultButton);   
        easyButton.addActionListener((ActionEvent e) -> {
            this.isHard = false;
            System.out.println("Schwierigkeit auf leicht gesetzt");
        });

        difficultButton.addActionListener((ActionEvent e) -> {
            this.isHard = true;
            System.out.println("Schwierigkeit auf leicht gesetzt");
        });
        easyButton.setBounds(20, 20, 100, 30);
        add(easyButton);
        difficultButton.setBounds(20, 60, 100, 30);
        add(difficultButton);
        setLocationRelativeTo(null);

        // Start Game 
        startButton = new JButton("Spiel beginnen");
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isHard = difficultButton.isSelected();
                startGame(isHard);
                
            }
        });

        setVisible(true);

    }
public void startGame(boolean isHard) {
        System.out.println("Spiel gestartet. Schwierigkeit: " + (isHard ? "Schwer" : "Leicht"));
        Labyrinth.startGame(isHard);
        // Hier rufst du z.B. den Controller auf:
        // new Controller(isHard);
        dispose(); // Menü schließen
    }

    





    
}
