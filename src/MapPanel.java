import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
            buttons[i] = IDButton.createImageButton(i, INITIAL_BUTTON_POSITION[i], color, units);
            add(buttons[i]);
        }
        
        setBounds(0, 0, INITIAL_PANEL_WIDTH, INITIAL_PANEL_HEIGHT);
        Timer timer = new Timer(1000, new ActionListener() {
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
        timer.start();
    }   

    public void adjustButtons() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        double widthRatio = (double) panelWidth / MapPanel.INITIAL_PANEL_WIDTH;
        double heightRatio = (double) panelHeight / MapPanel.INITIAL_PANEL_HEIGHT;

        for (int i = 0; i < buttons.length && i < INITIAL_BUTTON_POSITION.length; i++) {
            ButtonAdjuster.adjustButton(buttons[i], color[i],i, INITIAL_BUTTON_POSITION[i], panelWidth, panelHeight, widthRatio, heightRatio);
                        
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
                            UnitsCalculator.calculateUnits(firstClickedButtonID, targetButton.getId(), color, units);
                            adjustButtons();

                        }
                    }
                }
            });
        }
    }

}
