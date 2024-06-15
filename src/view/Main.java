package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
    private JFrame frame;
    private PreGamePanel preGamePanel;


    public Main(){
        frame = new JFrame("Conquer the world!");
        preGamePanel = new PreGamePanel();
        setWindow();
     }

    public void setWindow(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.setLayout(new BorderLayout());
        setStartView();
    }

    private void setStartView(){
        frame.add(preGamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

      
}