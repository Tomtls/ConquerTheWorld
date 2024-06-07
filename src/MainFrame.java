

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    private MapPanel mapPanel; 
    private boolean resizing;

    public MainFrame(){
        setTitle("Conquer the world!");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        mapPanel = new MapPanel(new Player(Color.RED));
        
        add(mapPanel);   
        
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
}