package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGamePanel extends JPanel {
    private JButton startButton;
    private JTextField playerNameField;

    public PreGamePanel() {
        setPreferredSize(new Dimension(800,600));
        setLayout(new GridBagLayout());
        createComponents();
    }

    private void createComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel playerLabel = new JLabel("Player Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playerLabel, gbc);

        playerNameField = new JTextField(15);
        gbc.gridx = 1;
        add(playerNameField, gbc);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hier Logik für StartButton
                System.out.println("Start");
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
