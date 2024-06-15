package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class IDButton extends JButton{

    private static Font font = new Font("Arial", Font.BOLD, 20);
    private int id;
    private BufferedImage img;

    public IDButton(int id, Icon icon, BufferedImage img) {
        super(icon);
        this.id = id;
        this.img = img;
    }
    
    public static IDButton createIdButton(int id, Color color, int units) {
        try {
            BufferedImage img = ImageIO.read(new File("src/imgNumber/" + id + ".png"));
            BufferedImage colorizedImg = changeImageColor(img, color);
            ImageIcon imgIcon = new ImageIcon(colorizedImg);

            IDButton button = new IDButton(id, imgIcon, colorizedImg);
            button.setText(String.valueOf(units));
            button.setHorizontalTextPosition(CENTER); 
            button.setVerticalTextPosition(CENTER);
            button.setForeground(Color.BLACK);
            button.setFont(font);
            
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);

            button.setActionCommand(String.valueOf(id));

            return button;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean contains(int x, int y) {
        if (img == null) {
            return super.contains(x, y);
        }

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

    public void addUnits(IDButton button, int units){
        //Text der Units 
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setForeground(Color.BLACK);
        button.setFont(font);
        button.setText(String.valueOf(units));        
    }

    public static BufferedImage changeImageColor(BufferedImage bImg, Color color) {
        BufferedImage coloredImage = new BufferedImage(bImg.getWidth(), bImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = coloredImage.createGraphics();
        g2d.drawImage(bImg, 0, 0, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(color);
        g2d.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
        g2d.dispose();
        return coloredImage;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getImg() {
        return img;
    }
}