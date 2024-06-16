package view;

import model.Game;
import model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
    public static final int INITIAL_PANEL_WIDTH = 1050;
    public static final int INITIAL_PANEL_HEIGHT = 1400;
    public static final double PANEL_ASPECT_RATIO = (double) INITIAL_PANEL_WIDTH / INITIAL_PANEL_HEIGHT;
    private static final int[][] INITIAL_BUTTON_POSITION = {
        {309,0}, {541,91}, {90,197}, {614,266}, {532,365}, {0,458}, 
        {687,617}, {218,619}, {459,626}, {26,748}, {353,815}, {185,950}
    };
    private IDButton[] buttons;
    private Game game;

    public MapPanel(Game game){
        this.game = game;
        setLayout(null);
        setBackground(Color.WHITE); //test
        
        addIdButtons();
        adjustPanel(Main.getFrame()); //adjust Size & Position(Center) of MapPanel

    }   

    private void addIdButtons(){
        buttons = new IDButton[INITIAL_BUTTON_POSITION.length];
        for (int i = 0; i < buttons.length; i++) {
            State state = game.getState(i);
            buttons[i] = IDButton.createIdButton(state.getId(), state.getOwner().getColor(), state.getUnits());
            add(buttons[i]);
        }
    }

    public void adjustPanel(JFrame frame) {
        //neue Framegöße berechnen
        Insets insets = frame.getInsets();
        int frameHeight = frame.getHeight() - insets.top - insets.bottom;
        int frameWidth = frame.getWidth() - insets.left - insets.right;

        int panelWidth = (int) (frameWidth * 0.9);
        int panelHeight = (int) (panelWidth / PANEL_ASPECT_RATIO);

        if (panelHeight > frameHeight) {
            panelHeight = frameHeight;
            panelWidth = (int) (panelHeight * PANEL_ASPECT_RATIO);
        }

        int panelX = (frameWidth - panelWidth) / 2;
        int panelY = (frameHeight - panelHeight) / 2;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        adjustButtons();
    }

    public void adjustButtons() {
          double[] sizeRatio = { 
            (double) getWidth() / MapPanel.INITIAL_PANEL_WIDTH, 
            (double) getHeight() / MapPanel.INITIAL_PANEL_HEIGHT
        };
        
        for (int i = 0; i < buttons.length && i < INITIAL_BUTTON_POSITION.length; i++) {
            adjustButton(buttons[i], INITIAL_BUTTON_POSITION[i], sizeRatio);
        }
    }

    public static void adjustButton(IDButton button, int[] initialPosition, double[] sizeRatio) {
        int xPosition = (int) (initialPosition[0] * sizeRatio[0]);
        int yPosition = (int) (initialPosition[1] * sizeRatio[1]);
        
        BufferedImage img = button.getImg();
        int buttonWidth = (int) (img.getWidth() * sizeRatio[0]);
        int buttonHeight = (int) (img.getHeight() * sizeRatio[1]);
        Image scaledImg = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon imgIcon = new ImageIcon(scaledImg);
        
        button.setIcon(imgIcon);
        button.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
        button.revalidate();
        button.repaint();
    
    }
    
    public void updateButton(int index, State state) {
        buttons[index].setText(String.valueOf(state.getUnits()));
    }

}