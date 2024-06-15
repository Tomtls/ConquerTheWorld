package view;
import javax.imageio.ImageIO;
import javax.swing.*;


import model.IDButton;
import model.Player;
import model.Unit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MapPanel extends JPanel {
    public static final int INITIAL_PANEL_WIDTH = 1050;
    public static final int INITIAL_PANEL_HEIGHT = 1400;
    public static final double PANEL_ASPECT_RATIO = (double) INITIAL_PANEL_WIDTH / INITIAL_PANEL_HEIGHT;

    private static final int[][] INITIAL_BUTTON_POSITION = {{309,0}, {541,91}, {90,197}, {614,266}, {532,365}, {0,458}, {687,617}, {218,619}, {459,626}, {26,748}, {353,815}, {185,950}};
    private JButton[] buttons;
    private Player player1, player2;
    private int firstClickedButtonID = -1;

    public int[] units;
    public Color [] color;
    public Timer timer;

    public MapPanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        setLayout(null);
        setBackground(Color.CYAN);

        color = new Color[12];
        color[0] = player1.getColor();
        color[color.length - 1] = player2.getColor();
        for (int i = 1; i < INITIAL_BUTTON_POSITION.length-1; i++) {
            color[i] = Color.GRAY;
        }

        units = new int[12];
        buttons = new JButton[12];
        for (int i = 0; i < buttons.length; i++) {
            units[i] = 10;
            buttons[i] = IDButton.createIdButton(i, color[i], units[i]);
            add(buttons[i]);
        }
        
        setBounds(0, 0, INITIAL_PANEL_WIDTH, INITIAL_PANEL_HEIGHT);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for (int i = 0; i < units.length; i++) {
                    if(color[i] != Color.GRAY || units[i] < 10){
                        units[i]++;
                    }
                    buttons[i].setText(String.valueOf(units[i]));
                }
            }
        });
        
    }   



    public void adjustButtons() {
        int[] panelSize = { 
            getWidth(), 
            getHeight()
        };

        double[] sizeRatio = { 
            (double) getWidth() / MapPanel.INITIAL_PANEL_WIDTH, 
            (double) getHeight() / MapPanel.INITIAL_PANEL_HEIGHT
        };
        
        for (int i = 0; i < buttons.length && i < INITIAL_BUTTON_POSITION.length; i++) {
            adjustButton(buttons[i], color[i],i, INITIAL_BUTTON_POSITION[i], panelSize, sizeRatio);
                        
            for (MouseListener listener : buttons[i].getMouseListeners()) {
                buttons[i].removeMouseListener(listener);
            }

            final int index = i;
            buttons[index].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        buttons[index].getModel().setArmed(true);
                        buttons[index].getModel().setPressed(true);
                        firstClickedButtonID = index;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        buttons[index].getModel().setArmed(false);
                        buttons[index].getModel().setPressed(false);

                        Point pointOnScreen = e.getLocationOnScreen();
                        SwingUtilities.convertPointFromScreen(pointOnScreen, buttons[index].getParent());
                        Component comp = SwingUtilities.getDeepestComponentAt(buttons[index].getParent(), pointOnScreen.x, pointOnScreen.y);
                        
                        if (comp instanceof IDButton && comp != buttons[index]) {
                            IDButton targetButton = (IDButton) comp;
                            System.out.println("Mouse pressed button with ID " + firstClickedButtonID);
                            System.out.println("Mouse released over button with ID " + targetButton.getId());
                            Unit.calculateUnits(firstClickedButtonID, targetButton.getId(), color, units);
                            adjustButtons();

                        }
                    }
                }
            });
        }
    }
    public static void adjustPanel(JFrame frame, JPanel panel) {
        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        double panelAspectRatio = MapPanel.PANEL_ASPECT_RATIO;
        int panelWidth = (int) (frameWidth * 0.9);
        int panelHeight = (int) (panelWidth / panelAspectRatio);

        if (panelHeight > frameHeight * 0.9) {
            panelHeight = (int) (frameHeight * 0.9);
            panelWidth = (int) (panelHeight * panelAspectRatio);
        }

        int panelX = (frameWidth - panelWidth) / 2;
        int panelY = (frameHeight - panelHeight) / 2;

        panel.setBounds(panelX, panelY, panelWidth, panelHeight);
    }
    public static void adjustButton(JButton button, Color color, int btnNumber, int[] initialPosition, int[] panelSize, double[] sizeRatio) {

        int xPosition = initialPosition[0] * panelSize[0] / MapPanel.INITIAL_PANEL_WIDTH;
        int yPosition = initialPosition[1] * panelSize[1] / MapPanel.INITIAL_PANEL_HEIGHT;

        try {
            BufferedImage bImg = ImageIO.read(new File("src/imgNumber/" + btnNumber + ".png"));
            Graphics2D g2d = bImg.createGraphics();

            g2d.drawImage(bImg, 0, 0, null);            
            g2d.setColor(color); 
            g2d.setComposite(AlphaComposite.SrcAtop);
            g2d.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
    
            g2d.dispose();
            int buttonWidth = (int) (bImg.getWidth() * sizeRatio[0]);
            int buttonHeight = (int) (bImg.getHeight() * sizeRatio[1]);
            
            Image scaledImg = bImg.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            ImageIcon imgIcon = new ImageIcon(scaledImg);
            
            button.setIcon(imgIcon);
            button.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
            button.revalidate();
            button.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}