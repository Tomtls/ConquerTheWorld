import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ButtonAdjuster {
    public static void adjustButton(JButton button, Color color, int btnNumber, int[] initialPosition, int panelWidth, int panelHeight, double widthRatio, double heightRatio) {

        int xPosition = initialPosition[0] * panelWidth / MapPanel.INITIAL_PANEL_WIDTH;
        int yPosition = initialPosition[1] * panelHeight / MapPanel.INITIAL_PANEL_HEIGHT;

        try {
            BufferedImage bImg = ImageIO.read(new File("src/imgNumber/" + btnNumber + ".png"));
            Graphics2D g2d = bImg.createGraphics();

            g2d.drawImage(bImg, 0, 0, null);            
            g2d.setColor(color); 
            g2d.setComposite(AlphaComposite.SrcAtop);
            g2d.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
    
            g2d.dispose();
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
