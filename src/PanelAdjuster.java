import javax.swing.*;

public class PanelAdjuster {
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
}
