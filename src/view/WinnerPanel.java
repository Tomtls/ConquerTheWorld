package view;
import java.awt.Color;
import java.awt.Font;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class WinnerPanel extends JPanel {
    private Main main;
    private JButton backButton;
    private Player player;
     
    public WinnerPanel(Player player) {
        this.player = player;
        setBounds(0,0,800,600);
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        createComponents();
    }
 
    private void createComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
 
        JLabel winner = new JLabel(player.getName() + " is the winner!");
        winner.setFont(new Font("Arial", Font.BOLD, 30));
        winner.setForeground(player.getColor());
        add(winner, gbc);
 
        backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setStartView();
                // server stoppen?
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(backButton, gbc);
    }
}