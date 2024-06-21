package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
public class PreGamePanel extends JPanel {
    private JButton startButton, multiplayerButton, loadGameButton;
    private JTextField playerNameField;
    private Main main;

    private JComboBox<String> colorComboBox;
    private String[] colors = { "RED", "BLUE", "GREEN", "CYAN", "MAGENTA", "PINK", "ORANGE", "YELLOW"};

    private Map<String, Color> colorMap;    
    public PreGamePanel(Main main) {
        this.main = main;
        setBounds(0,0,800,600);
        setLayout(new GridBagLayout());
        initializeColorMap();
        createComponents();
    }

    private void initializeColorMap() {
        colorMap = new HashMap<>();
        colorMap.put("RED", Color.RED);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("CYAN", Color.CYAN);
        colorMap.put("MAGENTA", Color.MAGENTA);
        colorMap.put("PINK", Color.PINK);
        colorMap.put("ORANGE", Color.ORANGE);
        colorMap.put("YELLOW", Color.YELLOW);
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
        playerNameField = new JTextField(10);
        gbc.gridy = 1;
        add(playerNameField, gbc);

        //Label for player name field 
        JLabel colorLabel = new JLabel("Player Color:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(colorLabel, gbc);

        // ComboBox for selecting color
        colorComboBox = new JComboBox<>(colors);
        gbc.gridy = 1;
        add(colorComboBox, gbc);
        //Start game button 
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.getText().isEmpty()) {
                    playerNameField.setText("Username");
                }
                String selectedColorName = (String) colorComboBox.getSelectedItem();
                Color selectedColor = colorMap.get(selectedColorName);
                main.startSingleGame(playerNameField.getText(), selectedColor);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(startButton, gbc);        

        //Multiplayer button
        multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNameField.getText().isEmpty()) {
                    playerNameField.setText("Username");
                }
                String selectedColorName = (String) colorComboBox.getSelectedItem();
                Color selectedColor = colorMap.get(selectedColorName);
                main.startMultiplayerSetup(playerNameField.getText(), selectedColor);
                
            }
        });
        gbc.gridx = 1;
        add(multiplayerButton, gbc);

        loadGameButton = new JButton("Load game");
        loadGameButton.addActionListener(e -> loadGame());
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(loadGameButton, gbc);
    }

    private void loadGame(){
        main.loadGame();
    }

}
