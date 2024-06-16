package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGamePanel extends JPanel {
    private JButton startButton, multiplayerButton;
    private JTextField playerNameField;
    private Main main;

    private JComboBox<Color> colorComboBox;

    public PreGamePanel(Main main) {
        this.main = main;
        setBounds(0,0,800,600);
        setLayout(new GridBagLayout());
        createComponents();
    }

    private void createComponents() {
        //Layout 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Label for player name field 
        JLabel playerLabel = new JLabel("Player Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playerLabel, gbc);

        //Player name field
        playerNameField = new JTextField(15);
        gbc.gridx = 1;
        add(playerNameField, gbc);

        // ComboBox for selecting color
        colorComboBox = new JComboBox<>(new Color[] {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(colorComboBox, gbc);
        //Start game button 
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.getText().isEmpty()) {
                    playerNameField.setText("Username");
                }
                Color selectedColor = (Color) colorComboBox.getSelectedItem();
                main.startGame(getPlayerName(), selectedColor);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);        

        //Multiplayer button
        multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.getText().isEmpty()) {
                    playerNameField.setText("Username");
                }
                Color selectedColor = (Color) colorComboBox.getSelectedItem();
                main.startMultiplayer(getPlayerName(), selectedColor);
                
            }
        });
        gbc.gridx = 1;
        add(multiplayerButton, gbc);
    }

    private String getPlayerName() {
        return playerNameField.getText();
    }

}
