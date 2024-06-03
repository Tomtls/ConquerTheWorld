import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    public static final int INITIAL_PANEL_WIDTH = 1050;
    public static final int INITIAL_PANEL_HEIGHT = 1400;
    public static final double PANEL_ASPECT_RATIO = (double) INITIAL_PANEL_WIDTH / INITIAL_PANEL_HEIGHT;

    private static final int[][] INITIAL_BUTTON_POSITION = {{309,0}, {541,91}, {90,197}, {614,266}, {532,365}, {0,458}, {687,617}, {218,619}, {459,626}, {26,748}, {353,815}, {185,950}};
    private JButton[] buttons;

    public MapPanel() {
        setLayout(null);
        setBackground(Color.CYAN);

        buttons = new JButton[12];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = ImageButton.createImageButton(i, INITIAL_BUTTON_POSITION[i]);
            add(buttons[i]);
        }

        setBounds(0, 0, INITIAL_PANEL_WIDTH, INITIAL_PANEL_HEIGHT);
    }   

    public void adjustButtons() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        double widthRatio = (double) panelWidth / MapPanel.INITIAL_PANEL_WIDTH;
        double heightRatio = (double) panelHeight / MapPanel.INITIAL_PANEL_HEIGHT;

        for (int i = 0; i < buttons.length && i < INITIAL_BUTTON_POSITION.length; i++) {
            ButtonAdjuster.adjustButton(buttons[i],i, this, INITIAL_BUTTON_POSITION[i], panelWidth, panelHeight, widthRatio, heightRatio);
        }
    }
}
