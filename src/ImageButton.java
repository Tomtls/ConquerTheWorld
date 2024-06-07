import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageButton {
    public static JButton createImageButton(int imgNumber, int[] initialPosition, Color[] color) {
        try {
            BufferedImage img = ImageIO.read(new File("src/imgNumber/" + imgNumber + ".png"));
            ImageIcon imgIcon = new ImageIcon(img);
            JButton button = new JButton(imgIcon) {
                @Override
                public boolean contains(int x, int y) {
                    Dimension size = getSize();

                    double scaleX = (double) size.width / img.getWidth();
                    double scaleY = (double) size.height / img.getHeight();

                    int imgX = (int) (x / scaleX);
                    int imgY = (int) (y / scaleY);
                    
                    if (imgX >= 0 && imgX < img.getWidth() && imgY >= 0 && imgY < img.getHeight()) {
                        int alpha = (img.getRGB(imgX, imgY) >> 24) & 0xff;
                        return alpha > 0;
                    }
                    return false;
                }
            };

            button.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            button.setBounds(initialPosition[0], initialPosition[1], img.getWidth(), img.getHeight());
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    color[imgNumber] =  Color.GREEN;
                    
                    System.out.println("Button " + imgNumber + " clicked");
                    
                }
            });
            return button;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
