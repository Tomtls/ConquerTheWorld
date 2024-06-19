package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import network.GameClient;

public class ClientConnectingPanel extends JPanel {
    
    private Main main;
    private String name;
    private Color color;
  //  private JTextField ipField;
    private JButton connectButton, backButton;
    
    private final String ip = "127.0.0.1"; //Default IP für Localhost

    public ClientConnectingPanel (Main main, String name, Color color) {
        this.main = main;
        this.name = name;
        this.color = color;
        setBounds(0,0,800,600);
        setLayout(new GridBagLayout());
        createComponents();
    }

    private void createComponents() {
               //Layout 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Label  
        JLabel waiting = new JLabel("Connect to server...");
        waiting.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(waiting, gbc);
        
        //Label for player name field 
        JLabel playerName = new JLabel(name);
        gbc.gridy = 1;
        add(playerName, gbc);
        
        //Label for player name field 
        JLabel playerColor = new JLabel(color.toString());
        gbc.gridy = 2;
        add(playerColor, gbc);
        
        JTextField ipField = new JTextField(ip);
        gbc.gridy = 3;
        add(ipField);
        
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("connected");
                main.startClient(name, color, ipField.getText());
                //controller.startTimer();

            }
        });
        gbc.gridy = 4;
        add(connectButton, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startMultiplayerSetup(name, color);
            }
        });
        gbc.gridy = 5;
        add(backButton, gbc);

       
    }
    
}
