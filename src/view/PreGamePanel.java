package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGamePanel extends JPanel {
    private JButton startButton;
    private JTextField playerNameField;
    private Main main;

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

        //Start game button 
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.getText().isEmpty()) {
                    playerNameField.setText("Username");
                }
                //Farbe ist nur zum testen, muss noch geändert werden
                main.startGame(getPlayerName(), Color.RED);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);        
    }

    public String getPlayerName() {
        return playerNameField.getText();
    }

}
