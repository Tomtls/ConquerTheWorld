import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ButtonAdjuster {
    public static void adjustButton(JButton button, int btnNumber, JPanel panel, int[] initialPosition, int panelWidth, int panelHeight, double widthRatio, double heightRatio) {

        int xPosition = initialPosition[0] * panelWidth / MapPanel.INITIAL_PANEL_WIDTH;
        int yPosition = initialPosition[1] * panelHeight / MapPanel.INITIAL_PANEL_HEIGHT;

        try {
            BufferedImage bImg = ImageIO.read(new File("src/imgNumber/" + btnNumber + ".png"));

            int buttonWidth = (int) (bImg.getWidth() * widthRatio);
            int buttonHeight = (int) (bImg.getHeight() * heightRatio);
            
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
