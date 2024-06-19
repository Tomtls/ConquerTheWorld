package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerWaitingPanel extends JPanel{

    private final String ip = "127.0.0.1";
    private String name;
    private Color color;
    private JButton backButton;
    private Main main;
     
    public ServerWaitingPanel(Main main, String name, Color color) {
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

                //Label for ip field
        JLabel waiting = new JLabel("Waiting for player to connect");
        waiting.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(waiting, gbc);

        //Label for ip field
        JLabel ipLabel = new JLabel("ServerIP: " + ip);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(ipLabel, gbc);
        
        //Label for player name field 
        JLabel playerName = new JLabel("Your Name: " + name);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(playerName, gbc);
        
        //Label for player name field 
        JLabel playerColor = new JLabel("Your Color: " + color.toString());
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(playerColor, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startMultiplayerSetup(name, color);
                // server stoppen?
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(backButton, gbc);
    }

}