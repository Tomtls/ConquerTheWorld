import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    private MapPanel mapPanel; 
    private boolean resizing;
    private JButton startButton;

    public MainFrame(){
        setTitle("Conquer the world!");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        startButton = new JButton("Start Game");
        startButton.setBounds(350, 400, 100, 50);
        startButton.addActionListener(e -> startGame());
        mapPanel = new MapPanel(new Player(Color.RED), new Player(Color.BLUE));
        
        add(startButton);   
        
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e){
                if (!resizing) {
                    resizing = true;
                    SwingUtilities.invokeLater(() -> {
                        adjustPanel();
                        resizing = false;
                    });
                }        
            }
            @Override
            public void componentHidden(ComponentEvent e) {}
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
        });
        setVisible(true);
    }
    
    private void adjustPanel() {
        PanelAdjuster.adjustPanel(this, mapPanel);
        mapPanel.adjustButtons();
        mapPanel.revalidate();
        mapPanel.repaint();
        System.out.println("Panel adjusted");
    }   

    private void startGame(){
        remove(startButton);
        mapPanel = new MapPanel(new Player(Color.RED), new Player(Color.BLUE));
        
        add(mapPanel);   
        adjustPanel();
        revalidate();
        repaint();
    }
    
}