import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class IDButton extends JButton{

    private static Font font = new Font("Arial", Font.BOLD, 20);
    private int id;
    
    public IDButton(int id, Icon icon){
        super(icon);
        this.id = id;
    }
    
    public static IDButton createImageButton(int imgID, int[] initialPosition, Color[] color, int[] units) {
        try {
            BufferedImage img = ImageIO.read(new File("src/imgNumber/" + imgID + ".png"));
            ImageIcon imgIcon = new ImageIcon(img);
            IDButton button = new IDButton(imgID, imgIcon) {
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

            //Text der Units 
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setFont(font);
            button.setForeground(Color.BLACK);
            button.setText(String.valueOf(units[imgID]));

            return button;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }
}