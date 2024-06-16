package view;

import javax.swing.*;
import java.awt.*;

public class MultiplayerSetupPanel extends JPanel {
    private JButton serverButton;
    private JButton clientButton;
    private Main main;
    private String name;
    private Color color;


    public MultiplayerSetupPanel(Main main, String name, Color color) {
        this.main = main;
        this.name = name;
        this.color = color;
        setBounds(0,0,800,600);
        setLayout(new GridBagLayout());
        createComponents();
    }

    private void createComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Multiplayer Setup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        serverButton = new JButton("Start as Server");
        serverButton.addActionListener(e -> main.startAsServer(name, color));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(serverButton, gbc);

        clientButton = new JButton("Connect as Client");
        clientButton.addActionListener(e -> main.startAsClient(name, color));
        gbc.gridx = 1;
        add(clientButton, gbc);
    }
}

